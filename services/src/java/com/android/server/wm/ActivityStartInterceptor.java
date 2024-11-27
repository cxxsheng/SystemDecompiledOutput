package com.android.server.wm;

/* loaded from: classes3.dex */
class ActivityStartInterceptor {
    android.content.pm.ActivityInfo mAInfo;
    android.app.ActivityOptions mActivityOptions;

    @android.annotation.Nullable
    private java.lang.String mCallingFeatureId;
    private java.lang.String mCallingPackage;
    int mCallingPid;
    int mCallingUid;
    com.android.server.wm.Task mInTask;
    com.android.server.wm.TaskFragment mInTaskFragment;
    android.content.Intent mIntent;
    com.android.server.wm.TaskDisplayArea mPresumableLaunchDisplayArea;
    android.content.pm.ResolveInfo mRInfo;
    private int mRealCallingPid;
    private int mRealCallingUid;
    java.lang.String mResolvedType;
    private final com.android.server.wm.ActivityTaskManagerService mService;
    private final android.content.Context mServiceContext;
    private int mStartFlags;
    private final com.android.server.wm.ActivityTaskSupervisor mSupervisor;
    private int mUserId;
    private android.os.UserManager mUserManager;

    ActivityStartInterceptor(com.android.server.wm.ActivityTaskManagerService activityTaskManagerService, com.android.server.wm.ActivityTaskSupervisor activityTaskSupervisor) {
        this(activityTaskManagerService, activityTaskSupervisor, activityTaskManagerService.mContext);
    }

    @com.android.internal.annotations.VisibleForTesting
    ActivityStartInterceptor(com.android.server.wm.ActivityTaskManagerService activityTaskManagerService, com.android.server.wm.ActivityTaskSupervisor activityTaskSupervisor, android.content.Context context) {
        this.mService = activityTaskManagerService;
        this.mSupervisor = activityTaskSupervisor;
        this.mServiceContext = context;
    }

    void setStates(int i, int i2, int i3, int i4, java.lang.String str, @android.annotation.Nullable java.lang.String str2) {
        this.mRealCallingPid = i2;
        this.mRealCallingUid = i3;
        this.mUserId = i;
        this.mStartFlags = i4;
        this.mCallingPackage = str;
        this.mCallingFeatureId = str2;
    }

    private android.content.IntentSender createIntentSenderForOriginalIntent(int i, int i2) {
        android.app.ActivityOptions deferCrossProfileAppsAnimationIfNecessary = deferCrossProfileAppsAnimationIfNecessary();
        deferCrossProfileAppsAnimationIfNecessary.setPendingIntentCreatorBackgroundActivityStartMode(1);
        com.android.server.wm.TaskFragment launchTaskFragment = getLaunchTaskFragment();
        if (launchTaskFragment != null) {
            deferCrossProfileAppsAnimationIfNecessary.setLaunchTaskFragmentToken(launchTaskFragment.getFragmentToken());
        }
        return new android.content.IntentSender(this.mService.getIntentSenderLocked(2, this.mCallingPackage, this.mCallingFeatureId, i, this.mUserId, null, null, 0, new android.content.Intent[]{this.mIntent}, new java.lang.String[]{this.mResolvedType}, i2, deferCrossProfileAppsAnimationIfNecessary.toBundle()));
    }

    @android.annotation.Nullable
    private com.android.server.wm.TaskFragment getLaunchTaskFragment() {
        android.os.IBinder launchTaskFragmentToken;
        if (this.mInTaskFragment != null) {
            return this.mInTaskFragment;
        }
        if (this.mActivityOptions == null || (launchTaskFragmentToken = this.mActivityOptions.getLaunchTaskFragmentToken()) == null) {
            return null;
        }
        return com.android.server.wm.TaskFragment.fromTaskFragmentToken(launchTaskFragmentToken, this.mService);
    }

