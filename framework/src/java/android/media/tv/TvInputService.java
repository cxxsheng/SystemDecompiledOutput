package android.media.tv;

/* loaded from: classes2.dex */
public abstract class TvInputService extends android.app.Service {
    private static final boolean DEBUG = false;
    private static final int DETACH_OVERLAY_VIEW_TIMEOUT_MS = 5000;
    public static final int PRIORITY_HINT_USE_CASE_TYPE_BACKGROUND = 100;
    public static final int PRIORITY_HINT_USE_CASE_TYPE_LIVE = 400;
    public static final int PRIORITY_HINT_USE_CASE_TYPE_PLAYBACK = 300;
    public static final int PRIORITY_HINT_USE_CASE_TYPE_RECORD = 500;
    public static final int PRIORITY_HINT_USE_CASE_TYPE_SCAN = 200;
    public static final java.lang.String SERVICE_INTERFACE = "android.media.tv.TvInputService";
    public static final java.lang.String SERVICE_META_DATA = "android.media.tv.input";
    private static final java.lang.String TAG = "TvInputService";
    private android.media.tv.TvInputManager mTvInputManager;
    private final android.os.Handler mServiceHandler = new android.media.tv.TvInputService.ServiceHandler();
    private final android.os.RemoteCallbackList<android.media.tv.ITvInputServiceCallback> mCallbacks = new android.os.RemoteCallbackList<>();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PriorityHintUseCaseType {
    }

    public abstract android.media.tv.TvInputService.Session onCreateSession(java.lang.String str);

    @Override // android.app.Service
    public final android.os.IBinder onBind(android.content.Intent intent) {
        android.media.tv.ITvInputService.Stub stub = new android.media.tv.ITvInputService.Stub() { // from class: android.media.tv.TvInputService.1
            @Override // android.media.tv.ITvInputService
            public void registerCallback(android.media.tv.ITvInputServiceCallback iTvInputServiceCallback) {
                if (iTvInputServiceCallback != null) {
                    android.media.tv.TvInputService.this.mCallbacks.register(iTvInputServiceCallback);
                }
            }

            @Override // android.media.tv.ITvInputService
            public void unregisterCallback(android.media.tv.ITvInputServiceCallback iTvInputServiceCallback) {
                if (iTvInputServiceCallback != null) {
                    android.media.tv.TvInputService.this.mCallbacks.unregister(iTvInputServiceCallback);
                }
            }

            @Override // android.media.tv.ITvInputService
            public void createSession(android.view.InputChannel inputChannel, android.media.tv.ITvInputSessionCallback iTvInputSessionCallback, java.lang.String str, java.lang.String str2, android.content.AttributionSource attributionSource) {
                if (inputChannel == null) {
                    android.util.Log.w(android.media.tv.TvInputService.TAG, "Creating session without input channel");
                }
                if (iTvInputSessionCallback == null) {
                    return;
                }
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = inputChannel;
                obtain.arg2 = iTvInputSessionCallback;
                obtain.arg3 = str;
                obtain.arg4 = str2;
                obtain.arg5 = attributionSource;
                android.media.tv.TvInputService.this.mServiceHandler.obtainMessage(1, obtain).sendToTarget();
            }

            @Override // android.media.tv.ITvInputService
            public void createRecordingSession(android.media.tv.ITvInputSessionCallback iTvInputSessionCallback, java.lang.String str, java.lang.String str2) {
                if (iTvInputSessionCallback == null) {
                    return;
                }
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = iTvInputSessionCallback;
                obtain.arg2 = str;
                obtain.arg3 = str2;
                android.media.tv.TvInputService.this.mServiceHandler.obtainMessage(3, obtain).sendToTarget();
            }

            @Override // android.media.tv.ITvInputService
            public java.util.List<java.lang.String> getAvailableExtensionInterfaceNames() {
                return android.media.tv.TvInputService.this.getAvailableExtensionInterfaceNames();
            }

            @Override // android.media.tv.ITvInputService
            public android.os.IBinder getExtensionInterface(java.lang.String str) {
                return android.media.tv.TvInputService.this.getExtensionInterface(str);
            }

            @Override // android.media.tv.ITvInputService
            public java.lang.String getExtensionInterfacePermission(java.lang.String str) {
                return android.media.tv.TvInputService.this.getExtensionInterfacePermission(str);
            }

            @Override // android.media.tv.ITvInputService
            public void notifyHardwareAdded(android.media.tv.TvInputHardwareInfo tvInputHardwareInfo) {
                android.media.tv.TvInputService.this.mServiceHandler.obtainMessage(4, tvInputHardwareInfo).sendToTarget();
            }

            @Override // android.media.tv.ITvInputService
            public void notifyHardwareRemoved(android.media.tv.TvInputHardwareInfo tvInputHardwareInfo) {
                android.media.tv.TvInputService.this.mServiceHandler.obtainMessage(5, tvInputHardwareInfo).sendToTarget();
            }

            @Override // android.media.tv.ITvInputService
            public void notifyHdmiDeviceAdded(android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo) {
                android.media.tv.TvInputService.this.mServiceHandler.obtainMessage(6, hdmiDeviceInfo).sendToTarget();
            }

            @Override // android.media.tv.ITvInputService
            public void notifyHdmiDeviceRemoved(android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo) {
                android.media.tv.TvInputService.this.mServiceHandler.obtainMessage(7, hdmiDeviceInfo).sendToTarget();
            }

            @Override // android.media.tv.ITvInputService
            public void notifyHdmiDeviceUpdated(android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo) {
                android.media.tv.TvInputService.this.mServiceHandler.obtainMessage(8, hdmiDeviceInfo).sendToTarget();
            }
        };
        android.os.IBinder createExtension = createExtension();
        if (createExtension != null) {
            stub.setExtension(createExtension);
        }
        return stub;
    }

    @android.annotation.SystemApi
    public android.os.IBinder createExtension() {
        return null;
    }

    @android.annotation.SystemApi
    public java.util.List<java.lang.String> getAvailableExtensionInterfaceNames() {
        return new java.util.ArrayList();
    }

    @android.annotation.SystemApi
    public android.os.IBinder getExtensionInterface(java.lang.String str) {
        return null;
    }

    @android.annotation.SystemApi
    public java.lang.String getExtensionInterfacePermission(java.lang.String str) {
        return null;
    }

    public android.media.tv.TvInputService.RecordingSession onCreateRecordingSession(java.lang.String str) {
        return null;
    }

    public android.media.tv.TvInputService.Session onCreateSession(java.lang.String str, java.lang.String str2) {
        return onCreateSession(str);
    }

    public android.media.tv.TvInputService.Session onCreateSession(java.lang.String str, java.lang.String str2, android.content.AttributionSource attributionSource) {
        return onCreateSession(str, str2);
    }

