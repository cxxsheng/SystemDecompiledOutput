package com.android.server.pm;

/* loaded from: classes2.dex */
public class CrossProfileAppsServiceImpl extends android.content.pm.ICrossProfileApps.Stub {
    private static final java.lang.String TAG = "CrossProfileAppsService";
    private final android.content.Context mContext;
    private final com.android.server.pm.CrossProfileAppsServiceImpl.Injector mInjector;
    private final com.android.server.pm.CrossProfileAppsServiceImpl.LocalService mLocalService;

    @com.android.internal.annotations.VisibleForTesting
    public interface Injector {
        int checkComponentPermission(java.lang.String str, int i, int i2, boolean z);

        long clearCallingIdentity();

        android.app.ActivityManagerInternal getActivityManagerInternal();

        com.android.server.wm.ActivityTaskManagerInternal getActivityTaskManagerInternal();

        android.app.AppOpsManager getAppOpsManager();

        int getCallingPid();

        int getCallingUid();

        android.os.UserHandle getCallingUserHandle();

        int getCallingUserId();

        android.app.admin.DevicePolicyManagerInternal getDevicePolicyManagerInternal();

        android.content.pm.IPackageManager getIPackageManager();

        android.content.pm.PackageManager getPackageManager();

        android.content.pm.PackageManagerInternal getPackageManagerInternal();

        android.os.UserManager getUserManager();

        void killUid(int i);

        void restoreCallingIdentity(long j);

        void sendBroadcastAsUser(android.content.Intent intent, android.os.UserHandle userHandle);

        <T> T withCleanCallingIdentity(com.android.internal.util.FunctionalUtils.ThrowingSupplier<T> throwingSupplier);

        void withCleanCallingIdentity(com.android.internal.util.FunctionalUtils.ThrowingRunnable throwingRunnable);
    }

    public CrossProfileAppsServiceImpl(android.content.Context context) {
        this(context, new com.android.server.pm.CrossProfileAppsServiceImpl.InjectorImpl(context));
    }

    @com.android.internal.annotations.VisibleForTesting
    CrossProfileAppsServiceImpl(android.content.Context context, com.android.server.pm.CrossProfileAppsServiceImpl.Injector injector) {
        this.mLocalService = new com.android.server.pm.CrossProfileAppsServiceImpl.LocalService();
        this.mContext = context;
        this.mInjector = injector;
    }

    public java.util.List<android.os.UserHandle> getTargetUserProfiles(java.lang.String str) {
        java.util.Objects.requireNonNull(str);
        verifyCallingPackage(str);
        android.app.admin.DevicePolicyEventLogger.createEvent(125).setStrings(new java.lang.String[]{str}).write();
        return getTargetUserProfilesUnchecked(str, this.mInjector.getCallingUserId());
    }

    public void startActivityAsUser(android.app.IApplicationThread iApplicationThread, java.lang.String str, java.lang.String str2, android.content.ComponentName componentName, int i, boolean z, android.os.IBinder iBinder, android.os.Bundle bundle) throws android.os.RemoteException {
        android.os.Bundle bundle2;
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(componentName);
        verifyCallingPackage(str);
        android.app.admin.DevicePolicyEventLogger.createEvent(126).setStrings(new java.lang.String[]{str}).write();
        int callingUserId = this.mInjector.getCallingUserId();
        int callingUid = this.mInjector.getCallingUid();
        int callingPid = this.mInjector.getCallingPid();
        if (!getTargetUserProfilesUnchecked(str, callingUserId).contains(android.os.UserHandle.of(i))) {
            throw new java.lang.SecurityException(str + " cannot access unrelated user " + i);
        }
        if (!str.equals(componentName.getPackageName())) {
            throw new java.lang.SecurityException(str + " attempts to start an activity in other package - " + componentName.getPackageName());
        }
        android.content.Intent intent = new android.content.Intent();
        if (z) {
            intent.setAction("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.LAUNCHER");
            if (iBinder == null || bundle != null) {
                intent.addFlags(270532608);
            } else {
                intent.addFlags(2097152);
            }
            intent.setPackage(componentName.getPackageName());
        } else {
            if (callingUserId != i) {
                if (!hasInteractAcrossProfilesPermission(str, callingUid, callingPid) && !isPermissionGranted("android.permission.START_CROSS_PROFILE_ACTIVITIES", callingUid)) {
                    throw new java.lang.SecurityException("Attempt to launch activity without one of the required android.permission.INTERACT_ACROSS_PROFILES or android.permission.START_CROSS_PROFILE_ACTIVITIES permissions.");
                }
                if (!isSameProfileGroup(callingUserId, i)) {
                    throw new java.lang.SecurityException("Attempt to launch activity when target user is not in the same profile group.");
                }
            }
            intent.setComponent(componentName);
        }
        verifyActivityCanHandleIntentAndExported(intent, componentName, callingUid, i);
        if (bundle == null) {
            bundle2 = android.app.ActivityOptions.makeOpenCrossProfileAppsAnimation().toBundle();
        } else {
            bundle.putAll(android.app.ActivityOptions.makeOpenCrossProfileAppsAnimation().toBundle());
            bundle2 = bundle;
        }
        intent.setPackage(null);
        intent.setComponent(componentName);
        this.mInjector.getActivityTaskManagerInternal().startActivityAsUser(iApplicationThread, str, str2, intent, iBinder, 0, bundle2, i);
    }

