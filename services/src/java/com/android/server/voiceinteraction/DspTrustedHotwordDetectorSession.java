package com.android.server.voiceinteraction;

/* loaded from: classes2.dex */
final class DspTrustedHotwordDetectorSession extends com.android.server.voiceinteraction.DetectorSession {
    private static final long MAX_VALIDATION_TIMEOUT_MILLIS = 4000;
    private static final java.lang.String TAG = "DspTrustedHotwordDetectorSession";
    private static final long VALIDATION_TIMEOUT_MILLIS = 3000;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.util.concurrent.ScheduledFuture<?> mCancellationKeyPhraseDetectionFuture;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.service.voice.HotwordRejectedResult mLastHotwordRejectedResult;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mValidatingDspTrigger;

    DspTrustedHotwordDetectorSession(@android.annotation.NonNull com.android.server.voiceinteraction.HotwordDetectionConnection.ServiceConnection serviceConnection, @android.annotation.NonNull java.lang.Object obj, @android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull com.android.internal.app.IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback, int i, android.media.permission.Identity identity, @android.annotation.NonNull java.util.concurrent.ScheduledExecutorService scheduledExecutorService, boolean z, @android.annotation.NonNull com.android.server.voiceinteraction.VoiceInteractionManagerServiceImpl.DetectorRemoteExceptionListener detectorRemoteExceptionListener, int i2) {
        super(serviceConnection, obj, context, iBinder, iHotwordRecognitionStatusCallback, i, identity, scheduledExecutorService, z, detectorRemoteExceptionListener, i2);
        this.mValidatingDspTrigger = false;
        this.mLastHotwordRejectedResult = null;
    }