    public android.media.tv.TvInputService.RecordingSession onCreateRecordingSession(java.lang.String str, java.lang.String str2) {
        return onCreateRecordingSession(str);
    }

    @android.annotation.SystemApi
    public android.media.tv.TvInputInfo onHardwareAdded(android.media.tv.TvInputHardwareInfo tvInputHardwareInfo) {
        return null;
    }

    @android.annotation.SystemApi
    public java.lang.String onHardwareRemoved(android.media.tv.TvInputHardwareInfo tvInputHardwareInfo) {
        return null;
    }

    @android.annotation.SystemApi
    public android.media.tv.TvInputInfo onHdmiDeviceAdded(android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo) {
        return null;
    }

    @android.annotation.SystemApi
    public java.lang.String onHdmiDeviceRemoved(android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo) {
        return null;
    }

    @android.annotation.SystemApi
    public void onHdmiDeviceUpdated(android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isPassthroughInput(java.lang.String str) {
        if (this.mTvInputManager == null) {
            this.mTvInputManager = (android.media.tv.TvInputManager) getSystemService(android.content.Context.TV_INPUT_SERVICE);
        }
        android.media.tv.TvInputInfo tvInputInfo = this.mTvInputManager.getTvInputInfo(str);
        return tvInputInfo != null && tvInputInfo.isPassthroughInput();
    }

    public static abstract class Session implements android.view.KeyEvent.Callback {
        private static final int POSITION_UPDATE_INTERVAL_MS = 1000;
        private final android.content.Context mContext;
        final android.os.Handler mHandler;
        private android.graphics.Rect mOverlayFrame;
        private android.view.View mOverlayView;
        private android.media.tv.TvInputService.OverlayViewCleanUpTask mOverlayViewCleanUpTask;
        private android.widget.FrameLayout mOverlayViewContainer;
        private boolean mOverlayViewEnabled;
        private android.media.tv.ITvInputSessionCallback mSessionCallback;
        private android.view.Surface mSurface;
        private final android.view.WindowManager mWindowManager;
        private android.view.WindowManager.LayoutParams mWindowParams;
        private android.os.IBinder mWindowToken;
        private final android.view.KeyEvent.DispatcherState mDispatcherState = new android.view.KeyEvent.DispatcherState();
        private long mStartPositionMs = Long.MIN_VALUE;
        private long mCurrentPositionMs = Long.MIN_VALUE;
        private final android.media.tv.TvInputService.Session.TimeShiftPositionTrackingRunnable mTimeShiftPositionTrackingRunnable = new android.media.tv.TvInputService.Session.TimeShiftPositionTrackingRunnable();
        private final java.lang.Object mLock = new java.lang.Object();
        private final java.util.List<java.lang.Runnable> mPendingActions = new java.util.ArrayList();

        public abstract void onRelease();

        public abstract void onSetCaptionEnabled(boolean z);

        public abstract void onSetStreamVolume(float f);

        public abstract boolean onSetSurface(android.view.Surface surface);

        public abstract boolean onTune(android.net.Uri uri);

        public Session(android.content.Context context) {
            this.mContext = context;
            this.mWindowManager = (android.view.WindowManager) context.getSystemService(android.content.Context.WINDOW_SERVICE);
            this.mHandler = new android.os.Handler(context.getMainLooper());
        }

        public void setOverlayViewEnabled(final boolean z) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.TvInputService.Session.1
                @Override // java.lang.Runnable
                public void run() {
                    if (z == android.media.tv.TvInputService.Session.this.mOverlayViewEnabled) {
                        return;
                    }
                    android.media.tv.TvInputService.Session.this.mOverlayViewEnabled = z;
                    if (z) {
                        if (android.media.tv.TvInputService.Session.this.mWindowToken != null) {
                            android.media.tv.TvInputService.Session.this.createOverlayView(android.media.tv.TvInputService.Session.this.mWindowToken, android.media.tv.TvInputService.Session.this.mOverlayFrame);
                            return;
                        }
                        return;
                    }
                    android.media.tv.TvInputService.Session.this.removeOverlayView(false);
                }
            });
        }

        @android.annotation.SystemApi
        public void notifySessionEvent(final java.lang.String str, final android.os.Bundle bundle) {
            com.android.internal.util.Preconditions.checkNotNull(str);
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.TvInputService.Session.2
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (android.media.tv.TvInputService.Session.this.mSessionCallback != null) {
                            android.media.tv.TvInputService.Session.this.mSessionCallback.onSessionEvent(str, bundle);
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(android.media.tv.TvInputService.TAG, "error in sending event (event=" + str + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END, e);
                    }
                }
            });
        }

        public void notifyChannelRetuned(final android.net.Uri uri) {
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.TvInputService.Session.3
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (android.media.tv.TvInputService.Session.this.mSessionCallback != null) {
                            android.media.tv.TvInputService.Session.this.mSessionCallback.onChannelRetuned(uri);
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(android.media.tv.TvInputService.TAG, "error in notifyChannelRetuned", e);
                    }
                }
            });
        }

        public void notifyTuned(final android.net.Uri uri) {
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.TvInputService.Session.4
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (android.media.tv.TvInputService.Session.this.mSessionCallback != null) {
                            android.media.tv.TvInputService.Session.this.mSessionCallback.onTuned(uri);
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(android.media.tv.TvInputService.TAG, "error in notifyTuned", e);
                    }
                }
            });
        }

        public void notifyTracksChanged(java.util.List<android.media.tv.TvTrackInfo> list) {
            final java.util.ArrayList arrayList = new java.util.ArrayList(list);
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.TvInputService.Session.5
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (android.media.tv.TvInputService.Session.this.mSessionCallback != null) {
                            android.media.tv.TvInputService.Session.this.mSessionCallback.onTracksChanged(arrayList);
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(android.media.tv.TvInputService.TAG, "error in notifyTracksChanged", e);
                    }
                }
            });
        }

        public void notifyTrackSelected(final int i, final java.lang.String str) {
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.TvInputService.Session.6
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (android.media.tv.TvInputService.Session.this.mSessionCallback != null) {
                            android.media.tv.TvInputService.Session.this.mSessionCallback.onTrackSelected(i, str);
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(android.media.tv.TvInputService.TAG, "error in notifyTrackSelected", e);
                    }
                }
            });
        }

        public void notifyVideoAvailable() {
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.TvInputService.Session.7
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (android.media.tv.TvInputService.Session.this.mSessionCallback != null) {
                            android.media.tv.TvInputService.Session.this.mSessionCallback.onVideoAvailable();
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(android.media.tv.TvInputService.TAG, "error in notifyVideoAvailable", e);
                    }
                }
            });
        }

        public void notifyVideoUnavailable(final int i) {
            if (i < 0 || i > 18) {
                android.util.Log.e(android.media.tv.TvInputService.TAG, "notifyVideoUnavailable - unknown reason: " + i);
            }
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.TvInputService.Session.8
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (android.media.tv.TvInputService.Session.this.mSessionCallback != null) {
                            android.media.tv.TvInputService.Session.this.mSessionCallback.onVideoUnavailable(i);
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(android.media.tv.TvInputService.TAG, "error in notifyVideoUnavailable", e);
                    }
                }
            });
        }

        public void notifyVideoFreezeUpdated(final boolean z) {
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.TvInputService.Session.9
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (android.media.tv.TvInputService.Session.this.mSessionCallback != null) {
                            android.media.tv.TvInputService.Session.this.mSessionCallback.onVideoFreezeUpdated(z);
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.e(android.media.tv.TvInputService.TAG, "error in notifyVideoFreezeUpdated", e);
                    }
                }
            });
        }

        public void notifyAudioPresentationChanged(java.util.List<android.media.AudioPresentation> list) {
            final java.util.ArrayList arrayList = new java.util.ArrayList(list);
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.TvInputService.Session.10
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (android.media.tv.TvInputService.Session.this.mSessionCallback != null) {
                            android.media.tv.TvInputService.Session.this.mSessionCallback.onAudioPresentationsChanged(arrayList);
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.e(android.media.tv.TvInputService.TAG, "error in notifyAudioPresentationsChanged", e);
                    }
                }
            });
        }

        public void notifyAudioPresentationSelected(final int i, final int i2) {
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.TvInputService.Session.11
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (android.media.tv.TvInputService.Session.this.mSessionCallback != null) {
                            android.media.tv.TvInputService.Session.this.mSessionCallback.onAudioPresentationSelected(i, i2);
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.e(android.media.tv.TvInputService.TAG, "error in notifyAudioPresentationSelected", e);
                    }
                }
            });
        }

        public void notifyContentAllowed() {
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.TvInputService.Session.12
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (android.media.tv.TvInputService.Session.this.mSessionCallback != null) {
                            android.media.tv.TvInputService.Session.this.mSessionCallback.onContentAllowed();
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(android.media.tv.TvInputService.TAG, "error in notifyContentAllowed", e);
                    }
                }
            });
        }

        public void notifyContentBlocked(final android.media.tv.TvContentRating tvContentRating) {
            com.android.internal.util.Preconditions.checkNotNull(tvContentRating);
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.TvInputService.Session.13
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (android.media.tv.TvInputService.Session.this.mSessionCallback != null) {
                            android.media.tv.TvInputService.Session.this.mSessionCallback.onContentBlocked(tvContentRating.flattenToString());
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(android.media.tv.TvInputService.TAG, "error in notifyContentBlocked", e);
                    }
                }
            });
        }

        public void notifyTimeShiftStatusChanged(final int i) {
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.TvInputService.Session.14
                @Override // java.lang.Runnable
                public void run() {
                    android.media.tv.TvInputService.Session.this.timeShiftEnablePositionTracking(i == 3);
                    try {
                        if (android.media.tv.TvInputService.Session.this.mSessionCallback != null) {
                            android.media.tv.TvInputService.Session.this.mSessionCallback.onTimeShiftStatusChanged(i);
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(android.media.tv.TvInputService.TAG, "error in notifyTimeShiftStatusChanged", e);
                    }
                }
            });
        }

        public void notifyBroadcastInfoResponse(final android.media.tv.BroadcastInfoResponse broadcastInfoResponse) {
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.TvInputService.Session.15
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (android.media.tv.TvInputService.Session.this.mSessionCallback != null) {
                            android.media.tv.TvInputService.Session.this.mSessionCallback.onBroadcastInfoResponse(broadcastInfoResponse);
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(android.media.tv.TvInputService.TAG, "error in notifyBroadcastInfoResponse", e);
                    }
                }
            });
        }

        public void notifyAdResponse(final android.media.tv.AdResponse adResponse) {
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.TvInputService.Session.16
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (android.media.tv.TvInputService.Session.this.mSessionCallback != null) {
                            android.media.tv.TvInputService.Session.this.mSessionCallback.onAdResponse(adResponse);
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(android.media.tv.TvInputService.TAG, "error in notifyAdResponse", e);
                    }
                }
            });
        }

        public void notifyAdBufferConsumed(android.media.tv.AdBuffer adBuffer) {
            try {
                final android.media.tv.AdBuffer dupAdBuffer = android.media.tv.AdBuffer.dupAdBuffer(adBuffer);
                executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.TvInputService.Session.17
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            try {
                                if (android.media.tv.TvInputService.Session.this.mSessionCallback != null) {
                                    android.media.tv.TvInputService.Session.this.mSessionCallback.onAdBufferConsumed(dupAdBuffer);
                                }
                                if (dupAdBuffer == null) {
                                    return;
                                }
                            } catch (android.os.RemoteException e) {
                                android.util.Log.w(android.media.tv.TvInputService.TAG, "error in notifyAdBufferConsumed", e);
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
                android.util.Log.w(android.media.tv.TvInputService.TAG, "dup AdBuffer error in notifyAdBufferConsumed:", e);
            }
        }

        public void notifyTvMessage(final int i, final android.os.Bundle bundle) {
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.TvInputService.Session.18
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (android.media.tv.TvInputService.Session.this.mSessionCallback != null) {
                            android.media.tv.TvInputService.Session.this.mSessionCallback.onTvMessage(i, bundle);
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(android.media.tv.TvInputService.TAG, "error in notifyTvMessage", e);
                    }
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void notifyTimeShiftStartPositionChanged(final long j) {
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.TvInputService.Session.19
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (android.media.tv.TvInputService.Session.this.mSessionCallback != null) {
                            android.media.tv.TvInputService.Session.this.mSessionCallback.onTimeShiftStartPositionChanged(j);
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(android.media.tv.TvInputService.TAG, "error in notifyTimeShiftStartPositionChanged", e);
                    }
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void notifyTimeShiftCurrentPositionChanged(final long j) {
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.TvInputService.Session.20
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (android.media.tv.TvInputService.Session.this.mSessionCallback != null) {
                            android.media.tv.TvInputService.Session.this.mSessionCallback.onTimeShiftCurrentPositionChanged(j);
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(android.media.tv.TvInputService.TAG, "error in notifyTimeShiftCurrentPositionChanged", e);
                    }
                }
            });
        }

        public void notifyAitInfoUpdated(final android.media.tv.AitInfo aitInfo) {
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.TvInputService.Session.21
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (android.media.tv.TvInputService.Session.this.mSessionCallback != null) {
                            android.media.tv.TvInputService.Session.this.mSessionCallback.onAitInfoUpdated(aitInfo);
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(android.media.tv.TvInputService.TAG, "error in notifyAitInfoUpdated", e);
                    }
                }
            });
        }

        public void notifyTimeShiftMode(final int i) {
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.TvInputService.Session.22
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (android.media.tv.TvInputService.Session.this.mSessionCallback != null) {
                            android.media.tv.TvInputService.Session.this.mSessionCallback.onTimeShiftMode(i);
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(android.media.tv.TvInputService.TAG, "error in notifyTimeShiftMode", e);
                    }
                }
            });
        }

        public void notifyAvailableSpeeds(final float[] fArr) {
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.TvInputService.Session.23
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (android.media.tv.TvInputService.Session.this.mSessionCallback != null) {
                            java.util.Arrays.sort(fArr);
                            android.media.tv.TvInputService.Session.this.mSessionCallback.onAvailableSpeeds(fArr);
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(android.media.tv.TvInputService.TAG, "error in notifyAvailableSpeeds", e);
                    }
                }
            });
        }

        public void notifySignalStrength(final int i) {
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.TvInputService.Session.24
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (android.media.tv.TvInputService.Session.this.mSessionCallback != null) {
                            android.media.tv.TvInputService.Session.this.mSessionCallback.onSignalStrength(i);
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(android.media.tv.TvInputService.TAG, "error in notifySignalStrength", e);
                    }
                }
            });
        }

        public void notifyCueingMessageAvailability(final boolean z) {
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.TvInputService.Session.25
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (android.media.tv.TvInputService.Session.this.mSessionCallback != null) {
                            android.media.tv.TvInputService.Session.this.mSessionCallback.onCueingMessageAvailability(z);
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(android.media.tv.TvInputService.TAG, "error in notifyCueingMessageAvailability", e);
                    }
                }
            });
        }

        public void sendTvInputSessionData(final java.lang.String str, final android.os.Bundle bundle) {
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.TvInputService.Session.26
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (android.media.tv.TvInputService.Session.this.mSessionCallback != null) {
                            android.media.tv.TvInputService.Session.this.mSessionCallback.onTvInputSessionData(str, bundle);
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(android.media.tv.TvInputService.TAG, "error in sendTvInputSessionData", e);
                    }
                }
            });
        }

        public void layoutSurface(final int i, final int i2, final int i3, final int i4) {
            if (i > i3 || i2 > i4) {
                throw new java.lang.IllegalArgumentException("Invalid parameter");
            }
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.TvInputService.Session.27
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (android.media.tv.TvInputService.Session.this.mSessionCallback != null) {
                            android.media.tv.TvInputService.Session.this.mSessionCallback.onLayoutSurface(i, i2, i3, i4);
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(android.media.tv.TvInputService.TAG, "error in layoutSurface", e);
                    }
                }
            });
        }

        @android.annotation.SystemApi
        public void onSetMain(boolean z) {
        }

        public void onSurfaceChanged(int i, int i2, int i3) {
        }

        public void onOverlayViewSizeChanged(int i, int i2) {
        }

        public void onRequestBroadcastInfo(android.media.tv.BroadcastInfoRequest broadcastInfoRequest) {
        }

        public void onRemoveBroadcastInfo(int i) {
        }

        public void onRequestAd(android.media.tv.AdRequest adRequest) {
        }

        public void onAdBufferReady(android.media.tv.AdBuffer adBuffer) {
        }

        public void onTvAdSessionData(java.lang.String str, android.os.Bundle bundle) {
        }

        public boolean onTune(android.net.Uri uri, android.os.Bundle bundle) {
            return onTune(uri);
        }

        public void onUnblockContent(android.media.tv.TvContentRating tvContentRating) {
        }

        public boolean onSelectTrack(int i, java.lang.String str) {
            return false;
        }

        public void onSetInteractiveAppNotificationEnabled(boolean z) {
        }

        public boolean onSelectAudioPresentation(int i, int i2) {
            return false;
        }

        public void onAppPrivateCommand(java.lang.String str, android.os.Bundle bundle) {
        }

        public android.view.View onCreateOverlayView() {
            return null;
        }

        public void onSetTvMessageEnabled(int i, boolean z) {
        }

        public void onTvMessage(int i, android.os.Bundle bundle) {
        }

        public void onStopPlayback(int i) {
        }

        public void onResumePlayback() {
        }

        public void onSetVideoFrozen(boolean z) {
        }

        public void onTimeShiftPlay(android.net.Uri uri) {
        }

        public void onTimeShiftPause() {
        }

        public void onTimeShiftResume() {
        }

        public void onTimeShiftSeekTo(long j) {
        }

        public void onTimeShiftSetPlaybackParams(android.media.PlaybackParams playbackParams) {
        }

        public void onTimeShiftSetMode(int i) {
        }

        public long onTimeShiftGetStartPosition() {
            return Long.MIN_VALUE;
        }

        public long onTimeShiftGetCurrentPosition() {
            return Long.MIN_VALUE;
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
            removeOverlayView(true);
            this.mHandler.removeCallbacks(this.mTimeShiftPositionTrackingRunnable);
        }

        void setMain(boolean z) {
            onSetMain(z);
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

        void setStreamVolume(float f) {
            onSetStreamVolume(f);
        }

        void tune(android.net.Uri uri, android.os.Bundle bundle) {
            this.mCurrentPositionMs = Long.MIN_VALUE;
            onTune(uri, bundle);
        }

        void setCaptionEnabled(boolean z) {
            onSetCaptionEnabled(z);
        }

        void selectAudioPresentation(int i, int i2) {
            onSelectAudioPresentation(i, i2);
        }

        void selectTrack(int i, java.lang.String str) {
            onSelectTrack(i, str);
        }

        void unblockContent(java.lang.String str) {
            onUnblockContent(android.media.tv.TvContentRating.unflattenFromString(str));
        }

        void setInteractiveAppNotificationEnabled(boolean z) {
            onSetInteractiveAppNotificationEnabled(z);
        }

        void setTvMessageEnabled(int i, boolean z) {
            onSetTvMessageEnabled(i, z);
        }

        void appPrivateCommand(java.lang.String str, android.os.Bundle bundle) {
            onAppPrivateCommand(str, bundle);
        }

        void createOverlayView(android.os.IBinder iBinder, android.graphics.Rect rect) {
            int i;
            if (this.mOverlayViewContainer != null) {
                removeOverlayView(false);
            }
            this.mWindowToken = iBinder;
            this.mOverlayFrame = rect;
            onOverlayViewSizeChanged(rect.right - rect.left, rect.bottom - rect.top);
            if (!this.mOverlayViewEnabled) {
                return;
            }
            this.mOverlayView = onCreateOverlayView();
            if (this.mOverlayView == null) {
                return;
            }
            if (this.mOverlayViewCleanUpTask != null) {
                this.mOverlayViewCleanUpTask.cancel(true);
                this.mOverlayViewCleanUpTask = null;
            }
            this.mOverlayViewContainer = new android.widget.FrameLayout(this.mContext.getApplicationContext());
            this.mOverlayViewContainer.addView(this.mOverlayView);
            if (!android.app.ActivityManager.isHighEndGfx()) {
                i = 536;
            } else {
                i = 16777752;
            }
            this.mWindowParams = new android.view.WindowManager.LayoutParams(rect.right - rect.left, rect.bottom - rect.top, rect.left, rect.top, 1004, i, -2);
            this.mWindowParams.privateFlags |= 64;
            this.mWindowParams.gravity = 8388659;
            this.mWindowParams.token = iBinder;
            this.mWindowManager.addView(this.mOverlayViewContainer, this.mWindowParams);
        }

        void relayoutOverlayView(android.graphics.Rect rect) {
            if (this.mOverlayFrame == null || this.mOverlayFrame.width() != rect.width() || this.mOverlayFrame.height() != rect.height()) {
                onOverlayViewSizeChanged(rect.right - rect.left, rect.bottom - rect.top);
            }
            this.mOverlayFrame = rect;
            if (!this.mOverlayViewEnabled || this.mOverlayViewContainer == null) {
                return;
            }
            this.mWindowParams.x = rect.left;
            this.mWindowParams.y = rect.top;
            this.mWindowParams.width = rect.right - rect.left;
            this.mWindowParams.height = rect.bottom - rect.top;
            this.mWindowManager.updateViewLayout(this.mOverlayViewContainer, this.mWindowParams);
        }

        void removeOverlayView(boolean z) {
            if (z) {
                this.mWindowToken = null;
                this.mOverlayFrame = null;
            }
            if (this.mOverlayViewContainer != null) {
                this.mOverlayViewContainer.removeView(this.mOverlayView);
                this.mOverlayView = null;
                this.mWindowManager.removeView(this.mOverlayViewContainer);
                this.mOverlayViewContainer = null;
                this.mWindowParams = null;
            }
        }

        void stopPlayback(int i) {
            onStopPlayback(i);
        }

        void resumePlayback() {
            onResumePlayback();
        }

        void setVideoFrozen(boolean z) {
            onSetVideoFrozen(z);
        }

        void timeShiftPlay(android.net.Uri uri) {
            this.mCurrentPositionMs = 0L;
            onTimeShiftPlay(uri);
        }

        void timeShiftPause() {
            onTimeShiftPause();
        }

        void timeShiftResume() {
            onTimeShiftResume();
        }

        void timeShiftSeekTo(long j) {
            onTimeShiftSeekTo(j);
        }

        void timeShiftSetPlaybackParams(android.media.PlaybackParams playbackParams) {
            onTimeShiftSetPlaybackParams(playbackParams);
        }

        void timeShiftSetMode(int i) {
            onTimeShiftSetMode(i);
        }

        void timeShiftEnablePositionTracking(boolean z) {
            if (z) {
                this.mHandler.post(this.mTimeShiftPositionTrackingRunnable);
                return;
            }
            this.mHandler.removeCallbacks(this.mTimeShiftPositionTrackingRunnable);
            this.mStartPositionMs = Long.MIN_VALUE;
            this.mCurrentPositionMs = Long.MIN_VALUE;
        }

        void scheduleOverlayViewCleanup() {
            android.widget.FrameLayout frameLayout = this.mOverlayViewContainer;
            if (frameLayout != null) {
                this.mOverlayViewCleanUpTask = new android.media.tv.TvInputService.OverlayViewCleanUpTask();
                this.mOverlayViewCleanUpTask.executeOnExecutor(android.os.AsyncTask.THREAD_POOL_EXECUTOR, frameLayout);
            }
        }

        void requestBroadcastInfo(android.media.tv.BroadcastInfoRequest broadcastInfoRequest) {
            onRequestBroadcastInfo(broadcastInfoRequest);
        }

        void removeBroadcastInfo(int i) {
            onRemoveBroadcastInfo(i);
        }

        void requestAd(android.media.tv.AdRequest adRequest) {
            onRequestAd(adRequest);
        }

        void notifyAdBufferReady(android.media.tv.AdBuffer adBuffer) {
            onAdBufferReady(adBuffer);
        }

        void notifyTvAdSessionData(java.lang.String str, android.os.Bundle bundle) {
            onTvAdSessionData(str, bundle);
        }

        void onTvMessageReceived(int i, android.os.Bundle bundle) {
            onTvMessage(i, bundle);
        }

        int dispatchInputEvent(android.view.InputEvent inputEvent, android.view.InputEventReceiver inputEventReceiver) {
            boolean z;
            boolean z2;
            if (inputEvent instanceof android.view.KeyEvent) {
                android.view.KeyEvent keyEvent = (android.view.KeyEvent) inputEvent;
                if (keyEvent.dispatch(this, this.mDispatcherState, this)) {
                    return 1;
                }
                z2 = android.media.tv.TvInputService.isNavigationKey(keyEvent.getKeyCode());
                z = android.view.KeyEvent.isMediaSessionKey(keyEvent.getKeyCode()) || keyEvent.getKeyCode() == 222;
            } else {
                if (inputEvent instanceof android.view.MotionEvent) {
                    android.view.MotionEvent motionEvent = (android.view.MotionEvent) inputEvent;
                    int source = motionEvent.getSource();
                    if (motionEvent.isTouchEvent()) {
                        if (onTouchEvent(motionEvent)) {
                            return 1;
                        }
                    } else if ((source & 4) != 0) {
                        if (onTrackballEvent(motionEvent)) {
                            return 1;
                        }
                    } else if (onGenericMotionEvent(motionEvent)) {
                        return 1;
                    }
                }
                z = false;
                z2 = false;
            }
            if (this.mOverlayViewContainer == null || !this.mOverlayViewContainer.isAttachedToWindow() || z) {
                return 0;
            }
            if (!this.mOverlayViewContainer.hasWindowFocus()) {
                this.mOverlayViewContainer.getViewRootImpl().windowFocusChanged(true);
            }
            if (z2 && this.mOverlayViewContainer.hasFocusable()) {
                this.mOverlayViewContainer.getViewRootImpl().dispatchInputEvent(inputEvent);
                return 1;
            }
            this.mOverlayViewContainer.getViewRootImpl().dispatchInputEvent(inputEvent, inputEventReceiver);
            return -1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void initialize(android.media.tv.ITvInputSessionCallback iTvInputSessionCallback) {
            synchronized (this.mLock) {
                this.mSessionCallback = iTvInputSessionCallback;
                java.util.Iterator<java.lang.Runnable> it = this.mPendingActions.iterator();
                while (it.hasNext()) {
                    it.next().run();
                }
                this.mPendingActions.clear();
            }
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

        private final class TimeShiftPositionTrackingRunnable implements java.lang.Runnable {
            private TimeShiftPositionTrackingRunnable() {
            }

            @Override // java.lang.Runnable
            public void run() {
                long onTimeShiftGetStartPosition = android.media.tv.TvInputService.Session.this.onTimeShiftGetStartPosition();
                if (android.media.tv.TvInputService.Session.this.mStartPositionMs == Long.MIN_VALUE || android.media.tv.TvInputService.Session.this.mStartPositionMs != onTimeShiftGetStartPosition) {
                    android.media.tv.TvInputService.Session.this.mStartPositionMs = onTimeShiftGetStartPosition;
                    android.media.tv.TvInputService.Session.this.notifyTimeShiftStartPositionChanged(onTimeShiftGetStartPosition);
                }
                long onTimeShiftGetCurrentPosition = android.media.tv.TvInputService.Session.this.onTimeShiftGetCurrentPosition();
                if (onTimeShiftGetCurrentPosition < android.media.tv.TvInputService.Session.this.mStartPositionMs) {
                    android.util.Log.w(android.media.tv.TvInputService.TAG, "Current position (" + onTimeShiftGetCurrentPosition + ") cannot be earlier than start position (" + android.media.tv.TvInputService.Session.this.mStartPositionMs + "). Reset to the start position.");
                    onTimeShiftGetCurrentPosition = android.media.tv.TvInputService.Session.this.mStartPositionMs;
                }
                if (android.media.tv.TvInputService.Session.this.mCurrentPositionMs == Long.MIN_VALUE || android.media.tv.TvInputService.Session.this.mCurrentPositionMs != onTimeShiftGetCurrentPosition) {
                    android.media.tv.TvInputService.Session.this.mCurrentPositionMs = onTimeShiftGetCurrentPosition;
                    android.media.tv.TvInputService.Session.this.notifyTimeShiftCurrentPositionChanged(onTimeShiftGetCurrentPosition);
                }
                android.media.tv.TvInputService.Session.this.mHandler.removeCallbacks(android.media.tv.TvInputService.Session.this.mTimeShiftPositionTrackingRunnable);
                android.media.tv.TvInputService.Session.this.mHandler.postDelayed(android.media.tv.TvInputService.Session.this.mTimeShiftPositionTrackingRunnable, 1000L);
            }
        }
    }

    private static final class OverlayViewCleanUpTask extends android.os.AsyncTask<android.view.View, java.lang.Void, java.lang.Void> {
        private OverlayViewCleanUpTask() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public java.lang.Void doInBackground(android.view.View... viewArr) {
            android.view.View view = viewArr[0];
            try {
                java.lang.Thread.sleep(5000L);
                if (!isCancelled() && view.isAttachedToWindow()) {
                    android.util.Log.e(android.media.tv.TvInputService.TAG, "Time out on releasing overlay view. Killing " + view.getContext().getPackageName());
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
                return null;
            } catch (java.lang.InterruptedException e) {
                return null;
            }
        }
    }

    public static abstract class RecordingSession {
        final android.os.Handler mHandler;
        private final java.lang.Object mLock = new java.lang.Object();
        private final java.util.List<java.lang.Runnable> mPendingActions = new java.util.ArrayList();
        private android.media.tv.ITvInputSessionCallback mSessionCallback;

        public abstract void onRelease();

        public abstract void onStartRecording(android.net.Uri uri);

        public abstract void onStopRecording();

        public abstract void onTune(android.net.Uri uri);

        public RecordingSession(android.content.Context context) {
            this.mHandler = new android.os.Handler(context.getMainLooper());
        }

        public void notifyTuned(final android.net.Uri uri) {
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.TvInputService.RecordingSession.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (android.media.tv.TvInputService.RecordingSession.this.mSessionCallback != null) {
                            android.media.tv.TvInputService.RecordingSession.this.mSessionCallback.onTuned(uri);
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(android.media.tv.TvInputService.TAG, "error in notifyTuned", e);
                    }
                }
            });
        }

        public void notifyRecordingStopped(final android.net.Uri uri) {
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.TvInputService.RecordingSession.2
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (android.media.tv.TvInputService.RecordingSession.this.mSessionCallback != null) {
                            android.media.tv.TvInputService.RecordingSession.this.mSessionCallback.onRecordingStopped(uri);
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(android.media.tv.TvInputService.TAG, "error in notifyRecordingStopped", e);
                    }
                }
            });
        }

        public void notifyError(final int i) {
            if (i < 0 || i > 2) {
                android.util.Log.w(android.media.tv.TvInputService.TAG, "notifyError - invalid error code (" + i + ") is changed to RECORDING_ERROR_UNKNOWN.");
                i = 0;
            }
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.TvInputService.RecordingSession.3
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (android.media.tv.TvInputService.RecordingSession.this.mSessionCallback != null) {
                            android.media.tv.TvInputService.RecordingSession.this.mSessionCallback.onError(i);
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(android.media.tv.TvInputService.TAG, "error in notifyError", e);
                    }
                }
            });
        }

        @android.annotation.SystemApi
        public void notifySessionEvent(final java.lang.String str, final android.os.Bundle bundle) {
            com.android.internal.util.Preconditions.checkNotNull(str);
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.TvInputService.RecordingSession.4
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (android.media.tv.TvInputService.RecordingSession.this.mSessionCallback != null) {
                            android.media.tv.TvInputService.RecordingSession.this.mSessionCallback.onSessionEvent(str, bundle);
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(android.media.tv.TvInputService.TAG, "error in sending event (event=" + str + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END, e);
                    }
                }
            });
        }

        public void onTune(android.net.Uri uri, android.os.Bundle bundle) {
            onTune(uri);
        }

        public void onStartRecording(android.net.Uri uri, android.os.Bundle bundle) {
            onStartRecording(uri);
        }

        public void onPauseRecording(android.os.Bundle bundle) {
        }

        public void onResumeRecording(android.os.Bundle bundle) {
        }

        public void onAppPrivateCommand(java.lang.String str, android.os.Bundle bundle) {
        }

        void tune(android.net.Uri uri, android.os.Bundle bundle) {
            onTune(uri, bundle);
        }

        void release() {
            onRelease();
        }

        void startRecording(android.net.Uri uri, android.os.Bundle bundle) {
            onStartRecording(uri, bundle);
        }

        void stopRecording() {
            onStopRecording();
        }

        void pauseRecording(android.os.Bundle bundle) {
            onPauseRecording(bundle);
        }

        void resumeRecording(android.os.Bundle bundle) {
            onResumeRecording(bundle);
        }

        void appPrivateCommand(java.lang.String str, android.os.Bundle bundle) {
            onAppPrivateCommand(str, bundle);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void initialize(android.media.tv.ITvInputSessionCallback iTvInputSessionCallback) {
            synchronized (this.mLock) {
                this.mSessionCallback = iTvInputSessionCallback;
                java.util.Iterator<java.lang.Runnable> it = this.mPendingActions.iterator();
                while (it.hasNext()) {
                    it.next().run();
                }
                this.mPendingActions.clear();
            }
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
    }

    public static abstract class HardwareSession extends android.media.tv.TvInputService.Session {
        private android.media.tv.TvInputManager.Session mHardwareSession;
        private final android.media.tv.TvInputManager.SessionCallback mHardwareSessionCallback;
        private android.media.tv.ITvInputSession mProxySession;
        private android.media.tv.ITvInputSessionCallback mProxySessionCallback;
        private android.os.Handler mServiceHandler;

        public abstract java.lang.String getHardwareInputId();

        public HardwareSession(android.content.Context context) {
            super(context);
            this.mHardwareSessionCallback = new android.media.tv.TvInputManager.SessionCallback() { // from class: android.media.tv.TvInputService.HardwareSession.1
                @Override // android.media.tv.TvInputManager.SessionCallback
                public void onSessionCreated(android.media.tv.TvInputManager.Session session) {
                    android.media.tv.TvInputService.HardwareSession.this.mHardwareSession = session;
                    com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                    if (session != null) {
                        obtain.arg1 = android.media.tv.TvInputService.HardwareSession.this;
                        obtain.arg2 = android.media.tv.TvInputService.HardwareSession.this.mProxySession;
                        obtain.arg3 = android.media.tv.TvInputService.HardwareSession.this.mProxySessionCallback;
                        obtain.arg4 = session.getToken();
                        session.tune(android.media.tv.TvContract.buildChannelUriForPassthroughInput(android.media.tv.TvInputService.HardwareSession.this.getHardwareInputId()));
                    } else {
                        obtain.arg1 = null;
                        obtain.arg2 = null;
                        obtain.arg3 = android.media.tv.TvInputService.HardwareSession.this.mProxySessionCallback;
                        obtain.arg4 = null;
                        android.media.tv.TvInputService.HardwareSession.this.onRelease();
                    }
                    android.media.tv.TvInputService.HardwareSession.this.mServiceHandler.obtainMessage(2, obtain).sendToTarget();
                }

                @Override // android.media.tv.TvInputManager.SessionCallback
                public void onVideoAvailable(android.media.tv.TvInputManager.Session session) {
                    if (android.media.tv.TvInputService.HardwareSession.this.mHardwareSession == session) {
                        android.media.tv.TvInputService.HardwareSession.this.onHardwareVideoAvailable();
                    }
                }

                @Override // android.media.tv.TvInputManager.SessionCallback
                public void onVideoUnavailable(android.media.tv.TvInputManager.Session session, int i) {
                    if (android.media.tv.TvInputService.HardwareSession.this.mHardwareSession == session) {
                        android.media.tv.TvInputService.HardwareSession.this.onHardwareVideoUnavailable(i);
                    }
                }
            };
        }

        @Override // android.media.tv.TvInputService.Session
        public final boolean onSetSurface(android.view.Surface surface) {
            android.util.Log.e(android.media.tv.TvInputService.TAG, "onSetSurface() should not be called in HardwareProxySession.");
            return false;
        }

        public void onHardwareVideoAvailable() {
        }

        public void onHardwareVideoUnavailable(int i) {
        }

        @Override // android.media.tv.TvInputService.Session
        void release() {
            if (this.mHardwareSession != null) {
                this.mHardwareSession.release();
                this.mHardwareSession = null;
            }
            super.release();
        }
    }

    public static boolean isNavigationKey(int i) {
        switch (i) {
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 61:
            case 62:
            case 66:
            case 92:
            case 93:
            case 122:
            case 123:
                return true;
            default:
                return false;
        }
    }

    private final class ServiceHandler extends android.os.Handler {
        private static final int DO_ADD_HARDWARE_INPUT = 4;
        private static final int DO_ADD_HDMI_INPUT = 6;
        private static final int DO_CREATE_RECORDING_SESSION = 3;
        private static final int DO_CREATE_SESSION = 1;
        private static final int DO_NOTIFY_SESSION_CREATED = 2;
        private static final int DO_REMOVE_HARDWARE_INPUT = 5;
        private static final int DO_REMOVE_HDMI_INPUT = 7;
        private static final int DO_UPDATE_HDMI_INPUT = 8;

        private ServiceHandler() {
        }

        private void broadcastAddHardwareInput(int i, android.media.tv.TvInputInfo tvInputInfo) {
            int beginBroadcast = android.media.tv.TvInputService.this.mCallbacks.beginBroadcast();
            for (int i2 = 0; i2 < beginBroadcast; i2++) {
                try {
                    ((android.media.tv.ITvInputServiceCallback) android.media.tv.TvInputService.this.mCallbacks.getBroadcastItem(i2)).addHardwareInput(i, tvInputInfo);
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(android.media.tv.TvInputService.TAG, "error in broadcastAddHardwareInput", e);
                }
            }
            android.media.tv.TvInputService.this.mCallbacks.finishBroadcast();
        }

        private void broadcastAddHdmiInput(int i, android.media.tv.TvInputInfo tvInputInfo) {
            int beginBroadcast = android.media.tv.TvInputService.this.mCallbacks.beginBroadcast();
            for (int i2 = 0; i2 < beginBroadcast; i2++) {
                try {
                    ((android.media.tv.ITvInputServiceCallback) android.media.tv.TvInputService.this.mCallbacks.getBroadcastItem(i2)).addHdmiInput(i, tvInputInfo);
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(android.media.tv.TvInputService.TAG, "error in broadcastAddHdmiInput", e);
                }
            }
            android.media.tv.TvInputService.this.mCallbacks.finishBroadcast();
        }

        private void broadcastRemoveHardwareInput(java.lang.String str) {
            int beginBroadcast = android.media.tv.TvInputService.this.mCallbacks.beginBroadcast();
            for (int i = 0; i < beginBroadcast; i++) {
                try {
                    ((android.media.tv.ITvInputServiceCallback) android.media.tv.TvInputService.this.mCallbacks.getBroadcastItem(i)).removeHardwareInput(str);
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(android.media.tv.TvInputService.TAG, "error in broadcastRemoveHardwareInput", e);
                }
            }
            android.media.tv.TvInputService.this.mCallbacks.finishBroadcast();
        }

        @Override // android.os.Handler
        public final void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    com.android.internal.os.SomeArgs someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    android.view.InputChannel inputChannel = (android.view.InputChannel) someArgs.arg1;
                    android.media.tv.ITvInputSessionCallback iTvInputSessionCallback = (android.media.tv.ITvInputSessionCallback) someArgs.arg2;
                    java.lang.String str = (java.lang.String) someArgs.arg3;
                    java.lang.String str2 = (java.lang.String) someArgs.arg4;
                    android.content.AttributionSource attributionSource = (android.content.AttributionSource) someArgs.arg5;
                    someArgs.recycle();
                    android.media.tv.TvInputService.Session onCreateSession = android.media.tv.TvInputService.this.onCreateSession(str, str2, attributionSource);
                    if (onCreateSession == null) {
                        try {
                            iTvInputSessionCallback.onSessionCreated(null, null);
                            break;
                        } catch (android.os.RemoteException e) {
                            android.util.Log.e(android.media.tv.TvInputService.TAG, "error in onSessionCreated", e);
                            return;
                        }
                    } else {
                        android.media.tv.ITvInputSessionWrapper iTvInputSessionWrapper = new android.media.tv.ITvInputSessionWrapper(android.media.tv.TvInputService.this, onCreateSession, inputChannel);
                        if (onCreateSession instanceof android.media.tv.TvInputService.HardwareSession) {
                            android.media.tv.TvInputService.HardwareSession hardwareSession = (android.media.tv.TvInputService.HardwareSession) onCreateSession;
                            java.lang.String hardwareInputId = hardwareSession.getHardwareInputId();
                            if (android.text.TextUtils.isEmpty(hardwareInputId) || !android.media.tv.TvInputService.this.isPassthroughInput(hardwareInputId)) {
                                if (android.text.TextUtils.isEmpty(hardwareInputId)) {
                                    android.util.Log.w(android.media.tv.TvInputService.TAG, "Hardware input id is not setup yet.");
                                } else {
                                    android.util.Log.w(android.media.tv.TvInputService.TAG, "Invalid hardware input id : " + hardwareInputId);
                                }
                                onCreateSession.onRelease();
                                try {
                                    iTvInputSessionCallback.onSessionCreated(null, null);
                                    break;
                                } catch (android.os.RemoteException e2) {
                                    android.util.Log.e(android.media.tv.TvInputService.TAG, "error in onSessionCreated", e2);
                                    return;
                                }
                            } else {
                                hardwareSession.mProxySession = iTvInputSessionWrapper;
                                hardwareSession.mProxySessionCallback = iTvInputSessionCallback;
                                hardwareSession.mServiceHandler = android.media.tv.TvInputService.this.mServiceHandler;
                                ((android.media.tv.TvInputManager) android.media.tv.TvInputService.this.getSystemService(android.content.Context.TV_INPUT_SERVICE)).createSession(hardwareInputId, attributionSource, hardwareSession.mHardwareSessionCallback, android.media.tv.TvInputService.this.mServiceHandler);
                                break;
                            }
                        } else {
                            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                            obtain.arg1 = onCreateSession;
                            obtain.arg2 = iTvInputSessionWrapper;
                            obtain.arg3 = iTvInputSessionCallback;
                            obtain.arg4 = null;
                            android.media.tv.TvInputService.this.mServiceHandler.obtainMessage(2, obtain).sendToTarget();
                            break;
                        }
                    }
                    break;
                case 2:
                    com.android.internal.os.SomeArgs someArgs2 = (com.android.internal.os.SomeArgs) message.obj;
                    android.media.tv.TvInputService.Session session = (android.media.tv.TvInputService.Session) someArgs2.arg1;
                    android.media.tv.ITvInputSession iTvInputSession = (android.media.tv.ITvInputSession) someArgs2.arg2;
                    android.media.tv.ITvInputSessionCallback iTvInputSessionCallback2 = (android.media.tv.ITvInputSessionCallback) someArgs2.arg3;
                    try {
                        iTvInputSessionCallback2.onSessionCreated(iTvInputSession, (android.os.IBinder) someArgs2.arg4);
                    } catch (android.os.RemoteException e3) {
                        android.util.Log.e(android.media.tv.TvInputService.TAG, "error in onSessionCreated", e3);
                    }
                    if (session != null) {
                        session.initialize(iTvInputSessionCallback2);
                    }
                    someArgs2.recycle();
                    break;
                case 3:
                    com.android.internal.os.SomeArgs someArgs3 = (com.android.internal.os.SomeArgs) message.obj;
                    android.media.tv.ITvInputSessionCallback iTvInputSessionCallback3 = (android.media.tv.ITvInputSessionCallback) someArgs3.arg1;
                    java.lang.String str3 = (java.lang.String) someArgs3.arg2;
                    java.lang.String str4 = (java.lang.String) someArgs3.arg3;
                    someArgs3.recycle();
                    android.media.tv.TvInputService.RecordingSession onCreateRecordingSession = android.media.tv.TvInputService.this.onCreateRecordingSession(str3, str4);
                    if (onCreateRecordingSession == null) {
                        try {
                            iTvInputSessionCallback3.onSessionCreated(null, null);
                            break;
                        } catch (android.os.RemoteException e4) {
                            android.util.Log.e(android.media.tv.TvInputService.TAG, "error in onSessionCreated", e4);
                            return;
                        }
                    } else {
                        try {
                            iTvInputSessionCallback3.onSessionCreated(new android.media.tv.ITvInputSessionWrapper(android.media.tv.TvInputService.this, onCreateRecordingSession), null);
                        } catch (android.os.RemoteException e5) {
                            android.util.Log.e(android.media.tv.TvInputService.TAG, "error in onSessionCreated", e5);
                        }
                        onCreateRecordingSession.initialize(iTvInputSessionCallback3);
                        break;
                    }
                case 4:
                    android.media.tv.TvInputHardwareInfo tvInputHardwareInfo = (android.media.tv.TvInputHardwareInfo) message.obj;
                    android.media.tv.TvInputInfo onHardwareAdded = android.media.tv.TvInputService.this.onHardwareAdded(tvInputHardwareInfo);
                    if (onHardwareAdded != null) {
                        broadcastAddHardwareInput(tvInputHardwareInfo.getDeviceId(), onHardwareAdded);
                        break;
                    }
                    break;
                case 5:
                    java.lang.String onHardwareRemoved = android.media.tv.TvInputService.this.onHardwareRemoved((android.media.tv.TvInputHardwareInfo) message.obj);
                    if (onHardwareRemoved != null) {
                        broadcastRemoveHardwareInput(onHardwareRemoved);
                        break;
                    }
                    break;
                case 6:
                    android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo = (android.hardware.hdmi.HdmiDeviceInfo) message.obj;
                    android.media.tv.TvInputInfo onHdmiDeviceAdded = android.media.tv.TvInputService.this.onHdmiDeviceAdded(hdmiDeviceInfo);
                    if (onHdmiDeviceAdded != null) {
                        broadcastAddHdmiInput(hdmiDeviceInfo.getId(), onHdmiDeviceAdded);
                        break;
                    }
                    break;
                case 7:
                    java.lang.String onHdmiDeviceRemoved = android.media.tv.TvInputService.this.onHdmiDeviceRemoved((android.hardware.hdmi.HdmiDeviceInfo) message.obj);
                    if (onHdmiDeviceRemoved != null) {
                        broadcastRemoveHardwareInput(onHdmiDeviceRemoved);
                        break;
                    }
                    break;
                case 8:
                    android.media.tv.TvInputService.this.onHdmiDeviceUpdated((android.hardware.hdmi.HdmiDeviceInfo) message.obj);
                    break;
                default:
                    android.util.Log.w(android.media.tv.TvInputService.TAG, "Unhandled message code: " + message.what);
                    break;
            }
        }
    }
}
