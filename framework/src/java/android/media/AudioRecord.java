package android.media;

/* loaded from: classes2.dex */
public class AudioRecord implements android.media.AudioRouting, android.media.MicrophoneDirection, android.media.AudioRecordingMonitor, android.media.AudioRecordingMonitorClient {
    private static final int AUDIORECORD_ERROR_SETUP_INVALIDCHANNELMASK = -17;
    private static final int AUDIORECORD_ERROR_SETUP_INVALIDFORMAT = -18;
    private static final int AUDIORECORD_ERROR_SETUP_INVALIDSOURCE = -19;
    private static final int AUDIORECORD_ERROR_SETUP_NATIVEINITFAILED = -20;
    private static final int AUDIORECORD_ERROR_SETUP_ZEROFRAMECOUNT = -16;
    public static final int ERROR = -1;
    public static final int ERROR_BAD_VALUE = -2;
    public static final int ERROR_DEAD_OBJECT = -6;
    public static final int ERROR_INVALID_OPERATION = -3;
    private static final long MAX_SHARED_AUDIO_HISTORY_MS = 5000;
    private static final int NATIVE_EVENT_MARKER = 2;
    private static final int NATIVE_EVENT_NEW_POS = 3;
    public static final int READ_BLOCKING = 0;
    public static final int READ_NON_BLOCKING = 1;
    public static final int RECORDSTATE_RECORDING = 3;
    public static final int RECORDSTATE_STOPPED = 1;
    public static final int STATE_INITIALIZED = 1;
    public static final int STATE_UNINITIALIZED = 0;
    public static final java.lang.String SUBMIX_FIXED_VOLUME = "fixedVolume";
    public static final int SUCCESS = 0;
    private static final java.lang.String TAG = "android.media.AudioRecord";
    private android.media.AudioAttributes mAudioAttributes;
    private android.media.audiopolicy.AudioPolicy mAudioCapturePolicy;
    private int mAudioFormat;
    private int mChannelCount;
    private int mChannelIndexMask;
    private int mChannelMask;
    private android.media.AudioRecord.NativeEventHandler mEventHandler;
    private int mHalInputFlags;
    private final android.os.IBinder mICallBack;
    private android.os.Looper mInitializationLooper;
    private boolean mIsSubmixFullVolume;
    private android.media.metrics.LogSessionId mLogSessionId;
    private long mNativeAudioRecordHandle;
    private int mNativeBufferSizeInBytes;
    private long mNativeJNIDataHandle;
    private android.media.AudioRecord.OnRecordPositionUpdateListener mPositionListener;
    private final java.lang.Object mPositionListenerLock;
    private android.media.AudioDeviceInfo mPreferredDevice;
    private int mRecordSource;
    android.media.AudioRecordingMonitorImpl mRecordingInfoImpl;
    private int mRecordingState;
    private final java.lang.Object mRecordingStateLock;
    private android.util.ArrayMap<android.media.AudioRouting.OnRoutingChangedListener, android.media.NativeRoutingEventHandlerDelegate> mRoutingChangeListeners;
    private int mSampleRate;
    private int mSessionId;
    private int mState;

    public interface OnRecordPositionUpdateListener {
        void onMarkerReached(android.media.AudioRecord audioRecord);

        void onPeriodicNotification(android.media.AudioRecord audioRecord);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ReadMode {
    }

    private final native void native_disableDeviceCallback();

    private final native void native_enableDeviceCallback();

    private native void native_finalize();

    private native android.os.PersistableBundle native_getMetrics();

    private native int native_getPortId();

    private final native int native_getRoutedDeviceId();

    private final native int native_get_active_microphones(java.util.ArrayList<android.media.MicrophoneInfo> arrayList);

    private final native int native_get_buffer_size_in_frames();

    private final native int native_get_marker_pos();

    private static final native int native_get_min_buff_size(int i, int i2, int i3);

    private final native int native_get_pos_update_period();

    private final native int native_get_timestamp(android.media.AudioTimestamp audioTimestamp, int i);

    private final native int native_read_in_byte_array(byte[] bArr, int i, int i2, boolean z);

    private final native int native_read_in_direct_buffer(java.lang.Object obj, int i, boolean z);

    private final native int native_read_in_float_array(float[] fArr, int i, int i2, boolean z);

    private final native int native_read_in_short_array(short[] sArr, int i, int i2, boolean z);

    private final native boolean native_setInputDevice(int i);

    private native void native_setLogSessionId(java.lang.String str);

    private final native int native_set_marker_pos(int i);

    private final native int native_set_pos_update_period(int i);

    private native int native_set_preferred_microphone_direction(int i);

    private native int native_set_preferred_microphone_field_dimension(float f);

    private native int native_setup(java.lang.Object obj, java.lang.Object obj2, int[] iArr, int i, int i2, int i3, int i4, int[] iArr2, android.os.Parcel parcel, long j, int i5, int i6);

    private native int native_shareAudioHistory(java.lang.String str, long j);

    private final native int native_start(int i, int i2);

    private final native void native_stop();

    public final native void native_release();

    public AudioRecord(int i, int i2, int i3, int i4, int i5) throws java.lang.IllegalArgumentException {
        this(new android.media.AudioAttributes.Builder().setInternalCapturePreset(i).build(), new android.media.AudioFormat.Builder().setChannelMask(getChannelMaskFromLegacyConfig(i3, true)).setEncoding(i4).setSampleRate(i2).build(), i5, 0);
    }

    @android.annotation.SystemApi
    public AudioRecord(android.media.AudioAttributes audioAttributes, android.media.AudioFormat audioFormat, int i, int i2) throws java.lang.IllegalArgumentException {
        this(audioAttributes, audioFormat, i, i2, android.app.ActivityThread.currentApplication(), 0, 0);
    }

