package android.media.tv;

/* loaded from: classes2.dex */
public class TvRecordingClient {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "TvRecordingClient";
    private final android.media.tv.TvRecordingClient.RecordingCallback mCallback;
    private final android.os.Handler mHandler;
    private boolean mIsPaused;
    private boolean mIsRecordingStarted;
    private boolean mIsRecordingStopping;
    private boolean mIsTuned;
    private final java.util.Queue<android.util.Pair<java.lang.String, android.os.Bundle>> mPendingAppPrivateCommands = new java.util.ArrayDeque();
    private java.lang.String mRecordingId;
    private android.media.tv.TvInputManager.Session mSession;
    private android.media.tv.TvRecordingClient.MySessionCallback mSessionCallback;
    private android.media.tv.interactive.TvInteractiveAppView mTvIAppView;
    private final android.media.tv.TvInputManager mTvInputManager;

    public TvRecordingClient(android.content.Context context, java.lang.String str, android.media.tv.TvRecordingClient.RecordingCallback recordingCallback, android.os.Handler handler) {
        this.mCallback = recordingCallback;
        this.mHandler = handler == null ? new android.os.Handler(android.os.Looper.getMainLooper()) : handler;
        this.mTvInputManager = (android.media.tv.TvInputManager) context.getSystemService(android.content.Context.TV_INPUT_SERVICE);
    }

    public void setTvInteractiveAppView(android.media.tv.interactive.TvInteractiveAppView tvInteractiveAppView, java.lang.String str) {
        if (tvInteractiveAppView != null && str == null) {
            throw new java.lang.IllegalArgumentException("null recordingId is not allowed only when the view is not null");
        }
        if (tvInteractiveAppView == null && str != null) {
            throw new java.lang.IllegalArgumentException("recordingId should be null when the view is null");
        }
        this.mTvIAppView = tvInteractiveAppView;
        this.mRecordingId = str;
    }

    public void tune(java.lang.String str, android.net.Uri uri) {
        tune(str, uri, null);
    }

