package android.media;

@java.lang.Deprecated
/* loaded from: classes2.dex */
public final class RemoteController {
    private static final boolean DEBUG = false;
    private static final int MAX_BITMAP_DIMENSION = 512;
    private static final int MSG_CLIENT_CHANGE = 0;
    private static final int MSG_NEW_MEDIA_METADATA = 2;
    private static final int MSG_NEW_PLAYBACK_STATE = 1;
    public static final int POSITION_SYNCHRONIZATION_CHECK = 1;
    public static final int POSITION_SYNCHRONIZATION_NONE = 0;
    private static final int SENDMSG_NOOP = 1;
    private static final int SENDMSG_QUEUE = 2;
    private static final int SENDMSG_REPLACE = 0;
    private static final java.lang.String TAG = "RemoteController";
    private static final java.lang.Object mInfoLock = new java.lang.Object();
    private int mArtworkHeight;
    private int mArtworkWidth;
    private final android.content.Context mContext;
    private android.media.session.MediaController mCurrentSession;
    private boolean mEnabled;
    private final android.media.RemoteController.EventHandler mEventHandler;
    private boolean mIsRegistered;
    private android.media.RemoteController.PlaybackInfo mLastPlaybackInfo;
    private final int mMaxBitmapDimension;
    private android.media.RemoteController.MetadataEditor mMetadataEditor;
    private android.media.RemoteController.OnClientUpdateListener mOnClientUpdateListener;
    private android.media.session.MediaController.Callback mSessionCb;
    private android.media.session.MediaSessionManager.OnActiveSessionsChangedListener mSessionListener;
    private android.media.session.MediaSessionManager mSessionManager;

    public interface OnClientUpdateListener {
        void onClientChange(boolean z);

        void onClientMetadataUpdate(android.media.RemoteController.MetadataEditor metadataEditor);

        void onClientPlaybackStateUpdate(int i);

        void onClientPlaybackStateUpdate(int i, long j, long j2, float f);

        void onClientTransportControlUpdate(int i);
    }

    public RemoteController(android.content.Context context, android.media.RemoteController.OnClientUpdateListener onClientUpdateListener) throws java.lang.IllegalArgumentException {
        this(context, onClientUpdateListener, null);
    }

    public RemoteController(android.content.Context context, android.media.RemoteController.OnClientUpdateListener onClientUpdateListener, android.os.Looper looper) throws java.lang.IllegalArgumentException {
        byte b = 0;
        this.mSessionCb = new android.media.RemoteController.MediaControllerCallback();
        this.mIsRegistered = false;
        this.mArtworkWidth = -1;
        this.mArtworkHeight = -1;
        this.mEnabled = true;
        if (context == null) {
            throw new java.lang.IllegalArgumentException("Invalid null Context");
        }
        if (onClientUpdateListener == null) {
            throw new java.lang.IllegalArgumentException("Invalid null OnClientUpdateListener");
        }
        if (looper != null) {
            this.mEventHandler = new android.media.RemoteController.EventHandler(this, looper);
        } else {
            android.os.Looper myLooper = android.os.Looper.myLooper();
            if (myLooper != null) {
                this.mEventHandler = new android.media.RemoteController.EventHandler(this, myLooper);
            } else {
                throw new java.lang.IllegalArgumentException("Calling thread not associated with a looper");
            }
        }
        this.mOnClientUpdateListener = onClientUpdateListener;
        this.mContext = context;
        this.mSessionManager = (android.media.session.MediaSessionManager) context.getSystemService(android.content.Context.MEDIA_SESSION_SERVICE);
        this.mSessionListener = new android.media.RemoteController.TopTransportSessionListener();
        if (android.app.ActivityManager.isLowRamDeviceStatic()) {
            this.mMaxBitmapDimension = 512;
        } else {
            android.util.DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            this.mMaxBitmapDimension = java.lang.Math.max(displayMetrics.widthPixels, displayMetrics.heightPixels);
        }
    }

