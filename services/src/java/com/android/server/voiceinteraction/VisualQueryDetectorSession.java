package com.android.server.voiceinteraction;

/* loaded from: classes2.dex */
final class VisualQueryDetectorSession extends com.android.server.voiceinteraction.DetectorSession {
    private static final java.lang.String TAG = "VisualQueryDetectorSession";
    private com.android.internal.app.IVisualQueryDetectionAttentionListener mAttentionListener;
    private boolean mEgressingData;
    private boolean mEnableAccessibilityDataEgress;
    private boolean mQueryStreaming;

    VisualQueryDetectorSession(@android.annotation.NonNull com.android.server.voiceinteraction.HotwordDetectionConnection.ServiceConnection serviceConnection, @android.annotation.NonNull java.lang.Object obj, @android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull com.android.internal.app.IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback, int i, android.media.permission.Identity identity, @android.annotation.NonNull java.util.concurrent.ScheduledExecutorService scheduledExecutorService, boolean z, @android.annotation.NonNull com.android.server.voiceinteraction.VoiceInteractionManagerServiceImpl.DetectorRemoteExceptionListener detectorRemoteExceptionListener, int i2) {
        super(serviceConnection, obj, context, iBinder, iHotwordRecognitionStatusCallback, i, identity, scheduledExecutorService, z, detectorRemoteExceptionListener, i2);
        this.mEgressingData = false;
        this.mQueryStreaming = false;
        this.mAttentionListener = null;
        this.mEnableAccessibilityDataEgress = android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "visual_query_accessibility_detection_enabled", 0, this.mUserId) == 1;
    }

    @Override // com.android.server.voiceinteraction.DetectorSession
    void informRestartProcessLocked() {
        android.util.Slog.v(TAG, "informRestartProcessLocked");
        this.mUpdateStateAfterStartFinished.set(false);
        try {
            this.mCallback.onProcessRestarted();
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Failed to communicate #onProcessRestarted", e);
            notifyOnDetectorRemoteException();
        }
    }

    void setVisualQueryDetectionAttentionListenerLocked(@android.annotation.Nullable com.android.internal.app.IVisualQueryDetectionAttentionListener iVisualQueryDetectionAttentionListener) {
        this.mAttentionListener = iVisualQueryDetectionAttentionListener;
    }

    boolean startPerceivingLocked(final android.service.voice.IVisualQueryDetectionVoiceInteractionCallback iVisualQueryDetectionVoiceInteractionCallback) {
        final android.service.voice.IDetectorSessionVisualQueryDetectionCallback.Stub stub = new android.service.voice.IDetectorSessionVisualQueryDetectionCallback.Stub() { // from class: com.android.server.voiceinteraction.VisualQueryDetectorSession.1
            public void onAttentionGained(android.service.voice.VisualQueryAttentionResult visualQueryAttentionResult) {
                android.util.Slog.v(com.android.server.voiceinteraction.VisualQueryDetectorSession.TAG, "BinderCallback#onAttentionGained");
                synchronized (com.android.server.voiceinteraction.VisualQueryDetectorSession.this.mLock) {
                    com.android.server.voiceinteraction.VisualQueryDetectorSession.this.mEgressingData = true;
                    if (com.android.server.voiceinteraction.VisualQueryDetectorSession.this.mAttentionListener == null) {
                        return;
                    }
                    try {
                        com.android.server.voiceinteraction.VisualQueryDetectorSession.this.mAttentionListener.onAttentionGained(visualQueryAttentionResult);
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.e(com.android.server.voiceinteraction.VisualQueryDetectorSession.TAG, "Error delivering attention gained event.", e);
                        try {
                            iVisualQueryDetectionVoiceInteractionCallback.onVisualQueryDetectionServiceFailure(new android.service.voice.VisualQueryDetectionServiceFailure(3, "Attention listener fails to switch to GAINED state."));
                        } catch (android.os.RemoteException e2) {
                            android.util.Slog.v(com.android.server.voiceinteraction.VisualQueryDetectorSession.TAG, "Fail to call onVisualQueryDetectionServiceFailure");
                        }
                    }
                }
            }

            public void onAttentionLost(int i) {
                android.util.Slog.v(com.android.server.voiceinteraction.VisualQueryDetectorSession.TAG, "BinderCallback#onAttentionLost");
                synchronized (com.android.server.voiceinteraction.VisualQueryDetectorSession.this.mLock) {
                    com.android.server.voiceinteraction.VisualQueryDetectorSession.this.mEgressingData = false;
                    if (com.android.server.voiceinteraction.VisualQueryDetectorSession.this.mAttentionListener == null) {
                        return;
                    }
                    try {
                        com.android.server.voiceinteraction.VisualQueryDetectorSession.this.mAttentionListener.onAttentionLost(i);
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.e(com.android.server.voiceinteraction.VisualQueryDetectorSession.TAG, "Error delivering attention lost event.", e);
                        try {
                            iVisualQueryDetectionVoiceInteractionCallback.onVisualQueryDetectionServiceFailure(new android.service.voice.VisualQueryDetectionServiceFailure(3, "Attention listener fails to switch to LOST state."));
                        } catch (android.os.RemoteException e2) {
                            android.util.Slog.v(com.android.server.voiceinteraction.VisualQueryDetectorSession.TAG, "Fail to call onVisualQueryDetectionServiceFailure");
                        }
                    }
                }
            }

            public void onQueryDetected(@android.annotation.NonNull java.lang.String str) throws android.os.RemoteException {
                android.util.Slog.v(com.android.server.voiceinteraction.VisualQueryDetectorSession.TAG, "BinderCallback#onQueryDetected");
                synchronized (com.android.server.voiceinteraction.VisualQueryDetectorSession.this.mLock) {
                    try {
                        java.util.Objects.requireNonNull(str);
                        if (!com.android.server.voiceinteraction.VisualQueryDetectorSession.this.mEgressingData) {
                            android.util.Slog.v(com.android.server.voiceinteraction.VisualQueryDetectorSession.TAG, "Query should not be egressed within the unattention state.");
                            iVisualQueryDetectionVoiceInteractionCallback.onVisualQueryDetectionServiceFailure(new android.service.voice.VisualQueryDetectionServiceFailure(4, "Cannot stream queries without attention signals."));
                        } else {
                            com.android.server.voiceinteraction.VisualQueryDetectorSession.this.mQueryStreaming = true;
                            iVisualQueryDetectionVoiceInteractionCallback.onQueryDetected(str);
                            android.util.Slog.i(com.android.server.voiceinteraction.VisualQueryDetectorSession.TAG, "Egressed from visual query detection process.");
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }

            public void onResultDetected(@android.annotation.NonNull android.service.voice.VisualQueryDetectedResult visualQueryDetectedResult) throws android.os.RemoteException {
                android.util.Slog.v(com.android.server.voiceinteraction.VisualQueryDetectorSession.TAG, "BinderCallback#onResultDetected");
                synchronized (com.android.server.voiceinteraction.VisualQueryDetectorSession.this.mLock) {
                    try {
                        java.util.Objects.requireNonNull(visualQueryDetectedResult);
                        if (!com.android.server.voiceinteraction.VisualQueryDetectorSession.this.mEgressingData) {
                            android.util.Slog.v(com.android.server.voiceinteraction.VisualQueryDetectorSession.TAG, "Result should not be egressed within the unattention state.");
                            iVisualQueryDetectionVoiceInteractionCallback.onVisualQueryDetectionServiceFailure(new android.service.voice.VisualQueryDetectionServiceFailure(4, "Cannot stream results without attention signals."));
                        } else if (!checkDetectedResultDataLocked(visualQueryDetectedResult)) {
                            android.util.Slog.v(com.android.server.voiceinteraction.VisualQueryDetectorSession.TAG, "Accessibility data can be egressed only when the isAccessibilityDetectionEnabled() is true.");
                            iVisualQueryDetectionVoiceInteractionCallback.onVisualQueryDetectionServiceFailure(new android.service.voice.VisualQueryDetectionServiceFailure(4, "Cannot stream accessibility data without enabling the setting."));
                        } else {
                            com.android.server.voiceinteraction.VisualQueryDetectorSession.this.mQueryStreaming = true;
                            iVisualQueryDetectionVoiceInteractionCallback.onResultDetected(visualQueryDetectedResult);
                            android.util.Slog.i(com.android.server.voiceinteraction.VisualQueryDetectorSession.TAG, "Egressed from visual query detection process.");
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }

            public void onQueryFinished() throws android.os.RemoteException {
                android.util.Slog.v(com.android.server.voiceinteraction.VisualQueryDetectorSession.TAG, "BinderCallback#onQueryFinished");
                synchronized (com.android.server.voiceinteraction.VisualQueryDetectorSession.this.mLock) {
                    try {
                        if (!com.android.server.voiceinteraction.VisualQueryDetectorSession.this.mQueryStreaming) {
                            android.util.Slog.v(com.android.server.voiceinteraction.VisualQueryDetectorSession.TAG, "Query streaming state signal FINISHED is block since there is no active query being streamed.");
                            iVisualQueryDetectionVoiceInteractionCallback.onVisualQueryDetectionServiceFailure(new android.service.voice.VisualQueryDetectionServiceFailure(4, "Cannot send FINISHED signal with no query streamed."));
                        } else {
                            iVisualQueryDetectionVoiceInteractionCallback.onQueryFinished();
                            com.android.server.voiceinteraction.VisualQueryDetectorSession.this.mQueryStreaming = false;
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }

            public void onQueryRejected() throws android.os.RemoteException {
                android.util.Slog.v(com.android.server.voiceinteraction.VisualQueryDetectorSession.TAG, "BinderCallback#onQueryRejected");
                synchronized (com.android.server.voiceinteraction.VisualQueryDetectorSession.this.mLock) {
                    try {
                        if (!com.android.server.voiceinteraction.VisualQueryDetectorSession.this.mQueryStreaming) {
                            android.util.Slog.v(com.android.server.voiceinteraction.VisualQueryDetectorSession.TAG, "Query streaming state signal REJECTED is block since there is no active query being streamed.");
                            iVisualQueryDetectionVoiceInteractionCallback.onVisualQueryDetectionServiceFailure(new android.service.voice.VisualQueryDetectionServiceFailure(4, "Cannot send REJECTED signal with no query streamed."));
                        } else {
                            iVisualQueryDetectionVoiceInteractionCallback.onQueryRejected();
                            com.android.server.voiceinteraction.VisualQueryDetectorSession.this.mQueryStreaming = false;
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }

            private boolean checkDetectedResultDataLocked(android.service.voice.VisualQueryDetectedResult visualQueryDetectedResult) {
                return visualQueryDetectedResult.getAccessibilityDetectionData() == null || com.android.server.voiceinteraction.VisualQueryDetectorSession.this.mEnableAccessibilityDataEgress;
            }
        };
        return this.mRemoteDetectionService.run(new com.android.internal.infra.ServiceConnector.VoidJob() { // from class: com.android.server.voiceinteraction.VisualQueryDetectorSession$$ExternalSyntheticLambda0
            public final void runNoResult(java.lang.Object obj) {
                ((android.service.voice.ISandboxedDetectionService) obj).detectWithVisualSignals(stub);
            }
        });
    }

    boolean stopPerceivingLocked() {
        return this.mRemoteDetectionService.run(new com.android.server.voiceinteraction.SoftwareTrustedHotwordDetectorSession$$ExternalSyntheticLambda0());
    }

    @Override // com.android.server.voiceinteraction.DetectorSession
    void startListeningFromExternalSourceLocked(android.os.ParcelFileDescriptor parcelFileDescriptor, android.media.AudioFormat audioFormat, @android.annotation.Nullable android.os.PersistableBundle persistableBundle, android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback iMicrophoneHotwordDetectionVoiceInteractionCallback) throws java.lang.UnsupportedOperationException {
        throw new java.lang.UnsupportedOperationException("HotwordDetectionService method should not be called from VisualQueryDetectorSession.");
    }

    void updateAccessibilityEgressStateLocked(boolean z) {
        this.mEnableAccessibilityDataEgress = z;
    }

    @Override // com.android.server.voiceinteraction.DetectorSession
    public void dumpLocked(java.lang.String str, java.io.PrintWriter printWriter) {
        super.dumpLocked(str, printWriter);
        printWriter.print(str);
    }
}
