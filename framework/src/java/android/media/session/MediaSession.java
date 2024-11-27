package android.media.session;

/* loaded from: classes2.dex */
public final class MediaSession {

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static final int FLAG_EXCLUSIVE_GLOBAL_PRIORITY = 65536;

    @java.lang.Deprecated
    public static final int FLAG_HANDLES_MEDIA_BUTTONS = 1;

    @java.lang.Deprecated
    public static final int FLAG_HANDLES_TRANSPORT_CONTROLS = 2;
    public static final int INVALID_PID = -1;
    public static final int INVALID_UID = -1;
    static final java.lang.String TAG = "MediaSession";
    private boolean mActive;
    private final android.media.session.ISession mBinder;
    private android.media.session.MediaSession.CallbackMessageHandler mCallback;
    private final android.media.session.MediaSession.CallbackStub mCbStub;
    private android.content.Context mContext;
    private final android.media.session.MediaController mController;
    private final java.lang.Object mLock;
    private final int mMaxBitmapSize;
    private android.media.session.PlaybackState mPlaybackState;
    private final android.media.session.MediaSession.Token mSessionToken;
    private android.media.VolumeProvider mVolumeProvider;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SessionFlags {
    }

    public MediaSession(android.content.Context context, java.lang.String str) {
        this(context, str, null);
    }

