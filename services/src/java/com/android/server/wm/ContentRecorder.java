package com.android.server.wm;

/* loaded from: classes3.dex */
final class ContentRecorder implements com.android.server.wm.WindowContainerListener {
    private static final float MAX_ANISOTROPY = 0.025f;
    private android.view.ContentRecordingSession mContentRecordingSession;
    private final boolean mCorrectForAnisotropicPixels;

    @android.annotation.NonNull
    private final com.android.server.wm.DisplayContent mDisplayContent;

    @android.annotation.Nullable
    private android.graphics.Point mLastConsumingSurfaceSize;
    private int mLastOrientation;

    @android.annotation.Nullable
    private android.graphics.Rect mLastRecordedBounds;
    private int mLastWindowingMode;

    @android.annotation.Nullable
    private final com.android.server.wm.ContentRecorder.MediaProjectionManagerWrapper mMediaProjectionManager;

    @android.annotation.Nullable
    private android.view.SurfaceControl mRecordedSurface;

    @android.annotation.Nullable
    private com.android.server.wm.WindowContainer mRecordedWindowContainer;

    @com.android.internal.annotations.VisibleForTesting
    interface MediaProjectionManagerWrapper {
        void notifyActiveProjectionCapturedContentResized(int i, int i2);

        void notifyActiveProjectionCapturedContentVisibilityChanged(boolean z);

        void notifyWindowingModeChanged(int i, int i2, int i3);

        void stopActiveProjection();
    }

    ContentRecorder(@android.annotation.NonNull com.android.server.wm.DisplayContent displayContent) {
        this(displayContent, new com.android.server.wm.ContentRecorder.RemoteMediaProjectionManagerWrapper(displayContent.mDisplayId), new com.android.server.display.feature.DisplayManagerFlags().isConnectedDisplayManagementEnabled() && displayContent.getDisplayInfo().type == 2);
    }

    @com.android.internal.annotations.VisibleForTesting
    ContentRecorder(@android.annotation.NonNull com.android.server.wm.DisplayContent displayContent, @android.annotation.NonNull com.android.server.wm.ContentRecorder.MediaProjectionManagerWrapper mediaProjectionManagerWrapper, boolean z) {
        this.mContentRecordingSession = null;
        this.mRecordedWindowContainer = null;
        this.mRecordedSurface = null;
        this.mLastRecordedBounds = null;
        this.mLastConsumingSurfaceSize = new android.graphics.Point(0, 0);
        this.mLastOrientation = 0;
        this.mLastWindowingMode = 0;
        this.mDisplayContent = displayContent;
        this.mMediaProjectionManager = mediaProjectionManagerWrapper;
        this.mCorrectForAnisotropicPixels = z;
    }

    void setContentRecordingSession(@android.annotation.Nullable android.view.ContentRecordingSession contentRecordingSession) {
        this.mContentRecordingSession = contentRecordingSession;
    }

    boolean isContentRecordingSessionSet() {
        return this.mContentRecordingSession != null;
    }

    boolean isCurrentlyRecording() {
        return (this.mContentRecordingSession == null || this.mRecordedSurface == null) ? false : true;
    }

    @com.android.internal.annotations.VisibleForTesting
    void updateRecording() {
        if (isCurrentlyRecording() && (this.mDisplayContent.getLastHasContent() || this.mDisplayContent.getDisplayInfo().state == 1)) {
            pauseRecording();
        } else {
            startRecordingIfNeeded();
        }
    }

