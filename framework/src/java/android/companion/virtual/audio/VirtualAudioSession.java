package android.companion.virtual.audio;

/* loaded from: classes.dex */
public final class VirtualAudioSession extends android.companion.virtual.audio.IAudioRoutingCallback.Stub implements android.companion.virtual.audio.UserRestrictionsDetector.UserRestrictionsCallback, java.io.Closeable {
    private static final java.lang.String TAG = "VirtualAudioSession";
    private android.companion.virtual.audio.AudioCapture mAudioCapture;
    private final android.companion.virtual.audio.VirtualAudioSession.AudioConfigChangedCallback mAudioConfigChangedCallback;
    private android.companion.virtual.audio.AudioInjection mAudioInjection;
    private android.media.audiopolicy.AudioPolicy mAudioPolicy;
    private final android.content.Context mContext;
    private final java.lang.Object mLock = new java.lang.Object();
    private final android.util.IntArray mReroutedAppUids = new android.util.IntArray();
    private final android.companion.virtual.audio.UserRestrictionsDetector mUserRestrictionsDetector;

    public static final class AudioConfigChangedCallback extends android.companion.virtual.audio.IAudioConfigChangedCallback.Stub {
        private final android.companion.virtual.audio.VirtualAudioDevice.AudioConfigurationChangeCallback mCallback;
        private final java.util.concurrent.Executor mExecutor;

        AudioConfigChangedCallback(android.content.Context context, java.util.concurrent.Executor executor, android.companion.virtual.audio.VirtualAudioDevice.AudioConfigurationChangeCallback audioConfigurationChangeCallback) {
            this.mExecutor = executor == null ? context.getMainExecutor() : executor;
            this.mCallback = audioConfigurationChangeCallback;
        }

