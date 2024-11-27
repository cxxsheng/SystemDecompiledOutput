package android.media;

/* loaded from: classes2.dex */
public class AudioTrack extends android.media.PlayerBase implements android.media.AudioRouting, android.media.VolumeAutomation {
    private static final int AUDIO_OUTPUT_FLAG_DEEP_BUFFER = 8;
    private static final int AUDIO_OUTPUT_FLAG_FAST = 4;
    private static final java.util.Map<java.lang.String, java.lang.Integer> CHANNEL_PAIR_MAP = java.util.Map.of("front", 12, android.inputmethodservice.navigationbar.NavigationBarInflaterView.BACK, 192, "front of center", 768, "side", java.lang.Integer.valueOf(android.opengl.GLES30.GL_COLOR), "top front", 81920, "top back", 655360, "top side", java.lang.Integer.valueOf(android.content.IntentFilter.MATCH_CATEGORY_HOST), "bottom front", 20971520, "front wide", java.lang.Integer.valueOf(android.media.audio.Enums.AUDIO_FORMAT_DTS_HD));
    public static final int DUAL_MONO_MODE_LL = 2;
    public static final int DUAL_MONO_MODE_LR = 1;
    public static final int DUAL_MONO_MODE_OFF = 0;
    public static final int DUAL_MONO_MODE_RR = 3;
    public static final int ENCAPSULATION_METADATA_TYPE_DVB_AD_DESCRIPTOR = 2;
    public static final int ENCAPSULATION_METADATA_TYPE_FRAMEWORK_TUNER = 1;
    public static final int ENCAPSULATION_METADATA_TYPE_NONE = 0;
    public static final int ENCAPSULATION_METADATA_TYPE_SUPPLEMENTARY_AUDIO_PLACEMENT = 3;
    public static final int ENCAPSULATION_MODE_ELEMENTARY_STREAM = 1;

    @android.annotation.SystemApi
    public static final int ENCAPSULATION_MODE_HANDLE = 2;
    public static final int ENCAPSULATION_MODE_NONE = 0;
    public static final int ERROR = -1;
    public static final int ERROR_BAD_VALUE = -2;
    public static final int ERROR_DEAD_OBJECT = -6;
    public static final int ERROR_INVALID_OPERATION = -3;
    private static final int ERROR_NATIVESETUP_AUDIOSYSTEM = -16;
    private static final int ERROR_NATIVESETUP_INVALIDCHANNELMASK = -17;
    private static final int ERROR_NATIVESETUP_INVALIDFORMAT = -18;
    private static final int ERROR_NATIVESETUP_INVALIDSTREAMTYPE = -19;
    private static final int ERROR_NATIVESETUP_NATIVEINITFAILED = -20;
    public static final int ERROR_WOULD_BLOCK = -7;
    private static final float GAIN_MAX = 1.0f;
    private static final float GAIN_MIN = 0.0f;
    private static final float HEADER_V2_SIZE_BYTES = 20.0f;
    private static final float MAX_AUDIO_DESCRIPTION_MIX_LEVEL = 48.0f;
    public static final int MODE_STATIC = 0;
    public static final int MODE_STREAM = 1;
    private static final int NATIVE_EVENT_CAN_WRITE_MORE_DATA = 9;
    private static final int NATIVE_EVENT_CODEC_FORMAT_CHANGE = 100;
    private static final int NATIVE_EVENT_MARKER = 3;
    private static final int NATIVE_EVENT_NEW_IAUDIOTRACK = 6;
    private static final int NATIVE_EVENT_NEW_POS = 4;
    private static final int NATIVE_EVENT_STREAM_END = 7;
    public static final int PERFORMANCE_MODE_LOW_LATENCY = 1;
    public static final int PERFORMANCE_MODE_NONE = 0;
    public static final int PERFORMANCE_MODE_POWER_SAVING = 2;
    public static final int PLAYSTATE_PAUSED = 2;
    private static final int PLAYSTATE_PAUSED_STOPPING = 5;
    public static final int PLAYSTATE_PLAYING = 3;
    public static final int PLAYSTATE_STOPPED = 1;
    private static final int PLAYSTATE_STOPPING = 4;
    public static final int STATE_INITIALIZED = 1;
    public static final int STATE_NO_STATIC_DATA = 2;
    public static final int STATE_UNINITIALIZED = 0;
    public static final int SUCCESS = 0;
    public static final int SUPPLEMENTARY_AUDIO_PLACEMENT_LEFT = 1;
    public static final int SUPPLEMENTARY_AUDIO_PLACEMENT_NORMAL = 0;
    public static final int SUPPLEMENTARY_AUDIO_PLACEMENT_RIGHT = 2;
    private static final int SUPPORTED_OUT_CHANNELS = 268435452;
    private static final java.lang.String TAG = "android.media.AudioTrack";
    public static final int WRITE_BLOCKING = 0;
    public static final int WRITE_NON_BLOCKING = 1;
    private int mAudioFormat;
    private android.media.audiopolicy.AudioPolicy mAudioPolicy;
    private int mAvSyncBytesRemaining;
    private java.nio.ByteBuffer mAvSyncHeader;
    private int mChannelConfiguration;
    private int mChannelCount;
    private int mChannelIndexMask;
    private int mChannelMask;
    private final android.media.Utils.ListenerList<android.media.AudioMetadataReadMap> mCodecFormatChangedListeners;
    private android.media.AudioAttributes mConfiguredAudioAttributes;
    private int mDataLoadMode;
    private boolean mEnableSelfRoutingMonitor;
    private android.media.AudioTrack.NativePositionEventHandlerDelegate mEventHandlerDelegate;
    private final android.os.Looper mInitializationLooper;
    private long mJniData;
    private android.media.metrics.LogSessionId mLogSessionId;
    private int mNativeBufferSizeInBytes;
    private int mNativeBufferSizeInFrames;
    protected long mNativeTrackInJavaObj;
    private int mOffloadDelayFrames;
    private boolean mOffloadEosPending;
    private int mOffloadPaddingFrames;
    private boolean mOffloaded;
    private int mOffset;
    private int mPlayState;
    private final java.lang.Object mPlayStateLock;
    private android.media.AudioDeviceInfo mPreferredDevice;
    private android.util.ArrayMap<android.media.AudioRouting.OnRoutingChangedListener, android.media.NativeRoutingEventHandlerDelegate> mRoutingChangeListeners;
    private int mSampleRate;
    private int mSessionId;
    private int mState;
    private java.util.LinkedList<android.media.AudioTrack.StreamEventCbInfo> mStreamEventCbInfoList;
    private final java.lang.Object mStreamEventCbLock;
    private volatile android.media.AudioTrack.StreamEventHandler mStreamEventHandler;
    private android.os.HandlerThread mStreamEventHandlerThread;
    private int mStreamType;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DualMonoMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface EncapsulationMetadataType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface EncapsulationMode {
    }

    public interface OnCodecFormatChangedListener {
        void onCodecFormatChanged(android.media.AudioTrack audioTrack, android.media.AudioMetadataReadMap audioMetadataReadMap);
    }

    public interface OnPlaybackPositionUpdateListener {
        void onMarkerReached(android.media.AudioTrack audioTrack);