    private AudioRecord(android.media.AudioAttributes audioAttributes, android.media.AudioFormat audioFormat, int i, int i2, android.content.Context context, int i3, int i4) throws java.lang.IllegalArgumentException {
        int i5;
        android.media.AudioAttributes audioAttributes2 = audioAttributes;
        this.mState = 0;
        this.mRecordingState = 1;
        this.mRecordingStateLock = new java.lang.Object();
        this.mPositionListener = null;
        this.mPositionListenerLock = new java.lang.Object();
        this.mEventHandler = null;
        this.mInitializationLooper = null;
        this.mNativeBufferSizeInBytes = 0;
        this.mSessionId = 0;
        this.mHalInputFlags = 0;
        this.mIsSubmixFullVolume = false;
        this.mLogSessionId = android.media.metrics.LogSessionId.LOG_SESSION_ID_NONE;
        this.mICallBack = new android.os.Binder();
        this.mRoutingChangeListeners = new android.util.ArrayMap<>();
        this.mPreferredDevice = null;
        this.mRecordingInfoImpl = new android.media.AudioRecordingMonitorImpl(this);
        this.mRecordingState = 1;
        this.mHalInputFlags = i4;
        if (audioAttributes2 == null) {
            throw new java.lang.IllegalArgumentException("Illegal null AudioAttributes");
        }
        if (audioFormat == null) {
            throw new java.lang.IllegalArgumentException("Illegal null AudioFormat");
        }
        android.os.Looper myLooper = android.os.Looper.myLooper();
        this.mInitializationLooper = myLooper;
        if (myLooper == null) {
            this.mInitializationLooper = android.os.Looper.getMainLooper();
        }
        if (audioAttributes.getCapturePreset() == 8) {
            android.media.AudioAttributes.Builder builder = new android.media.AudioAttributes.Builder(audioAttributes2);
            java.util.HashSet<java.lang.String> hashSet = new java.util.HashSet<>();
            for (java.lang.String str : audioAttributes.getTags()) {
                if (str.equalsIgnoreCase(SUBMIX_FIXED_VOLUME)) {
                    this.mIsSubmixFullVolume = true;
                    android.util.Log.v(TAG, "Will record from REMOTE_SUBMIX at full fixed volume");
                } else {
                    hashSet.add(str);
                }
            }
            builder.replaceTags(hashSet);
            audioAttributes2 = builder.build();
        }
        this.mAudioAttributes = audioAttributes2;
        int sampleRate = audioFormat.getSampleRate();
        sampleRate = sampleRate == 0 ? 0 : sampleRate;
        if ((audioFormat.getPropertySetMask() & 1) == 0) {
            i5 = 1;
        } else {
            i5 = audioFormat.getEncoding();
        }
        audioParamCheck(this.mAudioAttributes.getCapturePreset(), sampleRate, i5);
        if ((audioFormat.getPropertySetMask() & 8) != 0) {
            this.mChannelIndexMask = audioFormat.getChannelIndexMask();
            this.mChannelCount = audioFormat.getChannelCount();
        }
        if ((audioFormat.getPropertySetMask() & 4) != 0) {
            this.mChannelMask = getChannelMaskFromLegacyConfig(audioFormat.getChannelMask(), false);
            this.mChannelCount = audioFormat.getChannelCount();
        } else if (this.mChannelIndexMask == 0) {
            this.mChannelMask = getChannelMaskFromLegacyConfig(1, false);
            this.mChannelCount = android.media.AudioFormat.channelCountFromInChannelMask(this.mChannelMask);
        }
        audioBuffSizeCheck(i);
        android.content.AttributionSource attributionSource = context != null ? context.getAttributionSource() : android.content.AttributionSource.myAttributionSource();
        attributionSource = attributionSource.getPackageName() == null ? attributionSource.withPackageName("uid:" + android.os.Binder.getCallingUid()) : attributionSource;
        int[] iArr = {this.mSampleRate};
        int[] iArr2 = {resolveSessionId(context, i2)};
        android.content.AttributionSource.ScopedParcelState asScopedParcelState = attributionSource.asScopedParcelState();
        try {
            int native_setup = native_setup(new java.lang.ref.WeakReference(this), this.mAudioAttributes, iArr, this.mChannelMask, this.mChannelIndexMask, this.mAudioFormat, this.mNativeBufferSizeInBytes, iArr2, asScopedParcelState.getParcel(), 0L, i3, this.mHalInputFlags);
            if (native_setup != 0) {
                loge("Error code " + native_setup + " when initializing native AudioRecord object.");
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
            this.mState = 1;
        } finally {
        }
    }

    AudioRecord(long j) {
        this.mState = 0;
        this.mRecordingState = 1;
        this.mRecordingStateLock = new java.lang.Object();
        this.mPositionListener = null;
        this.mPositionListenerLock = new java.lang.Object();
        this.mEventHandler = null;
        this.mInitializationLooper = null;
        this.mNativeBufferSizeInBytes = 0;
        this.mSessionId = 0;
        this.mHalInputFlags = 0;
        this.mIsSubmixFullVolume = false;
        this.mLogSessionId = android.media.metrics.LogSessionId.LOG_SESSION_ID_NONE;
        this.mICallBack = new android.os.Binder();
        this.mRoutingChangeListeners = new android.util.ArrayMap<>();
        this.mPreferredDevice = null;
        this.mRecordingInfoImpl = new android.media.AudioRecordingMonitorImpl(this);
        this.mNativeAudioRecordHandle = 0L;
        this.mNativeJNIDataHandle = 0L;
        if (j != 0) {
            deferred_connect(j);
        } else {
            this.mState = 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unregisterAudioPolicyOnRelease(android.media.audiopolicy.AudioPolicy audioPolicy) {
        this.mAudioCapturePolicy = audioPolicy;
    }

    void deferred_connect(long j) {
        if (this.mState != 1) {
            int[] iArr = {0};
            int[] iArr2 = {0};
            android.content.AttributionSource.ScopedParcelState asScopedParcelState = android.content.AttributionSource.myAttributionSource().asScopedParcelState();
            try {
                int native_setup = native_setup(new java.lang.ref.WeakReference(this), null, iArr2, 0, 0, 0, 0, iArr, asScopedParcelState.getParcel(), j, 0, 0);
                if (asScopedParcelState != null) {
                    asScopedParcelState.close();
                }
                if (native_setup == 0) {
                    this.mSessionId = iArr[0];
                    this.mState = 1;
                } else {
                    loge("Error code " + native_setup + " when initializing native AudioRecord object.");
                }
            } finally {
            }
        }
    }

    public android.media.AudioAttributes getAudioAttributes() {
        return this.mAudioAttributes;
    }

    public static class Builder {
        private static final java.lang.String ERROR_MESSAGE_SOURCE_MISMATCH = "Cannot both set audio source and set playback capture config";
        private static final int PRIVACY_SENSITIVE_DEFAULT = -1;
        private static final int PRIVACY_SENSITIVE_DISABLED = 0;
        private static final int PRIVACY_SENSITIVE_ENABLED = 1;
        private android.media.AudioAttributes mAttributes;
        private android.media.AudioPlaybackCaptureConfiguration mAudioPlaybackCaptureConfiguration;
        private int mBufferSizeInBytes;
        private android.content.Context mContext;
        private android.media.AudioFormat mFormat;
        private int mSessionId = 0;
        private int mPrivacySensitive = -1;
        private int mMaxSharedAudioHistoryMs = 0;
        private int mCallRedirectionMode = 0;
        private boolean mIsHotwordStream = false;
        private boolean mIsHotwordLookback = false;

        public android.media.AudioRecord.Builder setAudioSource(int i) throws java.lang.IllegalArgumentException {
            com.android.internal.util.Preconditions.checkState(this.mAudioPlaybackCaptureConfiguration == null, ERROR_MESSAGE_SOURCE_MISMATCH);
            if (i < 0 || i > android.media.MediaRecorder.getAudioSourceMax()) {
                throw new java.lang.IllegalArgumentException("Invalid audio source " + i);
            }
            this.mAttributes = new android.media.AudioAttributes.Builder().setInternalCapturePreset(i).build();
            return this;
        }

        public android.media.AudioRecord.Builder setContext(android.content.Context context) {
            this.mContext = (android.content.Context) java.util.Objects.requireNonNull(context);
            return this;
        }

        @android.annotation.SystemApi
        public android.media.AudioRecord.Builder setAudioAttributes(android.media.AudioAttributes audioAttributes) throws java.lang.IllegalArgumentException {
            if (audioAttributes == null) {
                throw new java.lang.IllegalArgumentException("Illegal null AudioAttributes argument");
            }
            if (audioAttributes.getCapturePreset() == -1) {
                throw new java.lang.IllegalArgumentException("No valid capture preset in AudioAttributes argument");
            }
            this.mAttributes = audioAttributes;
            return this;
        }

        public android.media.AudioRecord.Builder setAudioFormat(android.media.AudioFormat audioFormat) throws java.lang.IllegalArgumentException {
            if (audioFormat == null) {
                throw new java.lang.IllegalArgumentException("Illegal null AudioFormat argument");
            }
            this.mFormat = audioFormat;
            return this;
        }

        public android.media.AudioRecord.Builder setBufferSizeInBytes(int i) throws java.lang.IllegalArgumentException {
            if (i <= 0) {
                throw new java.lang.IllegalArgumentException("Invalid buffer size " + i);
            }
            this.mBufferSizeInBytes = i;
            return this;
        }

        public android.media.AudioRecord.Builder setAudioPlaybackCaptureConfig(android.media.AudioPlaybackCaptureConfiguration audioPlaybackCaptureConfiguration) {
            com.android.internal.util.Preconditions.checkNotNull(audioPlaybackCaptureConfiguration, "Illegal null AudioPlaybackCaptureConfiguration argument");
            com.android.internal.util.Preconditions.checkState(this.mAttributes == null, ERROR_MESSAGE_SOURCE_MISMATCH);
            this.mAudioPlaybackCaptureConfiguration = audioPlaybackCaptureConfiguration;
            return this;
        }

        public android.media.AudioRecord.Builder setPrivacySensitive(boolean z) {
            this.mPrivacySensitive = z ? 1 : 0;
            return this;
        }

        @android.annotation.SystemApi
        public android.media.AudioRecord.Builder setSessionId(int i) throws java.lang.IllegalArgumentException {
            if (i < 0) {
                throw new java.lang.IllegalArgumentException("Invalid session ID " + i);
            }
            if (this.mSessionId == 0) {
                this.mSessionId = i;
            } else {
                android.util.Log.e(android.media.AudioRecord.TAG, "setSessionId() called twice or after setSharedAudioEvent()");
            }
            return this;
        }

        private android.media.AudioRecord buildAudioPlaybackCaptureRecord() {
            android.media.audiopolicy.AudioMix createAudioMix = this.mAudioPlaybackCaptureConfiguration.createAudioMix(this.mFormat);
            android.media.audiopolicy.AudioPolicy build = new android.media.audiopolicy.AudioPolicy.Builder(null).setMediaProjection(this.mAudioPlaybackCaptureConfiguration.getMediaProjection()).addMix(createAudioMix).build();
            if (android.media.AudioManager.registerAudioPolicyStatic(build) != 0) {
                throw new java.lang.UnsupportedOperationException("Error: could not register audio policy");
            }
            android.media.AudioRecord createAudioRecordSink = build.createAudioRecordSink(createAudioMix);
            if (createAudioRecordSink == null) {
                throw new java.lang.UnsupportedOperationException("Cannot create AudioRecord");
            }
            createAudioRecordSink.unregisterAudioPolicyOnRelease(build);
            return createAudioRecordSink;
        }

        public android.media.AudioRecord.Builder setCallRedirectionMode(int i) {
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

        private android.media.AudioRecord buildCallExtractionRecord() {
            android.media.audiopolicy.AudioMix build = new android.media.audiopolicy.AudioMix.Builder(new android.media.audiopolicy.AudioMixingRule.Builder().addMixRule(1, new android.media.AudioAttributes.Builder().setUsage(2).setForCallRedirection().build()).addMixRule(1, new android.media.AudioAttributes.Builder().setUsage(3).setForCallRedirection().build()).setTargetMixRole(0).build()).setFormat(this.mFormat).setRouteFlags(2).build();
            android.media.audiopolicy.AudioPolicy build2 = new android.media.audiopolicy.AudioPolicy.Builder(null).addMix(build).build();
            if (android.media.AudioManager.registerAudioPolicyStatic(build2) != 0) {
                throw new java.lang.UnsupportedOperationException("Error: could not register audio policy");
            }
            android.media.AudioRecord createAudioRecordSink = build2.createAudioRecordSink(build);
            if (createAudioRecordSink == null) {
                throw new java.lang.UnsupportedOperationException("Cannot create extraction AudioRecord");
            }
            createAudioRecordSink.unregisterAudioPolicyOnRelease(build2);
            return createAudioRecordSink;
        }

        @android.annotation.SystemApi
        public android.media.AudioRecord.Builder setMaxSharedAudioHistoryMillis(long j) throws java.lang.IllegalArgumentException {
            if (j <= 0 || j > 5000) {
                throw new java.lang.IllegalArgumentException("Illegal maxSharedAudioHistoryMillis argument");
            }
            this.mMaxSharedAudioHistoryMs = (int) j;
            return this;
        }

        @android.annotation.SystemApi
        public android.media.AudioRecord.Builder setSharedAudioEvent(android.media.MediaSyncEvent mediaSyncEvent) throws java.lang.IllegalArgumentException {
            java.util.Objects.requireNonNull(mediaSyncEvent);
            if (mediaSyncEvent.getType() != 100) {
                throw new java.lang.IllegalArgumentException("Invalid event type " + mediaSyncEvent.getType());
            }
            if (mediaSyncEvent.getAudioSessionId() == 0) {
                throw new java.lang.IllegalArgumentException("Invalid session ID " + mediaSyncEvent.getAudioSessionId());
            }
            this.mSessionId = mediaSyncEvent.getAudioSessionId();
            return this;
        }

        @android.annotation.SystemApi
        public android.media.AudioRecord.Builder setRequestHotwordStream(boolean z) {
            this.mIsHotwordStream = z;
            return this;
        }

        @android.annotation.SystemApi
        public android.media.AudioRecord.Builder setRequestHotwordLookbackStream(boolean z) {
            this.mIsHotwordLookback = z;
            return this;
        }

        public android.media.AudioRecord build() throws java.lang.UnsupportedOperationException {
            int i;
            if (this.mAudioPlaybackCaptureConfiguration != null) {
                return buildAudioPlaybackCaptureRecord();
            }
            if (this.mIsHotwordStream) {
                if (this.mIsHotwordLookback) {
                    throw new java.lang.UnsupportedOperationException("setRequestHotwordLookbackStream and setRequestHotwordStream used concurrently");
                }
                i = 512;
            } else if (!this.mIsHotwordLookback) {
                i = 0;
            } else {
                i = 1536;
            }
            if (this.mFormat == null) {
                this.mFormat = new android.media.AudioFormat.Builder().setEncoding(2).setChannelMask(16).build();
            } else {
                if (this.mFormat.getEncoding() == 0) {
                    this.mFormat = new android.media.AudioFormat.Builder(this.mFormat).setEncoding(2).build();
                }
                if (this.mFormat.getChannelMask() == 0 && this.mFormat.getChannelIndexMask() == 0) {
                    this.mFormat = new android.media.AudioFormat.Builder(this.mFormat).setChannelMask(16).build();
                }
            }
            if (this.mAttributes == null) {
                this.mAttributes = new android.media.AudioAttributes.Builder().setInternalCapturePreset(0).build();
            }
            if (this.mIsHotwordStream || this.mIsHotwordLookback) {
                this.mAttributes = new android.media.AudioAttributes.Builder(this.mAttributes).setInternalCapturePreset(6).build();
            }
            if (this.mPrivacySensitive != -1) {
                int capturePreset = this.mAttributes.getCapturePreset();
                if (capturePreset == 8 || capturePreset == 1998 || capturePreset == 3 || capturePreset == 2 || capturePreset == 4 || capturePreset == 1997) {
                    throw new java.lang.UnsupportedOperationException("Cannot request private capture with source: " + capturePreset);
                }
                this.mAttributes = new android.media.AudioAttributes.Builder(this.mAttributes).setInternalCapturePreset(capturePreset).setPrivacySensitive(this.mPrivacySensitive == 1).build();
            }
            if (this.mCallRedirectionMode == 2) {
                return buildCallExtractionRecord();
            }
            if (this.mCallRedirectionMode == 1) {
                this.mAttributes = new android.media.AudioAttributes.Builder(this.mAttributes).setForCallRedirection().build();
            }
            try {
                if (this.mBufferSizeInBytes == 0) {
                    this.mBufferSizeInBytes = this.mFormat.getChannelCount() * android.media.AudioFormat.getBytesPerSample(this.mFormat.getEncoding());
                }
                android.media.AudioRecord audioRecord = new android.media.AudioRecord(this.mAttributes, this.mFormat, this.mBufferSizeInBytes, this.mSessionId, this.mContext, this.mMaxSharedAudioHistoryMs, i);
                if (audioRecord.getState() == 0) {
                    throw new java.lang.UnsupportedOperationException("Cannot create AudioRecord");
                }
                return audioRecord;
            } catch (java.lang.IllegalArgumentException e) {
                throw new java.lang.UnsupportedOperationException(e.getMessage());
            }
        }
    }

    private static int resolveSessionId(android.content.Context context, int i) {
        int deviceId;
        android.companion.virtual.VirtualDeviceManager virtualDeviceManager;
        if (i != 0) {
            return i;
        }
        if (context == null || (deviceId = context.getDeviceId()) == 0 || (virtualDeviceManager = (android.companion.virtual.VirtualDeviceManager) context.getSystemService(android.companion.virtual.VirtualDeviceManager.class)) == null || virtualDeviceManager.getDevicePolicy(deviceId, 1) == 0) {
            return 0;
        }
        return virtualDeviceManager.getAudioRecordingSessionId(deviceId);
    }

    private static int getChannelMaskFromLegacyConfig(int i, boolean z) {
        int i2;
        switch (i) {
            case 1:
            case 2:
            case 16:
                i2 = 16;
                break;
            case 3:
            case 12:
                i2 = 12;
                break;
            case 48:
                i2 = i;
                break;
            default:
                throw new java.lang.IllegalArgumentException("Unsupported channel configuration.");
        }
        if (!z && (i == 2 || i == 3)) {
            throw new java.lang.IllegalArgumentException("Unsupported deprecated configuration.");
        }
        return i2;
    }

    private void audioParamCheck(int i, int i2, int i3) throws java.lang.IllegalArgumentException {
        if (i < 0 || (i > android.media.MediaRecorder.getAudioSourceMax() && i != 1998 && i != 1997 && i != 1999 && i != 2000)) {
            throw new java.lang.IllegalArgumentException("Invalid audio source " + i);
        }
        this.mRecordSource = i;
        if ((i2 < android.media.AudioFormat.SAMPLE_RATE_HZ_MIN || i2 > android.media.AudioFormat.SAMPLE_RATE_HZ_MAX) && i2 != 0) {
            throw new java.lang.IllegalArgumentException(i2 + "Hz is not a supported sample rate.");
        }
        this.mSampleRate = i2;
        switch (i3) {
            case 1:
                this.mAudioFormat = 2;
                return;
            case 2:
            case 3:
            case 4:
            case 18:
            case 21:
            case 22:
                this.mAudioFormat = i3;
                return;
            default:
                throw new java.lang.IllegalArgumentException("Unsupported sample encoding " + i3 + ". Should be ENCODING_PCM_8BIT, ENCODING_PCM_16BIT, ENCODING_PCM_24BIT_PACKED, ENCODING_PCM_32BIT, or ENCODING_PCM_FLOAT.");
        }
    }

    private void audioBuffSizeCheck(int i) throws java.lang.IllegalArgumentException {
        if (i % getFormat().getFrameSizeInBytes() != 0 || i < 1) {
            throw new java.lang.IllegalArgumentException("Invalid audio buffer size " + i + " (frame size " + getFormat().getFrameSizeInBytes() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        this.mNativeBufferSizeInBytes = i;
    }

    public void release() {
        try {
            stop();
        } catch (java.lang.IllegalStateException e) {
        }
        if (this.mAudioCapturePolicy != null) {
            android.media.AudioManager.unregisterAudioPolicyAsyncStatic(this.mAudioCapturePolicy);
            this.mAudioCapturePolicy = null;
        }
        native_release();
        this.mState = 0;
    }

    protected void finalize() {
        release();
    }

    public int getSampleRate() {
        return this.mSampleRate;
    }

    public int getAudioSource() {
        return this.mRecordSource;
    }

    public int getAudioFormat() {
        return this.mAudioFormat;
    }

    public int getChannelConfiguration() {
        return this.mChannelMask;
    }

    public android.media.AudioFormat getFormat() {
        android.media.AudioFormat.Builder encoding = new android.media.AudioFormat.Builder().setSampleRate(this.mSampleRate).setEncoding(this.mAudioFormat);
        if (this.mChannelMask != 0) {
            encoding.setChannelMask(this.mChannelMask);
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

    public int getRecordingState() {
        int i;
        synchronized (this.mRecordingStateLock) {
            i = this.mRecordingState;
        }
        return i;
    }

    public int getBufferSizeInFrames() {
        return native_get_buffer_size_in_frames();
    }

    public int getNotificationMarkerPosition() {
        return native_get_marker_pos();
    }

    public int getPositionNotificationPeriod() {
        return native_get_pos_update_period();
    }

    public int getTimestamp(android.media.AudioTimestamp audioTimestamp, int i) {
        if (audioTimestamp == null || (i != 1 && i != 0)) {
            throw new java.lang.IllegalArgumentException();
        }
        return native_get_timestamp(audioTimestamp, i);
    }

    public static int getMinBufferSize(int i, int i2, int i3) {
        int i4;
        switch (i2) {
            case 1:
            case 2:
            case 16:
                i4 = 1;
                break;
            case 3:
            case 12:
            case 48:
                i4 = 2;
                break;
            default:
                loge("getMinBufferSize(): Invalid channel configuration.");
                return -2;
        }
        int native_get_min_buff_size = native_get_min_buff_size(i, i4, i3);
        if (native_get_min_buff_size == 0) {
            return -2;
        }
        if (native_get_min_buff_size == -1) {
            return -1;
        }
        return native_get_min_buff_size;
    }

    public int getAudioSessionId() {
        return this.mSessionId;
    }

    public boolean isPrivacySensitive() {
        return (this.mAudioAttributes.getAllFlags() & 8192) != 0;
    }

    @android.annotation.SystemApi
    public boolean isHotwordStream() {
        return (this.mHalInputFlags & 512) != 0 && (this.mHalInputFlags & 1024) == 0;
    }

    @android.annotation.SystemApi
    public boolean isHotwordLookbackStream() {
        return (this.mHalInputFlags & 1024) != 0;
    }

    public void startRecording() throws java.lang.IllegalStateException {
        if (this.mState != 1) {
            throw new java.lang.IllegalStateException("startRecording() called on an uninitialized AudioRecord.");
        }
        synchronized (this.mRecordingStateLock) {
            if (native_start(0, 0) == 0) {
                handleFullVolumeRec(true);
                this.mRecordingState = 3;
            }
        }
    }

    public void startRecording(android.media.MediaSyncEvent mediaSyncEvent) throws java.lang.IllegalStateException {
        if (this.mState != 1) {
            throw new java.lang.IllegalStateException("startRecording() called on an uninitialized AudioRecord.");
        }
        synchronized (this.mRecordingStateLock) {
            if (native_start(mediaSyncEvent.getType(), mediaSyncEvent.getAudioSessionId()) == 0) {
                handleFullVolumeRec(true);
                this.mRecordingState = 3;
            }
        }
    }

    public void stop() throws java.lang.IllegalStateException {
        if (this.mState != 1) {
            throw new java.lang.IllegalStateException("stop() called on an uninitialized AudioRecord.");
        }
        synchronized (this.mRecordingStateLock) {
            handleFullVolumeRec(false);
            native_stop();
            this.mRecordingState = 1;
        }
    }

    private void handleFullVolumeRec(boolean z) {
        if (!this.mIsSubmixFullVolume) {
            return;
        }
        try {
            android.media.IAudioService.Stub.asInterface(android.os.ServiceManager.getService("audio")).forceRemoteSubmixFullVolume(z, this.mICallBack);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error talking to AudioService when handling full submix volume", e);
        }
    }

    public int read(byte[] bArr, int i, int i2) {
        return read(bArr, i, i2, 0);
    }

    public int read(byte[] bArr, int i, int i2, int i3) {
        int i4;
        if (this.mState != 1 || this.mAudioFormat == 4) {
            return -3;
        }
        if (i3 != 0 && i3 != 1) {
            android.util.Log.e(TAG, "AudioRecord.read() called with invalid blocking mode");
            return -2;
        }
        if (bArr == null || i < 0 || i2 < 0 || (i4 = i + i2) < 0 || i4 > bArr.length) {
            return -2;
        }
        return native_read_in_byte_array(bArr, i, i2, i3 == 0);
    }

    public int read(short[] sArr, int i, int i2) {
        return read(sArr, i, i2, 0);
    }

    public int read(short[] sArr, int i, int i2, int i3) {
        int i4;
        if (this.mState != 1 || this.mAudioFormat == 4 || this.mAudioFormat > 20) {
            return -3;
        }
        if (i3 != 0 && i3 != 1) {
            android.util.Log.e(TAG, "AudioRecord.read() called with invalid blocking mode");
            return -2;
        }
        if (sArr == null || i < 0 || i2 < 0 || (i4 = i + i2) < 0 || i4 > sArr.length) {
            return -2;
        }
        return native_read_in_short_array(sArr, i, i2, i3 == 0);
    }

    public int read(float[] fArr, int i, int i2, int i3) {
        int i4;
        if (this.mState == 0) {
            android.util.Log.e(TAG, "AudioRecord.read() called in invalid state STATE_UNINITIALIZED");
            return -3;
        }
        if (this.mAudioFormat != 4) {
            android.util.Log.e(TAG, "AudioRecord.read(float[] ...) requires format ENCODING_PCM_FLOAT");
            return -3;
        }
        if (i3 != 0 && i3 != 1) {
            android.util.Log.e(TAG, "AudioRecord.read() called with invalid blocking mode");
            return -2;
        }
        if (fArr == null || i < 0 || i2 < 0 || (i4 = i + i2) < 0 || i4 > fArr.length) {
            return -2;
        }
        return native_read_in_float_array(fArr, i, i2, i3 == 0);
    }

    public int read(java.nio.ByteBuffer byteBuffer, int i) {
        return read(byteBuffer, i, 0);
    }

    public int read(java.nio.ByteBuffer byteBuffer, int i, int i2) {
        if (this.mState != 1) {
            return -3;
        }
        if (i2 != 0 && i2 != 1) {
            android.util.Log.e(TAG, "AudioRecord.read() called with invalid blocking mode");
            return -2;
        }
        if (byteBuffer == null || i < 0) {
            return -2;
        }
        return native_read_in_direct_buffer(byteBuffer, i, i2 == 0);
    }

    public android.os.PersistableBundle getMetrics() {
        return native_getMetrics();
    }

    public void setRecordPositionUpdateListener(android.media.AudioRecord.OnRecordPositionUpdateListener onRecordPositionUpdateListener) {
        setRecordPositionUpdateListener(onRecordPositionUpdateListener, null);
    }

    public void setRecordPositionUpdateListener(android.media.AudioRecord.OnRecordPositionUpdateListener onRecordPositionUpdateListener, android.os.Handler handler) {
        synchronized (this.mPositionListenerLock) {
            this.mPositionListener = onRecordPositionUpdateListener;
            if (onRecordPositionUpdateListener != null) {
                if (handler != null) {
                    this.mEventHandler = new android.media.AudioRecord.NativeEventHandler(this, handler.getLooper());
                } else {
                    this.mEventHandler = new android.media.AudioRecord.NativeEventHandler(this, this.mInitializationLooper);
                }
            } else {
                this.mEventHandler = null;
            }
        }
    }

    public int setNotificationMarkerPosition(int i) {
        if (this.mState == 0) {
            return -3;
        }
        return native_set_marker_pos(i);
    }

    @Override // android.media.AudioRouting
    public android.media.AudioDeviceInfo getRoutedDevice() {
        int native_getRoutedDeviceId = native_getRoutedDeviceId();
        if (native_getRoutedDeviceId == 0) {
            return null;
        }
        return android.media.AudioManager.getDeviceForPortId(native_getRoutedDeviceId, 1);
    }

    @android.annotation.SystemApi
    public static long getMaxSharedAudioHistoryMillis() {
        return 5000L;
    }

    @android.annotation.SystemApi
    public android.media.MediaSyncEvent shareAudioHistory(java.lang.String str, long j) {
        java.util.Objects.requireNonNull(str);
        if (j < 0) {
            throw new java.lang.IllegalArgumentException("Illegal negative sharedAudioHistoryMs argument");
        }
        int native_shareAudioHistory = native_shareAudioHistory(str, j);
        if (native_shareAudioHistory == -2) {
            throw new java.lang.IllegalArgumentException("Illegal sharedAudioHistoryMs argument");
        }
        if (native_shareAudioHistory == -4) {
            throw new java.lang.SecurityException("permission CAPTURE_AUDIO_HOTWORD required");
        }
        android.media.MediaSyncEvent createEvent = android.media.MediaSyncEvent.createEvent(100);
        createEvent.setAudioSessionId(this.mSessionId);
        return createEvent;
    }

    private void testEnableNativeRoutingCallbacksLocked() {
        if (this.mRoutingChangeListeners.size() == 0) {
            native_enableDeviceCallback();
        }
    }

    private void testDisableNativeRoutingCallbacksLocked() {
        if (this.mRoutingChangeListeners.size() == 0) {
            native_disableDeviceCallback();
        }
    }

    @Override // android.media.AudioRouting
    public void addOnRoutingChangedListener(android.media.AudioRouting.OnRoutingChangedListener onRoutingChangedListener, android.os.Handler handler) {
        synchronized (this.mRoutingChangeListeners) {
            if (onRoutingChangedListener != null) {
                if (!this.mRoutingChangeListeners.containsKey(onRoutingChangedListener)) {
                    testEnableNativeRoutingCallbacksLocked();
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
                testDisableNativeRoutingCallbacksLocked();
            }
        }
    }

    @java.lang.Deprecated
    public interface OnRoutingChangedListener extends android.media.AudioRouting.OnRoutingChangedListener {
        void onRoutingChanged(android.media.AudioRecord audioRecord);

        @Override // android.media.AudioRouting.OnRoutingChangedListener
        default void onRoutingChanged(android.media.AudioRouting audioRouting) {
            if (audioRouting instanceof android.media.AudioRecord) {
                onRoutingChanged((android.media.AudioRecord) audioRouting);
            }
        }
    }

    @java.lang.Deprecated
    public void addOnRoutingChangedListener(android.media.AudioRecord.OnRoutingChangedListener onRoutingChangedListener, android.os.Handler handler) {
        addOnRoutingChangedListener((android.media.AudioRouting.OnRoutingChangedListener) onRoutingChangedListener, handler);
    }

    @java.lang.Deprecated
    public void removeOnRoutingChangedListener(android.media.AudioRecord.OnRoutingChangedListener onRoutingChangedListener) {
        removeOnRoutingChangedListener((android.media.AudioRouting.OnRoutingChangedListener) onRoutingChangedListener);
    }

    private void broadcastRoutingChange() {
        android.media.AudioManager.resetAudioPortGeneration();
        synchronized (this.mRoutingChangeListeners) {
            java.util.Iterator<android.media.NativeRoutingEventHandlerDelegate> it = this.mRoutingChangeListeners.values().iterator();
            while (it.hasNext()) {
                it.next().notifyClient();
            }
        }
    }

    public int setPositionNotificationPeriod(int i) {
        if (this.mState == 0) {
            return -3;
        }
        return native_set_pos_update_period(i);
    }

    @Override // android.media.AudioRouting
    public boolean setPreferredDevice(android.media.AudioDeviceInfo audioDeviceInfo) {
        if (audioDeviceInfo != null && !audioDeviceInfo.isSource()) {
            return false;
        }
        boolean native_setInputDevice = native_setInputDevice(audioDeviceInfo != null ? audioDeviceInfo.getId() : 0);
        if (native_setInputDevice) {
            synchronized (this) {
                this.mPreferredDevice = audioDeviceInfo;
            }
        }
        return native_setInputDevice;
    }

    @Override // android.media.AudioRouting
    public android.media.AudioDeviceInfo getPreferredDevice() {
        android.media.AudioDeviceInfo audioDeviceInfo;
        synchronized (this) {
            audioDeviceInfo = this.mPreferredDevice;
        }
        return audioDeviceInfo;
    }

    public java.util.List<android.media.MicrophoneInfo> getActiveMicrophones() throws java.io.IOException {
        android.media.AudioDeviceInfo routedDevice;
        java.util.ArrayList<android.media.MicrophoneInfo> arrayList = new java.util.ArrayList<>();
        int native_get_active_microphones = native_get_active_microphones(arrayList);
        if (native_get_active_microphones != 0) {
            if (native_get_active_microphones != -3) {
                android.util.Log.e(TAG, "getActiveMicrophones failed:" + native_get_active_microphones);
            }
            android.util.Log.i(TAG, "getActiveMicrophones failed, fallback on routed device info");
        }
        android.media.AudioManager.setPortIdForMicrophones(arrayList);
        if (arrayList.size() == 0 && (routedDevice = getRoutedDevice()) != null) {
            android.media.MicrophoneInfo microphoneInfoFromAudioDeviceInfo = android.media.AudioManager.microphoneInfoFromAudioDeviceInfo(routedDevice);
            java.util.ArrayList arrayList2 = new java.util.ArrayList();
            for (int i = 0; i < this.mChannelCount; i++) {
                arrayList2.add(new android.util.Pair(java.lang.Integer.valueOf(i), 1));
            }
            microphoneInfoFromAudioDeviceInfo.setChannelMapping(arrayList2);
            arrayList.add(microphoneInfoFromAudioDeviceInfo);
        }
        return arrayList;
    }

    @Override // android.media.AudioRecordingMonitor
    public void registerAudioRecordingCallback(java.util.concurrent.Executor executor, android.media.AudioManager.AudioRecordingCallback audioRecordingCallback) {
        this.mRecordingInfoImpl.registerAudioRecordingCallback(executor, audioRecordingCallback);
    }

    @Override // android.media.AudioRecordingMonitor
    public void unregisterAudioRecordingCallback(android.media.AudioManager.AudioRecordingCallback audioRecordingCallback) {
        this.mRecordingInfoImpl.unregisterAudioRecordingCallback(audioRecordingCallback);
    }

    @Override // android.media.AudioRecordingMonitor
    public android.media.AudioRecordingConfiguration getActiveRecordingConfiguration() {
        return this.mRecordingInfoImpl.getActiveRecordingConfiguration();
    }

    @Override // android.media.AudioRecordingMonitorClient
    public int getPortId() {
        if (this.mNativeAudioRecordHandle == 0) {
            return 0;
        }
        try {
            return native_getPortId();
        } catch (java.lang.IllegalStateException e) {
            return 0;
        }
    }

    @Override // android.media.MicrophoneDirection
    public boolean setPreferredMicrophoneDirection(int i) {
        return native_set_preferred_microphone_direction(i) == 0;
    }

    @Override // android.media.MicrophoneDirection
    public boolean setPreferredMicrophoneFieldDimension(float f) {
        com.android.internal.util.Preconditions.checkArgument(f >= -1.0f && f <= 1.0f, "Argument must fall between -1 & 1 (inclusive)");
        return native_set_preferred_microphone_field_dimension(f) == 0;
    }

    public void setLogSessionId(android.media.metrics.LogSessionId logSessionId) {
        java.util.Objects.requireNonNull(logSessionId);
        if (this.mState == 0) {
            throw new java.lang.IllegalStateException("AudioRecord not initialized");
        }
        native_setLogSessionId(logSessionId.getStringId());
        this.mLogSessionId = logSessionId;
    }

    public android.media.metrics.LogSessionId getLogSessionId() {
        return this.mLogSessionId;
    }

    private class NativeEventHandler extends android.os.Handler {
        private final android.media.AudioRecord mAudioRecord;

        NativeEventHandler(android.media.AudioRecord audioRecord, android.os.Looper looper) {
            super(looper);
            this.mAudioRecord = audioRecord;
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            android.media.AudioRecord.OnRecordPositionUpdateListener onRecordPositionUpdateListener;
            synchronized (android.media.AudioRecord.this.mPositionListenerLock) {
                onRecordPositionUpdateListener = this.mAudioRecord.mPositionListener;
            }
            switch (message.what) {
                case 2:
                    if (onRecordPositionUpdateListener != null) {
                        onRecordPositionUpdateListener.onMarkerReached(this.mAudioRecord);
                        return;
                    }
                    return;
                case 3:
                    if (onRecordPositionUpdateListener != null) {
                        onRecordPositionUpdateListener.onPeriodicNotification(this.mAudioRecord);
                        return;
                    }
                    return;
                default:
                    android.media.AudioRecord.loge("Unknown native event type: " + message.what);
                    return;
            }
        }
    }

    private static void postEventFromNative(java.lang.Object obj, int i, int i2, int i3, java.lang.Object obj2) {
        android.media.AudioRecord audioRecord = (android.media.AudioRecord) ((java.lang.ref.WeakReference) obj).get();
        if (audioRecord == null) {
            return;
        }
        if (i == 1000) {
            audioRecord.broadcastRoutingChange();
        } else if (audioRecord.mEventHandler != null) {
            audioRecord.mEventHandler.sendMessage(audioRecord.mEventHandler.obtainMessage(i, i2, i3, obj2));
        }
    }

    @java.lang.Deprecated
    private int native_setup(java.lang.Object obj, java.lang.Object obj2, int[] iArr, int i, int i2, int i3, int i4, int[] iArr2, java.lang.String str, long j, int i5) {
        android.content.AttributionSource.ScopedParcelState asScopedParcelState = android.content.AttributionSource.myAttributionSource().withPackageName(str).asScopedParcelState();
        try {
            int native_setup = native_setup(obj, obj2, iArr, i, i2, i3, i4, iArr2, asScopedParcelState.getParcel(), j, 0, i5);
            if (asScopedParcelState != null) {
                asScopedParcelState.close();
            }
            return native_setup;
        } catch (java.lang.Throwable th) {
            if (asScopedParcelState == null) {
                throw th;
            }
            try {
                asScopedParcelState.close();
                throw th;
            } catch (java.lang.Throwable th2) {
                th.addSuppressed(th2);
                throw th;
            }
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
        public static final java.lang.String ATTRIBUTES = "android.media.audiorecord.attributes";
        public static final java.lang.String CHANNELS = "android.media.audiorecord.channels";
        public static final java.lang.String CHANNEL_MASK = "android.media.audiorecord.channelMask";
        public static final java.lang.String DURATION_MS = "android.media.audiorecord.durationMs";
        public static final java.lang.String ENCODING = "android.media.audiorecord.encoding";
        public static final java.lang.String FRAME_COUNT = "android.media.audiorecord.frameCount";

        @java.lang.Deprecated
        public static final java.lang.String LATENCY = "android.media.audiorecord.latency";
        private static final java.lang.String MM_PREFIX = "android.media.audiorecord.";
        public static final java.lang.String PORT_ID = "android.media.audiorecord.portId";
        public static final java.lang.String SAMPLERATE = "android.media.audiorecord.samplerate";
        public static final java.lang.String SOURCE = "android.media.audiorecord.source";
        public static final java.lang.String START_COUNT = "android.media.audiorecord.startCount";

        private MetricsConstants() {
        }
    }
}
