package com.android.server.wm;

/* loaded from: classes3.dex */
class TaskLaunchParamsModifier implements com.android.server.wm.LaunchParamsController.LaunchParamsModifier {
    private static final int BOUNDS_CONFLICT_THRESHOLD = 4;
    private static final int CASCADING_OFFSET_DP = 75;
    private static final boolean DEBUG = false;
    private static final int EPSILON = 2;
    private static final int MINIMAL_STEP = 1;
    private static final int STEP_DENOMINATOR = 16;
    private static final java.lang.String TAG = "ActivityTaskManager";
    private java.lang.StringBuilder mLogBuilder;
    private final com.android.server.wm.ActivityTaskSupervisor mSupervisor;
    private com.android.server.wm.TaskDisplayArea mTmpDisplayArea;
    private final android.graphics.Rect mTmpBounds = new android.graphics.Rect();
    private final android.graphics.Rect mTmpStableBounds = new android.graphics.Rect();
    private final int[] mTmpDirections = new int[2];

    TaskLaunchParamsModifier(com.android.server.wm.ActivityTaskSupervisor activityTaskSupervisor) {
        this.mSupervisor = activityTaskSupervisor;
    }

    @Override // com.android.server.wm.LaunchParamsController.LaunchParamsModifier
    public int onCalculate(@android.annotation.Nullable com.android.server.wm.Task task, @android.annotation.Nullable android.content.pm.ActivityInfo.WindowLayout windowLayout, @android.annotation.Nullable com.android.server.wm.ActivityRecord activityRecord, @android.annotation.Nullable com.android.server.wm.ActivityRecord activityRecord2, @android.annotation.Nullable android.app.ActivityOptions activityOptions, @android.annotation.Nullable com.android.server.wm.ActivityStarter.Request request, int i, com.android.server.wm.LaunchParamsController.LaunchParams launchParams, com.android.server.wm.LaunchParamsController.LaunchParams launchParams2) {
        initLogBuilder(task, activityRecord);
        int calculate = calculate(task, windowLayout, activityRecord, activityRecord2, activityOptions, request, i, launchParams, launchParams2);
        outputLog();
        return calculate;
    }