        @Override // android.companion.virtual.audio.IAudioConfigChangedCallback
        public void onPlaybackConfigChanged(final java.util.List<android.media.AudioPlaybackConfiguration> list) {
            if (this.mCallback != null) {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.companion.virtual.audio.VirtualAudioSession$AudioConfigChangedCallback$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.companion.virtual.audio.VirtualAudioSession.AudioConfigChangedCallback.this.lambda$onPlaybackConfigChanged$0(list);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPlaybackConfigChanged$0(java.util.List list) {
            this.mCallback.onPlaybackConfigChanged(list);
        }

        @Override // android.companion.virtual.audio.IAudioConfigChangedCallback
        public void onRecordingConfigChanged(final java.util.List<android.media.AudioRecordingConfiguration> list) {
            if (this.mCallback != null) {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.companion.virtual.audio.VirtualAudioSession$AudioConfigChangedCallback$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.companion.virtual.audio.VirtualAudioSession.AudioConfigChangedCallback.this.lambda$onRecordingConfigChanged$1(list);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRecordingConfigChanged$1(java.util.List list) {
            this.mCallback.onRecordingConfigChanged(list);
        }
    }

    public VirtualAudioSession(android.content.Context context, android.companion.virtual.audio.VirtualAudioDevice.AudioConfigurationChangeCallback audioConfigurationChangeCallback, java.util.concurrent.Executor executor) {
        this.mContext = context;
        this.mUserRestrictionsDetector = new android.companion.virtual.audio.UserRestrictionsDetector(context);
        this.mAudioConfigChangedCallback = audioConfigurationChangeCallback == null ? null : new android.companion.virtual.audio.VirtualAudioSession.AudioConfigChangedCallback(context, executor, audioConfigurationChangeCallback);
    }

    public android.companion.virtual.audio.AudioCapture startAudioCapture(android.media.AudioFormat audioFormat) {
        android.companion.virtual.audio.AudioCapture audioCapture;
        java.util.Objects.requireNonNull(audioFormat, "captureFormat must not be null");
        synchronized (this.mLock) {
            if (this.mAudioCapture != null) {
                throw new java.lang.IllegalStateException("Cannot start capture while another capture is ongoing.");
            }
            this.mAudioCapture = new android.companion.virtual.audio.AudioCapture(audioFormat);
            this.mAudioCapture.startRecording();
            audioCapture = this.mAudioCapture;
        }
        return audioCapture;
    }

    public android.companion.virtual.audio.AudioInjection startAudioInjection(android.media.AudioFormat audioFormat) {
        android.companion.virtual.audio.AudioInjection audioInjection;
        java.util.Objects.requireNonNull(audioFormat, "injectionFormat must not be null");
        synchronized (this.mLock) {
            if (this.mAudioInjection != null) {
                throw new java.lang.IllegalStateException("Cannot start injection while injection is already ongoing.");
            }
            this.mAudioInjection = new android.companion.virtual.audio.AudioInjection(audioFormat);
            this.mAudioInjection.play();
            this.mUserRestrictionsDetector.register(this);
            this.mAudioInjection.setSilent(this.mUserRestrictionsDetector.isUnmuteMicrophoneDisallowed());
            audioInjection = this.mAudioInjection;
        }
        return audioInjection;
    }

    public android.companion.virtual.audio.VirtualAudioSession.AudioConfigChangedCallback getAudioConfigChangedListener() {
        return this.mAudioConfigChangedCallback;
    }

    public android.companion.virtual.audio.AudioCapture getAudioCapture() {
        android.companion.virtual.audio.AudioCapture audioCapture;
        synchronized (this.mLock) {
            audioCapture = this.mAudioCapture;
        }
        return audioCapture;
    }

    public android.companion.virtual.audio.AudioInjection getAudioInjection() {
        android.companion.virtual.audio.AudioInjection audioInjection;
        synchronized (this.mLock) {
            audioInjection = this.mAudioInjection;
        }
        return audioInjection;
    }

    @Override // android.companion.virtual.audio.IAudioRoutingCallback
    public void onAppsNeedingAudioRoutingChanged(int[] iArr) {
        synchronized (this.mLock) {
            if (java.util.Arrays.equals(this.mReroutedAppUids.toArray(), iArr)) {
                return;
            }
            releaseAudioStreams();
            if (iArr.length == 0) {
                return;
            }
            createAudioStreams(iArr);
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.mUserRestrictionsDetector.unregister();
        releaseAudioStreams();
        synchronized (this.mLock) {
            if (this.mAudioCapture != null) {
                this.mAudioCapture.close();
                this.mAudioCapture = null;
            }
            if (this.mAudioInjection != null) {
                this.mAudioInjection.close();
                this.mAudioInjection = null;
            }
        }
    }

    @Override // android.companion.virtual.audio.UserRestrictionsDetector.UserRestrictionsCallback
    public void onMicrophoneRestrictionChanged(boolean z) {
        synchronized (this.mLock) {
            if (this.mAudioInjection != null) {
                this.mAudioInjection.setSilent(z);
            }
        }
    }

    private void createAudioStreams(int[] iArr) {
        android.media.audiopolicy.AudioMix audioMix;
        android.media.audiopolicy.AudioMix audioMix2;
        synchronized (this.mLock) {
            if (this.mAudioCapture == null && this.mAudioInjection == null) {
                throw new java.lang.IllegalStateException("At least one of AudioCapture and AudioInjection must be started.");
            }
            if (this.mAudioPolicy != null) {
                throw new java.lang.IllegalStateException("Cannot create audio streams while the audio policy is registered. Call releaseAudioStreams() first to unregister the previous audio policy.");
            }
            this.mReroutedAppUids.clear();
            for (int i : iArr) {
                this.mReroutedAppUids.add(i);
            }
            android.media.audiopolicy.AudioPolicy.Builder builder = new android.media.audiopolicy.AudioPolicy.Builder(this.mContext);
            if (this.mAudioCapture == null) {
                audioMix = null;
            } else {
                audioMix = createAudioRecordMix(this.mAudioCapture.getFormat(), iArr);
                builder.addMix(audioMix);
            }
            if (this.mAudioInjection == null) {
                audioMix2 = null;
            } else {
                audioMix2 = createAudioTrackMix(this.mAudioInjection.getFormat(), iArr);
                builder.addMix(audioMix2);
            }
            this.mAudioPolicy = builder.build();
            if (((android.media.AudioManager) this.mContext.getSystemService(android.media.AudioManager.class)).registerAudioPolicy(this.mAudioPolicy) == -1) {
                android.util.Log.e(TAG, "Failed to register audio policy!");
            }
            android.media.AudioRecord createAudioRecordSink = audioMix != null ? this.mAudioPolicy.createAudioRecordSink(audioMix) : null;
            android.media.AudioTrack createAudioTrackSource = audioMix2 != null ? this.mAudioPolicy.createAudioTrackSource(audioMix2) : null;
            if (this.mAudioCapture != null) {
                this.mAudioCapture.setAudioRecord(createAudioRecordSink);
            }
            if (this.mAudioInjection != null) {
                this.mAudioInjection.setAudioTrack(createAudioTrackSource);
            }
        }
    }

    private void releaseAudioStreams() {
        synchronized (this.mLock) {
            if (this.mAudioCapture != null) {
                this.mAudioCapture.setAudioRecord(null);
            }
            if (this.mAudioInjection != null) {
                this.mAudioInjection.setAudioTrack(null);
            }
            this.mReroutedAppUids.clear();
            if (this.mAudioPolicy != null) {
                ((android.media.AudioManager) this.mContext.getSystemService(android.media.AudioManager.class)).unregisterAudioPolicy(this.mAudioPolicy);
                this.mAudioPolicy = null;
                android.util.Log.i(TAG, "AudioPolicy unregistered");
            }
        }
    }

    public android.util.IntArray getReroutedAppUids() {
        android.util.IntArray intArray;
        synchronized (this.mLock) {
            intArray = this.mReroutedAppUids;
        }
        return intArray;
    }

    private static android.media.audiopolicy.AudioMix createAudioRecordMix(android.media.AudioFormat audioFormat, int[] iArr) {
        android.media.audiopolicy.AudioMixingRule.Builder builder = new android.media.audiopolicy.AudioMixingRule.Builder();
        builder.setTargetMixRole(0);
        for (int i : iArr) {
            builder.addMixRule(4, java.lang.Integer.valueOf(i));
        }
        return new android.media.audiopolicy.AudioMix.Builder(builder.allowPrivilegedPlaybackCapture(false).build()).setFormat(audioFormat).setRouteFlags(2).build();
    }

    private static android.media.audiopolicy.AudioMix createAudioTrackMix(android.media.AudioFormat audioFormat, int[] iArr) {
        android.media.audiopolicy.AudioMixingRule.Builder builder = new android.media.audiopolicy.AudioMixingRule.Builder();
        builder.setTargetMixRole(1);
        for (int i : iArr) {
            builder.addMixRule(4, java.lang.Integer.valueOf(i));
        }
        return new android.media.audiopolicy.AudioMix.Builder(builder.build()).setFormat(audioFormat).setRouteFlags(2).build();
    }
}
