package android.media.tv.interactive;

/* loaded from: classes2.dex */
public abstract class TvInteractiveAppService extends android.app.Service {
    public static final java.lang.String COMMAND_PARAMETER_KEY_CHANGE_CHANNEL_QUIETLY = "command_change_channel_quietly";
    public static final java.lang.String COMMAND_PARAMETER_KEY_CHANNEL_URI = "command_channel_uri";
    public static final java.lang.String COMMAND_PARAMETER_KEY_INPUT_ID = "command_input_id";
    public static final java.lang.String COMMAND_PARAMETER_KEY_PLAYBACK_PARAMS = "command_playback_params";
    public static final java.lang.String COMMAND_PARAMETER_KEY_PROGRAM_URI = "command_program_uri";
    public static final java.lang.String COMMAND_PARAMETER_KEY_STOP_MODE = "command_stop_mode";
    public static final java.lang.String COMMAND_PARAMETER_KEY_TIME_POSITION = "command_time_position";
    public static final java.lang.String COMMAND_PARAMETER_KEY_TIME_SHIFT_MODE = "command_time_shift_mode";
    public static final java.lang.String COMMAND_PARAMETER_KEY_TRACK_ID = "command_track_id";
    public static final java.lang.String COMMAND_PARAMETER_KEY_TRACK_TYPE = "command_track_type";
    public static final java.lang.String COMMAND_PARAMETER_KEY_VOLUME = "command_volume";
    public static final int COMMAND_PARAMETER_VALUE_STOP_MODE_BLANK = 1;
    public static final int COMMAND_PARAMETER_VALUE_STOP_MODE_FREEZE = 2;
    private static final boolean DEBUG = false;
    private static final int DETACH_MEDIA_VIEW_TIMEOUT_MS = 5000;
    public static final java.lang.String PLAYBACK_COMMAND_TYPE_FREEZE = "freeze";
    public static final java.lang.String PLAYBACK_COMMAND_TYPE_SELECT_TRACK = "select_track";
    public static final java.lang.String PLAYBACK_COMMAND_TYPE_SET_STREAM_VOLUME = "set_stream_volume";
    public static final java.lang.String PLAYBACK_COMMAND_TYPE_STOP = "stop";
    public static final java.lang.String PLAYBACK_COMMAND_TYPE_TUNE = "tune";
    public static final java.lang.String PLAYBACK_COMMAND_TYPE_TUNE_NEXT = "tune_next";
    public static final java.lang.String PLAYBACK_COMMAND_TYPE_TUNE_PREV = "tune_previous";
    public static final java.lang.String SERVICE_INTERFACE = "android.media.tv.interactive.TvInteractiveAppService";
    public static final java.lang.String SERVICE_META_DATA = "android.media.tv.interactive.app";
    private static final java.lang.String TAG = "TvInteractiveAppService";
    public static final java.lang.String TIME_SHIFT_COMMAND_TYPE_PAUSE = "pause";
    public static final java.lang.String TIME_SHIFT_COMMAND_TYPE_PLAY = "play";
    public static final java.lang.String TIME_SHIFT_COMMAND_TYPE_RESUME = "resume";
    public static final java.lang.String TIME_SHIFT_COMMAND_TYPE_SEEK_TO = "seek_to";
    public static final java.lang.String TIME_SHIFT_COMMAND_TYPE_SET_MODE = "set_mode";
    public static final java.lang.String TIME_SHIFT_COMMAND_TYPE_SET_PLAYBACK_PARAMS = "set_playback_params";
    private final android.os.Handler mServiceHandler = new android.media.tv.interactive.TvInteractiveAppService.ServiceHandler();
    private final android.os.RemoteCallbackList<android.media.tv.interactive.ITvInteractiveAppServiceCallback> mCallbacks = new android.os.RemoteCallbackList<>();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PlaybackCommandStopMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PlaybackCommandType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface TimeShiftCommandType {
    }

    public abstract android.media.tv.interactive.TvInteractiveAppService.Session onCreateSession(java.lang.String str, int i);