    /* JADX WARN: Removed duplicated region for block: B:120:0x025e  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0293  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x01c3 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x01c5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int calculate(@android.annotation.Nullable com.android.server.wm.Task task, @android.annotation.Nullable android.content.pm.ActivityInfo.WindowLayout windowLayout, @android.annotation.Nullable com.android.server.wm.ActivityRecord activityRecord, @android.annotation.Nullable com.android.server.wm.ActivityRecord activityRecord2, @android.annotation.Nullable android.app.ActivityOptions activityOptions, @android.annotation.Nullable com.android.server.wm.ActivityStarter.Request request, int i, com.android.server.wm.LaunchParamsController.LaunchParams launchParams, com.android.server.wm.LaunchParamsController.LaunchParams launchParams2) {
        com.android.server.wm.ActivityRecord activityRecord3;
        boolean z;
        boolean z2;
        boolean z3;
        com.android.server.wm.DisplayContent displayContent;
        com.android.server.wm.TaskDisplayArea taskDisplayArea;
        int i2;
        int i3;
        boolean z4;
        int i4;
        int i5;
        com.android.server.wm.TaskDisplayArea taskDisplayArea2;
        boolean z5;
        if (task != null) {
            activityRecord3 = task.getRootActivity() == null ? activityRecord : task.getRootActivity();
        } else {
            activityRecord3 = activityRecord;
        }
        if (activityRecord3 == null && i != 0) {
            return 0;
        }
        com.android.server.wm.TaskDisplayArea preferredLaunchTaskDisplayArea = getPreferredLaunchTaskDisplayArea(task, activityOptions, activityRecord2, launchParams, activityRecord, request);
        launchParams2.mPreferredTaskDisplayArea = preferredLaunchTaskDisplayArea;
        com.android.server.wm.DisplayContent displayContent2 = preferredLaunchTaskDisplayArea.mDisplayContent;
        if (i == 0) {
            return 2;
        }
        int launchWindowingMode = activityOptions != null ? activityOptions.getLaunchWindowingMode() : 0;
        if (launchWindowingMode == 0 && canInheritWindowingModeFromSource(displayContent2, preferredLaunchTaskDisplayArea, activityRecord2)) {
            launchWindowingMode = activityRecord2.getTask().getWindowingMode();
        }
        if (launchWindowingMode == 0 && task != null && task.getTaskDisplayArea() == preferredLaunchTaskDisplayArea && !task.getRootTask().mReparentLeafTaskIfRelaunch) {
            launchWindowingMode = task.getWindowingMode();
        }
        boolean canCalculateBoundsForFullscreenTask = canCalculateBoundsForFullscreenTask(preferredLaunchTaskDisplayArea, launchWindowingMode);
        boolean canApplyFreeformWindowPolicy = canApplyFreeformWindowPolicy(preferredLaunchTaskDisplayArea, launchWindowingMode);
        boolean z6 = windowLayout != null && (canApplyFreeformWindowPolicy || canCalculateBoundsForFullscreenTask);
        if (this.mSupervisor.canUseActivityOptionsLaunchBounds(activityOptions) && (canApplyFreeformWindowPolicy || canApplyPipWindowPolicy(launchWindowingMode) || canCalculateBoundsForFullscreenTask)) {
            if (launchWindowingMode == 0 && canApplyFreeformWindowPolicy) {
                launchWindowingMode = 5;
            }
            launchParams2.mBounds.set(activityOptions.getLaunchBounds());
            z = false;
            z2 = true;
        } else if (z6) {
            this.mTmpBounds.set(launchParams.mBounds);
            getLayoutBounds(preferredLaunchTaskDisplayArea, activityRecord3, windowLayout, this.mTmpBounds);
            if (!this.mTmpBounds.isEmpty()) {
                if (canApplyFreeformWindowPolicy) {
                    launchWindowingMode = 5;
                }
                launchParams2.mBounds.set(this.mTmpBounds);
                z2 = true;
                z = true;
            }
            z2 = false;
            z = false;
        } else {
            if (launchWindowingMode == 6 && activityOptions != null && activityOptions.getLaunchBounds() != null) {
                launchParams2.mBounds.set(activityOptions.getLaunchBounds());
                z = false;
                z2 = true;
            }
            z2 = false;
            z = false;
        }
        if (!launchParams.isEmpty() && !z2 && (launchParams.mPreferredTaskDisplayArea == null || launchParams.mPreferredTaskDisplayArea.getDisplayId() == displayContent2.getDisplayId())) {
            if (launchParams.hasWindowingMode() && preferredLaunchTaskDisplayArea.inFreeformWindowingMode()) {
                launchWindowingMode = launchParams.mWindowingMode;
                z5 = launchWindowingMode != 5;
            } else {
                z5 = false;
            }
            if (launchParams.mBounds.isEmpty()) {
                z3 = z5;
            } else {
                launchParams2.mBounds.set(launchParams.mBounds);
                z3 = true;
            }
        } else {
            z3 = false;
        }
        if (!preferredLaunchTaskDisplayArea.inFreeformWindowingMode()) {
            displayContent = displayContent2;
            taskDisplayArea = preferredLaunchTaskDisplayArea;
            i2 = 1;
        } else if (launchWindowingMode == 2) {
            displayContent = displayContent2;
            taskDisplayArea = preferredLaunchTaskDisplayArea;
            i2 = 1;
        } else if (activityRecord3.isResizeable()) {
            displayContent = displayContent2;
            taskDisplayArea = preferredLaunchTaskDisplayArea;
            i2 = 1;
        } else {
            if (shouldLaunchUnresizableAppInFreeform(activityRecord3, preferredLaunchTaskDisplayArea, activityOptions)) {
                if (!launchParams2.mBounds.isEmpty()) {
                    displayContent = displayContent2;
                    taskDisplayArea = preferredLaunchTaskDisplayArea;
                    i2 = 1;
                    z4 = false;
                    i3 = 5;
                } else {
                    i2 = 1;
                    i3 = 5;
                    displayContent = displayContent2;
                    taskDisplayArea = preferredLaunchTaskDisplayArea;
                    getTaskBounds(activityRecord3, preferredLaunchTaskDisplayArea, windowLayout, 5, z2, launchParams2.mBounds);
                    z4 = true;
                }
            } else {
                displayContent = displayContent2;
                taskDisplayArea = preferredLaunchTaskDisplayArea;
                i2 = 1;
                launchParams2.mBounds.setEmpty();
                i3 = 1;
                z4 = false;
            }
            launchParams2.mWindowingMode = (i3 == taskDisplayArea.getWindowingMode() || shouldUpdateExistingTaskWindowingMode(task, i3)) ? i3 : 0;
            if (i != i2) {
                return 2;
            }
            final int windowingMode = i3 != 0 ? i3 : taskDisplayArea.getWindowingMode();
            if (activityOptions == null || (activityOptions.getLaunchTaskDisplayArea() == null && activityOptions.getLaunchTaskDisplayAreaFeatureId() == -1)) {
                final int resolveActivityType = this.mSupervisor.mRootWindowContainer.resolveActivityType(activityRecord3, activityOptions, task);
                displayContent.forAllTaskDisplayAreas(new java.util.function.Predicate() { // from class: com.android.server.wm.TaskLaunchParamsModifier$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(java.lang.Object obj) {
                        boolean lambda$calculate$0;
                        lambda$calculate$0 = com.android.server.wm.TaskLaunchParamsModifier.this.lambda$calculate$0(windowingMode, resolveActivityType, (com.android.server.wm.TaskDisplayArea) obj);
                        return lambda$calculate$0;
                    }
                });
                if (this.mTmpDisplayArea != null) {
                    com.android.server.wm.TaskDisplayArea taskDisplayArea3 = taskDisplayArea;
                    if (this.mTmpDisplayArea == taskDisplayArea3) {
                        i4 = windowingMode;
                        taskDisplayArea = taskDisplayArea3;
                    } else {
                        launchParams2.mWindowingMode = i3 == this.mTmpDisplayArea.getWindowingMode() ? 0 : i3;
                        if (z) {
                            launchParams2.mBounds.setEmpty();
                            getLayoutBounds(this.mTmpDisplayArea, activityRecord3, windowLayout, launchParams2.mBounds);
                            z2 = !launchParams2.mBounds.isEmpty();
                            i4 = windowingMode;
                            taskDisplayArea = taskDisplayArea3;
                            i5 = 2;
                        } else if (!z4) {
                            i4 = windowingMode;
                            taskDisplayArea = taskDisplayArea3;
                            i5 = 2;
                        } else {
                            launchParams2.mBounds.setEmpty();
                            i4 = windowingMode;
                            taskDisplayArea = taskDisplayArea3;
                            i5 = 2;
                            getTaskBounds(activityRecord3, this.mTmpDisplayArea, windowLayout, i3, z2, launchParams2.mBounds);
                        }
                        if (this.mTmpDisplayArea != null) {
                            taskDisplayArea2 = taskDisplayArea;
                        } else {
                            taskDisplayArea2 = this.mTmpDisplayArea;
                            this.mTmpDisplayArea = null;
                            appendLog("overridden-display-area=[" + android.app.WindowConfiguration.activityTypeToString(resolveActivityType) + ", " + android.app.WindowConfiguration.windowingModeToString(i4) + ", " + taskDisplayArea2 + "]");
                        }
                    }
                } else {
                    i4 = windowingMode;
                }
                i5 = 2;
                if (this.mTmpDisplayArea != null) {
                }
            } else {
                taskDisplayArea2 = taskDisplayArea;
                i4 = windowingMode;
                i5 = 2;
            }
            appendLog("display-area=" + taskDisplayArea2);
            launchParams2.mPreferredTaskDisplayArea = taskDisplayArea2;
            if (i == i5) {
                return i5;
            }
            if (z3) {
                if (i4 == 5) {
                    if (launchParams.mPreferredTaskDisplayArea != taskDisplayArea2) {
                        adjustBoundsToFitInDisplayArea(taskDisplayArea2, windowLayout, launchParams2.mBounds);
                    }
                    adjustBoundsToAvoidConflictInDisplayArea(taskDisplayArea2, launchParams2.mBounds);
                }
            } else {
                int i6 = i4;
                if (activityRecord2 != null && activityRecord2.inFreeformWindowingMode() && i6 == 5 && launchParams2.mBounds.isEmpty() && activityRecord2.getDisplayArea() == taskDisplayArea2) {
                    cascadeBounds(activityRecord2.getConfiguration().windowConfiguration.getBounds(), taskDisplayArea2, launchParams2.mBounds);
                }
                getTaskBounds(activityRecord3, taskDisplayArea2, windowLayout, i6, z2, launchParams2.mBounds);
            }
            return i5;
        }
        i3 = launchWindowingMode;
        z4 = false;
        launchParams2.mWindowingMode = (i3 == taskDisplayArea.getWindowingMode() || shouldUpdateExistingTaskWindowingMode(task, i3)) ? i3 : 0;
        if (i != i2) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$calculate$0(int i, int i2, com.android.server.wm.TaskDisplayArea taskDisplayArea) {
        if (taskDisplayArea.getLaunchRootTask(i, i2, null, null, 0) == null) {
            return false;
        }
        this.mTmpDisplayArea = taskDisplayArea;
        return true;
    }

    private boolean shouldUpdateExistingTaskWindowingMode(com.android.server.wm.Task task, int i) {
        return (task == null || task.getRequestedOverrideWindowingMode() == 0 || task.getRequestedOverrideWindowingMode() == 2 || i == task.getRequestedOverrideWindowingMode()) ? false : true;
    }

    private com.android.server.wm.TaskDisplayArea getPreferredLaunchTaskDisplayArea(@android.annotation.Nullable com.android.server.wm.Task task, @android.annotation.Nullable android.app.ActivityOptions activityOptions, @android.annotation.Nullable com.android.server.wm.ActivityRecord activityRecord, @android.annotation.Nullable com.android.server.wm.LaunchParamsController.LaunchParams launchParams, @android.annotation.Nullable com.android.server.wm.ActivityRecord activityRecord2, @android.annotation.Nullable com.android.server.wm.ActivityStarter.Request request) {
        android.window.WindowContainerToken windowContainerToken;
        com.android.server.wm.TaskDisplayArea taskDisplayArea;
        com.android.server.wm.DisplayContent displayContent;
        final int launchTaskDisplayAreaFeatureId;
        com.android.server.wm.Task task2 = null;
        if (activityOptions == null) {
            windowContainerToken = null;
        } else {
            windowContainerToken = activityOptions.getLaunchTaskDisplayArea();
        }
        if (windowContainerToken == null) {
            taskDisplayArea = null;
        } else {
            taskDisplayArea = (com.android.server.wm.TaskDisplayArea) com.android.server.wm.WindowContainer.fromBinder(windowContainerToken.asBinder());
        }
        if (taskDisplayArea == null && activityOptions != null && (launchTaskDisplayAreaFeatureId = activityOptions.getLaunchTaskDisplayAreaFeatureId()) != -1) {
            com.android.server.wm.DisplayContent displayContent2 = this.mSupervisor.mRootWindowContainer.getDisplayContent(activityOptions.getLaunchDisplayId() == -1 ? 0 : activityOptions.getLaunchDisplayId());
            if (displayContent2 != null) {
                taskDisplayArea = (com.android.server.wm.TaskDisplayArea) displayContent2.getItemFromTaskDisplayAreas(new java.util.function.Function() { // from class: com.android.server.wm.TaskLaunchParamsModifier$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final java.lang.Object apply(java.lang.Object obj) {
                        com.android.server.wm.TaskDisplayArea lambda$getPreferredLaunchTaskDisplayArea$1;
                        lambda$getPreferredLaunchTaskDisplayArea$1 = com.android.server.wm.TaskLaunchParamsModifier.lambda$getPreferredLaunchTaskDisplayArea$1(launchTaskDisplayAreaFeatureId, (com.android.server.wm.TaskDisplayArea) obj);
                        return lambda$getPreferredLaunchTaskDisplayArea$1;
                    }
                });
            }
        }
        if (taskDisplayArea == null) {
            int launchDisplayId = activityOptions != null ? activityOptions.getLaunchDisplayId() : -1;
            if (launchDisplayId != -1 && (displayContent = this.mSupervisor.mRootWindowContainer.getDisplayContent(launchDisplayId)) != null) {
                taskDisplayArea = displayContent.getDefaultTaskDisplayArea();
            }
        }
        if (taskDisplayArea == null && activityRecord != null && activityRecord.noDisplay && (taskDisplayArea = activityRecord.mHandoverTaskDisplayArea) == null) {
            com.android.server.wm.DisplayContent displayContent3 = this.mSupervisor.mRootWindowContainer.getDisplayContent(activityRecord.mHandoverLaunchDisplayId);
            if (displayContent3 != null) {
                taskDisplayArea = displayContent3.getDefaultTaskDisplayArea();
            }
        }
        if (taskDisplayArea == null && activityRecord != null) {
            taskDisplayArea = activityRecord.getDisplayArea();
        }
        if (taskDisplayArea == null && task != null) {
            task2 = task.getRootTask();
        }
        if (task2 != null) {
            taskDisplayArea = task2.getDisplayArea();
        }
        if (taskDisplayArea == null && activityOptions != null) {
            com.android.server.wm.DisplayContent displayContent4 = this.mSupervisor.mRootWindowContainer.getDisplayContent(activityOptions.getCallerDisplayId());
            if (displayContent4 != null) {
                taskDisplayArea = displayContent4.getDefaultTaskDisplayArea();
            }
        }
        if (taskDisplayArea == null && launchParams != null) {
            taskDisplayArea = launchParams.mPreferredTaskDisplayArea;
        }
        if (taskDisplayArea != null && !this.mSupervisor.mService.mSupportsMultiDisplay && taskDisplayArea.getDisplayId() != 0) {
            taskDisplayArea = this.mSupervisor.mRootWindowContainer.getDefaultTaskDisplayArea();
        }
        if (taskDisplayArea != null && activityRecord2 != null && activityRecord2.isActivityTypeHome() && !this.mSupervisor.mRootWindowContainer.canStartHomeOnDisplayArea(activityRecord2.info, taskDisplayArea, false)) {
            taskDisplayArea = this.mSupervisor.mRootWindowContainer.getDefaultTaskDisplayArea();
        }
        if (taskDisplayArea != null) {
            return taskDisplayArea;
        }
        return getFallbackDisplayAreaForActivity(activityRecord2, request);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.server.wm.TaskDisplayArea lambda$getPreferredLaunchTaskDisplayArea$1(int i, com.android.server.wm.TaskDisplayArea taskDisplayArea) {
        if (taskDisplayArea.mFeatureId == i) {
            return taskDisplayArea;
        }
        return null;
    }

    private com.android.server.wm.TaskDisplayArea getFallbackDisplayAreaForActivity(@android.annotation.Nullable com.android.server.wm.ActivityRecord activityRecord, @android.annotation.Nullable com.android.server.wm.ActivityStarter.Request request) {
        com.android.server.wm.WindowProcessController processController;
        com.android.server.wm.TaskDisplayArea topActivityDisplayArea;
        com.android.server.wm.TaskDisplayArea topActivityDisplayArea2;
        com.android.server.wm.TaskDisplayArea topActivityDisplayArea3;
        if (activityRecord != null) {
            com.android.server.wm.WindowProcessController processController2 = this.mSupervisor.mService.getProcessController(activityRecord.launchedFromPid, activityRecord.launchedFromUid);
            if (processController2 != null && (topActivityDisplayArea3 = processController2.getTopActivityDisplayArea()) != null) {
                return topActivityDisplayArea3;
            }
            com.android.server.wm.WindowProcessController processController3 = this.mSupervisor.mService.getProcessController(activityRecord.getProcessName(), activityRecord.getUid());
            if (processController3 != null && (topActivityDisplayArea2 = processController3.getTopActivityDisplayArea()) != null) {
                return topActivityDisplayArea2;
            }
        }
        if (request != null && (processController = this.mSupervisor.mService.getProcessController(request.realCallingPid, request.realCallingUid)) != null && (topActivityDisplayArea = processController.getTopActivityDisplayArea()) != null) {
            return topActivityDisplayArea;
        }
        return this.mSupervisor.mRootWindowContainer.getDefaultTaskDisplayArea();
    }

    private boolean canInheritWindowingModeFromSource(@android.annotation.NonNull com.android.server.wm.DisplayContent displayContent, com.android.server.wm.TaskDisplayArea taskDisplayArea, @android.annotation.Nullable com.android.server.wm.ActivityRecord activityRecord) {
        if (activityRecord == null || taskDisplayArea.inFreeformWindowingMode()) {
            return false;
        }
        int windowingMode = activityRecord.getTask().getWindowingMode();
        if ((windowingMode != 1 && windowingMode != 5) || displayContent.getDisplayId() != activityRecord.getDisplayId()) {
            return false;
        }
        return true;
    }

    private boolean canCalculateBoundsForFullscreenTask(@android.annotation.NonNull com.android.server.wm.TaskDisplayArea taskDisplayArea, int i) {
        return this.mSupervisor.mService.mSupportsFreeformWindowManagement && ((taskDisplayArea.getWindowingMode() == 1 && i == 0) || i == 1);
    }

    private boolean canApplyFreeformWindowPolicy(@android.annotation.NonNull com.android.server.wm.TaskDisplayArea taskDisplayArea, int i) {
        return this.mSupervisor.mService.mSupportsFreeformWindowManagement && ((taskDisplayArea.inFreeformWindowingMode() && i == 0) || i == 5);
    }

    private boolean canApplyPipWindowPolicy(int i) {
        return this.mSupervisor.mService.mSupportsPictureInPicture && i == 2;
    }

    private void getLayoutBounds(@android.annotation.NonNull com.android.server.wm.TaskDisplayArea taskDisplayArea, @android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord, @android.annotation.NonNull android.content.pm.ActivityInfo.WindowLayout windowLayout, @android.annotation.NonNull android.graphics.Rect rect) {
        int i;
        int i2;
        float f;
        int i3 = windowLayout.gravity & 112;
        int i4 = windowLayout.gravity & 7;
        if (!windowLayout.hasSpecifiedSize() && i3 == 0 && i4 == 0) {
            rect.setEmpty();
            return;
        }
        android.graphics.Rect rect2 = this.mTmpStableBounds;
        taskDisplayArea.getStableRect(rect2);
        int width = rect2.width();
        int height = rect2.height();
        float f2 = 1.0f;
        if (!windowLayout.hasSpecifiedSize()) {
            if (!rect.isEmpty()) {
                i = rect.width();
                i2 = rect.height();
            } else {
                getTaskBounds(activityRecord, taskDisplayArea, windowLayout, 5, false, rect);
                i = rect.width();
                i2 = rect.height();
            }
        } else {
            if (windowLayout.width > 0 && windowLayout.width < width) {
                i = windowLayout.width;
            } else if (windowLayout.widthFraction > com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE && windowLayout.widthFraction < 1.0f) {
                i = (int) (width * windowLayout.widthFraction);
            } else {
                i = width;
            }
            if (windowLayout.height > 0 && windowLayout.height < height) {
                i2 = windowLayout.height;
            } else if (windowLayout.heightFraction > com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE && windowLayout.heightFraction < 1.0f) {
                i2 = (int) (height * windowLayout.heightFraction);
            } else {
                i2 = height;
            }
        }
        switch (i4) {
            case 3:
                f = 0.0f;
                break;
            case 4:
            default:
                f = 0.5f;
                break;
            case 5:
                f = 1.0f;
                break;
        }
        switch (i3) {
            case 48:
                f2 = 0.0f;
                break;
            case 80:
                break;
            default:
                f2 = 0.5f;
                break;
        }
        rect.set(0, 0, i, i2);
        rect.offset(rect2.left, rect2.top);
        rect.offset((int) (f * (width - i)), (int) (f2 * (height - i2)));
    }

    private boolean shouldLaunchUnresizableAppInFreeform(com.android.server.wm.ActivityRecord activityRecord, com.android.server.wm.TaskDisplayArea taskDisplayArea, @android.annotation.Nullable android.app.ActivityOptions activityOptions) {
        if ((activityOptions == null || activityOptions.getLaunchWindowingMode() != 1) && activityRecord.supportsFreeformInDisplayArea(taskDisplayArea) && !activityRecord.isResizeable()) {
            return taskDisplayArea.getWindowingMode() == 5 && orientationFromBounds(taskDisplayArea.getBounds()) != resolveOrientation(activityRecord, taskDisplayArea, taskDisplayArea.getBounds());
        }
        return false;
    }

    private int resolveOrientation(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord) {
        switch (activityRecord.info.screenOrientation) {
            case 0:
            case 6:
            case 8:
            case 11:
                return 0;
            case 1:
            case 7:
            case 9:
            case 12:
                return 1;
            case 2:
            case 3:
            case 4:
            case 10:
            case 13:
            default:
                return -1;
            case 5:
            case 14:
                return 14;
        }
    }

    private void cascadeBounds(@android.annotation.NonNull android.graphics.Rect rect, @android.annotation.NonNull com.android.server.wm.TaskDisplayArea taskDisplayArea, @android.annotation.NonNull android.graphics.Rect rect2) {
        rect2.set(rect);
        int i = (int) (((taskDisplayArea.getConfiguration().densityDpi / 160.0f) * 75.0f) + 0.5f);
        taskDisplayArea.getBounds(this.mTmpBounds);
        rect2.offset(java.lang.Math.min(i, java.lang.Math.max(0, this.mTmpBounds.right - rect.right)), java.lang.Math.min(i, java.lang.Math.max(0, this.mTmpBounds.bottom - rect.bottom)));
    }

    private void getTaskBounds(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord, @android.annotation.NonNull com.android.server.wm.TaskDisplayArea taskDisplayArea, @android.annotation.NonNull android.content.pm.ActivityInfo.WindowLayout windowLayout, int i, boolean z, @android.annotation.NonNull android.graphics.Rect rect) {
        if (i != 5 && i != 1) {
            return;
        }
        int resolveOrientation = resolveOrientation(activityRecord, taskDisplayArea, rect);
        if (resolveOrientation != 1 && resolveOrientation != 0) {
            throw new java.lang.IllegalStateException("Orientation must be one of portrait or landscape, but it's " + android.content.pm.ActivityInfo.screenOrientationToString(resolveOrientation));
        }
        taskDisplayArea.getStableRect(this.mTmpStableBounds);
        android.util.Size defaultFreeformSize = com.android.server.wm.LaunchParamsUtil.getDefaultFreeformSize(activityRecord, taskDisplayArea, windowLayout, resolveOrientation, this.mTmpStableBounds);
        this.mTmpBounds.set(0, 0, defaultFreeformSize.getWidth(), defaultFreeformSize.getHeight());
        if (z || sizeMatches(rect, this.mTmpBounds)) {
            if (resolveOrientation != orientationFromBounds(rect)) {
                com.android.server.wm.LaunchParamsUtil.centerBounds(taskDisplayArea, rect.height(), rect.width(), rect);
            }
        } else {
            adjustBoundsToFitInDisplayArea(taskDisplayArea, windowLayout, this.mTmpBounds);
            rect.setEmpty();
            com.android.server.wm.LaunchParamsUtil.centerBounds(taskDisplayArea, this.mTmpBounds.width(), this.mTmpBounds.height(), rect);
        }
        adjustBoundsToAvoidConflictInDisplayArea(taskDisplayArea, rect);
    }

    private int convertOrientationToScreenOrientation(int i) {
        switch (i) {
            case 1:
                return 1;
            case 2:
                return 0;
            default:
                return -1;
        }
    }

    private int resolveOrientation(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord, @android.annotation.NonNull com.android.server.wm.TaskDisplayArea taskDisplayArea, @android.annotation.NonNull android.graphics.Rect rect) {
        int resolveOrientation = resolveOrientation(activityRecord);
        if (resolveOrientation == 14) {
            if (rect.isEmpty()) {
                resolveOrientation = convertOrientationToScreenOrientation(taskDisplayArea.getConfiguration().orientation);
            } else {
                resolveOrientation = orientationFromBounds(rect);
            }
        }
        if (resolveOrientation == -1) {
            if (rect.isEmpty()) {
                return 1;
            }
            return orientationFromBounds(rect);
        }
        return resolveOrientation;
    }

    private void adjustBoundsToFitInDisplayArea(@android.annotation.NonNull com.android.server.wm.TaskDisplayArea taskDisplayArea, @android.annotation.NonNull android.content.pm.ActivityInfo.WindowLayout windowLayout, @android.annotation.NonNull android.graphics.Rect rect) {
        com.android.server.wm.LaunchParamsUtil.adjustBoundsToFitInDisplayArea(taskDisplayArea, this.mSupervisor.mRootWindowContainer.getConfiguration().getLayoutDirection(), windowLayout, rect);
    }

    private void adjustBoundsToAvoidConflictInDisplayArea(@android.annotation.NonNull com.android.server.wm.TaskDisplayArea taskDisplayArea, @android.annotation.NonNull android.graphics.Rect rect) {
        final java.util.ArrayList arrayList = new java.util.ArrayList();
        taskDisplayArea.forAllRootTasks(new java.util.function.Consumer() { // from class: com.android.server.wm.TaskLaunchParamsModifier$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.TaskLaunchParamsModifier.lambda$adjustBoundsToAvoidConflictInDisplayArea$2(arrayList, (com.android.server.wm.Task) obj);
            }
        }, false);
        adjustBoundsToAvoidConflict(taskDisplayArea.getBounds(), arrayList, rect);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$adjustBoundsToAvoidConflictInDisplayArea$2(java.util.List list, com.android.server.wm.Task task) {
        if (!task.inFreeformWindowingMode()) {
            return;
        }
        for (int i = 0; i < task.getChildCount(); i++) {
            list.add(task.getChildAt(i).getBounds());
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void adjustBoundsToAvoidConflict(@android.annotation.NonNull android.graphics.Rect rect, @android.annotation.NonNull java.util.List<android.graphics.Rect> list, @android.annotation.NonNull android.graphics.Rect rect2) {
        if (!rect.contains(rect2) || !boundsConflict(list, rect2)) {
            return;
        }
        calculateCandidateShiftDirections(rect, rect2);
        for (int i : this.mTmpDirections) {
            if (i != 0) {
                this.mTmpBounds.set(rect2);
                while (boundsConflict(list, this.mTmpBounds) && rect.contains(this.mTmpBounds)) {
                    shiftBounds(i, rect, this.mTmpBounds);
                }
                if (!boundsConflict(list, this.mTmpBounds) && rect.contains(this.mTmpBounds)) {
                    rect2.set(this.mTmpBounds);
                    return;
                }
            } else {
                return;
            }
        }
    }

    private void calculateCandidateShiftDirections(@android.annotation.NonNull android.graphics.Rect rect, @android.annotation.NonNull android.graphics.Rect rect2) {
        for (int i = 0; i < this.mTmpDirections.length; i++) {
            this.mTmpDirections[i] = 0;
        }
        int i2 = ((rect.left * 2) + rect.right) / 3;
        int i3 = (rect.left + (rect.right * 2)) / 3;
        int centerX = rect2.centerX();
        if (centerX < i2) {
            this.mTmpDirections[0] = 5;
            return;
        }
        if (centerX > i3) {
            this.mTmpDirections[0] = 3;
            return;
        }
        int i4 = ((rect.top * 2) + rect.bottom) / 3;
        int i5 = (rect.top + (rect.bottom * 2)) / 3;
        int centerY = rect2.centerY();
        if (centerY < i4 || centerY > i5) {
            this.mTmpDirections[0] = 5;
            this.mTmpDirections[1] = 3;
        } else {
            this.mTmpDirections[0] = 85;
            this.mTmpDirections[1] = 51;
        }
    }

    private boolean boundsConflict(@android.annotation.NonNull java.util.List<android.graphics.Rect> list, @android.annotation.NonNull android.graphics.Rect rect) {
        java.util.Iterator<android.graphics.Rect> it = list.iterator();
        while (true) {
            if (!it.hasNext()) {
                return false;
            }
            android.graphics.Rect next = it.next();
            boolean z = java.lang.Math.abs(next.left - rect.left) < 4;
            boolean z2 = java.lang.Math.abs(next.top - rect.top) < 4;
            boolean z3 = java.lang.Math.abs(next.right - rect.right) < 4;
            boolean z4 = java.lang.Math.abs(next.bottom - rect.bottom) < 4;
            if ((!z || !z2) && ((!z || !z4) && ((!z3 || !z2) && (!z3 || !z4)))) {
            }
        }
        return true;
    }

    private void shiftBounds(int i, @android.annotation.NonNull android.graphics.Rect rect, @android.annotation.NonNull android.graphics.Rect rect2) {
        int i2;
        int i3 = 0;
        switch (i & 7) {
            case 3:
                i2 = -java.lang.Math.max(1, rect.width() / 16);
                break;
            case 4:
            default:
                i2 = 0;
                break;
            case 5:
                i2 = java.lang.Math.max(1, rect.width() / 16);
                break;
        }
        switch (i & 112) {
            case 48:
                i3 = -java.lang.Math.max(1, rect.height() / 16);
                break;
            case 80:
                i3 = java.lang.Math.max(1, rect.height() / 16);
                break;
        }
        rect2.offset(i2, i3);
    }

    private void initLogBuilder(com.android.server.wm.Task task, com.android.server.wm.ActivityRecord activityRecord) {
    }

    private void appendLog(java.lang.String str) {
    }

    private void outputLog() {
    }

    private static int orientationFromBounds(android.graphics.Rect rect) {
        return rect.width() > rect.height() ? 0 : 1;
    }

    private static boolean sizeMatches(android.graphics.Rect rect, android.graphics.Rect rect2) {
        return java.lang.Math.abs(rect2.width() - rect.width()) < 2 && java.lang.Math.abs(rect2.height() - rect.height()) < 2;
    }
}
