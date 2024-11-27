package com.android.server.wm;

/* loaded from: classes3.dex */
public class SafeActivityOptions {
    private static final java.lang.String TAG = "ActivityTaskManager";

    @android.annotation.Nullable
    private android.app.ActivityOptions mCallerOptions;
    private final int mOriginalCallingPid;
    private final int mOriginalCallingUid;

    @android.annotation.Nullable
    private final android.app.ActivityOptions mOriginalOptions;
    private int mRealCallingPid;
    private int mRealCallingUid;

    public static com.android.server.wm.SafeActivityOptions fromBundle(android.os.Bundle bundle) {
        if (bundle != null) {
            return new com.android.server.wm.SafeActivityOptions(android.app.ActivityOptions.fromBundle(bundle));
        }
        return null;
    }

    static com.android.server.wm.SafeActivityOptions fromBundle(android.os.Bundle bundle, int i, int i2) {
        if (bundle != null) {
            return new com.android.server.wm.SafeActivityOptions(android.app.ActivityOptions.fromBundle(bundle), i, i2);
        }
        return null;
    }

    public SafeActivityOptions(@android.annotation.Nullable android.app.ActivityOptions activityOptions) {
        this.mOriginalCallingPid = android.os.Binder.getCallingPid();
        this.mOriginalCallingUid = android.os.Binder.getCallingUid();
        this.mOriginalOptions = activityOptions;
    }

    private SafeActivityOptions(@android.annotation.Nullable android.app.ActivityOptions activityOptions, int i, int i2) {
        this.mOriginalCallingPid = i;
        this.mOriginalCallingUid = i2;
        this.mOriginalOptions = activityOptions;
    }

    @android.annotation.Nullable
    com.android.server.wm.SafeActivityOptions selectiveCloneLaunchOptions() {
        android.app.ActivityOptions cloneLaunchingOptions = cloneLaunchingOptions(this.mOriginalOptions);
        android.app.ActivityOptions cloneLaunchingOptions2 = cloneLaunchingOptions(this.mCallerOptions);
        if (cloneLaunchingOptions == null && cloneLaunchingOptions2 == null) {
            return null;
        }
        com.android.server.wm.SafeActivityOptions safeActivityOptions = new com.android.server.wm.SafeActivityOptions(cloneLaunchingOptions, this.mOriginalCallingPid, this.mOriginalCallingUid);
        safeActivityOptions.mCallerOptions = cloneLaunchingOptions2;
        safeActivityOptions.mRealCallingPid = this.mRealCallingPid;
        safeActivityOptions.mRealCallingUid = this.mRealCallingUid;
        return safeActivityOptions;
    }

    private android.app.ActivityOptions cloneLaunchingOptions(android.app.ActivityOptions activityOptions) {
        if (activityOptions == null) {
            return null;
        }
        return android.app.ActivityOptions.makeBasic().setLaunchTaskDisplayArea(activityOptions.getLaunchTaskDisplayArea()).setLaunchDisplayId(activityOptions.getLaunchDisplayId()).setCallerDisplayId(activityOptions.getCallerDisplayId()).setLaunchRootTask(activityOptions.getLaunchRootTask()).setPendingIntentBackgroundActivityStartMode(activityOptions.getPendingIntentBackgroundActivityStartMode()).setPendingIntentCreatorBackgroundActivityStartMode(activityOptions.getPendingIntentCreatorBackgroundActivityStartMode()).setRemoteTransition(activityOptions.getRemoteTransition());
    }

    public void setCallerOptions(@android.annotation.Nullable android.app.ActivityOptions activityOptions) {
        this.mRealCallingPid = android.os.Binder.getCallingPid();
        this.mRealCallingUid = android.os.Binder.getCallingUid();
        this.mCallerOptions = activityOptions;
    }

    android.app.ActivityOptions getOptions(com.android.server.wm.ActivityRecord activityRecord) throws java.lang.SecurityException {
        return getOptions(activityRecord.intent, activityRecord.info, activityRecord.app, activityRecord.mTaskSupervisor);
    }

    android.app.ActivityOptions getOptions(com.android.server.wm.ActivityTaskSupervisor activityTaskSupervisor) throws java.lang.SecurityException {
        return getOptions(null, null, null, activityTaskSupervisor);
    }

