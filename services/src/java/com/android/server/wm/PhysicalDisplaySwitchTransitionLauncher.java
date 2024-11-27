package com.android.server.wm;

/* loaded from: classes3.dex */
public class PhysicalDisplaySwitchTransitionLauncher {
    private final com.android.server.wm.ActivityTaskManagerService mAtmService;
    private final android.content.Context mContext;
    private com.android.server.wm.DeviceStateController.DeviceState mDeviceState;
    private final com.android.server.wm.DisplayContent mDisplayContent;
    private boolean mShouldRequestTransitionOnDisplaySwitch;
    private com.android.server.wm.Transition mTransition;
    private final com.android.server.wm.TransitionController mTransitionController;

    public PhysicalDisplaySwitchTransitionLauncher(com.android.server.wm.DisplayContent displayContent, com.android.server.wm.TransitionController transitionController) {
        this(displayContent, displayContent.mWmService.mAtmService, displayContent.mWmService.mContext, transitionController);
    }

    @com.android.internal.annotations.VisibleForTesting
    public PhysicalDisplaySwitchTransitionLauncher(com.android.server.wm.DisplayContent displayContent, com.android.server.wm.ActivityTaskManagerService activityTaskManagerService, android.content.Context context, com.android.server.wm.TransitionController transitionController) {
        this.mShouldRequestTransitionOnDisplaySwitch = false;
        this.mDeviceState = com.android.server.wm.DeviceStateController.DeviceState.UNKNOWN;
        this.mDisplayContent = displayContent;
        this.mAtmService = activityTaskManagerService;
        this.mContext = context;
        this.mTransitionController = transitionController;
    }

    void foldStateChanged(com.android.server.wm.DeviceStateController.DeviceState deviceState) {
        if (this.mDeviceState == com.android.server.wm.DeviceStateController.DeviceState.FOLDED && (deviceState == com.android.server.wm.DeviceStateController.DeviceState.HALF_FOLDED || deviceState == com.android.server.wm.DeviceStateController.DeviceState.OPEN)) {
            this.mShouldRequestTransitionOnDisplaySwitch = true;
        } else if (deviceState != com.android.server.wm.DeviceStateController.DeviceState.HALF_FOLDED && deviceState != com.android.server.wm.DeviceStateController.DeviceState.OPEN) {
            this.mShouldRequestTransitionOnDisplaySwitch = false;
        }
        this.mDeviceState = deviceState;
    }

    public void requestDisplaySwitchTransitionIfNeeded(int i, int i2, int i3, int i4, int i5) {
        if (this.mShouldRequestTransitionOnDisplaySwitch && this.mTransitionController.isShellTransitionsEnabled() && this.mDisplayContent.getLastHasContent()) {
            if (this.mContext.getResources().getBoolean(android.R.bool.config_tvExternalInputLoggingDisplayNameFilterEnabled) && android.animation.ValueAnimator.areAnimatorsEnabled()) {
                this.mTransition = null;
                if (this.mTransitionController.isCollecting()) {
                    this.mTransitionController.collect(this.mDisplayContent);
                    this.mTransition = this.mTransitionController.getCollectingTransition();
                    this.mTransition.setReady(this.mDisplayContent, false);
                    this.mTransition.addFlag(16384);
                    com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, 5106303602270682056L, 0, null, null);
                } else {
                    android.window.TransitionRequestInfo.DisplayChange displayChange = new android.window.TransitionRequestInfo.DisplayChange(i);
                    displayChange.setStartAbsBounds(new android.graphics.Rect(0, 0, i2, i3));
                    displayChange.setEndAbsBounds(new android.graphics.Rect(0, 0, i4, i5));
                    displayChange.setPhysicalDisplayChanged(true);
                    this.mTransition = this.mTransitionController.requestTransitionIfNeeded(6, 0, this.mDisplayContent, this.mDisplayContent, null, displayChange);
                }
                if (this.mTransition != null) {
                    this.mAtmService.startPowerMode(2);
                }
                this.mShouldRequestTransitionOnDisplaySwitch = false;
            }
        }
    }

    public void onDisplayUpdated(int i, int i2, @android.annotation.NonNull android.window.DisplayAreaInfo displayAreaInfo) {
        if (this.mTransition != null && !this.mDisplayContent.mRemoteDisplayChangeController.performRemoteDisplayChange(i, i2, displayAreaInfo, new com.android.server.wm.RemoteDisplayChangeController.ContinueRemoteDisplayChangeCallback() { // from class: com.android.server.wm.PhysicalDisplaySwitchTransitionLauncher$$ExternalSyntheticLambda0
            @Override // com.android.server.wm.RemoteDisplayChangeController.ContinueRemoteDisplayChangeCallback
            public final void onContinueRemoteDisplayChange(android.window.WindowContainerTransaction windowContainerTransaction) {
                com.android.server.wm.PhysicalDisplaySwitchTransitionLauncher.this.continueDisplayUpdate(windowContainerTransaction);
            }
        })) {
            markTransitionAsReady();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void continueDisplayUpdate(@android.annotation.Nullable android.window.WindowContainerTransaction windowContainerTransaction) {
        if (this.mTransition == null) {
            return;
        }
        if (windowContainerTransaction != null) {
            this.mAtmService.mWindowOrganizerController.applyTransaction(windowContainerTransaction);
        }
        markTransitionAsReady();
    }

    private void markTransitionAsReady() {
        if (this.mTransition == null) {
            return;
        }
        this.mTransition.setAllReady();
        this.mTransition = null;
    }
}