    boolean intercept(android.content.Intent intent, android.content.pm.ResolveInfo resolveInfo, android.content.pm.ActivityInfo activityInfo, java.lang.String str, com.android.server.wm.Task task, com.android.server.wm.TaskFragment taskFragment, int i, int i2, android.app.ActivityOptions activityOptions, com.android.server.wm.TaskDisplayArea taskDisplayArea) {
        this.mUserManager = android.os.UserManager.get(this.mServiceContext);
        this.mIntent = intent;
        this.mCallingPid = i;
        this.mCallingUid = i2;
        this.mRInfo = resolveInfo;
        this.mAInfo = activityInfo;
        this.mResolvedType = str;
        this.mInTask = task;
        this.mInTaskFragment = taskFragment;
        this.mActivityOptions = activityOptions;
        this.mPresumableLaunchDisplayArea = taskDisplayArea;
        if (interceptQuietProfileIfNeeded() || interceptSuspendedPackageIfNeeded() || interceptLockTaskModeViolationPackageIfNeeded() || interceptHarmfulAppIfNeeded() || interceptLockedProfileIfNeeded() || interceptHomeIfNeeded()) {
            return true;
        }
        android.util.SparseArray<com.android.server.wm.ActivityInterceptorCallback> activityInterceptorCallbacks = this.mService.getActivityInterceptorCallbacks();
        com.android.server.wm.ActivityInterceptorCallback.ActivityInterceptorInfo interceptorInfo = getInterceptorInfo(null);
        for (int i3 = 0; i3 < activityInterceptorCallbacks.size(); i3++) {
            com.android.server.wm.ActivityInterceptorCallback.ActivityInterceptResult onInterceptActivityLaunch = activityInterceptorCallbacks.valueAt(i3).onInterceptActivityLaunch(interceptorInfo);
            if (onInterceptActivityLaunch != null) {
                this.mIntent = onInterceptActivityLaunch.getIntent();
                this.mActivityOptions = onInterceptActivityLaunch.getActivityOptions();
                this.mCallingPid = this.mRealCallingPid;
                this.mCallingUid = this.mRealCallingUid;
                if (onInterceptActivityLaunch.isActivityResolved()) {
                    return true;
                }
                this.mRInfo = this.mSupervisor.resolveIntent(this.mIntent, null, this.mUserId, 0, this.mRealCallingUid, this.mRealCallingPid);
                this.mAInfo = this.mSupervisor.resolveActivity(this.mIntent, this.mRInfo, this.mStartFlags, null);
                return true;
            }
        }
        return false;
    }

    private boolean hasCrossProfileAnimation() {
        return this.mActivityOptions != null && this.mActivityOptions.getAnimationType() == 12;
    }

    private android.app.ActivityOptions deferCrossProfileAppsAnimationIfNecessary() {
        if (hasCrossProfileAnimation()) {
            this.mActivityOptions = null;
            return android.app.ActivityOptions.makeOpenCrossProfileAppsAnimation();
        }
        return android.app.ActivityOptions.makeBasic();
    }

    private boolean interceptQuietProfileIfNeeded() {
        if (!this.mUserManager.isQuietModeEnabled(android.os.UserHandle.of(this.mUserId))) {
            return false;
        }
        this.mIntent = com.android.internal.app.UnlaunchableAppActivity.createInQuietModeDialogIntent(this.mUserId, createIntentSenderForOriginalIntent(this.mCallingUid, com.android.server.tare.AlarmManagerEconomicPolicy.ACTION_ALARM_WAKEUP_EXACT_ALLOW_WHILE_IDLE), this.mRInfo);
        this.mCallingPid = this.mRealCallingPid;
        this.mCallingUid = this.mRealCallingUid;
        this.mResolvedType = null;
        this.mRInfo = this.mSupervisor.resolveIntent(this.mIntent, this.mResolvedType, this.mUserManager.getProfileParent(this.mUserId).id, 0, this.mRealCallingUid, this.mRealCallingPid);
        this.mAInfo = this.mSupervisor.resolveActivity(this.mIntent, this.mRInfo, this.mStartFlags, null);
        return true;
    }

