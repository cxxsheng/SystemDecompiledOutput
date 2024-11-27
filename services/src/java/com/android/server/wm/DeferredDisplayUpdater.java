package com.android.server.wm;

/* loaded from: classes3.dex */
public class DeferredDisplayUpdater implements com.android.server.wm.DisplayUpdater {

    @com.android.internal.annotations.VisibleForTesting
    static final com.android.server.wm.utils.DisplayInfoOverrides.DisplayInfoFieldsUpdater DEFERRABLE_FIELDS = new com.android.server.wm.utils.DisplayInfoOverrides.DisplayInfoFieldsUpdater() { // from class: com.android.server.wm.DeferredDisplayUpdater$$ExternalSyntheticLambda0
        @Override // com.android.server.wm.utils.DisplayInfoOverrides.DisplayInfoFieldsUpdater
        public final void setFields(android.view.DisplayInfo displayInfo, android.view.DisplayInfo displayInfo2) {
            com.android.server.wm.DeferredDisplayUpdater.lambda$static$0(displayInfo, displayInfo2);
        }
    };
    static final int DIFF_EVERYTHING = -1;
    static final int DIFF_NONE = 0;
    static final int DIFF_NOT_WM_DEFERRABLE = 2;
    static final int DIFF_WM_DEFERRABLE = 1;
    private final com.android.server.wm.DisplayContent mDisplayContent;

    @android.annotation.Nullable
    private android.view.DisplayInfo mLastDisplayInfo;

    @android.annotation.Nullable
    private android.view.DisplayInfo mLastWmDisplayInfo;

    @android.annotation.NonNull
    private final android.view.DisplayInfo mNonOverrideDisplayInfo = new android.view.DisplayInfo();

    @android.annotation.NonNull
    private final android.view.DisplayInfo mOutputDisplayInfo = new android.view.DisplayInfo();

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$static$0(android.view.DisplayInfo displayInfo, android.view.DisplayInfo displayInfo2) {
        displayInfo.uniqueId = displayInfo2.uniqueId;
        displayInfo.address = displayInfo2.address;
        com.android.server.wm.utils.DisplayInfoOverrides.WM_OVERRIDE_FIELDS.setFields(displayInfo, displayInfo2);
    }

    public DeferredDisplayUpdater(@android.annotation.NonNull com.android.server.wm.DisplayContent displayContent) {
        this.mDisplayContent = displayContent;
        this.mNonOverrideDisplayInfo.copyFrom(this.mDisplayContent.getDisplayInfo());
    }

