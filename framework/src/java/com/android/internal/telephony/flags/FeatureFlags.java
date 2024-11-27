package com.android.internal.telephony.flags;

/* loaded from: classes5.dex */
public interface FeatureFlags {
    boolean addAnomalyWhenNotifyConfigChangedWithInvalidPhone();

    boolean addRatRelatedSuggestedActionToImsRegistration();

    boolean allowMmtelInNonVops();

    boolean apDomainSelectionEnabled();

    boolean apnSettingFieldSupportFlag();

    boolean autoDataSwitchAllowRoaming();

    boolean autoDataSwitchRatSs();

    boolean callExtraForNonHoldSupportedCarriers();

    boolean carrierEnabledSatelliteFlag();

    boolean carrierRestrictionRulesEnhancement();

    boolean carrierRestrictionStatus();

    boolean clearCachedImsPhoneNumberWhenDeviceLostImsRegistration();

    boolean conferenceHoldUnholdChangedToSendMessage();

    boolean dataCallSessionStatsCapturesCrossSimCalling();

    boolean dataOnlyCellularService();

    boolean dataOnlyServiceAllowEmergencyCallOnly();

    boolean dismissNetworkSelectionNotificationOnSimDisable();

    boolean doNotOverridePreciseLabel();

    boolean domainSelectionMetricsEnabled();

    boolean dsrsDiagnosticsEnabled();

    boolean emergencyRegistrationState();

    boolean enableAeadAlgorithms();

    boolean enableCarrierConfigN1ControlAttempt2();

    boolean enableIdentifierDisclosureTransparency();

    boolean enableIdentifierDisclosureTransparencyUnsolEvents();

    boolean enableModemCipherTransparency();

    boolean enableModemCipherTransparencyUnsolEvents();

    boolean enableMultipleSaProposals();

    boolean enableTelephonyAnalytics();

    boolean enableWpsCheckApiFlag();

    boolean enforceSubscriptionUserFilter();

    boolean enforceTelephonyFeatureMapping();

    boolean enforceTelephonyFeatureMappingForPublicApis();

    boolean ensureAccessToCallSettingsIsRestricted();

    boolean esimAvailableMemory();

    boolean esimBootstrapProvisioningFlag();

    boolean fixCrashOnGettingConfigWhenPhoneIsGone();

    boolean forceIwlanMms();

    boolean hidePrefer3gItem();

    boolean hideRoamingIcon();

    boolean ignoreAlreadyTerminatedIncomingCallBeforeRegisteringListener();

    boolean ignoreExistingNetworksForInternetAllowedChecking();

    boolean imsiKeyRetryDownloadOnPhoneUnlock();

    boolean logMmsSmsDatabaseAccessInfo();

    boolean meteredEmbbUrlcc();

    boolean minimalTelephonyCdmCheck();

    boolean minimalTelephonyManagersConditionalOnFeatures();

    boolean mmsDisabledError();

    boolean networkRegistrationInfoRejectCause();

    boolean networkValidation();

    boolean notifyDataActivityChangedWithSlot();

    boolean oemEnabledSatelliteFlag();

    boolean preventInvocationRepeatOfRilCallWhenDeviceDoesNotSupportVoice();

    boolean preventSystemServerAndPhoneDeadlock();

    boolean reconnectQualifiedNetwork();

    boolean refinePreferredDataProfileSelection();

    boolean rejectBadSubIdInteraction();

    boolean relaxHoTeardown();

    boolean reorganizeRoamingNotification();

    boolean resetMobileNetworkSettings();

    boolean showCallFailNotificationFor2gToggle();

    boolean showCallIdAndCallWaitingInAdditionalSettingsMenu();

    boolean simultaneousCallingIndications();

    boolean slicingAdditionalErrorCodes();

    boolean smsDomainSelectionEnabled();

    boolean stopSpammingEmergencyNotification();

    boolean subscriptionUserAssociationQuery();

    boolean supportNrSaRrcIdle();

    boolean supportPsimToEsimConversion();

    boolean terminateActiveVideoCallWhenAcceptingSecondVideoCallAsAudioOnly();

    boolean unthrottleCheckTransport();

    boolean updateImsServiceByGatheringProvisioningChanges();

    boolean updateRoamingStateToSetWfcMode();

    boolean useAlarmCallback();

    boolean useAospDomainSelectionService();

    boolean useOemDomainSelectionService();

    boolean vonrEnabledMetric();

    boolean workProfileApiSplit();
}
