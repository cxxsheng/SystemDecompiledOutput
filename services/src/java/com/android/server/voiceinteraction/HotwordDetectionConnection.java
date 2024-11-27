package com.android.server.voiceinteraction;

/* loaded from: classes2.dex */
final class HotwordDetectionConnection {
    static final boolean DEBUG = false;
    private static final int DETECTION_SERVICE_TYPE_HOTWORD = 1;
    private static final int DETECTION_SERVICE_TYPE_VISUAL_QUERY = 2;
    public static final long ENFORCE_HOTWORD_PHRASE_ID = 215066299;
    private static final java.lang.String KEY_RESTART_PERIOD_IN_SECONDS = "restart_period_in_seconds";
    private static final int MAX_ISOLATED_PROCESS_NUMBER = 10;
    private static final long RESET_DEBUG_HOTWORD_LOGGING_TIMEOUT_MILLIS = 3600000;
    private static final boolean SYSPROP_VISUAL_QUERY_SERVICE_ENABLED = android.os.SystemProperties.getBoolean("ro.hotword.visual_query_service_enabled", false);
    private static final java.lang.String TAG = "HotwordDetectionConnection";

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private android.os.IBinder mAudioFlinger;

    @android.annotation.Nullable
    private final java.util.concurrent.ScheduledFuture<?> mCancellationTaskFuture;
    final android.content.Context mContext;
    private int mDetectorType;
    final android.content.ComponentName mHotwordDetectionComponentName;

    @android.annotation.NonNull
    private final com.android.server.voiceinteraction.HotwordDetectionConnection.ServiceConnectionFactory mHotwordDetectionServiceConnectionFactory;

    @android.annotation.Nullable
    private com.android.internal.app.IHotwordRecognitionStatusCallback mHotwordRecognitionCallback;
    volatile android.service.voice.VoiceInteractionManagerInternal.HotwordDetectionServiceIdentity mIdentity;
    private java.time.Instant mLastRestartInstant;
    final java.lang.Object mLock;
    private com.android.server.voiceinteraction.VoiceInteractionManagerServiceImpl.DetectorRemoteExceptionListener mRemoteExceptionListener;

    @android.annotation.NonNull
    private com.android.server.voiceinteraction.HotwordDetectionConnection.ServiceConnection mRemoteHotwordDetectionService;

    @android.annotation.NonNull
    private com.android.server.voiceinteraction.HotwordDetectionConnection.ServiceConnection mRemoteVisualQueryDetectionService;
    final int mUserId;
    final android.content.ComponentName mVisualQueryDetectionComponentName;

