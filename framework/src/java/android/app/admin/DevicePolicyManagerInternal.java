package android.app.admin;

/* loaded from: classes.dex */
public abstract class DevicePolicyManagerInternal {

    public interface OnCrossProfileWidgetProvidersChangeListener {
        void onCrossProfileWidgetProvidersChanged(int i, java.util.List<java.lang.String> list);
    }

    public abstract void addOnCrossProfileWidgetProvidersChangeListener(android.app.admin.DevicePolicyManagerInternal.OnCrossProfileWidgetProvidersChangeListener onCrossProfileWidgetProvidersChangeListener);

    public abstract void broadcastIntentToManifestReceivers(android.content.Intent intent, android.os.UserHandle userHandle, boolean z);

    public abstract boolean canSilentlyInstallPackage(java.lang.String str, int i);

    public abstract android.content.Intent createShowAdminSupportIntent(int i, boolean z);

    public abstract android.content.Intent createUserRestrictionSupportIntent(int i, java.lang.String str);

    public abstract void enforceAuditLoggingPolicy(boolean z);

    public abstract void enforcePermission(java.lang.String str, java.lang.String str2, int i);

    public abstract void enforceSecurityLoggingPolicy(boolean z);

    public abstract java.util.List<java.lang.String> getAllCrossProfilePackages(int i);

    public abstract java.util.List<android.os.Bundle> getApplicationRestrictionsPerAdminForUser(java.lang.String str, int i);

    public abstract java.util.List<java.lang.String> getCrossProfileWidgetProviders(int i);

    public abstract java.util.List<java.lang.String> getDefaultCrossProfilePackages();

    @java.lang.Deprecated
    public abstract android.content.ComponentName getDeviceOwnerComponent(boolean z);

    public abstract int getDeviceOwnerUserId();

    protected abstract android.app.admin.DevicePolicyCache getDevicePolicyCache();

    protected abstract android.app.admin.DeviceStateCache getDeviceStateCache();

    public abstract java.lang.CharSequence getPrintingDisabledReasonForUser(int i);

    public abstract android.content.ComponentName getProfileOwnerAsUser(int i);

    public abstract android.content.ComponentName getProfileOwnerOrDeviceOwnerSupervisionComponent(android.os.UserHandle userHandle);

    public abstract java.util.List<android.os.UserManager.EnforcingUser> getUserRestrictionSources(java.lang.String str, int i);

    public abstract boolean hasPermission(java.lang.String str, java.lang.String str2, int i);

    public abstract boolean isActiveDeviceOwner(int i);

    public abstract boolean isActiveProfileOwner(int i);

    public abstract boolean isActiveSupervisionApp(int i);

    public abstract boolean isApplicationExemptionsFlagEnabled();

    public abstract boolean isDeviceOrProfileOwnerInCallingUser(java.lang.String str);

    public abstract boolean isUserAffiliatedWithDevice(int i);

    public abstract boolean isUserOrganizationManaged(int i);

    public abstract void reportSeparateProfileChallengeChanged(int i);

    public abstract void resetOp(int i, java.lang.String str, int i2);

    public abstract boolean supportsResetOp(int i);
}