    private boolean interceptSuspendedByAdminPackage() {
        android.app.admin.DevicePolicyManagerInternal devicePolicyManagerInternal = (android.app.admin.DevicePolicyManagerInternal) com.android.server.LocalServices.getService(android.app.admin.DevicePolicyManagerInternal.class);
        if (devicePolicyManagerInternal == null) {
            return false;
        }
        this.mIntent = devicePolicyManagerInternal.createShowAdminSupportIntent(this.mUserId, true);
        this.mIntent.putExtra("android.app.extra.RESTRICTION", "policy_suspend_packages");
        this.mCallingPid = this.mRealCallingPid;
        this.mCallingUid = this.mRealCallingUid;
        this.mResolvedType = null;
        android.content.pm.UserInfo profileParent = this.mUserManager.getProfileParent(this.mUserId);
        if (profileParent != null) {
            this.mRInfo = this.mSupervisor.resolveIntent(this.mIntent, this.mResolvedType, profileParent.id, 0, this.mRealCallingUid, this.mRealCallingPid);
        } else {
            this.mRInfo = this.mSupervisor.resolveIntent(this.mIntent, this.mResolvedType, this.mUserId, 0, this.mRealCallingUid, this.mRealCallingPid);
        }
        this.mAInfo = this.mSupervisor.resolveActivity(this.mIntent, this.mRInfo, this.mStartFlags, null);
        return true;
    }

    private boolean interceptSuspendedPackageIfNeeded() {
        android.content.pm.PackageManagerInternal packageManagerInternalLocked;
        android.os.Bundle bundle;
        if (!isPackageSuspended() || (packageManagerInternalLocked = this.mService.getPackageManagerInternalLocked()) == null) {
            return false;
        }
        java.lang.String str = this.mAInfo.applicationInfo.packageName;
        android.content.pm.UserPackage suspendingPackage = packageManagerInternalLocked.getSuspendingPackage(str, this.mUserId);
        if (suspendingPackage != null && com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME.equals(suspendingPackage.packageName)) {
            return interceptSuspendedByAdminPackage();
        }
        android.content.pm.SuspendDialogInfo suspendedDialogInfo = packageManagerInternalLocked.getSuspendedDialogInfo(str, suspendingPackage, this.mUserId);
        if (hasCrossProfileAnimation()) {
            bundle = android.app.ActivityOptions.makeOpenCrossProfileAppsAnimation().toBundle();
        } else {
            bundle = null;
        }
        this.mIntent = com.android.internal.app.SuspendedAppActivity.createSuspendedAppInterceptIntent(str, suspendingPackage, suspendedDialogInfo, bundle, createIntentSenderForOriginalIntent(this.mCallingUid, 67108864), this.mUserId);
        this.mCallingPid = this.mRealCallingPid;
        this.mCallingUid = this.mRealCallingUid;
        this.mResolvedType = null;
        this.mRInfo = this.mSupervisor.resolveIntent(this.mIntent, this.mResolvedType, this.mUserId, 0, this.mRealCallingUid, this.mRealCallingPid);
        this.mAInfo = this.mSupervisor.resolveActivity(this.mIntent, this.mRInfo, this.mStartFlags, null);
        return true;
    }

