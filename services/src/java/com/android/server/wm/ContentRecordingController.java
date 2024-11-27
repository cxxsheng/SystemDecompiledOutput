package com.android.server.wm;

/* loaded from: classes3.dex */
final class ContentRecordingController {

    @android.annotation.Nullable
    private android.view.ContentRecordingSession mSession = null;

    @android.annotation.Nullable
    private com.android.server.wm.DisplayContent mDisplayContent = null;

    ContentRecordingController() {
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    android.view.ContentRecordingSession getContentRecordingSessionLocked() {
        return this.mSession;
    }

    void setContentRecordingSessionLocked(@android.annotation.Nullable android.view.ContentRecordingSession contentRecordingSession, @android.annotation.NonNull com.android.server.wm.WindowManagerService windowManagerService) {
        com.android.server.wm.DisplayContent displayContent;
        if (contentRecordingSession != null && !android.view.ContentRecordingSession.isValid(contentRecordingSession)) {
            return;
        }
        boolean z = (this.mSession == null || contentRecordingSession == null || !this.mSession.isWaitingForConsent() || contentRecordingSession.isWaitingForConsent()) ? false : true;
        if (android.view.ContentRecordingSession.isProjectionOnSameDisplay(this.mSession, contentRecordingSession)) {
            if (z) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING, -225319884529912382L, 1, "Content Recording: Accept session updating same display %d with granted consent, with an existing session %s", java.lang.Long.valueOf(contentRecordingSession.getVirtualDisplayId()), java.lang.String.valueOf(this.mSession.getVirtualDisplayId()));
            } else {
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING, -5981322449150461244L, 1, "Content Recording: Ignoring session on same display %d, with an existing session %s", java.lang.Long.valueOf(contentRecordingSession.getVirtualDisplayId()), java.lang.String.valueOf(this.mSession.getVirtualDisplayId()));
                return;
            }
        }
        if (contentRecordingSession == null) {
            displayContent = null;
        } else {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING, 4226710957373144819L, 1, "Content Recording: Handle incoming session on display %d, with a pre-existing session %s", java.lang.Long.valueOf(contentRecordingSession.getVirtualDisplayId()), java.lang.String.valueOf(this.mSession == null ? null : java.lang.Integer.valueOf(this.mSession.getVirtualDisplayId())));
            displayContent = windowManagerService.mRoot.getDisplayContentOrCreate(contentRecordingSession.getVirtualDisplayId());
            if (displayContent == null) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING, -1415855962859555663L, 1, "Content Recording: Incoming session on display %d can't be set since it is already null; the corresponding VirtualDisplay must have already been removed.", java.lang.Long.valueOf(contentRecordingSession.getVirtualDisplayId()));
                return;
            } else {
                displayContent.setContentRecordingSession(contentRecordingSession);
                displayContent.updateRecording();
            }
        }
        if (this.mSession != null && !z) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING, -5750232782380780139L, 0, "Content Recording: Pause the recording session on display %s", java.lang.String.valueOf(this.mDisplayContent.getDisplayId()));
            this.mDisplayContent.pauseRecording();
            this.mDisplayContent.setContentRecordingSession(null);
        }
        this.mDisplayContent = displayContent;
        this.mSession = contentRecordingSession;
    }
}