    public MediaSession(android.content.Context context, java.lang.String str, android.os.Bundle bundle) {
        this.mLock = new java.lang.Object();
        this.mActive = false;
        if (context == null) {
            throw new java.lang.IllegalArgumentException("context cannot be null.");
        }
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.IllegalArgumentException("tag cannot be null or empty");
        }
        if (hasCustomParcelable(bundle)) {
            throw new java.lang.IllegalArgumentException("sessionInfo shouldn't contain any custom parcelables");
        }
        this.mContext = context;
        this.mMaxBitmapSize = context.getResources().getDimensionPixelSize(com.android.internal.R.dimen.config_mediaMetadataBitmapMaxSize);
        this.mCbStub = new android.media.session.MediaSession.CallbackStub(this);
        try {
            this.mBinder = ((android.media.session.MediaSessionManager) context.getSystemService(android.content.Context.MEDIA_SESSION_SERVICE)).createSession(this.mCbStub, str, bundle);
            this.mSessionToken = new android.media.session.MediaSession.Token(android.os.Process.myUid(), this.mBinder.getController());
            this.mController = new android.media.session.MediaController(context, this.mSessionToken);
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException("Remote error creating session.", e);
        }
    }

    public void setCallback(android.media.session.MediaSession.Callback callback) {
        setCallback(callback, null);
    }

    public void setCallback(android.media.session.MediaSession.Callback callback, android.os.Handler handler) {
        synchronized (this.mLock) {
            if (this.mCallback != null) {
                this.mCallback.mCallback.mSession = null;
                this.mCallback.removeCallbacksAndMessages(null);
            }
            if (callback == null) {
                this.mCallback = null;
                return;
            }
            android.os.Looper looper = handler != null ? handler.getLooper() : android.os.Looper.myLooper();
            callback.mSession = this;
            this.mCallback = new android.media.session.MediaSession.CallbackMessageHandler(looper, callback);
        }
    }

    public void setSessionActivity(android.app.PendingIntent pendingIntent) {
        try {
            this.mBinder.setLaunchPendingIntent(pendingIntent);
        } catch (android.os.RemoteException e) {
            android.util.Log.wtf(TAG, "Failure in setLaunchPendingIntent.", e);
        }
    }

    @java.lang.Deprecated
    public void setMediaButtonReceiver(android.app.PendingIntent pendingIntent) {
        try {
            this.mBinder.setMediaButtonReceiver(pendingIntent);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void setMediaButtonBroadcastReceiver(android.content.ComponentName componentName) {
        if (componentName != null) {
            try {
                if (!android.text.TextUtils.equals(componentName.getPackageName(), this.mContext.getPackageName())) {
                    throw new java.lang.IllegalArgumentException("broadcastReceiver should belong to the same package as the context given when creating MediaSession.");
                }
            } catch (android.os.RemoteException e) {
                e.rethrowFromSystemServer();
                return;
            }
        }
        this.mBinder.setMediaButtonBroadcastReceiver(componentName);
    }

    public void setFlags(int i) {
        try {
            this.mBinder.setFlags(i);
        } catch (android.os.RemoteException e) {
            android.util.Log.wtf(TAG, "Failure in setFlags.", e);
        }
    }

    public void setPlaybackToLocal(android.media.AudioAttributes audioAttributes) {
        if (audioAttributes == null) {
            throw new java.lang.IllegalArgumentException("Attributes cannot be null for local playback.");
        }
        try {
            this.mBinder.setPlaybackToLocal(audioAttributes);
        } catch (android.os.RemoteException e) {
            android.util.Log.wtf(TAG, "Failure in setPlaybackToLocal.", e);
        }
    }

    public void setPlaybackToRemote(android.media.VolumeProvider volumeProvider) {
        if (volumeProvider == null) {
            throw new java.lang.IllegalArgumentException("volumeProvider may not be null!");
        }
        synchronized (this.mLock) {
            this.mVolumeProvider = volumeProvider;
        }
        volumeProvider.setCallback(new android.media.VolumeProvider.Callback() { // from class: android.media.session.MediaSession.1
            @Override // android.media.VolumeProvider.Callback
            public void onVolumeChanged(android.media.VolumeProvider volumeProvider2) {
                android.media.session.MediaSession.this.notifyRemoteVolumeChanged(volumeProvider2);
            }
        });
        try {
            this.mBinder.setPlaybackToRemote(volumeProvider.getVolumeControl(), volumeProvider.getMaxVolume(), volumeProvider.getVolumeControlId());
            this.mBinder.setCurrentVolume(volumeProvider.getCurrentVolume());
        } catch (android.os.RemoteException e) {
            android.util.Log.wtf(TAG, "Failure in setPlaybackToRemote.", e);
        }
    }

    public void setActive(boolean z) {
        if (this.mActive == z) {
            return;
        }
        try {
            this.mBinder.setActive(z);
            this.mActive = z;
        } catch (android.os.RemoteException e) {
            android.util.Log.wtf(TAG, "Failure in setActive.", e);
        }
    }

    public boolean isActive() {
        return this.mActive;
    }

    public void sendSessionEvent(java.lang.String str, android.os.Bundle bundle) {
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.IllegalArgumentException("event cannot be null or empty");
        }
        try {
            this.mBinder.sendEvent(str, bundle);
        } catch (android.os.RemoteException e) {
            android.util.Log.wtf(TAG, "Error sending event", e);
        }
    }

    public void release() {
        setCallback(null);
        try {
            this.mBinder.destroySession();
        } catch (android.os.RemoteException e) {
            android.util.Log.wtf(TAG, "Error releasing session: ", e);
        }
    }

    public android.media.session.MediaSession.Token getSessionToken() {
        return this.mSessionToken;
    }

    public android.media.session.MediaController getController() {
        return this.mController;
    }

    public void setPlaybackState(android.media.session.PlaybackState playbackState) {
        this.mPlaybackState = playbackState;
        try {
            this.mBinder.setPlaybackState(playbackState);
        } catch (android.os.RemoteException e) {
            android.util.Log.wtf(TAG, "Dead object in setPlaybackState.", e);
        }
    }

    public void setMetadata(android.media.MediaMetadata mediaMetadata) {
        int i;
        android.media.MediaDescription mediaDescription;
        long j = -1;
        if (mediaMetadata == null) {
            i = 0;
            mediaDescription = null;
        } else {
            mediaMetadata = new android.media.MediaMetadata.Builder(mediaMetadata).setBitmapDimensionLimit(this.mMaxBitmapSize).build();
            if (mediaMetadata.containsKey(android.media.MediaMetadata.METADATA_KEY_DURATION)) {
                j = mediaMetadata.getLong(android.media.MediaMetadata.METADATA_KEY_DURATION);
            }
            i = mediaMetadata.size();
            mediaDescription = mediaMetadata.getDescription();
        }
        try {
            this.mBinder.setMetadata(mediaMetadata, j, "size=" + i + ", description=" + mediaDescription);
        } catch (android.os.RemoteException e) {
            android.util.Log.wtf(TAG, "Dead object in setPlaybackState.", e);
        }
    }

    public void setQueue(java.util.List<android.media.session.MediaSession.QueueItem> list) {
        try {
            if (list == null) {
                this.mBinder.resetQueue();
            } else {
                android.media.session.ParcelableListBinder.send(this.mBinder.getBinderForSetQueue(), list);
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.wtf("Dead object in setQueue.", e);
        }
    }

    public void setQueueTitle(java.lang.CharSequence charSequence) {
        try {
            this.mBinder.setQueueTitle(charSequence);
        } catch (android.os.RemoteException e) {
            android.util.Log.wtf("Dead object in setQueueTitle.", e);
        }
    }

    public void setRatingType(int i) {
        try {
            this.mBinder.setRatingType(i);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error in setRatingType.", e);
        }
    }

    public void setExtras(android.os.Bundle bundle) {
        try {
            this.mBinder.setExtras(bundle);
        } catch (android.os.RemoteException e) {
            android.util.Log.wtf("Dead object in setExtras.", e);
        }
    }

    public final android.media.session.MediaSessionManager.RemoteUserInfo getCurrentControllerInfo() {
        if (this.mCallback == null || this.mCallback.mCurrentControllerInfo == null) {
            throw new java.lang.IllegalStateException("This should be called inside of MediaSession.Callback methods");
        }
        return this.mCallback.mCurrentControllerInfo;
    }

    public void notifyRemoteVolumeChanged(android.media.VolumeProvider volumeProvider) {
        synchronized (this.mLock) {
            if (volumeProvider != null) {
                if (volumeProvider == this.mVolumeProvider) {
                    try {
                        this.mBinder.setCurrentVolume(volumeProvider.getCurrentVolume());
                        return;
                    } catch (android.os.RemoteException e) {
                        android.util.Log.e(TAG, "Error in notifyVolumeChanged", e);
                        return;
                    }
                }
            }
            android.util.Log.w(TAG, "Received update from stale volume provider");
        }
    }

    public java.lang.String getCallingPackage() {
        if (this.mCallback != null && this.mCallback.mCurrentControllerInfo != null) {
            return this.mCallback.mCurrentControllerInfo.getPackageName();
        }
        return null;
    }

    static boolean hasCustomParcelable(android.os.Bundle bundle) {
        if (bundle == null) {
            return false;
        }
        android.os.Parcel parcel = null;
        try {
            try {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeBundle(bundle);
                    obtain.setDataPosition(0);
                    android.os.Bundle readBundle = obtain.readBundle(null);
                    java.util.Iterator<java.lang.String> it = readBundle.keySet().iterator();
                    while (it.hasNext()) {
                        readBundle.get(it.next());
                    }
                    if (obtain != null) {
                        obtain.recycle();
                    }
                    return false;
                } catch (android.os.BadParcelableException e) {
                    e = e;
                    parcel = obtain;
                    android.util.Log.d(TAG, "Custom parcelable in bundle.", e);
                    if (parcel == null) {
                        return true;
                    }
                    parcel.recycle();
                    return true;
                } catch (java.lang.Throwable th) {
                    th = th;
                    parcel = obtain;
                    if (parcel != null) {
                        parcel.recycle();
                    }
                    throw th;
                }
            } catch (android.os.BadParcelableException e2) {
                e = e2;
            }
        } catch (java.lang.Throwable th2) {
            th = th2;
        }
    }

    void dispatchPrepare(android.media.session.MediaSessionManager.RemoteUserInfo remoteUserInfo) {
        postToCallback(remoteUserInfo, 3, null, null);
    }

    void dispatchPrepareFromMediaId(android.media.session.MediaSessionManager.RemoteUserInfo remoteUserInfo, java.lang.String str, android.os.Bundle bundle) {
        postToCallback(remoteUserInfo, 4, str, bundle);
    }

    void dispatchPrepareFromSearch(android.media.session.MediaSessionManager.RemoteUserInfo remoteUserInfo, java.lang.String str, android.os.Bundle bundle) {
        postToCallback(remoteUserInfo, 5, str, bundle);
    }

    void dispatchPrepareFromUri(android.media.session.MediaSessionManager.RemoteUserInfo remoteUserInfo, android.net.Uri uri, android.os.Bundle bundle) {
        postToCallback(remoteUserInfo, 6, uri, bundle);
    }

    void dispatchPlay(android.media.session.MediaSessionManager.RemoteUserInfo remoteUserInfo) {
        postToCallback(remoteUserInfo, 7, null, null);
    }

    void dispatchPlayFromMediaId(android.media.session.MediaSessionManager.RemoteUserInfo remoteUserInfo, java.lang.String str, android.os.Bundle bundle) {
        postToCallback(remoteUserInfo, 8, str, bundle);
    }

    void dispatchPlayFromSearch(android.media.session.MediaSessionManager.RemoteUserInfo remoteUserInfo, java.lang.String str, android.os.Bundle bundle) {
        postToCallback(remoteUserInfo, 9, str, bundle);
    }

    void dispatchPlayFromUri(android.media.session.MediaSessionManager.RemoteUserInfo remoteUserInfo, android.net.Uri uri, android.os.Bundle bundle) {
        postToCallback(remoteUserInfo, 10, uri, bundle);
    }

    void dispatchSkipToItem(android.media.session.MediaSessionManager.RemoteUserInfo remoteUserInfo, long j) {
        postToCallback(remoteUserInfo, 11, java.lang.Long.valueOf(j), null);
    }

    void dispatchPause(android.media.session.MediaSessionManager.RemoteUserInfo remoteUserInfo) {
        postToCallback(remoteUserInfo, 12, null, null);
    }

    void dispatchStop(android.media.session.MediaSessionManager.RemoteUserInfo remoteUserInfo) {
        postToCallback(remoteUserInfo, 13, null, null);
    }

    void dispatchNext(android.media.session.MediaSessionManager.RemoteUserInfo remoteUserInfo) {
        postToCallback(remoteUserInfo, 14, null, null);
    }

    void dispatchPrevious(android.media.session.MediaSessionManager.RemoteUserInfo remoteUserInfo) {
        postToCallback(remoteUserInfo, 15, null, null);
    }

    void dispatchFastForward(android.media.session.MediaSessionManager.RemoteUserInfo remoteUserInfo) {
        postToCallback(remoteUserInfo, 16, null, null);
    }

    void dispatchRewind(android.media.session.MediaSessionManager.RemoteUserInfo remoteUserInfo) {
        postToCallback(remoteUserInfo, 17, null, null);
    }

    void dispatchSeekTo(android.media.session.MediaSessionManager.RemoteUserInfo remoteUserInfo, long j) {
        postToCallback(remoteUserInfo, 18, java.lang.Long.valueOf(j), null);
    }

    void dispatchRate(android.media.session.MediaSessionManager.RemoteUserInfo remoteUserInfo, android.media.Rating rating) {
        postToCallback(remoteUserInfo, 19, rating, null);
    }

    void dispatchSetPlaybackSpeed(android.media.session.MediaSessionManager.RemoteUserInfo remoteUserInfo, float f) {
        postToCallback(remoteUserInfo, 20, java.lang.Float.valueOf(f), null);
    }

    void dispatchCustomAction(android.media.session.MediaSessionManager.RemoteUserInfo remoteUserInfo, java.lang.String str, android.os.Bundle bundle) {
        postToCallback(remoteUserInfo, 21, str, bundle);
    }

    void dispatchMediaButton(android.media.session.MediaSessionManager.RemoteUserInfo remoteUserInfo, android.content.Intent intent) {
        postToCallback(remoteUserInfo, 2, intent, null);
    }

    void dispatchMediaButtonDelayed(android.media.session.MediaSessionManager.RemoteUserInfo remoteUserInfo, android.content.Intent intent, long j) {
        postToCallbackDelayed(remoteUserInfo, 24, intent, null, j);
    }

    void dispatchAdjustVolume(android.media.session.MediaSessionManager.RemoteUserInfo remoteUserInfo, int i) {
        postToCallback(remoteUserInfo, 22, java.lang.Integer.valueOf(i), null);
    }

    void dispatchSetVolumeTo(android.media.session.MediaSessionManager.RemoteUserInfo remoteUserInfo, int i) {
        postToCallback(remoteUserInfo, 23, java.lang.Integer.valueOf(i), null);
    }

    void dispatchCommand(android.media.session.MediaSessionManager.RemoteUserInfo remoteUserInfo, java.lang.String str, android.os.Bundle bundle, android.os.ResultReceiver resultReceiver) {
        postToCallback(remoteUserInfo, 1, new android.media.session.MediaSession.Command(str, bundle, resultReceiver), null);
    }

    void postToCallback(android.media.session.MediaSessionManager.RemoteUserInfo remoteUserInfo, int i, java.lang.Object obj, android.os.Bundle bundle) {
        postToCallbackDelayed(remoteUserInfo, i, obj, bundle, 0L);
    }

    void postToCallbackDelayed(android.media.session.MediaSessionManager.RemoteUserInfo remoteUserInfo, int i, java.lang.Object obj, android.os.Bundle bundle, long j) {
        synchronized (this.mLock) {
            if (this.mCallback != null) {
                this.mCallback.post(remoteUserInfo, i, obj, bundle, j);
            }
        }
    }

    public static final class Token implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.media.session.MediaSession.Token> CREATOR = new android.os.Parcelable.Creator<android.media.session.MediaSession.Token>() { // from class: android.media.session.MediaSession.Token.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.media.session.MediaSession.Token createFromParcel(android.os.Parcel parcel) {
                return new android.media.session.MediaSession.Token(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.media.session.MediaSession.Token[] newArray(int i) {
                return new android.media.session.MediaSession.Token[i];
            }
        };
        private final android.media.session.ISessionController mBinder;
        private final int mUid;

        public Token(int i, android.media.session.ISessionController iSessionController) {
            this.mUid = i;
            this.mBinder = iSessionController;
        }

        Token(android.os.Parcel parcel) {
            this.mUid = parcel.readInt();
            this.mBinder = android.media.session.ISessionController.Stub.asInterface(parcel.readStrongBinder());
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mUid);
            parcel.writeStrongBinder(this.mBinder.asBinder());
        }

        public int hashCode() {
            return (this.mUid * 31) + (this.mBinder == null ? 0 : this.mBinder.asBinder().hashCode());
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            android.media.session.MediaSession.Token token = (android.media.session.MediaSession.Token) obj;
            if (this.mUid != token.mUid) {
                return false;
            }
            if (this.mBinder == null || token.mBinder == null) {
                if (this.mBinder == token.mBinder) {
                    return true;
                }
                return false;
            }
            return java.util.Objects.equals(this.mBinder.asBinder(), token.mBinder.asBinder());
        }

        @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
        public int getUid() {
            return this.mUid;
        }

        public android.media.session.ISessionController getBinder() {
            return this.mBinder;
        }
    }

    public static abstract class Callback {
        private android.media.session.MediaSession.CallbackMessageHandler mHandler;
        private boolean mMediaPlayPauseKeyPending;
        private android.media.session.MediaSession mSession;

        public void onCommand(java.lang.String str, android.os.Bundle bundle, android.os.ResultReceiver resultReceiver) {
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        public boolean onMediaButtonEvent(android.content.Intent intent) {
            android.view.KeyEvent keyEvent;
            if (this.mSession != null && this.mHandler != null && android.content.Intent.ACTION_MEDIA_BUTTON.equals(intent.getAction()) && (keyEvent = (android.view.KeyEvent) intent.getParcelableExtra(android.content.Intent.EXTRA_KEY_EVENT, android.view.KeyEvent.class)) != null && keyEvent.getAction() == 0) {
                android.media.session.PlaybackState playbackState = this.mSession.mPlaybackState;
                long actions = playbackState == null ? 0L : playbackState.getActions();
                switch (keyEvent.getKeyCode()) {
                    case 79:
                    case 85:
                        if (keyEvent.getRepeatCount() > 0) {
                            handleMediaPlayPauseKeySingleTapIfPending();
                        } else if (this.mMediaPlayPauseKeyPending) {
                            this.mHandler.removeMessages(24);
                            this.mMediaPlayPauseKeyPending = false;
                            if ((actions & 32) != 0) {
                                onSkipToNext();
                            }
                        } else {
                            this.mMediaPlayPauseKeyPending = true;
                            this.mSession.dispatchMediaButtonDelayed(this.mSession.getCurrentControllerInfo(), intent, android.view.ViewConfiguration.getDoubleTapTimeout());
                        }
                        return true;
                    default:
                        handleMediaPlayPauseKeySingleTapIfPending();
                        switch (keyEvent.getKeyCode()) {
                            case 86:
                                if ((actions & 1) != 0) {
                                    onStop();
                                    return true;
                                }
                                break;
                            case 87:
                                if ((actions & 32) != 0) {
                                    onSkipToNext();
                                    return true;
                                }
                                break;
                            case 88:
                                if ((actions & 16) != 0) {
                                    onSkipToPrevious();
                                    return true;
                                }
                                break;
                            case 89:
                                if ((actions & 8) != 0) {
                                    onRewind();
                                    return true;
                                }
                                break;
                            case 90:
                                if ((actions & 64) != 0) {
                                    onFastForward();
                                    return true;
                                }
                                break;
                            case 126:
                                if ((actions & 4) != 0) {
                                    onPlay();
                                    return true;
                                }
                                break;
                            case 127:
                                if ((actions & 2) != 0) {
                                    onPause();
                                    return true;
                                }
                                break;
                        }
                }
            }
            return false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void handleMediaPlayPauseKeySingleTapIfPending() {
            boolean z;
            boolean z2;
            if (!this.mMediaPlayPauseKeyPending) {
                return;
            }
            boolean z3 = false;
            this.mMediaPlayPauseKeyPending = false;
            this.mHandler.removeMessages(24);
            android.media.session.PlaybackState playbackState = this.mSession.mPlaybackState;
            long actions = playbackState == null ? 0L : playbackState.getActions();
            if (playbackState == null || playbackState.getState() != 3) {
                z = false;
            } else {
                z = true;
            }
            if ((516 & actions) == 0) {
                z2 = false;
            } else {
                z2 = true;
            }
            if ((actions & 514) != 0) {
                z3 = true;
            }
            if (z && z3) {
                onPause();
            } else if (!z && z2) {
                onPlay();
            }
        }

        public void onPrepare() {
        }

        public void onPrepareFromMediaId(java.lang.String str, android.os.Bundle bundle) {
        }

        public void onPrepareFromSearch(java.lang.String str, android.os.Bundle bundle) {
        }

        public void onPrepareFromUri(android.net.Uri uri, android.os.Bundle bundle) {
        }

        public void onPlay() {
        }

        public void onPlayFromSearch(java.lang.String str, android.os.Bundle bundle) {
        }

        public void onPlayFromMediaId(java.lang.String str, android.os.Bundle bundle) {
        }

        public void onPlayFromUri(android.net.Uri uri, android.os.Bundle bundle) {
        }

        public void onSkipToQueueItem(long j) {
        }

        public void onPause() {
        }

        public void onSkipToNext() {
        }

        public void onSkipToPrevious() {
        }

        public void onFastForward() {
        }

        public void onRewind() {
        }

        public void onStop() {
        }

        public void onSeekTo(long j) {
        }

        public void onSetRating(android.media.Rating rating) {
        }

        public void onSetPlaybackSpeed(float f) {
        }

        public void onCustomAction(java.lang.String str, android.os.Bundle bundle) {
        }
    }

    public static class CallbackStub extends android.media.session.ISessionCallback.Stub {
        private java.lang.ref.WeakReference<android.media.session.MediaSession> mMediaSession;

        public CallbackStub(android.media.session.MediaSession mediaSession) {
            this.mMediaSession = new java.lang.ref.WeakReference<>(mediaSession);
        }

        private static android.media.session.MediaSessionManager.RemoteUserInfo createRemoteUserInfo(java.lang.String str, int i, int i2) {
            return new android.media.session.MediaSessionManager.RemoteUserInfo(str, i, i2);
        }

        @Override // android.media.session.ISessionCallback
        public void onCommand(java.lang.String str, int i, int i2, java.lang.String str2, android.os.Bundle bundle, android.os.ResultReceiver resultReceiver) {
            android.media.session.MediaSession mediaSession = this.mMediaSession.get();
            if (mediaSession != null) {
                mediaSession.dispatchCommand(createRemoteUserInfo(str, i, i2), str2, bundle, resultReceiver);
            }
        }

        @Override // android.media.session.ISessionCallback
        public void onMediaButton(java.lang.String str, int i, int i2, android.content.Intent intent, int i3, android.os.ResultReceiver resultReceiver) {
            android.media.session.MediaSession mediaSession = this.mMediaSession.get();
            if (mediaSession != null) {
                try {
                    mediaSession.dispatchMediaButton(createRemoteUserInfo(str, i, i2), intent);
                } finally {
                    if (resultReceiver != null) {
                        resultReceiver.send(i3, null);
                    }
                }
            }
        }

        @Override // android.media.session.ISessionCallback
        public void onMediaButtonFromController(java.lang.String str, int i, int i2, android.content.Intent intent) {
            android.media.session.MediaSession mediaSession = this.mMediaSession.get();
            if (mediaSession != null) {
                mediaSession.dispatchMediaButton(createRemoteUserInfo(str, i, i2), intent);
            }
        }

        @Override // android.media.session.ISessionCallback
        public void onPrepare(java.lang.String str, int i, int i2) {
            android.media.session.MediaSession mediaSession = this.mMediaSession.get();
            if (mediaSession != null) {
                mediaSession.dispatchPrepare(createRemoteUserInfo(str, i, i2));
            }
        }

        @Override // android.media.session.ISessionCallback
        public void onPrepareFromMediaId(java.lang.String str, int i, int i2, java.lang.String str2, android.os.Bundle bundle) {
            android.media.session.MediaSession mediaSession = this.mMediaSession.get();
            if (mediaSession != null) {
                mediaSession.dispatchPrepareFromMediaId(createRemoteUserInfo(str, i, i2), str2, bundle);
            }
        }

        @Override // android.media.session.ISessionCallback
        public void onPrepareFromSearch(java.lang.String str, int i, int i2, java.lang.String str2, android.os.Bundle bundle) {
            android.media.session.MediaSession mediaSession = this.mMediaSession.get();
            if (mediaSession != null) {
                mediaSession.dispatchPrepareFromSearch(createRemoteUserInfo(str, i, i2), str2, bundle);
            }
        }

        @Override // android.media.session.ISessionCallback
        public void onPrepareFromUri(java.lang.String str, int i, int i2, android.net.Uri uri, android.os.Bundle bundle) {
            android.media.session.MediaSession mediaSession = this.mMediaSession.get();
            if (mediaSession != null) {
                mediaSession.dispatchPrepareFromUri(createRemoteUserInfo(str, i, i2), uri, bundle);
            }
        }

        @Override // android.media.session.ISessionCallback
        public void onPlay(java.lang.String str, int i, int i2) {
            android.media.session.MediaSession mediaSession = this.mMediaSession.get();
            if (mediaSession != null) {
                mediaSession.dispatchPlay(createRemoteUserInfo(str, i, i2));
            }
        }

        @Override // android.media.session.ISessionCallback
        public void onPlayFromMediaId(java.lang.String str, int i, int i2, java.lang.String str2, android.os.Bundle bundle) {
            android.media.session.MediaSession mediaSession = this.mMediaSession.get();
            if (mediaSession != null) {
                mediaSession.dispatchPlayFromMediaId(createRemoteUserInfo(str, i, i2), str2, bundle);
            }
        }

        @Override // android.media.session.ISessionCallback
        public void onPlayFromSearch(java.lang.String str, int i, int i2, java.lang.String str2, android.os.Bundle bundle) {
            android.media.session.MediaSession mediaSession = this.mMediaSession.get();
            if (mediaSession != null) {
                mediaSession.dispatchPlayFromSearch(createRemoteUserInfo(str, i, i2), str2, bundle);
            }
        }

        @Override // android.media.session.ISessionCallback
        public void onPlayFromUri(java.lang.String str, int i, int i2, android.net.Uri uri, android.os.Bundle bundle) {
            android.media.session.MediaSession mediaSession = this.mMediaSession.get();
            if (mediaSession != null) {
                mediaSession.dispatchPlayFromUri(createRemoteUserInfo(str, i, i2), uri, bundle);
            }
        }

        @Override // android.media.session.ISessionCallback
        public void onSkipToTrack(java.lang.String str, int i, int i2, long j) {
            android.media.session.MediaSession mediaSession = this.mMediaSession.get();
            if (mediaSession != null) {
                mediaSession.dispatchSkipToItem(createRemoteUserInfo(str, i, i2), j);
            }
        }

        @Override // android.media.session.ISessionCallback
        public void onPause(java.lang.String str, int i, int i2) {
            android.media.session.MediaSession mediaSession = this.mMediaSession.get();
            if (mediaSession != null) {
                mediaSession.dispatchPause(createRemoteUserInfo(str, i, i2));
            }
        }

        @Override // android.media.session.ISessionCallback
        public void onStop(java.lang.String str, int i, int i2) {
            android.media.session.MediaSession mediaSession = this.mMediaSession.get();
            if (mediaSession != null) {
                mediaSession.dispatchStop(createRemoteUserInfo(str, i, i2));
            }
        }

        @Override // android.media.session.ISessionCallback
        public void onNext(java.lang.String str, int i, int i2) {
            android.media.session.MediaSession mediaSession = this.mMediaSession.get();
            if (mediaSession != null) {
                mediaSession.dispatchNext(createRemoteUserInfo(str, i, i2));
            }
        }

        @Override // android.media.session.ISessionCallback
        public void onPrevious(java.lang.String str, int i, int i2) {
            android.media.session.MediaSession mediaSession = this.mMediaSession.get();
            if (mediaSession != null) {
                mediaSession.dispatchPrevious(createRemoteUserInfo(str, i, i2));
            }
        }

        @Override // android.media.session.ISessionCallback
        public void onFastForward(java.lang.String str, int i, int i2) {
            android.media.session.MediaSession mediaSession = this.mMediaSession.get();
            if (mediaSession != null) {
                mediaSession.dispatchFastForward(createRemoteUserInfo(str, i, i2));
            }
        }

        @Override // android.media.session.ISessionCallback
        public void onRewind(java.lang.String str, int i, int i2) {
            android.media.session.MediaSession mediaSession = this.mMediaSession.get();
            if (mediaSession != null) {
                mediaSession.dispatchRewind(createRemoteUserInfo(str, i, i2));
            }
        }

        @Override // android.media.session.ISessionCallback
        public void onSeekTo(java.lang.String str, int i, int i2, long j) {
            android.media.session.MediaSession mediaSession = this.mMediaSession.get();
            if (mediaSession != null) {
                mediaSession.dispatchSeekTo(createRemoteUserInfo(str, i, i2), j);
            }
        }

        @Override // android.media.session.ISessionCallback
        public void onRate(java.lang.String str, int i, int i2, android.media.Rating rating) {
            android.media.session.MediaSession mediaSession = this.mMediaSession.get();
            if (mediaSession != null) {
                mediaSession.dispatchRate(createRemoteUserInfo(str, i, i2), rating);
            }
        }

        @Override // android.media.session.ISessionCallback
        public void onSetPlaybackSpeed(java.lang.String str, int i, int i2, float f) {
            android.media.session.MediaSession mediaSession = this.mMediaSession.get();
            if (mediaSession != null) {
                mediaSession.dispatchSetPlaybackSpeed(createRemoteUserInfo(str, i, i2), f);
            }
        }

        @Override // android.media.session.ISessionCallback
        public void onCustomAction(java.lang.String str, int i, int i2, java.lang.String str2, android.os.Bundle bundle) {
            android.media.session.MediaSession mediaSession = this.mMediaSession.get();
            if (mediaSession != null) {
                mediaSession.dispatchCustomAction(createRemoteUserInfo(str, i, i2), str2, bundle);
            }
        }

        @Override // android.media.session.ISessionCallback
        public void onAdjustVolume(java.lang.String str, int i, int i2, int i3) {
            android.media.session.MediaSession mediaSession = this.mMediaSession.get();
            if (mediaSession != null) {
                mediaSession.dispatchAdjustVolume(createRemoteUserInfo(str, i, i2), i3);
            }
        }

        @Override // android.media.session.ISessionCallback
        public void onSetVolumeTo(java.lang.String str, int i, int i2, int i3) {
            android.media.session.MediaSession mediaSession = this.mMediaSession.get();
            if (mediaSession != null) {
                mediaSession.dispatchSetVolumeTo(createRemoteUserInfo(str, i, i2), i3);
            }
        }
    }

    public static final class QueueItem implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.media.session.MediaSession.QueueItem> CREATOR = new android.os.Parcelable.Creator<android.media.session.MediaSession.QueueItem>() { // from class: android.media.session.MediaSession.QueueItem.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.media.session.MediaSession.QueueItem createFromParcel(android.os.Parcel parcel) {
                return new android.media.session.MediaSession.QueueItem(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.media.session.MediaSession.QueueItem[] newArray(int i) {
                return new android.media.session.MediaSession.QueueItem[i];
            }
        };
        public static final int UNKNOWN_ID = -1;
        private final android.media.MediaDescription mDescription;
        private final long mId;

        public QueueItem(android.media.MediaDescription mediaDescription, long j) {
            if (mediaDescription == null) {
                throw new java.lang.IllegalArgumentException("Description cannot be null.");
            }
            if (j == -1) {
                throw new java.lang.IllegalArgumentException("Id cannot be QueueItem.UNKNOWN_ID");
            }
            this.mDescription = mediaDescription;
            this.mId = j;
        }

        private QueueItem(android.os.Parcel parcel) {
            this.mDescription = android.media.MediaDescription.CREATOR.createFromParcel(parcel);
            this.mId = parcel.readLong();
        }

        public android.media.MediaDescription getDescription() {
            return this.mDescription;
        }

        public long getQueueId() {
            return this.mId;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            this.mDescription.writeToParcel(parcel, i);
            parcel.writeLong(this.mId);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public java.lang.String toString() {
            return "MediaSession.QueueItem {Description=" + this.mDescription + ", Id=" + this.mId + " }";
        }

        public boolean equals(java.lang.Object obj) {
            if (obj == null || !(obj instanceof android.media.session.MediaSession.QueueItem)) {
                return false;
            }
            android.media.session.MediaSession.QueueItem queueItem = (android.media.session.MediaSession.QueueItem) obj;
            if (this.mId != queueItem.mId || !java.util.Objects.equals(this.mDescription, queueItem.mDescription)) {
                return false;
            }
            return true;
        }
    }

    private static final class Command {
        public final java.lang.String command;
        public final android.os.Bundle extras;
        public final android.os.ResultReceiver stub;

        Command(java.lang.String str, android.os.Bundle bundle, android.os.ResultReceiver resultReceiver) {
            this.command = str;
            this.extras = bundle;
            this.stub = resultReceiver;
        }
    }

    private class CallbackMessageHandler extends android.os.Handler {
        private static final int MSG_ADJUST_VOLUME = 22;
        private static final int MSG_COMMAND = 1;
        private static final int MSG_CUSTOM_ACTION = 21;
        private static final int MSG_FAST_FORWARD = 16;
        private static final int MSG_MEDIA_BUTTON = 2;
        private static final int MSG_NEXT = 14;
        private static final int MSG_PAUSE = 12;
        private static final int MSG_PLAY = 7;
        private static final int MSG_PLAY_MEDIA_ID = 8;
        private static final int MSG_PLAY_PAUSE_KEY_DOUBLE_TAP_TIMEOUT = 24;
        private static final int MSG_PLAY_SEARCH = 9;
        private static final int MSG_PLAY_URI = 10;
        private static final int MSG_PREPARE = 3;
        private static final int MSG_PREPARE_MEDIA_ID = 4;
        private static final int MSG_PREPARE_SEARCH = 5;
        private static final int MSG_PREPARE_URI = 6;
        private static final int MSG_PREVIOUS = 15;
        private static final int MSG_RATE = 19;
        private static final int MSG_REWIND = 17;
        private static final int MSG_SEEK_TO = 18;
        private static final int MSG_SET_PLAYBACK_SPEED = 20;
        private static final int MSG_SET_VOLUME = 23;
        private static final int MSG_SKIP_TO_ITEM = 11;
        private static final int MSG_STOP = 13;
        private android.media.session.MediaSession.Callback mCallback;
        private android.media.session.MediaSessionManager.RemoteUserInfo mCurrentControllerInfo;

        CallbackMessageHandler(android.os.Looper looper, android.media.session.MediaSession.Callback callback) {
            super(looper);
            this.mCallback = callback;
            this.mCallback.mHandler = this;
        }

        void post(android.media.session.MediaSessionManager.RemoteUserInfo remoteUserInfo, int i, java.lang.Object obj, android.os.Bundle bundle, long j) {
            android.os.Message obtainMessage = obtainMessage(i, android.util.Pair.create(remoteUserInfo, obj));
            obtainMessage.setAsynchronous(true);
            obtainMessage.setData(bundle);
            if (j > 0) {
                sendMessageDelayed(obtainMessage, j);
            } else {
                sendMessage(obtainMessage);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            android.media.VolumeProvider volumeProvider;
            android.media.VolumeProvider volumeProvider2;
            this.mCurrentControllerInfo = (android.media.session.MediaSessionManager.RemoteUserInfo) ((android.util.Pair) message.obj).first;
            S s = ((android.util.Pair) message.obj).second;
            switch (message.what) {
                case 1:
                    android.media.session.MediaSession.Command command = (android.media.session.MediaSession.Command) s;
                    this.mCallback.onCommand(command.command, command.extras, command.stub);
                    break;
                case 2:
                    this.mCallback.onMediaButtonEvent((android.content.Intent) s);
                    break;
                case 3:
                    this.mCallback.onPrepare();
                    break;
                case 4:
                    this.mCallback.onPrepareFromMediaId((java.lang.String) s, message.getData());
                    break;
                case 5:
                    this.mCallback.onPrepareFromSearch((java.lang.String) s, message.getData());
                    break;
                case 6:
                    this.mCallback.onPrepareFromUri((android.net.Uri) s, message.getData());
                    break;
                case 7:
                    this.mCallback.onPlay();
                    break;
                case 8:
                    this.mCallback.onPlayFromMediaId((java.lang.String) s, message.getData());
                    break;
                case 9:
                    this.mCallback.onPlayFromSearch((java.lang.String) s, message.getData());
                    break;
                case 10:
                    this.mCallback.onPlayFromUri((android.net.Uri) s, message.getData());
                    break;
                case 11:
                    this.mCallback.onSkipToQueueItem(((java.lang.Long) s).longValue());
                    break;
                case 12:
                    this.mCallback.onPause();
                    break;
                case 13:
                    this.mCallback.onStop();
                    break;
                case 14:
                    this.mCallback.onSkipToNext();
                    break;
                case 15:
                    this.mCallback.onSkipToPrevious();
                    break;
                case 16:
                    this.mCallback.onFastForward();
                    break;
                case 17:
                    this.mCallback.onRewind();
                    break;
                case 18:
                    this.mCallback.onSeekTo(((java.lang.Long) s).longValue());
                    break;
                case 19:
                    this.mCallback.onSetRating((android.media.Rating) s);
                    break;
                case 20:
                    this.mCallback.onSetPlaybackSpeed(((java.lang.Float) s).floatValue());
                    break;
                case 21:
                    this.mCallback.onCustomAction((java.lang.String) s, message.getData());
                    break;
                case 22:
                    synchronized (android.media.session.MediaSession.this.mLock) {
                        volumeProvider = android.media.session.MediaSession.this.mVolumeProvider;
                    }
                    if (volumeProvider != null) {
                        volumeProvider.onAdjustVolume(((java.lang.Integer) s).intValue());
                        break;
                    }
                    break;
                case 23:
                    synchronized (android.media.session.MediaSession.this.mLock) {
                        volumeProvider2 = android.media.session.MediaSession.this.mVolumeProvider;
                    }
                    if (volumeProvider2 != null) {
                        volumeProvider2.onSetVolumeTo(((java.lang.Integer) s).intValue());
                        break;
                    }
                    break;
                case 24:
                    this.mCallback.handleMediaPlayPauseKeySingleTapIfPending();
                    break;
            }
            this.mCurrentControllerInfo = null;
        }
    }
}
