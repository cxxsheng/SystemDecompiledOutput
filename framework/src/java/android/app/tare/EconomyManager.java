package android.app.tare;

/* loaded from: classes.dex */
public class EconomyManager {
    public static final long CAKE_IN_ARC = 1000000000;
    public static final long DEFAULT_AM_REWARD_TOP_ACTIVITY_ONGOING_CAKES = 10000000;
    public static final boolean DEFAULT_ENABLE_POLICY_ALARM = true;
    public static final boolean DEFAULT_ENABLE_POLICY_JOB_SCHEDULER = true;
    public static final int DEFAULT_ENABLE_TARE_MODE = 0;
    public static final long DEFAULT_JS_REWARD_TOP_ACTIVITY_ONGOING_CAKES = 500000000;
    public static final int ENABLED_MODE_OFF = 0;
    public static final int ENABLED_MODE_ON = 1;
    public static final int ENABLED_MODE_SHADOW = 2;
    public static final java.lang.String KEY_AM_ACTION_ALARM_ALARMCLOCK_BASE_PRICE = "am_action_alarm_alarmclock_base_price";
    public static final java.lang.String KEY_AM_ACTION_ALARM_ALARMCLOCK_CTP = "am_action_alarm_alarmclock_ctp";
    public static final java.lang.String KEY_AM_ACTION_ALARM_ALLOW_WHILE_IDLE_EXACT_NONWAKEUP_BASE_PRICE = "am_action_alarm_allow_while_idle_exact_nonwakeup_base_price";
    public static final java.lang.String KEY_AM_ACTION_ALARM_ALLOW_WHILE_IDLE_EXACT_NONWAKEUP_CTP = "am_action_alarm_allow_while_idle_exact_nonwakeup_ctp";
    public static final java.lang.String KEY_AM_ACTION_ALARM_ALLOW_WHILE_IDLE_EXACT_WAKEUP_BASE_PRICE = "am_action_alarm_allow_while_idle_exact_wakeup_base_price";
    public static final java.lang.String KEY_AM_ACTION_ALARM_ALLOW_WHILE_IDLE_EXACT_WAKEUP_CTP = "am_action_alarm_allow_while_idle_exact_wakeup_ctp";
    public static final java.lang.String KEY_AM_ACTION_ALARM_ALLOW_WHILE_IDLE_INEXACT_NONWAKEUP_BASE_PRICE = "am_action_alarm_allow_while_idle_inexact_nonwakeup_base_price";
    public static final java.lang.String KEY_AM_ACTION_ALARM_ALLOW_WHILE_IDLE_INEXACT_NONWAKEUP_CTP = "am_action_alarm_allow_while_idle_inexact_nonwakeup_ctp";
    public static final java.lang.String KEY_AM_ACTION_ALARM_ALLOW_WHILE_IDLE_INEXACT_WAKEUP_BASE_PRICE = "am_action_alarm_allow_while_idle_inexact_wakeup_base_price";
    public static final java.lang.String KEY_AM_ACTION_ALARM_ALLOW_WHILE_IDLE_INEXACT_WAKEUP_CTP = "am_action_alarm_allow_while_idle_inexact_wakeup_ctp";
    public static final java.lang.String KEY_AM_ACTION_ALARM_EXACT_NONWAKEUP_BASE_PRICE = "am_action_alarm_exact_nonwakeup_base_price";
    public static final java.lang.String KEY_AM_ACTION_ALARM_EXACT_NONWAKEUP_CTP = "am_action_alarm_exact_nonwakeup_ctp";
    public static final java.lang.String KEY_AM_ACTION_ALARM_EXACT_WAKEUP_BASE_PRICE = "am_action_alarm_exact_wakeup_base_price";
    public static final java.lang.String KEY_AM_ACTION_ALARM_EXACT_WAKEUP_CTP = "am_action_alarm_exact_wakeup_ctp";
    public static final java.lang.String KEY_AM_ACTION_ALARM_INEXACT_NONWAKEUP_BASE_PRICE = "am_action_alarm_inexact_nonwakeup_base_price";
    public static final java.lang.String KEY_AM_ACTION_ALARM_INEXACT_NONWAKEUP_CTP = "am_action_alarm_inexact_nonwakeup_ctp";
    public static final java.lang.String KEY_AM_ACTION_ALARM_INEXACT_WAKEUP_BASE_PRICE = "am_action_alarm_inexact_wakeup_base_price";
    public static final java.lang.String KEY_AM_ACTION_ALARM_INEXACT_WAKEUP_CTP = "am_action_alarm_inexact_wakeup_ctp";
    public static final java.lang.String KEY_AM_INITIAL_CONSUMPTION_LIMIT = "am_initial_consumption_limit";
    public static final java.lang.String KEY_AM_MAX_CONSUMPTION_LIMIT = "am_maximum_consumption_limit";
    public static final java.lang.String KEY_AM_MAX_SATIATED_BALANCE = "am_max_satiated_balance";
    public static final java.lang.String KEY_AM_MIN_CONSUMPTION_LIMIT = "am_minimum_consumption_limit";
    public static final java.lang.String KEY_AM_MIN_SATIATED_BALANCE_EXEMPTED = "am_min_satiated_balance_exempted";
    public static final java.lang.String KEY_AM_MIN_SATIATED_BALANCE_HEADLESS_SYSTEM_APP = "am_min_satiated_balance_headless_system_app";
    public static final java.lang.String KEY_AM_MIN_SATIATED_BALANCE_OTHER_APP = "am_min_satiated_balance_other_app";
    public static final java.lang.String KEY_AM_REWARD_NOTIFICATION_INTERACTION_INSTANT = "am_reward_notification_interaction_instant";
    public static final java.lang.String KEY_AM_REWARD_NOTIFICATION_INTERACTION_MAX = "am_reward_notification_interaction_max";
    public static final java.lang.String KEY_AM_REWARD_NOTIFICATION_INTERACTION_ONGOING = "am_reward_notification_interaction_ongoing";
    public static final java.lang.String KEY_AM_REWARD_NOTIFICATION_SEEN_INSTANT = "am_reward_notification_seen_instant";
    public static final java.lang.String KEY_AM_REWARD_NOTIFICATION_SEEN_MAX = "am_reward_notification_seen_max";
    public static final java.lang.String KEY_AM_REWARD_NOTIFICATION_SEEN_ONGOING = "am_reward_notification_seen_ongoing";
    public static final java.lang.String KEY_AM_REWARD_NOTIFICATION_SEEN_WITHIN_15_INSTANT = "am_reward_notification_seen_within_15_instant";
    public static final java.lang.String KEY_AM_REWARD_NOTIFICATION_SEEN_WITHIN_15_MAX = "am_reward_notification_seen_within_15_max";
    public static final java.lang.String KEY_AM_REWARD_NOTIFICATION_SEEN_WITHIN_15_ONGOING = "am_reward_notification_seen_within_15_ongoing";
    public static final java.lang.String KEY_AM_REWARD_OTHER_USER_INTERACTION_INSTANT = "am_reward_other_user_interaction_instant";
    public static final java.lang.String KEY_AM_REWARD_OTHER_USER_INTERACTION_MAX = "am_reward_other_user_interaction_max";
    public static final java.lang.String KEY_AM_REWARD_OTHER_USER_INTERACTION_ONGOING = "am_reward_other_user_interaction_ongoing";
    public static final java.lang.String KEY_AM_REWARD_TOP_ACTIVITY_INSTANT = "am_reward_top_activity_instant";
    public static final java.lang.String KEY_AM_REWARD_TOP_ACTIVITY_MAX = "am_reward_top_activity_max";
    public static final java.lang.String KEY_AM_REWARD_TOP_ACTIVITY_ONGOING = "am_reward_top_activity_ongoing";
    public static final java.lang.String KEY_AM_REWARD_WIDGET_INTERACTION_INSTANT = "am_reward_widget_interaction_instant";
    public static final java.lang.String KEY_AM_REWARD_WIDGET_INTERACTION_MAX = "am_reward_widget_interaction_max";
    public static final java.lang.String KEY_AM_REWARD_WIDGET_INTERACTION_ONGOING = "am_reward_widget_interaction_ongoing";
    public static final java.lang.String KEY_ENABLE_POLICY_ALARM = "enable_policy_alarm";
    public static final java.lang.String KEY_ENABLE_POLICY_JOB_SCHEDULER = "enable_policy_job";
    public static final java.lang.String KEY_ENABLE_TARE_MODE = "enable_tare_mode";
    public static final java.lang.String KEY_JS_ACTION_JOB_DEFAULT_RUNNING_BASE_PRICE = "js_action_job_default_running_base_price";
    public static final java.lang.String KEY_JS_ACTION_JOB_DEFAULT_RUNNING_CTP = "js_action_job_default_running_ctp";
    public static final java.lang.String KEY_JS_ACTION_JOB_DEFAULT_START_BASE_PRICE = "js_action_job_default_start_base_price";
    public static final java.lang.String KEY_JS_ACTION_JOB_DEFAULT_START_CTP = "js_action_job_default_start_ctp";
    public static final java.lang.String KEY_JS_ACTION_JOB_HIGH_RUNNING_BASE_PRICE = "js_action_job_high_running_base_price";
    public static final java.lang.String KEY_JS_ACTION_JOB_HIGH_RUNNING_CTP = "js_action_job_high_running_ctp";
    public static final java.lang.String KEY_JS_ACTION_JOB_HIGH_START_BASE_PRICE = "js_action_job_high_start_base_price";
    public static final java.lang.String KEY_JS_ACTION_JOB_HIGH_START_CTP = "js_action_job_high_start_ctp";
    public static final java.lang.String KEY_JS_ACTION_JOB_LOW_RUNNING_BASE_PRICE = "js_action_job_low_running_base_price";
    public static final java.lang.String KEY_JS_ACTION_JOB_LOW_RUNNING_CTP = "js_action_job_low_running_ctp";
    public static final java.lang.String KEY_JS_ACTION_JOB_LOW_START_BASE_PRICE = "js_action_job_low_start_base_price";
    public static final java.lang.String KEY_JS_ACTION_JOB_LOW_START_CTP = "js_action_job_low_start_ctp";
    public static final java.lang.String KEY_JS_ACTION_JOB_MAX_RUNNING_BASE_PRICE = "js_action_job_max_running_base_price";
    public static final java.lang.String KEY_JS_ACTION_JOB_MAX_RUNNING_CTP = "js_action_job_max_running_ctp";
    public static final java.lang.String KEY_JS_ACTION_JOB_MAX_START_BASE_PRICE = "js_action_job_max_start_base_price";
    public static final java.lang.String KEY_JS_ACTION_JOB_MAX_START_CTP = "js_action_job_max_start_ctp";
    public static final java.lang.String KEY_JS_ACTION_JOB_MIN_RUNNING_BASE_PRICE = "js_action_job_min_running_base_price";
    public static final java.lang.String KEY_JS_ACTION_JOB_MIN_RUNNING_CTP = "js_action_job_min_running_ctp";
    public static final java.lang.String KEY_JS_ACTION_JOB_MIN_START_BASE_PRICE = "js_action_job_min_start_base_price";
    public static final java.lang.String KEY_JS_ACTION_JOB_MIN_START_CTP = "js_action_job_min_start_ctp";
    public static final java.lang.String KEY_JS_ACTION_JOB_TIMEOUT_PENALTY_BASE_PRICE = "js_action_job_timeout_penalty_base_price";
    public static final java.lang.String KEY_JS_ACTION_JOB_TIMEOUT_PENALTY_CTP = "js_action_job_timeout_penalty_ctp";
    public static final java.lang.String KEY_JS_INITIAL_CONSUMPTION_LIMIT = "js_initial_consumption_limit";
    public static final java.lang.String KEY_JS_MAX_CONSUMPTION_LIMIT = "js_maximum_consumption_limit";
    public static final java.lang.String KEY_JS_MAX_SATIATED_BALANCE = "js_max_satiated_balance";
    public static final java.lang.String KEY_JS_MIN_CONSUMPTION_LIMIT = "js_minimum_consumption_limit";
    public static final java.lang.String KEY_JS_MIN_SATIATED_BALANCE_EXEMPTED = "js_min_satiated_balance_exempted";
    public static final java.lang.String KEY_JS_MIN_SATIATED_BALANCE_HEADLESS_SYSTEM_APP = "js_min_satiated_balance_headless_system_app";
    public static final java.lang.String KEY_JS_MIN_SATIATED_BALANCE_INCREMENT_APP_UPDATER = "js_min_satiated_balance_increment_updater";
    public static final java.lang.String KEY_JS_MIN_SATIATED_BALANCE_OTHER_APP = "js_min_satiated_balance_other_app";
    public static final java.lang.String KEY_JS_REWARD_APP_INSTALL_INSTANT = "js_reward_app_install_instant";
    public static final java.lang.String KEY_JS_REWARD_APP_INSTALL_MAX = "js_reward_app_install_max";
    public static final java.lang.String KEY_JS_REWARD_APP_INSTALL_ONGOING = "js_reward_app_install_ongoing";
    public static final java.lang.String KEY_JS_REWARD_NOTIFICATION_INTERACTION_INSTANT = "js_reward_notification_interaction_instant";
    public static final java.lang.String KEY_JS_REWARD_NOTIFICATION_INTERACTION_MAX = "js_reward_notification_interaction_max";
    public static final java.lang.String KEY_JS_REWARD_NOTIFICATION_INTERACTION_ONGOING = "js_reward_notification_interaction_ongoing";
    public static final java.lang.String KEY_JS_REWARD_NOTIFICATION_SEEN_INSTANT = "js_reward_notification_seen_instant";
    public static final java.lang.String KEY_JS_REWARD_NOTIFICATION_SEEN_MAX = "js_reward_notification_seen_max";
    public static final java.lang.String KEY_JS_REWARD_NOTIFICATION_SEEN_ONGOING = "js_reward_notification_seen_ongoing";
    public static final java.lang.String KEY_JS_REWARD_OTHER_USER_INTERACTION_INSTANT = "js_reward_other_user_interaction_instant";
    public static final java.lang.String KEY_JS_REWARD_OTHER_USER_INTERACTION_MAX = "js_reward_other_user_interaction_max";
    public static final java.lang.String KEY_JS_REWARD_OTHER_USER_INTERACTION_ONGOING = "js_reward_other_user_interaction_ongoing";
    public static final java.lang.String KEY_JS_REWARD_TOP_ACTIVITY_INSTANT = "js_reward_top_activity_instant";
    public static final java.lang.String KEY_JS_REWARD_TOP_ACTIVITY_MAX = "js_reward_top_activity_max";
    public static final java.lang.String KEY_JS_REWARD_TOP_ACTIVITY_ONGOING = "js_reward_top_activity_ongoing";
    public static final java.lang.String KEY_JS_REWARD_WIDGET_INTERACTION_INSTANT = "js_reward_widget_interaction_instant";
    public static final java.lang.String KEY_JS_REWARD_WIDGET_INTERACTION_MAX = "js_reward_widget_interaction_max";
    public static final java.lang.String KEY_JS_REWARD_WIDGET_INTERACTION_ONGOING = "js_reward_widget_interaction_ongoing";
    private final android.app.tare.IEconomyManager mService;
    private static final java.lang.String TAG = "TARE-" + android.app.tare.EconomyManager.class.getSimpleName();
    public static final long DEFAULT_AM_MIN_SATIATED_BALANCE_EXEMPTED_CAKES = arcToCake(500);
    public static final long DEFAULT_AM_MIN_SATIATED_BALANCE_HEADLESS_SYSTEM_APP_CAKES = arcToCake(256);
    public static final long DEFAULT_AM_MIN_SATIATED_BALANCE_OTHER_APP_CAKES = arcToCake(160);
    public static final long DEFAULT_AM_MAX_SATIATED_BALANCE_CAKES = arcToCake(960);
    public static final long DEFAULT_AM_INITIAL_CONSUMPTION_LIMIT_CAKES = arcToCake(2880);
    public static final long DEFAULT_AM_MIN_CONSUMPTION_LIMIT_CAKES = arcToCake(com.android.internal.logging.nano.MetricsProto.MetricsEvent.ACTION_HUSH_GESTURE);
    public static final long DEFAULT_AM_MAX_CONSUMPTION_LIMIT_CAKES = arcToCake(15000);
    public static final long DEFAULT_AM_REWARD_TOP_ACTIVITY_INSTANT_CAKES = arcToCake(0);
    public static final long DEFAULT_AM_REWARD_TOP_ACTIVITY_MAX_CAKES = arcToCake(500);
    public static final long DEFAULT_AM_REWARD_NOTIFICATION_SEEN_INSTANT_CAKES = arcToCake(3);
    public static final long DEFAULT_AM_REWARD_NOTIFICATION_SEEN_ONGOING_CAKES = arcToCake(0);
    public static final long DEFAULT_AM_REWARD_NOTIFICATION_SEEN_MAX_CAKES = arcToCake(60);
    public static final long DEFAULT_AM_REWARD_NOTIFICATION_SEEN_WITHIN_15_INSTANT_CAKES = arcToCake(5);
    public static final long DEFAULT_AM_REWARD_NOTIFICATION_SEEN_WITHIN_15_ONGOING_CAKES = arcToCake(0);
    public static final long DEFAULT_AM_REWARD_NOTIFICATION_SEEN_WITHIN_15_MAX_CAKES = arcToCake(500);
    public static final long DEFAULT_AM_REWARD_NOTIFICATION_INTERACTION_INSTANT_CAKES = arcToCake(5);
    public static final long DEFAULT_AM_REWARD_NOTIFICATION_INTERACTION_ONGOING_CAKES = arcToCake(0);
    public static final long DEFAULT_AM_REWARD_NOTIFICATION_INTERACTION_MAX_CAKES = arcToCake(500);
    public static final long DEFAULT_AM_REWARD_WIDGET_INTERACTION_INSTANT_CAKES = arcToCake(10);
    public static final long DEFAULT_AM_REWARD_WIDGET_INTERACTION_ONGOING_CAKES = arcToCake(0);
    public static final long DEFAULT_AM_REWARD_WIDGET_INTERACTION_MAX_CAKES = arcToCake(500);
    public static final long DEFAULT_AM_REWARD_OTHER_USER_INTERACTION_INSTANT_CAKES = arcToCake(10);
    public static final long DEFAULT_AM_REWARD_OTHER_USER_INTERACTION_ONGOING_CAKES = arcToCake(0);
    public static final long DEFAULT_AM_REWARD_OTHER_USER_INTERACTION_MAX_CAKES = arcToCake(500);
    public static final long DEFAULT_AM_ACTION_ALARM_ALLOW_WHILE_IDLE_EXACT_WAKEUP_CTP_CAKES = arcToCake(3);
    public static final long DEFAULT_AM_ACTION_ALARM_ALLOW_WHILE_IDLE_INEXACT_WAKEUP_CTP_CAKES = arcToCake(3);
    public static final long DEFAULT_AM_ACTION_ALARM_EXACT_WAKEUP_CTP_CAKES = arcToCake(3);
    public static final long DEFAULT_AM_ACTION_ALARM_INEXACT_WAKEUP_CTP_CAKES = arcToCake(3);
    public static final long DEFAULT_AM_ACTION_ALARM_ALLOW_WHILE_IDLE_EXACT_NONWAKEUP_CTP_CAKES = arcToCake(1);
    public static final long DEFAULT_AM_ACTION_ALARM_EXACT_NONWAKEUP_CTP_CAKES = arcToCake(1);
    public static final long DEFAULT_AM_ACTION_ALARM_ALLOW_WHILE_IDLE_INEXACT_NONWAKEUP_CTP_CAKES = arcToCake(1);
    public static final long DEFAULT_AM_ACTION_ALARM_INEXACT_NONWAKEUP_CTP_CAKES = arcToCake(1);
    public static final long DEFAULT_AM_ACTION_ALARM_ALARMCLOCK_CTP_CAKES = arcToCake(5);
    public static final long DEFAULT_AM_ACTION_ALARM_ALLOW_WHILE_IDLE_EXACT_WAKEUP_BASE_PRICE_CAKES = arcToCake(5);
    public static final long DEFAULT_AM_ACTION_ALARM_ALLOW_WHILE_IDLE_INEXACT_WAKEUP_BASE_PRICE_CAKES = arcToCake(4);
    public static final long DEFAULT_AM_ACTION_ALARM_EXACT_WAKEUP_BASE_PRICE_CAKES = arcToCake(4);
    public static final long DEFAULT_AM_ACTION_ALARM_INEXACT_WAKEUP_BASE_PRICE_CAKES = arcToCake(3);
    public static final long DEFAULT_AM_ACTION_ALARM_ALLOW_WHILE_IDLE_EXACT_NONWAKEUP_BASE_PRICE_CAKES = arcToCake(3);
    public static final long DEFAULT_AM_ACTION_ALARM_EXACT_NONWAKEUP_BASE_PRICE_CAKES = arcToCake(2);
    public static final long DEFAULT_AM_ACTION_ALARM_ALLOW_WHILE_IDLE_INEXACT_NONWAKEUP_BASE_PRICE_CAKES = arcToCake(2);
    public static final long DEFAULT_AM_ACTION_ALARM_INEXACT_NONWAKEUP_BASE_PRICE_CAKES = arcToCake(1);
    public static final long DEFAULT_AM_ACTION_ALARM_ALARMCLOCK_BASE_PRICE_CAKES = arcToCake(10);
    public static final long DEFAULT_JS_MIN_SATIATED_BALANCE_EXEMPTED_CAKES = arcToCake(15000);
    public static final long DEFAULT_JS_MIN_SATIATED_BALANCE_HEADLESS_SYSTEM_APP_CAKES = arcToCake(7500);
    public static final long DEFAULT_JS_MIN_SATIATED_BALANCE_OTHER_APP_CAKES = arcToCake(2000);
    public static final long DEFAULT_JS_MAX_SATIATED_BALANCE_CAKES = arcToCake(60000);
    public static final long DEFAULT_JS_INITIAL_CONSUMPTION_LIMIT_CAKES = arcToCake(29000);
    public static final long DEFAULT_JS_MIN_CONSUMPTION_LIMIT_CAKES = arcToCake(17000);
    public static final long DEFAULT_JS_MAX_CONSUMPTION_LIMIT_CAKES = arcToCake(250000);
    public static final long DEFAULT_JS_REWARD_APP_INSTALL_INSTANT_CAKES = arcToCake(408);
    public static final long DEFAULT_JS_REWARD_APP_INSTALL_ONGOING_CAKES = arcToCake(0);
    public static final long DEFAULT_JS_REWARD_APP_INSTALL_MAX_CAKES = arcToCake(4000);
    public static final long DEFAULT_JS_REWARD_TOP_ACTIVITY_INSTANT_CAKES = arcToCake(0);
    public static final long DEFAULT_JS_REWARD_TOP_ACTIVITY_MAX_CAKES = arcToCake(15000);
    public static final long DEFAULT_JS_REWARD_NOTIFICATION_SEEN_INSTANT_CAKES = arcToCake(1);
    public static final long DEFAULT_JS_REWARD_NOTIFICATION_SEEN_ONGOING_CAKES = arcToCake(0);
    public static final long DEFAULT_JS_REWARD_NOTIFICATION_SEEN_MAX_CAKES = arcToCake(10);
    public static final long DEFAULT_JS_REWARD_NOTIFICATION_INTERACTION_INSTANT_CAKES = arcToCake(5);
    public static final long DEFAULT_JS_REWARD_NOTIFICATION_INTERACTION_ONGOING_CAKES = arcToCake(0);
    public static final long DEFAULT_JS_REWARD_NOTIFICATION_INTERACTION_MAX_CAKES = arcToCake(5000);
    public static final long DEFAULT_JS_REWARD_WIDGET_INTERACTION_INSTANT_CAKES = arcToCake(10);
    public static final long DEFAULT_JS_REWARD_WIDGET_INTERACTION_ONGOING_CAKES = arcToCake(0);
    public static final long DEFAULT_JS_REWARD_WIDGET_INTERACTION_MAX_CAKES = arcToCake(5000);
    public static final long DEFAULT_JS_REWARD_OTHER_USER_INTERACTION_INSTANT_CAKES = arcToCake(10);
    public static final long DEFAULT_JS_REWARD_OTHER_USER_INTERACTION_ONGOING_CAKES = arcToCake(0);
    public static final long DEFAULT_JS_REWARD_OTHER_USER_INTERACTION_MAX_CAKES = arcToCake(5000);
    public static final long DEFAULT_JS_MIN_SATIATED_BALANCE_INCREMENT_APP_UPDATER_CAKES = DEFAULT_JS_REWARD_APP_INSTALL_INSTANT_CAKES / 14;
    public static final long DEFAULT_JS_ACTION_JOB_MAX_START_CTP_CAKES = arcToCake(3);
    public static final long DEFAULT_JS_ACTION_JOB_MAX_RUNNING_CTP_CAKES = arcToCake(2);
    public static final long DEFAULT_JS_ACTION_JOB_HIGH_START_CTP_CAKES = arcToCake(3);
    public static final long DEFAULT_JS_ACTION_JOB_HIGH_RUNNING_CTP_CAKES = arcToCake(2);
    public static final long DEFAULT_JS_ACTION_JOB_DEFAULT_START_CTP_CAKES = arcToCake(3);
    public static final long DEFAULT_JS_ACTION_JOB_DEFAULT_RUNNING_CTP_CAKES = arcToCake(2);
    public static final long DEFAULT_JS_ACTION_JOB_LOW_START_CTP_CAKES = arcToCake(3);
    public static final long DEFAULT_JS_ACTION_JOB_LOW_RUNNING_CTP_CAKES = arcToCake(2);
    public static final long DEFAULT_JS_ACTION_JOB_MIN_START_CTP_CAKES = arcToCake(3);
    public static final long DEFAULT_JS_ACTION_JOB_MIN_RUNNING_CTP_CAKES = arcToCake(2);
    public static final long DEFAULT_JS_ACTION_JOB_TIMEOUT_PENALTY_CTP_CAKES = arcToCake(30);
    public static final long DEFAULT_JS_ACTION_JOB_MAX_START_BASE_PRICE_CAKES = arcToCake(10);
    public static final long DEFAULT_JS_ACTION_JOB_MAX_RUNNING_BASE_PRICE_CAKES = arcToCake(5);
    public static final long DEFAULT_JS_ACTION_JOB_HIGH_START_BASE_PRICE_CAKES = arcToCake(8);
    public static final long DEFAULT_JS_ACTION_JOB_HIGH_RUNNING_BASE_PRICE_CAKES = arcToCake(4);
    public static final long DEFAULT_JS_ACTION_JOB_DEFAULT_START_BASE_PRICE_CAKES = arcToCake(6);
    public static final long DEFAULT_JS_ACTION_JOB_DEFAULT_RUNNING_BASE_PRICE_CAKES = arcToCake(3);
    public static final long DEFAULT_JS_ACTION_JOB_LOW_START_BASE_PRICE_CAKES = arcToCake(4);
    public static final long DEFAULT_JS_ACTION_JOB_LOW_RUNNING_BASE_PRICE_CAKES = arcToCake(2);
    public static final long DEFAULT_JS_ACTION_JOB_MIN_START_BASE_PRICE_CAKES = arcToCake(2);
    public static final long DEFAULT_JS_ACTION_JOB_MIN_RUNNING_BASE_PRICE_CAKES = arcToCake(1);
    public static final long DEFAULT_JS_ACTION_JOB_TIMEOUT_PENALTY_BASE_PRICE_CAKES = arcToCake(60);

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface EnabledMode {
    }

