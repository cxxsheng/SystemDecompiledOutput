package android.telephony;

/* loaded from: classes3.dex */
public class SubscriptionManager {
    public static final java.lang.String ACCESS_RULES = "access_rules";
    public static final java.lang.String ACCESS_RULES_FROM_CARRIER_CONFIGS = "access_rules_from_carrier_configs";
    public static final java.lang.String ACTION_DEFAULT_SMS_SUBSCRIPTION_CHANGED = "android.telephony.action.DEFAULT_SMS_SUBSCRIPTION_CHANGED";
    public static final java.lang.String ACTION_DEFAULT_SUBSCRIPTION_CHANGED = "android.telephony.action.DEFAULT_SUBSCRIPTION_CHANGED";
    public static final java.lang.String ACTION_MANAGE_SUBSCRIPTION_PLANS = "android.telephony.action.MANAGE_SUBSCRIPTION_PLANS";
    public static final java.lang.String ACTION_REFRESH_SUBSCRIPTION_PLANS = "android.telephony.action.REFRESH_SUBSCRIPTION_PLANS";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_SUBSCRIPTION_PLANS_CHANGED = "android.telephony.action.SUBSCRIPTION_PLANS_CHANGED";
    public static final java.lang.String ALLOWED_NETWORK_TYPES = "allowed_network_types_for_reasons";
    public static final java.lang.String CARD_ID = "card_id";
    public static final java.lang.String CARRIER_ID = "carrier_id";
    public static final java.lang.String CARRIER_NAME = "carrier_name";
    public static final java.lang.String CB_ALERT_REMINDER_INTERVAL = "alert_reminder_interval";
    public static final java.lang.String CB_ALERT_SOUND_DURATION = "alert_sound_duration";
    public static final java.lang.String CB_ALERT_SPEECH = "enable_alert_speech";
    public static final java.lang.String CB_ALERT_VIBRATE = "enable_alert_vibrate";
    public static final java.lang.String CB_AMBER_ALERT = "enable_cmas_amber_alerts";
    public static final java.lang.String CB_CHANNEL_50_ALERT = "enable_channel_50_alerts";
    public static final java.lang.String CB_CMAS_TEST_ALERT = "enable_cmas_test_alerts";
    public static final java.lang.String CB_EMERGENCY_ALERT = "enable_emergency_alerts";
    public static final java.lang.String CB_ETWS_TEST_ALERT = "enable_etws_test_alerts";
    public static final java.lang.String CB_EXTREME_THREAT_ALERT = "enable_cmas_extreme_threat_alerts";
    public static final java.lang.String CB_OPT_OUT_DIALOG = "show_cmas_opt_out_dialog";
    public static final java.lang.String CB_SEVERE_THREAT_ALERT = "enable_cmas_severe_threat_alerts";
    public static final java.lang.String CROSS_SIM_CALLING_ENABLED = "cross_sim_calling_enabled";
    public static final int D2D_SHARING_ALL = 3;
    public static final int D2D_SHARING_ALL_CONTACTS = 1;
    public static final int D2D_SHARING_DISABLED = 0;
    public static final int D2D_SHARING_SELECTED_CONTACTS = 2;
    public static final java.lang.String D2D_STATUS_SHARING = "d2d_sharing_status";
    public static final java.lang.String D2D_STATUS_SHARING_SELECTED_CONTACTS = "d2d_sharing_contacts";
    public static final java.lang.String DATA_ROAMING = "data_roaming";
    public static final int DATA_ROAMING_DISABLE = 0;
    public static final int DATA_ROAMING_ENABLE = 1;
    private static final boolean DBG = false;
    public static final int DEFAULT_NAME_RES = 17039374;
    public static final int DEFAULT_PHONE_INDEX = Integer.MAX_VALUE;
    public static final int DEFAULT_SIM_SLOT_INDEX = Integer.MAX_VALUE;
    public static final int DEFAULT_SUBSCRIPTION_ID = Integer.MAX_VALUE;
    public static final java.lang.String DISPLAY_NAME = "display_name";
    public static final java.lang.String EHPLMNS = "ehplmns";
    public static final java.lang.String ENABLED_MOBILE_DATA_POLICIES = "enabled_mobile_data_policies";
    public static final java.lang.String ENHANCED_4G_MODE_ENABLED = "volte_vt_enabled";
    public static final java.lang.String EXTRA_SLOT_INDEX = "android.telephony.extra.SLOT_INDEX";
    public static final java.lang.String EXTRA_SUBSCRIPTION_INDEX = "android.telephony.extra.SUBSCRIPTION_INDEX";
    public static final java.lang.String GET_SIM_SPECIFIC_SETTINGS_METHOD_NAME = "getSimSpecificSettings";
    public static final java.lang.String GROUP_OWNER = "group_owner";
    public static final java.lang.String GROUP_UUID = "group_uuid";
    public static final java.lang.String HPLMNS = "hplmns";
    public static final java.lang.String HUE = "color";
    public static final java.lang.String ICC_ID = "icc_id";
    public static final java.lang.String IMSI = "imsi";
    public static final java.lang.String IMS_RCS_UCE_ENABLED = "ims_rcs_uce_enabled";
    public static final int INVALID_PHONE_INDEX = -1;
    public static final int INVALID_SIM_SLOT_INDEX = -1;
    public static final int INVALID_SUBSCRIPTION_ID = -1;
    public static final java.lang.String ISO_COUNTRY_CODE = "iso_country_code";
    public static final java.lang.String IS_EMBEDDED = "is_embedded";
    public static final java.lang.String IS_NTN = "is_ntn";
    public static final java.lang.String IS_OPPORTUNISTIC = "is_opportunistic";
    public static final java.lang.String IS_REMOVABLE = "is_removable";
    public static final java.lang.String KEY_SIM_SPECIFIC_SETTINGS_DATA = "KEY_SIM_SPECIFIC_SETTINGS_DATA";
    private static final java.lang.String LOG_TAG = "SubscriptionManager";
    private static final int MAX_CACHE_SIZE = 4;
    private static final int MAX_RESOURCE_CACHE_ENTRY_COUNT = 1000;
    public static final int MAX_SUBSCRIPTION_ID_VALUE = 2147483646;
    public static final java.lang.String MCC = "mcc";
    public static final java.lang.String MCC_STRING = "mcc_string";
    public static final int MIN_SUBSCRIPTION_ID_VALUE = 0;
    public static final java.lang.String MNC = "mnc";
    public static final java.lang.String MNC_STRING = "mnc_string";
    public static final java.lang.String NAME_SOURCE = "name_source";
    public static final int NAME_SOURCE_CARRIER = 3;
    public static final int NAME_SOURCE_CARRIER_ID = 0;
    public static final int NAME_SOURCE_SIM_PNN = 4;
    public static final int NAME_SOURCE_SIM_SPN = 1;
    public static final int NAME_SOURCE_UNKNOWN = -1;
    public static final int NAME_SOURCE_USER_INPUT = 2;
    public static final java.lang.String NR_ADVANCED_CALLING_ENABLED = "nr_advanced_calling_enabled";
    public static final java.lang.String NUMBER = "number";
    public static final int PHONE_NUMBER_SOURCE_CARRIER = 2;
    public static final int PHONE_NUMBER_SOURCE_IMS = 3;
    public static final int PHONE_NUMBER_SOURCE_UICC = 1;
    public static final int PLACEHOLDER_SUBSCRIPTION_ID_BASE = -2;
    public static final java.lang.String PORT_INDEX = "port_index";
    public static final java.lang.String PROFILE_CLASS = "profile_class";

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final int PROFILE_CLASS_DEFAULT = -1;

    @android.annotation.SystemApi
    public static final int PROFILE_CLASS_OPERATIONAL = 2;

    @android.annotation.SystemApi
    public static final int PROFILE_CLASS_PROVISIONING = 1;

    @android.annotation.SystemApi
    public static final int PROFILE_CLASS_TESTING = 0;

    @android.annotation.SystemApi
    public static final int PROFILE_CLASS_UNSET = -1;
    public static final java.lang.String RESTORE_SIM_SPECIFIC_SETTINGS_DATABASE_UPDATED = "restoreSimSpecificSettingsDatabaseUpdated";
    public static final java.lang.String RESTORE_SIM_SPECIFIC_SETTINGS_METHOD_NAME = "restoreSimSpecificSettings";
    public static final java.lang.String SATELLITE_ATTACH_ENABLED_FOR_CARRIER = "satellite_attach_enabled_for_carrier";
    public static final java.lang.String SATELLITE_ENABLED = "satellite_enabled";
    public static final java.lang.String SATELLITE_ENTITLEMENT_PLMNS = "satellite_entitlement_plmns";
    public static final java.lang.String SATELLITE_ENTITLEMENT_STATUS = "satellite_entitlement_status";
    public static final java.lang.String SERVICE_CAPABILITIES = "service_capabilities";
    public static final int SERVICE_CAPABILITY_DATA = 3;
    public static final int SERVICE_CAPABILITY_MAX = 3;
    public static final int SERVICE_CAPABILITY_SMS = 2;
    public static final int SERVICE_CAPABILITY_VOICE = 1;
    public static final int SIM_NOT_INSERTED = -1;
    public static final java.lang.String SIM_SLOT_INDEX = "sim_id";
    public static final int SLOT_INDEX_FOR_REMOTE_SIM_SUB = -1;
    public static final java.lang.String SUBSCRIPTION_TYPE = "subscription_type";
    public static final int SUBSCRIPTION_TYPE_LOCAL_SIM = 0;
    public static final int SUBSCRIPTION_TYPE_REMOTE_SIM = 1;
    public static final java.lang.String SUB_DEFAULT_CHANGED_ACTION = "android.intent.action.SUB_DEFAULT_CHANGED";
    public static final java.lang.String TP_MESSAGE_REF = "tp_message_ref";
    public static final java.lang.String TRANSFER_STATUS = "transfer_status";

    @android.annotation.SystemApi
    public static final int TRANSFER_STATUS_CONVERTED = 2;