    public void startActivityAsUserByIntent(android.app.IApplicationThread iApplicationThread, java.lang.String str, java.lang.String str2, android.content.Intent intent, int i, android.os.IBinder iBinder, android.os.Bundle bundle) throws android.os.RemoteException {
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(intent);
        java.util.Objects.requireNonNull(intent.getComponent(), "The intent must have a Component set");
        verifyCallingPackage(str);
        int callingUserId = this.mInjector.getCallingUserId();
        int callingUid = this.mInjector.getCallingUid();
        java.util.List<android.os.UserHandle> targetUserProfilesUnchecked = getTargetUserProfilesUnchecked(str, callingUserId);
        if (callingUserId != i && !targetUserProfilesUnchecked.contains(android.os.UserHandle.of(i))) {
            throw new java.lang.SecurityException(str + " cannot access unrelated user " + i);
        }
        android.content.Intent intent2 = new android.content.Intent(intent);
        intent2.setPackage(str);
        if (!str.equals(intent2.getComponent().getPackageName())) {
            throw new java.lang.SecurityException(str + " attempts to start an activity in other package - " + intent2.getComponent().getPackageName());
        }
        if (callingUserId != i && !hasCallerGotInteractAcrossProfilesPermission(str)) {
            throw new java.lang.SecurityException("Attempt to launch activity without required android.permission.INTERACT_ACROSS_PROFILES permission or target user is not in the same profile group.");
        }
        verifyActivityCanHandleIntent(intent2, callingUid, i);
        this.mInjector.getActivityTaskManagerInternal().startActivityAsUser(iApplicationThread, str, str2, intent2, iBinder, 0, bundle, i);
        logStartActivityByIntent(str);
    }

    private void logStartActivityByIntent(java.lang.String str) {
        android.app.admin.DevicePolicyEventLogger.createEvent(150).setStrings(new java.lang.String[]{str}).setBoolean(isCallingUserAManagedProfile()).write();
    }

    public boolean canRequestInteractAcrossProfiles(java.lang.String str) {
        java.util.Objects.requireNonNull(str);
        verifyCallingPackage(str);
        return canRequestInteractAcrossProfilesUnchecked(str);
    }

    private boolean canRequestInteractAcrossProfilesUnchecked(java.lang.String str) {
        int callingUserId = this.mInjector.getCallingUserId();
        int[] profileIdsExcludingHidden = this.mInjector.getUserManager().getProfileIdsExcludingHidden(callingUserId, true);
        if (profileIdsExcludingHidden.length >= 2 && !isProfileOwner(str, profileIdsExcludingHidden)) {
            return hasRequestedAppOpPermission(android.app.AppOpsManager.opToPermission(93), str, callingUserId);
        }
        return false;
    }

