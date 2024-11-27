package android.media;

/* loaded from: classes2.dex */
public class MediaPlayer extends android.media.PlayerBase implements android.media.SubtitleController.Listener, android.media.VolumeAutomation, android.media.AudioRouting {
    public static final boolean APPLY_METADATA_FILTER = true;
    public static final boolean BYPASS_METADATA_FILTER = false;
    private static final java.lang.String IMEDIA_PLAYER = "android.media.IMediaPlayer";
    private static final int INVOKE_ID_ADD_EXTERNAL_SOURCE = 2;
    private static final int INVOKE_ID_ADD_EXTERNAL_SOURCE_FD = 3;
    private static final int INVOKE_ID_DESELECT_TRACK = 5;
    private static final int INVOKE_ID_GET_SELECTED_TRACK = 7;
    private static final int INVOKE_ID_GET_TRACK_INFO = 1;
    private static final int INVOKE_ID_SELECT_TRACK = 4;
    private static final int INVOKE_ID_SET_PLAYER_IID = 8;
    private static final int INVOKE_ID_SET_VIDEO_SCALE_MODE = 6;
    private static final int KEY_PARAMETER_AUDIO_ATTRIBUTES = 1400;
    private static final int MEDIA_AUDIO_ROUTING_CHANGED = 10000;
    private static final int MEDIA_BUFFERING_UPDATE = 3;
    private static final int MEDIA_DRM_INFO = 210;
    private static final int MEDIA_ERROR = 100;
    public static final int MEDIA_ERROR_IO = -1004;
    public static final int MEDIA_ERROR_MALFORMED = -1007;
    public static final int MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK = 200;
    public static final int MEDIA_ERROR_SERVER_DIED = 100;
    public static final int MEDIA_ERROR_SYSTEM = Integer.MIN_VALUE;
    public static final int MEDIA_ERROR_TIMED_OUT = -110;
    public static final int MEDIA_ERROR_UNKNOWN = 1;
    public static final int MEDIA_ERROR_UNSUPPORTED = -1010;
    private static final int MEDIA_INFO = 200;
    public static final int MEDIA_INFO_AUDIO_NOT_PLAYING = 804;
    public static final int MEDIA_INFO_BAD_INTERLEAVING = 800;
    public static final int MEDIA_INFO_BUFFERING_END = 702;
    public static final int MEDIA_INFO_BUFFERING_START = 701;
    public static final int MEDIA_INFO_EXTERNAL_METADATA_UPDATE = 803;
    public static final int MEDIA_INFO_METADATA_UPDATE = 802;
    public static final int MEDIA_INFO_NETWORK_BANDWIDTH = 703;
    public static final int MEDIA_INFO_NOT_SEEKABLE = 801;
    public static final int MEDIA_INFO_STARTED_AS_NEXT = 2;
    public static final int MEDIA_INFO_SUBTITLE_TIMED_OUT = 902;
    public static final int MEDIA_INFO_TIMED_TEXT_ERROR = 900;
    public static final int MEDIA_INFO_UNKNOWN = 1;
    public static final int MEDIA_INFO_UNSUPPORTED_SUBTITLE = 901;
    public static final int MEDIA_INFO_VIDEO_NOT_PLAYING = 805;
    public static final int MEDIA_INFO_VIDEO_RENDERING_START = 3;
    public static final int MEDIA_INFO_VIDEO_TRACK_LAGGING = 700;
    private static final int MEDIA_META_DATA = 202;
    public static final java.lang.String MEDIA_MIMETYPE_TEXT_CEA_608 = "text/cea-608";
    public static final java.lang.String MEDIA_MIMETYPE_TEXT_CEA_708 = "text/cea-708";
    public static final java.lang.String MEDIA_MIMETYPE_TEXT_SUBRIP = "application/x-subrip";
    public static final java.lang.String MEDIA_MIMETYPE_TEXT_VTT = "text/vtt";
    private static final int MEDIA_NOP = 0;
    private static final int MEDIA_NOTIFY_TIME = 98;
    private static final int MEDIA_PAUSED = 7;
    private static final int MEDIA_PLAYBACK_COMPLETE = 2;
    private static final int MEDIA_PREPARED = 1;
    private static final int MEDIA_RTP_RX_NOTICE = 300;
    private static final int MEDIA_SEEK_COMPLETE = 4;
    private static final int MEDIA_SET_VIDEO_SIZE = 5;
    private static final int MEDIA_SKIPPED = 9;
    private static final int MEDIA_STARTED = 6;
    private static final int MEDIA_STOPPED = 8;
    private static final int MEDIA_SUBTITLE_DATA = 201;
    private static final int MEDIA_TIMED_TEXT = 99;
    private static final int MEDIA_TIME_DISCONTINUITY = 211;
    public static final boolean METADATA_ALL = false;
    public static final boolean METADATA_UPDATE_ONLY = true;
    public static final int PLAYBACK_RATE_AUDIO_MODE_DEFAULT = 0;
    public static final int PLAYBACK_RATE_AUDIO_MODE_RESAMPLE = 2;
    public static final int PLAYBACK_RATE_AUDIO_MODE_STRETCH = 1;
    public static final int PREPARE_DRM_STATUS_PREPARATION_ERROR = 3;
    public static final int PREPARE_DRM_STATUS_PROVISIONING_NETWORK_ERROR = 1;
    public static final int PREPARE_DRM_STATUS_PROVISIONING_SERVER_ERROR = 2;
    public static final int PREPARE_DRM_STATUS_SUCCESS = 0;
    public static final int SEEK_CLOSEST = 3;
    public static final int SEEK_CLOSEST_SYNC = 2;
    public static final int SEEK_NEXT_SYNC = 1;
    public static final int SEEK_PREVIOUS_SYNC = 0;
    private static final java.lang.String TAG = "MediaPlayer";
    public static final int VIDEO_SCALING_MODE_SCALE_TO_FIT = 1;
    public static final int VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING = 2;
    private boolean mActiveDrmScheme;
    private boolean mDrmConfigAllowed;
    private android.media.MediaPlayer.DrmInfo mDrmInfo;
    private boolean mDrmInfoResolved;
    private final java.lang.Object mDrmLock;
    private android.media.MediaDrm mDrmObj;
    private boolean mDrmProvisioningInProgress;
    private android.media.MediaPlayer.ProvisioningThread mDrmProvisioningThread;
    private byte[] mDrmSessionId;
    private java.util.UUID mDrmUUID;
    private boolean mEnableSelfRoutingMonitor;
    private android.media.MediaPlayer.EventHandler mEventHandler;
    private android.os.Handler mExtSubtitleDataHandler;
    private android.media.MediaPlayer.OnSubtitleDataListener mExtSubtitleDataListener;
    private java.util.BitSet mInbandTrackIndices;
    private java.util.Vector<android.util.Pair<java.lang.Integer, android.media.SubtitleTrack>> mIndexTrackPairs;
    private final android.media.MediaPlayer.OnSubtitleDataListener mIntSubtitleDataListener;
    private int mListenerContext;
    private long mNativeContext;
    private long mNativeSurfaceTexture;
    private android.media.MediaPlayer.OnBufferingUpdateListener mOnBufferingUpdateListener;
    private final android.media.MediaPlayer.OnCompletionListener mOnCompletionInternalListener;
    private android.media.MediaPlayer.OnCompletionListener mOnCompletionListener;
    private android.media.MediaPlayer.OnDrmConfigHelper mOnDrmConfigHelper;
    private android.media.MediaPlayer.OnDrmInfoHandlerDelegate mOnDrmInfoHandlerDelegate;
    private android.media.MediaPlayer.OnDrmPreparedHandlerDelegate mOnDrmPreparedHandlerDelegate;
    private android.media.MediaPlayer.OnErrorListener mOnErrorListener;
    private android.media.MediaPlayer.OnInfoListener mOnInfoListener;
    private android.os.Handler mOnMediaTimeDiscontinuityHandler;
    private android.media.MediaPlayer.OnMediaTimeDiscontinuityListener mOnMediaTimeDiscontinuityListener;
    private android.media.MediaPlayer.OnPreparedListener mOnPreparedListener;
    private java.util.concurrent.Executor mOnRtpRxNoticeExecutor;
    private android.media.MediaPlayer.OnRtpRxNoticeListener mOnRtpRxNoticeListener;
    private android.media.MediaPlayer.OnSeekCompleteListener mOnSeekCompleteListener;
    private android.media.MediaPlayer.OnTimedMetaDataAvailableListener mOnTimedMetaDataAvailableListener;
    private android.media.MediaPlayer.OnTimedTextListener mOnTimedTextListener;
    private android.media.MediaPlayer.OnVideoSizeChangedListener mOnVideoSizeChangedListener;
    private java.util.Vector<java.io.InputStream> mOpenSubtitleSources;
    private android.media.AudioDeviceInfo mPreferredDevice;
    private boolean mPrepareDrmInProgress;
    private android.util.ArrayMap<android.media.AudioRouting.OnRoutingChangedListener, android.media.NativeRoutingEventHandlerDelegate> mRoutingChangeListeners;
    private boolean mScreenOnWhilePlaying;
    private int mSelectedSubtitleTrackIndex;
    private boolean mStayAwake;
    private int mStreamType;
    private android.media.SubtitleController mSubtitleController;
    private boolean mSubtitleDataListenerDisabled;
    private android.view.SurfaceHolder mSurfaceHolder;
    private android.media.MediaPlayer.TimeProvider mTimeProvider;
    private final java.lang.Object mTimeProviderLock;
    private android.os.PowerManager.WakeLock mWakeLock;

    public interface OnBufferingUpdateListener {
        void onBufferingUpdate(android.media.MediaPlayer mediaPlayer, int i);
    }

    public interface OnCompletionListener {
        void onCompletion(android.media.MediaPlayer mediaPlayer);
    }

    public interface OnDrmConfigHelper {
        void onDrmConfig(android.media.MediaPlayer mediaPlayer);
    }

    public interface OnDrmInfoListener {
        void onDrmInfo(android.media.MediaPlayer mediaPlayer, android.media.MediaPlayer.DrmInfo drmInfo);
    }

    public interface OnDrmPreparedListener {
        void onDrmPrepared(android.media.MediaPlayer mediaPlayer, int i);
    }

    public interface OnErrorListener {
        boolean onError(android.media.MediaPlayer mediaPlayer, int i, int i2);
    }

    public interface OnInfoListener {
        boolean onInfo(android.media.MediaPlayer mediaPlayer, int i, int i2);
    }

    public interface OnMediaTimeDiscontinuityListener {
        void onMediaTimeDiscontinuity(android.media.MediaPlayer mediaPlayer, android.media.MediaTimestamp mediaTimestamp);
    }

    public interface OnPreparedListener {
        void onPrepared(android.media.MediaPlayer mediaPlayer);
    }

    @android.annotation.SystemApi
    public interface OnRtpRxNoticeListener {
        void onRtpRxNotice(android.media.MediaPlayer mediaPlayer, int i, int[] iArr);
    }

    public interface OnSeekCompleteListener {
        void onSeekComplete(android.media.MediaPlayer mediaPlayer);
    }

    public interface OnSubtitleDataListener {
        void onSubtitleData(android.media.MediaPlayer mediaPlayer, android.media.SubtitleData subtitleData);
    }

    public interface OnTimedMetaDataAvailableListener {
        void onTimedMetaDataAvailable(android.media.MediaPlayer mediaPlayer, android.media.TimedMetaData timedMetaData);
    }

    public interface OnTimedTextListener {
        void onTimedText(android.media.MediaPlayer mediaPlayer, android.media.TimedText timedText);
    }

    public interface OnVideoSizeChangedListener {
        void onVideoSizeChanged(android.media.MediaPlayer mediaPlayer, int i, int i2);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PlaybackRateAudioMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PrepareDrmStatusCode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SeekMode {
    }

    private native int _getAudioStreamType() throws java.lang.IllegalStateException;

    private native void _notifyAt(long j);

    private native void _pause() throws java.lang.IllegalStateException;

    private native int _prepare(android.os.Parcel parcel) throws java.io.IOException, java.lang.IllegalStateException;

    private native int _prepareAsync(android.os.Parcel parcel) throws java.lang.IllegalStateException;

    private native void _prepareDrm(byte[] bArr, byte[] bArr2);

    private native void _release();

    private native void _releaseDrm();

    private native void _reset();

    private final native void _seekTo(long j, int i);

    private native void _setAudioStreamType(int i);

    private native void _setAuxEffectSendLevel(float f);

    private native void _setDataSource(android.media.MediaDataSource mediaDataSource) throws java.lang.IllegalArgumentException, java.lang.IllegalStateException;

    private native void _setDataSource(java.io.FileDescriptor fileDescriptor, long j, long j2) throws java.io.IOException, java.lang.IllegalArgumentException, java.lang.IllegalStateException;

    private native void _setVideoSurface(android.view.Surface surface);

    private native void _setVolume(float f, float f2);

    private native void _start() throws java.lang.IllegalStateException;

    private native void _stop() throws java.lang.IllegalStateException;

    private native void nativeSetDataSource(android.os.IBinder iBinder, java.lang.String str, java.lang.String[] strArr, java.lang.String[] strArr2) throws java.io.IOException, java.lang.IllegalArgumentException, java.lang.SecurityException, java.lang.IllegalStateException;

    private native int native_applyVolumeShaper(android.media.VolumeShaper.Configuration configuration, android.media.VolumeShaper.Operation operation);

    private final native void native_enableDeviceCallback(boolean z);

    private final native void native_finalize();

    private final native boolean native_getMetadata(boolean z, boolean z2, android.os.Parcel parcel);

    private native android.os.PersistableBundle native_getMetrics();

    private final native int native_getRoutedDeviceId();

    private native android.media.VolumeShaper.State native_getVolumeShaperState(int i);

    private static final native void native_init();

    private final native int native_invoke(android.os.Parcel parcel, android.os.Parcel parcel2);

    public static native int native_pullBatteryData(android.os.Parcel parcel);

    private native void native_setAudioSessionId(int i);

    private final native int native_setMetadataFilter(android.os.Parcel parcel);

    private final native boolean native_setOutputDevice(int i);

    private final native int native_setRetransmitEndpoint(java.lang.String str, int i);

    private native void native_setup(java.lang.Object obj, android.os.Parcel parcel, int i);

    private native boolean setParameter(int i, android.os.Parcel parcel);

    public native void attachAuxEffect(int i);

    public native int getAudioSessionId();

    public native int getCurrentPosition();

    public native int getDuration();

    public native android.media.PlaybackParams getPlaybackParams();

    public native android.media.SyncParams getSyncParams();

    public native int getVideoHeight();

    public native int getVideoWidth();

    public native boolean isLooping();

    public native boolean isPlaying();

    public native void setLooping(boolean z);

    public native void setNextMediaPlayer(android.media.MediaPlayer mediaPlayer);

    public native void setPlaybackParams(android.media.PlaybackParams playbackParams);

    public native void setSyncParams(android.media.SyncParams syncParams);

    static {
        java.lang.System.loadLibrary("media_jni");
        native_init();
    }

    public MediaPlayer() {
        this(null, 0);
    }

    public MediaPlayer(android.content.Context context) {
        this((android.content.Context) java.util.Objects.requireNonNull(context), 0);
    }

