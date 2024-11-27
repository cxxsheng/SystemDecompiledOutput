package com.android.internal.telephony.flags;

/* loaded from: classes5.dex */
public class FakeFeatureFlagsImpl implements com.android.internal.telephony.flags.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_ADD_ANOMALY_WHEN_NOTIFY_CONFIG_CHANGED_WITH_INVALID_PHONE, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_ADD_RAT_RELATED_SUGGESTED_ACTION_TO_IMS_REGISTRATION, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_ALLOW_MMTEL_IN_NON_VOPS, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_AP_DOMAIN_SELECTION_ENABLED, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_APN_SETTING_FIELD_SUPPORT_FLAG, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_AUTO_DATA_SWITCH_ALLOW_ROAMING, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_AUTO_DATA_SWITCH_RAT_SS, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_CALL_EXTRA_FOR_NON_HOLD_SUPPORTED_CARRIERS, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_CARRIER_ENABLED_SATELLITE_FLAG, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_CARRIER_RESTRICTION_RULES_ENHANCEMENT, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_CARRIER_RESTRICTION_STATUS, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_CLEAR_CACHED_IMS_PHONE_NUMBER_WHEN_DEVICE_LOST_IMS_REGISTRATION, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_CONFERENCE_HOLD_UNHOLD_CHANGED_TO_SEND_MESSAGE, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_DATA_CALL_SESSION_STATS_CAPTURES_CROSS_SIM_CALLING, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_DATA_ONLY_CELLULAR_SERVICE, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_DATA_ONLY_SERVICE_ALLOW_EMERGENCY_CALL_ONLY, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_DISMISS_NETWORK_SELECTION_NOTIFICATION_ON_SIM_DISABLE, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_DO_NOT_OVERRIDE_PRECISE_LABEL, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_DOMAIN_SELECTION_METRICS_ENABLED, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_DSRS_DIAGNOSTICS_ENABLED, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_EMERGENCY_REGISTRATION_STATE, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_ENABLE_AEAD_ALGORITHMS, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_ENABLE_CARRIER_CONFIG_N1_CONTROL_ATTEMPT2, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_ENABLE_IDENTIFIER_DISCLOSURE_TRANSPARENCY, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_ENABLE_IDENTIFIER_DISCLOSURE_TRANSPARENCY_UNSOL_EVENTS, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_ENABLE_MODEM_CIPHER_TRANSPARENCY, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_ENABLE_MODEM_CIPHER_TRANSPARENCY_UNSOL_EVENTS, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_ENABLE_MULTIPLE_SA_PROPOSALS, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_ENABLE_TELEPHONY_ANALYTICS, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_ENABLE_WPS_CHECK_API_FLAG, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_ENFORCE_SUBSCRIPTION_USER_FILTER, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_ENFORCE_TELEPHONY_FEATURE_MAPPING, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_ENFORCE_TELEPHONY_FEATURE_MAPPING_FOR_PUBLIC_APIS, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_ENSURE_ACCESS_TO_CALL_SETTINGS_IS_RESTRICTED, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_ESIM_AVAILABLE_MEMORY, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_ESIM_BOOTSTRAP_PROVISIONING_FLAG, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_FIX_CRASH_ON_GETTING_CONFIG_WHEN_PHONE_IS_GONE, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_FORCE_IWLAN_MMS, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_HIDE_PREFER_3G_ITEM, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_HIDE_ROAMING_ICON, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_IGNORE_ALREADY_TERMINATED_INCOMING_CALL_BEFORE_REGISTERING_LISTENER, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_IGNORE_EXISTING_NETWORKS_FOR_INTERNET_ALLOWED_CHECKING, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_IMSI_KEY_RETRY_DOWNLOAD_ON_PHONE_UNLOCK, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_LOG_MMS_SMS_DATABASE_ACCESS_INFO, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_METERED_EMBB_URLCC, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_MINIMAL_TELEPHONY_CDM_CHECK, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_MINIMAL_TELEPHONY_MANAGERS_CONDITIONAL_ON_FEATURES, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_MMS_DISABLED_ERROR, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_NETWORK_REGISTRATION_INFO_REJECT_CAUSE, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_NETWORK_VALIDATION, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_NOTIFY_DATA_ACTIVITY_CHANGED_WITH_SLOT, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_OEM_ENABLED_SATELLITE_FLAG, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_PREVENT_INVOCATION_REPEAT_OF_RIL_CALL_WHEN_DEVICE_DOES_NOT_SUPPORT_VOICE, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_PREVENT_SYSTEM_SERVER_AND_PHONE_DEADLOCK, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_RECONNECT_QUALIFIED_NETWORK, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_REFINE_PREFERRED_DATA_PROFILE_SELECTION, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_REJECT_BAD_SUB_ID_INTERACTION, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_RELAX_HO_TEARDOWN, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_REORGANIZE_ROAMING_NOTIFICATION, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_RESET_MOBILE_NETWORK_SETTINGS, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_SHOW_CALL_FAIL_NOTIFICATION_FOR_2G_TOGGLE, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_SHOW_CALL_ID_AND_CALL_WAITING_IN_ADDITIONAL_SETTINGS_MENU, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_SIMULTANEOUS_CALLING_INDICATIONS, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_SLICING_ADDITIONAL_ERROR_CODES, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_SMS_DOMAIN_SELECTION_ENABLED, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_STOP_SPAMMING_EMERGENCY_NOTIFICATION, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_SUBSCRIPTION_USER_ASSOCIATION_QUERY, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_SUPPORT_NR_SA_RRC_IDLE, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_SUPPORT_PSIM_TO_ESIM_CONVERSION, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_TERMINATE_ACTIVE_VIDEO_CALL_WHEN_ACCEPTING_SECOND_VIDEO_CALL_AS_AUDIO_ONLY, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_UNTHROTTLE_CHECK_TRANSPORT, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_UPDATE_IMS_SERVICE_BY_GATHERING_PROVISIONING_CHANGES, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_UPDATE_ROAMING_STATE_TO_SET_WFC_MODE, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_USE_ALARM_CALLBACK, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_USE_AOSP_DOMAIN_SELECTION_SERVICE, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_USE_OEM_DOMAIN_SELECTION_SERVICE, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_VONR_ENABLED_METRIC, false), java.util.Map.entry(com.android.internal.telephony.flags.Flags.FLAG_WORK_PROFILE_API_SPLIT, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(com.android.internal.telephony.flags.Flags.FLAG_ADD_ANOMALY_WHEN_NOTIFY_CONFIG_CHANGED_WITH_INVALID_PHONE, com.android.internal.telephony.flags.Flags.FLAG_ADD_RAT_RELATED_SUGGESTED_ACTION_TO_IMS_REGISTRATION, com.android.internal.telephony.flags.Flags.FLAG_ALLOW_MMTEL_IN_NON_VOPS, com.android.internal.telephony.flags.Flags.FLAG_AP_DOMAIN_SELECTION_ENABLED, com.android.internal.telephony.flags.Flags.FLAG_APN_SETTING_FIELD_SUPPORT_FLAG, com.android.internal.telephony.flags.Flags.FLAG_AUTO_DATA_SWITCH_ALLOW_ROAMING, com.android.internal.telephony.flags.Flags.FLAG_AUTO_DATA_SWITCH_RAT_SS, com.android.internal.telephony.flags.Flags.FLAG_CALL_EXTRA_FOR_NON_HOLD_SUPPORTED_CARRIERS, com.android.internal.telephony.flags.Flags.FLAG_CARRIER_ENABLED_SATELLITE_FLAG, com.android.internal.telephony.flags.Flags.FLAG_CARRIER_RESTRICTION_RULES_ENHANCEMENT, com.android.internal.telephony.flags.Flags.FLAG_CARRIER_RESTRICTION_STATUS, com.android.internal.telephony.flags.Flags.FLAG_CLEAR_CACHED_IMS_PHONE_NUMBER_WHEN_DEVICE_LOST_IMS_REGISTRATION, com.android.internal.telephony.flags.Flags.FLAG_CONFERENCE_HOLD_UNHOLD_CHANGED_TO_SEND_MESSAGE, com.android.internal.telephony.flags.Flags.FLAG_DATA_CALL_SESSION_STATS_CAPTURES_CROSS_SIM_CALLING, com.android.internal.telephony.flags.Flags.FLAG_DATA_ONLY_CELLULAR_SERVICE, com.android.internal.telephony.flags.Flags.FLAG_DATA_ONLY_SERVICE_ALLOW_EMERGENCY_CALL_ONLY, com.android.internal.telephony.flags.Flags.FLAG_DISMISS_NETWORK_SELECTION_NOTIFICATION_ON_SIM_DISABLE, com.android.internal.telephony.flags.Flags.FLAG_DO_NOT_OVERRIDE_PRECISE_LABEL, com.android.internal.telephony.flags.Flags.FLAG_DOMAIN_SELECTION_METRICS_ENABLED, com.android.internal.telephony.flags.Flags.FLAG_DSRS_DIAGNOSTICS_ENABLED, com.android.internal.telephony.flags.Flags.FLAG_EMERGENCY_REGISTRATION_STATE, com.android.internal.telephony.flags.Flags.FLAG_ENABLE_AEAD_ALGORITHMS, com.android.internal.telephony.flags.Flags.FLAG_ENABLE_CARRIER_CONFIG_N1_CONTROL_ATTEMPT2, com.android.internal.telephony.flags.Flags.FLAG_ENABLE_IDENTIFIER_DISCLOSURE_TRANSPARENCY, com.android.internal.telephony.flags.Flags.FLAG_ENABLE_IDENTIFIER_DISCLOSURE_TRANSPARENCY_UNSOL_EVENTS, com.android.internal.telephony.flags.Flags.FLAG_ENABLE_MODEM_CIPHER_TRANSPARENCY, com.android.internal.telephony.flags.Flags.FLAG_ENABLE_MODEM_CIPHER_TRANSPARENCY_UNSOL_EVENTS, com.android.internal.telephony.flags.Flags.FLAG_ENABLE_MULTIPLE_SA_PROPOSALS, com.android.internal.telephony.flags.Flags.FLAG_ENABLE_TELEPHONY_ANALYTICS, com.android.internal.telephony.flags.Flags.FLAG_ENABLE_WPS_CHECK_API_FLAG, com.android.internal.telephony.flags.Flags.FLAG_ENFORCE_SUBSCRIPTION_USER_FILTER, com.android.internal.telephony.flags.Flags.FLAG_ENFORCE_TELEPHONY_FEATURE_MAPPING, com.android.internal.telephony.flags.Flags.FLAG_ENFORCE_TELEPHONY_FEATURE_MAPPING_FOR_PUBLIC_APIS, com.android.internal.telephony.flags.Flags.FLAG_ENSURE_ACCESS_TO_CALL_SETTINGS_IS_RESTRICTED, com.android.internal.telephony.flags.Flags.FLAG_ESIM_AVAILABLE_MEMORY, com.android.internal.telephony.flags.Flags.FLAG_ESIM_BOOTSTRAP_PROVISIONING_FLAG, com.android.internal.telephony.flags.Flags.FLAG_FIX_CRASH_ON_GETTING_CONFIG_WHEN_PHONE_IS_GONE, com.android.internal.telephony.flags.Flags.FLAG_FORCE_IWLAN_MMS, com.android.internal.telephony.flags.Flags.FLAG_HIDE_PREFER_3G_ITEM, com.android.internal.telephony.flags.Flags.FLAG_HIDE_ROAMING_ICON, com.android.internal.telephony.flags.Flags.FLAG_IGNORE_ALREADY_TERMINATED_INCOMING_CALL_BEFORE_REGISTERING_LISTENER, com.android.internal.telephony.flags.Flags.FLAG_IGNORE_EXISTING_NETWORKS_FOR_INTERNET_ALLOWED_CHECKING, com.android.internal.telephony.flags.Flags.FLAG_IMSI_KEY_RETRY_DOWNLOAD_ON_PHONE_UNLOCK, com.android.internal.telephony.flags.Flags.FLAG_LOG_MMS_SMS_DATABASE_ACCESS_INFO, com.android.internal.telephony.flags.Flags.FLAG_METERED_EMBB_URLCC, com.android.internal.telephony.flags.Flags.FLAG_MINIMAL_TELEPHONY_CDM_CHECK, com.android.internal.telephony.flags.Flags.FLAG_MINIMAL_TELEPHONY_MANAGERS_CONDITIONAL_ON_FEATURES, com.android.internal.telephony.flags.Flags.FLAG_MMS_DISABLED_ERROR, com.android.internal.telephony.flags.Flags.FLAG_NETWORK_REGISTRATION_INFO_REJECT_CAUSE, com.android.internal.telephony.flags.Flags.FLAG_NETWORK_VALIDATION, com.android.internal.telephony.flags.Flags.FLAG_NOTIFY_DATA_ACTIVITY_CHANGED_WITH_SLOT, com.android.internal.telephony.flags.Flags.FLAG_OEM_ENABLED_SATELLITE_FLAG, com.android.internal.telephony.flags.Flags.FLAG_PREVENT_INVOCATION_REPEAT_OF_RIL_CALL_WHEN_DEVICE_DOES_NOT_SUPPORT_VOICE, com.android.internal.telephony.flags.Flags.FLAG_PREVENT_SYSTEM_SERVER_AND_PHONE_DEADLOCK, com.android.internal.telephony.flags.Flags.FLAG_RECONNECT_QUALIFIED_NETWORK, com.android.internal.telephony.flags.Flags.FLAG_REFINE_PREFERRED_DATA_PROFILE_SELECTION, com.android.internal.telephony.flags.Flags.FLAG_REJECT_BAD_SUB_ID_INTERACTION, com.android.internal.telephony.flags.Flags.FLAG_RELAX_HO_TEARDOWN, com.android.internal.telephony.flags.Flags.FLAG_REORGANIZE_ROAMING_NOTIFICATION, com.android.internal.telephony.flags.Flags.FLAG_RESET_MOBILE_NETWORK_SETTINGS, com.android.internal.telephony.flags.Flags.FLAG_SHOW_CALL_FAIL_NOTIFICATION_FOR_2G_TOGGLE, com.android.internal.telephony.flags.Flags.FLAG_SHOW_CALL_ID_AND_CALL_WAITING_IN_ADDITIONAL_SETTINGS_MENU, com.android.internal.telephony.flags.Flags.FLAG_SIMULTANEOUS_CALLING_INDICATIONS, com.android.internal.telephony.flags.Flags.FLAG_SLICING_ADDITIONAL_ERROR_CODES, com.android.internal.telephony.flags.Flags.FLAG_SMS_DOMAIN_SELECTION_ENABLED, com.android.internal.telephony.flags.Flags.FLAG_STOP_SPAMMING_EMERGENCY_NOTIFICATION, com.android.internal.telephony.flags.Flags.FLAG_SUBSCRIPTION_USER_ASSOCIATION_QUERY, com.android.internal.telephony.flags.Flags.FLAG_SUPPORT_NR_SA_RRC_IDLE, com.android.internal.telephony.flags.Flags.FLAG_SUPPORT_PSIM_TO_ESIM_CONVERSION, com.android.internal.telephony.flags.Flags.FLAG_TERMINATE_ACTIVE_VIDEO_CALL_WHEN_ACCEPTING_SECOND_VIDEO_CALL_AS_AUDIO_ONLY, com.android.internal.telephony.flags.Flags.FLAG_UNTHROTTLE_CHECK_TRANSPORT, com.android.internal.telephony.flags.Flags.FLAG_UPDATE_IMS_SERVICE_BY_GATHERING_PROVISIONING_CHANGES, com.android.internal.telephony.flags.Flags.FLAG_UPDATE_ROAMING_STATE_TO_SET_WFC_MODE, com.android.internal.telephony.flags.Flags.FLAG_USE_ALARM_CALLBACK, com.android.internal.telephony.flags.Flags.FLAG_USE_AOSP_DOMAIN_SELECTION_SERVICE, com.android.internal.telephony.flags.Flags.FLAG_USE_OEM_DOMAIN_SELECTION_SERVICE, com.android.internal.telephony.flags.Flags.FLAG_VONR_ENABLED_METRIC, com.android.internal.telephony.flags.Flags.FLAG_WORK_PROFILE_API_SPLIT, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean addAnomalyWhenNotifyConfigChangedWithInvalidPhone() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_ADD_ANOMALY_WHEN_NOTIFY_CONFIG_CHANGED_WITH_INVALID_PHONE);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean addRatRelatedSuggestedActionToImsRegistration() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_ADD_RAT_RELATED_SUGGESTED_ACTION_TO_IMS_REGISTRATION);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean allowMmtelInNonVops() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_ALLOW_MMTEL_IN_NON_VOPS);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean apDomainSelectionEnabled() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_AP_DOMAIN_SELECTION_ENABLED);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean apnSettingFieldSupportFlag() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_APN_SETTING_FIELD_SUPPORT_FLAG);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean autoDataSwitchAllowRoaming() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_AUTO_DATA_SWITCH_ALLOW_ROAMING);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean autoDataSwitchRatSs() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_AUTO_DATA_SWITCH_RAT_SS);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean callExtraForNonHoldSupportedCarriers() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_CALL_EXTRA_FOR_NON_HOLD_SUPPORTED_CARRIERS);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean carrierEnabledSatelliteFlag() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_CARRIER_ENABLED_SATELLITE_FLAG);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean carrierRestrictionRulesEnhancement() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_CARRIER_RESTRICTION_RULES_ENHANCEMENT);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean carrierRestrictionStatus() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_CARRIER_RESTRICTION_STATUS);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean clearCachedImsPhoneNumberWhenDeviceLostImsRegistration() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_CLEAR_CACHED_IMS_PHONE_NUMBER_WHEN_DEVICE_LOST_IMS_REGISTRATION);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean conferenceHoldUnholdChangedToSendMessage() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_CONFERENCE_HOLD_UNHOLD_CHANGED_TO_SEND_MESSAGE);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean dataCallSessionStatsCapturesCrossSimCalling() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_DATA_CALL_SESSION_STATS_CAPTURES_CROSS_SIM_CALLING);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean dataOnlyCellularService() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_DATA_ONLY_CELLULAR_SERVICE);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean dataOnlyServiceAllowEmergencyCallOnly() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_DATA_ONLY_SERVICE_ALLOW_EMERGENCY_CALL_ONLY);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean dismissNetworkSelectionNotificationOnSimDisable() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_DISMISS_NETWORK_SELECTION_NOTIFICATION_ON_SIM_DISABLE);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean doNotOverridePreciseLabel() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_DO_NOT_OVERRIDE_PRECISE_LABEL);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean domainSelectionMetricsEnabled() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_DOMAIN_SELECTION_METRICS_ENABLED);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean dsrsDiagnosticsEnabled() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_DSRS_DIAGNOSTICS_ENABLED);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean emergencyRegistrationState() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_EMERGENCY_REGISTRATION_STATE);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean enableAeadAlgorithms() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_ENABLE_AEAD_ALGORITHMS);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean enableCarrierConfigN1ControlAttempt2() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_ENABLE_CARRIER_CONFIG_N1_CONTROL_ATTEMPT2);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean enableIdentifierDisclosureTransparency() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_ENABLE_IDENTIFIER_DISCLOSURE_TRANSPARENCY);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean enableIdentifierDisclosureTransparencyUnsolEvents() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_ENABLE_IDENTIFIER_DISCLOSURE_TRANSPARENCY_UNSOL_EVENTS);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean enableModemCipherTransparency() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_ENABLE_MODEM_CIPHER_TRANSPARENCY);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean enableModemCipherTransparencyUnsolEvents() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_ENABLE_MODEM_CIPHER_TRANSPARENCY_UNSOL_EVENTS);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean enableMultipleSaProposals() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_ENABLE_MULTIPLE_SA_PROPOSALS);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean enableTelephonyAnalytics() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_ENABLE_TELEPHONY_ANALYTICS);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean enableWpsCheckApiFlag() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_ENABLE_WPS_CHECK_API_FLAG);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean enforceSubscriptionUserFilter() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_ENFORCE_SUBSCRIPTION_USER_FILTER);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean enforceTelephonyFeatureMapping() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_ENFORCE_TELEPHONY_FEATURE_MAPPING);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean enforceTelephonyFeatureMappingForPublicApis() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_ENFORCE_TELEPHONY_FEATURE_MAPPING_FOR_PUBLIC_APIS);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean ensureAccessToCallSettingsIsRestricted() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_ENSURE_ACCESS_TO_CALL_SETTINGS_IS_RESTRICTED);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean esimAvailableMemory() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_ESIM_AVAILABLE_MEMORY);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean esimBootstrapProvisioningFlag() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_ESIM_BOOTSTRAP_PROVISIONING_FLAG);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean fixCrashOnGettingConfigWhenPhoneIsGone() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_FIX_CRASH_ON_GETTING_CONFIG_WHEN_PHONE_IS_GONE);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean forceIwlanMms() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_FORCE_IWLAN_MMS);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean hidePrefer3gItem() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_HIDE_PREFER_3G_ITEM);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean hideRoamingIcon() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_HIDE_ROAMING_ICON);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean ignoreAlreadyTerminatedIncomingCallBeforeRegisteringListener() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_IGNORE_ALREADY_TERMINATED_INCOMING_CALL_BEFORE_REGISTERING_LISTENER);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean ignoreExistingNetworksForInternetAllowedChecking() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_IGNORE_EXISTING_NETWORKS_FOR_INTERNET_ALLOWED_CHECKING);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean imsiKeyRetryDownloadOnPhoneUnlock() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_IMSI_KEY_RETRY_DOWNLOAD_ON_PHONE_UNLOCK);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean logMmsSmsDatabaseAccessInfo() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_LOG_MMS_SMS_DATABASE_ACCESS_INFO);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean meteredEmbbUrlcc() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_METERED_EMBB_URLCC);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean minimalTelephonyCdmCheck() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_MINIMAL_TELEPHONY_CDM_CHECK);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean minimalTelephonyManagersConditionalOnFeatures() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_MINIMAL_TELEPHONY_MANAGERS_CONDITIONAL_ON_FEATURES);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean mmsDisabledError() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_MMS_DISABLED_ERROR);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean networkRegistrationInfoRejectCause() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_NETWORK_REGISTRATION_INFO_REJECT_CAUSE);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean networkValidation() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_NETWORK_VALIDATION);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean notifyDataActivityChangedWithSlot() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_NOTIFY_DATA_ACTIVITY_CHANGED_WITH_SLOT);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean oemEnabledSatelliteFlag() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_OEM_ENABLED_SATELLITE_FLAG);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean preventInvocationRepeatOfRilCallWhenDeviceDoesNotSupportVoice() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_PREVENT_INVOCATION_REPEAT_OF_RIL_CALL_WHEN_DEVICE_DOES_NOT_SUPPORT_VOICE);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean preventSystemServerAndPhoneDeadlock() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_PREVENT_SYSTEM_SERVER_AND_PHONE_DEADLOCK);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean reconnectQualifiedNetwork() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_RECONNECT_QUALIFIED_NETWORK);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean refinePreferredDataProfileSelection() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_REFINE_PREFERRED_DATA_PROFILE_SELECTION);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean rejectBadSubIdInteraction() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_REJECT_BAD_SUB_ID_INTERACTION);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean relaxHoTeardown() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_RELAX_HO_TEARDOWN);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean reorganizeRoamingNotification() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_REORGANIZE_ROAMING_NOTIFICATION);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean resetMobileNetworkSettings() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_RESET_MOBILE_NETWORK_SETTINGS);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean showCallFailNotificationFor2gToggle() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_SHOW_CALL_FAIL_NOTIFICATION_FOR_2G_TOGGLE);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean showCallIdAndCallWaitingInAdditionalSettingsMenu() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_SHOW_CALL_ID_AND_CALL_WAITING_IN_ADDITIONAL_SETTINGS_MENU);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean simultaneousCallingIndications() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_SIMULTANEOUS_CALLING_INDICATIONS);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean slicingAdditionalErrorCodes() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_SLICING_ADDITIONAL_ERROR_CODES);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean smsDomainSelectionEnabled() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_SMS_DOMAIN_SELECTION_ENABLED);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean stopSpammingEmergencyNotification() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_STOP_SPAMMING_EMERGENCY_NOTIFICATION);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean subscriptionUserAssociationQuery() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_SUBSCRIPTION_USER_ASSOCIATION_QUERY);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean supportNrSaRrcIdle() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_SUPPORT_NR_SA_RRC_IDLE);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean supportPsimToEsimConversion() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_SUPPORT_PSIM_TO_ESIM_CONVERSION);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean terminateActiveVideoCallWhenAcceptingSecondVideoCallAsAudioOnly() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_TERMINATE_ACTIVE_VIDEO_CALL_WHEN_ACCEPTING_SECOND_VIDEO_CALL_AS_AUDIO_ONLY);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean unthrottleCheckTransport() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_UNTHROTTLE_CHECK_TRANSPORT);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean updateImsServiceByGatheringProvisioningChanges() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_UPDATE_IMS_SERVICE_BY_GATHERING_PROVISIONING_CHANGES);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean updateRoamingStateToSetWfcMode() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_UPDATE_ROAMING_STATE_TO_SET_WFC_MODE);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean useAlarmCallback() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_USE_ALARM_CALLBACK);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean useAospDomainSelectionService() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_USE_AOSP_DOMAIN_SELECTION_SERVICE);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean useOemDomainSelectionService() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_USE_OEM_DOMAIN_SELECTION_SERVICE);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean vonrEnabledMetric() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_VONR_ENABLED_METRIC);
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean workProfileApiSplit() {
        return getValue(com.android.internal.telephony.flags.Flags.FLAG_WORK_PROFILE_API_SPLIT);
    }

    public void setFlag(java.lang.String str, boolean z) {
        if (!this.mFlagMap.containsKey(str)) {
            throw new java.lang.IllegalArgumentException("no such flag " + str);
        }
        this.mFlagMap.put(str, java.lang.Boolean.valueOf(z));
    }

    public void resetAll() {
        java.util.Iterator<java.util.Map.Entry<java.lang.String, java.lang.Boolean>> it = this.mFlagMap.entrySet().iterator();
        while (it.hasNext()) {
            it.next().setValue(null);
        }
    }

    public boolean isFlagReadOnlyOptimized(java.lang.String str) {
        if (this.mReadOnlyFlagsSet.contains(str) && isOptimizationEnabled()) {
            return true;
        }
        return false;
    }

    private boolean getValue(java.lang.String str) {
        java.lang.Boolean bool = this.mFlagMap.get(str);
        if (bool == null) {
            throw new java.lang.IllegalArgumentException(str + " is not set");
        }
        return bool.booleanValue();
    }

    private boolean isOptimizationEnabled() {
        return false;
    }
}