    @android.annotation.NonNull
    private final com.android.server.voiceinteraction.HotwordDetectionConnection.ServiceConnectionFactory mVisualQueryDetectionServiceConnectionFactory;
    final int mVoiceInteractionServiceUid;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private final android.media.permission.Identity mVoiceInteractorIdentity;
    private final java.util.concurrent.ScheduledThreadPoolExecutor mScheduledExecutorService = new java.util.concurrent.ScheduledThreadPoolExecutor(1);
    private final android.os.IBinder.DeathRecipient mAudioServerDeathRecipient = new android.os.IBinder.DeathRecipient() { // from class: com.android.server.voiceinteraction.HotwordDetectionConnection$$ExternalSyntheticLambda10
        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            com.android.server.voiceinteraction.HotwordDetectionConnection.this.audioServerDied();
        }
    };
    private java.util.concurrent.ScheduledFuture<?> mDebugHotwordLoggingTimeoutFuture = null;
    private int mRestartCount = 0;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mDebugHotwordLogging = false;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<com.android.server.voiceinteraction.DetectorSession> mDetectorSessions = new android.util.SparseArray<>();
    private final android.app.AppOpsManager.OnOpChangedListener mOnOpChangedListener = new android.app.AppOpsManager.OnOpChangedListener() { // from class: com.android.server.voiceinteraction.HotwordDetectionConnection.1
        @Override // android.app.AppOpsManager.OnOpChangedListener
        public void onOpChanged(java.lang.String str, java.lang.String str2) {
            if (str.equals("android:receive_sandbox_trigger_audio")) {
                android.app.AppOpsManager appOpsManager = (android.app.AppOpsManager) com.android.server.voiceinteraction.HotwordDetectionConnection.this.mContext.getSystemService(android.app.AppOpsManager.class);
                synchronized (com.android.server.voiceinteraction.HotwordDetectionConnection.this.mLock) {
                    try {
                        if (appOpsManager.unsafeCheckOpNoThrow("android:receive_sandbox_trigger_audio", com.android.server.voiceinteraction.HotwordDetectionConnection.this.mVoiceInteractorIdentity.uid, com.android.server.voiceinteraction.HotwordDetectionConnection.this.mVoiceInteractorIdentity.packageName) == 2) {
                            android.util.Slog.i(com.android.server.voiceinteraction.HotwordDetectionConnection.TAG, "Shutdown hotword detection service on voice activation op disabled.");
                            com.android.server.voiceinteraction.HotwordDetectionConnection.this.safelyShutdownHotwordDetectionOnVoiceActivationDisabledLocked();
                        }
                    } finally {
                    }
                }
            }
        }
    };
    private final int mReStartPeriodSeconds = android.provider.DeviceConfig.getInt("voice_interaction", KEY_RESTART_PERIOD_IN_SECONDS, 0);
    final com.android.server.voiceinteraction.HotwordDetectionConnection.AccessibilitySettingsListener mAccessibilitySettingsListener = new com.android.server.voiceinteraction.HotwordDetectionConnection.AccessibilitySettingsListener();

    /* JADX INFO: Access modifiers changed from: private */
    final class AccessibilitySettingsListener extends com.android.internal.app.IVoiceInteractionAccessibilitySettingsListener.Stub {
        private AccessibilitySettingsListener() {
        }

        public void onAccessibilityDetectionChanged(boolean z) {
            synchronized (com.android.server.voiceinteraction.HotwordDetectionConnection.this.mLock) {
                try {
                    com.android.server.voiceinteraction.VisualQueryDetectorSession visualQueryDetectorSessionLocked = com.android.server.voiceinteraction.HotwordDetectionConnection.this.getVisualQueryDetectorSessionLocked();
                    if (visualQueryDetectorSessionLocked != null) {
                        visualQueryDetectorSessionLocked.updateAccessibilityEgressStateLocked(z);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    HotwordDetectionConnection(java.lang.Object obj, android.content.Context context, int i, android.media.permission.Identity identity, android.content.ComponentName componentName, android.content.ComponentName componentName2, int i2, boolean z, int i3, com.android.server.voiceinteraction.VoiceInteractionManagerServiceImpl.DetectorRemoteExceptionListener detectorRemoteExceptionListener) {
        this.mLock = obj;
        this.mContext = context;
        this.mVoiceInteractionServiceUid = i;
        this.mVoiceInteractorIdentity = identity;
        this.mHotwordDetectionComponentName = componentName;
        this.mVisualQueryDetectionComponentName = componentName2;
        this.mUserId = i2;
        this.mDetectorType = i3;
        this.mRemoteExceptionListener = detectorRemoteExceptionListener;
        android.content.Intent intent = new android.content.Intent("android.service.voice.HotwordDetectionService");
        intent.setComponent(this.mHotwordDetectionComponentName);
        android.content.Intent intent2 = new android.content.Intent("android.service.voice.VisualQueryDetectionService");
        intent2.setComponent(this.mVisualQueryDetectionComponentName);
        initAudioFlinger();
        this.mHotwordDetectionServiceConnectionFactory = new com.android.server.voiceinteraction.HotwordDetectionConnection.ServiceConnectionFactory(intent, z, 1);
        this.mVisualQueryDetectionServiceConnectionFactory = new com.android.server.voiceinteraction.HotwordDetectionConnection.ServiceConnectionFactory(intent2, z, 2);
        this.mLastRestartInstant = java.time.Instant.now();
        ((android.app.AppOpsManager) this.mContext.getSystemService(android.app.AppOpsManager.class)).startWatchingMode(136, this.mVoiceInteractorIdentity.packageName, this.mOnOpChangedListener);
        if (this.mReStartPeriodSeconds <= 0) {
            this.mCancellationTaskFuture = null;
        } else {
            this.mScheduledExecutorService.setRemoveOnCancelPolicy(true);
            this.mCancellationTaskFuture = this.mScheduledExecutorService.scheduleAtFixedRate(new java.lang.Runnable() { // from class: com.android.server.voiceinteraction.HotwordDetectionConnection$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.voiceinteraction.HotwordDetectionConnection.this.lambda$new$0();
                }
            }, this.mReStartPeriodSeconds, this.mReStartPeriodSeconds, java.util.concurrent.TimeUnit.SECONDS);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        android.util.Slog.v(TAG, "Time to restart the process, TTL has passed");
        synchronized (this.mLock) {
            try {
                restartProcessLocked();
                if (this.mDetectorType != 3) {
                    com.android.server.voiceinteraction.HotwordMetricsLogger.writeServiceRestartEvent(this.mDetectorType, 2, this.mVoiceInteractionServiceUid);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void initAudioFlinger() {
        android.os.IBinder waitForService = android.os.ServiceManager.waitForService("media.audio_flinger");
        if (waitForService == null) {
            setAudioFlinger(null);
            throw new java.lang.IllegalStateException("Service media.audio_flinger wasn't found.");
        }
        try {
            waitForService.linkToDeath(this.mAudioServerDeathRecipient, 0);
            setAudioFlinger(waitForService);
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Audio server died before we registered a DeathRecipient; retrying init.", e);
            initAudioFlinger();
        }
    }

    private void setAudioFlinger(@android.annotation.Nullable android.os.IBinder iBinder) {
        synchronized (this.mLock) {
            this.mAudioFlinger = iBinder;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void audioServerDied() {
        android.util.Slog.w(TAG, "Audio server died; restarting the HotwordDetectionService.");
        initAudioFlinger();
        synchronized (this.mLock) {
            try {
                restartProcessLocked();
                if (this.mDetectorType != 3) {
                    com.android.server.voiceinteraction.HotwordMetricsLogger.writeServiceRestartEvent(this.mDetectorType, 1, this.mVoiceInteractionServiceUid);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void cancelLocked() {
        android.util.Slog.v(TAG, "cancelLocked");
        clearDebugHotwordLoggingTimeoutLocked();
        this.mRemoteExceptionListener = null;
        runForEachDetectorSessionLocked(new java.util.function.Consumer() { // from class: com.android.server.voiceinteraction.HotwordDetectionConnection$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.voiceinteraction.DetectorSession) obj).destroyLocked();
            }
        });
        this.mDetectorSessions.clear();
        this.mDebugHotwordLogging = false;
        unbindVisualQueryDetectionService();
        unbindHotwordDetectionService();
        if (this.mCancellationTaskFuture != null) {
            this.mCancellationTaskFuture.cancel(true);
        }
        if (this.mAudioFlinger != null) {
            this.mAudioFlinger.unlinkToDeath(this.mAudioServerDeathRecipient, 0);
            this.mAudioFlinger = null;
        }
        ((android.app.AppOpsManager) this.mContext.getSystemService(android.app.AppOpsManager.class)).stopWatchingMode(this.mOnOpChangedListener);
    }

    private void unbindVisualQueryDetectionService() {
        if (this.mRemoteVisualQueryDetectionService != null) {
            this.mRemoteVisualQueryDetectionService.unbind();
            this.mRemoteVisualQueryDetectionService = null;
        }
        resetDetectionProcessIdentityIfEmptyLocked();
    }

    private void unbindHotwordDetectionService() {
        if (this.mRemoteHotwordDetectionService != null) {
            this.mRemoteHotwordDetectionService.unbind();
            this.mRemoteHotwordDetectionService = null;
        }
        resetDetectionProcessIdentityIfEmptyLocked();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void resetDetectionProcessIdentityIfEmptyLocked() {
        if (this.mRemoteHotwordDetectionService == null && this.mRemoteVisualQueryDetectionService == null) {
            ((com.android.server.pm.permission.PermissionManagerServiceInternal) com.android.server.LocalServices.getService(com.android.server.pm.permission.PermissionManagerServiceInternal.class)).setHotwordDetectionServiceProvider(null);
            if (this.mIdentity != null) {
                removeServiceUidForAudioPolicy(this.mIdentity.getIsolatedUid());
            }
            this.mIdentity = null;
        }
    }

    void updateStateLocked(android.os.PersistableBundle persistableBundle, android.os.SharedMemory sharedMemory, @android.annotation.NonNull android.os.IBinder iBinder) {
        com.android.server.voiceinteraction.DetectorSession detectorSessionByTokenLocked = getDetectorSessionByTokenLocked(iBinder);
        if (detectorSessionByTokenLocked == null) {
            android.util.Slog.v(TAG, "Not found the detector by token");
        } else {
            detectorSessionByTokenLocked.updateStateLocked(persistableBundle, sharedMemory, this.mLastRestartInstant);
        }
    }

    void startListeningFromMicLocked(android.media.AudioFormat audioFormat, android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback iMicrophoneHotwordDetectionVoiceInteractionCallback) {
        com.android.server.voiceinteraction.SoftwareTrustedHotwordDetectorSession softwareTrustedHotwordDetectorSessionLocked = getSoftwareTrustedHotwordDetectorSessionLocked();
        if (softwareTrustedHotwordDetectorSessionLocked == null) {
            return;
        }
        softwareTrustedHotwordDetectorSessionLocked.startListeningFromMicLocked(audioFormat, iMicrophoneHotwordDetectionVoiceInteractionCallback);
    }

    public void setVisualQueryDetectionAttentionListenerLocked(@android.annotation.Nullable com.android.internal.app.IVisualQueryDetectionAttentionListener iVisualQueryDetectionAttentionListener) {
        com.android.server.voiceinteraction.VisualQueryDetectorSession visualQueryDetectorSessionLocked = getVisualQueryDetectorSessionLocked();
        if (visualQueryDetectorSessionLocked == null) {
            return;
        }
        visualQueryDetectorSessionLocked.setVisualQueryDetectionAttentionListenerLocked(iVisualQueryDetectionAttentionListener);
    }

    boolean startPerceivingLocked(android.service.voice.IVisualQueryDetectionVoiceInteractionCallback iVisualQueryDetectionVoiceInteractionCallback) {
        com.android.server.voiceinteraction.VisualQueryDetectorSession visualQueryDetectorSessionLocked = getVisualQueryDetectorSessionLocked();
        if (visualQueryDetectorSessionLocked == null) {
            return false;
        }
        return visualQueryDetectorSessionLocked.startPerceivingLocked(iVisualQueryDetectionVoiceInteractionCallback);
    }

    boolean stopPerceivingLocked() {
        com.android.server.voiceinteraction.VisualQueryDetectorSession visualQueryDetectorSessionLocked = getVisualQueryDetectorSessionLocked();
        if (visualQueryDetectorSessionLocked == null) {
            return false;
        }
        return visualQueryDetectorSessionLocked.stopPerceivingLocked();
    }

    public void startListeningFromExternalSourceLocked(android.os.ParcelFileDescriptor parcelFileDescriptor, android.media.AudioFormat audioFormat, @android.annotation.Nullable android.os.PersistableBundle persistableBundle, @android.annotation.NonNull android.os.IBinder iBinder, android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback iMicrophoneHotwordDetectionVoiceInteractionCallback) {
        com.android.server.voiceinteraction.DetectorSession detectorSessionByTokenLocked = getDetectorSessionByTokenLocked(iBinder);
        if (detectorSessionByTokenLocked == null) {
            android.util.Slog.v(TAG, "Not found the detector by token");
        } else {
            detectorSessionByTokenLocked.startListeningFromExternalSourceLocked(parcelFileDescriptor, audioFormat, persistableBundle, iMicrophoneHotwordDetectionVoiceInteractionCallback);
        }
    }

    public void startListeningFromWearableLocked(android.os.ParcelFileDescriptor parcelFileDescriptor, android.media.AudioFormat audioFormat, android.os.PersistableBundle persistableBundle, android.service.voice.VoiceInteractionManagerInternal.WearableHotwordDetectionCallback wearableHotwordDetectionCallback) {
        com.android.server.voiceinteraction.DspTrustedHotwordDetectorSession dspTrustedHotwordDetectorSessionLocked = getDspTrustedHotwordDetectorSessionLocked();
        if (dspTrustedHotwordDetectorSessionLocked == null) {
            wearableHotwordDetectionCallback.onError("Unable to start listening from wearable because the trusted hotword detection session is not available.");
        } else {
            dspTrustedHotwordDetectorSessionLocked.startListeningFromWearableLocked(parcelFileDescriptor, audioFormat, persistableBundle, wearableHotwordDetectionCallback);
        }
    }

    void stopListeningFromMicLocked() {
        com.android.server.voiceinteraction.SoftwareTrustedHotwordDetectorSession softwareTrustedHotwordDetectorSessionLocked = getSoftwareTrustedHotwordDetectorSessionLocked();
        if (softwareTrustedHotwordDetectorSessionLocked == null) {
            return;
        }
        softwareTrustedHotwordDetectorSessionLocked.stopListeningFromMicLocked();
    }

    void triggerHardwareRecognitionEventForTestLocked(android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent, com.android.internal.app.IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback) {
        detectFromDspSource(keyphraseRecognitionEvent, iHotwordRecognitionStatusCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void detectFromDspSource(android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent, com.android.internal.app.IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback) {
        synchronized (this.mLock) {
            try {
                com.android.server.voiceinteraction.DspTrustedHotwordDetectorSession dspTrustedHotwordDetectorSessionLocked = getDspTrustedHotwordDetectorSessionLocked();
                if (dspTrustedHotwordDetectorSessionLocked == null || !dspTrustedHotwordDetectorSessionLocked.isSameCallback(iHotwordRecognitionStatusCallback)) {
                    android.util.Slog.v(TAG, "Not found the Dsp detector by callback");
                } else {
                    dspTrustedHotwordDetectorSessionLocked.detectFromDspSourceLocked(keyphraseRecognitionEvent, iHotwordRecognitionStatusCallback);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void forceRestart() {
        android.util.Slog.v(TAG, "Requested to restart the service internally. Performing the restart");
        synchronized (this.mLock) {
            restartProcessLocked();
        }
    }

    void setDebugHotwordLoggingLocked(final boolean z) {
        android.util.Slog.v(TAG, "setDebugHotwordLoggingLocked: " + z);
        clearDebugHotwordLoggingTimeoutLocked();
        this.mDebugHotwordLogging = z;
        runForEachDetectorSessionLocked(new java.util.function.Consumer() { // from class: com.android.server.voiceinteraction.HotwordDetectionConnection$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.voiceinteraction.DetectorSession) obj).setDebugHotwordLoggingLocked(z);
            }
        });
        if (z) {
            this.mDebugHotwordLoggingTimeoutFuture = this.mScheduledExecutorService.schedule(new java.lang.Runnable() { // from class: com.android.server.voiceinteraction.HotwordDetectionConnection$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.voiceinteraction.HotwordDetectionConnection.this.lambda$setDebugHotwordLoggingLocked$4();
                }
            }, 3600000L, java.util.concurrent.TimeUnit.MILLISECONDS);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setDebugHotwordLoggingLocked$4() {
        android.util.Slog.v(TAG, "Timeout to reset mDebugHotwordLogging to false");
        synchronized (this.mLock) {
            this.mDebugHotwordLogging = false;
            runForEachDetectorSessionLocked(new java.util.function.Consumer() { // from class: com.android.server.voiceinteraction.HotwordDetectionConnection$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((com.android.server.voiceinteraction.DetectorSession) obj).setDebugHotwordLoggingLocked(false);
                }
            });
        }
    }

    void setDetectorType(int i) {
        this.mDetectorType = i;
    }

    private void clearDebugHotwordLoggingTimeoutLocked() {
        if (this.mDebugHotwordLoggingTimeoutFuture != null) {
            this.mDebugHotwordLoggingTimeoutFuture.cancel(true);
            this.mDebugHotwordLoggingTimeoutFuture = null;
        }
    }

    private void restartProcessLocked() {
        android.util.Slog.v(TAG, "Restarting hotword detection process");
        com.android.server.voiceinteraction.HotwordDetectionConnection.ServiceConnection serviceConnection = this.mRemoteHotwordDetectionService;
        com.android.server.voiceinteraction.HotwordDetectionConnection.ServiceConnection serviceConnection2 = this.mRemoteVisualQueryDetectionService;
        android.service.voice.VoiceInteractionManagerInternal.HotwordDetectionServiceIdentity hotwordDetectionServiceIdentity = this.mIdentity;
        this.mLastRestartInstant = java.time.Instant.now();
        this.mRestartCount++;
        if (serviceConnection != null) {
            this.mRemoteHotwordDetectionService = this.mHotwordDetectionServiceConnectionFactory.createLocked();
        }
        if (serviceConnection2 != null) {
            this.mRemoteVisualQueryDetectionService = this.mVisualQueryDetectionServiceConnectionFactory.createLocked();
        }
        android.util.Slog.v(TAG, "Started the new process, dispatching processRestarted to detector");
        runForEachDetectorSessionLocked(new java.util.function.Consumer() { // from class: com.android.server.voiceinteraction.HotwordDetectionConnection$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.voiceinteraction.HotwordDetectionConnection.this.lambda$restartProcessLocked$5((com.android.server.voiceinteraction.DetectorSession) obj);
            }
        });
        if (serviceConnection != null) {
            serviceConnection.ignoreConnectionStatusEvents();
            serviceConnection.unbind();
        }
        if (serviceConnection2 != null) {
            serviceConnection2.ignoreConnectionStatusEvents();
            serviceConnection2.unbind();
        }
        if (hotwordDetectionServiceIdentity != null) {
            removeServiceUidForAudioPolicy(hotwordDetectionServiceIdentity.getIsolatedUid());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$restartProcessLocked$5(com.android.server.voiceinteraction.DetectorSession detectorSession) {
        detectorSession.updateRemoteSandboxedDetectionServiceLocked(detectorSession instanceof com.android.server.voiceinteraction.VisualQueryDetectorSession ? this.mRemoteVisualQueryDetectionService : this.mRemoteHotwordDetectionService);
        detectorSession.informRestartProcessLocked();
    }

    void safelyShutdownHotwordDetectionOnVoiceActivationDisabledLocked() {
        android.util.Slog.v(TAG, "safelyShutdownHotwordDetectionOnVoiceActivationDisabled");
        try {
            clearDebugHotwordLoggingTimeoutLocked();
            this.mRemoteExceptionListener = null;
            runForEachDetectorSessionLocked(new java.util.function.Consumer() { // from class: com.android.server.voiceinteraction.HotwordDetectionConnection$$ExternalSyntheticLambda15
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.voiceinteraction.HotwordDetectionConnection.lambda$safelyShutdownHotwordDetectionOnVoiceActivationDisabledLocked$6((com.android.server.voiceinteraction.DetectorSession) obj);
                }
            });
            this.mDetectorSessions.delete(1);
            this.mDetectorSessions.delete(2);
            this.mDebugHotwordLogging = false;
            unbindHotwordDetectionService();
            if (this.mCancellationTaskFuture != null) {
                this.mCancellationTaskFuture.cancel(true);
            }
            if (this.mAudioFlinger != null) {
                this.mAudioFlinger.unlinkToDeath(this.mAudioServerDeathRecipient, 0);
                this.mAudioFlinger = null;
            }
        } catch (java.lang.Exception e) {
            android.util.Slog.e(TAG, "Swallowing error while shutting down hotword detection.Error message: " + e.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$safelyShutdownHotwordDetectionOnVoiceActivationDisabledLocked$6(com.android.server.voiceinteraction.DetectorSession detectorSession) {
        if (!(detectorSession instanceof com.android.server.voiceinteraction.VisualQueryDetectorSession)) {
            detectorSession.reportErrorLocked(new android.service.voice.HotwordDetectionServiceFailure(10, "Shutdown hotword detection service on voice activation op disabled!"));
            detectorSession.destroyLocked();
        }
    }

    static final class SoundTriggerCallback extends android.hardware.soundtrigger.IRecognitionStatusCallback.Stub {
        private final android.content.Context mContext;
        private final com.android.internal.app.IHotwordRecognitionStatusCallback mExternalCallback;
        private final com.android.server.voiceinteraction.HotwordDetectionConnection mHotwordDetectionConnection;
        private final android.media.permission.Identity mVoiceInteractorIdentity;

        SoundTriggerCallback(android.content.Context context, com.android.internal.app.IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback, com.android.server.voiceinteraction.HotwordDetectionConnection hotwordDetectionConnection, android.media.permission.Identity identity) {
            this.mContext = context;
            this.mHotwordDetectionConnection = hotwordDetectionConnection;
            this.mExternalCallback = iHotwordRecognitionStatusCallback;
            this.mVoiceInteractorIdentity = identity;
        }

        public void onKeyphraseDetected(android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent) throws android.os.RemoteException {
            if (this.mHotwordDetectionConnection != null) {
                com.android.server.voiceinteraction.HotwordMetricsLogger.writeKeyphraseTriggerEvent(1, 0, this.mVoiceInteractorIdentity.uid);
                this.mHotwordDetectionConnection.detectFromDspSource(keyphraseRecognitionEvent, this.mExternalCallback);
                return;
            }
            int noteOpNoThrow = ((android.app.AppOpsManager) this.mContext.getSystemService(android.app.AppOpsManager.class)).noteOpNoThrow(102, this.mVoiceInteractorIdentity.uid, this.mVoiceInteractorIdentity.packageName, this.mVoiceInteractorIdentity.attributionTag, "Non-HDS keyphrase recognition to VoiceInteractionService");
            if (noteOpNoThrow != 0) {
                android.util.Slog.w(com.android.server.voiceinteraction.HotwordDetectionConnection.TAG, "onKeyphraseDetected suppressed, permission check returned: " + noteOpNoThrow);
                this.mExternalCallback.onRecognitionPaused();
                return;
            }
            com.android.server.voiceinteraction.HotwordMetricsLogger.writeKeyphraseTriggerEvent(0, 0, this.mVoiceInteractorIdentity.uid);
            this.mExternalCallback.onKeyphraseDetected(keyphraseRecognitionEvent, (android.service.voice.HotwordDetectedResult) null);
        }

        public void onGenericSoundTriggerDetected(android.hardware.soundtrigger.SoundTrigger.GenericRecognitionEvent genericRecognitionEvent) throws android.os.RemoteException {
            this.mExternalCallback.onGenericSoundTriggerDetected(genericRecognitionEvent);
        }

        public void onPreempted() throws android.os.RemoteException {
            this.mExternalCallback.onSoundTriggerFailure(new android.service.voice.SoundTriggerFailure(3, "Unexpected startRecognition on already started ST session"));
        }

        public void onModuleDied() throws android.os.RemoteException {
            this.mExternalCallback.onSoundTriggerFailure(new android.service.voice.SoundTriggerFailure(1, "STHAL died"));
        }

        public void onResumeFailed(int i) throws android.os.RemoteException {
            this.mExternalCallback.onSoundTriggerFailure(new android.service.voice.SoundTriggerFailure(2, "STService recognition resume failed with: " + i));
        }

        public void onPauseFailed(int i) throws android.os.RemoteException {
            this.mExternalCallback.onSoundTriggerFailure(new android.service.voice.SoundTriggerFailure(2, "STService recognition pause failed with: " + i));
        }

        public void onRecognitionPaused() throws android.os.RemoteException {
            this.mExternalCallback.onRecognitionPaused();
        }

        public void onRecognitionResumed() throws android.os.RemoteException {
            this.mExternalCallback.onRecognitionResumed();
        }
    }

    public void dump(final java.lang.String str, final java.io.PrintWriter printWriter) {
        synchronized (this.mLock) {
            try {
                printWriter.print(str);
                printWriter.print("mReStartPeriodSeconds=");
                printWriter.println(this.mReStartPeriodSeconds);
                printWriter.print(str);
                printWriter.print("bound for HotwordDetectionService=");
                boolean z = false;
                printWriter.println(this.mRemoteHotwordDetectionService != null && this.mRemoteHotwordDetectionService.isBound());
                printWriter.print(str);
                printWriter.print("bound for VisualQueryDetectionService=");
                if (this.mRemoteVisualQueryDetectionService != null && this.mRemoteHotwordDetectionService != null && this.mRemoteHotwordDetectionService.isBound()) {
                    z = true;
                }
                printWriter.println(z);
                printWriter.print(str);
                printWriter.print("mRestartCount=");
                printWriter.println(this.mRestartCount);
                printWriter.print(str);
                printWriter.print("mLastRestartInstant=");
                printWriter.println(this.mLastRestartInstant);
                printWriter.print(str);
                printWriter.println("DetectorSession(s):");
                printWriter.print(str);
                printWriter.print("Num of DetectorSession(s)=");
                printWriter.println(this.mDetectorSessions.size());
                runForEachDetectorSessionLocked(new java.util.function.Consumer() { // from class: com.android.server.voiceinteraction.HotwordDetectionConnection$$ExternalSyntheticLambda12
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        ((com.android.server.voiceinteraction.DetectorSession) obj).dumpLocked(str, printWriter);
                    }
                });
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private class ServiceConnectionFactory {
        private final int mBindingFlags;
        private final int mDetectionServiceType;
        private final android.content.Intent mIntent;

        ServiceConnectionFactory(@android.annotation.NonNull android.content.Intent intent, boolean z, int i) {
            this.mIntent = intent;
            this.mDetectionServiceType = i;
            int i2 = z ? 4194304 : 0;
            if (com.android.server.voiceinteraction.HotwordDetectionConnection.SYSPROP_VISUAL_QUERY_SERVICE_ENABLED && com.android.server.voiceinteraction.HotwordDetectionConnection.this.mVisualQueryDetectionComponentName != null && com.android.server.voiceinteraction.HotwordDetectionConnection.this.mHotwordDetectionComponentName != null) {
                i2 |= 8192;
            }
            this.mBindingFlags = i2;
        }

        com.android.server.voiceinteraction.HotwordDetectionConnection.ServiceConnection createLocked() {
            com.android.server.voiceinteraction.HotwordDetectionConnection.ServiceConnection serviceConnection = com.android.server.voiceinteraction.HotwordDetectionConnection.this.new ServiceConnection(com.android.server.voiceinteraction.HotwordDetectionConnection.this.mContext, this.mIntent, this.mBindingFlags, com.android.server.voiceinteraction.HotwordDetectionConnection.this.mUserId, new java.util.function.Function() { // from class: com.android.server.voiceinteraction.HotwordDetectionConnection$ServiceConnectionFactory$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    return android.service.voice.ISandboxedDetectionService.Stub.asInterface((android.os.IBinder) obj);
                }
            }, com.android.server.voiceinteraction.HotwordDetectionConnection.this.mRestartCount % 10, this.mDetectionServiceType);
            serviceConnection.connect();
            com.android.server.voiceinteraction.HotwordDetectionConnection.updateAudioFlinger(serviceConnection, com.android.server.voiceinteraction.HotwordDetectionConnection.this.mAudioFlinger);
            com.android.server.voiceinteraction.HotwordDetectionConnection.updateContentCaptureManager(serviceConnection);
            com.android.server.voiceinteraction.HotwordDetectionConnection.updateSpeechService(serviceConnection);
            com.android.server.voiceinteraction.HotwordDetectionConnection.this.updateServiceIdentity(serviceConnection);
            com.android.server.voiceinteraction.HotwordDetectionConnection.this.updateStorageService(serviceConnection);
            return serviceConnection;
        }
    }

    class ServiceConnection extends com.android.internal.infra.ServiceConnector.Impl<android.service.voice.ISandboxedDetectionService> {
        private final int mBindingFlags;
        private final int mDetectionServiceType;
        private final int mInstanceNumber;
        private final android.content.Intent mIntent;
        private boolean mIsBound;
        private boolean mIsLoggedFirstConnect;
        private final java.lang.Object mLock;
        private boolean mRespectServiceConnectionStatusChanged;

        ServiceConnection(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.content.Intent intent, int i, int i2, @android.annotation.Nullable java.util.function.Function<android.os.IBinder, android.service.voice.ISandboxedDetectionService> function, int i3, int i4) {
            super(context, intent, i, i2, function);
            this.mLock = new java.lang.Object();
            this.mRespectServiceConnectionStatusChanged = true;
            this.mIsBound = false;
            this.mIsLoggedFirstConnect = false;
            this.mIntent = intent;
            this.mBindingFlags = i;
            this.mInstanceNumber = i3;
            this.mDetectionServiceType = i4;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public void onServiceConnectionStatusChanged(android.service.voice.ISandboxedDetectionService iSandboxedDetectionService, boolean z) {
            synchronized (this.mLock) {
                try {
                    if (!this.mRespectServiceConnectionStatusChanged) {
                        android.util.Slog.v(com.android.server.voiceinteraction.HotwordDetectionConnection.TAG, "Ignored onServiceConnectionStatusChanged event");
                        return;
                    }
                    this.mIsBound = z;
                    if (!z) {
                        if (this.mDetectionServiceType != 2) {
                            com.android.server.voiceinteraction.HotwordMetricsLogger.writeDetectorEvent(com.android.server.voiceinteraction.HotwordDetectionConnection.this.mDetectorType, 7, com.android.server.voiceinteraction.HotwordDetectionConnection.this.mVoiceInteractionServiceUid);
                        }
                    } else if (!this.mIsLoggedFirstConnect) {
                        this.mIsLoggedFirstConnect = true;
                        if (this.mDetectionServiceType != 2) {
                            com.android.server.voiceinteraction.HotwordMetricsLogger.writeDetectorEvent(com.android.server.voiceinteraction.HotwordDetectionConnection.this.mDetectorType, 2, com.android.server.voiceinteraction.HotwordDetectionConnection.this.mVoiceInteractionServiceUid);
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        protected long getAutoDisconnectTimeoutMs() {
            return -1L;
        }

        public void binderDied() {
            super.binderDied();
            android.util.Slog.w(com.android.server.voiceinteraction.HotwordDetectionConnection.TAG, "binderDied mDetectionServiceType = " + this.mDetectionServiceType);
            synchronized (this.mLock) {
                try {
                    if (!this.mRespectServiceConnectionStatusChanged) {
                        android.util.Slog.v(com.android.server.voiceinteraction.HotwordDetectionConnection.TAG, "Ignored #binderDied event");
                        return;
                    }
                    synchronized (com.android.server.voiceinteraction.HotwordDetectionConnection.this.mLock) {
                        com.android.server.voiceinteraction.HotwordDetectionConnection.this.runForEachDetectorSessionLocked(new java.util.function.Consumer() { // from class: com.android.server.voiceinteraction.HotwordDetectionConnection$ServiceConnection$$ExternalSyntheticLambda1
                            @Override // java.util.function.Consumer
                            public final void accept(java.lang.Object obj) {
                                com.android.server.voiceinteraction.HotwordDetectionConnection.ServiceConnection.this.reportBinderDiedLocked((com.android.server.voiceinteraction.DetectorSession) obj);
                            }
                        });
                    }
                    if (this.mDetectionServiceType != 2) {
                        com.android.server.voiceinteraction.HotwordMetricsLogger.writeKeyphraseTriggerEvent(com.android.server.voiceinteraction.HotwordDetectionConnection.this.mDetectorType, 4, com.android.server.voiceinteraction.HotwordDetectionConnection.this.mVoiceInteractionServiceUid);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        protected boolean bindService(@android.annotation.NonNull android.content.ServiceConnection serviceConnection) {
            try {
                if (this.mDetectionServiceType != 2) {
                    com.android.server.voiceinteraction.HotwordMetricsLogger.writeDetectorEvent(com.android.server.voiceinteraction.HotwordDetectionConnection.this.mDetectorType, 1, com.android.server.voiceinteraction.HotwordDetectionConnection.this.mVoiceInteractionServiceUid);
                }
                boolean bindIsolatedService = ((com.android.internal.infra.ServiceConnector.Impl) this).mContext.bindIsolatedService(this.mIntent, this.mBindingFlags | android.hardware.audio.common.V2_0.AudioFormat.AAC_MAIN, "hotword_detector_" + this.mInstanceNumber, ((com.android.internal.infra.ServiceConnector.Impl) this).mExecutor, serviceConnection);
                if (!bindIsolatedService) {
                    android.util.Slog.w(com.android.server.voiceinteraction.HotwordDetectionConnection.TAG, "bindService failure mDetectionServiceType = " + this.mDetectionServiceType);
                    synchronized (com.android.server.voiceinteraction.HotwordDetectionConnection.this.mLock) {
                        com.android.server.voiceinteraction.HotwordDetectionConnection.this.runForEachDetectorSessionLocked(new java.util.function.Consumer() { // from class: com.android.server.voiceinteraction.HotwordDetectionConnection$ServiceConnection$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(java.lang.Object obj) {
                                com.android.server.voiceinteraction.HotwordDetectionConnection.ServiceConnection.this.reportBindServiceFailureLocked((com.android.server.voiceinteraction.DetectorSession) obj);
                            }
                        });
                    }
                    if (this.mDetectionServiceType != 2) {
                        com.android.server.voiceinteraction.HotwordMetricsLogger.writeDetectorEvent(com.android.server.voiceinteraction.HotwordDetectionConnection.this.mDetectorType, 3, com.android.server.voiceinteraction.HotwordDetectionConnection.this.mVoiceInteractionServiceUid);
                    }
                }
                return bindIsolatedService;
            } catch (java.lang.IllegalArgumentException e) {
                if (this.mDetectionServiceType != 2) {
                    com.android.server.voiceinteraction.HotwordMetricsLogger.writeDetectorEvent(com.android.server.voiceinteraction.HotwordDetectionConnection.this.mDetectorType, 3, com.android.server.voiceinteraction.HotwordDetectionConnection.this.mVoiceInteractionServiceUid);
                }
                android.util.Slog.wtf(com.android.server.voiceinteraction.HotwordDetectionConnection.TAG, "Can't bind to the hotword detection service!", e);
                return false;
            }
        }

        boolean isBound() {
            boolean z;
            synchronized (this.mLock) {
                z = this.mIsBound;
            }
            return z;
        }

        void ignoreConnectionStatusEvents() {
            synchronized (this.mLock) {
                this.mRespectServiceConnectionStatusChanged = false;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void reportBinderDiedLocked(com.android.server.voiceinteraction.DetectorSession detectorSession) {
            if (this.mDetectionServiceType == 1 && ((detectorSession instanceof com.android.server.voiceinteraction.DspTrustedHotwordDetectorSession) || (detectorSession instanceof com.android.server.voiceinteraction.SoftwareTrustedHotwordDetectorSession))) {
                detectorSession.reportErrorLocked(new android.service.voice.HotwordDetectionServiceFailure(2, "Detection service is dead."));
            } else if (this.mDetectionServiceType == 2 && (detectorSession instanceof com.android.server.voiceinteraction.VisualQueryDetectorSession)) {
                detectorSession.reportErrorLocked(new android.service.voice.VisualQueryDetectionServiceFailure(2, "Detection service is dead."));
            } else {
                detectorSession.reportErrorLocked("Detection service is dead with unknown detection service type.");
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void reportBindServiceFailureLocked(com.android.server.voiceinteraction.DetectorSession detectorSession) {
            if (this.mDetectionServiceType == 1 && ((detectorSession instanceof com.android.server.voiceinteraction.DspTrustedHotwordDetectorSession) || (detectorSession instanceof com.android.server.voiceinteraction.SoftwareTrustedHotwordDetectorSession))) {
                detectorSession.reportErrorLocked(new android.service.voice.HotwordDetectionServiceFailure(1, "Bind detection service failure."));
            } else if (this.mDetectionServiceType == 2 && (detectorSession instanceof com.android.server.voiceinteraction.VisualQueryDetectorSession)) {
                detectorSession.reportErrorLocked(new android.service.voice.VisualQueryDetectionServiceFailure(1, "Bind detection service failure."));
            } else {
                detectorSession.reportErrorLocked("Bind detection service failure with unknown detection service type.");
            }
        }
    }

    void createDetectorLocked(@android.annotation.Nullable android.os.PersistableBundle persistableBundle, @android.annotation.Nullable android.os.SharedMemory sharedMemory, @android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull com.android.internal.app.IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback, int i) {
        com.android.server.voiceinteraction.DetectorSession softwareTrustedHotwordDetectorSession;
        com.android.server.voiceinteraction.DetectorSession detectorSession = this.mDetectorSessions.get(i);
        if (detectorSession != null) {
            detectorSession.destroyLocked();
            this.mDetectorSessions.remove(i);
        }
        if (i == 1) {
            if (this.mRemoteHotwordDetectionService == null) {
                this.mRemoteHotwordDetectionService = this.mHotwordDetectionServiceConnectionFactory.createLocked();
            }
            softwareTrustedHotwordDetectorSession = new com.android.server.voiceinteraction.DspTrustedHotwordDetectorSession(this.mRemoteHotwordDetectionService, this.mLock, this.mContext, iBinder, iHotwordRecognitionStatusCallback, this.mVoiceInteractionServiceUid, this.mVoiceInteractorIdentity, this.mScheduledExecutorService, this.mDebugHotwordLogging, this.mRemoteExceptionListener, this.mUserId);
        } else if (i == 3) {
            if (this.mRemoteVisualQueryDetectionService == null) {
                this.mRemoteVisualQueryDetectionService = this.mVisualQueryDetectionServiceConnectionFactory.createLocked();
            }
            softwareTrustedHotwordDetectorSession = new com.android.server.voiceinteraction.VisualQueryDetectorSession(this.mRemoteVisualQueryDetectionService, this.mLock, this.mContext, iBinder, iHotwordRecognitionStatusCallback, this.mVoiceInteractionServiceUid, this.mVoiceInteractorIdentity, this.mScheduledExecutorService, this.mDebugHotwordLogging, this.mRemoteExceptionListener, this.mUserId);
        } else {
            if (this.mRemoteHotwordDetectionService == null) {
                this.mRemoteHotwordDetectionService = this.mHotwordDetectionServiceConnectionFactory.createLocked();
            }
            softwareTrustedHotwordDetectorSession = new com.android.server.voiceinteraction.SoftwareTrustedHotwordDetectorSession(this.mRemoteHotwordDetectionService, this.mLock, this.mContext, iBinder, iHotwordRecognitionStatusCallback, this.mVoiceInteractionServiceUid, this.mVoiceInteractorIdentity, this.mScheduledExecutorService, this.mDebugHotwordLogging, this.mRemoteExceptionListener, this.mUserId);
        }
        this.mHotwordRecognitionCallback = iHotwordRecognitionStatusCallback;
        this.mDetectorSessions.put(i, softwareTrustedHotwordDetectorSession);
        softwareTrustedHotwordDetectorSession.initialize(persistableBundle, sharedMemory);
    }

    void destroyDetectorLocked(@android.annotation.NonNull android.os.IBinder iBinder) {
        com.android.server.voiceinteraction.DetectorSession detectorSessionByTokenLocked = getDetectorSessionByTokenLocked(iBinder);
        if (detectorSessionByTokenLocked == null) {
            return;
        }
        detectorSessionByTokenLocked.destroyLocked();
        int indexOfValue = this.mDetectorSessions.indexOfValue(detectorSessionByTokenLocked);
        if (indexOfValue >= 0) {
            boolean z = true;
            if (indexOfValue > this.mDetectorSessions.size() - 1) {
                return;
            }
            this.mDetectorSessions.removeAt(indexOfValue);
            if (detectorSessionByTokenLocked instanceof com.android.server.voiceinteraction.VisualQueryDetectorSession) {
                unbindVisualQueryDetectionService();
            }
            if (this.mDetectorSessions.size() != 0 && (this.mDetectorSessions.size() != 1 || !(this.mDetectorSessions.get(0) instanceof com.android.server.voiceinteraction.VisualQueryDetectorSession))) {
                z = false;
            }
            if (z) {
                unbindHotwordDetectionService();
            }
        }
    }

    private com.android.server.voiceinteraction.DetectorSession getDetectorSessionByTokenLocked(android.os.IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        for (int i = 0; i < this.mDetectorSessions.size(); i++) {
            com.android.server.voiceinteraction.DetectorSession valueAt = this.mDetectorSessions.valueAt(i);
            if (!valueAt.isDestroyed() && valueAt.isSameToken(iBinder)) {
                return valueAt;
            }
        }
        return null;
    }

    private com.android.server.voiceinteraction.DspTrustedHotwordDetectorSession getDspTrustedHotwordDetectorSessionLocked() {
        com.android.server.voiceinteraction.DetectorSession detectorSession = this.mDetectorSessions.get(1);
        if (detectorSession == null || detectorSession.isDestroyed()) {
            android.util.Slog.v(TAG, "Not found the Dsp detector");
            return null;
        }
        return (com.android.server.voiceinteraction.DspTrustedHotwordDetectorSession) detectorSession;
    }

    private com.android.server.voiceinteraction.SoftwareTrustedHotwordDetectorSession getSoftwareTrustedHotwordDetectorSessionLocked() {
        com.android.server.voiceinteraction.DetectorSession detectorSession = this.mDetectorSessions.get(2);
        if (detectorSession == null || detectorSession.isDestroyed()) {
            android.util.Slog.v(TAG, "Not found the software detector");
            return null;
        }
        return (com.android.server.voiceinteraction.SoftwareTrustedHotwordDetectorSession) detectorSession;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.server.voiceinteraction.VisualQueryDetectorSession getVisualQueryDetectorSessionLocked() {
        com.android.server.voiceinteraction.DetectorSession detectorSession = this.mDetectorSessions.get(3);
        if (detectorSession == null || detectorSession.isDestroyed()) {
            android.util.Slog.v(TAG, "Not found the visual query detector");
            return null;
        }
        return (com.android.server.voiceinteraction.VisualQueryDetectorSession) detectorSession;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void runForEachDetectorSessionLocked(@android.annotation.NonNull java.util.function.Consumer<com.android.server.voiceinteraction.DetectorSession> consumer) {
        for (int i = 0; i < this.mDetectorSessions.size(); i++) {
            consumer.accept(this.mDetectorSessions.valueAt(i));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void updateAudioFlinger(com.android.server.voiceinteraction.HotwordDetectionConnection.ServiceConnection serviceConnection, final android.os.IBinder iBinder) {
        serviceConnection.run(new com.android.internal.infra.ServiceConnector.VoidJob() { // from class: com.android.server.voiceinteraction.HotwordDetectionConnection$$ExternalSyntheticLambda9
            public final void runNoResult(java.lang.Object obj) {
                ((android.service.voice.ISandboxedDetectionService) obj).updateAudioFlinger(iBinder);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void updateContentCaptureManager(com.android.server.voiceinteraction.HotwordDetectionConnection.ServiceConnection serviceConnection) {
        final android.view.contentcapture.IContentCaptureManager asInterface = android.view.contentcapture.IContentCaptureManager.Stub.asInterface(android.os.ServiceManager.getService("content_capture"));
        serviceConnection.run(new com.android.internal.infra.ServiceConnector.VoidJob() { // from class: com.android.server.voiceinteraction.HotwordDetectionConnection$$ExternalSyntheticLambda2
            public final void runNoResult(java.lang.Object obj) {
                com.android.server.voiceinteraction.HotwordDetectionConnection.lambda$updateContentCaptureManager$9(asInterface, (android.service.voice.ISandboxedDetectionService) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$updateContentCaptureManager$9(android.view.contentcapture.IContentCaptureManager iContentCaptureManager, android.service.voice.ISandboxedDetectionService iSandboxedDetectionService) throws java.lang.Exception {
        iSandboxedDetectionService.updateContentCaptureManager(iContentCaptureManager, new android.content.ContentCaptureOptions((android.util.ArraySet) null));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void updateSpeechService(com.android.server.voiceinteraction.HotwordDetectionConnection.ServiceConnection serviceConnection) {
        final android.speech.IRecognitionServiceManager asInterface = android.speech.IRecognitionServiceManager.Stub.asInterface(android.os.ServiceManager.getService("speech_recognition"));
        serviceConnection.run(new com.android.internal.infra.ServiceConnector.VoidJob() { // from class: com.android.server.voiceinteraction.HotwordDetectionConnection$$ExternalSyntheticLambda13
            public final void runNoResult(java.lang.Object obj) {
                ((android.service.voice.ISandboxedDetectionService) obj).updateRecognitionServiceManager(asInterface);
            }
        });
    }

    /* renamed from: com.android.server.voiceinteraction.HotwordDetectionConnection$2, reason: invalid class name */
    class AnonymousClass2 extends android.os.IRemoteCallback.Stub {
        AnonymousClass2() {
        }

        public void sendResult(android.os.Bundle bundle) throws android.os.RemoteException {
            final int callingUid = android.os.Binder.getCallingUid();
            ((com.android.server.pm.permission.PermissionManagerServiceInternal) com.android.server.LocalServices.getService(com.android.server.pm.permission.PermissionManagerServiceInternal.class)).setHotwordDetectionServiceProvider(new com.android.server.pm.permission.PermissionManagerServiceInternal.HotwordDetectionServiceProvider() { // from class: com.android.server.voiceinteraction.HotwordDetectionConnection$2$$ExternalSyntheticLambda0
                @Override // com.android.server.pm.permission.PermissionManagerServiceInternal.HotwordDetectionServiceProvider
                public final int getUid() {
                    int lambda$sendResult$0;
                    lambda$sendResult$0 = com.android.server.voiceinteraction.HotwordDetectionConnection.AnonymousClass2.lambda$sendResult$0(callingUid);
                    return lambda$sendResult$0;
                }
            });
            com.android.server.voiceinteraction.HotwordDetectionConnection.this.mIdentity = new android.service.voice.VoiceInteractionManagerInternal.HotwordDetectionServiceIdentity(callingUid, com.android.server.voiceinteraction.HotwordDetectionConnection.this.mVoiceInteractionServiceUid);
            com.android.server.voiceinteraction.HotwordDetectionConnection.this.addServiceUidForAudioPolicy(callingUid);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ int lambda$sendResult$0(int i) {
            return i;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateServiceIdentity$11(android.service.voice.ISandboxedDetectionService iSandboxedDetectionService) throws java.lang.Exception {
        iSandboxedDetectionService.ping(new com.android.server.voiceinteraction.HotwordDetectionConnection.AnonymousClass2());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateServiceIdentity(com.android.server.voiceinteraction.HotwordDetectionConnection.ServiceConnection serviceConnection) {
        serviceConnection.run(new com.android.internal.infra.ServiceConnector.VoidJob() { // from class: com.android.server.voiceinteraction.HotwordDetectionConnection$$ExternalSyntheticLambda8
            public final void runNoResult(java.lang.Object obj) {
                com.android.server.voiceinteraction.HotwordDetectionConnection.this.lambda$updateServiceIdentity$11((android.service.voice.ISandboxedDetectionService) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateStorageService(com.android.server.voiceinteraction.HotwordDetectionConnection.ServiceConnection serviceConnection) {
        serviceConnection.run(new com.android.internal.infra.ServiceConnector.VoidJob() { // from class: com.android.server.voiceinteraction.HotwordDetectionConnection$$ExternalSyntheticLambda7
            public final void runNoResult(java.lang.Object obj) {
                com.android.server.voiceinteraction.HotwordDetectionConnection.this.lambda$updateStorageService$12((android.service.voice.ISandboxedDetectionService) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateStorageService$12(android.service.voice.ISandboxedDetectionService iSandboxedDetectionService) throws java.lang.Exception {
        iSandboxedDetectionService.registerRemoteStorageService(new android.service.voice.IDetectorSessionStorageService.Stub() { // from class: com.android.server.voiceinteraction.HotwordDetectionConnection.3
            public void openFile(java.lang.String str, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
                android.util.Slog.v(com.android.server.voiceinteraction.HotwordDetectionConnection.TAG, "BinderCallback#onFileOpen");
                try {
                    com.android.server.voiceinteraction.HotwordDetectionConnection.this.mHotwordRecognitionCallback.onOpenFile(str, androidFuture);
                } catch (android.os.RemoteException e) {
                    e.rethrowFromSystemServer();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addServiceUidForAudioPolicy(final int i) {
        this.mScheduledExecutorService.execute(new java.lang.Runnable() { // from class: com.android.server.voiceinteraction.HotwordDetectionConnection$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.voiceinteraction.HotwordDetectionConnection.lambda$addServiceUidForAudioPolicy$13(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$addServiceUidForAudioPolicy$13(int i) {
        android.media.AudioManagerInternal audioManagerInternal = (android.media.AudioManagerInternal) com.android.server.LocalServices.getService(android.media.AudioManagerInternal.class);
        if (audioManagerInternal != null) {
            audioManagerInternal.addAssistantServiceUid(i);
        }
    }

    private void removeServiceUidForAudioPolicy(final int i) {
        this.mScheduledExecutorService.execute(new java.lang.Runnable() { // from class: com.android.server.voiceinteraction.HotwordDetectionConnection$$ExternalSyntheticLambda14
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.voiceinteraction.HotwordDetectionConnection.lambda$removeServiceUidForAudioPolicy$14(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$removeServiceUidForAudioPolicy$14(int i) {
        android.media.AudioManagerInternal audioManagerInternal = (android.media.AudioManagerInternal) com.android.server.LocalServices.getService(android.media.AudioManagerInternal.class);
        if (audioManagerInternal != null) {
            audioManagerInternal.removeAssistantServiceUid(i);
        }
    }
}