    void onConfigurationChanged(int i, int i2) {
        if (!isCurrentlyRecording() || this.mLastRecordedBounds == null) {
            return;
        }
        if (this.mRecordedWindowContainer == null) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING, -6620483833570774987L, 1, "Content Recording: Unexpectedly null window container; unable to update recording for display %d", java.lang.Long.valueOf(this.mDisplayContent.getDisplayId()));
            return;
        }
        if (this.mContentRecordingSession.getContentToRecord() == 1 && this.mRecordedWindowContainer.asTask().inPinnedWindowingMode()) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING, 7226080178642957768L, 1, "Content Recording: Display %d was already recording, but pause capture since the task is in PIP", java.lang.Long.valueOf(this.mDisplayContent.getDisplayId()));
            pauseRecording();
            return;
        }
        int windowingMode = this.mRecordedWindowContainer.getWindowingMode();
        if (i2 != windowingMode) {
            this.mMediaProjectionManager.notifyWindowingModeChanged(this.mContentRecordingSession.getContentToRecord(), this.mContentRecordingSession.getTargetUid(), windowingMode);
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING, -311001578548807570L, 1, "Content Recording: Display %d was already recording, so apply transformations if necessary", java.lang.Long.valueOf(this.mDisplayContent.getDisplayId()));
        android.graphics.Rect bounds = this.mRecordedWindowContainer.getBounds();
        int i3 = this.mRecordedWindowContainer.getConfiguration().orientation;
        android.graphics.Point fetchSurfaceSizeIfPresent = fetchSurfaceSizeIfPresent();
        if (!this.mLastRecordedBounds.equals(bounds) || i != i3 || !this.mLastConsumingSurfaceSize.equals(fetchSurfaceSizeIfPresent)) {
            if (fetchSurfaceSizeIfPresent != null) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING, 2350883351096538149L, 17, "Content Recording: Going ahead with updating recording for display %d to new bounds %s and/or orientation %d and/or surface size %s", java.lang.Long.valueOf(this.mDisplayContent.getDisplayId()), java.lang.String.valueOf(bounds), java.lang.Long.valueOf(i3), java.lang.String.valueOf(fetchSurfaceSizeIfPresent));
                updateMirroredSurface(this.mRecordedWindowContainer.getSyncTransaction(), bounds, fetchSurfaceSizeIfPresent);
                return;
            }
            long displayId = this.mDisplayContent.getDisplayId();
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING, 8446758574558556540L, 17, "Content Recording: Unable to update recording for display %d to new bounds %s and/or orientation %d and/or surface size %s, since the surface is not available.", java.lang.Long.valueOf(displayId), java.lang.String.valueOf(bounds), java.lang.Long.valueOf(i3), java.lang.String.valueOf(fetchSurfaceSizeIfPresent));
        }
    }

    void pauseRecording() {
        if (this.mRecordedSurface == null) {
            return;
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING, -4320004054011530388L, 13, "Content Recording: Display %d has content (%b) so pause recording", java.lang.Long.valueOf(this.mDisplayContent.getDisplayId()), java.lang.Boolean.valueOf(this.mDisplayContent.getLastHasContent()));
        this.mDisplayContent.mWmService.mTransactionFactory.get().remove(this.mRecordedSurface).reparent(this.mDisplayContent.getWindowingLayer(), this.mDisplayContent.getSurfaceControl()).reparent(this.mDisplayContent.getOverlayLayer(), this.mDisplayContent.getSurfaceControl()).apply();
        this.mRecordedSurface = null;
    }

    void stopRecording() {
        unregisterListener();
        if (this.mRecordedSurface != null) {
            this.mDisplayContent.mWmService.mTransactionFactory.get().remove(this.mRecordedSurface).apply();
            this.mRecordedSurface = null;
            clearContentRecordingSession();
        }
    }

    private void stopMediaProjection() {
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING, 5951434375221687741L, 1, "Content Recording: Stop MediaProjection on virtual display %d", java.lang.Long.valueOf(this.mDisplayContent.getDisplayId()));
        if (this.mMediaProjectionManager != null) {
            this.mMediaProjectionManager.stopActiveProjection();
        }
    }

    private void clearContentRecordingSession() {
        this.mContentRecordingSession = null;
        this.mDisplayContent.mWmService.mContentRecordingController.setContentRecordingSessionLocked(null, this.mDisplayContent.mWmService);
    }

    private void unregisterListener() {
        com.android.server.wm.Task asTask = this.mRecordedWindowContainer != null ? this.mRecordedWindowContainer.asTask() : null;
        if (asTask == null || !isRecordingContentTask()) {
            return;
        }
        asTask.unregisterWindowContainerListener(this);
        this.mRecordedWindowContainer = null;
    }

    private void startRecordingIfNeeded() {
        if (!this.mDisplayContent.getLastHasContent() && !isCurrentlyRecording()) {
            if (this.mDisplayContent.getDisplayInfo().state == 1 || this.mContentRecordingSession == null) {
                return;
            }
            if (this.mContentRecordingSession.isWaitingForConsent()) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING, -3395581813971405090L, 0, "Content Recording: waiting to record, so do nothing", null);
                return;
            }
            this.mRecordedWindowContainer = retrieveRecordedWindowContainer();
            if (this.mRecordedWindowContainer == null) {
                return;
            }
            int contentToRecord = this.mContentRecordingSession.getContentToRecord();
            if (contentToRecord == 1 && this.mRecordedWindowContainer.asTask().inPinnedWindowingMode()) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING, 6779858226066635065L, 1, "Content Recording: Display %d should start recording, but don't yet since the task is in PIP", java.lang.Long.valueOf(this.mDisplayContent.getDisplayId()));
                return;
            }
            android.graphics.Point fetchSurfaceSizeIfPresent = fetchSurfaceSizeIfPresent();
            if (fetchSurfaceSizeIfPresent == null) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING, 7051210836345306671L, 1, "Content Recording: Unable to start recording for display %d since the surface is not available.", java.lang.Long.valueOf(this.mDisplayContent.getDisplayId()));
                return;
            }
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING, 2255758299558330282L, 5, "Content Recording: Display %d has no content and is on, so start recording for state %d", java.lang.Long.valueOf(this.mDisplayContent.getDisplayId()), java.lang.Long.valueOf(this.mDisplayContent.getDisplayInfo().state));
            this.mRecordedSurface = android.view.SurfaceControl.mirrorSurface(this.mRecordedWindowContainer.getSurfaceControl());
            android.view.SurfaceControl.Transaction reparent = this.mDisplayContent.mWmService.mTransactionFactory.get().reparent(this.mRecordedSurface, this.mDisplayContent.getSurfaceControl()).reparent(this.mDisplayContent.getWindowingLayer(), null).reparent(this.mDisplayContent.getOverlayLayer(), null);
            updateMirroredSurface(reparent, this.mRecordedWindowContainer.getBounds(), fetchSurfaceSizeIfPresent);
            reparent.apply();
            if (contentToRecord == 1) {
                this.mMediaProjectionManager.notifyActiveProjectionCapturedContentVisibilityChanged(this.mRecordedWindowContainer.asTask().isVisibleRequested());
            } else {
                this.mMediaProjectionManager.notifyActiveProjectionCapturedContentVisibilityChanged(this.mRecordedWindowContainer.asDisplayContent().getDisplayInfo().state != 1);
            }
            this.mMediaProjectionManager.notifyWindowingModeChanged(contentToRecord, this.mContentRecordingSession.getTargetUid(), this.mRecordedWindowContainer.getWindowConfiguration().getWindowingMode());
        }
    }

    @android.annotation.Nullable
    private com.android.server.wm.WindowContainer retrieveRecordedWindowContainer() {
        int contentToRecord = this.mContentRecordingSession.getContentToRecord();
        android.os.IBinder tokenToRecord = this.mContentRecordingSession.getTokenToRecord();
        switch (contentToRecord) {
            case 0:
                com.android.server.wm.DisplayContent displayContent = this.mDisplayContent.mWmService.mRoot.getDisplayContent(this.mContentRecordingSession.getDisplayToRecord());
                if (displayContent == null) {
                    this.mDisplayContent.mWmService.mDisplayManagerInternal.setWindowManagerMirroring(this.mDisplayContent.getDisplayId(), false);
                    handleStartRecordingFailed();
                    com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING, 2269158922723670768L, 1, "Unable to retrieve window container to start recording for display %d", java.lang.Long.valueOf(this.mDisplayContent.getDisplayId()));
                    break;
                }
                break;
            case 1:
                if (tokenToRecord == null) {
                    handleStartRecordingFailed();
                    com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING, -2177493963028285555L, 1, "Content Recording: Unable to start recording due to null token for display %d", java.lang.Long.valueOf(this.mDisplayContent.getDisplayId()));
                    break;
                } else {
                    com.android.server.wm.Task asTask = com.android.server.wm.WindowContainer.fromBinder(tokenToRecord).asTask();
                    if (asTask == null) {
                        handleStartRecordingFailed();
                        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING, -928577038848872043L, 1, "Content Recording: Unable to retrieve task to start recording for display %d", java.lang.Long.valueOf(this.mDisplayContent.getDisplayId()));
                        break;
                    } else {
                        asTask.registerWindowContainerListener(this);
                        break;
                    }
                }
            default:
                handleStartRecordingFailed();
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING, -3564317873468917405L, 1, "Content Recording: Unable to start recording due to invalid region for display %d", java.lang.Long.valueOf(this.mDisplayContent.getDisplayId()));
                break;
        }
        return null;
    }

    private void handleStartRecordingFailed() {
        boolean isRecordingContentTask = isRecordingContentTask();
        unregisterListener();
        clearContentRecordingSession();
        if (isRecordingContentTask) {
            stopMediaProjection();
        }
    }

    private void computeScaling(int i, int i2, float f, float f2, int i3, int i4, float f3, float f4, android.graphics.PointF pointF) {
        float f5 = (f2 / f) / (f4 / f3);
        if (!this.mCorrectForAnisotropicPixels || (f5 > 0.975f && f5 < 1.025f)) {
            float min = java.lang.Math.min(i3 / i, i4 / i2);
            pointF.x = min;
            pointF.y = min;
        } else {
            float f6 = f3 / f;
            float f7 = f4 / f2;
            float min2 = java.lang.Math.min((i3 / f6) / i, (i4 / f7) / i2);
            pointF.x = f6 * min2;
            pointF.y = min2 * f7;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void updateMirroredSurface(android.view.SurfaceControl.Transaction transaction, android.graphics.Rect rect, android.graphics.Point point) {
        int i;
        android.view.DisplayInfo displayInfo = this.mRecordedWindowContainer.mDisplayContent.getDisplayInfo();
        android.view.DisplayInfo displayInfo2 = this.mDisplayContent.getDisplayInfo();
        android.graphics.PointF pointF = new android.graphics.PointF();
        computeScaling(rect.width(), rect.height(), displayInfo.physicalXDpi, displayInfo.physicalYDpi, point.x, point.y, displayInfo2.physicalXDpi, displayInfo2.physicalYDpi, pointF);
        int round = java.lang.Math.round(pointF.x * rect.width());
        int round2 = java.lang.Math.round(pointF.y * rect.height());
        if (round == point.x) {
            i = 0;
        } else {
            i = (point.x - round) / 2;
        }
        int i2 = round2 != point.y ? (point.y - round2) / 2 : 0;
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING, 1100676037289065396L, 1398181, "Content Recording: Apply transformations of shift %d x %d, scale %f x %f, crop (aka recorded content size) %d x %d for display %d; display has size %d x %d; surface has size %d x %d", java.lang.Long.valueOf(i), java.lang.Long.valueOf(i2), java.lang.Double.valueOf(pointF.x), java.lang.Double.valueOf(pointF.y), java.lang.Long.valueOf(rect.width()), java.lang.Long.valueOf(rect.height()), java.lang.Long.valueOf(this.mDisplayContent.getDisplayId()), java.lang.Long.valueOf(this.mDisplayContent.getConfiguration().screenWidthDp), java.lang.Long.valueOf(this.mDisplayContent.getConfiguration().screenHeightDp), java.lang.Long.valueOf(point.x), java.lang.Long.valueOf(point.y));
        transaction.setWindowCrop(this.mRecordedSurface, rect.width(), rect.height()).setMatrix(this.mRecordedSurface, pointF.x, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, pointF.y).setPosition(this.mRecordedSurface, i, i2);
        this.mLastRecordedBounds = new android.graphics.Rect(rect);
        this.mLastConsumingSurfaceSize.x = point.x;
        this.mLastConsumingSurfaceSize.y = point.y;
        this.mMediaProjectionManager.notifyActiveProjectionCapturedContentResized(this.mLastRecordedBounds.width(), this.mLastRecordedBounds.height());
    }

    @android.annotation.Nullable
    private android.graphics.Point fetchSurfaceSizeIfPresent() {
        android.graphics.Point displaySurfaceDefaultSize = this.mDisplayContent.mWmService.mDisplayManagerInternal.getDisplaySurfaceDefaultSize(this.mDisplayContent.getDisplayId());
        if (displaySurfaceDefaultSize == null) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING, 2330946591287751995L, 1, "Content Recording: Provided surface for recording on display %d is not present, so do not update the surface", java.lang.Long.valueOf(this.mDisplayContent.getDisplayId()));
            return null;
        }
        return displaySurfaceDefaultSize;
    }

    @Override // com.android.server.wm.WindowContainerListener
    public void onRemoved() {
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING, 7993045936648632984L, 1, "Content Recording: Recorded task is removed, so stop recording on display %d", java.lang.Long.valueOf(this.mDisplayContent.getDisplayId()));
        unregisterListener();
        clearContentRecordingSession();
        stopMediaProjection();
    }

    @Override // com.android.server.wm.ConfigurationContainerListener
    public void onMergedOverrideConfigurationChanged(android.content.res.Configuration configuration) {
        super.onMergedOverrideConfigurationChanged(configuration);
        onConfigurationChanged(this.mLastOrientation, this.mLastWindowingMode);
        this.mLastOrientation = configuration.orientation;
        this.mLastWindowingMode = configuration.windowConfiguration.getWindowingMode();
    }

    @Override // com.android.server.wm.WindowContainerListener
    public void onVisibleRequestedChanged(boolean z) {
        if (isCurrentlyRecording() && this.mLastRecordedBounds != null) {
            this.mMediaProjectionManager.notifyActiveProjectionCapturedContentVisibilityChanged(z);
            if (this.mContentRecordingSession.getContentToRecord() == 1) {
                this.mRecordedWindowContainer.getSyncTransaction().setVisibility(this.mRecordedSurface, z);
                this.mRecordedWindowContainer.scheduleAnimation();
            }
        }
    }

    private static final class RemoteMediaProjectionManagerWrapper implements com.android.server.wm.ContentRecorder.MediaProjectionManagerWrapper {
        private final int mDisplayId;

        @android.annotation.Nullable
        private android.media.projection.IMediaProjectionManager mIMediaProjectionManager = null;

        RemoteMediaProjectionManagerWrapper(int i) {
            this.mDisplayId = i;
        }

        @Override // com.android.server.wm.ContentRecorder.MediaProjectionManagerWrapper
        public void stopActiveProjection() {
            fetchMediaProjectionManager();
            if (this.mIMediaProjectionManager == null) {
                return;
            }
            try {
                com.android.internal.protolog.ProtoLogImpl_1545807451.e(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING, 3197882223327917085L, 1, "Content Recording: stopping active projection for display %d", java.lang.Long.valueOf(this.mDisplayId));
                this.mIMediaProjectionManager.stopActiveProjection();
            } catch (android.os.RemoteException e) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.e(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING, 4391984931064789228L, 1, "Content Recording: Unable to tell MediaProjectionManagerService to stop the active projection for display %d: %s", java.lang.Long.valueOf(this.mDisplayId), java.lang.String.valueOf(e));
            }
        }

        @Override // com.android.server.wm.ContentRecorder.MediaProjectionManagerWrapper
        public void notifyActiveProjectionCapturedContentResized(int i, int i2) {
            fetchMediaProjectionManager();
            if (this.mIMediaProjectionManager == null) {
                return;
            }
            try {
                this.mIMediaProjectionManager.notifyActiveProjectionCapturedContentResized(i, i2);
            } catch (android.os.RemoteException e) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.e(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING, 6721270269112237694L, 0, "Content Recording: Unable to tell MediaProjectionManagerService about resizing the active projection: %s", java.lang.String.valueOf(e));
            }
        }

        @Override // com.android.server.wm.ContentRecorder.MediaProjectionManagerWrapper
        public void notifyActiveProjectionCapturedContentVisibilityChanged(boolean z) {
            fetchMediaProjectionManager();
            if (this.mIMediaProjectionManager == null) {
                return;
            }
            try {
                this.mIMediaProjectionManager.notifyActiveProjectionCapturedContentVisibilityChanged(z);
            } catch (android.os.RemoteException e) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.e(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING, 1600318776990120244L, 0, "Content Recording: Unable to tell MediaProjectionManagerService about visibility change on the active projection: %s", java.lang.String.valueOf(e));
            }
        }

        @Override // com.android.server.wm.ContentRecorder.MediaProjectionManagerWrapper
        public void notifyWindowingModeChanged(int i, int i2, int i3) {
            fetchMediaProjectionManager();
            if (this.mIMediaProjectionManager == null) {
                return;
            }
            try {
                this.mIMediaProjectionManager.notifyWindowingModeChanged(i, i2, i3);
            } catch (android.os.RemoteException e) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.e(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING, -1451477179301743956L, 0, "Content Recording: Unable to tell log windowing mode change: %s", java.lang.String.valueOf(e));
            }
        }

        private void fetchMediaProjectionManager() {
            android.os.IBinder service;
            if (this.mIMediaProjectionManager != null || (service = android.os.ServiceManager.getService("media_projection")) == null) {
                return;
            }
            this.mIMediaProjectionManager = android.media.projection.IMediaProjectionManager.Stub.asInterface(service);
        }
    }

    private boolean isRecordingContentTask() {
        return this.mContentRecordingSession != null && this.mContentRecordingSession.getContentToRecord() == 1;
    }
}