    private boolean interceptLockTaskModeViolationPackageIfNeeded() {
        if (this.mAInfo == null || this.mAInfo.applicationInfo == null) {
            return false;
        }
        if (this.mService.getLockTaskController().isActivityAllowed(this.mUserId, this.mAInfo.applicationInfo.packageName, com.android.server.wm.ActivityRecord.getLockTaskLaunchMode(this.mAInfo, this.mActivityOptions))) {
            return false;
        }
        this.mIntent = com.android.internal.app.BlockedAppActivity.createIntent(this.mUserId, this.mAInfo.applicationInfo.packageName);
        this.mCallingPid = this.mRealCallingPid;
        this.mCallingUid = this.mRealCallingUid;
        this.mResolvedType = null;
        this.mRInfo = this.mSupervisor.resolveIntent(this.mIntent, this.mResolvedType, this.mUserId, 0, this.mRealCallingUid, this.mRealCallingPid);
        this.mAInfo = this.mSupervisor.resolveActivity(this.mIntent, this.mRInfo, this.mStartFlags, null);
        return true;
    }

    private boolean interceptLockedProfileIfNeeded() {
        com.android.server.wm.Task task;
        android.content.Intent interceptWithConfirmCredentialsIfNeeded = interceptWithConfirmCredentialsIfNeeded(this.mAInfo, this.mUserId);
        if (interceptWithConfirmCredentialsIfNeeded == null) {
            return false;
        }
        this.mIntent = interceptWithConfirmCredentialsIfNeeded;
        this.mCallingPid = this.mRealCallingPid;
        this.mCallingUid = this.mRealCallingUid;
        this.mResolvedType = null;
        com.android.server.wm.TaskFragment launchTaskFragment = getLaunchTaskFragment();
        if (this.mInTask != null) {
            this.mIntent.putExtra("android.intent.extra.TASK_ID", this.mInTask.mTaskId);
            this.mInTask = null;
        } else if (launchTaskFragment != null && (task = launchTaskFragment.getTask()) != null) {
            this.mIntent.putExtra("android.intent.extra.TASK_ID", task.mTaskId);
        }
        if (this.mActivityOptions == null) {
            this.mActivityOptions = android.app.ActivityOptions.makeBasic();
        }
        this.mRInfo = this.mSupervisor.resolveIntent(this.mIntent, this.mResolvedType, this.mUserManager.getProfileParent(this.mUserId).id, 0, this.mRealCallingUid, this.mRealCallingPid);
        this.mAInfo = this.mSupervisor.resolveActivity(this.mIntent, this.mRInfo, this.mStartFlags, null);
        return true;
    }

    private android.content.Intent interceptWithConfirmCredentialsIfNeeded(android.content.pm.ActivityInfo activityInfo, int i) {
        if (!this.mService.mAmInternal.shouldConfirmCredentials(i)) {
            return null;
        }
        if ((activityInfo.flags & 8388608) != 0 && (this.mUserManager.isUserUnlocked(i) || activityInfo.directBootAware)) {
            return null;
        }
        android.content.IntentSender createIntentSenderForOriginalIntent = createIntentSenderForOriginalIntent(this.mCallingUid, 1409286144);
        android.content.Intent createConfirmDeviceCredentialIntent = ((android.app.KeyguardManager) this.mServiceContext.getSystemService("keyguard")).createConfirmDeviceCredentialIntent(null, null, i, true);
        if (createConfirmDeviceCredentialIntent == null) {
            return null;
        }
        createConfirmDeviceCredentialIntent.setFlags(276840448);
        createConfirmDeviceCredentialIntent.putExtra("android.intent.extra.PACKAGE_NAME", activityInfo.packageName);
        createConfirmDeviceCredentialIntent.putExtra("android.intent.extra.INTENT", createIntentSenderForOriginalIntent);
        return createConfirmDeviceCredentialIntent;
    }

