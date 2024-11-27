package android.media.tv.ad;

/* loaded from: classes2.dex */
public abstract class TvAdService extends android.app.Service {
    private static final boolean DEBUG = false;
    private static final int DETACH_MEDIA_VIEW_TIMEOUT_MS = 5000;
    public static final java.lang.String SERVICE_INTERFACE = "android.media.tv.ad.TvAdService";
    public static final java.lang.String SERVICE_META_DATA = "android.media.tv.ad.service";
    private static final java.lang.String TAG = "TvAdService";
    private final android.os.Handler mServiceHandler = new android.media.tv.ad.TvAdService.ServiceHandler();
    private final android.os.RemoteCallbackList<android.media.tv.ad.ITvAdServiceCallback> mCallbacks = new android.os.RemoteCallbackList<>();

    public abstract android.media.tv.ad.TvAdService.Session onCreateSession(java.lang.String str, java.lang.String str2);

    @Override // android.app.Service
    public final android.os.IBinder onBind(android.content.Intent intent) {
        return new android.media.tv.ad.ITvAdService.Stub() { // from class: android.media.tv.ad.TvAdService.1
            @Override // android.media.tv.ad.ITvAdService
            public void registerCallback(android.media.tv.ad.ITvAdServiceCallback iTvAdServiceCallback) {
                if (iTvAdServiceCallback != null) {
                    android.media.tv.ad.TvAdService.this.mCallbacks.register(iTvAdServiceCallback);
                }
            }

            @Override // android.media.tv.ad.ITvAdService
            public void unregisterCallback(android.media.tv.ad.ITvAdServiceCallback iTvAdServiceCallback) {
                if (iTvAdServiceCallback != null) {
                    android.media.tv.ad.TvAdService.this.mCallbacks.unregister(iTvAdServiceCallback);
                }
            }

            @Override // android.media.tv.ad.ITvAdService
            public void createSession(android.view.InputChannel inputChannel, android.media.tv.ad.ITvAdSessionCallback iTvAdSessionCallback, java.lang.String str, java.lang.String str2) {
                if (iTvAdSessionCallback == null) {
                    return;
                }
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = inputChannel;
                obtain.arg2 = iTvAdSessionCallback;
                obtain.arg3 = str;
                obtain.arg4 = str2;
                android.media.tv.ad.TvAdService.this.mServiceHandler.obtainMessage(1, obtain).sendToTarget();
            }

            @Override // android.media.tv.ad.ITvAdService
            public void sendAppLinkCommand(android.os.Bundle bundle) {
                android.media.tv.ad.TvAdService.this.onAppLinkCommand(bundle);
            }
        };
    }

    public void onAppLinkCommand(android.os.Bundle bundle) {
    }

