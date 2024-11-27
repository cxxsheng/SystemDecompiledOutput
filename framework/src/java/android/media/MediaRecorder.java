package android.media;

/* loaded from: classes2.dex */
public class MediaRecorder implements android.media.AudioRouting, android.media.AudioRecordingMonitor, android.media.AudioRecordingMonitorClient, android.media.MicrophoneDirection {
    public static final int MEDIA_ERROR_SERVER_DIED = 100;
    public static final int MEDIA_RECORDER_ERROR_UNKNOWN = 1;
    public static final int MEDIA_RECORDER_INFO_MAX_DURATION_REACHED = 800;
    public static final int MEDIA_RECORDER_INFO_MAX_FILESIZE_APPROACHING = 802;
    public static final int MEDIA_RECORDER_INFO_MAX_FILESIZE_REACHED = 801;
    public static final int MEDIA_RECORDER_INFO_NEXT_OUTPUT_FILE_STARTED = 803;
    public static final int MEDIA_RECORDER_INFO_UNKNOWN = 1;
    public static final int MEDIA_RECORDER_TRACK_INFO_COMPLETION_STATUS = 1000;
    public static final int MEDIA_RECORDER_TRACK_INFO_DATA_KBYTES = 1009;
    public static final int MEDIA_RECORDER_TRACK_INFO_DURATION_MS = 1003;
    public static final int MEDIA_RECORDER_TRACK_INFO_ENCODED_FRAMES = 1005;
    public static final int MEDIA_RECORDER_TRACK_INFO_INITIAL_DELAY_MS = 1007;
    public static final int MEDIA_RECORDER_TRACK_INFO_LIST_END = 2000;
    public static final int MEDIA_RECORDER_TRACK_INFO_LIST_START = 1000;
    public static final int MEDIA_RECORDER_TRACK_INFO_MAX_CHUNK_DUR_MS = 1004;
    public static final int MEDIA_RECORDER_TRACK_INFO_PROGRESS_IN_TIME = 1001;
    public static final int MEDIA_RECORDER_TRACK_INFO_START_OFFSET_MS = 1008;
    public static final int MEDIA_RECORDER_TRACK_INFO_TYPE = 1002;
    public static final int MEDIA_RECORDER_TRACK_INTER_CHUNK_TIME_MS = 1006;
    private static final java.lang.String TAG = "MediaRecorder";
    private int mChannelCount;
    private android.media.MediaRecorder.EventHandler mEventHandler;
    private java.io.FileDescriptor mFd;
    private java.io.File mFile;
    private android.media.metrics.LogSessionId mLogSessionId;
    private long mNativeContext;
    private android.media.MediaRecorder.OnErrorListener mOnErrorListener;
    private android.media.MediaRecorder.OnInfoListener mOnInfoListener;
    private java.lang.String mPath;
    private android.media.AudioDeviceInfo mPreferredDevice;
    android.media.AudioRecordingMonitorImpl mRecordingInfoImpl;
    private android.util.ArrayMap<android.media.AudioRouting.OnRoutingChangedListener, android.media.NativeRoutingEventHandlerDelegate> mRoutingChangeListeners;
    private android.view.Surface mSurface;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AudioEncoderValues {
    }

    public interface OnErrorListener {
        void onError(android.media.MediaRecorder mediaRecorder, int i, int i2);
    }