    private boolean interceptHarmfulAppIfNeeded() {
        try {
            java.lang.CharSequence harmfulAppWarning = this.mService.getPackageManager().getHarmfulAppWarning(this.mAInfo.packageName, this.mUserId);
            if (harmfulAppWarning == null) {
                return false;
            }
            this.mIntent = com.android.internal.app.HarmfulAppWarningActivity.createHarmfulAppWarningIntent(this.mServiceContext, this.mAInfo.packageName, createIntentSenderForOriginalIntent(this.mCallingUid, 1409286144), harmfulAppWarning);
            this.mCallingPid = this.mRealCallingPid;
            this.mCallingUid = this.mRealCallingUid;
            this.mResolvedType = null;
            this.mRInfo = this.mSupervisor.resolveIntent(this.mIntent, this.mResolvedType, this.mUserId, 0, this.mRealCallingUid, this.mRealCallingPid);
            this.mAInfo = this.mSupervisor.resolveActivity(this.mIntent, this.mRInfo, this.mStartFlags, null);
            return true;
        } catch (android.os.RemoteException | java.lang.IllegalArgumentException e) {
            return false;
        }
    }

    private boolean interceptHomeIfNeeded() {
        if (this.mPresumableLaunchDisplayArea == null || this.mService.mRootWindowContainer == null || !com.android.server.wm.ActivityRecord.isHomeIntent(this.mIntent) || !this.mIntent.hasCategory("android.intent.category.HOME") || this.mService.mRootWindowContainer.shouldPlacePrimaryHomeOnDisplay(this.mPresumableLaunchDisplayArea.getDisplayId()) || !this.mService.mRootWindowContainer.shouldPlaceSecondaryHomeOnDisplayArea(this.mPresumableLaunchDisplayArea)) {
            return false;
        }
        this.mIntent = (android.content.Intent) this.mService.mRootWindowContainer.resolveSecondaryHomeActivity(this.mUserId, this.mPresumableLaunchDisplayArea).second;
        this.mIntent.addFlags(268435456);
        this.mCallingPid = this.mRealCallingPid;
        this.mCallingUid = this.mRealCallingUid;
        this.mResolvedType = null;
        this.mRInfo = this.mSupervisor.resolveIntent(this.mIntent, this.mResolvedType, this.mUserId, 0, this.mRealCallingUid, this.mRealCallingPid);
        this.mAInfo = this.mSupervisor.resolveActivity(this.mIntent, this.mRInfo, this.mStartFlags, null);
        return true;
    }

    private boolean isPackageSuspended() {
        return (this.mAInfo == null || this.mAInfo.applicationInfo == null || (this.mAInfo.applicationInfo.flags & 1073741824) == 0) ? false : true;
    }

    void onActivityLaunched(android.app.TaskInfo taskInfo, final com.android.server.wm.ActivityRecord activityRecord) {
        android.util.SparseArray<com.android.server.wm.ActivityInterceptorCallback> activityInterceptorCallbacks = this.mService.getActivityInterceptorCallbacks();
        java.util.Objects.requireNonNull(activityRecord);
        com.android.server.wm.ActivityInterceptorCallback.ActivityInterceptorInfo interceptorInfo = getInterceptorInfo(new java.lang.Runnable() { // from class: com.android.server.wm.ActivityStartInterceptor$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.ActivityRecord.this.clearOptionsAnimationForSiblings();
            }
        });
        for (int i = 0; i < activityInterceptorCallbacks.size(); i++) {
            activityInterceptorCallbacks.valueAt(i).onActivityLaunched(taskInfo, activityRecord.info, interceptorInfo);
        }
    }

    private com.android.server.wm.ActivityInterceptorCallback.ActivityInterceptorInfo getInterceptorInfo(@android.annotation.Nullable java.lang.Runnable runnable) {
        return new com.android.server.wm.ActivityInterceptorCallback.ActivityInterceptorInfo.Builder(this.mCallingUid, this.mCallingPid, this.mRealCallingUid, this.mRealCallingPid, this.mUserId, this.mIntent, this.mRInfo, this.mAInfo).setResolvedType(this.mResolvedType).setCallingPackage(this.mCallingPackage).setCallingFeatureId(this.mCallingFeatureId).setCheckedOptions(this.mActivityOptions).setClearOptionsAnimationRunnable(runnable).build();
    }
}