    android.app.ActivityOptions getOptions(@android.annotation.Nullable android.content.Intent intent, @android.annotation.Nullable android.content.pm.ActivityInfo activityInfo, @android.annotation.Nullable com.android.server.wm.WindowProcessController windowProcessController, com.android.server.wm.ActivityTaskSupervisor activityTaskSupervisor) throws java.lang.SecurityException {
        if (this.mOriginalOptions != null) {
            checkPermissions(intent, activityInfo, windowProcessController, activityTaskSupervisor, this.mOriginalOptions, this.mOriginalCallingPid, this.mOriginalCallingUid);
            setCallingPidUidForRemoteAnimationAdapter(this.mOriginalOptions, this.mOriginalCallingPid, this.mOriginalCallingUid);
        }
        if (this.mCallerOptions != null) {
            checkPermissions(intent, activityInfo, windowProcessController, activityTaskSupervisor, this.mCallerOptions, this.mRealCallingPid, this.mRealCallingUid);
            setCallingPidUidForRemoteAnimationAdapter(this.mCallerOptions, this.mRealCallingPid, this.mRealCallingUid);
        }
        return mergeActivityOptions(this.mOriginalOptions, this.mCallerOptions);
    }

    private void setCallingPidUidForRemoteAnimationAdapter(android.app.ActivityOptions activityOptions, int i, int i2) {
        android.view.RemoteAnimationAdapter remoteAnimationAdapter = activityOptions.getRemoteAnimationAdapter();
        if (remoteAnimationAdapter == null) {
            return;
        }
        if (i == com.android.server.wm.WindowManagerService.MY_PID) {
            android.util.Slog.wtf(TAG, "Safe activity options constructed after clearing calling id");
        } else {
            remoteAnimationAdapter.setCallingPidUid(i, i2);
        }
    }

    android.app.ActivityOptions getOriginalOptions() {
        return this.mOriginalOptions;
    }

    android.os.Bundle popAppVerificationBundle() {
        if (this.mOriginalOptions != null) {
            return this.mOriginalOptions.popAppVerificationBundle();
        }
        return null;
    }

    private void abort() {
        if (this.mOriginalOptions != null) {
            android.app.ActivityOptions.abort(this.mOriginalOptions);
        }
        if (this.mCallerOptions != null) {
            android.app.ActivityOptions.abort(this.mCallerOptions);
        }
    }