    private MediaPlayer(android.content.Context context, int i) {
        super(new android.media.AudioAttributes.Builder().build(), 2);
        this.mWakeLock = null;
        this.mStreamType = Integer.MIN_VALUE;
        this.mDrmLock = new java.lang.Object();
        this.mPreferredDevice = null;
        this.mRoutingChangeListeners = new android.util.ArrayMap<>();
        this.mIndexTrackPairs = new java.util.Vector<>();
        this.mInbandTrackIndices = new java.util.BitSet();
        this.mSelectedSubtitleTrackIndex = -1;
        this.mIntSubtitleDataListener = new android.media.MediaPlayer.OnSubtitleDataListener() { // from class: android.media.MediaPlayer.3
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.media.MediaPlayer.OnSubtitleDataListener
            public void onSubtitleData(android.media.MediaPlayer mediaPlayer, android.media.SubtitleData subtitleData) {
                int trackIndex = subtitleData.getTrackIndex();
                synchronized (android.media.MediaPlayer.this.mIndexTrackPairs) {
                    java.util.Iterator it = android.media.MediaPlayer.this.mIndexTrackPairs.iterator();
                    while (it.hasNext()) {
                        android.util.Pair pair = (android.util.Pair) it.next();
                        if (pair.first != 0 && ((java.lang.Integer) pair.first).intValue() == trackIndex && pair.second != 0) {
                            ((android.media.SubtitleTrack) pair.second).onData(subtitleData);
                        }
                    }
                }
            }
        };
        this.mTimeProviderLock = new java.lang.Object();
        this.mOnCompletionInternalListener = new android.media.MediaPlayer.OnCompletionListener() { // from class: android.media.MediaPlayer.7
            @Override // android.media.MediaPlayer.OnCompletionListener
            public void onCompletion(android.media.MediaPlayer mediaPlayer) {
                android.media.MediaPlayer.this.tryToDisableNativeRoutingCallback();
                android.media.MediaPlayer.this.baseStop();
            }
        };
        android.os.Looper myLooper = android.os.Looper.myLooper();
        if (myLooper != null) {
            this.mEventHandler = new android.media.MediaPlayer.EventHandler(this, myLooper);
        } else {
            android.os.Looper mainLooper = android.os.Looper.getMainLooper();
            if (mainLooper != null) {
                this.mEventHandler = new android.media.MediaPlayer.EventHandler(this, mainLooper);
            } else {
                this.mEventHandler = null;
            }
        }
        this.mTimeProvider = new android.media.MediaPlayer.TimeProvider(this);
        this.mOpenSubtitleSources = new java.util.Vector<>();
        android.content.AttributionSource myAttributionSource = context == null ? android.content.AttributionSource.myAttributionSource() : context.getAttributionSource();
        android.content.AttributionSource.ScopedParcelState asScopedParcelState = (myAttributionSource.getPackageName() == null ? myAttributionSource.withPackageName("") : myAttributionSource).asScopedParcelState();
        try {
            native_setup(new java.lang.ref.WeakReference(this), asScopedParcelState.getParcel(), resolvePlaybackSessionId(context, i));
            if (asScopedParcelState != null) {
                asScopedParcelState.close();
            }
            baseRegisterPlayer(getAudioSessionId());
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

    private android.os.Parcel createPlayerIIdParcel() {
        android.os.Parcel newRequest = newRequest();
        newRequest.writeInt(8);
        newRequest.writeInt(this.mPlayerIId);
        return newRequest;
    }

    public android.os.Parcel newRequest() {
        android.os.Parcel obtain = android.os.Parcel.obtain();
        obtain.writeInterfaceToken(IMEDIA_PLAYER);
        return obtain;
    }

    public void invoke(android.os.Parcel parcel, android.os.Parcel parcel2) {
        int native_invoke = native_invoke(parcel, parcel2);
        parcel2.setDataPosition(0);
        if (native_invoke != 0) {
            throw new java.lang.RuntimeException("failure code: " + native_invoke);
        }
    }

    public void setDisplay(android.view.SurfaceHolder surfaceHolder) {
        android.view.Surface surface;
        this.mSurfaceHolder = surfaceHolder;
        if (surfaceHolder != null) {
            surface = surfaceHolder.getSurface();
        } else {
            surface = null;
        }
        _setVideoSurface(surface);
        updateSurfaceScreenOn();
    }

    public void setSurface(android.view.Surface surface) {
        if (this.mScreenOnWhilePlaying && surface != null) {
            android.util.Log.w(TAG, "setScreenOnWhilePlaying(true) is ineffective for Surface");
        }
        this.mSurfaceHolder = null;
        _setVideoSurface(surface);
        updateSurfaceScreenOn();
    }

    public void setVideoScalingMode(int i) {
        if (!isVideoScalingModeSupported(i)) {
            throw new java.lang.IllegalArgumentException("Scaling mode " + i + " is not supported");
        }
        android.os.Parcel obtain = android.os.Parcel.obtain();
        android.os.Parcel obtain2 = android.os.Parcel.obtain();
        try {
            obtain.writeInterfaceToken(IMEDIA_PLAYER);
            obtain.writeInt(6);
            obtain.writeInt(i);
            invoke(obtain, obtain2);
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }

    public static android.media.MediaPlayer create(android.content.Context context, android.net.Uri uri) {
        return create(context, uri, null);
    }

    public static android.media.MediaPlayer create(android.content.Context context, android.net.Uri uri, android.view.SurfaceHolder surfaceHolder) {
        return create(context, uri, surfaceHolder, null, 0);
    }

    public static android.media.MediaPlayer create(android.content.Context context, android.net.Uri uri, android.view.SurfaceHolder surfaceHolder, android.media.AudioAttributes audioAttributes, int i) {
        try {
            android.media.MediaPlayer mediaPlayer = new android.media.MediaPlayer(context, i);
            if (audioAttributes == null) {
                audioAttributes = new android.media.AudioAttributes.Builder().build();
            }
            mediaPlayer.setAudioAttributes(audioAttributes);
            mediaPlayer.setDataSource(context, uri);
            if (surfaceHolder != null) {
                mediaPlayer.setDisplay(surfaceHolder);
            }
            mediaPlayer.prepare();
            return mediaPlayer;
        } catch (java.io.IOException e) {
            android.util.Log.d(TAG, "create failed:", e);
            return null;
        } catch (java.lang.IllegalArgumentException e2) {
            android.util.Log.d(TAG, "create failed:", e2);
            return null;
        } catch (java.lang.SecurityException e3) {
            android.util.Log.d(TAG, "create failed:", e3);
            return null;
        }
    }

    public static android.media.MediaPlayer create(android.content.Context context, int i) {
        return create(context, i, null, 0);
    }

    public static android.media.MediaPlayer create(android.content.Context context, int i, android.media.AudioAttributes audioAttributes, int i2) {
        try {
            android.content.res.AssetFileDescriptor openRawResourceFd = context.getResources().openRawResourceFd(i);
            if (openRawResourceFd == null) {
                return null;
            }
            android.media.MediaPlayer mediaPlayer = new android.media.MediaPlayer(context, i2);
            if (audioAttributes == null) {
                audioAttributes = new android.media.AudioAttributes.Builder().build();
            }
            mediaPlayer.setAudioAttributes(audioAttributes);
            mediaPlayer.setDataSource(openRawResourceFd.getFileDescriptor(), openRawResourceFd.getStartOffset(), openRawResourceFd.getLength());
            openRawResourceFd.close();
            mediaPlayer.prepare();
            return mediaPlayer;
        } catch (java.io.IOException e) {
            android.util.Log.d(TAG, "create failed:", e);
            return null;
        } catch (java.lang.IllegalArgumentException e2) {
            android.util.Log.d(TAG, "create failed:", e2);
            return null;
        } catch (java.lang.SecurityException e3) {
            android.util.Log.d(TAG, "create failed:", e3);
            return null;
        }
    }

    public void setDataSource(android.content.Context context, android.net.Uri uri) throws java.io.IOException, java.lang.IllegalArgumentException, java.lang.SecurityException, java.lang.IllegalStateException {
        setDataSource(context, uri, (java.util.Map<java.lang.String, java.lang.String>) null, (java.util.List<java.net.HttpCookie>) null);
    }

    public void setDataSource(android.content.Context context, android.net.Uri uri, java.util.Map<java.lang.String, java.lang.String> map, java.util.List<java.net.HttpCookie> list) throws java.io.IOException {
        java.net.CookieHandler cookieHandler;
        if (context == null) {
            throw new java.lang.NullPointerException("context param can not be null.");
        }
        if (uri == null) {
            throw new java.lang.NullPointerException("uri param can not be null.");
        }
        if (list != null && (cookieHandler = java.net.CookieHandler.getDefault()) != null && !(cookieHandler instanceof java.net.CookieManager)) {
            throw new java.lang.IllegalArgumentException("The cookie handler has to be of CookieManager type when cookies are provided.");
        }
        android.content.ContentResolver contentResolver = context.getContentResolver();
        java.lang.String scheme = uri.getScheme();
        java.lang.String authorityWithoutUserId = android.content.ContentProvider.getAuthorityWithoutUserId(uri.getAuthority());
        if ("file".equals(scheme)) {
            setDataSource(uri.getPath());
            return;
        }
        if ("content".equals(scheme) && "settings".equals(authorityWithoutUserId)) {
            int defaultType = android.media.RingtoneManager.getDefaultType(uri);
            android.net.Uri cacheForType = android.media.RingtoneManager.getCacheForType(defaultType, context.getUserId());
            android.net.Uri actualDefaultRingtoneUri = android.media.RingtoneManager.getActualDefaultRingtoneUri(context, defaultType);
            if (attemptDataSource(contentResolver, cacheForType) || attemptDataSource(contentResolver, actualDefaultRingtoneUri)) {
                return;
            }
            setDataSource(uri.toString(), map, list);
            return;
        }
        if (attemptDataSource(contentResolver, uri)) {
            return;
        }
        setDataSource(uri.toString(), map, list);
    }

    public void setDataSource(android.content.Context context, android.net.Uri uri, java.util.Map<java.lang.String, java.lang.String> map) throws java.io.IOException, java.lang.IllegalArgumentException, java.lang.SecurityException, java.lang.IllegalStateException {
        setDataSource(context, uri, map, (java.util.List<java.net.HttpCookie>) null);
    }

    private boolean attemptDataSource(android.content.ContentResolver contentResolver, android.net.Uri uri) {
        android.content.res.AssetFileDescriptor openAssetFileDescriptor;
        boolean z = android.os.SystemProperties.getBoolean("fuse.sys.transcode_player_optimize", false);
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putBoolean("android.provider.extra.ACCEPT_ORIGINAL_MEDIA_FORMAT", true);
        try {
            if (z) {
                openAssetFileDescriptor = contentResolver.openTypedAssetFileDescriptor(uri, "*/*", bundle);
            } else {
                openAssetFileDescriptor = contentResolver.openAssetFileDescriptor(uri, "r");
            }
            try {
                setDataSource(openAssetFileDescriptor);
                if (openAssetFileDescriptor != null) {
                    openAssetFileDescriptor.close();
                }
                return true;
            } catch (java.lang.Throwable th) {
                if (openAssetFileDescriptor != null) {
                    try {
                        openAssetFileDescriptor.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (java.io.IOException | java.lang.NullPointerException | java.lang.SecurityException e) {
            android.util.Log.w(TAG, "Error setting data source via ContentResolver", e);
            return false;
        }
    }

    public void setDataSource(java.lang.String str) throws java.io.IOException, java.lang.IllegalArgumentException, java.lang.SecurityException, java.lang.IllegalStateException {
        setDataSource(str, (java.util.Map<java.lang.String, java.lang.String>) null, (java.util.List<java.net.HttpCookie>) null);
    }

    public void setDataSource(java.lang.String str, java.util.Map<java.lang.String, java.lang.String> map) throws java.io.IOException, java.lang.IllegalArgumentException, java.lang.SecurityException, java.lang.IllegalStateException {
        setDataSource(str, map, (java.util.List<java.net.HttpCookie>) null);
    }

    private void setDataSource(java.lang.String str, java.util.Map<java.lang.String, java.lang.String> map, java.util.List<java.net.HttpCookie> list) throws java.io.IOException, java.lang.IllegalArgumentException, java.lang.SecurityException, java.lang.IllegalStateException {
        java.lang.String[] strArr;
        java.lang.String[] strArr2;
        if (map == null) {
            strArr = null;
            strArr2 = null;
        } else {
            strArr = new java.lang.String[map.size()];
            strArr2 = new java.lang.String[map.size()];
            int i = 0;
            for (java.util.Map.Entry<java.lang.String, java.lang.String> entry : map.entrySet()) {
                strArr[i] = entry.getKey();
                strArr2[i] = entry.getValue();
                i++;
            }
        }
        setDataSource(str, strArr, strArr2, list);
    }

    private void setDataSource(java.lang.String str, java.lang.String[] strArr, java.lang.String[] strArr2, java.util.List<java.net.HttpCookie> list) throws java.io.IOException, java.lang.IllegalArgumentException, java.lang.SecurityException, java.lang.IllegalStateException {
        android.net.Uri parse = android.net.Uri.parse(str);
        java.lang.String scheme = parse.getScheme();
        if ("file".equals(scheme)) {
            str = parse.getPath();
        } else if (scheme != null) {
            nativeSetDataSource(android.media.MediaHTTPService.createHttpServiceBinderIfNecessary(str, list), str, strArr, strArr2);
            return;
        }
        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(new java.io.File(str));
        try {
            setDataSource(fileInputStream.getFD());
            fileInputStream.close();
        } catch (java.lang.Throwable th) {
            try {
                fileInputStream.close();
            } catch (java.lang.Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public void setDataSource(android.content.res.AssetFileDescriptor assetFileDescriptor) throws java.io.IOException, java.lang.IllegalArgumentException, java.lang.IllegalStateException {
        com.android.internal.util.Preconditions.checkNotNull(assetFileDescriptor);
        if (assetFileDescriptor.getDeclaredLength() < 0) {
            setDataSource(assetFileDescriptor.getFileDescriptor());
        } else {
            setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getDeclaredLength());
        }
    }

    public void setDataSource(java.io.FileDescriptor fileDescriptor) throws java.io.IOException, java.lang.IllegalArgumentException, java.lang.IllegalStateException {
        setDataSource(fileDescriptor, 0L, 576460752303423487L);
    }

    public void setDataSource(java.io.FileDescriptor fileDescriptor, long j, long j2) throws java.io.IOException, java.lang.IllegalArgumentException, java.lang.IllegalStateException {
        try {
            android.os.ParcelFileDescriptor convertToModernFd = android.os.FileUtils.convertToModernFd(fileDescriptor);
            try {
                if (convertToModernFd == null) {
                    _setDataSource(fileDescriptor, j, j2);
                } else {
                    _setDataSource(convertToModernFd.getFileDescriptor(), j, j2);
                }
                if (convertToModernFd != null) {
                    convertToModernFd.close();
                }
            } finally {
            }
        } catch (java.io.IOException e) {
            android.util.Log.w(TAG, "Ignoring IO error while setting data source", e);
        }
    }

    public void setDataSource(android.media.MediaDataSource mediaDataSource) throws java.lang.IllegalArgumentException, java.lang.IllegalStateException {
        _setDataSource(mediaDataSource);
    }

    public void prepare() throws java.io.IOException, java.lang.IllegalStateException {
        android.os.Parcel createPlayerIIdParcel = createPlayerIIdParcel();
        try {
            if (_prepare(createPlayerIIdParcel) != 0) {
                android.util.Log.w(TAG, "prepare(): could not set piid " + this.mPlayerIId);
            }
            createPlayerIIdParcel.recycle();
            scanInternalSubtitleTracks();
            synchronized (this.mDrmLock) {
                this.mDrmInfoResolved = true;
            }
        } catch (java.lang.Throwable th) {
            createPlayerIIdParcel.recycle();
            throw th;
        }
    }

    public void prepareAsync() throws java.lang.IllegalStateException {
        android.os.Parcel createPlayerIIdParcel = createPlayerIIdParcel();
        try {
            if (_prepareAsync(createPlayerIIdParcel) != 0) {
                android.util.Log.w(TAG, "prepareAsync(): could not set piid " + this.mPlayerIId);
            }
        } finally {
            createPlayerIIdParcel.recycle();
        }
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [android.media.MediaPlayer$1] */
    public void start() throws java.lang.IllegalStateException {
        final int startDelayMs = getStartDelayMs();
        if (startDelayMs == 0) {
            startImpl();
        } else {
            new java.lang.Thread() { // from class: android.media.MediaPlayer.1
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    try {
                        java.lang.Thread.sleep(startDelayMs);
                    } catch (java.lang.InterruptedException e) {
                        e.printStackTrace();
                    }
                    android.media.MediaPlayer.this.baseSetStartDelayMs(0);
                    try {
                        android.media.MediaPlayer.this.startImpl();
                    } catch (java.lang.IllegalStateException e2) {
                    }
                }
            }.start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startImpl() {
        baseStart(0);
        stayAwake(true);
        tryToEnableNativeRoutingCallback();
        _start();
    }

    private int getAudioStreamType() {
        if (this.mStreamType == Integer.MIN_VALUE) {
            this.mStreamType = _getAudioStreamType();
        }
        return this.mStreamType;
    }

    public void stop() throws java.lang.IllegalStateException {
        stayAwake(false);
        _stop();
        baseStop();
        tryToDisableNativeRoutingCallback();
    }

    public void pause() throws java.lang.IllegalStateException {
        stayAwake(false);
        _pause();
        basePause();
    }

    @Override // android.media.PlayerBase
    void playerStart() {
        start();
    }

    @Override // android.media.PlayerBase
    void playerPause() {
        pause();
    }

    @Override // android.media.PlayerBase
    void playerStop() {
        stop();
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

    /* JADX INFO: Access modifiers changed from: private */
    public void broadcastRoutingChange() {
        android.media.AudioManager.resetAudioPortGeneration();
        synchronized (this.mRoutingChangeListeners) {
            if (this.mEnableSelfRoutingMonitor) {
                baseUpdateDeviceId(getRoutedDevice());
            }
            java.util.Iterator<android.media.NativeRoutingEventHandlerDelegate> it = this.mRoutingChangeListeners.values().iterator();
            while (it.hasNext()) {
                it.next().notifyClient();
            }
        }
    }

    private boolean testEnableNativeRoutingCallbacksLocked() {
        if (this.mRoutingChangeListeners.size() == 0 && !this.mEnableSelfRoutingMonitor) {
            try {
                native_enableDeviceCallback(true);
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

    private void tryToEnableNativeRoutingCallback() {
        synchronized (this.mRoutingChangeListeners) {
            if (!this.mEnableSelfRoutingMonitor) {
                this.mEnableSelfRoutingMonitor = testEnableNativeRoutingCallbacksLocked();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void tryToDisableNativeRoutingCallback() {
        synchronized (this.mRoutingChangeListeners) {
            if (this.mEnableSelfRoutingMonitor) {
                this.mEnableSelfRoutingMonitor = false;
                testDisableNativeRoutingCallbacksLocked();
            }
        }
    }

    private void testDisableNativeRoutingCallbacksLocked() {
        if (this.mRoutingChangeListeners.size() == 0 && !this.mEnableSelfRoutingMonitor) {
            try {
                native_enableDeviceCallback(false);
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
            }
            testDisableNativeRoutingCallbacksLocked();
        }
    }

    public void setWakeMode(android.content.Context context, int i) {
        boolean z = true;
        if (android.os.SystemProperties.getBoolean("audio.offload.ignore_setawake", false)) {
            android.util.Log.w(TAG, "IGNORING setWakeMode " + i);
            return;
        }
        if (this.mWakeLock == null) {
            z = false;
        } else {
            if (!this.mWakeLock.isHeld()) {
                z = false;
            } else {
                this.mWakeLock.release();
            }
            this.mWakeLock = null;
        }
        this.mWakeLock = ((android.os.PowerManager) context.getSystemService(android.content.Context.POWER_SERVICE)).newWakeLock(i | 536870912, android.media.MediaPlayer.class.getName());
        this.mWakeLock.setReferenceCounted(false);
        if (z) {
            this.mWakeLock.acquire();
        }
    }

    public void setScreenOnWhilePlaying(boolean z) {
        if (this.mScreenOnWhilePlaying != z) {
            if (z && this.mSurfaceHolder == null) {
                android.util.Log.w(TAG, "setScreenOnWhilePlaying(true) is ineffective without a SurfaceHolder");
            }
            this.mScreenOnWhilePlaying = z;
            updateSurfaceScreenOn();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stayAwake(boolean z) {
        if (this.mWakeLock != null) {
            if (z && !this.mWakeLock.isHeld()) {
                this.mWakeLock.acquire();
            } else if (!z && this.mWakeLock.isHeld()) {
                this.mWakeLock.release();
            }
        }
        this.mStayAwake = z;
        updateSurfaceScreenOn();
    }

    private void updateSurfaceScreenOn() {
        if (this.mSurfaceHolder != null) {
            this.mSurfaceHolder.setKeepScreenOn(this.mScreenOnWhilePlaying && this.mStayAwake);
        }
    }

    public android.os.PersistableBundle getMetrics() {
        return native_getMetrics();
    }

    public android.media.PlaybackParams easyPlaybackParams(float f, int i) {
        android.media.PlaybackParams playbackParams = new android.media.PlaybackParams();
        playbackParams.allowDefaults();
        switch (i) {
            case 0:
                playbackParams.setSpeed(f).setPitch(1.0f);
                return playbackParams;
            case 1:
                playbackParams.setSpeed(f).setPitch(1.0f).setAudioFallbackMode(2);
                return playbackParams;
            case 2:
                playbackParams.setSpeed(f).setPitch(f);
                return playbackParams;
            default:
                throw new java.lang.IllegalArgumentException("Audio playback mode " + i + " is not supported");
        }
    }

    public void seekTo(long j, int i) {
        if (i < 0 || i > 3) {
            throw new java.lang.IllegalArgumentException("Illegal seek mode: " + i);
        }
        if (j > 2147483647L) {
            android.util.Log.w(TAG, "seekTo offset " + j + " is too large, cap to 2147483647");
            j = 2147483647L;
        } else if (j < -2147483648L) {
            android.util.Log.w(TAG, "seekTo offset " + j + " is too small, cap to -2147483648");
            j = -2147483648L;
        }
        _seekTo(j, i);
    }

    public void seekTo(int i) throws java.lang.IllegalStateException {
        seekTo(i, 0);
    }

    public android.media.MediaTimestamp getTimestamp() {
        try {
            return new android.media.MediaTimestamp(getCurrentPosition() * 1000, java.lang.System.nanoTime(), isPlaying() ? getPlaybackParams().getSpeed() : 0.0f);
        } catch (java.lang.IllegalStateException e) {
            return null;
        }
    }

    public android.media.Metadata getMetadata(boolean z, boolean z2) {
        android.os.Parcel obtain = android.os.Parcel.obtain();
        android.media.Metadata metadata = new android.media.Metadata();
        if (!native_getMetadata(z, z2, obtain)) {
            obtain.recycle();
            return null;
        }
        if (!metadata.parse(obtain)) {
            obtain.recycle();
            return null;
        }
        return metadata;
    }

    public int setMetadataFilter(java.util.Set<java.lang.Integer> set, java.util.Set<java.lang.Integer> set2) {
        android.os.Parcel newRequest = newRequest();
        int dataSize = newRequest.dataSize() + ((set.size() + 1 + 1 + set2.size()) * 4);
        if (newRequest.dataCapacity() < dataSize) {
            newRequest.setDataCapacity(dataSize);
        }
        newRequest.writeInt(set.size());
        java.util.Iterator<java.lang.Integer> it = set.iterator();
        while (it.hasNext()) {
            newRequest.writeInt(it.next().intValue());
        }
        newRequest.writeInt(set2.size());
        java.util.Iterator<java.lang.Integer> it2 = set2.iterator();
        while (it2.hasNext()) {
            newRequest.writeInt(it2.next().intValue());
        }
        return native_setMetadataFilter(newRequest);
    }

    public void release() {
        baseRelease();
        stayAwake(false);
        updateSurfaceScreenOn();
        this.mOnPreparedListener = null;
        this.mOnBufferingUpdateListener = null;
        this.mOnCompletionListener = null;
        this.mOnSeekCompleteListener = null;
        this.mOnErrorListener = null;
        this.mOnInfoListener = null;
        this.mOnVideoSizeChangedListener = null;
        this.mOnTimedTextListener = null;
        this.mOnRtpRxNoticeListener = null;
        this.mOnRtpRxNoticeExecutor = null;
        synchronized (this.mTimeProviderLock) {
            if (this.mTimeProvider != null) {
                this.mTimeProvider.close();
                this.mTimeProvider = null;
            }
        }
        synchronized (this) {
            this.mSubtitleDataListenerDisabled = false;
            this.mExtSubtitleDataListener = null;
            this.mExtSubtitleDataHandler = null;
            this.mOnMediaTimeDiscontinuityListener = null;
            this.mOnMediaTimeDiscontinuityHandler = null;
        }
        this.mOnDrmConfigHelper = null;
        this.mOnDrmInfoHandlerDelegate = null;
        this.mOnDrmPreparedHandlerDelegate = null;
        resetDrmState();
        _release();
    }

    public void reset() {
        this.mSelectedSubtitleTrackIndex = -1;
        synchronized (this.mOpenSubtitleSources) {
            java.util.Iterator<java.io.InputStream> it = this.mOpenSubtitleSources.iterator();
            while (it.hasNext()) {
                try {
                    it.next().close();
                } catch (java.io.IOException e) {
                }
            }
            this.mOpenSubtitleSources.clear();
        }
        if (this.mSubtitleController != null) {
            this.mSubtitleController.reset();
        }
        synchronized (this.mTimeProviderLock) {
            if (this.mTimeProvider != null) {
                this.mTimeProvider.close();
                this.mTimeProvider = null;
            }
        }
        stayAwake(false);
        _reset();
        if (this.mEventHandler != null) {
            this.mEventHandler.removeCallbacksAndMessages(null);
        }
        synchronized (this.mIndexTrackPairs) {
            this.mIndexTrackPairs.clear();
            this.mInbandTrackIndices.clear();
        }
        resetDrmState();
    }

    public void notifyAt(long j) {
        _notifyAt(j);
    }

    public void setAudioStreamType(int i) {
        deprecateStreamTypeForPlayback(i, TAG, "setAudioStreamType()");
        baseUpdateAudioAttributes(new android.media.AudioAttributes.Builder().setInternalLegacyStreamType(i).build());
        _setAudioStreamType(i);
        this.mStreamType = i;
    }

    public void setAudioAttributes(android.media.AudioAttributes audioAttributes) throws java.lang.IllegalArgumentException {
        if (audioAttributes == null) {
            throw new java.lang.IllegalArgumentException("Cannot set AudioAttributes to null");
        }
        baseUpdateAudioAttributes(audioAttributes);
        android.os.Parcel obtain = android.os.Parcel.obtain();
        audioAttributes.writeToParcel(obtain, 1);
        setParameter(1400, obtain);
        obtain.recycle();
    }

    public void setVolume(float f, float f2) {
        baseSetVolume(f, f2);
    }

    @Override // android.media.PlayerBase
    void playerSetVolume(boolean z, float f, float f2) {
        if (z) {
            f = 0.0f;
        }
        if (z) {
            f2 = 0.0f;
        }
        _setVolume(f, f2);
    }

    public void setVolume(float f) {
        setVolume(f, f);
    }

    public void setAudioSessionId(int i) throws java.lang.IllegalArgumentException, java.lang.IllegalStateException {
        native_setAudioSessionId(i);
        baseUpdateSessionId(i);
    }

    public void setAuxEffectSendLevel(float f) {
        baseSetAuxEffectSendLevel(f);
    }

    @Override // android.media.PlayerBase
    int playerSetAuxEffectSendLevel(boolean z, float f) {
        if (z) {
            f = 0.0f;
        }
        _setAuxEffectSendLevel(f);
        return 0;
    }

    public static class TrackInfo implements android.os.Parcelable {
        static final android.os.Parcelable.Creator<android.media.MediaPlayer.TrackInfo> CREATOR = new android.os.Parcelable.Creator<android.media.MediaPlayer.TrackInfo>() { // from class: android.media.MediaPlayer.TrackInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.media.MediaPlayer.TrackInfo createFromParcel(android.os.Parcel parcel) {
                return new android.media.MediaPlayer.TrackInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.media.MediaPlayer.TrackInfo[] newArray(int i) {
                return new android.media.MediaPlayer.TrackInfo[i];
            }
        };
        public static final int MEDIA_TRACK_TYPE_AUDIO = 2;
        public static final int MEDIA_TRACK_TYPE_METADATA = 5;
        public static final int MEDIA_TRACK_TYPE_SUBTITLE = 4;
        public static final int MEDIA_TRACK_TYPE_TIMEDTEXT = 3;
        public static final int MEDIA_TRACK_TYPE_UNKNOWN = 0;
        public static final int MEDIA_TRACK_TYPE_VIDEO = 1;
        final android.media.MediaFormat mFormat;
        final int mTrackType;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface TrackType {
        }

        public int getTrackType() {
            return this.mTrackType;
        }

        public java.lang.String getLanguage() {
            java.lang.String string = this.mFormat.getString("language");
            return string == null ? "und" : string;
        }

        public boolean hasHapticChannels() {
            return this.mFormat != null && this.mFormat.containsKey(android.media.MediaFormat.KEY_HAPTIC_CHANNEL_COUNT) && this.mFormat.getInteger(android.media.MediaFormat.KEY_HAPTIC_CHANNEL_COUNT) > 0;
        }

        public android.media.MediaFormat getFormat() {
            if (this.mTrackType == 3 || this.mTrackType == 4) {
                return this.mFormat;
            }
            return null;
        }

        TrackInfo(android.os.Parcel parcel) {
            this.mTrackType = parcel.readInt();
            this.mFormat = android.media.MediaFormat.createSubtitleFormat(parcel.readString(), parcel.readString());
            if (this.mTrackType == 4) {
                this.mFormat.setInteger(android.media.MediaFormat.KEY_IS_AUTOSELECT, parcel.readInt());
                this.mFormat.setInteger(android.media.MediaFormat.KEY_IS_DEFAULT, parcel.readInt());
                this.mFormat.setInteger(android.media.MediaFormat.KEY_IS_FORCED_SUBTITLE, parcel.readInt());
            } else if (this.mTrackType == 2 && parcel.readBoolean()) {
                this.mFormat.setInteger(android.media.MediaFormat.KEY_HAPTIC_CHANNEL_COUNT, parcel.readInt());
            }
        }

        TrackInfo(int i, android.media.MediaFormat mediaFormat) {
            this.mTrackType = i;
            this.mFormat = mediaFormat;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mTrackType);
            parcel.writeString(this.mFormat.getString(android.media.MediaFormat.KEY_MIME));
            parcel.writeString(getLanguage());
            if (this.mTrackType == 4) {
                parcel.writeInt(this.mFormat.getInteger(android.media.MediaFormat.KEY_IS_AUTOSELECT));
                parcel.writeInt(this.mFormat.getInteger(android.media.MediaFormat.KEY_IS_DEFAULT));
                parcel.writeInt(this.mFormat.getInteger(android.media.MediaFormat.KEY_IS_FORCED_SUBTITLE));
            } else if (this.mTrackType == 2) {
                boolean containsKey = this.mFormat.containsKey(android.media.MediaFormat.KEY_HAPTIC_CHANNEL_COUNT);
                parcel.writeBoolean(containsKey);
                if (containsKey) {
                    parcel.writeInt(this.mFormat.getInteger(android.media.MediaFormat.KEY_HAPTIC_CHANNEL_COUNT));
                }
            }
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
            sb.append(getClass().getName());
            sb.append('{');
            switch (this.mTrackType) {
                case 1:
                    sb.append("VIDEO");
                    break;
                case 2:
                    sb.append("AUDIO");
                    break;
                case 3:
                    sb.append("TIMEDTEXT");
                    break;
                case 4:
                    sb.append("SUBTITLE");
                    break;
                default:
                    sb.append("UNKNOWN");
                    break;
            }
            sb.append(", " + this.mFormat.toString());
            sb.append("}");
            return sb.toString();
        }
    }

    public android.media.MediaPlayer.TrackInfo[] getTrackInfo() throws java.lang.IllegalStateException {
        android.media.MediaPlayer.TrackInfo[] trackInfoArr;
        android.media.MediaPlayer.TrackInfo[] inbandTrackInfo = getInbandTrackInfo();
        synchronized (this.mIndexTrackPairs) {
            int size = this.mIndexTrackPairs.size();
            trackInfoArr = new android.media.MediaPlayer.TrackInfo[size];
            for (int i = 0; i < size; i++) {
                android.util.Pair<java.lang.Integer, android.media.SubtitleTrack> pair = this.mIndexTrackPairs.get(i);
                if (pair.first != null) {
                    trackInfoArr[i] = inbandTrackInfo[pair.first.intValue()];
                } else {
                    android.media.SubtitleTrack subtitleTrack = pair.second;
                    trackInfoArr[i] = new android.media.MediaPlayer.TrackInfo(subtitleTrack.getTrackType(), subtitleTrack.getFormat());
                }
            }
        }
        return trackInfoArr;
    }

    private android.media.MediaPlayer.TrackInfo[] getInbandTrackInfo() throws java.lang.IllegalStateException {
        android.os.Parcel obtain = android.os.Parcel.obtain();
        android.os.Parcel obtain2 = android.os.Parcel.obtain();
        try {
            obtain.writeInterfaceToken(IMEDIA_PLAYER);
            obtain.writeInt(1);
            invoke(obtain, obtain2);
            return (android.media.MediaPlayer.TrackInfo[]) obtain2.createTypedArray(android.media.MediaPlayer.TrackInfo.CREATOR);
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }

    private static boolean availableMimeTypeForExternalSource(java.lang.String str) {
        if ("application/x-subrip".equals(str)) {
            return true;
        }
        return false;
    }

    public void setSubtitleAnchor(android.media.SubtitleController subtitleController, android.media.SubtitleController.Anchor anchor) {
        this.mSubtitleController = subtitleController;
        this.mSubtitleController.setAnchor(anchor);
    }

    private synchronized void setSubtitleAnchor() {
        if (this.mSubtitleController == null && android.app.ActivityThread.currentApplication() != null) {
            final android.media.MediaPlayer.TimeProvider timeProvider = (android.media.MediaPlayer.TimeProvider) getMediaTimeProvider();
            final android.os.HandlerThread handlerThread = new android.os.HandlerThread("SetSubtitleAnchorThread");
            handlerThread.start();
            new android.os.Handler(handlerThread.getLooper()).post(new java.lang.Runnable() { // from class: android.media.MediaPlayer.2
                @Override // java.lang.Runnable
                public void run() {
                    android.app.Application currentApplication = android.app.ActivityThread.currentApplication();
                    android.media.MediaPlayer.this.mSubtitleController = new android.media.SubtitleController(currentApplication, timeProvider, android.media.MediaPlayer.this);
                    android.media.MediaPlayer.this.mSubtitleController.setAnchor(new android.media.SubtitleController.Anchor() { // from class: android.media.MediaPlayer.2.1
                        @Override // android.media.SubtitleController.Anchor
                        public void setSubtitleWidget(android.media.SubtitleTrack.RenderingWidget renderingWidget) {
                        }

                        @Override // android.media.SubtitleController.Anchor
                        public android.os.Looper getSubtitleLooper() {
                            return timeProvider.mEventHandler.getLooper();
                        }
                    });
                    handlerThread.getLooper().quitSafely();
                }
            });
            try {
                handlerThread.join();
            } catch (java.lang.InterruptedException e) {
                java.lang.Thread.currentThread().interrupt();
                android.util.Log.w(TAG, "failed to join SetSubtitleAnchorThread");
            }
        }
    }

    @Override // android.media.SubtitleController.Listener
    public void onSubtitleTrackSelected(android.media.SubtitleTrack subtitleTrack) {
        if (this.mSelectedSubtitleTrackIndex >= 0) {
            try {
                selectOrDeselectInbandTrack(this.mSelectedSubtitleTrackIndex, false);
            } catch (java.lang.IllegalStateException e) {
            }
            this.mSelectedSubtitleTrackIndex = -1;
        }
        synchronized (this) {
            this.mSubtitleDataListenerDisabled = true;
        }
        if (subtitleTrack == null) {
            return;
        }
        synchronized (this.mIndexTrackPairs) {
            java.util.Iterator<android.util.Pair<java.lang.Integer, android.media.SubtitleTrack>> it = this.mIndexTrackPairs.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                android.util.Pair<java.lang.Integer, android.media.SubtitleTrack> next = it.next();
                if (next.first != null && next.second == subtitleTrack) {
                    this.mSelectedSubtitleTrackIndex = next.first.intValue();
                    break;
                }
            }
        }
        if (this.mSelectedSubtitleTrackIndex >= 0) {
            try {
                selectOrDeselectInbandTrack(this.mSelectedSubtitleTrackIndex, true);
            } catch (java.lang.IllegalStateException e2) {
            }
            synchronized (this) {
                this.mSubtitleDataListenerDisabled = false;
            }
        }
    }

    public void addSubtitleSource(final java.io.InputStream inputStream, final android.media.MediaFormat mediaFormat) throws java.lang.IllegalStateException {
        if (inputStream != null) {
            synchronized (this.mOpenSubtitleSources) {
                this.mOpenSubtitleSources.add(inputStream);
            }
        } else {
            android.util.Log.w(TAG, "addSubtitleSource called with null InputStream");
        }
        getMediaTimeProvider();
        final android.os.HandlerThread handlerThread = new android.os.HandlerThread("SubtitleReadThread", 9);
        handlerThread.start();
        new android.os.Handler(handlerThread.getLooper()).post(new java.lang.Runnable() { // from class: android.media.MediaPlayer.4
            private int addTrack() {
                android.media.SubtitleTrack addTrack;
                if (inputStream == null || android.media.MediaPlayer.this.mSubtitleController == null || (addTrack = android.media.MediaPlayer.this.mSubtitleController.addTrack(mediaFormat)) == null) {
                    return 901;
                }
                java.util.Scanner scanner = new java.util.Scanner(inputStream, android.media.tv.SignalingDataInfo.CONTENT_ENCODING_UTF_8);
                java.lang.String next = scanner.useDelimiter("\\A").next();
                synchronized (android.media.MediaPlayer.this.mOpenSubtitleSources) {
                    android.media.MediaPlayer.this.mOpenSubtitleSources.remove(inputStream);
                }
                scanner.close();
                synchronized (android.media.MediaPlayer.this.mIndexTrackPairs) {
                    android.media.MediaPlayer.this.mIndexTrackPairs.add(android.util.Pair.create(null, addTrack));
                }
                synchronized (android.media.MediaPlayer.this.mTimeProviderLock) {
                    if (android.media.MediaPlayer.this.mTimeProvider != null) {
                        android.os.Handler handler = android.media.MediaPlayer.this.mTimeProvider.mEventHandler;
                        handler.sendMessage(handler.obtainMessage(1, 4, 0, android.util.Pair.create(addTrack, next.getBytes())));
                    }
                }
                return 803;
            }

            @Override // java.lang.Runnable
            public void run() {
                int addTrack = addTrack();
                if (android.media.MediaPlayer.this.mEventHandler != null) {
                    android.media.MediaPlayer.this.mEventHandler.sendMessage(android.media.MediaPlayer.this.mEventHandler.obtainMessage(200, addTrack, 0, null));
                }
                handlerThread.getLooper().quitSafely();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scanInternalSubtitleTracks() {
        setSubtitleAnchor();
        populateInbandTracks();
        if (this.mSubtitleController != null) {
            this.mSubtitleController.selectDefaultTrack();
        }
    }

    private void populateInbandTracks() {
        android.media.MediaPlayer.TrackInfo[] inbandTrackInfo = getInbandTrackInfo();
        synchronized (this.mIndexTrackPairs) {
            for (int i = 0; i < inbandTrackInfo.length; i++) {
                if (!this.mInbandTrackIndices.get(i)) {
                    this.mInbandTrackIndices.set(i);
                    if (inbandTrackInfo[i] == null) {
                        android.util.Log.w(TAG, "unexpected NULL track at index " + i);
                    }
                    if (inbandTrackInfo[i] != null && inbandTrackInfo[i].getTrackType() == 4) {
                        this.mIndexTrackPairs.add(android.util.Pair.create(java.lang.Integer.valueOf(i), this.mSubtitleController.addTrack(inbandTrackInfo[i].getFormat())));
                    } else {
                        this.mIndexTrackPairs.add(android.util.Pair.create(java.lang.Integer.valueOf(i), null));
                    }
                }
            }
        }
    }

    public void addTimedTextSource(java.lang.String str, java.lang.String str2) throws java.io.IOException, java.lang.IllegalArgumentException, java.lang.IllegalStateException {
        if (!availableMimeTypeForExternalSource(str2)) {
            throw new java.lang.IllegalArgumentException("Illegal mimeType for timed text source: " + str2);
        }
        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(new java.io.File(str));
        try {
            addTimedTextSource(fileInputStream.getFD(), str2);
            fileInputStream.close();
        } catch (java.lang.Throwable th) {
            try {
                fileInputStream.close();
            } catch (java.lang.Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public void addTimedTextSource(android.content.Context context, android.net.Uri uri, java.lang.String str) throws java.io.IOException, java.lang.IllegalArgumentException, java.lang.IllegalStateException {
        java.lang.String scheme = uri.getScheme();
        if (scheme == null || scheme.equals("file")) {
            addTimedTextSource(uri.getPath(), str);
            return;
        }
        android.content.res.AssetFileDescriptor assetFileDescriptor = null;
        try {
            boolean z = android.os.SystemProperties.getBoolean("fuse.sys.transcode_player_optimize", false);
            android.content.ContentResolver contentResolver = context.getContentResolver();
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putBoolean("android.provider.extra.ACCEPT_ORIGINAL_MEDIA_FORMAT", true);
            assetFileDescriptor = z ? contentResolver.openTypedAssetFileDescriptor(uri, "*/*", bundle) : contentResolver.openAssetFileDescriptor(uri, "r");
            if (assetFileDescriptor == null) {
                if (assetFileDescriptor != null) {
                    assetFileDescriptor.close();
                }
            } else {
                addTimedTextSource(assetFileDescriptor.getFileDescriptor(), str);
                if (assetFileDescriptor != null) {
                    assetFileDescriptor.close();
                }
            }
        } catch (java.io.IOException e) {
            if (assetFileDescriptor == null) {
                return;
            }
            assetFileDescriptor.close();
        } catch (java.lang.SecurityException e2) {
            if (assetFileDescriptor == null) {
                return;
            }
            assetFileDescriptor.close();
        } catch (java.lang.Throwable th) {
            if (assetFileDescriptor != null) {
                assetFileDescriptor.close();
            }
            throw th;
        }
    }

    public void addTimedTextSource(java.io.FileDescriptor fileDescriptor, java.lang.String str) throws java.lang.IllegalArgumentException, java.lang.IllegalStateException {
        addTimedTextSource(fileDescriptor, 0L, 576460752303423487L, str);
    }

    public void addTimedTextSource(java.io.FileDescriptor fileDescriptor, final long j, final long j2, java.lang.String str) throws java.lang.IllegalArgumentException, java.lang.IllegalStateException {
        if (!availableMimeTypeForExternalSource(str)) {
            throw new java.lang.IllegalArgumentException("Illegal mimeType for timed text source: " + str);
        }
        try {
            final java.io.FileDescriptor dup = android.system.Os.dup(fileDescriptor);
            android.media.MediaFormat mediaFormat = new android.media.MediaFormat();
            mediaFormat.setString(android.media.MediaFormat.KEY_MIME, str);
            mediaFormat.setInteger(android.media.MediaFormat.KEY_IS_TIMED_TEXT, 1);
            if (this.mSubtitleController == null) {
                setSubtitleAnchor();
            }
            if (!this.mSubtitleController.hasRendererFor(mediaFormat)) {
                this.mSubtitleController.registerRenderer(new android.media.SRTRenderer(android.app.ActivityThread.currentApplication(), this.mEventHandler));
            }
            final android.media.SubtitleTrack addTrack = this.mSubtitleController.addTrack(mediaFormat);
            synchronized (this.mIndexTrackPairs) {
                this.mIndexTrackPairs.add(android.util.Pair.create(null, addTrack));
            }
            getMediaTimeProvider();
            final android.os.HandlerThread handlerThread = new android.os.HandlerThread("TimedTextReadThread", 9);
            handlerThread.start();
            new android.os.Handler(handlerThread.getLooper()).post(new java.lang.Runnable() { // from class: android.media.MediaPlayer.5
                private int addTrack() {
                    java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
                    try {
                        try {
                            android.system.Os.lseek(dup, j, android.system.OsConstants.SEEK_SET);
                            byte[] bArr = new byte[4096];
                            long j3 = 0;
                            while (j3 < j2) {
                                int read = libcore.io.IoBridge.read(dup, bArr, 0, (int) java.lang.Math.min(4096, j2 - j3));
                                if (read < 0) {
                                    break;
                                }
                                byteArrayOutputStream.write(bArr, 0, read);
                                j3 += read;
                            }
                            synchronized (android.media.MediaPlayer.this.mTimeProviderLock) {
                                if (android.media.MediaPlayer.this.mTimeProvider != null) {
                                    android.os.Handler handler = android.media.MediaPlayer.this.mTimeProvider.mEventHandler;
                                    handler.sendMessage(handler.obtainMessage(1, 4, 0, android.util.Pair.create(addTrack, byteArrayOutputStream.toByteArray())));
                                }
                            }
                            try {
                                android.system.Os.close(dup);
                                return 803;
                            } catch (android.system.ErrnoException e) {
                                android.util.Log.e(android.media.MediaPlayer.TAG, e.getMessage(), e);
                                return 803;
                            }
                        } catch (java.lang.Throwable th) {
                            try {
                                android.system.Os.close(dup);
                            } catch (android.system.ErrnoException e2) {
                                android.util.Log.e(android.media.MediaPlayer.TAG, e2.getMessage(), e2);
                            }
                            throw th;
                        }
                    } catch (java.lang.Exception e3) {
                        android.util.Log.e(android.media.MediaPlayer.TAG, e3.getMessage(), e3);
                        try {
                            android.system.Os.close(dup);
                            return 900;
                        } catch (android.system.ErrnoException e4) {
                            android.util.Log.e(android.media.MediaPlayer.TAG, e4.getMessage(), e4);
                            return 900;
                        }
                    }
                }

                @Override // java.lang.Runnable
                public void run() {
                    int addTrack2 = addTrack();
                    if (android.media.MediaPlayer.this.mEventHandler != null) {
                        android.media.MediaPlayer.this.mEventHandler.sendMessage(android.media.MediaPlayer.this.mEventHandler.obtainMessage(200, addTrack2, 0, null));
                    }
                    handlerThread.getLooper().quitSafely();
                }
            });
        } catch (android.system.ErrnoException e) {
            android.util.Log.e(TAG, e.getMessage(), e);
            throw new java.lang.RuntimeException(e);
        }
    }

    public int getSelectedTrack(int i) throws java.lang.IllegalStateException {
        android.media.SubtitleTrack selectedTrack;
        if (this.mSubtitleController != null && ((i == 4 || i == 3) && (selectedTrack = this.mSubtitleController.getSelectedTrack()) != null)) {
            synchronized (this.mIndexTrackPairs) {
                for (int i2 = 0; i2 < this.mIndexTrackPairs.size(); i2++) {
                    if (this.mIndexTrackPairs.get(i2).second == selectedTrack && selectedTrack.getTrackType() == i) {
                        return i2;
                    }
                }
            }
        }
        android.os.Parcel obtain = android.os.Parcel.obtain();
        android.os.Parcel obtain2 = android.os.Parcel.obtain();
        try {
            obtain.writeInterfaceToken(IMEDIA_PLAYER);
            obtain.writeInt(7);
            obtain.writeInt(i);
            invoke(obtain, obtain2);
            int readInt = obtain2.readInt();
            synchronized (this.mIndexTrackPairs) {
                for (int i3 = 0; i3 < this.mIndexTrackPairs.size(); i3++) {
                    android.util.Pair<java.lang.Integer, android.media.SubtitleTrack> pair = this.mIndexTrackPairs.get(i3);
                    if (pair.first != null && pair.first.intValue() == readInt) {
                        return i3;
                    }
                }
                obtain.recycle();
                obtain2.recycle();
                return -1;
            }
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }

    public void selectTrack(int i) throws java.lang.IllegalStateException {
        selectOrDeselectTrack(i, true);
    }

    public void deselectTrack(int i) throws java.lang.IllegalStateException {
        selectOrDeselectTrack(i, false);
    }

    private void selectOrDeselectTrack(int i, boolean z) throws java.lang.IllegalStateException {
        populateInbandTracks();
        try {
            android.util.Pair<java.lang.Integer, android.media.SubtitleTrack> pair = this.mIndexTrackPairs.get(i);
            android.media.SubtitleTrack subtitleTrack = pair.second;
            if (subtitleTrack == null) {
                selectOrDeselectInbandTrack(pair.first.intValue(), z);
                return;
            }
            if (this.mSubtitleController == null) {
                return;
            }
            if (!z) {
                if (this.mSubtitleController.getSelectedTrack() == subtitleTrack) {
                    this.mSubtitleController.selectTrack(null);
                    return;
                } else {
                    android.util.Log.w(TAG, "trying to deselect track that was not selected");
                    return;
                }
            }
            if (subtitleTrack.getTrackType() == 3) {
                int selectedTrack = getSelectedTrack(3);
                synchronized (this.mIndexTrackPairs) {
                    if (selectedTrack >= 0) {
                        if (selectedTrack < this.mIndexTrackPairs.size()) {
                            android.util.Pair<java.lang.Integer, android.media.SubtitleTrack> pair2 = this.mIndexTrackPairs.get(selectedTrack);
                            if (pair2.first != null && pair2.second == null) {
                                selectOrDeselectInbandTrack(pair2.first.intValue(), false);
                            }
                        }
                    }
                }
            }
            this.mSubtitleController.selectTrack(subtitleTrack);
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
        }
    }

    private void selectOrDeselectInbandTrack(int i, boolean z) throws java.lang.IllegalStateException {
        android.os.Parcel obtain = android.os.Parcel.obtain();
        android.os.Parcel obtain2 = android.os.Parcel.obtain();
        try {
            obtain.writeInterfaceToken(IMEDIA_PLAYER);
            obtain.writeInt(z ? 4 : 5);
            obtain.writeInt(i);
            invoke(obtain, obtain2);
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }

    public void setRetransmitEndpoint(java.net.InetSocketAddress inetSocketAddress) throws java.lang.IllegalStateException, java.lang.IllegalArgumentException {
        java.lang.String str;
        int i;
        if (inetSocketAddress == null) {
            str = null;
            i = 0;
        } else {
            str = inetSocketAddress.getAddress().getHostAddress();
            i = inetSocketAddress.getPort();
        }
        int native_setRetransmitEndpoint = native_setRetransmitEndpoint(str, i);
        if (native_setRetransmitEndpoint != 0) {
            throw new java.lang.IllegalArgumentException("Illegal re-transmit endpoint; native ret " + native_setRetransmitEndpoint);
        }
    }

    protected void finalize() {
        tryToDisableNativeRoutingCallback();
        baseRelease();
        native_finalize();
    }

    public android.media.MediaTimeProvider getMediaTimeProvider() {
        android.media.MediaPlayer.TimeProvider timeProvider;
        synchronized (this.mTimeProviderLock) {
            if (this.mTimeProvider == null) {
                this.mTimeProvider = new android.media.MediaPlayer.TimeProvider(this);
            }
            timeProvider = this.mTimeProvider;
        }
        return timeProvider;
    }

    /* JADX INFO: Access modifiers changed from: private */
    class EventHandler extends android.os.Handler {
        private android.media.MediaPlayer mMediaPlayer;

        public EventHandler(android.media.MediaPlayer mediaPlayer, android.os.Looper looper) {
            super(looper);
            this.mMediaPlayer = mediaPlayer;
        }

        /* JADX WARN: Finally extract failed */
        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            boolean z;
            android.media.MediaPlayer.OnDrmInfoHandlerDelegate onDrmInfoHandlerDelegate;
            final android.media.MediaPlayer.OnMediaTimeDiscontinuityListener onMediaTimeDiscontinuityListener;
            android.os.Handler handler;
            final android.media.MediaTimestamp mediaTimestamp;
            if (this.mMediaPlayer.mNativeContext == 0) {
                android.util.Log.w(android.media.MediaPlayer.TAG, "mediaplayer went away with unhandled events");
                return;
            }
            android.media.MediaPlayer.DrmInfo drmInfo = null;
            switch (message.what) {
                case 0:
                    return;
                case 1:
                    try {
                        android.media.MediaPlayer.this.scanInternalSubtitleTracks();
                    } catch (java.lang.RuntimeException e) {
                        sendMessage(obtainMessage(100, 1, android.media.MediaPlayer.MEDIA_ERROR_UNSUPPORTED, null));
                    }
                    android.media.MediaPlayer.OnPreparedListener onPreparedListener = android.media.MediaPlayer.this.mOnPreparedListener;
                    if (onPreparedListener != null) {
                        onPreparedListener.onPrepared(this.mMediaPlayer);
                        return;
                    }
                    return;
                case 2:
                    android.media.MediaPlayer.this.mOnCompletionInternalListener.onCompletion(this.mMediaPlayer);
                    android.media.MediaPlayer.OnCompletionListener onCompletionListener = android.media.MediaPlayer.this.mOnCompletionListener;
                    if (onCompletionListener != null) {
                        onCompletionListener.onCompletion(this.mMediaPlayer);
                    }
                    android.media.MediaPlayer.this.stayAwake(false);
                    return;
                case 3:
                    android.media.MediaPlayer.OnBufferingUpdateListener onBufferingUpdateListener = android.media.MediaPlayer.this.mOnBufferingUpdateListener;
                    if (onBufferingUpdateListener != null) {
                        onBufferingUpdateListener.onBufferingUpdate(this.mMediaPlayer, message.arg1);
                        return;
                    }
                    return;
                case 4:
                    android.media.MediaPlayer.OnSeekCompleteListener onSeekCompleteListener = android.media.MediaPlayer.this.mOnSeekCompleteListener;
                    if (onSeekCompleteListener != null) {
                        onSeekCompleteListener.onSeekComplete(this.mMediaPlayer);
                        break;
                    }
                    break;
                case 5:
                    android.media.MediaPlayer.OnVideoSizeChangedListener onVideoSizeChangedListener = android.media.MediaPlayer.this.mOnVideoSizeChangedListener;
                    if (onVideoSizeChangedListener != null) {
                        onVideoSizeChangedListener.onVideoSizeChanged(this.mMediaPlayer, message.arg1, message.arg2);
                        return;
                    }
                    return;
                case 6:
                case 7:
                    android.media.MediaPlayer.TimeProvider timeProvider = android.media.MediaPlayer.this.mTimeProvider;
                    if (timeProvider != null) {
                        timeProvider.onPaused(message.what == 7);
                        return;
                    }
                    return;
                case 8:
                    android.media.MediaPlayer.TimeProvider timeProvider2 = android.media.MediaPlayer.this.mTimeProvider;
                    if (timeProvider2 != null) {
                        timeProvider2.onStopped();
                        return;
                    }
                    return;
                case 9:
                    break;
                case 98:
                    android.media.MediaPlayer.TimeProvider timeProvider3 = android.media.MediaPlayer.this.mTimeProvider;
                    if (timeProvider3 != null) {
                        timeProvider3.onNotifyTime();
                        return;
                    }
                    return;
                case 99:
                    android.media.MediaPlayer.OnTimedTextListener onTimedTextListener = android.media.MediaPlayer.this.mOnTimedTextListener;
                    if (onTimedTextListener == null) {
                        return;
                    }
                    if (message.obj == null) {
                        onTimedTextListener.onTimedText(this.mMediaPlayer, null);
                        return;
                    } else {
                        if (message.obj instanceof android.os.Parcel) {
                            android.os.Parcel parcel = (android.os.Parcel) message.obj;
                            android.media.TimedText timedText = new android.media.TimedText(parcel);
                            parcel.recycle();
                            onTimedTextListener.onTimedText(this.mMediaPlayer, timedText);
                            return;
                        }
                        return;
                    }
                case 100:
                    android.util.Log.e(android.media.MediaPlayer.TAG, "Error (" + message.arg1 + "," + message.arg2 + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
                    android.media.MediaPlayer.OnErrorListener onErrorListener = android.media.MediaPlayer.this.mOnErrorListener;
                    if (onErrorListener == null) {
                        z = false;
                    } else {
                        z = onErrorListener.onError(this.mMediaPlayer, message.arg1, message.arg2);
                    }
                    android.media.MediaPlayer.this.mOnCompletionInternalListener.onCompletion(this.mMediaPlayer);
                    android.media.MediaPlayer.OnCompletionListener onCompletionListener2 = android.media.MediaPlayer.this.mOnCompletionListener;
                    if (onCompletionListener2 != null && !z) {
                        onCompletionListener2.onCompletion(this.mMediaPlayer);
                    }
                    android.media.MediaPlayer.this.stayAwake(false);
                    return;
                case 200:
                    switch (message.arg1) {
                        case 700:
                            android.util.Log.i(android.media.MediaPlayer.TAG, "Info (" + message.arg1 + "," + message.arg2 + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
                            break;
                        case 701:
                        case 702:
                            android.media.MediaPlayer.TimeProvider timeProvider4 = android.media.MediaPlayer.this.mTimeProvider;
                            if (timeProvider4 != null) {
                                timeProvider4.onBuffering(message.arg1 == 701);
                                break;
                            }
                            break;
                        case 802:
                            try {
                                android.media.MediaPlayer.this.scanInternalSubtitleTracks();
                            } catch (java.lang.RuntimeException e2) {
                                sendMessage(obtainMessage(100, 1, android.media.MediaPlayer.MEDIA_ERROR_UNSUPPORTED, null));
                            }
                        case 803:
                            message.arg1 = 802;
                            if (android.media.MediaPlayer.this.mSubtitleController != null) {
                                android.media.MediaPlayer.this.mSubtitleController.selectDefaultTrack();
                                break;
                            }
                            break;
                    }
                    android.media.MediaPlayer.OnInfoListener onInfoListener = android.media.MediaPlayer.this.mOnInfoListener;
                    if (onInfoListener != null) {
                        onInfoListener.onInfo(this.mMediaPlayer, message.arg1, message.arg2);
                        return;
                    }
                    return;
                case 201:
                    synchronized (this) {
                        if (android.media.MediaPlayer.this.mSubtitleDataListenerDisabled) {
                            return;
                        }
                        final android.media.MediaPlayer.OnSubtitleDataListener onSubtitleDataListener = android.media.MediaPlayer.this.mExtSubtitleDataListener;
                        android.os.Handler handler2 = android.media.MediaPlayer.this.mExtSubtitleDataHandler;
                        if (message.obj instanceof android.os.Parcel) {
                            android.os.Parcel parcel2 = (android.os.Parcel) message.obj;
                            final android.media.SubtitleData subtitleData = new android.media.SubtitleData(parcel2);
                            parcel2.recycle();
                            android.media.MediaPlayer.this.mIntSubtitleDataListener.onSubtitleData(this.mMediaPlayer, subtitleData);
                            if (onSubtitleDataListener != null) {
                                if (handler2 == null) {
                                    onSubtitleDataListener.onSubtitleData(this.mMediaPlayer, subtitleData);
                                    return;
                                } else {
                                    handler2.post(new java.lang.Runnable() { // from class: android.media.MediaPlayer.EventHandler.1
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            onSubtitleDataListener.onSubtitleData(android.media.MediaPlayer.EventHandler.this.mMediaPlayer, subtitleData);
                                        }
                                    });
                                    return;
                                }
                            }
                            return;
                        }
                        return;
                    }
                case 202:
                    android.media.MediaPlayer.OnTimedMetaDataAvailableListener onTimedMetaDataAvailableListener = android.media.MediaPlayer.this.mOnTimedMetaDataAvailableListener;
                    if (onTimedMetaDataAvailableListener != null && (message.obj instanceof android.os.Parcel)) {
                        android.os.Parcel parcel3 = (android.os.Parcel) message.obj;
                        android.media.TimedMetaData createTimedMetaDataFromParcel = android.media.TimedMetaData.createTimedMetaDataFromParcel(parcel3);
                        parcel3.recycle();
                        onTimedMetaDataAvailableListener.onTimedMetaDataAvailable(this.mMediaPlayer, createTimedMetaDataFromParcel);
                        return;
                    }
                    return;
                case 210:
                    android.util.Log.v(android.media.MediaPlayer.TAG, "MEDIA_DRM_INFO " + android.media.MediaPlayer.this.mOnDrmInfoHandlerDelegate);
                    if (message.obj == null) {
                        android.util.Log.w(android.media.MediaPlayer.TAG, "MEDIA_DRM_INFO msg.obj=NULL");
                        return;
                    }
                    if (message.obj instanceof android.os.Parcel) {
                        synchronized (android.media.MediaPlayer.this.mDrmLock) {
                            if (android.media.MediaPlayer.this.mOnDrmInfoHandlerDelegate != null && android.media.MediaPlayer.this.mDrmInfo != null) {
                                drmInfo = android.media.MediaPlayer.this.mDrmInfo.makeCopy();
                            }
                            onDrmInfoHandlerDelegate = android.media.MediaPlayer.this.mOnDrmInfoHandlerDelegate;
                        }
                        if (onDrmInfoHandlerDelegate != null) {
                            onDrmInfoHandlerDelegate.notifyClient(drmInfo);
                            return;
                        }
                        return;
                    }
                    android.util.Log.w(android.media.MediaPlayer.TAG, "MEDIA_DRM_INFO msg.obj of unexpected type " + message.obj);
                    return;
                case 211:
                    synchronized (this) {
                        onMediaTimeDiscontinuityListener = android.media.MediaPlayer.this.mOnMediaTimeDiscontinuityListener;
                        handler = android.media.MediaPlayer.this.mOnMediaTimeDiscontinuityHandler;
                    }
                    if (onMediaTimeDiscontinuityListener != null && (message.obj instanceof android.os.Parcel)) {
                        android.os.Parcel parcel4 = (android.os.Parcel) message.obj;
                        parcel4.setDataPosition(0);
                        long readLong = parcel4.readLong();
                        long readLong2 = parcel4.readLong();
                        float readFloat = parcel4.readFloat();
                        parcel4.recycle();
                        if (readLong != -1 && readLong2 != -1) {
                            mediaTimestamp = new android.media.MediaTimestamp(readLong, readLong2 * 1000, readFloat);
                        } else {
                            mediaTimestamp = android.media.MediaTimestamp.TIMESTAMP_UNKNOWN;
                        }
                        if (handler == null) {
                            onMediaTimeDiscontinuityListener.onMediaTimeDiscontinuity(this.mMediaPlayer, mediaTimestamp);
                            return;
                        } else {
                            handler.post(new java.lang.Runnable() { // from class: android.media.MediaPlayer.EventHandler.2
                                @Override // java.lang.Runnable
                                public void run() {
                                    onMediaTimeDiscontinuityListener.onMediaTimeDiscontinuity(android.media.MediaPlayer.EventHandler.this.mMediaPlayer, mediaTimestamp);
                                }
                            });
                            return;
                        }
                    }
                    return;
                case 300:
                    final android.media.MediaPlayer.OnRtpRxNoticeListener onRtpRxNoticeListener = android.media.MediaPlayer.this.mOnRtpRxNoticeListener;
                    if (onRtpRxNoticeListener != null && (message.obj instanceof android.os.Parcel)) {
                        android.os.Parcel parcel5 = (android.os.Parcel) message.obj;
                        parcel5.setDataPosition(0);
                        try {
                            final int readInt = parcel5.readInt();
                            int dataAvail = parcel5.dataAvail() / 4;
                            final int[] iArr = new int[dataAvail];
                            for (int i = 0; i < dataAvail; i++) {
                                iArr[i] = parcel5.readInt();
                            }
                            parcel5.recycle();
                            android.media.MediaPlayer.this.mOnRtpRxNoticeExecutor.execute(new java.lang.Runnable() { // from class: android.media.MediaPlayer$EventHandler$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    android.media.MediaPlayer.EventHandler.this.lambda$handleMessage$0(onRtpRxNoticeListener, readInt, iArr);
                                }
                            });
                            return;
                        } catch (java.lang.Throwable th) {
                            parcel5.recycle();
                            throw th;
                        }
                    }
                    return;
                case 10000:
                    android.media.MediaPlayer.this.broadcastRoutingChange();
                    return;
                default:
                    android.util.Log.e(android.media.MediaPlayer.TAG, "Unknown message type " + message.what);
                    return;
            }
            android.media.MediaPlayer.TimeProvider timeProvider5 = android.media.MediaPlayer.this.mTimeProvider;
            if (timeProvider5 != null) {
                timeProvider5.onSeekComplete(this.mMediaPlayer);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleMessage$0(android.media.MediaPlayer.OnRtpRxNoticeListener onRtpRxNoticeListener, int i, int[] iArr) {
            onRtpRxNoticeListener.onRtpRxNotice(this.mMediaPlayer, i, iArr);
        }
    }

    private static void postEventFromNative(java.lang.Object obj, int i, int i2, int i3, java.lang.Object obj2) {
        android.media.MediaPlayer mediaPlayer = (android.media.MediaPlayer) ((java.lang.ref.WeakReference) obj).get();
        if (mediaPlayer == null) {
            return;
        }
        switch (i) {
            case 1:
                synchronized (mediaPlayer.mDrmLock) {
                    mediaPlayer.mDrmInfoResolved = true;
                }
                break;
            case 200:
                if (i2 == 2) {
                    new java.lang.Thread(new java.lang.Runnable() { // from class: android.media.MediaPlayer.6
                        @Override // java.lang.Runnable
                        public void run() {
                            android.media.MediaPlayer.this.start();
                        }
                    }).start();
                    java.lang.Thread.yield();
                    break;
                }
                break;
            case 210:
                android.util.Log.v(TAG, "postEventFromNative MEDIA_DRM_INFO");
                if (obj2 instanceof android.os.Parcel) {
                    android.media.MediaPlayer.DrmInfo drmInfo = new android.media.MediaPlayer.DrmInfo((android.os.Parcel) obj2);
                    synchronized (mediaPlayer.mDrmLock) {
                        mediaPlayer.mDrmInfo = drmInfo;
                    }
                    break;
                } else {
                    android.util.Log.w(TAG, "MEDIA_DRM_INFO msg.obj of unexpected type " + obj2);
                    break;
                }
        }
        if (mediaPlayer.mEventHandler != null) {
            mediaPlayer.mEventHandler.sendMessage(mediaPlayer.mEventHandler.obtainMessage(i, i2, i3, obj2));
        }
    }

    public void setOnPreparedListener(android.media.MediaPlayer.OnPreparedListener onPreparedListener) {
        this.mOnPreparedListener = onPreparedListener;
    }

    public void setOnCompletionListener(android.media.MediaPlayer.OnCompletionListener onCompletionListener) {
        this.mOnCompletionListener = onCompletionListener;
    }

    public void setOnBufferingUpdateListener(android.media.MediaPlayer.OnBufferingUpdateListener onBufferingUpdateListener) {
        this.mOnBufferingUpdateListener = onBufferingUpdateListener;
    }

    public void setOnSeekCompleteListener(android.media.MediaPlayer.OnSeekCompleteListener onSeekCompleteListener) {
        this.mOnSeekCompleteListener = onSeekCompleteListener;
    }

    public void setOnVideoSizeChangedListener(android.media.MediaPlayer.OnVideoSizeChangedListener onVideoSizeChangedListener) {
        this.mOnVideoSizeChangedListener = onVideoSizeChangedListener;
    }

    public void setOnTimedTextListener(android.media.MediaPlayer.OnTimedTextListener onTimedTextListener) {
        this.mOnTimedTextListener = onTimedTextListener;
    }

    public void setOnSubtitleDataListener(android.media.MediaPlayer.OnSubtitleDataListener onSubtitleDataListener, android.os.Handler handler) {
        if (onSubtitleDataListener == null) {
            throw new java.lang.IllegalArgumentException("Illegal null listener");
        }
        if (handler == null) {
            throw new java.lang.IllegalArgumentException("Illegal null handler");
        }
        setOnSubtitleDataListenerInt(onSubtitleDataListener, handler);
    }

    public void setOnSubtitleDataListener(android.media.MediaPlayer.OnSubtitleDataListener onSubtitleDataListener) {
        if (onSubtitleDataListener == null) {
            throw new java.lang.IllegalArgumentException("Illegal null listener");
        }
        setOnSubtitleDataListenerInt(onSubtitleDataListener, null);
    }

    public void clearOnSubtitleDataListener() {
        setOnSubtitleDataListenerInt(null, null);
    }

    private void setOnSubtitleDataListenerInt(android.media.MediaPlayer.OnSubtitleDataListener onSubtitleDataListener, android.os.Handler handler) {
        synchronized (this) {
            this.mExtSubtitleDataListener = onSubtitleDataListener;
            this.mExtSubtitleDataHandler = handler;
        }
    }

    public void setOnMediaTimeDiscontinuityListener(android.media.MediaPlayer.OnMediaTimeDiscontinuityListener onMediaTimeDiscontinuityListener, android.os.Handler handler) {
        if (onMediaTimeDiscontinuityListener == null) {
            throw new java.lang.IllegalArgumentException("Illegal null listener");
        }
        if (handler == null) {
            throw new java.lang.IllegalArgumentException("Illegal null handler");
        }
        setOnMediaTimeDiscontinuityListenerInt(onMediaTimeDiscontinuityListener, handler);
    }

    public void setOnMediaTimeDiscontinuityListener(android.media.MediaPlayer.OnMediaTimeDiscontinuityListener onMediaTimeDiscontinuityListener) {
        if (onMediaTimeDiscontinuityListener == null) {
            throw new java.lang.IllegalArgumentException("Illegal null listener");
        }
        setOnMediaTimeDiscontinuityListenerInt(onMediaTimeDiscontinuityListener, null);
    }

    public void clearOnMediaTimeDiscontinuityListener() {
        setOnMediaTimeDiscontinuityListenerInt(null, null);
    }

    private void setOnMediaTimeDiscontinuityListenerInt(android.media.MediaPlayer.OnMediaTimeDiscontinuityListener onMediaTimeDiscontinuityListener, android.os.Handler handler) {
        synchronized (this) {
            this.mOnMediaTimeDiscontinuityListener = onMediaTimeDiscontinuityListener;
            this.mOnMediaTimeDiscontinuityHandler = handler;
        }
    }

    @android.annotation.SystemApi
    public void setOnRtpRxNoticeListener(android.content.Context context, java.util.concurrent.Executor executor, android.media.MediaPlayer.OnRtpRxNoticeListener onRtpRxNoticeListener) {
        java.util.Objects.requireNonNull(context);
        com.android.internal.util.Preconditions.checkArgument(context.checkSelfPermission(android.Manifest.permission.BIND_IMS_SERVICE) == 0, "android.permission.BIND_IMS_SERVICE permission not granted.");
        this.mOnRtpRxNoticeListener = (android.media.MediaPlayer.OnRtpRxNoticeListener) java.util.Objects.requireNonNull(onRtpRxNoticeListener);
        this.mOnRtpRxNoticeExecutor = (java.util.concurrent.Executor) java.util.Objects.requireNonNull(executor);
    }

    public void setOnTimedMetaDataAvailableListener(android.media.MediaPlayer.OnTimedMetaDataAvailableListener onTimedMetaDataAvailableListener) {
        this.mOnTimedMetaDataAvailableListener = onTimedMetaDataAvailableListener;
    }

    public void setOnErrorListener(android.media.MediaPlayer.OnErrorListener onErrorListener) {
        this.mOnErrorListener = onErrorListener;
    }

    public void setOnInfoListener(android.media.MediaPlayer.OnInfoListener onInfoListener) {
        this.mOnInfoListener = onInfoListener;
    }

    public void setOnDrmConfigHelper(android.media.MediaPlayer.OnDrmConfigHelper onDrmConfigHelper) {
        synchronized (this.mDrmLock) {
            this.mOnDrmConfigHelper = onDrmConfigHelper;
        }
    }

    public void setOnDrmInfoListener(android.media.MediaPlayer.OnDrmInfoListener onDrmInfoListener) {
        setOnDrmInfoListener(onDrmInfoListener, null);
    }

    public void setOnDrmInfoListener(android.media.MediaPlayer.OnDrmInfoListener onDrmInfoListener, android.os.Handler handler) {
        synchronized (this.mDrmLock) {
            if (onDrmInfoListener != null) {
                this.mOnDrmInfoHandlerDelegate = new android.media.MediaPlayer.OnDrmInfoHandlerDelegate(this, onDrmInfoListener, handler);
            } else {
                this.mOnDrmInfoHandlerDelegate = null;
            }
        }
    }

    public void setOnDrmPreparedListener(android.media.MediaPlayer.OnDrmPreparedListener onDrmPreparedListener) {
        setOnDrmPreparedListener(onDrmPreparedListener, null);
    }

    public void setOnDrmPreparedListener(android.media.MediaPlayer.OnDrmPreparedListener onDrmPreparedListener, android.os.Handler handler) {
        synchronized (this.mDrmLock) {
            if (onDrmPreparedListener != null) {
                this.mOnDrmPreparedHandlerDelegate = new android.media.MediaPlayer.OnDrmPreparedHandlerDelegate(this, onDrmPreparedListener, handler);
            } else {
                this.mOnDrmPreparedHandlerDelegate = null;
            }
        }
    }

    private class OnDrmInfoHandlerDelegate {
        private android.os.Handler mHandler;
        private android.media.MediaPlayer mMediaPlayer;
        private android.media.MediaPlayer.OnDrmInfoListener mOnDrmInfoListener;

        OnDrmInfoHandlerDelegate(android.media.MediaPlayer mediaPlayer, android.media.MediaPlayer.OnDrmInfoListener onDrmInfoListener, android.os.Handler handler) {
            this.mMediaPlayer = mediaPlayer;
            this.mOnDrmInfoListener = onDrmInfoListener;
            if (handler != null) {
                this.mHandler = handler;
            }
        }

        void notifyClient(final android.media.MediaPlayer.DrmInfo drmInfo) {
            if (this.mHandler != null) {
                this.mHandler.post(new java.lang.Runnable() { // from class: android.media.MediaPlayer.OnDrmInfoHandlerDelegate.1
                    @Override // java.lang.Runnable
                    public void run() {
                        android.media.MediaPlayer.OnDrmInfoHandlerDelegate.this.mOnDrmInfoListener.onDrmInfo(android.media.MediaPlayer.OnDrmInfoHandlerDelegate.this.mMediaPlayer, drmInfo);
                    }
                });
            } else {
                this.mOnDrmInfoListener.onDrmInfo(this.mMediaPlayer, drmInfo);
            }
        }
    }

    private class OnDrmPreparedHandlerDelegate {
        private android.os.Handler mHandler;
        private android.media.MediaPlayer mMediaPlayer;
        private android.media.MediaPlayer.OnDrmPreparedListener mOnDrmPreparedListener;

        OnDrmPreparedHandlerDelegate(android.media.MediaPlayer mediaPlayer, android.media.MediaPlayer.OnDrmPreparedListener onDrmPreparedListener, android.os.Handler handler) {
            this.mMediaPlayer = mediaPlayer;
            this.mOnDrmPreparedListener = onDrmPreparedListener;
            if (handler != null) {
                this.mHandler = handler;
            } else if (android.media.MediaPlayer.this.mEventHandler != null) {
                this.mHandler = android.media.MediaPlayer.this.mEventHandler;
            } else {
                android.util.Log.e(android.media.MediaPlayer.TAG, "OnDrmPreparedHandlerDelegate: Unexpected null mEventHandler");
            }
        }

        void notifyClient(final int i) {
            if (this.mHandler != null) {
                this.mHandler.post(new java.lang.Runnable() { // from class: android.media.MediaPlayer.OnDrmPreparedHandlerDelegate.1
                    @Override // java.lang.Runnable
                    public void run() {
                        android.media.MediaPlayer.OnDrmPreparedHandlerDelegate.this.mOnDrmPreparedListener.onDrmPrepared(android.media.MediaPlayer.OnDrmPreparedHandlerDelegate.this.mMediaPlayer, i);
                    }
                });
            } else {
                android.util.Log.e(android.media.MediaPlayer.TAG, "OnDrmPreparedHandlerDelegate:notifyClient: Unexpected null mHandler");
            }
        }
    }

    public android.media.MediaPlayer.DrmInfo getDrmInfo() {
        android.media.MediaPlayer.DrmInfo drmInfo;
        synchronized (this.mDrmLock) {
            if (!this.mDrmInfoResolved && this.mDrmInfo == null) {
                android.util.Log.v(TAG, "The Player has not been prepared yet");
                throw new java.lang.IllegalStateException("The Player has not been prepared yet");
            }
            if (this.mDrmInfo == null) {
                drmInfo = null;
            } else {
                drmInfo = this.mDrmInfo.makeCopy();
            }
        }
        return drmInfo;
    }

    public void prepareDrm(java.util.UUID uuid) throws android.media.UnsupportedSchemeException, android.media.ResourceBusyException, android.media.MediaPlayer.ProvisioningNetworkErrorException, android.media.MediaPlayer.ProvisioningServerErrorException {
        boolean z;
        android.media.MediaPlayer.OnDrmPreparedHandlerDelegate onDrmPreparedHandlerDelegate;
        android.util.Log.v(TAG, "prepareDrm: uuid: " + uuid + " mOnDrmConfigHelper: " + this.mOnDrmConfigHelper);
        synchronized (this.mDrmLock) {
            if (this.mDrmInfo == null) {
                android.util.Log.e(TAG, "prepareDrm(): Wrong usage: The player must be prepared and DRM info be retrieved before this call.");
                throw new java.lang.IllegalStateException("prepareDrm(): Wrong usage: The player must be prepared and DRM info be retrieved before this call.");
            }
            if (this.mActiveDrmScheme) {
                java.lang.String str = "prepareDrm(): Wrong usage: There is already an active DRM scheme with " + this.mDrmUUID;
                android.util.Log.e(TAG, str);
                throw new java.lang.IllegalStateException(str);
            }
            if (this.mPrepareDrmInProgress) {
                android.util.Log.e(TAG, "prepareDrm(): Wrong usage: There is already a pending prepareDrm call.");
                throw new java.lang.IllegalStateException("prepareDrm(): Wrong usage: There is already a pending prepareDrm call.");
            }
            if (this.mDrmProvisioningInProgress) {
                android.util.Log.e(TAG, "prepareDrm(): Unexpectd: Provisioning is already in progress.");
                throw new java.lang.IllegalStateException("prepareDrm(): Unexpectd: Provisioning is already in progress.");
            }
            cleanDrmObj();
            z = true;
            this.mPrepareDrmInProgress = true;
            onDrmPreparedHandlerDelegate = this.mOnDrmPreparedHandlerDelegate;
            try {
                prepareDrm_createDrmStep(uuid);
                this.mDrmConfigAllowed = true;
            } catch (java.lang.Exception e) {
                android.util.Log.w(TAG, "prepareDrm(): Exception ", e);
                this.mPrepareDrmInProgress = false;
                throw e;
            }
        }
        if (this.mOnDrmConfigHelper != null) {
            this.mOnDrmConfigHelper.onDrmConfig(this);
        }
        synchronized (this.mDrmLock) {
            try {
                this.mDrmConfigAllowed = false;
            } catch (java.lang.Throwable th) {
                th = th;
                z = false;
            }
            try {
                try {
                    try {
                        try {
                            prepareDrm_openSessionStep(uuid);
                            this.mDrmUUID = uuid;
                            this.mActiveDrmScheme = true;
                            if (!this.mDrmProvisioningInProgress) {
                                this.mPrepareDrmInProgress = false;
                            }
                        } catch (android.media.NotProvisionedException e2) {
                            android.util.Log.w(TAG, "prepareDrm: NotProvisionedException");
                            int HandleProvisioninig = HandleProvisioninig(uuid);
                            if (HandleProvisioninig != 0) {
                                switch (HandleProvisioninig) {
                                    case 1:
                                        android.util.Log.e(TAG, "prepareDrm: Provisioning was required but failed due to a network error.");
                                        throw new android.media.MediaPlayer.ProvisioningNetworkErrorException("prepareDrm: Provisioning was required but failed due to a network error.");
                                    case 2:
                                        android.util.Log.e(TAG, "prepareDrm: Provisioning was required but the request was denied by the server.");
                                        throw new android.media.MediaPlayer.ProvisioningServerErrorException("prepareDrm: Provisioning was required but the request was denied by the server.");
                                    default:
                                        android.util.Log.e(TAG, "prepareDrm: Post-provisioning preparation failed.");
                                        throw new java.lang.IllegalStateException("prepareDrm: Post-provisioning preparation failed.");
                                }
                            }
                            if (!this.mDrmProvisioningInProgress) {
                                this.mPrepareDrmInProgress = false;
                            }
                            z = false;
                        }
                    } catch (java.lang.Exception e3) {
                        android.util.Log.e(TAG, "prepareDrm: Exception " + e3);
                        throw e3;
                    }
                } catch (java.lang.IllegalStateException e4) {
                    android.util.Log.e(TAG, "prepareDrm(): Wrong usage: The player must be in the prepared state to call prepareDrm().");
                    throw new java.lang.IllegalStateException("prepareDrm(): Wrong usage: The player must be in the prepared state to call prepareDrm().");
                }
            } catch (java.lang.Throwable th2) {
                th = th2;
                if (!this.mDrmProvisioningInProgress) {
                    this.mPrepareDrmInProgress = false;
                }
                if (z) {
                    cleanDrmObj();
                }
                throw th;
            }
        }
        if (!z || onDrmPreparedHandlerDelegate == null) {
            return;
        }
        onDrmPreparedHandlerDelegate.notifyClient(0);
    }

    public void releaseDrm() throws android.media.MediaPlayer.NoDrmSchemeException {
        android.util.Log.v(TAG, "releaseDrm:");
        synchronized (this.mDrmLock) {
            if (!this.mActiveDrmScheme) {
                android.util.Log.e(TAG, "releaseDrm(): No active DRM scheme to release.");
                throw new android.media.MediaPlayer.NoDrmSchemeException("releaseDrm: No active DRM scheme to release.");
            }
            try {
                try {
                    _releaseDrm();
                    cleanDrmObj();
                    this.mActiveDrmScheme = false;
                } catch (java.lang.Exception e) {
                    android.util.Log.e(TAG, "releaseDrm: Exception ", e);
                }
            } catch (java.lang.IllegalStateException e2) {
                android.util.Log.w(TAG, "releaseDrm: Exception ", e2);
                throw new java.lang.IllegalStateException("releaseDrm: The player is not in a valid state.");
            }
        }
    }

    public android.media.MediaDrm.KeyRequest getKeyRequest(byte[] bArr, byte[] bArr2, java.lang.String str, int i, java.util.Map<java.lang.String, java.lang.String> map) throws android.media.MediaPlayer.NoDrmSchemeException {
        byte[] bArr3;
        java.util.HashMap<java.lang.String, java.lang.String> hashMap;
        android.media.MediaDrm.KeyRequest keyRequest;
        android.util.Log.v(TAG, "getKeyRequest:  keySetId: " + java.util.Arrays.toString(bArr) + " initData:" + java.util.Arrays.toString(bArr2) + " mimeType: " + str + " keyType: " + i + " optionalParameters: " + map);
        synchronized (this.mDrmLock) {
            if (!this.mActiveDrmScheme) {
                android.util.Log.e(TAG, "getKeyRequest NoDrmSchemeException");
                throw new android.media.MediaPlayer.NoDrmSchemeException("getKeyRequest: Has to set a DRM scheme first.");
            }
            if (i != 3) {
                try {
                    try {
                        bArr3 = this.mDrmSessionId;
                    } catch (android.media.NotProvisionedException e) {
                        android.util.Log.w(TAG, "getKeyRequest NotProvisionedException: Unexpected. Shouldn't have reached here.");
                        throw new java.lang.IllegalStateException("getKeyRequest: Unexpected provisioning error.");
                    }
                } catch (java.lang.Exception e2) {
                    android.util.Log.w(TAG, "getKeyRequest Exception " + e2);
                    throw e2;
                }
            } else {
                bArr3 = bArr;
            }
            if (map != null) {
                hashMap = new java.util.HashMap<>(map);
            } else {
                hashMap = null;
            }
            keyRequest = this.mDrmObj.getKeyRequest(bArr3, bArr2, str, i, hashMap);
            android.util.Log.v(TAG, "getKeyRequest:   --> request: " + keyRequest);
        }
        return keyRequest;
    }

    public byte[] provideKeyResponse(byte[] bArr, byte[] bArr2) throws android.media.MediaPlayer.NoDrmSchemeException, android.media.DeniedByServerException {
        byte[] bArr3;
        byte[] provideKeyResponse;
        android.util.Log.v(TAG, "provideKeyResponse: keySetId: " + java.util.Arrays.toString(bArr) + " response: " + java.util.Arrays.toString(bArr2));
        synchronized (this.mDrmLock) {
            if (!this.mActiveDrmScheme) {
                android.util.Log.e(TAG, "getKeyRequest NoDrmSchemeException");
                throw new android.media.MediaPlayer.NoDrmSchemeException("getKeyRequest: Has to set a DRM scheme first.");
            }
            if (bArr == null) {
                try {
                    try {
                        bArr3 = this.mDrmSessionId;
                    } catch (android.media.NotProvisionedException e) {
                        android.util.Log.w(TAG, "provideKeyResponse NotProvisionedException: Unexpected. Shouldn't have reached here.");
                        throw new java.lang.IllegalStateException("provideKeyResponse: Unexpected provisioning error.");
                    }
                } catch (java.lang.Exception e2) {
                    android.util.Log.w(TAG, "provideKeyResponse Exception " + e2);
                    throw e2;
                }
            } else {
                bArr3 = bArr;
            }
            provideKeyResponse = this.mDrmObj.provideKeyResponse(bArr3, bArr2);
            android.util.Log.v(TAG, "provideKeyResponse: keySetId: " + java.util.Arrays.toString(bArr) + " response: " + java.util.Arrays.toString(bArr2) + " --> " + java.util.Arrays.toString(provideKeyResponse));
        }
        return provideKeyResponse;
    }

    public void restoreKeys(byte[] bArr) throws android.media.MediaPlayer.NoDrmSchemeException {
        android.util.Log.v(TAG, "restoreKeys: keySetId: " + java.util.Arrays.toString(bArr));
        synchronized (this.mDrmLock) {
            if (!this.mActiveDrmScheme) {
                android.util.Log.w(TAG, "restoreKeys NoDrmSchemeException");
                throw new android.media.MediaPlayer.NoDrmSchemeException("restoreKeys: Has to set a DRM scheme first.");
            }
            try {
                this.mDrmObj.restoreKeys(this.mDrmSessionId, bArr);
            } catch (java.lang.Exception e) {
                android.util.Log.w(TAG, "restoreKeys Exception " + e);
                throw e;
            }
        }
    }

    public java.lang.String getDrmPropertyString(java.lang.String str) throws android.media.MediaPlayer.NoDrmSchemeException {
        java.lang.String propertyString;
        android.util.Log.v(TAG, "getDrmPropertyString: propertyName: " + str);
        synchronized (this.mDrmLock) {
            if (!this.mActiveDrmScheme && !this.mDrmConfigAllowed) {
                android.util.Log.w(TAG, "getDrmPropertyString NoDrmSchemeException");
                throw new android.media.MediaPlayer.NoDrmSchemeException("getDrmPropertyString: Has to prepareDrm() first.");
            }
            try {
                propertyString = this.mDrmObj.getPropertyString(str);
            } catch (java.lang.Exception e) {
                android.util.Log.w(TAG, "getDrmPropertyString Exception " + e);
                throw e;
            }
        }
        android.util.Log.v(TAG, "getDrmPropertyString: propertyName: " + str + " --> value: " + propertyString);
        return propertyString;
    }

    public void setDrmPropertyString(java.lang.String str, java.lang.String str2) throws android.media.MediaPlayer.NoDrmSchemeException {
        android.util.Log.v(TAG, "setDrmPropertyString: propertyName: " + str + " value: " + str2);
        synchronized (this.mDrmLock) {
            if (!this.mActiveDrmScheme && !this.mDrmConfigAllowed) {
                android.util.Log.w(TAG, "setDrmPropertyString NoDrmSchemeException");
                throw new android.media.MediaPlayer.NoDrmSchemeException("setDrmPropertyString: Has to prepareDrm() first.");
            }
            try {
                this.mDrmObj.setPropertyString(str, str2);
            } catch (java.lang.Exception e) {
                android.util.Log.w(TAG, "setDrmPropertyString Exception " + e);
                throw e;
            }
        }
    }

    public static final class DrmInfo {
        private java.util.Map<java.util.UUID, byte[]> mapPssh;
        private java.util.UUID[] supportedSchemes;

        public java.util.Map<java.util.UUID, byte[]> getPssh() {
            return this.mapPssh;
        }

        public java.util.UUID[] getSupportedSchemes() {
            return this.supportedSchemes;
        }

        private DrmInfo(java.util.Map<java.util.UUID, byte[]> map, java.util.UUID[] uuidArr) {
            this.mapPssh = map;
            this.supportedSchemes = uuidArr;
        }

        private DrmInfo(android.os.Parcel parcel) {
            android.util.Log.v(android.media.MediaPlayer.TAG, "DrmInfo(" + parcel + ") size " + parcel.dataSize());
            int readInt = parcel.readInt();
            byte[] bArr = new byte[readInt];
            parcel.readByteArray(bArr);
            android.util.Log.v(android.media.MediaPlayer.TAG, "DrmInfo() PSSH: " + arrToHex(bArr));
            this.mapPssh = parsePSSH(bArr, readInt);
            android.util.Log.v(android.media.MediaPlayer.TAG, "DrmInfo() PSSH: " + this.mapPssh);
            int readInt2 = parcel.readInt();
            this.supportedSchemes = new java.util.UUID[readInt2];
            for (int i = 0; i < readInt2; i++) {
                byte[] bArr2 = new byte[16];
                parcel.readByteArray(bArr2);
                this.supportedSchemes[i] = bytesToUUID(bArr2);
                android.util.Log.v(android.media.MediaPlayer.TAG, "DrmInfo() supportedScheme[" + i + "]: " + this.supportedSchemes[i]);
            }
            android.util.Log.v(android.media.MediaPlayer.TAG, "DrmInfo() Parcel psshsize: " + readInt + " supportedDRMsCount: " + readInt2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public android.media.MediaPlayer.DrmInfo makeCopy() {
            return new android.media.MediaPlayer.DrmInfo(this.mapPssh, this.supportedSchemes);
        }

        private java.lang.String arrToHex(byte[] bArr) {
            java.lang.String str = "0x";
            for (byte b : bArr) {
                str = str + java.lang.String.format("%02x", java.lang.Byte.valueOf(b));
            }
            return str;
        }

        private java.util.UUID bytesToUUID(byte[] bArr) {
            long j = 0;
            long j2 = 0;
            for (int i = 0; i < 8; i++) {
                int i2 = (7 - i) * 8;
                j |= (bArr[i] & 255) << i2;
                j2 |= (bArr[i + 8] & 255) << i2;
            }
            return new java.util.UUID(j, j2);
        }

        private java.util.Map<java.util.UUID, byte[]> parsePSSH(byte[] bArr, int i) {
            int i2;
            java.util.HashMap hashMap = new java.util.HashMap();
            int i3 = i;
            int i4 = 0;
            int i5 = 0;
            while (i3 > 0) {
                if (i3 < 16) {
                    android.util.Log.w(android.media.MediaPlayer.TAG, java.lang.String.format("parsePSSH: len is too short to parse UUID: (%d < 16) pssh: %d", java.lang.Integer.valueOf(i3), java.lang.Integer.valueOf(i)));
                    return null;
                }
                int i6 = i4 + 16;
                java.util.UUID bytesToUUID = bytesToUUID(java.util.Arrays.copyOfRange(bArr, i4, i6));
                int i7 = i3 - 16;
                if (i7 < 4) {
                    android.util.Log.w(android.media.MediaPlayer.TAG, java.lang.String.format("parsePSSH: len is too short to parse datalen: (%d < 4) pssh: %d", java.lang.Integer.valueOf(i7), java.lang.Integer.valueOf(i)));
                    return null;
                }
                int i8 = i6 + 4;
                byte[] copyOfRange = java.util.Arrays.copyOfRange(bArr, i6, i8);
                if (java.nio.ByteOrder.nativeOrder() == java.nio.ByteOrder.LITTLE_ENDIAN) {
                    i2 = ((copyOfRange[2] & 255) << 16) | ((copyOfRange[3] & 255) << 24) | ((copyOfRange[1] & 255) << 8) | (copyOfRange[0] & 255);
                } else {
                    i2 = ((copyOfRange[1] & 255) << 16) | ((copyOfRange[0] & 255) << 24) | ((copyOfRange[2] & 255) << 8) | (copyOfRange[3] & 255);
                }
                int i9 = i7 - 4;
                if (i9 < i2) {
                    android.util.Log.w(android.media.MediaPlayer.TAG, java.lang.String.format("parsePSSH: len is too short to parse data: (%d < %d) pssh: %d", java.lang.Integer.valueOf(i9), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i)));
                    return null;
                }
                int i10 = i8 + i2;
                byte[] copyOfRange2 = java.util.Arrays.copyOfRange(bArr, i8, i10);
                i3 = i9 - i2;
                android.util.Log.v(android.media.MediaPlayer.TAG, java.lang.String.format("parsePSSH[%d]: <%s, %s> pssh: %d", java.lang.Integer.valueOf(i5), bytesToUUID, arrToHex(copyOfRange2), java.lang.Integer.valueOf(i)));
                i5++;
                hashMap.put(bytesToUUID, copyOfRange2);
                i4 = i10;
            }
            return hashMap;
        }
    }

    public static final class NoDrmSchemeException extends android.media.MediaDrmException {
        public NoDrmSchemeException(java.lang.String str) {
            super(str);
        }
    }

    public static final class ProvisioningNetworkErrorException extends android.media.MediaDrmException {
        public ProvisioningNetworkErrorException(java.lang.String str) {
            super(str);
        }
    }

    public static final class ProvisioningServerErrorException extends android.media.MediaDrmException {
        public ProvisioningServerErrorException(java.lang.String str) {
            super(str);
        }
    }

    private void prepareDrm_createDrmStep(java.util.UUID uuid) throws android.media.UnsupportedSchemeException {
        android.util.Log.v(TAG, "prepareDrm_createDrmStep: UUID: " + uuid);
        try {
            this.mDrmObj = new android.media.MediaDrm(uuid);
            android.util.Log.v(TAG, "prepareDrm_createDrmStep: Created mDrmObj=" + this.mDrmObj);
        } catch (java.lang.Exception e) {
            android.util.Log.e(TAG, "prepareDrm_createDrmStep: MediaDrm failed with " + e);
            throw e;
        }
    }

    private void prepareDrm_openSessionStep(java.util.UUID uuid) throws android.media.NotProvisionedException, android.media.ResourceBusyException {
        android.util.Log.v(TAG, "prepareDrm_openSessionStep: uuid: " + uuid);
        try {
            this.mDrmSessionId = this.mDrmObj.openSession();
            android.util.Log.v(TAG, "prepareDrm_openSessionStep: mDrmSessionId=" + java.util.Arrays.toString(this.mDrmSessionId));
            _prepareDrm(getByteArrayFromUUID(uuid), this.mDrmSessionId);
            android.util.Log.v(TAG, "prepareDrm_openSessionStep: _prepareDrm/Crypto succeeded");
        } catch (java.lang.Exception e) {
            android.util.Log.e(TAG, "prepareDrm_openSessionStep: open/crypto failed with " + e);
            throw e;
        }
    }

    private class ProvisioningThread extends java.lang.Thread {
        public static final int TIMEOUT_MS = 60000;
        private java.lang.Object drmLock;
        private boolean finished;
        private android.media.MediaPlayer mediaPlayer;
        private android.media.MediaPlayer.OnDrmPreparedHandlerDelegate onDrmPreparedHandlerDelegate;
        private int status;
        private java.lang.String urlStr;
        private java.util.UUID uuid;

        private ProvisioningThread() {
        }

        public int status() {
            return this.status;
        }

        public android.media.MediaPlayer.ProvisioningThread initialize(android.media.MediaDrm.ProvisionRequest provisionRequest, java.util.UUID uuid, android.media.MediaPlayer mediaPlayer) {
            this.drmLock = mediaPlayer.mDrmLock;
            this.onDrmPreparedHandlerDelegate = mediaPlayer.mOnDrmPreparedHandlerDelegate;
            this.mediaPlayer = mediaPlayer;
            this.urlStr = provisionRequest.getDefaultUrl() + "&signedRequest=" + new java.lang.String(provisionRequest.getData());
            this.uuid = uuid;
            this.status = 3;
            android.util.Log.v(android.media.MediaPlayer.TAG, "HandleProvisioninig: Thread is initialised url: " + this.urlStr);
            return this;
        }

        /* JADX WARN: Removed duplicated region for block: B:11:0x00dc  */
        /* JADX WARN: Removed duplicated region for block: B:32:0x010f  */
        @Override // java.lang.Thread, java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void run() {
            boolean z;
            boolean z2;
            boolean resumePrepareDrm;
            byte[] bArr = null;
            try {
                java.net.URL url = new java.net.URL(this.urlStr);
                java.net.HttpURLConnection httpURLConnection = (java.net.HttpURLConnection) url.openConnection();
                try {
                    try {
                        httpURLConnection.setRequestMethod("POST");
                        httpURLConnection.setDoOutput(false);
                        httpURLConnection.setDoInput(true);
                        httpURLConnection.setConnectTimeout(60000);
                        httpURLConnection.setReadTimeout(60000);
                        httpURLConnection.connect();
                        bArr = libcore.io.Streams.readFully(httpURLConnection.getInputStream());
                        android.util.Log.v(android.media.MediaPlayer.TAG, "HandleProvisioninig: Thread run: response " + bArr.length + " " + java.util.Arrays.toString(bArr));
                    } catch (java.lang.Throwable th) {
                        httpURLConnection.disconnect();
                        throw th;
                    }
                } catch (java.lang.Exception e) {
                    this.status = 1;
                    android.util.Log.w(android.media.MediaPlayer.TAG, "HandleProvisioninig: Thread run: connect " + e + " url: " + url);
                }
                httpURLConnection.disconnect();
            } catch (java.lang.Exception e2) {
                this.status = 1;
                android.util.Log.w(android.media.MediaPlayer.TAG, "HandleProvisioninig: Thread run: openConnection " + e2);
            }
            if (bArr != null) {
                try {
                    android.media.MediaPlayer.this.mDrmObj.provideProvisionResponse(bArr);
                    android.util.Log.v(android.media.MediaPlayer.TAG, "HandleProvisioninig: Thread run: provideProvisionResponse SUCCEEDED!");
                    z = true;
                } catch (java.lang.Exception e3) {
                    this.status = 2;
                    android.util.Log.w(android.media.MediaPlayer.TAG, "HandleProvisioninig: Thread run: provideProvisionResponse " + e3);
                }
                if (this.onDrmPreparedHandlerDelegate == null) {
                    synchronized (this.drmLock) {
                        if (!z) {
                            resumePrepareDrm = false;
                        } else {
                            resumePrepareDrm = this.mediaPlayer.resumePrepareDrm(this.uuid);
                            this.status = resumePrepareDrm ? 0 : 3;
                        }
                        this.mediaPlayer.mDrmProvisioningInProgress = false;
                        this.mediaPlayer.mPrepareDrmInProgress = false;
                        if (!resumePrepareDrm) {
                            android.media.MediaPlayer.this.cleanDrmObj();
                        }
                    }
                    this.onDrmPreparedHandlerDelegate.notifyClient(this.status);
                } else {
                    if (!z) {
                        z2 = false;
                    } else {
                        z2 = this.mediaPlayer.resumePrepareDrm(this.uuid);
                        this.status = z2 ? 0 : 3;
                    }
                    this.mediaPlayer.mDrmProvisioningInProgress = false;
                    this.mediaPlayer.mPrepareDrmInProgress = false;
                    if (!z2) {
                        android.media.MediaPlayer.this.cleanDrmObj();
                    }
                }
                this.finished = true;
            }
            z = false;
            if (this.onDrmPreparedHandlerDelegate == null) {
            }
            this.finished = true;
        }
    }

    private int HandleProvisioninig(java.util.UUID uuid) {
        if (this.mDrmProvisioningInProgress) {
            android.util.Log.e(TAG, "HandleProvisioninig: Unexpected mDrmProvisioningInProgress");
            return 3;
        }
        android.media.MediaDrm.ProvisionRequest provisionRequest = this.mDrmObj.getProvisionRequest();
        if (provisionRequest == null) {
            android.util.Log.e(TAG, "HandleProvisioninig: getProvisionRequest returned null.");
            return 3;
        }
        android.util.Log.v(TAG, "HandleProvisioninig provReq  data: " + java.util.Arrays.toString(provisionRequest.getData()) + " url: " + provisionRequest.getDefaultUrl());
        this.mDrmProvisioningInProgress = true;
        this.mDrmProvisioningThread = new android.media.MediaPlayer.ProvisioningThread().initialize(provisionRequest, uuid, this);
        this.mDrmProvisioningThread.start();
        if (this.mOnDrmPreparedHandlerDelegate != null) {
            return 0;
        }
        try {
            this.mDrmProvisioningThread.join();
        } catch (java.lang.Exception e) {
            android.util.Log.w(TAG, "HandleProvisioninig: Thread.join Exception " + e);
        }
        int status = this.mDrmProvisioningThread.status();
        this.mDrmProvisioningThread = null;
        return status;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean resumePrepareDrm(java.util.UUID uuid) {
        android.util.Log.v(TAG, "resumePrepareDrm: uuid: " + uuid);
        try {
            prepareDrm_openSessionStep(uuid);
            this.mDrmUUID = uuid;
            this.mActiveDrmScheme = true;
            return true;
        } catch (java.lang.Exception e) {
            android.util.Log.w(TAG, "HandleProvisioninig: Thread run _prepareDrm resume failed with " + e);
            return false;
        }
    }

    private void resetDrmState() {
        synchronized (this.mDrmLock) {
            android.util.Log.v(TAG, "resetDrmState:  mDrmInfo=" + this.mDrmInfo + " mDrmProvisioningThread=" + this.mDrmProvisioningThread + " mPrepareDrmInProgress=" + this.mPrepareDrmInProgress + " mActiveDrmScheme=" + this.mActiveDrmScheme);
            this.mDrmInfoResolved = false;
            this.mDrmInfo = null;
            if (this.mDrmProvisioningThread != null) {
                try {
                    this.mDrmProvisioningThread.join();
                } catch (java.lang.InterruptedException e) {
                    android.util.Log.w(TAG, "resetDrmState: ProvThread.join Exception " + e);
                }
                this.mDrmProvisioningThread = null;
            }
            this.mPrepareDrmInProgress = false;
            this.mActiveDrmScheme = false;
            cleanDrmObj();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cleanDrmObj() {
        android.util.Log.v(TAG, "cleanDrmObj: mDrmObj=" + this.mDrmObj + " mDrmSessionId=" + java.util.Arrays.toString(this.mDrmSessionId));
        if (this.mDrmSessionId != null) {
            this.mDrmObj.closeSession(this.mDrmSessionId);
            this.mDrmSessionId = null;
        }
        if (this.mDrmObj != null) {
            this.mDrmObj.release();
            this.mDrmObj = null;
        }
    }

    private static final byte[] getByteArrayFromUUID(java.util.UUID uuid) {
        long mostSignificantBits = uuid.getMostSignificantBits();
        long leastSignificantBits = uuid.getLeastSignificantBits();
        byte[] bArr = new byte[16];
        for (int i = 0; i < 8; i++) {
            int i2 = (7 - i) * 8;
            bArr[i] = (byte) (mostSignificantBits >>> i2);
            bArr[i + 8] = (byte) (leastSignificantBits >>> i2);
        }
        return bArr;
    }

    private boolean isVideoScalingModeSupported(int i) {
        return i == 1 || i == 2;
    }

    static class TimeProvider implements android.media.MediaPlayer.OnSeekCompleteListener, android.media.MediaTimeProvider {
        private static final long MAX_EARLY_CALLBACK_US = 1000;
        private static final long MAX_NS_WITHOUT_POSITION_CHECK = 5000000000L;
        private static final int NOTIFY = 1;
        private static final int NOTIFY_SEEK = 3;
        private static final int NOTIFY_STOP = 2;
        private static final int NOTIFY_TIME = 0;
        private static final int NOTIFY_TRACK_DATA = 4;
        private static final java.lang.String TAG = "MTP";
        private static final long TIME_ADJUSTMENT_RATE = 2;
        private boolean mBuffering;
        private android.os.Handler mEventHandler;
        private android.os.HandlerThread mHandlerThread;
        private long mLastReportedTime;
        private long mLastTimeUs;
        private android.media.MediaTimeProvider.OnMediaTimeListener[] mListeners;
        private android.media.MediaPlayer mPlayer;
        private boolean mRefresh;
        private long[] mTimes;
        private boolean mPaused = true;
        private boolean mStopped = true;
        private boolean mPausing = false;
        private boolean mSeeking = false;
        public boolean DEBUG = false;

        public TimeProvider(android.media.MediaPlayer mediaPlayer) {
            this.mLastTimeUs = 0L;
            this.mRefresh = false;
            this.mPlayer = mediaPlayer;
            try {
                getCurrentTimeUs(true, false);
            } catch (java.lang.IllegalStateException e) {
                this.mRefresh = true;
            }
            android.os.Looper myLooper = android.os.Looper.myLooper();
            if (myLooper == null && (myLooper = android.os.Looper.getMainLooper()) == null) {
                this.mHandlerThread = new android.os.HandlerThread("MediaPlayerMTPEventThread", -2);
                this.mHandlerThread.start();
                myLooper = this.mHandlerThread.getLooper();
            }
            this.mEventHandler = new android.media.MediaPlayer.TimeProvider.EventHandler(myLooper);
            this.mListeners = new android.media.MediaTimeProvider.OnMediaTimeListener[0];
            this.mTimes = new long[0];
            this.mLastTimeUs = 0L;
        }

        private void scheduleNotification(int i, long j) {
            if (this.mSeeking && i == 0) {
                return;
            }
            if (this.DEBUG) {
                android.util.Log.v(TAG, "scheduleNotification " + i + " in " + j);
            }
            this.mEventHandler.removeMessages(1);
            this.mEventHandler.sendMessageDelayed(this.mEventHandler.obtainMessage(1, i, 0), (int) (j / 1000));
        }

        public void close() {
            this.mEventHandler.removeMessages(1);
            if (this.mHandlerThread != null) {
                this.mHandlerThread.quitSafely();
                this.mHandlerThread = null;
            }
        }

        protected void finalize() {
            if (this.mHandlerThread != null) {
                this.mHandlerThread.quitSafely();
            }
        }

        public void onNotifyTime() {
            synchronized (this) {
                if (this.DEBUG) {
                    android.util.Log.d(TAG, "onNotifyTime: ");
                }
                scheduleNotification(0, 0L);
            }
        }

        public void onPaused(boolean z) {
            synchronized (this) {
                if (this.DEBUG) {
                    android.util.Log.d(TAG, "onPaused: " + z);
                }
                if (this.mStopped) {
                    this.mStopped = false;
                    this.mSeeking = true;
                    scheduleNotification(3, 0L);
                } else {
                    this.mPausing = z;
                    this.mSeeking = false;
                    scheduleNotification(0, 0L);
                }
            }
        }

        public void onBuffering(boolean z) {
            synchronized (this) {
                if (this.DEBUG) {
                    android.util.Log.d(TAG, "onBuffering: " + z);
                }
                this.mBuffering = z;
                scheduleNotification(0, 0L);
            }
        }

        public void onStopped() {
            synchronized (this) {
                if (this.DEBUG) {
                    android.util.Log.d(TAG, "onStopped");
                }
                this.mPaused = true;
                this.mStopped = true;
                this.mSeeking = false;
                this.mBuffering = false;
                scheduleNotification(2, 0L);
            }
        }

        @Override // android.media.MediaPlayer.OnSeekCompleteListener
        public void onSeekComplete(android.media.MediaPlayer mediaPlayer) {
            synchronized (this) {
                this.mStopped = false;
                this.mSeeking = true;
                scheduleNotification(3, 0L);
            }
        }

        public void onNewPlayer() {
            if (this.mRefresh) {
                synchronized (this) {
                    this.mStopped = false;
                    this.mSeeking = true;
                    this.mBuffering = false;
                    scheduleNotification(3, 0L);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public synchronized void notifySeek() {
            this.mSeeking = false;
            try {
                long currentTimeUs = getCurrentTimeUs(true, false);
                if (this.DEBUG) {
                    android.util.Log.d(TAG, "onSeekComplete at " + currentTimeUs);
                }
                for (android.media.MediaTimeProvider.OnMediaTimeListener onMediaTimeListener : this.mListeners) {
                    if (onMediaTimeListener == null) {
                        break;
                    }
                    onMediaTimeListener.onSeek(currentTimeUs);
                }
            } catch (java.lang.IllegalStateException e) {
                if (this.DEBUG) {
                    android.util.Log.d(TAG, "onSeekComplete but no player");
                }
                this.mPausing = true;
                notifyTimedEvent(false);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public synchronized void notifyTrackData(android.util.Pair<android.media.SubtitleTrack, byte[]> pair) {
            pair.first.onData(pair.second, true, -1L);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public synchronized void notifyStop() {
            for (android.media.MediaTimeProvider.OnMediaTimeListener onMediaTimeListener : this.mListeners) {
                if (onMediaTimeListener == null) {
                    break;
                }
                onMediaTimeListener.onStop();
            }
        }

        private int registerListener(android.media.MediaTimeProvider.OnMediaTimeListener onMediaTimeListener) {
            int i = 0;
            while (i < this.mListeners.length && this.mListeners[i] != onMediaTimeListener && this.mListeners[i] != null) {
                i++;
            }
            if (i >= this.mListeners.length) {
                int i2 = i + 1;
                android.media.MediaTimeProvider.OnMediaTimeListener[] onMediaTimeListenerArr = new android.media.MediaTimeProvider.OnMediaTimeListener[i2];
                long[] jArr = new long[i2];
                java.lang.System.arraycopy(this.mListeners, 0, onMediaTimeListenerArr, 0, this.mListeners.length);
                java.lang.System.arraycopy(this.mTimes, 0, jArr, 0, this.mTimes.length);
                this.mListeners = onMediaTimeListenerArr;
                this.mTimes = jArr;
            }
            if (this.mListeners[i] == null) {
                this.mListeners[i] = onMediaTimeListener;
                this.mTimes[i] = -1;
            }
            return i;
        }

        @Override // android.media.MediaTimeProvider
        public void notifyAt(long j, android.media.MediaTimeProvider.OnMediaTimeListener onMediaTimeListener) {
            synchronized (this) {
                if (this.DEBUG) {
                    android.util.Log.d(TAG, "notifyAt " + j);
                }
                this.mTimes[registerListener(onMediaTimeListener)] = j;
                scheduleNotification(0, 0L);
            }
        }

        @Override // android.media.MediaTimeProvider
        public void scheduleUpdate(android.media.MediaTimeProvider.OnMediaTimeListener onMediaTimeListener) {
            synchronized (this) {
                if (this.DEBUG) {
                    android.util.Log.d(TAG, "scheduleUpdate");
                }
                int registerListener = registerListener(onMediaTimeListener);
                if (!this.mStopped) {
                    this.mTimes[registerListener] = 0;
                    scheduleNotification(0, 0L);
                }
            }
        }

        @Override // android.media.MediaTimeProvider
        public void cancelNotifications(android.media.MediaTimeProvider.OnMediaTimeListener onMediaTimeListener) {
            synchronized (this) {
                int i = 0;
                while (true) {
                    if (i >= this.mListeners.length) {
                        break;
                    }
                    if (this.mListeners[i] == onMediaTimeListener) {
                        int i2 = i + 1;
                        java.lang.System.arraycopy(this.mListeners, i2, this.mListeners, i, (this.mListeners.length - i) - 1);
                        java.lang.System.arraycopy(this.mTimes, i2, this.mTimes, i, (this.mTimes.length - i) - 1);
                        this.mListeners[this.mListeners.length - 1] = null;
                        this.mTimes[this.mTimes.length - 1] = -1;
                        break;
                    }
                    if (this.mListeners[i] == null) {
                        break;
                    } else {
                        i++;
                    }
                }
                scheduleNotification(0, 0L);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public synchronized void notifyTimedEvent(boolean z) {
            long currentTimeUs;
            try {
                currentTimeUs = getCurrentTimeUs(z, true);
            } catch (java.lang.IllegalStateException e) {
                this.mRefresh = true;
                this.mPausing = true;
                currentTimeUs = getCurrentTimeUs(z, true);
            }
            if (this.mSeeking) {
                return;
            }
            if (this.DEBUG) {
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append("notifyTimedEvent(").append(this.mLastTimeUs).append(" -> ").append(currentTimeUs).append(") from {");
                boolean z2 = true;
                for (long j : this.mTimes) {
                    if (j != -1) {
                        if (!z2) {
                            sb.append(", ");
                        }
                        sb.append(j);
                        z2 = false;
                    }
                }
                sb.append("}");
                android.util.Log.d(TAG, sb.toString());
            }
            java.util.Vector vector = new java.util.Vector();
            long j2 = currentTimeUs;
            for (int i = 0; i < this.mTimes.length && this.mListeners[i] != null; i++) {
                if (this.mTimes[i] > -1) {
                    if (this.mTimes[i] <= 1000 + currentTimeUs) {
                        vector.add(this.mListeners[i]);
                        if (this.DEBUG) {
                            android.util.Log.d(TAG, android.os.Environment.MEDIA_REMOVED);
                        }
                        this.mTimes[i] = -1;
                    } else if (j2 == currentTimeUs || this.mTimes[i] < j2) {
                        j2 = this.mTimes[i];
                    }
                }
            }
            if (j2 <= currentTimeUs || this.mPaused) {
                this.mEventHandler.removeMessages(1);
            } else {
                if (this.DEBUG) {
                    android.util.Log.d(TAG, "scheduling for " + j2 + " and " + currentTimeUs);
                }
                this.mPlayer.notifyAt(j2);
            }
            java.util.Iterator it = vector.iterator();
            while (it.hasNext()) {
                ((android.media.MediaTimeProvider.OnMediaTimeListener) it.next()).onTimedEvent(currentTimeUs);
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:20:0x002f A[Catch: IllegalStateException -> 0x0080, all -> 0x00ba, TryCatch #0 {IllegalStateException -> 0x0080, blocks: (B:12:0x000d, B:14:0x0021, B:18:0x0029, B:20:0x002f, B:23:0x003f), top: B:11:0x000d, outer: #1 }] */
        @Override // android.media.MediaTimeProvider
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public long getCurrentTimeUs(boolean z, boolean z2) throws java.lang.IllegalStateException {
            boolean z3;
            synchronized (this) {
                if (this.mPaused && !z) {
                    return this.mLastReportedTime;
                }
                try {
                    this.mLastTimeUs = this.mPlayer.getCurrentPosition() * 1000;
                    if (this.mPlayer.isPlaying() && !this.mBuffering) {
                        z3 = false;
                        this.mPaused = z3;
                        if (this.DEBUG) {
                            android.util.Log.v(TAG, (this.mPaused ? "paused" : "playing") + " at " + this.mLastTimeUs);
                        }
                        if (z2 || this.mLastTimeUs >= this.mLastReportedTime) {
                            this.mLastReportedTime = this.mLastTimeUs;
                        } else if (this.mLastReportedTime - this.mLastTimeUs > 1000000) {
                            this.mStopped = false;
                            this.mSeeking = true;
                            scheduleNotification(3, 0L);
                        }
                        return this.mLastReportedTime;
                    }
                    z3 = true;
                    this.mPaused = z3;
                    if (this.DEBUG) {
                    }
                    if (z2) {
                    }
                    this.mLastReportedTime = this.mLastTimeUs;
                    return this.mLastReportedTime;
                } catch (java.lang.IllegalStateException e) {
                    if (this.mPausing) {
                        this.mPausing = false;
                        if (!z2 || this.mLastReportedTime < this.mLastTimeUs) {
                            this.mLastReportedTime = this.mLastTimeUs;
                        }
                        this.mPaused = true;
                        if (this.DEBUG) {
                            android.util.Log.d(TAG, "illegal state, but pausing: estimating at " + this.mLastReportedTime);
                        }
                        return this.mLastReportedTime;
                    }
                    throw e;
                }
            }
        }

        private class EventHandler extends android.os.Handler {
            public EventHandler(android.os.Looper looper) {
                super(looper);
            }

            @Override // android.os.Handler
            public void handleMessage(android.os.Message message) {
                if (message.what == 1) {
                    switch (message.arg1) {
                        case 0:
                            android.media.MediaPlayer.TimeProvider.this.notifyTimedEvent(true);
                            break;
                        case 2:
                            android.media.MediaPlayer.TimeProvider.this.notifyStop();
                            break;
                        case 3:
                            android.media.MediaPlayer.TimeProvider.this.notifySeek();
                            break;
                        case 4:
                            android.media.MediaPlayer.TimeProvider.this.notifyTrackData((android.util.Pair) message.obj);
                            break;
                    }
                }
            }
        }
    }

    public static final class MetricsConstants {
        public static final java.lang.String CODEC_AUDIO = "android.media.mediaplayer.audio.codec";
        public static final java.lang.String CODEC_VIDEO = "android.media.mediaplayer.video.codec";
        public static final java.lang.String DURATION = "android.media.mediaplayer.durationMs";
        public static final java.lang.String ERRORS = "android.media.mediaplayer.err";
        public static final java.lang.String ERROR_CODE = "android.media.mediaplayer.errcode";
        public static final java.lang.String FRAMES = "android.media.mediaplayer.frames";
        public static final java.lang.String FRAMES_DROPPED = "android.media.mediaplayer.dropped";
        public static final java.lang.String HEIGHT = "android.media.mediaplayer.height";
        public static final java.lang.String MIME_TYPE_AUDIO = "android.media.mediaplayer.audio.mime";
        public static final java.lang.String MIME_TYPE_VIDEO = "android.media.mediaplayer.video.mime";
        public static final java.lang.String PLAYING = "android.media.mediaplayer.playingMs";
        public static final java.lang.String WIDTH = "android.media.mediaplayer.width";

        private MetricsConstants() {
        }
    }
}
