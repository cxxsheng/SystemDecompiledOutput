package com.android.server.voiceinteraction;

/* loaded from: classes2.dex */
final class SoftwareTrustedHotwordDetectorSession extends com.android.server.voiceinteraction.DetectorSession {
    private static final java.lang.String TAG = "SoftwareTrustedHotwordDetectorSession";

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mPerformingSoftwareHotwordDetection;
    private android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback mSoftwareCallback;

    SoftwareTrustedHotwordDetectorSession(@android.annotation.NonNull com.android.server.voiceinteraction.HotwordDetectionConnection.ServiceConnection serviceConnection, @android.annotation.NonNull java.lang.Object obj, @android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull com.android.internal.app.IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback, int i, android.media.permission.Identity identity, @android.annotation.NonNull java.util.concurrent.ScheduledExecutorService scheduledExecutorService, boolean z, @android.annotation.NonNull com.android.server.voiceinteraction.VoiceInteractionManagerServiceImpl.DetectorRemoteExceptionListener detectorRemoteExceptionListener, int i2) {
        super(serviceConnection, obj, context, iBinder, iHotwordRecognitionStatusCallback, i, identity, scheduledExecutorService, z, detectorRemoteExceptionListener, i2);
    }

    void startListeningFromMicLocked(android.media.AudioFormat audioFormat, android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback iMicrophoneHotwordDetectionVoiceInteractionCallback) {
        this.mSoftwareCallback = iMicrophoneHotwordDetectionVoiceInteractionCallback;
        if (this.mPerformingSoftwareHotwordDetection) {
            android.util.Slog.i(TAG, "Hotword validation is already in progress, ignoring.");
        } else {
            this.mPerformingSoftwareHotwordDetection = true;
            startListeningFromMicLocked();
        }
    }