    static void abort(@android.annotation.Nullable com.android.server.wm.SafeActivityOptions safeActivityOptions) {
        if (safeActivityOptions != null) {
            safeActivityOptions.abort();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    android.app.ActivityOptions mergeActivityOptions(@android.annotation.Nullable android.app.ActivityOptions activityOptions, @android.annotation.Nullable android.app.ActivityOptions activityOptions2) {
        if (activityOptions == null) {
            return activityOptions2;
        }
        if (activityOptions2 == null) {
            return activityOptions;
        }
        android.os.Bundle bundle = activityOptions.toBundle();
        bundle.putAll(activityOptions2.toBundle());
        return android.app.ActivityOptions.fromBundle(bundle);
    }

    private void checkPermissions(@android.annotation.Nullable android.content.Intent intent, @android.annotation.Nullable android.content.pm.ActivityInfo activityInfo, @android.annotation.Nullable com.android.server.wm.WindowProcessController windowProcessController, com.android.server.wm.ActivityTaskSupervisor activityTaskSupervisor, android.app.ActivityOptions activityOptions, int i, int i2) {
        boolean z;
        if ((activityOptions.getLaunchTaskId() != -1 || activityOptions.getDisableStartingWindow()) && !activityTaskSupervisor.mRecentTasks.isCallerRecents(i2) && com.android.server.wm.ActivityTaskManagerService.checkPermission("android.permission.START_TASKS_FROM_RECENTS", i, i2) == -1) {
            java.lang.String str = "Permission Denial: starting " + getIntentString(intent) + " from " + windowProcessController + " (pid=" + i + ", uid=" + i2 + ") with launchTaskId=" + activityOptions.getLaunchTaskId();
            android.util.Slog.w(TAG, str);
            throw new java.lang.SecurityException(str);
        }
        if (!activityOptions.getTransientLaunch() || activityTaskSupervisor.mRecentTasks.isCallerRecents(i2) || com.android.server.wm.ActivityTaskManagerService.checkPermission("android.permission.MANAGE_ACTIVITY_TASKS", i, i2) != -1) {
            com.android.server.wm.TaskDisplayArea launchTaskDisplayArea = getLaunchTaskDisplayArea(activityOptions, activityTaskSupervisor);
            if (activityInfo != null && launchTaskDisplayArea != null && !activityTaskSupervisor.isCallerAllowedToLaunchOnTaskDisplayArea(i, i2, launchTaskDisplayArea, activityInfo)) {
                java.lang.String str2 = "Permission Denial: starting " + getIntentString(intent) + " from " + windowProcessController + " (pid=" + i + ", uid=" + i2 + ") with launchTaskDisplayArea=" + launchTaskDisplayArea;
                android.util.Slog.w(TAG, str2);
                throw new java.lang.SecurityException(str2);
            }
            int launchDisplayId = activityOptions.getLaunchDisplayId();
            if (activityInfo != null && launchDisplayId != -1 && !activityTaskSupervisor.isCallerAllowedToLaunchOnDisplay(i, i2, launchDisplayId, activityInfo)) {
                java.lang.String str3 = "Permission Denial: starting " + getIntentString(intent) + " from " + windowProcessController + " (pid=" + i + ", uid=" + i2 + ") with launchDisplayId=" + launchDisplayId;
                android.util.Slog.w(TAG, str3);
                throw new java.lang.SecurityException(str3);
            }
            boolean lockTaskMode = activityOptions.getLockTaskMode();
            if (activityInfo != null && lockTaskMode && !activityTaskSupervisor.mService.getLockTaskController().isPackageAllowlisted(android.os.UserHandle.getUserId(i2), activityInfo.packageName)) {
                java.lang.String str4 = "Permission Denial: starting " + getIntentString(intent) + " from " + windowProcessController + " (pid=" + i + ", uid=" + i2 + ") with lockTaskMode=true";
                android.util.Slog.w(TAG, str4);
                throw new java.lang.SecurityException(str4);
            }
            boolean overrideTaskTransition = activityOptions.getOverrideTaskTransition();
            if (activityInfo != null && overrideTaskTransition && com.android.server.wm.ActivityTaskManagerService.checkPermission("android.permission.START_TASKS_FROM_RECENTS", i, i2) != 0 && !isAssistant(activityTaskSupervisor.mService, i2)) {
                java.lang.String str5 = "Permission Denial: starting " + getIntentString(intent) + " from " + windowProcessController + " (pid=" + i + ", uid=" + i2 + ") with overrideTaskTransition=true";
                android.util.Slog.w(TAG, str5);
                throw new java.lang.SecurityException(str5);
            }
            boolean dismissKeyguardIfInsecure = activityOptions.getDismissKeyguardIfInsecure();
            if (activityInfo != null && dismissKeyguardIfInsecure && com.android.server.wm.ActivityTaskManagerService.checkPermission("android.permission.CONTROL_KEYGUARD", i, i2) != 0) {
                java.lang.String str6 = "Permission Denial: starting " + getIntentString(intent) + " from " + windowProcessController + " (pid=" + i + ", uid=" + i2 + ") with dismissKeyguardIfInsecure=true";
                android.util.Slog.w(TAG, str6);
                throw new java.lang.SecurityException(str6);
            }
            if (activityOptions.getRemoteAnimationAdapter() != null) {
                com.android.server.wm.ActivityTaskManagerService activityTaskManagerService = activityTaskSupervisor.mService;
                if (com.android.server.wm.ActivityTaskManagerService.checkPermission("android.permission.CONTROL_REMOTE_APP_TRANSITION_ANIMATIONS", i, i2) != 0) {
                    java.lang.String str7 = "Permission Denial: starting " + getIntentString(intent) + " from " + windowProcessController + " (pid=" + i + ", uid=" + i2 + ") with remoteAnimationAdapter";
                    android.util.Slog.w(TAG, str7);
                    throw new java.lang.SecurityException(str7);
                }
            }
            if (activityOptions.getRemoteTransition() != null) {
                com.android.server.wm.ActivityTaskManagerService activityTaskManagerService2 = activityTaskSupervisor.mService;
                if (com.android.server.wm.ActivityTaskManagerService.checkPermission("android.permission.CONTROL_REMOTE_APP_TRANSITION_ANIMATIONS", i, i2) != 0) {
                    java.lang.String str8 = "Permission Denial: starting " + getIntentString(intent) + " from " + windowProcessController + " (pid=" + i + ", uid=" + i2 + ") with remoteTransition";
                    android.util.Slog.w(TAG, str8);
                    throw new java.lang.SecurityException(str8);
                }
            }
            if (activityOptions.getLaunchedFromBubble() && !isSystemOrSystemUI(i, i2)) {
                java.lang.String str9 = "Permission Denial: starting " + getIntentString(intent) + " from " + windowProcessController + " (pid=" + i + ", uid=" + i2 + ") with launchedFromBubble=true";
                android.util.Slog.w(TAG, str9);
                throw new java.lang.SecurityException(str9);
            }
            int launchActivityType = activityOptions.getLaunchActivityType();
            if (launchActivityType != 0 && !isSystemOrSystemUI(i, i2)) {
                if (launchActivityType == 4 && isAssistant(activityTaskSupervisor.mService, i2)) {
                    z = true;
                } else {
                    z = false;
                }
                if (!z) {
                    java.lang.String str10 = "Permission Denial: starting " + getIntentString(intent) + " from " + windowProcessController + " (pid=" + i + ", uid=" + i2 + ") with launchActivityType=" + android.app.WindowConfiguration.activityTypeToString(activityOptions.getLaunchActivityType());
                    android.util.Slog.w(TAG, str10);
                    throw new java.lang.SecurityException(str10);
                }
                return;
            }
            return;
        }
        java.lang.String str11 = "Permission Denial: starting transient launch from " + windowProcessController + ", pid=" + i + ", uid=" + i2;
        android.util.Slog.w(TAG, str11);
        throw new java.lang.SecurityException(str11);
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.wm.TaskDisplayArea getLaunchTaskDisplayArea(android.app.ActivityOptions activityOptions, com.android.server.wm.ActivityTaskSupervisor activityTaskSupervisor) {
        android.window.WindowContainerToken launchTaskDisplayArea = activityOptions.getLaunchTaskDisplayArea();
        com.android.server.wm.TaskDisplayArea taskDisplayArea = launchTaskDisplayArea != null ? (com.android.server.wm.TaskDisplayArea) com.android.server.wm.WindowContainer.fromBinder(launchTaskDisplayArea.asBinder()) : null;
        if (taskDisplayArea != null) {
            return taskDisplayArea;
        }
        final int launchTaskDisplayAreaFeatureId = activityOptions.getLaunchTaskDisplayAreaFeatureId();
        if (launchTaskDisplayAreaFeatureId != -1) {
            com.android.server.wm.DisplayContent displayContent = activityTaskSupervisor.mRootWindowContainer.getDisplayContent(activityOptions.getLaunchDisplayId() == -1 ? 0 : activityOptions.getLaunchDisplayId());
            if (displayContent != null) {
                return (com.android.server.wm.TaskDisplayArea) displayContent.getItemFromTaskDisplayAreas(new java.util.function.Function() { // from class: com.android.server.wm.SafeActivityOptions$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final java.lang.Object apply(java.lang.Object obj) {
                        com.android.server.wm.TaskDisplayArea lambda$getLaunchTaskDisplayArea$0;
                        lambda$getLaunchTaskDisplayArea$0 = com.android.server.wm.SafeActivityOptions.lambda$getLaunchTaskDisplayArea$0(launchTaskDisplayAreaFeatureId, (com.android.server.wm.TaskDisplayArea) obj);
                        return lambda$getLaunchTaskDisplayArea$0;
                    }
                });
            }
            return taskDisplayArea;
        }
        return taskDisplayArea;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.server.wm.TaskDisplayArea lambda$getLaunchTaskDisplayArea$0(int i, com.android.server.wm.TaskDisplayArea taskDisplayArea) {
        if (taskDisplayArea.mFeatureId == i) {
            return taskDisplayArea;
        }
        return null;
    }

    private boolean isAssistant(com.android.server.wm.ActivityTaskManagerService activityTaskManagerService, int i) {
        if (activityTaskManagerService.mActiveVoiceInteractionServiceComponent == null) {
            return false;
        }
        return android.app.AppGlobals.getPackageManager().getPackageUid(activityTaskManagerService.mActiveVoiceInteractionServiceComponent.getPackageName(), 268435456L, android.os.UserHandle.getUserId(i)) == i;
    }

    private boolean isSystemOrSystemUI(int i, int i2) {
        return i2 == 1000 || com.android.server.wm.ActivityTaskManagerService.checkPermission("android.permission.STATUS_BAR_SERVICE", i, i2) == 0;
    }

    private java.lang.String getIntentString(android.content.Intent intent) {
        return intent != null ? intent.toString() : "(no intent)";
    }
}