    @android.annotation.SystemApi
    public static final int TRANSFER_STATUS_NONE = 0;

    @android.annotation.SystemApi
    public static final int TRANSFER_STATUS_TRANSFERRED_OUT = 1;
    public static final java.lang.String UICC_APPLICATIONS_ENABLED = "uicc_applications_enabled";
    public static final java.lang.String UNIQUE_KEY_SUBSCRIPTION_ID = "_id";
    public static final java.lang.String USAGE_SETTING = "usage_setting";
    public static final int USAGE_SETTING_DATA_CENTRIC = 2;
    public static final int USAGE_SETTING_DEFAULT = 0;
    public static final int USAGE_SETTING_UNKNOWN = -1;
    public static final int USAGE_SETTING_VOICE_CENTRIC = 1;
    public static final java.lang.String USER_HANDLE = "user_handle";
    private static final boolean VDBG = false;
    public static final java.lang.String VOIMS_OPT_IN_STATUS = "voims_opt_in_status";
    public static final java.lang.String VT_IMS_ENABLED = "vt_ims_enabled";
    public static final java.lang.String WFC_IMS_ENABLED = "wfc_ims_enabled";
    public static final java.lang.String WFC_IMS_MODE = "wfc_ims_mode";
    public static final java.lang.String WFC_IMS_ROAMING_ENABLED = "wfc_ims_roaming_enabled";
    public static final java.lang.String WFC_IMS_ROAMING_MODE = "wfc_ims_roaming_mode";
    private final android.content.Context mContext;
    private final boolean mIsForAllUserProfiles;
    public static final android.net.Uri CONTENT_URI = android.provider.Telephony.SimInfo.CONTENT_URI;
    private static final java.lang.String CACHE_KEY_SUBSCRIPTION_MANAGER_SERVICE_PROPERTY = "cache_key.telephony.subscription_manager_service";
    private static android.telephony.SubscriptionManager.IntegerPropertyInvalidatedCache<java.lang.Integer> sGetDefaultSubIdCacheAsUser = new android.telephony.SubscriptionManager.IntegerPropertyInvalidatedCache<>(new com.android.internal.util.FunctionalUtils.ThrowingBiFunction() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda9
        @Override // com.android.internal.util.FunctionalUtils.ThrowingBiFunction
        public final java.lang.Object applyOrThrow(java.lang.Object obj, java.lang.Object obj2) {
            return java.lang.Integer.valueOf(((com.android.internal.telephony.ISub) obj).getDefaultSubIdAsUser(((java.lang.Integer) obj2).intValue()));
        }
    }, CACHE_KEY_SUBSCRIPTION_MANAGER_SERVICE_PROPERTY, -1);
    private static android.telephony.SubscriptionManager.VoidPropertyInvalidatedCache<java.lang.Integer> sGetDefaultDataSubIdCache = new android.telephony.SubscriptionManager.VoidPropertyInvalidatedCache<>(new com.android.internal.util.FunctionalUtils.ThrowingFunction() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda10
        @Override // com.android.internal.util.FunctionalUtils.ThrowingFunction
        public final java.lang.Object applyOrThrow(java.lang.Object obj) {
            return java.lang.Integer.valueOf(((com.android.internal.telephony.ISub) obj).getDefaultDataSubId());
        }
    }, CACHE_KEY_SUBSCRIPTION_MANAGER_SERVICE_PROPERTY, -1);
    private static android.telephony.SubscriptionManager.IntegerPropertyInvalidatedCache<java.lang.Integer> sGetDefaultSmsSubIdCacheAsUser = new android.telephony.SubscriptionManager.IntegerPropertyInvalidatedCache<>(new com.android.internal.util.FunctionalUtils.ThrowingBiFunction() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda11
        @Override // com.android.internal.util.FunctionalUtils.ThrowingBiFunction
        public final java.lang.Object applyOrThrow(java.lang.Object obj, java.lang.Object obj2) {
            return java.lang.Integer.valueOf(((com.android.internal.telephony.ISub) obj).getDefaultSmsSubIdAsUser(((java.lang.Integer) obj2).intValue()));
        }
    }, CACHE_KEY_SUBSCRIPTION_MANAGER_SERVICE_PROPERTY, -1);
    private static android.telephony.SubscriptionManager.VoidPropertyInvalidatedCache<java.lang.Integer> sGetActiveDataSubscriptionIdCache = new android.telephony.SubscriptionManager.VoidPropertyInvalidatedCache<>(new com.android.internal.util.FunctionalUtils.ThrowingFunction() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda12
        @Override // com.android.internal.util.FunctionalUtils.ThrowingFunction
        public final java.lang.Object applyOrThrow(java.lang.Object obj) {
            return java.lang.Integer.valueOf(((com.android.internal.telephony.ISub) obj).getActiveDataSubscriptionId());
        }
    }, CACHE_KEY_SUBSCRIPTION_MANAGER_SERVICE_PROPERTY, -1);
    private static android.telephony.SubscriptionManager.IntegerPropertyInvalidatedCache<java.lang.Integer> sGetSlotIndexCache = new android.telephony.SubscriptionManager.IntegerPropertyInvalidatedCache<>(new com.android.internal.util.FunctionalUtils.ThrowingBiFunction() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda13
        @Override // com.android.internal.util.FunctionalUtils.ThrowingBiFunction
        public final java.lang.Object applyOrThrow(java.lang.Object obj, java.lang.Object obj2) {
            return java.lang.Integer.valueOf(((com.android.internal.telephony.ISub) obj).getSlotIndex(((java.lang.Integer) obj2).intValue()));
        }
    }, CACHE_KEY_SUBSCRIPTION_MANAGER_SERVICE_PROPERTY, -1);
    private static android.telephony.SubscriptionManager.IntegerPropertyInvalidatedCache<java.lang.Integer> sGetSubIdCache = new android.telephony.SubscriptionManager.IntegerPropertyInvalidatedCache<>(new com.android.internal.util.FunctionalUtils.ThrowingBiFunction() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda14
        @Override // com.android.internal.util.FunctionalUtils.ThrowingBiFunction
        public final java.lang.Object applyOrThrow(java.lang.Object obj, java.lang.Object obj2) {
            return java.lang.Integer.valueOf(((com.android.internal.telephony.ISub) obj).getSubId(((java.lang.Integer) obj2).intValue()));
        }
    }, CACHE_KEY_SUBSCRIPTION_MANAGER_SERVICE_PROPERTY, -1);
    private static android.telephony.SubscriptionManager.IntegerPropertyInvalidatedCache<java.lang.Integer> sGetPhoneIdCache = new android.telephony.SubscriptionManager.IntegerPropertyInvalidatedCache<>(new com.android.internal.util.FunctionalUtils.ThrowingBiFunction() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda15
        @Override // com.android.internal.util.FunctionalUtils.ThrowingBiFunction
        public final java.lang.Object applyOrThrow(java.lang.Object obj, java.lang.Object obj2) {
            return java.lang.Integer.valueOf(((com.android.internal.telephony.ISub) obj).getPhoneId(((java.lang.Integer) obj2).intValue()));
        }
    }, CACHE_KEY_SUBSCRIPTION_MANAGER_SERVICE_PROPERTY, -1);

    @android.annotation.SystemApi
    public static final android.net.Uri WFC_ENABLED_CONTENT_URI = android.net.Uri.withAppendedPath(CONTENT_URI, "wfc");

    @android.annotation.SystemApi
    public static final android.net.Uri ADVANCED_CALLING_ENABLED_CONTENT_URI = android.net.Uri.withAppendedPath(CONTENT_URI, "advanced_calling");

    @android.annotation.SystemApi
    public static final android.net.Uri WFC_MODE_CONTENT_URI = android.net.Uri.withAppendedPath(CONTENT_URI, "wfc_mode");

    @android.annotation.SystemApi
    public static final android.net.Uri WFC_ROAMING_MODE_CONTENT_URI = android.net.Uri.withAppendedPath(CONTENT_URI, "wfc_roaming_mode");

    @android.annotation.SystemApi
    public static final android.net.Uri VT_ENABLED_CONTENT_URI = android.net.Uri.withAppendedPath(CONTENT_URI, "vt_enabled");

    @android.annotation.SystemApi
    public static final android.net.Uri WFC_ROAMING_ENABLED_CONTENT_URI = android.net.Uri.withAppendedPath(CONTENT_URI, "wfc_roaming_enabled");
    public static final android.net.Uri SIM_INFO_BACKUP_AND_RESTORE_CONTENT_URI = android.net.Uri.withAppendedPath(CONTENT_URI, "backup_and_restore");
    public static final android.net.Uri SIM_INFO_SUW_RESTORE_CONTENT_URI = android.net.Uri.withAppendedPath(SIM_INFO_BACKUP_AND_RESTORE_CONTENT_URI, "suw_restore");

    @android.annotation.SystemApi
    public static final android.net.Uri CROSS_SIM_ENABLED_CONTENT_URI = android.net.Uri.withAppendedPath(CONTENT_URI, "cross_sim_calling_enabled");
    public static final int SERVICE_CAPABILITY_VOICE_BITMASK = serviceCapabilityToBitmask(1);
    public static final int SERVICE_CAPABILITY_SMS_BITMASK = serviceCapabilityToBitmask(2);
    public static final int SERVICE_CAPABILITY_DATA_BITMASK = serviceCapabilityToBitmask(3);
    private static final android.util.LruCache<android.util.Pair<java.lang.String, android.content.res.Configuration>, android.content.res.Resources> sResourcesCache = new android.util.LruCache<>(1000);

    /* JADX INFO: Access modifiers changed from: private */
    interface CallISubMethodHelper {
        int callMethod(com.android.internal.telephony.ISub iSub) throws android.os.RemoteException;
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DataRoamingMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DeviceToDeviceStatusSharingPreference {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PhoneNumberSource {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ProfileClass {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ServiceCapability {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SimDisplayNameSource {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SubscriptionType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface TransferStatus {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface UsageSetting {
    }

    private static class VoidPropertyInvalidatedCache<T> extends android.app.PropertyInvalidatedCache<java.lang.Void, T> {
        private final java.lang.String mCacheKeyProperty;
        private final T mDefaultValue;
        private final com.android.internal.util.FunctionalUtils.ThrowingFunction<com.android.internal.telephony.ISub, T> mInterfaceMethod;

        VoidPropertyInvalidatedCache(com.android.internal.util.FunctionalUtils.ThrowingFunction<com.android.internal.telephony.ISub, T> throwingFunction, java.lang.String str, T t) {
            super(4, str);
            this.mInterfaceMethod = throwingFunction;
            this.mCacheKeyProperty = str;
            this.mDefaultValue = t;
        }

        @Override // android.app.PropertyInvalidatedCache
        public T recompute(java.lang.Void r2) {
            try {
                return this.mInterfaceMethod.applyOrThrow(android.telephony.TelephonyManager.getSubscriptionService());
            } catch (java.lang.Exception e) {
                throw new java.lang.RuntimeException(e);
            }
        }

        @Override // android.app.PropertyInvalidatedCache
        public T query(java.lang.Void r3) {
            T t = this.mDefaultValue;
            try {
                if (android.telephony.TelephonyManager.getSubscriptionService() != null) {
                    return (T) super.query((android.telephony.SubscriptionManager.VoidPropertyInvalidatedCache<T>) r3);
                }
                return t;
            } catch (java.lang.Exception e) {
                com.android.telephony.Rlog.w(android.telephony.SubscriptionManager.LOG_TAG, "Failed to recompute cache key for " + this.mCacheKeyProperty);
                return t;
            }
        }
    }

    private static class IntegerPropertyInvalidatedCache<T> extends android.app.PropertyInvalidatedCache<java.lang.Integer, T> {
        private final java.lang.String mCacheKeyProperty;
        private final T mDefaultValue;
        private final com.android.internal.util.FunctionalUtils.ThrowingBiFunction<com.android.internal.telephony.ISub, java.lang.Integer, T> mInterfaceMethod;

        IntegerPropertyInvalidatedCache(com.android.internal.util.FunctionalUtils.ThrowingBiFunction<com.android.internal.telephony.ISub, java.lang.Integer, T> throwingBiFunction, java.lang.String str, T t) {
            super(4, str);
            this.mInterfaceMethod = throwingBiFunction;
            this.mCacheKeyProperty = str;
            this.mDefaultValue = t;
        }

        @Override // android.app.PropertyInvalidatedCache
        public T recompute(java.lang.Integer num) {
            try {
                return this.mInterfaceMethod.applyOrThrow(android.telephony.TelephonyManager.getSubscriptionService(), num);
            } catch (java.lang.Exception e) {
                throw new java.lang.RuntimeException(e);
            }
        }

        @Override // android.app.PropertyInvalidatedCache
        public T query(java.lang.Integer num) {
            T t = this.mDefaultValue;
            try {
                if (android.telephony.TelephonyManager.getSubscriptionService() != null) {
                    return (T) super.query((android.telephony.SubscriptionManager.IntegerPropertyInvalidatedCache<T>) num);
                }
                return t;
            } catch (java.lang.Exception e) {
                com.android.telephony.Rlog.w(android.telephony.SubscriptionManager.LOG_TAG, "Failed to recompute cache key for " + this.mCacheKeyProperty);
                return t;
            }
        }
    }

    public static android.net.Uri getUriForSubscriptionId(int i) {
        return android.net.Uri.withAppendedPath(CONTENT_URI, java.lang.String.valueOf(i));
    }

    public static class OnSubscriptionsChangedListener {
        private static final long LAZY_INITIALIZE_SUBSCRIPTIONS_CHANGED_HANDLER = 278814050;
        private final android.os.Looper mCreatorLooper;

        public android.os.Looper getCreatorLooper() {
            return this.mCreatorLooper;
        }

        public OnSubscriptionsChangedListener() {
            this.mCreatorLooper = android.os.Looper.myLooper();
            if (this.mCreatorLooper == null && !android.compat.Compatibility.isChangeEnabled(LAZY_INITIALIZE_SUBSCRIPTIONS_CHANGED_HANDLER)) {
                throw new java.lang.RuntimeException("Can't create handler inside thread " + java.lang.Thread.currentThread() + " that has not called Looper.prepare()");
            }
        }

        public OnSubscriptionsChangedListener(android.os.Looper looper) {
            java.util.Objects.requireNonNull(looper);
            this.mCreatorLooper = looper;
        }

        public void onSubscriptionsChanged() {
        }

        public void onAddListenerFailed() {
            com.android.telephony.Rlog.w(android.telephony.SubscriptionManager.LOG_TAG, "onAddListenerFailed not overridden");
        }

        private void log(java.lang.String str) {
            com.android.telephony.Rlog.d(android.telephony.SubscriptionManager.LOG_TAG, str);
        }
    }

    public SubscriptionManager(android.content.Context context) {
        this(context, false);
    }

    private SubscriptionManager(android.content.Context context, boolean z) {
        this.mIsForAllUserProfiles = z;
        this.mContext = context;
    }

    private android.net.NetworkPolicyManager getNetworkPolicyManager() {
        return (android.net.NetworkPolicyManager) this.mContext.getSystemService(android.content.Context.NETWORK_POLICY_SERVICE);
    }

    @java.lang.Deprecated
    public static android.telephony.SubscriptionManager from(android.content.Context context) {
        return (android.telephony.SubscriptionManager) context.getSystemService(android.content.Context.TELEPHONY_SUBSCRIPTION_SERVICE);
    }

    @java.lang.Deprecated
    public void addOnSubscriptionsChangedListener(android.telephony.SubscriptionManager.OnSubscriptionsChangedListener onSubscriptionsChangedListener) {
        if (onSubscriptionsChangedListener == null) {
            return;
        }
        android.os.Looper creatorLooper = onSubscriptionsChangedListener.getCreatorLooper();
        if (creatorLooper == null) {
            throw new java.lang.RuntimeException("Can't create handler inside thread " + java.lang.Thread.currentThread() + " that has not called Looper.prepare()");
        }
        addOnSubscriptionsChangedListener(new com.android.internal.telephony.util.HandlerExecutor(new android.os.Handler(creatorLooper)), onSubscriptionsChangedListener);
    }

    public void addOnSubscriptionsChangedListener(java.util.concurrent.Executor executor, final android.telephony.SubscriptionManager.OnSubscriptionsChangedListener onSubscriptionsChangedListener) {
        java.lang.String opPackageName = this.mContext != null ? this.mContext.getOpPackageName() : "<unknown>";
        android.telephony.TelephonyRegistryManager telephonyRegistryManager = (android.telephony.TelephonyRegistryManager) this.mContext.getSystemService(android.content.Context.TELEPHONY_REGISTRY_SERVICE);
        if (telephonyRegistryManager != null) {
            telephonyRegistryManager.addOnSubscriptionsChangedListener(onSubscriptionsChangedListener, executor);
        } else {
            loge("addOnSubscriptionsChangedListener: pkgname=" + opPackageName + " failed to be added  due to TELEPHONY_REGISTRY_SERVICE being unavailable.");
            executor.execute(new java.lang.Runnable() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.SubscriptionManager.OnSubscriptionsChangedListener.this.onAddListenerFailed();
                }
            });
        }
    }

    public void removeOnSubscriptionsChangedListener(android.telephony.SubscriptionManager.OnSubscriptionsChangedListener onSubscriptionsChangedListener) {
        if (onSubscriptionsChangedListener == null) {
            return;
        }
        if (this.mContext != null) {
            this.mContext.getOpPackageName();
        }
        android.telephony.TelephonyRegistryManager telephonyRegistryManager = (android.telephony.TelephonyRegistryManager) this.mContext.getSystemService(android.content.Context.TELEPHONY_REGISTRY_SERVICE);
        if (telephonyRegistryManager != null) {
            telephonyRegistryManager.removeOnSubscriptionsChangedListener(onSubscriptionsChangedListener);
        }
    }

    public static class OnOpportunisticSubscriptionsChangedListener {
        public void onOpportunisticSubscriptionsChanged() {
        }

        private void log(java.lang.String str) {
            com.android.telephony.Rlog.d(android.telephony.SubscriptionManager.LOG_TAG, str);
        }
    }

    public void addOnOpportunisticSubscriptionsChangedListener(java.util.concurrent.Executor executor, android.telephony.SubscriptionManager.OnOpportunisticSubscriptionsChangedListener onOpportunisticSubscriptionsChangedListener) {
        if (executor == null || onOpportunisticSubscriptionsChangedListener == null) {
            return;
        }
        if (this.mContext != null) {
            this.mContext.getOpPackageName();
        }
        android.telephony.TelephonyRegistryManager telephonyRegistryManager = (android.telephony.TelephonyRegistryManager) this.mContext.getSystemService(android.content.Context.TELEPHONY_REGISTRY_SERVICE);
        if (telephonyRegistryManager != null) {
            telephonyRegistryManager.addOnOpportunisticSubscriptionsChangedListener(onOpportunisticSubscriptionsChangedListener, executor);
        }
    }

    public void removeOnOpportunisticSubscriptionsChangedListener(android.telephony.SubscriptionManager.OnOpportunisticSubscriptionsChangedListener onOpportunisticSubscriptionsChangedListener) {
        com.android.internal.util.Preconditions.checkNotNull(onOpportunisticSubscriptionsChangedListener, "listener cannot be null");
        if (this.mContext != null) {
            this.mContext.getOpPackageName();
        }
        android.telephony.TelephonyRegistryManager telephonyRegistryManager = (android.telephony.TelephonyRegistryManager) this.mContext.getSystemService(android.content.Context.TELEPHONY_REGISTRY_SERVICE);
        if (telephonyRegistryManager != null) {
            telephonyRegistryManager.removeOnOpportunisticSubscriptionsChangedListener(onOpportunisticSubscriptionsChangedListener);
        }
    }

    public android.telephony.SubscriptionInfo getActiveSubscriptionInfo(int i) {
        if (!isValidSubscriptionId(i)) {
            return null;
        }
        try {
            com.android.internal.telephony.ISub subscriptionService = android.telephony.TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                return subscriptionService.getActiveSubscriptionInfo(i, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
            }
            return null;
        } catch (android.os.RemoteException e) {
            return null;
        }
    }

    @android.annotation.SystemApi
    public android.telephony.SubscriptionInfo getActiveSubscriptionInfoForIcc(java.lang.String str) {
        if (str == null) {
            logd("[getActiveSubscriptionInfoForIccIndex]- null iccid");
            return null;
        }
        try {
            com.android.internal.telephony.ISub subscriptionService = android.telephony.TelephonyManager.getSubscriptionService();
            if (subscriptionService == null) {
                return null;
            }
            return subscriptionService.getActiveSubscriptionInfoForIccId(str, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
        } catch (android.os.RemoteException e) {
            return null;
        }
    }

    public android.telephony.SubscriptionInfo getActiveSubscriptionInfoForSimSlotIndex(int i) {
        if (!isValidSlotIndex(i)) {
            logd("[getActiveSubscriptionInfoForSimSlotIndex]- invalid slotIndex");
            return null;
        }
        try {
            com.android.internal.telephony.ISub subscriptionService = android.telephony.TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                return subscriptionService.getActiveSubscriptionInfoForSimSlotIndex(i, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
            }
            return null;
        } catch (android.os.RemoteException e) {
            return null;
        }
    }

    public java.util.List<android.telephony.SubscriptionInfo> getAllSubscriptionInfoList() {
        java.util.List<android.telephony.SubscriptionInfo> list = null;
        try {
            com.android.internal.telephony.ISub subscriptionService = android.telephony.TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                list = subscriptionService.getAllSubInfoList(this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
            }
        } catch (android.os.RemoteException e) {
        }
        if (list == null) {
            return java.util.Collections.emptyList();
        }
        return list;
    }

    public java.util.List<android.telephony.SubscriptionInfo> getActiveSubscriptionInfoList() {
        java.util.List<android.telephony.SubscriptionInfo> list = null;
        try {
            com.android.internal.telephony.ISub subscriptionService = android.telephony.TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                list = subscriptionService.getActiveSubscriptionInfoList(this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), this.mIsForAllUserProfiles);
            }
        } catch (android.os.RemoteException e) {
        }
        if (list != null) {
            return (java.util.List) list.stream().filter(new java.util.function.Predicate() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda6
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$getActiveSubscriptionInfoList$1;
                    lambda$getActiveSubscriptionInfoList$1 = android.telephony.SubscriptionManager.this.lambda$getActiveSubscriptionInfoList$1((android.telephony.SubscriptionInfo) obj);
                    return lambda$getActiveSubscriptionInfoList$1;
                }
            }).collect(java.util.stream.Collectors.toList());
        }
        return java.util.Collections.emptyList();
    }

    public java.util.List<android.telephony.SubscriptionInfo> getCompleteActiveSubscriptionInfoList() {
        return getActiveSubscriptionInfoList(false);
    }

    public android.telephony.SubscriptionManager createForAllUserProfiles() {
        return new android.telephony.SubscriptionManager(this.mContext, true);
    }

    public java.util.List<android.telephony.SubscriptionInfo> getActiveSubscriptionInfoList(boolean z) {
        java.util.List<android.telephony.SubscriptionInfo> list = null;
        try {
            com.android.internal.telephony.ISub subscriptionService = android.telephony.TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                list = subscriptionService.getActiveSubscriptionInfoList(this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), true);
            }
        } catch (android.os.RemoteException e) {
        }
        if (list == null || list.isEmpty()) {
            return java.util.Collections.emptyList();
        }
        if (z) {
            return (java.util.List) list.stream().filter(new java.util.function.Predicate() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda17
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$getActiveSubscriptionInfoList$2;
                    lambda$getActiveSubscriptionInfoList$2 = android.telephony.SubscriptionManager.this.lambda$getActiveSubscriptionInfoList$2((android.telephony.SubscriptionInfo) obj);
                    return lambda$getActiveSubscriptionInfoList$2;
                }
            }).collect(java.util.stream.Collectors.toList());
        }
        return list;
    }

    @android.annotation.SystemApi
    public java.util.List<android.telephony.SubscriptionInfo> getAvailableSubscriptionInfoList() {
        java.util.List<android.telephony.SubscriptionInfo> list = null;
        try {
            com.android.internal.telephony.ISub subscriptionService = android.telephony.TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                list = subscriptionService.getAvailableSubscriptionInfoList(this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
            }
        } catch (android.os.RemoteException e) {
        }
        return list == null ? java.util.Collections.emptyList() : list;
    }

    public java.util.List<android.telephony.SubscriptionInfo> getAccessibleSubscriptionInfoList() {
        java.util.List<android.telephony.SubscriptionInfo> list = null;
        try {
            com.android.internal.telephony.ISub subscriptionService = android.telephony.TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                list = subscriptionService.getAccessibleSubscriptionInfoList(this.mContext.getOpPackageName());
            }
        } catch (android.os.RemoteException e) {
        }
        return list == null ? java.util.Collections.emptyList() : list;
    }

    @android.annotation.SystemApi
    public void requestEmbeddedSubscriptionInfoListRefresh() {
        int cardIdForDefaultEuicc = android.telephony.TelephonyManager.from(this.mContext).getCardIdForDefaultEuicc();
        try {
            com.android.internal.telephony.ISub subscriptionService = android.telephony.TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                subscriptionService.requestEmbeddedSubscriptionInfoListRefresh(cardIdForDefaultEuicc);
            }
        } catch (android.os.RemoteException e) {
            logd("requestEmbeddedSubscriptionInfoListFresh for card = " + cardIdForDefaultEuicc + " failed.");
        }
    }

    @android.annotation.SystemApi
    public void requestEmbeddedSubscriptionInfoListRefresh(int i) {
        try {
            com.android.internal.telephony.ISub subscriptionService = android.telephony.TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                subscriptionService.requestEmbeddedSubscriptionInfoListRefresh(i);
            }
        } catch (android.os.RemoteException e) {
            logd("requestEmbeddedSubscriptionInfoListFresh for card = " + i + " failed.");
        }
    }

    public int getActiveSubscriptionInfoCount() {
        try {
            com.android.internal.telephony.ISub subscriptionService = android.telephony.TelephonyManager.getSubscriptionService();
            if (subscriptionService == null) {
                return 0;
            }
            return subscriptionService.getActiveSubInfoCount(this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), this.mIsForAllUserProfiles);
        } catch (android.os.RemoteException e) {
            return 0;
        }
    }

    public int getActiveSubscriptionInfoCountMax() {
        try {
            com.android.internal.telephony.ISub subscriptionService = android.telephony.TelephonyManager.getSubscriptionService();
            if (subscriptionService == null) {
                return 0;
            }
            return subscriptionService.getActiveSubInfoCountMax();
        } catch (android.os.RemoteException e) {
            return 0;
        }
    }

    public android.net.Uri addSubscriptionInfoRecord(java.lang.String str, int i) {
        if (str == null) {
            logd("[addSubscriptionInfoRecord]- null iccId");
        }
        if (!isValidSlotIndex(i)) {
            logd("[addSubscriptionInfoRecord]- invalid slotIndex");
        }
        addSubscriptionInfoRecord(str, null, i, 0);
        return null;
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public void addSubscriptionInfoRecord(java.lang.String str, java.lang.String str2, int i, int i2) {
        if (str == null) {
            android.util.Log.e(LOG_TAG, "[addSubscriptionInfoRecord]- uniqueId is null");
            return;
        }
        try {
            com.android.internal.telephony.ISub subscriptionService = android.telephony.TelephonyManager.getSubscriptionService();
            if (subscriptionService == null) {
                android.util.Log.e(LOG_TAG, "[addSubscriptionInfoRecord]- ISub service is null");
                return;
            }
            int addSubInfo = subscriptionService.addSubInfo(str, str2, i, i2);
            if (addSubInfo < 0) {
                android.util.Log.e(LOG_TAG, "Adding of subscription didn't succeed: error = " + addSubInfo);
            } else {
                logd("successfully added new subscription");
            }
        } catch (android.os.RemoteException e) {
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public void removeSubscriptionInfoRecord(java.lang.String str, int i) {
        if (str == null) {
            android.util.Log.e(LOG_TAG, "[addSubscriptionInfoRecord]- uniqueId is null");
            return;
        }
        try {
            com.android.internal.telephony.ISub subscriptionService = android.telephony.TelephonyManager.getSubscriptionService();
            if (subscriptionService == null) {
                android.util.Log.e(LOG_TAG, "[removeSubscriptionInfoRecord]- ISub service is null");
            } else if (!subscriptionService.removeSubInfo(str, i)) {
                android.util.Log.e(LOG_TAG, "Removal of subscription didn't succeed");
            } else {
                logd("successfully removed subscription");
            }
        } catch (android.os.RemoteException e) {
        }
    }

    public int setIconTint(final int i, final int i2) {
        return setSubscriptionPropertyHelper(i2, "setIconTint", new android.telephony.SubscriptionManager.CallISubMethodHelper() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda4
            @Override // android.telephony.SubscriptionManager.CallISubMethodHelper
            public final int callMethod(com.android.internal.telephony.ISub iSub) {
                int iconTint;
                iconTint = iSub.setIconTint(i2, i);
                return iconTint;
            }
        });
    }

    public int setDisplayName(final java.lang.String str, final int i, final int i2) {
        return setSubscriptionPropertyHelper(i, "setDisplayName", new android.telephony.SubscriptionManager.CallISubMethodHelper() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda16
            @Override // android.telephony.SubscriptionManager.CallISubMethodHelper
            public final int callMethod(com.android.internal.telephony.ISub iSub) {
                int displayNameUsingSrc;
                displayNameUsingSrc = iSub.setDisplayNameUsingSrc(str, i, i2);
                return displayNameUsingSrc;
            }
        });
    }

    public int setDisplayNumber(final java.lang.String str, final int i) {
        if (str == null) {
            logd("[setDisplayNumber]- fail");
            return -1;
        }
        return setSubscriptionPropertyHelper(i, "setDisplayNumber", new android.telephony.SubscriptionManager.CallISubMethodHelper() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda19
            @Override // android.telephony.SubscriptionManager.CallISubMethodHelper
            public final int callMethod(com.android.internal.telephony.ISub iSub) {
                int displayNumber;
                displayNumber = iSub.setDisplayNumber(str, i);
                return displayNumber;
            }
        });
    }

    public int setDataRoaming(final int i, final int i2) {
        return setSubscriptionPropertyHelper(i2, "setDataRoaming", new android.telephony.SubscriptionManager.CallISubMethodHelper() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda5
            @Override // android.telephony.SubscriptionManager.CallISubMethodHelper
            public final int callMethod(com.android.internal.telephony.ISub iSub) {
                int dataRoaming;
                dataRoaming = iSub.setDataRoaming(i, i2);
                return dataRoaming;
            }
        });
    }

    public static int getSlotIndex(int i) {
        return sGetSlotIndexCache.query(java.lang.Integer.valueOf(i)).intValue();
    }

    @java.lang.Deprecated
    public int[] getSubscriptionIds(int i) {
        if (!isValidSlotIndex(i)) {
            return null;
        }
        return new int[]{getSubscriptionId(i)};
    }

    @java.lang.Deprecated
    public static int[] getSubId(int i) {
        if (!isValidSlotIndex(i)) {
            return null;
        }
        return new int[]{getSubscriptionId(i)};
    }

    public static int getSubscriptionId(int i) {
        if (!isValidSlotIndex(i)) {
            return -1;
        }
        return sGetSubIdCache.query(java.lang.Integer.valueOf(i)).intValue();
    }

    public static int getPhoneId(int i) {
        return sGetPhoneIdCache.query(java.lang.Integer.valueOf(i)).intValue();
    }

    private static void logd(java.lang.String str) {
        com.android.telephony.Rlog.d(LOG_TAG, str);
    }

    private static void loge(java.lang.String str) {
        com.android.telephony.Rlog.e(LOG_TAG, str);
    }

    public static int getDefaultSubscriptionId() {
        return sGetDefaultSubIdCacheAsUser.query(java.lang.Integer.valueOf(android.os.Process.myUserHandle().getIdentifier())).intValue();
    }

    public static int getDefaultVoiceSubscriptionId() {
        try {
            com.android.internal.telephony.ISub subscriptionService = android.telephony.TelephonyManager.getSubscriptionService();
            if (subscriptionService == null) {
                return -1;
            }
            return subscriptionService.getDefaultVoiceSubIdAsUser(android.os.Process.myUserHandle().getIdentifier());
        } catch (android.os.RemoteException e) {
            return -1;
        }
    }

    @android.annotation.SystemApi
    public void setDefaultVoiceSubscriptionId(int i) {
        try {
            com.android.internal.telephony.ISub subscriptionService = android.telephony.TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                subscriptionService.setDefaultVoiceSubId(i);
            }
        } catch (android.os.RemoteException e) {
        }
    }

    public void setDefaultVoiceSubId(int i) {
        setDefaultVoiceSubscriptionId(i);
    }

    public android.telephony.SubscriptionInfo getDefaultVoiceSubscriptionInfo() {
        return getActiveSubscriptionInfo(getDefaultVoiceSubscriptionId());
    }

    public static int getDefaultVoicePhoneId() {
        return getPhoneId(getDefaultVoiceSubscriptionId());
    }

    public static int getDefaultSmsSubscriptionId() {
        return sGetDefaultSmsSubIdCacheAsUser.query(java.lang.Integer.valueOf(android.os.Process.myUserHandle().getIdentifier())).intValue();
    }

    @android.annotation.SystemApi
    public void setDefaultSmsSubId(int i) {
        try {
            com.android.internal.telephony.ISub subscriptionService = android.telephony.TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                subscriptionService.setDefaultSmsSubId(i);
            }
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public static int getDefaultDataSubscriptionId() {
        return sGetDefaultDataSubIdCache.query((java.lang.Void) null).intValue();
    }

    @android.annotation.SystemApi
    public void setDefaultDataSubId(int i) {
        try {
            com.android.internal.telephony.ISub subscriptionService = android.telephony.TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                subscriptionService.setDefaultDataSubId(i);
            }
        } catch (android.os.RemoteException e) {
        }
    }

    public android.telephony.SubscriptionInfo getDefaultDataSubscriptionInfo() {
        return getActiveSubscriptionInfo(getDefaultDataSubscriptionId());
    }

    public static boolean isValidSubscriptionId(int i) {
        return i > -1;
    }

    public static boolean isUsableSubscriptionId(int i) {
        return isUsableSubIdValue(i);
    }

    public static boolean isUsableSubIdValue(int i) {
        return i >= 0 && i <= 2147483646;
    }

    public static boolean isValidSlotIndex(int i) {
        return i >= 0 && i < android.telephony.TelephonyManager.getDefault().getActiveModemCount();
    }

    public static boolean isValidPhoneId(int i) {
        return i >= 0 && i < android.telephony.TelephonyManager.getDefault().getActiveModemCount();
    }

    public static void putPhoneIdAndSubIdExtra(android.content.Intent intent, int i) {
        int subscriptionId = getSubscriptionId(i);
        if (isValidSubscriptionId(subscriptionId)) {
            putPhoneIdAndSubIdExtra(intent, i, subscriptionId);
            return;
        }
        logd("putPhoneIdAndSubIdExtra: no valid subs");
        intent.putExtra("phone", i);
        intent.putExtra("android.telephony.extra.SLOT_INDEX", i);
    }

    public static void putPhoneIdAndSubIdExtra(android.content.Intent intent, int i, int i2) {
        intent.putExtra("android.telephony.extra.SLOT_INDEX", i);
        intent.putExtra("phone", i);
        putSubscriptionIdExtra(intent, i2);
    }

    @android.annotation.SystemApi
    public int[] getActiveSubscriptionIdList() {
        return getActiveSubscriptionIdList(true);
    }

    @android.annotation.SystemApi
    public int[] getCompleteActiveSubscriptionIdList() {
        return getActiveSubscriptionIdList(false);
    }

    public int[] getActiveSubscriptionIdList(boolean z) {
        try {
            com.android.internal.telephony.ISub subscriptionService = android.telephony.TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                int[] activeSubIdList = subscriptionService.getActiveSubIdList(z);
                if (activeSubIdList != null) {
                    return activeSubIdList;
                }
            }
        } catch (android.os.RemoteException e) {
        }
        return new int[0];
    }

    public boolean isNetworkRoaming(int i) {
        if (getPhoneId(i) < 0) {
            return false;
        }
        return android.telephony.TelephonyManager.getDefault().isNetworkRoaming(i);
    }

    public static void setSubscriptionProperty(int i, java.lang.String str, java.lang.String str2) {
        try {
            com.android.internal.telephony.ISub subscriptionService = android.telephony.TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                subscriptionService.setSubscriptionProperty(i, str, str2);
            }
        } catch (android.os.RemoteException e) {
        }
    }

    public static java.lang.String serializeUriLists(java.util.List<android.net.Uri> list) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Iterator<android.net.Uri> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().toString());
        }
        try {
            java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
            java.io.ObjectOutputStream objectOutputStream = new java.io.ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(arrayList);
            objectOutputStream.flush();
            return android.util.Base64.encodeToString(byteArrayOutputStream.toByteArray(), 0);
        } catch (java.io.IOException e) {
            logd("serializeUriLists IO exception");
            return "";
        }
    }

    private static java.lang.String getStringSubscriptionProperty(android.content.Context context, int i, java.lang.String str) {
        java.lang.String str2 = null;
        try {
            com.android.internal.telephony.ISub subscriptionService = android.telephony.TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                str2 = subscriptionService.getSubscriptionProperty(i, str, context.getOpPackageName(), context.getAttributionTag());
            }
        } catch (android.os.RemoteException e) {
        }
        return android.text.TextUtils.emptyIfNull(str2);
    }

    public static boolean getBooleanSubscriptionProperty(int i, java.lang.String str, boolean z, android.content.Context context) {
        java.lang.String stringSubscriptionProperty = getStringSubscriptionProperty(context, i, str);
        if (!stringSubscriptionProperty.isEmpty()) {
            try {
                return java.lang.Integer.parseInt(stringSubscriptionProperty) == 1;
            } catch (java.lang.NumberFormatException e) {
                logd("getBooleanSubscriptionProperty NumberFormat exception");
            }
        }
        return z;
    }

    public static int getIntegerSubscriptionProperty(int i, java.lang.String str, int i2, android.content.Context context) {
        java.lang.String stringSubscriptionProperty = getStringSubscriptionProperty(context, i, str);
        if (!stringSubscriptionProperty.isEmpty()) {
            try {
                return java.lang.Integer.parseInt(stringSubscriptionProperty);
            } catch (java.lang.NumberFormatException e) {
                logd("getIntegerSubscriptionProperty NumberFormat exception");
            }
        }
        return i2;
    }

    public static long getLongSubscriptionProperty(int i, java.lang.String str, long j, android.content.Context context) {
        java.lang.String stringSubscriptionProperty = getStringSubscriptionProperty(context, i, str);
        if (!stringSubscriptionProperty.isEmpty()) {
            try {
                return java.lang.Long.parseLong(stringSubscriptionProperty);
            } catch (java.lang.NumberFormatException e) {
                logd("getLongSubscriptionProperty NumberFormat exception");
            }
        }
        return j;
    }

    @android.annotation.SystemApi
    public static android.content.res.Resources getResourcesForSubId(android.content.Context context, int i) {
        return getResourcesForSubId(context, i, false);
    }

    public static android.content.res.Resources getResourcesForSubId(android.content.Context context, int i, boolean z) {
        android.util.Pair<java.lang.String, android.content.res.Configuration> pair;
        android.util.Pair<java.lang.String, android.content.res.Configuration> pair2 = null;
        if (!isValidSubscriptionId(i)) {
            pair = null;
        } else {
            android.content.res.Configuration configuration = new android.content.res.Configuration(context.getResources().getConfiguration());
            if (z) {
                configuration.setLocale(java.util.Locale.ROOT);
            }
            pair = android.util.Pair.create(context.getPackageName(), configuration);
            synchronized (sResourcesCache) {
                android.content.res.Resources resources = sResourcesCache.get(pair);
                if (resources != null) {
                    return resources;
                }
            }
        }
        android.telephony.SubscriptionInfo activeSubscriptionInfo = from(context).getActiveSubscriptionInfo(i);
        android.content.res.Configuration configuration2 = new android.content.res.Configuration();
        if (activeSubscriptionInfo != null) {
            configuration2.mcc = activeSubscriptionInfo.getMcc();
            configuration2.mnc = activeSubscriptionInfo.getMnc();
            if (configuration2.mnc != 0) {
                pair2 = pair;
            } else {
                configuration2.mnc = 65535;
            }
        }
        if (z) {
            configuration2.setLocale(java.util.Locale.ROOT);
        }
        android.content.res.Resources resources2 = context.createConfigurationContext(configuration2).getResources();
        if (pair2 != null) {
            synchronized (sResourcesCache) {
                sResourcesCache.put(pair2, resources2);
            }
        }
        return resources2;
    }

    public boolean isActiveSubscriptionId(int i) {
        return isActiveSubId(i);
    }

    public boolean isActiveSubId(int i) {
        try {
            com.android.internal.telephony.ISub subscriptionService = android.telephony.TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                return subscriptionService.isActiveSubId(i, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
            }
            return false;
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    public java.util.List<android.telephony.SubscriptionPlan> getSubscriptionPlans(int i) {
        android.telephony.SubscriptionPlan[] subscriptionPlans = getNetworkPolicyManager().getSubscriptionPlans(i, this.mContext.getOpPackageName());
        return subscriptionPlans == null ? java.util.Collections.emptyList() : java.util.Arrays.asList(subscriptionPlans);
    }

    @java.lang.Deprecated
    public void setSubscriptionPlans(int i, java.util.List<android.telephony.SubscriptionPlan> list) {
        setSubscriptionPlans(i, list, 0L);
    }

    public void setSubscriptionPlans(int i, java.util.List<android.telephony.SubscriptionPlan> list, long j) {
        getNetworkPolicyManager().setSubscriptionPlans(i, (android.telephony.SubscriptionPlan[]) list.toArray(new android.telephony.SubscriptionPlan[0]), j, this.mContext.getOpPackageName());
    }

    public void setSubscriptionOverrideUnmetered(int i, boolean z, long j) {
        setSubscriptionOverrideUnmetered(i, z, android.telephony.TelephonyManager.getAllNetworkTypes(), j);
    }

    public void setSubscriptionOverrideUnmetered(int i, boolean z, int[] iArr, long j) {
        getNetworkPolicyManager().setSubscriptionOverride(i, 1, z ? 1 : 0, iArr, j, this.mContext.getOpPackageName());
    }

    public void setSubscriptionOverrideCongested(int i, boolean z, long j) {
        setSubscriptionOverrideCongested(i, z, android.telephony.TelephonyManager.getAllNetworkTypes(), j);
    }

    public void setSubscriptionOverrideCongested(int i, boolean z, int[] iArr, long j) {
        getNetworkPolicyManager().setSubscriptionOverride(i, 2, z ? 2 : 0, iArr, j, this.mContext.getOpPackageName());
    }

    public boolean canManageSubscription(android.telephony.SubscriptionInfo subscriptionInfo) {
        return canManageSubscription(subscriptionInfo, this.mContext.getPackageName());
    }

    @android.annotation.SystemApi
    public boolean canManageSubscription(android.telephony.SubscriptionInfo subscriptionInfo, java.lang.String str) {
        if (subscriptionInfo == null || subscriptionInfo.getAccessRules() == null || str == null) {
            return false;
        }
        try {
            android.content.pm.PackageInfo packageInfo = this.mContext.getPackageManager().getPackageInfo(str, 134217728);
            java.util.Iterator<android.telephony.UiccAccessRule> it = subscriptionInfo.getAccessRules().iterator();
            while (it.hasNext()) {
                if (it.next().getCarrierPrivilegeStatus(packageInfo) == 1) {
                    return true;
                }
            }
            return false;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            logd("Unknown package: " + str);
            return false;
        }
    }

    @android.annotation.SystemApi
    public void setPreferredDataSubscriptionId(int i, boolean z, java.util.concurrent.Executor executor, java.util.function.Consumer<java.lang.Integer> consumer) {
        try {
            com.android.internal.telephony.ISub subscriptionService = android.telephony.TelephonyManager.getSubscriptionService();
            if (subscriptionService == null) {
                throw new java.lang.IllegalStateException("subscription manager service is null.");
            }
            subscriptionService.setPreferredDataSubscriptionId(i, z, new android.telephony.SubscriptionManager.AnonymousClass1(executor, consumer));
        } catch (android.os.RemoteException e) {
            loge("setPreferredDataSubscriptionId RemoteException=" + e);
            e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.telephony.SubscriptionManager$1, reason: invalid class name */
    class AnonymousClass1 extends com.android.internal.telephony.ISetOpportunisticDataCallback.Stub {
        final /* synthetic */ java.util.function.Consumer val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        AnonymousClass1(java.util.concurrent.Executor executor, java.util.function.Consumer consumer) {
            this.val$executor = executor;
            this.val$callback = consumer;
        }

        @Override // com.android.internal.telephony.ISetOpportunisticDataCallback
        public void onComplete(final int i) {
            if (this.val$executor == null || this.val$callback == null) {
                return;
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.Executor executor = this.val$executor;
                final java.util.function.Consumer consumer = this.val$callback;
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.SubscriptionManager$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        consumer.accept(java.lang.Integer.valueOf(i));
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public int getPreferredDataSubscriptionId() {
        try {
            com.android.internal.telephony.ISub subscriptionService = android.telephony.TelephonyManager.getSubscriptionService();
            if (subscriptionService == null) {
                return Integer.MAX_VALUE;
            }
            return subscriptionService.getPreferredDataSubscriptionId();
        } catch (android.os.RemoteException e) {
            return Integer.MAX_VALUE;
        }
    }

    public java.util.List<android.telephony.SubscriptionInfo> getOpportunisticSubscriptions() {
        java.lang.String opPackageName = this.mContext != null ? this.mContext.getOpPackageName() : "<unknown>";
        java.util.List<android.telephony.SubscriptionInfo> list = null;
        java.lang.String attributionTag = this.mContext != null ? this.mContext.getAttributionTag() : null;
        try {
            com.android.internal.telephony.ISub subscriptionService = android.telephony.TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                list = subscriptionService.getOpportunisticSubscriptions(opPackageName, attributionTag);
            }
        } catch (android.os.RemoteException e) {
        }
        if (list == null) {
            return new java.util.ArrayList();
        }
        return list;
    }

    @java.lang.Deprecated
    public void switchToSubscription(int i, android.app.PendingIntent pendingIntent) {
        com.android.internal.util.Preconditions.checkNotNull(pendingIntent, "callbackIntent cannot be null");
        new android.telephony.euicc.EuiccManager(this.mContext).switchToSubscription(i, pendingIntent);
    }

    public boolean setOpportunistic(final boolean z, final int i) {
        return setSubscriptionPropertyHelper(i, "setOpportunistic", new android.telephony.SubscriptionManager.CallISubMethodHelper() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda18
            @Override // android.telephony.SubscriptionManager.CallISubMethodHelper
            public final int callMethod(com.android.internal.telephony.ISub iSub) {
                int lambda$setOpportunistic$7;
                lambda$setOpportunistic$7 = android.telephony.SubscriptionManager.this.lambda$setOpportunistic$7(z, i, iSub);
                return lambda$setOpportunistic$7;
            }
        }) == 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$setOpportunistic$7(boolean z, int i, com.android.internal.telephony.ISub iSub) throws android.os.RemoteException {
        return iSub.setOpportunistic(z, i, this.mContext.getOpPackageName());
    }

    public android.os.ParcelUuid createSubscriptionGroup(java.util.List<java.lang.Integer> list) {
        com.android.internal.util.Preconditions.checkNotNull(list, "can't create group for null subId list");
        java.lang.String opPackageName = this.mContext != null ? this.mContext.getOpPackageName() : "<unknown>";
        int[] array = list.stream().mapToInt(new java.util.function.ToIntFunction() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda7
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(java.lang.Object obj) {
                int intValue;
                intValue = ((java.lang.Integer) obj).intValue();
                return intValue;
            }
        }).toArray();
        try {
            com.android.internal.telephony.ISub subscriptionService = android.telephony.TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                return subscriptionService.createSubscriptionGroup(array, opPackageName);
            }
            throw new java.lang.IllegalStateException("telephony service is null.");
        } catch (android.os.RemoteException e) {
            loge("createSubscriptionGroup RemoteException " + e);
            e.rethrowAsRuntimeException();
            return null;
        }
    }

    public void addSubscriptionsIntoGroup(java.util.List<java.lang.Integer> list, android.os.ParcelUuid parcelUuid) {
        com.android.internal.util.Preconditions.checkNotNull(list, "subIdList can't be null.");
        com.android.internal.util.Preconditions.checkNotNull(parcelUuid, "groupUuid can't be null.");
        java.lang.String opPackageName = this.mContext != null ? this.mContext.getOpPackageName() : "<unknown>";
        int[] array = list.stream().mapToInt(new java.util.function.ToIntFunction() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda20
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(java.lang.Object obj) {
                int intValue;
                intValue = ((java.lang.Integer) obj).intValue();
                return intValue;
            }
        }).toArray();
        try {
            com.android.internal.telephony.ISub subscriptionService = android.telephony.TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                subscriptionService.addSubscriptionsIntoGroup(array, parcelUuid, opPackageName);
                return;
            }
            throw new java.lang.IllegalStateException("telephony service is null.");
        } catch (android.os.RemoteException e) {
            loge("addSubscriptionsIntoGroup RemoteException " + e);
            e.rethrowAsRuntimeException();
        }
    }

    private boolean isSystemProcess() {
        return android.os.Process.myUid() == 1000;
    }

    public void removeSubscriptionsFromGroup(java.util.List<java.lang.Integer> list, android.os.ParcelUuid parcelUuid) {
        com.android.internal.util.Preconditions.checkNotNull(list, "subIdList can't be null.");
        com.android.internal.util.Preconditions.checkNotNull(parcelUuid, "groupUuid can't be null.");
        java.lang.String opPackageName = this.mContext != null ? this.mContext.getOpPackageName() : "<unknown>";
        int[] array = list.stream().mapToInt(new java.util.function.ToIntFunction() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda0
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(java.lang.Object obj) {
                int intValue;
                intValue = ((java.lang.Integer) obj).intValue();
                return intValue;
            }
        }).toArray();
        try {
            com.android.internal.telephony.ISub subscriptionService = android.telephony.TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                subscriptionService.removeSubscriptionsFromGroup(array, parcelUuid, opPackageName);
                return;
            }
            throw new java.lang.IllegalStateException("telephony service is null.");
        } catch (android.os.RemoteException e) {
            loge("removeSubscriptionsFromGroup RemoteException " + e);
            e.rethrowAsRuntimeException();
        }
    }

    public java.util.List<android.telephony.SubscriptionInfo> getSubscriptionsInGroup(android.os.ParcelUuid parcelUuid) {
        com.android.internal.util.Preconditions.checkNotNull(parcelUuid, "groupUuid can't be null");
        java.lang.String opPackageName = this.mContext != null ? this.mContext.getOpPackageName() : "<unknown>";
        java.util.List<android.telephony.SubscriptionInfo> list = null;
        java.lang.String attributionTag = this.mContext != null ? this.mContext.getAttributionTag() : null;
        try {
            com.android.internal.telephony.ISub subscriptionService = android.telephony.TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                list = subscriptionService.getSubscriptionsInGroup(parcelUuid, opPackageName, attributionTag);
            } else if (!isSystemProcess()) {
                throw new java.lang.IllegalStateException("telephony service is null.");
            }
        } catch (android.os.RemoteException e) {
            loge("removeSubscriptionsFromGroup RemoteException " + e);
            if (!isSystemProcess()) {
                e.rethrowAsRuntimeException();
            }
        }
        return list == null ? java.util.Collections.emptyList() : list;
    }

    /* renamed from: isSubscriptionVisible, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public boolean lambda$getActiveSubscriptionInfoList$2(android.telephony.SubscriptionInfo subscriptionInfo) {
        if (subscriptionInfo == null) {
            return false;
        }
        if (subscriptionInfo.getGroupUuid() == null || !subscriptionInfo.isOpportunistic()) {
            return true;
        }
        return android.telephony.TelephonyManager.from(this.mContext).hasCarrierPrivileges(subscriptionInfo.getSubscriptionId()) || canManageSubscription(subscriptionInfo);
    }

    public java.util.List<android.telephony.SubscriptionInfo> getSelectableSubscriptionInfoList() {
        java.util.List<android.telephony.SubscriptionInfo> availableSubscriptionInfoList = getAvailableSubscriptionInfoList();
        if (availableSubscriptionInfoList == null) {
            return null;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.HashMap hashMap = new java.util.HashMap();
        for (android.telephony.SubscriptionInfo subscriptionInfo : availableSubscriptionInfoList) {
            if (subscriptionInfo.getGroupUuid() == null || !subscriptionInfo.isOpportunistic()) {
                android.os.ParcelUuid groupUuid = subscriptionInfo.getGroupUuid();
                if (groupUuid == null) {
                    arrayList.add(subscriptionInfo);
                } else if (!hashMap.containsKey(groupUuid) || (((android.telephony.SubscriptionInfo) hashMap.get(groupUuid)).getSimSlotIndex() == -1 && subscriptionInfo.getSimSlotIndex() != -1)) {
                    arrayList.remove(hashMap.get(groupUuid));
                    arrayList.add(subscriptionInfo);
                    hashMap.put(groupUuid, subscriptionInfo);
                }
            }
        }
        return arrayList;
    }

    @android.annotation.SystemApi
    public boolean setSubscriptionEnabled(int i, boolean z) {
        try {
            com.android.internal.telephony.ISub subscriptionService = android.telephony.TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                subscriptionService.setUiccApplicationsEnabled(z, i);
                return true;
            }
            return true;
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    @android.annotation.SystemApi
    public void setUiccApplicationsEnabled(int i, boolean z) {
        try {
            com.android.internal.telephony.ISub subscriptionService = android.telephony.TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                subscriptionService.setUiccApplicationsEnabled(z, i);
            }
        } catch (android.os.RemoteException e) {
        }
    }

    @android.annotation.SystemApi
    public boolean canDisablePhysicalSubscription() {
        try {
            com.android.internal.telephony.ISub subscriptionService = android.telephony.TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                return subscriptionService.canDisablePhysicalSubscription();
            }
            return false;
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    @android.annotation.SystemApi
    public boolean isSubscriptionEnabled(int i) {
        try {
            com.android.internal.telephony.ISub subscriptionService = android.telephony.TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                return subscriptionService.isSubscriptionEnabled(i);
            }
            return false;
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    public void setDeviceToDeviceStatusSharingPreference(final int i, final int i2) {
        setSubscriptionPropertyHelper(i, "setDeviceToDeviceSharingStatus", new android.telephony.SubscriptionManager.CallISubMethodHelper() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda1
            @Override // android.telephony.SubscriptionManager.CallISubMethodHelper
            public final int callMethod(com.android.internal.telephony.ISub iSub) {
                int deviceToDeviceStatusSharing;
                deviceToDeviceStatusSharing = iSub.setDeviceToDeviceStatusSharing(i2, i);
                return deviceToDeviceStatusSharing;
            }
        });
    }

    public int getDeviceToDeviceStatusSharingPreference(int i) {
        return getIntegerSubscriptionProperty(i, "d2d_sharing_status", 0, this.mContext);
    }

    public void setDeviceToDeviceStatusSharingContacts(final int i, final java.util.List<android.net.Uri> list) {
        serializeUriLists(list);
        setSubscriptionPropertyHelper(i, "setDeviceToDeviceSharingStatus", new android.telephony.SubscriptionManager.CallISubMethodHelper() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda8
            @Override // android.telephony.SubscriptionManager.CallISubMethodHelper
            public final int callMethod(com.android.internal.telephony.ISub iSub) {
                int deviceToDeviceStatusSharingContacts;
                deviceToDeviceStatusSharingContacts = iSub.setDeviceToDeviceStatusSharingContacts(android.telephony.SubscriptionManager.serializeUriLists(list), i);
                return deviceToDeviceStatusSharingContacts;
            }
        });
    }

    public java.util.List<android.net.Uri> getDeviceToDeviceStatusSharingContacts(int i) {
        java.lang.String stringSubscriptionProperty = getStringSubscriptionProperty(this.mContext, i, "d2d_sharing_contacts");
        if (stringSubscriptionProperty != null) {
            try {
                java.util.List list = (java.util.List) java.util.ArrayList.class.cast(new java.io.ObjectInputStream(new java.io.ByteArrayInputStream(android.util.Base64.decode(stringSubscriptionProperty, 0))).readObject());
                java.util.ArrayList arrayList = new java.util.ArrayList();
                java.util.Iterator it = list.iterator();
                while (it.hasNext()) {
                    arrayList.add(android.net.Uri.parse((java.lang.String) it.next()));
                }
                return arrayList;
            } catch (java.io.IOException e) {
                logd("getDeviceToDeviceStatusSharingContacts IO exception");
            } catch (java.lang.ClassNotFoundException e2) {
                logd("getDeviceToDeviceStatusSharingContacts ClassNotFound exception");
            }
        }
        return new java.util.ArrayList();
    }

    @android.annotation.SystemApi
    public int getEnabledSubscriptionId(int i) {
        try {
            com.android.internal.telephony.ISub subscriptionService = android.telephony.TelephonyManager.getSubscriptionService();
            if (subscriptionService == null) {
                return -1;
            }
            return subscriptionService.getEnabledSubscriptionId(i);
        } catch (android.os.RemoteException e) {
            return -1;
        }
    }

    private int setSubscriptionPropertyHelper(int i, java.lang.String str, android.telephony.SubscriptionManager.CallISubMethodHelper callISubMethodHelper) {
        if (!isValidSubscriptionId(i)) {
            logd(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START + str + "]- fail");
            return -1;
        }
        try {
            com.android.internal.telephony.ISub subscriptionService = android.telephony.TelephonyManager.getSubscriptionService();
            if (subscriptionService == null) {
                return 0;
            }
            return callISubMethodHelper.callMethod(subscriptionService);
        } catch (android.os.RemoteException e) {
            return 0;
        }
    }

    public static int getActiveDataSubscriptionId() {
        return sGetActiveDataSubscriptionIdCache.query((java.lang.Void) null).intValue();
    }

    public static void putSubscriptionIdExtra(android.content.Intent intent, int i) {
        intent.putExtra("android.telephony.extra.SUBSCRIPTION_INDEX", i);
        intent.putExtra(com.android.internal.telephony.PhoneConstants.SUBSCRIPTION_KEY, i);
    }

    public static void invalidateSubscriptionManagerServiceCaches() {
        android.app.PropertyInvalidatedCache.invalidateCache(CACHE_KEY_SUBSCRIPTION_MANAGER_SERVICE_PROPERTY);
    }

    public static void disableCaching() {
        sGetDefaultSubIdCacheAsUser.disableLocal();
        sGetDefaultDataSubIdCache.disableLocal();
        sGetActiveDataSubscriptionIdCache.disableLocal();
        sGetDefaultSmsSubIdCacheAsUser.disableLocal();
        sGetSlotIndexCache.disableLocal();
        sGetSubIdCache.disableLocal();
        sGetPhoneIdCache.disableLocal();
    }

    public static void clearCaches() {
        sGetDefaultSubIdCacheAsUser.clear();
        sGetDefaultDataSubIdCache.clear();
        sGetActiveDataSubscriptionIdCache.clear();
        sGetDefaultSmsSubIdCacheAsUser.clear();
        sGetSlotIndexCache.clear();
        sGetSubIdCache.clear();
        sGetPhoneIdCache.clear();
    }

    @android.annotation.SystemApi
    public byte[] getAllSimSpecificSettingsForBackup() {
        return this.mContext.getContentResolver().call(SIM_INFO_BACKUP_AND_RESTORE_CONTENT_URI, GET_SIM_SPECIFIC_SETTINGS_METHOD_NAME, (java.lang.String) null, (android.os.Bundle) null).getByteArray(KEY_SIM_SPECIFIC_SETTINGS_DATA);
    }

    @android.annotation.SystemApi
    public void restoreAllSimSpecificSettingsFromBackup(byte[] bArr) {
        try {
            com.android.internal.telephony.ISub subscriptionService = android.telephony.TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                subscriptionService.restoreAllSimSpecificSettingsFromBackup(bArr);
                return;
            }
            throw new java.lang.IllegalStateException("subscription service unavailable.");
        } catch (android.os.RemoteException e) {
            if (!isSystemProcess()) {
                e.rethrowAsRuntimeException();
            }
        }
    }

    public java.lang.String getPhoneNumber(int i, int i2) {
        if (i == Integer.MAX_VALUE) {
            i = getDefaultSubscriptionId();
        }
        if (i2 != 1 && i2 != 2 && i2 != 3) {
            throw new java.lang.IllegalArgumentException("invalid source " + i2);
        }
        try {
            com.android.internal.telephony.ISub subscriptionService = android.telephony.TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                return subscriptionService.getPhoneNumber(i, i2, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
            }
            throw new java.lang.IllegalStateException("subscription service unavailable.");
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public java.lang.String getPhoneNumber(int i) {
        if (i == Integer.MAX_VALUE) {
            i = getDefaultSubscriptionId();
        }
        try {
            com.android.internal.telephony.ISub subscriptionService = android.telephony.TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                return subscriptionService.getPhoneNumberFromFirstAvailableSource(i, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
            }
            throw new java.lang.IllegalStateException("subscription service unavailable.");
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public void setCarrierPhoneNumber(int i, java.lang.String str) {
        int i2;
        if (i != Integer.MAX_VALUE) {
            i2 = i;
        } else {
            i2 = getDefaultSubscriptionId();
        }
        if (str == null) {
            throw new java.lang.NullPointerException("invalid number null");
        }
        try {
            com.android.internal.telephony.ISub subscriptionService = android.telephony.TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                subscriptionService.setPhoneNumber(i2, 2, str, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
                return;
            }
            throw new java.lang.IllegalStateException("subscription service unavailable.");
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    void setUsageSetting(final int i, final int i2) {
        setSubscriptionPropertyHelper(i, "setUsageSetting", new android.telephony.SubscriptionManager.CallISubMethodHelper() { // from class: android.telephony.SubscriptionManager$$ExternalSyntheticLambda3
            @Override // android.telephony.SubscriptionManager.CallISubMethodHelper
            public final int callMethod(com.android.internal.telephony.ISub iSub) {
                int lambda$setUsageSetting$13;
                lambda$setUsageSetting$13 = android.telephony.SubscriptionManager.this.lambda$setUsageSetting$13(i2, i, iSub);
                return lambda$setUsageSetting$13;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$setUsageSetting$13(int i, int i2, com.android.internal.telephony.ISub iSub) throws android.os.RemoteException {
        return iSub.setUsageSetting(i, i2, this.mContext.getOpPackageName());
    }

    public static java.lang.String phoneNumberSourceToString(int i) {
        switch (i) {
            case 1:
                return "UICC";
            case 2:
                return "CARRIER";
            case 3:
                return "IMS";
            default:
                return "UNKNOWN(" + i + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    public static java.lang.String displayNameSourceToString(int i) {
        switch (i) {
            case -1:
                return "UNKNOWN";
            case 0:
                return "CARRIER_ID";
            case 1:
                return "SIM_SPN";
            case 2:
                return "USER_INPUT";
            case 3:
                return "CARRIER";
            case 4:
                return "SIM_PNN";
            default:
                return "UNKNOWN(" + i + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    public static java.lang.String subscriptionTypeToString(int i) {
        switch (i) {
            case 0:
                return "LOCAL_SIM";
            case 1:
                return "REMOTE_SIM";
            default:
                return "UNKNOWN(" + i + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    public static java.lang.String usageSettingToString(int i) {
        switch (i) {
            case -1:
                return "UNKNOWN";
            case 0:
                return "DEFAULT";
            case 1:
                return "VOICE_CENTRIC";
            case 2:
                return "DATA_CENTRIC";
            default:
                return "UNKNOWN(" + i + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    public void setSubscriptionUserHandle(int i, android.os.UserHandle userHandle) {
        if (!isValidSubscriptionId(i)) {
            throw new java.lang.IllegalArgumentException("[setSubscriptionUserHandle]: Invalid subscriptionId: " + i);
        }
        try {
            com.android.internal.telephony.ISub subscriptionService = android.telephony.TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                subscriptionService.setSubscriptionUserHandle(userHandle, i);
                return;
            }
            throw new java.lang.IllegalStateException("[setSubscriptionUserHandle]: subscription service unavailable");
        } catch (android.os.RemoteException e) {
            e.rethrowAsRuntimeException();
        }
    }

    public android.os.UserHandle getSubscriptionUserHandle(int i) {
        if (!isValidSubscriptionId(i)) {
            throw new java.lang.IllegalArgumentException("[getSubscriptionUserHandle]: Invalid subscriptionId: " + i);
        }
        try {
            com.android.internal.telephony.ISub subscriptionService = android.telephony.TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                return subscriptionService.getSubscriptionUserHandle(i);
            }
            android.util.Log.e(LOG_TAG, "[getSubscriptionUserHandle]: subscription service unavailable");
            return null;
        } catch (android.os.RemoteException e) {
            e.rethrowAsRuntimeException();
            return null;
        }
    }

    public boolean isSubscriptionAssociatedWithUser(int i, android.os.UserHandle userHandle) {
        if (!isValidSubscriptionId(i)) {
            throw new java.lang.IllegalArgumentException("[isSubscriptionAssociatedWithUser]: Invalid subscriptionId: " + i);
        }
        try {
            com.android.internal.telephony.ISub subscriptionService = android.telephony.TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                return subscriptionService.isSubscriptionAssociatedWithUser(i, userHandle);
            }
            android.util.Log.e(LOG_TAG, "[isSubscriptionAssociatedWithUser]: subscription service unavailable");
            return false;
        } catch (android.os.RemoteException e) {
            e.rethrowAsRuntimeException();
            return false;
        }
    }

    public boolean isSubscriptionAssociatedWithUser(int i) {
        if (!isValidSubscriptionId(i)) {
            throw new java.lang.IllegalArgumentException("[isSubscriptionAssociatedWithCallingUser]: Invalid subscriptionId: " + i);
        }
        try {
            com.android.internal.telephony.ISub subscriptionService = android.telephony.TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                return subscriptionService.isSubscriptionAssociatedWithCallingUser(i);
            }
            throw new java.lang.IllegalStateException("subscription service unavailable.");
        } catch (android.os.RemoteException e) {
            e.rethrowAsRuntimeException();
            return false;
        }
    }

    public java.util.List<android.telephony.SubscriptionInfo> getSubscriptionInfoListAssociatedWithUser(android.os.UserHandle userHandle) {
        com.android.internal.telephony.ISub subscriptionService;
        try {
            subscriptionService = android.telephony.TelephonyManager.getSubscriptionService();
        } catch (android.os.RemoteException e) {
            e.rethrowAsRuntimeException();
        }
        if (subscriptionService != null) {
            return subscriptionService.getSubscriptionInfoListAssociatedWithUser(userHandle);
        }
        android.util.Log.e(LOG_TAG, "[getSubscriptionInfoListAssociatedWithUser]: subscription service unavailable");
        return new java.util.ArrayList();
    }

    public static int getAllServiceCapabilityBitmasks() {
        return SERVICE_CAPABILITY_VOICE_BITMASK | SERVICE_CAPABILITY_SMS_BITMASK | SERVICE_CAPABILITY_DATA_BITMASK;
    }

    public static java.util.Set<java.lang.Integer> getServiceCapabilitiesSet(int i) {
        java.util.HashSet hashSet = new java.util.HashSet();
        for (int i2 = 1; i2 <= 3; i2++) {
            int serviceCapabilityToBitmask = serviceCapabilityToBitmask(i2);
            if ((i & serviceCapabilityToBitmask) == serviceCapabilityToBitmask) {
                hashSet.add(java.lang.Integer.valueOf(i2));
            }
        }
        return java.util.Collections.unmodifiableSet(hashSet);
    }

    public static int serviceCapabilityToBitmask(int i) {
        return 1 << (i - 1);
    }

    @android.annotation.SystemApi
    public void setTransferStatus(int i, int i2) {
        try {
            com.android.internal.telephony.ISub subscriptionService = android.telephony.TelephonyManager.getSubscriptionService();
            if (subscriptionService != null) {
                subscriptionService.setTransferStatus(i, i2);
            }
        } catch (android.os.RemoteException e) {
            logd("setTransferStatus for subId = " + i + " failed.");
            throw e.rethrowFromSystemServer();
        }
    }
}