    private void startListeningFromMicLocked() {
        final android.service.voice.IDspHotwordDetectionCallback.Stub stub = new android.service.voice.IDspHotwordDetectionCallback.Stub() { // from class: com.android.server.voiceinteraction.SoftwareTrustedHotwordDetectorSession.1
            public void onDetected(android.service.voice.HotwordDetectedResult hotwordDetectedResult) throws android.os.RemoteException {
                synchronized (com.android.server.voiceinteraction.SoftwareTrustedHotwordDetectorSession.this.mLock) {
                    try {
                        com.android.server.voiceinteraction.HotwordMetricsLogger.writeKeyphraseTriggerEvent(2, 5, com.android.server.voiceinteraction.SoftwareTrustedHotwordDetectorSession.this.mVoiceInteractionServiceUid);
                        if (!com.android.server.voiceinteraction.SoftwareTrustedHotwordDetectorSession.this.mPerformingSoftwareHotwordDetection) {
                            android.util.Slog.i(com.android.server.voiceinteraction.SoftwareTrustedHotwordDetectorSession.TAG, "Hotword detection has already completed");
                            com.android.server.voiceinteraction.HotwordMetricsLogger.writeKeyphraseTriggerEvent(2, 7, com.android.server.voiceinteraction.SoftwareTrustedHotwordDetectorSession.this.mVoiceInteractionServiceUid);
                            return;
                        }
                        com.android.server.voiceinteraction.SoftwareTrustedHotwordDetectorSession.this.mPerformingSoftwareHotwordDetection = false;
                        try {
                            com.android.server.voiceinteraction.SoftwareTrustedHotwordDetectorSession.this.enforcePermissionsForDataDelivery();
                            com.android.server.voiceinteraction.SoftwareTrustedHotwordDetectorSession.this.saveProximityValueToBundle(hotwordDetectedResult);
                            try {
                                android.service.voice.HotwordDetectedResult startCopyingAudioStreams = com.android.server.voiceinteraction.SoftwareTrustedHotwordDetectorSession.this.mHotwordAudioStreamCopier.startCopyingAudioStreams(hotwordDetectedResult);
                                try {
                                    com.android.server.voiceinteraction.SoftwareTrustedHotwordDetectorSession.this.mSoftwareCallback.onDetected(startCopyingAudioStreams, (android.media.AudioFormat) null, (android.os.ParcelFileDescriptor) null);
                                    android.util.Slog.i(com.android.server.voiceinteraction.SoftwareTrustedHotwordDetectorSession.TAG, "Egressed " + android.service.voice.HotwordDetectedResult.getUsageSize(startCopyingAudioStreams) + " bits from hotword trusted process");
                                    if (com.android.server.voiceinteraction.SoftwareTrustedHotwordDetectorSession.this.mDebugHotwordLogging) {
                                        android.util.Slog.i(com.android.server.voiceinteraction.SoftwareTrustedHotwordDetectorSession.TAG, "Egressed detected result: " + startCopyingAudioStreams);
                                    }
                                } catch (android.os.RemoteException e) {
                                    com.android.server.voiceinteraction.SoftwareTrustedHotwordDetectorSession.this.notifyOnDetectorRemoteException();
                                    com.android.server.voiceinteraction.HotwordMetricsLogger.writeDetectorEvent(2, 17, com.android.server.voiceinteraction.SoftwareTrustedHotwordDetectorSession.this.mVoiceInteractionServiceUid);
                                    throw e;
                                }
                            } catch (java.io.IOException e2) {
                                android.util.Slog.w(com.android.server.voiceinteraction.SoftwareTrustedHotwordDetectorSession.TAG, "Ignoring #onDetected due to a IOException", e2);
                                try {
                                    com.android.server.voiceinteraction.SoftwareTrustedHotwordDetectorSession.this.mSoftwareCallback.onHotwordDetectionServiceFailure(new android.service.voice.HotwordDetectionServiceFailure(6, "Copy audio stream failure."));
                                } catch (android.os.RemoteException e3) {
                                    com.android.server.voiceinteraction.SoftwareTrustedHotwordDetectorSession.this.notifyOnDetectorRemoteException();
                                    com.android.server.voiceinteraction.HotwordMetricsLogger.writeDetectorEvent(2, 15, com.android.server.voiceinteraction.SoftwareTrustedHotwordDetectorSession.this.mVoiceInteractionServiceUid);
                                    throw e3;
                                }
                            }
                        } catch (java.lang.SecurityException e4) {
                            android.util.Slog.w(com.android.server.voiceinteraction.SoftwareTrustedHotwordDetectorSession.TAG, "Ignoring #onDetected due to a SecurityException", e4);
                            com.android.server.voiceinteraction.HotwordMetricsLogger.writeKeyphraseTriggerEvent(2, 8, com.android.server.voiceinteraction.SoftwareTrustedHotwordDetectorSession.this.mVoiceInteractionServiceUid);
                            try {
                                com.android.server.voiceinteraction.SoftwareTrustedHotwordDetectorSession.this.mSoftwareCallback.onHotwordDetectionServiceFailure(new android.service.voice.HotwordDetectionServiceFailure(5, "Security exception occurs in #onDetected method."));
                            } catch (android.os.RemoteException e5) {
                                com.android.server.voiceinteraction.SoftwareTrustedHotwordDetectorSession.this.notifyOnDetectorRemoteException();
                                com.android.server.voiceinteraction.HotwordMetricsLogger.writeDetectorEvent(2, 15, com.android.server.voiceinteraction.SoftwareTrustedHotwordDetectorSession.this.mVoiceInteractionServiceUid);
                                throw e5;
                            }
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }

            public void onRejected(android.service.voice.HotwordRejectedResult hotwordRejectedResult) throws android.os.RemoteException {
                com.android.server.voiceinteraction.HotwordMetricsLogger.writeKeyphraseTriggerEvent(2, 6, com.android.server.voiceinteraction.SoftwareTrustedHotwordDetectorSession.this.mVoiceInteractionServiceUid);
            }
        };
        this.mRemoteDetectionService.run(new com.android.internal.infra.ServiceConnector.VoidJob() { // from class: com.android.server.voiceinteraction.SoftwareTrustedHotwordDetectorSession$$ExternalSyntheticLambda1
            public final void runNoResult(java.lang.Object obj) {
                ((android.service.voice.ISandboxedDetectionService) obj).detectFromMicrophoneSource(null, 1, null, null, stub);
            }
        });
        com.android.server.voiceinteraction.HotwordMetricsLogger.writeDetectorEvent(2, 9, this.mVoiceInteractionServiceUid);
    }

    void stopListeningFromMicLocked() {
        if (!this.mPerformingSoftwareHotwordDetection) {
            android.util.Slog.i(TAG, "Hotword detection is not running");
            return;
        }
        this.mPerformingSoftwareHotwordDetection = false;
        this.mRemoteDetectionService.run(new com.android.server.voiceinteraction.SoftwareTrustedHotwordDetectorSession$$ExternalSyntheticLambda0());
        closeExternalAudioStreamLocked("stopping requested");
    }

    @Override // com.android.server.voiceinteraction.DetectorSession
    void informRestartProcessLocked() {
        android.util.Slog.v(TAG, "informRestartProcessLocked");
        this.mUpdateStateAfterStartFinished.set(false);
        try {
            this.mCallback.onProcessRestarted();
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Failed to communicate #onProcessRestarted", e);
            com.android.server.voiceinteraction.HotwordMetricsLogger.writeDetectorEvent(2, 18, this.mVoiceInteractionServiceUid);
            notifyOnDetectorRemoteException();
        }
        if (this.mPerformingSoftwareHotwordDetection) {
            android.util.Slog.i(TAG, "Process restarted: calling startRecognition() again");
            startListeningFromMicLocked();
        }
        this.mPerformingExternalSourceHotwordDetection = false;
        closeExternalAudioStreamLocked("process restarted");
    }

    @Override // com.android.server.voiceinteraction.DetectorSession
    public void dumpLocked(java.lang.String str, java.io.PrintWriter printWriter) {
        super.dumpLocked(str, printWriter);
        printWriter.print(str);
        printWriter.print("mPerformingSoftwareHotwordDetection=");
        printWriter.println(this.mPerformingSoftwareHotwordDetection);
    }
}
