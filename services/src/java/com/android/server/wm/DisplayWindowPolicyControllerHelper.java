package com.android.server.wm;

/* loaded from: classes3.dex */
class DisplayWindowPolicyControllerHelper {
    private static final java.lang.String TAG = "DisplayWindowPolicyControllerHelper";
    private final com.android.server.wm.DisplayContent mDisplayContent;

    @android.annotation.Nullable
    private android.window.DisplayWindowPolicyController mDisplayWindowPolicyController;
    private com.android.server.wm.ActivityRecord mTopRunningActivity = null;
    private android.util.ArraySet<java.lang.Integer> mRunningUid = new android.util.ArraySet<>();

    DisplayWindowPolicyControllerHelper(com.android.server.wm.DisplayContent displayContent) {
        this.mDisplayContent = displayContent;
        this.mDisplayWindowPolicyController = this.mDisplayContent.mWmService.mDisplayManagerInternal.getDisplayWindowPolicyController(this.mDisplayContent.mDisplayId);
    }

    public boolean hasController() {
        return this.mDisplayWindowPolicyController != null;
    }

    public boolean canContainActivities(@android.annotation.NonNull java.util.List<android.content.pm.ActivityInfo> list, int i) {
        if (this.mDisplayWindowPolicyController == null) {
            for (int i2 = 0; i2 < list.size(); i2++) {
                if (hasDisplayCategory(list.get(i2))) {
                    return false;
                }
            }
            return true;
        }
        return this.mDisplayWindowPolicyController.canContainActivities(list, i);
    }

    public boolean canActivityBeLaunched(android.content.pm.ActivityInfo activityInfo, android.content.Intent intent, int i, int i2, boolean z) {
        if (this.mDisplayWindowPolicyController == null) {
            return !hasDisplayCategory(activityInfo);
        }
        return this.mDisplayWindowPolicyController.canActivityBeLaunched(activityInfo, intent, i, i2, z);
    }

    private boolean hasDisplayCategory(android.content.pm.ActivityInfo activityInfo) {
        if (activityInfo.requiredDisplayCategory != null) {
            android.util.Slog.d(TAG, java.lang.String.format("Checking activity launch with requiredDisplayCategory='%s' on display %d, which doesn't have a matching category.", activityInfo.requiredDisplayCategory, java.lang.Integer.valueOf(this.mDisplayContent.mDisplayId)));
            return true;
        }
        return false;
    }

    boolean keepActivityOnWindowFlagsChanged(android.content.pm.ActivityInfo activityInfo, int i, int i2, int i3, int i4) {
        if (this.mDisplayWindowPolicyController != null && this.mDisplayWindowPolicyController.isInterestedWindowFlags(i, i2)) {
            return this.mDisplayWindowPolicyController.keepActivityOnWindowFlagsChanged(activityInfo, i3, i4);
        }
        return true;
    }

    void onRunningActivityChanged() {
        if (this.mDisplayWindowPolicyController != null) {
            com.android.server.wm.ActivityRecord topActivity = this.mDisplayContent.getTopActivity(false, true);
            if (topActivity != this.mTopRunningActivity) {
                this.mTopRunningActivity = topActivity;
                if (topActivity == null) {
                    this.mDisplayWindowPolicyController.onTopActivityChanged((android.content.ComponentName) null, -1, com.android.server.am.ProcessList.INVALID_ADJ);
                } else {
                    this.mDisplayWindowPolicyController.onTopActivityChanged(topActivity.info.getComponentName(), topActivity.info.applicationInfo.uid, topActivity.mUserId);
                }
            }
            final boolean[] zArr = {false};
            final android.util.ArraySet<java.lang.Integer> arraySet = new android.util.ArraySet<>();
            this.mDisplayContent.forAllActivities(new java.util.function.Consumer() { // from class: com.android.server.wm.DisplayWindowPolicyControllerHelper$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.wm.DisplayWindowPolicyControllerHelper.lambda$onRunningActivityChanged$0(zArr, arraySet, (com.android.server.wm.ActivityRecord) obj);
                }
            });
            if (zArr[0] || this.mRunningUid.size() != arraySet.size()) {
                this.mRunningUid = arraySet;
                this.mDisplayWindowPolicyController.onRunningAppsChanged(arraySet);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onRunningActivityChanged$0(boolean[] zArr, android.util.ArraySet arraySet, com.android.server.wm.ActivityRecord activityRecord) {
        if (!activityRecord.finishing) {
            zArr[0] = arraySet.add(java.lang.Integer.valueOf(activityRecord.getUid())) | zArr[0];
        }
    }

    public final boolean isWindowingModeSupported(int i) {
        if (this.mDisplayWindowPolicyController == null) {
            return true;
        }
        return this.mDisplayWindowPolicyController.isWindowingModeSupported(i);
    }

    public final boolean canShowTasksInHostDeviceRecents() {
        if (this.mDisplayWindowPolicyController == null) {
            return true;
        }
        return this.mDisplayWindowPolicyController.canShowTasksInHostDeviceRecents();
    }

    public final boolean isEnteringPipAllowed(int i) {
        if (this.mDisplayWindowPolicyController == null) {
            return true;
        }
        return this.mDisplayWindowPolicyController.isEnteringPipAllowed(i);
    }

    @android.annotation.Nullable
    public android.content.ComponentName getCustomHomeComponent() {
        if (this.mDisplayWindowPolicyController == null) {
            return null;
        }
        return this.mDisplayWindowPolicyController.getCustomHomeComponent();
    }

    void dump(java.lang.String str, java.io.PrintWriter printWriter) {
        if (this.mDisplayWindowPolicyController != null) {
            printWriter.println();
            this.mDisplayWindowPolicyController.dump(str, printWriter);
        }
    }
}
