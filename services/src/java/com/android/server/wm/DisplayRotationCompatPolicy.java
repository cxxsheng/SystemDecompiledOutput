package com.android.server.wm;

/* loaded from: classes3.dex */
final class DisplayRotationCompatPolicy {
    private static final int CAMERA_CLOSED_ROTATION_UPDATE_DELAY_MS = 2000;
    private static final int CAMERA_OPENED_ROTATION_UPDATE_DELAY_MS = 1000;
    private static final int REFRESH_CALLBACK_TIMEOUT_MS = 2000;
    private final android.hardware.camera2.CameraManager.AvailabilityCallback mAvailabilityCallback;

    @com.android.internal.annotations.GuardedBy({"this"})
    private final com.android.server.wm.DisplayRotationCompatPolicy.CameraIdPackageNameBiMap mCameraIdPackageBiMap;
    private final android.hardware.camera2.CameraManager mCameraManager;
    private final com.android.server.wm.DisplayContent mDisplayContent;
    private final android.os.Handler mHandler;
    private int mLastReportedOrientation;

    @com.android.internal.annotations.GuardedBy({"this"})
    private final java.util.Set<java.lang.String> mScheduledOrientationUpdateCameraIdSet;

    @com.android.internal.annotations.GuardedBy({"this"})
    private final java.util.Set<java.lang.String> mScheduledToBeRemovedCameraIdSet;
    private final com.android.server.wm.WindowManagerService mWmService;

    DisplayRotationCompatPolicy(@android.annotation.NonNull com.android.server.wm.DisplayContent displayContent) {
        this(displayContent, displayContent.mWmService.mH);
    }

    @com.android.internal.annotations.VisibleForTesting
    DisplayRotationCompatPolicy(@android.annotation.NonNull com.android.server.wm.DisplayContent displayContent, android.os.Handler handler) {
        this.mCameraIdPackageBiMap = new com.android.server.wm.DisplayRotationCompatPolicy.CameraIdPackageNameBiMap();
        this.mScheduledToBeRemovedCameraIdSet = new android.util.ArraySet();
        this.mScheduledOrientationUpdateCameraIdSet = new android.util.ArraySet();
        this.mAvailabilityCallback = new android.hardware.camera2.CameraManager.AvailabilityCallback() { // from class: com.android.server.wm.DisplayRotationCompatPolicy.1
            public void onCameraOpened(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2) {
                com.android.server.wm.DisplayRotationCompatPolicy.this.notifyCameraOpened(str, str2);
            }

            public void onCameraClosed(@android.annotation.NonNull java.lang.String str) {
                com.android.server.wm.DisplayRotationCompatPolicy.this.notifyCameraClosed(str);
            }
        };
        this.mLastReportedOrientation = -2;
        this.mHandler = handler;
        this.mDisplayContent = displayContent;
        this.mWmService = displayContent.mWmService;
        this.mCameraManager = (android.hardware.camera2.CameraManager) this.mWmService.mContext.getSystemService(android.hardware.camera2.CameraManager.class);
        this.mCameraManager.registerAvailabilityCallback(this.mWmService.mContext.getMainExecutor(), this.mAvailabilityCallback);
    }

    void dispose() {
        this.mCameraManager.unregisterAvailabilityCallback(this.mAvailabilityCallback);
    }

    int getOrientation() {
        this.mLastReportedOrientation = getOrientationInternal();
        if (this.mLastReportedOrientation != -1) {
            rememberOverriddenOrientationIfNeeded();
        } else {
            restoreOverriddenOrientationIfNeeded();
        }
        return this.mLastReportedOrientation;
    }

    private synchronized int getOrientationInternal() {
        if (!isTreatmentEnabledForDisplay()) {
            return -1;
        }
        int i = 1;
        com.android.server.wm.ActivityRecord activityRecord = this.mDisplayContent.topRunningActivity(true);
        if (!isTreatmentEnabledForActivity(activityRecord)) {
            return -1;
        }
        boolean z = activityRecord.getRequestedConfigurationOrientation() == 1;
        boolean z2 = this.mDisplayContent.getNaturalOrientation() == 1;
        if ((!z || !z2) && (z || z2)) {
            i = 0;
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, 7429138692709430028L, 241, null, java.lang.Long.valueOf(this.mDisplayContent.mDisplayId), java.lang.String.valueOf(android.content.pm.ActivityInfo.screenOrientationToString(i)), java.lang.Boolean.valueOf(z), java.lang.Boolean.valueOf(z2));
        return i;
    }

