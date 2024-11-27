package android.content.pm;

/* loaded from: classes.dex */
public class CrossProfileApps {
    public static final java.lang.String ACTION_CAN_INTERACT_ACROSS_PROFILES_CHANGED = "android.content.pm.action.CAN_INTERACT_ACROSS_PROFILES_CHANGED";
    private final android.content.Context mContext;
    private final android.content.res.Resources mResources;
    private final android.content.pm.ICrossProfileApps mService;
    private final android.os.UserManager mUserManager;

    public CrossProfileApps(android.content.Context context, android.content.pm.ICrossProfileApps iCrossProfileApps) {
        this.mContext = context;
        this.mService = iCrossProfileApps;
        this.mUserManager = (android.os.UserManager) context.getSystemService(android.os.UserManager.class);
        this.mResources = context.getResources();
    }

    public void startMainActivity(android.content.ComponentName componentName, android.os.UserHandle userHandle) {
        try {
            this.mService.startActivityAsUser(this.mContext.getIApplicationThread(), this.mContext.getPackageName(), this.mContext.getAttributionTag(), componentName, userHandle.getIdentifier(), true, this.mContext.getActivityToken(), android.app.ActivityOptions.makeBasic().toBundle());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void startMainActivity(android.content.ComponentName componentName, android.os.UserHandle userHandle, android.app.Activity activity, android.os.Bundle bundle) {
        try {
            this.mService.startActivityAsUser(this.mContext.getIApplicationThread(), this.mContext.getPackageName(), this.mContext.getAttributionTag(), componentName, userHandle.getIdentifier(), true, activity != null ? activity.getActivityToken() : null, bundle);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void startActivity(android.content.Intent intent, android.os.UserHandle userHandle, android.app.Activity activity) {
        startActivity(intent, userHandle, activity, (android.os.Bundle) null);
    }

    public void startActivity(android.content.Intent intent, android.os.UserHandle userHandle, android.app.Activity activity, android.os.Bundle bundle) {
        try {
            this.mService.startActivityAsUserByIntent(this.mContext.getIApplicationThread(), this.mContext.getPackageName(), this.mContext.getAttributionTag(), intent, userHandle.getIdentifier(), activity != null ? activity.getActivityToken() : null, bundle);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void startActivity(android.content.ComponentName componentName, android.os.UserHandle userHandle, android.app.Activity activity, android.os.Bundle bundle) {
        try {
            this.mService.startActivityAsUser(this.mContext.getIApplicationThread(), this.mContext.getPackageName(), this.mContext.getAttributionTag(), componentName, userHandle.getIdentifier(), false, activity != null ? activity.getActivityToken() : null, bundle);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void startActivity(android.content.ComponentName componentName, android.os.UserHandle userHandle) {
        try {
            this.mService.startActivityAsUser(this.mContext.getIApplicationThread(), this.mContext.getPackageName(), this.mContext.getAttributionTag(), componentName, userHandle.getIdentifier(), false, null, null);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.os.UserHandle> getTargetUserProfiles() {
        try {
            return this.mService.getTargetUserProfiles(this.mContext.getPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isProfile(android.os.UserHandle userHandle) {
        verifyCanAccessUser(userHandle);
        return this.mUserManager.isProfile(userHandle.getIdentifier());
    }

    public boolean isManagedProfile(android.os.UserHandle userHandle) {
        verifyCanAccessUser(userHandle);
        return this.mUserManager.isManagedProfile(userHandle.getIdentifier());
    }

    public java.lang.CharSequence getProfileSwitchingLabel(android.os.UserHandle userHandle) {
        verifyCanAccessUser(userHandle);
        final boolean isManagedProfile = this.mUserManager.isManagedProfile(userHandle.getIdentifier());
        android.app.admin.DevicePolicyManager devicePolicyManager = (android.app.admin.DevicePolicyManager) this.mContext.getSystemService(android.app.admin.DevicePolicyManager.class);
        final java.lang.String charSequence = getCallingApplicationLabel().toString();
        return devicePolicyManager.getResources().getString(getUpdatableProfileSwitchingLabelId(isManagedProfile), new java.util.function.Supplier() { // from class: android.content.pm.CrossProfileApps$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                java.lang.String lambda$getProfileSwitchingLabel$0;
                lambda$getProfileSwitchingLabel$0 = android.content.pm.CrossProfileApps.this.lambda$getProfileSwitchingLabel$0(isManagedProfile, charSequence);
                return lambda$getProfileSwitchingLabel$0;
            }
        }, charSequence);
    }

    private java.lang.CharSequence getCallingApplicationLabel() {
        android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
        android.content.Intent launchIntentForPackage = packageManager.getLaunchIntentForPackage(this.mContext.getPackageName());
        if (launchIntentForPackage == null) {
            return getDefaultCallingApplicationLabel();
        }
        java.util.List<android.content.pm.ResolveInfo> queryIntentActivities = packageManager.queryIntentActivities(launchIntentForPackage, android.content.pm.PackageManager.ResolveInfoFlags.of(65536L));
        if (queryIntentActivities.size() > 0) {
            return queryIntentActivities.get(0).loadLabel(packageManager);
        }
        return getDefaultCallingApplicationLabel();
    }

    private java.lang.CharSequence getDefaultCallingApplicationLabel() {
        return this.mContext.getApplicationInfo().loadSafeLabel(this.mContext.getPackageManager(), 0.0f, 3);
    }

    private java.lang.String getUpdatableProfileSwitchingLabelId(boolean z) {
        return z ? android.app.admin.DevicePolicyResources.Strings.Core.SWITCH_TO_WORK_LABEL : android.app.admin.DevicePolicyResources.Strings.Core.SWITCH_TO_PERSONAL_LABEL;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: getDefaultProfileSwitchingLabel, reason: merged with bridge method [inline-methods] */
    public java.lang.String lambda$getProfileSwitchingLabel$0(boolean z, java.lang.String str) {
        return this.mResources.getString(z ? com.android.internal.R.string.managed_profile_app_label : com.android.internal.R.string.user_owner_app_label, str);
    }

    public android.graphics.drawable.Drawable getProfileSwitchingIconDrawable(android.os.UserHandle userHandle) {
        int i;
        verifyCanAccessUser(userHandle);
        if (this.mUserManager.isManagedProfile(userHandle.getIdentifier())) {
            return this.mContext.getPackageManager().getUserBadgeForDensityNoBackground(userHandle, 0);
        }
        android.graphics.drawable.Drawable defaultUserIcon = com.android.internal.util.UserIcons.getDefaultUserIcon(this.mResources, 0, true);
        if (this.mContext.getResources().getConfiguration().isNightModeActive()) {
            i = com.android.internal.R.color.profile_badge_1_dark;
        } else {
            i = com.android.internal.R.color.profile_badge_1;
        }
        defaultUserIcon.setColorFilter(null);
        defaultUserIcon.setTint(this.mResources.getColor(i, null));
        return defaultUserIcon;
    }

    public boolean canRequestInteractAcrossProfiles() {
        try {
            return this.mService.canRequestInteractAcrossProfiles(this.mContext.getPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean canInteractAcrossProfiles() {
        try {
            return this.mService.canInteractAcrossProfiles(this.mContext.getPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.content.Intent createRequestInteractAcrossProfilesIntent() {
        if (!canRequestInteractAcrossProfiles()) {
            throw new java.lang.SecurityException("The calling package can not request to interact across profiles.");
        }
        android.content.Intent intent = new android.content.Intent();
        intent.setAction(android.provider.Settings.ACTION_MANAGE_CROSS_PROFILE_ACCESS);
        intent.setData(android.net.Uri.parse("package:" + this.mContext.getPackageName()));
        return intent;
    }

    public void setInteractAcrossProfilesAppOp(java.lang.String str, int i) {
        try {
            this.mService.setInteractAcrossProfilesAppOp(this.mContext.getUserId(), str, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean canConfigureInteractAcrossProfiles(java.lang.String str) {
        try {
            return this.mService.canConfigureInteractAcrossProfiles(this.mContext.getUserId(), str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean canUserAttemptToConfigureInteractAcrossProfiles(java.lang.String str) {
        try {
            return this.mService.canUserAttemptToConfigureInteractAcrossProfiles(this.mContext.getUserId(), str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void resetInteractAcrossProfilesAppOps(java.util.Collection<java.lang.String> collection, final java.util.Set<java.lang.String> set) {
        if (collection.isEmpty()) {
            return;
        }
        java.util.List<java.lang.String> list = (java.util.List) collection.stream().filter(new java.util.function.Predicate() { // from class: android.content.pm.CrossProfileApps$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                return android.content.pm.CrossProfileApps.lambda$resetInteractAcrossProfilesAppOps$1(set, (java.lang.String) obj);
            }
        }).collect(java.util.stream.Collectors.toList());
        if (list.isEmpty()) {
            return;
        }
        try {
            this.mService.resetInteractAcrossProfilesAppOps(this.mContext.getUserId(), list);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static /* synthetic */ boolean lambda$resetInteractAcrossProfilesAppOps$1(java.util.Set set, java.lang.String str) {
        return !set.contains(str);
    }

    public void clearInteractAcrossProfilesAppOps() {
        try {
            this.mService.clearInteractAcrossProfilesAppOps(this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private void verifyCanAccessUser(android.os.UserHandle userHandle) {
        if (!getTargetUserProfiles().contains(userHandle)) {
            throw new java.lang.SecurityException("Not allowed to access " + userHandle);
        }
    }
}