    private boolean hasRequestedAppOpPermission(java.lang.String str, java.lang.String str2, int i) {
        try {
            return com.android.internal.util.ArrayUtils.contains(this.mInjector.getIPackageManager().getAppOpPermissionPackages(str, i), str2);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "PackageManager dead. Cannot get permission info");
            return false;
        }
    }

    public boolean canInteractAcrossProfiles(java.lang.String str) {
        java.util.Objects.requireNonNull(str);
        verifyCallingPackage(str);
        java.util.List<android.os.UserHandle> targetUserProfilesUnchecked = getTargetUserProfilesUnchecked(str, this.mInjector.getCallingUserId());
        return !targetUserProfilesUnchecked.isEmpty() && hasCallerGotInteractAcrossProfilesPermission(str) && haveProfilesGotInteractAcrossProfilesPermission(str, targetUserProfilesUnchecked);
    }

    private boolean hasCallerGotInteractAcrossProfilesPermission(java.lang.String str) {
        return hasInteractAcrossProfilesPermission(str, this.mInjector.getCallingUid(), this.mInjector.getCallingPid());
    }

    private boolean haveProfilesGotInteractAcrossProfilesPermission(final java.lang.String str, java.util.List<android.os.UserHandle> list) {
        for (final android.os.UserHandle userHandle : list) {
            int intValue = ((java.lang.Integer) this.mInjector.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.pm.CrossProfileAppsServiceImpl$$ExternalSyntheticLambda12
                public final java.lang.Object getOrThrow() {
                    java.lang.Integer lambda$haveProfilesGotInteractAcrossProfilesPermission$0;
                    lambda$haveProfilesGotInteractAcrossProfilesPermission$0 = com.android.server.pm.CrossProfileAppsServiceImpl.this.lambda$haveProfilesGotInteractAcrossProfilesPermission$0(str, userHandle);
                    return lambda$haveProfilesGotInteractAcrossProfilesPermission$0;
                }
            })).intValue();
            if (intValue == -1 || !hasInteractAcrossProfilesPermission(str, intValue, -1)) {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Integer lambda$haveProfilesGotInteractAcrossProfilesPermission$0(java.lang.String str, android.os.UserHandle userHandle) throws java.lang.Exception {
        try {
            return java.lang.Integer.valueOf(this.mInjector.getPackageManager().getPackageUidAsUser(str, 0, userHandle.getIdentifier()));
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return -1;
        }
    }

    private boolean isCrossProfilePackageAllowlisted(final java.lang.String str) {
        final int callingUserId = this.mInjector.getCallingUserId();
        return ((java.lang.Boolean) this.mInjector.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.pm.CrossProfileAppsServiceImpl$$ExternalSyntheticLambda14
            public final java.lang.Object getOrThrow() {
                java.lang.Boolean lambda$isCrossProfilePackageAllowlisted$1;
                lambda$isCrossProfilePackageAllowlisted$1 = com.android.server.pm.CrossProfileAppsServiceImpl.this.lambda$isCrossProfilePackageAllowlisted$1(callingUserId, str);
                return lambda$isCrossProfilePackageAllowlisted$1;
            }
        })).booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Boolean lambda$isCrossProfilePackageAllowlisted$1(int i, java.lang.String str) throws java.lang.Exception {
        return java.lang.Boolean.valueOf(this.mInjector.getDevicePolicyManagerInternal().getAllCrossProfilePackages(i).contains(str));
    }

    private boolean isCrossProfilePackageAllowlistedByDefault(final java.lang.String str) {
        return ((java.lang.Boolean) this.mInjector.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.pm.CrossProfileAppsServiceImpl$$ExternalSyntheticLambda6
            public final java.lang.Object getOrThrow() {
                java.lang.Boolean lambda$isCrossProfilePackageAllowlistedByDefault$2;
                lambda$isCrossProfilePackageAllowlistedByDefault$2 = com.android.server.pm.CrossProfileAppsServiceImpl.this.lambda$isCrossProfilePackageAllowlistedByDefault$2(str);
                return lambda$isCrossProfilePackageAllowlistedByDefault$2;
            }
        })).booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Boolean lambda$isCrossProfilePackageAllowlistedByDefault$2(java.lang.String str) throws java.lang.Exception {
        return java.lang.Boolean.valueOf(this.mInjector.getDevicePolicyManagerInternal().getDefaultCrossProfilePackages().contains(str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.util.List<android.os.UserHandle> getTargetUserProfilesUnchecked(final java.lang.String str, final int i) {
        return (java.util.List) this.mInjector.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.pm.CrossProfileAppsServiceImpl$$ExternalSyntheticLambda9
            public final java.lang.Object getOrThrow() {
                java.util.List lambda$getTargetUserProfilesUnchecked$3;
                lambda$getTargetUserProfilesUnchecked$3 = com.android.server.pm.CrossProfileAppsServiceImpl.this.lambda$getTargetUserProfilesUnchecked$3(i, str);
                return lambda$getTargetUserProfilesUnchecked$3;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.util.List lambda$getTargetUserProfilesUnchecked$3(int i, java.lang.String str) throws java.lang.Exception {
        int[] profileIdsExcludingHidden = this.mInjector.getUserManager().getProfileIdsExcludingHidden(i, true);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i2 : profileIdsExcludingHidden) {
            if (i2 != i && isPackageEnabled(str, i2)) {
                arrayList.add(android.os.UserHandle.of(i2));
            }
        }
        return arrayList;
    }

    private boolean isPackageEnabled(final java.lang.String str, final int i) {
        final int callingUid = this.mInjector.getCallingUid();
        return ((java.lang.Boolean) this.mInjector.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.pm.CrossProfileAppsServiceImpl$$ExternalSyntheticLambda11
            public final java.lang.Object getOrThrow() {
                java.lang.Boolean lambda$isPackageEnabled$4;
                lambda$isPackageEnabled$4 = com.android.server.pm.CrossProfileAppsServiceImpl.this.lambda$isPackageEnabled$4(str, callingUid, i);
                return lambda$isPackageEnabled$4;
            }
        })).booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Boolean lambda$isPackageEnabled$4(java.lang.String str, int i, int i2) throws java.lang.Exception {
        android.content.pm.PackageInfo packageInfo = this.mInjector.getPackageManagerInternal().getPackageInfo(str, 786432L, i, i2);
        return java.lang.Boolean.valueOf(packageInfo != null && packageInfo.applicationInfo.enabled);
    }

    private void verifyActivityCanHandleIntent(final android.content.Intent intent, final int i, final int i2) {
        this.mInjector.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.pm.CrossProfileAppsServiceImpl$$ExternalSyntheticLambda13
            public final void runOrThrow() {
                com.android.server.pm.CrossProfileAppsServiceImpl.this.lambda$verifyActivityCanHandleIntent$5(intent, i, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$verifyActivityCanHandleIntent$5(android.content.Intent intent, int i, int i2) throws java.lang.Exception {
        if (!this.mInjector.getPackageManagerInternal().queryIntentActivities(intent, intent.resolveTypeIfNeeded(this.mContext.getContentResolver()), 786432L, i, i2).isEmpty()) {
        } else {
            throw new java.lang.SecurityException("Activity cannot handle intent");
        }
    }

    private void verifyActivityCanHandleIntentAndExported(final android.content.Intent intent, final android.content.ComponentName componentName, final int i, final int i2) {
        this.mInjector.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.pm.CrossProfileAppsServiceImpl$$ExternalSyntheticLambda0
            public final void runOrThrow() {
                com.android.server.pm.CrossProfileAppsServiceImpl.this.lambda$verifyActivityCanHandleIntentAndExported$6(intent, i, i2, componentName);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$verifyActivityCanHandleIntentAndExported$6(android.content.Intent intent, int i, int i2, android.content.ComponentName componentName) throws java.lang.Exception {
        java.util.List<android.content.pm.ResolveInfo> queryIntentActivities = this.mInjector.getPackageManagerInternal().queryIntentActivities(intent, intent.resolveTypeIfNeeded(this.mContext.getContentResolver()), 786432L, i, i2);
        int size = queryIntentActivities.size();
        for (int i3 = 0; i3 < size; i3++) {
            android.content.pm.ActivityInfo activityInfo = queryIntentActivities.get(i3).activityInfo;
            if (android.text.TextUtils.equals(activityInfo.packageName, componentName.getPackageName()) && android.text.TextUtils.equals(activityInfo.name, componentName.getClassName()) && activityInfo.exported) {
                return;
            }
        }
        throw new java.lang.SecurityException("Attempt to launch activity without  category Intent.CATEGORY_LAUNCHER or activity is not exported" + componentName);
    }

    /* renamed from: setInteractAcrossProfilesAppOp, reason: merged with bridge method [inline-methods] */
    public void lambda$clearInteractAcrossProfilesAppOps$11(int i, java.lang.String str, int i2) {
        setInteractAcrossProfilesAppOp(str, i2, i);
    }

    private void setInteractAcrossProfilesAppOp(java.lang.String str, int i, int i2) {
        int callingUid = this.mInjector.getCallingUid();
        if (!isPermissionGranted("android.permission.INTERACT_ACROSS_USERS_FULL", callingUid) && !isPermissionGranted("android.permission.INTERACT_ACROSS_USERS", callingUid)) {
            throw new java.lang.SecurityException("INTERACT_ACROSS_USERS or INTERACT_ACROSS_USERS_FULL is required to set the app-op for interacting across profiles.");
        }
        if (!isPermissionGranted("android.permission.MANAGE_APP_OPS_MODES", callingUid) && !isPermissionGranted("android.permission.CONFIGURE_INTERACT_ACROSS_PROFILES", callingUid)) {
            throw new java.lang.SecurityException("MANAGE_APP_OPS_MODES or CONFIGURE_INTERACT_ACROSS_PROFILES is required to set the app-op for interacting across profiles.");
        }
        setInteractAcrossProfilesAppOpUnchecked(str, i, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setInteractAcrossProfilesAppOpUnchecked(java.lang.String str, int i, int i2) {
        if (i == 0 && !canConfigureInteractAcrossProfiles(str, i2)) {
            android.util.Slog.e(TAG, "Tried to turn on the appop for interacting across profiles for invalid app " + str);
            return;
        }
        int[] profileIdsExcludingHidden = this.mInjector.getUserManager().getProfileIdsExcludingHidden(i2, false);
        int length = profileIdsExcludingHidden.length;
        for (int i3 = 0; i3 < length; i3++) {
            int i4 = profileIdsExcludingHidden[i3];
            if (isPackageInstalled(str, i4)) {
                setInteractAcrossProfilesAppOpForProfile(str, i, i4, i4 == i2);
            }
        }
    }

    private boolean isPackageInstalled(final java.lang.String str, final int i) {
        return ((java.lang.Boolean) this.mInjector.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.pm.CrossProfileAppsServiceImpl$$ExternalSyntheticLambda4
            public final java.lang.Object getOrThrow() {
                java.lang.Boolean lambda$isPackageInstalled$7;
                lambda$isPackageInstalled$7 = com.android.server.pm.CrossProfileAppsServiceImpl.this.lambda$isPackageInstalled$7(str, i);
                return lambda$isPackageInstalled$7;
            }
        })).booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Boolean lambda$isPackageInstalled$7(java.lang.String str, int i) throws java.lang.Exception {
        return java.lang.Boolean.valueOf(this.mInjector.getPackageManagerInternal().getPackageInfo(str, 786432L, this.mInjector.getCallingUid(), i) != null);
    }

    private void setInteractAcrossProfilesAppOpForProfile(java.lang.String str, int i, int i2, boolean z) {
        try {
            setInteractAcrossProfilesAppOpForProfileOrThrow(str, i, i2, z);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Slog.e(TAG, "Missing package " + str + " on profile user ID " + i2, e);
        }
    }

    private void setInteractAcrossProfilesAppOpForProfileOrThrow(java.lang.String str, final int i, int i2, boolean z) throws android.content.pm.PackageManager.NameNotFoundException {
        final int packageUidAsUser = this.mInjector.getPackageManager().getPackageUidAsUser(str, 0, i2);
        if (currentModeEquals(i, str, packageUidAsUser)) {
            android.util.Slog.i(TAG, "Attempt to set mode to existing value of " + i + " for " + str + " on profile user ID " + i2);
            return;
        }
        boolean hasInteractAcrossProfilesPermission = hasInteractAcrossProfilesPermission(str, packageUidAsUser, -1);
        if (isPermissionGranted("android.permission.CONFIGURE_INTERACT_ACROSS_PROFILES", this.mInjector.getCallingUid())) {
            this.mInjector.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.pm.CrossProfileAppsServiceImpl$$ExternalSyntheticLambda8
                public final void runOrThrow() {
                    com.android.server.pm.CrossProfileAppsServiceImpl.this.lambda$setInteractAcrossProfilesAppOpForProfileOrThrow$8(packageUidAsUser, i);
                }
            });
        } else {
            this.mInjector.getAppOpsManager().setUidMode(93, packageUidAsUser, i);
        }
        maybeKillUid(str, packageUidAsUser, hasInteractAcrossProfilesPermission);
        sendCanInteractAcrossProfilesChangedBroadcast(str, android.os.UserHandle.of(i2));
        maybeLogSetInteractAcrossProfilesAppOp(str, i, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setInteractAcrossProfilesAppOpForProfileOrThrow$8(int i, int i2) throws java.lang.Exception {
        this.mInjector.getAppOpsManager().setUidMode(93, i, i2);
    }

    private void maybeKillUid(java.lang.String str, int i, boolean z) {
        if (!z || hasInteractAcrossProfilesPermission(str, i, -1)) {
            return;
        }
        this.mInjector.killUid(i);
    }

    private void maybeLogSetInteractAcrossProfilesAppOp(java.lang.String str, int i, boolean z) {
        if (!z) {
            return;
        }
        android.app.admin.DevicePolicyEventLogger.createEvent(139).setStrings(new java.lang.String[]{str}).setInt(i).setBoolean(appDeclaresCrossProfileAttribute(str)).write();
    }

    private boolean currentModeEquals(final int i, final java.lang.String str, final int i2) {
        final java.lang.String permissionToOp = android.app.AppOpsManager.permissionToOp("android.permission.INTERACT_ACROSS_PROFILES");
        return ((java.lang.Boolean) this.mInjector.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.pm.CrossProfileAppsServiceImpl$$ExternalSyntheticLambda5
            public final java.lang.Object getOrThrow() {
                java.lang.Boolean lambda$currentModeEquals$9;
                lambda$currentModeEquals$9 = com.android.server.pm.CrossProfileAppsServiceImpl.this.lambda$currentModeEquals$9(i, permissionToOp, i2, str);
                return lambda$currentModeEquals$9;
            }
        })).booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Boolean lambda$currentModeEquals$9(int i, java.lang.String str, int i2, java.lang.String str2) throws java.lang.Exception {
        return java.lang.Boolean.valueOf(i == this.mInjector.getAppOpsManager().unsafeCheckOpNoThrow(str, i2, str2));
    }

    private void sendCanInteractAcrossProfilesChangedBroadcast(java.lang.String str, android.os.UserHandle userHandle) {
        android.content.Intent intent = new android.content.Intent("android.content.pm.action.CAN_INTERACT_ACROSS_PROFILES_CHANGED").setPackage(str);
        if (appDeclaresCrossProfileAttribute(str)) {
            intent.addFlags(android.hardware.audio.common.V2_0.AudioFormat.EVRCB);
        } else {
            intent.addFlags(1073741824);
        }
        java.util.Iterator<android.content.pm.ResolveInfo> it = findBroadcastReceiversForUser(intent, userHandle).iterator();
        while (it.hasNext()) {
            intent.setComponent(it.next().getComponentInfo().getComponentName());
            this.mInjector.sendBroadcastAsUser(intent, userHandle);
        }
    }

    private java.util.List<android.content.pm.ResolveInfo> findBroadcastReceiversForUser(android.content.Intent intent, android.os.UserHandle userHandle) {
        return this.mInjector.getPackageManager().queryBroadcastReceiversAsUser(intent, 0, userHandle);
    }

    private boolean appDeclaresCrossProfileAttribute(java.lang.String str) {
        return this.mInjector.getPackageManagerInternal().getPackage(str).isCrossProfile();
    }

    public boolean canConfigureInteractAcrossProfiles(int i, java.lang.String str) {
        if (this.mInjector.getCallingUserId() != i) {
            this.mContext.checkCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS");
        }
        return canConfigureInteractAcrossProfiles(str, i);
    }

    private boolean canConfigureInteractAcrossProfiles(java.lang.String str, int i) {
        if (canUserAttemptToConfigureInteractAcrossProfiles(str, i) && hasOtherProfileWithPackageInstalled(str, i) && hasRequestedAppOpPermission(android.app.AppOpsManager.opToPermission(93), str, i)) {
            return isCrossProfilePackageAllowlisted(str);
        }
        return false;
    }

    public boolean canUserAttemptToConfigureInteractAcrossProfiles(int i, java.lang.String str) {
        if (this.mInjector.getCallingUserId() != i) {
            this.mContext.checkCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS");
        }
        return canUserAttemptToConfigureInteractAcrossProfiles(str, i);
    }

    private boolean canUserAttemptToConfigureInteractAcrossProfiles(java.lang.String str, int i) {
        int[] profileIdsExcludingHidden = this.mInjector.getUserManager().getProfileIdsExcludingHidden(i, false);
        if (profileIdsExcludingHidden.length >= 2 && !isProfileOwner(str, profileIdsExcludingHidden) && hasRequestedAppOpPermission(android.app.AppOpsManager.opToPermission(93), str, i)) {
            return !isPlatformSignedAppWithNonUserConfigurablePermission(str, profileIdsExcludingHidden);
        }
        return false;
    }

    private boolean isPlatformSignedAppWithNonUserConfigurablePermission(java.lang.String str, int[] iArr) {
        return !isCrossProfilePackageAllowlistedByDefault(str) && isPlatformSignedAppWithAutomaticProfilesPermission(str, iArr);
    }

    private boolean isPlatformSignedAppWithAutomaticProfilesPermission(java.lang.String str, int[] iArr) {
        for (int i : iArr) {
            int packageUid = this.mInjector.getPackageManagerInternal().getPackageUid(str, 0L, i);
            if (packageUid != -1 && isPermissionGranted("android.permission.INTERACT_ACROSS_PROFILES", packageUid)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasOtherProfileWithPackageInstalled(final java.lang.String str, final int i) {
        return ((java.lang.Boolean) this.mInjector.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.pm.CrossProfileAppsServiceImpl$$ExternalSyntheticLambda15
            public final java.lang.Object getOrThrow() {
                java.lang.Boolean lambda$hasOtherProfileWithPackageInstalled$10;
                lambda$hasOtherProfileWithPackageInstalled$10 = com.android.server.pm.CrossProfileAppsServiceImpl.this.lambda$hasOtherProfileWithPackageInstalled$10(i, str);
                return lambda$hasOtherProfileWithPackageInstalled$10;
            }
        })).booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Boolean lambda$hasOtherProfileWithPackageInstalled$10(int i, java.lang.String str) throws java.lang.Exception {
        for (int i2 : this.mInjector.getUserManager().getProfileIdsExcludingHidden(i, false)) {
            if (i2 != i && isPackageInstalled(str, i2)) {
                return true;
            }
        }
        return false;
    }

    public void resetInteractAcrossProfilesAppOps(int i, java.util.List<java.lang.String> list) {
        java.util.Iterator<java.lang.String> it = list.iterator();
        while (it.hasNext()) {
            resetInteractAcrossProfilesAppOp(i, it.next());
        }
    }

    private void resetInteractAcrossProfilesAppOp(int i, java.lang.String str) {
        if (canConfigureInteractAcrossProfiles(str, i)) {
            android.util.Slog.w(TAG, "Not resetting app-op for package " + str + " since it is still configurable by users.");
            return;
        }
        lambda$clearInteractAcrossProfilesAppOps$11(i, str, android.app.AppOpsManager.opToDefaultMode(android.app.AppOpsManager.permissionToOp("android.permission.INTERACT_ACROSS_PROFILES")));
    }

    public void clearInteractAcrossProfilesAppOps(final int i) {
        final int opToDefaultMode = android.app.AppOpsManager.opToDefaultMode(android.app.AppOpsManager.permissionToOp("android.permission.INTERACT_ACROSS_PROFILES"));
        findAllPackageNames().forEach(new java.util.function.Consumer() { // from class: com.android.server.pm.CrossProfileAppsServiceImpl$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.pm.CrossProfileAppsServiceImpl.this.lambda$clearInteractAcrossProfilesAppOps$11(i, opToDefaultMode, (java.lang.String) obj);
            }
        });
    }

    private java.util.List<java.lang.String> findAllPackageNames() {
        return (java.util.List) this.mInjector.getPackageManagerInternal().getInstalledApplications(0L, this.mInjector.getCallingUserId(), this.mInjector.getCallingUid()).stream().map(new java.util.function.Function() { // from class: com.android.server.pm.CrossProfileAppsServiceImpl$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.String str;
                str = ((android.content.pm.ApplicationInfo) obj).packageName;
                return str;
            }
        }).collect(java.util.stream.Collectors.toList());
    }

    android.content.pm.CrossProfileAppsInternal getLocalService() {
        return this.mLocalService;
    }

    private boolean isSameProfileGroup(final int i, final int i2) {
        return ((java.lang.Boolean) this.mInjector.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.pm.CrossProfileAppsServiceImpl$$ExternalSyntheticLambda10
            public final java.lang.Object getOrThrow() {
                java.lang.Boolean lambda$isSameProfileGroup$13;
                lambda$isSameProfileGroup$13 = com.android.server.pm.CrossProfileAppsServiceImpl.this.lambda$isSameProfileGroup$13(i, i2);
                return lambda$isSameProfileGroup$13;
            }
        })).booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Boolean lambda$isSameProfileGroup$13(int i, int i2) throws java.lang.Exception {
        return java.lang.Boolean.valueOf(this.mInjector.getUserManager().isSameProfileGroup(i, i2));
    }

    private void verifyCallingPackage(java.lang.String str) {
        this.mInjector.getAppOpsManager().checkPackage(this.mInjector.getCallingUid(), str);
    }

    private boolean isPermissionGranted(java.lang.String str, int i) {
        return this.mInjector.checkComponentPermission(str, i, -1, true) == 0;
    }

    private boolean isCallingUserAManagedProfile() {
        return isManagedProfile(this.mInjector.getCallingUserId());
    }

    private boolean isManagedProfile(final int i) {
        return ((java.lang.Boolean) this.mInjector.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.pm.CrossProfileAppsServiceImpl$$ExternalSyntheticLambda1
            public final java.lang.Object getOrThrow() {
                java.lang.Boolean lambda$isManagedProfile$14;
                lambda$isManagedProfile$14 = com.android.server.pm.CrossProfileAppsServiceImpl.this.lambda$isManagedProfile$14(i);
                return lambda$isManagedProfile$14;
            }
        })).booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Boolean lambda$isManagedProfile$14(int i) throws java.lang.Exception {
        return java.lang.Boolean.valueOf(((android.os.UserManager) this.mContext.getSystemService(android.os.UserManager.class)).isManagedProfile(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean hasInteractAcrossProfilesPermission(java.lang.String str, int i, int i2) {
        return isPermissionGranted("android.permission.INTERACT_ACROSS_USERS_FULL", i) || isPermissionGranted("android.permission.INTERACT_ACROSS_USERS", i) || android.content.PermissionChecker.checkPermissionForPreflight(this.mContext, "android.permission.INTERACT_ACROSS_PROFILES", i2, i, str) == 0;
    }

    private boolean isProfileOwner(java.lang.String str, int[] iArr) {
        for (int i : iArr) {
            if (isProfileOwner(str, i)) {
                return true;
            }
        }
        return false;
    }

    private boolean isProfileOwner(java.lang.String str, final int i) {
        android.content.ComponentName componentName = (android.content.ComponentName) this.mInjector.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.pm.CrossProfileAppsServiceImpl$$ExternalSyntheticLambda3
            public final java.lang.Object getOrThrow() {
                android.content.ComponentName lambda$isProfileOwner$15;
                lambda$isProfileOwner$15 = com.android.server.pm.CrossProfileAppsServiceImpl.this.lambda$isProfileOwner$15(i);
                return lambda$isProfileOwner$15;
            }
        });
        if (componentName == null) {
            return false;
        }
        return componentName.getPackageName().equals(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.content.ComponentName lambda$isProfileOwner$15(int i) throws java.lang.Exception {
        return this.mInjector.getDevicePolicyManagerInternal().getProfileOwnerAsUser(i);
    }

    private static class InjectorImpl implements com.android.server.pm.CrossProfileAppsServiceImpl.Injector {
        private final android.content.Context mContext;

        public InjectorImpl(android.content.Context context) {
            this.mContext = context;
        }

        @Override // com.android.server.pm.CrossProfileAppsServiceImpl.Injector
        public int getCallingUid() {
            return android.os.Binder.getCallingUid();
        }

        @Override // com.android.server.pm.CrossProfileAppsServiceImpl.Injector
        public int getCallingPid() {
            return android.os.Binder.getCallingPid();
        }

        @Override // com.android.server.pm.CrossProfileAppsServiceImpl.Injector
        public int getCallingUserId() {
            return android.os.UserHandle.getCallingUserId();
        }

        @Override // com.android.server.pm.CrossProfileAppsServiceImpl.Injector
        public android.os.UserHandle getCallingUserHandle() {
            return android.os.Binder.getCallingUserHandle();
        }

        @Override // com.android.server.pm.CrossProfileAppsServiceImpl.Injector
        public long clearCallingIdentity() {
            return android.os.Binder.clearCallingIdentity();
        }

        @Override // com.android.server.pm.CrossProfileAppsServiceImpl.Injector
        public void restoreCallingIdentity(long j) {
            android.os.Binder.restoreCallingIdentity(j);
        }

        @Override // com.android.server.pm.CrossProfileAppsServiceImpl.Injector
        public void withCleanCallingIdentity(com.android.internal.util.FunctionalUtils.ThrowingRunnable throwingRunnable) {
            android.os.Binder.withCleanCallingIdentity(throwingRunnable);
        }

        @Override // com.android.server.pm.CrossProfileAppsServiceImpl.Injector
        public final <T> T withCleanCallingIdentity(com.android.internal.util.FunctionalUtils.ThrowingSupplier<T> throwingSupplier) {
            return (T) android.os.Binder.withCleanCallingIdentity(throwingSupplier);
        }

        @Override // com.android.server.pm.CrossProfileAppsServiceImpl.Injector
        public android.os.UserManager getUserManager() {
            return (android.os.UserManager) this.mContext.getSystemService(android.os.UserManager.class);
        }

        @Override // com.android.server.pm.CrossProfileAppsServiceImpl.Injector
        public android.content.pm.PackageManagerInternal getPackageManagerInternal() {
            return (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
        }

        @Override // com.android.server.pm.CrossProfileAppsServiceImpl.Injector
        public android.content.pm.PackageManager getPackageManager() {
            return this.mContext.getPackageManager();
        }

        @Override // com.android.server.pm.CrossProfileAppsServiceImpl.Injector
        public android.app.AppOpsManager getAppOpsManager() {
            return (android.app.AppOpsManager) this.mContext.getSystemService(android.app.AppOpsManager.class);
        }

        @Override // com.android.server.pm.CrossProfileAppsServiceImpl.Injector
        public android.app.ActivityManagerInternal getActivityManagerInternal() {
            return (android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class);
        }

        @Override // com.android.server.pm.CrossProfileAppsServiceImpl.Injector
        public com.android.server.wm.ActivityTaskManagerInternal getActivityTaskManagerInternal() {
            return (com.android.server.wm.ActivityTaskManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.ActivityTaskManagerInternal.class);
        }

        @Override // com.android.server.pm.CrossProfileAppsServiceImpl.Injector
        public android.content.pm.IPackageManager getIPackageManager() {
            return android.app.AppGlobals.getPackageManager();
        }

        @Override // com.android.server.pm.CrossProfileAppsServiceImpl.Injector
        public android.app.admin.DevicePolicyManagerInternal getDevicePolicyManagerInternal() {
            return (android.app.admin.DevicePolicyManagerInternal) com.android.server.LocalServices.getService(android.app.admin.DevicePolicyManagerInternal.class);
        }

        @Override // com.android.server.pm.CrossProfileAppsServiceImpl.Injector
        public void sendBroadcastAsUser(android.content.Intent intent, android.os.UserHandle userHandle) {
            this.mContext.sendBroadcastAsUser(intent, userHandle);
        }

        @Override // com.android.server.pm.CrossProfileAppsServiceImpl.Injector
        public int checkComponentPermission(java.lang.String str, int i, int i2, boolean z) {
            return android.app.ActivityManager.checkComponentPermission(str, i, i2, z);
        }

        @Override // com.android.server.pm.CrossProfileAppsServiceImpl.Injector
        public void killUid(int i) {
            com.android.server.pm.permission.PermissionManagerService.killUid(android.os.UserHandle.getAppId(i), android.os.UserHandle.getUserId(i), "permissions revoked");
        }
    }

    class LocalService extends android.content.pm.CrossProfileAppsInternal {
        LocalService() {
        }

        public boolean verifyPackageHasInteractAcrossProfilePermission(java.lang.String str, int i) throws android.content.pm.PackageManager.NameNotFoundException {
            android.content.pm.PackageManager packageManager = com.android.server.pm.CrossProfileAppsServiceImpl.this.mInjector.getPackageManager();
            java.util.Objects.requireNonNull(str);
            android.content.pm.ApplicationInfo applicationInfoAsUser = packageManager.getApplicationInfoAsUser(str, 0, i);
            java.util.Objects.requireNonNull(applicationInfoAsUser);
            return verifyUidHasInteractAcrossProfilePermission(str, applicationInfoAsUser.uid);
        }

        public boolean verifyUidHasInteractAcrossProfilePermission(java.lang.String str, int i) {
            java.util.Objects.requireNonNull(str);
            return com.android.server.pm.CrossProfileAppsServiceImpl.this.hasInteractAcrossProfilesPermission(str, i, -1);
        }

        public java.util.List<android.os.UserHandle> getTargetUserProfiles(java.lang.String str, int i) {
            return com.android.server.pm.CrossProfileAppsServiceImpl.this.getTargetUserProfilesUnchecked(str, i);
        }

        public void setInteractAcrossProfilesAppOp(java.lang.String str, int i, int i2) {
            com.android.server.pm.CrossProfileAppsServiceImpl.this.setInteractAcrossProfilesAppOpUnchecked(str, i, i2);
        }
    }
}