    public long getEstimatedMediaPosition() {
        android.media.session.PlaybackState playbackState;
        synchronized (mInfoLock) {
            if (this.mCurrentSession != null && (playbackState = this.mCurrentSession.getPlaybackState()) != null) {
                return playbackState.getPosition();
            }
            return -1L;
        }
    }

    public boolean sendMediaKeyEvent(android.view.KeyEvent keyEvent) throws java.lang.IllegalArgumentException {
        if (!android.view.KeyEvent.isMediaSessionKey(keyEvent.getKeyCode())) {
            throw new java.lang.IllegalArgumentException("not a media key event");
        }
        synchronized (mInfoLock) {
            if (this.mCurrentSession == null) {
                return false;
            }
            return this.mCurrentSession.dispatchMediaButtonEvent(keyEvent);
        }
    }

    public boolean seekTo(long j) throws java.lang.IllegalArgumentException {
        if (!this.mEnabled) {
            android.util.Log.e(TAG, "Cannot use seekTo() from a disabled RemoteController");
            return false;
        }
        if (j < 0) {
            throw new java.lang.IllegalArgumentException("illegal negative time value");
        }
        synchronized (mInfoLock) {
            if (this.mCurrentSession != null) {
                this.mCurrentSession.getTransportControls().seekTo(j);
            }
        }
        return true;
    }

    public boolean setArtworkConfiguration(boolean z, int i, int i2) throws java.lang.IllegalArgumentException {
        synchronized (mInfoLock) {
            if (z) {
                if (i > 0 && i2 > 0) {
                    if (i > this.mMaxBitmapDimension) {
                        i = this.mMaxBitmapDimension;
                    }
                    if (i2 > this.mMaxBitmapDimension) {
                        i2 = this.mMaxBitmapDimension;
                    }
                    this.mArtworkWidth = i;
                    this.mArtworkHeight = i2;
                } else {
                    throw new java.lang.IllegalArgumentException("Invalid dimensions");
                }
            } else {
                this.mArtworkWidth = -1;
                this.mArtworkHeight = -1;
            }
        }
        return true;
    }

    public boolean setArtworkConfiguration(int i, int i2) throws java.lang.IllegalArgumentException {
        return setArtworkConfiguration(true, i, i2);
    }

    public boolean clearArtworkConfiguration() {
        return setArtworkConfiguration(false, -1, -1);
    }

    public boolean setSynchronizationMode(int i) throws java.lang.IllegalArgumentException {
        if (i != 0 && i != 1) {
            throw new java.lang.IllegalArgumentException("Unknown synchronization mode " + i);
        }
        if (this.mIsRegistered) {
            return true;
        }
        android.util.Log.e(TAG, "Cannot set synchronization mode on an unregistered RemoteController");
        return false;
    }

    public android.media.RemoteController.MetadataEditor editMetadata() {
        android.media.RemoteController.MetadataEditor metadataEditor = new android.media.RemoteController.MetadataEditor();
        metadataEditor.mEditorMetadata = new android.os.Bundle();
        metadataEditor.mEditorArtwork = null;
        metadataEditor.mMetadataChanged = true;
        metadataEditor.mArtworkChanged = true;
        metadataEditor.mEditableKeys = 0L;
        return metadataEditor;
    }

    public class MetadataEditor extends android.media.MediaMetadataEditor {
        protected MetadataEditor() {
        }

        protected MetadataEditor(android.os.Bundle bundle, long j) {
            this.mEditorMetadata = bundle;
            this.mEditableKeys = j;
            this.mEditorArtwork = (android.graphics.Bitmap) bundle.getParcelable(java.lang.String.valueOf(100), android.graphics.Bitmap.class);
            if (this.mEditorArtwork != null) {
                cleanupBitmapFromBundle(100);
            }
            this.mMetadataChanged = true;
            this.mArtworkChanged = true;
            this.mApplied = false;
        }

