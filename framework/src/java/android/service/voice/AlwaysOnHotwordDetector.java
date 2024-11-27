package android.service.voice;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public class AlwaysOnHotwordDetector extends android.service.voice.AbstractDetector {
    public static final int AUDIO_CAPABILITY_ECHO_CANCELLATION = 1;
    public static final int AUDIO_CAPABILITY_NOISE_SUPPRESSION = 2;
    static final boolean DBG = false;
    public static final int MODEL_PARAM_THRESHOLD_FACTOR = 0;
    private static final int MSG_AVAILABILITY_CHANGED = 1;
    private static final int MSG_DETECTION_ERROR = 3;
    private static final int MSG_DETECTION_HOTWORD_DETECTION_SERVICE_FAILURE = 9;
    private static final int MSG_DETECTION_PAUSE = 4;
    private static final int MSG_DETECTION_RESUME = 5;
    private static final int MSG_DETECTION_SOUND_TRIGGER_FAILURE = 10;
    private static final int MSG_DETECTION_UNKNOWN_FAILURE = 11;
    private static final int MSG_HOTWORD_DETECTED = 2;
    private static final int MSG_HOTWORD_REJECTED = 6;
    private static final int MSG_HOTWORD_STATUS_REPORTED = 7;
    private static final int MSG_PROCESS_RESTARTED = 8;
    public static final int RECOGNITION_FLAG_ALLOW_MULTIPLE_TRIGGERS = 2;
    public static final int RECOGNITION_FLAG_CAPTURE_TRIGGER_AUDIO = 1;
    public static final int RECOGNITION_FLAG_ENABLE_AUDIO_ECHO_CANCELLATION = 4;
    public static final int RECOGNITION_FLAG_ENABLE_AUDIO_NOISE_SUPPRESSION = 8;
    public static final int RECOGNITION_FLAG_NONE = 0;
    public static final int RECOGNITION_FLAG_RUN_IN_BATTERY_SAVER = 16;
    public static final int RECOGNITION_MODE_USER_IDENTIFICATION = 2;
    public static final int RECOGNITION_MODE_VOICE_TRIGGER = 1;
    static final long SEND_ON_FAILURE_FOR_ASYNC_EXCEPTIONS = 280471513;
    public static final int STATE_ERROR = 3;
    public static final int STATE_HARDWARE_UNAVAILABLE = -2;
    private static final int STATE_INVALID = -3;
    public static final int STATE_KEYPHRASE_ENROLLED = 2;
    public static final int STATE_KEYPHRASE_UNENROLLED = 1;

    @java.lang.Deprecated
    public static final int STATE_KEYPHRASE_UNSUPPORTED = -1;
    private static final int STATE_NOT_READY = 0;
    private static final int STATUS_ERROR = Integer.MIN_VALUE;
    private static final int STATUS_OK = 0;
    static final java.lang.String TAG = "AlwaysOnHotwordDetector";
    static final long THROW_ON_INITIALIZE_IF_NO_DSP = 269165460;
    private final java.lang.String mAttributionTag;
    private int mAvailability;
    private final android.os.IBinder mBinder;
    private final android.service.voice.AlwaysOnHotwordDetector.Callback mExternalCallback;
    private final java.util.concurrent.Executor mExternalExecutor;
    private final android.os.Handler mHandler;
    private final android.service.voice.AlwaysOnHotwordDetector.SoundTriggerListener mInternalCallback;
    private boolean mIsAvailabilityOverriddenByTestApi;
    private final android.hardware.soundtrigger.KeyphraseEnrollmentInfo mKeyphraseEnrollmentInfo;
    private android.hardware.soundtrigger.KeyphraseMetadata mKeyphraseMetadata;
    private final java.util.Locale mLocale;
    private final com.android.internal.app.IVoiceInteractionManagerService mModelManagementService;
    private com.android.internal.app.IVoiceInteractionSoundTriggerSession mSoundTriggerSession;
    private final boolean mSupportSandboxedDetectionService;
    private final java.lang.String mText;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AudioCapabilities {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ModelParams {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RecognitionFlags {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RecognitionModes {
    }

    @Override // android.service.voice.AbstractDetector, android.service.voice.HotwordDetector
    public /* bridge */ /* synthetic */ boolean startRecognition(android.os.ParcelFileDescriptor parcelFileDescriptor, android.media.AudioFormat audioFormat, android.os.PersistableBundle persistableBundle) {
        return super.startRecognition(parcelFileDescriptor, audioFormat, persistableBundle);
    }

    public static final class ModelParamRange {
        private final android.hardware.soundtrigger.SoundTrigger.ModelParamRange mModelParamRange;

        ModelParamRange(android.hardware.soundtrigger.SoundTrigger.ModelParamRange modelParamRange) {
            this.mModelParamRange = modelParamRange;
        }

        public int getStart() {
            return this.mModelParamRange.getStart();
        }

        public int getEnd() {
            return this.mModelParamRange.getEnd();
        }

        public java.lang.String toString() {
            return this.mModelParamRange.toString();
        }

        public boolean equals(java.lang.Object obj) {
            return this.mModelParamRange.equals(obj);
        }

        public int hashCode() {
            return this.mModelParamRange.hashCode();
        }
    }

    public static class EventPayload {
        public static final int DATA_FORMAT_RAW = 0;
        public static final int DATA_FORMAT_TRIGGER_AUDIO = 1;
        private final android.media.AudioFormat mAudioFormat;
        private final android.os.ParcelFileDescriptor mAudioStream;
        private final boolean mCaptureAvailable;
        private final int mCaptureSession;
        private final byte[] mData;
        private final int mDataFormat;
        private final long mHalEventReceivedMillis;
        private final android.service.voice.HotwordDetectedResult mHotwordDetectedResult;
        private final boolean mIsRecognitionStopped;
        private final java.util.List<android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionExtra> mKephraseExtras;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface DataFormat {
        }

        private EventPayload(boolean z, android.media.AudioFormat audioFormat, int i, int i2, byte[] bArr, android.service.voice.HotwordDetectedResult hotwordDetectedResult, android.os.ParcelFileDescriptor parcelFileDescriptor, java.util.List<android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionExtra> list, long j, boolean z2) {
            this.mCaptureAvailable = z;
            this.mCaptureSession = i;
            this.mAudioFormat = audioFormat;
            this.mDataFormat = i2;
            this.mData = bArr;
            this.mHotwordDetectedResult = hotwordDetectedResult;
            this.mAudioStream = parcelFileDescriptor;
            this.mKephraseExtras = list;
            this.mHalEventReceivedMillis = j;
            this.mIsRecognitionStopped = z2;
        }

        public android.media.AudioFormat getCaptureAudioFormat() {
            return this.mAudioFormat;
        }

        @java.lang.Deprecated
        public byte[] getTriggerAudio() {
            if (this.mDataFormat == 1) {
                return this.mData;
            }
            return null;
        }

        public int getDataFormat() {
            return this.mDataFormat;
        }

        public byte[] getData() {
            return this.mData;
        }

        public java.lang.Integer getCaptureSession() {
            if (this.mCaptureAvailable) {
                return java.lang.Integer.valueOf(this.mCaptureSession);
            }
            return null;
        }

        public android.service.voice.HotwordDetectedResult getHotwordDetectedResult() {
            return this.mHotwordDetectedResult;
        }

        public android.os.ParcelFileDescriptor getAudioStream() {
            return this.mAudioStream;
        }

        public java.util.List<android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionExtra> getKeyphraseRecognitionExtras() {
            return this.mKephraseExtras;
        }

        public long getHalEventReceivedMillis() {
            return this.mHalEventReceivedMillis;
        }

        public boolean isRecognitionStopped() {
            return this.mIsRecognitionStopped;
        }

        public static final class Builder {
            private boolean mCaptureAvailable = false;
            private int mCaptureSession = -1;
            private android.media.AudioFormat mAudioFormat = null;
            private int mDataFormat = 0;
            private byte[] mData = null;
            private android.service.voice.HotwordDetectedResult mHotwordDetectedResult = null;
            private android.os.ParcelFileDescriptor mAudioStream = null;
            private java.util.List<android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionExtra> mKeyphraseExtras = java.util.Collections.emptyList();
            private long mHalEventReceivedMillis = -1;
            private boolean mIsRecognitionStopped = true;

            public Builder() {
            }

            Builder(android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent) {
                setCaptureAvailable(keyphraseRecognitionEvent.isCaptureAvailable());
                setCaptureSession(keyphraseRecognitionEvent.getCaptureSession());
                if (keyphraseRecognitionEvent.getCaptureFormat() != null) {
                    setCaptureAudioFormat(keyphraseRecognitionEvent.getCaptureFormat());
                }
                setDataFormat(keyphraseRecognitionEvent.triggerInData ? 1 : 0);
                if (keyphraseRecognitionEvent.getData() != null) {
                    setData(keyphraseRecognitionEvent.getData());
                }
                if (keyphraseRecognitionEvent.keyphraseExtras != null) {
                    setKeyphraseRecognitionExtras(java.util.Arrays.asList(keyphraseRecognitionEvent.keyphraseExtras));
                }
                setHalEventReceivedMillis(keyphraseRecognitionEvent.getHalEventReceivedMillis());
            }

            public android.service.voice.AlwaysOnHotwordDetector.EventPayload.Builder setCaptureAvailable(boolean z) {
                this.mCaptureAvailable = z;
                return this;
            }

            public android.service.voice.AlwaysOnHotwordDetector.EventPayload.Builder setCaptureSession(int i) {
                this.mCaptureSession = i;
                return this;
            }

            public android.service.voice.AlwaysOnHotwordDetector.EventPayload.Builder setCaptureAudioFormat(android.media.AudioFormat audioFormat) {
                this.mAudioFormat = audioFormat;
                return this;
            }

            public android.service.voice.AlwaysOnHotwordDetector.EventPayload.Builder setDataFormat(int i) {
                this.mDataFormat = i;
                return this;
            }

            public android.service.voice.AlwaysOnHotwordDetector.EventPayload.Builder setData(byte[] bArr) {
                this.mData = bArr;
                return this;
            }

            public android.service.voice.AlwaysOnHotwordDetector.EventPayload.Builder setHotwordDetectedResult(android.service.voice.HotwordDetectedResult hotwordDetectedResult) {
                this.mHotwordDetectedResult = hotwordDetectedResult;
                return this;
            }

            public android.service.voice.AlwaysOnHotwordDetector.EventPayload.Builder setAudioStream(android.os.ParcelFileDescriptor parcelFileDescriptor) {
                this.mAudioStream = parcelFileDescriptor;
                return this;
            }

            public android.service.voice.AlwaysOnHotwordDetector.EventPayload.Builder setKeyphraseRecognitionExtras(java.util.List<android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionExtra> list) {
                this.mKeyphraseExtras = list;
                return this;
            }

            public android.service.voice.AlwaysOnHotwordDetector.EventPayload.Builder setHalEventReceivedMillis(long j) {
                this.mHalEventReceivedMillis = j;
                return this;
            }

            public android.service.voice.AlwaysOnHotwordDetector.EventPayload.Builder setIsRecognitionStopped(boolean z) {
                this.mIsRecognitionStopped = z;
                return this;
            }

            public android.service.voice.AlwaysOnHotwordDetector.EventPayload build() {
                return new android.service.voice.AlwaysOnHotwordDetector.EventPayload(this.mCaptureAvailable, this.mAudioFormat, this.mCaptureSession, this.mDataFormat, this.mData, this.mHotwordDetectedResult, this.mAudioStream, this.mKeyphraseExtras, this.mHalEventReceivedMillis, this.mIsRecognitionStopped);
            }
        }
    }

    public static abstract class Callback implements android.service.voice.HotwordDetector.Callback {
        public abstract void onAvailabilityChanged(int i);

        @Override // android.service.voice.HotwordDetector.Callback
        public abstract void onDetected(android.service.voice.AlwaysOnHotwordDetector.EventPayload eventPayload);

        @Override // android.service.voice.HotwordDetector.Callback
        @java.lang.Deprecated
        public abstract void onError();

        @Override // android.service.voice.HotwordDetector.Callback
        public abstract void onRecognitionPaused();

        @Override // android.service.voice.HotwordDetector.Callback
        public abstract void onRecognitionResumed();

        public void onFailure(android.service.voice.SoundTriggerFailure soundTriggerFailure) {
            onError();
        }

        @Override // android.service.voice.HotwordDetector.Callback
        public void onRejected(android.service.voice.HotwordRejectedResult hotwordRejectedResult) {
        }

        @Override // android.service.voice.HotwordDetector.Callback
        public void onHotwordDetectionServiceInitialized(int i) {
        }

        @Override // android.service.voice.HotwordDetector.Callback
        public void onHotwordDetectionServiceRestarted() {
        }
    }

    public AlwaysOnHotwordDetector(java.lang.String str, java.util.Locale locale, java.util.concurrent.Executor executor, android.service.voice.AlwaysOnHotwordDetector.Callback callback, android.hardware.soundtrigger.KeyphraseEnrollmentInfo keyphraseEnrollmentInfo, com.android.internal.app.IVoiceInteractionManagerService iVoiceInteractionManagerService, int i, boolean z, java.lang.String str2) {
        super(iVoiceInteractionManagerService, executor, callback);
        this.mBinder = new android.os.Binder();
        this.mIsAvailabilityOverriddenByTestApi = false;
        this.mAvailability = 0;
        this.mHandler = new android.service.voice.AlwaysOnHotwordDetector.MyHandler(android.os.Looper.getMainLooper());
        this.mText = str;
        this.mLocale = locale;
        this.mKeyphraseEnrollmentInfo = keyphraseEnrollmentInfo;
        this.mExternalCallback = callback;
        this.mExternalExecutor = executor == null ? new android.os.HandlerExecutor(new android.os.Handler(android.os.Looper.myLooper())) : executor;
        this.mInternalCallback = new android.service.voice.AlwaysOnHotwordDetector.SoundTriggerListener(this.mHandler);
        this.mModelManagementService = iVoiceInteractionManagerService;
        this.mSupportSandboxedDetectionService = z;
        this.mAttributionTag = str2;
    }

    @Override // android.service.voice.AbstractDetector
    void initialize(android.os.PersistableBundle persistableBundle, android.os.SharedMemory sharedMemory) {
    }

    void initialize(android.os.PersistableBundle persistableBundle, android.os.SharedMemory sharedMemory, android.hardware.soundtrigger.SoundTrigger.ModuleProperties moduleProperties) {
        if (this.mSupportSandboxedDetectionService) {
            initAndVerifyDetector(persistableBundle, sharedMemory, this.mInternalCallback, 1, this.mAttributionTag);
        }
        try {
            android.media.permission.Identity identity = new android.media.permission.Identity();
            identity.packageName = android.app.ActivityThread.currentOpPackageName();
            if (moduleProperties == null) {
                moduleProperties = this.mModelManagementService.listModuleProperties(identity).stream().filter(new java.util.function.Predicate() { // from class: android.service.voice.AlwaysOnHotwordDetector$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(java.lang.Object obj) {
                        return android.service.voice.AlwaysOnHotwordDetector.lambda$initialize$0((android.hardware.soundtrigger.SoundTrigger.ModuleProperties) obj);
                    }
                }).findFirst().orElse(null);
                if (android.app.compat.CompatChanges.isChangeEnabled(THROW_ON_INITIALIZE_IF_NO_DSP) && moduleProperties == null) {
                    throw new java.lang.IllegalStateException("No DSP module available to attach to");
                }
            }
            this.mSoundTriggerSession = this.mModelManagementService.createSoundTriggerSessionAsOriginator(identity, this.mBinder, moduleProperties);
            new android.service.voice.AlwaysOnHotwordDetector.RefreshAvailabilityTask().execute(new java.lang.Void[0]);
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    static /* synthetic */ boolean lambda$initialize$0(android.hardware.soundtrigger.SoundTrigger.ModuleProperties moduleProperties) {
        return !moduleProperties.getSupportedModelArch().equals("injection");
    }

    @Override // android.service.voice.AbstractDetector, android.service.voice.HotwordDetector
    public final void updateState(android.os.PersistableBundle persistableBundle, android.os.SharedMemory sharedMemory) {
        synchronized (this.mLock) {
            if (!this.mSupportSandboxedDetectionService) {
                throw new java.lang.IllegalStateException("updateState called, but it doesn't support hotword detection service");
            }
            if (this.mAvailability == -3 || this.mAvailability == 3) {
                throw new java.lang.IllegalStateException("updateState called on an invalid detector or error state");
            }
        }
        super.updateState(persistableBundle, sharedMemory);
    }

    public void overrideAvailability(int i) {
        synchronized (this.mLock) {
            this.mAvailability = i;
            this.mIsAvailabilityOverriddenByTestApi = true;
            if (this.mKeyphraseMetadata == null && this.mAvailability == 2) {
                java.util.HashSet hashSet = new java.util.HashSet();
                hashSet.add(this.mLocale);
                this.mKeyphraseMetadata = new android.hardware.soundtrigger.KeyphraseMetadata(1, this.mText, hashSet, 1);
            }
            notifyStateChangedLocked();
        }
    }

    public void resetAvailability() {
        synchronized (this.mLock) {
            this.mIsAvailabilityOverriddenByTestApi = false;
        }
        new android.service.voice.AlwaysOnHotwordDetector.RefreshAvailabilityTask().execute(new java.lang.Void[0]);
    }

    public void triggerHardwareRecognitionEventForTest(int i, int i2, long j, boolean z, int i3, int i4, int i5, boolean z2, android.media.AudioFormat audioFormat, byte[] bArr, java.util.List<android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionExtra> list) {
        android.util.Log.d(TAG, "triggerHardwareRecognitionEventForTest()");
        synchronized (this.mLock) {
            if (this.mAvailability == -3 || this.mAvailability == 3) {
                throw new java.lang.IllegalStateException("triggerHardwareRecognitionEventForTest called on an invalid detector or error state");
            }
            try {
                this.mModelManagementService.triggerHardwareRecognitionEventForTest(new android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionEvent(i, i2, z, i3, i4, i5, z2, audioFormat, bArr, (android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionExtra[]) list.toArray(new android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionExtra[0]), j, new android.os.Binder()), this.mInternalCallback);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public int getSupportedRecognitionModes() {
        int supportedRecognitionModesLocked;
        synchronized (this.mLock) {
            supportedRecognitionModesLocked = getSupportedRecognitionModesLocked();
        }
        return supportedRecognitionModesLocked;
    }

    private int getSupportedRecognitionModesLocked() {
        if (this.mAvailability == -3 || this.mAvailability == 3) {
            throw new java.lang.IllegalStateException("getSupportedRecognitionModes called on an invalid detector or error state");
        }
        if (this.mAvailability != 2 || this.mKeyphraseMetadata == null) {
            throw new java.lang.UnsupportedOperationException("Getting supported recognition modes for the keyphrase is not supported");
        }
        return this.mKeyphraseMetadata.getRecognitionModeFlags();
    }

    public int getSupportedAudioCapabilities() {
        int supportedAudioCapabilitiesLocked;
        synchronized (this.mLock) {
            supportedAudioCapabilitiesLocked = getSupportedAudioCapabilitiesLocked();
        }
        return supportedAudioCapabilitiesLocked;
    }

    private int getSupportedAudioCapabilitiesLocked() {
        try {
            android.hardware.soundtrigger.SoundTrigger.ModuleProperties dspModuleProperties = this.mSoundTriggerSession.getDspModuleProperties();
            if (dspModuleProperties != null) {
                return dspModuleProperties.getAudioCapabilities();
            }
            return 0;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean startRecognition(int i, byte[] bArr) {
        boolean z;
        synchronized (this.mLock) {
            z = startRecognitionLocked(i, bArr) == 0;
        }
        return z;
    }

    public boolean startRecognition(int i) {
        boolean z;
        synchronized (this.mLock) {
            z = startRecognitionLocked(i, null) == 0;
        }
        return z;
    }

    @Override // android.service.voice.HotwordDetector
    public boolean startRecognition() {
        return startRecognition(0);
    }

    @Override // android.service.voice.HotwordDetector
    public boolean stopRecognition() {
        boolean z;
        synchronized (this.mLock) {
            if (this.mAvailability == -3 || this.mAvailability == 3) {
                throw new java.lang.IllegalStateException("stopRecognition called on an invalid detector or error state");
            }
            if (this.mAvailability != 2) {
                throw new java.lang.UnsupportedOperationException("Recognition for the given keyphrase is not supported");
            }
            z = stopRecognitionLocked() == 0;
        }
        return z;
    }

    public int setParameter(int i, int i2) {
        int parameterLocked;
        synchronized (this.mLock) {
            if (this.mAvailability == -3 || this.mAvailability == 3) {
                throw new java.lang.IllegalStateException("setParameter called on an invalid detector or error state");
            }
            parameterLocked = setParameterLocked(i, i2);
        }
        return parameterLocked;
    }

    public int getParameter(int i) {
        int parameterLocked;
        synchronized (this.mLock) {
            if (this.mAvailability == -3 || this.mAvailability == 3) {
                throw new java.lang.IllegalStateException("getParameter called on an invalid detector or error state");
            }
            parameterLocked = getParameterLocked(i);
        }
        return parameterLocked;
    }

    public android.service.voice.AlwaysOnHotwordDetector.ModelParamRange queryParameter(int i) {
        android.service.voice.AlwaysOnHotwordDetector.ModelParamRange queryParameterLocked;
        synchronized (this.mLock) {
            if (this.mAvailability == -3 || this.mAvailability == 3) {
                throw new java.lang.IllegalStateException("queryParameter called on an invalid detector or error state");
            }
            queryParameterLocked = queryParameterLocked(i);
        }
        return queryParameterLocked;
    }

    public android.content.Intent createEnrollIntent() {
        android.content.Intent manageIntentLocked;
        synchronized (this.mLock) {
            manageIntentLocked = getManageIntentLocked(0);
        }
        return manageIntentLocked;
    }

    public android.content.Intent createUnEnrollIntent() {
        android.content.Intent manageIntentLocked;
        synchronized (this.mLock) {
            manageIntentLocked = getManageIntentLocked(2);
        }
        return manageIntentLocked;
    }

    public android.content.Intent createReEnrollIntent() {
        android.content.Intent manageIntentLocked;
        synchronized (this.mLock) {
            manageIntentLocked = getManageIntentLocked(1);
        }
        return manageIntentLocked;
    }

    private android.content.Intent getManageIntentLocked(int i) {
        if (this.mAvailability == -3 || this.mAvailability == 3) {
            throw new java.lang.IllegalStateException("getManageIntent called on an invalid detector or error state");
        }
        if (this.mAvailability != 2 && this.mAvailability != 1) {
            throw new java.lang.UnsupportedOperationException("Managing the given keyphrase is not supported");
        }
        return this.mKeyphraseEnrollmentInfo.getManageKeyphraseIntent(i, this.mText, this.mLocale);
    }

    @Override // android.service.voice.AbstractDetector, android.service.voice.HotwordDetector
    public void destroy() {
        synchronized (this.mLock) {
            detachSessionLocked();
            this.mAvailability = -3;
            this.mIsAvailabilityOverriddenByTestApi = false;
            notifyStateChangedLocked();
        }
        super.destroy();
    }

    private void detachSessionLocked() {
        try {
            if (this.mSoundTriggerSession != null) {
                this.mSoundTriggerSession.detach();
            }
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    @Override // android.service.voice.HotwordDetector
    public boolean isUsingSandboxedDetectionService() {
        return this.mSupportSandboxedDetectionService;
    }

    void onSoundModelsChanged() {
        synchronized (this.mLock) {
            if (this.mAvailability != -3 && this.mAvailability != -2 && this.mAvailability != 3) {
                if (this.mIsAvailabilityOverriddenByTestApi) {
                    android.util.Slog.w(TAG, "Suppressing system availability update. Availability is overridden by test API.");
                    return;
                }
                if (this.mAvailability == 2) {
                    try {
                        int stopRecognitionLocked = stopRecognitionLocked();
                        if (stopRecognitionLocked == 0) {
                            sendSoundTriggerFailure(new android.service.voice.SoundTriggerFailure(0, "stopped recognition because of enrollment update", 4));
                        }
                        android.util.Log.w(TAG, "Failed to stop recognition after enrollment update: code=" + stopRecognitionLocked);
                    } catch (java.lang.Exception e) {
                        android.util.Slog.w(TAG, "Failed to stop recognition after enrollment update", e);
                        if (android.app.compat.CompatChanges.isChangeEnabled(SEND_ON_FAILURE_FOR_ASYNC_EXCEPTIONS)) {
                            sendSoundTriggerFailure(new android.service.voice.SoundTriggerFailure(0, "Failed to stop recognition after enrollment update: " + android.util.Log.getStackTraceString(e), 3));
                        } else {
                            updateAndNotifyStateChangedLocked(3);
                        }
                        return;
                    }
                }
                new android.service.voice.AlwaysOnHotwordDetector.RefreshAvailabilityTask().execute(new java.lang.Void[0]);
                return;
            }
            android.util.Slog.w(TAG, "Received onSoundModelsChanged for an unsupported keyphrase/config or in the error state");
        }
    }

    private int startRecognitionLocked(int i, byte[] bArr) {
        int i2;
        if (this.mAvailability == -3 || this.mAvailability == 3) {
            throw new java.lang.IllegalStateException("startRecognition called on an invalid detector or error state");
        }
        if (this.mAvailability != 2) {
            throw new java.lang.UnsupportedOperationException("Recognition for the given keyphrase is not supported");
        }
        int i3 = 1;
        android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionExtra[] keyphraseRecognitionExtraArr = {new android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionExtra(this.mKeyphraseMetadata.getId(), this.mKeyphraseMetadata.getRecognitionModeFlags(), 0, new android.hardware.soundtrigger.SoundTrigger.ConfidenceLevel[0])};
        boolean z = (i & 1) != 0;
        boolean z2 = (i & 2) != 0;
        boolean z3 = (i & 16) != 0;
        if ((i & 4) == 0) {
            i3 = 0;
        }
        if ((i & 8) == 0) {
            i2 = i3;
        } else {
            i2 = i3 | 2;
        }
        try {
            int startRecognition = this.mSoundTriggerSession.startRecognition(this.mKeyphraseMetadata.getId(), this.mLocale.toLanguageTag(), this.mInternalCallback, new android.hardware.soundtrigger.SoundTrigger.RecognitionConfig(z, z2, keyphraseRecognitionExtraArr, bArr, i2), z3);
            if (startRecognition != 0) {
                android.util.Slog.w(TAG, "startRecognition() failed with error code " + startRecognition);
            }
            return startRecognition;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private int stopRecognitionLocked() {
        try {
            int stopRecognition = this.mSoundTriggerSession.stopRecognition(this.mKeyphraseMetadata.getId(), this.mInternalCallback);
            if (stopRecognition != 0) {
                android.util.Slog.w(TAG, "stopRecognition() failed with error code " + stopRecognition);
            }
            return stopRecognition;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private int setParameterLocked(int i, int i2) {
        try {
            int parameter = this.mSoundTriggerSession.setParameter(this.mKeyphraseMetadata.getId(), i, i2);
            if (parameter != 0) {
                android.util.Slog.w(TAG, "setParameter failed with error code " + parameter);
            }
            return parameter;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private int getParameterLocked(int i) {
        try {
            return this.mSoundTriggerSession.getParameter(this.mKeyphraseMetadata.getId(), i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private android.service.voice.AlwaysOnHotwordDetector.ModelParamRange queryParameterLocked(int i) {
        try {
            android.hardware.soundtrigger.SoundTrigger.ModelParamRange queryParameter = this.mSoundTriggerSession.queryParameter(this.mKeyphraseMetadata.getId(), i);
            if (queryParameter == null) {
                return null;
            }
            return new android.service.voice.AlwaysOnHotwordDetector.ModelParamRange(queryParameter);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateAndNotifyStateChangedLocked(int i) {
        updateAvailabilityLocked(i);
        notifyStateChangedLocked();
    }

    private void updateAvailabilityLocked(int i) {
        if (!this.mIsAvailabilityOverriddenByTestApi) {
            this.mAvailability = i;
        }
    }

    private void notifyStateChangedLocked() {
        android.os.Message obtain = android.os.Message.obtain(this.mHandler, 1);
        obtain.arg1 = this.mAvailability;
        obtain.sendToTarget();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendUnknownFailure(java.lang.String str) {
        updateAvailabilityLocked(3);
        android.os.Message.obtain(this.mHandler, 11, str).sendToTarget();
    }

    private void sendSoundTriggerFailure(android.service.voice.SoundTriggerFailure soundTriggerFailure) {
        android.os.Message.obtain(this.mHandler, 10, soundTriggerFailure).sendToTarget();
    }

    static final class SoundTriggerListener extends com.android.internal.app.IHotwordRecognitionStatusCallback.Stub {
        private final android.os.Handler mHandler;

        public SoundTriggerListener(android.os.Handler handler) {
            this.mHandler = handler;
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onKeyphraseDetected(android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent, android.service.voice.HotwordDetectedResult hotwordDetectedResult) {
            android.util.Slog.i(android.service.voice.AlwaysOnHotwordDetector.TAG, "onDetected");
            android.os.Message.obtain(this.mHandler, 2, new android.service.voice.AlwaysOnHotwordDetector.EventPayload.Builder(keyphraseRecognitionEvent).setHotwordDetectedResult(hotwordDetectedResult).build()).sendToTarget();
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onKeyphraseDetectedFromExternalSource(android.service.voice.HotwordDetectedResult hotwordDetectedResult) {
            android.util.Slog.i(android.service.voice.AlwaysOnHotwordDetector.TAG, "onKeyphraseDetectedFromExternalSource");
            android.service.voice.AlwaysOnHotwordDetector.EventPayload.Builder builder = new android.service.voice.AlwaysOnHotwordDetector.EventPayload.Builder();
            if (android.app.wearable.Flags.enableHotwordWearableSensingApi()) {
                builder.setIsRecognitionStopped(false);
            }
            android.os.Message.obtain(this.mHandler, 2, builder.setHotwordDetectedResult(hotwordDetectedResult).build()).sendToTarget();
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onGenericSoundTriggerDetected(android.hardware.soundtrigger.SoundTrigger.GenericRecognitionEvent genericRecognitionEvent) {
            android.util.Slog.w(android.service.voice.AlwaysOnHotwordDetector.TAG, "Generic sound trigger event detected at AOHD: " + genericRecognitionEvent);
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onRejected(android.service.voice.HotwordRejectedResult hotwordRejectedResult) {
            android.util.Slog.i(android.service.voice.AlwaysOnHotwordDetector.TAG, "onRejected");
            android.os.Message.obtain(this.mHandler, 6, hotwordRejectedResult).sendToTarget();
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onHotwordDetectionServiceFailure(android.service.voice.HotwordDetectionServiceFailure hotwordDetectionServiceFailure) {
            android.util.Slog.v(android.service.voice.AlwaysOnHotwordDetector.TAG, "onHotwordDetectionServiceFailure: " + hotwordDetectionServiceFailure);
            if (hotwordDetectionServiceFailure != null) {
                android.os.Message.obtain(this.mHandler, 9, hotwordDetectionServiceFailure).sendToTarget();
            } else {
                android.os.Message.obtain(this.mHandler, 11, "Error data is null").sendToTarget();
            }
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onVisualQueryDetectionServiceFailure(android.service.voice.VisualQueryDetectionServiceFailure visualQueryDetectionServiceFailure) throws android.os.RemoteException {
            android.util.Slog.w(android.service.voice.AlwaysOnHotwordDetector.TAG, "onVisualQueryDetectionServiceFailure: " + visualQueryDetectionServiceFailure);
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onSoundTriggerFailure(android.service.voice.SoundTriggerFailure soundTriggerFailure) {
            android.os.Message.obtain(this.mHandler, 10, java.util.Objects.requireNonNull(soundTriggerFailure)).sendToTarget();
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onUnknownFailure(java.lang.String str) throws android.os.RemoteException {
            android.util.Slog.v(android.service.voice.AlwaysOnHotwordDetector.TAG, "onUnknownFailure: " + str);
            android.os.Handler handler = this.mHandler;
            if (android.text.TextUtils.isEmpty(str)) {
                str = "Error data is null";
            }
            android.os.Message.obtain(handler, 11, str).sendToTarget();
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onRecognitionPaused() {
            android.util.Slog.i(android.service.voice.AlwaysOnHotwordDetector.TAG, "onRecognitionPaused");
            this.mHandler.sendEmptyMessage(4);
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onRecognitionResumed() {
            android.util.Slog.i(android.service.voice.AlwaysOnHotwordDetector.TAG, "onRecognitionResumed");
            this.mHandler.sendEmptyMessage(5);
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onStatusReported(int i) {
            android.util.Slog.i(android.service.voice.AlwaysOnHotwordDetector.TAG, "onStatusReported");
            android.os.Message obtain = android.os.Message.obtain(this.mHandler, 7);
            obtain.arg1 = i;
            obtain.sendToTarget();
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onProcessRestarted() {
            android.util.Slog.i(android.service.voice.AlwaysOnHotwordDetector.TAG, "onProcessRestarted");
            this.mHandler.sendEmptyMessage(8);
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onOpenFile(java.lang.String str, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
            throw new java.lang.UnsupportedOperationException("Hotword cannot access files from the disk.");
        }
    }

    void onDetectorRemoteException() {
        android.os.Message.obtain(this.mHandler, 9, new android.service.voice.HotwordDetectionServiceFailure(7, "Detector remote exception occurs")).sendToTarget();
    }

    class MyHandler extends android.os.Handler {
        MyHandler(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            synchronized (android.service.voice.AlwaysOnHotwordDetector.this.mLock) {
                if (android.service.voice.AlwaysOnHotwordDetector.this.mAvailability == -3) {
                    android.util.Slog.w(android.service.voice.AlwaysOnHotwordDetector.TAG, "Received message: " + message.what + " for an invalid detector");
                } else {
                    final android.os.Message obtain = android.os.Message.obtain(message);
                    android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.service.voice.AlwaysOnHotwordDetector$MyHandler$$ExternalSyntheticLambda1
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            android.service.voice.AlwaysOnHotwordDetector.MyHandler.this.lambda$handleMessage$1(obtain);
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleMessage$1(final android.os.Message message) throws java.lang.Exception {
            android.service.voice.AlwaysOnHotwordDetector.this.mExternalExecutor.execute(new java.lang.Runnable() { // from class: android.service.voice.AlwaysOnHotwordDetector$MyHandler$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.voice.AlwaysOnHotwordDetector.MyHandler.this.lambda$handleMessage$0(message);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleMessage$0(android.os.Message message) {
            android.util.Slog.i(android.service.voice.AlwaysOnHotwordDetector.TAG, "handle message " + message.what);
            switch (message.what) {
                case 1:
                    android.service.voice.AlwaysOnHotwordDetector.this.mExternalCallback.onAvailabilityChanged(message.arg1);
                    break;
                case 2:
                    android.service.voice.AlwaysOnHotwordDetector.this.mExternalCallback.onDetected((android.service.voice.AlwaysOnHotwordDetector.EventPayload) message.obj);
                    break;
                case 3:
                    android.service.voice.AlwaysOnHotwordDetector.this.mExternalCallback.onError();
                    break;
                case 4:
                    android.service.voice.AlwaysOnHotwordDetector.this.mExternalCallback.onRecognitionPaused();
                    break;
                case 5:
                    android.service.voice.AlwaysOnHotwordDetector.this.mExternalCallback.onRecognitionResumed();
                    break;
                case 6:
                    android.service.voice.AlwaysOnHotwordDetector.this.mExternalCallback.onRejected((android.service.voice.HotwordRejectedResult) message.obj);
                    break;
                case 7:
                    android.service.voice.AlwaysOnHotwordDetector.this.mExternalCallback.onHotwordDetectionServiceInitialized(message.arg1);
                    break;
                case 8:
                    android.service.voice.AlwaysOnHotwordDetector.this.mExternalCallback.onHotwordDetectionServiceRestarted();
                    break;
                case 9:
                    android.service.voice.AlwaysOnHotwordDetector.this.mExternalCallback.onFailure((android.service.voice.HotwordDetectionServiceFailure) message.obj);
                    break;
                case 10:
                    android.service.voice.AlwaysOnHotwordDetector.this.mExternalCallback.onFailure((android.service.voice.SoundTriggerFailure) message.obj);
                    break;
                case 11:
                    android.service.voice.AlwaysOnHotwordDetector.this.mExternalCallback.onUnknownFailure((java.lang.String) message.obj);
                    break;
                default:
                    super.handleMessage(message);
                    break;
            }
            message.recycle();
        }
    }

    class RefreshAvailabilityTask extends android.os.AsyncTask<java.lang.Void, java.lang.Void, java.lang.Void> {
        RefreshAvailabilityTask() {
        }

        @Override // android.os.AsyncTask
        public java.lang.Void doInBackground(java.lang.Void... voidArr) {
            try {
                int internalGetInitialAvailability = internalGetInitialAvailability();
                synchronized (android.service.voice.AlwaysOnHotwordDetector.this.mLock) {
                    if (internalGetInitialAvailability == 0) {
                        internalUpdateEnrolledKeyphraseMetadata();
                        if (android.service.voice.AlwaysOnHotwordDetector.this.mKeyphraseMetadata != null) {
                            internalGetInitialAvailability = 2;
                        } else {
                            internalGetInitialAvailability = 1;
                        }
                    }
                    android.service.voice.AlwaysOnHotwordDetector.this.updateAndNotifyStateChangedLocked(internalGetInitialAvailability);
                }
                return null;
            } catch (java.lang.Exception e) {
                android.util.Slog.w(android.service.voice.AlwaysOnHotwordDetector.TAG, "Failed to refresh availability", e);
                synchronized (android.service.voice.AlwaysOnHotwordDetector.this.mLock) {
                    if (android.app.compat.CompatChanges.isChangeEnabled(android.service.voice.AlwaysOnHotwordDetector.SEND_ON_FAILURE_FOR_ASYNC_EXCEPTIONS)) {
                        android.service.voice.AlwaysOnHotwordDetector.this.sendUnknownFailure("Failed to refresh availability: " + android.util.Log.getStackTraceString(e));
                    } else {
                        android.service.voice.AlwaysOnHotwordDetector.this.updateAndNotifyStateChangedLocked(3);
                    }
                    return null;
                }
            }
        }

        private int internalGetInitialAvailability() {
            synchronized (android.service.voice.AlwaysOnHotwordDetector.this.mLock) {
                if (android.service.voice.AlwaysOnHotwordDetector.this.mAvailability == -3) {
                    return -3;
                }
                if (!android.app.compat.CompatChanges.isChangeEnabled(android.service.voice.AlwaysOnHotwordDetector.THROW_ON_INITIALIZE_IF_NO_DSP)) {
                    try {
                        if (android.service.voice.AlwaysOnHotwordDetector.this.mSoundTriggerSession.getDspModuleProperties() == null) {
                            return -2;
                        }
                        return 0;
                    } catch (android.os.RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                }
                return 0;
            }
        }

        private void internalUpdateEnrolledKeyphraseMetadata() {
            try {
                android.service.voice.AlwaysOnHotwordDetector.this.mKeyphraseMetadata = android.service.voice.AlwaysOnHotwordDetector.this.mModelManagementService.getEnrolledKeyphraseMetadata(android.service.voice.AlwaysOnHotwordDetector.this.mText, android.service.voice.AlwaysOnHotwordDetector.this.mLocale.toLanguageTag());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public boolean equals(java.lang.Object obj) {
        if (android.app.compat.CompatChanges.isChangeEnabled(193232191L)) {
            if (!(obj instanceof android.service.voice.AlwaysOnHotwordDetector)) {
                return false;
            }
            android.service.voice.AlwaysOnHotwordDetector alwaysOnHotwordDetector = (android.service.voice.AlwaysOnHotwordDetector) obj;
            return android.text.TextUtils.equals(this.mText, alwaysOnHotwordDetector.mText) && this.mLocale.equals(alwaysOnHotwordDetector.mLocale);
        }
        return super.equals(obj);
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mText, this.mLocale);
    }

    @Override // android.service.voice.HotwordDetector
    public void dump(java.lang.String str, java.io.PrintWriter printWriter) {
        synchronized (this.mLock) {
            printWriter.print(str);
            printWriter.print("Text=");
            printWriter.println(this.mText);
            printWriter.print(str);
            printWriter.print("Locale=");
            printWriter.println(this.mLocale);
            printWriter.print(str);
            printWriter.print("Availability=");
            printWriter.println(this.mAvailability);
            printWriter.print(str);
            printWriter.print("KeyphraseMetadata=");
            printWriter.println(this.mKeyphraseMetadata);
            printWriter.print(str);
            printWriter.print("EnrollmentInfo=");
            printWriter.println(this.mKeyphraseEnrollmentInfo);
        }
    }
}