    void onActivityConfigurationChanging(final com.android.server.wm.ActivityRecord activityRecord, android.content.res.Configuration configuration, android.content.res.Configuration configuration2) {
        if (!isTreatmentEnabledForDisplay() || !this.mWmService.mLetterboxConfiguration.isCameraCompatRefreshEnabled() || !shouldRefreshActivity(activityRecord, configuration, configuration2)) {
            return;
        }
        boolean z = this.mWmService.mLetterboxConfiguration.isCameraCompatRefreshCycleThroughStopEnabled() && !activityRecord.mLetterboxUiController.shouldRefreshActivityViaPauseForCameraCompat();
        try {
            activityRecord.mLetterboxUiController.setIsRefreshAfterRotationRequested(true);
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, -7756685416834187936L, 0, null, java.lang.String.valueOf(activityRecord));
            activityRecord.mAtmService.getLifecycleManager().scheduleTransactionAndLifecycleItems(activityRecord.app.getThread(), android.app.servertransaction.RefreshCallbackItem.obtain(activityRecord.token, z ? 5 : 4), android.app.servertransaction.ResumeActivityItem.obtain(activityRecord.token, false, false));
            this.mHandler.postDelayed(new java.lang.Runnable() { // from class: com.android.server.wm.DisplayRotationCompatPolicy$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.wm.DisplayRotationCompatPolicy.this.lambda$onActivityConfigurationChanging$0(activityRecord);
                }
            }, 2000L);
        } catch (android.os.RemoteException e) {
            activityRecord.mLetterboxUiController.setIsRefreshAfterRotationRequested(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: onActivityRefreshed, reason: merged with bridge method [inline-methods] */
    public void lambda$onActivityConfigurationChanging$0(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord) {
        activityRecord.mLetterboxUiController.setIsRefreshAfterRotationRequested(false);
    }

    void onScreenRotationAnimationFinished() {
        if (!isTreatmentEnabledForDisplay() || this.mCameraIdPackageBiMap.isEmpty() || !isTreatmentEnabledForActivity(this.mDisplayContent.topRunningActivity(true))) {
            return;
        }
        showToast(android.R.string.device_policy_manager_service);
    }

    java.lang.String getSummaryForDisplayRotationHistoryRecord() {
        java.lang.String str;
        if (!isTreatmentEnabledForDisplay()) {
            str = "";
        } else {
            com.android.server.wm.ActivityRecord activityRecord = this.mDisplayContent.topRunningActivity(true);
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(" mLastReportedOrientation=");
            sb.append(android.content.pm.ActivityInfo.screenOrientationToString(this.mLastReportedOrientation));
            sb.append(" topActivity=");
            sb.append(activityRecord == null ? "null" : activityRecord.shortComponentName);
            sb.append(" isTreatmentEnabledForActivity=");
            sb.append(isTreatmentEnabledForActivity(activityRecord));
            sb.append(" CameraIdPackageNameBiMap=");
            sb.append(this.mCameraIdPackageBiMap.getSummaryForDisplayRotationHistoryRecord());
            str = sb.toString();
        }
        return "DisplayRotationCompatPolicy{ isTreatmentEnabledForDisplay=" + isTreatmentEnabledForDisplay() + str + " }";
    }

    private void restoreOverriddenOrientationIfNeeded() {
        if (isOrientationOverridden() && this.mDisplayContent.getRotationReversionController().revertOverride(1)) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, -5176775281239247368L, 0, null, null);
            this.mDisplayContent.mLastOrientationSource = null;
        }
    }

    private boolean isOrientationOverridden() {
        return this.mDisplayContent.getRotationReversionController().isOverrideActive(1);
    }

    private void rememberOverriddenOrientationIfNeeded() {
        if (!isOrientationOverridden()) {
            this.mDisplayContent.getRotationReversionController().beforeOverrideApplied(1);
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, -2188976047008497712L, 1, null, java.lang.Long.valueOf(this.mDisplayContent.getLastOrientation()));
        }
    }

    private boolean shouldRefreshActivity(com.android.server.wm.ActivityRecord activityRecord, android.content.res.Configuration configuration, android.content.res.Configuration configuration2) {
        return ((configuration.windowConfiguration.getDisplayRotation() != configuration2.windowConfiguration.getDisplayRotation()) || activityRecord.mLetterboxUiController.isCameraCompatSplitScreenAspectRatioAllowed()) && isTreatmentEnabledForActivity(activityRecord) && activityRecord.mLetterboxUiController.shouldRefreshActivityForCameraCompat();
    }

    private boolean isTreatmentEnabledForDisplay() {
        return this.mWmService.mLetterboxConfiguration.isCameraCompatTreatmentEnabled() && this.mDisplayContent.getIgnoreOrientationRequest() && this.mDisplayContent.getDisplay().getType() == 1;
    }

    boolean isActivityEligibleForOrientationOverride(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord) {
        return isTreatmentEnabledForDisplay() && isCameraActive(activityRecord, true);
    }

    boolean isTreatmentEnabledForActivity(@android.annotation.Nullable com.android.server.wm.ActivityRecord activityRecord) {
        return isTreatmentEnabledForActivity(activityRecord, true);
    }

    private boolean isTreatmentEnabledForActivity(@android.annotation.Nullable com.android.server.wm.ActivityRecord activityRecord, boolean z) {
        return (activityRecord == null || !isCameraActive(activityRecord, z) || activityRecord.getRequestedConfigurationOrientation() == 0 || activityRecord.getOverrideOrientation() == 5 || activityRecord.getOverrideOrientation() == 14) ? false : true;
    }

    private boolean isCameraActive(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord, boolean z) {
        return !(z && activityRecord.inMultiWindowMode()) && this.mCameraIdPackageBiMap.containsPackageName(activityRecord.packageName) && activityRecord.mLetterboxUiController.shouldForceRotateForCameraCompat();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void notifyCameraOpened(@android.annotation.NonNull final java.lang.String str, @android.annotation.NonNull final java.lang.String str2) {
        this.mScheduledToBeRemovedCameraIdSet.remove(str);
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, -8302211458579221117L, 1, null, java.lang.Long.valueOf(this.mDisplayContent.mDisplayId), java.lang.String.valueOf(str), java.lang.String.valueOf(str2));
        this.mScheduledOrientationUpdateCameraIdSet.add(str);
        this.mHandler.postDelayed(new java.lang.Runnable() { // from class: com.android.server.wm.DisplayRotationCompatPolicy$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.DisplayRotationCompatPolicy.this.lambda$notifyCameraOpened$1(str, str2);
            }
        }, 1000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: delayedUpdateOrientationWithWmLock, reason: merged with bridge method [inline-methods] */
    public void lambda$notifyCameraOpened$1(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2) {
        synchronized (this) {
            try {
                if (this.mScheduledOrientationUpdateCameraIdSet.remove(str)) {
                    this.mCameraIdPackageBiMap.put(str2, str);
                    com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mWmService.mGlobalLock;
                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                        try {
                            com.android.server.wm.ActivityRecord activityRecord = this.mDisplayContent.topRunningActivity(true);
                            if (activityRecord == null || activityRecord.getTask() == null) {
                                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                                return;
                            }
                            if (activityRecord.getWindowingMode() == 1) {
                                activityRecord.mLetterboxUiController.recomputeConfigurationForCameraCompatIfNeeded();
                                this.mDisplayContent.updateOrientation();
                                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                                return;
                            }
                            if (activityRecord.getTask().getWindowingMode() == 6 && isTreatmentEnabledForActivity(activityRecord, false)) {
                                android.content.pm.PackageManager packageManager = this.mWmService.mContext.getPackageManager();
                                try {
                                    showToast(android.R.string.device_state_notification_settings_button, (java.lang.String) packageManager.getApplicationLabel(packageManager.getApplicationInfo(str2, 0)));
                                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                                    com.android.internal.protolog.ProtoLogImpl_1545807451.e(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, -1534784331886673955L, 0, null, java.lang.String.valueOf(str2));
                                }
                            }
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        } catch (java.lang.Throwable th) {
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                            throw th;
                        }
                    }
                }
            } finally {
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void showToast(final int i) {
        com.android.server.UiThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.wm.DisplayRotationCompatPolicy$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.DisplayRotationCompatPolicy.this.lambda$showToast$2(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showToast$2(int i) {
        android.widget.Toast.makeText(this.mWmService.mContext, i, 1).show();
    }

    @com.android.internal.annotations.VisibleForTesting
    void showToast(final int i, @android.annotation.NonNull final java.lang.String str) {
        com.android.server.UiThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.wm.DisplayRotationCompatPolicy$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.DisplayRotationCompatPolicy.this.lambda$showToast$3(i, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showToast$3(int i, java.lang.String str) {
        android.widget.Toast.makeText(this.mWmService.mContext, this.mWmService.mContext.getString(i, str), 1).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void notifyCameraClosed(@android.annotation.NonNull java.lang.String str) {
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, 1797195804376906831L, 1, null, java.lang.Long.valueOf(this.mDisplayContent.mDisplayId), java.lang.String.valueOf(str));
        this.mScheduledToBeRemovedCameraIdSet.add(str);
        this.mScheduledOrientationUpdateCameraIdSet.remove(str);
        scheduleRemoveCameraId(str);
    }

    private void scheduleRemoveCameraId(@android.annotation.NonNull final java.lang.String str) {
        this.mHandler.postDelayed(new java.lang.Runnable() { // from class: com.android.server.wm.DisplayRotationCompatPolicy$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.DisplayRotationCompatPolicy.this.lambda$scheduleRemoveCameraId$4(str);
            }
        }, 2000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: removeCameraId, reason: merged with bridge method [inline-methods] */
    public void lambda$scheduleRemoveCameraId$4(java.lang.String str) {
        synchronized (this) {
            try {
                if (this.mScheduledToBeRemovedCameraIdSet.remove(str)) {
                    if (isActivityForCameraIdRefreshing(str)) {
                        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, -8746776274432739264L, 1, null, java.lang.Long.valueOf(this.mDisplayContent.mDisplayId), java.lang.String.valueOf(str));
                        this.mScheduledToBeRemovedCameraIdSet.add(str);
                        scheduleRemoveCameraId(str);
                        return;
                    }
                    this.mCameraIdPackageBiMap.removeCameraId(str);
                    com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, 3622181214422515679L, 1, null, java.lang.Long.valueOf(this.mDisplayContent.mDisplayId), java.lang.String.valueOf(str));
                    com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mWmService.mGlobalLock;
                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                        try {
                            com.android.server.wm.ActivityRecord activityRecord = this.mDisplayContent.topRunningActivity(true);
                            if (activityRecord != null && activityRecord.getWindowingMode() == 1) {
                                activityRecord.mLetterboxUiController.recomputeConfigurationForCameraCompatIfNeeded();
                                this.mDisplayContent.updateOrientation();
                                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                                return;
                            }
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        } catch (java.lang.Throwable th) {
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                            throw th;
                        }
                    }
                }
            } finally {
            }
        }
    }

    private boolean isActivityForCameraIdRefreshing(java.lang.String str) {
        java.lang.String cameraId;
        com.android.server.wm.ActivityRecord activityRecord = this.mDisplayContent.topRunningActivity(true);
        if (isTreatmentEnabledForActivity(activityRecord) && (cameraId = this.mCameraIdPackageBiMap.getCameraId(activityRecord.packageName)) != null && cameraId == str) {
            return activityRecord.mLetterboxUiController.isRefreshAfterRotationRequested();
        }
        return false;
    }

    private static class CameraIdPackageNameBiMap {
        private final java.util.Map<java.lang.String, java.lang.String> mCameraIdToPackageMap;
        private final java.util.Map<java.lang.String, java.lang.String> mPackageToCameraIdMap;

        private CameraIdPackageNameBiMap() {
            this.mPackageToCameraIdMap = new android.util.ArrayMap();
            this.mCameraIdToPackageMap = new android.util.ArrayMap();
        }

        boolean isEmpty() {
            return this.mCameraIdToPackageMap.isEmpty();
        }

        void put(java.lang.String str, java.lang.String str2) {
            removePackageName(str);
            removeCameraId(str2);
            this.mPackageToCameraIdMap.put(str, str2);
            this.mCameraIdToPackageMap.put(str2, str);
        }

        boolean containsPackageName(java.lang.String str) {
            return this.mPackageToCameraIdMap.containsKey(str);
        }

        @android.annotation.Nullable
        java.lang.String getCameraId(java.lang.String str) {
            return this.mPackageToCameraIdMap.get(str);
        }

        void removeCameraId(java.lang.String str) {
            java.lang.String str2 = this.mCameraIdToPackageMap.get(str);
            if (str2 == null) {
                return;
            }
            this.mPackageToCameraIdMap.remove(str2, str);
            this.mCameraIdToPackageMap.remove(str, str2);
        }

        java.lang.String getSummaryForDisplayRotationHistoryRecord() {
            return "{ mPackageToCameraIdMap=" + this.mPackageToCameraIdMap + " }";
        }

        private void removePackageName(java.lang.String str) {
            java.lang.String str2 = this.mPackageToCameraIdMap.get(str);
            if (str2 == null) {
                return;
            }
            this.mPackageToCameraIdMap.remove(str, str2);
            this.mCameraIdToPackageMap.remove(str2, str);
        }
    }
}
