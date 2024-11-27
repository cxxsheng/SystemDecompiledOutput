package android.media.projection;

/* loaded from: classes2.dex */
public final class MediaProjectionManager {
    public static final java.lang.String EXTRA_APP_TOKEN = "android.media.projection.extra.EXTRA_APP_TOKEN";
    public static final java.lang.String EXTRA_LAUNCH_COOKIE = "android.media.projection.extra.EXTRA_LAUNCH_COOKIE";
    public static final java.lang.String EXTRA_MEDIA_PROJECTION = "android.media.projection.extra.EXTRA_MEDIA_PROJECTION";
    public static final java.lang.String EXTRA_MEDIA_PROJECTION_CONFIG = "android.media.projection.extra.EXTRA_MEDIA_PROJECTION_CONFIG";
    private static final java.lang.String TAG = "MediaProjectionManager";
    public static final int TYPE_MIRRORING = 1;
    public static final int TYPE_PRESENTATION = 2;
    public static final int TYPE_SCREEN_CAPTURE = 0;
    private android.content.Context mContext;
    private android.media.projection.IMediaProjectionManager mService = android.media.projection.IMediaProjectionManager.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.MEDIA_PROJECTION_SERVICE));
    private java.util.Map<android.media.projection.MediaProjectionManager.Callback, android.media.projection.MediaProjectionManager.CallbackDelegate> mCallbacks = new android.util.ArrayMap();

    public MediaProjectionManager(android.content.Context context) {
        this.mContext = context;
    }

    public android.content.Intent createScreenCaptureIntent() {
        android.content.Intent intent = new android.content.Intent();
        intent.setComponent(android.content.ComponentName.unflattenFromString(this.mContext.getResources().getString(com.android.internal.R.string.config_mediaProjectionPermissionDialogComponent)));
        return intent;
    }

    public android.content.Intent createScreenCaptureIntent(android.media.projection.MediaProjectionConfig mediaProjectionConfig) {
        android.content.Intent createScreenCaptureIntent = createScreenCaptureIntent();
        createScreenCaptureIntent.putExtra(EXTRA_MEDIA_PROJECTION_CONFIG, mediaProjectionConfig);
        return createScreenCaptureIntent;
    }

    public android.content.Intent createScreenCaptureIntent(android.app.ActivityOptions.LaunchCookie launchCookie) {
        android.content.Intent createScreenCaptureIntent = createScreenCaptureIntent();
        createScreenCaptureIntent.putExtra(EXTRA_LAUNCH_COOKIE, launchCookie);
        return createScreenCaptureIntent;
    }

    public android.media.projection.MediaProjection getMediaProjection(int i, android.content.Intent intent) {
        android.os.IBinder iBinderExtra;
        if (i != -1 || intent == null || (iBinderExtra = intent.getIBinderExtra(EXTRA_MEDIA_PROJECTION)) == null) {
            return null;
        }
        return new android.media.projection.MediaProjection(this.mContext, android.media.projection.IMediaProjection.Stub.asInterface(iBinderExtra));
    }

    public android.media.projection.MediaProjectionInfo getActiveProjectionInfo() {
        try {
            return this.mService.getActiveProjectionInfo();
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Unable to get the active projection info", e);
            return null;
        }
    }

    public void stopActiveProjection() {
        try {
            android.util.Log.d(TAG, "Content Recording: stopping active projection");
            this.mService.stopActiveProjection();
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Unable to stop the currently active media projection", e);
        }
    }

    public void addCallback(android.media.projection.MediaProjectionManager.Callback callback, android.os.Handler handler) {
        if (callback == null) {
            android.util.Log.w(TAG, "Content Recording: cannot add null callback");
            throw new java.lang.IllegalArgumentException("callback must not be null");
        }
        android.media.projection.MediaProjectionManager.CallbackDelegate callbackDelegate = new android.media.projection.MediaProjectionManager.CallbackDelegate(callback, handler);
        this.mCallbacks.put(callback, callbackDelegate);
        try {
            this.mService.addCallback(callbackDelegate);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Unable to add callbacks to MediaProjection service", e);
        }
    }

    public void removeCallback(android.media.projection.MediaProjectionManager.Callback callback) {
        if (callback == null) {
            android.util.Log.w(TAG, "ContentRecording: cannot remove null callback");
            throw new java.lang.IllegalArgumentException("callback must not be null");
        }
        android.media.projection.MediaProjectionManager.CallbackDelegate remove = this.mCallbacks.remove(callback);
        if (remove != null) {
            try {
                this.mService.removeCallback(remove);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Unable to add callbacks to MediaProjection service", e);
            }
        }
    }

    public static abstract class Callback {
        public abstract void onStart(android.media.projection.MediaProjectionInfo mediaProjectionInfo);

        public abstract void onStop(android.media.projection.MediaProjectionInfo mediaProjectionInfo);

        public void onRecordingSessionSet(android.media.projection.MediaProjectionInfo mediaProjectionInfo, android.view.ContentRecordingSession contentRecordingSession) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class CallbackDelegate extends android.media.projection.IMediaProjectionWatcherCallback.Stub {
        private android.media.projection.MediaProjectionManager.Callback mCallback;
        private android.os.Handler mHandler;

        public CallbackDelegate(android.media.projection.MediaProjectionManager.Callback callback, android.os.Handler handler) {
            this.mCallback = callback;
            this.mHandler = handler == null ? new android.os.Handler() : handler;
        }

        @Override // android.media.projection.IMediaProjectionWatcherCallback
        public void onStart(final android.media.projection.MediaProjectionInfo mediaProjectionInfo) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.projection.MediaProjectionManager.CallbackDelegate.1
                @Override // java.lang.Runnable
                public void run() {
                    android.media.projection.MediaProjectionManager.CallbackDelegate.this.mCallback.onStart(mediaProjectionInfo);
                }
            });
        }

        @Override // android.media.projection.IMediaProjectionWatcherCallback
        public void onStop(final android.media.projection.MediaProjectionInfo mediaProjectionInfo) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.projection.MediaProjectionManager.CallbackDelegate.2
                @Override // java.lang.Runnable
                public void run() {
                    android.media.projection.MediaProjectionManager.CallbackDelegate.this.mCallback.onStop(mediaProjectionInfo);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRecordingSessionSet$0(android.media.projection.MediaProjectionInfo mediaProjectionInfo, android.view.ContentRecordingSession contentRecordingSession) {
            this.mCallback.onRecordingSessionSet(mediaProjectionInfo, contentRecordingSession);
        }

        @Override // android.media.projection.IMediaProjectionWatcherCallback
        public void onRecordingSessionSet(final android.media.projection.MediaProjectionInfo mediaProjectionInfo, final android.view.ContentRecordingSession contentRecordingSession) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.projection.MediaProjectionManager$CallbackDelegate$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.media.projection.MediaProjectionManager.CallbackDelegate.this.lambda$onRecordingSessionSet$0(mediaProjectionInfo, contentRecordingSession);
                }
            });
        }
    }
}