    public interface OnInfoListener {
        void onInfo(android.media.MediaRecorder mediaRecorder, int i, int i2);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface OutputFormatValues {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Source {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SystemSource {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface VideoEncoderValues {
    }

    private native void _prepare() throws java.lang.IllegalStateException, java.io.IOException;

    private native void _setNextOutputFile(java.io.FileDescriptor fileDescriptor) throws java.lang.IllegalStateException, java.io.IOException;

    private native void _setOutputFile(java.io.FileDescriptor fileDescriptor) throws java.lang.IllegalStateException, java.io.IOException;

    private final native void native_enableDeviceCallback(boolean z);

    private native void native_finalize();

    private final native int native_getActiveMicrophones(java.util.ArrayList<android.media.MicrophoneInfo> arrayList);

    private native android.os.PersistableBundle native_getMetrics();

    private native int native_getPortId();

    private final native int native_getRoutedDeviceId();

    private static final native void native_init();

    private native void native_reset();

    private final native boolean native_setInputDevice(int i);

    private final native void native_setInputSurface(android.view.Surface surface);

    private native int native_setPreferredMicrophoneDirection(int i);

    private native int native_setPreferredMicrophoneFieldDimension(float f);

    private native void native_setup(java.lang.Object obj, java.lang.String str, android.os.Parcel parcel) throws java.lang.IllegalStateException;

    private native void setParameter(java.lang.String str);

    public native int getMaxAmplitude() throws java.lang.IllegalStateException;

    public native android.view.Surface getSurface();

    public native boolean isPrivacySensitive();

    public native void pause() throws java.lang.IllegalStateException;

    public native void release();

    public native void resume() throws java.lang.IllegalStateException;

    public native void setAudioEncoder(int i) throws java.lang.IllegalStateException;

    public native void setAudioSource(int i) throws java.lang.IllegalStateException;

    @java.lang.Deprecated
    public native void setCamera(android.hardware.Camera camera);

    public native void setMaxDuration(int i) throws java.lang.IllegalArgumentException;

    public native void setMaxFileSize(long j) throws java.lang.IllegalArgumentException;

    public native void setOutputFormat(int i) throws java.lang.IllegalStateException;

    public native void setPrivacySensitive(boolean z);

    public native void setVideoEncoder(int i) throws java.lang.IllegalStateException;

    public native void setVideoFrameRate(int i) throws java.lang.IllegalStateException;

    public native void setVideoSize(int i, int i2) throws java.lang.IllegalStateException;

    public native void setVideoSource(int i) throws java.lang.IllegalStateException;

    public native void start() throws java.lang.IllegalStateException;

    public native void stop() throws java.lang.IllegalStateException;

    static {
        java.lang.System.loadLibrary("media_jni");
        native_init();
    }

    @java.lang.Deprecated
    public MediaRecorder() {
        this(android.app.ActivityThread.currentApplication());
    }

    public MediaRecorder(android.content.Context context) {
        this.mLogSessionId = android.media.metrics.LogSessionId.LOG_SESSION_ID_NONE;
        this.mPreferredDevice = null;
        this.mRoutingChangeListeners = new android.util.ArrayMap<>();
        this.mRecordingInfoImpl = new android.media.AudioRecordingMonitorImpl(this);
        java.util.Objects.requireNonNull(context);
        android.os.Looper myLooper = android.os.Looper.myLooper();
        if (myLooper != null) {
            this.mEventHandler = new android.media.MediaRecorder.EventHandler(this, myLooper);
        } else {
            android.os.Looper mainLooper = android.os.Looper.getMainLooper();
            if (mainLooper != null) {
                this.mEventHandler = new android.media.MediaRecorder.EventHandler(this, mainLooper);
            } else {
                this.mEventHandler = null;
            }
        }
        this.mChannelCount = 1;
        android.content.AttributionSource.ScopedParcelState asScopedParcelState = context.getAttributionSource().asScopedParcelState();
        try {
            native_setup(new java.lang.ref.WeakReference(this), android.app.ActivityThread.currentPackageName(), asScopedParcelState.getParcel());
            if (asScopedParcelState != null) {
                asScopedParcelState.close();
            }
        } catch (java.lang.Throwable th) {
            if (asScopedParcelState != null) {
                try {
                    asScopedParcelState.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public void setLogSessionId(android.media.metrics.LogSessionId logSessionId) {
        java.util.Objects.requireNonNull(logSessionId);
        this.mLogSessionId = logSessionId;
        setParameter("log-session-id=" + logSessionId.getStringId());
    }

    public android.media.metrics.LogSessionId getLogSessionId() {
        return this.mLogSessionId;
    }

    public void setInputSurface(android.view.Surface surface) {
        if (!(surface instanceof android.media.MediaCodec.PersistentSurface)) {
            throw new java.lang.IllegalArgumentException("not a PersistentSurface");
        }
        native_setInputSurface(surface);
    }

    public void setPreviewDisplay(android.view.Surface surface) {
        this.mSurface = surface;
    }

    public final class AudioSource {
        public static final int AUDIO_SOURCE_INVALID = -1;
        public static final int CAMCORDER = 5;
        public static final int DEFAULT = 0;

        @android.annotation.SystemApi
        public static final int ECHO_REFERENCE = 1997;

        @android.annotation.SystemApi
        public static final int HOTWORD = 1999;
        public static final int MIC = 1;

        @android.annotation.SystemApi
        public static final int RADIO_TUNER = 1998;
        public static final int REMOTE_SUBMIX = 8;

        @android.annotation.SystemApi
        public static final int ULTRASOUND = 2000;
        public static final int UNPROCESSED = 9;
        public static final int VOICE_CALL = 4;
        public static final int VOICE_COMMUNICATION = 7;
        public static final int VOICE_DOWNLINK = 3;
        public static final int VOICE_PERFORMANCE = 10;
        public static final int VOICE_RECOGNITION = 6;
        public static final int VOICE_UPLINK = 2;

        private AudioSource() {
        }
    }

    public static boolean isSystemOnlyAudioSource(int i) {
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 9:
            case 10:
                return false;
            case 8:
            default:
                return true;
        }
    }

    public static boolean isValidAudioSource(int i) {
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 1997:
            case 1998:
            case 1999:
            case 2000:
                return true;
            default:
                return false;
        }
    }

    public static final java.lang.String toLogFriendlyAudioSource(int i) {
        switch (i) {
            case -1:
                return "AUDIO_SOURCE_INVALID";
            case 0:
                return "DEFAULT";
            case 1:
                return "MIC";
            case 2:
                return "VOICE_UPLINK";
            case 3:
                return "VOICE_DOWNLINK";
            case 4:
                return "VOICE_CALL";
            case 5:
                return "CAMCORDER";
            case 6:
                return "VOICE_RECOGNITION";
            case 7:
                return "VOICE_COMMUNICATION";
            case 8:
                return "REMOTE_SUBMIX";
            case 9:
                return "UNPROCESSED";
            case 10:
                return "VOICE_PERFORMANCE";
            case 1997:
                return "ECHO_REFERENCE";
            case 1998:
                return "RADIO_TUNER";
            case 1999:
                return "HOTWORD";
            case 2000:
                return "ULTRASOUND";
            default:
                return "unknown source " + i;
        }
    }

    public final class VideoSource {
        public static final int CAMERA = 1;
        public static final int DEFAULT = 0;
        public static final int SURFACE = 2;

        private VideoSource() {
        }
    }

    public final class OutputFormat {
        public static final int AAC_ADIF = 5;
        public static final int AAC_ADTS = 6;
        public static final int AMR_NB = 3;
        public static final int AMR_WB = 4;
        public static final int DEFAULT = 0;
        public static final int HEIF = 10;
        public static final int MPEG_2_TS = 8;
        public static final int MPEG_4 = 2;
        public static final int OGG = 11;
        public static final int OUTPUT_FORMAT_RTP_AVP = 7;
        public static final int RAW_AMR = 3;
        public static final int THREE_GPP = 1;
        public static final int WEBM = 9;

        private OutputFormat() {
        }
    }

    public final class AudioEncoder {
        public static final int AAC = 3;
        public static final int AAC_ELD = 5;
        public static final int AMR_NB = 1;
        public static final int AMR_WB = 2;
        public static final int DEFAULT = 0;
        public static final int HE_AAC = 4;
        public static final int OPUS = 7;
        public static final int VORBIS = 6;

        private AudioEncoder() {
        }
    }

    public final class VideoEncoder {
        public static final int AV1 = 8;
        public static final int DEFAULT = 0;
        public static final int DOLBY_VISION = 7;
        public static final int H263 = 1;
        public static final int H264 = 2;
        public static final int HEVC = 5;
        public static final int MPEG_4_SP = 3;
        public static final int VP8 = 4;
        public static final int VP9 = 6;

        private VideoEncoder() {
        }
    }

    public static final int getAudioSourceMax() {
        return 10;
    }

    public void setProfile(android.media.CamcorderProfile camcorderProfile) {
        setOutputFormat(camcorderProfile.fileFormat);
        setVideoFrameRate(camcorderProfile.videoFrameRate);
        setVideoSize(camcorderProfile.videoFrameWidth, camcorderProfile.videoFrameHeight);
        setVideoEncodingBitRate(camcorderProfile.videoBitRate);
        setVideoEncoder(camcorderProfile.videoCodec);
        if (camcorderProfile.quality < 1000 || camcorderProfile.quality > 1007) {
            setAudioEncodingBitRate(camcorderProfile.audioBitRate);
            setAudioChannels(camcorderProfile.audioChannels);
            setAudioSamplingRate(camcorderProfile.audioSampleRate);
            setAudioEncoder(camcorderProfile.audioCodec);
        }
    }

    public void setAudioProfile(android.media.EncoderProfiles.AudioProfile audioProfile) {
        setAudioEncodingBitRate(audioProfile.getBitrate());
        setAudioChannels(audioProfile.getChannels());
        setAudioSamplingRate(audioProfile.getSampleRate());
        setAudioEncoder(audioProfile.getCodec());
    }

    public void setVideoProfile(android.media.EncoderProfiles.VideoProfile videoProfile) {
        setVideoFrameRate(videoProfile.getFrameRate());
        setVideoSize(videoProfile.getWidth(), videoProfile.getHeight());
        setVideoEncodingBitRate(videoProfile.getBitrate());
        setVideoEncoder(videoProfile.getCodec());
        if (videoProfile.getProfile() >= 0) {
            setVideoEncodingProfileLevel(videoProfile.getProfile(), 0);
        }
    }

    public void setCaptureRate(double d) {
        setParameter("time-lapse-enable=1");
        setParameter("time-lapse-fps=" + d);
    }

    public void setOrientationHint(int i) {
        if (i != 0 && i != 90 && i != 180 && i != 270) {
            throw new java.lang.IllegalArgumentException("Unsupported angle: " + i);
        }
        setParameter("video-param-rotation-angle-degrees=" + i);
    }

    public void setLocation(float f, float f2) {
        int i = (int) ((f * 10000.0f) + 0.5d);
        int i2 = (int) ((10000.0f * f2) + 0.5d);
        if (i > 900000 || i < -900000) {
            throw new java.lang.IllegalArgumentException("Latitude: " + f + " out of range.");
        }
        if (i2 > 1800000 || i2 < -1800000) {
            throw new java.lang.IllegalArgumentException("Longitude: " + f2 + " out of range");
        }
        setParameter("param-geotag-latitude=" + i);
        setParameter("param-geotag-longitude=" + i2);
    }

    public void setAudioSamplingRate(int i) {
        if (i <= 0) {
            throw new java.lang.IllegalArgumentException("Audio sampling rate is not positive");
        }
        setParameter("audio-param-sampling-rate=" + i);
    }

    public void setAudioChannels(int i) {
        if (i <= 0) {
            throw new java.lang.IllegalArgumentException("Number of channels is not positive");
        }
        this.mChannelCount = i;
        setParameter("audio-param-number-of-channels=" + i);
    }

    public void setAudioEncodingBitRate(int i) {
        if (i <= 0) {
            throw new java.lang.IllegalArgumentException("Audio encoding bit rate is not positive");
        }
        setParameter("audio-param-encoding-bitrate=" + i);
    }

    public void setVideoEncodingBitRate(int i) {
        if (i <= 0) {
            throw new java.lang.IllegalArgumentException("Video encoding bit rate is not positive");
        }
        setParameter("video-param-encoding-bitrate=" + i);
    }

    public void setVideoEncodingProfileLevel(int i, int i2) {
        if (i < 0) {
            throw new java.lang.IllegalArgumentException("Video encoding profile is not positive");
        }
        if (i2 < 0) {
            throw new java.lang.IllegalArgumentException("Video encoding level is not positive");
        }
        setParameter("video-param-encoder-profile=" + i);
        setParameter("video-param-encoder-level=" + i2);
    }

    public void setAuxiliaryOutputFile(java.io.FileDescriptor fileDescriptor) {
        android.util.Log.w(TAG, "setAuxiliaryOutputFile(FileDescriptor) is no longer supported.");
    }

    public void setAuxiliaryOutputFile(java.lang.String str) {
        android.util.Log.w(TAG, "setAuxiliaryOutputFile(String) is no longer supported.");
    }

    public void setOutputFile(java.io.FileDescriptor fileDescriptor) throws java.lang.IllegalStateException {
        this.mPath = null;
        this.mFile = null;
        this.mFd = fileDescriptor;
    }

    public void setOutputFile(java.io.File file) {
        this.mPath = null;
        this.mFd = null;
        this.mFile = file;
    }

    public void setNextOutputFile(java.io.FileDescriptor fileDescriptor) throws java.io.IOException {
        _setNextOutputFile(fileDescriptor);
    }

    public void setOutputFile(java.lang.String str) throws java.lang.IllegalStateException {
        this.mFd = null;
        this.mFile = null;
        this.mPath = str;
    }

    public void setNextOutputFile(java.io.File file) throws java.io.IOException {
        java.io.RandomAccessFile randomAccessFile = new java.io.RandomAccessFile(file, "rw");
        try {
            _setNextOutputFile(randomAccessFile.getFD());
        } finally {
            randomAccessFile.close();
        }
    }

    public void prepare() throws java.lang.IllegalStateException, java.io.IOException {
        if (this.mPath != null) {
            java.io.RandomAccessFile randomAccessFile = new java.io.RandomAccessFile(this.mPath, "rw");
            try {
                _setOutputFile(randomAccessFile.getFD());
            } finally {
                randomAccessFile.close();
            }
        } else if (this.mFd != null) {
            _setOutputFile(this.mFd);
        } else if (this.mFile != null) {
            java.io.RandomAccessFile randomAccessFile2 = new java.io.RandomAccessFile(this.mFile, "rw");
            try {
                _setOutputFile(randomAccessFile2.getFD());
            } finally {
                randomAccessFile2.close();
            }
        } else {
            throw new java.io.IOException("No valid output file");
        }
        _prepare();
    }

    public void reset() {
        native_reset();
        this.mEventHandler.removeCallbacksAndMessages(null);
    }

    public void setOnErrorListener(android.media.MediaRecorder.OnErrorListener onErrorListener) {
        this.mOnErrorListener = onErrorListener;
    }

    public void setOnInfoListener(android.media.MediaRecorder.OnInfoListener onInfoListener) {
        this.mOnInfoListener = onInfoListener;
    }

    private class EventHandler extends android.os.Handler {
        private static final int MEDIA_RECORDER_AUDIO_ROUTING_CHANGED = 10000;
        private static final int MEDIA_RECORDER_EVENT_ERROR = 1;
        private static final int MEDIA_RECORDER_EVENT_INFO = 2;
        private static final int MEDIA_RECORDER_EVENT_LIST_END = 99;
        private static final int MEDIA_RECORDER_EVENT_LIST_START = 1;
        private static final int MEDIA_RECORDER_TRACK_EVENT_ERROR = 100;
        private static final int MEDIA_RECORDER_TRACK_EVENT_INFO = 101;
        private static final int MEDIA_RECORDER_TRACK_EVENT_LIST_END = 1000;
        private static final int MEDIA_RECORDER_TRACK_EVENT_LIST_START = 100;
        private android.media.MediaRecorder mMediaRecorder;

        public EventHandler(android.media.MediaRecorder mediaRecorder, android.os.Looper looper) {
            super(looper);
            this.mMediaRecorder = mediaRecorder;
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            if (this.mMediaRecorder.mNativeContext == 0) {
                android.util.Log.w(android.media.MediaRecorder.TAG, "mediarecorder went away with unhandled events");
                return;
            }
            switch (message.what) {
                case 1:
                case 100:
                    if (android.media.MediaRecorder.this.mOnErrorListener != null) {
                        android.media.MediaRecorder.this.mOnErrorListener.onError(this.mMediaRecorder, message.arg1, message.arg2);
                        return;
                    }
                    return;
                case 2:
                case 101:
                    if (android.media.MediaRecorder.this.mOnInfoListener != null) {
                        android.media.MediaRecorder.this.mOnInfoListener.onInfo(this.mMediaRecorder, message.arg1, message.arg2);
                        return;
                    }
                    return;
                case 10000:
                    android.media.AudioManager.resetAudioPortGeneration();
                    synchronized (android.media.MediaRecorder.this.mRoutingChangeListeners) {
                        java.util.Iterator it = android.media.MediaRecorder.this.mRoutingChangeListeners.values().iterator();
                        while (it.hasNext()) {
                            ((android.media.NativeRoutingEventHandlerDelegate) it.next()).notifyClient();
                        }
                    }
                    return;
                default:
                    android.util.Log.e(android.media.MediaRecorder.TAG, "Unknown message type " + message.what);
                    return;
            }
        }
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

    @Override // android.media.AudioRouting
    public android.media.AudioDeviceInfo getRoutedDevice() {
        int native_getRoutedDeviceId = native_getRoutedDeviceId();
        if (native_getRoutedDeviceId == 0) {
            return null;
        }
        return android.media.AudioManager.getDeviceForPortId(native_getRoutedDeviceId, 1);
    }

    private void enableNativeRoutingCallbacksLocked(boolean z) {
        if (this.mRoutingChangeListeners.size() == 0) {
            native_enableDeviceCallback(z);
        }
    }

    @Override // android.media.AudioRouting
    public void addOnRoutingChangedListener(android.media.AudioRouting.OnRoutingChangedListener onRoutingChangedListener, android.os.Handler handler) {
        synchronized (this.mRoutingChangeListeners) {
            if (onRoutingChangedListener != null) {
                if (!this.mRoutingChangeListeners.containsKey(onRoutingChangedListener)) {
                    enableNativeRoutingCallbacksLocked(true);
                    android.util.ArrayMap<android.media.AudioRouting.OnRoutingChangedListener, android.media.NativeRoutingEventHandlerDelegate> arrayMap = this.mRoutingChangeListeners;
                    if (handler == null) {
                        handler = this.mEventHandler;
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
                enableNativeRoutingCallbacksLocked(false);
            }
        }
    }

    public java.util.List<android.media.MicrophoneInfo> getActiveMicrophones() throws java.io.IOException {
        android.media.AudioDeviceInfo routedDevice;
        java.util.ArrayList<android.media.MicrophoneInfo> arrayList = new java.util.ArrayList<>();
        int native_getActiveMicrophones = native_getActiveMicrophones(arrayList);
        if (native_getActiveMicrophones != 0) {
            if (native_getActiveMicrophones != -3) {
                android.util.Log.e(TAG, "getActiveMicrophones failed:" + native_getActiveMicrophones);
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

    @Override // android.media.MicrophoneDirection
    public boolean setPreferredMicrophoneDirection(int i) {
        return native_setPreferredMicrophoneDirection(i) == 0;
    }

    @Override // android.media.MicrophoneDirection
    public boolean setPreferredMicrophoneFieldDimension(float f) {
        com.android.internal.util.Preconditions.checkArgument(f >= -1.0f && f <= 1.0f, "Argument must fall between -1 & 1 (inclusive)");
        return native_setPreferredMicrophoneFieldDimension(f) == 0;
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
        if (this.mNativeContext == 0) {
            return 0;
        }
        return native_getPortId();
    }

    private static void postEventFromNative(java.lang.Object obj, int i, int i2, int i3, java.lang.Object obj2) {
        android.media.MediaRecorder mediaRecorder = (android.media.MediaRecorder) ((java.lang.ref.WeakReference) obj).get();
        if (mediaRecorder != null && mediaRecorder.mEventHandler != null) {
            mediaRecorder.mEventHandler.sendMessage(mediaRecorder.mEventHandler.obtainMessage(i, i2, i3, obj2));
        }
    }

    private void native_setup(java.lang.Object obj, java.lang.String str, java.lang.String str2) throws java.lang.IllegalStateException {
        android.content.AttributionSource.ScopedParcelState asScopedParcelState = android.content.AttributionSource.myAttributionSource().withPackageName(str2).asScopedParcelState();
        try {
            native_setup(obj, str, asScopedParcelState.getParcel());
            if (asScopedParcelState != null) {
                asScopedParcelState.close();
            }
        } catch (java.lang.Throwable th) {
            if (asScopedParcelState != null) {
                try {
                    asScopedParcelState.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public android.os.PersistableBundle getMetrics() {
        return native_getMetrics();
    }

    protected void finalize() {
        native_finalize();
    }

    public static final class MetricsConstants {
        public static final java.lang.String AUDIO_BITRATE = "android.media.mediarecorder.audio-bitrate";
        public static final java.lang.String AUDIO_CHANNELS = "android.media.mediarecorder.audio-channels";
        public static final java.lang.String AUDIO_SAMPLERATE = "android.media.mediarecorder.audio-samplerate";
        public static final java.lang.String AUDIO_TIMESCALE = "android.media.mediarecorder.audio-timescale";
        public static final java.lang.String CAPTURE_FPS = "android.media.mediarecorder.capture-fps";
        public static final java.lang.String CAPTURE_FPS_ENABLE = "android.media.mediarecorder.capture-fpsenable";
        public static final java.lang.String FRAMERATE = "android.media.mediarecorder.frame-rate";
        public static final java.lang.String HEIGHT = "android.media.mediarecorder.height";
        public static final java.lang.String MOVIE_TIMESCALE = "android.media.mediarecorder.movie-timescale";
        public static final java.lang.String ROTATION = "android.media.mediarecorder.rotation";
        public static final java.lang.String VIDEO_BITRATE = "android.media.mediarecorder.video-bitrate";
        public static final java.lang.String VIDEO_IFRAME_INTERVAL = "android.media.mediarecorder.video-iframe-interval";
        public static final java.lang.String VIDEO_LEVEL = "android.media.mediarecorder.video-encoder-level";
        public static final java.lang.String VIDEO_PROFILE = "android.media.mediarecorder.video-encoder-profile";
        public static final java.lang.String VIDEO_TIMESCALE = "android.media.mediarecorder.video-timescale";
        public static final java.lang.String WIDTH = "android.media.mediarecorder.width";

        private MetricsConstants() {
        }
    }
}
