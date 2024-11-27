package android.app.admin;

/* loaded from: classes.dex */
public interface IDevicePolicyManager extends android.os.IInterface {
    void acknowledgeDeviceCompliant() throws android.os.RemoteException;

    void acknowledgeNewUserDisclaimer(int i) throws android.os.RemoteException;

    void addCrossProfileIntentFilter(android.content.ComponentName componentName, java.lang.String str, android.content.IntentFilter intentFilter, int i) throws android.os.RemoteException;

    boolean addCrossProfileWidgetProvider(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    int addOverrideApn(android.content.ComponentName componentName, android.telephony.data.ApnSetting apnSetting) throws android.os.RemoteException;

    void addPersistentPreferredActivity(android.content.ComponentName componentName, java.lang.String str, android.content.IntentFilter intentFilter, android.content.ComponentName componentName2) throws android.os.RemoteException;

    boolean approveCaCert(java.lang.String str, int i, boolean z) throws android.os.RemoteException;

    boolean bindDeviceAdminServiceAsUser(android.content.ComponentName componentName, android.app.IApplicationThread iApplicationThread, android.os.IBinder iBinder, android.content.Intent intent, android.app.IServiceConnection iServiceConnection, long j, int i) throws android.os.RemoteException;

    void calculateHasIncompatibleAccounts() throws android.os.RemoteException;

    boolean canAdminGrantSensorsPermissions() throws android.os.RemoteException;

    boolean canProfileOwnerResetPasswordWhenLocked(int i) throws android.os.RemoteException;

    boolean canUsbDataSignalingBeDisabled() throws android.os.RemoteException;

    boolean checkDeviceIdentifierAccess(java.lang.String str, int i, int i2) throws android.os.RemoteException;

    int checkProvisioningPrecondition(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void choosePrivateKeyAlias(int i, android.net.Uri uri, java.lang.String str, android.os.IBinder iBinder) throws android.os.RemoteException;

    void clearApplicationUserData(android.content.ComponentName componentName, java.lang.String str, android.content.pm.IPackageDataObserver iPackageDataObserver) throws android.os.RemoteException;

    void clearCrossProfileIntentFilters(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException;

    void clearDeviceOwner(java.lang.String str) throws android.os.RemoteException;

    void clearOrganizationIdForUser(int i) throws android.os.RemoteException;

    void clearPackagePersistentPreferredActivities(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void clearProfileOwner(android.content.ComponentName componentName) throws android.os.RemoteException;

    boolean clearResetPasswordToken(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException;

    void clearSystemUpdatePolicyFreezePeriodRecord() throws android.os.RemoteException;

    android.content.Intent createAdminSupportIntent(java.lang.String str) throws android.os.RemoteException;

    android.os.UserHandle createAndManageUser(android.content.ComponentName componentName, java.lang.String str, android.content.ComponentName componentName2, android.os.PersistableBundle persistableBundle, int i) throws android.os.RemoteException;

    android.os.UserHandle createAndProvisionManagedProfile(android.app.admin.ManagedProfileProvisioningParams managedProfileProvisioningParams, java.lang.String str) throws android.os.RemoteException;

    void enableSystemApp(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    int enableSystemAppWithIntent(android.content.ComponentName componentName, java.lang.String str, android.content.Intent intent) throws android.os.RemoteException;

    void enforceCanManageCaCerts(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException;

    void finalizeWorkProfileProvisioning(android.os.UserHandle userHandle, android.accounts.Account account) throws android.os.RemoteException;

    long forceNetworkLogs() throws android.os.RemoteException;

    void forceRemoveActiveAdmin(android.content.ComponentName componentName, int i) throws android.os.RemoteException;

    long forceSecurityLogs() throws android.os.RemoteException;

    void forceUpdateUserSetupComplete(int i) throws android.os.RemoteException;

    boolean generateKeyPair(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2, android.security.keystore.ParcelableKeyGenParameterSpec parcelableKeyGenParameterSpec, int i, android.security.keymaster.KeymasterCertificateChain keymasterCertificateChain) throws android.os.RemoteException;

    java.lang.String[] getAccountTypesWithManagementDisabled(java.lang.String str) throws android.os.RemoteException;

    java.lang.String[] getAccountTypesWithManagementDisabledAsUser(int i, java.lang.String str, boolean z) throws android.os.RemoteException;

    java.util.List<android.content.ComponentName> getActiveAdmins(int i) throws android.os.RemoteException;

    java.util.List<java.lang.String> getAffiliationIds(android.content.ComponentName componentName) throws android.os.RemoteException;

    int getAggregatedPasswordComplexityForUser(int i, boolean z) throws android.os.RemoteException;

    java.util.List<java.lang.String> getAllCrossProfilePackages(int i) throws android.os.RemoteException;

    java.util.List<java.lang.String> getAlwaysOnVpnLockdownAllowlist(android.content.ComponentName componentName) throws android.os.RemoteException;

    java.lang.String getAlwaysOnVpnPackage(android.content.ComponentName componentName) throws android.os.RemoteException;

    java.lang.String getAlwaysOnVpnPackageForUser(int i) throws android.os.RemoteException;

    int[] getApplicationExemptions(java.lang.String str) throws android.os.RemoteException;

    android.os.Bundle getApplicationRestrictions(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    java.lang.String getApplicationRestrictionsManagingPackage(android.content.ComponentName componentName) throws android.os.RemoteException;

    boolean getAutoTimeEnabled(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException;

    boolean getAutoTimeRequired() throws android.os.RemoteException;

    boolean getAutoTimeZoneEnabled(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException;

    java.util.List<android.os.UserHandle> getBindDeviceAdminTargetUsers(android.content.ComponentName componentName) throws android.os.RemoteException;

    boolean getBluetoothContactSharingDisabled(android.content.ComponentName componentName) throws android.os.RemoteException;

    boolean getBluetoothContactSharingDisabledForUser(int i) throws android.os.RemoteException;

    boolean getCameraDisabled(android.content.ComponentName componentName, java.lang.String str, int i, boolean z) throws android.os.RemoteException;

    java.lang.String getCertInstallerPackage(android.content.ComponentName componentName) throws android.os.RemoteException;

    int getContentProtectionPolicy(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException;

    android.app.admin.PackagePolicy getCredentialManagerPolicy(int i) throws android.os.RemoteException;

    java.util.List<java.lang.String> getCrossProfileCalendarPackages(android.content.ComponentName componentName) throws android.os.RemoteException;

    java.util.List<java.lang.String> getCrossProfileCalendarPackagesForUser(int i) throws android.os.RemoteException;

    boolean getCrossProfileCallerIdDisabled(android.content.ComponentName componentName) throws android.os.RemoteException;

    boolean getCrossProfileCallerIdDisabledForUser(int i) throws android.os.RemoteException;

    boolean getCrossProfileContactsSearchDisabled(android.content.ComponentName componentName) throws android.os.RemoteException;

    boolean getCrossProfileContactsSearchDisabledForUser(int i) throws android.os.RemoteException;

    java.util.List<java.lang.String> getCrossProfilePackages(android.content.ComponentName componentName) throws android.os.RemoteException;

    java.util.List<java.lang.String> getCrossProfileWidgetProviders(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException;

    int getCurrentFailedPasswordAttempts(java.lang.String str, int i, boolean z) throws android.os.RemoteException;

    java.util.List<java.lang.String> getDefaultCrossProfilePackages() throws android.os.RemoteException;

    java.util.List<java.lang.String> getDelegatePackages(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException;

    java.util.List<java.lang.String> getDelegatedScopes(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException;

    android.content.ComponentName getDeviceOwnerComponent(boolean z) throws android.os.RemoteException;

    android.content.ComponentName getDeviceOwnerComponentOnUser(int i) throws android.os.RemoteException;

    java.lang.CharSequence getDeviceOwnerLockScreenInfo() throws android.os.RemoteException;

    java.lang.String getDeviceOwnerName() throws android.os.RemoteException;

    java.lang.CharSequence getDeviceOwnerOrganizationName() throws android.os.RemoteException;

    int getDeviceOwnerType(android.content.ComponentName componentName) throws android.os.RemoteException;

    int getDeviceOwnerUserId() throws android.os.RemoteException;

    android.app.admin.DevicePolicyState getDevicePolicyState() throws android.os.RemoteException;

    java.util.List<java.lang.String> getDisallowedSystemApps(android.content.ComponentName componentName, int i, java.lang.String str) throws android.os.RemoteException;

    boolean getDoNotAskCredentialsOnBoot() throws android.os.RemoteException;

    android.app.admin.ParcelableResource getDrawable(java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException;

    java.lang.CharSequence getEndUserSessionMessage(android.content.ComponentName componentName) throws android.os.RemoteException;

    android.os.Bundle getEnforcingAdminAndUserDetails(int i, java.lang.String str) throws android.os.RemoteException;

    java.util.List<android.app.admin.EnforcingAdmin> getEnforcingAdminsForRestriction(int i, java.lang.String str) throws android.os.RemoteException;

    java.lang.String getEnrollmentSpecificId(java.lang.String str) throws android.os.RemoteException;

    android.app.admin.FactoryResetProtectionPolicy getFactoryResetProtectionPolicy(android.content.ComponentName componentName) throws android.os.RemoteException;

    java.lang.String getFinancedDeviceKioskRoleHolder(java.lang.String str) throws android.os.RemoteException;

    boolean getForceEphemeralUsers(android.content.ComponentName componentName) throws android.os.RemoteException;

    java.lang.String getGlobalPrivateDnsHost(android.content.ComponentName componentName) throws android.os.RemoteException;

    int getGlobalPrivateDnsMode(android.content.ComponentName componentName) throws android.os.RemoteException;

    android.content.ComponentName getGlobalProxyAdmin(int i) throws android.os.RemoteException;

    java.util.List<java.lang.String> getKeepUninstalledPackages(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException;

    android.app.admin.ParcelableGranteeMap getKeyPairGrants(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    int getKeyguardDisabledFeatures(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException;

    long getLastBugReportRequestTime() throws android.os.RemoteException;

    long getLastNetworkLogRetrievalTime() throws android.os.RemoteException;

    long getLastSecurityLogRetrievalTime() throws android.os.RemoteException;

    int getLockTaskFeatures(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException;

    java.lang.String[] getLockTaskPackages(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException;

    int getLogoutUserId() throws android.os.RemoteException;

    java.lang.CharSequence getLongSupportMessage(android.content.ComponentName componentName) throws android.os.RemoteException;

    java.lang.CharSequence getLongSupportMessageForUser(android.content.ComponentName componentName, int i) throws android.os.RemoteException;

    android.app.admin.PackagePolicy getManagedProfileCallerIdAccessPolicy() throws android.os.RemoteException;

    android.app.admin.PackagePolicy getManagedProfileContactsAccessPolicy() throws android.os.RemoteException;

    long getManagedProfileMaximumTimeOff(android.content.ComponentName componentName) throws android.os.RemoteException;

    android.app.admin.ManagedSubscriptionsPolicy getManagedSubscriptionsPolicy() throws android.os.RemoteException;

    int getMaxPolicyStorageLimit(java.lang.String str) throws android.os.RemoteException;

    int getMaximumFailedPasswordsForWipe(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException;

    long getMaximumTimeToLock(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException;

    java.util.List<java.lang.String> getMeteredDataDisabledPackages(android.content.ComponentName componentName) throws android.os.RemoteException;

    int getMinimumRequiredWifiSecurityLevel() throws android.os.RemoteException;

    int getMtePolicy(java.lang.String str) throws android.os.RemoteException;

    int getNearbyAppStreamingPolicy(int i) throws android.os.RemoteException;

    int getNearbyNotificationStreamingPolicy(int i) throws android.os.RemoteException;

    int getOrganizationColor(android.content.ComponentName componentName) throws android.os.RemoteException;

    int getOrganizationColorForUser(int i) throws android.os.RemoteException;

    java.lang.CharSequence getOrganizationName(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException;

    java.lang.CharSequence getOrganizationNameForUser(int i) throws android.os.RemoteException;

    java.util.List<android.telephony.data.ApnSetting> getOverrideApns(android.content.ComponentName componentName) throws android.os.RemoteException;

    android.content.pm.StringParceledListSlice getOwnerInstalledCaCerts(android.os.UserHandle userHandle) throws android.os.RemoteException;

    int getPasswordComplexity(boolean z) throws android.os.RemoteException;

    long getPasswordExpiration(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException;

    long getPasswordExpirationTimeout(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException;

    int getPasswordHistoryLength(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException;

    int getPasswordMinimumLength(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException;

    int getPasswordMinimumLetters(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException;

    int getPasswordMinimumLowerCase(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException;

    android.app.admin.PasswordMetrics getPasswordMinimumMetrics(int i, boolean z) throws android.os.RemoteException;

    int getPasswordMinimumNonLetter(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException;

    int getPasswordMinimumNumeric(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException;

    int getPasswordMinimumSymbols(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException;

    int getPasswordMinimumUpperCase(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException;

    int getPasswordQuality(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException;

    android.app.admin.SystemUpdateInfo getPendingSystemUpdate(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException;

    int getPermissionGrantState(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException;

    int getPermissionPolicy(android.content.ComponentName componentName) throws android.os.RemoteException;

    java.util.List<java.lang.String> getPermittedAccessibilityServices(android.content.ComponentName componentName) throws android.os.RemoteException;

    java.util.List<java.lang.String> getPermittedAccessibilityServicesForUser(int i) throws android.os.RemoteException;

    java.util.List<java.lang.String> getPermittedCrossProfileNotificationListeners(android.content.ComponentName componentName) throws android.os.RemoteException;

    java.util.List<java.lang.String> getPermittedInputMethods(android.content.ComponentName componentName, java.lang.String str, boolean z) throws android.os.RemoteException;

    java.util.List<java.lang.String> getPermittedInputMethodsAsUser(int i) throws android.os.RemoteException;

    int getPersonalAppsSuspendedReasons(android.content.ComponentName componentName) throws android.os.RemoteException;

    java.util.List<android.os.UserHandle> getPolicyManagedProfiles(android.os.UserHandle userHandle) throws android.os.RemoteException;

    java.util.List<android.app.admin.PreferentialNetworkServiceConfig> getPreferentialNetworkServiceConfigs() throws android.os.RemoteException;

    android.content.ComponentName getProfileOwnerAsUser(int i) throws android.os.RemoteException;

    java.lang.String getProfileOwnerName(int i) throws android.os.RemoteException;

    android.content.ComponentName getProfileOwnerOrDeviceOwnerSupervisionComponent(android.os.UserHandle userHandle) throws android.os.RemoteException;

    int getProfileWithMinimumFailedPasswordsForWipe(int i, boolean z) throws android.os.RemoteException;

    void getRemoveWarning(android.content.ComponentName componentName, android.os.RemoteCallback remoteCallback, int i) throws android.os.RemoteException;

    int getRequiredPasswordComplexity(java.lang.String str, boolean z) throws android.os.RemoteException;

    long getRequiredStrongAuthTimeout(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException;

    android.content.ComponentName getRestrictionsProvider(int i) throws android.os.RemoteException;

    boolean getScreenCaptureDisabled(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException;

    java.util.List<android.os.UserHandle> getSecondaryUsers(android.content.ComponentName componentName) throws android.os.RemoteException;

    java.lang.CharSequence getShortSupportMessage(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException;

    java.lang.CharSequence getShortSupportMessageForUser(android.content.ComponentName componentName, int i) throws android.os.RemoteException;

    java.lang.CharSequence getStartUserSessionMessage(android.content.ComponentName componentName) throws android.os.RemoteException;

    boolean getStorageEncryption(android.content.ComponentName componentName, int i) throws android.os.RemoteException;

    int getStorageEncryptionStatus(java.lang.String str, int i) throws android.os.RemoteException;

    android.app.admin.ParcelableResource getString(java.lang.String str) throws android.os.RemoteException;

    int[] getSubscriptionIds(java.lang.String str) throws android.os.RemoteException;

    android.app.admin.SystemUpdatePolicy getSystemUpdatePolicy() throws android.os.RemoteException;

    android.os.PersistableBundle getTransferOwnershipBundle() throws android.os.RemoteException;

    java.util.List<android.os.PersistableBundle> getTrustAgentConfiguration(android.content.ComponentName componentName, android.content.ComponentName componentName2, int i, boolean z) throws android.os.RemoteException;

    java.util.List<java.lang.String> getUserControlDisabledPackages(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException;

    int getUserProvisioningState(int i) throws android.os.RemoteException;

    android.os.Bundle getUserRestrictions(android.content.ComponentName componentName, java.lang.String str, boolean z) throws android.os.RemoteException;

    android.os.Bundle getUserRestrictionsGlobally(java.lang.String str) throws android.os.RemoteException;

    java.lang.String getWifiMacAddress(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException;

    android.app.admin.WifiSsidPolicy getWifiSsidPolicy(java.lang.String str) throws android.os.RemoteException;

    boolean hasDeviceOwner() throws android.os.RemoteException;

    boolean hasGrantedPolicy(android.content.ComponentName componentName, int i, int i2) throws android.os.RemoteException;

    boolean hasKeyPair(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    boolean hasLockdownAdminConfiguredNetworks(android.content.ComponentName componentName) throws android.os.RemoteException;

    boolean hasManagedProfileCallerIdAccess(int i, java.lang.String str) throws android.os.RemoteException;

    boolean hasManagedProfileContactsAccess(int i, java.lang.String str) throws android.os.RemoteException;

    boolean hasUserSetupCompleted() throws android.os.RemoteException;

    boolean installCaCert(android.content.ComponentName componentName, java.lang.String str, byte[] bArr) throws android.os.RemoteException;

    boolean installExistingPackage(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    boolean installKeyPair(android.content.ComponentName componentName, java.lang.String str, byte[] bArr, byte[] bArr2, byte[] bArr3, java.lang.String str2, boolean z, boolean z2) throws android.os.RemoteException;

    void installUpdateFromFile(android.content.ComponentName componentName, java.lang.String str, android.os.ParcelFileDescriptor parcelFileDescriptor, android.app.admin.StartInstallingUpdateCallback startInstallingUpdateCallback) throws android.os.RemoteException;

    boolean isAccessibilityServicePermittedByAdmin(android.content.ComponentName componentName, java.lang.String str, int i) throws android.os.RemoteException;

    boolean isActivePasswordSufficient(java.lang.String str, int i, boolean z) throws android.os.RemoteException;

    boolean isActivePasswordSufficientForDeviceRequirement() throws android.os.RemoteException;

    boolean isAdminActive(android.content.ComponentName componentName, int i) throws android.os.RemoteException;

    boolean isAffiliatedUser(int i) throws android.os.RemoteException;

    boolean isAlwaysOnVpnLockdownEnabled(android.content.ComponentName componentName) throws android.os.RemoteException;

    boolean isAlwaysOnVpnLockdownEnabledForUser(int i) throws android.os.RemoteException;

    boolean isApplicationHidden(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2, boolean z) throws android.os.RemoteException;

    boolean isAuditLogEnabled(java.lang.String str) throws android.os.RemoteException;

    boolean isBackupServiceEnabled(android.content.ComponentName componentName) throws android.os.RemoteException;

    boolean isCaCertApproved(java.lang.String str, int i) throws android.os.RemoteException;

    boolean isCallerApplicationRestrictionsManagingPackage(java.lang.String str) throws android.os.RemoteException;

    boolean isCallingUserAffiliated() throws android.os.RemoteException;

    boolean isCommonCriteriaModeEnabled(android.content.ComponentName componentName) throws android.os.RemoteException;

    boolean isComplianceAcknowledgementRequired() throws android.os.RemoteException;

    boolean isCurrentInputMethodSetByOwner() throws android.os.RemoteException;

    boolean isDeviceFinanced(java.lang.String str) throws android.os.RemoteException;

    boolean isDeviceProvisioned() throws android.os.RemoteException;

    boolean isDeviceProvisioningConfigApplied() throws android.os.RemoteException;

    boolean isDpcDownloaded() throws android.os.RemoteException;

    boolean isEphemeralUser(android.content.ComponentName componentName) throws android.os.RemoteException;

    boolean isFactoryResetProtectionPolicySupported() throws android.os.RemoteException;

    boolean isInputMethodPermittedByAdmin(android.content.ComponentName componentName, java.lang.String str, int i, boolean z) throws android.os.RemoteException;

    boolean isKeyPairGrantedToWifiAuth(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    boolean isLockTaskPermitted(java.lang.String str) throws android.os.RemoteException;

    boolean isLogoutEnabled() throws android.os.RemoteException;

    boolean isManagedKiosk() throws android.os.RemoteException;

    boolean isManagedProfile(android.content.ComponentName componentName) throws android.os.RemoteException;

    boolean isMasterVolumeMuted(android.content.ComponentName componentName) throws android.os.RemoteException;

    boolean isMeteredDataDisabledPackageForUser(android.content.ComponentName componentName, java.lang.String str, int i) throws android.os.RemoteException;

    boolean isNetworkLoggingEnabled(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException;

    boolean isNewUserDisclaimerAcknowledged(int i) throws android.os.RemoteException;

    boolean isNotificationListenerServicePermitted(java.lang.String str, int i) throws android.os.RemoteException;

    boolean isOrganizationOwnedDeviceWithManagedProfile() throws android.os.RemoteException;

    boolean isOverrideApnEnabled(android.content.ComponentName componentName) throws android.os.RemoteException;

    boolean isPackageAllowedToAccessCalendarForUser(java.lang.String str, int i) throws android.os.RemoteException;

    boolean isPackageSuspended(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    boolean isPasswordSufficientAfterProfileUnification(int i, int i2) throws android.os.RemoteException;

    boolean isProvisioningAllowed(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    boolean isRemovingAdmin(android.content.ComponentName componentName, int i) throws android.os.RemoteException;

    boolean isResetPasswordTokenActive(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException;

    boolean isSafeOperation(int i) throws android.os.RemoteException;

    boolean isSecondaryLockscreenEnabled(android.os.UserHandle userHandle) throws android.os.RemoteException;

    boolean isSecurityLoggingEnabled(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException;

    boolean isStatusBarDisabled(java.lang.String str) throws android.os.RemoteException;

    boolean isSupervisionComponent(android.content.ComponentName componentName) throws android.os.RemoteException;

    boolean isTheftDetectionTriggered(java.lang.String str) throws android.os.RemoteException;

    boolean isUnattendedManagedKiosk() throws android.os.RemoteException;

    boolean isUninstallBlocked(java.lang.String str) throws android.os.RemoteException;

    boolean isUninstallInQueue(java.lang.String str) throws android.os.RemoteException;

    boolean isUsbDataSignalingEnabled(java.lang.String str) throws android.os.RemoteException;

    boolean isUsingUnifiedPassword(android.content.ComponentName componentName) throws android.os.RemoteException;

    java.util.List<android.os.UserHandle> listForegroundAffiliatedUsers() throws android.os.RemoteException;

    java.util.List<java.lang.String> listPolicyExemptApps() throws android.os.RemoteException;

    void lockNow(int i, java.lang.String str, boolean z) throws android.os.RemoteException;

    int logoutUser(android.content.ComponentName componentName) throws android.os.RemoteException;

    int logoutUserInternal() throws android.os.RemoteException;

    void notifyLockTaskModeChanged(boolean z, java.lang.String str, int i) throws android.os.RemoteException;

    void notifyPendingSystemUpdate(android.app.admin.SystemUpdateInfo systemUpdateInfo) throws android.os.RemoteException;

    boolean packageHasActiveAdmins(java.lang.String str, int i) throws android.os.RemoteException;

    void provisionFullyManagedDevice(android.app.admin.FullyManagedDeviceProvisioningParams fullyManagedDeviceProvisioningParams, java.lang.String str) throws android.os.RemoteException;

    void reboot(android.content.ComponentName componentName) throws android.os.RemoteException;

    void removeActiveAdmin(android.content.ComponentName componentName, int i) throws android.os.RemoteException;

    boolean removeCrossProfileWidgetProvider(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    boolean removeKeyPair(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    boolean removeOverrideApn(android.content.ComponentName componentName, int i) throws android.os.RemoteException;

    boolean removeUser(android.content.ComponentName componentName, android.os.UserHandle userHandle) throws android.os.RemoteException;

    void reportFailedBiometricAttempt(int i) throws android.os.RemoteException;

    void reportFailedPasswordAttempt(int i, boolean z) throws android.os.RemoteException;

    void reportKeyguardDismissed(int i) throws android.os.RemoteException;

    void reportKeyguardSecured(int i) throws android.os.RemoteException;

    void reportPasswordChanged(android.app.admin.PasswordMetrics passwordMetrics, int i) throws android.os.RemoteException;

    void reportSuccessfulBiometricAttempt(int i) throws android.os.RemoteException;

    void reportSuccessfulPasswordAttempt(int i) throws android.os.RemoteException;

    boolean requestBugreport(android.content.ComponentName componentName) throws android.os.RemoteException;

    boolean requireSecureKeyguard(int i) throws android.os.RemoteException;

    void resetDefaultCrossProfileIntentFilters(int i) throws android.os.RemoteException;

    void resetDrawables(java.util.List<java.lang.String> list) throws android.os.RemoteException;

    boolean resetPassword(java.lang.String str, int i) throws android.os.RemoteException;

    boolean resetPasswordWithToken(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2, byte[] bArr, int i) throws android.os.RemoteException;

    void resetShouldAllowBypassingDevicePolicyManagementRoleQualificationState() throws android.os.RemoteException;

    void resetStrings(java.util.List<java.lang.String> list) throws android.os.RemoteException;

    java.util.List<android.app.admin.NetworkEvent> retrieveNetworkLogs(android.content.ComponentName componentName, java.lang.String str, long j) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice retrievePreRebootSecurityLogs(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice retrieveSecurityLogs(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException;

    void sendLostModeLocationUpdate(com.android.internal.infra.AndroidFuture<java.lang.Boolean> androidFuture) throws android.os.RemoteException;

    void setAccountManagementDisabled(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2, boolean z, boolean z2) throws android.os.RemoteException;

    void setActiveAdmin(android.content.ComponentName componentName, boolean z, int i) throws android.os.RemoteException;

    void setAffiliationIds(android.content.ComponentName componentName, java.util.List<java.lang.String> list) throws android.os.RemoteException;

    boolean setAlwaysOnVpnPackage(android.content.ComponentName componentName, java.lang.String str, boolean z, java.util.List<java.lang.String> list) throws android.os.RemoteException;

    void setApplicationExemptions(java.lang.String str, java.lang.String str2, int[] iArr) throws android.os.RemoteException;

    boolean setApplicationHidden(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2, boolean z, boolean z2) throws android.os.RemoteException;

    void setApplicationRestrictions(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2, android.os.Bundle bundle) throws android.os.RemoteException;

    boolean setApplicationRestrictionsManagingPackage(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException;

    void setAuditLogEnabled(java.lang.String str, boolean z) throws android.os.RemoteException;

    void setAuditLogEventsCallback(java.lang.String str, android.app.admin.IAuditLogEventsCallback iAuditLogEventsCallback) throws android.os.RemoteException;

    void setAutoTimeEnabled(android.content.ComponentName componentName, java.lang.String str, boolean z) throws android.os.RemoteException;

    void setAutoTimeRequired(android.content.ComponentName componentName, boolean z) throws android.os.RemoteException;

    void setAutoTimeZoneEnabled(android.content.ComponentName componentName, java.lang.String str, boolean z) throws android.os.RemoteException;

    void setBackupServiceEnabled(android.content.ComponentName componentName, boolean z) throws android.os.RemoteException;

    void setBluetoothContactSharingDisabled(android.content.ComponentName componentName, boolean z) throws android.os.RemoteException;

    void setCameraDisabled(android.content.ComponentName componentName, java.lang.String str, boolean z, boolean z2) throws android.os.RemoteException;

    void setCertInstallerPackage(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException;

    void setCommonCriteriaModeEnabled(android.content.ComponentName componentName, java.lang.String str, boolean z) throws android.os.RemoteException;

    void setConfiguredNetworksLockdownState(android.content.ComponentName componentName, java.lang.String str, boolean z) throws android.os.RemoteException;

    void setContentProtectionPolicy(android.content.ComponentName componentName, java.lang.String str, int i) throws android.os.RemoteException;

    void setCredentialManagerPolicy(android.app.admin.PackagePolicy packagePolicy) throws android.os.RemoteException;

    void setCrossProfileCalendarPackages(android.content.ComponentName componentName, java.util.List<java.lang.String> list) throws android.os.RemoteException;

    void setCrossProfileCallerIdDisabled(android.content.ComponentName componentName, boolean z) throws android.os.RemoteException;

    void setCrossProfileContactsSearchDisabled(android.content.ComponentName componentName, boolean z) throws android.os.RemoteException;

    void setCrossProfilePackages(android.content.ComponentName componentName, java.util.List<java.lang.String> list) throws android.os.RemoteException;

    void setDefaultDialerApplication(java.lang.String str) throws android.os.RemoteException;

    void setDefaultSmsApplication(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2, boolean z) throws android.os.RemoteException;

    void setDelegatedScopes(android.content.ComponentName componentName, java.lang.String str, java.util.List<java.lang.String> list) throws android.os.RemoteException;

    boolean setDeviceOwner(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException;

    void setDeviceOwnerLockScreenInfo(android.content.ComponentName componentName, java.lang.CharSequence charSequence) throws android.os.RemoteException;

    void setDeviceOwnerType(android.content.ComponentName componentName, int i) throws android.os.RemoteException;

    void setDeviceProvisioningConfigApplied() throws android.os.RemoteException;

    void setDpcDownloaded(boolean z) throws android.os.RemoteException;

    void setDrawables(java.util.List<android.app.admin.DevicePolicyDrawableResource> list) throws android.os.RemoteException;

    void setEndUserSessionMessage(android.content.ComponentName componentName, java.lang.CharSequence charSequence) throws android.os.RemoteException;

    void setFactoryResetProtectionPolicy(android.content.ComponentName componentName, java.lang.String str, android.app.admin.FactoryResetProtectionPolicy factoryResetProtectionPolicy) throws android.os.RemoteException;

    void setForceEphemeralUsers(android.content.ComponentName componentName, boolean z) throws android.os.RemoteException;

    int setGlobalPrivateDns(android.content.ComponentName componentName, int i, java.lang.String str) throws android.os.RemoteException;

    android.content.ComponentName setGlobalProxy(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void setGlobalSetting(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void setKeepUninstalledPackages(android.content.ComponentName componentName, java.lang.String str, java.util.List<java.lang.String> list) throws android.os.RemoteException;

    boolean setKeyGrantForApp(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2, java.lang.String str3, boolean z) throws android.os.RemoteException;

    boolean setKeyGrantToWifiAuth(java.lang.String str, java.lang.String str2, boolean z) throws android.os.RemoteException;

    boolean setKeyPairCertificate(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2, byte[] bArr, byte[] bArr2, boolean z) throws android.os.RemoteException;

    boolean setKeyguardDisabled(android.content.ComponentName componentName, boolean z) throws android.os.RemoteException;

    void setKeyguardDisabledFeatures(android.content.ComponentName componentName, java.lang.String str, int i, boolean z) throws android.os.RemoteException;

    void setLocationEnabled(android.content.ComponentName componentName, boolean z) throws android.os.RemoteException;

    void setLockTaskFeatures(android.content.ComponentName componentName, java.lang.String str, int i) throws android.os.RemoteException;

    void setLockTaskPackages(android.content.ComponentName componentName, java.lang.String str, java.lang.String[] strArr) throws android.os.RemoteException;

    void setLogoutEnabled(android.content.ComponentName componentName, boolean z) throws android.os.RemoteException;

    void setLongSupportMessage(android.content.ComponentName componentName, java.lang.CharSequence charSequence) throws android.os.RemoteException;

    void setManagedProfileCallerIdAccessPolicy(android.app.admin.PackagePolicy packagePolicy) throws android.os.RemoteException;

    void setManagedProfileContactsAccessPolicy(android.app.admin.PackagePolicy packagePolicy) throws android.os.RemoteException;

    void setManagedProfileMaximumTimeOff(android.content.ComponentName componentName, long j) throws android.os.RemoteException;

    void setManagedSubscriptionsPolicy(android.app.admin.ManagedSubscriptionsPolicy managedSubscriptionsPolicy) throws android.os.RemoteException;

    void setMasterVolumeMuted(android.content.ComponentName componentName, boolean z) throws android.os.RemoteException;

    void setMaxPolicyStorageLimit(java.lang.String str, int i) throws android.os.RemoteException;

    void setMaximumFailedPasswordsForWipe(android.content.ComponentName componentName, java.lang.String str, int i, boolean z) throws android.os.RemoteException;

    void setMaximumTimeToLock(android.content.ComponentName componentName, java.lang.String str, long j, boolean z) throws android.os.RemoteException;

    java.util.List<java.lang.String> setMeteredDataDisabledPackages(android.content.ComponentName componentName, java.util.List<java.lang.String> list) throws android.os.RemoteException;

    void setMinimumRequiredWifiSecurityLevel(java.lang.String str, int i) throws android.os.RemoteException;

    void setMtePolicy(int i, java.lang.String str) throws android.os.RemoteException;

    void setNearbyAppStreamingPolicy(int i) throws android.os.RemoteException;

    void setNearbyNotificationStreamingPolicy(int i) throws android.os.RemoteException;

    void setNetworkLoggingEnabled(android.content.ComponentName componentName, java.lang.String str, boolean z) throws android.os.RemoteException;

    void setNextOperationSafety(int i, int i2) throws android.os.RemoteException;

    void setOrganizationColor(android.content.ComponentName componentName, int i) throws android.os.RemoteException;

    void setOrganizationColorForUser(int i, int i2) throws android.os.RemoteException;

    void setOrganizationIdForUser(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException;

    void setOrganizationName(android.content.ComponentName componentName, java.lang.String str, java.lang.CharSequence charSequence) throws android.os.RemoteException;

    void setOverrideApnsEnabled(android.content.ComponentName componentName, boolean z) throws android.os.RemoteException;

    java.lang.String[] setPackagesSuspended(android.content.ComponentName componentName, java.lang.String str, java.lang.String[] strArr, boolean z) throws android.os.RemoteException;

    void setPasswordExpirationTimeout(android.content.ComponentName componentName, java.lang.String str, long j, boolean z) throws android.os.RemoteException;

    void setPasswordHistoryLength(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException;

    void setPasswordMinimumLength(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException;

    void setPasswordMinimumLetters(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException;

    void setPasswordMinimumLowerCase(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException;

    void setPasswordMinimumNonLetter(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException;

    void setPasswordMinimumNumeric(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException;

    void setPasswordMinimumSymbols(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException;

    void setPasswordMinimumUpperCase(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException;

    void setPasswordQuality(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException;

    void setPermissionGrantState(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2, java.lang.String str3, int i, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException;

    void setPermissionPolicy(android.content.ComponentName componentName, java.lang.String str, int i) throws android.os.RemoteException;

    boolean setPermittedAccessibilityServices(android.content.ComponentName componentName, java.util.List<java.lang.String> list) throws android.os.RemoteException;

    boolean setPermittedCrossProfileNotificationListeners(android.content.ComponentName componentName, java.util.List<java.lang.String> list) throws android.os.RemoteException;

    boolean setPermittedInputMethods(android.content.ComponentName componentName, java.lang.String str, java.util.List<java.lang.String> list, boolean z) throws android.os.RemoteException;

    void setPersonalAppsSuspended(android.content.ComponentName componentName, boolean z) throws android.os.RemoteException;

    void setPreferentialNetworkServiceConfigs(java.util.List<android.app.admin.PreferentialNetworkServiceConfig> list) throws android.os.RemoteException;

    void setProfileEnabled(android.content.ComponentName componentName) throws android.os.RemoteException;

    void setProfileName(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException;

    boolean setProfileOwner(android.content.ComponentName componentName, int i) throws android.os.RemoteException;

    void setProfileOwnerOnOrganizationOwnedDevice(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException;

    void setRecommendedGlobalProxy(android.content.ComponentName componentName, android.net.ProxyInfo proxyInfo) throws android.os.RemoteException;

    void setRequiredPasswordComplexity(java.lang.String str, int i, boolean z) throws android.os.RemoteException;

    void setRequiredStrongAuthTimeout(android.content.ComponentName componentName, java.lang.String str, long j, boolean z) throws android.os.RemoteException;

    boolean setResetPasswordToken(android.content.ComponentName componentName, java.lang.String str, byte[] bArr) throws android.os.RemoteException;

    void setRestrictionsProvider(android.content.ComponentName componentName, android.content.ComponentName componentName2) throws android.os.RemoteException;

    void setScreenCaptureDisabled(android.content.ComponentName componentName, java.lang.String str, boolean z, boolean z2) throws android.os.RemoteException;

    void setSecondaryLockscreenEnabled(android.content.ComponentName componentName, boolean z) throws android.os.RemoteException;

    void setSecureSetting(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void setSecurityLoggingEnabled(android.content.ComponentName componentName, java.lang.String str, boolean z) throws android.os.RemoteException;

    void setShortSupportMessage(android.content.ComponentName componentName, java.lang.String str, java.lang.CharSequence charSequence) throws android.os.RemoteException;

    void setStartUserSessionMessage(android.content.ComponentName componentName, java.lang.CharSequence charSequence) throws android.os.RemoteException;

    boolean setStatusBarDisabled(android.content.ComponentName componentName, java.lang.String str, boolean z) throws android.os.RemoteException;

    int setStorageEncryption(android.content.ComponentName componentName, boolean z) throws android.os.RemoteException;

    void setStrings(java.util.List<android.app.admin.DevicePolicyStringResource> list) throws android.os.RemoteException;

    void setSystemSetting(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void setSystemUpdatePolicy(android.content.ComponentName componentName, java.lang.String str, android.app.admin.SystemUpdatePolicy systemUpdatePolicy) throws android.os.RemoteException;

    boolean setTime(android.content.ComponentName componentName, java.lang.String str, long j) throws android.os.RemoteException;

    boolean setTimeZone(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void setTrustAgentConfiguration(android.content.ComponentName componentName, java.lang.String str, android.content.ComponentName componentName2, android.os.PersistableBundle persistableBundle, boolean z) throws android.os.RemoteException;

    void setUninstallBlocked(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2, boolean z) throws android.os.RemoteException;

    void setUsbDataSignalingEnabled(java.lang.String str, boolean z) throws android.os.RemoteException;

    void setUserControlDisabledPackages(android.content.ComponentName componentName, java.lang.String str, java.util.List<java.lang.String> list) throws android.os.RemoteException;

    void setUserIcon(android.content.ComponentName componentName, android.graphics.Bitmap bitmap) throws android.os.RemoteException;

    void setUserProvisioningState(int i, int i2) throws android.os.RemoteException;

    void setUserRestriction(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2, boolean z, boolean z2) throws android.os.RemoteException;

    void setUserRestrictionGlobally(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void setWifiSsidPolicy(java.lang.String str, android.app.admin.WifiSsidPolicy wifiSsidPolicy) throws android.os.RemoteException;

    boolean shouldAllowBypassingDevicePolicyManagementRoleQualification() throws android.os.RemoteException;

    void startManagedQuickContact(java.lang.String str, long j, boolean z, long j2, android.content.Intent intent) throws android.os.RemoteException;

    int startUserInBackground(android.content.ComponentName componentName, android.os.UserHandle userHandle) throws android.os.RemoteException;

    boolean startViewCalendarEventInManagedProfile(java.lang.String str, long j, long j2, long j3, boolean z, int i) throws android.os.RemoteException;

    int stopUser(android.content.ComponentName componentName, android.os.UserHandle userHandle) throws android.os.RemoteException;

    boolean switchUser(android.content.ComponentName componentName, android.os.UserHandle userHandle) throws android.os.RemoteException;

    void transferOwnership(android.content.ComponentName componentName, android.content.ComponentName componentName2, android.os.PersistableBundle persistableBundle) throws android.os.RemoteException;

    boolean triggerDevicePolicyEngineMigration(boolean z) throws android.os.RemoteException;

    void uninstallCaCerts(android.content.ComponentName componentName, java.lang.String str, java.lang.String[] strArr) throws android.os.RemoteException;

    void uninstallPackageWithActiveAdmins(java.lang.String str) throws android.os.RemoteException;

    boolean updateOverrideApn(android.content.ComponentName componentName, int i, android.telephony.data.ApnSetting apnSetting) throws android.os.RemoteException;

    void wipeDataWithReason(java.lang.String str, int i, java.lang.String str2, boolean z, boolean z2) throws android.os.RemoteException;

    public static class Default implements android.app.admin.IDevicePolicyManager {
        @Override // android.app.admin.IDevicePolicyManager
        public void setPasswordQuality(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getPasswordQuality(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setPasswordMinimumLength(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getPasswordMinimumLength(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setPasswordMinimumUpperCase(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getPasswordMinimumUpperCase(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setPasswordMinimumLowerCase(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getPasswordMinimumLowerCase(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setPasswordMinimumLetters(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getPasswordMinimumLetters(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setPasswordMinimumNumeric(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getPasswordMinimumNumeric(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setPasswordMinimumSymbols(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getPasswordMinimumSymbols(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setPasswordMinimumNonLetter(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getPasswordMinimumNonLetter(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public android.app.admin.PasswordMetrics getPasswordMinimumMetrics(int i, boolean z) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setPasswordHistoryLength(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getPasswordHistoryLength(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setPasswordExpirationTimeout(android.content.ComponentName componentName, java.lang.String str, long j, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public long getPasswordExpirationTimeout(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public long getPasswordExpiration(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isActivePasswordSufficient(java.lang.String str, int i, boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isActivePasswordSufficientForDeviceRequirement() throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isPasswordSufficientAfterProfileUnification(int i, int i2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getPasswordComplexity(boolean z) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setRequiredPasswordComplexity(java.lang.String str, int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getRequiredPasswordComplexity(java.lang.String str, boolean z) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getAggregatedPasswordComplexityForUser(int i, boolean z) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isUsingUnifiedPassword(android.content.ComponentName componentName) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getCurrentFailedPasswordAttempts(java.lang.String str, int i, boolean z) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getProfileWithMinimumFailedPasswordsForWipe(int i, boolean z) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setMaximumFailedPasswordsForWipe(android.content.ComponentName componentName, java.lang.String str, int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getMaximumFailedPasswordsForWipe(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean resetPassword(java.lang.String str, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setMaximumTimeToLock(android.content.ComponentName componentName, java.lang.String str, long j, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public long getMaximumTimeToLock(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setRequiredStrongAuthTimeout(android.content.ComponentName componentName, java.lang.String str, long j, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public long getRequiredStrongAuthTimeout(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void lockNow(int i, java.lang.String str, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void wipeDataWithReason(java.lang.String str, int i, java.lang.String str2, boolean z, boolean z2) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setFactoryResetProtectionPolicy(android.content.ComponentName componentName, java.lang.String str, android.app.admin.FactoryResetProtectionPolicy factoryResetProtectionPolicy) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public android.app.admin.FactoryResetProtectionPolicy getFactoryResetProtectionPolicy(android.content.ComponentName componentName) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isFactoryResetProtectionPolicySupported() throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void sendLostModeLocationUpdate(com.android.internal.infra.AndroidFuture<java.lang.Boolean> androidFuture) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public android.content.ComponentName setGlobalProxy(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public android.content.ComponentName getGlobalProxyAdmin(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setRecommendedGlobalProxy(android.content.ComponentName componentName, android.net.ProxyInfo proxyInfo) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int setStorageEncryption(android.content.ComponentName componentName, boolean z) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean getStorageEncryption(android.content.ComponentName componentName, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getStorageEncryptionStatus(java.lang.String str, int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean requestBugreport(android.content.ComponentName componentName) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setCameraDisabled(android.content.ComponentName componentName, java.lang.String str, boolean z, boolean z2) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean getCameraDisabled(android.content.ComponentName componentName, java.lang.String str, int i, boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setScreenCaptureDisabled(android.content.ComponentName componentName, java.lang.String str, boolean z, boolean z2) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean getScreenCaptureDisabled(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setNearbyNotificationStreamingPolicy(int i) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getNearbyNotificationStreamingPolicy(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setNearbyAppStreamingPolicy(int i) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getNearbyAppStreamingPolicy(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setKeyguardDisabledFeatures(android.content.ComponentName componentName, java.lang.String str, int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getKeyguardDisabledFeatures(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setActiveAdmin(android.content.ComponentName componentName, boolean z, int i) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isAdminActive(android.content.ComponentName componentName, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public java.util.List<android.content.ComponentName> getActiveAdmins(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean packageHasActiveAdmins(java.lang.String str, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void getRemoveWarning(android.content.ComponentName componentName, android.os.RemoteCallback remoteCallback, int i) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void removeActiveAdmin(android.content.ComponentName componentName, int i) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void forceRemoveActiveAdmin(android.content.ComponentName componentName, int i) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean hasGrantedPolicy(android.content.ComponentName componentName, int i, int i2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void reportPasswordChanged(android.app.admin.PasswordMetrics passwordMetrics, int i) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void reportFailedPasswordAttempt(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void reportSuccessfulPasswordAttempt(int i) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void reportFailedBiometricAttempt(int i) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void reportSuccessfulBiometricAttempt(int i) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void reportKeyguardDismissed(int i) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void reportKeyguardSecured(int i) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean setDeviceOwner(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public android.content.ComponentName getDeviceOwnerComponent(boolean z) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public android.content.ComponentName getDeviceOwnerComponentOnUser(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean hasDeviceOwner() throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public java.lang.String getDeviceOwnerName() throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void clearDeviceOwner(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getDeviceOwnerUserId() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean setProfileOwner(android.content.ComponentName componentName, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public android.content.ComponentName getProfileOwnerAsUser(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public android.content.ComponentName getProfileOwnerOrDeviceOwnerSupervisionComponent(android.os.UserHandle userHandle) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isSupervisionComponent(android.content.ComponentName componentName) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public java.lang.String getProfileOwnerName(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setProfileEnabled(android.content.ComponentName componentName) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setProfileName(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void clearProfileOwner(android.content.ComponentName componentName) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean hasUserSetupCompleted() throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isOrganizationOwnedDeviceWithManagedProfile() throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean checkDeviceIdentifierAccess(java.lang.String str, int i, int i2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setDeviceOwnerLockScreenInfo(android.content.ComponentName componentName, java.lang.CharSequence charSequence) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public java.lang.CharSequence getDeviceOwnerLockScreenInfo() throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public java.lang.String[] setPackagesSuspended(android.content.ComponentName componentName, java.lang.String str, java.lang.String[] strArr, boolean z) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isPackageSuspended(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public java.util.List<java.lang.String> listPolicyExemptApps() throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean installCaCert(android.content.ComponentName componentName, java.lang.String str, byte[] bArr) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void uninstallCaCerts(android.content.ComponentName componentName, java.lang.String str, java.lang.String[] strArr) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void enforceCanManageCaCerts(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean approveCaCert(java.lang.String str, int i, boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isCaCertApproved(java.lang.String str, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean installKeyPair(android.content.ComponentName componentName, java.lang.String str, byte[] bArr, byte[] bArr2, byte[] bArr3, java.lang.String str2, boolean z, boolean z2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean removeKeyPair(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean hasKeyPair(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean generateKeyPair(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2, android.security.keystore.ParcelableKeyGenParameterSpec parcelableKeyGenParameterSpec, int i, android.security.keymaster.KeymasterCertificateChain keymasterCertificateChain) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean setKeyPairCertificate(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2, byte[] bArr, byte[] bArr2, boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void choosePrivateKeyAlias(int i, android.net.Uri uri, java.lang.String str, android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setDelegatedScopes(android.content.ComponentName componentName, java.lang.String str, java.util.List<java.lang.String> list) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public java.util.List<java.lang.String> getDelegatedScopes(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public java.util.List<java.lang.String> getDelegatePackages(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setCertInstallerPackage(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public java.lang.String getCertInstallerPackage(android.content.ComponentName componentName) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean setAlwaysOnVpnPackage(android.content.ComponentName componentName, java.lang.String str, boolean z, java.util.List<java.lang.String> list) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public java.lang.String getAlwaysOnVpnPackage(android.content.ComponentName componentName) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public java.lang.String getAlwaysOnVpnPackageForUser(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isAlwaysOnVpnLockdownEnabled(android.content.ComponentName componentName) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isAlwaysOnVpnLockdownEnabledForUser(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public java.util.List<java.lang.String> getAlwaysOnVpnLockdownAllowlist(android.content.ComponentName componentName) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void addPersistentPreferredActivity(android.content.ComponentName componentName, java.lang.String str, android.content.IntentFilter intentFilter, android.content.ComponentName componentName2) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void clearPackagePersistentPreferredActivities(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setDefaultSmsApplication(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setDefaultDialerApplication(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setApplicationRestrictions(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public android.os.Bundle getApplicationRestrictions(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean setApplicationRestrictionsManagingPackage(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public java.lang.String getApplicationRestrictionsManagingPackage(android.content.ComponentName componentName) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isCallerApplicationRestrictionsManagingPackage(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setRestrictionsProvider(android.content.ComponentName componentName, android.content.ComponentName componentName2) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public android.content.ComponentName getRestrictionsProvider(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setUserRestriction(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2, boolean z, boolean z2) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setUserRestrictionGlobally(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public android.os.Bundle getUserRestrictions(android.content.ComponentName componentName, java.lang.String str, boolean z) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public android.os.Bundle getUserRestrictionsGlobally(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void addCrossProfileIntentFilter(android.content.ComponentName componentName, java.lang.String str, android.content.IntentFilter intentFilter, int i) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void clearCrossProfileIntentFilters(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean setPermittedAccessibilityServices(android.content.ComponentName componentName, java.util.List<java.lang.String> list) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public java.util.List<java.lang.String> getPermittedAccessibilityServices(android.content.ComponentName componentName) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public java.util.List<java.lang.String> getPermittedAccessibilityServicesForUser(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isAccessibilityServicePermittedByAdmin(android.content.ComponentName componentName, java.lang.String str, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean setPermittedInputMethods(android.content.ComponentName componentName, java.lang.String str, java.util.List<java.lang.String> list, boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public java.util.List<java.lang.String> getPermittedInputMethods(android.content.ComponentName componentName, java.lang.String str, boolean z) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public java.util.List<java.lang.String> getPermittedInputMethodsAsUser(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isInputMethodPermittedByAdmin(android.content.ComponentName componentName, java.lang.String str, int i, boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean setPermittedCrossProfileNotificationListeners(android.content.ComponentName componentName, java.util.List<java.lang.String> list) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public java.util.List<java.lang.String> getPermittedCrossProfileNotificationListeners(android.content.ComponentName componentName) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isNotificationListenerServicePermitted(java.lang.String str, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public android.content.Intent createAdminSupportIntent(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public android.os.Bundle getEnforcingAdminAndUserDetails(int i, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public java.util.List<android.app.admin.EnforcingAdmin> getEnforcingAdminsForRestriction(int i, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean setApplicationHidden(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2, boolean z, boolean z2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isApplicationHidden(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2, boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public android.os.UserHandle createAndManageUser(android.content.ComponentName componentName, java.lang.String str, android.content.ComponentName componentName2, android.os.PersistableBundle persistableBundle, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean removeUser(android.content.ComponentName componentName, android.os.UserHandle userHandle) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean switchUser(android.content.ComponentName componentName, android.os.UserHandle userHandle) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int startUserInBackground(android.content.ComponentName componentName, android.os.UserHandle userHandle) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int stopUser(android.content.ComponentName componentName, android.os.UserHandle userHandle) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int logoutUser(android.content.ComponentName componentName) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int logoutUserInternal() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getLogoutUserId() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public java.util.List<android.os.UserHandle> getSecondaryUsers(android.content.ComponentName componentName) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void acknowledgeNewUserDisclaimer(int i) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isNewUserDisclaimerAcknowledged(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void enableSystemApp(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int enableSystemAppWithIntent(android.content.ComponentName componentName, java.lang.String str, android.content.Intent intent) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean installExistingPackage(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setAccountManagementDisabled(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2, boolean z, boolean z2) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public java.lang.String[] getAccountTypesWithManagementDisabled(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public java.lang.String[] getAccountTypesWithManagementDisabledAsUser(int i, java.lang.String str, boolean z) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setSecondaryLockscreenEnabled(android.content.ComponentName componentName, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isSecondaryLockscreenEnabled(android.os.UserHandle userHandle) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setPreferentialNetworkServiceConfigs(java.util.List<android.app.admin.PreferentialNetworkServiceConfig> list) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public java.util.List<android.app.admin.PreferentialNetworkServiceConfig> getPreferentialNetworkServiceConfigs() throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setLockTaskPackages(android.content.ComponentName componentName, java.lang.String str, java.lang.String[] strArr) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public java.lang.String[] getLockTaskPackages(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isLockTaskPermitted(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setLockTaskFeatures(android.content.ComponentName componentName, java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getLockTaskFeatures(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setGlobalSetting(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setSystemSetting(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setSecureSetting(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setConfiguredNetworksLockdownState(android.content.ComponentName componentName, java.lang.String str, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean hasLockdownAdminConfiguredNetworks(android.content.ComponentName componentName) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setLocationEnabled(android.content.ComponentName componentName, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean setTime(android.content.ComponentName componentName, java.lang.String str, long j) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean setTimeZone(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setMasterVolumeMuted(android.content.ComponentName componentName, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isMasterVolumeMuted(android.content.ComponentName componentName) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void notifyLockTaskModeChanged(boolean z, java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setUninstallBlocked(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isUninstallBlocked(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setCrossProfileCallerIdDisabled(android.content.ComponentName componentName, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean getCrossProfileCallerIdDisabled(android.content.ComponentName componentName) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean getCrossProfileCallerIdDisabledForUser(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setCrossProfileContactsSearchDisabled(android.content.ComponentName componentName, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean getCrossProfileContactsSearchDisabled(android.content.ComponentName componentName) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean getCrossProfileContactsSearchDisabledForUser(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void startManagedQuickContact(java.lang.String str, long j, boolean z, long j2, android.content.Intent intent) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setManagedProfileCallerIdAccessPolicy(android.app.admin.PackagePolicy packagePolicy) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public android.app.admin.PackagePolicy getManagedProfileCallerIdAccessPolicy() throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean hasManagedProfileCallerIdAccess(int i, java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setCredentialManagerPolicy(android.app.admin.PackagePolicy packagePolicy) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public android.app.admin.PackagePolicy getCredentialManagerPolicy(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setManagedProfileContactsAccessPolicy(android.app.admin.PackagePolicy packagePolicy) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public android.app.admin.PackagePolicy getManagedProfileContactsAccessPolicy() throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean hasManagedProfileContactsAccess(int i, java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setBluetoothContactSharingDisabled(android.content.ComponentName componentName, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean getBluetoothContactSharingDisabled(android.content.ComponentName componentName) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean getBluetoothContactSharingDisabledForUser(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setTrustAgentConfiguration(android.content.ComponentName componentName, java.lang.String str, android.content.ComponentName componentName2, android.os.PersistableBundle persistableBundle, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public java.util.List<android.os.PersistableBundle> getTrustAgentConfiguration(android.content.ComponentName componentName, android.content.ComponentName componentName2, int i, boolean z) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean addCrossProfileWidgetProvider(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean removeCrossProfileWidgetProvider(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public java.util.List<java.lang.String> getCrossProfileWidgetProviders(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setAutoTimeRequired(android.content.ComponentName componentName, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean getAutoTimeRequired() throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setAutoTimeEnabled(android.content.ComponentName componentName, java.lang.String str, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean getAutoTimeEnabled(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setAutoTimeZoneEnabled(android.content.ComponentName componentName, java.lang.String str, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean getAutoTimeZoneEnabled(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setForceEphemeralUsers(android.content.ComponentName componentName, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean getForceEphemeralUsers(android.content.ComponentName componentName) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isRemovingAdmin(android.content.ComponentName componentName, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setUserIcon(android.content.ComponentName componentName, android.graphics.Bitmap bitmap) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setSystemUpdatePolicy(android.content.ComponentName componentName, java.lang.String str, android.app.admin.SystemUpdatePolicy systemUpdatePolicy) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public android.app.admin.SystemUpdatePolicy getSystemUpdatePolicy() throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void clearSystemUpdatePolicyFreezePeriodRecord() throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean setKeyguardDisabled(android.content.ComponentName componentName, boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean setStatusBarDisabled(android.content.ComponentName componentName, java.lang.String str, boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isStatusBarDisabled(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean getDoNotAskCredentialsOnBoot() throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void notifyPendingSystemUpdate(android.app.admin.SystemUpdateInfo systemUpdateInfo) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public android.app.admin.SystemUpdateInfo getPendingSystemUpdate(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setPermissionPolicy(android.content.ComponentName componentName, java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getPermissionPolicy(android.content.ComponentName componentName) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setPermissionGrantState(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2, java.lang.String str3, int i, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getPermissionGrantState(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isProvisioningAllowed(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int checkProvisioningPrecondition(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setKeepUninstalledPackages(android.content.ComponentName componentName, java.lang.String str, java.util.List<java.lang.String> list) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public java.util.List<java.lang.String> getKeepUninstalledPackages(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isManagedProfile(android.content.ComponentName componentName) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public java.lang.String getWifiMacAddress(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void reboot(android.content.ComponentName componentName) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setShortSupportMessage(android.content.ComponentName componentName, java.lang.String str, java.lang.CharSequence charSequence) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public java.lang.CharSequence getShortSupportMessage(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setLongSupportMessage(android.content.ComponentName componentName, java.lang.CharSequence charSequence) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public java.lang.CharSequence getLongSupportMessage(android.content.ComponentName componentName) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public java.lang.CharSequence getShortSupportMessageForUser(android.content.ComponentName componentName, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public java.lang.CharSequence getLongSupportMessageForUser(android.content.ComponentName componentName, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setOrganizationColor(android.content.ComponentName componentName, int i) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setOrganizationColorForUser(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void clearOrganizationIdForUser(int i) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getOrganizationColor(android.content.ComponentName componentName) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getOrganizationColorForUser(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setOrganizationName(android.content.ComponentName componentName, java.lang.String str, java.lang.CharSequence charSequence) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public java.lang.CharSequence getOrganizationName(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public java.lang.CharSequence getDeviceOwnerOrganizationName() throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public java.lang.CharSequence getOrganizationNameForUser(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getUserProvisioningState(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setUserProvisioningState(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setAffiliationIds(android.content.ComponentName componentName, java.util.List<java.lang.String> list) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public java.util.List<java.lang.String> getAffiliationIds(android.content.ComponentName componentName) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isCallingUserAffiliated() throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isAffiliatedUser(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setSecurityLoggingEnabled(android.content.ComponentName componentName, java.lang.String str, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isSecurityLoggingEnabled(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public android.content.pm.ParceledListSlice retrieveSecurityLogs(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public android.content.pm.ParceledListSlice retrievePreRebootSecurityLogs(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public long forceNetworkLogs() throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public long forceSecurityLogs() throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setAuditLogEnabled(java.lang.String str, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isAuditLogEnabled(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setAuditLogEventsCallback(java.lang.String str, android.app.admin.IAuditLogEventsCallback iAuditLogEventsCallback) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isUninstallInQueue(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void uninstallPackageWithActiveAdmins(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isDeviceProvisioned() throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isDeviceProvisioningConfigApplied() throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setDeviceProvisioningConfigApplied() throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void forceUpdateUserSetupComplete(int i) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setBackupServiceEnabled(android.content.ComponentName componentName, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isBackupServiceEnabled(android.content.ComponentName componentName) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setNetworkLoggingEnabled(android.content.ComponentName componentName, java.lang.String str, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isNetworkLoggingEnabled(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public java.util.List<android.app.admin.NetworkEvent> retrieveNetworkLogs(android.content.ComponentName componentName, java.lang.String str, long j) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean bindDeviceAdminServiceAsUser(android.content.ComponentName componentName, android.app.IApplicationThread iApplicationThread, android.os.IBinder iBinder, android.content.Intent intent, android.app.IServiceConnection iServiceConnection, long j, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public java.util.List<android.os.UserHandle> getBindDeviceAdminTargetUsers(android.content.ComponentName componentName) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isEphemeralUser(android.content.ComponentName componentName) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public long getLastSecurityLogRetrievalTime() throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public long getLastBugReportRequestTime() throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public long getLastNetworkLogRetrievalTime() throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean setResetPasswordToken(android.content.ComponentName componentName, java.lang.String str, byte[] bArr) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean clearResetPasswordToken(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isResetPasswordTokenActive(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean resetPasswordWithToken(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2, byte[] bArr, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isCurrentInputMethodSetByOwner() throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public android.content.pm.StringParceledListSlice getOwnerInstalledCaCerts(android.os.UserHandle userHandle) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void clearApplicationUserData(android.content.ComponentName componentName, java.lang.String str, android.content.pm.IPackageDataObserver iPackageDataObserver) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setLogoutEnabled(android.content.ComponentName componentName, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isLogoutEnabled() throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public java.util.List<java.lang.String> getDisallowedSystemApps(android.content.ComponentName componentName, int i, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void transferOwnership(android.content.ComponentName componentName, android.content.ComponentName componentName2, android.os.PersistableBundle persistableBundle) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public android.os.PersistableBundle getTransferOwnershipBundle() throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setStartUserSessionMessage(android.content.ComponentName componentName, java.lang.CharSequence charSequence) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setEndUserSessionMessage(android.content.ComponentName componentName, java.lang.CharSequence charSequence) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public java.lang.CharSequence getStartUserSessionMessage(android.content.ComponentName componentName) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public java.lang.CharSequence getEndUserSessionMessage(android.content.ComponentName componentName) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public java.util.List<java.lang.String> setMeteredDataDisabledPackages(android.content.ComponentName componentName, java.util.List<java.lang.String> list) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public java.util.List<java.lang.String> getMeteredDataDisabledPackages(android.content.ComponentName componentName) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int addOverrideApn(android.content.ComponentName componentName, android.telephony.data.ApnSetting apnSetting) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean updateOverrideApn(android.content.ComponentName componentName, int i, android.telephony.data.ApnSetting apnSetting) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean removeOverrideApn(android.content.ComponentName componentName, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public java.util.List<android.telephony.data.ApnSetting> getOverrideApns(android.content.ComponentName componentName) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setOverrideApnsEnabled(android.content.ComponentName componentName, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isOverrideApnEnabled(android.content.ComponentName componentName) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isMeteredDataDisabledPackageForUser(android.content.ComponentName componentName, java.lang.String str, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int setGlobalPrivateDns(android.content.ComponentName componentName, int i, java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getGlobalPrivateDnsMode(android.content.ComponentName componentName) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public java.lang.String getGlobalPrivateDnsHost(android.content.ComponentName componentName) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setProfileOwnerOnOrganizationOwnedDevice(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void installUpdateFromFile(android.content.ComponentName componentName, java.lang.String str, android.os.ParcelFileDescriptor parcelFileDescriptor, android.app.admin.StartInstallingUpdateCallback startInstallingUpdateCallback) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setCrossProfileCalendarPackages(android.content.ComponentName componentName, java.util.List<java.lang.String> list) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public java.util.List<java.lang.String> getCrossProfileCalendarPackages(android.content.ComponentName componentName) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isPackageAllowedToAccessCalendarForUser(java.lang.String str, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public java.util.List<java.lang.String> getCrossProfileCalendarPackagesForUser(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setCrossProfilePackages(android.content.ComponentName componentName, java.util.List<java.lang.String> list) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public java.util.List<java.lang.String> getCrossProfilePackages(android.content.ComponentName componentName) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public java.util.List<java.lang.String> getAllCrossProfilePackages(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public java.util.List<java.lang.String> getDefaultCrossProfilePackages() throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isManagedKiosk() throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isUnattendedManagedKiosk() throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean startViewCalendarEventInManagedProfile(java.lang.String str, long j, long j2, long j3, boolean z, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean setKeyGrantForApp(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2, java.lang.String str3, boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public android.app.admin.ParcelableGranteeMap getKeyPairGrants(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean setKeyGrantToWifiAuth(java.lang.String str, java.lang.String str2, boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isKeyPairGrantedToWifiAuth(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setUserControlDisabledPackages(android.content.ComponentName componentName, java.lang.String str, java.util.List<java.lang.String> list) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public java.util.List<java.lang.String> getUserControlDisabledPackages(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setCommonCriteriaModeEnabled(android.content.ComponentName componentName, java.lang.String str, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isCommonCriteriaModeEnabled(android.content.ComponentName componentName) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getPersonalAppsSuspendedReasons(android.content.ComponentName componentName) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setPersonalAppsSuspended(android.content.ComponentName componentName, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public long getManagedProfileMaximumTimeOff(android.content.ComponentName componentName) throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setManagedProfileMaximumTimeOff(android.content.ComponentName componentName, long j) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void acknowledgeDeviceCompliant() throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isComplianceAcknowledgementRequired() throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean canProfileOwnerResetPasswordWhenLocked(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setNextOperationSafety(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isSafeOperation(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public java.lang.String getEnrollmentSpecificId(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setOrganizationIdForUser(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public android.os.UserHandle createAndProvisionManagedProfile(android.app.admin.ManagedProfileProvisioningParams managedProfileProvisioningParams, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void provisionFullyManagedDevice(android.app.admin.FullyManagedDeviceProvisioningParams fullyManagedDeviceProvisioningParams, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void finalizeWorkProfileProvisioning(android.os.UserHandle userHandle, android.accounts.Account account) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setDeviceOwnerType(android.content.ComponentName componentName, int i) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getDeviceOwnerType(android.content.ComponentName componentName) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void resetDefaultCrossProfileIntentFilters(int i) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean canAdminGrantSensorsPermissions() throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setUsbDataSignalingEnabled(java.lang.String str, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isUsbDataSignalingEnabled(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean canUsbDataSignalingBeDisabled() throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setMinimumRequiredWifiSecurityLevel(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getMinimumRequiredWifiSecurityLevel() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setWifiSsidPolicy(java.lang.String str, android.app.admin.WifiSsidPolicy wifiSsidPolicy) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public android.app.admin.WifiSsidPolicy getWifiSsidPolicy(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isTheftDetectionTriggered(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public java.util.List<android.os.UserHandle> listForegroundAffiliatedUsers() throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setDrawables(java.util.List<android.app.admin.DevicePolicyDrawableResource> list) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void resetDrawables(java.util.List<java.lang.String> list) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public android.app.admin.ParcelableResource getDrawable(java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isDpcDownloaded() throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setDpcDownloaded(boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setStrings(java.util.List<android.app.admin.DevicePolicyStringResource> list) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void resetStrings(java.util.List<java.lang.String> list) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public android.app.admin.ParcelableResource getString(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void resetShouldAllowBypassingDevicePolicyManagementRoleQualificationState() throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean shouldAllowBypassingDevicePolicyManagementRoleQualification() throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public java.util.List<android.os.UserHandle> getPolicyManagedProfiles(android.os.UserHandle userHandle) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setApplicationExemptions(java.lang.String str, java.lang.String str2, int[] iArr) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int[] getApplicationExemptions(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setMtePolicy(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getMtePolicy(java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setManagedSubscriptionsPolicy(android.app.admin.ManagedSubscriptionsPolicy managedSubscriptionsPolicy) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public android.app.admin.ManagedSubscriptionsPolicy getManagedSubscriptionsPolicy() throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public android.app.admin.DevicePolicyState getDevicePolicyState() throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean triggerDevicePolicyEngineMigration(boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean isDeviceFinanced(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public java.lang.String getFinancedDeviceKioskRoleHolder(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void calculateHasIncompatibleAccounts() throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setContentProtectionPolicy(android.content.ComponentName componentName, java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getContentProtectionPolicy(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int[] getSubscriptionIds(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public void setMaxPolicyStorageLimit(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IDevicePolicyManager
        public int getMaxPolicyStorageLimit(java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.admin.IDevicePolicyManager
        public boolean requireSecureKeyguard(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.admin.IDevicePolicyManager {
        public static final java.lang.String DESCRIPTOR = "android.app.admin.IDevicePolicyManager";
        static final int TRANSACTION_acknowledgeDeviceCompliant = 348;
        static final int TRANSACTION_acknowledgeNewUserDisclaimer = 165;
        static final int TRANSACTION_addCrossProfileIntentFilter = 138;
        static final int TRANSACTION_addCrossProfileWidgetProvider = 215;
        static final int TRANSACTION_addOverrideApn = 313;
        static final int TRANSACTION_addPersistentPreferredActivity = 123;
        static final int TRANSACTION_approveCaCert = 104;
        static final int TRANSACTION_bindDeviceAdminServiceAsUser = 289;
        static final int TRANSACTION_calculateHasIncompatibleAccounts = 392;
        static final int TRANSACTION_canAdminGrantSensorsPermissions = 361;
        static final int TRANSACTION_canProfileOwnerResetPasswordWhenLocked = 350;
        static final int TRANSACTION_canUsbDataSignalingBeDisabled = 364;
        static final int TRANSACTION_checkDeviceIdentifierAccess = 95;
        static final int TRANSACTION_checkProvisioningPrecondition = 242;
        static final int TRANSACTION_choosePrivateKeyAlias = 111;
        static final int TRANSACTION_clearApplicationUserData = 301;
        static final int TRANSACTION_clearCrossProfileIntentFilters = 139;
        static final int TRANSACTION_clearDeviceOwner = 83;
        static final int TRANSACTION_clearOrganizationIdForUser = 256;
        static final int TRANSACTION_clearPackagePersistentPreferredActivities = 124;
        static final int TRANSACTION_clearProfileOwner = 92;
        static final int TRANSACTION_clearResetPasswordToken = 296;
        static final int TRANSACTION_clearSystemUpdatePolicyFreezePeriodRecord = 230;
        static final int TRANSACTION_createAdminSupportIntent = 151;
        static final int TRANSACTION_createAndManageUser = 156;
        static final int TRANSACTION_createAndProvisionManagedProfile = 355;
        static final int TRANSACTION_enableSystemApp = 167;
        static final int TRANSACTION_enableSystemAppWithIntent = 168;
        static final int TRANSACTION_enforceCanManageCaCerts = 103;
        static final int TRANSACTION_finalizeWorkProfileProvisioning = 357;
        static final int TRANSACTION_forceNetworkLogs = 273;
        static final int TRANSACTION_forceRemoveActiveAdmin = 69;
        static final int TRANSACTION_forceSecurityLogs = 274;
        static final int TRANSACTION_forceUpdateUserSetupComplete = 283;
        static final int TRANSACTION_generateKeyPair = 109;
        static final int TRANSACTION_getAccountTypesWithManagementDisabled = 171;
        static final int TRANSACTION_getAccountTypesWithManagementDisabledAsUser = 172;
        static final int TRANSACTION_getActiveAdmins = 65;
        static final int TRANSACTION_getAffiliationIds = 266;
        static final int TRANSACTION_getAggregatedPasswordComplexityForUser = 29;
        static final int TRANSACTION_getAllCrossProfilePackages = 331;
        static final int TRANSACTION_getAlwaysOnVpnLockdownAllowlist = 122;
        static final int TRANSACTION_getAlwaysOnVpnPackage = 118;
        static final int TRANSACTION_getAlwaysOnVpnPackageForUser = 119;
        static final int TRANSACTION_getApplicationExemptions = 383;
        static final int TRANSACTION_getApplicationRestrictions = 128;
        static final int TRANSACTION_getApplicationRestrictionsManagingPackage = 130;
        static final int TRANSACTION_getAutoTimeEnabled = 221;
        static final int TRANSACTION_getAutoTimeRequired = 219;
        static final int TRANSACTION_getAutoTimeZoneEnabled = 223;
        static final int TRANSACTION_getBindDeviceAdminTargetUsers = 290;
        static final int TRANSACTION_getBluetoothContactSharingDisabled = 211;
        static final int TRANSACTION_getBluetoothContactSharingDisabledForUser = 212;
        static final int TRANSACTION_getCameraDisabled = 54;
        static final int TRANSACTION_getCertInstallerPackage = 116;
        static final int TRANSACTION_getContentProtectionPolicy = 394;
        static final int TRANSACTION_getCredentialManagerPolicy = 206;
        static final int TRANSACTION_getCrossProfileCalendarPackages = 326;
        static final int TRANSACTION_getCrossProfileCalendarPackagesForUser = 328;
        static final int TRANSACTION_getCrossProfileCallerIdDisabled = 196;
        static final int TRANSACTION_getCrossProfileCallerIdDisabledForUser = 197;
        static final int TRANSACTION_getCrossProfileContactsSearchDisabled = 199;
        static final int TRANSACTION_getCrossProfileContactsSearchDisabledForUser = 200;
        static final int TRANSACTION_getCrossProfilePackages = 330;
        static final int TRANSACTION_getCrossProfileWidgetProviders = 217;
        static final int TRANSACTION_getCurrentFailedPasswordAttempts = 31;
        static final int TRANSACTION_getDefaultCrossProfilePackages = 332;
        static final int TRANSACTION_getDelegatePackages = 114;
        static final int TRANSACTION_getDelegatedScopes = 113;
        static final int TRANSACTION_getDeviceOwnerComponent = 79;
        static final int TRANSACTION_getDeviceOwnerComponentOnUser = 80;
        static final int TRANSACTION_getDeviceOwnerLockScreenInfo = 97;
        static final int TRANSACTION_getDeviceOwnerName = 82;
        static final int TRANSACTION_getDeviceOwnerOrganizationName = 261;
        static final int TRANSACTION_getDeviceOwnerType = 359;
        static final int TRANSACTION_getDeviceOwnerUserId = 84;
        static final int TRANSACTION_getDevicePolicyState = 388;
        static final int TRANSACTION_getDisallowedSystemApps = 304;
        static final int TRANSACTION_getDoNotAskCredentialsOnBoot = 234;
        static final int TRANSACTION_getDrawable = 373;
        static final int TRANSACTION_getEndUserSessionMessage = 310;
        static final int TRANSACTION_getEnforcingAdminAndUserDetails = 152;
        static final int TRANSACTION_getEnforcingAdminsForRestriction = 153;
        static final int TRANSACTION_getEnrollmentSpecificId = 353;
        static final int TRANSACTION_getFactoryResetProtectionPolicy = 43;
        static final int TRANSACTION_getFinancedDeviceKioskRoleHolder = 391;
        static final int TRANSACTION_getForceEphemeralUsers = 225;
        static final int TRANSACTION_getGlobalPrivateDnsHost = 322;
        static final int TRANSACTION_getGlobalPrivateDnsMode = 321;
        static final int TRANSACTION_getGlobalProxyAdmin = 47;
        static final int TRANSACTION_getKeepUninstalledPackages = 244;
        static final int TRANSACTION_getKeyPairGrants = 337;
        static final int TRANSACTION_getKeyguardDisabledFeatures = 62;
        static final int TRANSACTION_getLastBugReportRequestTime = 293;
        static final int TRANSACTION_getLastNetworkLogRetrievalTime = 294;
        static final int TRANSACTION_getLastSecurityLogRetrievalTime = 292;
        static final int TRANSACTION_getLockTaskFeatures = 181;
        static final int TRANSACTION_getLockTaskPackages = 178;
        static final int TRANSACTION_getLogoutUserId = 163;
        static final int TRANSACTION_getLongSupportMessage = 251;
        static final int TRANSACTION_getLongSupportMessageForUser = 253;
        static final int TRANSACTION_getManagedProfileCallerIdAccessPolicy = 203;
        static final int TRANSACTION_getManagedProfileContactsAccessPolicy = 208;
        static final int TRANSACTION_getManagedProfileMaximumTimeOff = 346;
        static final int TRANSACTION_getManagedSubscriptionsPolicy = 387;
        static final int TRANSACTION_getMaxPolicyStorageLimit = 397;
        static final int TRANSACTION_getMaximumFailedPasswordsForWipe = 34;
        static final int TRANSACTION_getMaximumTimeToLock = 37;
        static final int TRANSACTION_getMeteredDataDisabledPackages = 312;
        static final int TRANSACTION_getMinimumRequiredWifiSecurityLevel = 366;
        static final int TRANSACTION_getMtePolicy = 385;
        static final int TRANSACTION_getNearbyAppStreamingPolicy = 60;
        static final int TRANSACTION_getNearbyNotificationStreamingPolicy = 58;
        static final int TRANSACTION_getOrganizationColor = 257;
        static final int TRANSACTION_getOrganizationColorForUser = 258;
        static final int TRANSACTION_getOrganizationName = 260;
        static final int TRANSACTION_getOrganizationNameForUser = 262;
        static final int TRANSACTION_getOverrideApns = 316;
        static final int TRANSACTION_getOwnerInstalledCaCerts = 300;
        static final int TRANSACTION_getPasswordComplexity = 26;
        static final int TRANSACTION_getPasswordExpiration = 22;
        static final int TRANSACTION_getPasswordExpirationTimeout = 21;
        static final int TRANSACTION_getPasswordHistoryLength = 19;
        static final int TRANSACTION_getPasswordMinimumLength = 4;
        static final int TRANSACTION_getPasswordMinimumLetters = 10;
        static final int TRANSACTION_getPasswordMinimumLowerCase = 8;
        static final int TRANSACTION_getPasswordMinimumMetrics = 17;
        static final int TRANSACTION_getPasswordMinimumNonLetter = 16;
        static final int TRANSACTION_getPasswordMinimumNumeric = 12;
        static final int TRANSACTION_getPasswordMinimumSymbols = 14;
        static final int TRANSACTION_getPasswordMinimumUpperCase = 6;
        static final int TRANSACTION_getPasswordQuality = 2;
        static final int TRANSACTION_getPendingSystemUpdate = 236;
        static final int TRANSACTION_getPermissionGrantState = 240;
        static final int TRANSACTION_getPermissionPolicy = 238;
        static final int TRANSACTION_getPermittedAccessibilityServices = 141;
        static final int TRANSACTION_getPermittedAccessibilityServicesForUser = 142;
        static final int TRANSACTION_getPermittedCrossProfileNotificationListeners = 149;
        static final int TRANSACTION_getPermittedInputMethods = 145;
        static final int TRANSACTION_getPermittedInputMethodsAsUser = 146;
        static final int TRANSACTION_getPersonalAppsSuspendedReasons = 344;
        static final int TRANSACTION_getPolicyManagedProfiles = 381;
        static final int TRANSACTION_getPreferentialNetworkServiceConfigs = 176;
        static final int TRANSACTION_getProfileOwnerAsUser = 86;
        static final int TRANSACTION_getProfileOwnerName = 89;
        static final int TRANSACTION_getProfileOwnerOrDeviceOwnerSupervisionComponent = 87;
        static final int TRANSACTION_getProfileWithMinimumFailedPasswordsForWipe = 32;
        static final int TRANSACTION_getRemoveWarning = 67;
        static final int TRANSACTION_getRequiredPasswordComplexity = 28;
        static final int TRANSACTION_getRequiredStrongAuthTimeout = 39;
        static final int TRANSACTION_getRestrictionsProvider = 133;
        static final int TRANSACTION_getScreenCaptureDisabled = 56;
        static final int TRANSACTION_getSecondaryUsers = 164;
        static final int TRANSACTION_getShortSupportMessage = 249;
        static final int TRANSACTION_getShortSupportMessageForUser = 252;
        static final int TRANSACTION_getStartUserSessionMessage = 309;
        static final int TRANSACTION_getStorageEncryption = 50;
        static final int TRANSACTION_getStorageEncryptionStatus = 51;
        static final int TRANSACTION_getString = 378;
        static final int TRANSACTION_getSubscriptionIds = 395;
        static final int TRANSACTION_getSystemUpdatePolicy = 229;
        static final int TRANSACTION_getTransferOwnershipBundle = 306;
        static final int TRANSACTION_getTrustAgentConfiguration = 214;
        static final int TRANSACTION_getUserControlDisabledPackages = 341;
        static final int TRANSACTION_getUserProvisioningState = 263;
        static final int TRANSACTION_getUserRestrictions = 136;
        static final int TRANSACTION_getUserRestrictionsGlobally = 137;
        static final int TRANSACTION_getWifiMacAddress = 246;
        static final int TRANSACTION_getWifiSsidPolicy = 368;
        static final int TRANSACTION_hasDeviceOwner = 81;
        static final int TRANSACTION_hasGrantedPolicy = 70;
        static final int TRANSACTION_hasKeyPair = 108;
        static final int TRANSACTION_hasLockdownAdminConfiguredNetworks = 186;
        static final int TRANSACTION_hasManagedProfileCallerIdAccess = 204;
        static final int TRANSACTION_hasManagedProfileContactsAccess = 209;
        static final int TRANSACTION_hasUserSetupCompleted = 93;
        static final int TRANSACTION_installCaCert = 101;
        static final int TRANSACTION_installExistingPackage = 169;
        static final int TRANSACTION_installKeyPair = 106;
        static final int TRANSACTION_installUpdateFromFile = 324;
        static final int TRANSACTION_isAccessibilityServicePermittedByAdmin = 143;
        static final int TRANSACTION_isActivePasswordSufficient = 23;
        static final int TRANSACTION_isActivePasswordSufficientForDeviceRequirement = 24;
        static final int TRANSACTION_isAdminActive = 64;
        static final int TRANSACTION_isAffiliatedUser = 268;
        static final int TRANSACTION_isAlwaysOnVpnLockdownEnabled = 120;
        static final int TRANSACTION_isAlwaysOnVpnLockdownEnabledForUser = 121;
        static final int TRANSACTION_isApplicationHidden = 155;
        static final int TRANSACTION_isAuditLogEnabled = 276;
        static final int TRANSACTION_isBackupServiceEnabled = 285;
        static final int TRANSACTION_isCaCertApproved = 105;
        static final int TRANSACTION_isCallerApplicationRestrictionsManagingPackage = 131;
        static final int TRANSACTION_isCallingUserAffiliated = 267;
        static final int TRANSACTION_isCommonCriteriaModeEnabled = 343;
        static final int TRANSACTION_isComplianceAcknowledgementRequired = 349;
        static final int TRANSACTION_isCurrentInputMethodSetByOwner = 299;
        static final int TRANSACTION_isDeviceFinanced = 390;
        static final int TRANSACTION_isDeviceProvisioned = 280;
        static final int TRANSACTION_isDeviceProvisioningConfigApplied = 281;
        static final int TRANSACTION_isDpcDownloaded = 374;
        static final int TRANSACTION_isEphemeralUser = 291;
        static final int TRANSACTION_isFactoryResetProtectionPolicySupported = 44;
        static final int TRANSACTION_isInputMethodPermittedByAdmin = 147;
        static final int TRANSACTION_isKeyPairGrantedToWifiAuth = 339;
        static final int TRANSACTION_isLockTaskPermitted = 179;
        static final int TRANSACTION_isLogoutEnabled = 303;
        static final int TRANSACTION_isManagedKiosk = 333;
        static final int TRANSACTION_isManagedProfile = 245;
        static final int TRANSACTION_isMasterVolumeMuted = 191;
        static final int TRANSACTION_isMeteredDataDisabledPackageForUser = 319;
        static final int TRANSACTION_isNetworkLoggingEnabled = 287;
        static final int TRANSACTION_isNewUserDisclaimerAcknowledged = 166;
        static final int TRANSACTION_isNotificationListenerServicePermitted = 150;
        static final int TRANSACTION_isOrganizationOwnedDeviceWithManagedProfile = 94;
        static final int TRANSACTION_isOverrideApnEnabled = 318;
        static final int TRANSACTION_isPackageAllowedToAccessCalendarForUser = 327;
        static final int TRANSACTION_isPackageSuspended = 99;
        static final int TRANSACTION_isPasswordSufficientAfterProfileUnification = 25;
        static final int TRANSACTION_isProvisioningAllowed = 241;
        static final int TRANSACTION_isRemovingAdmin = 226;
        static final int TRANSACTION_isResetPasswordTokenActive = 297;
        static final int TRANSACTION_isSafeOperation = 352;
        static final int TRANSACTION_isSecondaryLockscreenEnabled = 174;
        static final int TRANSACTION_isSecurityLoggingEnabled = 270;
        static final int TRANSACTION_isStatusBarDisabled = 233;
        static final int TRANSACTION_isSupervisionComponent = 88;
        static final int TRANSACTION_isTheftDetectionTriggered = 369;
        static final int TRANSACTION_isUnattendedManagedKiosk = 334;
        static final int TRANSACTION_isUninstallBlocked = 194;
        static final int TRANSACTION_isUninstallInQueue = 278;
        static final int TRANSACTION_isUsbDataSignalingEnabled = 363;
        static final int TRANSACTION_isUsingUnifiedPassword = 30;
        static final int TRANSACTION_listForegroundAffiliatedUsers = 370;
        static final int TRANSACTION_listPolicyExemptApps = 100;
        static final int TRANSACTION_lockNow = 40;
        static final int TRANSACTION_logoutUser = 161;
        static final int TRANSACTION_logoutUserInternal = 162;
        static final int TRANSACTION_notifyLockTaskModeChanged = 192;
        static final int TRANSACTION_notifyPendingSystemUpdate = 235;
        static final int TRANSACTION_packageHasActiveAdmins = 66;
        static final int TRANSACTION_provisionFullyManagedDevice = 356;
        static final int TRANSACTION_reboot = 247;
        static final int TRANSACTION_removeActiveAdmin = 68;
        static final int TRANSACTION_removeCrossProfileWidgetProvider = 216;
        static final int TRANSACTION_removeKeyPair = 107;
        static final int TRANSACTION_removeOverrideApn = 315;
        static final int TRANSACTION_removeUser = 157;
        static final int TRANSACTION_reportFailedBiometricAttempt = 74;
        static final int TRANSACTION_reportFailedPasswordAttempt = 72;
        static final int TRANSACTION_reportKeyguardDismissed = 76;
        static final int TRANSACTION_reportKeyguardSecured = 77;
        static final int TRANSACTION_reportPasswordChanged = 71;
        static final int TRANSACTION_reportSuccessfulBiometricAttempt = 75;
        static final int TRANSACTION_reportSuccessfulPasswordAttempt = 73;
        static final int TRANSACTION_requestBugreport = 52;
        static final int TRANSACTION_requireSecureKeyguard = 398;
        static final int TRANSACTION_resetDefaultCrossProfileIntentFilters = 360;
        static final int TRANSACTION_resetDrawables = 372;
        static final int TRANSACTION_resetPassword = 35;
        static final int TRANSACTION_resetPasswordWithToken = 298;
        static final int TRANSACTION_resetShouldAllowBypassingDevicePolicyManagementRoleQualificationState = 379;
        static final int TRANSACTION_resetStrings = 377;
        static final int TRANSACTION_retrieveNetworkLogs = 288;
        static final int TRANSACTION_retrievePreRebootSecurityLogs = 272;
        static final int TRANSACTION_retrieveSecurityLogs = 271;
        static final int TRANSACTION_sendLostModeLocationUpdate = 45;
        static final int TRANSACTION_setAccountManagementDisabled = 170;
        static final int TRANSACTION_setActiveAdmin = 63;
        static final int TRANSACTION_setAffiliationIds = 265;
        static final int TRANSACTION_setAlwaysOnVpnPackage = 117;
        static final int TRANSACTION_setApplicationExemptions = 382;
        static final int TRANSACTION_setApplicationHidden = 154;
        static final int TRANSACTION_setApplicationRestrictions = 127;
        static final int TRANSACTION_setApplicationRestrictionsManagingPackage = 129;
        static final int TRANSACTION_setAuditLogEnabled = 275;
        static final int TRANSACTION_setAuditLogEventsCallback = 277;
        static final int TRANSACTION_setAutoTimeEnabled = 220;
        static final int TRANSACTION_setAutoTimeRequired = 218;
        static final int TRANSACTION_setAutoTimeZoneEnabled = 222;
        static final int TRANSACTION_setBackupServiceEnabled = 284;
        static final int TRANSACTION_setBluetoothContactSharingDisabled = 210;
        static final int TRANSACTION_setCameraDisabled = 53;
        static final int TRANSACTION_setCertInstallerPackage = 115;
        static final int TRANSACTION_setCommonCriteriaModeEnabled = 342;
        static final int TRANSACTION_setConfiguredNetworksLockdownState = 185;
        static final int TRANSACTION_setContentProtectionPolicy = 393;
        static final int TRANSACTION_setCredentialManagerPolicy = 205;
        static final int TRANSACTION_setCrossProfileCalendarPackages = 325;
        static final int TRANSACTION_setCrossProfileCallerIdDisabled = 195;
        static final int TRANSACTION_setCrossProfileContactsSearchDisabled = 198;
        static final int TRANSACTION_setCrossProfilePackages = 329;
        static final int TRANSACTION_setDefaultDialerApplication = 126;
        static final int TRANSACTION_setDefaultSmsApplication = 125;
        static final int TRANSACTION_setDelegatedScopes = 112;
        static final int TRANSACTION_setDeviceOwner = 78;
        static final int TRANSACTION_setDeviceOwnerLockScreenInfo = 96;
        static final int TRANSACTION_setDeviceOwnerType = 358;
        static final int TRANSACTION_setDeviceProvisioningConfigApplied = 282;
        static final int TRANSACTION_setDpcDownloaded = 375;
        static final int TRANSACTION_setDrawables = 371;
        static final int TRANSACTION_setEndUserSessionMessage = 308;
        static final int TRANSACTION_setFactoryResetProtectionPolicy = 42;
        static final int TRANSACTION_setForceEphemeralUsers = 224;
        static final int TRANSACTION_setGlobalPrivateDns = 320;
        static final int TRANSACTION_setGlobalProxy = 46;
        static final int TRANSACTION_setGlobalSetting = 182;
        static final int TRANSACTION_setKeepUninstalledPackages = 243;
        static final int TRANSACTION_setKeyGrantForApp = 336;
        static final int TRANSACTION_setKeyGrantToWifiAuth = 338;
        static final int TRANSACTION_setKeyPairCertificate = 110;
        static final int TRANSACTION_setKeyguardDisabled = 231;
        static final int TRANSACTION_setKeyguardDisabledFeatures = 61;
        static final int TRANSACTION_setLocationEnabled = 187;
        static final int TRANSACTION_setLockTaskFeatures = 180;
        static final int TRANSACTION_setLockTaskPackages = 177;
        static final int TRANSACTION_setLogoutEnabled = 302;
        static final int TRANSACTION_setLongSupportMessage = 250;
        static final int TRANSACTION_setManagedProfileCallerIdAccessPolicy = 202;
        static final int TRANSACTION_setManagedProfileContactsAccessPolicy = 207;
        static final int TRANSACTION_setManagedProfileMaximumTimeOff = 347;
        static final int TRANSACTION_setManagedSubscriptionsPolicy = 386;
        static final int TRANSACTION_setMasterVolumeMuted = 190;
        static final int TRANSACTION_setMaxPolicyStorageLimit = 396;
        static final int TRANSACTION_setMaximumFailedPasswordsForWipe = 33;
        static final int TRANSACTION_setMaximumTimeToLock = 36;
        static final int TRANSACTION_setMeteredDataDisabledPackages = 311;
        static final int TRANSACTION_setMinimumRequiredWifiSecurityLevel = 365;
        static final int TRANSACTION_setMtePolicy = 384;
        static final int TRANSACTION_setNearbyAppStreamingPolicy = 59;
        static final int TRANSACTION_setNearbyNotificationStreamingPolicy = 57;
        static final int TRANSACTION_setNetworkLoggingEnabled = 286;
        static final int TRANSACTION_setNextOperationSafety = 351;
        static final int TRANSACTION_setOrganizationColor = 254;
        static final int TRANSACTION_setOrganizationColorForUser = 255;
        static final int TRANSACTION_setOrganizationIdForUser = 354;
        static final int TRANSACTION_setOrganizationName = 259;
        static final int TRANSACTION_setOverrideApnsEnabled = 317;
        static final int TRANSACTION_setPackagesSuspended = 98;
        static final int TRANSACTION_setPasswordExpirationTimeout = 20;
        static final int TRANSACTION_setPasswordHistoryLength = 18;
        static final int TRANSACTION_setPasswordMinimumLength = 3;
        static final int TRANSACTION_setPasswordMinimumLetters = 9;
        static final int TRANSACTION_setPasswordMinimumLowerCase = 7;
        static final int TRANSACTION_setPasswordMinimumNonLetter = 15;
        static final int TRANSACTION_setPasswordMinimumNumeric = 11;
        static final int TRANSACTION_setPasswordMinimumSymbols = 13;
        static final int TRANSACTION_setPasswordMinimumUpperCase = 5;
        static final int TRANSACTION_setPasswordQuality = 1;
        static final int TRANSACTION_setPermissionGrantState = 239;
        static final int TRANSACTION_setPermissionPolicy = 237;
        static final int TRANSACTION_setPermittedAccessibilityServices = 140;
        static final int TRANSACTION_setPermittedCrossProfileNotificationListeners = 148;
        static final int TRANSACTION_setPermittedInputMethods = 144;
        static final int TRANSACTION_setPersonalAppsSuspended = 345;
        static final int TRANSACTION_setPreferentialNetworkServiceConfigs = 175;
        static final int TRANSACTION_setProfileEnabled = 90;
        static final int TRANSACTION_setProfileName = 91;
        static final int TRANSACTION_setProfileOwner = 85;
        static final int TRANSACTION_setProfileOwnerOnOrganizationOwnedDevice = 323;
        static final int TRANSACTION_setRecommendedGlobalProxy = 48;
        static final int TRANSACTION_setRequiredPasswordComplexity = 27;
        static final int TRANSACTION_setRequiredStrongAuthTimeout = 38;
        static final int TRANSACTION_setResetPasswordToken = 295;
        static final int TRANSACTION_setRestrictionsProvider = 132;
        static final int TRANSACTION_setScreenCaptureDisabled = 55;
        static final int TRANSACTION_setSecondaryLockscreenEnabled = 173;
        static final int TRANSACTION_setSecureSetting = 184;
        static final int TRANSACTION_setSecurityLoggingEnabled = 269;
        static final int TRANSACTION_setShortSupportMessage = 248;
        static final int TRANSACTION_setStartUserSessionMessage = 307;
        static final int TRANSACTION_setStatusBarDisabled = 232;
        static final int TRANSACTION_setStorageEncryption = 49;
        static final int TRANSACTION_setStrings = 376;
        static final int TRANSACTION_setSystemSetting = 183;
        static final int TRANSACTION_setSystemUpdatePolicy = 228;
        static final int TRANSACTION_setTime = 188;
        static final int TRANSACTION_setTimeZone = 189;
        static final int TRANSACTION_setTrustAgentConfiguration = 213;
        static final int TRANSACTION_setUninstallBlocked = 193;
        static final int TRANSACTION_setUsbDataSignalingEnabled = 362;
        static final int TRANSACTION_setUserControlDisabledPackages = 340;
        static final int TRANSACTION_setUserIcon = 227;
        static final int TRANSACTION_setUserProvisioningState = 264;
        static final int TRANSACTION_setUserRestriction = 134;
        static final int TRANSACTION_setUserRestrictionGlobally = 135;
        static final int TRANSACTION_setWifiSsidPolicy = 367;
        static final int TRANSACTION_shouldAllowBypassingDevicePolicyManagementRoleQualification = 380;
        static final int TRANSACTION_startManagedQuickContact = 201;
        static final int TRANSACTION_startUserInBackground = 159;
        static final int TRANSACTION_startViewCalendarEventInManagedProfile = 335;
        static final int TRANSACTION_stopUser = 160;
        static final int TRANSACTION_switchUser = 158;
        static final int TRANSACTION_transferOwnership = 305;
        static final int TRANSACTION_triggerDevicePolicyEngineMigration = 389;
        static final int TRANSACTION_uninstallCaCerts = 102;
        static final int TRANSACTION_uninstallPackageWithActiveAdmins = 279;
        static final int TRANSACTION_updateOverrideApn = 314;
        static final int TRANSACTION_wipeDataWithReason = 41;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.app.admin.IDevicePolicyManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.admin.IDevicePolicyManager)) {
                return (android.app.admin.IDevicePolicyManager) queryLocalInterface;
            }
            return new android.app.admin.IDevicePolicyManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setPasswordQuality";
                case 2:
                    return "getPasswordQuality";
                case 3:
                    return "setPasswordMinimumLength";
                case 4:
                    return "getPasswordMinimumLength";
                case 5:
                    return "setPasswordMinimumUpperCase";
                case 6:
                    return "getPasswordMinimumUpperCase";
                case 7:
                    return "setPasswordMinimumLowerCase";
                case 8:
                    return "getPasswordMinimumLowerCase";
                case 9:
                    return "setPasswordMinimumLetters";
                case 10:
                    return "getPasswordMinimumLetters";
                case 11:
                    return "setPasswordMinimumNumeric";
                case 12:
                    return "getPasswordMinimumNumeric";
                case 13:
                    return "setPasswordMinimumSymbols";
                case 14:
                    return "getPasswordMinimumSymbols";
                case 15:
                    return "setPasswordMinimumNonLetter";
                case 16:
                    return "getPasswordMinimumNonLetter";
                case 17:
                    return "getPasswordMinimumMetrics";
                case 18:
                    return "setPasswordHistoryLength";
                case 19:
                    return "getPasswordHistoryLength";
                case 20:
                    return "setPasswordExpirationTimeout";
                case 21:
                    return "getPasswordExpirationTimeout";
                case 22:
                    return "getPasswordExpiration";
                case 23:
                    return "isActivePasswordSufficient";
                case 24:
                    return "isActivePasswordSufficientForDeviceRequirement";
                case 25:
                    return "isPasswordSufficientAfterProfileUnification";
                case 26:
                    return "getPasswordComplexity";
                case 27:
                    return "setRequiredPasswordComplexity";
                case 28:
                    return "getRequiredPasswordComplexity";
                case 29:
                    return "getAggregatedPasswordComplexityForUser";
                case 30:
                    return "isUsingUnifiedPassword";
                case 31:
                    return "getCurrentFailedPasswordAttempts";
                case 32:
                    return "getProfileWithMinimumFailedPasswordsForWipe";
                case 33:
                    return "setMaximumFailedPasswordsForWipe";
                case 34:
                    return "getMaximumFailedPasswordsForWipe";
                case 35:
                    return "resetPassword";
                case 36:
                    return "setMaximumTimeToLock";
                case 37:
                    return "getMaximumTimeToLock";
                case 38:
                    return "setRequiredStrongAuthTimeout";
                case 39:
                    return "getRequiredStrongAuthTimeout";
                case 40:
                    return "lockNow";
                case 41:
                    return "wipeDataWithReason";
                case 42:
                    return "setFactoryResetProtectionPolicy";
                case 43:
                    return "getFactoryResetProtectionPolicy";
                case 44:
                    return "isFactoryResetProtectionPolicySupported";
                case 45:
                    return "sendLostModeLocationUpdate";
                case 46:
                    return "setGlobalProxy";
                case 47:
                    return "getGlobalProxyAdmin";
                case 48:
                    return "setRecommendedGlobalProxy";
                case 49:
                    return "setStorageEncryption";
                case 50:
                    return "getStorageEncryption";
                case 51:
                    return "getStorageEncryptionStatus";
                case 52:
                    return "requestBugreport";
                case 53:
                    return "setCameraDisabled";
                case 54:
                    return "getCameraDisabled";
                case 55:
                    return "setScreenCaptureDisabled";
                case 56:
                    return "getScreenCaptureDisabled";
                case 57:
                    return "setNearbyNotificationStreamingPolicy";
                case 58:
                    return "getNearbyNotificationStreamingPolicy";
                case 59:
                    return "setNearbyAppStreamingPolicy";
                case 60:
                    return "getNearbyAppStreamingPolicy";
                case 61:
                    return "setKeyguardDisabledFeatures";
                case 62:
                    return "getKeyguardDisabledFeatures";
                case 63:
                    return "setActiveAdmin";
                case 64:
                    return "isAdminActive";
                case 65:
                    return "getActiveAdmins";
                case 66:
                    return "packageHasActiveAdmins";
                case 67:
                    return "getRemoveWarning";
                case 68:
                    return "removeActiveAdmin";
                case 69:
                    return "forceRemoveActiveAdmin";
                case 70:
                    return "hasGrantedPolicy";
                case 71:
                    return "reportPasswordChanged";
                case 72:
                    return "reportFailedPasswordAttempt";
                case 73:
                    return "reportSuccessfulPasswordAttempt";
                case 74:
                    return "reportFailedBiometricAttempt";
                case 75:
                    return "reportSuccessfulBiometricAttempt";
                case 76:
                    return "reportKeyguardDismissed";
                case 77:
                    return "reportKeyguardSecured";
                case 78:
                    return "setDeviceOwner";
                case 79:
                    return "getDeviceOwnerComponent";
                case 80:
                    return "getDeviceOwnerComponentOnUser";
                case 81:
                    return "hasDeviceOwner";
                case 82:
                    return "getDeviceOwnerName";
                case 83:
                    return "clearDeviceOwner";
                case 84:
                    return "getDeviceOwnerUserId";
                case 85:
                    return "setProfileOwner";
                case 86:
                    return "getProfileOwnerAsUser";
                case 87:
                    return "getProfileOwnerOrDeviceOwnerSupervisionComponent";
                case 88:
                    return "isSupervisionComponent";
                case 89:
                    return "getProfileOwnerName";
                case 90:
                    return "setProfileEnabled";
                case 91:
                    return "setProfileName";
                case 92:
                    return "clearProfileOwner";
                case 93:
                    return "hasUserSetupCompleted";
                case 94:
                    return "isOrganizationOwnedDeviceWithManagedProfile";
                case 95:
                    return "checkDeviceIdentifierAccess";
                case 96:
                    return "setDeviceOwnerLockScreenInfo";
                case 97:
                    return "getDeviceOwnerLockScreenInfo";
                case 98:
                    return "setPackagesSuspended";
                case 99:
                    return "isPackageSuspended";
                case 100:
                    return "listPolicyExemptApps";
                case 101:
                    return "installCaCert";
                case 102:
                    return "uninstallCaCerts";
                case 103:
                    return "enforceCanManageCaCerts";
                case 104:
                    return "approveCaCert";
                case 105:
                    return "isCaCertApproved";
                case 106:
                    return "installKeyPair";
                case 107:
                    return "removeKeyPair";
                case 108:
                    return "hasKeyPair";
                case 109:
                    return "generateKeyPair";
                case 110:
                    return "setKeyPairCertificate";
                case 111:
                    return "choosePrivateKeyAlias";
                case 112:
                    return "setDelegatedScopes";
                case 113:
                    return "getDelegatedScopes";
                case 114:
                    return "getDelegatePackages";
                case 115:
                    return "setCertInstallerPackage";
                case 116:
                    return "getCertInstallerPackage";
                case 117:
                    return "setAlwaysOnVpnPackage";
                case 118:
                    return "getAlwaysOnVpnPackage";
                case 119:
                    return "getAlwaysOnVpnPackageForUser";
                case 120:
                    return "isAlwaysOnVpnLockdownEnabled";
                case 121:
                    return "isAlwaysOnVpnLockdownEnabledForUser";
                case 122:
                    return "getAlwaysOnVpnLockdownAllowlist";
                case 123:
                    return "addPersistentPreferredActivity";
                case 124:
                    return "clearPackagePersistentPreferredActivities";
                case 125:
                    return "setDefaultSmsApplication";
                case 126:
                    return "setDefaultDialerApplication";
                case 127:
                    return "setApplicationRestrictions";
                case 128:
                    return "getApplicationRestrictions";
                case 129:
                    return "setApplicationRestrictionsManagingPackage";
                case 130:
                    return "getApplicationRestrictionsManagingPackage";
                case 131:
                    return "isCallerApplicationRestrictionsManagingPackage";
                case 132:
                    return "setRestrictionsProvider";
                case 133:
                    return "getRestrictionsProvider";
                case 134:
                    return "setUserRestriction";
                case 135:
                    return "setUserRestrictionGlobally";
                case 136:
                    return "getUserRestrictions";
                case 137:
                    return "getUserRestrictionsGlobally";
                case 138:
                    return "addCrossProfileIntentFilter";
                case 139:
                    return "clearCrossProfileIntentFilters";
                case 140:
                    return "setPermittedAccessibilityServices";
                case 141:
                    return "getPermittedAccessibilityServices";
                case 142:
                    return "getPermittedAccessibilityServicesForUser";
                case 143:
                    return "isAccessibilityServicePermittedByAdmin";
                case 144:
                    return "setPermittedInputMethods";
                case 145:
                    return "getPermittedInputMethods";
                case 146:
                    return "getPermittedInputMethodsAsUser";
                case 147:
                    return "isInputMethodPermittedByAdmin";
                case 148:
                    return "setPermittedCrossProfileNotificationListeners";
                case 149:
                    return "getPermittedCrossProfileNotificationListeners";
                case 150:
                    return "isNotificationListenerServicePermitted";
                case 151:
                    return "createAdminSupportIntent";
                case 152:
                    return "getEnforcingAdminAndUserDetails";
                case 153:
                    return "getEnforcingAdminsForRestriction";
                case 154:
                    return "setApplicationHidden";
                case 155:
                    return "isApplicationHidden";
                case 156:
                    return "createAndManageUser";
                case 157:
                    return "removeUser";
                case 158:
                    return "switchUser";
                case 159:
                    return "startUserInBackground";
                case 160:
                    return "stopUser";
                case 161:
                    return "logoutUser";
                case 162:
                    return "logoutUserInternal";
                case 163:
                    return "getLogoutUserId";
                case 164:
                    return "getSecondaryUsers";
                case 165:
                    return "acknowledgeNewUserDisclaimer";
                case 166:
                    return "isNewUserDisclaimerAcknowledged";
                case 167:
                    return "enableSystemApp";
                case 168:
                    return "enableSystemAppWithIntent";
                case 169:
                    return "installExistingPackage";
                case 170:
                    return "setAccountManagementDisabled";
                case 171:
                    return "getAccountTypesWithManagementDisabled";
                case 172:
                    return "getAccountTypesWithManagementDisabledAsUser";
                case 173:
                    return "setSecondaryLockscreenEnabled";
                case 174:
                    return "isSecondaryLockscreenEnabled";
                case 175:
                    return "setPreferentialNetworkServiceConfigs";
                case 176:
                    return "getPreferentialNetworkServiceConfigs";
                case 177:
                    return "setLockTaskPackages";
                case 178:
                    return "getLockTaskPackages";
                case 179:
                    return "isLockTaskPermitted";
                case 180:
                    return "setLockTaskFeatures";
                case 181:
                    return "getLockTaskFeatures";
                case 182:
                    return "setGlobalSetting";
                case 183:
                    return "setSystemSetting";
                case 184:
                    return "setSecureSetting";
                case 185:
                    return "setConfiguredNetworksLockdownState";
                case 186:
                    return "hasLockdownAdminConfiguredNetworks";
                case 187:
                    return "setLocationEnabled";
                case 188:
                    return "setTime";
                case 189:
                    return "setTimeZone";
                case 190:
                    return "setMasterVolumeMuted";
                case 191:
                    return "isMasterVolumeMuted";
                case 192:
                    return "notifyLockTaskModeChanged";
                case 193:
                    return "setUninstallBlocked";
                case 194:
                    return "isUninstallBlocked";
                case 195:
                    return "setCrossProfileCallerIdDisabled";
                case 196:
                    return "getCrossProfileCallerIdDisabled";
                case 197:
                    return "getCrossProfileCallerIdDisabledForUser";
                case 198:
                    return "setCrossProfileContactsSearchDisabled";
                case 199:
                    return "getCrossProfileContactsSearchDisabled";
                case 200:
                    return "getCrossProfileContactsSearchDisabledForUser";
                case 201:
                    return "startManagedQuickContact";
                case 202:
                    return "setManagedProfileCallerIdAccessPolicy";
                case 203:
                    return "getManagedProfileCallerIdAccessPolicy";
                case 204:
                    return "hasManagedProfileCallerIdAccess";
                case 205:
                    return "setCredentialManagerPolicy";
                case 206:
                    return "getCredentialManagerPolicy";
                case 207:
                    return "setManagedProfileContactsAccessPolicy";
                case 208:
                    return "getManagedProfileContactsAccessPolicy";
                case 209:
                    return "hasManagedProfileContactsAccess";
                case 210:
                    return "setBluetoothContactSharingDisabled";
                case 211:
                    return "getBluetoothContactSharingDisabled";
                case 212:
                    return "getBluetoothContactSharingDisabledForUser";
                case 213:
                    return "setTrustAgentConfiguration";
                case 214:
                    return "getTrustAgentConfiguration";
                case 215:
                    return "addCrossProfileWidgetProvider";
                case 216:
                    return "removeCrossProfileWidgetProvider";
                case 217:
                    return "getCrossProfileWidgetProviders";
                case 218:
                    return "setAutoTimeRequired";
                case 219:
                    return "getAutoTimeRequired";
                case 220:
                    return "setAutoTimeEnabled";
                case 221:
                    return "getAutoTimeEnabled";
                case 222:
                    return "setAutoTimeZoneEnabled";
                case 223:
                    return "getAutoTimeZoneEnabled";
                case 224:
                    return "setForceEphemeralUsers";
                case 225:
                    return "getForceEphemeralUsers";
                case 226:
                    return "isRemovingAdmin";
                case 227:
                    return "setUserIcon";
                case 228:
                    return "setSystemUpdatePolicy";
                case 229:
                    return "getSystemUpdatePolicy";
                case 230:
                    return "clearSystemUpdatePolicyFreezePeriodRecord";
                case 231:
                    return "setKeyguardDisabled";
                case 232:
                    return "setStatusBarDisabled";
                case 233:
                    return "isStatusBarDisabled";
                case 234:
                    return "getDoNotAskCredentialsOnBoot";
                case 235:
                    return "notifyPendingSystemUpdate";
                case 236:
                    return "getPendingSystemUpdate";
                case 237:
                    return "setPermissionPolicy";
                case 238:
                    return "getPermissionPolicy";
                case 239:
                    return "setPermissionGrantState";
                case 240:
                    return "getPermissionGrantState";
                case 241:
                    return "isProvisioningAllowed";
                case 242:
                    return "checkProvisioningPrecondition";
                case 243:
                    return "setKeepUninstalledPackages";
                case 244:
                    return "getKeepUninstalledPackages";
                case 245:
                    return "isManagedProfile";
                case 246:
                    return "getWifiMacAddress";
                case 247:
                    return "reboot";
                case 248:
                    return "setShortSupportMessage";
                case 249:
                    return "getShortSupportMessage";
                case 250:
                    return "setLongSupportMessage";
                case 251:
                    return "getLongSupportMessage";
                case 252:
                    return "getShortSupportMessageForUser";
                case 253:
                    return "getLongSupportMessageForUser";
                case 254:
                    return "setOrganizationColor";
                case 255:
                    return "setOrganizationColorForUser";
                case 256:
                    return "clearOrganizationIdForUser";
                case 257:
                    return "getOrganizationColor";
                case 258:
                    return "getOrganizationColorForUser";
                case 259:
                    return "setOrganizationName";
                case 260:
                    return "getOrganizationName";
                case 261:
                    return "getDeviceOwnerOrganizationName";
                case 262:
                    return "getOrganizationNameForUser";
                case 263:
                    return "getUserProvisioningState";
                case 264:
                    return "setUserProvisioningState";
                case 265:
                    return "setAffiliationIds";
                case 266:
                    return "getAffiliationIds";
                case 267:
                    return "isCallingUserAffiliated";
                case 268:
                    return "isAffiliatedUser";
                case 269:
                    return "setSecurityLoggingEnabled";
                case 270:
                    return "isSecurityLoggingEnabled";
                case 271:
                    return "retrieveSecurityLogs";
                case 272:
                    return "retrievePreRebootSecurityLogs";
                case 273:
                    return "forceNetworkLogs";
                case 274:
                    return "forceSecurityLogs";
                case 275:
                    return "setAuditLogEnabled";
                case 276:
                    return "isAuditLogEnabled";
                case 277:
                    return "setAuditLogEventsCallback";
                case 278:
                    return "isUninstallInQueue";
                case 279:
                    return "uninstallPackageWithActiveAdmins";
                case 280:
                    return "isDeviceProvisioned";
                case 281:
                    return "isDeviceProvisioningConfigApplied";
                case 282:
                    return "setDeviceProvisioningConfigApplied";
                case 283:
                    return "forceUpdateUserSetupComplete";
                case 284:
                    return "setBackupServiceEnabled";
                case 285:
                    return "isBackupServiceEnabled";
                case 286:
                    return "setNetworkLoggingEnabled";
                case 287:
                    return "isNetworkLoggingEnabled";
                case 288:
                    return "retrieveNetworkLogs";
                case 289:
                    return "bindDeviceAdminServiceAsUser";
                case 290:
                    return "getBindDeviceAdminTargetUsers";
                case 291:
                    return "isEphemeralUser";
                case 292:
                    return "getLastSecurityLogRetrievalTime";
                case 293:
                    return "getLastBugReportRequestTime";
                case 294:
                    return "getLastNetworkLogRetrievalTime";
                case 295:
                    return "setResetPasswordToken";
                case 296:
                    return "clearResetPasswordToken";
                case 297:
                    return "isResetPasswordTokenActive";
                case 298:
                    return "resetPasswordWithToken";
                case 299:
                    return "isCurrentInputMethodSetByOwner";
                case 300:
                    return "getOwnerInstalledCaCerts";
                case 301:
                    return "clearApplicationUserData";
                case 302:
                    return "setLogoutEnabled";
                case 303:
                    return "isLogoutEnabled";
                case 304:
                    return "getDisallowedSystemApps";
                case 305:
                    return "transferOwnership";
                case 306:
                    return "getTransferOwnershipBundle";
                case 307:
                    return "setStartUserSessionMessage";
                case 308:
                    return "setEndUserSessionMessage";
                case 309:
                    return "getStartUserSessionMessage";
                case 310:
                    return "getEndUserSessionMessage";
                case 311:
                    return "setMeteredDataDisabledPackages";
                case 312:
                    return "getMeteredDataDisabledPackages";
                case 313:
                    return "addOverrideApn";
                case 314:
                    return "updateOverrideApn";
                case 315:
                    return "removeOverrideApn";
                case 316:
                    return "getOverrideApns";
                case 317:
                    return "setOverrideApnsEnabled";
                case 318:
                    return "isOverrideApnEnabled";
                case 319:
                    return "isMeteredDataDisabledPackageForUser";
                case 320:
                    return "setGlobalPrivateDns";
                case 321:
                    return "getGlobalPrivateDnsMode";
                case 322:
                    return "getGlobalPrivateDnsHost";
                case 323:
                    return "setProfileOwnerOnOrganizationOwnedDevice";
                case 324:
                    return "installUpdateFromFile";
                case 325:
                    return "setCrossProfileCalendarPackages";
                case 326:
                    return "getCrossProfileCalendarPackages";
                case 327:
                    return "isPackageAllowedToAccessCalendarForUser";
                case 328:
                    return "getCrossProfileCalendarPackagesForUser";
                case 329:
                    return "setCrossProfilePackages";
                case 330:
                    return "getCrossProfilePackages";
                case 331:
                    return "getAllCrossProfilePackages";
                case 332:
                    return "getDefaultCrossProfilePackages";
                case 333:
                    return "isManagedKiosk";
                case 334:
                    return "isUnattendedManagedKiosk";
                case 335:
                    return "startViewCalendarEventInManagedProfile";
                case 336:
                    return "setKeyGrantForApp";
                case 337:
                    return "getKeyPairGrants";
                case 338:
                    return "setKeyGrantToWifiAuth";
                case 339:
                    return "isKeyPairGrantedToWifiAuth";
                case 340:
                    return "setUserControlDisabledPackages";
                case 341:
                    return "getUserControlDisabledPackages";
                case 342:
                    return "setCommonCriteriaModeEnabled";
                case 343:
                    return "isCommonCriteriaModeEnabled";
                case 344:
                    return "getPersonalAppsSuspendedReasons";
                case 345:
                    return "setPersonalAppsSuspended";
                case 346:
                    return "getManagedProfileMaximumTimeOff";
                case 347:
                    return "setManagedProfileMaximumTimeOff";
                case 348:
                    return "acknowledgeDeviceCompliant";
                case 349:
                    return "isComplianceAcknowledgementRequired";
                case 350:
                    return "canProfileOwnerResetPasswordWhenLocked";
                case 351:
                    return "setNextOperationSafety";
                case 352:
                    return "isSafeOperation";
                case 353:
                    return "getEnrollmentSpecificId";
                case 354:
                    return "setOrganizationIdForUser";
                case 355:
                    return "createAndProvisionManagedProfile";
                case 356:
                    return "provisionFullyManagedDevice";
                case 357:
                    return "finalizeWorkProfileProvisioning";
                case 358:
                    return "setDeviceOwnerType";
                case 359:
                    return "getDeviceOwnerType";
                case 360:
                    return "resetDefaultCrossProfileIntentFilters";
                case 361:
                    return "canAdminGrantSensorsPermissions";
                case 362:
                    return "setUsbDataSignalingEnabled";
                case 363:
                    return "isUsbDataSignalingEnabled";
                case 364:
                    return "canUsbDataSignalingBeDisabled";
                case 365:
                    return "setMinimumRequiredWifiSecurityLevel";
                case 366:
                    return "getMinimumRequiredWifiSecurityLevel";
                case 367:
                    return "setWifiSsidPolicy";
                case 368:
                    return "getWifiSsidPolicy";
                case 369:
                    return "isTheftDetectionTriggered";
                case 370:
                    return "listForegroundAffiliatedUsers";
                case 371:
                    return "setDrawables";
                case 372:
                    return "resetDrawables";
                case 373:
                    return "getDrawable";
                case 374:
                    return "isDpcDownloaded";
                case 375:
                    return "setDpcDownloaded";
                case 376:
                    return "setStrings";
                case 377:
                    return "resetStrings";
                case 378:
                    return "getString";
                case 379:
                    return "resetShouldAllowBypassingDevicePolicyManagementRoleQualificationState";
                case 380:
                    return "shouldAllowBypassingDevicePolicyManagementRoleQualification";
                case 381:
                    return "getPolicyManagedProfiles";
                case 382:
                    return "setApplicationExemptions";
                case 383:
                    return "getApplicationExemptions";
                case 384:
                    return "setMtePolicy";
                case 385:
                    return "getMtePolicy";
                case 386:
                    return "setManagedSubscriptionsPolicy";
                case 387:
                    return "getManagedSubscriptionsPolicy";
                case 388:
                    return "getDevicePolicyState";
                case 389:
                    return "triggerDevicePolicyEngineMigration";
                case 390:
                    return "isDeviceFinanced";
                case 391:
                    return "getFinancedDeviceKioskRoleHolder";
                case 392:
                    return "calculateHasIncompatibleAccounts";
                case 393:
                    return "setContentProtectionPolicy";
                case 394:
                    return "getContentProtectionPolicy";
                case 395:
                    return "getSubscriptionIds";
                case 396:
                    return "setMaxPolicyStorageLimit";
                case 397:
                    return "getMaxPolicyStorageLimit";
                case 398:
                    return "requireSecureKeyguard";
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
                    android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    int readInt = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setPasswordQuality(componentName, readInt, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    android.content.ComponentName componentName2 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    int readInt2 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int passwordQuality = getPasswordQuality(componentName2, readInt2, readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeInt(passwordQuality);
                    return true;
                case 3:
                    return onTransact$setPasswordMinimumLength$(parcel, parcel2);
                case 4:
                    return onTransact$getPasswordMinimumLength$(parcel, parcel2);
                case 5:
                    return onTransact$setPasswordMinimumUpperCase$(parcel, parcel2);
                case 6:
                    return onTransact$getPasswordMinimumUpperCase$(parcel, parcel2);
                case 7:
                    return onTransact$setPasswordMinimumLowerCase$(parcel, parcel2);
                case 8:
                    return onTransact$getPasswordMinimumLowerCase$(parcel, parcel2);
                case 9:
                    return onTransact$setPasswordMinimumLetters$(parcel, parcel2);
                case 10:
                    return onTransact$getPasswordMinimumLetters$(parcel, parcel2);
                case 11:
                    return onTransact$setPasswordMinimumNumeric$(parcel, parcel2);
                case 12:
                    return onTransact$getPasswordMinimumNumeric$(parcel, parcel2);
                case 13:
                    return onTransact$setPasswordMinimumSymbols$(parcel, parcel2);
                case 14:
                    return onTransact$getPasswordMinimumSymbols$(parcel, parcel2);
                case 15:
                    return onTransact$setPasswordMinimumNonLetter$(parcel, parcel2);
                case 16:
                    return onTransact$getPasswordMinimumNonLetter$(parcel, parcel2);
                case 17:
                    int readInt3 = parcel.readInt();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    android.app.admin.PasswordMetrics passwordMinimumMetrics = getPasswordMinimumMetrics(readInt3, readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(passwordMinimumMetrics, 1);
                    return true;
                case 18:
                    return onTransact$setPasswordHistoryLength$(parcel, parcel2);
                case 19:
                    return onTransact$getPasswordHistoryLength$(parcel, parcel2);
                case 20:
                    return onTransact$setPasswordExpirationTimeout$(parcel, parcel2);
                case 21:
                    return onTransact$getPasswordExpirationTimeout$(parcel, parcel2);
                case 22:
                    return onTransact$getPasswordExpiration$(parcel, parcel2);
                case 23:
                    return onTransact$isActivePasswordSufficient$(parcel, parcel2);
                case 24:
                    boolean isActivePasswordSufficientForDeviceRequirement = isActivePasswordSufficientForDeviceRequirement();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isActivePasswordSufficientForDeviceRequirement);
                    return true;
                case 25:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isPasswordSufficientAfterProfileUnification = isPasswordSufficientAfterProfileUnification(readInt4, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPasswordSufficientAfterProfileUnification);
                    return true;
                case 26:
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int passwordComplexity = getPasswordComplexity(readBoolean4);
                    parcel2.writeNoException();
                    parcel2.writeInt(passwordComplexity);
                    return true;
                case 27:
                    return onTransact$setRequiredPasswordComplexity$(parcel, parcel2);
                case 28:
                    java.lang.String readString = parcel.readString();
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int requiredPasswordComplexity = getRequiredPasswordComplexity(readString, readBoolean5);
                    parcel2.writeNoException();
                    parcel2.writeInt(requiredPasswordComplexity);
                    return true;
                case 29:
                    int readInt6 = parcel.readInt();
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int aggregatedPasswordComplexityForUser = getAggregatedPasswordComplexityForUser(readInt6, readBoolean6);
                    parcel2.writeNoException();
                    parcel2.writeInt(aggregatedPasswordComplexityForUser);
                    return true;
                case 30:
                    android.content.ComponentName componentName3 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isUsingUnifiedPassword = isUsingUnifiedPassword(componentName3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUsingUnifiedPassword);
                    return true;
                case 31:
                    return onTransact$getCurrentFailedPasswordAttempts$(parcel, parcel2);
                case 32:
                    int readInt7 = parcel.readInt();
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int profileWithMinimumFailedPasswordsForWipe = getProfileWithMinimumFailedPasswordsForWipe(readInt7, readBoolean7);
                    parcel2.writeNoException();
                    parcel2.writeInt(profileWithMinimumFailedPasswordsForWipe);
                    return true;
                case 33:
                    return onTransact$setMaximumFailedPasswordsForWipe$(parcel, parcel2);
                case 34:
                    return onTransact$getMaximumFailedPasswordsForWipe$(parcel, parcel2);
                case 35:
                    java.lang.String readString2 = parcel.readString();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean resetPassword = resetPassword(readString2, readInt8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(resetPassword);
                    return true;
                case 36:
                    return onTransact$setMaximumTimeToLock$(parcel, parcel2);
                case 37:
                    return onTransact$getMaximumTimeToLock$(parcel, parcel2);
                case 38:
                    return onTransact$setRequiredStrongAuthTimeout$(parcel, parcel2);
                case 39:
                    return onTransact$getRequiredStrongAuthTimeout$(parcel, parcel2);
                case 40:
                    return onTransact$lockNow$(parcel, parcel2);
                case 41:
                    return onTransact$wipeDataWithReason$(parcel, parcel2);
                case 42:
                    return onTransact$setFactoryResetProtectionPolicy$(parcel, parcel2);
                case 43:
                    android.content.ComponentName componentName4 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.app.admin.FactoryResetProtectionPolicy factoryResetProtectionPolicy = getFactoryResetProtectionPolicy(componentName4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(factoryResetProtectionPolicy, 1);
                    return true;
                case 44:
                    boolean isFactoryResetProtectionPolicySupported = isFactoryResetProtectionPolicySupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isFactoryResetProtectionPolicySupported);
                    return true;
                case 45:
                    com.android.internal.infra.AndroidFuture<java.lang.Boolean> androidFuture = (com.android.internal.infra.AndroidFuture) parcel.readTypedObject(com.android.internal.infra.AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendLostModeLocationUpdate(androidFuture);
                    parcel2.writeNoException();
                    return true;
                case 46:
                    return onTransact$setGlobalProxy$(parcel, parcel2);
                case 47:
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.ComponentName globalProxyAdmin = getGlobalProxyAdmin(readInt9);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(globalProxyAdmin, 1);
                    return true;
                case 48:
                    android.content.ComponentName componentName5 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    android.net.ProxyInfo proxyInfo = (android.net.ProxyInfo) parcel.readTypedObject(android.net.ProxyInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setRecommendedGlobalProxy(componentName5, proxyInfo);
                    parcel2.writeNoException();
                    return true;
                case 49:
                    android.content.ComponentName componentName6 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int storageEncryption = setStorageEncryption(componentName6, readBoolean8);
                    parcel2.writeNoException();
                    parcel2.writeInt(storageEncryption);
                    return true;
                case 50:
                    android.content.ComponentName componentName7 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean storageEncryption2 = getStorageEncryption(componentName7, readInt10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(storageEncryption2);
                    return true;
                case 51:
                    java.lang.String readString3 = parcel.readString();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int storageEncryptionStatus = getStorageEncryptionStatus(readString3, readInt11);
                    parcel2.writeNoException();
                    parcel2.writeInt(storageEncryptionStatus);
                    return true;
                case 52:
                    android.content.ComponentName componentName8 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean requestBugreport = requestBugreport(componentName8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requestBugreport);
                    return true;
                case 53:
                    return onTransact$setCameraDisabled$(parcel, parcel2);
                case 54:
                    return onTransact$getCameraDisabled$(parcel, parcel2);
                case 55:
                    return onTransact$setScreenCaptureDisabled$(parcel, parcel2);
                case 56:
                    return onTransact$getScreenCaptureDisabled$(parcel, parcel2);
                case 57:
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setNearbyNotificationStreamingPolicy(readInt12);
                    parcel2.writeNoException();
                    return true;
                case 58:
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int nearbyNotificationStreamingPolicy = getNearbyNotificationStreamingPolicy(readInt13);
                    parcel2.writeNoException();
                    parcel2.writeInt(nearbyNotificationStreamingPolicy);
                    return true;
                case 59:
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setNearbyAppStreamingPolicy(readInt14);
                    parcel2.writeNoException();
                    return true;
                case 60:
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int nearbyAppStreamingPolicy = getNearbyAppStreamingPolicy(readInt15);
                    parcel2.writeNoException();
                    parcel2.writeInt(nearbyAppStreamingPolicy);
                    return true;
                case 61:
                    return onTransact$setKeyguardDisabledFeatures$(parcel, parcel2);
                case 62:
                    return onTransact$getKeyguardDisabledFeatures$(parcel, parcel2);
                case 63:
                    return onTransact$setActiveAdmin$(parcel, parcel2);
                case 64:
                    android.content.ComponentName componentName9 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isAdminActive = isAdminActive(componentName9, readInt16);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAdminActive);
                    return true;
                case 65:
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.content.ComponentName> activeAdmins = getActiveAdmins(readInt17);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(activeAdmins, 1);
                    return true;
                case 66:
                    java.lang.String readString4 = parcel.readString();
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean packageHasActiveAdmins = packageHasActiveAdmins(readString4, readInt18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(packageHasActiveAdmins);
                    return true;
                case 67:
                    return onTransact$getRemoveWarning$(parcel, parcel2);
                case 68:
                    android.content.ComponentName componentName10 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeActiveAdmin(componentName10, readInt19);
                    parcel2.writeNoException();
                    return true;
                case 69:
                    android.content.ComponentName componentName11 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    forceRemoveActiveAdmin(componentName11, readInt20);
                    parcel2.writeNoException();
                    return true;
                case 70:
                    return onTransact$hasGrantedPolicy$(parcel, parcel2);
                case 71:
                    android.app.admin.PasswordMetrics passwordMetrics = (android.app.admin.PasswordMetrics) parcel.readTypedObject(android.app.admin.PasswordMetrics.CREATOR);
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportPasswordChanged(passwordMetrics, readInt21);
                    parcel2.writeNoException();
                    return true;
                case 72:
                    int readInt22 = parcel.readInt();
                    boolean readBoolean9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    reportFailedPasswordAttempt(readInt22, readBoolean9);
                    parcel2.writeNoException();
                    return true;
                case 73:
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportSuccessfulPasswordAttempt(readInt23);
                    parcel2.writeNoException();
                    return true;
                case 74:
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportFailedBiometricAttempt(readInt24);
                    parcel2.writeNoException();
                    return true;
                case 75:
                    int readInt25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportSuccessfulBiometricAttempt(readInt25);
                    parcel2.writeNoException();
                    return true;
                case 76:
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportKeyguardDismissed(readInt26);
                    parcel2.writeNoException();
                    return true;
                case 77:
                    int readInt27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportKeyguardSecured(readInt27);
                    parcel2.writeNoException();
                    return true;
                case 78:
                    return onTransact$setDeviceOwner$(parcel, parcel2);
                case 79:
                    boolean readBoolean10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    android.content.ComponentName deviceOwnerComponent = getDeviceOwnerComponent(readBoolean10);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(deviceOwnerComponent, 1);
                    return true;
                case 80:
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.ComponentName deviceOwnerComponentOnUser = getDeviceOwnerComponentOnUser(readInt28);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(deviceOwnerComponentOnUser, 1);
                    return true;
                case 81:
                    boolean hasDeviceOwner = hasDeviceOwner();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasDeviceOwner);
                    return true;
                case 82:
                    java.lang.String deviceOwnerName = getDeviceOwnerName();
                    parcel2.writeNoException();
                    parcel2.writeString(deviceOwnerName);
                    return true;
                case 83:
                    java.lang.String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    clearDeviceOwner(readString5);
                    parcel2.writeNoException();
                    return true;
                case 84:
                    int deviceOwnerUserId = getDeviceOwnerUserId();
                    parcel2.writeNoException();
                    parcel2.writeInt(deviceOwnerUserId);
                    return true;
                case 85:
                    android.content.ComponentName componentName12 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean profileOwner = setProfileOwner(componentName12, readInt29);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(profileOwner);
                    return true;
                case 86:
                    int readInt30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.ComponentName profileOwnerAsUser = getProfileOwnerAsUser(readInt30);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(profileOwnerAsUser, 1);
                    return true;
                case 87:
                    android.os.UserHandle userHandle = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.content.ComponentName profileOwnerOrDeviceOwnerSupervisionComponent = getProfileOwnerOrDeviceOwnerSupervisionComponent(userHandle);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(profileOwnerOrDeviceOwnerSupervisionComponent, 1);
                    return true;
                case 88:
                    android.content.ComponentName componentName13 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isSupervisionComponent = isSupervisionComponent(componentName13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSupervisionComponent);
                    return true;
                case 89:
                    int readInt31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String profileOwnerName = getProfileOwnerName(readInt31);
                    parcel2.writeNoException();
                    parcel2.writeString(profileOwnerName);
                    return true;
                case 90:
                    android.content.ComponentName componentName14 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    setProfileEnabled(componentName14);
                    parcel2.writeNoException();
                    return true;
                case 91:
                    android.content.ComponentName componentName15 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    java.lang.String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setProfileName(componentName15, readString6);
                    parcel2.writeNoException();
                    return true;
                case 92:
                    android.content.ComponentName componentName16 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    clearProfileOwner(componentName16);
                    parcel2.writeNoException();
                    return true;
                case 93:
                    boolean hasUserSetupCompleted = hasUserSetupCompleted();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasUserSetupCompleted);
                    return true;
                case 94:
                    boolean isOrganizationOwnedDeviceWithManagedProfile = isOrganizationOwnedDeviceWithManagedProfile();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isOrganizationOwnedDeviceWithManagedProfile);
                    return true;
                case 95:
                    return onTransact$checkDeviceIdentifierAccess$(parcel, parcel2);
                case 96:
                    android.content.ComponentName componentName17 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    java.lang.CharSequence charSequence = (java.lang.CharSequence) parcel.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                    parcel.enforceNoDataAvail();
                    setDeviceOwnerLockScreenInfo(componentName17, charSequence);
                    parcel2.writeNoException();
                    return true;
                case 97:
                    java.lang.CharSequence deviceOwnerLockScreenInfo = getDeviceOwnerLockScreenInfo();
                    parcel2.writeNoException();
                    if (deviceOwnerLockScreenInfo != null) {
                        parcel2.writeInt(1);
                        android.text.TextUtils.writeToParcel(deviceOwnerLockScreenInfo, parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 98:
                    return onTransact$setPackagesSuspended$(parcel, parcel2);
                case 99:
                    return onTransact$isPackageSuspended$(parcel, parcel2);
                case 100:
                    java.util.List<java.lang.String> listPolicyExemptApps = listPolicyExemptApps();
                    parcel2.writeNoException();
                    parcel2.writeStringList(listPolicyExemptApps);
                    return true;
                case 101:
                    return onTransact$installCaCert$(parcel, parcel2);
                case 102:
                    return onTransact$uninstallCaCerts$(parcel, parcel2);
                case 103:
                    android.content.ComponentName componentName18 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    java.lang.String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    enforceCanManageCaCerts(componentName18, readString7);
                    parcel2.writeNoException();
                    return true;
                case 104:
                    return onTransact$approveCaCert$(parcel, parcel2);
                case 105:
                    java.lang.String readString8 = parcel.readString();
                    int readInt32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isCaCertApproved = isCaCertApproved(readString8, readInt32);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCaCertApproved);
                    return true;
                case 106:
                    return onTransact$installKeyPair$(parcel, parcel2);
                case 107:
                    return onTransact$removeKeyPair$(parcel, parcel2);
                case 108:
                    java.lang.String readString9 = parcel.readString();
                    java.lang.String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean hasKeyPair = hasKeyPair(readString9, readString10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasKeyPair);
                    return true;
                case 109:
                    return onTransact$generateKeyPair$(parcel, parcel2);
                case 110:
                    return onTransact$setKeyPairCertificate$(parcel, parcel2);
                case 111:
                    return onTransact$choosePrivateKeyAlias$(parcel, parcel2);
                case 112:
                    return onTransact$setDelegatedScopes$(parcel, parcel2);
                case 113:
                    android.content.ComponentName componentName19 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    java.lang.String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.util.List<java.lang.String> delegatedScopes = getDelegatedScopes(componentName19, readString11);
                    parcel2.writeNoException();
                    parcel2.writeStringList(delegatedScopes);
                    return true;
                case 114:
                    android.content.ComponentName componentName20 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    java.lang.String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.util.List<java.lang.String> delegatePackages = getDelegatePackages(componentName20, readString12);
                    parcel2.writeNoException();
                    parcel2.writeStringList(delegatePackages);
                    return true;
                case 115:
                    android.content.ComponentName componentName21 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    java.lang.String readString13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setCertInstallerPackage(componentName21, readString13);
                    parcel2.writeNoException();
                    return true;
                case 116:
                    android.content.ComponentName componentName22 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    java.lang.String certInstallerPackage = getCertInstallerPackage(componentName22);
                    parcel2.writeNoException();
                    parcel2.writeString(certInstallerPackage);
                    return true;
                case 117:
                    return onTransact$setAlwaysOnVpnPackage$(parcel, parcel2);
                case 118:
                    android.content.ComponentName componentName23 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    java.lang.String alwaysOnVpnPackage = getAlwaysOnVpnPackage(componentName23);
                    parcel2.writeNoException();
                    parcel2.writeString(alwaysOnVpnPackage);
                    return true;
                case 119:
                    int readInt33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String alwaysOnVpnPackageForUser = getAlwaysOnVpnPackageForUser(readInt33);
                    parcel2.writeNoException();
                    parcel2.writeString(alwaysOnVpnPackageForUser);
                    return true;
                case 120:
                    android.content.ComponentName componentName24 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isAlwaysOnVpnLockdownEnabled = isAlwaysOnVpnLockdownEnabled(componentName24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAlwaysOnVpnLockdownEnabled);
                    return true;
                case 121:
                    int readInt34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isAlwaysOnVpnLockdownEnabledForUser = isAlwaysOnVpnLockdownEnabledForUser(readInt34);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAlwaysOnVpnLockdownEnabledForUser);
                    return true;
                case 122:
                    android.content.ComponentName componentName25 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    java.util.List<java.lang.String> alwaysOnVpnLockdownAllowlist = getAlwaysOnVpnLockdownAllowlist(componentName25);
                    parcel2.writeNoException();
                    parcel2.writeStringList(alwaysOnVpnLockdownAllowlist);
                    return true;
                case 123:
                    return onTransact$addPersistentPreferredActivity$(parcel, parcel2);
                case 124:
                    return onTransact$clearPackagePersistentPreferredActivities$(parcel, parcel2);
                case 125:
                    return onTransact$setDefaultSmsApplication$(parcel, parcel2);
                case 126:
                    java.lang.String readString14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setDefaultDialerApplication(readString14);
                    parcel2.writeNoException();
                    return true;
                case 127:
                    return onTransact$setApplicationRestrictions$(parcel, parcel2);
                case 128:
                    return onTransact$getApplicationRestrictions$(parcel, parcel2);
                case 129:
                    android.content.ComponentName componentName26 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    java.lang.String readString15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean applicationRestrictionsManagingPackage = setApplicationRestrictionsManagingPackage(componentName26, readString15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(applicationRestrictionsManagingPackage);
                    return true;
                case 130:
                    android.content.ComponentName componentName27 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    java.lang.String applicationRestrictionsManagingPackage2 = getApplicationRestrictionsManagingPackage(componentName27);
                    parcel2.writeNoException();
                    parcel2.writeString(applicationRestrictionsManagingPackage2);
                    return true;
                case 131:
                    java.lang.String readString16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isCallerApplicationRestrictionsManagingPackage = isCallerApplicationRestrictionsManagingPackage(readString16);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCallerApplicationRestrictionsManagingPackage);
                    return true;
                case 132:
                    android.content.ComponentName componentName28 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    android.content.ComponentName componentName29 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    setRestrictionsProvider(componentName28, componentName29);
                    parcel2.writeNoException();
                    return true;
                case 133:
                    int readInt35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.ComponentName restrictionsProvider = getRestrictionsProvider(readInt35);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(restrictionsProvider, 1);
                    return true;
                case 134:
                    return onTransact$setUserRestriction$(parcel, parcel2);
                case 135:
                    java.lang.String readString17 = parcel.readString();
                    java.lang.String readString18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setUserRestrictionGlobally(readString17, readString18);
                    parcel2.writeNoException();
                    return true;
                case 136:
                    return onTransact$getUserRestrictions$(parcel, parcel2);
                case 137:
                    java.lang.String readString19 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.os.Bundle userRestrictionsGlobally = getUserRestrictionsGlobally(readString19);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(userRestrictionsGlobally, 1);
                    return true;
                case 138:
                    return onTransact$addCrossProfileIntentFilter$(parcel, parcel2);
                case 139:
                    android.content.ComponentName componentName30 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    java.lang.String readString20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    clearCrossProfileIntentFilters(componentName30, readString20);
                    parcel2.writeNoException();
                    return true;
                case 140:
                    android.content.ComponentName componentName31 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    java.util.ArrayList<java.lang.String> createStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean permittedAccessibilityServices = setPermittedAccessibilityServices(componentName31, createStringArrayList);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(permittedAccessibilityServices);
                    return true;
                case 141:
                    android.content.ComponentName componentName32 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    java.util.List<java.lang.String> permittedAccessibilityServices2 = getPermittedAccessibilityServices(componentName32);
                    parcel2.writeNoException();
                    parcel2.writeStringList(permittedAccessibilityServices2);
                    return true;
                case 142:
                    int readInt36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.List<java.lang.String> permittedAccessibilityServicesForUser = getPermittedAccessibilityServicesForUser(readInt36);
                    parcel2.writeNoException();
                    parcel2.writeStringList(permittedAccessibilityServicesForUser);
                    return true;
                case 143:
                    return onTransact$isAccessibilityServicePermittedByAdmin$(parcel, parcel2);
                case 144:
                    return onTransact$setPermittedInputMethods$(parcel, parcel2);
                case 145:
                    return onTransact$getPermittedInputMethods$(parcel, parcel2);
                case 146:
                    int readInt37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.List<java.lang.String> permittedInputMethodsAsUser = getPermittedInputMethodsAsUser(readInt37);
                    parcel2.writeNoException();
                    parcel2.writeStringList(permittedInputMethodsAsUser);
                    return true;
                case 147:
                    return onTransact$isInputMethodPermittedByAdmin$(parcel, parcel2);
                case 148:
                    android.content.ComponentName componentName33 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    java.util.ArrayList<java.lang.String> createStringArrayList2 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean permittedCrossProfileNotificationListeners = setPermittedCrossProfileNotificationListeners(componentName33, createStringArrayList2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(permittedCrossProfileNotificationListeners);
                    return true;
                case 149:
                    android.content.ComponentName componentName34 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    java.util.List<java.lang.String> permittedCrossProfileNotificationListeners2 = getPermittedCrossProfileNotificationListeners(componentName34);
                    parcel2.writeNoException();
                    parcel2.writeStringList(permittedCrossProfileNotificationListeners2);
                    return true;
                case 150:
                    java.lang.String readString21 = parcel.readString();
                    int readInt38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isNotificationListenerServicePermitted = isNotificationListenerServicePermitted(readString21, readInt38);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isNotificationListenerServicePermitted);
                    return true;
                case 151:
                    java.lang.String readString22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.content.Intent createAdminSupportIntent = createAdminSupportIntent(readString22);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(createAdminSupportIntent, 1);
                    return true;
                case 152:
                    int readInt39 = parcel.readInt();
                    java.lang.String readString23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.os.Bundle enforcingAdminAndUserDetails = getEnforcingAdminAndUserDetails(readInt39, readString23);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(enforcingAdminAndUserDetails, 1);
                    return true;
                case 153:
                    int readInt40 = parcel.readInt();
                    java.lang.String readString24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.app.admin.EnforcingAdmin> enforcingAdminsForRestriction = getEnforcingAdminsForRestriction(readInt40, readString24);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(enforcingAdminsForRestriction, 1);
                    return true;
                case 154:
                    return onTransact$setApplicationHidden$(parcel, parcel2);
                case 155:
                    return onTransact$isApplicationHidden$(parcel, parcel2);
                case 156:
                    return onTransact$createAndManageUser$(parcel, parcel2);
                case 157:
                    android.content.ComponentName componentName35 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    android.os.UserHandle userHandle2 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean removeUser = removeUser(componentName35, userHandle2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeUser);
                    return true;
                case 158:
                    android.content.ComponentName componentName36 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    android.os.UserHandle userHandle3 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean switchUser = switchUser(componentName36, userHandle3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(switchUser);
                    return true;
                case 159:
                    android.content.ComponentName componentName37 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    android.os.UserHandle userHandle4 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int startUserInBackground = startUserInBackground(componentName37, userHandle4);
                    parcel2.writeNoException();
                    parcel2.writeInt(startUserInBackground);
                    return true;
                case 160:
                    android.content.ComponentName componentName38 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    android.os.UserHandle userHandle5 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    int stopUser = stopUser(componentName38, userHandle5);
                    parcel2.writeNoException();
                    parcel2.writeInt(stopUser);
                    return true;
                case 161:
                    android.content.ComponentName componentName39 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    int logoutUser = logoutUser(componentName39);
                    parcel2.writeNoException();
                    parcel2.writeInt(logoutUser);
                    return true;
                case 162:
                    int logoutUserInternal = logoutUserInternal();
                    parcel2.writeNoException();
                    parcel2.writeInt(logoutUserInternal);
                    return true;
                case 163:
                    int logoutUserId = getLogoutUserId();
                    parcel2.writeNoException();
                    parcel2.writeInt(logoutUserId);
                    return true;
                case 164:
                    android.content.ComponentName componentName40 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    java.util.List<android.os.UserHandle> secondaryUsers = getSecondaryUsers(componentName40);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(secondaryUsers, 1);
                    return true;
                case 165:
                    int readInt41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    acknowledgeNewUserDisclaimer(readInt41);
                    parcel2.writeNoException();
                    return true;
                case 166:
                    int readInt42 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isNewUserDisclaimerAcknowledged = isNewUserDisclaimerAcknowledged(readInt42);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isNewUserDisclaimerAcknowledged);
                    return true;
                case 167:
                    return onTransact$enableSystemApp$(parcel, parcel2);
                case 168:
                    return onTransact$enableSystemAppWithIntent$(parcel, parcel2);
                case 169:
                    return onTransact$installExistingPackage$(parcel, parcel2);
                case 170:
                    return onTransact$setAccountManagementDisabled$(parcel, parcel2);
                case 171:
                    java.lang.String readString25 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String[] accountTypesWithManagementDisabled = getAccountTypesWithManagementDisabled(readString25);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(accountTypesWithManagementDisabled);
                    return true;
                case 172:
                    return onTransact$getAccountTypesWithManagementDisabledAsUser$(parcel, parcel2);
                case 173:
                    android.content.ComponentName componentName41 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    boolean readBoolean11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSecondaryLockscreenEnabled(componentName41, readBoolean11);
                    parcel2.writeNoException();
                    return true;
                case 174:
                    android.os.UserHandle userHandle6 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isSecondaryLockscreenEnabled = isSecondaryLockscreenEnabled(userHandle6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSecondaryLockscreenEnabled);
                    return true;
                case 175:
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.app.admin.PreferentialNetworkServiceConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    setPreferentialNetworkServiceConfigs(createTypedArrayList);
                    parcel2.writeNoException();
                    return true;
                case 176:
                    java.util.List<android.app.admin.PreferentialNetworkServiceConfig> preferentialNetworkServiceConfigs = getPreferentialNetworkServiceConfigs();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(preferentialNetworkServiceConfigs, 1);
                    return true;
                case 177:
                    return onTransact$setLockTaskPackages$(parcel, parcel2);
                case 178:
                    android.content.ComponentName componentName42 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    java.lang.String readString26 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String[] lockTaskPackages = getLockTaskPackages(componentName42, readString26);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(lockTaskPackages);
                    return true;
                case 179:
                    java.lang.String readString27 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isLockTaskPermitted = isLockTaskPermitted(readString27);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isLockTaskPermitted);
                    return true;
                case 180:
                    return onTransact$setLockTaskFeatures$(parcel, parcel2);
                case 181:
                    android.content.ComponentName componentName43 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    java.lang.String readString28 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int lockTaskFeatures = getLockTaskFeatures(componentName43, readString28);
                    parcel2.writeNoException();
                    parcel2.writeInt(lockTaskFeatures);
                    return true;
                case 182:
                    return onTransact$setGlobalSetting$(parcel, parcel2);
                case 183:
                    return onTransact$setSystemSetting$(parcel, parcel2);
                case 184:
                    return onTransact$setSecureSetting$(parcel, parcel2);
                case 185:
                    return onTransact$setConfiguredNetworksLockdownState$(parcel, parcel2);
                case 186:
                    android.content.ComponentName componentName44 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean hasLockdownAdminConfiguredNetworks = hasLockdownAdminConfiguredNetworks(componentName44);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasLockdownAdminConfiguredNetworks);
                    return true;
                case 187:
                    android.content.ComponentName componentName45 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    boolean readBoolean12 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setLocationEnabled(componentName45, readBoolean12);
                    parcel2.writeNoException();
                    return true;
                case 188:
                    return onTransact$setTime$(parcel, parcel2);
                case 189:
                    return onTransact$setTimeZone$(parcel, parcel2);
                case 190:
                    android.content.ComponentName componentName46 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    boolean readBoolean13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setMasterVolumeMuted(componentName46, readBoolean13);
                    parcel2.writeNoException();
                    return true;
                case 191:
                    android.content.ComponentName componentName47 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isMasterVolumeMuted = isMasterVolumeMuted(componentName47);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isMasterVolumeMuted);
                    return true;
                case 192:
                    return onTransact$notifyLockTaskModeChanged$(parcel, parcel2);
                case 193:
                    return onTransact$setUninstallBlocked$(parcel, parcel2);
                case 194:
                    java.lang.String readString29 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isUninstallBlocked = isUninstallBlocked(readString29);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUninstallBlocked);
                    return true;
                case 195:
                    android.content.ComponentName componentName48 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    boolean readBoolean14 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setCrossProfileCallerIdDisabled(componentName48, readBoolean14);
                    parcel2.writeNoException();
                    return true;
                case 196:
                    android.content.ComponentName componentName49 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean crossProfileCallerIdDisabled = getCrossProfileCallerIdDisabled(componentName49);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(crossProfileCallerIdDisabled);
                    return true;
                case 197:
                    int readInt43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean crossProfileCallerIdDisabledForUser = getCrossProfileCallerIdDisabledForUser(readInt43);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(crossProfileCallerIdDisabledForUser);
                    return true;
                case 198:
                    android.content.ComponentName componentName50 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    boolean readBoolean15 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setCrossProfileContactsSearchDisabled(componentName50, readBoolean15);
                    parcel2.writeNoException();
                    return true;
                case 199:
                    android.content.ComponentName componentName51 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean crossProfileContactsSearchDisabled = getCrossProfileContactsSearchDisabled(componentName51);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(crossProfileContactsSearchDisabled);
                    return true;
                case 200:
                    int readInt44 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean crossProfileContactsSearchDisabledForUser = getCrossProfileContactsSearchDisabledForUser(readInt44);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(crossProfileContactsSearchDisabledForUser);
                    return true;
                case 201:
                    return onTransact$startManagedQuickContact$(parcel, parcel2);
                case 202:
                    android.app.admin.PackagePolicy packagePolicy = (android.app.admin.PackagePolicy) parcel.readTypedObject(android.app.admin.PackagePolicy.CREATOR);
                    parcel.enforceNoDataAvail();
                    setManagedProfileCallerIdAccessPolicy(packagePolicy);
                    parcel2.writeNoException();
                    return true;
                case 203:
                    android.app.admin.PackagePolicy managedProfileCallerIdAccessPolicy = getManagedProfileCallerIdAccessPolicy();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(managedProfileCallerIdAccessPolicy, 1);
                    return true;
                case 204:
                    int readInt45 = parcel.readInt();
                    java.lang.String readString30 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean hasManagedProfileCallerIdAccess = hasManagedProfileCallerIdAccess(readInt45, readString30);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasManagedProfileCallerIdAccess);
                    return true;
                case 205:
                    android.app.admin.PackagePolicy packagePolicy2 = (android.app.admin.PackagePolicy) parcel.readTypedObject(android.app.admin.PackagePolicy.CREATOR);
                    parcel.enforceNoDataAvail();
                    setCredentialManagerPolicy(packagePolicy2);
                    parcel2.writeNoException();
                    return true;
                case 206:
                    int readInt46 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.app.admin.PackagePolicy credentialManagerPolicy = getCredentialManagerPolicy(readInt46);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(credentialManagerPolicy, 1);
                    return true;
                case 207:
                    android.app.admin.PackagePolicy packagePolicy3 = (android.app.admin.PackagePolicy) parcel.readTypedObject(android.app.admin.PackagePolicy.CREATOR);
                    parcel.enforceNoDataAvail();
                    setManagedProfileContactsAccessPolicy(packagePolicy3);
                    parcel2.writeNoException();
                    return true;
                case 208:
                    android.app.admin.PackagePolicy managedProfileContactsAccessPolicy = getManagedProfileContactsAccessPolicy();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(managedProfileContactsAccessPolicy, 1);
                    return true;
                case 209:
                    int readInt47 = parcel.readInt();
                    java.lang.String readString31 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean hasManagedProfileContactsAccess = hasManagedProfileContactsAccess(readInt47, readString31);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasManagedProfileContactsAccess);
                    return true;
                case 210:
                    android.content.ComponentName componentName52 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    boolean readBoolean16 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setBluetoothContactSharingDisabled(componentName52, readBoolean16);
                    parcel2.writeNoException();
                    return true;
                case 211:
                    android.content.ComponentName componentName53 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean bluetoothContactSharingDisabled = getBluetoothContactSharingDisabled(componentName53);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(bluetoothContactSharingDisabled);
                    return true;
                case 212:
                    int readInt48 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean bluetoothContactSharingDisabledForUser = getBluetoothContactSharingDisabledForUser(readInt48);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(bluetoothContactSharingDisabledForUser);
                    return true;
                case 213:
                    return onTransact$setTrustAgentConfiguration$(parcel, parcel2);
                case 214:
                    return onTransact$getTrustAgentConfiguration$(parcel, parcel2);
                case 215:
                    return onTransact$addCrossProfileWidgetProvider$(parcel, parcel2);
                case 216:
                    return onTransact$removeCrossProfileWidgetProvider$(parcel, parcel2);
                case 217:
                    android.content.ComponentName componentName54 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    java.lang.String readString32 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.util.List<java.lang.String> crossProfileWidgetProviders = getCrossProfileWidgetProviders(componentName54, readString32);
                    parcel2.writeNoException();
                    parcel2.writeStringList(crossProfileWidgetProviders);
                    return true;
                case 218:
                    android.content.ComponentName componentName55 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    boolean readBoolean17 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAutoTimeRequired(componentName55, readBoolean17);
                    parcel2.writeNoException();
                    return true;
                case 219:
                    boolean autoTimeRequired = getAutoTimeRequired();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(autoTimeRequired);
                    return true;
                case 220:
                    return onTransact$setAutoTimeEnabled$(parcel, parcel2);
                case 221:
                    android.content.ComponentName componentName56 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    java.lang.String readString33 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean autoTimeEnabled = getAutoTimeEnabled(componentName56, readString33);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(autoTimeEnabled);
                    return true;
                case 222:
                    return onTransact$setAutoTimeZoneEnabled$(parcel, parcel2);
                case 223:
                    android.content.ComponentName componentName57 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    java.lang.String readString34 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean autoTimeZoneEnabled = getAutoTimeZoneEnabled(componentName57, readString34);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(autoTimeZoneEnabled);
                    return true;
                case 224:
                    android.content.ComponentName componentName58 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    boolean readBoolean18 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setForceEphemeralUsers(componentName58, readBoolean18);
                    parcel2.writeNoException();
                    return true;
                case 225:
                    android.content.ComponentName componentName59 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean forceEphemeralUsers = getForceEphemeralUsers(componentName59);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(forceEphemeralUsers);
                    return true;
                case 226:
                    android.content.ComponentName componentName60 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    int readInt49 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isRemovingAdmin = isRemovingAdmin(componentName60, readInt49);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isRemovingAdmin);
                    return true;
                case 227:
                    android.content.ComponentName componentName61 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    android.graphics.Bitmap bitmap = (android.graphics.Bitmap) parcel.readTypedObject(android.graphics.Bitmap.CREATOR);
                    parcel.enforceNoDataAvail();
                    setUserIcon(componentName61, bitmap);
                    parcel2.writeNoException();
                    return true;
                case 228:
                    return onTransact$setSystemUpdatePolicy$(parcel, parcel2);
                case 229:
                    android.app.admin.SystemUpdatePolicy systemUpdatePolicy = getSystemUpdatePolicy();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(systemUpdatePolicy, 1);
                    return true;
                case 230:
                    clearSystemUpdatePolicyFreezePeriodRecord();
                    parcel2.writeNoException();
                    return true;
                case 231:
                    android.content.ComponentName componentName62 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    boolean readBoolean19 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean keyguardDisabled = setKeyguardDisabled(componentName62, readBoolean19);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(keyguardDisabled);
                    return true;
                case 232:
                    return onTransact$setStatusBarDisabled$(parcel, parcel2);
                case 233:
                    java.lang.String readString35 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isStatusBarDisabled = isStatusBarDisabled(readString35);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isStatusBarDisabled);
                    return true;
                case 234:
                    boolean doNotAskCredentialsOnBoot = getDoNotAskCredentialsOnBoot();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(doNotAskCredentialsOnBoot);
                    return true;
                case 235:
                    android.app.admin.SystemUpdateInfo systemUpdateInfo = (android.app.admin.SystemUpdateInfo) parcel.readTypedObject(android.app.admin.SystemUpdateInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyPendingSystemUpdate(systemUpdateInfo);
                    parcel2.writeNoException();
                    return true;
                case 236:
                    android.content.ComponentName componentName63 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    java.lang.String readString36 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.app.admin.SystemUpdateInfo pendingSystemUpdate = getPendingSystemUpdate(componentName63, readString36);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(pendingSystemUpdate, 1);
                    return true;
                case 237:
                    return onTransact$setPermissionPolicy$(parcel, parcel2);
                case 238:
                    android.content.ComponentName componentName64 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    int permissionPolicy = getPermissionPolicy(componentName64);
                    parcel2.writeNoException();
                    parcel2.writeInt(permissionPolicy);
                    return true;
                case 239:
                    return onTransact$setPermissionGrantState$(parcel, parcel2);
                case 240:
                    return onTransact$getPermissionGrantState$(parcel, parcel2);
                case 241:
                    java.lang.String readString37 = parcel.readString();
                    java.lang.String readString38 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isProvisioningAllowed = isProvisioningAllowed(readString37, readString38);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isProvisioningAllowed);
                    return true;
                case 242:
                    java.lang.String readString39 = parcel.readString();
                    java.lang.String readString40 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int checkProvisioningPrecondition = checkProvisioningPrecondition(readString39, readString40);
                    parcel2.writeNoException();
                    parcel2.writeInt(checkProvisioningPrecondition);
                    return true;
                case 243:
                    return onTransact$setKeepUninstalledPackages$(parcel, parcel2);
                case 244:
                    android.content.ComponentName componentName65 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    java.lang.String readString41 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.util.List<java.lang.String> keepUninstalledPackages = getKeepUninstalledPackages(componentName65, readString41);
                    parcel2.writeNoException();
                    parcel2.writeStringList(keepUninstalledPackages);
                    return true;
                case 245:
                    android.content.ComponentName componentName66 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isManagedProfile = isManagedProfile(componentName66);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isManagedProfile);
                    return true;
                case 246:
                    android.content.ComponentName componentName67 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    java.lang.String readString42 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String wifiMacAddress = getWifiMacAddress(componentName67, readString42);
                    parcel2.writeNoException();
                    parcel2.writeString(wifiMacAddress);
                    return true;
                case 247:
                    android.content.ComponentName componentName68 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    reboot(componentName68);
                    parcel2.writeNoException();
                    return true;
                case 248:
                    return onTransact$setShortSupportMessage$(parcel, parcel2);
                case 249:
                    android.content.ComponentName componentName69 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    java.lang.String readString43 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.CharSequence shortSupportMessage = getShortSupportMessage(componentName69, readString43);
                    parcel2.writeNoException();
                    if (shortSupportMessage != null) {
                        parcel2.writeInt(1);
                        android.text.TextUtils.writeToParcel(shortSupportMessage, parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 250:
                    android.content.ComponentName componentName70 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    java.lang.CharSequence charSequence2 = (java.lang.CharSequence) parcel.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                    parcel.enforceNoDataAvail();
                    setLongSupportMessage(componentName70, charSequence2);
                    parcel2.writeNoException();
                    return true;
                case 251:
                    android.content.ComponentName componentName71 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    java.lang.CharSequence longSupportMessage = getLongSupportMessage(componentName71);
                    parcel2.writeNoException();
                    if (longSupportMessage != null) {
                        parcel2.writeInt(1);
                        android.text.TextUtils.writeToParcel(longSupportMessage, parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 252:
                    android.content.ComponentName componentName72 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    int readInt50 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.CharSequence shortSupportMessageForUser = getShortSupportMessageForUser(componentName72, readInt50);
                    parcel2.writeNoException();
                    if (shortSupportMessageForUser != null) {
                        parcel2.writeInt(1);
                        android.text.TextUtils.writeToParcel(shortSupportMessageForUser, parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 253:
                    android.content.ComponentName componentName73 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    int readInt51 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.CharSequence longSupportMessageForUser = getLongSupportMessageForUser(componentName73, readInt51);
                    parcel2.writeNoException();
                    if (longSupportMessageForUser != null) {
                        parcel2.writeInt(1);
                        android.text.TextUtils.writeToParcel(longSupportMessageForUser, parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 254:
                    android.content.ComponentName componentName74 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    int readInt52 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setOrganizationColor(componentName74, readInt52);
                    parcel2.writeNoException();
                    return true;
                case 255:
                    int readInt53 = parcel.readInt();
                    int readInt54 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setOrganizationColorForUser(readInt53, readInt54);
                    parcel2.writeNoException();
                    return true;
                case 256:
                    int readInt55 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearOrganizationIdForUser(readInt55);
                    parcel2.writeNoException();
                    return true;
                case 257:
                    android.content.ComponentName componentName75 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    int organizationColor = getOrganizationColor(componentName75);
                    parcel2.writeNoException();
                    parcel2.writeInt(organizationColor);
                    return true;
                case 258:
                    int readInt56 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int organizationColorForUser = getOrganizationColorForUser(readInt56);
                    parcel2.writeNoException();
                    parcel2.writeInt(organizationColorForUser);
                    return true;
                case 259:
                    return onTransact$setOrganizationName$(parcel, parcel2);
                case 260:
                    android.content.ComponentName componentName76 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    java.lang.String readString44 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.CharSequence organizationName = getOrganizationName(componentName76, readString44);
                    parcel2.writeNoException();
                    if (organizationName != null) {
                        parcel2.writeInt(1);
                        android.text.TextUtils.writeToParcel(organizationName, parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 261:
                    java.lang.CharSequence deviceOwnerOrganizationName = getDeviceOwnerOrganizationName();
                    parcel2.writeNoException();
                    if (deviceOwnerOrganizationName != null) {
                        parcel2.writeInt(1);
                        android.text.TextUtils.writeToParcel(deviceOwnerOrganizationName, parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 262:
                    int readInt57 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.CharSequence organizationNameForUser = getOrganizationNameForUser(readInt57);
                    parcel2.writeNoException();
                    if (organizationNameForUser != null) {
                        parcel2.writeInt(1);
                        android.text.TextUtils.writeToParcel(organizationNameForUser, parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 263:
                    int readInt58 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int userProvisioningState = getUserProvisioningState(readInt58);
                    parcel2.writeNoException();
                    parcel2.writeInt(userProvisioningState);
                    return true;
                case 264:
                    int readInt59 = parcel.readInt();
                    int readInt60 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setUserProvisioningState(readInt59, readInt60);
                    parcel2.writeNoException();
                    return true;
                case 265:
                    android.content.ComponentName componentName77 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    java.util.ArrayList<java.lang.String> createStringArrayList3 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    setAffiliationIds(componentName77, createStringArrayList3);
                    parcel2.writeNoException();
                    return true;
                case 266:
                    android.content.ComponentName componentName78 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    java.util.List<java.lang.String> affiliationIds = getAffiliationIds(componentName78);
                    parcel2.writeNoException();
                    parcel2.writeStringList(affiliationIds);
                    return true;
                case 267:
                    boolean isCallingUserAffiliated = isCallingUserAffiliated();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCallingUserAffiliated);
                    return true;
                case 268:
                    int readInt61 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isAffiliatedUser = isAffiliatedUser(readInt61);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAffiliatedUser);
                    return true;
                case 269:
                    return onTransact$setSecurityLoggingEnabled$(parcel, parcel2);
                case 270:
                    android.content.ComponentName componentName79 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    java.lang.String readString45 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isSecurityLoggingEnabled = isSecurityLoggingEnabled(componentName79, readString45);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSecurityLoggingEnabled);
                    return true;
                case 271:
                    android.content.ComponentName componentName80 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    java.lang.String readString46 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice retrieveSecurityLogs = retrieveSecurityLogs(componentName80, readString46);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(retrieveSecurityLogs, 1);
                    return true;
                case 272:
                    android.content.ComponentName componentName81 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    java.lang.String readString47 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice retrievePreRebootSecurityLogs = retrievePreRebootSecurityLogs(componentName81, readString47);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(retrievePreRebootSecurityLogs, 1);
                    return true;
                case 273:
                    long forceNetworkLogs = forceNetworkLogs();
                    parcel2.writeNoException();
                    parcel2.writeLong(forceNetworkLogs);
                    return true;
                case 274:
                    long forceSecurityLogs = forceSecurityLogs();
                    parcel2.writeNoException();
                    parcel2.writeLong(forceSecurityLogs);
                    return true;
                case 275:
                    java.lang.String readString48 = parcel.readString();
                    boolean readBoolean20 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAuditLogEnabled(readString48, readBoolean20);
                    parcel2.writeNoException();
                    return true;
                case 276:
                    java.lang.String readString49 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isAuditLogEnabled = isAuditLogEnabled(readString49);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAuditLogEnabled);
                    return true;
                case 277:
                    java.lang.String readString50 = parcel.readString();
                    android.app.admin.IAuditLogEventsCallback asInterface = android.app.admin.IAuditLogEventsCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setAuditLogEventsCallback(readString50, asInterface);
                    parcel2.writeNoException();
                    return true;
                case 278:
                    java.lang.String readString51 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isUninstallInQueue = isUninstallInQueue(readString51);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUninstallInQueue);
                    return true;
                case 279:
                    java.lang.String readString52 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    uninstallPackageWithActiveAdmins(readString52);
                    parcel2.writeNoException();
                    return true;
                case 280:
                    boolean isDeviceProvisioned = isDeviceProvisioned();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDeviceProvisioned);
                    return true;
                case 281:
                    boolean isDeviceProvisioningConfigApplied = isDeviceProvisioningConfigApplied();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDeviceProvisioningConfigApplied);
                    return true;
                case 282:
                    setDeviceProvisioningConfigApplied();
                    parcel2.writeNoException();
                    return true;
                case 283:
                    int readInt62 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    forceUpdateUserSetupComplete(readInt62);
                    parcel2.writeNoException();
                    return true;
                case 284:
                    android.content.ComponentName componentName82 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    boolean readBoolean21 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setBackupServiceEnabled(componentName82, readBoolean21);
                    parcel2.writeNoException();
                    return true;
                case 285:
                    android.content.ComponentName componentName83 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isBackupServiceEnabled = isBackupServiceEnabled(componentName83);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isBackupServiceEnabled);
                    return true;
                case 286:
                    return onTransact$setNetworkLoggingEnabled$(parcel, parcel2);
                case 287:
                    android.content.ComponentName componentName84 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    java.lang.String readString53 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isNetworkLoggingEnabled = isNetworkLoggingEnabled(componentName84, readString53);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isNetworkLoggingEnabled);
                    return true;
                case 288:
                    return onTransact$retrieveNetworkLogs$(parcel, parcel2);
                case 289:
                    return onTransact$bindDeviceAdminServiceAsUser$(parcel, parcel2);
                case 290:
                    android.content.ComponentName componentName85 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    java.util.List<android.os.UserHandle> bindDeviceAdminTargetUsers = getBindDeviceAdminTargetUsers(componentName85);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(bindDeviceAdminTargetUsers, 1);
                    return true;
                case 291:
                    android.content.ComponentName componentName86 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isEphemeralUser = isEphemeralUser(componentName86);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isEphemeralUser);
                    return true;
                case 292:
                    long lastSecurityLogRetrievalTime = getLastSecurityLogRetrievalTime();
                    parcel2.writeNoException();
                    parcel2.writeLong(lastSecurityLogRetrievalTime);
                    return true;
                case 293:
                    long lastBugReportRequestTime = getLastBugReportRequestTime();
                    parcel2.writeNoException();
                    parcel2.writeLong(lastBugReportRequestTime);
                    return true;
                case 294:
                    long lastNetworkLogRetrievalTime = getLastNetworkLogRetrievalTime();
                    parcel2.writeNoException();
                    parcel2.writeLong(lastNetworkLogRetrievalTime);
                    return true;
                case 295:
                    return onTransact$setResetPasswordToken$(parcel, parcel2);
                case 296:
                    android.content.ComponentName componentName87 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    java.lang.String readString54 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean clearResetPasswordToken = clearResetPasswordToken(componentName87, readString54);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(clearResetPasswordToken);
                    return true;
                case 297:
                    android.content.ComponentName componentName88 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    java.lang.String readString55 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isResetPasswordTokenActive = isResetPasswordTokenActive(componentName88, readString55);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isResetPasswordTokenActive);
                    return true;
                case 298:
                    return onTransact$resetPasswordWithToken$(parcel, parcel2);
                case 299:
                    boolean isCurrentInputMethodSetByOwner = isCurrentInputMethodSetByOwner();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCurrentInputMethodSetByOwner);
                    return true;
                case 300:
                    android.os.UserHandle userHandle7 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.content.pm.StringParceledListSlice ownerInstalledCaCerts = getOwnerInstalledCaCerts(userHandle7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(ownerInstalledCaCerts, 1);
                    return true;
                case 301:
                    return onTransact$clearApplicationUserData$(parcel, parcel2);
                case 302:
                    android.content.ComponentName componentName89 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    boolean readBoolean22 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setLogoutEnabled(componentName89, readBoolean22);
                    parcel2.writeNoException();
                    return true;
                case 303:
                    boolean isLogoutEnabled = isLogoutEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isLogoutEnabled);
                    return true;
                case 304:
                    return onTransact$getDisallowedSystemApps$(parcel, parcel2);
                case 305:
                    return onTransact$transferOwnership$(parcel, parcel2);
                case 306:
                    android.os.PersistableBundle transferOwnershipBundle = getTransferOwnershipBundle();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(transferOwnershipBundle, 1);
                    return true;
                case 307:
                    android.content.ComponentName componentName90 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    java.lang.CharSequence charSequence3 = (java.lang.CharSequence) parcel.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                    parcel.enforceNoDataAvail();
                    setStartUserSessionMessage(componentName90, charSequence3);
                    parcel2.writeNoException();
                    return true;
                case 308:
                    android.content.ComponentName componentName91 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    java.lang.CharSequence charSequence4 = (java.lang.CharSequence) parcel.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                    parcel.enforceNoDataAvail();
                    setEndUserSessionMessage(componentName91, charSequence4);
                    parcel2.writeNoException();
                    return true;
                case 309:
                    android.content.ComponentName componentName92 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    java.lang.CharSequence startUserSessionMessage = getStartUserSessionMessage(componentName92);
                    parcel2.writeNoException();
                    if (startUserSessionMessage != null) {
                        parcel2.writeInt(1);
                        android.text.TextUtils.writeToParcel(startUserSessionMessage, parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 310:
                    android.content.ComponentName componentName93 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    java.lang.CharSequence endUserSessionMessage = getEndUserSessionMessage(componentName93);
                    parcel2.writeNoException();
                    if (endUserSessionMessage != null) {
                        parcel2.writeInt(1);
                        android.text.TextUtils.writeToParcel(endUserSessionMessage, parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 311:
                    android.content.ComponentName componentName94 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    java.util.ArrayList<java.lang.String> createStringArrayList4 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    java.util.List<java.lang.String> meteredDataDisabledPackages = setMeteredDataDisabledPackages(componentName94, createStringArrayList4);
                    parcel2.writeNoException();
                    parcel2.writeStringList(meteredDataDisabledPackages);
                    return true;
                case 312:
                    android.content.ComponentName componentName95 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    java.util.List<java.lang.String> meteredDataDisabledPackages2 = getMeteredDataDisabledPackages(componentName95);
                    parcel2.writeNoException();
                    parcel2.writeStringList(meteredDataDisabledPackages2);
                    return true;
                case 313:
                    android.content.ComponentName componentName96 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    android.telephony.data.ApnSetting apnSetting = (android.telephony.data.ApnSetting) parcel.readTypedObject(android.telephony.data.ApnSetting.CREATOR);
                    parcel.enforceNoDataAvail();
                    int addOverrideApn = addOverrideApn(componentName96, apnSetting);
                    parcel2.writeNoException();
                    parcel2.writeInt(addOverrideApn);
                    return true;
                case 314:
                    return onTransact$updateOverrideApn$(parcel, parcel2);
                case 315:
                    android.content.ComponentName componentName97 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    int readInt63 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean removeOverrideApn = removeOverrideApn(componentName97, readInt63);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeOverrideApn);
                    return true;
                case 316:
                    android.content.ComponentName componentName98 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    java.util.List<android.telephony.data.ApnSetting> overrideApns = getOverrideApns(componentName98);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(overrideApns, 1);
                    return true;
                case 317:
                    android.content.ComponentName componentName99 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    boolean readBoolean23 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setOverrideApnsEnabled(componentName99, readBoolean23);
                    parcel2.writeNoException();
                    return true;
                case 318:
                    android.content.ComponentName componentName100 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isOverrideApnEnabled = isOverrideApnEnabled(componentName100);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isOverrideApnEnabled);
                    return true;
                case 319:
                    return onTransact$isMeteredDataDisabledPackageForUser$(parcel, parcel2);
                case 320:
                    return onTransact$setGlobalPrivateDns$(parcel, parcel2);
                case 321:
                    android.content.ComponentName componentName101 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    int globalPrivateDnsMode = getGlobalPrivateDnsMode(componentName101);
                    parcel2.writeNoException();
                    parcel2.writeInt(globalPrivateDnsMode);
                    return true;
                case 322:
                    android.content.ComponentName componentName102 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    java.lang.String globalPrivateDnsHost = getGlobalPrivateDnsHost(componentName102);
                    parcel2.writeNoException();
                    parcel2.writeString(globalPrivateDnsHost);
                    return true;
                case 323:
                    return onTransact$setProfileOwnerOnOrganizationOwnedDevice$(parcel, parcel2);
                case 324:
                    return onTransact$installUpdateFromFile$(parcel, parcel2);
                case 325:
                    android.content.ComponentName componentName103 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    java.util.ArrayList<java.lang.String> createStringArrayList5 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    setCrossProfileCalendarPackages(componentName103, createStringArrayList5);
                    parcel2.writeNoException();
                    return true;
                case 326:
                    android.content.ComponentName componentName104 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    java.util.List<java.lang.String> crossProfileCalendarPackages = getCrossProfileCalendarPackages(componentName104);
                    parcel2.writeNoException();
                    parcel2.writeStringList(crossProfileCalendarPackages);
                    return true;
                case 327:
                    java.lang.String readString56 = parcel.readString();
                    int readInt64 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isPackageAllowedToAccessCalendarForUser = isPackageAllowedToAccessCalendarForUser(readString56, readInt64);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPackageAllowedToAccessCalendarForUser);
                    return true;
                case 328:
                    int readInt65 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.List<java.lang.String> crossProfileCalendarPackagesForUser = getCrossProfileCalendarPackagesForUser(readInt65);
                    parcel2.writeNoException();
                    parcel2.writeStringList(crossProfileCalendarPackagesForUser);
                    return true;
                case 329:
                    android.content.ComponentName componentName105 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    java.util.ArrayList<java.lang.String> createStringArrayList6 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    setCrossProfilePackages(componentName105, createStringArrayList6);
                    parcel2.writeNoException();
                    return true;
                case 330:
                    android.content.ComponentName componentName106 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    java.util.List<java.lang.String> crossProfilePackages = getCrossProfilePackages(componentName106);
                    parcel2.writeNoException();
                    parcel2.writeStringList(crossProfilePackages);
                    return true;
                case 331:
                    int readInt66 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.List<java.lang.String> allCrossProfilePackages = getAllCrossProfilePackages(readInt66);
                    parcel2.writeNoException();
                    parcel2.writeStringList(allCrossProfilePackages);
                    return true;
                case 332:
                    java.util.List<java.lang.String> defaultCrossProfilePackages = getDefaultCrossProfilePackages();
                    parcel2.writeNoException();
                    parcel2.writeStringList(defaultCrossProfilePackages);
                    return true;
                case 333:
                    boolean isManagedKiosk = isManagedKiosk();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isManagedKiosk);
                    return true;
                case 334:
                    boolean isUnattendedManagedKiosk = isUnattendedManagedKiosk();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUnattendedManagedKiosk);
                    return true;
                case 335:
                    return onTransact$startViewCalendarEventInManagedProfile$(parcel, parcel2);
                case 336:
                    return onTransact$setKeyGrantForApp$(parcel, parcel2);
                case 337:
                    java.lang.String readString57 = parcel.readString();
                    java.lang.String readString58 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.app.admin.ParcelableGranteeMap keyPairGrants = getKeyPairGrants(readString57, readString58);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyPairGrants, 1);
                    return true;
                case 338:
                    return onTransact$setKeyGrantToWifiAuth$(parcel, parcel2);
                case 339:
                    java.lang.String readString59 = parcel.readString();
                    java.lang.String readString60 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isKeyPairGrantedToWifiAuth = isKeyPairGrantedToWifiAuth(readString59, readString60);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isKeyPairGrantedToWifiAuth);
                    return true;
                case 340:
                    return onTransact$setUserControlDisabledPackages$(parcel, parcel2);
                case 341:
                    android.content.ComponentName componentName107 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    java.lang.String readString61 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.util.List<java.lang.String> userControlDisabledPackages = getUserControlDisabledPackages(componentName107, readString61);
                    parcel2.writeNoException();
                    parcel2.writeStringList(userControlDisabledPackages);
                    return true;
                case 342:
                    return onTransact$setCommonCriteriaModeEnabled$(parcel, parcel2);
                case 343:
                    android.content.ComponentName componentName108 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isCommonCriteriaModeEnabled = isCommonCriteriaModeEnabled(componentName108);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCommonCriteriaModeEnabled);
                    return true;
                case 344:
                    android.content.ComponentName componentName109 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    int personalAppsSuspendedReasons = getPersonalAppsSuspendedReasons(componentName109);
                    parcel2.writeNoException();
                    parcel2.writeInt(personalAppsSuspendedReasons);
                    return true;
                case 345:
                    android.content.ComponentName componentName110 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    boolean readBoolean24 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setPersonalAppsSuspended(componentName110, readBoolean24);
                    parcel2.writeNoException();
                    return true;
                case 346:
                    android.content.ComponentName componentName111 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    long managedProfileMaximumTimeOff = getManagedProfileMaximumTimeOff(componentName111);
                    parcel2.writeNoException();
                    parcel2.writeLong(managedProfileMaximumTimeOff);
                    return true;
                case 347:
                    android.content.ComponentName componentName112 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setManagedProfileMaximumTimeOff(componentName112, readLong);
                    parcel2.writeNoException();
                    return true;
                case 348:
                    acknowledgeDeviceCompliant();
                    parcel2.writeNoException();
                    return true;
                case 349:
                    boolean isComplianceAcknowledgementRequired = isComplianceAcknowledgementRequired();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isComplianceAcknowledgementRequired);
                    return true;
                case 350:
                    int readInt67 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean canProfileOwnerResetPasswordWhenLocked = canProfileOwnerResetPasswordWhenLocked(readInt67);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canProfileOwnerResetPasswordWhenLocked);
                    return true;
                case 351:
                    int readInt68 = parcel.readInt();
                    int readInt69 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setNextOperationSafety(readInt68, readInt69);
                    parcel2.writeNoException();
                    return true;
                case 352:
                    int readInt70 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isSafeOperation = isSafeOperation(readInt70);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSafeOperation);
                    return true;
                case 353:
                    java.lang.String readString62 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String enrollmentSpecificId = getEnrollmentSpecificId(readString62);
                    parcel2.writeNoException();
                    parcel2.writeString(enrollmentSpecificId);
                    return true;
                case 354:
                    return onTransact$setOrganizationIdForUser$(parcel, parcel2);
                case 355:
                    android.app.admin.ManagedProfileProvisioningParams managedProfileProvisioningParams = (android.app.admin.ManagedProfileProvisioningParams) parcel.readTypedObject(android.app.admin.ManagedProfileProvisioningParams.CREATOR);
                    java.lang.String readString63 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.os.UserHandle createAndProvisionManagedProfile = createAndProvisionManagedProfile(managedProfileProvisioningParams, readString63);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(createAndProvisionManagedProfile, 1);
                    return true;
                case 356:
                    android.app.admin.FullyManagedDeviceProvisioningParams fullyManagedDeviceProvisioningParams = (android.app.admin.FullyManagedDeviceProvisioningParams) parcel.readTypedObject(android.app.admin.FullyManagedDeviceProvisioningParams.CREATOR);
                    java.lang.String readString64 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    provisionFullyManagedDevice(fullyManagedDeviceProvisioningParams, readString64);
                    parcel2.writeNoException();
                    return true;
                case 357:
                    android.os.UserHandle userHandle8 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    android.accounts.Account account = (android.accounts.Account) parcel.readTypedObject(android.accounts.Account.CREATOR);
                    parcel.enforceNoDataAvail();
                    finalizeWorkProfileProvisioning(userHandle8, account);
                    parcel2.writeNoException();
                    return true;
                case 358:
                    android.content.ComponentName componentName113 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    int readInt71 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDeviceOwnerType(componentName113, readInt71);
                    parcel2.writeNoException();
                    return true;
                case 359:
                    android.content.ComponentName componentName114 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    int deviceOwnerType = getDeviceOwnerType(componentName114);
                    parcel2.writeNoException();
                    parcel2.writeInt(deviceOwnerType);
                    return true;
                case 360:
                    int readInt72 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    resetDefaultCrossProfileIntentFilters(readInt72);
                    parcel2.writeNoException();
                    return true;
                case 361:
                    boolean canAdminGrantSensorsPermissions = canAdminGrantSensorsPermissions();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canAdminGrantSensorsPermissions);
                    return true;
                case 362:
                    java.lang.String readString65 = parcel.readString();
                    boolean readBoolean25 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setUsbDataSignalingEnabled(readString65, readBoolean25);
                    parcel2.writeNoException();
                    return true;
                case 363:
                    java.lang.String readString66 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isUsbDataSignalingEnabled = isUsbDataSignalingEnabled(readString66);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUsbDataSignalingEnabled);
                    return true;
                case 364:
                    boolean canUsbDataSignalingBeDisabled = canUsbDataSignalingBeDisabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canUsbDataSignalingBeDisabled);
                    return true;
                case 365:
                    java.lang.String readString67 = parcel.readString();
                    int readInt73 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setMinimumRequiredWifiSecurityLevel(readString67, readInt73);
                    parcel2.writeNoException();
                    return true;
                case 366:
                    int minimumRequiredWifiSecurityLevel = getMinimumRequiredWifiSecurityLevel();
                    parcel2.writeNoException();
                    parcel2.writeInt(minimumRequiredWifiSecurityLevel);
                    return true;
                case 367:
                    java.lang.String readString68 = parcel.readString();
                    android.app.admin.WifiSsidPolicy wifiSsidPolicy = (android.app.admin.WifiSsidPolicy) parcel.readTypedObject(android.app.admin.WifiSsidPolicy.CREATOR);
                    parcel.enforceNoDataAvail();
                    setWifiSsidPolicy(readString68, wifiSsidPolicy);
                    parcel2.writeNoException();
                    return true;
                case 368:
                    java.lang.String readString69 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.app.admin.WifiSsidPolicy wifiSsidPolicy2 = getWifiSsidPolicy(readString69);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(wifiSsidPolicy2, 1);
                    return true;
                case 369:
                    java.lang.String readString70 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isTheftDetectionTriggered = isTheftDetectionTriggered(readString70);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isTheftDetectionTriggered);
                    return true;
                case 370:
                    java.util.List<android.os.UserHandle> listForegroundAffiliatedUsers = listForegroundAffiliatedUsers();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(listForegroundAffiliatedUsers, 1);
                    return true;
                case 371:
                    java.util.ArrayList createTypedArrayList2 = parcel.createTypedArrayList(android.app.admin.DevicePolicyDrawableResource.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDrawables(createTypedArrayList2);
                    parcel2.writeNoException();
                    return true;
                case 372:
                    java.util.ArrayList<java.lang.String> createStringArrayList7 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    resetDrawables(createStringArrayList7);
                    parcel2.writeNoException();
                    return true;
                case 373:
                    return onTransact$getDrawable$(parcel, parcel2);
                case 374:
                    boolean isDpcDownloaded = isDpcDownloaded();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDpcDownloaded);
                    return true;
                case 375:
                    boolean readBoolean26 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDpcDownloaded(readBoolean26);
                    parcel2.writeNoException();
                    return true;
                case 376:
                    java.util.ArrayList createTypedArrayList3 = parcel.createTypedArrayList(android.app.admin.DevicePolicyStringResource.CREATOR);
                    parcel.enforceNoDataAvail();
                    setStrings(createTypedArrayList3);
                    parcel2.writeNoException();
                    return true;
                case 377:
                    java.util.ArrayList<java.lang.String> createStringArrayList8 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    resetStrings(createStringArrayList8);
                    parcel2.writeNoException();
                    return true;
                case 378:
                    java.lang.String readString71 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.app.admin.ParcelableResource string = getString(readString71);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(string, 1);
                    return true;
                case 379:
                    resetShouldAllowBypassingDevicePolicyManagementRoleQualificationState();
                    parcel2.writeNoException();
                    return true;
                case 380:
                    boolean shouldAllowBypassingDevicePolicyManagementRoleQualification = shouldAllowBypassingDevicePolicyManagementRoleQualification();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(shouldAllowBypassingDevicePolicyManagementRoleQualification);
                    return true;
                case 381:
                    android.os.UserHandle userHandle9 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    java.util.List<android.os.UserHandle> policyManagedProfiles = getPolicyManagedProfiles(userHandle9);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(policyManagedProfiles, 1);
                    return true;
                case 382:
                    return onTransact$setApplicationExemptions$(parcel, parcel2);
                case 383:
                    java.lang.String readString72 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int[] applicationExemptions = getApplicationExemptions(readString72);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(applicationExemptions);
                    return true;
                case 384:
                    int readInt74 = parcel.readInt();
                    java.lang.String readString73 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setMtePolicy(readInt74, readString73);
                    parcel2.writeNoException();
                    return true;
                case 385:
                    java.lang.String readString74 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int mtePolicy = getMtePolicy(readString74);
                    parcel2.writeNoException();
                    parcel2.writeInt(mtePolicy);
                    return true;
                case 386:
                    android.app.admin.ManagedSubscriptionsPolicy managedSubscriptionsPolicy = (android.app.admin.ManagedSubscriptionsPolicy) parcel.readTypedObject(android.app.admin.ManagedSubscriptionsPolicy.CREATOR);
                    parcel.enforceNoDataAvail();
                    setManagedSubscriptionsPolicy(managedSubscriptionsPolicy);
                    parcel2.writeNoException();
                    return true;
                case 387:
                    android.app.admin.ManagedSubscriptionsPolicy managedSubscriptionsPolicy2 = getManagedSubscriptionsPolicy();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(managedSubscriptionsPolicy2, 1);
                    return true;
                case 388:
                    android.app.admin.DevicePolicyState devicePolicyState = getDevicePolicyState();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(devicePolicyState, 1);
                    return true;
                case 389:
                    boolean readBoolean27 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean triggerDevicePolicyEngineMigration = triggerDevicePolicyEngineMigration(readBoolean27);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(triggerDevicePolicyEngineMigration);
                    return true;
                case 390:
                    java.lang.String readString75 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isDeviceFinanced = isDeviceFinanced(readString75);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDeviceFinanced);
                    return true;
                case 391:
                    java.lang.String readString76 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String financedDeviceKioskRoleHolder = getFinancedDeviceKioskRoleHolder(readString76);
                    parcel2.writeNoException();
                    parcel2.writeString(financedDeviceKioskRoleHolder);
                    return true;
                case 392:
                    calculateHasIncompatibleAccounts();
                    parcel2.writeNoException();
                    return true;
                case 393:
                    return onTransact$setContentProtectionPolicy$(parcel, parcel2);
                case 394:
                    android.content.ComponentName componentName115 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    java.lang.String readString77 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int contentProtectionPolicy = getContentProtectionPolicy(componentName115, readString77);
                    parcel2.writeNoException();
                    parcel2.writeInt(contentProtectionPolicy);
                    return true;
                case 395:
                    java.lang.String readString78 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int[] subscriptionIds = getSubscriptionIds(readString78);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(subscriptionIds);
                    return true;
                case 396:
                    java.lang.String readString79 = parcel.readString();
                    int readInt75 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setMaxPolicyStorageLimit(readString79, readInt75);
                    parcel2.writeNoException();
                    return true;
                case 397:
                    java.lang.String readString80 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int maxPolicyStorageLimit = getMaxPolicyStorageLimit(readString80);
                    parcel2.writeNoException();
                    parcel2.writeInt(maxPolicyStorageLimit);
                    return true;
                case 398:
                    int readInt76 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean requireSecureKeyguard = requireSecureKeyguard(readInt76);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requireSecureKeyguard);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.admin.IDevicePolicyManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR;
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPasswordQuality(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getPasswordQuality(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPasswordMinimumLength(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getPasswordMinimumLength(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPasswordMinimumUpperCase(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getPasswordMinimumUpperCase(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPasswordMinimumLowerCase(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getPasswordMinimumLowerCase(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPasswordMinimumLetters(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getPasswordMinimumLetters(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPasswordMinimumNumeric(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getPasswordMinimumNumeric(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPasswordMinimumSymbols(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getPasswordMinimumSymbols(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPasswordMinimumNonLetter(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getPasswordMinimumNonLetter(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public android.app.admin.PasswordMetrics getPasswordMinimumMetrics(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.admin.PasswordMetrics) obtain2.readTypedObject(android.app.admin.PasswordMetrics.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPasswordHistoryLength(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getPasswordHistoryLength(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPasswordExpirationTimeout(android.content.ComponentName componentName, java.lang.String str, long j, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public long getPasswordExpirationTimeout(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public long getPasswordExpiration(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isActivePasswordSufficient(java.lang.String str, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isActivePasswordSufficientForDeviceRequirement() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isPasswordSufficientAfterProfileUnification(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getPasswordComplexity(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setRequiredPasswordComplexity(java.lang.String str, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getRequiredPasswordComplexity(java.lang.String str, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getAggregatedPasswordComplexityForUser(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isUsingUnifiedPassword(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getCurrentFailedPasswordAttempts(java.lang.String str, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getProfileWithMinimumFailedPasswordsForWipe(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setMaximumFailedPasswordsForWipe(android.content.ComponentName componentName, java.lang.String str, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getMaximumFailedPasswordsForWipe(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean resetPassword(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setMaximumTimeToLock(android.content.ComponentName componentName, java.lang.String str, long j, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public long getMaximumTimeToLock(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setRequiredStrongAuthTimeout(android.content.ComponentName componentName, java.lang.String str, long j, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public long getRequiredStrongAuthTimeout(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void lockNow(int i, java.lang.String str, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void wipeDataWithReason(java.lang.String str, int i, java.lang.String str2, boolean z, boolean z2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setFactoryResetProtectionPolicy(android.content.ComponentName componentName, java.lang.String str, android.app.admin.FactoryResetProtectionPolicy factoryResetProtectionPolicy) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(factoryResetProtectionPolicy, 0);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public android.app.admin.FactoryResetProtectionPolicy getFactoryResetProtectionPolicy(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.admin.FactoryResetProtectionPolicy) obtain2.readTypedObject(android.app.admin.FactoryResetProtectionPolicy.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isFactoryResetProtectionPolicySupported() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void sendLostModeLocationUpdate(com.android.internal.infra.AndroidFuture<java.lang.Boolean> androidFuture) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public android.content.ComponentName setGlobalProxy(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.ComponentName) obtain2.readTypedObject(android.content.ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public android.content.ComponentName getGlobalProxyAdmin(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.ComponentName) obtain2.readTypedObject(android.content.ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setRecommendedGlobalProxy(android.content.ComponentName componentName, android.net.ProxyInfo proxyInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(proxyInfo, 0);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int setStorageEncryption(android.content.ComponentName componentName, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean getStorageEncryption(android.content.ComponentName componentName, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getStorageEncryptionStatus(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean requestBugreport(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setCameraDisabled(android.content.ComponentName componentName, java.lang.String str, boolean z, boolean z2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean getCameraDisabled(android.content.ComponentName componentName, java.lang.String str, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setScreenCaptureDisabled(android.content.ComponentName componentName, java.lang.String str, boolean z, boolean z2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean getScreenCaptureDisabled(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setNearbyNotificationStreamingPolicy(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getNearbyNotificationStreamingPolicy(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setNearbyAppStreamingPolicy(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getNearbyAppStreamingPolicy(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setKeyguardDisabledFeatures(android.content.ComponentName componentName, java.lang.String str, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getKeyguardDisabledFeatures(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setActiveAdmin(android.content.ComponentName componentName, boolean z, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isAdminActive(android.content.ComponentName componentName, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public java.util.List<android.content.ComponentName> getActiveAdmins(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.content.ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean packageHasActiveAdmins(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void getRemoveWarning(android.content.ComponentName componentName, android.os.RemoteCallback remoteCallback, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(remoteCallback, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void removeActiveAdmin(android.content.ComponentName componentName, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(68, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void forceRemoveActiveAdmin(android.content.ComponentName componentName, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(69, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean hasGrantedPolicy(android.content.ComponentName componentName, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(70, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void reportPasswordChanged(android.app.admin.PasswordMetrics passwordMetrics, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(passwordMetrics, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(71, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void reportFailedPasswordAttempt(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(72, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void reportSuccessfulPasswordAttempt(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(73, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void reportFailedBiometricAttempt(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(74, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void reportSuccessfulBiometricAttempt(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(75, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void reportKeyguardDismissed(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(76, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void reportKeyguardSecured(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(77, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setDeviceOwner(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(78, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public android.content.ComponentName getDeviceOwnerComponent(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(79, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.ComponentName) obtain2.readTypedObject(android.content.ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public android.content.ComponentName getDeviceOwnerComponentOnUser(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(80, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.ComponentName) obtain2.readTypedObject(android.content.ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean hasDeviceOwner() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(81, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public java.lang.String getDeviceOwnerName() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(82, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void clearDeviceOwner(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(83, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getDeviceOwnerUserId() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(84, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setProfileOwner(android.content.ComponentName componentName, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(85, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public android.content.ComponentName getProfileOwnerAsUser(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(86, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.ComponentName) obtain2.readTypedObject(android.content.ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public android.content.ComponentName getProfileOwnerOrDeviceOwnerSupervisionComponent(android.os.UserHandle userHandle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(87, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.ComponentName) obtain2.readTypedObject(android.content.ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isSupervisionComponent(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(88, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public java.lang.String getProfileOwnerName(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(89, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setProfileEnabled(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(90, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setProfileName(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(91, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void clearProfileOwner(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(92, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean hasUserSetupCompleted() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(93, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isOrganizationOwnedDeviceWithManagedProfile() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(94, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean checkDeviceIdentifierAccess(java.lang.String str, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(95, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setDeviceOwnerLockScreenInfo(android.content.ComponentName componentName, java.lang.CharSequence charSequence) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        android.text.TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(96, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public java.lang.CharSequence getDeviceOwnerLockScreenInfo() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(97, obtain, obtain2, 0);
                    obtain2.readException();
                    return (java.lang.CharSequence) obtain2.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public java.lang.String[] setPackagesSuspended(android.content.ComponentName componentName, java.lang.String str, java.lang.String[] strArr, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeStringArray(strArr);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(98, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isPackageSuspended(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(99, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public java.util.List<java.lang.String> listPolicyExemptApps() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(100, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean installCaCert(android.content.ComponentName componentName, java.lang.String str, byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(101, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void uninstallCaCerts(android.content.ComponentName componentName, java.lang.String str, java.lang.String[] strArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(102, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void enforceCanManageCaCerts(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(103, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean approveCaCert(java.lang.String str, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(104, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isCaCertApproved(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(105, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean installKeyPair(android.content.ComponentName componentName, java.lang.String str, byte[] bArr, byte[] bArr2, byte[] bArr3, java.lang.String str2, boolean z, boolean z2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    obtain.writeByteArray(bArr3);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(106, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean removeKeyPair(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(107, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean hasKeyPair(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(108, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean generateKeyPair(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2, android.security.keystore.ParcelableKeyGenParameterSpec parcelableKeyGenParameterSpec, int i, android.security.keymaster.KeymasterCertificateChain keymasterCertificateChain) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(parcelableKeyGenParameterSpec, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(109, obtain, obtain2, 0);
                    obtain2.readException();
                    boolean readBoolean = obtain2.readBoolean();
                    if (obtain2.readInt() != 0) {
                        keymasterCertificateChain.readFromParcel(obtain2);
                    }
                    return readBoolean;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setKeyPairCertificate(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2, byte[] bArr, byte[] bArr2, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(110, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void choosePrivateKeyAlias(int i, android.net.Uri uri, java.lang.String str, android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(111, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setDelegatedScopes(android.content.ComponentName componentName, java.lang.String str, java.util.List<java.lang.String> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    this.mRemote.transact(112, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public java.util.List<java.lang.String> getDelegatedScopes(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(113, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public java.util.List<java.lang.String> getDelegatePackages(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(114, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setCertInstallerPackage(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(115, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public java.lang.String getCertInstallerPackage(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(116, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setAlwaysOnVpnPackage(android.content.ComponentName componentName, java.lang.String str, boolean z, java.util.List<java.lang.String> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeStringList(list);
                    this.mRemote.transact(117, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public java.lang.String getAlwaysOnVpnPackage(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(118, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public java.lang.String getAlwaysOnVpnPackageForUser(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(119, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isAlwaysOnVpnLockdownEnabled(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(120, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isAlwaysOnVpnLockdownEnabledForUser(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(121, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public java.util.List<java.lang.String> getAlwaysOnVpnLockdownAllowlist(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(122, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void addPersistentPreferredActivity(android.content.ComponentName componentName, java.lang.String str, android.content.IntentFilter intentFilter, android.content.ComponentName componentName2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(intentFilter, 0);
                    obtain.writeTypedObject(componentName2, 0);
                    this.mRemote.transact(123, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void clearPackagePersistentPreferredActivities(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(124, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setDefaultSmsApplication(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(125, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setDefaultDialerApplication(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(126, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setApplicationRestrictions(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(127, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public android.os.Bundle getApplicationRestrictions(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(128, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.Bundle) obtain2.readTypedObject(android.os.Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setApplicationRestrictionsManagingPackage(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(129, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public java.lang.String getApplicationRestrictionsManagingPackage(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(130, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isCallerApplicationRestrictionsManagingPackage(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(131, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setRestrictionsProvider(android.content.ComponentName componentName, android.content.ComponentName componentName2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(componentName2, 0);
                    this.mRemote.transact(132, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public android.content.ComponentName getRestrictionsProvider(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(133, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.ComponentName) obtain2.readTypedObject(android.content.ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setUserRestriction(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2, boolean z, boolean z2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(134, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setUserRestrictionGlobally(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(135, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public android.os.Bundle getUserRestrictions(android.content.ComponentName componentName, java.lang.String str, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(136, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.Bundle) obtain2.readTypedObject(android.os.Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public android.os.Bundle getUserRestrictionsGlobally(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(137, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.Bundle) obtain2.readTypedObject(android.os.Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void addCrossProfileIntentFilter(android.content.ComponentName componentName, java.lang.String str, android.content.IntentFilter intentFilter, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(intentFilter, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(138, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void clearCrossProfileIntentFilters(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(139, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setPermittedAccessibilityServices(android.content.ComponentName componentName, java.util.List<java.lang.String> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(140, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public java.util.List<java.lang.String> getPermittedAccessibilityServices(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(141, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public java.util.List<java.lang.String> getPermittedAccessibilityServicesForUser(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(142, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isAccessibilityServicePermittedByAdmin(android.content.ComponentName componentName, java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(143, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setPermittedInputMethods(android.content.ComponentName componentName, java.lang.String str, java.util.List<java.lang.String> list, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(144, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public java.util.List<java.lang.String> getPermittedInputMethods(android.content.ComponentName componentName, java.lang.String str, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(145, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public java.util.List<java.lang.String> getPermittedInputMethodsAsUser(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(146, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isInputMethodPermittedByAdmin(android.content.ComponentName componentName, java.lang.String str, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(147, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setPermittedCrossProfileNotificationListeners(android.content.ComponentName componentName, java.util.List<java.lang.String> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(148, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public java.util.List<java.lang.String> getPermittedCrossProfileNotificationListeners(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(149, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isNotificationListenerServicePermitted(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(150, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public android.content.Intent createAdminSupportIntent(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(151, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.Intent) obtain2.readTypedObject(android.content.Intent.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public android.os.Bundle getEnforcingAdminAndUserDetails(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(152, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.Bundle) obtain2.readTypedObject(android.os.Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public java.util.List<android.app.admin.EnforcingAdmin> getEnforcingAdminsForRestriction(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(153, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.app.admin.EnforcingAdmin.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setApplicationHidden(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2, boolean z, boolean z2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(154, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isApplicationHidden(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(155, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public android.os.UserHandle createAndManageUser(android.content.ComponentName componentName, java.lang.String str, android.content.ComponentName componentName2, android.os.PersistableBundle persistableBundle, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(componentName2, 0);
                    obtain.writeTypedObject(persistableBundle, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(156, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.UserHandle) obtain2.readTypedObject(android.os.UserHandle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean removeUser(android.content.ComponentName componentName, android.os.UserHandle userHandle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(157, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean switchUser(android.content.ComponentName componentName, android.os.UserHandle userHandle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(158, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int startUserInBackground(android.content.ComponentName componentName, android.os.UserHandle userHandle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(159, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int stopUser(android.content.ComponentName componentName, android.os.UserHandle userHandle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(160, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int logoutUser(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(161, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int logoutUserInternal() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(162, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getLogoutUserId() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(163, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public java.util.List<android.os.UserHandle> getSecondaryUsers(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(164, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.os.UserHandle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void acknowledgeNewUserDisclaimer(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(165, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isNewUserDisclaimerAcknowledged(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(166, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void enableSystemApp(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(167, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int enableSystemAppWithIntent(android.content.ComponentName componentName, java.lang.String str, android.content.Intent intent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(intent, 0);
                    this.mRemote.transact(168, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean installExistingPackage(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(169, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setAccountManagementDisabled(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2, boolean z, boolean z2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(170, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public java.lang.String[] getAccountTypesWithManagementDisabled(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(171, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public java.lang.String[] getAccountTypesWithManagementDisabledAsUser(int i, java.lang.String str, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(172, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setSecondaryLockscreenEnabled(android.content.ComponentName componentName, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(173, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isSecondaryLockscreenEnabled(android.os.UserHandle userHandle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(174, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPreferentialNetworkServiceConfigs(java.util.List<android.app.admin.PreferentialNetworkServiceConfig> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(175, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public java.util.List<android.app.admin.PreferentialNetworkServiceConfig> getPreferentialNetworkServiceConfigs() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(176, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.app.admin.PreferentialNetworkServiceConfig.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setLockTaskPackages(android.content.ComponentName componentName, java.lang.String str, java.lang.String[] strArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(177, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public java.lang.String[] getLockTaskPackages(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(178, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isLockTaskPermitted(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(179, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setLockTaskFeatures(android.content.ComponentName componentName, java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(180, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getLockTaskFeatures(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(181, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setGlobalSetting(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(182, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setSystemSetting(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(183, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setSecureSetting(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(184, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setConfiguredNetworksLockdownState(android.content.ComponentName componentName, java.lang.String str, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(185, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean hasLockdownAdminConfiguredNetworks(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(186, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setLocationEnabled(android.content.ComponentName componentName, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(187, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setTime(android.content.ComponentName componentName, java.lang.String str, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(188, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setTimeZone(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(189, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setMasterVolumeMuted(android.content.ComponentName componentName, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(190, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isMasterVolumeMuted(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(191, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void notifyLockTaskModeChanged(boolean z, java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(192, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setUninstallBlocked(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(193, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isUninstallBlocked(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(194, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setCrossProfileCallerIdDisabled(android.content.ComponentName componentName, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(195, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean getCrossProfileCallerIdDisabled(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(196, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean getCrossProfileCallerIdDisabledForUser(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(197, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setCrossProfileContactsSearchDisabled(android.content.ComponentName componentName, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(198, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean getCrossProfileContactsSearchDisabled(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(199, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean getCrossProfileContactsSearchDisabledForUser(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(200, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void startManagedQuickContact(java.lang.String str, long j, boolean z, long j2, android.content.Intent intent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeBoolean(z);
                    obtain.writeLong(j2);
                    obtain.writeTypedObject(intent, 0);
                    this.mRemote.transact(201, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setManagedProfileCallerIdAccessPolicy(android.app.admin.PackagePolicy packagePolicy) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(packagePolicy, 0);
                    this.mRemote.transact(202, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public android.app.admin.PackagePolicy getManagedProfileCallerIdAccessPolicy() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(203, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.admin.PackagePolicy) obtain2.readTypedObject(android.app.admin.PackagePolicy.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean hasManagedProfileCallerIdAccess(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(204, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setCredentialManagerPolicy(android.app.admin.PackagePolicy packagePolicy) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(packagePolicy, 0);
                    this.mRemote.transact(205, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public android.app.admin.PackagePolicy getCredentialManagerPolicy(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(206, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.admin.PackagePolicy) obtain2.readTypedObject(android.app.admin.PackagePolicy.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setManagedProfileContactsAccessPolicy(android.app.admin.PackagePolicy packagePolicy) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(packagePolicy, 0);
                    this.mRemote.transact(207, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public android.app.admin.PackagePolicy getManagedProfileContactsAccessPolicy() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(208, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.admin.PackagePolicy) obtain2.readTypedObject(android.app.admin.PackagePolicy.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean hasManagedProfileContactsAccess(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(209, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setBluetoothContactSharingDisabled(android.content.ComponentName componentName, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(210, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean getBluetoothContactSharingDisabled(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(211, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean getBluetoothContactSharingDisabledForUser(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(212, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setTrustAgentConfiguration(android.content.ComponentName componentName, java.lang.String str, android.content.ComponentName componentName2, android.os.PersistableBundle persistableBundle, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(componentName2, 0);
                    obtain.writeTypedObject(persistableBundle, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(213, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public java.util.List<android.os.PersistableBundle> getTrustAgentConfiguration(android.content.ComponentName componentName, android.content.ComponentName componentName2, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(componentName2, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(214, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.os.PersistableBundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean addCrossProfileWidgetProvider(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(215, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean removeCrossProfileWidgetProvider(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(216, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public java.util.List<java.lang.String> getCrossProfileWidgetProviders(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(217, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setAutoTimeRequired(android.content.ComponentName componentName, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(218, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean getAutoTimeRequired() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(219, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setAutoTimeEnabled(android.content.ComponentName componentName, java.lang.String str, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(220, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean getAutoTimeEnabled(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(221, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setAutoTimeZoneEnabled(android.content.ComponentName componentName, java.lang.String str, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(222, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean getAutoTimeZoneEnabled(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(223, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setForceEphemeralUsers(android.content.ComponentName componentName, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(224, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean getForceEphemeralUsers(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(225, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isRemovingAdmin(android.content.ComponentName componentName, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(226, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setUserIcon(android.content.ComponentName componentName, android.graphics.Bitmap bitmap) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(bitmap, 0);
                    this.mRemote.transact(227, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setSystemUpdatePolicy(android.content.ComponentName componentName, java.lang.String str, android.app.admin.SystemUpdatePolicy systemUpdatePolicy) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(systemUpdatePolicy, 0);
                    this.mRemote.transact(228, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public android.app.admin.SystemUpdatePolicy getSystemUpdatePolicy() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(229, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.admin.SystemUpdatePolicy) obtain2.readTypedObject(android.app.admin.SystemUpdatePolicy.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void clearSystemUpdatePolicyFreezePeriodRecord() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(230, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setKeyguardDisabled(android.content.ComponentName componentName, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(231, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setStatusBarDisabled(android.content.ComponentName componentName, java.lang.String str, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(232, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isStatusBarDisabled(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(233, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean getDoNotAskCredentialsOnBoot() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(234, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void notifyPendingSystemUpdate(android.app.admin.SystemUpdateInfo systemUpdateInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(systemUpdateInfo, 0);
                    this.mRemote.transact(235, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public android.app.admin.SystemUpdateInfo getPendingSystemUpdate(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(236, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.admin.SystemUpdateInfo) obtain2.readTypedObject(android.app.admin.SystemUpdateInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPermissionPolicy(android.content.ComponentName componentName, java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(237, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getPermissionPolicy(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(238, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPermissionGrantState(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2, java.lang.String str3, int i, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(239, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getPermissionGrantState(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(240, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isProvisioningAllowed(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(241, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int checkProvisioningPrecondition(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(242, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setKeepUninstalledPackages(android.content.ComponentName componentName, java.lang.String str, java.util.List<java.lang.String> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    this.mRemote.transact(243, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public java.util.List<java.lang.String> getKeepUninstalledPackages(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(244, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isManagedProfile(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(245, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public java.lang.String getWifiMacAddress(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(246, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void reboot(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(247, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setShortSupportMessage(android.content.ComponentName componentName, java.lang.String str, java.lang.CharSequence charSequence) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        android.text.TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(248, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public java.lang.CharSequence getShortSupportMessage(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(249, obtain, obtain2, 0);
                    obtain2.readException();
                    return (java.lang.CharSequence) obtain2.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setLongSupportMessage(android.content.ComponentName componentName, java.lang.CharSequence charSequence) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        android.text.TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(250, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public java.lang.CharSequence getLongSupportMessage(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(251, obtain, obtain2, 0);
                    obtain2.readException();
                    return (java.lang.CharSequence) obtain2.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public java.lang.CharSequence getShortSupportMessageForUser(android.content.ComponentName componentName, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(252, obtain, obtain2, 0);
                    obtain2.readException();
                    return (java.lang.CharSequence) obtain2.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public java.lang.CharSequence getLongSupportMessageForUser(android.content.ComponentName componentName, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(253, obtain, obtain2, 0);
                    obtain2.readException();
                    return (java.lang.CharSequence) obtain2.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setOrganizationColor(android.content.ComponentName componentName, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(254, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setOrganizationColorForUser(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(255, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void clearOrganizationIdForUser(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(256, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getOrganizationColor(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(257, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getOrganizationColorForUser(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(258, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setOrganizationName(android.content.ComponentName componentName, java.lang.String str, java.lang.CharSequence charSequence) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        android.text.TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(259, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public java.lang.CharSequence getOrganizationName(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(260, obtain, obtain2, 0);
                    obtain2.readException();
                    return (java.lang.CharSequence) obtain2.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public java.lang.CharSequence getDeviceOwnerOrganizationName() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(261, obtain, obtain2, 0);
                    obtain2.readException();
                    return (java.lang.CharSequence) obtain2.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public java.lang.CharSequence getOrganizationNameForUser(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(262, obtain, obtain2, 0);
                    obtain2.readException();
                    return (java.lang.CharSequence) obtain2.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getUserProvisioningState(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(263, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setUserProvisioningState(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(264, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setAffiliationIds(android.content.ComponentName componentName, java.util.List<java.lang.String> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(265, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public java.util.List<java.lang.String> getAffiliationIds(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(266, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isCallingUserAffiliated() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(267, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isAffiliatedUser(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(268, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setSecurityLoggingEnabled(android.content.ComponentName componentName, java.lang.String str, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(269, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isSecurityLoggingEnabled(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(270, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public android.content.pm.ParceledListSlice retrieveSecurityLogs(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(271, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public android.content.pm.ParceledListSlice retrievePreRebootSecurityLogs(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(272, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public long forceNetworkLogs() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(273, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public long forceSecurityLogs() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(274, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setAuditLogEnabled(java.lang.String str, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(275, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isAuditLogEnabled(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(276, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setAuditLogEventsCallback(java.lang.String str, android.app.admin.IAuditLogEventsCallback iAuditLogEventsCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iAuditLogEventsCallback);
                    this.mRemote.transact(277, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isUninstallInQueue(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(278, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void uninstallPackageWithActiveAdmins(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(279, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isDeviceProvisioned() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(280, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isDeviceProvisioningConfigApplied() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(281, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setDeviceProvisioningConfigApplied() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(282, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void forceUpdateUserSetupComplete(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(283, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setBackupServiceEnabled(android.content.ComponentName componentName, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(284, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isBackupServiceEnabled(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(285, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setNetworkLoggingEnabled(android.content.ComponentName componentName, java.lang.String str, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(286, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isNetworkLoggingEnabled(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(287, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public java.util.List<android.app.admin.NetworkEvent> retrieveNetworkLogs(android.content.ComponentName componentName, java.lang.String str, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(288, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.app.admin.NetworkEvent.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean bindDeviceAdminServiceAsUser(android.content.ComponentName componentName, android.app.IApplicationThread iApplicationThread, android.os.IBinder iBinder, android.content.Intent intent, android.app.IServiceConnection iServiceConnection, long j, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeStrongInterface(iServiceConnection);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(289, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public java.util.List<android.os.UserHandle> getBindDeviceAdminTargetUsers(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(290, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.os.UserHandle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isEphemeralUser(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(291, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public long getLastSecurityLogRetrievalTime() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(292, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public long getLastBugReportRequestTime() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(293, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public long getLastNetworkLogRetrievalTime() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(294, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setResetPasswordToken(android.content.ComponentName componentName, java.lang.String str, byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(295, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean clearResetPasswordToken(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(296, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isResetPasswordTokenActive(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(297, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean resetPasswordWithToken(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2, byte[] bArr, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(298, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isCurrentInputMethodSetByOwner() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(299, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public android.content.pm.StringParceledListSlice getOwnerInstalledCaCerts(android.os.UserHandle userHandle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(300, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.StringParceledListSlice) obtain2.readTypedObject(android.content.pm.StringParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void clearApplicationUserData(android.content.ComponentName componentName, java.lang.String str, android.content.pm.IPackageDataObserver iPackageDataObserver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iPackageDataObserver);
                    this.mRemote.transact(301, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setLogoutEnabled(android.content.ComponentName componentName, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(302, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isLogoutEnabled() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(303, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public java.util.List<java.lang.String> getDisallowedSystemApps(android.content.ComponentName componentName, int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(304, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void transferOwnership(android.content.ComponentName componentName, android.content.ComponentName componentName2, android.os.PersistableBundle persistableBundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(componentName2, 0);
                    obtain.writeTypedObject(persistableBundle, 0);
                    this.mRemote.transact(305, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public android.os.PersistableBundle getTransferOwnershipBundle() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(306, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.PersistableBundle) obtain2.readTypedObject(android.os.PersistableBundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setStartUserSessionMessage(android.content.ComponentName componentName, java.lang.CharSequence charSequence) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        android.text.TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(307, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setEndUserSessionMessage(android.content.ComponentName componentName, java.lang.CharSequence charSequence) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        android.text.TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(308, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public java.lang.CharSequence getStartUserSessionMessage(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(309, obtain, obtain2, 0);
                    obtain2.readException();
                    return (java.lang.CharSequence) obtain2.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public java.lang.CharSequence getEndUserSessionMessage(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(310, obtain, obtain2, 0);
                    obtain2.readException();
                    return (java.lang.CharSequence) obtain2.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public java.util.List<java.lang.String> setMeteredDataDisabledPackages(android.content.ComponentName componentName, java.util.List<java.lang.String> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(311, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public java.util.List<java.lang.String> getMeteredDataDisabledPackages(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(312, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int addOverrideApn(android.content.ComponentName componentName, android.telephony.data.ApnSetting apnSetting) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(apnSetting, 0);
                    this.mRemote.transact(313, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean updateOverrideApn(android.content.ComponentName componentName, int i, android.telephony.data.ApnSetting apnSetting) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(apnSetting, 0);
                    this.mRemote.transact(314, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean removeOverrideApn(android.content.ComponentName componentName, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(315, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public java.util.List<android.telephony.data.ApnSetting> getOverrideApns(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(316, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.telephony.data.ApnSetting.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setOverrideApnsEnabled(android.content.ComponentName componentName, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(317, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isOverrideApnEnabled(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(318, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isMeteredDataDisabledPackageForUser(android.content.ComponentName componentName, java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(319, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int setGlobalPrivateDns(android.content.ComponentName componentName, int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
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

            @Override // android.app.admin.IDevicePolicyManager
            public int getGlobalPrivateDnsMode(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(321, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public java.lang.String getGlobalPrivateDnsHost(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(322, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setProfileOwnerOnOrganizationOwnedDevice(android.content.ComponentName componentName, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(323, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void installUpdateFromFile(android.content.ComponentName componentName, java.lang.String str, android.os.ParcelFileDescriptor parcelFileDescriptor, android.app.admin.StartInstallingUpdateCallback startInstallingUpdateCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeStrongInterface(startInstallingUpdateCallback);
                    this.mRemote.transact(324, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setCrossProfileCalendarPackages(android.content.ComponentName componentName, java.util.List<java.lang.String> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(325, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public java.util.List<java.lang.String> getCrossProfileCalendarPackages(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(326, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isPackageAllowedToAccessCalendarForUser(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(327, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public java.util.List<java.lang.String> getCrossProfileCalendarPackagesForUser(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(328, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setCrossProfilePackages(android.content.ComponentName componentName, java.util.List<java.lang.String> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(329, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public java.util.List<java.lang.String> getCrossProfilePackages(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(330, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public java.util.List<java.lang.String> getAllCrossProfilePackages(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(331, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public java.util.List<java.lang.String> getDefaultCrossProfilePackages() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(332, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isManagedKiosk() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(333, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isUnattendedManagedKiosk() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(334, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean startViewCalendarEventInManagedProfile(java.lang.String str, long j, long j2, long j3, boolean z, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    obtain.writeLong(j3);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(335, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setKeyGrantForApp(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2, java.lang.String str3, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(336, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public android.app.admin.ParcelableGranteeMap getKeyPairGrants(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(337, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.admin.ParcelableGranteeMap) obtain2.readTypedObject(android.app.admin.ParcelableGranteeMap.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean setKeyGrantToWifiAuth(java.lang.String str, java.lang.String str2, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(338, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isKeyPairGrantedToWifiAuth(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(339, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setUserControlDisabledPackages(android.content.ComponentName componentName, java.lang.String str, java.util.List<java.lang.String> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    this.mRemote.transact(340, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public java.util.List<java.lang.String> getUserControlDisabledPackages(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(341, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setCommonCriteriaModeEnabled(android.content.ComponentName componentName, java.lang.String str, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(342, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isCommonCriteriaModeEnabled(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(343, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getPersonalAppsSuspendedReasons(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(344, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setPersonalAppsSuspended(android.content.ComponentName componentName, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(345, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public long getManagedProfileMaximumTimeOff(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(346, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setManagedProfileMaximumTimeOff(android.content.ComponentName componentName, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeLong(j);
                    this.mRemote.transact(347, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void acknowledgeDeviceCompliant() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(348, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isComplianceAcknowledgementRequired() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(349, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean canProfileOwnerResetPasswordWhenLocked(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(350, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setNextOperationSafety(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(351, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isSafeOperation(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(352, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public java.lang.String getEnrollmentSpecificId(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(353, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setOrganizationIdForUser(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(354, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public android.os.UserHandle createAndProvisionManagedProfile(android.app.admin.ManagedProfileProvisioningParams managedProfileProvisioningParams, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(managedProfileProvisioningParams, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(355, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.UserHandle) obtain2.readTypedObject(android.os.UserHandle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void provisionFullyManagedDevice(android.app.admin.FullyManagedDeviceProvisioningParams fullyManagedDeviceProvisioningParams, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(fullyManagedDeviceProvisioningParams, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(356, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void finalizeWorkProfileProvisioning(android.os.UserHandle userHandle, android.accounts.Account account) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeTypedObject(account, 0);
                    this.mRemote.transact(357, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setDeviceOwnerType(android.content.ComponentName componentName, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(358, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getDeviceOwnerType(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(359, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void resetDefaultCrossProfileIntentFilters(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(360, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean canAdminGrantSensorsPermissions() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(361, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setUsbDataSignalingEnabled(java.lang.String str, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(362, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isUsbDataSignalingEnabled(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(363, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean canUsbDataSignalingBeDisabled() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(364, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setMinimumRequiredWifiSecurityLevel(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(365, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getMinimumRequiredWifiSecurityLevel() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(366, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setWifiSsidPolicy(java.lang.String str, android.app.admin.WifiSsidPolicy wifiSsidPolicy) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(wifiSsidPolicy, 0);
                    this.mRemote.transact(367, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public android.app.admin.WifiSsidPolicy getWifiSsidPolicy(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(368, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.admin.WifiSsidPolicy) obtain2.readTypedObject(android.app.admin.WifiSsidPolicy.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isTheftDetectionTriggered(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(369, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public java.util.List<android.os.UserHandle> listForegroundAffiliatedUsers() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(370, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.os.UserHandle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setDrawables(java.util.List<android.app.admin.DevicePolicyDrawableResource> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(371, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void resetDrawables(java.util.List<java.lang.String> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeStringList(list);
                    this.mRemote.transact(372, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public android.app.admin.ParcelableResource getDrawable(java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(373, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.admin.ParcelableResource) obtain2.readTypedObject(android.app.admin.ParcelableResource.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isDpcDownloaded() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(374, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setDpcDownloaded(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(375, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setStrings(java.util.List<android.app.admin.DevicePolicyStringResource> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(376, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void resetStrings(java.util.List<java.lang.String> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeStringList(list);
                    this.mRemote.transact(377, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public android.app.admin.ParcelableResource getString(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(378, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.admin.ParcelableResource) obtain2.readTypedObject(android.app.admin.ParcelableResource.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void resetShouldAllowBypassingDevicePolicyManagementRoleQualificationState() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(379, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean shouldAllowBypassingDevicePolicyManagementRoleQualification() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(380, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public java.util.List<android.os.UserHandle> getPolicyManagedProfiles(android.os.UserHandle userHandle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(381, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.os.UserHandle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setApplicationExemptions(java.lang.String str, java.lang.String str2, int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(382, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int[] getApplicationExemptions(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(383, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setMtePolicy(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(384, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getMtePolicy(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(385, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setManagedSubscriptionsPolicy(android.app.admin.ManagedSubscriptionsPolicy managedSubscriptionsPolicy) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(managedSubscriptionsPolicy, 0);
                    this.mRemote.transact(386, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public android.app.admin.ManagedSubscriptionsPolicy getManagedSubscriptionsPolicy() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(387, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.admin.ManagedSubscriptionsPolicy) obtain2.readTypedObject(android.app.admin.ManagedSubscriptionsPolicy.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public android.app.admin.DevicePolicyState getDevicePolicyState() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(388, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.admin.DevicePolicyState) obtain2.readTypedObject(android.app.admin.DevicePolicyState.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean triggerDevicePolicyEngineMigration(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(389, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean isDeviceFinanced(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(390, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public java.lang.String getFinancedDeviceKioskRoleHolder(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(391, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void calculateHasIncompatibleAccounts() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(392, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setContentProtectionPolicy(android.content.ComponentName componentName, java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(393, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getContentProtectionPolicy(android.content.ComponentName componentName, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(394, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int[] getSubscriptionIds(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(395, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public void setMaxPolicyStorageLimit(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(396, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public int getMaxPolicyStorageLimit(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(397, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IDevicePolicyManager
            public boolean requireSecureKeyguard(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.admin.IDevicePolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(398, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        private boolean onTransact$setPasswordMinimumLength$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setPasswordMinimumLength(componentName, readInt, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getPasswordMinimumLength$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            int passwordMinimumLength = getPasswordMinimumLength(componentName, readInt, readBoolean);
            parcel2.writeNoException();
            parcel2.writeInt(passwordMinimumLength);
            return true;
        }

        private boolean onTransact$setPasswordMinimumUpperCase$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setPasswordMinimumUpperCase(componentName, readInt, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getPasswordMinimumUpperCase$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            int passwordMinimumUpperCase = getPasswordMinimumUpperCase(componentName, readInt, readBoolean);
            parcel2.writeNoException();
            parcel2.writeInt(passwordMinimumUpperCase);
            return true;
        }

        private boolean onTransact$setPasswordMinimumLowerCase$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setPasswordMinimumLowerCase(componentName, readInt, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getPasswordMinimumLowerCase$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            int passwordMinimumLowerCase = getPasswordMinimumLowerCase(componentName, readInt, readBoolean);
            parcel2.writeNoException();
            parcel2.writeInt(passwordMinimumLowerCase);
            return true;
        }

        private boolean onTransact$setPasswordMinimumLetters$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setPasswordMinimumLetters(componentName, readInt, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getPasswordMinimumLetters$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            int passwordMinimumLetters = getPasswordMinimumLetters(componentName, readInt, readBoolean);
            parcel2.writeNoException();
            parcel2.writeInt(passwordMinimumLetters);
            return true;
        }

        private boolean onTransact$setPasswordMinimumNumeric$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setPasswordMinimumNumeric(componentName, readInt, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getPasswordMinimumNumeric$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            int passwordMinimumNumeric = getPasswordMinimumNumeric(componentName, readInt, readBoolean);
            parcel2.writeNoException();
            parcel2.writeInt(passwordMinimumNumeric);
            return true;
        }

        private boolean onTransact$setPasswordMinimumSymbols$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setPasswordMinimumSymbols(componentName, readInt, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getPasswordMinimumSymbols$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            int passwordMinimumSymbols = getPasswordMinimumSymbols(componentName, readInt, readBoolean);
            parcel2.writeNoException();
            parcel2.writeInt(passwordMinimumSymbols);
            return true;
        }

        private boolean onTransact$setPasswordMinimumNonLetter$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setPasswordMinimumNonLetter(componentName, readInt, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getPasswordMinimumNonLetter$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            int passwordMinimumNonLetter = getPasswordMinimumNonLetter(componentName, readInt, readBoolean);
            parcel2.writeNoException();
            parcel2.writeInt(passwordMinimumNonLetter);
            return true;
        }

        private boolean onTransact$setPasswordHistoryLength$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setPasswordHistoryLength(componentName, readInt, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getPasswordHistoryLength$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            int passwordHistoryLength = getPasswordHistoryLength(componentName, readInt, readBoolean);
            parcel2.writeNoException();
            parcel2.writeInt(passwordHistoryLength);
            return true;
        }

        private boolean onTransact$setPasswordExpirationTimeout$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            long readLong = parcel.readLong();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setPasswordExpirationTimeout(componentName, readString, readLong, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getPasswordExpirationTimeout$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            long passwordExpirationTimeout = getPasswordExpirationTimeout(componentName, readInt, readBoolean);
            parcel2.writeNoException();
            parcel2.writeLong(passwordExpirationTimeout);
            return true;
        }

        private boolean onTransact$getPasswordExpiration$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            long passwordExpiration = getPasswordExpiration(componentName, readInt, readBoolean);
            parcel2.writeNoException();
            parcel2.writeLong(passwordExpiration);
            return true;
        }

        private boolean onTransact$isActivePasswordSufficient$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            java.lang.String readString = parcel.readString();
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            boolean isActivePasswordSufficient = isActivePasswordSufficient(readString, readInt, readBoolean);
            parcel2.writeNoException();
            parcel2.writeBoolean(isActivePasswordSufficient);
            return true;
        }

        private boolean onTransact$setRequiredPasswordComplexity$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            java.lang.String readString = parcel.readString();
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setRequiredPasswordComplexity(readString, readInt, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getCurrentFailedPasswordAttempts$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            java.lang.String readString = parcel.readString();
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            int currentFailedPasswordAttempts = getCurrentFailedPasswordAttempts(readString, readInt, readBoolean);
            parcel2.writeNoException();
            parcel2.writeInt(currentFailedPasswordAttempts);
            return true;
        }

        private boolean onTransact$setMaximumFailedPasswordsForWipe$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setMaximumFailedPasswordsForWipe(componentName, readString, readInt, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getMaximumFailedPasswordsForWipe$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            int maximumFailedPasswordsForWipe = getMaximumFailedPasswordsForWipe(componentName, readInt, readBoolean);
            parcel2.writeNoException();
            parcel2.writeInt(maximumFailedPasswordsForWipe);
            return true;
        }

        private boolean onTransact$setMaximumTimeToLock$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            long readLong = parcel.readLong();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setMaximumTimeToLock(componentName, readString, readLong, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getMaximumTimeToLock$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            long maximumTimeToLock = getMaximumTimeToLock(componentName, readInt, readBoolean);
            parcel2.writeNoException();
            parcel2.writeLong(maximumTimeToLock);
            return true;
        }

        private boolean onTransact$setRequiredStrongAuthTimeout$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            long readLong = parcel.readLong();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setRequiredStrongAuthTimeout(componentName, readString, readLong, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getRequiredStrongAuthTimeout$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            long requiredStrongAuthTimeout = getRequiredStrongAuthTimeout(componentName, readInt, readBoolean);
            parcel2.writeNoException();
            parcel2.writeLong(requiredStrongAuthTimeout);
            return true;
        }

        private boolean onTransact$lockNow$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            java.lang.String readString = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            lockNow(readInt, readString, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$wipeDataWithReason$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            java.lang.String readString = parcel.readString();
            int readInt = parcel.readInt();
            java.lang.String readString2 = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            boolean readBoolean2 = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            wipeDataWithReason(readString, readInt, readString2, readBoolean, readBoolean2);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setFactoryResetProtectionPolicy$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            android.app.admin.FactoryResetProtectionPolicy factoryResetProtectionPolicy = (android.app.admin.FactoryResetProtectionPolicy) parcel.readTypedObject(android.app.admin.FactoryResetProtectionPolicy.CREATOR);
            parcel.enforceNoDataAvail();
            setFactoryResetProtectionPolicy(componentName, readString, factoryResetProtectionPolicy);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setGlobalProxy$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            android.content.ComponentName globalProxy = setGlobalProxy(componentName, readString, readString2);
            parcel2.writeNoException();
            parcel2.writeTypedObject(globalProxy, 1);
            return true;
        }

        private boolean onTransact$setCameraDisabled$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            boolean readBoolean2 = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setCameraDisabled(componentName, readString, readBoolean, readBoolean2);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getCameraDisabled$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            boolean cameraDisabled = getCameraDisabled(componentName, readString, readInt, readBoolean);
            parcel2.writeNoException();
            parcel2.writeBoolean(cameraDisabled);
            return true;
        }

        private boolean onTransact$setScreenCaptureDisabled$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            boolean readBoolean2 = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setScreenCaptureDisabled(componentName, readString, readBoolean, readBoolean2);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getScreenCaptureDisabled$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            boolean screenCaptureDisabled = getScreenCaptureDisabled(componentName, readInt, readBoolean);
            parcel2.writeNoException();
            parcel2.writeBoolean(screenCaptureDisabled);
            return true;
        }

        private boolean onTransact$setKeyguardDisabledFeatures$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setKeyguardDisabledFeatures(componentName, readString, readInt, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getKeyguardDisabledFeatures$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            int keyguardDisabledFeatures = getKeyguardDisabledFeatures(componentName, readInt, readBoolean);
            parcel2.writeNoException();
            parcel2.writeInt(keyguardDisabledFeatures);
            return true;
        }

        private boolean onTransact$setActiveAdmin$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            boolean readBoolean = parcel.readBoolean();
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            setActiveAdmin(componentName, readBoolean, readInt);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getRemoveWarning$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            android.os.RemoteCallback remoteCallback = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            getRemoveWarning(componentName, remoteCallback, readInt);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$hasGrantedPolicy$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean hasGrantedPolicy = hasGrantedPolicy(componentName, readInt, readInt2);
            parcel2.writeNoException();
            parcel2.writeBoolean(hasGrantedPolicy);
            return true;
        }

        private boolean onTransact$setDeviceOwner$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            boolean deviceOwner = setDeviceOwner(componentName, readInt, readBoolean);
            parcel2.writeNoException();
            parcel2.writeBoolean(deviceOwner);
            return true;
        }

        private boolean onTransact$checkDeviceIdentifierAccess$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            java.lang.String readString = parcel.readString();
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean checkDeviceIdentifierAccess = checkDeviceIdentifierAccess(readString, readInt, readInt2);
            parcel2.writeNoException();
            parcel2.writeBoolean(checkDeviceIdentifierAccess);
            return true;
        }

        private boolean onTransact$setPackagesSuspended$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            java.lang.String[] createStringArray = parcel.createStringArray();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            java.lang.String[] packagesSuspended = setPackagesSuspended(componentName, readString, createStringArray, readBoolean);
            parcel2.writeNoException();
            parcel2.writeStringArray(packagesSuspended);
            return true;
        }

        private boolean onTransact$isPackageSuspended$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            boolean isPackageSuspended = isPackageSuspended(componentName, readString, readString2);
            parcel2.writeNoException();
            parcel2.writeBoolean(isPackageSuspended);
            return true;
        }

        private boolean onTransact$installCaCert$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            byte[] createByteArray = parcel.createByteArray();
            parcel.enforceNoDataAvail();
            boolean installCaCert = installCaCert(componentName, readString, createByteArray);
            parcel2.writeNoException();
            parcel2.writeBoolean(installCaCert);
            return true;
        }

        private boolean onTransact$uninstallCaCerts$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            java.lang.String[] createStringArray = parcel.createStringArray();
            parcel.enforceNoDataAvail();
            uninstallCaCerts(componentName, readString, createStringArray);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$approveCaCert$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            java.lang.String readString = parcel.readString();
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            boolean approveCaCert = approveCaCert(readString, readInt, readBoolean);
            parcel2.writeNoException();
            parcel2.writeBoolean(approveCaCert);
            return true;
        }

        private boolean onTransact$installKeyPair$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            byte[] createByteArray = parcel.createByteArray();
            byte[] createByteArray2 = parcel.createByteArray();
            byte[] createByteArray3 = parcel.createByteArray();
            java.lang.String readString2 = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            boolean readBoolean2 = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            boolean installKeyPair = installKeyPair(componentName, readString, createByteArray, createByteArray2, createByteArray3, readString2, readBoolean, readBoolean2);
            parcel2.writeNoException();
            parcel2.writeBoolean(installKeyPair);
            return true;
        }

        private boolean onTransact$removeKeyPair$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            boolean removeKeyPair = removeKeyPair(componentName, readString, readString2);
            parcel2.writeNoException();
            parcel2.writeBoolean(removeKeyPair);
            return true;
        }

        private boolean onTransact$generateKeyPair$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            android.security.keystore.ParcelableKeyGenParameterSpec parcelableKeyGenParameterSpec = (android.security.keystore.ParcelableKeyGenParameterSpec) parcel.readTypedObject(android.security.keystore.ParcelableKeyGenParameterSpec.CREATOR);
            int readInt = parcel.readInt();
            android.security.keymaster.KeymasterCertificateChain keymasterCertificateChain = new android.security.keymaster.KeymasterCertificateChain();
            parcel.enforceNoDataAvail();
            boolean generateKeyPair = generateKeyPair(componentName, readString, readString2, parcelableKeyGenParameterSpec, readInt, keymasterCertificateChain);
            parcel2.writeNoException();
            parcel2.writeBoolean(generateKeyPair);
            parcel2.writeTypedObject(keymasterCertificateChain, 1);
            return true;
        }

        private boolean onTransact$setKeyPairCertificate$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            byte[] createByteArray = parcel.createByteArray();
            byte[] createByteArray2 = parcel.createByteArray();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            boolean keyPairCertificate = setKeyPairCertificate(componentName, readString, readString2, createByteArray, createByteArray2, readBoolean);
            parcel2.writeNoException();
            parcel2.writeBoolean(keyPairCertificate);
            return true;
        }

        private boolean onTransact$choosePrivateKeyAlias$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            android.net.Uri uri = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
            java.lang.String readString = parcel.readString();
            android.os.IBinder readStrongBinder = parcel.readStrongBinder();
            parcel.enforceNoDataAvail();
            choosePrivateKeyAlias(readInt, uri, readString, readStrongBinder);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setDelegatedScopes$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            java.util.ArrayList<java.lang.String> createStringArrayList = parcel.createStringArrayList();
            parcel.enforceNoDataAvail();
            setDelegatedScopes(componentName, readString, createStringArrayList);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setAlwaysOnVpnPackage$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            java.util.ArrayList<java.lang.String> createStringArrayList = parcel.createStringArrayList();
            parcel.enforceNoDataAvail();
            boolean alwaysOnVpnPackage = setAlwaysOnVpnPackage(componentName, readString, readBoolean, createStringArrayList);
            parcel2.writeNoException();
            parcel2.writeBoolean(alwaysOnVpnPackage);
            return true;
        }

        private boolean onTransact$addPersistentPreferredActivity$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            android.content.IntentFilter intentFilter = (android.content.IntentFilter) parcel.readTypedObject(android.content.IntentFilter.CREATOR);
            android.content.ComponentName componentName2 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            parcel.enforceNoDataAvail();
            addPersistentPreferredActivity(componentName, readString, intentFilter, componentName2);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$clearPackagePersistentPreferredActivities$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            clearPackagePersistentPreferredActivities(componentName, readString, readString2);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setDefaultSmsApplication$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setDefaultSmsApplication(componentName, readString, readString2, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setApplicationRestrictions$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
            parcel.enforceNoDataAvail();
            setApplicationRestrictions(componentName, readString, readString2, bundle);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getApplicationRestrictions$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            android.os.Bundle applicationRestrictions = getApplicationRestrictions(componentName, readString, readString2);
            parcel2.writeNoException();
            parcel2.writeTypedObject(applicationRestrictions, 1);
            return true;
        }

        private boolean onTransact$setUserRestriction$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            boolean readBoolean2 = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setUserRestriction(componentName, readString, readString2, readBoolean, readBoolean2);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getUserRestrictions$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            android.os.Bundle userRestrictions = getUserRestrictions(componentName, readString, readBoolean);
            parcel2.writeNoException();
            parcel2.writeTypedObject(userRestrictions, 1);
            return true;
        }

        private boolean onTransact$addCrossProfileIntentFilter$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            android.content.IntentFilter intentFilter = (android.content.IntentFilter) parcel.readTypedObject(android.content.IntentFilter.CREATOR);
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            addCrossProfileIntentFilter(componentName, readString, intentFilter, readInt);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$isAccessibilityServicePermittedByAdmin$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean isAccessibilityServicePermittedByAdmin = isAccessibilityServicePermittedByAdmin(componentName, readString, readInt);
            parcel2.writeNoException();
            parcel2.writeBoolean(isAccessibilityServicePermittedByAdmin);
            return true;
        }

        private boolean onTransact$setPermittedInputMethods$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            java.util.ArrayList<java.lang.String> createStringArrayList = parcel.createStringArrayList();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            boolean permittedInputMethods = setPermittedInputMethods(componentName, readString, createStringArrayList, readBoolean);
            parcel2.writeNoException();
            parcel2.writeBoolean(permittedInputMethods);
            return true;
        }

        private boolean onTransact$getPermittedInputMethods$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            java.util.List<java.lang.String> permittedInputMethods = getPermittedInputMethods(componentName, readString, readBoolean);
            parcel2.writeNoException();
            parcel2.writeStringList(permittedInputMethods);
            return true;
        }

        private boolean onTransact$isInputMethodPermittedByAdmin$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            boolean isInputMethodPermittedByAdmin = isInputMethodPermittedByAdmin(componentName, readString, readInt, readBoolean);
            parcel2.writeNoException();
            parcel2.writeBoolean(isInputMethodPermittedByAdmin);
            return true;
        }

        private boolean onTransact$setApplicationHidden$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            boolean readBoolean2 = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            boolean applicationHidden = setApplicationHidden(componentName, readString, readString2, readBoolean, readBoolean2);
            parcel2.writeNoException();
            parcel2.writeBoolean(applicationHidden);
            return true;
        }

        private boolean onTransact$isApplicationHidden$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            boolean isApplicationHidden = isApplicationHidden(componentName, readString, readString2, readBoolean);
            parcel2.writeNoException();
            parcel2.writeBoolean(isApplicationHidden);
            return true;
        }

        private boolean onTransact$createAndManageUser$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            android.content.ComponentName componentName2 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            android.os.PersistableBundle persistableBundle = (android.os.PersistableBundle) parcel.readTypedObject(android.os.PersistableBundle.CREATOR);
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            android.os.UserHandle createAndManageUser = createAndManageUser(componentName, readString, componentName2, persistableBundle, readInt);
            parcel2.writeNoException();
            parcel2.writeTypedObject(createAndManageUser, 1);
            return true;
        }

        private boolean onTransact$enableSystemApp$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            enableSystemApp(componentName, readString, readString2);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$enableSystemAppWithIntent$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            android.content.Intent intent = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
            parcel.enforceNoDataAvail();
            int enableSystemAppWithIntent = enableSystemAppWithIntent(componentName, readString, intent);
            parcel2.writeNoException();
            parcel2.writeInt(enableSystemAppWithIntent);
            return true;
        }

        private boolean onTransact$installExistingPackage$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            boolean installExistingPackage = installExistingPackage(componentName, readString, readString2);
            parcel2.writeNoException();
            parcel2.writeBoolean(installExistingPackage);
            return true;
        }

        private boolean onTransact$setAccountManagementDisabled$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            boolean readBoolean2 = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setAccountManagementDisabled(componentName, readString, readString2, readBoolean, readBoolean2);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getAccountTypesWithManagementDisabledAsUser$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            int readInt = parcel.readInt();
            java.lang.String readString = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            java.lang.String[] accountTypesWithManagementDisabledAsUser = getAccountTypesWithManagementDisabledAsUser(readInt, readString, readBoolean);
            parcel2.writeNoException();
            parcel2.writeStringArray(accountTypesWithManagementDisabledAsUser);
            return true;
        }

        private boolean onTransact$setLockTaskPackages$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            java.lang.String[] createStringArray = parcel.createStringArray();
            parcel.enforceNoDataAvail();
            setLockTaskPackages(componentName, readString, createStringArray);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setLockTaskFeatures$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            setLockTaskFeatures(componentName, readString, readInt);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setGlobalSetting$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            setGlobalSetting(componentName, readString, readString2);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setSystemSetting$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            setSystemSetting(componentName, readString, readString2);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setSecureSetting$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            setSecureSetting(componentName, readString, readString2);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setConfiguredNetworksLockdownState$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setConfiguredNetworksLockdownState(componentName, readString, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setTime$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            long readLong = parcel.readLong();
            parcel.enforceNoDataAvail();
            boolean time = setTime(componentName, readString, readLong);
            parcel2.writeNoException();
            parcel2.writeBoolean(time);
            return true;
        }

        private boolean onTransact$setTimeZone$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            boolean timeZone = setTimeZone(componentName, readString, readString2);
            parcel2.writeNoException();
            parcel2.writeBoolean(timeZone);
            return true;
        }

        private boolean onTransact$notifyLockTaskModeChanged$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            boolean readBoolean = parcel.readBoolean();
            java.lang.String readString = parcel.readString();
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            notifyLockTaskModeChanged(readBoolean, readString, readInt);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setUninstallBlocked$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setUninstallBlocked(componentName, readString, readString2, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$startManagedQuickContact$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            java.lang.String readString = parcel.readString();
            long readLong = parcel.readLong();
            boolean readBoolean = parcel.readBoolean();
            long readLong2 = parcel.readLong();
            android.content.Intent intent = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
            parcel.enforceNoDataAvail();
            startManagedQuickContact(readString, readLong, readBoolean, readLong2, intent);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setTrustAgentConfiguration$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            android.content.ComponentName componentName2 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            android.os.PersistableBundle persistableBundle = (android.os.PersistableBundle) parcel.readTypedObject(android.os.PersistableBundle.CREATOR);
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setTrustAgentConfiguration(componentName, readString, componentName2, persistableBundle, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getTrustAgentConfiguration$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            android.content.ComponentName componentName2 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            java.util.List<android.os.PersistableBundle> trustAgentConfiguration = getTrustAgentConfiguration(componentName, componentName2, readInt, readBoolean);
            parcel2.writeNoException();
            parcel2.writeTypedList(trustAgentConfiguration, 1);
            return true;
        }

        private boolean onTransact$addCrossProfileWidgetProvider$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            boolean addCrossProfileWidgetProvider = addCrossProfileWidgetProvider(componentName, readString, readString2);
            parcel2.writeNoException();
            parcel2.writeBoolean(addCrossProfileWidgetProvider);
            return true;
        }

        private boolean onTransact$removeCrossProfileWidgetProvider$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            boolean removeCrossProfileWidgetProvider = removeCrossProfileWidgetProvider(componentName, readString, readString2);
            parcel2.writeNoException();
            parcel2.writeBoolean(removeCrossProfileWidgetProvider);
            return true;
        }

        private boolean onTransact$setAutoTimeEnabled$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setAutoTimeEnabled(componentName, readString, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setAutoTimeZoneEnabled$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setAutoTimeZoneEnabled(componentName, readString, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setSystemUpdatePolicy$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            android.app.admin.SystemUpdatePolicy systemUpdatePolicy = (android.app.admin.SystemUpdatePolicy) parcel.readTypedObject(android.app.admin.SystemUpdatePolicy.CREATOR);
            parcel.enforceNoDataAvail();
            setSystemUpdatePolicy(componentName, readString, systemUpdatePolicy);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setStatusBarDisabled$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            boolean statusBarDisabled = setStatusBarDisabled(componentName, readString, readBoolean);
            parcel2.writeNoException();
            parcel2.writeBoolean(statusBarDisabled);
            return true;
        }

        private boolean onTransact$setPermissionPolicy$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            setPermissionPolicy(componentName, readString, readInt);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setPermissionGrantState$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            java.lang.String readString3 = parcel.readString();
            int readInt = parcel.readInt();
            android.os.RemoteCallback remoteCallback = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
            parcel.enforceNoDataAvail();
            setPermissionGrantState(componentName, readString, readString2, readString3, readInt, remoteCallback);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getPermissionGrantState$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            java.lang.String readString3 = parcel.readString();
            parcel.enforceNoDataAvail();
            int permissionGrantState = getPermissionGrantState(componentName, readString, readString2, readString3);
            parcel2.writeNoException();
            parcel2.writeInt(permissionGrantState);
            return true;
        }

        private boolean onTransact$setKeepUninstalledPackages$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            java.util.ArrayList<java.lang.String> createStringArrayList = parcel.createStringArrayList();
            parcel.enforceNoDataAvail();
            setKeepUninstalledPackages(componentName, readString, createStringArrayList);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setShortSupportMessage$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            java.lang.CharSequence charSequence = (java.lang.CharSequence) parcel.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
            parcel.enforceNoDataAvail();
            setShortSupportMessage(componentName, readString, charSequence);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setOrganizationName$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            java.lang.CharSequence charSequence = (java.lang.CharSequence) parcel.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
            parcel.enforceNoDataAvail();
            setOrganizationName(componentName, readString, charSequence);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setSecurityLoggingEnabled$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setSecurityLoggingEnabled(componentName, readString, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setNetworkLoggingEnabled$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setNetworkLoggingEnabled(componentName, readString, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$retrieveNetworkLogs$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            long readLong = parcel.readLong();
            parcel.enforceNoDataAvail();
            java.util.List<android.app.admin.NetworkEvent> retrieveNetworkLogs = retrieveNetworkLogs(componentName, readString, readLong);
            parcel2.writeNoException();
            parcel2.writeTypedList(retrieveNetworkLogs, 1);
            return true;
        }

        private boolean onTransact$bindDeviceAdminServiceAsUser$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            android.app.IApplicationThread asInterface = android.app.IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
            android.os.IBinder readStrongBinder = parcel.readStrongBinder();
            android.content.Intent intent = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
            android.app.IServiceConnection asInterface2 = android.app.IServiceConnection.Stub.asInterface(parcel.readStrongBinder());
            long readLong = parcel.readLong();
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean bindDeviceAdminServiceAsUser = bindDeviceAdminServiceAsUser(componentName, asInterface, readStrongBinder, intent, asInterface2, readLong, readInt);
            parcel2.writeNoException();
            parcel2.writeBoolean(bindDeviceAdminServiceAsUser);
            return true;
        }

        private boolean onTransact$setResetPasswordToken$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            byte[] createByteArray = parcel.createByteArray();
            parcel.enforceNoDataAvail();
            boolean resetPasswordToken = setResetPasswordToken(componentName, readString, createByteArray);
            parcel2.writeNoException();
            parcel2.writeBoolean(resetPasswordToken);
            return true;
        }

        private boolean onTransact$resetPasswordWithToken$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            byte[] createByteArray = parcel.createByteArray();
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean resetPasswordWithToken = resetPasswordWithToken(componentName, readString, readString2, createByteArray, readInt);
            parcel2.writeNoException();
            parcel2.writeBoolean(resetPasswordWithToken);
            return true;
        }

        private boolean onTransact$clearApplicationUserData$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            android.content.pm.IPackageDataObserver asInterface = android.content.pm.IPackageDataObserver.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            clearApplicationUserData(componentName, readString, asInterface);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getDisallowedSystemApps$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            int readInt = parcel.readInt();
            java.lang.String readString = parcel.readString();
            parcel.enforceNoDataAvail();
            java.util.List<java.lang.String> disallowedSystemApps = getDisallowedSystemApps(componentName, readInt, readString);
            parcel2.writeNoException();
            parcel2.writeStringList(disallowedSystemApps);
            return true;
        }

        private boolean onTransact$transferOwnership$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            android.content.ComponentName componentName2 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            android.os.PersistableBundle persistableBundle = (android.os.PersistableBundle) parcel.readTypedObject(android.os.PersistableBundle.CREATOR);
            parcel.enforceNoDataAvail();
            transferOwnership(componentName, componentName2, persistableBundle);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$updateOverrideApn$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            int readInt = parcel.readInt();
            android.telephony.data.ApnSetting apnSetting = (android.telephony.data.ApnSetting) parcel.readTypedObject(android.telephony.data.ApnSetting.CREATOR);
            parcel.enforceNoDataAvail();
            boolean updateOverrideApn = updateOverrideApn(componentName, readInt, apnSetting);
            parcel2.writeNoException();
            parcel2.writeBoolean(updateOverrideApn);
            return true;
        }

        private boolean onTransact$isMeteredDataDisabledPackageForUser$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean isMeteredDataDisabledPackageForUser = isMeteredDataDisabledPackageForUser(componentName, readString, readInt);
            parcel2.writeNoException();
            parcel2.writeBoolean(isMeteredDataDisabledPackageForUser);
            return true;
        }

        private boolean onTransact$setGlobalPrivateDns$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            int readInt = parcel.readInt();
            java.lang.String readString = parcel.readString();
            parcel.enforceNoDataAvail();
            int globalPrivateDns = setGlobalPrivateDns(componentName, readInt, readString);
            parcel2.writeNoException();
            parcel2.writeInt(globalPrivateDns);
            return true;
        }

        private boolean onTransact$setProfileOwnerOnOrganizationOwnedDevice$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            int readInt = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setProfileOwnerOnOrganizationOwnedDevice(componentName, readInt, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$installUpdateFromFile$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            android.os.ParcelFileDescriptor parcelFileDescriptor = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
            android.app.admin.StartInstallingUpdateCallback asInterface = android.app.admin.StartInstallingUpdateCallback.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            installUpdateFromFile(componentName, readString, parcelFileDescriptor, asInterface);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$startViewCalendarEventInManagedProfile$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            java.lang.String readString = parcel.readString();
            long readLong = parcel.readLong();
            long readLong2 = parcel.readLong();
            long readLong3 = parcel.readLong();
            boolean readBoolean = parcel.readBoolean();
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            boolean startViewCalendarEventInManagedProfile = startViewCalendarEventInManagedProfile(readString, readLong, readLong2, readLong3, readBoolean, readInt);
            parcel2.writeNoException();
            parcel2.writeBoolean(startViewCalendarEventInManagedProfile);
            return true;
        }

        private boolean onTransact$setKeyGrantForApp$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            java.lang.String readString3 = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            boolean keyGrantForApp = setKeyGrantForApp(componentName, readString, readString2, readString3, readBoolean);
            parcel2.writeNoException();
            parcel2.writeBoolean(keyGrantForApp);
            return true;
        }

        private boolean onTransact$setKeyGrantToWifiAuth$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            boolean keyGrantToWifiAuth = setKeyGrantToWifiAuth(readString, readString2, readBoolean);
            parcel2.writeNoException();
            parcel2.writeBoolean(keyGrantToWifiAuth);
            return true;
        }

        private boolean onTransact$setUserControlDisabledPackages$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            java.util.ArrayList<java.lang.String> createStringArrayList = parcel.createStringArrayList();
            parcel.enforceNoDataAvail();
            setUserControlDisabledPackages(componentName, readString, createStringArrayList);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setCommonCriteriaModeEnabled$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            setCommonCriteriaModeEnabled(componentName, readString, readBoolean);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setOrganizationIdForUser$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            setOrganizationIdForUser(readString, readString2, readInt);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$getDrawable$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            java.lang.String readString3 = parcel.readString();
            parcel.enforceNoDataAvail();
            android.app.admin.ParcelableResource drawable = getDrawable(readString, readString2, readString3);
            parcel2.writeNoException();
            parcel2.writeTypedObject(drawable, 1);
            return true;
        }

        private boolean onTransact$setApplicationExemptions$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            int[] createIntArray = parcel.createIntArray();
            parcel.enforceNoDataAvail();
            setApplicationExemptions(readString, readString2, createIntArray);
            parcel2.writeNoException();
            return true;
        }

        private boolean onTransact$setContentProtectionPolicy$(android.os.Parcel parcel, android.os.Parcel parcel2) throws android.os.RemoteException {
            android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
            java.lang.String readString = parcel.readString();
            int readInt = parcel.readInt();
            parcel.enforceNoDataAvail();
            setContentProtectionPolicy(componentName, readString, readInt);
            parcel2.writeNoException();
            return true;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 397;
        }
    }
}