    @Override // com.android.server.wm.DisplayUpdater
    public void updateDisplayInfo(@android.annotation.NonNull final java.lang.Runnable runnable) {
        final android.view.DisplayInfo currentDisplayInfo = getCurrentDisplayInfo();
        int calculateDisplayInfoDiff = calculateDisplayInfoDiff(this.mLastDisplayInfo, currentDisplayInfo);
        boolean isPhysicalDisplayUpdated = isPhysicalDisplayUpdated(this.mLastDisplayInfo, currentDisplayInfo);
        this.mLastDisplayInfo = currentDisplayInfo;
        if (calculateDisplayInfoDiff == -1 || !this.mDisplayContent.mTransitionController.isShellTransitionsEnabled()) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, -8058211784911995417L, 0, null, null);
            this.mLastWmDisplayInfo = currentDisplayInfo;
            applyLatestDisplayInfo();
            runnable.run();
            return;
        }
        if ((calculateDisplayInfoDiff & 2) > 0) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, 1944392458089872195L, 0, null, null);
            applyLatestDisplayInfo();
        }
        if ((calculateDisplayInfoDiff & 1) > 0) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, 8391643185322408089L, 0, null, null);
            requestDisplayChangeTransition(isPhysicalDisplayUpdated, new java.lang.Runnable() { // from class: com.android.server.wm.DeferredDisplayUpdater$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.wm.DeferredDisplayUpdater.this.lambda$updateDisplayInfo$1(currentDisplayInfo, runnable);
                }
            });
        } else {
            runnable.run();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateDisplayInfo$1(android.view.DisplayInfo displayInfo, java.lang.Runnable runnable) {
        this.mLastWmDisplayInfo = displayInfo;
        applyLatestDisplayInfo();
        runnable.run();
    }

    private void requestDisplayChangeTransition(final boolean z, @android.annotation.NonNull final java.lang.Runnable runnable) {
        final com.android.server.wm.Transition transition = new com.android.server.wm.Transition(6, 0, this.mDisplayContent.mTransitionController, this.mDisplayContent.mTransitionController.mSyncEngine);
        this.mDisplayContent.mAtmService.startPowerMode(2);
        this.mDisplayContent.mTransitionController.startCollectOrQueue(transition, new com.android.server.wm.TransitionController.OnStartCollect() { // from class: com.android.server.wm.DeferredDisplayUpdater$$ExternalSyntheticLambda1
            @Override // com.android.server.wm.TransitionController.OnStartCollect
            public final void onCollectStarted(boolean z2) {
                com.android.server.wm.DeferredDisplayUpdater.this.lambda$requestDisplayChangeTransition$2(runnable, z, transition, z2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestDisplayChangeTransition$2(java.lang.Runnable runnable, boolean z, com.android.server.wm.Transition transition, boolean z2) {
        android.graphics.Rect rect = new android.graphics.Rect(0, 0, this.mDisplayContent.mInitialDisplayWidth, this.mDisplayContent.mInitialDisplayHeight);
        int rotation = this.mDisplayContent.getRotation();
        runnable.run();
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, -915675022936690176L, 0, null, null);
        if (z) {
            onDisplayUpdated(transition, rotation, rect);
        } else {
            this.mDisplayContent.mTransitionController.requestStartTransition(transition, null, null, getCurrentDisplayChange(rotation, rect));
        }
    }

    private void applyLatestDisplayInfo() {
        com.android.server.wm.utils.DisplayInfoOverrides.copyDisplayInfoFields(this.mOutputDisplayInfo, this.mLastDisplayInfo, this.mLastWmDisplayInfo, DEFERRABLE_FIELDS);
        this.mDisplayContent.onDisplayInfoUpdated(this.mOutputDisplayInfo);
    }

    @android.annotation.NonNull
    private android.view.DisplayInfo getCurrentDisplayInfo() {
        this.mDisplayContent.mWmService.mDisplayManagerInternal.getNonOverrideDisplayInfo(this.mDisplayContent.mDisplayId, this.mNonOverrideDisplayInfo);
        return new android.view.DisplayInfo(this.mNonOverrideDisplayInfo);
    }

    @android.annotation.NonNull
    private android.window.TransitionRequestInfo.DisplayChange getCurrentDisplayChange(int i, @android.annotation.NonNull android.graphics.Rect rect) {
        android.graphics.Rect rect2 = new android.graphics.Rect(0, 0, this.mDisplayContent.mInitialDisplayWidth, this.mDisplayContent.mInitialDisplayHeight);
        int rotation = this.mDisplayContent.getRotation();
        android.window.TransitionRequestInfo.DisplayChange displayChange = new android.window.TransitionRequestInfo.DisplayChange(this.mDisplayContent.getDisplayId());
        displayChange.setStartAbsBounds(rect);
        displayChange.setEndAbsBounds(rect2);
        displayChange.setStartRotation(i);
        displayChange.setEndRotation(rotation);
        return displayChange;
    }

    private void onDisplayUpdated(@android.annotation.NonNull final com.android.server.wm.Transition transition, int i, @android.annotation.NonNull android.graphics.Rect rect) {
        int rotation = this.mDisplayContent.getRotation();
        android.window.TransitionRequestInfo.DisplayChange currentDisplayChange = getCurrentDisplayChange(i, rect);
        currentDisplayChange.setPhysicalDisplayChanged(true);
        this.mDisplayContent.mTransitionController.requestStartTransition(transition, null, null, currentDisplayChange);
        if (!this.mDisplayContent.mRemoteDisplayChangeController.performRemoteDisplayChange(i, rotation, this.mDisplayContent.getDisplayAreaInfo(), new com.android.server.wm.RemoteDisplayChangeController.ContinueRemoteDisplayChangeCallback() { // from class: com.android.server.wm.DeferredDisplayUpdater$$ExternalSyntheticLambda3
            @Override // com.android.server.wm.RemoteDisplayChangeController.ContinueRemoteDisplayChangeCallback
            public final void onContinueRemoteDisplayChange(android.window.WindowContainerTransaction windowContainerTransaction) {
                com.android.server.wm.DeferredDisplayUpdater.this.lambda$onDisplayUpdated$3(transition, windowContainerTransaction);
            }
        })) {
            lambda$onDisplayUpdated$3(null, transition);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: finishDisplayUpdate, reason: merged with bridge method [inline-methods] */
    public void lambda$onDisplayUpdated$3(@android.annotation.Nullable android.window.WindowContainerTransaction windowContainerTransaction, @android.annotation.NonNull com.android.server.wm.Transition transition) {
        if (windowContainerTransaction != null) {
            this.mDisplayContent.mAtmService.mWindowOrganizerController.applyTransaction(windowContainerTransaction);
        }
        transition.setAllReady();
    }

    private boolean isPhysicalDisplayUpdated(@android.annotation.Nullable android.view.DisplayInfo displayInfo, @android.annotation.Nullable android.view.DisplayInfo displayInfo2) {
        if (displayInfo == null || displayInfo2 == null) {
            return true;
        }
        return !java.util.Objects.equals(displayInfo.uniqueId, displayInfo2.uniqueId);
    }

    @com.android.internal.annotations.VisibleForTesting
    static int calculateDisplayInfoDiff(@android.annotation.Nullable android.view.DisplayInfo displayInfo, @android.annotation.Nullable android.view.DisplayInfo displayInfo2) {
        if (java.util.Objects.equals(displayInfo, displayInfo2)) {
            return 0;
        }
        if (displayInfo == null || displayInfo2 == null) {
            return -1;
        }
        int i = (displayInfo.layerStack == displayInfo2.layerStack && displayInfo.flags == displayInfo2.flags && displayInfo.type == displayInfo2.type && displayInfo.displayId == displayInfo2.displayId && displayInfo.displayGroupId == displayInfo2.displayGroupId && java.util.Objects.equals(displayInfo.deviceProductInfo, displayInfo2.deviceProductInfo) && displayInfo.modeId == displayInfo2.modeId && displayInfo.renderFrameRate == displayInfo2.renderFrameRate && displayInfo.defaultModeId == displayInfo2.defaultModeId && displayInfo.userPreferredModeId == displayInfo2.userPreferredModeId && java.util.Arrays.equals(displayInfo.supportedModes, displayInfo2.supportedModes) && displayInfo.colorMode == displayInfo2.colorMode && java.util.Arrays.equals(displayInfo.supportedColorModes, displayInfo2.supportedColorModes) && java.util.Objects.equals(displayInfo.hdrCapabilities, displayInfo2.hdrCapabilities) && java.util.Arrays.equals(displayInfo.userDisabledHdrTypes, displayInfo2.userDisabledHdrTypes) && displayInfo.minimalPostProcessingSupported == displayInfo2.minimalPostProcessingSupported && displayInfo.appVsyncOffsetNanos == displayInfo2.appVsyncOffsetNanos && displayInfo.presentationDeadlineNanos == displayInfo2.presentationDeadlineNanos && displayInfo.state == displayInfo2.state && displayInfo.committedState == displayInfo2.committedState && displayInfo.ownerUid == displayInfo2.ownerUid && java.util.Objects.equals(displayInfo.ownerPackageName, displayInfo2.ownerPackageName) && displayInfo.removeMode == displayInfo2.removeMode && displayInfo.getRefreshRate() == displayInfo2.getRefreshRate() && displayInfo.brightnessMinimum == displayInfo2.brightnessMinimum && displayInfo.brightnessMaximum == displayInfo2.brightnessMaximum && displayInfo.brightnessDefault == displayInfo2.brightnessDefault && displayInfo.installOrientation == displayInfo2.installOrientation && java.util.Objects.equals(displayInfo.layoutLimitedRefreshRate, displayInfo2.layoutLimitedRefreshRate) && com.android.internal.display.BrightnessSynchronizer.floatEquals(displayInfo.hdrSdrRatio, displayInfo2.hdrSdrRatio) && displayInfo.thermalRefreshRateThrottling.contentEquals(displayInfo2.thermalRefreshRateThrottling) && java.util.Objects.equals(displayInfo.thermalBrightnessThrottlingDataId, displayInfo2.thermalBrightnessThrottlingDataId)) ? 0 : 2;
        if (displayInfo.appWidth != displayInfo2.appWidth || displayInfo.appHeight != displayInfo2.appHeight || displayInfo.smallestNominalAppWidth != displayInfo2.smallestNominalAppWidth || displayInfo.smallestNominalAppHeight != displayInfo2.smallestNominalAppHeight || displayInfo.largestNominalAppWidth != displayInfo2.largestNominalAppWidth || displayInfo.largestNominalAppHeight != displayInfo2.largestNominalAppHeight || displayInfo.logicalWidth != displayInfo2.logicalWidth || displayInfo.logicalHeight != displayInfo2.logicalHeight || displayInfo.physicalXDpi != displayInfo2.physicalXDpi || displayInfo.physicalYDpi != displayInfo2.physicalYDpi || displayInfo.rotation != displayInfo2.rotation || !java.util.Objects.equals(displayInfo.displayCutout, displayInfo2.displayCutout) || displayInfo.logicalDensityDpi != displayInfo2.logicalDensityDpi || !java.util.Objects.equals(displayInfo.roundedCorners, displayInfo2.roundedCorners) || !java.util.Objects.equals(displayInfo.displayShape, displayInfo2.displayShape) || !java.util.Objects.equals(displayInfo.uniqueId, displayInfo2.uniqueId) || !java.util.Objects.equals(displayInfo.address, displayInfo2.address)) {
            return i | 1;
        }
        return i;
    }
}