        private void cleanupBitmapFromBundle(int i) {
            if (METADATA_KEYS_TYPE.get(i, -1) == 2) {
                this.mEditorMetadata.remove(java.lang.String.valueOf(i));
            }
        }

        @Override // android.media.MediaMetadataEditor
        public synchronized void apply() {
            android.media.Rating rating;
            if (this.mMetadataChanged) {
                synchronized (android.media.RemoteController.mInfoLock) {
                    if (android.media.RemoteController.this.mCurrentSession != null && this.mEditorMetadata.containsKey(java.lang.String.valueOf(android.media.MediaMetadataEditor.RATING_KEY_BY_USER)) && (rating = (android.media.Rating) getObject(android.media.MediaMetadataEditor.RATING_KEY_BY_USER, null)) != null) {
                        android.media.RemoteController.this.mCurrentSession.getTransportControls().setRating(rating);
                    }
                }
                this.mApplied = false;
            }
        }
    }

    private class MediaControllerCallback extends android.media.session.MediaController.Callback {
        private MediaControllerCallback() {
        }

        @Override // android.media.session.MediaController.Callback
        public void onPlaybackStateChanged(android.media.session.PlaybackState playbackState) {
            android.media.RemoteController.this.onNewPlaybackState(playbackState);
        }

        @Override // android.media.session.MediaController.Callback
        public void onMetadataChanged(android.media.MediaMetadata mediaMetadata) {
            android.media.RemoteController.this.onNewMediaMetadata(mediaMetadata);
        }
    }

    private class TopTransportSessionListener implements android.media.session.MediaSessionManager.OnActiveSessionsChangedListener {
        private TopTransportSessionListener() {
        }