    public static long arcToCake(int i) {
        return i * 1000000000;
    }

    public static long parseCreditValue(java.lang.String str, long j) {
        java.lang.String substring;
        if (str != null) {
            java.lang.String trim = str.trim();
            if (!trim.isEmpty()) {
                long j2 = 1;
                if (trim.endsWith("c")) {
                    substring = trim.substring(0, trim.length() - 1);
                } else if (trim.endsWith("ck")) {
                    substring = trim.substring(0, trim.length() - 2);
                } else if (trim.endsWith("A")) {
                    substring = trim.substring(0, trim.length() - 1);
                    j2 = 1000000000;
                } else if (trim.endsWith("ARC")) {
                    substring = trim.substring(0, trim.length() - 3);
                    j2 = 1000000000;
                } else {
                    android.util.Log.e(TAG, "Couldn't determine units of credit value: " + str);
                    return j;
                }
                if (substring.endsWith(android.app.backup.FullBackup.KEY_VALUE_DATA_TOKEN)) {
                    substring = substring.substring(0, substring.length() - 1);
                    j2 *= 1000;
                } else if (substring.endsWith(android.hardware.gnss.GnssSignalType.CODE_TYPE_M)) {
                    substring = substring.substring(0, substring.length() - 1);
                    j2 *= 1000000;
                } else if (substring.endsWith("G")) {
                    substring = substring.substring(0, substring.length() - 1);
                    j2 *= 1000000000;
                }
                try {
                    return java.lang.Long.parseLong(substring) * j2;
                } catch (java.lang.NumberFormatException e) {
                    android.util.Log.e(TAG, "Malformed config string: " + str + " to " + substring, e);
                    return j;
                }
            }
        }
        return j;
    }

    public static java.lang.String enabledModeToString(int i) {
        switch (i) {
            case 0:
                return "ENABLED_MODE_OFF";
            case 1:
                return "ENABLED_MODE_ON";
            case 2:
                return "ENABLED_MODE_SHADOW";
            default:
                return "ENABLED_MODE_" + i;
        }
    }

    public EconomyManager(android.app.tare.IEconomyManager iEconomyManager) {
        this.mService = iEconomyManager;
    }

    public int getEnabledMode() {
        try {
            return this.mService.getEnabledMode();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