    @Override // android.app.Service
    public final android.os.IBinder onBind(android.content.Intent intent) {
        return new android.media.tv.interactive.ITvInteractiveAppService.Stub() { // from class: android.media.tv.interactive.TvInteractiveAppService.1
            @Override // android.media.tv.interactive.ITvInteractiveAppService
            public void registerCallback(android.media.tv.interactive.ITvInteractiveAppServiceCallback iTvInteractiveAppServiceCallback) {
                if (iTvInteractiveAppServiceCallback != null) {
                    android.media.tv.interactive.TvInteractiveAppService.this.mCallbacks.register(iTvInteractiveAppServiceCallback);
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppService
            public void unregisterCallback(android.media.tv.interactive.ITvInteractiveAppServiceCallback iTvInteractiveAppServiceCallback) {
                if (iTvInteractiveAppServiceCallback != null) {
                    android.media.tv.interactive.TvInteractiveAppService.this.mCallbacks.unregister(iTvInteractiveAppServiceCallback);
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppService
            public void createSession(android.view.InputChannel inputChannel, android.media.tv.interactive.ITvInteractiveAppSessionCallback iTvInteractiveAppSessionCallback, java.lang.String str, int i) {
                if (iTvInteractiveAppSessionCallback == null) {
                    return;
                }
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = inputChannel;
                obtain.arg2 = iTvInteractiveAppSessionCallback;
                obtain.arg3 = str;
                obtain.arg4 = java.lang.Integer.valueOf(i);
                android.media.tv.interactive.TvInteractiveAppService.this.mServiceHandler.obtainMessage(1, obtain).sendToTarget();
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppService
            public void registerAppLinkInfo(android.media.tv.interactive.AppLinkInfo appLinkInfo) {
                android.media.tv.interactive.TvInteractiveAppService.this.onRegisterAppLinkInfo(appLinkInfo);
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppService
            public void unregisterAppLinkInfo(android.media.tv.interactive.AppLinkInfo appLinkInfo) {
                android.media.tv.interactive.TvInteractiveAppService.this.onUnregisterAppLinkInfo(appLinkInfo);
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppService
            public void sendAppLinkCommand(android.os.Bundle bundle) {
                android.media.tv.interactive.TvInteractiveAppService.this.onAppLinkCommand(bundle);
            }
        };
    }

    public void onRegisterAppLinkInfo(android.media.tv.interactive.AppLinkInfo appLinkInfo) {
    }

    public void onUnregisterAppLinkInfo(android.media.tv.interactive.AppLinkInfo appLinkInfo) {
    }

    public void onAppLinkCommand(android.os.Bundle bundle) {
    }

    public final void notifyStateChanged(int i, int i2, int i3) {
        com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
        obtain.arg1 = java.lang.Integer.valueOf(i);
        obtain.arg2 = java.lang.Integer.valueOf(i2);
        obtain.arg3 = java.lang.Integer.valueOf(i3);
        this.mServiceHandler.obtainMessage(3, obtain).sendToTarget();
    }

    public static abstract class Session implements android.view.KeyEvent.Callback {
        private final android.content.Context mContext;
        final android.os.Handler mHandler;
        private android.graphics.Rect mMediaFrame;
        private android.view.View mMediaView;
        private android.media.tv.interactive.TvInteractiveAppService.MediaViewCleanUpTask mMediaViewCleanUpTask;
        private android.widget.FrameLayout mMediaViewContainer;
        private boolean mMediaViewEnabled;
        private android.media.tv.interactive.ITvInteractiveAppSessionCallback mSessionCallback;
        private android.view.Surface mSurface;
        private final android.view.WindowManager mWindowManager;
        private android.view.WindowManager.LayoutParams mWindowParams;
        private android.os.IBinder mWindowToken;
        private final android.view.KeyEvent.DispatcherState mDispatcherState = new android.view.KeyEvent.DispatcherState();
        private final java.lang.Object mLock = new java.lang.Object();
        private final java.util.List<java.lang.Runnable> mPendingActions = new java.util.ArrayList();

        public abstract void onRelease();

        public abstract boolean onSetSurface(android.view.Surface surface);

        public Session(android.content.Context context) {
            this.mContext = context;
            this.mWindowManager = (android.view.WindowManager) context.getSystemService(android.content.Context.WINDOW_SERVICE);
            this.mHandler = new android.os.Handler(context.getMainLooper());
        }

        public void setMediaViewEnabled(final boolean z) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.1
                @Override // java.lang.Runnable
                public void run() {
                    if (z == android.media.tv.interactive.TvInteractiveAppService.Session.this.mMediaViewEnabled) {
                        return;
                    }
                    android.media.tv.interactive.TvInteractiveAppService.Session.this.mMediaViewEnabled = z;
                    if (z) {
                        if (android.media.tv.interactive.TvInteractiveAppService.Session.this.mWindowToken != null) {
                            android.media.tv.interactive.TvInteractiveAppService.Session.this.createMediaView(android.media.tv.interactive.TvInteractiveAppService.Session.this.mWindowToken, android.media.tv.interactive.TvInteractiveAppService.Session.this.mMediaFrame);
                            return;
                        }
                        return;
                    }
                    android.media.tv.interactive.TvInteractiveAppService.Session.this.removeMediaView(false);
                }
            });
        }

        public boolean isMediaViewEnabled() {
            return this.mMediaViewEnabled;
        }

        public void onStartInteractiveApp() {
        }

        public void onStopInteractiveApp() {
        }

        public void onResetInteractiveApp() {
        }

        public void onCreateBiInteractiveAppRequest(android.net.Uri uri, android.os.Bundle bundle) {
        }

        public void onDestroyBiInteractiveAppRequest(java.lang.String str) {
        }

        public void onSetTeletextAppEnabled(boolean z) {
        }

        public void onCurrentVideoBounds(android.graphics.Rect rect) {
        }

        public void onCurrentChannelUri(android.net.Uri uri) {
        }

        public void onCurrentChannelLcn(int i) {
        }

        public void onStreamVolume(float f) {
        }

        public void onTrackInfoList(java.util.List<android.media.tv.TvTrackInfo> list) {
        }

        public void onCurrentTvInputId(java.lang.String str) {
        }

        public void onTimeShiftMode(int i) {
        }

        public void onAvailableSpeeds(float[] fArr) {
        }

        public void onTvRecordingInfo(android.media.tv.TvRecordingInfo tvRecordingInfo) {
        }

        public void onTvRecordingInfoList(java.util.List<android.media.tv.TvRecordingInfo> list) {
        }

        public void onRecordingStarted(java.lang.String str, java.lang.String str2) {
        }

        public void onRecordingStopped(java.lang.String str) {
        }

        public void onRecordingConnectionFailed(java.lang.String str, java.lang.String str2) {
        }

        public void onRecordingDisconnected(java.lang.String str, java.lang.String str2) {
        }

        public void onRecordingTuned(java.lang.String str, android.net.Uri uri) {
        }

        public void onRecordingError(java.lang.String str, int i) {
        }

        public void onRecordingScheduled(java.lang.String str, java.lang.String str2) {
        }

        public void onSigningResult(java.lang.String str, byte[] bArr) {
        }

        public void onCertificate(java.lang.String str, int i, android.net.http.SslCertificate sslCertificate) {
        }

        public void onError(java.lang.String str, android.os.Bundle bundle) {
        }

        public void onTimeShiftPlaybackParams(android.media.PlaybackParams playbackParams) {
        }

        public void onTimeShiftStatusChanged(java.lang.String str, int i) {
        }

        public void onTimeShiftStartPositionChanged(java.lang.String str, long j) {
        }

        public void onTimeShiftCurrentPositionChanged(java.lang.String str, long j) {
        }

        public void onSurfaceChanged(int i, int i2, int i3) {
        }

        public void onMediaViewSizeChanged(int i, int i2) {
        }

        public android.view.View onCreateMediaView() {
            return null;
        }

        public void onTuned(android.net.Uri uri) {
        }

        public void onTrackSelected(int i, java.lang.String str) {
        }

        public void onTracksChanged(java.util.List<android.media.tv.TvTrackInfo> list) {
        }

        public void onVideoAvailable() {
        }

        public void onVideoUnavailable(int i) {
        }

        public void onVideoFreezeUpdated(boolean z) {
        }

        public void onContentAllowed() {
        }

        public void onContentBlocked(android.media.tv.TvContentRating tvContentRating) {
        }

        public void onSignalStrength(int i) {
        }

        public void onBroadcastInfoResponse(android.media.tv.BroadcastInfoResponse broadcastInfoResponse) {
        }

        public void onAdResponse(android.media.tv.AdResponse adResponse) {
        }

        public void onAdBufferConsumed(android.media.tv.AdBuffer adBuffer) {
        }

        public void onTvMessage(int i, android.os.Bundle bundle) {
        }

        public void onSelectedTrackInfo(java.util.List<android.media.tv.TvTrackInfo> list) {
        }

        @Override // android.view.KeyEvent.Callback
        public boolean onKeyDown(int i, android.view.KeyEvent keyEvent) {
            return false;
        }

        @Override // android.view.KeyEvent.Callback
        public boolean onKeyLongPress(int i, android.view.KeyEvent keyEvent) {
            return false;
        }

        @Override // android.view.KeyEvent.Callback
        public boolean onKeyMultiple(int i, int i2, android.view.KeyEvent keyEvent) {
            return false;
        }

        @Override // android.view.KeyEvent.Callback
        public boolean onKeyUp(int i, android.view.KeyEvent keyEvent) {
            return false;
        }

        public boolean onTouchEvent(android.view.MotionEvent motionEvent) {
            return false;
        }

        public boolean onTrackballEvent(android.view.MotionEvent motionEvent) {
            return false;
        }

        public boolean onGenericMotionEvent(android.view.MotionEvent motionEvent) {
            return false;
        }

        public void layoutSurface(final int i, final int i2, final int i3, final int i4) {
            if (i > i3 || i2 > i4) {
                throw new java.lang.IllegalArgumentException("Invalid parameter");
            }
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.2
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (android.media.tv.interactive.TvInteractiveAppService.Session.this.mSessionCallback != null) {
                            android.media.tv.interactive.TvInteractiveAppService.Session.this.mSessionCallback.onLayoutSurface(i, i2, i3, i4);
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(android.media.tv.interactive.TvInteractiveAppService.TAG, "error in layoutSurface", e);
                    }
                }
            });
        }

        public void requestBroadcastInfo(final android.media.tv.BroadcastInfoRequest broadcastInfoRequest) {
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.3
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (android.media.tv.interactive.TvInteractiveAppService.Session.this.mSessionCallback != null) {
                            android.media.tv.interactive.TvInteractiveAppService.Session.this.mSessionCallback.onBroadcastInfoRequest(broadcastInfoRequest);
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(android.media.tv.interactive.TvInteractiveAppService.TAG, "error in requestBroadcastInfo", e);
                    }
                }
            });
        }

        public void removeBroadcastInfo(final int i) {
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.4
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (android.media.tv.interactive.TvInteractiveAppService.Session.this.mSessionCallback != null) {
                            android.media.tv.interactive.TvInteractiveAppService.Session.this.mSessionCallback.onRemoveBroadcastInfo(i);
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(android.media.tv.interactive.TvInteractiveAppService.TAG, "error in removeBroadcastInfo", e);
                    }
                }
            });
        }

        public void sendPlaybackCommandRequest(final java.lang.String str, final android.os.Bundle bundle) {
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.5
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (android.media.tv.interactive.TvInteractiveAppService.Session.this.mSessionCallback != null) {
                            android.media.tv.interactive.TvInteractiveAppService.Session.this.mSessionCallback.onCommandRequest(str, bundle);
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(android.media.tv.interactive.TvInteractiveAppService.TAG, "error in requestCommand", e);
                    }
                }
            });
        }

        public void sendTimeShiftCommandRequest(final java.lang.String str, final android.os.Bundle bundle) {
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.6
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (android.media.tv.interactive.TvInteractiveAppService.Session.this.mSessionCallback != null) {
                            android.media.tv.interactive.TvInteractiveAppService.Session.this.mSessionCallback.onTimeShiftCommandRequest(str, bundle);
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(android.media.tv.interactive.TvInteractiveAppService.TAG, "error in requestTimeShiftCommand", e);
                    }
                }
            });
        }

        public void setVideoBounds(final android.graphics.Rect rect) {
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.7
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (android.media.tv.interactive.TvInteractiveAppService.Session.this.mSessionCallback != null) {
                            android.media.tv.interactive.TvInteractiveAppService.Session.this.mSessionCallback.onSetVideoBounds(rect);
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(android.media.tv.interactive.TvInteractiveAppService.TAG, "error in setVideoBounds", e);
                    }
                }
            });
        }

        public void requestCurrentVideoBounds() {
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.8
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (android.media.tv.interactive.TvInteractiveAppService.Session.this.mSessionCallback != null) {
                            android.media.tv.interactive.TvInteractiveAppService.Session.this.mSessionCallback.onRequestCurrentVideoBounds();
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(android.media.tv.interactive.TvInteractiveAppService.TAG, "error in requestCurrentVideoBounds", e);
                    }
                }
            });
        }

        public void requestCurrentChannelUri() {
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.9
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (android.media.tv.interactive.TvInteractiveAppService.Session.this.mSessionCallback != null) {
                            android.media.tv.interactive.TvInteractiveAppService.Session.this.mSessionCallback.onRequestCurrentChannelUri();
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(android.media.tv.interactive.TvInteractiveAppService.TAG, "error in requestCurrentChannelUri", e);
                    }
                }
            });
        }

        public void requestCurrentChannelLcn() {
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.10
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (android.media.tv.interactive.TvInteractiveAppService.Session.this.mSessionCallback != null) {
                            android.media.tv.interactive.TvInteractiveAppService.Session.this.mSessionCallback.onRequestCurrentChannelLcn();
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(android.media.tv.interactive.TvInteractiveAppService.TAG, "error in requestCurrentChannelLcn", e);
                    }
                }
            });
        }

        public void requestStreamVolume() {
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.11
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (android.media.tv.interactive.TvInteractiveAppService.Session.this.mSessionCallback != null) {
                            android.media.tv.interactive.TvInteractiveAppService.Session.this.mSessionCallback.onRequestStreamVolume();
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(android.media.tv.interactive.TvInteractiveAppService.TAG, "error in requestStreamVolume", e);
                    }
                }
            });
        }

        public void requestTrackInfoList() {
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.12
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (android.media.tv.interactive.TvInteractiveAppService.Session.this.mSessionCallback != null) {
                            android.media.tv.interactive.TvInteractiveAppService.Session.this.mSessionCallback.onRequestTrackInfoList();
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(android.media.tv.interactive.TvInteractiveAppService.TAG, "error in requestTrackInfoList", e);
                    }
                }
            });
        }

        public void requestCurrentTvInputId() {
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.13
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (android.media.tv.interactive.TvInteractiveAppService.Session.this.mSessionCallback != null) {
                            android.media.tv.interactive.TvInteractiveAppService.Session.this.mSessionCallback.onRequestCurrentTvInputId();
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(android.media.tv.interactive.TvInteractiveAppService.TAG, "error in requestCurrentTvInputId", e);
                    }
                }
            });
        }

        public void requestTimeShiftMode() {
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.14
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (android.media.tv.interactive.TvInteractiveAppService.Session.this.mSessionCallback != null) {
                            android.media.tv.interactive.TvInteractiveAppService.Session.this.mSessionCallback.onRequestTimeShiftMode();
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(android.media.tv.interactive.TvInteractiveAppService.TAG, "error in requestTimeShiftMode", e);
                    }
                }
            });
        }

        public void requestAvailableSpeeds() {
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.15
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (android.media.tv.interactive.TvInteractiveAppService.Session.this.mSessionCallback != null) {
                            android.media.tv.interactive.TvInteractiveAppService.Session.this.mSessionCallback.onRequestAvailableSpeeds();
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(android.media.tv.interactive.TvInteractiveAppService.TAG, "error in requestAvailableSpeeds", e);
                    }
                }
            });
        }

        public void requestSelectedTrackInfo() {
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService$Session$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    android.media.tv.interactive.TvInteractiveAppService.Session.this.lambda$requestSelectedTrackInfo$0();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$requestSelectedTrackInfo$0() {
            try {
                if (this.mSessionCallback != null) {
                    this.mSessionCallback.onRequestSelectedTrackInfo();
                }
            } catch (android.os.RemoteException e) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppService.TAG, "error in requestSelectedTrackInfo", e);
            }
        }

        public void requestStartRecording(final java.lang.String str, final android.net.Uri uri) {
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService$Session$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.media.tv.interactive.TvInteractiveAppService.Session.this.lambda$requestStartRecording$1(str, uri);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$requestStartRecording$1(java.lang.String str, android.net.Uri uri) {
            try {
                if (this.mSessionCallback != null) {
                    this.mSessionCallback.onRequestStartRecording(str, uri);
                }
            } catch (android.os.RemoteException e) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppService.TAG, "error in requestStartRecording", e);
            }
        }

        public void requestStopRecording(final java.lang.String str) {
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService$Session$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    android.media.tv.interactive.TvInteractiveAppService.Session.this.lambda$requestStopRecording$2(str);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$requestStopRecording$2(java.lang.String str) {
            try {
                if (this.mSessionCallback != null) {
                    this.mSessionCallback.onRequestStopRecording(str);
                }
            } catch (android.os.RemoteException e) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppService.TAG, "error in requestStopRecording", e);
            }
        }

        public void requestScheduleRecording(final java.lang.String str, final java.lang.String str2, final android.net.Uri uri, final android.net.Uri uri2, final android.os.Bundle bundle) {
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService$Session$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.media.tv.interactive.TvInteractiveAppService.Session.this.lambda$requestScheduleRecording$3(str, str2, uri, uri2, bundle);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$requestScheduleRecording$3(java.lang.String str, java.lang.String str2, android.net.Uri uri, android.net.Uri uri2, android.os.Bundle bundle) {
            try {
                if (this.mSessionCallback != null) {
                    this.mSessionCallback.onRequestScheduleRecording(str, str2, uri, uri2, bundle);
                }
            } catch (android.os.RemoteException e) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppService.TAG, "error in requestScheduleRecording", e);
            }
        }

        public void requestScheduleRecording(final java.lang.String str, final java.lang.String str2, final android.net.Uri uri, final long j, final long j2, final int i, final android.os.Bundle bundle) {
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService$Session$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    android.media.tv.interactive.TvInteractiveAppService.Session.this.lambda$requestScheduleRecording$4(str, str2, uri, j, j2, i, bundle);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$requestScheduleRecording$4(java.lang.String str, java.lang.String str2, android.net.Uri uri, long j, long j2, int i, android.os.Bundle bundle) {
            try {
                if (this.mSessionCallback != null) {
                    this.mSessionCallback.onRequestScheduleRecording2(str, str2, uri, j, j2, i, bundle);
                }
            } catch (android.os.RemoteException e) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppService.TAG, "error in requestScheduleRecording", e);
            }
        }

        public void setTvRecordingInfo(final java.lang.String str, final android.media.tv.TvRecordingInfo tvRecordingInfo) {
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService$Session$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    android.media.tv.interactive.TvInteractiveAppService.Session.this.lambda$setTvRecordingInfo$5(str, tvRecordingInfo);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setTvRecordingInfo$5(java.lang.String str, android.media.tv.TvRecordingInfo tvRecordingInfo) {
            try {
                if (this.mSessionCallback != null) {
                    this.mSessionCallback.onSetTvRecordingInfo(str, tvRecordingInfo);
                }
            } catch (android.os.RemoteException e) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppService.TAG, "error in setTvRecordingInfo", e);
            }
        }

        public void requestTvRecordingInfo(final java.lang.String str) {
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService$Session$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    android.media.tv.interactive.TvInteractiveAppService.Session.this.lambda$requestTvRecordingInfo$6(str);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$requestTvRecordingInfo$6(java.lang.String str) {
            try {
                if (this.mSessionCallback != null) {
                    this.mSessionCallback.onRequestTvRecordingInfo(str);
                }
            } catch (android.os.RemoteException e) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppService.TAG, "error in requestTvRecordingInfo", e);
            }
        }

        public void requestTvRecordingInfoList(final int i) {
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService$Session$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    android.media.tv.interactive.TvInteractiveAppService.Session.this.lambda$requestTvRecordingInfoList$7(i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$requestTvRecordingInfoList$7(int i) {
            try {
                if (this.mSessionCallback != null) {
                    this.mSessionCallback.onRequestTvRecordingInfoList(i);
                }
            } catch (android.os.RemoteException e) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppService.TAG, "error in requestTvRecordingInfoList", e);
            }
        }

        public void requestSigning(final java.lang.String str, final java.lang.String str2, final java.lang.String str3, final byte[] bArr) {
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.16
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (android.media.tv.interactive.TvInteractiveAppService.Session.this.mSessionCallback != null) {
                            android.media.tv.interactive.TvInteractiveAppService.Session.this.mSessionCallback.onRequestSigning(str, str2, str3, bArr);
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(android.media.tv.interactive.TvInteractiveAppService.TAG, "error in requestSigning", e);
                    }
                }
            });
        }

        public void requestSigning(final java.lang.String str, final java.lang.String str2, final java.lang.String str3, final int i, final byte[] bArr) {
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.17
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (android.media.tv.interactive.TvInteractiveAppService.Session.this.mSessionCallback != null) {
                            android.media.tv.interactive.TvInteractiveAppService.Session.this.mSessionCallback.onRequestSigning2(str, str2, str3, i, bArr);
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(android.media.tv.interactive.TvInteractiveAppService.TAG, "error in requestSigning", e);
                    }
                }
            });
        }

        public void requestCertificate(final java.lang.String str, final int i) {
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.18
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (android.media.tv.interactive.TvInteractiveAppService.Session.this.mSessionCallback != null) {
                            android.media.tv.interactive.TvInteractiveAppService.Session.this.mSessionCallback.onRequestCertificate(str, i);
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(android.media.tv.interactive.TvInteractiveAppService.TAG, "error in requestCertificate", e);
                    }
                }
            });
        }

        public void requestAd(final android.media.tv.AdRequest adRequest) {
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.19
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (android.media.tv.interactive.TvInteractiveAppService.Session.this.mSessionCallback != null) {
                            android.media.tv.interactive.TvInteractiveAppService.Session.this.mSessionCallback.onAdRequest(adRequest);
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(android.media.tv.interactive.TvInteractiveAppService.TAG, "error in requestAd", e);
                    }
                }
            });
        }

        void startInteractiveApp() {
            onStartInteractiveApp();
        }

        void stopInteractiveApp() {
            onStopInteractiveApp();
        }

        void resetInteractiveApp() {
            onResetInteractiveApp();
        }

        void createBiInteractiveApp(android.net.Uri uri, android.os.Bundle bundle) {
            onCreateBiInteractiveAppRequest(uri, bundle);
        }

        void destroyBiInteractiveApp(java.lang.String str) {
            onDestroyBiInteractiveAppRequest(str);
        }

        void setTeletextAppEnabled(boolean z) {
            onSetTeletextAppEnabled(z);
        }

        void sendCurrentVideoBounds(android.graphics.Rect rect) {
            onCurrentVideoBounds(rect);
        }

        void sendCurrentChannelUri(android.net.Uri uri) {
            onCurrentChannelUri(uri);
        }

        void sendCurrentChannelLcn(int i) {
            onCurrentChannelLcn(i);
        }

        void sendStreamVolume(float f) {
            onStreamVolume(f);
        }

        void sendTrackInfoList(java.util.List<android.media.tv.TvTrackInfo> list) {
            onTrackInfoList(list);
        }

        void sendCurrentTvInputId(java.lang.String str) {
            onCurrentTvInputId(str);
        }

        void sendTimeShiftMode(int i) {
            onTimeShiftMode(i);
        }

        void sendAvailableSpeeds(float[] fArr) {
            onAvailableSpeeds(fArr);
        }

        void sendTvRecordingInfo(android.media.tv.TvRecordingInfo tvRecordingInfo) {
            onTvRecordingInfo(tvRecordingInfo);
        }

        void sendTvRecordingInfoList(java.util.List<android.media.tv.TvRecordingInfo> list) {
            onTvRecordingInfoList(list);
        }

        void sendSigningResult(java.lang.String str, byte[] bArr) {
            onSigningResult(str, bArr);
        }

        void sendCertificate(java.lang.String str, int i, android.os.Bundle bundle) {
            onCertificate(str, i, android.net.http.SslCertificate.restoreState(bundle));
        }

        void notifyError(java.lang.String str, android.os.Bundle bundle) {
            onError(str, bundle);
        }

        void release() {
            onRelease();
            if (this.mSurface != null) {
                this.mSurface.release();
                this.mSurface = null;
            }
            synchronized (this.mLock) {
                this.mSessionCallback = null;
                this.mPendingActions.clear();
            }
            removeMediaView(true);
        }

        void notifyTuned(android.net.Uri uri) {
            onTuned(uri);
        }

        void notifyTrackSelected(int i, java.lang.String str) {
            if (str == null) {
                str = "";
            }
            onTrackSelected(i, str);
        }

        void notifyTracksChanged(java.util.List<android.media.tv.TvTrackInfo> list) {
            onTracksChanged(list);
        }

        void notifyVideoAvailable() {
            onVideoAvailable();
        }

        void notifyVideoUnavailable(int i) {
            onVideoUnavailable(i);
        }

        void notifyVideoFreezeUpdated(boolean z) {
            onVideoFreezeUpdated(z);
        }

        void notifyContentAllowed() {
            onContentAllowed();
        }

        void notifyContentBlocked(android.media.tv.TvContentRating tvContentRating) {
            onContentBlocked(tvContentRating);
        }

        void notifySignalStrength(int i) {
            onSignalStrength(i);
        }

        void notifyBroadcastInfoResponse(android.media.tv.BroadcastInfoResponse broadcastInfoResponse) {
            onBroadcastInfoResponse(broadcastInfoResponse);
        }

        void notifyAdResponse(android.media.tv.AdResponse adResponse) {
            onAdResponse(adResponse);
        }

        void notifyTvMessage(int i, android.os.Bundle bundle) {
            onTvMessage(i, bundle);
        }

        void sendSelectedTrackInfo(java.util.List<android.media.tv.TvTrackInfo> list) {
            onSelectedTrackInfo(list);
        }

        void notifyAdBufferConsumed(android.media.tv.AdBuffer adBuffer) {
            onAdBufferConsumed(adBuffer);
        }

        void notifyRecordingStarted(java.lang.String str, java.lang.String str2) {
            onRecordingStarted(str, str2);
        }

        void notifyRecordingStopped(java.lang.String str) {
            onRecordingStopped(str);
        }

        void notifyRecordingConnectionFailed(java.lang.String str, java.lang.String str2) {
            onRecordingConnectionFailed(str, str2);
        }

        void notifyRecordingDisconnected(java.lang.String str, java.lang.String str2) {
            onRecordingDisconnected(str, str2);
        }

        void notifyRecordingTuned(java.lang.String str, android.net.Uri uri) {
            onRecordingTuned(str, uri);
        }

        void notifyRecordingError(java.lang.String str, int i) {
            onRecordingError(str, i);
        }

        void notifyRecordingScheduled(java.lang.String str, java.lang.String str2) {
            onRecordingScheduled(str, str2);
        }

        void notifyTimeShiftPlaybackParams(android.media.PlaybackParams playbackParams) {
            onTimeShiftPlaybackParams(playbackParams);
        }

        void notifyTimeShiftStatusChanged(java.lang.String str, int i) {
            onTimeShiftStatusChanged(str, i);
        }

        void notifyTimeShiftStartPositionChanged(java.lang.String str, long j) {
            onTimeShiftStartPositionChanged(str, j);
        }

        void notifyTimeShiftCurrentPositionChanged(java.lang.String str, long j) {
            onTimeShiftCurrentPositionChanged(str, j);
        }

        public void notifySessionStateChanged(final int i, final int i2) {
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.20
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (android.media.tv.interactive.TvInteractiveAppService.Session.this.mSessionCallback != null) {
                            android.media.tv.interactive.TvInteractiveAppService.Session.this.mSessionCallback.onSessionStateChanged(i, i2);
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(android.media.tv.interactive.TvInteractiveAppService.TAG, "error in notifySessionStateChanged", e);
                    }
                }
            });
        }

        public final void notifyBiInteractiveAppCreated(final android.net.Uri uri, final java.lang.String str) {
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.21
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (android.media.tv.interactive.TvInteractiveAppService.Session.this.mSessionCallback != null) {
                            android.media.tv.interactive.TvInteractiveAppService.Session.this.mSessionCallback.onBiInteractiveAppCreated(uri, str);
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(android.media.tv.interactive.TvInteractiveAppService.TAG, "error in notifyBiInteractiveAppCreated", e);
                    }
                }
            });
        }

        public final void notifyTeletextAppStateChanged(final int i) {
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.22
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (android.media.tv.interactive.TvInteractiveAppService.Session.this.mSessionCallback != null) {
                            android.media.tv.interactive.TvInteractiveAppService.Session.this.mSessionCallback.onTeletextAppStateChanged(i);
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(android.media.tv.interactive.TvInteractiveAppService.TAG, "error in notifyTeletextAppState", e);
                    }
                }
            });
        }

        public void notifyAdBufferReady(final android.media.tv.AdBuffer adBuffer) {
            try {
                final android.media.tv.AdBuffer dupAdBuffer = android.media.tv.AdBuffer.dupAdBuffer(adBuffer);
                executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.interactive.TvInteractiveAppService.Session.23
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            try {
                                if (android.media.tv.interactive.TvInteractiveAppService.Session.this.mSessionCallback != null) {
                                    android.media.tv.interactive.TvInteractiveAppService.Session.this.mSessionCallback.onAdBufferReady(dupAdBuffer);
                                }
                                if (dupAdBuffer == null) {
                                    return;
                                }
                            } catch (android.os.RemoteException e) {
                                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppService.TAG, "error in notifyAdBuffer", e);
                                if (dupAdBuffer == null) {
                                    return;
                                }
                            }
                            dupAdBuffer.getSharedMemory().close();
                        } catch (java.lang.Throwable th) {
                            if (dupAdBuffer != null) {
                                dupAdBuffer.getSharedMemory().close();
                            }
                            throw th;
                        }
                    }
                });
            } catch (java.io.IOException e) {
                android.util.Log.w(android.media.tv.interactive.TvInteractiveAppService.TAG, "dup AdBuffer error in notifyAdBufferReady:", e);
            }
        }

        int dispatchInputEvent(android.view.InputEvent inputEvent, android.view.InputEventReceiver inputEventReceiver) {
            if (inputEvent instanceof android.view.KeyEvent) {
                return ((android.view.KeyEvent) inputEvent).dispatch(this, this.mDispatcherState, this) ? 1 : 0;
            }
            if (inputEvent instanceof android.view.MotionEvent) {
                android.view.MotionEvent motionEvent = (android.view.MotionEvent) inputEvent;
                return motionEvent.isTouchEvent() ? onTouchEvent(motionEvent) ? 1 : 0 : (motionEvent.getSource() & 4) != 0 ? onTrackballEvent(motionEvent) ? 1 : 0 : onGenericMotionEvent(motionEvent) ? 1 : 0;
            }
            return 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void initialize(android.media.tv.interactive.ITvInteractiveAppSessionCallback iTvInteractiveAppSessionCallback) {
            synchronized (this.mLock) {
                this.mSessionCallback = iTvInteractiveAppSessionCallback;
                java.util.Iterator<java.lang.Runnable> it = this.mPendingActions.iterator();
                while (it.hasNext()) {
                    it.next().run();
                }
                this.mPendingActions.clear();
            }
        }

        void setSurface(android.view.Surface surface) {
            onSetSurface(surface);
            if (this.mSurface != null) {
                this.mSurface.release();
            }
            this.mSurface = surface;
        }

        void dispatchSurfaceChanged(int i, int i2, int i3) {
            onSurfaceChanged(i, i2, i3);
        }

        private void executeOrPostRunnableOnMainThread(java.lang.Runnable runnable) {
            synchronized (this.mLock) {
                if (this.mSessionCallback == null) {
                    this.mPendingActions.add(runnable);
                } else if (this.mHandler.getLooper().isCurrentThread()) {
                    runnable.run();
                } else {
                    this.mHandler.post(runnable);
                }
            }
        }

        void createMediaView(android.os.IBinder iBinder, android.graphics.Rect rect) {
            int i;
            if (this.mMediaViewContainer != null) {
                removeMediaView(false);
            }
            this.mWindowToken = iBinder;
            this.mMediaFrame = rect;
            onMediaViewSizeChanged(rect.right - rect.left, rect.bottom - rect.top);
            if (!this.mMediaViewEnabled) {
                return;
            }
            this.mMediaView = onCreateMediaView();
            if (this.mMediaView == null) {
                return;
            }
            if (this.mMediaViewCleanUpTask != null) {
                this.mMediaViewCleanUpTask.cancel(true);
                this.mMediaViewCleanUpTask = null;
            }
            this.mMediaViewContainer = new android.widget.FrameLayout(this.mContext.getApplicationContext());
            this.mMediaViewContainer.addView(this.mMediaView);
            if (!android.app.ActivityManager.isHighEndGfx()) {
                i = 536;
            } else {
                i = 16777752;
            }
            this.mWindowParams = new android.view.WindowManager.LayoutParams(rect.right - rect.left, rect.bottom - rect.top, rect.left, rect.top, 1001, i, -2);
            this.mWindowParams.privateFlags |= 64;
            this.mWindowParams.gravity = 8388659;
            this.mWindowParams.token = iBinder;
            this.mWindowManager.addView(this.mMediaViewContainer, this.mWindowParams);
        }

        void relayoutMediaView(android.graphics.Rect rect) {
            if (this.mMediaFrame == null || this.mMediaFrame.width() != rect.width() || this.mMediaFrame.height() != rect.height()) {
                onMediaViewSizeChanged(rect.right - rect.left, rect.bottom - rect.top);
            }
            this.mMediaFrame = rect;
            if (!this.mMediaViewEnabled || this.mMediaViewContainer == null) {
                return;
            }
            this.mWindowParams.x = rect.left;
            this.mWindowParams.y = rect.top;
            this.mWindowParams.width = rect.right - rect.left;
            this.mWindowParams.height = rect.bottom - rect.top;
            this.mWindowManager.updateViewLayout(this.mMediaViewContainer, this.mWindowParams);
        }

        void removeMediaView(boolean z) {
            if (z) {
                this.mWindowToken = null;
                this.mMediaFrame = null;
            }
            if (this.mMediaViewContainer != null) {
                this.mMediaViewContainer.removeView(this.mMediaView);
                this.mMediaView = null;
                this.mWindowManager.removeView(this.mMediaViewContainer);
                this.mMediaViewContainer = null;
                this.mWindowParams = null;
            }
        }

        void scheduleMediaViewCleanup() {
            android.widget.FrameLayout frameLayout = this.mMediaViewContainer;
            if (frameLayout != null) {
                this.mMediaViewCleanUpTask = new android.media.tv.interactive.TvInteractiveAppService.MediaViewCleanUpTask();
                this.mMediaViewCleanUpTask.executeOnExecutor(android.os.AsyncTask.THREAD_POOL_EXECUTOR, frameLayout);
            }
        }
    }

    private static final class MediaViewCleanUpTask extends android.os.AsyncTask<android.view.View, java.lang.Void, java.lang.Void> {
        private MediaViewCleanUpTask() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public java.lang.Void doInBackground(android.view.View... viewArr) {
            android.view.View view = viewArr[0];
            try {
                java.lang.Thread.sleep(5000L);
                if (!isCancelled() && view.isAttachedToWindow()) {
                    android.util.Log.e(android.media.tv.interactive.TvInteractiveAppService.TAG, "Time out on releasing media view. Killing " + view.getContext().getPackageName());
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
                return null;
            } catch (java.lang.InterruptedException e) {
                return null;
            }
        }
    }

    private final class ServiceHandler extends android.os.Handler {
        private static final int DO_CREATE_SESSION = 1;
        private static final int DO_NOTIFY_RTE_STATE_CHANGED = 3;
        private static final int DO_NOTIFY_SESSION_CREATED = 2;

        private ServiceHandler() {
        }

        private void broadcastRteStateChanged(int i, int i2, int i3) {
            int beginBroadcast = android.media.tv.interactive.TvInteractiveAppService.this.mCallbacks.beginBroadcast();
            for (int i4 = 0; i4 < beginBroadcast; i4++) {
                try {
                    ((android.media.tv.interactive.ITvInteractiveAppServiceCallback) android.media.tv.interactive.TvInteractiveAppService.this.mCallbacks.getBroadcastItem(i4)).onStateChanged(i, i2, i3);
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(android.media.tv.interactive.TvInteractiveAppService.TAG, "error in broadcastRteStateChanged", e);
                }
            }
            android.media.tv.interactive.TvInteractiveAppService.this.mCallbacks.finishBroadcast();
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    com.android.internal.os.SomeArgs someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    android.view.InputChannel inputChannel = (android.view.InputChannel) someArgs.arg1;
                    android.media.tv.interactive.ITvInteractiveAppSessionCallback iTvInteractiveAppSessionCallback = (android.media.tv.interactive.ITvInteractiveAppSessionCallback) someArgs.arg2;
                    java.lang.String str = (java.lang.String) someArgs.arg3;
                    int intValue = ((java.lang.Integer) someArgs.arg4).intValue();
                    someArgs.recycle();
                    android.media.tv.interactive.TvInteractiveAppService.Session onCreateSession = android.media.tv.interactive.TvInteractiveAppService.this.onCreateSession(str, intValue);
                    if (onCreateSession == null) {
                        try {
                            iTvInteractiveAppSessionCallback.onSessionCreated(null);
                            break;
                        } catch (android.os.RemoteException e) {
                            android.util.Log.e(android.media.tv.interactive.TvInteractiveAppService.TAG, "error in onSessionCreated", e);
                            return;
                        }
                    } else {
                        android.media.tv.interactive.ITvInteractiveAppSessionWrapper iTvInteractiveAppSessionWrapper = new android.media.tv.interactive.ITvInteractiveAppSessionWrapper(android.media.tv.interactive.TvInteractiveAppService.this, onCreateSession, inputChannel);
                        com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                        obtain.arg1 = onCreateSession;
                        obtain.arg2 = iTvInteractiveAppSessionWrapper;
                        obtain.arg3 = iTvInteractiveAppSessionCallback;
                        android.media.tv.interactive.TvInteractiveAppService.this.mServiceHandler.obtainMessage(2, obtain).sendToTarget();
                        break;
                    }
                case 2:
                    com.android.internal.os.SomeArgs someArgs2 = (com.android.internal.os.SomeArgs) message.obj;
                    android.media.tv.interactive.TvInteractiveAppService.Session session = (android.media.tv.interactive.TvInteractiveAppService.Session) someArgs2.arg1;
                    android.media.tv.interactive.ITvInteractiveAppSession iTvInteractiveAppSession = (android.media.tv.interactive.ITvInteractiveAppSession) someArgs2.arg2;
                    android.media.tv.interactive.ITvInteractiveAppSessionCallback iTvInteractiveAppSessionCallback2 = (android.media.tv.interactive.ITvInteractiveAppSessionCallback) someArgs2.arg3;
                    try {
                        iTvInteractiveAppSessionCallback2.onSessionCreated(iTvInteractiveAppSession);
                    } catch (android.os.RemoteException e2) {
                        android.util.Log.e(android.media.tv.interactive.TvInteractiveAppService.TAG, "error in onSessionCreated", e2);
                    }
                    if (session != null) {
                        session.initialize(iTvInteractiveAppSessionCallback2);
                    }
                    someArgs2.recycle();
                    break;
                case 3:
                    com.android.internal.os.SomeArgs someArgs3 = (com.android.internal.os.SomeArgs) message.obj;
                    broadcastRteStateChanged(((java.lang.Integer) someArgs3.arg1).intValue(), ((java.lang.Integer) someArgs3.arg2).intValue(), ((java.lang.Integer) someArgs3.arg3).intValue());
                    break;
                default:
                    android.util.Log.w(android.media.tv.interactive.TvInteractiveAppService.TAG, "Unhandled message code: " + message.what);
                    break;
            }
        }
    }
}