    public static abstract class Session implements android.view.KeyEvent.Callback {
        private final android.content.Context mContext;
        final android.os.Handler mHandler;
        private android.graphics.Rect mMediaFrame;
        private android.view.View mMediaView;
        private android.media.tv.ad.TvAdService.MediaViewCleanUpTask mMediaViewCleanUpTask;
        private android.widget.FrameLayout mMediaViewContainer;
        private boolean mMediaViewEnabled;
        private android.media.tv.ad.ITvAdSessionCallback mSessionCallback;
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
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.tv.ad.TvAdService.Session.1
                @Override // java.lang.Runnable
                public void run() {
                    if (z == android.media.tv.ad.TvAdService.Session.this.mMediaViewEnabled) {
                        return;
                    }
                    android.media.tv.ad.TvAdService.Session.this.mMediaViewEnabled = z;
                    if (z) {
                        if (android.media.tv.ad.TvAdService.Session.this.mWindowToken != null) {
                            android.media.tv.ad.TvAdService.Session.this.createMediaView(android.media.tv.ad.TvAdService.Session.this.mWindowToken, android.media.tv.ad.TvAdService.Session.this.mMediaFrame);
                            return;
                        }
                        return;
                    }
                    android.media.tv.ad.TvAdService.Session.this.removeMediaView(false);
                }
            });
        }

        public boolean isMediaViewEnabled() {
            return this.mMediaViewEnabled;
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

        public void onStartAdService() {
        }

        public void onStopAdService() {
        }

        public void onResetAdService() {
        }

        void startAdService() {
            onStartAdService();
        }

        void stopAdService() {
            onStopAdService();
        }

        void resetAdService() {
            onResetAdService();
        }

        public void requestCurrentVideoBounds() {
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.ad.TvAdService.Session.2
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (android.media.tv.ad.TvAdService.Session.this.mSessionCallback != null) {
                            android.media.tv.ad.TvAdService.Session.this.mSessionCallback.onRequestCurrentVideoBounds();
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(android.media.tv.ad.TvAdService.TAG, "error in requestCurrentVideoBounds", e);
                    }
                }
            });
        }

        public void requestCurrentChannelUri() {
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.ad.TvAdService.Session.3
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (android.media.tv.ad.TvAdService.Session.this.mSessionCallback != null) {
                            android.media.tv.ad.TvAdService.Session.this.mSessionCallback.onRequestCurrentChannelUri();
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(android.media.tv.ad.TvAdService.TAG, "error in requestCurrentChannelUri", e);
                    }
                }
            });
        }

        public void requestTrackInfoList() {
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.ad.TvAdService.Session.4
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (android.media.tv.ad.TvAdService.Session.this.mSessionCallback != null) {
                            android.media.tv.ad.TvAdService.Session.this.mSessionCallback.onRequestTrackInfoList();
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(android.media.tv.ad.TvAdService.TAG, "error in requestTrackInfoList", e);
                    }
                }
            });
        }

        public void requestCurrentTvInputId() {
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.ad.TvAdService.Session.5
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (android.media.tv.ad.TvAdService.Session.this.mSessionCallback != null) {
                            android.media.tv.ad.TvAdService.Session.this.mSessionCallback.onRequestCurrentTvInputId();
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(android.media.tv.ad.TvAdService.TAG, "error in requestCurrentTvInputId", e);
                    }
                }
            });
        }

        public void requestSigning(final java.lang.String str, final java.lang.String str2, final java.lang.String str3, final byte[] bArr) {
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.ad.TvAdService.Session.6
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (android.media.tv.ad.TvAdService.Session.this.mSessionCallback != null) {
                            android.media.tv.ad.TvAdService.Session.this.mSessionCallback.onRequestSigning(str, str2, str3, bArr);
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(android.media.tv.ad.TvAdService.TAG, "error in requestSigning", e);
                    }
                }
            });
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
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.ad.TvAdService.Session.7
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (android.media.tv.ad.TvAdService.Session.this.mSessionCallback != null) {
                            android.media.tv.ad.TvAdService.Session.this.mSessionCallback.onLayoutSurface(i, i2, i3, i4);
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(android.media.tv.ad.TvAdService.TAG, "error in layoutSurface", e);
                    }
                }
            });
        }

        public void onSurfaceChanged(int i, int i2, int i3) {
        }

        public void onCurrentVideoBounds(android.graphics.Rect rect) {
        }

        public void onCurrentChannelUri(android.net.Uri uri) {
        }

        public void onTrackInfoList(java.util.List<android.media.tv.TvTrackInfo> list) {
        }

        public void onCurrentTvInputId(java.lang.String str) {
        }

        public void onSigningResult(java.lang.String str, byte[] bArr) {
        }

        public void onError(java.lang.String str, android.os.Bundle bundle) {
        }

        public void onTvMessage(int i, android.os.Bundle bundle) {
        }

        public void onTvInputSessionData(java.lang.String str, android.os.Bundle bundle) {
        }

        public void onMediaViewSizeChanged(int i, int i2) {
        }

        public android.view.View onCreateMediaView() {
            return null;
        }

        public void sendTvAdSessionData(final java.lang.String str, final android.os.Bundle bundle) {
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.ad.TvAdService.Session.8
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (android.media.tv.ad.TvAdService.Session.this.mSessionCallback != null) {
                            android.media.tv.ad.TvAdService.Session.this.mSessionCallback.onTvAdSessionData(str, bundle);
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(android.media.tv.ad.TvAdService.TAG, "error in sendTvAdSessionData", e);
                    }
                }
            });
        }

        public void notifySessionStateChanged(final int i, final int i2) {
            executeOrPostRunnableOnMainThread(new java.lang.Runnable() { // from class: android.media.tv.ad.TvAdService.Session.9
                @Override // java.lang.Runnable
                public void run() {
                }
            });
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
        public void initialize(android.media.tv.ad.ITvAdSessionCallback iTvAdSessionCallback) {
            synchronized (this.mLock) {
                this.mSessionCallback = iTvAdSessionCallback;
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

        void sendCurrentVideoBounds(android.graphics.Rect rect) {
            onCurrentVideoBounds(rect);
        }

        void sendCurrentChannelUri(android.net.Uri uri) {
            onCurrentChannelUri(uri);
        }

        void sendTrackInfoList(java.util.List<android.media.tv.TvTrackInfo> list) {
            onTrackInfoList(list);
        }

        void sendCurrentTvInputId(java.lang.String str) {
            onCurrentTvInputId(str);
        }

        void sendSigningResult(java.lang.String str, byte[] bArr) {
            onSigningResult(str, bArr);
        }

        void notifyError(java.lang.String str, android.os.Bundle bundle) {
            onError(str, bundle);
        }

        void notifyTvMessage(int i, android.os.Bundle bundle) {
            onTvMessage(i, bundle);
        }

        void notifyTvInputSessionData(java.lang.String str, android.os.Bundle bundle) {
            onTvInputSessionData(str, bundle);
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
                this.mMediaViewCleanUpTask = new android.media.tv.ad.TvAdService.MediaViewCleanUpTask();
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
                    android.util.Log.e(android.media.tv.ad.TvAdService.TAG, "Time out on releasing media view. Killing " + view.getContext().getPackageName());
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
        private static final int DO_NOTIFY_SESSION_CREATED = 2;

        private ServiceHandler() {
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    com.android.internal.os.SomeArgs someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    android.view.InputChannel inputChannel = (android.view.InputChannel) someArgs.arg1;
                    android.media.tv.ad.ITvAdSessionCallback iTvAdSessionCallback = (android.media.tv.ad.ITvAdSessionCallback) someArgs.arg2;
                    java.lang.String str = (java.lang.String) someArgs.arg3;
                    java.lang.String str2 = (java.lang.String) someArgs.arg4;
                    someArgs.recycle();
                    android.media.tv.ad.TvAdService.Session onCreateSession = android.media.tv.ad.TvAdService.this.onCreateSession(str, str2);
                    if (onCreateSession == null) {
                        try {
                            iTvAdSessionCallback.onSessionCreated(null);
                            break;
                        } catch (android.os.RemoteException e) {
                            android.util.Log.e(android.media.tv.ad.TvAdService.TAG, "error in onSessionCreated", e);
                            return;
                        }
                    } else {
                        android.media.tv.ad.ITvAdSessionWrapper iTvAdSessionWrapper = new android.media.tv.ad.ITvAdSessionWrapper(android.media.tv.ad.TvAdService.this, onCreateSession, inputChannel);
                        com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                        obtain.arg1 = onCreateSession;
                        obtain.arg2 = iTvAdSessionWrapper;
                        obtain.arg3 = iTvAdSessionCallback;
                        android.media.tv.ad.TvAdService.this.mServiceHandler.obtainMessage(2, obtain).sendToTarget();
                        break;
                    }
                case 2:
                    com.android.internal.os.SomeArgs someArgs2 = (com.android.internal.os.SomeArgs) message.obj;
                    android.media.tv.ad.TvAdService.Session session = (android.media.tv.ad.TvAdService.Session) someArgs2.arg1;
                    android.media.tv.ad.ITvAdSession iTvAdSession = (android.media.tv.ad.ITvAdSession) someArgs2.arg2;
                    android.media.tv.ad.ITvAdSessionCallback iTvAdSessionCallback2 = (android.media.tv.ad.ITvAdSessionCallback) someArgs2.arg3;
                    try {
                        iTvAdSessionCallback2.onSessionCreated(iTvAdSession);
                    } catch (android.os.RemoteException e2) {
                        android.util.Log.e(android.media.tv.ad.TvAdService.TAG, "error in onSessionCreated", e2);
                    }
                    if (session != null) {
                        session.initialize(iTvAdSessionCallback2);
                    }
                    someArgs2.recycle();
                    break;
                default:
                    android.util.Log.w(android.media.tv.ad.TvAdService.TAG, "Unhandled message code: " + message.what);
                    break;
            }
        }
    }
}
