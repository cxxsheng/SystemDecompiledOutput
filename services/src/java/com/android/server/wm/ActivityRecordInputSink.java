package com.android.server.wm;

/* loaded from: classes3.dex */
class ActivityRecordInputSink {
    static final long ENABLE_TOUCH_OPAQUE_ACTIVITIES = 194480991;
    private final com.android.server.wm.ActivityRecord mActivityRecord;
    private com.android.server.wm.InputWindowHandleWrapper mInputWindowHandleWrapper;
    private final boolean mIsCompatEnabled;
    private final java.lang.String mName;
    private android.view.SurfaceControl mSurfaceControl;

    ActivityRecordInputSink(com.android.server.wm.ActivityRecord activityRecord, com.android.server.wm.ActivityRecord activityRecord2) {
        this.mActivityRecord = activityRecord;
        this.mIsCompatEnabled = android.app.compat.CompatChanges.isChangeEnabled(ENABLE_TOUCH_OPAQUE_ACTIVITIES, this.mActivityRecord.getUid());
        this.mName = java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)) + " ActivityRecordInputSink " + this.mActivityRecord.mActivityComponent.flattenToShortString();
        if (activityRecord2 != null) {
            activityRecord2.mAllowedTouchUid = this.mActivityRecord.getUid();
        }
    }

    public void applyChangesToSurfaceIfChanged(android.view.SurfaceControl.Transaction transaction) {
        com.android.server.wm.InputWindowHandleWrapper inputWindowHandleWrapper = getInputWindowHandleWrapper();
        if (this.mSurfaceControl == null) {
            this.mSurfaceControl = createSurface(transaction);
        }
        if (inputWindowHandleWrapper.isChanged()) {
            inputWindowHandleWrapper.applyChangesToSurface(transaction, this.mSurfaceControl);
        }
    }

    private android.view.SurfaceControl createSurface(android.view.SurfaceControl.Transaction transaction) {
        android.view.SurfaceControl build = this.mActivityRecord.makeChildSurface(null).setName(this.mName).setHidden(false).setCallsite("ActivityRecordInputSink.createSurface").build();
        transaction.setLayer(build, Integer.MIN_VALUE);
        return build;
    }

    private com.android.server.wm.InputWindowHandleWrapper getInputWindowHandleWrapper() {
        if (this.mInputWindowHandleWrapper == null) {
            this.mInputWindowHandleWrapper = new com.android.server.wm.InputWindowHandleWrapper(createInputWindowHandle());
        }
        com.android.server.wm.ActivityRecord activityBelow = this.mActivityRecord.getTask() != null ? this.mActivityRecord.getTask().getActivityBelow(this.mActivityRecord) : null;
        if (!(activityBelow != null && (activityBelow.mAllowedTouchUid == this.mActivityRecord.getUid() || activityBelow.isUid(this.mActivityRecord.getUid()))) && this.mIsCompatEnabled && !this.mActivityRecord.isInTransition() && this.mActivityRecord.mActivityRecordInputSinkEnabled) {
            this.mInputWindowHandleWrapper.setInputConfigMasked(0, 8);
        } else {
            this.mInputWindowHandleWrapper.setInputConfigMasked(8, 8);
        }
        this.mInputWindowHandleWrapper.setDisplayId(this.mActivityRecord.getDisplayId());
        return this.mInputWindowHandleWrapper;
    }

    private android.view.InputWindowHandle createInputWindowHandle() {
        android.view.InputWindowHandle inputWindowHandle = new android.view.InputWindowHandle((android.view.InputApplicationHandle) null, this.mActivityRecord.getDisplayId());
        inputWindowHandle.replaceTouchableRegionWithCrop = true;
        inputWindowHandle.name = this.mName;
        inputWindowHandle.layoutParamsType = 2022;
        inputWindowHandle.ownerPid = com.android.server.wm.WindowManagerService.MY_PID;
        inputWindowHandle.ownerUid = com.android.server.wm.WindowManagerService.MY_UID;
        inputWindowHandle.inputConfig = 5;
        return inputWindowHandle;
    }

    void releaseSurfaceControl() {
        if (this.mSurfaceControl != null) {
            this.mSurfaceControl.release();
            this.mSurfaceControl = null;
        }
    }
}