    void detectFromDspSourceLocked(final android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent, final com.android.internal.app.IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback) {
        final java.util.concurrent.atomic.AtomicBoolean atomicBoolean = new java.util.concurrent.atomic.AtomicBoolean(false);
        final android.service.voice.IDspHotwordDetectionCallback.Stub stub = new android.service.voice.IDspHotwordDetectionCallback.Stub() { // from class: com.android.server.voiceinteraction.DspTrustedHotwordDetectorSession.1
            public void onDetected(android.service.voice.HotwordDetectedResult hotwordDetectedResult) throws android.os.RemoteException {
                synchronized (com.android.server.voiceinteraction.DspTrustedHotwordDetectorSession.this.mLock) {
                    try {
                        if (com.android.server.voiceinteraction.DspTrustedHotwordDetectorSession.this.mCancellationKeyPhraseDetectionFuture != null) {
                            com.android.server.voiceinteraction.DspTrustedHotwordDetectorSession.this.mCancellationKeyPhraseDetectionFuture.cancel(true);
                        }
                        if (atomicBoolean.get()) {
                            return;
                        }
                        com.android.server.voiceinteraction.HotwordMetricsLogger.writeKeyphraseTriggerEvent(1, 5, com.android.server.voiceinteraction.DspTrustedHotwordDetectorSession.this.mVoiceInteractionServiceUid);
                        if (!com.android.server.voiceinteraction.DspTrustedHotwordDetectorSession.this.mValidatingDspTrigger) {
                            android.util.Slog.i(com.android.server.voiceinteraction.DspTrustedHotwordDetectorSession.TAG, "Ignoring #onDetected due to a process restart or previous #onRejected result = " + com.android.server.voiceinteraction.DspTrustedHotwordDetectorSession.this.mLastHotwordRejectedResult);
                            com.android.server.voiceinteraction.HotwordMetricsLogger.writeKeyphraseTriggerEvent(1, 7, com.android.server.voiceinteraction.DspTrustedHotwordDetectorSession.this.mVoiceInteractionServiceUid);
                            return;
                        }
                        com.android.server.voiceinteraction.DspTrustedHotwordDetectorSession.this.mValidatingDspTrigger = false;
                        try {
                            com.android.server.voiceinteraction.DspTrustedHotwordDetectorSession.this.enforcePermissionsForDataDelivery();
                            com.android.server.voiceinteraction.DspTrustedHotwordDetectorSession.this.enforceExtraKeyphraseIdNotLeaked(hotwordDetectedResult, keyphraseRecognitionEvent);
                            com.android.server.voiceinteraction.DspTrustedHotwordDetectorSession.this.saveProximityValueToBundle(hotwordDetectedResult);
                            try {
                                android.service.voice.HotwordDetectedResult startCopyingAudioStreams = com.android.server.voiceinteraction.DspTrustedHotwordDetectorSession.this.mHotwordAudioStreamCopier.startCopyingAudioStreams(hotwordDetectedResult);
                                try {
                                    iHotwordRecognitionStatusCallback.onKeyphraseDetected(keyphraseRecognitionEvent, startCopyingAudioStreams);
                                    android.util.Slog.i(com.android.server.voiceinteraction.DspTrustedHotwordDetectorSession.TAG, "Egressed " + android.service.voice.HotwordDetectedResult.getUsageSize(startCopyingAudioStreams) + " bits from hotword trusted process");
                                    if (com.android.server.voiceinteraction.DspTrustedHotwordDetectorSession.this.mDebugHotwordLogging) {
                                        android.util.Slog.i(com.android.server.voiceinteraction.DspTrustedHotwordDetectorSession.TAG, "Egressed detected result: " + startCopyingAudioStreams);
                                    }
                                } catch (android.os.RemoteException e) {
                                    com.android.server.voiceinteraction.DspTrustedHotwordDetectorSession.this.notifyOnDetectorRemoteException();
                                    com.android.server.voiceinteraction.HotwordMetricsLogger.writeDetectorEvent(1, 17, com.android.server.voiceinteraction.DspTrustedHotwordDetectorSession.this.mVoiceInteractionServiceUid);
                                    throw e;
                                }
                            } catch (java.io.IOException e2) {
                                try {
                                    android.util.Slog.w(com.android.server.voiceinteraction.DspTrustedHotwordDetectorSession.TAG, "Ignoring #onDetected due to a IOException", e2);
                                    iHotwordRecognitionStatusCallback.onHotwordDetectionServiceFailure(new android.service.voice.HotwordDetectionServiceFailure(6, "Copy audio stream failure."));
                                } catch (android.os.RemoteException e3) {
                                    com.android.server.voiceinteraction.DspTrustedHotwordDetectorSession.this.notifyOnDetectorRemoteException();
                                    throw e3;
                                }
                            }
                        } catch (java.lang.SecurityException e4) {
                            android.util.Slog.w(com.android.server.voiceinteraction.DspTrustedHotwordDetectorSession.TAG, "Ignoring #onDetected due to a SecurityException", e4);
                            com.android.server.voiceinteraction.HotwordMetricsLogger.writeKeyphraseTriggerEvent(1, 8, com.android.server.voiceinteraction.DspTrustedHotwordDetectorSession.this.mVoiceInteractionServiceUid);
                            try {
                                iHotwordRecognitionStatusCallback.onHotwordDetectionServiceFailure(new android.service.voice.HotwordDetectionServiceFailure(5, "Security exception occurs in #onDetected method."));
                            } catch (android.os.RemoteException e5) {
                                com.android.server.voiceinteraction.DspTrustedHotwordDetectorSession.this.notifyOnDetectorRemoteException();
                                com.android.server.voiceinteraction.HotwordMetricsLogger.writeDetectorEvent(1, 15, com.android.server.voiceinteraction.DspTrustedHotwordDetectorSession.this.mVoiceInteractionServiceUid);
                                throw e5;
                            }
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }

            public void onRejected(android.service.voice.HotwordRejectedResult hotwordRejectedResult) throws android.os.RemoteException {
                synchronized (com.android.server.voiceinteraction.DspTrustedHotwordDetectorSession.this.mLock) {
                    try {
                        if (com.android.server.voiceinteraction.DspTrustedHotwordDetectorSession.this.mCancellationKeyPhraseDetectionFuture != null) {
                            com.android.server.voiceinteraction.DspTrustedHotwordDetectorSession.this.mCancellationKeyPhraseDetectionFuture.cancel(true);
                        }
                        if (atomicBoolean.get()) {
                            return;
                        }
                        com.android.server.voiceinteraction.HotwordMetricsLogger.writeKeyphraseTriggerEvent(1, 6, com.android.server.voiceinteraction.DspTrustedHotwordDetectorSession.this.mVoiceInteractionServiceUid);
                        if (!com.android.server.voiceinteraction.DspTrustedHotwordDetectorSession.this.mValidatingDspTrigger) {
                            android.util.Slog.i(com.android.server.voiceinteraction.DspTrustedHotwordDetectorSession.TAG, "Ignoring #onRejected due to a process restart");
                            com.android.server.voiceinteraction.HotwordMetricsLogger.writeKeyphraseTriggerEvent(1, 9, com.android.server.voiceinteraction.DspTrustedHotwordDetectorSession.this.mVoiceInteractionServiceUid);
                            return;
                        }
                        com.android.server.voiceinteraction.DspTrustedHotwordDetectorSession.this.mValidatingDspTrigger = false;
                        try {
                            iHotwordRecognitionStatusCallback.onRejected(hotwordRejectedResult);
                            com.android.server.voiceinteraction.DspTrustedHotwordDetectorSession.this.mLastHotwordRejectedResult = hotwordRejectedResult;
                            if (com.android.server.voiceinteraction.DspTrustedHotwordDetectorSession.this.mDebugHotwordLogging && hotwordRejectedResult != null) {
                                android.util.Slog.i(com.android.server.voiceinteraction.DspTrustedHotwordDetectorSession.TAG, "Egressed rejected result: " + hotwordRejectedResult);
                            }
                        } catch (android.os.RemoteException e) {
                            com.android.server.voiceinteraction.DspTrustedHotwordDetectorSession.this.notifyOnDetectorRemoteException();
                            com.android.server.voiceinteraction.HotwordMetricsLogger.writeDetectorEvent(1, 16, com.android.server.voiceinteraction.DspTrustedHotwordDetectorSession.this.mVoiceInteractionServiceUid);
                            throw e;
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        };
        this.mValidatingDspTrigger = true;
        this.mLastHotwordRejectedResult = null;
        this.mRemoteDetectionService.run(new com.android.internal.infra.ServiceConnector.VoidJob() { // from class: com.android.server.voiceinteraction.DspTrustedHotwordDetectorSession$$ExternalSyntheticLambda0
            public final void runNoResult(java.lang.Object obj) {
                com.android.server.voiceinteraction.DspTrustedHotwordDetectorSession.this.lambda$detectFromDspSourceLocked$1(atomicBoolean, iHotwordRecognitionStatusCallback, keyphraseRecognitionEvent, stub, (android.service.voice.ISandboxedDetectionService) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$detectFromDspSourceLocked$1(final java.util.concurrent.atomic.AtomicBoolean atomicBoolean, final com.android.internal.app.IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback, android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent, android.service.voice.IDspHotwordDetectionCallback iDspHotwordDetectionCallback, android.service.voice.ISandboxedDetectionService iSandboxedDetectionService) throws java.lang.Exception {
        this.mCancellationKeyPhraseDetectionFuture = this.mScheduledExecutorService.schedule(new java.lang.Runnable() { // from class: com.android.server.voiceinteraction.DspTrustedHotwordDetectorSession$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.voiceinteraction.DspTrustedHotwordDetectorSession.this.lambda$detectFromDspSourceLocked$0(atomicBoolean, iHotwordRecognitionStatusCallback);
            }
        }, MAX_VALIDATION_TIMEOUT_MILLIS, java.util.concurrent.TimeUnit.MILLISECONDS);
        iSandboxedDetectionService.detectFromDspSource(keyphraseRecognitionEvent, keyphraseRecognitionEvent.getCaptureFormat(), 3000L, iDspHotwordDetectionCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$detectFromDspSourceLocked$0(java.util.concurrent.atomic.AtomicBoolean atomicBoolean, com.android.internal.app.IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback) {
        atomicBoolean.set(true);
        android.util.Slog.w(TAG, "Timed out on #detectFromDspSource");
        com.android.server.voiceinteraction.HotwordMetricsLogger.writeKeyphraseTriggerEvent(1, 2, this.mVoiceInteractionServiceUid);
        try {
            iHotwordRecognitionStatusCallback.onHotwordDetectionServiceFailure(new android.service.voice.HotwordDetectionServiceFailure(4, "Timeout to response to the detection result."));
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Failed to report onError status: ", e);
            com.android.server.voiceinteraction.HotwordMetricsLogger.writeDetectorEvent(1, 15, this.mVoiceInteractionServiceUid);
            notifyOnDetectorRemoteException();
        }
    }

    @Override // com.android.server.voiceinteraction.DetectorSession
    void informRestartProcessLocked() {
        android.util.Slog.v(TAG, "informRestartProcessLocked");
        if (this.mValidatingDspTrigger) {
            try {
                this.mCallback.onRejected(new android.service.voice.HotwordRejectedResult.Builder().build());
                com.android.server.voiceinteraction.HotwordMetricsLogger.writeKeyphraseTriggerEvent(1, 10, this.mVoiceInteractionServiceUid);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(TAG, "Failed to call #rejected");
                com.android.server.voiceinteraction.HotwordMetricsLogger.writeDetectorEvent(1, 16, this.mVoiceInteractionServiceUid);
                notifyOnDetectorRemoteException();
            }
            this.mValidatingDspTrigger = false;
        }
        this.mUpdateStateAfterStartFinished.set(false);
        try {
            this.mCallback.onProcessRestarted();
        } catch (android.os.RemoteException e2) {
            android.util.Slog.w(TAG, "Failed to communicate #onProcessRestarted", e2);
            com.android.server.voiceinteraction.HotwordMetricsLogger.writeDetectorEvent(1, 18, this.mVoiceInteractionServiceUid);
            notifyOnDetectorRemoteException();
        }
        this.mPerformingExternalSourceHotwordDetection = false;
        closeExternalAudioStreamLocked("process restarted");
    }

    @Override // com.android.server.voiceinteraction.DetectorSession
    public void dumpLocked(java.lang.String str, java.io.PrintWriter printWriter) {
        super.dumpLocked(str, printWriter);
        printWriter.print(str);
        printWriter.print("mValidatingDspTrigger=");
        printWriter.println(this.mValidatingDspTrigger);
    }
}
