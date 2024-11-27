package com.android.internal.telephony;

/* loaded from: classes5.dex */
public interface ITelephony extends android.os.IInterface {
    void addAttachRestrictionForCarrier(int i, int i2, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException;

    android.telephony.ims.RcsContactUceCapability addUceRegistrationOverrideShell(int i, java.util.List<java.lang.String> list) throws android.os.RemoteException;

    void bootstrapAuthenticationRequest(int i, int i2, android.net.Uri uri, android.telephony.gba.UaSecurityProtocolIdentifier uaSecurityProtocolIdentifier, boolean z, android.telephony.IBootstrapAuthenticationCallback iBootstrapAuthenticationCallback) throws android.os.RemoteException;

    void call(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    boolean canChangeDtmfToneLength(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    boolean canConnectTo5GInDsdsMode() throws android.os.RemoteException;

    void carrierActionReportDefaultNetworkStatus(int i, boolean z) throws android.os.RemoteException;

    void carrierActionResetAll(int i) throws android.os.RemoteException;

    void carrierActionSetRadioEnabled(int i, boolean z) throws android.os.RemoteException;

    int changeIccLockPassword(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    int checkCarrierPrivilegesForPackage(int i, java.lang.String str) throws android.os.RemoteException;

    int checkCarrierPrivilegesForPackageAnyPhone(java.lang.String str) throws android.os.RemoteException;

    boolean clearCarrierImsServiceOverride(int i) throws android.os.RemoteException;

    boolean clearDomainSelectionServiceOverride() throws android.os.RemoteException;

    boolean clearRadioPowerOffForReason(int i, int i2) throws android.os.RemoteException;

    void clearSignalStrengthUpdateRequest(int i, android.telephony.SignalStrengthUpdateRequest signalStrengthUpdateRequest, java.lang.String str) throws android.os.RemoteException;

    android.telephony.ims.RcsContactUceCapability clearUceRegistrationOverrideShell(int i) throws android.os.RemoteException;

    void deprovisionSatelliteService(int i, java.lang.String str, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException;

    void dial(java.lang.String str) throws android.os.RemoteException;

    boolean disableDataConnectivity(java.lang.String str) throws android.os.RemoteException;

    void disableIms(int i) throws android.os.RemoteException;

    void disableLocationUpdates() throws android.os.RemoteException;

    void disableVisualVoicemailSmsFilter(java.lang.String str, int i) throws android.os.RemoteException;

    boolean doesSwitchMultiSimConfigTriggerReboot(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    boolean enableDataConnectivity(java.lang.String str) throws android.os.RemoteException;

    void enableIms(int i) throws android.os.RemoteException;

    void enableLocationUpdates() throws android.os.RemoteException;

    boolean enableModemForSlot(int i, boolean z) throws android.os.RemoteException;

    void enableVideoCalling(boolean z) throws android.os.RemoteException;

    void enableVisualVoicemailSmsFilter(java.lang.String str, int i, android.telephony.VisualVoicemailSmsFilterSettings visualVoicemailSmsFilterSettings) throws android.os.RemoteException;

    void enqueueSmsPickResult(java.lang.String str, java.lang.String str2, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException;

    void factoryReset(int i, java.lang.String str) throws android.os.RemoteException;

    int getActivePhoneType() throws android.os.RemoteException;

    int getActivePhoneTypeForSlot(int i) throws android.os.RemoteException;

    android.telephony.VisualVoicemailSmsFilterSettings getActiveVisualVoicemailSmsFilterSettings(int i) throws android.os.RemoteException;

    java.lang.String getAidForAppType(int i, int i2) throws android.os.RemoteException;

    java.util.List<android.telephony.CellInfo> getAllCellInfo(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    android.telephony.CarrierRestrictionRules getAllowedCarriers() throws android.os.RemoteException;

    int getAllowedNetworkTypesBitmask(int i) throws android.os.RemoteException;

    long getAllowedNetworkTypesForReason(int i, int i2) throws android.os.RemoteException;

    int[] getAttachRestrictionReasonsForCarrier(int i) throws android.os.RemoteException;

    java.lang.String getBoundGbaService(int i) throws android.os.RemoteException;

    java.lang.String getBoundImsServicePackage(int i, boolean z, int i2) throws android.os.RemoteException;

    int getCallComposerStatus(int i) throws android.os.RemoteException;

    void getCallForwarding(int i, int i2, com.android.internal.telephony.ICallForwardingInfoCallback iCallForwardingInfoCallback) throws android.os.RemoteException;

    int getCallState() throws android.os.RemoteException;

    int getCallStateForSubscription(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void getCallWaitingStatus(int i, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException;

    java.lang.String getCapabilityFromEab(java.lang.String str) throws android.os.RemoteException;

    int getCardIdForDefaultEuicc(int i, java.lang.String str) throws android.os.RemoteException;

    int getCarrierIdFromMccMnc(int i, java.lang.String str, boolean z) throws android.os.RemoteException;

    int getCarrierIdListVersion(int i) throws android.os.RemoteException;

    java.util.List<java.lang.String> getCarrierPackageNamesForIntentAndPhone(android.content.Intent intent, int i) throws android.os.RemoteException;

    int getCarrierPrivilegeStatus(int i) throws android.os.RemoteException;

    int getCarrierPrivilegeStatusForUid(int i, int i2) throws android.os.RemoteException;

    void getCarrierRestrictionStatus(com.android.internal.telephony.IIntegerConsumer iIntegerConsumer, java.lang.String str) throws android.os.RemoteException;

    java.lang.String getCarrierServicePackageNameForLogicalSlot(int i) throws android.os.RemoteException;

    boolean getCarrierSingleRegistrationEnabled(int i) throws android.os.RemoteException;

    int getCdmaEriIconIndex(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    int getCdmaEriIconIndexForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    int getCdmaEriIconMode(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    int getCdmaEriIconModeForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    java.lang.String getCdmaEriText(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    java.lang.String getCdmaEriTextForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    java.lang.String getCdmaMdn(int i) throws android.os.RemoteException;

    java.lang.String getCdmaMin(int i) throws android.os.RemoteException;

    java.lang.String getCdmaPrlVersion(int i) throws android.os.RemoteException;

    int getCdmaRoamingMode(int i) throws android.os.RemoteException;

    int getCdmaSubscriptionMode(int i) throws android.os.RemoteException;

    java.util.List<android.telephony.CellBroadcastIdRange> getCellBroadcastIdRanges(int i) throws android.os.RemoteException;

    android.telephony.CellIdentity getCellLocation(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    com.android.internal.telephony.CellNetworkScanResult getCellNetworkScanResults(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    java.util.List<java.lang.String> getCertsFromCarrierPrivilegeAccessRules(int i) throws android.os.RemoteException;

    java.util.List<android.telephony.ClientRequestStats> getClientRequestStats(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException;

    java.lang.String getContactFromEab(java.lang.String str) throws android.os.RemoteException;

    java.lang.String getCurrentPackageName() throws android.os.RemoteException;

    int getDataActivationState(int i, java.lang.String str) throws android.os.RemoteException;

    int getDataActivity() throws android.os.RemoteException;

    int getDataActivityForSubId(int i) throws android.os.RemoteException;

    boolean getDataEnabled(int i) throws android.os.RemoteException;

    int getDataNetworkType(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    int getDataNetworkTypeForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    int getDataState() throws android.os.RemoteException;

    int getDataStateForSubId(int i) throws android.os.RemoteException;

    android.content.ComponentName getDefaultRespondViaMessageApplication(int i, boolean z) throws android.os.RemoteException;

    @java.lang.Deprecated
    java.lang.String getDeviceId(java.lang.String str) throws android.os.RemoteException;

    java.lang.String getDeviceIdWithFeature(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    boolean getDeviceSingleRegistrationEnabled() throws android.os.RemoteException;

    java.lang.String getDeviceSoftwareVersionForSlot(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    boolean getDeviceUceEnabled() throws android.os.RemoteException;

    boolean getEmergencyCallbackMode(int i) throws android.os.RemoteException;

    int getEmergencyNumberDbVersion(int i) throws android.os.RemoteException;

    java.util.Map getEmergencyNumberList(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    java.util.List<java.lang.String> getEmergencyNumberListTestMode() throws android.os.RemoteException;

    java.util.List<java.lang.String> getEquivalentHomePlmns(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    java.lang.String getEsn(int i) throws android.os.RemoteException;

    java.lang.String[] getForbiddenPlmns(int i, int i2, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    int getGbaReleaseTime(int i) throws android.os.RemoteException;

    int getHalVersion(int i) throws android.os.RemoteException;

    java.lang.String getImeiForSlot(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    android.telephony.ims.aidl.IImsConfig getImsConfig(int i, int i2) throws android.os.RemoteException;

    boolean getImsFeatureValidationOverride(int i) throws android.os.RemoteException;

    void getImsMmTelFeatureState(int i, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException;

    void getImsMmTelRegistrationState(int i, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException;

    void getImsMmTelRegistrationTransportType(int i, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException;

    int getImsProvisioningInt(int i, int i2) throws android.os.RemoteException;

    boolean getImsProvisioningStatusForCapability(int i, int i2, int i3) throws android.os.RemoteException;

    java.lang.String getImsProvisioningString(int i, int i2) throws android.os.RemoteException;

    int getImsRegTechnologyForMmTel(int i) throws android.os.RemoteException;

    android.telephony.ims.aidl.IImsRegistration getImsRegistration(int i, int i2) throws android.os.RemoteException;

    android.telephony.CellIdentity getLastKnownCellIdentity(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    java.lang.String getLastUcePidfXmlShell(int i) throws android.os.RemoteException;

    android.telephony.ims.RcsContactUceCapability getLatestRcsContactUceCapabilityShell(int i) throws android.os.RemoteException;

    java.lang.String getLine1AlphaTagForDisplay(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    java.lang.String getLine1NumberForDisplay(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    int getLteOnCdmaMode(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    int getLteOnCdmaModeForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    java.lang.String getManualNetworkSelectionPlmn(int i) throws android.os.RemoteException;

    java.lang.String getManufacturerCodeForSlot(int i) throws android.os.RemoteException;

    java.lang.String getMeidForSlot(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    java.lang.String[] getMergedImsisFromGroup(int i, java.lang.String str) throws android.os.RemoteException;

    java.lang.String[] getMergedSubscriberIds(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    java.lang.String getMmsUAProfUrl(int i) throws android.os.RemoteException;

    java.lang.String getMmsUserAgent(int i) throws android.os.RemoteException;

    java.lang.String getMobileProvisioningUrl() throws android.os.RemoteException;

    java.lang.String getModemService() throws android.os.RemoteException;

    java.util.List<android.telephony.NeighboringCellInfo> getNeighboringCellInfo(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    java.lang.String getNetworkCountryIsoForPhone(int i) throws android.os.RemoteException;

    int getNetworkSelectionMode(int i) throws android.os.RemoteException;

    int getNetworkTypeForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    int getNumberOfModemsWithSimultaneousDataConnections(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    java.util.List<java.lang.String> getPackagesWithCarrierPrivileges(int i) throws android.os.RemoteException;

    java.util.List<java.lang.String> getPackagesWithCarrierPrivilegesForAllPhones() throws android.os.RemoteException;

    android.telecom.PhoneAccountHandle getPhoneAccountHandleForSubscriptionId(int i) throws android.os.RemoteException;

    android.telephony.PhoneCapability getPhoneCapability() throws android.os.RemoteException;

    java.lang.String getPrimaryImei(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    int getRadioAccessFamily(int i, java.lang.String str) throws android.os.RemoteException;

    int getRadioHalVersion() throws android.os.RemoteException;

    java.util.List getRadioPowerOffReasons(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    int getRadioPowerState(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    boolean getRcsProvisioningStatusForCapability(int i, int i2, int i3) throws android.os.RemoteException;

    boolean getRcsSingleRegistrationTestModeEnabled() throws android.os.RemoteException;

    java.util.List<java.lang.String> getSatellitePlmnsForCarrier(int i) throws android.os.RemoteException;

    android.telephony.ServiceState getServiceStateForSubscriber(int i, boolean z, boolean z2, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    java.util.List<java.lang.String> getShaIdFromAllowList(java.lang.String str, int i) throws android.os.RemoteException;

    android.telephony.SignalStrength getSignalStrength(int i) throws android.os.RemoteException;

    java.lang.String getSimLocaleForSubscriber(int i) throws android.os.RemoteException;

    int getSimStateForSlotIndex(int i) throws android.os.RemoteException;

    void getSlicingConfig(android.os.ResultReceiver resultReceiver) throws android.os.RemoteException;

    java.util.List<android.telephony.UiccSlotMapping> getSlotsMapping(java.lang.String str) throws android.os.RemoteException;

    int getSubIdForPhoneAccountHandle(android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    int getSubscriptionCarrierId(int i) throws android.os.RemoteException;

    java.lang.String getSubscriptionCarrierName(int i) throws android.os.RemoteException;

    int getSubscriptionSpecificCarrierId(int i) throws android.os.RemoteException;

    java.lang.String getSubscriptionSpecificCarrierName(int i) throws android.os.RemoteException;

    java.util.List<android.telephony.RadioAccessSpecifier> getSystemSelectionChannels(int i) throws android.os.RemoteException;

    java.util.List<android.telephony.TelephonyHistogram> getTelephonyHistograms() throws android.os.RemoteException;

    java.lang.String getTypeAllocationCodeForSlot(int i) throws android.os.RemoteException;

    java.util.List<android.telephony.UiccCardInfo> getUiccCardsInfo(java.lang.String str) throws android.os.RemoteException;

    android.telephony.UiccSlotInfo[] getUiccSlotsInfo(java.lang.String str) throws android.os.RemoteException;

    java.lang.String getVisualVoicemailPackageName(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException;

    android.os.Bundle getVisualVoicemailSettings(java.lang.String str, int i) throws android.os.RemoteException;

    android.telephony.VisualVoicemailSmsFilterSettings getVisualVoicemailSmsFilterSettings(java.lang.String str, int i) throws android.os.RemoteException;

    int getVoWiFiModeSetting(int i) throws android.os.RemoteException;

    int getVoWiFiRoamingModeSetting(int i) throws android.os.RemoteException;

    int getVoiceActivationState(int i, java.lang.String str) throws android.os.RemoteException;

    int getVoiceMessageCountForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    int getVoiceNetworkTypeForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    android.net.Uri getVoicemailRingtoneUri(android.telecom.PhoneAccountHandle phoneAccountHandle) throws android.os.RemoteException;

    boolean handlePinMmi(java.lang.String str) throws android.os.RemoteException;

    boolean handlePinMmiForSubscriber(int i, java.lang.String str) throws android.os.RemoteException;

    void handleUssdRequest(int i, java.lang.String str, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException;

    boolean hasIccCard() throws android.os.RemoteException;

    boolean hasIccCardUsingSlotIndex(int i) throws android.os.RemoteException;

    boolean iccCloseLogicalChannel(com.android.internal.telephony.IccLogicalChannelRequest iccLogicalChannelRequest) throws android.os.RemoteException;

    byte[] iccExchangeSimIO(int i, int i2, int i3, int i4, int i5, int i6, java.lang.String str) throws android.os.RemoteException;

    android.telephony.IccOpenLogicalChannelResponse iccOpenLogicalChannel(com.android.internal.telephony.IccLogicalChannelRequest iccLogicalChannelRequest) throws android.os.RemoteException;

    java.lang.String iccTransmitApduBasicChannel(int i, java.lang.String str, int i2, int i3, int i4, int i5, int i6, java.lang.String str2) throws android.os.RemoteException;

    java.lang.String iccTransmitApduBasicChannelByPort(int i, int i2, java.lang.String str, int i3, int i4, int i5, int i6, int i7, java.lang.String str2) throws android.os.RemoteException;

    java.lang.String iccTransmitApduLogicalChannel(int i, int i2, int i3, int i4, int i5, int i6, int i7, java.lang.String str) throws android.os.RemoteException;

    java.lang.String iccTransmitApduLogicalChannelByPort(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, java.lang.String str) throws android.os.RemoteException;

    boolean isAdvancedCallingSettingEnabled(int i) throws android.os.RemoteException;

    boolean isApnMetered(int i, int i2) throws android.os.RemoteException;

    boolean isApplicationOnUicc(int i, int i2) throws android.os.RemoteException;

    boolean isAvailable(int i, int i2, int i3) throws android.os.RemoteException;

    boolean isCapable(int i, int i2, int i3) throws android.os.RemoteException;

    boolean isCellularIdentifierDisclosureNotificationsEnabled() throws android.os.RemoteException;

    boolean isConcurrentVoiceAndDataAllowed(int i) throws android.os.RemoteException;

    boolean isCrossSimCallingEnabledByUser(int i) throws android.os.RemoteException;

    boolean isDataConnectivityPossible(int i) throws android.os.RemoteException;

    boolean isDataEnabled(int i) throws android.os.RemoteException;

    boolean isDataEnabledForApn(int i, int i2, java.lang.String str) throws android.os.RemoteException;

    boolean isDataEnabledForReason(int i, int i2) throws android.os.RemoteException;

    boolean isDataRoamingEnabled(int i) throws android.os.RemoteException;

    boolean isDomainSelectionSupported() throws android.os.RemoteException;

    boolean isEmergencyNumber(java.lang.String str, boolean z) throws android.os.RemoteException;

    boolean isHearingAidCompatibilitySupported() throws android.os.RemoteException;

    boolean isIccLockEnabled(int i) throws android.os.RemoteException;

    boolean isImsRegistered(int i) throws android.os.RemoteException;

    boolean isInEmergencySmsMode() throws android.os.RemoteException;

    boolean isManualNetworkSelectionAllowed(int i) throws android.os.RemoteException;

    void isMmTelCapabilitySupported(int i, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer, int i2, int i3) throws android.os.RemoteException;

    boolean isMobileDataPolicyEnabled(int i, int i2) throws android.os.RemoteException;

    boolean isModemEnabledForSlot(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    int isMultiSimSupported(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    boolean isMvnoMatched(int i, int i2, java.lang.String str) throws android.os.RemoteException;

    boolean isNrDualConnectivityEnabled(int i) throws android.os.RemoteException;

    boolean isNullCipherAndIntegrityPreferenceEnabled() throws android.os.RemoteException;

    boolean isNullCipherNotificationsEnabled() throws android.os.RemoteException;

    boolean isPremiumCapabilityAvailableForPurchase(int i, int i2) throws android.os.RemoteException;

    boolean isProvisioningRequiredForCapability(int i, int i2, int i3) throws android.os.RemoteException;

    boolean isRadioInterfaceCapabilitySupported(java.lang.String str) throws android.os.RemoteException;

    @java.lang.Deprecated
    boolean isRadioOn(java.lang.String str) throws android.os.RemoteException;

    @java.lang.Deprecated
    boolean isRadioOnForSubscriber(int i, java.lang.String str) throws android.os.RemoteException;

    boolean isRadioOnForSubscriberWithFeature(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    boolean isRadioOnWithFeature(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    boolean isRcsProvisioningRequiredForCapability(int i, int i2, int i3) throws android.os.RemoteException;

    boolean isRcsVolteSingleRegistrationCapable(int i) throws android.os.RemoteException;

    boolean isRemovableEsimDefaultEuicc(java.lang.String str) throws android.os.RemoteException;

    boolean isRttSupported(int i) throws android.os.RemoteException;

    boolean isTetheringApnRequiredForSubscriber(int i) throws android.os.RemoteException;

    boolean isTtyModeSupported() throws android.os.RemoteException;

    boolean isTtyOverVolteEnabled(int i) throws android.os.RemoteException;

    boolean isUserDataEnabled(int i) throws android.os.RemoteException;

    boolean isVideoCallingEnabled(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    boolean isVideoTelephonyAvailable(int i) throws android.os.RemoteException;

    boolean isVoNrEnabled(int i) throws android.os.RemoteException;

    boolean isVoWiFiRoamingSettingEnabled(int i) throws android.os.RemoteException;

    boolean isVoWiFiSettingEnabled(int i) throws android.os.RemoteException;

    boolean isVoicemailVibrationEnabled(android.telecom.PhoneAccountHandle phoneAccountHandle) throws android.os.RemoteException;

    boolean isVtSettingEnabled(int i) throws android.os.RemoteException;

    boolean isWifiCallingAvailable(int i) throws android.os.RemoteException;

    boolean isWorldPhone(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    boolean needMobileRadioShutdown() throws android.os.RemoteException;

    boolean needsOtaServiceProvisioning() throws android.os.RemoteException;

    void notifyOtaEmergencyNumberDbInstalled() throws android.os.RemoteException;

    void notifyRcsAutoConfigurationReceived(int i, byte[] bArr, boolean z) throws android.os.RemoteException;

    java.lang.String nvReadItem(int i) throws android.os.RemoteException;

    boolean nvWriteCdmaPrl(byte[] bArr) throws android.os.RemoteException;

    boolean nvWriteItem(int i, java.lang.String str) throws android.os.RemoteException;

    void persistEmergencyCallDiagnosticData(java.lang.String str, boolean z, long j, boolean z2, boolean z3) throws android.os.RemoteException;

    void pollPendingDatagrams(int i, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException;

    int prepareForUnattendedReboot() throws android.os.RemoteException;

    android.os.ICancellationSignal provisionSatelliteService(int i, java.lang.String str, byte[] bArr, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException;

    void purchasePremiumCapability(int i, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer, int i2) throws android.os.RemoteException;

    boolean rebootModem(int i) throws android.os.RemoteException;

    void refreshUiccProfile(int i) throws android.os.RemoteException;

    void registerFeatureProvisioningChangedCallback(int i, android.telephony.ims.aidl.IFeatureProvisioningCallback iFeatureProvisioningCallback) throws android.os.RemoteException;

    int registerForCapabilitiesChanged(int i, android.telephony.satellite.ISatelliteCapabilitiesCallback iSatelliteCapabilitiesCallback) throws android.os.RemoteException;

    int registerForIncomingDatagram(int i, android.telephony.satellite.ISatelliteDatagramCallback iSatelliteDatagramCallback) throws android.os.RemoteException;

    void registerForNtnSignalStrengthChanged(int i, android.telephony.satellite.INtnSignalStrengthCallback iNtnSignalStrengthCallback) throws android.os.RemoteException;

    int registerForSatelliteModemStateChanged(int i, android.telephony.satellite.ISatelliteModemStateCallback iSatelliteModemStateCallback) throws android.os.RemoteException;

    int registerForSatelliteProvisionStateChanged(int i, android.telephony.satellite.ISatelliteProvisionStateCallback iSatelliteProvisionStateCallback) throws android.os.RemoteException;

    void registerImsEmergencyRegistrationCallback(int i, android.telephony.ims.aidl.IImsRegistrationCallback iImsRegistrationCallback) throws android.os.RemoteException;

    void registerImsProvisioningChangedCallback(int i, android.telephony.ims.aidl.IImsConfigCallback iImsConfigCallback) throws android.os.RemoteException;

    void registerImsRegistrationCallback(int i, android.telephony.ims.aidl.IImsRegistrationCallback iImsRegistrationCallback) throws android.os.RemoteException;

    void registerImsStateCallback(int i, int i2, com.android.internal.telephony.IImsStateCallback iImsStateCallback, java.lang.String str) throws android.os.RemoteException;

    void registerMmTelCapabilityCallback(int i, android.telephony.ims.aidl.IImsCapabilityCallback iImsCapabilityCallback) throws android.os.RemoteException;

    void registerMmTelFeatureCallback(int i, com.android.ims.internal.IImsServiceFeatureCallback iImsServiceFeatureCallback) throws android.os.RemoteException;

    void registerRcsProvisioningCallback(int i, android.telephony.ims.aidl.IRcsConfigCallback iRcsConfigCallback) throws android.os.RemoteException;

    void removeAttachRestrictionForCarrier(int i, int i2, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException;

    int removeContactFromEab(int i, java.lang.String str) throws android.os.RemoteException;

    android.telephony.ims.RcsContactUceCapability removeUceRegistrationOverrideShell(int i, java.util.List<java.lang.String> list) throws android.os.RemoteException;

    boolean removeUceRequestDisallowedStatus(int i) throws android.os.RemoteException;

    void requestCellInfoUpdate(int i, android.telephony.ICellInfoCallback iCellInfoCallback, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void requestCellInfoUpdateWithWorkSource(int i, android.telephony.ICellInfoCallback iCellInfoCallback, java.lang.String str, java.lang.String str2, android.os.WorkSource workSource) throws android.os.RemoteException;

    void requestIsCommunicationAllowedForCurrentLocation(int i, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException;

    void requestIsDemoModeEnabled(int i, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException;

    void requestIsEmergencyModeEnabled(int i, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException;

    void requestIsSatelliteEnabled(int i, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException;

    void requestIsSatelliteProvisioned(int i, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException;

    void requestIsSatelliteSupported(int i, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException;

    void requestModemActivityInfo(android.os.ResultReceiver resultReceiver) throws android.os.RemoteException;

    int requestNetworkScan(int i, boolean z, android.telephony.NetworkScanRequest networkScanRequest, android.os.Messenger messenger, android.os.IBinder iBinder, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void requestNtnSignalStrength(int i, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException;

    void requestNumberVerification(android.telephony.PhoneNumberRange phoneNumberRange, long j, com.android.internal.telephony.INumberVerificationCallback iNumberVerificationCallback, java.lang.String str) throws android.os.RemoteException;

    boolean requestRadioPowerOffForReason(int i, int i2) throws android.os.RemoteException;

    void requestSatelliteCapabilities(int i, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException;

    void requestSatelliteEnabled(int i, boolean z, boolean z2, boolean z3, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException;

    void requestTimeForNextSatelliteVisibility(int i, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException;

    void requestUserActivityNotification() throws android.os.RemoteException;

    void resetIms(int i) throws android.os.RemoteException;

    boolean resetModemConfig(int i) throws android.os.RemoteException;

    void resetOtaEmergencyNumberDbFilePath() throws android.os.RemoteException;

    void sendDatagram(int i, int i2, android.telephony.satellite.SatelliteDatagram satelliteDatagram, boolean z, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException;

    void sendDeviceToDeviceMessage(int i, int i2) throws android.os.RemoteException;

    void sendDialerSpecialCode(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    java.lang.String sendEnvelopeWithStatus(int i, java.lang.String str) throws android.os.RemoteException;

    int sendThermalMitigationRequest(int i, android.telephony.ThermalMitigationRequest thermalMitigationRequest, java.lang.String str) throws android.os.RemoteException;

    void sendVisualVoicemailSmsForSubscriber(java.lang.String str, java.lang.String str2, int i, java.lang.String str3, int i2, java.lang.String str4, android.app.PendingIntent pendingIntent) throws android.os.RemoteException;

    void setActiveDeviceToDeviceTransport(java.lang.String str) throws android.os.RemoteException;

    void setAdvancedCallingSettingEnabled(int i, boolean z) throws android.os.RemoteException;

    int setAllowedCarriers(android.telephony.CarrierRestrictionRules carrierRestrictionRules) throws android.os.RemoteException;

    boolean setAllowedNetworkTypesForReason(int i, int i2, long j) throws android.os.RemoteException;

    boolean setBoundGbaServiceOverride(int i, java.lang.String str) throws android.os.RemoteException;

    boolean setBoundImsServiceOverride(int i, boolean z, int[] iArr, java.lang.String str) throws android.os.RemoteException;

    void setCallComposerStatus(int i, int i2) throws android.os.RemoteException;

    void setCallForwarding(int i, android.telephony.CallForwardingInfo callForwardingInfo, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException;

    void setCallWaitingStatus(int i, boolean z, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException;

    boolean setCapabilitiesRequestTimeout(int i, long j) throws android.os.RemoteException;

    void setCarrierServicePackageOverride(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    boolean setCarrierSingleRegistrationEnabledOverride(int i, java.lang.String str) throws android.os.RemoteException;

    void setCarrierTestOverride(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.lang.String str5, java.lang.String str6, java.lang.String str7, java.lang.String str8, java.lang.String str9) throws android.os.RemoteException;

    boolean setCdmaRoamingMode(int i, int i2) throws android.os.RemoteException;

    boolean setCdmaSubscriptionMode(int i, int i2) throws android.os.RemoteException;

    void setCellBroadcastIdRanges(int i, java.util.List<android.telephony.CellBroadcastIdRange> list, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException;

    void setCellInfoListRate(int i, int i2) throws android.os.RemoteException;

    void setCepEnabled(boolean z) throws android.os.RemoteException;

    boolean setCountryCodes(boolean z, java.util.List<java.lang.String> list, java.util.Map map, java.lang.String str, long j) throws android.os.RemoteException;

    void setCrossSimCallingEnabled(int i, boolean z) throws android.os.RemoteException;

    void setDataActivationState(int i, int i2) throws android.os.RemoteException;

    void setDataEnabledForReason(int i, int i2, boolean z, java.lang.String str) throws android.os.RemoteException;

    void setDataRoamingEnabled(int i, boolean z) throws android.os.RemoteException;

    boolean setDatagramControllerTimeoutDuration(boolean z, int i, long j) throws android.os.RemoteException;

    void setDeviceAlignedWithSatellite(int i, boolean z) throws android.os.RemoteException;

    void setDeviceSingleRegistrationEnabledOverride(java.lang.String str) throws android.os.RemoteException;

    void setDeviceToDeviceForceEnabled(boolean z) throws android.os.RemoteException;

    void setDeviceUceEnabled(boolean z) throws android.os.RemoteException;

    boolean setDomainSelectionServiceOverride(android.content.ComponentName componentName) throws android.os.RemoteException;

    boolean setEmergencyCallToSatelliteHandoverType(int i, int i2) throws android.os.RemoteException;

    void setEnableCellularIdentifierDisclosureNotifications(boolean z) throws android.os.RemoteException;

    int setForbiddenPlmns(int i, int i2, java.util.List<java.lang.String> list, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    boolean setGbaReleaseTimeOverride(int i, int i2) throws android.os.RemoteException;

    int setIccLockEnabled(int i, boolean z, java.lang.String str) throws android.os.RemoteException;

    boolean setImsFeatureValidationOverride(int i, java.lang.String str) throws android.os.RemoteException;

    int setImsProvisioningInt(int i, int i2, int i3) throws android.os.RemoteException;

    void setImsProvisioningStatusForCapability(int i, int i2, int i3, boolean z) throws android.os.RemoteException;

    int setImsProvisioningString(int i, int i2, java.lang.String str) throws android.os.RemoteException;

    void setImsRegistrationState(boolean z) throws android.os.RemoteException;

    boolean setLine1NumberForDisplayForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void setMobileDataPolicyEnabled(int i, int i2, boolean z) throws android.os.RemoteException;

    boolean setModemService(java.lang.String str) throws android.os.RemoteException;

    void setMultiSimCarrierRestriction(boolean z) throws android.os.RemoteException;

    void setNetworkSelectionModeAutomatic(int i) throws android.os.RemoteException;

    boolean setNetworkSelectionModeManual(int i, com.android.internal.telephony.OperatorInfo operatorInfo, boolean z) throws android.os.RemoteException;

    int setNrDualConnectivityState(int i, int i2) throws android.os.RemoteException;

    void setNullCipherAndIntegrityEnabled(boolean z) throws android.os.RemoteException;

    void setNullCipherNotificationsEnabled(boolean z) throws android.os.RemoteException;

    boolean setOemEnabledSatelliteProvisionStatus(boolean z, boolean z2) throws android.os.RemoteException;

    boolean setOperatorBrandOverride(int i, java.lang.String str) throws android.os.RemoteException;

    boolean setRadio(boolean z) throws android.os.RemoteException;

    boolean setRadioForSubscriber(int i, boolean z) throws android.os.RemoteException;

    boolean setRadioPower(boolean z) throws android.os.RemoteException;

    void setRcsClientConfiguration(int i, android.telephony.ims.RcsClientConfiguration rcsClientConfiguration) throws android.os.RemoteException;

    void setRcsProvisioningStatusForCapability(int i, int i2, int i3, boolean z) throws android.os.RemoteException;

    void setRcsSingleRegistrationTestModeEnabled(boolean z) throws android.os.RemoteException;

    void setRemovableEsimAsDefaultEuicc(boolean z, java.lang.String str) throws android.os.RemoteException;

    boolean setRoamingOverride(int i, java.util.List<java.lang.String> list, java.util.List<java.lang.String> list2, java.util.List<java.lang.String> list3, java.util.List<java.lang.String> list4) throws android.os.RemoteException;

    void setRttCapabilitySetting(int i, boolean z) throws android.os.RemoteException;

    boolean setSatelliteAccessControlOverlayConfigs(boolean z, boolean z2, java.lang.String str, long j, java.util.List<java.lang.String> list) throws android.os.RemoteException;

    boolean setSatelliteControllerTimeoutDuration(boolean z, int i, long j) throws android.os.RemoteException;

    boolean setSatelliteGatewayServicePackageName(java.lang.String str) throws android.os.RemoteException;

    boolean setSatelliteListeningTimeoutDuration(long j) throws android.os.RemoteException;

    boolean setSatellitePointingUiClassName(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    boolean setSatelliteServicePackageName(java.lang.String str) throws android.os.RemoteException;

    boolean setShouldSendDatagramToModemInDemoMode(boolean z) throws android.os.RemoteException;

    void setSignalStrengthUpdateRequest(int i, android.telephony.SignalStrengthUpdateRequest signalStrengthUpdateRequest, java.lang.String str) throws android.os.RemoteException;

    void setSimPowerStateForSlot(int i, int i2) throws android.os.RemoteException;

    void setSimPowerStateForSlotWithCallback(int i, int i2, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException;

    boolean setSimSlotMapping(java.util.List<android.telephony.UiccSlotMapping> list) throws android.os.RemoteException;

    void setSystemSelectionChannels(java.util.List<android.telephony.RadioAccessSpecifier> list, int i, com.android.internal.telephony.IBooleanConsumer iBooleanConsumer) throws android.os.RemoteException;

    int setVoNrEnabled(int i, boolean z) throws android.os.RemoteException;

    void setVoWiFiModeSetting(int i, int i2) throws android.os.RemoteException;

    void setVoWiFiNonPersistent(int i, boolean z, int i2) throws android.os.RemoteException;

    void setVoWiFiRoamingModeSetting(int i, int i2) throws android.os.RemoteException;

    void setVoWiFiRoamingSettingEnabled(int i, boolean z) throws android.os.RemoteException;

    void setVoWiFiSettingEnabled(int i, boolean z) throws android.os.RemoteException;

    void setVoiceActivationState(int i, int i2) throws android.os.RemoteException;

    boolean setVoiceMailNumber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void setVoiceServiceStateOverride(int i, boolean z, java.lang.String str) throws android.os.RemoteException;

    void setVoicemailRingtoneUri(java.lang.String str, android.telecom.PhoneAccountHandle phoneAccountHandle, android.net.Uri uri) throws android.os.RemoteException;

    void setVoicemailVibrationEnabled(java.lang.String str, android.telecom.PhoneAccountHandle phoneAccountHandle, boolean z) throws android.os.RemoteException;

    void setVtSettingEnabled(int i, boolean z) throws android.os.RemoteException;

    void showSwitchToManagedProfileDialog() throws android.os.RemoteException;

    void shutdownMobileRadios() throws android.os.RemoteException;

    void startEmergencyCallbackMode() throws android.os.RemoteException;

    void startSatelliteTransmissionUpdates(int i, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer, android.telephony.satellite.ISatelliteTransmissionUpdateCallback iSatelliteTransmissionUpdateCallback) throws android.os.RemoteException;

    void stopNetworkScan(int i, int i2) throws android.os.RemoteException;

    void stopSatelliteTransmissionUpdates(int i, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer, android.telephony.satellite.ISatelliteTransmissionUpdateCallback iSatelliteTransmissionUpdateCallback) throws android.os.RemoteException;

    boolean supplyPinForSubscriber(int i, java.lang.String str) throws android.os.RemoteException;

    int[] supplyPinReportResultForSubscriber(int i, java.lang.String str) throws android.os.RemoteException;

    boolean supplyPukForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    int[] supplyPukReportResultForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void switchMultiSimConfig(int i) throws android.os.RemoteException;

    @java.lang.Deprecated
    boolean switchSlots(int[] iArr) throws android.os.RemoteException;

    void toggleRadioOnOff() throws android.os.RemoteException;

    void toggleRadioOnOffForSubscriber(int i) throws android.os.RemoteException;

    void triggerRcsReconfiguration(int i) throws android.os.RemoteException;

    void unregisterFeatureProvisioningChangedCallback(int i, android.telephony.ims.aidl.IFeatureProvisioningCallback iFeatureProvisioningCallback) throws android.os.RemoteException;

    void unregisterForCapabilitiesChanged(int i, android.telephony.satellite.ISatelliteCapabilitiesCallback iSatelliteCapabilitiesCallback) throws android.os.RemoteException;

    void unregisterForIncomingDatagram(int i, android.telephony.satellite.ISatelliteDatagramCallback iSatelliteDatagramCallback) throws android.os.RemoteException;

    void unregisterForModemStateChanged(int i, android.telephony.satellite.ISatelliteModemStateCallback iSatelliteModemStateCallback) throws android.os.RemoteException;

    void unregisterForNtnSignalStrengthChanged(int i, android.telephony.satellite.INtnSignalStrengthCallback iNtnSignalStrengthCallback) throws android.os.RemoteException;

    void unregisterForSatelliteProvisionStateChanged(int i, android.telephony.satellite.ISatelliteProvisionStateCallback iSatelliteProvisionStateCallback) throws android.os.RemoteException;

    void unregisterImsEmergencyRegistrationCallback(int i, android.telephony.ims.aidl.IImsRegistrationCallback iImsRegistrationCallback) throws android.os.RemoteException;

    void unregisterImsFeatureCallback(com.android.ims.internal.IImsServiceFeatureCallback iImsServiceFeatureCallback) throws android.os.RemoteException;

    void unregisterImsProvisioningChangedCallback(int i, android.telephony.ims.aidl.IImsConfigCallback iImsConfigCallback) throws android.os.RemoteException;

    void unregisterImsRegistrationCallback(int i, android.telephony.ims.aidl.IImsRegistrationCallback iImsRegistrationCallback) throws android.os.RemoteException;

    void unregisterImsStateCallback(com.android.internal.telephony.IImsStateCallback iImsStateCallback) throws android.os.RemoteException;

    void unregisterMmTelCapabilityCallback(int i, android.telephony.ims.aidl.IImsCapabilityCallback iImsCapabilityCallback) throws android.os.RemoteException;

    void unregisterRcsProvisioningCallback(int i, android.telephony.ims.aidl.IRcsConfigCallback iRcsConfigCallback) throws android.os.RemoteException;

    void updateEmergencyNumberListTestMode(int i, android.telephony.emergency.EmergencyNumber emergencyNumber) throws android.os.RemoteException;

    void updateOtaEmergencyNumberDbFilePath(android.os.ParcelFileDescriptor parcelFileDescriptor) throws android.os.RemoteException;

    void updateServiceLocation() throws android.os.RemoteException;

    void updateServiceLocationWithPackageName(java.lang.String str) throws android.os.RemoteException;

    void uploadCallComposerPicture(int i, java.lang.String str, java.lang.String str2, android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException;

    void userActivity() throws android.os.RemoteException;

    public static class Default implements com.android.internal.telephony.ITelephony {
        @Override // com.android.internal.telephony.ITelephony
        public void dial(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void call(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isRadioOn(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isRadioOnWithFeature(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isRadioOnForSubscriber(int i, java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isRadioOnForSubscriberWithFeature(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setCallComposerStatus(int i, int i2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getCallComposerStatus(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean supplyPinForSubscriber(int i, java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean supplyPukForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int[] supplyPinReportResultForSubscriber(int i, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int[] supplyPukReportResultForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean handlePinMmi(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void handleUssdRequest(int i, java.lang.String str, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean handlePinMmiForSubscriber(int i, java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void toggleRadioOnOff() throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void toggleRadioOnOffForSubscriber(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setRadio(boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setRadioForSubscriber(int i, boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setRadioPower(boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean requestRadioPowerOffForReason(int i, int i2) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean clearRadioPowerOffForReason(int i, int i2) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.util.List getRadioPowerOffReasons(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void updateServiceLocation() throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void updateServiceLocationWithPackageName(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void enableLocationUpdates() throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void disableLocationUpdates() throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean enableDataConnectivity(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean disableDataConnectivity(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isDataConnectivityPossible(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public android.telephony.CellIdentity getCellLocation(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.lang.String getNetworkCountryIsoForPhone(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.util.List<android.telephony.NeighboringCellInfo> getNeighboringCellInfo(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getCallState() throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getCallStateForSubscription(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getDataActivity() throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getDataActivityForSubId(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getDataState() throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getDataStateForSubId(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getActivePhoneType() throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getActivePhoneTypeForSlot(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getCdmaEriIconIndex(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getCdmaEriIconIndexForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getCdmaEriIconMode(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getCdmaEriIconModeForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.lang.String getCdmaEriText(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.lang.String getCdmaEriTextForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean needsOtaServiceProvisioning() throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setVoiceMailNumber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setVoiceActivationState(int i, int i2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setDataActivationState(int i, int i2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getVoiceActivationState(int i, java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getDataActivationState(int i, java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getVoiceMessageCountForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isConcurrentVoiceAndDataAllowed(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public android.os.Bundle getVisualVoicemailSettings(java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.lang.String getVisualVoicemailPackageName(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void enableVisualVoicemailSmsFilter(java.lang.String str, int i, android.telephony.VisualVoicemailSmsFilterSettings visualVoicemailSmsFilterSettings) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void disableVisualVoicemailSmsFilter(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public android.telephony.VisualVoicemailSmsFilterSettings getVisualVoicemailSmsFilterSettings(java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public android.telephony.VisualVoicemailSmsFilterSettings getActiveVisualVoicemailSmsFilterSettings(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void sendVisualVoicemailSmsForSubscriber(java.lang.String str, java.lang.String str2, int i, java.lang.String str3, int i2, java.lang.String str4, android.app.PendingIntent pendingIntent) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void sendDialerSpecialCode(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getNetworkTypeForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getDataNetworkType(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getDataNetworkTypeForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getVoiceNetworkTypeForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean hasIccCard() throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean hasIccCardUsingSlotIndex(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getLteOnCdmaMode(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getLteOnCdmaModeForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.util.List<android.telephony.CellInfo> getAllCellInfo(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void requestCellInfoUpdate(int i, android.telephony.ICellInfoCallback iCellInfoCallback, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void requestCellInfoUpdateWithWorkSource(int i, android.telephony.ICellInfoCallback iCellInfoCallback, java.lang.String str, java.lang.String str2, android.os.WorkSource workSource) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setCellInfoListRate(int i, int i2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public android.telephony.IccOpenLogicalChannelResponse iccOpenLogicalChannel(com.android.internal.telephony.IccLogicalChannelRequest iccLogicalChannelRequest) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean iccCloseLogicalChannel(com.android.internal.telephony.IccLogicalChannelRequest iccLogicalChannelRequest) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.lang.String iccTransmitApduLogicalChannelByPort(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.lang.String iccTransmitApduLogicalChannel(int i, int i2, int i3, int i4, int i5, int i6, int i7, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.lang.String iccTransmitApduBasicChannelByPort(int i, int i2, java.lang.String str, int i3, int i4, int i5, int i6, int i7, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.lang.String iccTransmitApduBasicChannel(int i, java.lang.String str, int i2, int i3, int i4, int i5, int i6, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public byte[] iccExchangeSimIO(int i, int i2, int i3, int i4, int i5, int i6, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.lang.String sendEnvelopeWithStatus(int i, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.lang.String nvReadItem(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean nvWriteItem(int i, java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean nvWriteCdmaPrl(byte[] bArr) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean resetModemConfig(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean rebootModem(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getAllowedNetworkTypesBitmask(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isTetheringApnRequiredForSubscriber(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void enableIms(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void disableIms(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void resetIms(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void registerMmTelFeatureCallback(int i, com.android.ims.internal.IImsServiceFeatureCallback iImsServiceFeatureCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void unregisterImsFeatureCallback(com.android.ims.internal.IImsServiceFeatureCallback iImsServiceFeatureCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public android.telephony.ims.aidl.IImsRegistration getImsRegistration(int i, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public android.telephony.ims.aidl.IImsConfig getImsConfig(int i, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setBoundImsServiceOverride(int i, boolean z, int[] iArr, java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean clearCarrierImsServiceOverride(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.lang.String getBoundImsServicePackage(int i, boolean z, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void getImsMmTelFeatureState(int i, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setNetworkSelectionModeAutomatic(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public com.android.internal.telephony.CellNetworkScanResult getCellNetworkScanResults(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int requestNetworkScan(int i, boolean z, android.telephony.NetworkScanRequest networkScanRequest, android.os.Messenger messenger, android.os.IBinder iBinder, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void stopNetworkScan(int i, int i2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setNetworkSelectionModeManual(int i, com.android.internal.telephony.OperatorInfo operatorInfo, boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public long getAllowedNetworkTypesForReason(int i, int i2) throws android.os.RemoteException {
            return 0L;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setAllowedNetworkTypesForReason(int i, int i2, long j) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean getDataEnabled(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isUserDataEnabled(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isDataEnabled(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setDataEnabledForReason(int i, int i2, boolean z, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isDataEnabledForReason(int i, int i2) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isManualNetworkSelectionAllowed(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setImsRegistrationState(boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.lang.String getCdmaMdn(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.lang.String getCdmaMin(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void requestNumberVerification(android.telephony.PhoneNumberRange phoneNumberRange, long j, com.android.internal.telephony.INumberVerificationCallback iNumberVerificationCallback, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getCarrierPrivilegeStatus(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getCarrierPrivilegeStatusForUid(int i, int i2) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int checkCarrierPrivilegesForPackage(int i, java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int checkCarrierPrivilegesForPackageAnyPhone(java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.util.List<java.lang.String> getCarrierPackageNamesForIntentAndPhone(android.content.Intent intent, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setLine1NumberForDisplayForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.lang.String getLine1NumberForDisplay(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.lang.String getLine1AlphaTagForDisplay(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.lang.String[] getMergedSubscriberIds(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.lang.String[] getMergedImsisFromGroup(int i, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setOperatorBrandOverride(int i, java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setRoamingOverride(int i, java.util.List<java.lang.String> list, java.util.List<java.lang.String> list2, java.util.List<java.lang.String> list3, java.util.List<java.lang.String> list4) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean needMobileRadioShutdown() throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void shutdownMobileRadios() throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getRadioAccessFamily(int i, java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void uploadCallComposerPicture(int i, java.lang.String str, java.lang.String str2, android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void enableVideoCalling(boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isVideoCallingEnabled(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean canChangeDtmfToneLength(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isWorldPhone(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isTtyModeSupported() throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isRttSupported(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isHearingAidCompatibilitySupported() throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isImsRegistered(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isWifiCallingAvailable(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isVideoTelephonyAvailable(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getImsRegTechnologyForMmTel(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.lang.String getDeviceId(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.lang.String getDeviceIdWithFeature(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.lang.String getImeiForSlot(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.lang.String getPrimaryImei(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.lang.String getTypeAllocationCodeForSlot(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.lang.String getMeidForSlot(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.lang.String getManufacturerCodeForSlot(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.lang.String getDeviceSoftwareVersionForSlot(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getSubIdForPhoneAccountHandle(android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public android.telecom.PhoneAccountHandle getPhoneAccountHandleForSubscriptionId(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void factoryReset(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.lang.String getSimLocaleForSubscriber(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void requestModemActivityInfo(android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public android.telephony.ServiceState getServiceStateForSubscriber(int i, boolean z, boolean z2, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public android.net.Uri getVoicemailRingtoneUri(android.telecom.PhoneAccountHandle phoneAccountHandle) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setVoicemailRingtoneUri(java.lang.String str, android.telecom.PhoneAccountHandle phoneAccountHandle, android.net.Uri uri) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isVoicemailVibrationEnabled(android.telecom.PhoneAccountHandle phoneAccountHandle) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setVoicemailVibrationEnabled(java.lang.String str, android.telecom.PhoneAccountHandle phoneAccountHandle, boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.util.List<java.lang.String> getPackagesWithCarrierPrivileges(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.util.List<java.lang.String> getPackagesWithCarrierPrivilegesForAllPhones() throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.lang.String getAidForAppType(int i, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.lang.String getEsn(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.lang.String getCdmaPrlVersion(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.util.List<android.telephony.TelephonyHistogram> getTelephonyHistograms() throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int setAllowedCarriers(android.telephony.CarrierRestrictionRules carrierRestrictionRules) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public android.telephony.CarrierRestrictionRules getAllowedCarriers() throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getSubscriptionCarrierId(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.lang.String getSubscriptionCarrierName(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getSubscriptionSpecificCarrierId(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.lang.String getSubscriptionSpecificCarrierName(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getCarrierIdFromMccMnc(int i, java.lang.String str, boolean z) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void carrierActionSetRadioEnabled(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void carrierActionReportDefaultNetworkStatus(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void carrierActionResetAll(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void getCallForwarding(int i, int i2, com.android.internal.telephony.ICallForwardingInfoCallback iCallForwardingInfoCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setCallForwarding(int i, android.telephony.CallForwardingInfo callForwardingInfo, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void getCallWaitingStatus(int i, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setCallWaitingStatus(int i, boolean z, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.util.List<android.telephony.ClientRequestStats> getClientRequestStats(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setSimPowerStateForSlot(int i, int i2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setSimPowerStateForSlotWithCallback(int i, int i2, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.lang.String[] getForbiddenPlmns(int i, int i2, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int setForbiddenPlmns(int i, int i2, java.util.List<java.lang.String> list, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean getEmergencyCallbackMode(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public android.telephony.SignalStrength getSignalStrength(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getCardIdForDefaultEuicc(int i, java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.util.List<android.telephony.UiccCardInfo> getUiccCardsInfo(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public android.telephony.UiccSlotInfo[] getUiccSlotsInfo(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean switchSlots(int[] iArr) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setSimSlotMapping(java.util.List<android.telephony.UiccSlotMapping> list) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isDataRoamingEnabled(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setDataRoamingEnabled(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getCdmaRoamingMode(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setCdmaRoamingMode(int i, int i2) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getCdmaSubscriptionMode(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setCdmaSubscriptionMode(int i, int i2) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setCarrierTestOverride(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.lang.String str5, java.lang.String str6, java.lang.String str7, java.lang.String str8, java.lang.String str9) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setCarrierServicePackageOverride(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getCarrierIdListVersion(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void refreshUiccProfile(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getNumberOfModemsWithSimultaneousDataConnections(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getNetworkSelectionMode(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isInEmergencySmsMode() throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getRadioPowerState(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void registerImsRegistrationCallback(int i, android.telephony.ims.aidl.IImsRegistrationCallback iImsRegistrationCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void unregisterImsRegistrationCallback(int i, android.telephony.ims.aidl.IImsRegistrationCallback iImsRegistrationCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void registerImsEmergencyRegistrationCallback(int i, android.telephony.ims.aidl.IImsRegistrationCallback iImsRegistrationCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void unregisterImsEmergencyRegistrationCallback(int i, android.telephony.ims.aidl.IImsRegistrationCallback iImsRegistrationCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void getImsMmTelRegistrationState(int i, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void getImsMmTelRegistrationTransportType(int i, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void registerMmTelCapabilityCallback(int i, android.telephony.ims.aidl.IImsCapabilityCallback iImsCapabilityCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void unregisterMmTelCapabilityCallback(int i, android.telephony.ims.aidl.IImsCapabilityCallback iImsCapabilityCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isCapable(int i, int i2, int i3) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isAvailable(int i, int i2, int i3) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void isMmTelCapabilitySupported(int i, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isAdvancedCallingSettingEnabled(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setAdvancedCallingSettingEnabled(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isVtSettingEnabled(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setVtSettingEnabled(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isVoWiFiSettingEnabled(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setVoWiFiSettingEnabled(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isCrossSimCallingEnabledByUser(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setCrossSimCallingEnabled(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isVoWiFiRoamingSettingEnabled(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setVoWiFiRoamingSettingEnabled(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setVoWiFiNonPersistent(int i, boolean z, int i2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getVoWiFiModeSetting(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setVoWiFiModeSetting(int i, int i2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getVoWiFiRoamingModeSetting(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setVoWiFiRoamingModeSetting(int i, int i2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setRttCapabilitySetting(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isTtyOverVolteEnabled(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.util.Map getEmergencyNumberList(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isEmergencyNumber(java.lang.String str, boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.util.List<java.lang.String> getCertsFromCarrierPrivilegeAccessRules(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void registerImsProvisioningChangedCallback(int i, android.telephony.ims.aidl.IImsConfigCallback iImsConfigCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void unregisterImsProvisioningChangedCallback(int i, android.telephony.ims.aidl.IImsConfigCallback iImsConfigCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void registerFeatureProvisioningChangedCallback(int i, android.telephony.ims.aidl.IFeatureProvisioningCallback iFeatureProvisioningCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void unregisterFeatureProvisioningChangedCallback(int i, android.telephony.ims.aidl.IFeatureProvisioningCallback iFeatureProvisioningCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setImsProvisioningStatusForCapability(int i, int i2, int i3, boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean getImsProvisioningStatusForCapability(int i, int i2, int i3) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean getRcsProvisioningStatusForCapability(int i, int i2, int i3) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setRcsProvisioningStatusForCapability(int i, int i2, int i3, boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getImsProvisioningInt(int i, int i2) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.lang.String getImsProvisioningString(int i, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int setImsProvisioningInt(int i, int i2, int i3) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int setImsProvisioningString(int i, int i2, java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void startEmergencyCallbackMode() throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void updateEmergencyNumberListTestMode(int i, android.telephony.emergency.EmergencyNumber emergencyNumber) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.util.List<java.lang.String> getEmergencyNumberListTestMode() throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getEmergencyNumberDbVersion(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void notifyOtaEmergencyNumberDbInstalled() throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void updateOtaEmergencyNumberDbFilePath(android.os.ParcelFileDescriptor parcelFileDescriptor) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void resetOtaEmergencyNumberDbFilePath() throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean enableModemForSlot(int i, boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setMultiSimCarrierRestriction(boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public int isMultiSimSupported(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void switchMultiSimConfig(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean doesSwitchMultiSimConfigTriggerReboot(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.util.List<android.telephony.UiccSlotMapping> getSlotsMapping(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getRadioHalVersion() throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getHalVersion(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.lang.String getCurrentPackageName() throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isApplicationOnUicc(int i, int i2) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isModemEnabledForSlot(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isDataEnabledForApn(int i, int i2, java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isApnMetered(int i, int i2) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setSystemSelectionChannels(java.util.List<android.telephony.RadioAccessSpecifier> list, int i, com.android.internal.telephony.IBooleanConsumer iBooleanConsumer) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.util.List<android.telephony.RadioAccessSpecifier> getSystemSelectionChannels(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isMvnoMatched(int i, int i2, java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void enqueueSmsPickResult(java.lang.String str, java.lang.String str2, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void showSwitchToManagedProfileDialog() throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.lang.String getMmsUserAgent(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.lang.String getMmsUAProfUrl(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setMobileDataPolicyEnabled(int i, int i2, boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isMobileDataPolicyEnabled(int i, int i2) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setCepEnabled(boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void notifyRcsAutoConfigurationReceived(int i, byte[] bArr, boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isIccLockEnabled(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int setIccLockEnabled(int i, boolean z, java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int changeIccLockPassword(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void requestUserActivityNotification() throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void userActivity() throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.lang.String getManualNetworkSelectionPlmn(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean canConnectTo5GInDsdsMode() throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.util.List<java.lang.String> getEquivalentHomePlmns(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int setVoNrEnabled(int i, boolean z) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isVoNrEnabled(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int setNrDualConnectivityState(int i, int i2) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isNrDualConnectivityEnabled(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isRadioInterfaceCapabilitySupported(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int sendThermalMitigationRequest(int i, android.telephony.ThermalMitigationRequest thermalMitigationRequest, java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void bootstrapAuthenticationRequest(int i, int i2, android.net.Uri uri, android.telephony.gba.UaSecurityProtocolIdentifier uaSecurityProtocolIdentifier, boolean z, android.telephony.IBootstrapAuthenticationCallback iBootstrapAuthenticationCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setBoundGbaServiceOverride(int i, java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.lang.String getBoundGbaService(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setGbaReleaseTimeOverride(int i, int i2) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getGbaReleaseTime(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setRcsClientConfiguration(int i, android.telephony.ims.RcsClientConfiguration rcsClientConfiguration) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isRcsVolteSingleRegistrationCapable(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void registerRcsProvisioningCallback(int i, android.telephony.ims.aidl.IRcsConfigCallback iRcsConfigCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void unregisterRcsProvisioningCallback(int i, android.telephony.ims.aidl.IRcsConfigCallback iRcsConfigCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void triggerRcsReconfiguration(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setRcsSingleRegistrationTestModeEnabled(boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean getRcsSingleRegistrationTestModeEnabled() throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setDeviceSingleRegistrationEnabledOverride(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean getDeviceSingleRegistrationEnabled() throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setCarrierSingleRegistrationEnabledOverride(int i, java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void sendDeviceToDeviceMessage(int i, int i2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setActiveDeviceToDeviceTransport(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setDeviceToDeviceForceEnabled(boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean getCarrierSingleRegistrationEnabled(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setImsFeatureValidationOverride(int i, java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean getImsFeatureValidationOverride(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.lang.String getMobileProvisioningUrl() throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int removeContactFromEab(int i, java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.lang.String getContactFromEab(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.lang.String getCapabilityFromEab(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean getDeviceUceEnabled() throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setDeviceUceEnabled(boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public android.telephony.ims.RcsContactUceCapability addUceRegistrationOverrideShell(int i, java.util.List<java.lang.String> list) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public android.telephony.ims.RcsContactUceCapability removeUceRegistrationOverrideShell(int i, java.util.List<java.lang.String> list) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public android.telephony.ims.RcsContactUceCapability clearUceRegistrationOverrideShell(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public android.telephony.ims.RcsContactUceCapability getLatestRcsContactUceCapabilityShell(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.lang.String getLastUcePidfXmlShell(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean removeUceRequestDisallowedStatus(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setCapabilitiesRequestTimeout(int i, long j) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setSignalStrengthUpdateRequest(int i, android.telephony.SignalStrengthUpdateRequest signalStrengthUpdateRequest, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void clearSignalStrengthUpdateRequest(int i, android.telephony.SignalStrengthUpdateRequest signalStrengthUpdateRequest, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public android.telephony.PhoneCapability getPhoneCapability() throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int prepareForUnattendedReboot() throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void getSlicingConfig(android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isPremiumCapabilityAvailableForPurchase(int i, int i2) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void purchasePremiumCapability(int i, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer, int i2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void registerImsStateCallback(int i, int i2, com.android.internal.telephony.IImsStateCallback iImsStateCallback, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void unregisterImsStateCallback(com.android.internal.telephony.IImsStateCallback iImsStateCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public android.telephony.CellIdentity getLastKnownCellIdentity(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setModemService(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.lang.String getModemService() throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isProvisioningRequiredForCapability(int i, int i2, int i3) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isRcsProvisioningRequiredForCapability(int i, int i2, int i3) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setVoiceServiceStateOverride(int i, boolean z, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.lang.String getCarrierServicePackageNameForLogicalSlot(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setRemovableEsimAsDefaultEuicc(boolean z, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isRemovableEsimDefaultEuicc(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public android.content.ComponentName getDefaultRespondViaMessageApplication(int i, boolean z) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public int getSimStateForSlotIndex(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void persistEmergencyCallDiagnosticData(java.lang.String str, boolean z, long j, boolean z2, boolean z3) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setNullCipherAndIntegrityEnabled(boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isNullCipherAndIntegrityPreferenceEnabled() throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.util.List<android.telephony.CellBroadcastIdRange> getCellBroadcastIdRanges(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setCellBroadcastIdRanges(int i, java.util.List<android.telephony.CellBroadcastIdRange> list, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isDomainSelectionSupported() throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void getCarrierRestrictionStatus(com.android.internal.telephony.IIntegerConsumer iIntegerConsumer, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void requestSatelliteEnabled(int i, boolean z, boolean z2, boolean z3, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void requestIsSatelliteEnabled(int i, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void requestIsDemoModeEnabled(int i, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void requestIsEmergencyModeEnabled(int i, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void requestIsSatelliteSupported(int i, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void requestSatelliteCapabilities(int i, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void startSatelliteTransmissionUpdates(int i, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer, android.telephony.satellite.ISatelliteTransmissionUpdateCallback iSatelliteTransmissionUpdateCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void stopSatelliteTransmissionUpdates(int i, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer, android.telephony.satellite.ISatelliteTransmissionUpdateCallback iSatelliteTransmissionUpdateCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public android.os.ICancellationSignal provisionSatelliteService(int i, java.lang.String str, byte[] bArr, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void deprovisionSatelliteService(int i, java.lang.String str, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public int registerForSatelliteProvisionStateChanged(int i, android.telephony.satellite.ISatelliteProvisionStateCallback iSatelliteProvisionStateCallback) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void unregisterForSatelliteProvisionStateChanged(int i, android.telephony.satellite.ISatelliteProvisionStateCallback iSatelliteProvisionStateCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void requestIsSatelliteProvisioned(int i, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public int registerForSatelliteModemStateChanged(int i, android.telephony.satellite.ISatelliteModemStateCallback iSatelliteModemStateCallback) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void unregisterForModemStateChanged(int i, android.telephony.satellite.ISatelliteModemStateCallback iSatelliteModemStateCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public int registerForIncomingDatagram(int i, android.telephony.satellite.ISatelliteDatagramCallback iSatelliteDatagramCallback) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void unregisterForIncomingDatagram(int i, android.telephony.satellite.ISatelliteDatagramCallback iSatelliteDatagramCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void pollPendingDatagrams(int i, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void sendDatagram(int i, int i2, android.telephony.satellite.SatelliteDatagram satelliteDatagram, boolean z, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void requestIsCommunicationAllowedForCurrentLocation(int i, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void requestTimeForNextSatelliteVisibility(int i, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setDeviceAlignedWithSatellite(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setSatelliteServicePackageName(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setSatelliteGatewayServicePackageName(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setSatelliteListeningTimeoutDuration(long j) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setSatellitePointingUiClassName(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setDatagramControllerTimeoutDuration(boolean z, int i, long j) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setSatelliteControllerTimeoutDuration(boolean z, int i, long j) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setEmergencyCallToSatelliteHandoverType(int i, int i2) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setCountryCodes(boolean z, java.util.List<java.lang.String> list, java.util.Map map, java.lang.String str, long j) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setSatelliteAccessControlOverlayConfigs(boolean z, boolean z2, java.lang.String str, long j, java.util.List<java.lang.String> list) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setOemEnabledSatelliteProvisionStatus(boolean z, boolean z2) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.util.List<java.lang.String> getShaIdFromAllowList(java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void addAttachRestrictionForCarrier(int i, int i2, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void removeAttachRestrictionForCarrier(int i, int i2, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public int[] getAttachRestrictionReasonsForCarrier(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void requestNtnSignalStrength(int i, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void registerForNtnSignalStrengthChanged(int i, android.telephony.satellite.INtnSignalStrengthCallback iNtnSignalStrengthCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public void unregisterForNtnSignalStrengthChanged(int i, android.telephony.satellite.INtnSignalStrengthCallback iNtnSignalStrengthCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public int registerForCapabilitiesChanged(int i, android.telephony.satellite.ISatelliteCapabilitiesCallback iSatelliteCapabilitiesCallback) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void unregisterForCapabilitiesChanged(int i, android.telephony.satellite.ISatelliteCapabilitiesCallback iSatelliteCapabilitiesCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setShouldSendDatagramToModemInDemoMode(boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean setDomainSelectionServiceOverride(android.content.ComponentName componentName) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean clearDomainSelectionServiceOverride() throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setEnableCellularIdentifierDisclosureNotifications(boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isCellularIdentifierDisclosureNotificationsEnabled() throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public void setNullCipherNotificationsEnabled(boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephony
        public boolean isNullCipherNotificationsEnabled() throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ITelephony
        public java.util.List<java.lang.String> getSatellitePlmnsForCarrier(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.telephony.ITelephony {
        public static final java.lang.String DESCRIPTOR = "com.android.internal.telephony.ITelephony";
        static final int TRANSACTION_addAttachRestrictionForCarrier = 392;
        static final int TRANSACTION_addUceRegistrationOverrideShell = 325;
        static final int TRANSACTION_bootstrapAuthenticationRequest = 298;
        static final int TRANSACTION_call = 2;
        static final int TRANSACTION_canChangeDtmfToneLength = 137;
        static final int TRANSACTION_canConnectTo5GInDsdsMode = 290;
        static final int TRANSACTION_carrierActionReportDefaultNetworkStatus = 178;
        static final int TRANSACTION_carrierActionResetAll = 179;
        static final int TRANSACTION_carrierActionSetRadioEnabled = 177;
        static final int TRANSACTION_changeIccLockPassword = 286;
        static final int TRANSACTION_checkCarrierPrivilegesForPackage = 121;
        static final int TRANSACTION_checkCarrierPrivilegesForPackageAnyPhone = 122;
        static final int TRANSACTION_clearCarrierImsServiceOverride = 99;
        static final int TRANSACTION_clearDomainSelectionServiceOverride = 402;
        static final int TRANSACTION_clearRadioPowerOffForReason = 22;
        static final int TRANSACTION_clearSignalStrengthUpdateRequest = 333;
        static final int TRANSACTION_clearUceRegistrationOverrideShell = 327;
        static final int TRANSACTION_deprovisionSatelliteService = 368;
        static final int TRANSACTION_dial = 1;
        static final int TRANSACTION_disableDataConnectivity = 29;
        static final int TRANSACTION_disableIms = 92;
        static final int TRANSACTION_disableLocationUpdates = 27;
        static final int TRANSACTION_disableVisualVoicemailSmsFilter = 59;
        static final int TRANSACTION_doesSwitchMultiSimConfigTriggerReboot = 264;
        static final int TRANSACTION_enableDataConnectivity = 28;
        static final int TRANSACTION_enableIms = 91;
        static final int TRANSACTION_enableLocationUpdates = 26;
        static final int TRANSACTION_enableModemForSlot = 260;
        static final int TRANSACTION_enableVideoCalling = 135;
        static final int TRANSACTION_enableVisualVoicemailSmsFilter = 58;
        static final int TRANSACTION_enqueueSmsPickResult = 276;
        static final int TRANSACTION_factoryReset = 156;
        static final int TRANSACTION_getActivePhoneType = 40;
        static final int TRANSACTION_getActivePhoneTypeForSlot = 41;
        static final int TRANSACTION_getActiveVisualVoicemailSmsFilterSettings = 61;
        static final int TRANSACTION_getAidForAppType = 166;
        static final int TRANSACTION_getAllCellInfo = 72;
        static final int TRANSACTION_getAllowedCarriers = 171;
        static final int TRANSACTION_getAllowedNetworkTypesBitmask = 89;
        static final int TRANSACTION_getAllowedNetworkTypesForReason = 107;
        static final int TRANSACTION_getAttachRestrictionReasonsForCarrier = 394;
        static final int TRANSACTION_getBoundGbaService = 300;
        static final int TRANSACTION_getBoundImsServicePackage = 100;
        static final int TRANSACTION_getCallComposerStatus = 8;
        static final int TRANSACTION_getCallForwarding = 180;
        static final int TRANSACTION_getCallState = 34;
        static final int TRANSACTION_getCallStateForSubscription = 35;
        static final int TRANSACTION_getCallWaitingStatus = 182;
        static final int TRANSACTION_getCapabilityFromEab = 322;
        static final int TRANSACTION_getCardIdForDefaultEuicc = 191;
        static final int TRANSACTION_getCarrierIdFromMccMnc = 176;
        static final int TRANSACTION_getCarrierIdListVersion = 204;
        static final int TRANSACTION_getCarrierPackageNamesForIntentAndPhone = 123;
        static final int TRANSACTION_getCarrierPrivilegeStatus = 119;
        static final int TRANSACTION_getCarrierPrivilegeStatusForUid = 120;
        static final int TRANSACTION_getCarrierRestrictionStatus = 358;
        static final int TRANSACTION_getCarrierServicePackageNameForLogicalSlot = 347;
        static final int TRANSACTION_getCarrierSingleRegistrationEnabled = 316;
        static final int TRANSACTION_getCdmaEriIconIndex = 42;
        static final int TRANSACTION_getCdmaEriIconIndexForSubscriber = 43;
        static final int TRANSACTION_getCdmaEriIconMode = 44;
        static final int TRANSACTION_getCdmaEriIconModeForSubscriber = 45;
        static final int TRANSACTION_getCdmaEriText = 46;
        static final int TRANSACTION_getCdmaEriTextForSubscriber = 47;
        static final int TRANSACTION_getCdmaMdn = 116;
        static final int TRANSACTION_getCdmaMin = 117;
        static final int TRANSACTION_getCdmaPrlVersion = 168;
        static final int TRANSACTION_getCdmaRoamingMode = 198;
        static final int TRANSACTION_getCdmaSubscriptionMode = 200;
        static final int TRANSACTION_getCellBroadcastIdRanges = 355;
        static final int TRANSACTION_getCellLocation = 31;
        static final int TRANSACTION_getCellNetworkScanResults = 103;
        static final int TRANSACTION_getCertsFromCarrierPrivilegeAccessRules = 240;
        static final int TRANSACTION_getClientRequestStats = 184;
        static final int TRANSACTION_getContactFromEab = 321;
        static final int TRANSACTION_getCurrentPackageName = 268;
        static final int TRANSACTION_getDataActivationState = 53;
        static final int TRANSACTION_getDataActivity = 36;
        static final int TRANSACTION_getDataActivityForSubId = 37;
        static final int TRANSACTION_getDataEnabled = 109;
        static final int TRANSACTION_getDataNetworkType = 65;
        static final int TRANSACTION_getDataNetworkTypeForSubscriber = 66;
        static final int TRANSACTION_getDataState = 38;
        static final int TRANSACTION_getDataStateForSubId = 39;
        static final int TRANSACTION_getDefaultRespondViaMessageApplication = 350;
        static final int TRANSACTION_getDeviceId = 146;
        static final int TRANSACTION_getDeviceIdWithFeature = 147;
        static final int TRANSACTION_getDeviceSingleRegistrationEnabled = 311;
        static final int TRANSACTION_getDeviceSoftwareVersionForSlot = 153;
        static final int TRANSACTION_getDeviceUceEnabled = 323;
        static final int TRANSACTION_getEmergencyCallbackMode = 189;
        static final int TRANSACTION_getEmergencyNumberDbVersion = 256;
        static final int TRANSACTION_getEmergencyNumberList = 238;
        static final int TRANSACTION_getEmergencyNumberListTestMode = 255;
        static final int TRANSACTION_getEquivalentHomePlmns = 291;
        static final int TRANSACTION_getEsn = 167;
        static final int TRANSACTION_getForbiddenPlmns = 187;
        static final int TRANSACTION_getGbaReleaseTime = 302;
        static final int TRANSACTION_getHalVersion = 267;
        static final int TRANSACTION_getImeiForSlot = 148;
        static final int TRANSACTION_getImsConfig = 97;
        static final int TRANSACTION_getImsFeatureValidationOverride = 318;
        static final int TRANSACTION_getImsMmTelFeatureState = 101;
        static final int TRANSACTION_getImsMmTelRegistrationState = 214;
        static final int TRANSACTION_getImsMmTelRegistrationTransportType = 215;
        static final int TRANSACTION_getImsProvisioningInt = 249;
        static final int TRANSACTION_getImsProvisioningStatusForCapability = 246;
        static final int TRANSACTION_getImsProvisioningString = 250;
        static final int TRANSACTION_getImsRegTechnologyForMmTel = 145;
        static final int TRANSACTION_getImsRegistration = 96;
        static final int TRANSACTION_getLastKnownCellIdentity = 341;
        static final int TRANSACTION_getLastUcePidfXmlShell = 329;
        static final int TRANSACTION_getLatestRcsContactUceCapabilityShell = 328;
        static final int TRANSACTION_getLine1AlphaTagForDisplay = 126;
        static final int TRANSACTION_getLine1NumberForDisplay = 125;
        static final int TRANSACTION_getLteOnCdmaMode = 70;
        static final int TRANSACTION_getLteOnCdmaModeForSubscriber = 71;
        static final int TRANSACTION_getManualNetworkSelectionPlmn = 289;
        static final int TRANSACTION_getManufacturerCodeForSlot = 152;
        static final int TRANSACTION_getMeidForSlot = 151;
        static final int TRANSACTION_getMergedImsisFromGroup = 128;
        static final int TRANSACTION_getMergedSubscriberIds = 127;
        static final int TRANSACTION_getMmsUAProfUrl = 279;
        static final int TRANSACTION_getMmsUserAgent = 278;
        static final int TRANSACTION_getMobileProvisioningUrl = 319;
        static final int TRANSACTION_getModemService = 343;
        static final int TRANSACTION_getNeighboringCellInfo = 33;
        static final int TRANSACTION_getNetworkCountryIsoForPhone = 32;
        static final int TRANSACTION_getNetworkSelectionMode = 207;
        static final int TRANSACTION_getNetworkTypeForSubscriber = 64;
        static final int TRANSACTION_getNumberOfModemsWithSimultaneousDataConnections = 206;
        static final int TRANSACTION_getPackagesWithCarrierPrivileges = 164;
        static final int TRANSACTION_getPackagesWithCarrierPrivilegesForAllPhones = 165;
        static final int TRANSACTION_getPhoneAccountHandleForSubscriptionId = 155;
        static final int TRANSACTION_getPhoneCapability = 334;
        static final int TRANSACTION_getPrimaryImei = 149;
        static final int TRANSACTION_getRadioAccessFamily = 133;
        static final int TRANSACTION_getRadioHalVersion = 266;
        static final int TRANSACTION_getRadioPowerOffReasons = 23;
        static final int TRANSACTION_getRadioPowerState = 209;
        static final int TRANSACTION_getRcsProvisioningStatusForCapability = 247;
        static final int TRANSACTION_getRcsSingleRegistrationTestModeEnabled = 309;
        static final int TRANSACTION_getSatellitePlmnsForCarrier = 407;
        static final int TRANSACTION_getServiceStateForSubscriber = 159;
        static final int TRANSACTION_getShaIdFromAllowList = 391;
        static final int TRANSACTION_getSignalStrength = 190;
        static final int TRANSACTION_getSimLocaleForSubscriber = 157;
        static final int TRANSACTION_getSimStateForSlotIndex = 351;
        static final int TRANSACTION_getSlicingConfig = 336;
        static final int TRANSACTION_getSlotsMapping = 265;
        static final int TRANSACTION_getSubIdForPhoneAccountHandle = 154;
        static final int TRANSACTION_getSubscriptionCarrierId = 172;
        static final int TRANSACTION_getSubscriptionCarrierName = 173;
        static final int TRANSACTION_getSubscriptionSpecificCarrierId = 174;
        static final int TRANSACTION_getSubscriptionSpecificCarrierName = 175;
        static final int TRANSACTION_getSystemSelectionChannels = 274;
        static final int TRANSACTION_getTelephonyHistograms = 169;
        static final int TRANSACTION_getTypeAllocationCodeForSlot = 150;
        static final int TRANSACTION_getUiccCardsInfo = 192;
        static final int TRANSACTION_getUiccSlotsInfo = 193;
        static final int TRANSACTION_getVisualVoicemailPackageName = 57;
        static final int TRANSACTION_getVisualVoicemailSettings = 56;
        static final int TRANSACTION_getVisualVoicemailSmsFilterSettings = 60;
        static final int TRANSACTION_getVoWiFiModeSetting = 232;
        static final int TRANSACTION_getVoWiFiRoamingModeSetting = 234;
        static final int TRANSACTION_getVoiceActivationState = 52;
        static final int TRANSACTION_getVoiceMessageCountForSubscriber = 54;
        static final int TRANSACTION_getVoiceNetworkTypeForSubscriber = 67;
        static final int TRANSACTION_getVoicemailRingtoneUri = 160;
        static final int TRANSACTION_handlePinMmi = 13;
        static final int TRANSACTION_handlePinMmiForSubscriber = 15;
        static final int TRANSACTION_handleUssdRequest = 14;
        static final int TRANSACTION_hasIccCard = 68;
        static final int TRANSACTION_hasIccCardUsingSlotIndex = 69;
        static final int TRANSACTION_iccCloseLogicalChannel = 77;
        static final int TRANSACTION_iccExchangeSimIO = 82;
        static final int TRANSACTION_iccOpenLogicalChannel = 76;
        static final int TRANSACTION_iccTransmitApduBasicChannel = 81;
        static final int TRANSACTION_iccTransmitApduBasicChannelByPort = 80;
        static final int TRANSACTION_iccTransmitApduLogicalChannel = 79;
        static final int TRANSACTION_iccTransmitApduLogicalChannelByPort = 78;
        static final int TRANSACTION_isAdvancedCallingSettingEnabled = 221;
        static final int TRANSACTION_isApnMetered = 272;
        static final int TRANSACTION_isApplicationOnUicc = 269;
        static final int TRANSACTION_isAvailable = 219;
        static final int TRANSACTION_isCapable = 218;
        static final int TRANSACTION_isCellularIdentifierDisclosureNotificationsEnabled = 404;
        static final int TRANSACTION_isConcurrentVoiceAndDataAllowed = 55;
        static final int TRANSACTION_isCrossSimCallingEnabledByUser = 227;
        static final int TRANSACTION_isDataConnectivityPossible = 30;
        static final int TRANSACTION_isDataEnabled = 111;
        static final int TRANSACTION_isDataEnabledForApn = 271;
        static final int TRANSACTION_isDataEnabledForReason = 113;
        static final int TRANSACTION_isDataRoamingEnabled = 196;
        static final int TRANSACTION_isDomainSelectionSupported = 357;
        static final int TRANSACTION_isEmergencyNumber = 239;
        static final int TRANSACTION_isHearingAidCompatibilitySupported = 141;
        static final int TRANSACTION_isIccLockEnabled = 284;
        static final int TRANSACTION_isImsRegistered = 142;
        static final int TRANSACTION_isInEmergencySmsMode = 208;
        static final int TRANSACTION_isManualNetworkSelectionAllowed = 114;
        static final int TRANSACTION_isMmTelCapabilitySupported = 220;
        static final int TRANSACTION_isMobileDataPolicyEnabled = 281;
        static final int TRANSACTION_isModemEnabledForSlot = 270;
        static final int TRANSACTION_isMultiSimSupported = 262;
        static final int TRANSACTION_isMvnoMatched = 275;
        static final int TRANSACTION_isNrDualConnectivityEnabled = 295;
        static final int TRANSACTION_isNullCipherAndIntegrityPreferenceEnabled = 354;
        static final int TRANSACTION_isNullCipherNotificationsEnabled = 406;
        static final int TRANSACTION_isPremiumCapabilityAvailableForPurchase = 337;
        static final int TRANSACTION_isProvisioningRequiredForCapability = 344;
        static final int TRANSACTION_isRadioInterfaceCapabilitySupported = 296;
        static final int TRANSACTION_isRadioOn = 3;
        static final int TRANSACTION_isRadioOnForSubscriber = 5;
        static final int TRANSACTION_isRadioOnForSubscriberWithFeature = 6;
        static final int TRANSACTION_isRadioOnWithFeature = 4;
        static final int TRANSACTION_isRcsProvisioningRequiredForCapability = 345;
        static final int TRANSACTION_isRcsVolteSingleRegistrationCapable = 304;
        static final int TRANSACTION_isRemovableEsimDefaultEuicc = 349;
        static final int TRANSACTION_isRttSupported = 140;
        static final int TRANSACTION_isTetheringApnRequiredForSubscriber = 90;
        static final int TRANSACTION_isTtyModeSupported = 139;
        static final int TRANSACTION_isTtyOverVolteEnabled = 237;
        static final int TRANSACTION_isUserDataEnabled = 110;
        static final int TRANSACTION_isVideoCallingEnabled = 136;
        static final int TRANSACTION_isVideoTelephonyAvailable = 144;
        static final int TRANSACTION_isVoNrEnabled = 293;
        static final int TRANSACTION_isVoWiFiRoamingSettingEnabled = 229;
        static final int TRANSACTION_isVoWiFiSettingEnabled = 225;
        static final int TRANSACTION_isVoicemailVibrationEnabled = 162;
        static final int TRANSACTION_isVtSettingEnabled = 223;
        static final int TRANSACTION_isWifiCallingAvailable = 143;
        static final int TRANSACTION_isWorldPhone = 138;
        static final int TRANSACTION_needMobileRadioShutdown = 131;
        static final int TRANSACTION_needsOtaServiceProvisioning = 48;
        static final int TRANSACTION_notifyOtaEmergencyNumberDbInstalled = 257;
        static final int TRANSACTION_notifyRcsAutoConfigurationReceived = 283;
        static final int TRANSACTION_nvReadItem = 84;
        static final int TRANSACTION_nvWriteCdmaPrl = 86;
        static final int TRANSACTION_nvWriteItem = 85;
        static final int TRANSACTION_persistEmergencyCallDiagnosticData = 352;
        static final int TRANSACTION_pollPendingDatagrams = 376;
        static final int TRANSACTION_prepareForUnattendedReboot = 335;
        static final int TRANSACTION_provisionSatelliteService = 367;
        static final int TRANSACTION_purchasePremiumCapability = 338;
        static final int TRANSACTION_rebootModem = 88;
        static final int TRANSACTION_refreshUiccProfile = 205;
        static final int TRANSACTION_registerFeatureProvisioningChangedCallback = 243;
        static final int TRANSACTION_registerForCapabilitiesChanged = 398;
        static final int TRANSACTION_registerForIncomingDatagram = 374;
        static final int TRANSACTION_registerForNtnSignalStrengthChanged = 396;
        static final int TRANSACTION_registerForSatelliteModemStateChanged = 372;
        static final int TRANSACTION_registerForSatelliteProvisionStateChanged = 369;
        static final int TRANSACTION_registerImsEmergencyRegistrationCallback = 212;
        static final int TRANSACTION_registerImsProvisioningChangedCallback = 241;
        static final int TRANSACTION_registerImsRegistrationCallback = 210;
        static final int TRANSACTION_registerImsStateCallback = 339;
        static final int TRANSACTION_registerMmTelCapabilityCallback = 216;
        static final int TRANSACTION_registerMmTelFeatureCallback = 94;
        static final int TRANSACTION_registerRcsProvisioningCallback = 305;
        static final int TRANSACTION_removeAttachRestrictionForCarrier = 393;
        static final int TRANSACTION_removeContactFromEab = 320;
        static final int TRANSACTION_removeUceRegistrationOverrideShell = 326;
        static final int TRANSACTION_removeUceRequestDisallowedStatus = 330;
        static final int TRANSACTION_requestCellInfoUpdate = 73;
        static final int TRANSACTION_requestCellInfoUpdateWithWorkSource = 74;
        static final int TRANSACTION_requestIsCommunicationAllowedForCurrentLocation = 378;
        static final int TRANSACTION_requestIsDemoModeEnabled = 361;
        static final int TRANSACTION_requestIsEmergencyModeEnabled = 362;
        static final int TRANSACTION_requestIsSatelliteEnabled = 360;
        static final int TRANSACTION_requestIsSatelliteProvisioned = 371;
        static final int TRANSACTION_requestIsSatelliteSupported = 363;
        static final int TRANSACTION_requestModemActivityInfo = 158;
        static final int TRANSACTION_requestNetworkScan = 104;
        static final int TRANSACTION_requestNtnSignalStrength = 395;
        static final int TRANSACTION_requestNumberVerification = 118;
        static final int TRANSACTION_requestRadioPowerOffForReason = 21;
        static final int TRANSACTION_requestSatelliteCapabilities = 364;
        static final int TRANSACTION_requestSatelliteEnabled = 359;
        static final int TRANSACTION_requestTimeForNextSatelliteVisibility = 379;
        static final int TRANSACTION_requestUserActivityNotification = 287;
        static final int TRANSACTION_resetIms = 93;
        static final int TRANSACTION_resetModemConfig = 87;
        static final int TRANSACTION_resetOtaEmergencyNumberDbFilePath = 259;
        static final int TRANSACTION_sendDatagram = 377;
        static final int TRANSACTION_sendDeviceToDeviceMessage = 313;
        static final int TRANSACTION_sendDialerSpecialCode = 63;
        static final int TRANSACTION_sendEnvelopeWithStatus = 83;
        static final int TRANSACTION_sendThermalMitigationRequest = 297;
        static final int TRANSACTION_sendVisualVoicemailSmsForSubscriber = 62;
        static final int TRANSACTION_setActiveDeviceToDeviceTransport = 314;
        static final int TRANSACTION_setAdvancedCallingSettingEnabled = 222;
        static final int TRANSACTION_setAllowedCarriers = 170;
        static final int TRANSACTION_setAllowedNetworkTypesForReason = 108;
        static final int TRANSACTION_setBoundGbaServiceOverride = 299;
        static final int TRANSACTION_setBoundImsServiceOverride = 98;
        static final int TRANSACTION_setCallComposerStatus = 7;
        static final int TRANSACTION_setCallForwarding = 181;
        static final int TRANSACTION_setCallWaitingStatus = 183;
        static final int TRANSACTION_setCapabilitiesRequestTimeout = 331;
        static final int TRANSACTION_setCarrierServicePackageOverride = 203;
        static final int TRANSACTION_setCarrierSingleRegistrationEnabledOverride = 312;
        static final int TRANSACTION_setCarrierTestOverride = 202;
        static final int TRANSACTION_setCdmaRoamingMode = 199;
        static final int TRANSACTION_setCdmaSubscriptionMode = 201;
        static final int TRANSACTION_setCellBroadcastIdRanges = 356;
        static final int TRANSACTION_setCellInfoListRate = 75;
        static final int TRANSACTION_setCepEnabled = 282;
        static final int TRANSACTION_setCountryCodes = 388;
        static final int TRANSACTION_setCrossSimCallingEnabled = 228;
        static final int TRANSACTION_setDataActivationState = 51;
        static final int TRANSACTION_setDataEnabledForReason = 112;
        static final int TRANSACTION_setDataRoamingEnabled = 197;
        static final int TRANSACTION_setDatagramControllerTimeoutDuration = 385;
        static final int TRANSACTION_setDeviceAlignedWithSatellite = 380;
        static final int TRANSACTION_setDeviceSingleRegistrationEnabledOverride = 310;
        static final int TRANSACTION_setDeviceToDeviceForceEnabled = 315;
        static final int TRANSACTION_setDeviceUceEnabled = 324;
        static final int TRANSACTION_setDomainSelectionServiceOverride = 401;
        static final int TRANSACTION_setEmergencyCallToSatelliteHandoverType = 387;
        static final int TRANSACTION_setEnableCellularIdentifierDisclosureNotifications = 403;
        static final int TRANSACTION_setForbiddenPlmns = 188;
        static final int TRANSACTION_setGbaReleaseTimeOverride = 301;
        static final int TRANSACTION_setIccLockEnabled = 285;
        static final int TRANSACTION_setImsFeatureValidationOverride = 317;
        static final int TRANSACTION_setImsProvisioningInt = 251;
        static final int TRANSACTION_setImsProvisioningStatusForCapability = 245;
        static final int TRANSACTION_setImsProvisioningString = 252;
        static final int TRANSACTION_setImsRegistrationState = 115;
        static final int TRANSACTION_setLine1NumberForDisplayForSubscriber = 124;
        static final int TRANSACTION_setMobileDataPolicyEnabled = 280;
        static final int TRANSACTION_setModemService = 342;
        static final int TRANSACTION_setMultiSimCarrierRestriction = 261;
        static final int TRANSACTION_setNetworkSelectionModeAutomatic = 102;
        static final int TRANSACTION_setNetworkSelectionModeManual = 106;
        static final int TRANSACTION_setNrDualConnectivityState = 294;
        static final int TRANSACTION_setNullCipherAndIntegrityEnabled = 353;
        static final int TRANSACTION_setNullCipherNotificationsEnabled = 405;
        static final int TRANSACTION_setOemEnabledSatelliteProvisionStatus = 390;
        static final int TRANSACTION_setOperatorBrandOverride = 129;
        static final int TRANSACTION_setRadio = 18;
        static final int TRANSACTION_setRadioForSubscriber = 19;
        static final int TRANSACTION_setRadioPower = 20;
        static final int TRANSACTION_setRcsClientConfiguration = 303;
        static final int TRANSACTION_setRcsProvisioningStatusForCapability = 248;
        static final int TRANSACTION_setRcsSingleRegistrationTestModeEnabled = 308;
        static final int TRANSACTION_setRemovableEsimAsDefaultEuicc = 348;
        static final int TRANSACTION_setRoamingOverride = 130;
        static final int TRANSACTION_setRttCapabilitySetting = 236;
        static final int TRANSACTION_setSatelliteAccessControlOverlayConfigs = 389;
        static final int TRANSACTION_setSatelliteControllerTimeoutDuration = 386;
        static final int TRANSACTION_setSatelliteGatewayServicePackageName = 382;
        static final int TRANSACTION_setSatelliteListeningTimeoutDuration = 383;
        static final int TRANSACTION_setSatellitePointingUiClassName = 384;
        static final int TRANSACTION_setSatelliteServicePackageName = 381;
        static final int TRANSACTION_setShouldSendDatagramToModemInDemoMode = 400;
        static final int TRANSACTION_setSignalStrengthUpdateRequest = 332;
        static final int TRANSACTION_setSimPowerStateForSlot = 185;
        static final int TRANSACTION_setSimPowerStateForSlotWithCallback = 186;
        static final int TRANSACTION_setSimSlotMapping = 195;
        static final int TRANSACTION_setSystemSelectionChannels = 273;
        static final int TRANSACTION_setVoNrEnabled = 292;
        static final int TRANSACTION_setVoWiFiModeSetting = 233;
        static final int TRANSACTION_setVoWiFiNonPersistent = 231;
        static final int TRANSACTION_setVoWiFiRoamingModeSetting = 235;
        static final int TRANSACTION_setVoWiFiRoamingSettingEnabled = 230;
        static final int TRANSACTION_setVoWiFiSettingEnabled = 226;
        static final int TRANSACTION_setVoiceActivationState = 50;
        static final int TRANSACTION_setVoiceMailNumber = 49;
        static final int TRANSACTION_setVoiceServiceStateOverride = 346;
        static final int TRANSACTION_setVoicemailRingtoneUri = 161;
        static final int TRANSACTION_setVoicemailVibrationEnabled = 163;
        static final int TRANSACTION_setVtSettingEnabled = 224;
        static final int TRANSACTION_showSwitchToManagedProfileDialog = 277;
        static final int TRANSACTION_shutdownMobileRadios = 132;
        static final int TRANSACTION_startEmergencyCallbackMode = 253;
        static final int TRANSACTION_startSatelliteTransmissionUpdates = 365;
        static final int TRANSACTION_stopNetworkScan = 105;
        static final int TRANSACTION_stopSatelliteTransmissionUpdates = 366;
        static final int TRANSACTION_supplyPinForSubscriber = 9;
        static final int TRANSACTION_supplyPinReportResultForSubscriber = 11;
        static final int TRANSACTION_supplyPukForSubscriber = 10;
        static final int TRANSACTION_supplyPukReportResultForSubscriber = 12;
        static final int TRANSACTION_switchMultiSimConfig = 263;
        static final int TRANSACTION_switchSlots = 194;
        static final int TRANSACTION_toggleRadioOnOff = 16;
        static final int TRANSACTION_toggleRadioOnOffForSubscriber = 17;
        static final int TRANSACTION_triggerRcsReconfiguration = 307;
        static final int TRANSACTION_unregisterFeatureProvisioningChangedCallback = 244;
        static final int TRANSACTION_unregisterForCapabilitiesChanged = 399;
        static final int TRANSACTION_unregisterForIncomingDatagram = 375;
        static final int TRANSACTION_unregisterForModemStateChanged = 373;
        static final int TRANSACTION_unregisterForNtnSignalStrengthChanged = 397;
        static final int TRANSACTION_unregisterForSatelliteProvisionStateChanged = 370;
        static final int TRANSACTION_unregisterImsEmergencyRegistrationCallback = 213;
        static final int TRANSACTION_unregisterImsFeatureCallback = 95;
        static final int TRANSACTION_unregisterImsProvisioningChangedCallback = 242;
        static final int TRANSACTION_unregisterImsRegistrationCallback = 211;
        static final int TRANSACTION_unregisterImsStateCallback = 340;
        static final int TRANSACTION_unregisterMmTelCapabilityCallback = 217;
        static final int TRANSACTION_unregisterRcsProvisioningCallback = 306;
        static final int TRANSACTION_updateEmergencyNumberListTestMode = 254;
        static final int TRANSACTION_updateOtaEmergencyNumberDbFilePath = 258;
        static final int TRANSACTION_updateServiceLocation = 24;
        static final int TRANSACTION_updateServiceLocationWithPackageName = 25;
        static final int TRANSACTION_uploadCallComposerPicture = 134;
        static final int TRANSACTION_userActivity = 288;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.android.internal.telephony.ITelephony asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.telephony.ITelephony)) {
                return (com.android.internal.telephony.ITelephony) queryLocalInterface;
            }
            return new com.android.internal.telephony.ITelephony.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "dial";
                case 2:
                    return "call";
                case 3:
                    return "isRadioOn";
                case 4:
                    return "isRadioOnWithFeature";
                case 5:
                    return "isRadioOnForSubscriber";
                case 6:
                    return "isRadioOnForSubscriberWithFeature";
                case 7:
                    return "setCallComposerStatus";
                case 8:
                    return "getCallComposerStatus";
                case 9:
                    return "supplyPinForSubscriber";
                case 10:
                    return "supplyPukForSubscriber";
                case 11:
                    return "supplyPinReportResultForSubscriber";
                case 12:
                    return "supplyPukReportResultForSubscriber";
                case 13:
                    return "handlePinMmi";
                case 14:
                    return "handleUssdRequest";
                case 15:
                    return "handlePinMmiForSubscriber";
                case 16:
                    return "toggleRadioOnOff";
                case 17:
                    return "toggleRadioOnOffForSubscriber";
                case 18:
                    return "setRadio";
                case 19:
                    return "setRadioForSubscriber";
                case 20:
                    return "setRadioPower";
                case 21:
                    return "requestRadioPowerOffForReason";
                case 22:
                    return "clearRadioPowerOffForReason";
                case 23:
                    return "getRadioPowerOffReasons";
                case 24:
                    return "updateServiceLocation";
                case 25:
                    return "updateServiceLocationWithPackageName";
                case 26:
                    return "enableLocationUpdates";
                case 27:
                    return "disableLocationUpdates";
                case 28:
                    return "enableDataConnectivity";
                case 29:
                    return "disableDataConnectivity";
                case 30:
                    return "isDataConnectivityPossible";
                case 31:
                    return "getCellLocation";
                case 32:
                    return "getNetworkCountryIsoForPhone";
                case 33:
                    return "getNeighboringCellInfo";
                case 34:
                    return "getCallState";
                case 35:
                    return "getCallStateForSubscription";
                case 36:
                    return "getDataActivity";
                case 37:
                    return "getDataActivityForSubId";
                case 38:
                    return "getDataState";
                case 39:
                    return "getDataStateForSubId";
                case 40:
                    return "getActivePhoneType";
                case 41:
                    return "getActivePhoneTypeForSlot";
                case 42:
                    return "getCdmaEriIconIndex";
                case 43:
                    return "getCdmaEriIconIndexForSubscriber";
                case 44:
                    return "getCdmaEriIconMode";
                case 45:
                    return "getCdmaEriIconModeForSubscriber";
                case 46:
                    return "getCdmaEriText";
                case 47:
                    return "getCdmaEriTextForSubscriber";
                case 48:
                    return "needsOtaServiceProvisioning";
                case 49:
                    return "setVoiceMailNumber";
                case 50:
                    return "setVoiceActivationState";
                case 51:
                    return "setDataActivationState";
                case 52:
                    return "getVoiceActivationState";
                case 53:
                    return "getDataActivationState";
                case 54:
                    return "getVoiceMessageCountForSubscriber";
                case 55:
                    return "isConcurrentVoiceAndDataAllowed";
                case 56:
                    return "getVisualVoicemailSettings";
                case 57:
                    return "getVisualVoicemailPackageName";
                case 58:
                    return "enableVisualVoicemailSmsFilter";
                case 59:
                    return "disableVisualVoicemailSmsFilter";
                case 60:
                    return "getVisualVoicemailSmsFilterSettings";
                case 61:
                    return "getActiveVisualVoicemailSmsFilterSettings";
                case 62:
                    return "sendVisualVoicemailSmsForSubscriber";
                case 63:
                    return "sendDialerSpecialCode";
                case 64:
                    return "getNetworkTypeForSubscriber";
                case 65:
                    return "getDataNetworkType";
                case 66:
                    return "getDataNetworkTypeForSubscriber";
                case 67:
                    return "getVoiceNetworkTypeForSubscriber";
                case 68:
                    return "hasIccCard";
                case 69:
                    return "hasIccCardUsingSlotIndex";
                case 70:
                    return "getLteOnCdmaMode";
                case 71:
                    return "getLteOnCdmaModeForSubscriber";
                case 72:
                    return "getAllCellInfo";
                case 73:
                    return "requestCellInfoUpdate";
                case 74:
                    return "requestCellInfoUpdateWithWorkSource";
                case 75:
                    return "setCellInfoListRate";
                case 76:
                    return "iccOpenLogicalChannel";
                case 77:
                    return "iccCloseLogicalChannel";
                case 78:
                    return "iccTransmitApduLogicalChannelByPort";
                case 79:
                    return "iccTransmitApduLogicalChannel";
                case 80:
                    return "iccTransmitApduBasicChannelByPort";
                case 81:
                    return "iccTransmitApduBasicChannel";
                case 82:
                    return "iccExchangeSimIO";
                case 83:
                    return "sendEnvelopeWithStatus";
                case 84:
                    return "nvReadItem";
                case 85:
                    return "nvWriteItem";
                case 86:
                    return "nvWriteCdmaPrl";
                case 87:
                    return "resetModemConfig";
                case 88:
                    return "rebootModem";
                case 89:
                    return "getAllowedNetworkTypesBitmask";
                case 90:
                    return "isTetheringApnRequiredForSubscriber";
                case 91:
                    return "enableIms";
                case 92:
                    return "disableIms";
                case 93:
                    return "resetIms";
                case 94:
                    return "registerMmTelFeatureCallback";
                case 95:
                    return "unregisterImsFeatureCallback";
                case 96:
                    return "getImsRegistration";
                case 97:
                    return "getImsConfig";
                case 98:
                    return "setBoundImsServiceOverride";
                case 99:
                    return "clearCarrierImsServiceOverride";
                case 100:
                    return "getBoundImsServicePackage";
                case 101:
                    return "getImsMmTelFeatureState";
                case 102:
                    return "setNetworkSelectionModeAutomatic";
                case 103:
                    return "getCellNetworkScanResults";
                case 104:
                    return "requestNetworkScan";
                case 105:
                    return "stopNetworkScan";
                case 106:
                    return "setNetworkSelectionModeManual";
                case 107:
                    return "getAllowedNetworkTypesForReason";
                case 108:
                    return "setAllowedNetworkTypesForReason";
                case 109:
                    return "getDataEnabled";
                case 110:
                    return "isUserDataEnabled";
                case 111:
                    return "isDataEnabled";
                case 112:
                    return "setDataEnabledForReason";
                case 113:
                    return "isDataEnabledForReason";
                case 114:
                    return "isManualNetworkSelectionAllowed";
                case 115:
                    return "setImsRegistrationState";
                case 116:
                    return "getCdmaMdn";
                case 117:
                    return "getCdmaMin";
                case 118:
                    return "requestNumberVerification";
                case 119:
                    return "getCarrierPrivilegeStatus";
                case 120:
                    return "getCarrierPrivilegeStatusForUid";
                case 121:
                    return "checkCarrierPrivilegesForPackage";
                case 122:
                    return "checkCarrierPrivilegesForPackageAnyPhone";
                case 123:
                    return "getCarrierPackageNamesForIntentAndPhone";
                case 124:
                    return "setLine1NumberForDisplayForSubscriber";
                case 125:
                    return "getLine1NumberForDisplay";
                case 126:
                    return "getLine1AlphaTagForDisplay";
                case 127:
                    return "getMergedSubscriberIds";
                case 128:
                    return "getMergedImsisFromGroup";
                case 129:
                    return "setOperatorBrandOverride";
                case 130:
                    return "setRoamingOverride";
                case 131:
                    return "needMobileRadioShutdown";
                case 132:
                    return "shutdownMobileRadios";
                case 133:
                    return "getRadioAccessFamily";
                case 134:
                    return "uploadCallComposerPicture";
                case 135:
                    return "enableVideoCalling";
                case 136:
                    return "isVideoCallingEnabled";
                case 137:
                    return "canChangeDtmfToneLength";
                case 138:
                    return "isWorldPhone";
                case 139:
                    return "isTtyModeSupported";
                case 140:
                    return "isRttSupported";
                case 141:
                    return "isHearingAidCompatibilitySupported";
                case 142:
                    return "isImsRegistered";
                case 143:
                    return "isWifiCallingAvailable";
                case 144:
                    return "isVideoTelephonyAvailable";
                case 145:
                    return "getImsRegTechnologyForMmTel";
                case 146:
                    return "getDeviceId";
                case 147:
                    return "getDeviceIdWithFeature";
                case 148:
                    return "getImeiForSlot";
                case 149:
                    return "getPrimaryImei";
                case 150:
                    return "getTypeAllocationCodeForSlot";
                case 151:
                    return "getMeidForSlot";
                case 152:
                    return "getManufacturerCodeForSlot";
                case 153:
                    return "getDeviceSoftwareVersionForSlot";
                case 154:
                    return "getSubIdForPhoneAccountHandle";
                case 155:
                    return "getPhoneAccountHandleForSubscriptionId";
                case 156:
                    return "factoryReset";
                case 157:
                    return "getSimLocaleForSubscriber";
                case 158:
                    return "requestModemActivityInfo";
                case 159:
                    return "getServiceStateForSubscriber";
                case 160:
                    return "getVoicemailRingtoneUri";
                case 161:
                    return "setVoicemailRingtoneUri";
                case 162:
                    return "isVoicemailVibrationEnabled";
                case 163:
                    return "setVoicemailVibrationEnabled";
                case 164:
                    return "getPackagesWithCarrierPrivileges";
                case 165:
                    return "getPackagesWithCarrierPrivilegesForAllPhones";
                case 166:
                    return "getAidForAppType";
                case 167:
                    return "getEsn";
                case 168:
                    return "getCdmaPrlVersion";
                case 169:
                    return "getTelephonyHistograms";
                case 170:
                    return "setAllowedCarriers";
                case 171:
                    return "getAllowedCarriers";
                case 172:
                    return "getSubscriptionCarrierId";
                case 173:
                    return "getSubscriptionCarrierName";
                case 174:
                    return "getSubscriptionSpecificCarrierId";
                case 175:
                    return "getSubscriptionSpecificCarrierName";
                case 176:
                    return "getCarrierIdFromMccMnc";
                case 177:
                    return "carrierActionSetRadioEnabled";
                case 178:
                    return "carrierActionReportDefaultNetworkStatus";
                case 179:
                    return "carrierActionResetAll";
                case 180:
                    return "getCallForwarding";
                case 181:
                    return "setCallForwarding";
                case 182:
                    return "getCallWaitingStatus";
                case 183:
                    return "setCallWaitingStatus";
                case 184:
                    return "getClientRequestStats";
                case 185:
                    return "setSimPowerStateForSlot";
                case 186:
                    return "setSimPowerStateForSlotWithCallback";
                case 187:
                    return "getForbiddenPlmns";
                case 188:
                    return "setForbiddenPlmns";
                case 189:
                    return "getEmergencyCallbackMode";
                case 190:
                    return "getSignalStrength";
                case 191:
                    return "getCardIdForDefaultEuicc";
                case 192:
                    return "getUiccCardsInfo";
                case 193:
                    return "getUiccSlotsInfo";
                case 194:
                    return "switchSlots";
                case 195:
                    return "setSimSlotMapping";
                case 196:
                    return "isDataRoamingEnabled";
                case 197:
                    return "setDataRoamingEnabled";
                case 198:
                    return "getCdmaRoamingMode";
                case 199:
                    return "setCdmaRoamingMode";
                case 200:
                    return "getCdmaSubscriptionMode";
                case 201:
                    return "setCdmaSubscriptionMode";
                case 202:
                    return "setCarrierTestOverride";
                case 203:
                    return "setCarrierServicePackageOverride";
                case 204:
                    return "getCarrierIdListVersion";
                case 205:
                    return "refreshUiccProfile";
                case 206:
                    return "getNumberOfModemsWithSimultaneousDataConnections";
                case 207:
                    return "getNetworkSelectionMode";
                case 208:
                    return "isInEmergencySmsMode";
                case 209:
                    return "getRadioPowerState";
                case 210:
                    return "registerImsRegistrationCallback";
                case 211:
                    return "unregisterImsRegistrationCallback";
                case 212:
                    return "registerImsEmergencyRegistrationCallback";
                case 213:
                    return "unregisterImsEmergencyRegistrationCallback";
                case 214:
                    return "getImsMmTelRegistrationState";
                case 215:
                    return "getImsMmTelRegistrationTransportType";
                case 216:
                    return "registerMmTelCapabilityCallback";
                case 217:
                    return "unregisterMmTelCapabilityCallback";
                case 218:
                    return "isCapable";
                case 219:
                    return "isAvailable";
                case 220:
                    return "isMmTelCapabilitySupported";
                case 221:
                    return "isAdvancedCallingSettingEnabled";
                case 222:
                    return "setAdvancedCallingSettingEnabled";
                case 223:
                    return "isVtSettingEnabled";
                case 224:
                    return "setVtSettingEnabled";
                case 225:
                    return "isVoWiFiSettingEnabled";
                case 226:
                    return "setVoWiFiSettingEnabled";
                case 227:
                    return "isCrossSimCallingEnabledByUser";
                case 228:
                    return "setCrossSimCallingEnabled";
                case 229:
                    return "isVoWiFiRoamingSettingEnabled";
                case 230:
                    return "setVoWiFiRoamingSettingEnabled";
                case 231:
                    return "setVoWiFiNonPersistent";
                case 232:
                    return "getVoWiFiModeSetting";
                case 233:
                    return "setVoWiFiModeSetting";
                case 234:
                    return "getVoWiFiRoamingModeSetting";
                case 235:
                    return "setVoWiFiRoamingModeSetting";
                case 236:
                    return "setRttCapabilitySetting";
                case 237:
                    return "isTtyOverVolteEnabled";
                case 238:
                    return "getEmergencyNumberList";
                case 239:
                    return "isEmergencyNumber";
                case 240:
                    return "getCertsFromCarrierPrivilegeAccessRules";
                case 241:
                    return "registerImsProvisioningChangedCallback";
                case 242:
                    return "unregisterImsProvisioningChangedCallback";
                case 243:
                    return "registerFeatureProvisioningChangedCallback";
                case 244:
                    return "unregisterFeatureProvisioningChangedCallback";
                case 245:
                    return "setImsProvisioningStatusForCapability";
                case 246:
                    return "getImsProvisioningStatusForCapability";
                case 247:
                    return "getRcsProvisioningStatusForCapability";
                case 248:
                    return "setRcsProvisioningStatusForCapability";
                case 249:
                    return "getImsProvisioningInt";
                case 250:
                    return "getImsProvisioningString";
                case 251:
                    return "setImsProvisioningInt";
                case 252:
                    return "setImsProvisioningString";
                case 253:
                    return "startEmergencyCallbackMode";
                case 254:
                    return "updateEmergencyNumberListTestMode";
                case 255:
                    return "getEmergencyNumberListTestMode";
                case 256:
                    return "getEmergencyNumberDbVersion";
                case 257:
                    return "notifyOtaEmergencyNumberDbInstalled";
                case 258:
                    return "updateOtaEmergencyNumberDbFilePath";
                case 259:
                    return "resetOtaEmergencyNumberDbFilePath";
                case 260:
                    return "enableModemForSlot";
                case 261:
                    return "setMultiSimCarrierRestriction";
                case 262:
                    return "isMultiSimSupported";
                case 263:
                    return "switchMultiSimConfig";
                case 264:
                    return "doesSwitchMultiSimConfigTriggerReboot";
                case 265:
                    return "getSlotsMapping";
                case 266:
                    return "getRadioHalVersion";
                case 267:
                    return "getHalVersion";
                case 268:
                    return "getCurrentPackageName";
                case 269:
                    return "isApplicationOnUicc";
                case 270:
                    return "isModemEnabledForSlot";
                case 271:
                    return "isDataEnabledForApn";
                case 272:
                    return "isApnMetered";
                case 273:
                    return "setSystemSelectionChannels";
                case 274:
                    return "getSystemSelectionChannels";
                case 275:
                    return "isMvnoMatched";
                case 276:
                    return "enqueueSmsPickResult";
                case 277:
                    return "showSwitchToManagedProfileDialog";
                case 278:
                    return "getMmsUserAgent";
                case 279:
                    return "getMmsUAProfUrl";
                case 280:
                    return "setMobileDataPolicyEnabled";
                case 281:
                    return "isMobileDataPolicyEnabled";
                case 282:
                    return "setCepEnabled";
                case 283:
                    return "notifyRcsAutoConfigurationReceived";
                case 284:
                    return "isIccLockEnabled";
                case 285:
                    return "setIccLockEnabled";
                case 286:
                    return "changeIccLockPassword";
                case 287:
                    return "requestUserActivityNotification";
                case 288:
                    return "userActivity";
                case 289:
                    return "getManualNetworkSelectionPlmn";
                case 290:
                    return "canConnectTo5GInDsdsMode";
                case 291:
                    return "getEquivalentHomePlmns";
                case 292:
                    return "setVoNrEnabled";
                case 293:
                    return "isVoNrEnabled";
                case 294:
                    return "setNrDualConnectivityState";
                case 295:
                    return "isNrDualConnectivityEnabled";
                case 296:
                    return "isRadioInterfaceCapabilitySupported";
                case 297:
                    return "sendThermalMitigationRequest";
                case 298:
                    return "bootstrapAuthenticationRequest";
                case 299:
                    return "setBoundGbaServiceOverride";
                case 300:
                    return "getBoundGbaService";
                case 301:
                    return "setGbaReleaseTimeOverride";
                case 302:
                    return "getGbaReleaseTime";
                case 303:
                    return "setRcsClientConfiguration";
                case 304:
                    return "isRcsVolteSingleRegistrationCapable";
                case 305:
                    return "registerRcsProvisioningCallback";
                case 306:
                    return "unregisterRcsProvisioningCallback";
                case 307:
                    return "triggerRcsReconfiguration";
                case 308:
                    return "setRcsSingleRegistrationTestModeEnabled";
                case 309:
                    return "getRcsSingleRegistrationTestModeEnabled";
                case 310:
                    return "setDeviceSingleRegistrationEnabledOverride";
                case 311:
                    return "getDeviceSingleRegistrationEnabled";
                case 312:
                    return "setCarrierSingleRegistrationEnabledOverride";
                case 313:
                    return "sendDeviceToDeviceMessage";
                case 314:
                    return "setActiveDeviceToDeviceTransport";
                case 315:
                    return "setDeviceToDeviceForceEnabled";
                case 316:
                    return "getCarrierSingleRegistrationEnabled";
                case 317:
                    return "setImsFeatureValidationOverride";
                case 318:
                    return "getImsFeatureValidationOverride";
                case 319:
                    return "getMobileProvisioningUrl";
                case 320:
                    return "removeContactFromEab";
                case 321:
                    return "getContactFromEab";
                case 322:
                    return "getCapabilityFromEab";
                case 323:
                    return "getDeviceUceEnabled";
                case 324:
                    return "setDeviceUceEnabled";
                case 325:
                    return "addUceRegistrationOverrideShell";
                case 326:
                    return "removeUceRegistrationOverrideShell";
                case 327:
                    return "clearUceRegistrationOverrideShell";
                case 328:
                    return "getLatestRcsContactUceCapabilityShell";
                case 329:
                    return "getLastUcePidfXmlShell";
                case 330:
                    return "removeUceRequestDisallowedStatus";
                case 331:
                    return "setCapabilitiesRequestTimeout";
                case 332:
                    return "setSignalStrengthUpdateRequest";
                case 333:
                    return "clearSignalStrengthUpdateRequest";
                case 334:
                    return "getPhoneCapability";
                case 335:
                    return "prepareForUnattendedReboot";
                case 336:
                    return "getSlicingConfig";
                case 337:
                    return "isPremiumCapabilityAvailableForPurchase";
                case 338:
                    return "purchasePremiumCapability";
                case 339:
                    return "registerImsStateCallback";
                case 340:
                    return "unregisterImsStateCallback";
                case 341:
                    return "getLastKnownCellIdentity";
                case 342:
                    return "setModemService";
                case 343:
                    return "getModemService";
                case 344:
                    return "isProvisioningRequiredForCapability";
                case 345:
                    return "isRcsProvisioningRequiredForCapability";
                case 346:
                    return "setVoiceServiceStateOverride";
                case 347:
                    return "getCarrierServicePackageNameForLogicalSlot";
                case 348:
                    return "setRemovableEsimAsDefaultEuicc";
                case 349:
                    return "isRemovableEsimDefaultEuicc";
                case 350:
                    return "getDefaultRespondViaMessageApplication";
                case 351:
                    return "getSimStateForSlotIndex";
                case 352:
                    return "persistEmergencyCallDiagnosticData";
                case 353:
                    return "setNullCipherAndIntegrityEnabled";
                case 354:
                    return "isNullCipherAndIntegrityPreferenceEnabled";
                case 355:
                    return "getCellBroadcastIdRanges";
                case 356:
                    return "setCellBroadcastIdRanges";
                case 357:
                    return "isDomainSelectionSupported";
                case 358:
                    return "getCarrierRestrictionStatus";
                case 359:
                    return "requestSatelliteEnabled";
                case 360:
                    return "requestIsSatelliteEnabled";
                case 361:
                    return "requestIsDemoModeEnabled";
                case 362:
                    return "requestIsEmergencyModeEnabled";
                case 363:
                    return "requestIsSatelliteSupported";
                case 364:
                    return "requestSatelliteCapabilities";
                case 365:
                    return "startSatelliteTransmissionUpdates";
                case 366:
                    return "stopSatelliteTransmissionUpdates";
                case 367:
                    return "provisionSatelliteService";
                case 368:
                    return "deprovisionSatelliteService";
                case 369:
                    return "registerForSatelliteProvisionStateChanged";
                case 370:
                    return "unregisterForSatelliteProvisionStateChanged";
                case 371:
                    return "requestIsSatelliteProvisioned";
                case 372:
                    return "registerForSatelliteModemStateChanged";
                case 373:
                    return "unregisterForModemStateChanged";
                case 374:
                    return "registerForIncomingDatagram";
                case 375:
                    return "unregisterForIncomingDatagram";
                case 376:
                    return "pollPendingDatagrams";
                case 377:
                    return "sendDatagram";
                case 378:
                    return "requestIsCommunicationAllowedForCurrentLocation";
                case 379:
                    return "requestTimeForNextSatelliteVisibility";
                case 380:
                    return "setDeviceAlignedWithSatellite";
                case 381:
                    return "setSatelliteServicePackageName";
                case 382:
                    return "setSatelliteGatewayServicePackageName";
                case 383:
                    return "setSatelliteListeningTimeoutDuration";
                case 384:
                    return "setSatellitePointingUiClassName";
                case 385:
                    return "setDatagramControllerTimeoutDuration";
                case 386:
                    return "setSatelliteControllerTimeoutDuration";
                case 387:
                    return "setEmergencyCallToSatelliteHandoverType";
                case 388:
                    return "setCountryCodes";
                case 389:
                    return "setSatelliteAccessControlOverlayConfigs";
                case 390:
                    return "setOemEnabledSatelliteProvisionStatus";
                case 391:
                    return "getShaIdFromAllowList";
                case 392:
                    return "addAttachRestrictionForCarrier";
                case 393:
                    return "removeAttachRestrictionForCarrier";
                case 394:
                    return "getAttachRestrictionReasonsForCarrier";
                case 395:
                    return "requestNtnSignalStrength";
                case 396:
                    return "registerForNtnSignalStrengthChanged";
                case 397:
                    return "unregisterForNtnSignalStrengthChanged";
                case 398:
                    return "registerForCapabilitiesChanged";
                case 399:
                    return "unregisterForCapabilitiesChanged";
                case 400:
                    return "setShouldSendDatagramToModemInDemoMode";
                case 401:
                    return "setDomainSelectionServiceOverride";
                case 402:
                    return "clearDomainSelectionServiceOverride";
                case 403:
                    return "setEnableCellularIdentifierDisclosureNotifications";
                case 404:
                    return "isCellularIdentifierDisclosureNotificationsEnabled";
                case 405:
                    return "setNullCipherNotificationsEnabled";
                case 406:
                    return "isNullCipherNotificationsEnabled";
                case 407:
                    return "getSatellitePlmnsForCarrier";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public java.lang.String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    dial(readString);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    java.lang.String readString2 = parcel.readString();
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    call(readString2, readString3);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    java.lang.String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isRadioOn = isRadioOn(readString4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isRadioOn);
                    return true;
                case 4:
                    java.lang.String readString5 = parcel.readString();
                    java.lang.String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isRadioOnWithFeature = isRadioOnWithFeature(readString5, readString6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isRadioOnWithFeature);
                    return true;
                case 5:
                    int readInt = parcel.readInt();
                    java.lang.String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isRadioOnForSubscriber = isRadioOnForSubscriber(readInt, readString7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isRadioOnForSubscriber);
                    return true;
                case 6:
                    return onTransact$isRadioOnForSubscriberWithFeature$(parcel, parcel2);
                case 7:
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCallComposerStatus(readInt2, readInt3);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int callComposerStatus = getCallComposerStatus(readInt4);
                    parcel2.writeNoException();
                    parcel2.writeInt(callComposerStatus);
                    return true;
                case 9:
                    int readInt5 = parcel.readInt();
                    java.lang.String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean supplyPinForSubscriber = supplyPinForSubscriber(readInt5, readString8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(supplyPinForSubscriber);
                    return true;
                case 10:
                    return onTransact$supplyPukForSubscriber$(parcel, parcel2);
                case 11:
                    int readInt6 = parcel.readInt();
                    java.lang.String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int[] supplyPinReportResultForSubscriber = supplyPinReportResultForSubscriber(readInt6, readString9);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(supplyPinReportResultForSubscriber);
                    return true;
                case 12:
                    return onTransact$supplyPukReportResultForSubscriber$(parcel, parcel2);
                case 13:
                    java.lang.String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean handlePinMmi = handlePinMmi(readString10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(handlePinMmi);
                    return true;
                case 14:
                    return onTransact$handleUssdRequest$(parcel, parcel2);
                case 15:
                    int readInt7 = parcel.readInt();
                    java.lang.String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean handlePinMmiForSubscriber = handlePinMmiForSubscriber(readInt7, readString11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(handlePinMmiForSubscriber);
                    return true;
                case 16:
                    toggleRadioOnOff();
                    parcel2.writeNoException();
                    return true;
                case 17:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    toggleRadioOnOffForSubscriber(readInt8);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean radio = setRadio(readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(radio);
                    return true;
                case 19:
                    int readInt9 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean radioForSubscriber = setRadioForSubscriber(readInt9, readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(radioForSubscriber);
                    return true;
                case 20:
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean radioPower = setRadioPower(readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(radioPower);
                    return true;
                case 21:
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean requestRadioPowerOffForReason = requestRadioPowerOffForReason(readInt10, readInt11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requestRadioPowerOffForReason);
                    return true;
                case 22:
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean clearRadioPowerOffForReason = clearRadioPowerOffForReason(readInt12, readInt13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(clearRadioPowerOffForReason);
                    return true;
                case 23:
                    return onTransact$getRadioPowerOffReasons$(parcel, parcel2);
                case 24:
                    updateServiceLocation();
                    parcel2.writeNoException();
                    return true;
                case 25:
                    java.lang.String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    updateServiceLocationWithPackageName(readString12);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    enableLocationUpdates();
                    parcel2.writeNoException();
                    return true;
                case 27:
                    disableLocationUpdates();
                    parcel2.writeNoException();
                    return true;
                case 28:
                    java.lang.String readString13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean enableDataConnectivity = enableDataConnectivity(readString13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enableDataConnectivity);
                    return true;
                case 29:
                    java.lang.String readString14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean disableDataConnectivity = disableDataConnectivity(readString14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(disableDataConnectivity);
                    return true;
                case 30:
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isDataConnectivityPossible = isDataConnectivityPossible(readInt14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDataConnectivityPossible);
                    return true;
                case 31:
                    java.lang.String readString15 = parcel.readString();
                    java.lang.String readString16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.telephony.CellIdentity cellLocation = getCellLocation(readString15, readString16);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(cellLocation, 1);
                    return true;
                case 32:
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String networkCountryIsoForPhone = getNetworkCountryIsoForPhone(readInt15);
                    parcel2.writeNoException();
                    parcel2.writeString(networkCountryIsoForPhone);
                    return true;
                case 33:
                    java.lang.String readString17 = parcel.readString();
                    java.lang.String readString18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.telephony.NeighboringCellInfo> neighboringCellInfo = getNeighboringCellInfo(readString17, readString18);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(neighboringCellInfo, 1);
                    return true;
                case 34:
                    int callState = getCallState();
                    parcel2.writeNoException();
                    parcel2.writeInt(callState);
                    return true;
                case 35:
                    return onTransact$getCallStateForSubscription$(parcel, parcel2);
                case 36:
                    int dataActivity = getDataActivity();
                    parcel2.writeNoException();
                    parcel2.writeInt(dataActivity);
                    return true;
                case 37:
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int dataActivityForSubId = getDataActivityForSubId(readInt16);
                    parcel2.writeNoException();
                    parcel2.writeInt(dataActivityForSubId);
                    return true;
                case 38:
                    int dataState = getDataState();
                    parcel2.writeNoException();
                    parcel2.writeInt(dataState);
                    return true;
                case 39:
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int dataStateForSubId = getDataStateForSubId(readInt17);
                    parcel2.writeNoException();
                    parcel2.writeInt(dataStateForSubId);
                    return true;
                case 40:
                    int activePhoneType = getActivePhoneType();
                    parcel2.writeNoException();
                    parcel2.writeInt(activePhoneType);
                    return true;
                case 41:
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int activePhoneTypeForSlot = getActivePhoneTypeForSlot(readInt18);
                    parcel2.writeNoException();
                    parcel2.writeInt(activePhoneTypeForSlot);
                    return true;
                case 42:
                    java.lang.String readString19 = parcel.readString();
                    java.lang.String readString20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int cdmaEriIconIndex = getCdmaEriIconIndex(readString19, readString20);
                    parcel2.writeNoException();
                    parcel2.writeInt(cdmaEriIconIndex);
                    return true;
                case 43:
                    return onTransact$getCdmaEriIconIndexForSubscriber$(parcel, parcel2);
                case 44:
                    java.lang.String readString21 = parcel.readString();
                    java.lang.String readString22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int cdmaEriIconMode = getCdmaEriIconMode(readString21, readString22);
                    parcel2.writeNoException();
                    parcel2.writeInt(cdmaEriIconMode);
                    return true;
                case 45:
                    return onTransact$getCdmaEriIconModeForSubscriber$(parcel, parcel2);
                case 46:
                    java.lang.String readString23 = parcel.readString();
                    java.lang.String readString24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String cdmaEriText = getCdmaEriText(readString23, readString24);
                    parcel2.writeNoException();
                    parcel2.writeString(cdmaEriText);
                    return true;
                case 47:
                    return onTransact$getCdmaEriTextForSubscriber$(parcel, parcel2);
                case 48:
                    boolean needsOtaServiceProvisioning = needsOtaServiceProvisioning();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(needsOtaServiceProvisioning);
                    return true;
                case 49:
                    return onTransact$setVoiceMailNumber$(parcel, parcel2);
                case 50:
                    int readInt19 = parcel.readInt();
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setVoiceActivationState(readInt19, readInt20);
                    parcel2.writeNoException();
                    return true;
                case 51:
                    int readInt21 = parcel.readInt();
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDataActivationState(readInt21, readInt22);
                    parcel2.writeNoException();
                    return true;
                case 52:
                    int readInt23 = parcel.readInt();
                    java.lang.String readString25 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int voiceActivationState = getVoiceActivationState(readInt23, readString25);
                    parcel2.writeNoException();
                    parcel2.writeInt(voiceActivationState);
                    return true;
                case 53:
                    int readInt24 = parcel.readInt();
                    java.lang.String readString26 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int dataActivationState = getDataActivationState(readInt24, readString26);
                    parcel2.writeNoException();
                    parcel2.writeInt(dataActivationState);
                    return true;
                case 54:
                    return onTransact$getVoiceMessageCountForSubscriber$(parcel, parcel2);
                case 55:
                    int readInt25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isConcurrentVoiceAndDataAllowed = isConcurrentVoiceAndDataAllowed(readInt25);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isConcurrentVoiceAndDataAllowed);
                    return true;
                case 56:
                    java.lang.String readString27 = parcel.readString();
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.os.Bundle visualVoicemailSettings = getVisualVoicemailSettings(readString27, readInt26);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(visualVoicemailSettings, 1);
                    return true;
                case 57:
                    return onTransact$getVisualVoicemailPackageName$(parcel, parcel2);
                case 58:
                    return onTransact$enableVisualVoicemailSmsFilter$(parcel, parcel2);
                case 59:
                    java.lang.String readString28 = parcel.readString();
                    int readInt27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disableVisualVoicemailSmsFilter(readString28, readInt27);
                    return true;
                case 60:
                    java.lang.String readString29 = parcel.readString();
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.telephony.VisualVoicemailSmsFilterSettings visualVoicemailSmsFilterSettings = getVisualVoicemailSmsFilterSettings(readString29, readInt28);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(visualVoicemailSmsFilterSettings, 1);
                    return true;
                case 61:
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.telephony.VisualVoicemailSmsFilterSettings activeVisualVoicemailSmsFilterSettings = getActiveVisualVoicemailSmsFilterSettings(readInt29);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(activeVisualVoicemailSmsFilterSettings, 1);
                    return true;
                case 62:
                    return onTransact$sendVisualVoicemailSmsForSubscriber$(parcel, parcel2);
                case 63:
                    java.lang.String readString30 = parcel.readString();
                    java.lang.String readString31 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sendDialerSpecialCode(readString30, readString31);
                    parcel2.writeNoException();
                    return true;
                case 64:
                    return onTransact$getNetworkTypeForSubscriber$(parcel, parcel2);
                case 65:
                    java.lang.String readString32 = parcel.readString();
                    java.lang.String readString33 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int dataNetworkType = getDataNetworkType(readString32, readString33);
                    parcel2.writeNoException();
                    parcel2.writeInt(dataNetworkType);
                    return true;
                case 66:
                    return onTransact$getDataNetworkTypeForSubscriber$(parcel, parcel2);
                case 67:
                    return onTransact$getVoiceNetworkTypeForSubscriber$(parcel, parcel2);
                case 68:
                    boolean hasIccCard = hasIccCard();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasIccCard);
                    return true;
                case 69:
                    int readInt30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean hasIccCardUsingSlotIndex = hasIccCardUsingSlotIndex(readInt30);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasIccCardUsingSlotIndex);
                    return true;
                case 70:
                    java.lang.String readString34 = parcel.readString();
                    java.lang.String readString35 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int lteOnCdmaMode = getLteOnCdmaMode(readString34, readString35);
                    parcel2.writeNoException();
                    parcel2.writeInt(lteOnCdmaMode);
                    return true;
                case 71:
                    return onTransact$getLteOnCdmaModeForSubscriber$(parcel, parcel2);
                case 72:
                    java.lang.String readString36 = parcel.readString();
                    java.lang.String readString37 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.telephony.CellInfo> allCellInfo = getAllCellInfo(readString36, readString37);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(allCellInfo, 1);
                    return true;
                case 73:
                    return onTransact$requestCellInfoUpdate$(parcel, parcel2);
                case 74:
                    return onTransact$requestCellInfoUpdateWithWorkSource$(parcel, parcel2);
                case 75:
                    int readInt31 = parcel.readInt();
                    int readInt32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCellInfoListRate(readInt31, readInt32);
                    parcel2.writeNoException();
                    return true;
                case 76:
                    com.android.internal.telephony.IccLogicalChannelRequest iccLogicalChannelRequest = (com.android.internal.telephony.IccLogicalChannelRequest) parcel.readTypedObject(com.android.internal.telephony.IccLogicalChannelRequest.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.telephony.IccOpenLogicalChannelResponse iccOpenLogicalChannel = iccOpenLogicalChannel(iccLogicalChannelRequest);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(iccOpenLogicalChannel, 1);
                    return true;
                case 77:
                    com.android.internal.telephony.IccLogicalChannelRequest iccLogicalChannelRequest2 = (com.android.internal.telephony.IccLogicalChannelRequest) parcel.readTypedObject(com.android.internal.telephony.IccLogicalChannelRequest.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean iccCloseLogicalChannel = iccCloseLogicalChannel(iccLogicalChannelRequest2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(iccCloseLogicalChannel);
                    return true;
                case 78:
                    return onTransact$iccTransmitApduLogicalChannelByPort$(parcel, parcel2);
                case 79:
                    return onTransact$iccTransmitApduLogicalChannel$(parcel, parcel2);
                case 80:
                    return onTransact$iccTransmitApduBasicChannelByPort$(parcel, parcel2);
                case 81:
                    return onTransact$iccTransmitApduBasicChannel$(parcel, parcel2);
                case 82:
                    return onTransact$iccExchangeSimIO$(parcel, parcel2);
                case 83:
                    int readInt33 = parcel.readInt();
                    java.lang.String readString38 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String sendEnvelopeWithStatus = sendEnvelopeWithStatus(readInt33, readString38);
                    parcel2.writeNoException();
                    parcel2.writeString(sendEnvelopeWithStatus);
                    return true;
                case 84:
                    int readInt34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String nvReadItem = nvReadItem(readInt34);
                    parcel2.writeNoException();
                    parcel2.writeString(nvReadItem);
                    return true;
                case 85:
                    int readInt35 = parcel.readInt();
                    java.lang.String readString39 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean nvWriteItem = nvWriteItem(readInt35, readString39);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(nvWriteItem);
                    return true;
                case 86:
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    boolean nvWriteCdmaPrl = nvWriteCdmaPrl(createByteArray);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(nvWriteCdmaPrl);
                    return true;
                case 87:
                    int readInt36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean resetModemConfig = resetModemConfig(readInt36);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(resetModemConfig);
                    return true;
                case 88:
                    int readInt37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean rebootModem = rebootModem(readInt37);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(rebootModem);
                    return true;
                case 89:
                    int readInt38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int allowedNetworkTypesBitmask = getAllowedNetworkTypesBitmask(readInt38);
                    parcel2.writeNoException();
                    parcel2.writeInt(allowedNetworkTypesBitmask);
                    return true;
                case 90:
                    int readInt39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isTetheringApnRequiredForSubscriber = isTetheringApnRequiredForSubscriber(readInt39);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isTetheringApnRequiredForSubscriber);
                    return true;
                case 91:
                    int readInt40 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    enableIms(readInt40);
                    parcel2.writeNoException();
                    return true;
                case 92:
                    int readInt41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disableIms(readInt41);
                    parcel2.writeNoException();
                    return true;
                case 93:
                    int readInt42 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    resetIms(readInt42);
                    parcel2.writeNoException();
                    return true;
                case 94:
                    int readInt43 = parcel.readInt();
                    com.android.ims.internal.IImsServiceFeatureCallback asInterface = com.android.ims.internal.IImsServiceFeatureCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerMmTelFeatureCallback(readInt43, asInterface);
                    parcel2.writeNoException();
                    return true;
                case 95:
                    com.android.ims.internal.IImsServiceFeatureCallback asInterface2 = com.android.ims.internal.IImsServiceFeatureCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterImsFeatureCallback(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 96:
                    int readInt44 = parcel.readInt();
                    int readInt45 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.telephony.ims.aidl.IImsRegistration imsRegistration = getImsRegistration(readInt44, readInt45);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(imsRegistration);
                    return true;
                case 97:
                    int readInt46 = parcel.readInt();
                    int readInt47 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.telephony.ims.aidl.IImsConfig imsConfig = getImsConfig(readInt46, readInt47);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(imsConfig);
                    return true;
                case 98:
                    return onTransact$setBoundImsServiceOverride$(parcel, parcel2);
                case 99:
                    int readInt48 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean clearCarrierImsServiceOverride = clearCarrierImsServiceOverride(readInt48);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(clearCarrierImsServiceOverride);
                    return true;
                case 100:
                    return onTransact$getBoundImsServicePackage$(parcel, parcel2);
                case 101:
                    int readInt49 = parcel.readInt();
                    com.android.internal.telephony.IIntegerConsumer asInterface3 = com.android.internal.telephony.IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getImsMmTelFeatureState(readInt49, asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 102:
                    int readInt50 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setNetworkSelectionModeAutomatic(readInt50);
                    parcel2.writeNoException();
                    return true;
                case 103:
                    return onTransact$getCellNetworkScanResults$(parcel, parcel2);
                case 104:
                    return onTransact$requestNetworkScan$(parcel, parcel2);
                case 105:
                    int readInt51 = parcel.readInt();
                    int readInt52 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopNetworkScan(readInt51, readInt52);
                    parcel2.writeNoException();
                    return true;
                case 106:
                    return onTransact$setNetworkSelectionModeManual$(parcel, parcel2);
                case 107:
                    int readInt53 = parcel.readInt();
                    int readInt54 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long allowedNetworkTypesForReason = getAllowedNetworkTypesForReason(readInt53, readInt54);
                    parcel2.writeNoException();
                    parcel2.writeLong(allowedNetworkTypesForReason);
                    return true;
                case 108:
                    return onTransact$setAllowedNetworkTypesForReason$(parcel, parcel2);
                case 109:
                    int readInt55 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean dataEnabled = getDataEnabled(readInt55);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dataEnabled);
                    return true;
                case 110:
                    int readInt56 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isUserDataEnabled = isUserDataEnabled(readInt56);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUserDataEnabled);
                    return true;
                case 111:
                    int readInt57 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isDataEnabled = isDataEnabled(readInt57);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDataEnabled);
                    return true;
                case 112:
                    return onTransact$setDataEnabledForReason$(parcel, parcel2);
                case 113:
                    int readInt58 = parcel.readInt();
                    int readInt59 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isDataEnabledForReason = isDataEnabledForReason(readInt58, readInt59);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDataEnabledForReason);
                    return true;
                case 114:
                    int readInt60 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isManualNetworkSelectionAllowed = isManualNetworkSelectionAllowed(readInt60);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isManualNetworkSelectionAllowed);
                    return true;
                case 115:
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setImsRegistrationState(readBoolean4);
                    parcel2.writeNoException();
                    return true;
                case 116:
                    int readInt61 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String cdmaMdn = getCdmaMdn(readInt61);
                    parcel2.writeNoException();
                    parcel2.writeString(cdmaMdn);
                    return true;
                case 117:
                    int readInt62 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String cdmaMin = getCdmaMin(readInt62);
                    parcel2.writeNoException();
                    parcel2.writeString(cdmaMin);
                    return true;
                case 118:
                    return onTransact$requestNumberVerification$(parcel, parcel2);
                case 119:
                    int readInt63 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int carrierPrivilegeStatus = getCarrierPrivilegeStatus(readInt63);
                    parcel2.writeNoException();
                    parcel2.writeInt(carrierPrivilegeStatus);
                    return true;
                case 120:
                    int readInt64 = parcel.readInt();
                    int readInt65 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int carrierPrivilegeStatusForUid = getCarrierPrivilegeStatusForUid(readInt64, readInt65);
                    parcel2.writeNoException();
                    parcel2.writeInt(carrierPrivilegeStatusForUid);
                    return true;
                case 121:
                    int readInt66 = parcel.readInt();
                    java.lang.String readString40 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int checkCarrierPrivilegesForPackage = checkCarrierPrivilegesForPackage(readInt66, readString40);
                    parcel2.writeNoException();
                    parcel2.writeInt(checkCarrierPrivilegesForPackage);
                    return true;
                case 122:
                    java.lang.String readString41 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int checkCarrierPrivilegesForPackageAnyPhone = checkCarrierPrivilegesForPackageAnyPhone(readString41);
                    parcel2.writeNoException();
                    parcel2.writeInt(checkCarrierPrivilegesForPackageAnyPhone);
                    return true;
                case 123:
                    android.content.Intent intent = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    int readInt67 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.List<java.lang.String> carrierPackageNamesForIntentAndPhone = getCarrierPackageNamesForIntentAndPhone(intent, readInt67);
                    parcel2.writeNoException();
                    parcel2.writeStringList(carrierPackageNamesForIntentAndPhone);
                    return true;
                case 124:
                    return onTransact$setLine1NumberForDisplayForSubscriber$(parcel, parcel2);
                case 125:
                    return onTransact$getLine1NumberForDisplay$(parcel, parcel2);
                case 126:
                    return onTransact$getLine1AlphaTagForDisplay$(parcel, parcel2);
                case 127:
                    return onTransact$getMergedSubscriberIds$(parcel, parcel2);
                case 128:
                    int readInt68 = parcel.readInt();
                    java.lang.String readString42 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String[] mergedImsisFromGroup = getMergedImsisFromGroup(readInt68, readString42);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(mergedImsisFromGroup);
                    return true;
                case 129:
                    int readInt69 = parcel.readInt();
                    java.lang.String readString43 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean operatorBrandOverride = setOperatorBrandOverride(readInt69, readString43);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(operatorBrandOverride);
                    return true;
                case 130:
                    return onTransact$setRoamingOverride$(parcel, parcel2);
                case 131:
                    boolean needMobileRadioShutdown = needMobileRadioShutdown();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(needMobileRadioShutdown);
                    return true;
                case 132:
                    shutdownMobileRadios();
                    parcel2.writeNoException();
                    return true;
                case 133:
                    int readInt70 = parcel.readInt();
                    java.lang.String readString44 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int radioAccessFamily = getRadioAccessFamily(readInt70, readString44);
                    parcel2.writeNoException();
                    parcel2.writeInt(radioAccessFamily);
                    return true;
                case 134:
                    return onTransact$uploadCallComposerPicture$(parcel, parcel2);
                case 135:
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    enableVideoCalling(readBoolean5);
                    parcel2.writeNoException();
                    return true;
                case 136:
                    java.lang.String readString45 = parcel.readString();
                    java.lang.String readString46 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isVideoCallingEnabled = isVideoCallingEnabled(readString45, readString46);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isVideoCallingEnabled);
                    return true;
                case 137:
                    return onTransact$canChangeDtmfToneLength$(parcel, parcel2);
                case 138:
                    return onTransact$isWorldPhone$(parcel, parcel2);
                case 139:
                    boolean isTtyModeSupported = isTtyModeSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isTtyModeSupported);
                    return true;
                case 140:
                    int readInt71 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isRttSupported = isRttSupported(readInt71);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isRttSupported);
                    return true;
                case 141:
                    boolean isHearingAidCompatibilitySupported = isHearingAidCompatibilitySupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isHearingAidCompatibilitySupported);
                    return true;
                case 142:
                    int readInt72 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isImsRegistered = isImsRegistered(readInt72);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isImsRegistered);
                    return true;
                case 143:
                    int readInt73 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isWifiCallingAvailable = isWifiCallingAvailable(readInt73);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isWifiCallingAvailable);
                    return true;
                case 144:
                    int readInt74 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isVideoTelephonyAvailable = isVideoTelephonyAvailable(readInt74);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isVideoTelephonyAvailable);
                    return true;
                case 145:
                    int readInt75 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int imsRegTechnologyForMmTel = getImsRegTechnologyForMmTel(readInt75);
                    parcel2.writeNoException();
                    parcel2.writeInt(imsRegTechnologyForMmTel);
                    return true;
                case 146:
                    java.lang.String readString47 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String deviceId = getDeviceId(readString47);
                    parcel2.writeNoException();
                    parcel2.writeString(deviceId);
                    return true;
                case 147:
                    java.lang.String readString48 = parcel.readString();
                    java.lang.String readString49 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String deviceIdWithFeature = getDeviceIdWithFeature(readString48, readString49);
                    parcel2.writeNoException();
                    parcel2.writeString(deviceIdWithFeature);
                    return true;
                case 148:
                    return onTransact$getImeiForSlot$(parcel, parcel2);
                case 149:
                    java.lang.String readString50 = parcel.readString();
                    java.lang.String readString51 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String primaryImei = getPrimaryImei(readString50, readString51);
                    parcel2.writeNoException();
                    parcel2.writeString(primaryImei);
                    return true;
                case 150:
                    int readInt76 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String typeAllocationCodeForSlot = getTypeAllocationCodeForSlot(readInt76);
                    parcel2.writeNoException();
                    parcel2.writeString(typeAllocationCodeForSlot);
                    return true;
                case 151:
                    return onTransact$getMeidForSlot$(parcel, parcel2);
                case 152:
                    int readInt77 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String manufacturerCodeForSlot = getManufacturerCodeForSlot(readInt77);
                    parcel2.writeNoException();
                    parcel2.writeString(manufacturerCodeForSlot);
                    return true;
                case 153:
                    return onTransact$getDeviceSoftwareVersionForSlot$(parcel, parcel2);
                case 154:
                    return onTransact$getSubIdForPhoneAccountHandle$(parcel, parcel2);
                case 155:
                    int readInt78 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.telecom.PhoneAccountHandle phoneAccountHandleForSubscriptionId = getPhoneAccountHandleForSubscriptionId(readInt78);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(phoneAccountHandleForSubscriptionId, 1);
                    return true;
                case 156:
                    int readInt79 = parcel.readInt();
                    java.lang.String readString52 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    factoryReset(readInt79, readString52);
                    parcel2.writeNoException();
                    return true;
                case 157:
                    int readInt80 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String simLocaleForSubscriber = getSimLocaleForSubscriber(readInt80);
                    parcel2.writeNoException();
                    parcel2.writeString(simLocaleForSubscriber);
                    return true;
                case 158:
                    android.os.ResultReceiver resultReceiver = (android.os.ResultReceiver) parcel.readTypedObject(android.os.ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestModemActivityInfo(resultReceiver);
                    return true;
                case 159:
                    return onTransact$getServiceStateForSubscriber$(parcel, parcel2);
                case 160:
                    android.telecom.PhoneAccountHandle phoneAccountHandle = (android.telecom.PhoneAccountHandle) parcel.readTypedObject(android.telecom.PhoneAccountHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.net.Uri voicemailRingtoneUri = getVoicemailRingtoneUri(phoneAccountHandle);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(voicemailRingtoneUri, 1);
                    return true;
                case 161:
                    return onTransact$setVoicemailRingtoneUri$(parcel, parcel2);
                case 162:
                    android.telecom.PhoneAccountHandle phoneAccountHandle2 = (android.telecom.PhoneAccountHandle) parcel.readTypedObject(android.telecom.PhoneAccountHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isVoicemailVibrationEnabled = isVoicemailVibrationEnabled(phoneAccountHandle2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isVoicemailVibrationEnabled);
                    return true;
                case 163:
                    return onTransact$setVoicemailVibrationEnabled$(parcel, parcel2);
                case 164:
                    int readInt81 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.List<java.lang.String> packagesWithCarrierPrivileges = getPackagesWithCarrierPrivileges(readInt81);
                    parcel2.writeNoException();
                    parcel2.writeStringList(packagesWithCarrierPrivileges);
                    return true;
                case 165:
                    java.util.List<java.lang.String> packagesWithCarrierPrivilegesForAllPhones = getPackagesWithCarrierPrivilegesForAllPhones();
                    parcel2.writeNoException();
                    parcel2.writeStringList(packagesWithCarrierPrivilegesForAllPhones);
                    return true;
                case 166:
                    int readInt82 = parcel.readInt();
                    int readInt83 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String aidForAppType = getAidForAppType(readInt82, readInt83);
                    parcel2.writeNoException();
                    parcel2.writeString(aidForAppType);
                    return true;
                case 167:
                    int readInt84 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String esn = getEsn(readInt84);
                    parcel2.writeNoException();
                    parcel2.writeString(esn);
                    return true;
                case 168:
                    int readInt85 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String cdmaPrlVersion = getCdmaPrlVersion(readInt85);
                    parcel2.writeNoException();
                    parcel2.writeString(cdmaPrlVersion);
                    return true;
                case 169:
                    java.util.List<android.telephony.TelephonyHistogram> telephonyHistograms = getTelephonyHistograms();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(telephonyHistograms, 1);
                    return true;
                case 170:
                    android.telephony.CarrierRestrictionRules carrierRestrictionRules = (android.telephony.CarrierRestrictionRules) parcel.readTypedObject(android.telephony.CarrierRestrictionRules.CREATOR);
                    parcel.enforceNoDataAvail();
                    int allowedCarriers = setAllowedCarriers(carrierRestrictionRules);
                    parcel2.writeNoException();
                    parcel2.writeInt(allowedCarriers);
                    return true;
                case 171:
                    android.telephony.CarrierRestrictionRules allowedCarriers2 = getAllowedCarriers();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(allowedCarriers2, 1);
                    return true;
                case 172:
                    int readInt86 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int subscriptionCarrierId = getSubscriptionCarrierId(readInt86);
                    parcel2.writeNoException();
                    parcel2.writeInt(subscriptionCarrierId);
                    return true;
                case 173:
                    int readInt87 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String subscriptionCarrierName = getSubscriptionCarrierName(readInt87);
                    parcel2.writeNoException();
                    parcel2.writeString(subscriptionCarrierName);
                    return true;
                case 174:
                    int readInt88 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int subscriptionSpecificCarrierId = getSubscriptionSpecificCarrierId(readInt88);
                    parcel2.writeNoException();
                    parcel2.writeInt(subscriptionSpecificCarrierId);
                    return true;
                case 175:
                    int readInt89 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String subscriptionSpecificCarrierName = getSubscriptionSpecificCarrierName(readInt89);
                    parcel2.writeNoException();
                    parcel2.writeString(subscriptionSpecificCarrierName);
                    return true;
                case 176:
                    return onTransact$getCarrierIdFromMccMnc$(parcel, parcel2);
                case 177:
                    int readInt90 = parcel.readInt();
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    carrierActionSetRadioEnabled(readInt90, readBoolean6);
                    parcel2.writeNoException();
                    return true;
                case 178:
                    int readInt91 = parcel.readInt();
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    carrierActionReportDefaultNetworkStatus(readInt91, readBoolean7);
                    parcel2.writeNoException();
                    return true;
                case 179:
                    int readInt92 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    carrierActionResetAll(readInt92);
                    parcel2.writeNoException();
                    return true;
                case 180:
                    return onTransact$getCallForwarding$(parcel, parcel2);
                case 181:
                    return onTransact$setCallForwarding$(parcel, parcel2);
                case 182:
                    int readInt93 = parcel.readInt();
                    com.android.internal.telephony.IIntegerConsumer asInterface4 = com.android.internal.telephony.IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getCallWaitingStatus(readInt93, asInterface4);
                    parcel2.writeNoException();
                    return true;
                case 183:
                    return onTransact$setCallWaitingStatus$(parcel, parcel2);
                case 184:
                    return onTransact$getClientRequestStats$(parcel, parcel2);
                case 185:
                    int readInt94 = parcel.readInt();
                    int readInt95 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSimPowerStateForSlot(readInt94, readInt95);
                    parcel2.writeNoException();
                    return true;
                case 186:
                    return onTransact$setSimPowerStateForSlotWithCallback$(parcel, parcel2);
                case 187:
                    return onTransact$getForbiddenPlmns$(parcel, parcel2);
                case 188:
                    return onTransact$setForbiddenPlmns$(parcel, parcel2);
                case 189:
                    int readInt96 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean emergencyCallbackMode = getEmergencyCallbackMode(readInt96);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(emergencyCallbackMode);
                    return true;
                case 190:
                    int readInt97 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.telephony.SignalStrength signalStrength = getSignalStrength(readInt97);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(signalStrength, 1);
                    return true;
                case 191:
                    int readInt98 = parcel.readInt();
                    java.lang.String readString53 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int cardIdForDefaultEuicc = getCardIdForDefaultEuicc(readInt98, readString53);
                    parcel2.writeNoException();
                    parcel2.writeInt(cardIdForDefaultEuicc);
                    return true;
                case 192:
                    java.lang.String readString54 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.telephony.UiccCardInfo> uiccCardsInfo = getUiccCardsInfo(readString54);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(uiccCardsInfo, 1);
                    return true;
                case 193:
                    java.lang.String readString55 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.telephony.UiccSlotInfo[] uiccSlotsInfo = getUiccSlotsInfo(readString55);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(uiccSlotsInfo, 1);
                    return true;
                case 194:
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    boolean switchSlots = switchSlots(createIntArray);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(switchSlots);
                    return true;
                case 195:
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.telephony.UiccSlotMapping.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean simSlotMapping = setSimSlotMapping(createTypedArrayList);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(simSlotMapping);
                    return true;
                case 196:
                    int readInt99 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isDataRoamingEnabled = isDataRoamingEnabled(readInt99);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDataRoamingEnabled);
                    return true;
                case 197:
                    int readInt100 = parcel.readInt();
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDataRoamingEnabled(readInt100, readBoolean8);
                    parcel2.writeNoException();
                    return true;
                case 198:
                    int readInt101 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int cdmaRoamingMode = getCdmaRoamingMode(readInt101);
                    parcel2.writeNoException();
                    parcel2.writeInt(cdmaRoamingMode);
                    return true;
                case 199:
                    int readInt102 = parcel.readInt();
                    int readInt103 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean cdmaRoamingMode2 = setCdmaRoamingMode(readInt102, readInt103);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(cdmaRoamingMode2);
                    return true;
                case 200:
                    int readInt104 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int cdmaSubscriptionMode = getCdmaSubscriptionMode(readInt104);
                    parcel2.writeNoException();
                    parcel2.writeInt(cdmaSubscriptionMode);
                    return true;
                case 201:
                    int readInt105 = parcel.readInt();
                    int readInt106 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean cdmaSubscriptionMode2 = setCdmaSubscriptionMode(readInt105, readInt106);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(cdmaSubscriptionMode2);
                    return true;
                case 202:
                    return onTransact$setCarrierTestOverride$(parcel, parcel2);
                case 203:
                    return onTransact$setCarrierServicePackageOverride$(parcel, parcel2);
                case 204:
                    int readInt107 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int carrierIdListVersion = getCarrierIdListVersion(readInt107);
                    parcel2.writeNoException();
                    parcel2.writeInt(carrierIdListVersion);
                    return true;
                case 205:
                    int readInt108 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    refreshUiccProfile(readInt108);
                    parcel2.writeNoException();
                    return true;
                case 206:
                    return onTransact$getNumberOfModemsWithSimultaneousDataConnections$(parcel, parcel2);
                case 207:
                    int readInt109 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int networkSelectionMode = getNetworkSelectionMode(readInt109);
                    parcel2.writeNoException();
                    parcel2.writeInt(networkSelectionMode);
                    return true;
                case 208:
                    boolean isInEmergencySmsMode = isInEmergencySmsMode();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isInEmergencySmsMode);
                    return true;
                case 209:
                    return onTransact$getRadioPowerState$(parcel, parcel2);
                case 210:
                    int readInt110 = parcel.readInt();
                    android.telephony.ims.aidl.IImsRegistrationCallback asInterface5 = android.telephony.ims.aidl.IImsRegistrationCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerImsRegistrationCallback(readInt110, asInterface5);
                    parcel2.writeNoException();
                    return true;
                case 211:
                    int readInt111 = parcel.readInt();
                    android.telephony.ims.aidl.IImsRegistrationCallback asInterface6 = android.telephony.ims.aidl.IImsRegistrationCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterImsRegistrationCallback(readInt111, asInterface6);
                    parcel2.writeNoException();
                    return true;
                case 212:
                    int readInt112 = parcel.readInt();
                    android.telephony.ims.aidl.IImsRegistrationCallback asInterface7 = android.telephony.ims.aidl.IImsRegistrationCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerImsEmergencyRegistrationCallback(readInt112, asInterface7);
                    parcel2.writeNoException();
                    return true;
                case 213:
                    int readInt113 = parcel.readInt();
                    android.telephony.ims.aidl.IImsRegistrationCallback asInterface8 = android.telephony.ims.aidl.IImsRegistrationCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterImsEmergencyRegistrationCallback(readInt113, asInterface8);
                    parcel2.writeNoException();
                    return true;
                case 214:
                    int readInt114 = parcel.readInt();
                    com.android.internal.telephony.IIntegerConsumer asInterface9 = com.android.internal.telephony.IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getImsMmTelRegistrationState(readInt114, asInterface9);
                    parcel2.writeNoException();
                    return true;
                case 215:
                    int readInt115 = parcel.readInt();
                    com.android.internal.telephony.IIntegerConsumer asInterface10 = com.android.internal.telephony.IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getImsMmTelRegistrationTransportType(readInt115, asInterface10);
                    parcel2.writeNoException();
                    return true;
                case 216:
                    int readInt116 = parcel.readInt();
                    android.telephony.ims.aidl.IImsCapabilityCallback asInterface11 = android.telephony.ims.aidl.IImsCapabilityCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerMmTelCapabilityCallback(readInt116, asInterface11);
                    parcel2.writeNoException();
                    return true;
                case 217:
                    int readInt117 = parcel.readInt();
                    android.telephony.ims.aidl.IImsCapabilityCallback asInterface12 = android.telephony.ims.aidl.IImsCapabilityCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterMmTelCapabilityCallback(readInt117, asInterface12);
                    parcel2.writeNoException();
                    return true;
                case 218:
                    return onTransact$isCapable$(parcel, parcel2);
                case 219:
                    return onTransact$isAvailable$(parcel, parcel2);
                case 220:
                    return onTransact$isMmTelCapabilitySupported$(parcel, parcel2);
                case 221:
                    int readInt118 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isAdvancedCallingSettingEnabled = isAdvancedCallingSettingEnabled(readInt118);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAdvancedCallingSettingEnabled);
                    return true;
                case 222:
                    int readInt119 = parcel.readInt();
                    boolean readBoolean9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAdvancedCallingSettingEnabled(readInt119, readBoolean9);
                    parcel2.writeNoException();
                    return true;
                case 223:
                    int readInt120 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isVtSettingEnabled = isVtSettingEnabled(readInt120);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isVtSettingEnabled);
                    return true;
                case 224:
                    int readInt121 = parcel.readInt();
                    boolean readBoolean10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setVtSettingEnabled(readInt121, readBoolean10);
                    parcel2.writeNoException();
                    return true;
                case 225:
                    int readInt122 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isVoWiFiSettingEnabled = isVoWiFiSettingEnabled(readInt122);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isVoWiFiSettingEnabled);
                    return true;
                case 226:
                    int readInt123 = parcel.readInt();
                    boolean readBoolean11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setVoWiFiSettingEnabled(readInt123, readBoolean11);
                    parcel2.writeNoException();
                    return true;
                case 227:
                    int readInt124 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isCrossSimCallingEnabledByUser = isCrossSimCallingEnabledByUser(readInt124);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCrossSimCallingEnabledByUser);
                    return true;
                case 228:
                    int readInt125 = parcel.readInt();
                    boolean readBoolean12 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setCrossSimCallingEnabled(readInt125, readBoolean12);
                    parcel2.writeNoException();
                    return true;
                case 229:
                    int readInt126 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isVoWiFiRoamingSettingEnabled = isVoWiFiRoamingSettingEnabled(readInt126);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isVoWiFiRoamingSettingEnabled);
                    return true;
                case 230:
                    int readInt127 = parcel.readInt();
                    boolean readBoolean13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setVoWiFiRoamingSettingEnabled(readInt127, readBoolean13);
                    parcel2.writeNoException();
                    return true;
                case 231:
                    return onTransact$setVoWiFiNonPersistent$(parcel, parcel2);
                case 232:
                    int readInt128 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int voWiFiModeSetting = getVoWiFiModeSetting(readInt128);
                    parcel2.writeNoException();
                    parcel2.writeInt(voWiFiModeSetting);
                    return true;
                case 233:
                    int readInt129 = parcel.readInt();
                    int readInt130 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setVoWiFiModeSetting(readInt129, readInt130);
                    parcel2.writeNoException();
                    return true;
                case 234:
                    int readInt131 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int voWiFiRoamingModeSetting = getVoWiFiRoamingModeSetting(readInt131);
                    parcel2.writeNoException();
                    parcel2.writeInt(voWiFiRoamingModeSetting);
                    return true;
                case 235:
                    int readInt132 = parcel.readInt();
                    int readInt133 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setVoWiFiRoamingModeSetting(readInt132, readInt133);
                    parcel2.writeNoException();
                    return true;
                case 236:
                    int readInt134 = parcel.readInt();
                    boolean readBoolean14 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setRttCapabilitySetting(readInt134, readBoolean14);
                    parcel2.writeNoException();
                    return true;
                case 237:
                    int readInt135 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isTtyOverVolteEnabled = isTtyOverVolteEnabled(readInt135);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isTtyOverVolteEnabled);
                    return true;
                case 238:
                    java.lang.String readString56 = parcel.readString();
                    java.lang.String readString57 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.util.Map emergencyNumberList = getEmergencyNumberList(readString56, readString57);
                    parcel2.writeNoException();
                    parcel2.writeMap(emergencyNumberList);
                    return true;
                case 239:
                    java.lang.String readString58 = parcel.readString();
                    boolean readBoolean15 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isEmergencyNumber = isEmergencyNumber(readString58, readBoolean15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isEmergencyNumber);
                    return true;
                case 240:
                    int readInt136 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.List<java.lang.String> certsFromCarrierPrivilegeAccessRules = getCertsFromCarrierPrivilegeAccessRules(readInt136);
                    parcel2.writeNoException();
                    parcel2.writeStringList(certsFromCarrierPrivilegeAccessRules);
                    return true;
                case 241:
                    int readInt137 = parcel.readInt();
                    android.telephony.ims.aidl.IImsConfigCallback asInterface13 = android.telephony.ims.aidl.IImsConfigCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerImsProvisioningChangedCallback(readInt137, asInterface13);
                    parcel2.writeNoException();
                    return true;
                case 242:
                    int readInt138 = parcel.readInt();
                    android.telephony.ims.aidl.IImsConfigCallback asInterface14 = android.telephony.ims.aidl.IImsConfigCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterImsProvisioningChangedCallback(readInt138, asInterface14);
                    parcel2.writeNoException();
                    return true;
                case 243:
                    int readInt139 = parcel.readInt();
                    android.telephony.ims.aidl.IFeatureProvisioningCallback asInterface15 = android.telephony.ims.aidl.IFeatureProvisioningCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerFeatureProvisioningChangedCallback(readInt139, asInterface15);
                    parcel2.writeNoException();
                    return true;
                case 244:
                    int readInt140 = parcel.readInt();
                    android.telephony.ims.aidl.IFeatureProvisioningCallback asInterface16 = android.telephony.ims.aidl.IFeatureProvisioningCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterFeatureProvisioningChangedCallback(readInt140, asInterface16);
                    parcel2.writeNoException();
                    return true;
                case 245:
                    return onTransact$setImsProvisioningStatusForCapability$(parcel, parcel2);
                case 246:
                    return onTransact$getImsProvisioningStatusForCapability$(parcel, parcel2);
                case 247:
                    return onTransact$getRcsProvisioningStatusForCapability$(parcel, parcel2);
                case 248:
                    return onTransact$setRcsProvisioningStatusForCapability$(parcel, parcel2);
                case 249:
                    int readInt141 = parcel.readInt();
                    int readInt142 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int imsProvisioningInt = getImsProvisioningInt(readInt141, readInt142);
                    parcel2.writeNoException();
                    parcel2.writeInt(imsProvisioningInt);
                    return true;
                case 250:
                    int readInt143 = parcel.readInt();
                    int readInt144 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String imsProvisioningString = getImsProvisioningString(readInt143, readInt144);
                    parcel2.writeNoException();
                    parcel2.writeString(imsProvisioningString);
                    return true;
                case 251:
                    return onTransact$setImsProvisioningInt$(parcel, parcel2);
                case 252:
                    return onTransact$setImsProvisioningString$(parcel, parcel2);
                case 253:
                    startEmergencyCallbackMode();
                    parcel2.writeNoException();
                    return true;
                case 254:
                    int readInt145 = parcel.readInt();
                    android.telephony.emergency.EmergencyNumber emergencyNumber = (android.telephony.emergency.EmergencyNumber) parcel.readTypedObject(android.telephony.emergency.EmergencyNumber.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateEmergencyNumberListTestMode(readInt145, emergencyNumber);
                    parcel2.writeNoException();
                    return true;
                case 255:
                    java.util.List<java.lang.String> emergencyNumberListTestMode = getEmergencyNumberListTestMode();
                    parcel2.writeNoException();
                    parcel2.writeStringList(emergencyNumberListTestMode);
                    return true;
                case 256:
                    int readInt146 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int emergencyNumberDbVersion = getEmergencyNumberDbVersion(readInt146);
                    parcel2.writeNoException();
                    parcel2.writeInt(emergencyNumberDbVersion);
                    return true;
                case 257:
                    notifyOtaEmergencyNumberDbInstalled();
                    parcel2.writeNoException();
                    return true;
                case 258:
                    android.os.ParcelFileDescriptor parcelFileDescriptor = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateOtaEmergencyNumberDbFilePath(parcelFileDescriptor);
                    parcel2.writeNoException();
                    return true;
                case 259:
                    resetOtaEmergencyNumberDbFilePath();
                    parcel2.writeNoException();
                    return true;
                case 260:
                    int readInt147 = parcel.readInt();
                    boolean readBoolean16 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean enableModemForSlot = enableModemForSlot(readInt147, readBoolean16);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enableModemForSlot);
                    return true;
                case 261:
                    boolean readBoolean17 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setMultiSimCarrierRestriction(readBoolean17);
                    parcel2.writeNoException();
                    return true;
                case 262:
                    java.lang.String readString59 = parcel.readString();
                    java.lang.String readString60 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int isMultiSimSupported = isMultiSimSupported(readString59, readString60);
                    parcel2.writeNoException();
                    parcel2.writeInt(isMultiSimSupported);
                    return true;
                case 263:
                    int readInt148 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    switchMultiSimConfig(readInt148);
                    parcel2.writeNoException();
                    return true;
                case 264:
                    return onTransact$doesSwitchMultiSimConfigTriggerReboot$(parcel, parcel2);
                case 265:
                    java.lang.String readString61 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.telephony.UiccSlotMapping> slotsMapping = getSlotsMapping(readString61);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(slotsMapping, 1);
                    return true;
                case 266:
                    int radioHalVersion = getRadioHalVersion();
                    parcel2.writeNoException();
                    parcel2.writeInt(radioHalVersion);
                    return true;
                case 267:
                    int readInt149 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int halVersion = getHalVersion(readInt149);
                    parcel2.writeNoException();
                    parcel2.writeInt(halVersion);
                    return true;
                case 268:
                    java.lang.String currentPackageName = getCurrentPackageName();
                    parcel2.writeNoException();
                    parcel2.writeString(currentPackageName);
                    return true;
                case 269:
                    int readInt150 = parcel.readInt();
                    int readInt151 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isApplicationOnUicc = isApplicationOnUicc(readInt150, readInt151);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isApplicationOnUicc);
                    return true;
                case 270:
                    return onTransact$isModemEnabledForSlot$(parcel, parcel2);
                case 271:
                    return onTransact$isDataEnabledForApn$(parcel, parcel2);
                case 272:
                    int readInt152 = parcel.readInt();
                    int readInt153 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isApnMetered = isApnMetered(readInt152, readInt153);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isApnMetered);
                    return true;
                case 273:
                    return onTransact$setSystemSelectionChannels$(parcel, parcel2);
                case 274:
                    int readInt154 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.telephony.RadioAccessSpecifier> systemSelectionChannels = getSystemSelectionChannels(readInt154);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(systemSelectionChannels, 1);
                    return true;
                case 275:
                    return onTransact$isMvnoMatched$(parcel, parcel2);
                case 276:
                    return onTransact$enqueueSmsPickResult$(parcel, parcel2);
                case 277:
                    showSwitchToManagedProfileDialog();
                    return true;
                case 278:
                    int readInt155 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String mmsUserAgent = getMmsUserAgent(readInt155);
                    parcel2.writeNoException();
                    parcel2.writeString(mmsUserAgent);
                    return true;
                case 279:
                    int readInt156 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String mmsUAProfUrl = getMmsUAProfUrl(readInt156);
                    parcel2.writeNoException();
                    parcel2.writeString(mmsUAProfUrl);
                    return true;
                case 280:
                    return onTransact$setMobileDataPolicyEnabled$(parcel, parcel2);
                case 281:
                    int readInt157 = parcel.readInt();
                    int readInt158 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isMobileDataPolicyEnabled = isMobileDataPolicyEnabled(readInt157, readInt158);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isMobileDataPolicyEnabled);
                    return true;
                case 282:
                    boolean readBoolean18 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setCepEnabled(readBoolean18);
                    return true;
                case 283:
                    return onTransact$notifyRcsAutoConfigurationReceived$(parcel, parcel2);
                case 284:
                    int readInt159 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isIccLockEnabled = isIccLockEnabled(readInt159);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isIccLockEnabled);
                    return true;
                case 285:
                    return onTransact$setIccLockEnabled$(parcel, parcel2);
                case 286:
                    return onTransact$changeIccLockPassword$(parcel, parcel2);
                case 287:
                    requestUserActivityNotification();
                    return true;
                case 288:
                    userActivity();
                    return true;
                case 289:
                    int readInt160 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String manualNetworkSelectionPlmn = getManualNetworkSelectionPlmn(readInt160);
                    parcel2.writeNoException();
                    parcel2.writeString(manualNetworkSelectionPlmn);
                    return true;
                case 290:
                    boolean canConnectTo5GInDsdsMode = canConnectTo5GInDsdsMode();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canConnectTo5GInDsdsMode);
                    return true;
                case 291:
                    return onTransact$getEquivalentHomePlmns$(parcel, parcel2);
                case 292:
                    int readInt161 = parcel.readInt();
                    boolean readBoolean19 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int voNrEnabled = setVoNrEnabled(readInt161, readBoolean19);
                    parcel2.writeNoException();
                    parcel2.writeInt(voNrEnabled);
                    return true;
                case 293:
                    int readInt162 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isVoNrEnabled = isVoNrEnabled(readInt162);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isVoNrEnabled);
                    return true;
                case 294:
                    int readInt163 = parcel.readInt();
                    int readInt164 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int nrDualConnectivityState = setNrDualConnectivityState(readInt163, readInt164);
                    parcel2.writeNoException();
                    parcel2.writeInt(nrDualConnectivityState);
                    return true;
                case 295:
                    int readInt165 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isNrDualConnectivityEnabled = isNrDualConnectivityEnabled(readInt165);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isNrDualConnectivityEnabled);
                    return true;
                case 296:
                    java.lang.String readString62 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isRadioInterfaceCapabilitySupported = isRadioInterfaceCapabilitySupported(readString62);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isRadioInterfaceCapabilitySupported);
                    return true;
                case 297:
                    return onTransact$sendThermalMitigationRequest$(parcel, parcel2);
                case 298:
                    return onTransact$bootstrapAuthenticationRequest$(parcel, parcel2);
                case 299:
                    int readInt166 = parcel.readInt();
                    java.lang.String readString63 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean boundGbaServiceOverride = setBoundGbaServiceOverride(readInt166, readString63);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(boundGbaServiceOverride);
                    return true;
                case 300:
                    int readInt167 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String boundGbaService = getBoundGbaService(readInt167);
                    parcel2.writeNoException();
                    parcel2.writeString(boundGbaService);
                    return true;
                case 301:
                    int readInt168 = parcel.readInt();
                    int readInt169 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean gbaReleaseTimeOverride = setGbaReleaseTimeOverride(readInt168, readInt169);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(gbaReleaseTimeOverride);
                    return true;
                case 302:
                    int readInt170 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int gbaReleaseTime = getGbaReleaseTime(readInt170);
                    parcel2.writeNoException();
                    parcel2.writeInt(gbaReleaseTime);
                    return true;
                case 303:
                    int readInt171 = parcel.readInt();
                    android.telephony.ims.RcsClientConfiguration rcsClientConfiguration = (android.telephony.ims.RcsClientConfiguration) parcel.readTypedObject(android.telephony.ims.RcsClientConfiguration.CREATOR);
                    parcel.enforceNoDataAvail();
                    setRcsClientConfiguration(readInt171, rcsClientConfiguration);
                    parcel2.writeNoException();
                    return true;
                case 304:
                    int readInt172 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isRcsVolteSingleRegistrationCapable = isRcsVolteSingleRegistrationCapable(readInt172);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isRcsVolteSingleRegistrationCapable);
                    return true;
                case 305:
                    int readInt173 = parcel.readInt();
                    android.telephony.ims.aidl.IRcsConfigCallback asInterface17 = android.telephony.ims.aidl.IRcsConfigCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerRcsProvisioningCallback(readInt173, asInterface17);
                    parcel2.writeNoException();
                    return true;
                case 306:
                    int readInt174 = parcel.readInt();
                    android.telephony.ims.aidl.IRcsConfigCallback asInterface18 = android.telephony.ims.aidl.IRcsConfigCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterRcsProvisioningCallback(readInt174, asInterface18);
                    parcel2.writeNoException();
                    return true;
                case 307:
                    int readInt175 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    triggerRcsReconfiguration(readInt175);
                    parcel2.writeNoException();
                    return true;
                case 308:
                    boolean readBoolean20 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setRcsSingleRegistrationTestModeEnabled(readBoolean20);
                    parcel2.writeNoException();
                    return true;
                case 309:
                    boolean rcsSingleRegistrationTestModeEnabled = getRcsSingleRegistrationTestModeEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(rcsSingleRegistrationTestModeEnabled);
                    return true;
                case 310:
                    java.lang.String readString64 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setDeviceSingleRegistrationEnabledOverride(readString64);
                    parcel2.writeNoException();
                    return true;
                case 311:
                    boolean deviceSingleRegistrationEnabled = getDeviceSingleRegistrationEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(deviceSingleRegistrationEnabled);
                    return true;
                case 312:
                    int readInt176 = parcel.readInt();
                    java.lang.String readString65 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean carrierSingleRegistrationEnabledOverride = setCarrierSingleRegistrationEnabledOverride(readInt176, readString65);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(carrierSingleRegistrationEnabledOverride);
                    return true;
                case 313:
                    int readInt177 = parcel.readInt();
                    int readInt178 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendDeviceToDeviceMessage(readInt177, readInt178);
                    parcel2.writeNoException();
                    return true;
                case 314:
                    java.lang.String readString66 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setActiveDeviceToDeviceTransport(readString66);
                    parcel2.writeNoException();
                    return true;
                case 315:
                    boolean readBoolean21 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDeviceToDeviceForceEnabled(readBoolean21);
                    parcel2.writeNoException();
                    return true;
                case 316:
                    int readInt179 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean carrierSingleRegistrationEnabled = getCarrierSingleRegistrationEnabled(readInt179);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(carrierSingleRegistrationEnabled);
                    return true;
                case 317:
                    int readInt180 = parcel.readInt();
                    java.lang.String readString67 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean imsFeatureValidationOverride = setImsFeatureValidationOverride(readInt180, readString67);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(imsFeatureValidationOverride);
                    return true;
                case 318:
                    int readInt181 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean imsFeatureValidationOverride2 = getImsFeatureValidationOverride(readInt181);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(imsFeatureValidationOverride2);
                    return true;
                case 319:
                    java.lang.String mobileProvisioningUrl = getMobileProvisioningUrl();
                    parcel2.writeNoException();
                    parcel2.writeString(mobileProvisioningUrl);
                    return true;
                case 320:
                    int readInt182 = parcel.readInt();
                    java.lang.String readString68 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int removeContactFromEab = removeContactFromEab(readInt182, readString68);
                    parcel2.writeNoException();
                    parcel2.writeInt(removeContactFromEab);
                    return true;
                case 321:
                    java.lang.String readString69 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String contactFromEab = getContactFromEab(readString69);
                    parcel2.writeNoException();
                    parcel2.writeString(contactFromEab);
                    return true;
                case 322:
                    java.lang.String readString70 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String capabilityFromEab = getCapabilityFromEab(readString70);
                    parcel2.writeNoException();
                    parcel2.writeString(capabilityFromEab);
                    return true;
                case 323:
                    boolean deviceUceEnabled = getDeviceUceEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(deviceUceEnabled);
                    return true;
                case 324:
                    boolean readBoolean22 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDeviceUceEnabled(readBoolean22);
                    parcel2.writeNoException();
                    return true;
                case 325:
                    int readInt183 = parcel.readInt();
                    java.util.ArrayList<java.lang.String> createStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    android.telephony.ims.RcsContactUceCapability addUceRegistrationOverrideShell = addUceRegistrationOverrideShell(readInt183, createStringArrayList);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(addUceRegistrationOverrideShell, 1);
                    return true;
                case 326:
                    int readInt184 = parcel.readInt();
                    java.util.ArrayList<java.lang.String> createStringArrayList2 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    android.telephony.ims.RcsContactUceCapability removeUceRegistrationOverrideShell = removeUceRegistrationOverrideShell(readInt184, createStringArrayList2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(removeUceRegistrationOverrideShell, 1);
                    return true;
                case 327:
                    int readInt185 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.telephony.ims.RcsContactUceCapability clearUceRegistrationOverrideShell = clearUceRegistrationOverrideShell(readInt185);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(clearUceRegistrationOverrideShell, 1);
                    return true;
                case 328:
                    int readInt186 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.telephony.ims.RcsContactUceCapability latestRcsContactUceCapabilityShell = getLatestRcsContactUceCapabilityShell(readInt186);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(latestRcsContactUceCapabilityShell, 1);
                    return true;
                case 329:
                    int readInt187 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String lastUcePidfXmlShell = getLastUcePidfXmlShell(readInt187);
                    parcel2.writeNoException();
                    parcel2.writeString(lastUcePidfXmlShell);
                    return true;
                case 330:
                    int readInt188 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean removeUceRequestDisallowedStatus = removeUceRequestDisallowedStatus(readInt188);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeUceRequestDisallowedStatus);
                    return true;
                case 331:
                    int readInt189 = parcel.readInt();
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean capabilitiesRequestTimeout = setCapabilitiesRequestTimeout(readInt189, readLong);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(capabilitiesRequestTimeout);
                    return true;
                case 332:
                    return onTransact$setSignalStrengthUpdateRequest$(parcel, parcel2);
                case 333:
                    return onTransact$clearSignalStrengthUpdateRequest$(parcel, parcel2);
                case 334:
                    android.telephony.PhoneCapability phoneCapability = getPhoneCapability();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(phoneCapability, 1);
                    return true;
                case 335:
                    int prepareForUnattendedReboot = prepareForUnattendedReboot();
                    parcel2.writeNoException();
                    parcel2.writeInt(prepareForUnattendedReboot);
                    return true;
                case 336:
                    android.os.ResultReceiver resultReceiver2 = (android.os.ResultReceiver) parcel.readTypedObject(android.os.ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    getSlicingConfig(resultReceiver2);
                    parcel2.writeNoException();
                    return true;
                case 337:
                    int readInt190 = parcel.readInt();
                    int readInt191 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isPremiumCapabilityAvailableForPurchase = isPremiumCapabilityAvailableForPurchase(readInt190, readInt191);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPremiumCapabilityAvailableForPurchase);
                    return true;
                case 338:
                    return onTransact$purchasePremiumCapability$(parcel, parcel2);
                case 339:
                    return onTransact$registerImsStateCallback$(parcel, parcel2);
                case 340:
                    com.android.internal.telephony.IImsStateCallback asInterface19 = com.android.internal.telephony.IImsStateCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterImsStateCallback(asInterface19);
                    parcel2.writeNoException();
                    return true;
                case 341:
                    return onTransact$getLastKnownCellIdentity$(parcel, parcel2);
                case 342:
                    java.lang.String readString71 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean modemService = setModemService(readString71);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(modemService);
                    return true;
                case 343:
                    java.lang.String modemService2 = getModemService();
                    parcel2.writeNoException();
                    parcel2.writeString(modemService2);
                    return true;
                case 344:
                    return onTransact$isProvisioningRequiredForCapability$(parcel, parcel2);
                case 345:
                    return onTransact$isRcsProvisioningRequiredForCapability$(parcel, parcel2);
                case 346:
                    return onTransact$setVoiceServiceStateOverride$(parcel, parcel2);
                case 347:
                    int readInt192 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String carrierServicePackageNameForLogicalSlot = getCarrierServicePackageNameForLogicalSlot(readInt192);
                    parcel2.writeNoException();
                    parcel2.writeString(carrierServicePackageNameForLogicalSlot);
                    return true;
                case 348:
                    boolean readBoolean23 = parcel.readBoolean();
                    java.lang.String readString72 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setRemovableEsimAsDefaultEuicc(readBoolean23, readString72);
                    parcel2.writeNoException();
                    return true;
                case 349:
                    java.lang.String readString73 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isRemovableEsimDefaultEuicc = isRemovableEsimDefaultEuicc(readString73);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isRemovableEsimDefaultEuicc);
                    return true;
                case 350:
                    return onTransact$getDefaultRespondViaMessageApplication$(parcel, parcel2);
                case 351:
                    int readInt193 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int simStateForSlotIndex = getSimStateForSlotIndex(readInt193);
                    parcel2.writeNoException();
                    parcel2.writeInt(simStateForSlotIndex);
                    return true;
                case 352:
                    return onTransact$persistEmergencyCallDiagnosticData$(parcel, parcel2);
                case 353:
                    boolean readBoolean24 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setNullCipherAndIntegrityEnabled(readBoolean24);
                    parcel2.writeNoException();
                    return true;
                case 354:
                    boolean isNullCipherAndIntegrityPreferenceEnabled = isNullCipherAndIntegrityPreferenceEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isNullCipherAndIntegrityPreferenceEnabled);
                    return true;
                case 355:
                    int readInt194 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.telephony.CellBroadcastIdRange> cellBroadcastIdRanges = getCellBroadcastIdRanges(readInt194);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(cellBroadcastIdRanges, 1);
                    return true;
                case 356:
                    return onTransact$setCellBroadcastIdRanges$(parcel, parcel2);
                case 357:
                    boolean isDomainSelectionSupported = isDomainSelectionSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDomainSelectionSupported);
                    return true;
                case 358:
                    return onTransact$getCarrierRestrictionStatus$(parcel, parcel2);
                case 359:
                    return onTransact$requestSatelliteEnabled$(parcel, parcel2);
                case 360:
                    return onTransact$requestIsSatelliteEnabled$(parcel, parcel2);
                case 361:
                    return onTransact$requestIsDemoModeEnabled$(parcel, parcel2);
                case 362:
                    return onTransact$requestIsEmergencyModeEnabled$(parcel, parcel2);
                case 363:
                    return onTransact$requestIsSatelliteSupported$(parcel, parcel2);
                case 364:
                    return onTransact$requestSatelliteCapabilities$(parcel, parcel2);
                case 365:
                    return onTransact$startSatelliteTransmissionUpdates$(parcel, parcel2);
                case 366:
                    return onTransact$stopSatelliteTransmissionUpdates$(parcel, parcel2);
                case 367:
                    return onTransact$provisionSatelliteService$(parcel, parcel2);
                case 368:
                    return onTransact$deprovisionSatelliteService$(parcel, parcel2);
                case 369:
                    return onTransact$registerForSatelliteProvisionStateChanged$(parcel, parcel2);
                case 370:
                    return onTransact$unregisterForSatelliteProvisionStateChanged$(parcel, parcel2);
                case 371:
                    return onTransact$requestIsSatelliteProvisioned$(parcel, parcel2);
                case 372:
                    return onTransact$registerForSatelliteModemStateChanged$(parcel, parcel2);
                case 373:
                    return onTransact$unregisterForModemStateChanged$(parcel, parcel2);
                case 374:
                    return onTransact$registerForIncomingDatagram$(parcel, parcel2);
                case 375:
                    return onTransact$unregisterForIncomingDatagram$(parcel, parcel2);
                case 376:
                    return onTransact$pollPendingDatagrams$(parcel, parcel2);
                case 377:
                    return onTransact$sendDatagram$(parcel, parcel2);
                case 378:
                    return onTransact$requestIsCommunicationAllowedForCurrentLocation$(parcel, parcel2);
                case 379:
                    return onTransact$requestTimeForNextSatelliteVisibility$(parcel, parcel2);
                case 380:
                    return onTransact$setDeviceAlignedWithSatellite$(parcel, parcel2);
                case 381:
                    java.lang.String readString74 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean satelliteServicePackageName = setSatelliteServicePackageName(readString74);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(satelliteServicePackageName);
                    return true;
                case 382:
                    java.lang.String readString75 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean satelliteGatewayServicePackageName = setSatelliteGatewayServicePackageName(readString75);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(satelliteGatewayServicePackageName);
                    return true;
                case 383:
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean satelliteListeningTimeoutDuration = setSatelliteListeningTimeoutDuration(readLong2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(satelliteListeningTimeoutDuration);
                    return true;
                case 384:
                    return onTransact$setSatellitePointingUiClassName$(parcel, parcel2);
                case 385:
                    return onTransact$setDatagramControllerTimeoutDuration$(parcel, parcel2);
                case 386:
                    return onTransact$setSatelliteControllerTimeoutDuration$(parcel, parcel2);
                case 387:
                    return onTransact$setEmergencyCallToSatelliteHandoverType$(parcel, parcel2);
                case 388:
                    return onTransact$setCountryCodes$(parcel, parcel2);
                case 389:
                    return onTransact$setSatelliteAccessControlOverlayConfigs$(parcel, parcel2);
                case 390:
                    return onTransact$setOemEnabledSatelliteProvisionStatus$(parcel, parcel2);
                case 391:
                    return onTransact$getShaIdFromAllowList$(parcel, parcel2);
                case 392:
                    return onTransact$addAttachRestrictionForCarrier$(parcel, parcel2);
                case 393:
                    return onTransact$removeAttachRestrictionForCarrier$(parcel, parcel2);
                case 394:
                    int readInt195 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int[] attachRestrictionReasonsForCarrier = getAttachRestrictionReasonsForCarrier(readInt195);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(attachRestrictionReasonsForCarrier);
                    return true;
                case 395:
                    return onTransact$requestNtnSignalStrength$(parcel, parcel2);
                case 396:
                    return onTransact$registerForNtnSignalStrengthChanged$(parcel, parcel2);
                case 397:
                    return onTransact$unregisterForNtnSignalStrengthChanged$(parcel, parcel2);
                case 398:
                    return onTransact$registerForCapabilitiesChanged$(parcel, parcel2);
                case 399:
                    return onTransact$unregisterForCapabilitiesChanged$(parcel, parcel2);
                case 400:
                    boolean readBoolean25 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean shouldSendDatagramToModemInDemoMode = setShouldSendDatagramToModemInDemoMode(readBoolean25);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(shouldSendDatagramToModemInDemoMode);
                    return true;
                case 401:
                    android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean domainSelectionServiceOverride = setDomainSelectionServiceOverride(componentName);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(domainSelectionServiceOverride);
                    return true;
                case 402:
                    boolean clearDomainSelectionServiceOverride = clearDomainSelectionServiceOverride();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(clearDomainSelectionServiceOverride);
                    return true;
                case 403:
                    boolean readBoolean26 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setEnableCellularIdentifierDisclosureNotifications(readBoolean26);
                    parcel2.writeNoException();
                    return true;
                case 404:
                    boolean isCellularIdentifierDisclosureNotificationsEnabled = isCellularIdentifierDisclosureNotificationsEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCellularIdentifierDisclosureNotificationsEnabled);
                    return true;
                case 405:
                    boolean readBoolean27 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setNullCipherNotificationsEnabled(readBoolean27);
                    parcel2.writeNoException();
                    return true;
                case 406:
                    boolean isNullCipherNotificationsEnabled = isNullCipherNotificationsEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isNullCipherNotificationsEnabled);
                    return true;
                case 407:
                    int readInt196 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.List<java.lang.String> satellitePlmnsForCarrier = getSatellitePlmnsForCarrier(readInt196);
                    parcel2.writeNoException();
                    parcel2.writeStringList(satellitePlmnsForCarrier);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.telephony.ITelephony {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.ITelephony
            public void dial(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void call(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isRadioOn(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isRadioOnWithFeature(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isRadioOnForSubscriber(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isRadioOnForSubscriberWithFeature(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setCallComposerStatus(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getCallComposerStatus(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean supplyPinForSubscriber(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean supplyPukForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int[] supplyPinReportResultForSubscriber(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int[] supplyPukReportResultForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean handlePinMmi(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void handleUssdRequest(int i, java.lang.String str, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean handlePinMmiForSubscriber(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void toggleRadioOnOff() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void toggleRadioOnOffForSubscriber(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setRadio(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setRadioForSubscriber(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setRadioPower(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean requestRadioPowerOffForReason(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean clearRadioPowerOffForReason(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.util.List getRadioPowerOffReasons(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readArrayList(getClass().getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void updateServiceLocation() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void updateServiceLocationWithPackageName(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void enableLocationUpdates() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void disableLocationUpdates() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean enableDataConnectivity(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean disableDataConnectivity(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isDataConnectivityPossible(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public android.telephony.CellIdentity getCellLocation(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.telephony.CellIdentity) obtain2.readTypedObject(android.telephony.CellIdentity.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.lang.String getNetworkCountryIsoForPhone(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.util.List<android.telephony.NeighboringCellInfo> getNeighboringCellInfo(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.telephony.NeighboringCellInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getCallState() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getCallStateForSubscription(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getDataActivity() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getDataActivityForSubId(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getDataState() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getDataStateForSubId(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getActivePhoneType() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getActivePhoneTypeForSlot(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getCdmaEriIconIndex(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getCdmaEriIconIndexForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getCdmaEriIconMode(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getCdmaEriIconModeForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.lang.String getCdmaEriText(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.lang.String getCdmaEriTextForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean needsOtaServiceProvisioning() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setVoiceMailNumber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setVoiceActivationState(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setDataActivationState(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getVoiceActivationState(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getDataActivationState(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getVoiceMessageCountForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isConcurrentVoiceAndDataAllowed(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public android.os.Bundle getVisualVoicemailSettings(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.Bundle) obtain2.readTypedObject(android.os.Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.lang.String getVisualVoicemailPackageName(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void enableVisualVoicemailSmsFilter(java.lang.String str, int i, android.telephony.VisualVoicemailSmsFilterSettings visualVoicemailSmsFilterSettings) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(visualVoicemailSmsFilterSettings, 0);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void disableVisualVoicemailSmsFilter(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(59, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public android.telephony.VisualVoicemailSmsFilterSettings getVisualVoicemailSmsFilterSettings(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.telephony.VisualVoicemailSmsFilterSettings) obtain2.readTypedObject(android.telephony.VisualVoicemailSmsFilterSettings.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public android.telephony.VisualVoicemailSmsFilterSettings getActiveVisualVoicemailSmsFilterSettings(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.telephony.VisualVoicemailSmsFilterSettings) obtain2.readTypedObject(android.telephony.VisualVoicemailSmsFilterSettings.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void sendVisualVoicemailSmsForSubscriber(java.lang.String str, java.lang.String str2, int i, java.lang.String str3, int i2, java.lang.String str4, android.app.PendingIntent pendingIntent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeString(str3);
                    obtain.writeInt(i2);
                    obtain.writeString(str4);
                    obtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void sendDialerSpecialCode(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getNetworkTypeForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getDataNetworkType(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getDataNetworkTypeForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getVoiceNetworkTypeForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean hasIccCard() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    this.mRemote.transact(68, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean hasIccCardUsingSlotIndex(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(69, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getLteOnCdmaMode(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(70, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getLteOnCdmaModeForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(71, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.util.List<android.telephony.CellInfo> getAllCellInfo(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(72, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.telephony.CellInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void requestCellInfoUpdate(int i, android.telephony.ICellInfoCallback iCellInfoCallback, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iCellInfoCallback);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(73, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void requestCellInfoUpdateWithWorkSource(int i, android.telephony.ICellInfoCallback iCellInfoCallback, java.lang.String str, java.lang.String str2, android.os.WorkSource workSource) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iCellInfoCallback);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(workSource, 0);
                    this.mRemote.transact(74, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setCellInfoListRate(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(75, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public android.telephony.IccOpenLogicalChannelResponse iccOpenLogicalChannel(com.android.internal.telephony.IccLogicalChannelRequest iccLogicalChannelRequest) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(iccLogicalChannelRequest, 0);
                    this.mRemote.transact(76, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.telephony.IccOpenLogicalChannelResponse) obtain2.readTypedObject(android.telephony.IccOpenLogicalChannelResponse.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean iccCloseLogicalChannel(com.android.internal.telephony.IccLogicalChannelRequest iccLogicalChannelRequest) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(iccLogicalChannelRequest, 0);
                    this.mRemote.transact(77, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.lang.String iccTransmitApduLogicalChannelByPort(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    obtain.writeInt(i6);
                    obtain.writeInt(i7);
                    obtain.writeInt(i8);
                    obtain.writeString(str);
                    this.mRemote.transact(78, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.lang.String iccTransmitApduLogicalChannel(int i, int i2, int i3, int i4, int i5, int i6, int i7, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    obtain.writeInt(i6);
                    obtain.writeInt(i7);
                    obtain.writeString(str);
                    this.mRemote.transact(79, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.lang.String iccTransmitApduBasicChannelByPort(int i, int i2, java.lang.String str, int i3, int i4, int i5, int i6, int i7, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    obtain.writeInt(i6);
                    obtain.writeInt(i7);
                    obtain.writeString(str2);
                    this.mRemote.transact(80, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.lang.String iccTransmitApduBasicChannel(int i, java.lang.String str, int i2, int i3, int i4, int i5, int i6, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    obtain.writeInt(i6);
                    obtain.writeString(str2);
                    this.mRemote.transact(81, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public byte[] iccExchangeSimIO(int i, int i2, int i3, int i4, int i5, int i6, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    obtain.writeInt(i6);
                    obtain.writeString(str);
                    this.mRemote.transact(82, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.lang.String sendEnvelopeWithStatus(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(83, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.lang.String nvReadItem(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(84, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean nvWriteItem(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(85, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean nvWriteCdmaPrl(byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(86, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean resetModemConfig(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(87, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean rebootModem(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(88, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getAllowedNetworkTypesBitmask(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(89, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isTetheringApnRequiredForSubscriber(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(90, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void enableIms(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(91, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void disableIms(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(92, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void resetIms(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(93, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void registerMmTelFeatureCallback(int i, com.android.ims.internal.IImsServiceFeatureCallback iImsServiceFeatureCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iImsServiceFeatureCallback);
                    this.mRemote.transact(94, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void unregisterImsFeatureCallback(com.android.ims.internal.IImsServiceFeatureCallback iImsServiceFeatureCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsServiceFeatureCallback);
                    this.mRemote.transact(95, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public android.telephony.ims.aidl.IImsRegistration getImsRegistration(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(96, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.telephony.ims.aidl.IImsRegistration.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public android.telephony.ims.aidl.IImsConfig getImsConfig(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(97, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.telephony.ims.aidl.IImsConfig.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setBoundImsServiceOverride(int i, boolean z, int[] iArr, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeIntArray(iArr);
                    obtain.writeString(str);
                    this.mRemote.transact(98, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean clearCarrierImsServiceOverride(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(99, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.lang.String getBoundImsServicePackage(int i, boolean z, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i2);
                    this.mRemote.transact(100, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void getImsMmTelFeatureState(int i, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(101, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setNetworkSelectionModeAutomatic(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(102, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public com.android.internal.telephony.CellNetworkScanResult getCellNetworkScanResults(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(103, obtain, obtain2, 0);
                    obtain2.readException();
                    return (com.android.internal.telephony.CellNetworkScanResult) obtain2.readTypedObject(com.android.internal.telephony.CellNetworkScanResult.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int requestNetworkScan(int i, boolean z, android.telephony.NetworkScanRequest networkScanRequest, android.os.Messenger messenger, android.os.IBinder iBinder, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(networkScanRequest, 0);
                    obtain.writeTypedObject(messenger, 0);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(104, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void stopNetworkScan(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(105, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setNetworkSelectionModeManual(int i, com.android.internal.telephony.OperatorInfo operatorInfo, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(operatorInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(106, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public long getAllowedNetworkTypesForReason(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(107, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setAllowedNetworkTypesForReason(int i, int i2, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeLong(j);
                    this.mRemote.transact(108, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean getDataEnabled(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(109, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isUserDataEnabled(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(110, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isDataEnabled(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(111, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setDataEnabledForReason(int i, int i2, boolean z, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    this.mRemote.transact(112, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isDataEnabledForReason(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(113, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isManualNetworkSelectionAllowed(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(114, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setImsRegistrationState(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(115, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.lang.String getCdmaMdn(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(116, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.lang.String getCdmaMin(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(117, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void requestNumberVerification(android.telephony.PhoneNumberRange phoneNumberRange, long j, com.android.internal.telephony.INumberVerificationCallback iNumberVerificationCallback, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(phoneNumberRange, 0);
                    obtain.writeLong(j);
                    obtain.writeStrongInterface(iNumberVerificationCallback);
                    obtain.writeString(str);
                    this.mRemote.transact(118, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getCarrierPrivilegeStatus(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(119, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getCarrierPrivilegeStatusForUid(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(120, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int checkCarrierPrivilegesForPackage(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(121, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int checkCarrierPrivilegesForPackageAnyPhone(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(122, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.util.List<java.lang.String> getCarrierPackageNamesForIntentAndPhone(android.content.Intent intent, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(123, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setLine1NumberForDisplayForSubscriber(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(124, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.lang.String getLine1NumberForDisplay(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(125, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.lang.String getLine1AlphaTagForDisplay(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(126, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.lang.String[] getMergedSubscriberIds(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(127, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.lang.String[] getMergedImsisFromGroup(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(128, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setOperatorBrandOverride(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(129, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setRoamingOverride(int i, java.util.List<java.lang.String> list, java.util.List<java.lang.String> list2, java.util.List<java.lang.String> list3, java.util.List<java.lang.String> list4) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStringList(list);
                    obtain.writeStringList(list2);
                    obtain.writeStringList(list3);
                    obtain.writeStringList(list4);
                    this.mRemote.transact(130, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean needMobileRadioShutdown() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    this.mRemote.transact(131, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void shutdownMobileRadios() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    this.mRemote.transact(132, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getRadioAccessFamily(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(133, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void uploadCallComposerPicture(int i, java.lang.String str, java.lang.String str2, android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(134, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void enableVideoCalling(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(135, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isVideoCallingEnabled(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(136, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean canChangeDtmfToneLength(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(137, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isWorldPhone(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(138, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isTtyModeSupported() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    this.mRemote.transact(139, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isRttSupported(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(140, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isHearingAidCompatibilitySupported() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    this.mRemote.transact(141, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isImsRegistered(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(142, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isWifiCallingAvailable(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(143, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isVideoTelephonyAvailable(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(144, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getImsRegTechnologyForMmTel(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(145, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.lang.String getDeviceId(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(146, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.lang.String getDeviceIdWithFeature(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(147, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.lang.String getImeiForSlot(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(148, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.lang.String getPrimaryImei(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(149, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.lang.String getTypeAllocationCodeForSlot(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(150, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.lang.String getMeidForSlot(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(151, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.lang.String getManufacturerCodeForSlot(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(152, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.lang.String getDeviceSoftwareVersionForSlot(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(153, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getSubIdForPhoneAccountHandle(android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(phoneAccountHandle, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(154, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public android.telecom.PhoneAccountHandle getPhoneAccountHandleForSubscriptionId(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(155, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.telecom.PhoneAccountHandle) obtain2.readTypedObject(android.telecom.PhoneAccountHandle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void factoryReset(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(156, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.lang.String getSimLocaleForSubscriber(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(157, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void requestModemActivityInfo(android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(158, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public android.telephony.ServiceState getServiceStateForSubscriber(int i, boolean z, boolean z2, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(159, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.telephony.ServiceState) obtain2.readTypedObject(android.telephony.ServiceState.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public android.net.Uri getVoicemailRingtoneUri(android.telecom.PhoneAccountHandle phoneAccountHandle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(phoneAccountHandle, 0);
                    this.mRemote.transact(160, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.net.Uri) obtain2.readTypedObject(android.net.Uri.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setVoicemailRingtoneUri(java.lang.String str, android.telecom.PhoneAccountHandle phoneAccountHandle, android.net.Uri uri) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(phoneAccountHandle, 0);
                    obtain.writeTypedObject(uri, 0);
                    this.mRemote.transact(161, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isVoicemailVibrationEnabled(android.telecom.PhoneAccountHandle phoneAccountHandle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(phoneAccountHandle, 0);
                    this.mRemote.transact(162, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setVoicemailVibrationEnabled(java.lang.String str, android.telecom.PhoneAccountHandle phoneAccountHandle, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(phoneAccountHandle, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(163, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.util.List<java.lang.String> getPackagesWithCarrierPrivileges(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(164, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.util.List<java.lang.String> getPackagesWithCarrierPrivilegesForAllPhones() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    this.mRemote.transact(165, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.lang.String getAidForAppType(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(166, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.lang.String getEsn(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(167, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.lang.String getCdmaPrlVersion(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(168, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.util.List<android.telephony.TelephonyHistogram> getTelephonyHistograms() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    this.mRemote.transact(169, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.telephony.TelephonyHistogram.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int setAllowedCarriers(android.telephony.CarrierRestrictionRules carrierRestrictionRules) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(carrierRestrictionRules, 0);
                    this.mRemote.transact(170, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public android.telephony.CarrierRestrictionRules getAllowedCarriers() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    this.mRemote.transact(171, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.telephony.CarrierRestrictionRules) obtain2.readTypedObject(android.telephony.CarrierRestrictionRules.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getSubscriptionCarrierId(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(172, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.lang.String getSubscriptionCarrierName(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(173, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getSubscriptionSpecificCarrierId(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(174, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.lang.String getSubscriptionSpecificCarrierName(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(175, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getCarrierIdFromMccMnc(int i, java.lang.String str, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(176, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void carrierActionSetRadioEnabled(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(177, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void carrierActionReportDefaultNetworkStatus(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(178, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void carrierActionResetAll(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(179, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void getCallForwarding(int i, int i2, com.android.internal.telephony.ICallForwardingInfoCallback iCallForwardingInfoCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iCallForwardingInfoCallback);
                    this.mRemote.transact(180, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setCallForwarding(int i, android.telephony.CallForwardingInfo callForwardingInfo, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(callForwardingInfo, 0);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(181, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void getCallWaitingStatus(int i, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(182, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setCallWaitingStatus(int i, boolean z, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(183, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.util.List<android.telephony.ClientRequestStats> getClientRequestStats(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(184, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.telephony.ClientRequestStats.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setSimPowerStateForSlot(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(185, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setSimPowerStateForSlotWithCallback(int i, int i2, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(186, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.lang.String[] getForbiddenPlmns(int i, int i2, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(187, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int setForbiddenPlmns(int i, int i2, java.util.List<java.lang.String> list, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStringList(list);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(188, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean getEmergencyCallbackMode(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(189, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public android.telephony.SignalStrength getSignalStrength(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(190, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.telephony.SignalStrength) obtain2.readTypedObject(android.telephony.SignalStrength.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getCardIdForDefaultEuicc(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(191, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.util.List<android.telephony.UiccCardInfo> getUiccCardsInfo(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(192, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.telephony.UiccCardInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public android.telephony.UiccSlotInfo[] getUiccSlotsInfo(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(193, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.telephony.UiccSlotInfo[]) obtain2.createTypedArray(android.telephony.UiccSlotInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean switchSlots(int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(194, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setSimSlotMapping(java.util.List<android.telephony.UiccSlotMapping> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(195, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isDataRoamingEnabled(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(196, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setDataRoamingEnabled(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(197, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getCdmaRoamingMode(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(198, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setCdmaRoamingMode(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(199, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getCdmaSubscriptionMode(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(200, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setCdmaSubscriptionMode(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(201, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setCarrierTestOverride(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.lang.String str5, java.lang.String str6, java.lang.String str7, java.lang.String str8, java.lang.String str9) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeString(str5);
                    obtain.writeString(str6);
                    obtain.writeString(str7);
                    obtain.writeString(str8);
                    obtain.writeString(str9);
                    this.mRemote.transact(202, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setCarrierServicePackageOverride(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(203, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getCarrierIdListVersion(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(204, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void refreshUiccProfile(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(205, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getNumberOfModemsWithSimultaneousDataConnections(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(206, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getNetworkSelectionMode(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(207, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isInEmergencySmsMode() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    this.mRemote.transact(208, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getRadioPowerState(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(209, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void registerImsRegistrationCallback(int i, android.telephony.ims.aidl.IImsRegistrationCallback iImsRegistrationCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iImsRegistrationCallback);
                    this.mRemote.transact(210, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void unregisterImsRegistrationCallback(int i, android.telephony.ims.aidl.IImsRegistrationCallback iImsRegistrationCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iImsRegistrationCallback);
                    this.mRemote.transact(211, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void registerImsEmergencyRegistrationCallback(int i, android.telephony.ims.aidl.IImsRegistrationCallback iImsRegistrationCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iImsRegistrationCallback);
                    this.mRemote.transact(212, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void unregisterImsEmergencyRegistrationCallback(int i, android.telephony.ims.aidl.IImsRegistrationCallback iImsRegistrationCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iImsRegistrationCallback);
                    this.mRemote.transact(213, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void getImsMmTelRegistrationState(int i, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(214, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void getImsMmTelRegistrationTransportType(int i, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(215, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void registerMmTelCapabilityCallback(int i, android.telephony.ims.aidl.IImsCapabilityCallback iImsCapabilityCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iImsCapabilityCallback);
                    this.mRemote.transact(216, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void unregisterMmTelCapabilityCallback(int i, android.telephony.ims.aidl.IImsCapabilityCallback iImsCapabilityCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iImsCapabilityCallback);
                    this.mRemote.transact(217, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isCapable(int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(218, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isAvailable(int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(219, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void isMmTelCapabilitySupported(int i, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(220, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isAdvancedCallingSettingEnabled(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(221, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setAdvancedCallingSettingEnabled(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(222, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isVtSettingEnabled(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(223, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setVtSettingEnabled(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(224, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isVoWiFiSettingEnabled(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(225, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setVoWiFiSettingEnabled(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(226, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isCrossSimCallingEnabledByUser(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(227, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setCrossSimCallingEnabled(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(228, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isVoWiFiRoamingSettingEnabled(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(229, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setVoWiFiRoamingSettingEnabled(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(230, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setVoWiFiNonPersistent(int i, boolean z, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i2);
                    this.mRemote.transact(231, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getVoWiFiModeSetting(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(232, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setVoWiFiModeSetting(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(233, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getVoWiFiRoamingModeSetting(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(234, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setVoWiFiRoamingModeSetting(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(235, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setRttCapabilitySetting(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(236, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isTtyOverVolteEnabled(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(237, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.util.Map getEmergencyNumberList(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(238, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readHashMap(getClass().getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isEmergencyNumber(java.lang.String str, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(239, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.util.List<java.lang.String> getCertsFromCarrierPrivilegeAccessRules(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(240, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void registerImsProvisioningChangedCallback(int i, android.telephony.ims.aidl.IImsConfigCallback iImsConfigCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iImsConfigCallback);
                    this.mRemote.transact(241, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void unregisterImsProvisioningChangedCallback(int i, android.telephony.ims.aidl.IImsConfigCallback iImsConfigCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iImsConfigCallback);
                    this.mRemote.transact(242, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void registerFeatureProvisioningChangedCallback(int i, android.telephony.ims.aidl.IFeatureProvisioningCallback iFeatureProvisioningCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iFeatureProvisioningCallback);
                    this.mRemote.transact(243, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void unregisterFeatureProvisioningChangedCallback(int i, android.telephony.ims.aidl.IFeatureProvisioningCallback iFeatureProvisioningCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iFeatureProvisioningCallback);
                    this.mRemote.transact(244, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setImsProvisioningStatusForCapability(int i, int i2, int i3, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(245, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean getImsProvisioningStatusForCapability(int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(246, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean getRcsProvisioningStatusForCapability(int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(247, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setRcsProvisioningStatusForCapability(int i, int i2, int i3, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(248, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getImsProvisioningInt(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(249, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.lang.String getImsProvisioningString(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(250, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int setImsProvisioningInt(int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(251, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int setImsProvisioningString(int i, int i2, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.mRemote.transact(252, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void startEmergencyCallbackMode() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    this.mRemote.transact(253, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void updateEmergencyNumberListTestMode(int i, android.telephony.emergency.EmergencyNumber emergencyNumber) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(emergencyNumber, 0);
                    this.mRemote.transact(254, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.util.List<java.lang.String> getEmergencyNumberListTestMode() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    this.mRemote.transact(255, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getEmergencyNumberDbVersion(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(256, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void notifyOtaEmergencyNumberDbInstalled() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    this.mRemote.transact(257, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void updateOtaEmergencyNumberDbFilePath(android.os.ParcelFileDescriptor parcelFileDescriptor) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    this.mRemote.transact(258, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void resetOtaEmergencyNumberDbFilePath() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    this.mRemote.transact(259, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean enableModemForSlot(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(260, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setMultiSimCarrierRestriction(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(261, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int isMultiSimSupported(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(262, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void switchMultiSimConfig(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(263, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean doesSwitchMultiSimConfigTriggerReboot(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(264, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.util.List<android.telephony.UiccSlotMapping> getSlotsMapping(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(265, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.telephony.UiccSlotMapping.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getRadioHalVersion() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    this.mRemote.transact(266, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getHalVersion(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(267, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.lang.String getCurrentPackageName() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    this.mRemote.transact(268, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isApplicationOnUicc(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(269, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isModemEnabledForSlot(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(270, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isDataEnabledForApn(int i, int i2, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.mRemote.transact(271, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isApnMetered(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(272, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setSystemSelectionChannels(java.util.List<android.telephony.RadioAccessSpecifier> list, int i, com.android.internal.telephony.IBooleanConsumer iBooleanConsumer) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iBooleanConsumer);
                    this.mRemote.transact(273, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.util.List<android.telephony.RadioAccessSpecifier> getSystemSelectionChannels(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(274, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.telephony.RadioAccessSpecifier.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isMvnoMatched(int i, int i2, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.mRemote.transact(275, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void enqueueSmsPickResult(java.lang.String str, java.lang.String str2, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(276, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void showSwitchToManagedProfileDialog() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    this.mRemote.transact(277, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.lang.String getMmsUserAgent(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(278, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.lang.String getMmsUAProfUrl(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(279, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setMobileDataPolicyEnabled(int i, int i2, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(280, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isMobileDataPolicyEnabled(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(281, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setCepEnabled(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(282, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void notifyRcsAutoConfigurationReceived(int i, byte[] bArr, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(283, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isIccLockEnabled(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(284, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int setIccLockEnabled(int i, boolean z, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    this.mRemote.transact(285, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int changeIccLockPassword(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(286, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void requestUserActivityNotification() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    this.mRemote.transact(287, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void userActivity() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    this.mRemote.transact(288, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.lang.String getManualNetworkSelectionPlmn(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(289, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean canConnectTo5GInDsdsMode() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    this.mRemote.transact(290, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.util.List<java.lang.String> getEquivalentHomePlmns(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(291, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int setVoNrEnabled(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(292, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isVoNrEnabled(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(293, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int setNrDualConnectivityState(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(294, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isNrDualConnectivityEnabled(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(295, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isRadioInterfaceCapabilitySupported(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(296, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int sendThermalMitigationRequest(int i, android.telephony.ThermalMitigationRequest thermalMitigationRequest, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(thermalMitigationRequest, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(297, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void bootstrapAuthenticationRequest(int i, int i2, android.net.Uri uri, android.telephony.gba.UaSecurityProtocolIdentifier uaSecurityProtocolIdentifier, boolean z, android.telephony.IBootstrapAuthenticationCallback iBootstrapAuthenticationCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeTypedObject(uaSecurityProtocolIdentifier, 0);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iBootstrapAuthenticationCallback);
                    this.mRemote.transact(298, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setBoundGbaServiceOverride(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(299, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.lang.String getBoundGbaService(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(300, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setGbaReleaseTimeOverride(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(301, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getGbaReleaseTime(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(302, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setRcsClientConfiguration(int i, android.telephony.ims.RcsClientConfiguration rcsClientConfiguration) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(rcsClientConfiguration, 0);
                    this.mRemote.transact(303, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isRcsVolteSingleRegistrationCapable(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(304, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void registerRcsProvisioningCallback(int i, android.telephony.ims.aidl.IRcsConfigCallback iRcsConfigCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iRcsConfigCallback);
                    this.mRemote.transact(305, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void unregisterRcsProvisioningCallback(int i, android.telephony.ims.aidl.IRcsConfigCallback iRcsConfigCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iRcsConfigCallback);
                    this.mRemote.transact(306, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void triggerRcsReconfiguration(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(307, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setRcsSingleRegistrationTestModeEnabled(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(308, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean getRcsSingleRegistrationTestModeEnabled() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    this.mRemote.transact(309, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setDeviceSingleRegistrationEnabledOverride(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(310, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean getDeviceSingleRegistrationEnabled() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    this.mRemote.transact(311, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setCarrierSingleRegistrationEnabledOverride(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(312, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void sendDeviceToDeviceMessage(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(313, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setActiveDeviceToDeviceTransport(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(314, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setDeviceToDeviceForceEnabled(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(315, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean getCarrierSingleRegistrationEnabled(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(316, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setImsFeatureValidationOverride(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(317, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean getImsFeatureValidationOverride(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(318, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.lang.String getMobileProvisioningUrl() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    this.mRemote.transact(319, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int removeContactFromEab(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(320, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.lang.String getContactFromEab(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(321, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.lang.String getCapabilityFromEab(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(322, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean getDeviceUceEnabled() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    this.mRemote.transact(323, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setDeviceUceEnabled(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(324, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public android.telephony.ims.RcsContactUceCapability addUceRegistrationOverrideShell(int i, java.util.List<java.lang.String> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStringList(list);
                    this.mRemote.transact(325, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.telephony.ims.RcsContactUceCapability) obtain2.readTypedObject(android.telephony.ims.RcsContactUceCapability.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public android.telephony.ims.RcsContactUceCapability removeUceRegistrationOverrideShell(int i, java.util.List<java.lang.String> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStringList(list);
                    this.mRemote.transact(326, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.telephony.ims.RcsContactUceCapability) obtain2.readTypedObject(android.telephony.ims.RcsContactUceCapability.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public android.telephony.ims.RcsContactUceCapability clearUceRegistrationOverrideShell(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(327, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.telephony.ims.RcsContactUceCapability) obtain2.readTypedObject(android.telephony.ims.RcsContactUceCapability.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public android.telephony.ims.RcsContactUceCapability getLatestRcsContactUceCapabilityShell(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(328, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.telephony.ims.RcsContactUceCapability) obtain2.readTypedObject(android.telephony.ims.RcsContactUceCapability.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.lang.String getLastUcePidfXmlShell(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(329, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean removeUceRequestDisallowedStatus(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(330, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setCapabilitiesRequestTimeout(int i, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    this.mRemote.transact(331, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setSignalStrengthUpdateRequest(int i, android.telephony.SignalStrengthUpdateRequest signalStrengthUpdateRequest, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(signalStrengthUpdateRequest, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(332, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void clearSignalStrengthUpdateRequest(int i, android.telephony.SignalStrengthUpdateRequest signalStrengthUpdateRequest, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(signalStrengthUpdateRequest, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(333, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public android.telephony.PhoneCapability getPhoneCapability() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    this.mRemote.transact(334, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.telephony.PhoneCapability) obtain2.readTypedObject(android.telephony.PhoneCapability.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int prepareForUnattendedReboot() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    this.mRemote.transact(335, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void getSlicingConfig(android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(336, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isPremiumCapabilityAvailableForPurchase(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(337, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void purchasePremiumCapability(int i, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    obtain.writeInt(i2);
                    this.mRemote.transact(338, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void registerImsStateCallback(int i, int i2, com.android.internal.telephony.IImsStateCallback iImsStateCallback, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iImsStateCallback);
                    obtain.writeString(str);
                    this.mRemote.transact(339, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void unregisterImsStateCallback(com.android.internal.telephony.IImsStateCallback iImsStateCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsStateCallback);
                    this.mRemote.transact(340, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public android.telephony.CellIdentity getLastKnownCellIdentity(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(341, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.telephony.CellIdentity) obtain2.readTypedObject(android.telephony.CellIdentity.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setModemService(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(342, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.lang.String getModemService() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    this.mRemote.transact(343, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isProvisioningRequiredForCapability(int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(344, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isRcsProvisioningRequiredForCapability(int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(345, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setVoiceServiceStateOverride(int i, boolean z, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    this.mRemote.transact(346, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.lang.String getCarrierServicePackageNameForLogicalSlot(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(347, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setRemovableEsimAsDefaultEuicc(boolean z, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    this.mRemote.transact(348, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isRemovableEsimDefaultEuicc(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(349, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public android.content.ComponentName getDefaultRespondViaMessageApplication(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(350, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.ComponentName) obtain2.readTypedObject(android.content.ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int getSimStateForSlotIndex(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(351, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void persistEmergencyCallDiagnosticData(java.lang.String str, boolean z, long j, boolean z2, boolean z3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeLong(j);
                    obtain.writeBoolean(z2);
                    obtain.writeBoolean(z3);
                    this.mRemote.transact(352, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setNullCipherAndIntegrityEnabled(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(353, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isNullCipherAndIntegrityPreferenceEnabled() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    this.mRemote.transact(354, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.util.List<android.telephony.CellBroadcastIdRange> getCellBroadcastIdRanges(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(355, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.telephony.CellBroadcastIdRange.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setCellBroadcastIdRanges(int i, java.util.List<android.telephony.CellBroadcastIdRange> list, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedList(list, 0);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(356, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isDomainSelectionSupported() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    this.mRemote.transact(357, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void getCarrierRestrictionStatus(com.android.internal.telephony.IIntegerConsumer iIntegerConsumer, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    obtain.writeString(str);
                    this.mRemote.transact(358, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void requestSatelliteEnabled(int i, boolean z, boolean z2, boolean z3, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeBoolean(z3);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(359, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void requestIsSatelliteEnabled(int i, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(360, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void requestIsDemoModeEnabled(int i, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(361, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void requestIsEmergencyModeEnabled(int i, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(362, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void requestIsSatelliteSupported(int i, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(363, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void requestSatelliteCapabilities(int i, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(364, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void startSatelliteTransmissionUpdates(int i, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer, android.telephony.satellite.ISatelliteTransmissionUpdateCallback iSatelliteTransmissionUpdateCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    obtain.writeStrongInterface(iSatelliteTransmissionUpdateCallback);
                    this.mRemote.transact(365, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void stopSatelliteTransmissionUpdates(int i, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer, android.telephony.satellite.ISatelliteTransmissionUpdateCallback iSatelliteTransmissionUpdateCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    obtain.writeStrongInterface(iSatelliteTransmissionUpdateCallback);
                    this.mRemote.transact(366, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public android.os.ICancellationSignal provisionSatelliteService(int i, java.lang.String str, byte[] bArr, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(367, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.os.ICancellationSignal.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void deprovisionSatelliteService(int i, java.lang.String str, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(368, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int registerForSatelliteProvisionStateChanged(int i, android.telephony.satellite.ISatelliteProvisionStateCallback iSatelliteProvisionStateCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iSatelliteProvisionStateCallback);
                    this.mRemote.transact(369, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void unregisterForSatelliteProvisionStateChanged(int i, android.telephony.satellite.ISatelliteProvisionStateCallback iSatelliteProvisionStateCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iSatelliteProvisionStateCallback);
                    this.mRemote.transact(370, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void requestIsSatelliteProvisioned(int i, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(371, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int registerForSatelliteModemStateChanged(int i, android.telephony.satellite.ISatelliteModemStateCallback iSatelliteModemStateCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iSatelliteModemStateCallback);
                    this.mRemote.transact(372, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void unregisterForModemStateChanged(int i, android.telephony.satellite.ISatelliteModemStateCallback iSatelliteModemStateCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iSatelliteModemStateCallback);
                    this.mRemote.transact(373, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int registerForIncomingDatagram(int i, android.telephony.satellite.ISatelliteDatagramCallback iSatelliteDatagramCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iSatelliteDatagramCallback);
                    this.mRemote.transact(374, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void unregisterForIncomingDatagram(int i, android.telephony.satellite.ISatelliteDatagramCallback iSatelliteDatagramCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iSatelliteDatagramCallback);
                    this.mRemote.transact(375, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void pollPendingDatagrams(int i, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(376, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void sendDatagram(int i, int i2, android.telephony.satellite.SatelliteDatagram satelliteDatagram, boolean z, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(satelliteDatagram, 0);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(377, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void requestIsCommunicationAllowedForCurrentLocation(int i, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(378, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void requestTimeForNextSatelliteVisibility(int i, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(379, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setDeviceAlignedWithSatellite(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(380, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setSatelliteServicePackageName(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(381, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setSatelliteGatewayServicePackageName(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(382, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setSatelliteListeningTimeoutDuration(long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(383, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setSatellitePointingUiClassName(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(384, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setDatagramControllerTimeoutDuration(boolean z, int i, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    this.mRemote.transact(385, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setSatelliteControllerTimeoutDuration(boolean z, int i, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    this.mRemote.transact(386, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setEmergencyCallToSatelliteHandoverType(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(387, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setCountryCodes(boolean z, java.util.List<java.lang.String> list, java.util.Map map, java.lang.String str, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeStringList(list);
                    obtain.writeMap(map);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(388, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setSatelliteAccessControlOverlayConfigs(boolean z, boolean z2, java.lang.String str, long j, java.util.List<java.lang.String> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeStringList(list);
                    this.mRemote.transact(389, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setOemEnabledSatelliteProvisionStatus(boolean z, boolean z2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(390, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.util.List<java.lang.String> getShaIdFromAllowList(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(391, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void addAttachRestrictionForCarrier(int i, int i2, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(392, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void removeAttachRestrictionForCarrier(int i, int i2, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(393, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int[] getAttachRestrictionReasonsForCarrier(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(394, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void requestNtnSignalStrength(int i, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(395, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void registerForNtnSignalStrengthChanged(int i, android.telephony.satellite.INtnSignalStrengthCallback iNtnSignalStrengthCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iNtnSignalStrengthCallback);
                    this.mRemote.transact(396, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void unregisterForNtnSignalStrengthChanged(int i, android.telephony.satellite.INtnSignalStrengthCallback iNtnSignalStrengthCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iNtnSignalStrengthCallback);
                    this.mRemote.transact(397, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public int registerForCapabilitiesChanged(int i, android.telephony.satellite.ISatelliteCapabilitiesCallback iSatelliteCapabilitiesCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iSatelliteCapabilitiesCallback);
                    this.mRemote.transact(398, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void unregisterForCapabilitiesChanged(int i, android.telephony.satellite.ISatelliteCapabilitiesCallback iSatelliteCapabilitiesCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iSatelliteCapabilitiesCallback);
                    this.mRemote.transact(399, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setShouldSendDatagramToModemInDemoMode(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(400, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean setDomainSelectionServiceOverride(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(401, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean clearDomainSelectionServiceOverride() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    this.mRemote.transact(402, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setEnableCellularIdentifierDisclosureNotifications(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(403, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isCellularIdentifierDisclosureNotificationsEnabled() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    this.mRemote.transact(404, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public void setNullCipherNotificationsEnabled(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(405, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public boolean isNullCipherNotificationsEnabled() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    this.mRemote.transact(406, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephony
            public java.util.List<java.lang.String> getSatellitePlmnsForCarrier(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephony.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(407, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        private boolean onTransact$isRadioOnForSubscriberWithFeature$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            boolean isRadioOnForSubscriberWithFeature = isRadioOnForSubscriberWithFeature(readInt, readString, readString2);
            parcel2.writeNoException();
            parcel2.writeBoolean(isRadioOnForSubscriberWithFeature);
            return true;
        }

        private boolean onTransact$supplyPukForSubscriber$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            boolean supplyPukForSubscriber = supplyPukForSubscriber(readInt, readString, readString2);
            parcel2.writeNoException();
            parcel2.writeBoolean(supplyPukForSubscriber);
            return true;
        }

        private boolean onTransact$supplyPukReportResultForSubscriber$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            int[] supplyPukReportResultForSubscriber = supplyPukReportResultForSubscriber(readInt, readString, readString2);
            parcel2.writeNoException();
            parcel2.writeIntArray(supplyPukReportResultForSubscriber);
            return true;
        }

        private boolean onTransact$handleUssdRequest$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            java.lang.String readString = parcel.readString();
            android.os.ResultReceiver resultReceiver = (android.os.ResultReceiver) parcel.readTypedObject(android.os.ResultReceiver.CREATOR);
            parcel.enforceNoDataAvail();
            handleUssdRequest(readInt, readString, resultReceiver);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getRadioPowerOffReasons$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            java.util.List radioPowerOffReasons = getRadioPowerOffReasons(readInt, readString, readString2);
            parcel2.writeNoException();
            parcel2.writeList(radioPowerOffReasons);
            return true;
        }

        private boolean onTransact$getCallStateForSubscription$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            int callStateForSubscription = getCallStateForSubscription(readInt, readString, readString2);
            parcel2.writeNoException();
            parcel2.writeInt(callStateForSubscription);
            return true;
        }

        private boolean onTransact$getCdmaEriIconIndexForSubscriber$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            int cdmaEriIconIndexForSubscriber = getCdmaEriIconIndexForSubscriber(readInt, readString, readString2);
            parcel2.writeNoException();
            parcel2.writeInt(cdmaEriIconIndexForSubscriber);
            return true;
        }

        private boolean onTransact$getCdmaEriIconModeForSubscriber$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            int cdmaEriIconModeForSubscriber = getCdmaEriIconModeForSubscriber(readInt, readString, readString2);
            parcel2.writeNoException();
            parcel2.writeInt(cdmaEriIconModeForSubscriber);
            return true;
        }

        private boolean onTransact$getCdmaEriTextForSubscriber$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            java.lang.String cdmaEriTextForSubscriber = getCdmaEriTextForSubscriber(readInt, readString, readString2);
            parcel2.writeNoException();
            parcel2.writeString(cdmaEriTextForSubscriber);
            return true;
        }

        private boolean onTransact$setVoiceMailNumber$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            boolean voiceMailNumber = setVoiceMailNumber(readInt, readString, readString2);
            parcel2.writeNoException();
            parcel2.writeBoolean(voiceMailNumber);
            return true;
        }

        private boolean onTransact$getVoiceMessageCountForSubscriber$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            int voiceMessageCountForSubscriber = getVoiceMessageCountForSubscriber(readInt, readString, readString2);
            parcel2.writeNoException();
            parcel2.writeInt(voiceMessageCountForSubscriber);
            return true;
        }

        private boolean onTransact$getVisualVoicemailPackageName$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            java.lang.String visualVoicemailPackageName = getVisualVoicemailPackageName(readString, readString2, readInt);
            parcel2.writeNoException();
            parcel2.writeString(visualVoicemailPackageName);
            return true;
        }

        private boolean onTransact$enableVisualVoicemailSmsFilter$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            java.lang.String readString = parcel.readString();
            int readInt = parcel.readInt();
            android.telephony.VisualVoicemailSmsFilterSettings visualVoicemailSmsFilterSettings = (android.telephony.VisualVoicemailSmsFilterSettings) parcel.readTypedObject(android.telephony.VisualVoicemailSmsFilterSettings.CREATOR);
            parcel.enforceNoDataAvail();
            enableVisualVoicemailSmsFilter(readString, readInt, visualVoicemailSmsFilterSettings);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$sendVisualVoicemailSmsForSubscriber$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            int readInt = parcel.readInt();
            java.lang.String readString3 = parcel.readString();
            int readInt2 = parcel.readInt();
            java.lang.String readString4 = parcel.readString();
            android.app.PendingIntent pendingIntent = (android.app.PendingIntent) parcel.readTypedObject(android.app.PendingIntent.CREATOR);
            parcel.enforceNoDataAvail();
            sendVisualVoicemailSmsForSubscriber(readString, readString2, readInt, readString3, readInt2, readString4, pendingIntent);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getNetworkTypeForSubscriber$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            int networkTypeForSubscriber = getNetworkTypeForSubscriber(readInt, readString, readString2);
            parcel2.writeNoException();
            parcel2.writeInt(networkTypeForSubscriber);
            return true;
        }

        private boolean onTransact$getDataNetworkTypeForSubscriber$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            int dataNetworkTypeForSubscriber = getDataNetworkTypeForSubscriber(readInt, readString, readString2);
            parcel2.writeNoException();
            parcel2.writeInt(dataNetworkTypeForSubscriber);
            return true;
        }

        private boolean onTransact$getVoiceNetworkTypeForSubscriber$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            int voiceNetworkTypeForSubscriber = getVoiceNetworkTypeForSubscriber(readInt, readString, readString2);
            parcel2.writeNoException();
            parcel2.writeInt(voiceNetworkTypeForSubscriber);
            return true;
        }

        private boolean onTransact$getLteOnCdmaModeForSubscriber$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            int lteOnCdmaModeForSubscriber = getLteOnCdmaModeForSubscriber(readInt, readString, readString2);
            parcel2.writeNoException();
            parcel2.writeInt(lteOnCdmaModeForSubscriber);
            return true;
        }

        private boolean onTransact$requestCellInfoUpdate$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            android.telephony.ICellInfoCallback asInterface = android.telephony.ICellInfoCallback.Stub.asInterface(parcel.readStrongBinder());
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            requestCellInfoUpdate(readInt, asInterface, readString, readString2);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$requestCellInfoUpdateWithWorkSource$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            android.telephony.ICellInfoCallback asInterface = android.telephony.ICellInfoCallback.Stub.asInterface(parcel.readStrongBinder());
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            android.os.WorkSource workSource = (android.os.WorkSource) parcel.readTypedObject(android.os.WorkSource.CREATOR);
            parcel.enforceNoDataAvail();
            requestCellInfoUpdateWithWorkSource(readInt, asInterface, readString, readString2, workSource);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$iccTransmitApduLogicalChannelByPort$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            int readInt3 = parcel.readInt();
            int readInt4 = parcel.readInt();
            int readInt5 = parcel.readInt();
            int readInt6 = parcel.readInt();
            int readInt7 = parcel.readInt();
            int readInt8 = parcel.readInt();
            java.lang.String readString = parcel.readString();
            parcel.enforceNoDataAvail();
            java.lang.String iccTransmitApduLogicalChannelByPort = iccTransmitApduLogicalChannelByPort(readInt, readInt2, readInt3, readInt4, readInt5, readInt6, readInt7, readInt8, readString);
            parcel2.writeNoException();
            parcel2.writeString(iccTransmitApduLogicalChannelByPort);
            return true;
        }

        private boolean onTransact$iccTransmitApduLogicalChannel$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            int readInt3 = parcel.readInt();
            int readInt4 = parcel.readInt();
            int readInt5 = parcel.readInt();
            int readInt6 = parcel.readInt();
            int readInt7 = parcel.readInt();
            java.lang.String readString = parcel.readString();
            parcel.enforceNoDataAvail();
            java.lang.String iccTransmitApduLogicalChannel = iccTransmitApduLogicalChannel(readInt, readInt2, readInt3, readInt4, readInt5, readInt6, readInt7, readString);
            parcel2.writeNoException();
            parcel2.writeString(iccTransmitApduLogicalChannel);
            return true;
        }

        private boolean onTransact$iccTransmitApduBasicChannelByPort$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            java.lang.String readString = parcel.readString();
            int readInt3 = parcel.readInt();
            int readInt4 = parcel.readInt();
            int readInt5 = parcel.readInt();
            int readInt6 = parcel.readInt();
            int readInt7 = parcel.readInt();
            java.lang.String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            java.lang.String iccTransmitApduBasicChannelByPort = iccTransmitApduBasicChannelByPort(readInt, readInt2, readString, readInt3, readInt4, readInt5, readInt6, readInt7, readString2);
            parcel2.writeNoException();
            parcel2.writeString(iccTransmitApduBasicChannelByPort);
            return true;
        }

        private boolean onTransact$iccTransmitApduBasicChannel$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            java.lang.String readString = parcel.readString();
            int readInt2 = parcel.readInt();
            int readInt3 = parcel.readInt();
            int readInt4 = parcel.readInt();
            int readInt5 = parcel.readInt();
            int readInt6 = parcel.readInt();
            java.lang.String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            java.lang.String iccTransmitApduBasicChannel = iccTransmitApduBasicChannel(readInt, readString, readInt2, readInt3, readInt4, readInt5, readInt6, readString2);
            parcel2.writeNoException();
            parcel2.writeString(iccTransmitApduBasicChannel);
            return true;
        }

        private boolean onTransact$iccExchangeSimIO$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            int readInt3 = parcel.readInt();
            int readInt4 = parcel.readInt();
            int readInt5 = parcel.readInt();
            int readInt6 = parcel.readInt();
            java.lang.String readString = parcel.readString();
            parcel.enforceNoDataAvail();
            byte[] iccExchangeSimIO = iccExchangeSimIO(readInt, readInt2, readInt3, readInt4, readInt5, readInt6, readString);
            parcel2.writeNoException();
            parcel2.writeByteArray(iccExchangeSimIO);
            return true;
        }

        private boolean onTransact$setBoundImsServiceOverride$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            int[] createIntArray = parcel.createIntArray();
            java.lang.String readString = parcel.readString();
            parcel.enforceNoDataAvail();
            boolean boundImsServiceOverride = setBoundImsServiceOverride(readInt, readBoolean, createIntArray, readString);
            parcel2.writeNoException();
            parcel2.writeBoolean(boundImsServiceOverride);
            return true;
        }

        private boolean onTransact$getBoundImsServicePackage$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            int readInt2 = parcel.readInt();
            parcel.enforceNoDataAvail();
            java.lang.String boundImsServicePackage = getBoundImsServicePackage(readInt, readBoolean, readInt2);
            parcel2.writeNoException();
            parcel2.writeString(boundImsServicePackage);
            return true;
        }

        private boolean onTransact$getCellNetworkScanResults$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            com.android.internal.telephony.CellNetworkScanResult cellNetworkScanResults = getCellNetworkScanResults(readInt, readString, readString2);
            parcel2.writeNoException();
            parcel2.writeTypedObject(cellNetworkScanResults, 1);
            return true;
        }

        private boolean onTransact$requestNetworkScan$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            android.telephony.NetworkScanRequest networkScanRequest = (android.telephony.NetworkScanRequest) parcel.readTypedObject(android.telephony.NetworkScanRequest.CREATOR);
            android.os.Messenger messenger = (android.os.Messenger) parcel.readTypedObject(android.os.Messenger.CREATOR);
            android.os.IBinder readStrongBinder = parcel.readStrongBinder();
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            int requestNetworkScan = requestNetworkScan(readInt, readBoolean, networkScanRequest, messenger, readStrongBinder, readString, readString2);
            parcel2.writeNoException();
            parcel2.writeInt(requestNetworkScan);
            return true;
        }

        private boolean onTransact$setNetworkSelectionModeManual$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            com.android.internal.telephony.OperatorInfo operatorInfo = (com.android.internal.telephony.OperatorInfo) parcel.readTypedObject(com.android.internal.telephony.OperatorInfo.CREATOR);
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            boolean networkSelectionModeManual = setNetworkSelectionModeManual(readInt, operatorInfo, readBoolean);
            parcel2.writeNoException();
            parcel2.writeBoolean(networkSelectionModeManual);
            return true;
        }

        private boolean onTransact$setAllowedNetworkTypesForReason$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            long readLong = parcel.readLong();
            parcel.enforceNoDataAvail();
            boolean allowedNetworkTypesForReason = setAllowedNetworkTypesForReason(readInt, readInt2, readLong);
            parcel2.writeNoException();
            parcel2.writeBoolean(allowedNetworkTypesForReason);
            return true;
        }

        private boolean onTransact$setDataEnabledForReason$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            java.lang.String readString = parcel.readString();
            parcel.enforceNoDataAvail();
            setDataEnabledForReason(readInt, readInt2, readBoolean, readString);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$requestNumberVerification$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.telephony.PhoneNumberRange phoneNumberRange = (android.telephony.PhoneNumberRange) parcel.readTypedObject(android.telephony.PhoneNumberRange.CREATOR);
            long readLong = parcel.readLong();
            com.android.internal.telephony.INumberVerificationCallback asInterface = com.android.internal.telephony.INumberVerificationCallback.Stub.asInterface(parcel.readStrongBinder());
            java.lang.String readString = parcel.readString();
            parcel.enforceNoDataAvail();
            requestNumberVerification(phoneNumberRange, readLong, asInterface, readString);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setLine1NumberForDisplayForSubscriber$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            boolean line1NumberForDisplayForSubscriber = setLine1NumberForDisplayForSubscriber(readInt, readString, readString2);
            parcel2.writeNoException();
            parcel2.writeBoolean(line1NumberForDisplayForSubscriber);
            return true;
        }

        private boolean onTransact$getLine1NumberForDisplay$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            java.lang.String line1NumberForDisplay = getLine1NumberForDisplay(readInt, readString, readString2);
            parcel2.writeNoException();
            parcel2.writeString(line1NumberForDisplay);
            return true;
        }

        private boolean onTransact$getLine1AlphaTagForDisplay$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            java.lang.String line1AlphaTagForDisplay = getLine1AlphaTagForDisplay(readInt, readString, readString2);
            parcel2.writeNoException();
            parcel2.writeString(line1AlphaTagForDisplay);
            return true;
        }

        private boolean onTransact$getMergedSubscriberIds$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            java.lang.String[] mergedSubscriberIds = getMergedSubscriberIds(readInt, readString, readString2);
            parcel2.writeNoException();
            parcel2.writeStringArray(mergedSubscriberIds);
            return true;
        }

        private boolean onTransact$setRoamingOverride$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            java.util.ArrayList<java.lang.String> createStringArrayList = parcel.createStringArrayList();
            java.util.ArrayList<java.lang.String> createStringArrayList2 = parcel.createStringArrayList();
            java.util.ArrayList<java.lang.String> createStringArrayList3 = parcel.createStringArrayList();
            java.util.ArrayList<java.lang.String> createStringArrayList4 = parcel.createStringArrayList();
            parcel.enforceNoDataAvail();
            boolean roamingOverride = setRoamingOverride(readInt, createStringArrayList, createStringArrayList2, createStringArrayList3, createStringArrayList4);
            parcel2.writeNoException();
            parcel2.writeBoolean(roamingOverride);
            return true;
        }

        private boolean onTransact$uploadCallComposerPicture$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            android.os.ParcelFileDescriptor parcelFileDescriptor = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
            android.os.ResultReceiver resultReceiver = (android.os.ResultReceiver) parcel.readTypedObject(android.os.ResultReceiver.CREATOR);
            parcel.enforceNoDataAvail();
            uploadCallComposerPicture(readInt, readString, readString2, parcelFileDescriptor, resultReceiver);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$canChangeDtmfToneLength$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            boolean canChangeDtmfToneLength = canChangeDtmfToneLength(readInt, readString, readString2);
            parcel2.writeNoException();
            parcel2.writeBoolean(canChangeDtmfToneLength);
            return true;
        }

        private boolean onTransact$isWorldPhone$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            boolean isWorldPhone = isWorldPhone(readInt, readString, readString2);
            parcel2.writeNoException();
            parcel2.writeBoolean(isWorldPhone);
            return true;
        }

        private boolean onTransact$getImeiForSlot$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            java.lang.String imeiForSlot = getImeiForSlot(readInt, readString, readString2);
            parcel2.writeNoException();
            parcel2.writeString(imeiForSlot);
            return true;
        }

        private boolean onTransact$getMeidForSlot$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            java.lang.String meidForSlot = getMeidForSlot(readInt, readString, readString2);
            parcel2.writeNoException();
            parcel2.writeString(meidForSlot);
            return true;
        }

        private boolean onTransact$getDeviceSoftwareVersionForSlot$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            java.lang.String deviceSoftwareVersionForSlot = getDeviceSoftwareVersionForSlot(readInt, readString, readString2);
            parcel2.writeNoException();
            parcel2.writeString(deviceSoftwareVersionForSlot);
            return true;
        }

        private boolean onTransact$getSubIdForPhoneAccountHandle$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.telecom.PhoneAccountHandle phoneAccountHandle = (android.telecom.PhoneAccountHandle) parcel.readTypedObject(android.telecom.PhoneAccountHandle.CREATOR);
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            int subIdForPhoneAccountHandle = getSubIdForPhoneAccountHandle(phoneAccountHandle, readString, readString2);
            parcel2.writeNoException();
            parcel2.writeInt(subIdForPhoneAccountHandle);
            return true;
        }

        private boolean onTransact$getServiceStateForSubscriber$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            boolean readBoolean2 = parcel.readBoolean();
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            android.telephony.ServiceState serviceStateForSubscriber = getServiceStateForSubscriber(readInt, readBoolean, readBoolean2, readString, readString2);
            parcel2.writeNoException();
            parcel2.writeTypedObject(serviceStateForSubscriber, 1);
            return true;
        }

        private boolean onTransact$setVoicemailRingtoneUri$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            java.lang.String readString = parcel.readString();
            android.telecom.PhoneAccountHandle phoneAccountHandle = (android.telecom.PhoneAccountHandle) parcel.readTypedObject(android.telecom.PhoneAccountHandle.CREATOR);
            android.net.Uri uri = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
            parcel.enforceNoDataAvail();
            setVoicemailRingtoneUri(readString, phoneAccountHandle, uri);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setVoicemailVibrationEnabled$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            java.lang.String readString = parcel.readString();
            android.telecom.PhoneAccountHandle phoneAccountHandle = (android.telecom.PhoneAccountHandle) parcel.readTypedObject(android.telecom.PhoneAccountHandle.CREATOR);
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setVoicemailVibrationEnabled(readString, phoneAccountHandle, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getCarrierIdFromMccMnc$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            java.lang.String readString = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            int carrierIdFromMccMnc = getCarrierIdFromMccMnc(readInt, readString, readBoolean);
            parcel2.writeNoException();
            parcel2.writeInt(carrierIdFromMccMnc);
            return true;
        }

        private boolean onTransact$getCallForwarding$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            com.android.internal.telephony.ICallForwardingInfoCallback asInterface = com.android.internal.telephony.ICallForwardingInfoCallback.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            getCallForwarding(readInt, readInt2, asInterface);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setCallForwarding$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            android.telephony.CallForwardingInfo callForwardingInfo = (android.telephony.CallForwardingInfo) parcel.readTypedObject(android.telephony.CallForwardingInfo.CREATOR);
            com.android.internal.telephony.IIntegerConsumer asInterface = com.android.internal.telephony.IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            setCallForwarding(readInt, callForwardingInfo, asInterface);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setCallWaitingStatus$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            com.android.internal.telephony.IIntegerConsumer asInterface = com.android.internal.telephony.IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            setCallWaitingStatus(readInt, readBoolean, asInterface);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getClientRequestStats$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            java.util.List<android.telephony.ClientRequestStats> clientRequestStats = getClientRequestStats(readString, readString2, readInt);
            parcel2.writeNoException();
            parcel2.writeTypedList(clientRequestStats, 1);
            return true;
        }

        private boolean onTransact$setSimPowerStateForSlotWithCallback$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            com.android.internal.telephony.IIntegerConsumer asInterface = com.android.internal.telephony.IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            setSimPowerStateForSlotWithCallback(readInt, readInt2, asInterface);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getForbiddenPlmns$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            java.lang.String[] forbiddenPlmns = getForbiddenPlmns(readInt, readInt2, readString, readString2);
            parcel2.writeNoException();
            parcel2.writeStringArray(forbiddenPlmns);
            return true;
        }

        private boolean onTransact$setForbiddenPlmns$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            java.util.ArrayList<java.lang.String> createStringArrayList = parcel.createStringArrayList();
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            int forbiddenPlmns = setForbiddenPlmns(readInt, readInt2, createStringArrayList, readString, readString2);
            parcel2.writeNoException();
            parcel2.writeInt(forbiddenPlmns);
            return true;
        }

        private boolean onTransact$setCarrierTestOverride$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            java.lang.String readString3 = parcel.readString();
            java.lang.String readString4 = parcel.readString();
            java.lang.String readString5 = parcel.readString();
            java.lang.String readString6 = parcel.readString();
            java.lang.String readString7 = parcel.readString();
            java.lang.String readString8 = parcel.readString();
            java.lang.String readString9 = parcel.readString();
            parcel.enforceNoDataAvail();
            setCarrierTestOverride(readInt, readString, readString2, readString3, readString4, readString5, readString6, readString7, readString8, readString9);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setCarrierServicePackageOverride$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            setCarrierServicePackageOverride(readInt, readString, readString2);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getNumberOfModemsWithSimultaneousDataConnections$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            int numberOfModemsWithSimultaneousDataConnections = getNumberOfModemsWithSimultaneousDataConnections(readInt, readString, readString2);
            parcel2.writeNoException();
            parcel2.writeInt(numberOfModemsWithSimultaneousDataConnections);
            return true;
        }

        private boolean onTransact$getRadioPowerState$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            int radioPowerState = getRadioPowerState(readInt, readString, readString2);
            parcel2.writeNoException();
            parcel2.writeInt(radioPowerState);
            return true;
        }

        private boolean onTransact$isCapable$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            int readInt3 = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean isCapable = isCapable(readInt, readInt2, readInt3);
            parcel2.writeNoException();
            parcel2.writeBoolean(isCapable);
            return true;
        }

        private boolean onTransact$isAvailable$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            int readInt3 = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean isAvailable = isAvailable(readInt, readInt2, readInt3);
            parcel2.writeNoException();
            parcel2.writeBoolean(isAvailable);
            return true;
        }

        private boolean onTransact$isMmTelCapabilitySupported$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            com.android.internal.telephony.IIntegerConsumer asInterface = com.android.internal.telephony.IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
            int readInt2 = parcel.readInt();
            int readInt3 = parcel.readInt();
            parcel.enforceNoDataAvail();
            isMmTelCapabilitySupported(readInt, asInterface, readInt2, readInt3);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setVoWiFiNonPersistent$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            int readInt2 = parcel.readInt();
            parcel.enforceNoDataAvail();
            setVoWiFiNonPersistent(readInt, readBoolean, readInt2);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setImsProvisioningStatusForCapability$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            int readInt3 = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setImsProvisioningStatusForCapability(readInt, readInt2, readInt3, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getImsProvisioningStatusForCapability$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            int readInt3 = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean imsProvisioningStatusForCapability = getImsProvisioningStatusForCapability(readInt, readInt2, readInt3);
            parcel2.writeNoException();
            parcel2.writeBoolean(imsProvisioningStatusForCapability);
            return true;
        }

        private boolean onTransact$getRcsProvisioningStatusForCapability$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            int readInt3 = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean rcsProvisioningStatusForCapability = getRcsProvisioningStatusForCapability(readInt, readInt2, readInt3);
            parcel2.writeNoException();
            parcel2.writeBoolean(rcsProvisioningStatusForCapability);
            return true;
        }

        private boolean onTransact$setRcsProvisioningStatusForCapability$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            int readInt3 = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setRcsProvisioningStatusForCapability(readInt, readInt2, readInt3, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setImsProvisioningInt$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            int readInt3 = parcel.readInt();
            parcel.enforceNoDataAvail();
            int imsProvisioningInt = setImsProvisioningInt(readInt, readInt2, readInt3);
            parcel2.writeNoException();
            parcel2.writeInt(imsProvisioningInt);
            return true;
        }

        private boolean onTransact$setImsProvisioningString$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            java.lang.String readString = parcel.readString();
            parcel.enforceNoDataAvail();
            int imsProvisioningString = setImsProvisioningString(readInt, readInt2, readString);
            parcel2.writeNoException();
            parcel2.writeInt(imsProvisioningString);
            return true;
        }

        private boolean onTransact$doesSwitchMultiSimConfigTriggerReboot$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            boolean doesSwitchMultiSimConfigTriggerReboot = doesSwitchMultiSimConfigTriggerReboot(readInt, readString, readString2);
            parcel2.writeNoException();
            parcel2.writeBoolean(doesSwitchMultiSimConfigTriggerReboot);
            return true;
        }

        private boolean onTransact$isModemEnabledForSlot$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            boolean isModemEnabledForSlot = isModemEnabledForSlot(readInt, readString, readString2);
            parcel2.writeNoException();
            parcel2.writeBoolean(isModemEnabledForSlot);
            return true;
        }

        private boolean onTransact$isDataEnabledForApn$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            java.lang.String readString = parcel.readString();
            parcel.enforceNoDataAvail();
            boolean isDataEnabledForApn = isDataEnabledForApn(readInt, readInt2, readString);
            parcel2.writeNoException();
            parcel2.writeBoolean(isDataEnabledForApn);
            return true;
        }

        private boolean onTransact$setSystemSelectionChannels$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.telephony.RadioAccessSpecifier.CREATOR);
            int readInt = parcel.readInt();
            com.android.internal.telephony.IBooleanConsumer asInterface = com.android.internal.telephony.IBooleanConsumer.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            setSystemSelectionChannels(createTypedArrayList, readInt, asInterface);
            return true;
        }

        private boolean onTransact$isMvnoMatched$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            java.lang.String readString = parcel.readString();
            parcel.enforceNoDataAvail();
            boolean isMvnoMatched = isMvnoMatched(readInt, readInt2, readString);
            parcel2.writeNoException();
            parcel2.writeBoolean(isMvnoMatched);
            return true;
        }

        private boolean onTransact$enqueueSmsPickResult$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            com.android.internal.telephony.IIntegerConsumer asInterface = com.android.internal.telephony.IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            enqueueSmsPickResult(readString, readString2, asInterface);
            return true;
        }

        private boolean onTransact$setMobileDataPolicyEnabled$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setMobileDataPolicyEnabled(readInt, readInt2, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$notifyRcsAutoConfigurationReceived$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            byte[] createByteArray = parcel.createByteArray();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            notifyRcsAutoConfigurationReceived(readInt, createByteArray, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setIccLockEnabled$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            java.lang.String readString = parcel.readString();
            parcel.enforceNoDataAvail();
            int iccLockEnabled = setIccLockEnabled(readInt, readBoolean, readString);
            parcel2.writeNoException();
            parcel2.writeInt(iccLockEnabled);
            return true;
        }

        private boolean onTransact$changeIccLockPassword$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            int changeIccLockPassword = changeIccLockPassword(readInt, readString, readString2);
            parcel2.writeNoException();
            parcel2.writeInt(changeIccLockPassword);
            return true;
        }

        private boolean onTransact$getEquivalentHomePlmns$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            java.util.List<java.lang.String> equivalentHomePlmns = getEquivalentHomePlmns(readInt, readString, readString2);
            parcel2.writeNoException();
            parcel2.writeStringList(equivalentHomePlmns);
            return true;
        }

        private boolean onTransact$sendThermalMitigationRequest$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            android.telephony.ThermalMitigationRequest thermalMitigationRequest = (android.telephony.ThermalMitigationRequest) parcel.readTypedObject(android.telephony.ThermalMitigationRequest.CREATOR);
            java.lang.String readString = parcel.readString();
            parcel.enforceNoDataAvail();
            int sendThermalMitigationRequest = sendThermalMitigationRequest(readInt, thermalMitigationRequest, readString);
            parcel2.writeNoException();
            parcel2.writeInt(sendThermalMitigationRequest);
            return true;
        }

        private boolean onTransact$bootstrapAuthenticationRequest$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            android.net.Uri uri = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
            android.telephony.gba.UaSecurityProtocolIdentifier uaSecurityProtocolIdentifier = (android.telephony.gba.UaSecurityProtocolIdentifier) parcel.readTypedObject(android.telephony.gba.UaSecurityProtocolIdentifier.CREATOR);
            boolean readBoolean = parcel.readBoolean();
            android.telephony.IBootstrapAuthenticationCallback asInterface = android.telephony.IBootstrapAuthenticationCallback.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            bootstrapAuthenticationRequest(readInt, readInt2, uri, uaSecurityProtocolIdentifier, readBoolean, asInterface);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setSignalStrengthUpdateRequest$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            android.telephony.SignalStrengthUpdateRequest signalStrengthUpdateRequest = (android.telephony.SignalStrengthUpdateRequest) parcel.readTypedObject(android.telephony.SignalStrengthUpdateRequest.CREATOR);
            java.lang.String readString = parcel.readString();
            parcel.enforceNoDataAvail();
            setSignalStrengthUpdateRequest(readInt, signalStrengthUpdateRequest, readString);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$clearSignalStrengthUpdateRequest$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            android.telephony.SignalStrengthUpdateRequest signalStrengthUpdateRequest = (android.telephony.SignalStrengthUpdateRequest) parcel.readTypedObject(android.telephony.SignalStrengthUpdateRequest.CREATOR);
            java.lang.String readString = parcel.readString();
            parcel.enforceNoDataAvail();
            clearSignalStrengthUpdateRequest(readInt, signalStrengthUpdateRequest, readString);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$purchasePremiumCapability$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            com.android.internal.telephony.IIntegerConsumer asInterface = com.android.internal.telephony.IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
            int readInt2 = parcel.readInt();
            parcel.enforceNoDataAvail();
            purchasePremiumCapability(readInt, asInterface, readInt2);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$registerImsStateCallback$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            com.android.internal.telephony.IImsStateCallback asInterface = com.android.internal.telephony.IImsStateCallback.Stub.asInterface(parcel.readStrongBinder());
            java.lang.String readString = parcel.readString();
            parcel.enforceNoDataAvail();
            registerImsStateCallback(readInt, readInt2, asInterface, readString);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getLastKnownCellIdentity$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            android.telephony.CellIdentity lastKnownCellIdentity = getLastKnownCellIdentity(readInt, readString, readString2);
            parcel2.writeNoException();
            parcel2.writeTypedObject(lastKnownCellIdentity, 1);
            return true;
        }

        private boolean onTransact$isProvisioningRequiredForCapability$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            int readInt3 = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean isProvisioningRequiredForCapability = isProvisioningRequiredForCapability(readInt, readInt2, readInt3);
            parcel2.writeNoException();
            parcel2.writeBoolean(isProvisioningRequiredForCapability);
            return true;
        }

        private boolean onTransact$isRcsProvisioningRequiredForCapability$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            int readInt3 = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean isRcsProvisioningRequiredForCapability = isRcsProvisioningRequiredForCapability(readInt, readInt2, readInt3);
            parcel2.writeNoException();
            parcel2.writeBoolean(isRcsProvisioningRequiredForCapability);
            return true;
        }

        private boolean onTransact$setVoiceServiceStateOverride$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            java.lang.String readString = parcel.readString();
            parcel.enforceNoDataAvail();
            setVoiceServiceStateOverride(readInt, readBoolean, readString);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getDefaultRespondViaMessageApplication$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            android.content.ComponentName defaultRespondViaMessageApplication = getDefaultRespondViaMessageApplication(readInt, readBoolean);
            parcel2.writeNoException();
            parcel2.writeTypedObject(defaultRespondViaMessageApplication, 1);
            return true;
        }

        private boolean onTransact$persistEmergencyCallDiagnosticData$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            java.lang.String readString = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            long readLong = parcel.readLong();
            boolean readBoolean2 = parcel.readBoolean();
            boolean readBoolean3 = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            persistEmergencyCallDiagnosticData(readString, readBoolean, readLong, readBoolean2, readBoolean3);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setCellBroadcastIdRanges$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.telephony.CellBroadcastIdRange.CREATOR);
            com.android.internal.telephony.IIntegerConsumer asInterface = com.android.internal.telephony.IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            setCellBroadcastIdRanges(readInt, createTypedArrayList, asInterface);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getCarrierRestrictionStatus$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            com.android.internal.telephony.IIntegerConsumer asInterface = com.android.internal.telephony.IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
            java.lang.String readString = parcel.readString();
            parcel.enforceNoDataAvail();
            getCarrierRestrictionStatus(asInterface, readString);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$requestSatelliteEnabled$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            boolean readBoolean2 = parcel.readBoolean();
            boolean readBoolean3 = parcel.readBoolean();
            com.android.internal.telephony.IIntegerConsumer asInterface = com.android.internal.telephony.IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            requestSatelliteEnabled(readInt, readBoolean, readBoolean2, readBoolean3, asInterface);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$requestIsSatelliteEnabled$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            android.os.ResultReceiver resultReceiver = (android.os.ResultReceiver) parcel.readTypedObject(android.os.ResultReceiver.CREATOR);
            parcel.enforceNoDataAvail();
            requestIsSatelliteEnabled(readInt, resultReceiver);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$requestIsDemoModeEnabled$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            android.os.ResultReceiver resultReceiver = (android.os.ResultReceiver) parcel.readTypedObject(android.os.ResultReceiver.CREATOR);
            parcel.enforceNoDataAvail();
            requestIsDemoModeEnabled(readInt, resultReceiver);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$requestIsEmergencyModeEnabled$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            android.os.ResultReceiver resultReceiver = (android.os.ResultReceiver) parcel.readTypedObject(android.os.ResultReceiver.CREATOR);
            parcel.enforceNoDataAvail();
            requestIsEmergencyModeEnabled(readInt, resultReceiver);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$requestIsSatelliteSupported$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            android.os.ResultReceiver resultReceiver = (android.os.ResultReceiver) parcel.readTypedObject(android.os.ResultReceiver.CREATOR);
            parcel.enforceNoDataAvail();
            requestIsSatelliteSupported(readInt, resultReceiver);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$requestSatelliteCapabilities$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            android.os.ResultReceiver resultReceiver = (android.os.ResultReceiver) parcel.readTypedObject(android.os.ResultReceiver.CREATOR);
            parcel.enforceNoDataAvail();
            requestSatelliteCapabilities(readInt, resultReceiver);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$startSatelliteTransmissionUpdates$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            com.android.internal.telephony.IIntegerConsumer asInterface = com.android.internal.telephony.IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
            android.telephony.satellite.ISatelliteTransmissionUpdateCallback asInterface2 = android.telephony.satellite.ISatelliteTransmissionUpdateCallback.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            startSatelliteTransmissionUpdates(readInt, asInterface, asInterface2);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$stopSatelliteTransmissionUpdates$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            com.android.internal.telephony.IIntegerConsumer asInterface = com.android.internal.telephony.IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
            android.telephony.satellite.ISatelliteTransmissionUpdateCallback asInterface2 = android.telephony.satellite.ISatelliteTransmissionUpdateCallback.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            stopSatelliteTransmissionUpdates(readInt, asInterface, asInterface2);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$provisionSatelliteService$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            java.lang.String readString = parcel.readString();
            byte[] createByteArray = parcel.createByteArray();
            com.android.internal.telephony.IIntegerConsumer asInterface = com.android.internal.telephony.IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            android.os.ICancellationSignal provisionSatelliteService = provisionSatelliteService(readInt, readString, createByteArray, asInterface);
            parcel2.writeNoException();
            parcel2.writeStrongInterface(provisionSatelliteService);
            return true;
        }

        private boolean onTransact$deprovisionSatelliteService$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            java.lang.String readString = parcel.readString();
            com.android.internal.telephony.IIntegerConsumer asInterface = com.android.internal.telephony.IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            deprovisionSatelliteService(readInt, readString, asInterface);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$registerForSatelliteProvisionStateChanged$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            android.telephony.satellite.ISatelliteProvisionStateCallback asInterface = android.telephony.satellite.ISatelliteProvisionStateCallback.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            int registerForSatelliteProvisionStateChanged = registerForSatelliteProvisionStateChanged(readInt, asInterface);
            parcel2.writeNoException();
            parcel2.writeInt(registerForSatelliteProvisionStateChanged);
            return true;
        }

        private boolean onTransact$unregisterForSatelliteProvisionStateChanged$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            android.telephony.satellite.ISatelliteProvisionStateCallback asInterface = android.telephony.satellite.ISatelliteProvisionStateCallback.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            unregisterForSatelliteProvisionStateChanged(readInt, asInterface);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$requestIsSatelliteProvisioned$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            android.os.ResultReceiver resultReceiver = (android.os.ResultReceiver) parcel.readTypedObject(android.os.ResultReceiver.CREATOR);
            parcel.enforceNoDataAvail();
            requestIsSatelliteProvisioned(readInt, resultReceiver);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$registerForSatelliteModemStateChanged$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            android.telephony.satellite.ISatelliteModemStateCallback asInterface = android.telephony.satellite.ISatelliteModemStateCallback.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            int registerForSatelliteModemStateChanged = registerForSatelliteModemStateChanged(readInt, asInterface);
            parcel2.writeNoException();
            parcel2.writeInt(registerForSatelliteModemStateChanged);
            return true;
        }

        private boolean onTransact$unregisterForModemStateChanged$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            android.telephony.satellite.ISatelliteModemStateCallback asInterface = android.telephony.satellite.ISatelliteModemStateCallback.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            unregisterForModemStateChanged(readInt, asInterface);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$registerForIncomingDatagram$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            android.telephony.satellite.ISatelliteDatagramCallback asInterface = android.telephony.satellite.ISatelliteDatagramCallback.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            int registerForIncomingDatagram = registerForIncomingDatagram(readInt, asInterface);
            parcel2.writeNoException();
            parcel2.writeInt(registerForIncomingDatagram);
            return true;
        }

        private boolean onTransact$unregisterForIncomingDatagram$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            android.telephony.satellite.ISatelliteDatagramCallback asInterface = android.telephony.satellite.ISatelliteDatagramCallback.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            unregisterForIncomingDatagram(readInt, asInterface);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$pollPendingDatagrams$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            com.android.internal.telephony.IIntegerConsumer asInterface = com.android.internal.telephony.IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            pollPendingDatagrams(readInt, asInterface);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$sendDatagram$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            android.telephony.satellite.SatelliteDatagram satelliteDatagram = (android.telephony.satellite.SatelliteDatagram) parcel.readTypedObject(android.telephony.satellite.SatelliteDatagram.CREATOR);
            boolean readBoolean = parcel.readBoolean();
            com.android.internal.telephony.IIntegerConsumer asInterface = com.android.internal.telephony.IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            sendDatagram(readInt, readInt2, satelliteDatagram, readBoolean, asInterface);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$requestIsCommunicationAllowedForCurrentLocation$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            android.os.ResultReceiver resultReceiver = (android.os.ResultReceiver) parcel.readTypedObject(android.os.ResultReceiver.CREATOR);
            parcel.enforceNoDataAvail();
            requestIsCommunicationAllowedForCurrentLocation(readInt, resultReceiver);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$requestTimeForNextSatelliteVisibility$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            android.os.ResultReceiver resultReceiver = (android.os.ResultReceiver) parcel.readTypedObject(android.os.ResultReceiver.CREATOR);
            parcel.enforceNoDataAvail();
            requestTimeForNextSatelliteVisibility(readInt, resultReceiver);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setDeviceAlignedWithSatellite$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setDeviceAlignedWithSatellite(readInt, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setSatellitePointingUiClassName$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            boolean satellitePointingUiClassName = setSatellitePointingUiClassName(readString, readString2);
            parcel2.writeNoException();
            parcel2.writeBoolean(satellitePointingUiClassName);
            return true;
        }

        private boolean onTransact$setDatagramControllerTimeoutDuration$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            boolean readBoolean = parcel.readBoolean();
            int readInt = parcel.readInt();
            long readLong = parcel.readLong();
            parcel.enforceNoDataAvail();
            boolean datagramControllerTimeoutDuration = setDatagramControllerTimeoutDuration(readBoolean, readInt, readLong);
            parcel2.writeNoException();
            parcel2.writeBoolean(datagramControllerTimeoutDuration);
            return true;
        }

        private boolean onTransact$setSatelliteControllerTimeoutDuration$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            boolean readBoolean = parcel.readBoolean();
            int readInt = parcel.readInt();
            long readLong = parcel.readLong();
            parcel.enforceNoDataAvail();
            boolean satelliteControllerTimeoutDuration = setSatelliteControllerTimeoutDuration(readBoolean, readInt, readLong);
            parcel2.writeNoException();
            parcel2.writeBoolean(satelliteControllerTimeoutDuration);
            return true;
        }

        private boolean onTransact$setEmergencyCallToSatelliteHandoverType$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean emergencyCallToSatelliteHandoverType = setEmergencyCallToSatelliteHandoverType(readInt, readInt2);
            parcel2.writeNoException();
            parcel2.writeBoolean(emergencyCallToSatelliteHandoverType);
            return true;
        }

        private boolean onTransact$setCountryCodes$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            boolean readBoolean = parcel.readBoolean();
            java.util.ArrayList<java.lang.String> createStringArrayList = parcel.createStringArrayList();
            java.util.HashMap readHashMap = parcel.readHashMap(getClass().getClassLoader());
            java.lang.String readString = parcel.readString();
            long readLong = parcel.readLong();
            parcel.enforceNoDataAvail();
            boolean countryCodes = setCountryCodes(readBoolean, createStringArrayList, readHashMap, readString, readLong);
            parcel2.writeNoException();
            parcel2.writeBoolean(countryCodes);
            return true;
        }

        private boolean onTransact$setSatelliteAccessControlOverlayConfigs$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            boolean readBoolean = parcel.readBoolean();
            boolean readBoolean2 = parcel.readBoolean();
            java.lang.String readString = parcel.readString();
            long readLong = parcel.readLong();
            java.util.ArrayList<java.lang.String> createStringArrayList = parcel.createStringArrayList();
            parcel.enforceNoDataAvail();
            boolean satelliteAccessControlOverlayConfigs = setSatelliteAccessControlOverlayConfigs(readBoolean, readBoolean2, readString, readLong, createStringArrayList);
            parcel2.writeNoException();
            parcel2.writeBoolean(satelliteAccessControlOverlayConfigs);
            return true;
        }

        private boolean onTransact$setOemEnabledSatelliteProvisionStatus$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            boolean readBoolean = parcel.readBoolean();
            boolean readBoolean2 = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            boolean oemEnabledSatelliteProvisionStatus = setOemEnabledSatelliteProvisionStatus(readBoolean, readBoolean2);
            parcel2.writeNoException();
            parcel2.writeBoolean(oemEnabledSatelliteProvisionStatus);
            return true;
        }

        private boolean onTransact$getShaIdFromAllowList$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            java.lang.String readString = parcel.readString();
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            java.util.List<java.lang.String> shaIdFromAllowList = getShaIdFromAllowList(readString, readInt);
            parcel2.writeNoException();
            parcel2.writeStringList(shaIdFromAllowList);
            return true;
        }

        private boolean onTransact$addAttachRestrictionForCarrier$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            com.android.internal.telephony.IIntegerConsumer asInterface = com.android.internal.telephony.IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            addAttachRestrictionForCarrier(readInt, readInt2, asInterface);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$removeAttachRestrictionForCarrier$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            com.android.internal.telephony.IIntegerConsumer asInterface = com.android.internal.telephony.IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            removeAttachRestrictionForCarrier(readInt, readInt2, asInterface);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$requestNtnSignalStrength$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            android.os.ResultReceiver resultReceiver = (android.os.ResultReceiver) parcel.readTypedObject(android.os.ResultReceiver.CREATOR);
            parcel.enforceNoDataAvail();
            requestNtnSignalStrength(readInt, resultReceiver);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$registerForNtnSignalStrengthChanged$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            android.telephony.satellite.INtnSignalStrengthCallback asInterface = android.telephony.satellite.INtnSignalStrengthCallback.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            registerForNtnSignalStrengthChanged(readInt, asInterface);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$unregisterForNtnSignalStrengthChanged$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            android.telephony.satellite.INtnSignalStrengthCallback asInterface = android.telephony.satellite.INtnSignalStrengthCallback.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            unregisterForNtnSignalStrengthChanged(readInt, asInterface);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$registerForCapabilitiesChanged$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            android.telephony.satellite.ISatelliteCapabilitiesCallback asInterface = android.telephony.satellite.ISatelliteCapabilitiesCallback.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            int registerForCapabilitiesChanged = registerForCapabilitiesChanged(readInt, asInterface);
            parcel2.writeNoException();
            parcel2.writeInt(registerForCapabilitiesChanged);
            return true;
        }

        private boolean onTransact$unregisterForCapabilitiesChanged$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            android.telephony.satellite.ISatelliteCapabilitiesCallback asInterface = android.telephony.satellite.ISatelliteCapabilitiesCallback.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            unregisterForCapabilitiesChanged(readInt, asInterface);
            parcel2.writeNoException();
            return true;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 406;
        }
    }
}