    public void tune(java.lang.String str, android.net.Uri uri, android.os.Bundle bundle) {
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.IllegalArgumentException("inputId cannot be null or an empty string");
        }
        if (this.mIsRecordingStarted && !this.mIsPaused) {
            throw new java.lang.IllegalStateException("tune failed - recording already started");
        }
        if (this.mSessionCallback != null && android.text.TextUtils.equals(this.mSessionCallback.mInputId, str)) {
            if (this.mSession != null) {
                this.mSessionCallback.mChannelUri = uri;
                this.mSession.tune(uri, bundle);
            } else {
                this.mSessionCallback.mChannelUri = uri;
                this.mSessionCallback.mConnectionParams = bundle;
            }
            this.mIsTuned = false;
            return;
        }
        if (this.mIsPaused) {
            throw new java.lang.IllegalStateException("tune failed - inputId is changed during pause");
        }
        resetInternal();
        this.mSessionCallback = new android.media.tv.TvRecordingClient.MySessionCallback(str, uri, bundle);
        if (this.mTvInputManager != null) {
            this.mTvInputManager.createRecordingSession(str, this.mSessionCallback, this.mHandler);
        }
    }

    public void release() {
        resetInternal();
    }

    private void resetInternal() {
        this.mSessionCallback = null;
        this.mPendingAppPrivateCommands.clear();
        if (this.mSession != null) {
            this.mSession.release();
            this.mIsTuned = false;
            this.mIsRecordingStarted = false;
            this.mIsPaused = false;
            this.mIsRecordingStopping = false;
            this.mSession = null;
        }
    }

    public void startRecording(android.net.Uri uri) {
        startRecording(uri, android.os.Bundle.EMPTY);
    }

    public void startRecording(android.net.Uri uri, android.os.Bundle bundle) {
        if (this.mIsRecordingStopping || !this.mIsTuned || this.mIsPaused) {
            throw new java.lang.IllegalStateException("startRecording failed -recording not yet stopped or not yet tuned or paused");
        }
        if (this.mIsRecordingStarted) {
            android.util.Log.w(TAG, "startRecording failed - recording already started");
        }
        if (this.mSession != null) {
            this.mSession.startRecording(uri, bundle);
            this.mIsRecordingStarted = true;
        }
    }

    public void stopRecording() {
        if (!this.mIsRecordingStarted) {
            android.util.Log.w(TAG, "stopRecording failed - recording not yet started");
        }
        if (this.mSession != null) {
            this.mSession.stopRecording();
            if (this.mIsRecordingStarted) {
                this.mIsRecordingStopping = true;
            }
        }
    }

    public void pauseRecording() {
        pauseRecording(android.os.Bundle.EMPTY);
    }

    public void pauseRecording(android.os.Bundle bundle) {
        if (!this.mIsRecordingStarted || this.mIsRecordingStopping) {
            throw new java.lang.IllegalStateException("pauseRecording failed - recording not yet started or stopping");
        }
        android.media.tv.TvInputInfo tvInputInfo = this.mTvInputManager.getTvInputInfo(this.mSessionCallback.mInputId);
        if (tvInputInfo == null || !tvInputInfo.canPauseRecording()) {
            throw new java.lang.UnsupportedOperationException("pauseRecording failed - operation not supported");
        }
        if (this.mIsPaused) {
            android.util.Log.w(TAG, "pauseRecording failed - recording already paused");
        }
        if (this.mSession != null) {
            this.mSession.pauseRecording(bundle);
            this.mIsPaused = true;
        }
    }

    public void resumeRecording() {
        resumeRecording(android.os.Bundle.EMPTY);
    }

    public void resumeRecording(android.os.Bundle bundle) {
        if (!this.mIsRecordingStarted || this.mIsRecordingStopping || !this.mIsTuned) {
            throw new java.lang.IllegalStateException("resumeRecording failed - recording not yet started or stopping or not yet tuned");
        }
        if (!this.mIsPaused) {
            android.util.Log.w(TAG, "resumeRecording failed - recording not yet paused");
        }
        if (this.mSession != null) {
            this.mSession.resumeRecording(bundle);
            this.mIsPaused = false;
        }
    }

    public void sendAppPrivateCommand(java.lang.String str, android.os.Bundle bundle) {
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.IllegalArgumentException("action cannot be null or an empty string");
        }
        if (this.mSession != null) {
            this.mSession.sendAppPrivateCommand(str, bundle);
        } else {
            android.util.Log.w(TAG, "sendAppPrivateCommand - session not yet created (action \"" + str + "\" pending)");
            this.mPendingAppPrivateCommands.add(android.util.Pair.create(str, bundle));
        }
    }

    public android.media.tv.TvInputManager.SessionCallback getSessionCallback() {
        return this.mSessionCallback;
    }

    public static abstract class RecordingCallback {
        public void onConnectionFailed(java.lang.String str) {
        }

        public void onDisconnected(java.lang.String str) {
        }

        public void onTuned(android.net.Uri uri) {
        }

        public void onRecordingStopped(android.net.Uri uri) {
        }

        public void onError(int i) {
        }

        @android.annotation.SystemApi
        public void onEvent(java.lang.String str, java.lang.String str2, android.os.Bundle bundle) {
        }
    }

    private class MySessionCallback extends android.media.tv.TvInputManager.SessionCallback {
        android.net.Uri mChannelUri;
        android.os.Bundle mConnectionParams;
        final java.lang.String mInputId;

        MySessionCallback(java.lang.String str, android.net.Uri uri, android.os.Bundle bundle) {
            this.mInputId = str;
            this.mChannelUri = uri;
            this.mConnectionParams = bundle;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onSessionCreated(android.media.tv.TvInputManager.Session session) {
            if (this != android.media.tv.TvRecordingClient.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.TvRecordingClient.TAG, "onSessionCreated - session already created");
                if (session != null) {
                    session.release();
                    return;
                }
                return;
            }
            android.media.tv.TvRecordingClient.this.mSession = session;
            if (session != null) {
                for (android.util.Pair pair : android.media.tv.TvRecordingClient.this.mPendingAppPrivateCommands) {
                    android.media.tv.TvRecordingClient.this.mSession.sendAppPrivateCommand((java.lang.String) pair.first, (android.os.Bundle) pair.second);
                }
                android.media.tv.TvRecordingClient.this.mPendingAppPrivateCommands.clear();
                android.media.tv.TvRecordingClient.this.mSession.tune(this.mChannelUri, this.mConnectionParams);
                return;
            }
            android.media.tv.TvRecordingClient.this.mSessionCallback = null;
            if (android.media.tv.TvRecordingClient.this.mCallback != null) {
                android.media.tv.TvRecordingClient.this.mCallback.onConnectionFailed(this.mInputId);
            }
            if (android.media.tv.TvRecordingClient.this.mTvIAppView != null) {
                android.media.tv.TvRecordingClient.this.mTvIAppView.notifyRecordingConnectionFailed(android.media.tv.TvRecordingClient.this.mRecordingId, this.mInputId);
            }
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onTuned(android.media.tv.TvInputManager.Session session, android.net.Uri uri) {
            if (this != android.media.tv.TvRecordingClient.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.TvRecordingClient.TAG, "onTuned - session not created");
                return;
            }
            if (android.media.tv.TvRecordingClient.this.mIsTuned || !java.util.Objects.equals(this.mChannelUri, uri)) {
                android.util.Log.w(android.media.tv.TvRecordingClient.TAG, "onTuned - already tuned or not yet tuned to last channel");
                return;
            }
            android.media.tv.TvRecordingClient.this.mIsTuned = true;
            if (android.media.tv.TvRecordingClient.this.mCallback != null) {
                android.media.tv.TvRecordingClient.this.mCallback.onTuned(uri);
            }
            if (android.media.tv.TvRecordingClient.this.mTvIAppView != null) {
                android.media.tv.TvRecordingClient.this.mTvIAppView.notifyRecordingTuned(android.media.tv.TvRecordingClient.this.mRecordingId, uri);
            }
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onSessionReleased(android.media.tv.TvInputManager.Session session) {
            if (this != android.media.tv.TvRecordingClient.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.TvRecordingClient.TAG, "onSessionReleased - session not created");
                return;
            }
            android.media.tv.TvRecordingClient.this.mIsTuned = false;
            android.media.tv.TvRecordingClient.this.mIsRecordingStarted = false;
            android.media.tv.TvRecordingClient.this.mIsPaused = false;
            android.media.tv.TvRecordingClient.this.mIsRecordingStopping = false;
            android.media.tv.TvRecordingClient.this.mSessionCallback = null;
            android.media.tv.TvRecordingClient.this.mSession = null;
            if (android.media.tv.TvRecordingClient.this.mCallback != null) {
                android.media.tv.TvRecordingClient.this.mCallback.onDisconnected(this.mInputId);
            }
            if (android.media.tv.TvRecordingClient.this.mTvIAppView != null) {
                android.media.tv.TvRecordingClient.this.mTvIAppView.notifyRecordingDisconnected(android.media.tv.TvRecordingClient.this.mRecordingId, this.mInputId);
            }
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onRecordingStopped(android.media.tv.TvInputManager.Session session, android.net.Uri uri) {
            if (this != android.media.tv.TvRecordingClient.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.TvRecordingClient.TAG, "onRecordingStopped - session not created");
                return;
            }
            if (!android.media.tv.TvRecordingClient.this.mIsRecordingStarted) {
                android.util.Log.w(android.media.tv.TvRecordingClient.TAG, "onRecordingStopped - recording not yet started");
                return;
            }
            android.media.tv.TvRecordingClient.this.mIsRecordingStarted = false;
            android.media.tv.TvRecordingClient.this.mIsPaused = false;
            android.media.tv.TvRecordingClient.this.mIsRecordingStopping = false;
            if (android.media.tv.TvRecordingClient.this.mCallback != null) {
                android.media.tv.TvRecordingClient.this.mCallback.onRecordingStopped(uri);
            }
            if (android.media.tv.TvRecordingClient.this.mTvIAppView != null) {
                android.media.tv.TvRecordingClient.this.mTvIAppView.notifyRecordingStopped(android.media.tv.TvRecordingClient.this.mRecordingId);
            }
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onError(android.media.tv.TvInputManager.Session session, int i) {
            if (this != android.media.tv.TvRecordingClient.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.TvRecordingClient.TAG, "onError - session not created");
                return;
            }
            if (android.media.tv.TvRecordingClient.this.mCallback != null) {
                android.media.tv.TvRecordingClient.this.mCallback.onError(i);
            }
            if (android.media.tv.TvRecordingClient.this.mTvIAppView != null) {
                android.media.tv.TvRecordingClient.this.mTvIAppView.notifyRecordingError(android.media.tv.TvRecordingClient.this.mRecordingId, i);
            }
        }

        @Override // android.media.tv.TvInputManager.SessionCallback
        public void onSessionEvent(android.media.tv.TvInputManager.Session session, java.lang.String str, android.os.Bundle bundle) {
            if (this != android.media.tv.TvRecordingClient.this.mSessionCallback) {
                android.util.Log.w(android.media.tv.TvRecordingClient.TAG, "onSessionEvent - session not created");
            } else if (android.media.tv.TvRecordingClient.this.mCallback != null) {
                android.media.tv.TvRecordingClient.this.mCallback.onEvent(this.mInputId, str, bundle);
            }
        }
    }
}
