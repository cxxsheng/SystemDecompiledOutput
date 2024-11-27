package com.android.internal.telephony.flags;

/* loaded from: classes5.dex */
public final class FeatureFlagsImpl implements com.android.internal.telephony.flags.FeatureFlags {
    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean addAnomalyWhenNotifyConfigChangedWithInvalidPhone() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean addRatRelatedSuggestedActionToImsRegistration() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean allowMmtelInNonVops() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean apDomainSelectionEnabled() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean apnSettingFieldSupportFlag() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean autoDataSwitchAllowRoaming() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean autoDataSwitchRatSs() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean callExtraForNonHoldSupportedCarriers() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean carrierEnabledSatelliteFlag() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean carrierRestrictionRulesEnhancement() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean carrierRestrictionStatus() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean clearCachedImsPhoneNumberWhenDeviceLostImsRegistration() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean conferenceHoldUnholdChangedToSendMessage() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean dataCallSessionStatsCapturesCrossSimCalling() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean dataOnlyCellularService() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean dataOnlyServiceAllowEmergencyCallOnly() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean dismissNetworkSelectionNotificationOnSimDisable() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean doNotOverridePreciseLabel() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean domainSelectionMetricsEnabled() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean dsrsDiagnosticsEnabled() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean emergencyRegistrationState() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean enableAeadAlgorithms() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean enableCarrierConfigN1ControlAttempt2() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean enableIdentifierDisclosureTransparency() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean enableIdentifierDisclosureTransparencyUnsolEvents() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean enableModemCipherTransparency() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean enableModemCipherTransparencyUnsolEvents() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean enableMultipleSaProposals() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean enableTelephonyAnalytics() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean enableWpsCheckApiFlag() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean enforceSubscriptionUserFilter() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean enforceTelephonyFeatureMapping() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean enforceTelephonyFeatureMappingForPublicApis() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean ensureAccessToCallSettingsIsRestricted() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean esimAvailableMemory() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean esimBootstrapProvisioningFlag() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean fixCrashOnGettingConfigWhenPhoneIsGone() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean forceIwlanMms() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean hidePrefer3gItem() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean hideRoamingIcon() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean ignoreAlreadyTerminatedIncomingCallBeforeRegisteringListener() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean ignoreExistingNetworksForInternetAllowedChecking() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean imsiKeyRetryDownloadOnPhoneUnlock() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean logMmsSmsDatabaseAccessInfo() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean meteredEmbbUrlcc() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean minimalTelephonyCdmCheck() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean minimalTelephonyManagersConditionalOnFeatures() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean mmsDisabledError() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean networkRegistrationInfoRejectCause() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean networkValidation() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean notifyDataActivityChangedWithSlot() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean oemEnabledSatelliteFlag() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean preventInvocationRepeatOfRilCallWhenDeviceDoesNotSupportVoice() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean preventSystemServerAndPhoneDeadlock() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean reconnectQualifiedNetwork() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean refinePreferredDataProfileSelection() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean rejectBadSubIdInteraction() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean relaxHoTeardown() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean reorganizeRoamingNotification() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean resetMobileNetworkSettings() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean showCallFailNotificationFor2gToggle() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean showCallIdAndCallWaitingInAdditionalSettingsMenu() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean simultaneousCallingIndications() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean slicingAdditionalErrorCodes() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean smsDomainSelectionEnabled() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean stopSpammingEmergencyNotification() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean subscriptionUserAssociationQuery() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean supportNrSaRrcIdle() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean supportPsimToEsimConversion() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean terminateActiveVideoCallWhenAcceptingSecondVideoCallAsAudioOnly() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean unthrottleCheckTransport() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean updateImsServiceByGatheringProvisioningChanges() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean updateRoamingStateToSetWfcMode() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean useAlarmCallback() {
        return true;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean useAospDomainSelectionService() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean useOemDomainSelectionService() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean vonrEnabledMetric() {
        return false;
    }

    @Override // com.android.internal.telephony.flags.FeatureFlags
    public boolean workProfileApiSplit() {
        return false;
    }
}