        void onPeriodicNotification(android.media.AudioTrack audioTrack);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PerformanceMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SupplementaryAudioPlacement {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface TransferMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface WriteMode {
    }

    private native int native_applyVolumeShaper(android.media.VolumeShaper.Configuration configuration, android.media.VolumeShaper.Operation operation);

    private final native int native_attachAuxEffect(int i);

    private final native void native_disableDeviceCallback();

    private final native void native_enableDeviceCallback();

    private final native void native_finalize();

    private final native void native_flush();

    private native android.os.PersistableBundle native_getMetrics();

    private native int native_getPortId();

    private final native int native_getRoutedDeviceId();

    private native int native_getStartThresholdInFrames();

    private native android.media.VolumeShaper.State native_getVolumeShaperState(int i);

    private native int native_get_audio_description_mix_level_db(float[] fArr);

    private final native int native_get_buffer_capacity_frames();

    private final native int native_get_buffer_size_frames();

    private native int native_get_dual_mono_mode(int[] iArr);

    private final native int native_get_flags();

    private final native int native_get_latency();

    private final native int native_get_marker_pos();

    private static final native int native_get_min_buff_size(int i, int i2, int i3);

    private static final native int native_get_output_sample_rate(int i);

    private final native android.media.PlaybackParams native_get_playback_params();

    private final native int native_get_playback_rate();

    private final native int native_get_pos_update_period();

    private final native int native_get_position();

    private final native int native_get_timestamp(long[] jArr);

    private final native int native_get_underrun_count();

    private static native boolean native_is_direct_output_supported(int i, int i2, int i3, int i4, int i5, int i6, int i7);

    private final native void native_pause();

    private final native int native_reload_static();

    private final native int native_setAuxEffectSendLevel(float f);

    private native void native_setLogSessionId(java.lang.String str);

    private final native boolean native_setOutputDevice(int i);

    private native void native_setPlayerIId(int i);

    private final native int native_setPresentation(int i, int i2);

    private native int native_setStartThresholdInFrames(int i);

    private final native void native_setVolume(float f, float f2);

    private native int native_set_audio_description_mix_level_db(float f);

    private final native int native_set_buffer_size_frames(int i);

    private native void native_set_delay_padding(int i, int i2);

    private native int native_set_dual_mono_mode(int i);

    private final native int native_set_loop(int i, int i2, int i3);

    private final native int native_set_marker_pos(int i);

    private final native void native_set_playback_params(android.media.PlaybackParams playbackParams);

    private final native int native_set_playback_rate(int i);

    private final native int native_set_pos_update_period(int i);

    private final native int native_set_position(int i);

    private final native int native_setup(java.lang.Object obj, java.lang.Object obj2, int[] iArr, int i, int i2, int i3, int i4, int i5, int[] iArr2, android.os.Parcel parcel, long j, boolean z, int i6, java.lang.Object obj3, java.lang.String str);

    /* JADX INFO: Access modifiers changed from: private */
    public final native void native_start();

    private final native void native_stop();

    private final native int native_write_byte(byte[] bArr, int i, int i2, int i3, boolean z);

    private final native int native_write_float(float[] fArr, int i, int i2, int i3, boolean z);

    private final native int native_write_native_bytes(java.nio.ByteBuffer byteBuffer, int i, int i2, int i3, boolean z);

    private final native int native_write_short(short[] sArr, int i, int i2, int i3, boolean z);

    public final native void native_release();

    public AudioTrack(int i, int i2, int i3, int i4, int i5, int i6) throws java.lang.IllegalArgumentException {
        this(i, i2, i3, i4, i5, i6, 0);
    }

    public AudioTrack(int i, int i2, int i3, int i4, int i5, int i6, int i7) throws java.lang.IllegalArgumentException {
        this(new android.media.AudioAttributes.Builder().setLegacyStreamType(i).build(), new android.media.AudioFormat.Builder().setChannelMask(i3).setEncoding(i4).setSampleRate(i2).build(), i5, i6, i7);
        deprecateStreamTypeForPlayback(i, "AudioTrack", "AudioTrack()");
    }

    public AudioTrack(android.media.AudioAttributes audioAttributes, android.media.AudioFormat audioFormat, int i, int i2, int i3) throws java.lang.IllegalArgumentException {
        this(null, audioAttributes, audioFormat, i, i2, i3, false, 0, null);
    }

    private AudioTrack(android.content.Context context, android.media.AudioAttributes audioAttributes, android.media.AudioFormat audioFormat, int i, int i2, int i3, boolean z, int i4, android.media.AudioTrack.TunerConfiguration tunerConfiguration) throws java.lang.IllegalArgumentException {
        super(audioAttributes, 1);
        android.os.Looper looper;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        this.mState = 0;
        this.mPlayState = 1;
        this.mOffloadEosPending = false;
        this.mPlayStateLock = new java.lang.Object();
        this.mNativeBufferSizeInBytes = 0;
        this.mNativeBufferSizeInFrames = 0;
        this.mChannelCount = 1;
        this.mChannelMask = 4;
        this.mStreamType = 3;
        this.mDataLoadMode = 1;
        this.mChannelConfiguration = 4;
        this.mChannelIndexMask = 0;
        this.mSessionId = 0;
        this.mAvSyncHeader = null;
        this.mAvSyncBytesRemaining = 0;
        this.mOffset = 0;
        this.mOffloaded = false;
        this.mOffloadDelayFrames = 0;
        this.mOffloadPaddingFrames = 0;
        this.mLogSessionId = android.media.metrics.LogSessionId.LOG_SESSION_ID_NONE;
        this.mPreferredDevice = null;
        this.mRoutingChangeListeners = new android.util.ArrayMap<>();
        this.mCodecFormatChangedListeners = new android.media.Utils.ListenerList<>();
        this.mStreamEventCbLock = new java.lang.Object();
        this.mStreamEventCbInfoList = new java.util.LinkedList<>();
        this.mConfiguredAudioAttributes = audioAttributes;
        if (audioFormat == null) {
            throw new java.lang.IllegalArgumentException("Illegal null AudioFormat");
        }
        if (shouldEnablePowerSaving(this.mAttributes, audioFormat, i, i2)) {
            this.mAttributes = new android.media.AudioAttributes.Builder(this.mAttributes).replaceFlags((this.mAttributes.getAllFlags() | 512) & (-257)).build();
        }
        android.os.Looper myLooper = android.os.Looper.myLooper();
        if (myLooper != null) {
            looper = myLooper;
        } else {
            looper = android.os.Looper.getMainLooper();
        }
        int sampleRate = audioFormat.getSampleRate();
        if (sampleRate != 0) {
            i5 = sampleRate;
        } else {
            i5 = 0;
        }
        if ((audioFormat.getPropertySetMask() & 8) == 0) {
            i6 = 0;
        } else {
            i6 = audioFormat.getChannelIndexMask();
        }
        if ((audioFormat.getPropertySetMask() & 4) != 0) {
            i7 = audioFormat.getChannelMask();
        } else if (i6 != 0) {
            i7 = 0;
        } else {
            i7 = 12;
        }
        if ((audioFormat.getPropertySetMask() & 1) == 0) {
            i8 = 1;
        } else {
            i8 = audioFormat.getEncoding();
        }
        audioParamCheck(i5, i7, i6, i8, i2);
        this.mOffloaded = z;
        this.mStreamType = -1;
        audioBuffSizeCheck(i);
        this.mInitializationLooper = looper;
        if (i3 < 0) {
            throw new java.lang.IllegalArgumentException("Invalid audio session ID: " + i3);
        }
        int[] iArr = {this.mSampleRate};
        int[] iArr2 = {resolvePlaybackSessionId(context, i3)};
        android.content.AttributionSource.ScopedParcelState asScopedParcelState = (context == null ? android.content.AttributionSource.myAttributionSource() : context.getAttributionSource()).asScopedParcelState();
        try {
            int native_setup = native_setup(new java.lang.ref.WeakReference(this), this.mAttributes, iArr, this.mChannelMask, this.mChannelIndexMask, this.mAudioFormat, this.mNativeBufferSizeInBytes, this.mDataLoadMode, iArr2, asScopedParcelState.getParcel(), 0L, z, i4, tunerConfiguration, getCurrentOpPackageName());
            if (native_setup != 0) {
                loge("Error code " + native_setup + " when initializing AudioTrack.");
                if (asScopedParcelState != null) {
                    asScopedParcelState.close();
                    return;
                }
                return;
            }
            if (asScopedParcelState != null) {
                asScopedParcelState.close();
            }
            this.mSampleRate = iArr[0];
            this.mSessionId = iArr2[0];
            if ((this.mAttributes.getFlags() & 16) != 0) {
                if (android.media.AudioFormat.isEncodingLinearFrames(this.mAudioFormat)) {
                    i9 = this.mChannelCount * android.media.AudioFormat.getBytesPerSample(this.mAudioFormat);
                } else {
                    i9 = 1;
                }
                this.mOffset = ((int) java.lang.Math.ceil(HEADER_V2_SIZE_BYTES / i9)) * i9;
            }
            if (this.mDataLoadMode == 0) {
                this.mState = 2;
            } else {
                this.mState = 1;
            }
            baseRegisterPlayer(this.mSessionId);
            native_setPlayerIId(this.mPlayerIId);
        } finally {
        }
    }

    AudioTrack(long j) {
        super(new android.media.AudioAttributes.Builder().build(), 1);
        this.mState = 0;
        this.mPlayState = 1;
        this.mOffloadEosPending = false;
        this.mPlayStateLock = new java.lang.Object();
        this.mNativeBufferSizeInBytes = 0;
        this.mNativeBufferSizeInFrames = 0;
        this.mChannelCount = 1;
        this.mChannelMask = 4;
        this.mStreamType = 3;
        this.mDataLoadMode = 1;
        this.mChannelConfiguration = 4;
        this.mChannelIndexMask = 0;
        this.mSessionId = 0;
        this.mAvSyncHeader = null;
        this.mAvSyncBytesRemaining = 0;
        this.mOffset = 0;
        this.mOffloaded = false;
        this.mOffloadDelayFrames = 0;
        this.mOffloadPaddingFrames = 0;
        this.mLogSessionId = android.media.metrics.LogSessionId.LOG_SESSION_ID_NONE;
        this.mPreferredDevice = null;
        this.mRoutingChangeListeners = new android.util.ArrayMap<>();
        this.mCodecFormatChangedListeners = new android.media.Utils.ListenerList<>();
        this.mStreamEventCbLock = new java.lang.Object();
        this.mStreamEventCbInfoList = new java.util.LinkedList<>();
        this.mNativeTrackInJavaObj = 0L;
        this.mJniData = 0L;
        android.os.Looper myLooper = android.os.Looper.myLooper();
        this.mInitializationLooper = myLooper == null ? android.os.Looper.getMainLooper() : myLooper;
        if (j != 0) {
            baseRegisterPlayer(0);
            deferred_connect(j);
        } else {
            this.mState = 0;
        }
    }

    void deferred_connect(long j) {
        if (this.mState != 1) {
            int[] iArr = {0};
            int[] iArr2 = {0};
            android.content.AttributionSource.ScopedParcelState asScopedParcelState = android.content.AttributionSource.myAttributionSource().asScopedParcelState();
            try {
                int native_setup = native_setup(new java.lang.ref.WeakReference(this), null, iArr2, 0, 0, 0, 0, 0, iArr, asScopedParcelState.getParcel(), j, false, 0, null, "");
                if (native_setup != 0) {
                    loge("Error code " + native_setup + " when initializing AudioTrack.");
                    if (asScopedParcelState != null) {
                        asScopedParcelState.close();
                        return;
                    }
                    return;
                }
                if (asScopedParcelState != null) {
                    asScopedParcelState.close();
                }
                this.mSessionId = iArr[0];
                this.mState = 1;
            } finally {
            }
        }
    }

    @android.annotation.SystemApi
    public static class TunerConfiguration {
        public static final int CONTENT_ID_NONE = 0;
        private final int mContentId;
        private final int mSyncId;

        public TunerConfiguration(int i, int i2) {
            if (i < 0) {
                throw new java.lang.IllegalArgumentException("contentId " + i + " must be positive or CONTENT_ID_NONE");
            }
            if (i2 < 1) {
                throw new java.lang.IllegalArgumentException("syncId " + i2 + " must be positive");
            }
            this.mContentId = i;
            this.mSyncId = i2;
        }

        public int getContentId() {
            return this.mContentId;
        }

        public int getSyncId() {
            return this.mSyncId;
        }
    }

    public static class Builder {
        private android.media.AudioAttributes mAttributes;
        private int mBufferSizeInBytes;
        private android.content.Context mContext;
        private android.media.AudioFormat mFormat;
        private android.media.AudioTrack.TunerConfiguration mTunerConfiguration;
        private int mEncapsulationMode = 0;
        private int mSessionId = 0;
        private int mMode = 1;
        private int mPerformanceMode = 0;
        private boolean mOffload = false;
        private int mCallRedirectionMode = 0;

        public android.media.AudioTrack.Builder setContext(android.content.Context context) {
            this.mContext = (android.content.Context) java.util.Objects.requireNonNull(context);
            return this;
        }

        public android.media.AudioTrack.Builder setAudioAttributes(android.media.AudioAttributes audioAttributes) throws java.lang.IllegalArgumentException {
            if (audioAttributes == null) {
                throw new java.lang.IllegalArgumentException("Illegal null AudioAttributes argument");
            }
            this.mAttributes = audioAttributes;
            return this;
        }

        public android.media.AudioTrack.Builder setAudioFormat(android.media.AudioFormat audioFormat) throws java.lang.IllegalArgumentException {
            if (audioFormat == null) {
                throw new java.lang.IllegalArgumentException("Illegal null AudioFormat argument");
            }
            this.mFormat = audioFormat;
            return this;
        }

        public android.media.AudioTrack.Builder setBufferSizeInBytes(int i) throws java.lang.IllegalArgumentException {
            if (i <= 0) {
                throw new java.lang.IllegalArgumentException("Invalid buffer size " + i);
            }
            this.mBufferSizeInBytes = i;
            return this;
        }

        public android.media.AudioTrack.Builder setEncapsulationMode(int i) {
            switch (i) {
                case 0:
                case 1:
                case 2:
                    this.mEncapsulationMode = i;
                    return this;
                default:
                    throw new java.lang.IllegalArgumentException("Invalid encapsulation mode " + i);
            }
        }

        public android.media.AudioTrack.Builder setTransferMode(int i) throws java.lang.IllegalArgumentException {
            switch (i) {
                case 0:
                case 1:
                    this.mMode = i;
                    return this;
                default:
                    throw new java.lang.IllegalArgumentException("Invalid transfer mode " + i);
            }
        }

        public android.media.AudioTrack.Builder setSessionId(int i) throws java.lang.IllegalArgumentException {
            if (i != 0 && i < 1) {
                throw new java.lang.IllegalArgumentException("Invalid audio session ID " + i);
            }
            this.mSessionId = i;
            return this;
        }

        public android.media.AudioTrack.Builder setPerformanceMode(int i) {
            switch (i) {
                case 0:
                case 1:
                case 2:
                    this.mPerformanceMode = i;
                    return this;
                default:
                    throw new java.lang.IllegalArgumentException("Invalid performance mode " + i);
            }
        }

        public android.media.AudioTrack.Builder setOffloadedPlayback(boolean z) {
            this.mOffload = z;
            return this;
        }

        @android.annotation.SystemApi
        public android.media.AudioTrack.Builder setTunerConfiguration(android.media.AudioTrack.TunerConfiguration tunerConfiguration) {
            if (tunerConfiguration == null) {
                throw new java.lang.IllegalArgumentException("tunerConfiguration is null");
            }
            this.mTunerConfiguration = tunerConfiguration;
            return this;
        }

        public android.media.AudioTrack.Builder setCallRedirectionMode(int i) {
            switch (i) {
                case 0:
                case 1:
                case 2:
                    this.mCallRedirectionMode = i;
                    return this;
                default:
                    throw new java.lang.IllegalArgumentException("Invalid call redirection mode " + i);
            }
        }

        private android.media.AudioTrack buildCallInjectionTrack() {
            android.media.audiopolicy.AudioMix build = new android.media.audiopolicy.AudioMix.Builder(new android.media.audiopolicy.AudioMixingRule.Builder().addMixRule(2, new android.media.AudioAttributes.Builder().setCapturePreset(7).setForCallRedirection().build()).setTargetMixRole(1).build()).setFormat(this.mFormat).setRouteFlags(2).build();
            android.media.audiopolicy.AudioPolicy build2 = new android.media.audiopolicy.AudioPolicy.Builder(null).addMix(build).build();
            if (android.media.AudioManager.registerAudioPolicyStatic(build2) != 0) {
                throw new java.lang.UnsupportedOperationException("Error: could not register audio policy");
            }
            android.media.AudioTrack createAudioTrackSource = build2.createAudioTrackSource(build);
            if (createAudioTrackSource == null) {
                throw new java.lang.UnsupportedOperationException("Cannot create injection AudioTrack");
            }
            createAudioTrackSource.unregisterAudioPolicyOnRelease(build2);
            return createAudioTrackSource;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Code restructure failed: missing block: B:8:0x0042, code lost:
        
            if (android.media.AudioTrack.shouldEnablePowerSaving(r12.mAttributes, r12.mFormat, r12.mBufferSizeInBytes, r12.mMode) == false) goto L13;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public android.media.AudioTrack build() throws java.lang.UnsupportedOperationException {
            int i = 1;
            if (this.mAttributes == null) {
                this.mAttributes = new android.media.AudioAttributes.Builder().setUsage(1).build();
            }
            switch (this.mPerformanceMode) {
                case 1:
                    this.mAttributes = new android.media.AudioAttributes.Builder(this.mAttributes).replaceFlags((this.mAttributes.getAllFlags() | 256) & (-513)).build();
                    break;
                case 2:
                    this.mAttributes = new android.media.AudioAttributes.Builder(this.mAttributes).replaceFlags((this.mAttributes.getAllFlags() | 512) & (-257)).build();
                    break;
            }
            if (this.mFormat == null) {
                this.mFormat = new android.media.AudioFormat.Builder().setChannelMask(12).setEncoding(1).build();
            }
            if (this.mCallRedirectionMode == 2) {
                return buildCallInjectionTrack();
            }
            if (this.mCallRedirectionMode == 1) {
                this.mAttributes = new android.media.AudioAttributes.Builder(this.mAttributes).setForCallRedirection().build();
            }
            if (this.mOffload) {
                if (this.mPerformanceMode == 1) {
                    throw new java.lang.UnsupportedOperationException("Offload and low latency modes are incompatible");
                }
                if (android.media.AudioSystem.getDirectPlaybackSupport(this.mFormat, this.mAttributes) == 0) {
                    throw new java.lang.UnsupportedOperationException("Cannot create AudioTrack, offload format / attributes not supported");
                }
            }
            if (this.mMode == 1 && this.mBufferSizeInBytes == 0) {
                if (android.media.AudioFormat.isEncodingLinearFrames(this.mFormat.getEncoding())) {
                    try {
                        i = android.media.AudioFormat.getBytesPerSample(this.mFormat.getEncoding());
                    } catch (java.lang.IllegalArgumentException e) {
                    }
                }
                this.mBufferSizeInBytes = this.mFormat.getChannelCount() * i;
            }
            try {
                android.media.AudioTrack audioTrack = new android.media.AudioTrack(this.mContext, this.mAttributes, this.mFormat, this.mBufferSizeInBytes, this.mMode, this.mSessionId, this.mOffload, this.mEncapsulationMode, this.mTunerConfiguration);
                if (audioTrack.getState() == 0) {
                    throw new java.lang.UnsupportedOperationException("Cannot create AudioTrack");
                }
                return audioTrack;
            } catch (java.lang.IllegalArgumentException e2) {
                throw new java.lang.UnsupportedOperationException(e2.getMessage());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unregisterAudioPolicyOnRelease(android.media.audiopolicy.AudioPolicy audioPolicy) {
        this.mAudioPolicy = audioPolicy;
    }

    public void setOffloadDelayPadding(int i, int i2) {
        if (i2 < 0) {
            throw new java.lang.IllegalArgumentException("Illegal negative padding");
        }
        if (i < 0) {
            throw new java.lang.IllegalArgumentException("Illegal negative delay");
        }
        if (!this.mOffloaded) {
            throw new java.lang.IllegalStateException("Illegal use of delay/padding on non-offloaded track");
        }
        if (this.mState == 0) {
            throw new java.lang.IllegalStateException("Uninitialized track");
        }
        this.mOffloadDelayFrames = i;
        this.mOffloadPaddingFrames = i2;
        native_set_delay_padding(i, i2);
    }

    public int getOffloadDelay() {
        if (!this.mOffloaded) {
            throw new java.lang.IllegalStateException("Illegal query of delay on non-offloaded track");
        }
        if (this.mState == 0) {
            throw new java.lang.IllegalStateException("Illegal query of delay on uninitialized track");
        }
        return this.mOffloadDelayFrames;
    }

    public int getOffloadPadding() {
        if (!this.mOffloaded) {
            throw new java.lang.IllegalStateException("Illegal query of padding on non-offloaded track");
        }
        if (this.mState == 0) {
            throw new java.lang.IllegalStateException("Illegal query of padding on uninitialized track");
        }
        return this.mOffloadPaddingFrames;
    }

    public void setOffloadEndOfStream() {
        if (!this.mOffloaded) {
            throw new java.lang.IllegalStateException("EOS not supported on non-offloaded track");
        }
        if (this.mState == 0) {
            throw new java.lang.IllegalStateException("Uninitialized track");
        }
        if (this.mPlayState != 3) {
            throw new java.lang.IllegalStateException("EOS not supported if not playing");
        }
        synchronized (this.mStreamEventCbLock) {
            if (this.mStreamEventCbInfoList.size() == 0) {
                throw new java.lang.IllegalStateException("EOS not supported without StreamEventCallback");
            }
        }
        synchronized (this.mPlayStateLock) {
            native_stop();
            this.mOffloadEosPending = true;
            this.mPlayState = 4;
        }
    }

    public boolean isOffloadedPlayback() {
        return this.mOffloaded;
    }

    @java.lang.Deprecated
    public static boolean isDirectPlaybackSupported(android.media.AudioFormat audioFormat, android.media.AudioAttributes audioAttributes) {
        if (audioFormat == null) {
            throw new java.lang.IllegalArgumentException("Illegal null AudioFormat argument");
        }
        if (audioAttributes == null) {
            throw new java.lang.IllegalArgumentException("Illegal null AudioAttributes argument");
        }
        return native_is_direct_output_supported(audioFormat.getEncoding(), audioFormat.getSampleRate(), audioFormat.getChannelMask(), audioFormat.getChannelIndexMask(), audioAttributes.getContentType(), audioAttributes.getUsage(), audioAttributes.getFlags());
    }

    private static boolean isValidAudioDescriptionMixLevel(float f) {
        return !java.lang.Float.isNaN(f) && f <= MAX_AUDIO_DESCRIPTION_MIX_LEVEL;
    }

    public boolean setAudioDescriptionMixLeveldB(float f) {
        if (isValidAudioDescriptionMixLevel(f)) {
            return native_set_audio_description_mix_level_db(f) == 0;
        }
        throw new java.lang.IllegalArgumentException("level is out of range" + f);
    }

    public float getAudioDescriptionMixLeveldB() {
        float[] fArr = {Float.NEGATIVE_INFINITY};
        try {
            if (native_get_audio_description_mix_level_db(fArr) == 0) {
                if (!java.lang.Float.isNaN(fArr[0])) {
                    return fArr[0];
                }
            }
            return Float.NEGATIVE_INFINITY;
        } catch (java.lang.Exception e) {
            return Float.NEGATIVE_INFINITY;
        }
    }

    private static boolean isValidDualMonoMode(int i) {
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
                return true;
            default:
                return false;
        }
    }

    public boolean setDualMonoMode(int i) {
        if (isValidDualMonoMode(i)) {
            return native_set_dual_mono_mode(i) == 0;
        }
        throw new java.lang.IllegalArgumentException("Invalid Dual Mono mode " + i);
    }

    public int getDualMonoMode() {
        int[] iArr = {0};
        try {
            if (native_get_dual_mono_mode(iArr) == 0) {
                if (isValidDualMonoMode(iArr[0])) {
                    return iArr[0];
                }
            }
            return 0;
        } catch (java.lang.Exception e) {
            return 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean shouldEnablePowerSaving(android.media.AudioAttributes audioAttributes, android.media.AudioFormat audioFormat, int i, int i2) {
        int allFlags = audioAttributes.getAllFlags() & 792;
        if ((audioAttributes == null || (allFlags == 0 && audioAttributes.getUsage() == 1 && (audioAttributes.getContentType() == 0 || audioAttributes.getContentType() == 2 || audioAttributes.getContentType() == 3))) && audioFormat != null && audioFormat.getSampleRate() != 0 && android.media.AudioFormat.isEncodingLinearPcm(audioFormat.getEncoding()) && android.media.AudioFormat.isValidEncoding(audioFormat.getEncoding()) && audioFormat.getChannelCount() >= 1 && i2 == 1) {
            return i == 0 || ((long) i) >= (((((long) audioFormat.getChannelCount()) * 100) * ((long) android.media.AudioFormat.getBytesPerSample(audioFormat.getEncoding()))) * ((long) audioFormat.getSampleRate())) / 1000;
        }
        return false;
    }

    private void audioParamCheck(int i, int i2, int i3, int i4, int i5) {
        if ((i < android.media.AudioFormat.SAMPLE_RATE_HZ_MIN || i > android.media.AudioFormat.SAMPLE_RATE_HZ_MAX) && i != 0) {
            throw new java.lang.IllegalArgumentException(i + "Hz is not a supported sample rate.");
        }
        this.mSampleRate = i;
        if (i4 == 13 && i2 != 12 && android.media.AudioFormat.channelCountFromOutChannelMask(i2) != 8) {
            android.util.Log.w(TAG, "ENCODING_IEC61937 is configured with channel mask as " + i2 + ", which is not 2 or 8 channels");
        }
        this.mChannelConfiguration = i2;
        boolean z = false;
        switch (i2) {
            case 1:
            case 2:
            case 4:
                this.mChannelCount = 1;
                this.mChannelMask = 4;
                break;
            case 3:
            case 12:
                this.mChannelCount = 2;
                this.mChannelMask = 12;
                break;
            default:
                if (i2 == 0 && i3 != 0) {
                    this.mChannelCount = 0;
                    break;
                } else {
                    if (!isMultichannelConfigSupported(i2, i4)) {
                        throw new java.lang.IllegalArgumentException("Unsupported channel mask configuration " + i2 + " for encoding " + i4);
                    }
                    this.mChannelMask = i2;
                    this.mChannelCount = android.media.AudioFormat.channelCountFromOutChannelMask(i2);
                    break;
                }
        }
        this.mChannelIndexMask = i3;
        if (this.mChannelIndexMask != 0) {
            int bitCount = java.lang.Integer.bitCount(i3);
            if (((-16777216) & i3) == 0 && (!android.media.AudioFormat.isEncodingLinearFrames(i4) || bitCount <= android.media.AudioSystem.OUT_CHANNEL_COUNT_MAX)) {
                z = true;
            }
            if (!z) {
                throw new java.lang.IllegalArgumentException("Unsupported channel index mask configuration " + i3 + " for encoding " + i4);
            }
            if (this.mChannelCount == 0) {
                this.mChannelCount = bitCount;
            } else if (this.mChannelCount != bitCount) {
                throw new java.lang.IllegalArgumentException("Channel count must match");
            }
        }
        if (i4 == 1) {
            i4 = 2;
        }
        if (!android.media.AudioFormat.isPublicEncoding(i4)) {
            throw new java.lang.IllegalArgumentException("Unsupported audio encoding.");
        }
        this.mAudioFormat = i4;
        if ((i5 != 1 && i5 != 0) || (i5 != 1 && !android.media.AudioFormat.isEncodingLinearPcm(this.mAudioFormat))) {
            throw new java.lang.IllegalArgumentException("Invalid mode.");
        }
        this.mDataLoadMode = i5;
    }

    private static boolean isMultichannelConfigSupported(int i, int i2) {
        int i3;
        if ((SUPPORTED_OUT_CHANNELS & i) != i) {
            loge("Channel configuration features unsupported channels");
            return false;
        }
        int channelCountFromOutChannelMask = android.media.AudioFormat.channelCountFromOutChannelMask(i);
        try {
            if (android.media.AudioFormat.isEncodingLinearFrames(i2)) {
                i3 = android.media.AudioSystem.OUT_CHANNEL_COUNT_MAX;
            } else {
                i3 = 24;
            }
            if (channelCountFromOutChannelMask > i3) {
                loge("Channel configuration contains too many channels for encoding " + i2 + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START + channelCountFromOutChannelMask + " > " + i3 + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
                return false;
            }
            if ((i & 12) != 12) {
                loge("Front channels must be present in multichannel configurations");
                return false;
            }
            for (java.util.Map.Entry<java.lang.String, java.lang.Integer> entry : CHANNEL_PAIR_MAP.entrySet()) {
                int intValue = entry.getValue().intValue();
                int i4 = i & intValue;
                if (i4 != 0 && i4 != intValue) {
                    loge("Channel pair (" + entry.getKey() + ") cannot be used independently");
                    return false;
                }
            }
            return true;
        } catch (java.lang.IllegalArgumentException e) {
            loge("Unsupported encoding " + e);
            return false;
        }
    }

    private void audioBuffSizeCheck(int i) {
        int i2;
        if (android.media.AudioFormat.isEncodingLinearFrames(this.mAudioFormat)) {
            i2 = this.mChannelCount * android.media.AudioFormat.getBytesPerSample(this.mAudioFormat);
        } else {
            i2 = 1;
        }
        if (i % i2 != 0 || i < 1) {
            throw new java.lang.IllegalArgumentException("Invalid audio buffer size.");
        }
        this.mNativeBufferSizeInBytes = i;
        this.mNativeBufferSizeInFrames = i / i2;
    }

    public void release() {
        synchronized (this.mStreamEventCbLock) {
            endStreamEventHandling();
        }
        try {
            stop();
        } catch (java.lang.IllegalStateException e) {
        }
        if (this.mAudioPolicy != null) {
            android.media.AudioManager.unregisterAudioPolicyAsyncStatic(this.mAudioPolicy);
            this.mAudioPolicy = null;
        }
        baseRelease();
        native_release();
        synchronized (this.mPlayStateLock) {
            this.mState = 0;
            this.mPlayState = 1;
            this.mPlayStateLock.notify();
        }
    }

    protected void finalize() {
        tryToDisableNativeRoutingCallback();
        baseRelease();
        native_finalize();
    }

    public static float getMinVolume() {
        return 0.0f;
    }

    public static float getMaxVolume() {
        return 1.0f;
    }

    public int getSampleRate() {
        return this.mSampleRate;
    }

    public int getPlaybackRate() {
        return native_get_playback_rate();
    }

    public android.media.PlaybackParams getPlaybackParams() {
        return native_get_playback_params();
    }

    public android.media.AudioAttributes getAudioAttributes() {
        if (this.mState == 0 || this.mConfiguredAudioAttributes == null) {
            throw new java.lang.IllegalStateException("track not initialized");
        }
        return this.mConfiguredAudioAttributes;
    }

    public int getAudioFormat() {
        return this.mAudioFormat;
    }

    public int getStreamType() {
        return this.mStreamType;
    }

    public int getChannelConfiguration() {
        return this.mChannelConfiguration;
    }

    public android.media.AudioFormat getFormat() {
        android.media.AudioFormat.Builder encoding = new android.media.AudioFormat.Builder().setSampleRate(this.mSampleRate).setEncoding(this.mAudioFormat);
        if (this.mChannelConfiguration != 0) {
            encoding.setChannelMask(this.mChannelConfiguration);
        }
        if (this.mChannelIndexMask != 0) {
            encoding.setChannelIndexMask(this.mChannelIndexMask);
        }
        return encoding.build();
    }

    public int getChannelCount() {
        return this.mChannelCount;
    }

    public int getState() {
        return this.mState;
    }

    public int getPlayState() {
        synchronized (this.mPlayStateLock) {
            switch (this.mPlayState) {
                case 4:
                    return 3;
                case 5:
                    return 2;
                default:
                    return this.mPlayState;
            }
        }
    }

    public int getBufferSizeInFrames() {
        return native_get_buffer_size_frames();
    }

    public int setBufferSizeInFrames(int i) {
        if (this.mDataLoadMode == 0 || this.mState == 0) {
            return -3;
        }
        if (i < 0) {
            return -2;
        }
        return native_set_buffer_size_frames(i);
    }

    public int getBufferCapacityInFrames() {
        return native_get_buffer_capacity_frames();
    }

    public int setStartThresholdInFrames(int i) {
        if (this.mState != 1) {
            throw new java.lang.IllegalStateException("AudioTrack is not initialized");
        }
        if (this.mDataLoadMode != 1) {
            throw new java.lang.IllegalStateException("AudioTrack must be a streaming track");
        }
        if (i < 1) {
            throw new java.lang.IllegalArgumentException("startThresholdInFrames " + i + " must be positive");
        }
        return native_setStartThresholdInFrames(i);
    }

    public int getStartThresholdInFrames() {
        if (this.mState != 1) {
            throw new java.lang.IllegalStateException("AudioTrack is not initialized");
        }
        if (this.mDataLoadMode != 1) {
            throw new java.lang.IllegalStateException("AudioTrack must be a streaming track");
        }
        return native_getStartThresholdInFrames();
    }

    @java.lang.Deprecated
    protected int getNativeFrameCount() {
        return native_get_buffer_capacity_frames();
    }

    public int getNotificationMarkerPosition() {
        return native_get_marker_pos();
    }

    public int getPositionNotificationPeriod() {
        return native_get_pos_update_period();
    }

    public int getPlaybackHeadPosition() {
        return native_get_position();
    }

    public int getLatency() {
        return native_get_latency();
    }

    public int getUnderrunCount() {
        return native_get_underrun_count();
    }

    public int getPerformanceMode() {
        int native_get_flags = native_get_flags();
        if ((native_get_flags & 4) != 0) {
            return 1;
        }
        if ((native_get_flags & 8) != 0) {
            return 2;
        }
        return 0;
    }

    public static int getNativeOutputSampleRate(int i) {
        return native_get_output_sample_rate(i);
    }

    public static int getMinBufferSize(int i, int i2, int i3) {
        int i4;
        switch (i2) {
            case 2:
            case 4:
                i4 = 1;
                break;
            case 3:
            case 12:
                i4 = 2;
                break;
            default:
                if (!isMultichannelConfigSupported(i2, i3)) {
                    loge("getMinBufferSize(): Invalid channel configuration.");
                    return -2;
                }
                i4 = android.media.AudioFormat.channelCountFromOutChannelMask(i2);
                break;
        }
        if (!android.media.AudioFormat.isPublicEncoding(i3)) {
            loge("getMinBufferSize(): Invalid audio format.");
            return -2;
        }
        if (i < android.media.AudioFormat.SAMPLE_RATE_HZ_MIN || i > android.media.AudioFormat.SAMPLE_RATE_HZ_MAX) {
            loge("getMinBufferSize(): " + i + " Hz is not a supported sample rate.");
            return -2;
        }
        int native_get_min_buff_size = native_get_min_buff_size(i, i4, i3);
        if (native_get_min_buff_size <= 0) {
            loge("getMinBufferSize(): error querying hardware");
            return -1;
        }
        return native_get_min_buff_size;
    }

    public int getAudioSessionId() {
        return this.mSessionId;
    }

    public boolean getTimestamp(android.media.AudioTimestamp audioTimestamp) {
        if (audioTimestamp == null) {
            throw new java.lang.IllegalArgumentException();
        }
        long[] jArr = new long[2];
        if (native_get_timestamp(jArr) != 0) {
            return false;
        }
        audioTimestamp.framePosition = jArr[0];
        audioTimestamp.nanoTime = jArr[1];
        return true;
    }

    public int getTimestampWithStatus(android.media.AudioTimestamp audioTimestamp) {
        if (audioTimestamp == null) {
            throw new java.lang.IllegalArgumentException();
        }
        long[] jArr = new long[2];
        int native_get_timestamp = native_get_timestamp(jArr);
        audioTimestamp.framePosition = jArr[0];
        audioTimestamp.nanoTime = jArr[1];
        return native_get_timestamp;
    }

    public android.os.PersistableBundle getMetrics() {
        return native_getMetrics();
    }

    public void setPlaybackPositionUpdateListener(android.media.AudioTrack.OnPlaybackPositionUpdateListener onPlaybackPositionUpdateListener) {
        setPlaybackPositionUpdateListener(onPlaybackPositionUpdateListener, null);
    }

    public void setPlaybackPositionUpdateListener(android.media.AudioTrack.OnPlaybackPositionUpdateListener onPlaybackPositionUpdateListener, android.os.Handler handler) {
        if (onPlaybackPositionUpdateListener != null) {
            this.mEventHandlerDelegate = new android.media.AudioTrack.NativePositionEventHandlerDelegate(this, onPlaybackPositionUpdateListener, handler);
        } else {
            this.mEventHandlerDelegate = null;
        }
    }

    private static float clampGainOrLevel(float f) {
        if (java.lang.Float.isNaN(f)) {
            throw new java.lang.IllegalArgumentException();
        }
        if (f < 0.0f) {
            return 0.0f;
        }
        if (f > 1.0f) {
            return 1.0f;
        }
        return f;
    }

    @java.lang.Deprecated
    public int setStereoVolume(float f, float f2) {
        if (this.mState == 0) {
            return -3;
        }
        baseSetVolume(f, f2);
        return 0;
    }

    @Override // android.media.PlayerBase
    void playerSetVolume(boolean z, float f, float f2) {
        if (z) {
            f = 0.0f;
        }
        float clampGainOrLevel = clampGainOrLevel(f);
        if (z) {
            f2 = 0.0f;
        }
        native_setVolume(clampGainOrLevel, clampGainOrLevel(f2));
    }

    public int setVolume(float f) {
        return setStereoVolume(f, f);
    }

    @Override // android.media.PlayerBase
    int playerApplyVolumeShaper(android.media.VolumeShaper.Configuration configuration, android.media.VolumeShaper.Operation operation) {
        return native_applyVolumeShaper(configuration, operation);
    }

    @Override // android.media.PlayerBase
    android.media.VolumeShaper.State playerGetVolumeShaperState(int i) {
        return native_getVolumeShaperState(i);
    }

    @Override // android.media.VolumeAutomation
    public android.media.VolumeShaper createVolumeShaper(android.media.VolumeShaper.Configuration configuration) {
        return new android.media.VolumeShaper(configuration, this);
    }

    public int setPlaybackRate(int i) {
        if (this.mState != 1) {
            return -3;
        }
        if (i <= 0) {
            return -2;
        }
        return native_set_playback_rate(i);
    }

    public void setPlaybackParams(android.media.PlaybackParams playbackParams) {
        if (playbackParams == null) {
            throw new java.lang.IllegalArgumentException("params is null");
        }
        native_set_playback_params(playbackParams);
    }

    public int setNotificationMarkerPosition(int i) {
        if (this.mState == 0) {
            return -3;
        }
        return native_set_marker_pos(i);
    }

    public int setPositionNotificationPeriod(int i) {
        if (this.mState == 0) {
            return -3;
        }
        return native_set_pos_update_period(i);
    }

    public int setPlaybackHeadPosition(int i) {
        if (this.mDataLoadMode == 1 || this.mState == 0 || getPlayState() == 3) {
            return -3;
        }
        if (i < 0 || i > this.mNativeBufferSizeInFrames) {
            return -2;
        }
        return native_set_position(i);
    }

    public int setLoopPoints(int i, int i2, int i3) {
        if (this.mDataLoadMode == 1 || this.mState == 0 || getPlayState() == 3) {
            return -3;
        }
        if (i3 != 0 && (i < 0 || i >= this.mNativeBufferSizeInFrames || i >= i2 || i2 > this.mNativeBufferSizeInFrames)) {
            return -2;
        }
        return native_set_loop(i, i2, i3);
    }

    public int setPresentation(android.media.AudioPresentation audioPresentation) {
        if (audioPresentation == null) {
            throw new java.lang.IllegalArgumentException("audio presentation is null");
        }
        return native_setPresentation(audioPresentation.getPresentationId(), audioPresentation.getProgramId());
    }

    @java.lang.Deprecated
    protected void setState(int i) {
        this.mState = i;
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [android.media.AudioTrack$1] */
    public void play() throws java.lang.IllegalStateException {
        if (this.mState != 1) {
            throw new java.lang.IllegalStateException("play() called on uninitialized AudioTrack.");
        }
        final int startDelayMs = getStartDelayMs();
        if (startDelayMs == 0) {
            startImpl();
        } else {
            new java.lang.Thread() { // from class: android.media.AudioTrack.1
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    try {
                        java.lang.Thread.sleep(startDelayMs);
                    } catch (java.lang.InterruptedException e) {
                        e.printStackTrace();
                    }
                    android.media.AudioTrack.this.baseSetStartDelayMs(0);
                    try {
                        android.media.AudioTrack.this.startImpl();
                    } catch (java.lang.IllegalStateException e2) {
                    }
                }
            }.start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startImpl() {
        synchronized (this.mRoutingChangeListeners) {
            if (!this.mEnableSelfRoutingMonitor) {
                this.mEnableSelfRoutingMonitor = testEnableNativeRoutingCallbacksLocked();
            }
        }
        synchronized (this.mPlayStateLock) {
            baseStart(0);
            native_start();
            if (this.mPlayState == 5) {
                this.mPlayState = 4;
            } else {
                this.mPlayState = 3;
                this.mOffloadEosPending = false;
            }
        }
    }

    public void stop() throws java.lang.IllegalStateException {
        if (this.mState != 1) {
            throw new java.lang.IllegalStateException("stop() called on uninitialized AudioTrack.");
        }
        synchronized (this.mPlayStateLock) {
            native_stop();
            baseStop();
            if (this.mOffloaded && this.mPlayState != 5) {
                this.mPlayState = 4;
            } else {
                this.mPlayState = 1;
                this.mOffloadEosPending = false;
                this.mAvSyncHeader = null;
                this.mAvSyncBytesRemaining = 0;
                this.mPlayStateLock.notify();
            }
        }
        tryToDisableNativeRoutingCallback();
    }

    public void pause() throws java.lang.IllegalStateException {
        if (this.mState != 1) {
            throw new java.lang.IllegalStateException("pause() called on uninitialized AudioTrack.");
        }
        synchronized (this.mPlayStateLock) {
            native_pause();
            basePause();
            if (this.mPlayState == 4) {
                this.mPlayState = 5;
            } else {
                this.mPlayState = 2;
            }
        }
    }

    public void flush() {
        if (this.mState == 1) {
            native_flush();
            this.mAvSyncHeader = null;
            this.mAvSyncBytesRemaining = 0;
        }
    }

    public int write(byte[] bArr, int i, int i2) {
        return write(bArr, i, i2, 0);
    }

    public int write(byte[] bArr, int i, int i2, int i3) {
        int i4;
        if (this.mState == 0 || this.mAudioFormat == 4) {
            return -3;
        }
        if (i3 != 0 && i3 != 1) {
            android.util.Log.e(TAG, "AudioTrack.write() called with invalid blocking mode");
            return -2;
        }
        if (bArr == null || i < 0 || i2 < 0 || (i4 = i + i2) < 0 || i4 > bArr.length) {
            return -2;
        }
        if (!blockUntilOffloadDrain(i3)) {
            return 0;
        }
        int native_write_byte = native_write_byte(bArr, i, i2, this.mAudioFormat, i3 == 0);
        if (this.mDataLoadMode == 0 && this.mState == 2 && native_write_byte > 0) {
            this.mState = 1;
        }
        return native_write_byte;
    }

    public int write(short[] sArr, int i, int i2) {
        return write(sArr, i, i2, 0);
    }

    public int write(short[] sArr, int i, int i2, int i3) {
        int i4;
        if (this.mState == 0 || this.mAudioFormat == 4 || this.mAudioFormat > 20) {
            return -3;
        }
        if (i3 != 0 && i3 != 1) {
            android.util.Log.e(TAG, "AudioTrack.write() called with invalid blocking mode");
            return -2;
        }
        if (sArr == null || i < 0 || i2 < 0 || (i4 = i + i2) < 0 || i4 > sArr.length) {
            return -2;
        }
        if (!blockUntilOffloadDrain(i3)) {
            return 0;
        }
        int native_write_short = native_write_short(sArr, i, i2, this.mAudioFormat, i3 == 0);
        if (this.mDataLoadMode == 0 && this.mState == 2 && native_write_short > 0) {
            this.mState = 1;
        }
        return native_write_short;
    }

    public int write(float[] fArr, int i, int i2, int i3) {
        int i4;
        if (this.mState == 0) {
            android.util.Log.e(TAG, "AudioTrack.write() called in invalid state STATE_UNINITIALIZED");
            return -3;
        }
        if (this.mAudioFormat != 4) {
            android.util.Log.e(TAG, "AudioTrack.write(float[] ...) requires format ENCODING_PCM_FLOAT");
            return -3;
        }
        if (i3 != 0 && i3 != 1) {
            android.util.Log.e(TAG, "AudioTrack.write() called with invalid blocking mode");
            return -2;
        }
        if (fArr == null || i < 0 || i2 < 0 || (i4 = i + i2) < 0 || i4 > fArr.length) {
            android.util.Log.e(TAG, "AudioTrack.write() called with invalid array, offset, or size");
            return -2;
        }
        if (!blockUntilOffloadDrain(i3)) {
            return 0;
        }
        int native_write_float = native_write_float(fArr, i, i2, this.mAudioFormat, i3 == 0);
        if (this.mDataLoadMode == 0 && this.mState == 2 && native_write_float > 0) {
            this.mState = 1;
        }
        return native_write_float;
    }

    public int write(java.nio.ByteBuffer byteBuffer, int i, int i2) {
        int native_write_byte;
        if (this.mState == 0) {
            android.util.Log.e(TAG, "AudioTrack.write() called in invalid state STATE_UNINITIALIZED");
            return -3;
        }
        if (i2 != 0 && i2 != 1) {
            android.util.Log.e(TAG, "AudioTrack.write() called with invalid blocking mode");
            return -2;
        }
        if (byteBuffer == null || i < 0 || i > byteBuffer.remaining()) {
            android.util.Log.e(TAG, "AudioTrack.write() called with invalid size (" + i + ") value");
            return -2;
        }
        if (!blockUntilOffloadDrain(i2)) {
            return 0;
        }
        if (byteBuffer.isDirect()) {
            native_write_byte = native_write_native_bytes(byteBuffer, byteBuffer.position(), i, this.mAudioFormat, i2 == 0);
        } else {
            native_write_byte = native_write_byte(java.nio.NioUtils.unsafeArray(byteBuffer), java.nio.NioUtils.unsafeArrayOffset(byteBuffer) + byteBuffer.position(), i, this.mAudioFormat, i2 == 0);
        }
        if (this.mDataLoadMode == 0 && this.mState == 2 && native_write_byte > 0) {
            this.mState = 1;
        }
        if (native_write_byte > 0) {
            byteBuffer.position(byteBuffer.position() + native_write_byte);
        }
        return native_write_byte;
    }

    public int write(java.nio.ByteBuffer byteBuffer, int i, int i2, long j) {
        if (this.mState == 0) {
            android.util.Log.e(TAG, "AudioTrack.write() called in invalid state STATE_UNINITIALIZED");
            return -3;
        }
        if (i2 != 0 && i2 != 1) {
            android.util.Log.e(TAG, "AudioTrack.write() called with invalid blocking mode");
            return -2;
        }
        if (this.mDataLoadMode != 1) {
            android.util.Log.e(TAG, "AudioTrack.write() with timestamp called for non-streaming mode track");
            return -3;
        }
        if ((this.mAttributes.getFlags() & 16) == 0) {
            android.util.Log.d(TAG, "AudioTrack.write() called on a regular AudioTrack. Ignoring pts...");
            return write(byteBuffer, i, i2);
        }
        if (byteBuffer == null || i < 0 || i > byteBuffer.remaining()) {
            android.util.Log.e(TAG, "AudioTrack.write() called with invalid size (" + i + ") value");
            return -2;
        }
        if (!blockUntilOffloadDrain(i2)) {
            return 0;
        }
        if (this.mAvSyncHeader == null) {
            this.mAvSyncHeader = java.nio.ByteBuffer.allocate(this.mOffset);
            this.mAvSyncHeader.order(java.nio.ByteOrder.BIG_ENDIAN);
            this.mAvSyncHeader.putInt(1431633922);
        }
        if (this.mAvSyncBytesRemaining == 0) {
            this.mAvSyncHeader.putInt(4, i);
            this.mAvSyncHeader.putLong(8, j);
            this.mAvSyncHeader.putInt(16, this.mOffset);
            this.mAvSyncHeader.position(0);
            this.mAvSyncBytesRemaining = i;
        }
        if (this.mAvSyncHeader.remaining() != 0) {
            int write = write(this.mAvSyncHeader, this.mAvSyncHeader.remaining(), i2);
            if (write < 0) {
                android.util.Log.e(TAG, "AudioTrack.write() could not write timestamp header!");
                this.mAvSyncHeader = null;
                this.mAvSyncBytesRemaining = 0;
                return write;
            }
            if (this.mAvSyncHeader.remaining() > 0) {
                android.util.Log.v(TAG, "AudioTrack.write() partial timestamp header written.");
                return 0;
            }
        }
        int write2 = write(byteBuffer, java.lang.Math.min(this.mAvSyncBytesRemaining, i), i2);
        if (write2 < 0) {
            android.util.Log.e(TAG, "AudioTrack.write() could not write audio data!");
            this.mAvSyncHeader = null;
            this.mAvSyncBytesRemaining = 0;
            return write2;
        }
        this.mAvSyncBytesRemaining -= write2;
        return write2;
    }

    public int reloadStaticData() {
        if (this.mDataLoadMode == 1 || this.mState != 1) {
            return -3;
        }
        return native_reload_static();
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0014, code lost:
    
        return false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean blockUntilOffloadDrain(int i) {
        synchronized (this.mPlayStateLock) {
            while (true) {
                if (this.mPlayState != 4 && this.mPlayState != 5) {
                    return true;
                }
                try {
                    this.mPlayStateLock.wait();
                } catch (java.lang.InterruptedException e) {
                }
            }
        }
    }

    public int attachAuxEffect(int i) {
        if (this.mState == 0) {
            return -3;
        }
        return native_attachAuxEffect(i);
    }

    public int setAuxEffectSendLevel(float f) {
        if (this.mState == 0) {
            return -3;
        }
        return baseSetAuxEffectSendLevel(f);
    }

    @Override // android.media.PlayerBase
    int playerSetAuxEffectSendLevel(boolean z, float f) {
        if (z) {
            f = 0.0f;
        }
        return native_setAuxEffectSendLevel(clampGainOrLevel(f)) == 0 ? 0 : -1;
    }

    @Override // android.media.AudioRouting
    public boolean setPreferredDevice(android.media.AudioDeviceInfo audioDeviceInfo) {
        if (audioDeviceInfo != null && !audioDeviceInfo.isSink()) {
            return false;
        }
        boolean native_setOutputDevice = native_setOutputDevice(audioDeviceInfo != null ? audioDeviceInfo.getId() : 0);
        if (native_setOutputDevice) {
            synchronized (this) {
                this.mPreferredDevice = audioDeviceInfo;
            }
        }
        return native_setOutputDevice;
    }

    @Override // android.media.AudioRouting
    public android.media.AudioDeviceInfo getPreferredDevice() {
        android.media.AudioDeviceInfo audioDeviceInfo;
        synchronized (this) {
            audioDeviceInfo = this.mPreferredDevice;
        }
        return audioDeviceInfo;
    }

    @Override // android.media.AudioRouting
    public android.media.AudioDeviceInfo getRoutedDevice() {
        int native_getRoutedDeviceId = native_getRoutedDeviceId();
        if (native_getRoutedDeviceId == 0) {
            return null;
        }
        return android.media.AudioManager.getDeviceForPortId(native_getRoutedDeviceId, 2);
    }

    private void tryToDisableNativeRoutingCallback() {
        synchronized (this.mRoutingChangeListeners) {
            if (this.mEnableSelfRoutingMonitor) {
                this.mEnableSelfRoutingMonitor = false;
                testDisableNativeRoutingCallbacksLocked();
            }
        }
    }

    private boolean testEnableNativeRoutingCallbacksLocked() {
        if (this.mRoutingChangeListeners.size() == 0 && !this.mEnableSelfRoutingMonitor) {
            try {
                native_enableDeviceCallback();
                return true;
            } catch (java.lang.IllegalStateException e) {
                if (android.util.Log.isLoggable(TAG, 3)) {
                    android.util.Log.d(TAG, "testEnableNativeRoutingCallbacks failed", e);
                    return false;
                }
                return false;
            }
        }
        return false;
    }

    private void testDisableNativeRoutingCallbacksLocked() {
        if (this.mRoutingChangeListeners.size() == 0 && !this.mEnableSelfRoutingMonitor) {
            try {
                native_disableDeviceCallback();
            } catch (java.lang.IllegalStateException e) {
            }
        }
    }

    @Override // android.media.AudioRouting
    public void addOnRoutingChangedListener(android.media.AudioRouting.OnRoutingChangedListener onRoutingChangedListener, android.os.Handler handler) {
        synchronized (this.mRoutingChangeListeners) {
            if (onRoutingChangedListener != null) {
                if (!this.mRoutingChangeListeners.containsKey(onRoutingChangedListener)) {
                    this.mEnableSelfRoutingMonitor = testEnableNativeRoutingCallbacksLocked();
                    android.util.ArrayMap<android.media.AudioRouting.OnRoutingChangedListener, android.media.NativeRoutingEventHandlerDelegate> arrayMap = this.mRoutingChangeListeners;
                    if (handler == null) {
                        handler = new android.os.Handler(this.mInitializationLooper);
                    }
                    arrayMap.put(onRoutingChangedListener, new android.media.NativeRoutingEventHandlerDelegate(this, onRoutingChangedListener, handler));
                }
            }
        }
    }

    @Override // android.media.AudioRouting
    public void removeOnRoutingChangedListener(android.media.AudioRouting.OnRoutingChangedListener onRoutingChangedListener) {
        synchronized (this.mRoutingChangeListeners) {
            if (this.mRoutingChangeListeners.containsKey(onRoutingChangedListener)) {
                this.mRoutingChangeListeners.remove(onRoutingChangedListener);
            }
            testDisableNativeRoutingCallbacksLocked();
        }
    }

    @java.lang.Deprecated
    public interface OnRoutingChangedListener extends android.media.AudioRouting.OnRoutingChangedListener {
        void onRoutingChanged(android.media.AudioTrack audioTrack);

        @Override // android.media.AudioRouting.OnRoutingChangedListener
        default void onRoutingChanged(android.media.AudioRouting audioRouting) {
            if (audioRouting instanceof android.media.AudioTrack) {
                onRoutingChanged((android.media.AudioTrack) audioRouting);
            }
        }
    }

    @java.lang.Deprecated
    public void addOnRoutingChangedListener(android.media.AudioTrack.OnRoutingChangedListener onRoutingChangedListener, android.os.Handler handler) {
        addOnRoutingChangedListener((android.media.AudioRouting.OnRoutingChangedListener) onRoutingChangedListener, handler);
    }

    @java.lang.Deprecated
    public void removeOnRoutingChangedListener(android.media.AudioTrack.OnRoutingChangedListener onRoutingChangedListener) {
        removeOnRoutingChangedListener((android.media.AudioRouting.OnRoutingChangedListener) onRoutingChangedListener);
    }

    private void broadcastRoutingChange() {
        android.media.AudioManager.resetAudioPortGeneration();
        baseUpdateDeviceId(getRoutedDevice());
        synchronized (this.mRoutingChangeListeners) {
            java.util.Iterator<android.media.NativeRoutingEventHandlerDelegate> it = this.mRoutingChangeListeners.values().iterator();
            while (it.hasNext()) {
                it.next().notifyClient();
            }
        }
    }

    public void addOnCodecFormatChangedListener(java.util.concurrent.Executor executor, final android.media.AudioTrack.OnCodecFormatChangedListener onCodecFormatChangedListener) {
        this.mCodecFormatChangedListeners.add(onCodecFormatChangedListener, executor, new android.media.Utils.ListenerList.Listener() { // from class: android.media.AudioTrack$$ExternalSyntheticLambda0
            @Override // android.media.Utils.ListenerList.Listener
            public final void onEvent(int i, java.lang.Object obj) {
                android.media.AudioTrack.this.lambda$addOnCodecFormatChangedListener$0(onCodecFormatChangedListener, i, (android.media.AudioMetadataReadMap) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addOnCodecFormatChangedListener$0(android.media.AudioTrack.OnCodecFormatChangedListener onCodecFormatChangedListener, int i, android.media.AudioMetadataReadMap audioMetadataReadMap) {
        onCodecFormatChangedListener.onCodecFormatChanged(this, audioMetadataReadMap);
    }

    public void removeOnCodecFormatChangedListener(android.media.AudioTrack.OnCodecFormatChangedListener onCodecFormatChangedListener) {
        this.mCodecFormatChangedListeners.remove(onCodecFormatChangedListener);
    }

    public static abstract class StreamEventCallback {
        public void onTearDown(android.media.AudioTrack audioTrack) {
        }

        public void onPresentationEnded(android.media.AudioTrack audioTrack) {
        }

        public void onDataRequest(android.media.AudioTrack audioTrack, int i) {
        }
    }

    public void registerStreamEventCallback(java.util.concurrent.Executor executor, android.media.AudioTrack.StreamEventCallback streamEventCallback) {
        if (streamEventCallback == null) {
            throw new java.lang.IllegalArgumentException("Illegal null StreamEventCallback");
        }
        if (!this.mOffloaded) {
            throw new java.lang.IllegalStateException("Cannot register StreamEventCallback on non-offloaded AudioTrack");
        }
        if (executor == null) {
            throw new java.lang.IllegalArgumentException("Illegal null Executor for the StreamEventCallback");
        }
        synchronized (this.mStreamEventCbLock) {
            java.util.Iterator<android.media.AudioTrack.StreamEventCbInfo> it = this.mStreamEventCbInfoList.iterator();
            while (it.hasNext()) {
                if (it.next().mStreamEventCb == streamEventCallback) {
                    throw new java.lang.IllegalArgumentException("StreamEventCallback already registered");
                }
            }
            beginStreamEventHandling();
            this.mStreamEventCbInfoList.add(new android.media.AudioTrack.StreamEventCbInfo(executor, streamEventCallback));
        }
    }

    public void unregisterStreamEventCallback(android.media.AudioTrack.StreamEventCallback streamEventCallback) {
        if (streamEventCallback == null) {
            throw new java.lang.IllegalArgumentException("Illegal null StreamEventCallback");
        }
        if (!this.mOffloaded) {
            throw new java.lang.IllegalStateException("No StreamEventCallback on non-offloaded AudioTrack");
        }
        synchronized (this.mStreamEventCbLock) {
            java.util.Iterator<android.media.AudioTrack.StreamEventCbInfo> it = this.mStreamEventCbInfoList.iterator();
            while (it.hasNext()) {
                android.media.AudioTrack.StreamEventCbInfo next = it.next();
                if (next.mStreamEventCb == streamEventCallback) {
                    this.mStreamEventCbInfoList.remove(next);
                    if (this.mStreamEventCbInfoList.size() == 0) {
                        endStreamEventHandling();
                    }
                }
            }
            throw new java.lang.IllegalArgumentException("StreamEventCallback was not registered");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class StreamEventCbInfo {
        final android.media.AudioTrack.StreamEventCallback mStreamEventCb;
        final java.util.concurrent.Executor mStreamEventExec;

        StreamEventCbInfo(java.util.concurrent.Executor executor, android.media.AudioTrack.StreamEventCallback streamEventCallback) {
            this.mStreamEventExec = executor;
            this.mStreamEventCb = streamEventCallback;
        }
    }

    void handleStreamEventFromNative(int i, int i2) {
        if (this.mStreamEventHandler == null) {
        }
        switch (i) {
            case 6:
                this.mStreamEventHandler.sendMessage(this.mStreamEventHandler.obtainMessage(6));
                break;
            case 7:
                this.mStreamEventHandler.sendMessage(this.mStreamEventHandler.obtainMessage(7));
                break;
            case 9:
                this.mStreamEventHandler.removeMessages(9);
                this.mStreamEventHandler.sendMessage(this.mStreamEventHandler.obtainMessage(9, i2, 0));
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class StreamEventHandler extends android.os.Handler {
        StreamEventHandler(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(final android.os.Message message) {
            synchronized (android.media.AudioTrack.this.mStreamEventCbLock) {
                if (message.what == 7) {
                    synchronized (android.media.AudioTrack.this.mPlayStateLock) {
                        if (android.media.AudioTrack.this.mPlayState == 4) {
                            if (android.media.AudioTrack.this.mOffloadEosPending) {
                                android.media.AudioTrack.this.native_start();
                                android.media.AudioTrack.this.mPlayState = 3;
                            } else {
                                android.media.AudioTrack.this.mAvSyncHeader = null;
                                android.media.AudioTrack.this.mAvSyncBytesRemaining = 0;
                                android.media.AudioTrack.this.mPlayState = 1;
                            }
                            android.media.AudioTrack.this.mOffloadEosPending = false;
                            android.media.AudioTrack.this.mPlayStateLock.notify();
                        }
                    }
                }
                if (android.media.AudioTrack.this.mStreamEventCbInfoList.size() == 0) {
                    return;
                }
                java.util.LinkedList linkedList = new java.util.LinkedList(android.media.AudioTrack.this.mStreamEventCbInfoList);
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    java.util.Iterator it = linkedList.iterator();
                    while (it.hasNext()) {
                        final android.media.AudioTrack.StreamEventCbInfo streamEventCbInfo = (android.media.AudioTrack.StreamEventCbInfo) it.next();
                        switch (message.what) {
                            case 6:
                                streamEventCbInfo.mStreamEventExec.execute(new java.lang.Runnable() { // from class: android.media.AudioTrack$StreamEventHandler$$ExternalSyntheticLambda1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        android.media.AudioTrack.StreamEventHandler.this.lambda$handleMessage$1(streamEventCbInfo);
                                    }
                                });
                                break;
                            case 7:
                                streamEventCbInfo.mStreamEventExec.execute(new java.lang.Runnable() { // from class: android.media.AudioTrack$StreamEventHandler$$ExternalSyntheticLambda2
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        android.media.AudioTrack.StreamEventHandler.this.lambda$handleMessage$2(streamEventCbInfo);
                                    }
                                });
                                break;
                            case 9:
                                streamEventCbInfo.mStreamEventExec.execute(new java.lang.Runnable() { // from class: android.media.AudioTrack$StreamEventHandler$$ExternalSyntheticLambda0
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        android.media.AudioTrack.StreamEventHandler.this.lambda$handleMessage$0(streamEventCbInfo, message);
                                    }
                                });
                                break;
                        }
                    }
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleMessage$0(android.media.AudioTrack.StreamEventCbInfo streamEventCbInfo, android.os.Message message) {
            streamEventCbInfo.mStreamEventCb.onDataRequest(android.media.AudioTrack.this, message.arg1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleMessage$1(android.media.AudioTrack.StreamEventCbInfo streamEventCbInfo) {
            streamEventCbInfo.mStreamEventCb.onTearDown(android.media.AudioTrack.this);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleMessage$2(android.media.AudioTrack.StreamEventCbInfo streamEventCbInfo) {
            streamEventCbInfo.mStreamEventCb.onPresentationEnded(android.media.AudioTrack.this);
        }
    }

    private void beginStreamEventHandling() {
        if (this.mStreamEventHandlerThread == null) {
            this.mStreamEventHandlerThread = new android.os.HandlerThread("android.media.AudioTrack.StreamEvent");
            this.mStreamEventHandlerThread.start();
            android.os.Looper looper = this.mStreamEventHandlerThread.getLooper();
            if (looper != null) {
                this.mStreamEventHandler = new android.media.AudioTrack.StreamEventHandler(looper);
            }
        }
    }

    private void endStreamEventHandling() {
        if (this.mStreamEventHandlerThread != null) {
            this.mStreamEventHandlerThread.quit();
            this.mStreamEventHandlerThread = null;
        }
    }

    public void setLogSessionId(android.media.metrics.LogSessionId logSessionId) {
        java.util.Objects.requireNonNull(logSessionId);
        if (this.mState == 0) {
            throw new java.lang.IllegalStateException("track not initialized");
        }
        native_setLogSessionId(logSessionId.getStringId());
        this.mLogSessionId = logSessionId;
    }

    public android.media.metrics.LogSessionId getLogSessionId() {
        return this.mLogSessionId;
    }

    private class NativePositionEventHandlerDelegate {
        private final android.os.Handler mHandler;

        NativePositionEventHandlerDelegate(final android.media.AudioTrack audioTrack, final android.media.AudioTrack.OnPlaybackPositionUpdateListener onPlaybackPositionUpdateListener, android.os.Handler handler) {
            android.os.Looper looper;
            if (handler != null) {
                looper = handler.getLooper();
            } else {
                looper = android.media.AudioTrack.this.mInitializationLooper;
            }
            if (looper != null) {
                this.mHandler = new android.os.Handler(looper) { // from class: android.media.AudioTrack.NativePositionEventHandlerDelegate.1
                    @Override // android.os.Handler
                    public void handleMessage(android.os.Message message) {
                        if (audioTrack == null) {
                        }
                        switch (message.what) {
                            case 3:
                                if (onPlaybackPositionUpdateListener != null) {
                                    onPlaybackPositionUpdateListener.onMarkerReached(audioTrack);
                                    break;
                                }
                                break;
                            case 4:
                                if (onPlaybackPositionUpdateListener != null) {
                                    onPlaybackPositionUpdateListener.onPeriodicNotification(audioTrack);
                                    break;
                                }
                                break;
                            default:
                                android.media.AudioTrack.loge("Unknown native event type: " + message.what);
                                break;
                        }
                    }
                };
            } else {
                this.mHandler = null;
            }
        }

        android.os.Handler getHandler() {
            return this.mHandler;
        }
    }

    @Override // android.media.PlayerBase
    void playerStart() {
        play();
    }

    @Override // android.media.PlayerBase
    void playerPause() {
        pause();
    }

    @Override // android.media.PlayerBase
    void playerStop() {
        stop();
    }

    private static void postEventFromNative(java.lang.Object obj, int i, int i2, int i3, java.lang.Object obj2) {
        android.os.Handler handler;
        android.media.AudioTrack audioTrack = (android.media.AudioTrack) ((java.lang.ref.WeakReference) obj).get();
        if (audioTrack == null) {
            return;
        }
        if (i == 1000) {
            audioTrack.broadcastRoutingChange();
            return;
        }
        if (i == 100) {
            java.nio.ByteBuffer byteBuffer = (java.nio.ByteBuffer) obj2;
            byteBuffer.order(java.nio.ByteOrder.nativeOrder());
            byteBuffer.rewind();
            android.media.AudioMetadata.BaseMap fromByteBuffer = android.media.AudioMetadata.fromByteBuffer(byteBuffer);
            if (fromByteBuffer == null) {
                android.util.Log.e(TAG, "Unable to get audio metadata from byte buffer");
                return;
            } else {
                audioTrack.mCodecFormatChangedListeners.notify(0, fromByteBuffer);
                return;
            }
        }
        if (i == 9 || i == 6 || i == 7) {
            audioTrack.handleStreamEventFromNative(i, i2);
            return;
        }
        android.media.AudioTrack.NativePositionEventHandlerDelegate nativePositionEventHandlerDelegate = audioTrack.mEventHandlerDelegate;
        if (nativePositionEventHandlerDelegate != null && (handler = nativePositionEventHandlerDelegate.getHandler()) != null) {
            handler.sendMessage(handler.obtainMessage(i, i2, i3, obj2));
        }
    }

    private static void logd(java.lang.String str) {
        android.util.Log.d(TAG, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void loge(java.lang.String str) {
        android.util.Log.e(TAG, str);
    }

    public static final class MetricsConstants {
        public static final java.lang.String ATTRIBUTES = "android.media.audiotrack.attributes";

        @java.lang.Deprecated
        public static final java.lang.String CHANNELMASK = "android.media.audiorecord.channelmask";
        public static final java.lang.String CHANNEL_MASK = "android.media.audiotrack.channelMask";
        public static final java.lang.String CONTENTTYPE = "android.media.audiotrack.type";
        public static final java.lang.String ENCODING = "android.media.audiotrack.encoding";
        public static final java.lang.String FRAME_COUNT = "android.media.audiotrack.frameCount";
        private static final java.lang.String MM_PREFIX = "android.media.audiotrack.";
        public static final java.lang.String PORT_ID = "android.media.audiotrack.portId";

        @java.lang.Deprecated
        public static final java.lang.String SAMPLERATE = "android.media.audiorecord.samplerate";
        public static final java.lang.String SAMPLE_RATE = "android.media.audiotrack.sampleRate";
        public static final java.lang.String STREAMTYPE = "android.media.audiotrack.streamtype";
        public static final java.lang.String USAGE = "android.media.audiotrack.usage";

        private MetricsConstants() {
        }
    }
}