        @Override // android.media.session.MediaSessionManager.OnActiveSessionsChangedListener
        public void onActiveSessionsChanged(java.util.List<android.media.session.MediaController> list) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                android.media.session.MediaController mediaController = list.get(i);
                if ((mediaController.getFlags() & 2) != 0) {
                    android.media.RemoteController.this.updateController(mediaController);
                    return;
                }
            }
            android.media.RemoteController.this.updateController(null);
        }
    }

    private class EventHandler extends android.os.Handler {
        public EventHandler(android.media.RemoteController remoteController, android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 0:
                    android.media.RemoteController.this.onClientChange(message.arg2 == 1);
                    break;
                case 1:
                    android.media.RemoteController.this.onNewPlaybackState((android.media.session.PlaybackState) message.obj);
                    break;
                case 2:
                    android.media.RemoteController.this.onNewMediaMetadata((android.media.MediaMetadata) message.obj);
                    break;
                default:
                    android.util.Log.e(android.media.RemoteController.TAG, "unknown event " + message.what);
                    break;
            }
        }
    }

    void startListeningToSessions() {
        android.os.Handler handler;
        android.content.ComponentName componentName = new android.content.ComponentName(this.mContext, this.mOnClientUpdateListener.getClass());
        if (android.os.Looper.myLooper() != null) {
            handler = null;
        } else {
            handler = new android.os.Handler(android.os.Looper.getMainLooper());
        }
        this.mSessionManager.addOnActiveSessionsChangedListener(this.mSessionListener, componentName, handler);
        this.mSessionListener.onActiveSessionsChanged(this.mSessionManager.getActiveSessions(componentName));
    }

    void stopListeningToSessions() {
        this.mSessionManager.removeOnActiveSessionsChangedListener(this.mSessionListener);
    }

    private static void sendMsg(android.os.Handler handler, int i, int i2, int i3, int i4, java.lang.Object obj, int i5) {
        if (handler == null) {
            android.util.Log.e(TAG, "null event handler, will not deliver message " + i);
            return;
        }
        if (i2 == 0) {
            handler.removeMessages(i);
        } else if (i2 == 1 && handler.hasMessages(i)) {
            return;
        }
        handler.sendMessageDelayed(handler.obtainMessage(i, i3, i4, obj), i5);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onClientChange(boolean z) {
        android.media.RemoteController.OnClientUpdateListener onClientUpdateListener;
        synchronized (mInfoLock) {
            onClientUpdateListener = this.mOnClientUpdateListener;
            this.mMetadataEditor = null;
        }
        if (onClientUpdateListener != null) {
            onClientUpdateListener.onClientChange(z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateController(android.media.session.MediaController mediaController) {
        synchronized (mInfoLock) {
            if (mediaController == null) {
                if (this.mCurrentSession != null) {
                    this.mCurrentSession.unregisterCallback(this.mSessionCb);
                    this.mCurrentSession = null;
                    sendMsg(this.mEventHandler, 0, 0, 0, 1, null, 0);
                }
            } else if (this.mCurrentSession == null || !mediaController.getSessionToken().equals(this.mCurrentSession.getSessionToken())) {
                if (this.mCurrentSession != null) {
                    this.mCurrentSession.unregisterCallback(this.mSessionCb);
                }
                sendMsg(this.mEventHandler, 0, 0, 0, 0, null, 0);
                this.mCurrentSession = mediaController;
                this.mCurrentSession.registerCallback(this.mSessionCb, this.mEventHandler);
                sendMsg(this.mEventHandler, 1, 0, 0, 0, mediaController.getPlaybackState(), 0);
                sendMsg(this.mEventHandler, 2, 0, 0, 0, mediaController.getMetadata(), 0);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onNewPlaybackState(android.media.session.PlaybackState playbackState) {
        android.media.RemoteController.OnClientUpdateListener onClientUpdateListener;
        synchronized (mInfoLock) {
            onClientUpdateListener = this.mOnClientUpdateListener;
        }
        if (onClientUpdateListener != null) {
            int rccStateFromState = playbackState == null ? 0 : android.media.RemoteControlClient.getRccStateFromState(playbackState.getState());
            if (playbackState == null || playbackState.getPosition() == -1) {
                onClientUpdateListener.onClientPlaybackStateUpdate(rccStateFromState);
            } else {
                onClientUpdateListener.onClientPlaybackStateUpdate(rccStateFromState, playbackState.getLastPositionUpdateTime(), playbackState.getPosition(), playbackState.getPlaybackSpeed());
            }
            if (playbackState != null) {
                onClientUpdateListener.onClientTransportControlUpdate(android.media.RemoteControlClient.getRccControlFlagsFromActions(playbackState.getActions()));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onNewMediaMetadata(android.media.MediaMetadata mediaMetadata) {
        android.media.RemoteController.OnClientUpdateListener onClientUpdateListener;
        android.media.RemoteController.MetadataEditor metadataEditor;
        if (mediaMetadata == null) {
            return;
        }
        synchronized (mInfoLock) {
            onClientUpdateListener = this.mOnClientUpdateListener;
            this.mMetadataEditor = new android.media.RemoteController.MetadataEditor(android.media.session.MediaSessionLegacyHelper.getOldMetadata(mediaMetadata, this.mArtworkWidth, this.mArtworkHeight), this.mCurrentSession != null && this.mCurrentSession.getRatingType() != 0 ? 268435457L : 0L);
            metadataEditor = this.mMetadataEditor;
        }
        if (onClientUpdateListener != null) {
            onClientUpdateListener.onClientMetadataUpdate(metadataEditor);
        }
    }

    private static class PlaybackInfo {
        long mCurrentPosMs;
        float mSpeed;
        int mState;
        long mStateChangeTimeMs;

        PlaybackInfo(int i, long j, long j2, float f) {
            this.mState = i;
            this.mStateChangeTimeMs = j;
            this.mCurrentPosMs = j2;
            this.mSpeed = f;
        }
    }

    android.media.RemoteController.OnClientUpdateListener getUpdateListener() {
        return this.mOnClientUpdateListener;
    }
}
