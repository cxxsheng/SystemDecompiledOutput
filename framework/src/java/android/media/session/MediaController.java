package android.media.session;

/* loaded from: classes2.dex */
public final class MediaController {
    private static final int MSG_DESTROYED = 8;
    private static final int MSG_EVENT = 1;
    private static final int MSG_UPDATE_EXTRAS = 7;
    private static final int MSG_UPDATE_METADATA = 3;
    private static final int MSG_UPDATE_PLAYBACK_STATE = 2;
    private static final int MSG_UPDATE_QUEUE = 5;
    private static final int MSG_UPDATE_QUEUE_TITLE = 6;
    private static final int MSG_UPDATE_VOLUME = 4;
    private static final java.lang.String TAG = "MediaController";
    private final android.content.Context mContext;
    private java.lang.String mPackageName;
    private final android.media.session.ISessionController mSessionBinder;
    private android.os.Bundle mSessionInfo;
    private java.lang.String mTag;
    private final android.media.session.MediaSession.Token mToken;
    private final android.media.session.MediaController.TransportControls mTransportControls;
    private final android.media.session.MediaController.CallbackStub mCbStub = new android.media.session.MediaController.CallbackStub(this);
    private final java.util.ArrayList<android.media.session.MediaController.MessageHandler> mCallbacks = new java.util.ArrayList<>();
    private final java.lang.Object mLock = new java.lang.Object();
    private boolean mCbRegistered = false;

    public MediaController(android.content.Context context, android.media.session.MediaSession.Token token) {
        if (context == null) {
            throw new java.lang.IllegalArgumentException("context shouldn't be null");
        }
        if (token == null) {
            throw new java.lang.IllegalArgumentException("token shouldn't be null");
        }
        if (token.getBinder() == null) {
            throw new java.lang.IllegalArgumentException("token.getBinder() shouldn't be null");
        }
        this.mSessionBinder = token.getBinder();
        this.mTransportControls = new android.media.session.MediaController.TransportControls();
        this.mToken = token;
        this.mContext = context;
    }

    public android.media.session.MediaController.TransportControls getTransportControls() {
        return this.mTransportControls;
    }

    public boolean dispatchMediaButtonEvent(android.view.KeyEvent keyEvent) {
        if (keyEvent == null) {
            throw new java.lang.IllegalArgumentException("KeyEvent may not be null");
        }
        if (!android.view.KeyEvent.isMediaSessionKey(keyEvent.getKeyCode())) {
            return false;
        }
        try {
            return this.mSessionBinder.sendMediaButton(this.mContext.getPackageName(), keyEvent);
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    public android.media.session.PlaybackState getPlaybackState() {
        try {
            return this.mSessionBinder.getPlaybackState();
        } catch (android.os.RemoteException e) {
            android.util.Log.wtf(TAG, "Error calling getPlaybackState.", e);
            return null;
        }
    }

    public android.media.MediaMetadata getMetadata() {
        try {
            return this.mSessionBinder.getMetadata();
        } catch (android.os.RemoteException e) {
            android.util.Log.wtf(TAG, "Error calling getMetadata.", e);
            return null;
        }
    }

    public java.util.List<android.media.session.MediaSession.QueueItem> getQueue() {
        try {
            android.content.pm.ParceledListSlice queue = this.mSessionBinder.getQueue();
            if (queue == null) {
                return null;
            }
            return queue.getList();
        } catch (android.os.RemoteException e) {
            android.util.Log.wtf(TAG, "Error calling getQueue.", e);
            return null;
        }
    }

    public java.lang.CharSequence getQueueTitle() {
        try {
            return this.mSessionBinder.getQueueTitle();
        } catch (android.os.RemoteException e) {
            android.util.Log.wtf(TAG, "Error calling getQueueTitle", e);
            return null;
        }
    }

    public android.os.Bundle getExtras() {
        try {
            return this.mSessionBinder.getExtras();
        } catch (android.os.RemoteException e) {
            android.util.Log.wtf(TAG, "Error calling getExtras", e);
            return null;
        }
    }

    public int getRatingType() {
        try {
            return this.mSessionBinder.getRatingType();
        } catch (android.os.RemoteException e) {
            android.util.Log.wtf(TAG, "Error calling getRatingType.", e);
            return 0;
        }
    }

    public long getFlags() {
        try {
            return this.mSessionBinder.getFlags();
        } catch (android.os.RemoteException e) {
            android.util.Log.wtf(TAG, "Error calling getFlags.", e);
            return 0L;
        }
    }

    public android.media.session.MediaController.PlaybackInfo getPlaybackInfo() {
        try {
            return this.mSessionBinder.getVolumeAttributes();
        } catch (android.os.RemoteException e) {
            android.util.Log.wtf(TAG, "Error calling getAudioInfo.", e);
            return null;
        }
    }

    public android.app.PendingIntent getSessionActivity() {
        try {
            return this.mSessionBinder.getLaunchPendingIntent();
        } catch (android.os.RemoteException e) {
            android.util.Log.wtf(TAG, "Error calling getPendingIntent.", e);
            return null;
        }
    }

    public android.media.session.MediaSession.Token getSessionToken() {
        return this.mToken;
    }

    public void setVolumeTo(int i, int i2) {
        try {
            this.mSessionBinder.setVolumeTo(this.mContext.getPackageName(), this.mContext.getOpPackageName(), i, i2);
        } catch (android.os.RemoteException e) {
            android.util.Log.wtf(TAG, "Error calling setVolumeTo.", e);
        }
    }

    public void adjustVolume(int i, int i2) {
        try {
            this.mSessionBinder.adjustVolume(this.mContext.getPackageName(), this.mContext.getOpPackageName(), i, i2);
        } catch (android.os.RemoteException e) {
            android.util.Log.wtf(TAG, "Error calling adjustVolumeBy.", e);
        }
    }

    public void registerCallback(android.media.session.MediaController.Callback callback) {
        registerCallback(callback, null);
    }

    public void registerCallback(android.media.session.MediaController.Callback callback, android.os.Handler handler) {
        if (callback == null) {
            throw new java.lang.IllegalArgumentException("callback must not be null");
        }
        if (handler == null) {
            handler = new android.os.Handler();
        }
        synchronized (this.mLock) {
            addCallbackLocked(callback, handler);
        }
    }

    public void unregisterCallback(android.media.session.MediaController.Callback callback) {
        if (callback == null) {
            throw new java.lang.IllegalArgumentException("callback must not be null");
        }
        synchronized (this.mLock) {
            removeCallbackLocked(callback);
        }
    }

    public void sendCommand(java.lang.String str, android.os.Bundle bundle, android.os.ResultReceiver resultReceiver) {
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.IllegalArgumentException("command cannot be null or empty");
        }
        try {
            this.mSessionBinder.sendCommand(this.mContext.getPackageName(), str, bundle, resultReceiver);
        } catch (android.os.RemoteException e) {
            android.util.Log.d(TAG, "Dead object in sendCommand.", e);
        }
    }

    public java.lang.String getPackageName() {
        if (this.mPackageName == null) {
            try {
                this.mPackageName = this.mSessionBinder.getPackageName();
            } catch (android.os.RemoteException e) {
                android.util.Log.d(TAG, "Dead object in getPackageName.", e);
            }
        }
        return this.mPackageName;
    }

    public android.os.Bundle getSessionInfo() {
        if (this.mSessionInfo != null) {
            return new android.os.Bundle(this.mSessionInfo);
        }
        try {
            this.mSessionInfo = this.mSessionBinder.getSessionInfo();
        } catch (android.os.RemoteException e) {
            android.util.Log.d(TAG, "Dead object in getSessionInfo.", e);
        }
        if (this.mSessionInfo == null) {
            android.util.Log.d(TAG, "sessionInfo is not set.");
            this.mSessionInfo = android.os.Bundle.EMPTY;
        } else if (android.media.session.MediaSession.hasCustomParcelable(this.mSessionInfo)) {
            android.util.Log.w(TAG, "sessionInfo contains custom parcelable. Ignoring.");
            this.mSessionInfo = android.os.Bundle.EMPTY;
        }
        return new android.os.Bundle(this.mSessionInfo);
    }

    public java.lang.String getTag() {
        if (this.mTag == null) {
            try {
                this.mTag = this.mSessionBinder.getTag();
            } catch (android.os.RemoteException e) {
                android.util.Log.d(TAG, "Dead object in getTag.", e);
            }
        }
        return this.mTag;
    }

    public boolean controlsSameSession(android.media.session.MediaController mediaController) {
        if (mediaController == null) {
            return false;
        }
        return this.mToken.equals(mediaController.mToken);
    }

    private void addCallbackLocked(android.media.session.MediaController.Callback callback, android.os.Handler handler) {
        if (getHandlerForCallbackLocked(callback) != null) {
            android.util.Log.w(TAG, "Callback is already added, ignoring");
            return;
        }
        android.media.session.MediaController.MessageHandler messageHandler = new android.media.session.MediaController.MessageHandler(handler.getLooper(), callback);
        this.mCallbacks.add(messageHandler);
        messageHandler.mRegistered = true;
        if (!this.mCbRegistered) {
            try {
                this.mSessionBinder.registerCallback(this.mContext.getPackageName(), this.mCbStub);
                this.mCbRegistered = true;
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Dead object in registerCallback", e);
            }
        }
    }

    private boolean removeCallbackLocked(android.media.session.MediaController.Callback callback) {
        boolean z = false;
        for (int size = this.mCallbacks.size() - 1; size >= 0; size--) {
            android.media.session.MediaController.MessageHandler messageHandler = this.mCallbacks.get(size);
            if (callback == messageHandler.mCallback) {
                this.mCallbacks.remove(size);
                messageHandler.mRegistered = false;
                z = true;
            }
        }
        if (this.mCbRegistered && this.mCallbacks.size() == 0) {
            try {
                this.mSessionBinder.unregisterCallback(this.mCbStub);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Dead object in removeCallbackLocked");
            }
            this.mCbRegistered = false;
        }
        return z;
    }

    public android.os.Handler getHandlerForCallback(android.media.session.MediaController.Callback callback) {
        android.media.session.MediaController.MessageHandler handlerForCallbackLocked;
        synchronized (this.mLock) {
            handlerForCallbackLocked = getHandlerForCallbackLocked(callback);
        }
        return handlerForCallbackLocked;
    }

    private android.media.session.MediaController.MessageHandler getHandlerForCallbackLocked(android.media.session.MediaController.Callback callback) {
        if (callback == null) {
            throw new java.lang.IllegalArgumentException("Callback cannot be null");
        }
        for (int size = this.mCallbacks.size() - 1; size >= 0; size--) {
            android.media.session.MediaController.MessageHandler messageHandler = this.mCallbacks.get(size);
            if (callback == messageHandler.mCallback) {
                return messageHandler;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postMessage(int i, java.lang.Object obj, android.os.Bundle bundle) {
        synchronized (this.mLock) {
            for (int size = this.mCallbacks.size() - 1; size >= 0; size--) {
                this.mCallbacks.get(size).post(i, obj, bundle);
            }
        }
    }

    public static abstract class Callback {
        public void onSessionDestroyed() {
        }

        public void onSessionEvent(java.lang.String str, android.os.Bundle bundle) {
        }

        public void onPlaybackStateChanged(android.media.session.PlaybackState playbackState) {
        }

        public void onMetadataChanged(android.media.MediaMetadata mediaMetadata) {
        }

        public void onQueueChanged(java.util.List<android.media.session.MediaSession.QueueItem> list) {
        }

        public void onQueueTitleChanged(java.lang.CharSequence charSequence) {
        }

        public void onExtrasChanged(android.os.Bundle bundle) {
        }

        public void onAudioInfoChanged(android.media.session.MediaController.PlaybackInfo playbackInfo) {
        }
    }

    public final class TransportControls {
        private static final java.lang.String TAG = "TransportController";

        private TransportControls() {
        }

        public void prepare() {
            try {
                android.media.session.MediaController.this.mSessionBinder.prepare(android.media.session.MediaController.this.mContext.getPackageName());
            } catch (android.os.RemoteException e) {
                android.util.Log.wtf(TAG, "Error calling prepare.", e);
            }
        }

        public void prepareFromMediaId(java.lang.String str, android.os.Bundle bundle) {
            if (android.text.TextUtils.isEmpty(str)) {
                throw new java.lang.IllegalArgumentException("You must specify a non-empty String for prepareFromMediaId.");
            }
            try {
                android.media.session.MediaController.this.mSessionBinder.prepareFromMediaId(android.media.session.MediaController.this.mContext.getPackageName(), str, bundle);
            } catch (android.os.RemoteException e) {
                android.util.Log.wtf(TAG, "Error calling prepare(" + str + ").", e);
            }
        }

        public void prepareFromSearch(java.lang.String str, android.os.Bundle bundle) {
            if (str == null) {
                str = "";
            }
            try {
                android.media.session.MediaController.this.mSessionBinder.prepareFromSearch(android.media.session.MediaController.this.mContext.getPackageName(), str, bundle);
            } catch (android.os.RemoteException e) {
                android.util.Log.wtf(TAG, "Error calling prepare(" + str + ").", e);
            }
        }

        public void prepareFromUri(android.net.Uri uri, android.os.Bundle bundle) {
            if (uri == null || android.net.Uri.EMPTY.equals(uri)) {
                throw new java.lang.IllegalArgumentException("You must specify a non-empty Uri for prepareFromUri.");
            }
            try {
                android.media.session.MediaController.this.mSessionBinder.prepareFromUri(android.media.session.MediaController.this.mContext.getPackageName(), uri, bundle);
            } catch (android.os.RemoteException e) {
                android.util.Log.wtf(TAG, "Error calling prepare(" + uri + ").", e);
            }
        }

        public void play() {
            try {
                android.media.session.MediaController.this.mSessionBinder.play(android.media.session.MediaController.this.mContext.getPackageName());
            } catch (android.os.RemoteException e) {
                android.util.Log.wtf(TAG, "Error calling play.", e);
            }
        }

        public void playFromMediaId(java.lang.String str, android.os.Bundle bundle) {
            if (android.text.TextUtils.isEmpty(str)) {
                throw new java.lang.IllegalArgumentException("You must specify a non-empty String for playFromMediaId.");
            }
            try {
                android.media.session.MediaController.this.mSessionBinder.playFromMediaId(android.media.session.MediaController.this.mContext.getPackageName(), str, bundle);
            } catch (android.os.RemoteException e) {
                android.util.Log.wtf(TAG, "Error calling play(" + str + ").", e);
            }
        }

        public void playFromSearch(java.lang.String str, android.os.Bundle bundle) {
            if (str == null) {
                str = "";
            }
            try {
                android.media.session.MediaController.this.mSessionBinder.playFromSearch(android.media.session.MediaController.this.mContext.getPackageName(), str, bundle);
            } catch (android.os.RemoteException e) {
                android.util.Log.wtf(TAG, "Error calling play(" + str + ").", e);
            }
        }

        public void playFromUri(android.net.Uri uri, android.os.Bundle bundle) {
            if (uri == null || android.net.Uri.EMPTY.equals(uri)) {
                throw new java.lang.IllegalArgumentException("You must specify a non-empty Uri for playFromUri.");
            }
            try {
                android.media.session.MediaController.this.mSessionBinder.playFromUri(android.media.session.MediaController.this.mContext.getPackageName(), uri, bundle);
            } catch (android.os.RemoteException e) {
                android.util.Log.wtf(TAG, "Error calling play(" + uri + ").", e);
            }
        }

        public void skipToQueueItem(long j) {
            try {
                android.media.session.MediaController.this.mSessionBinder.skipToQueueItem(android.media.session.MediaController.this.mContext.getPackageName(), j);
            } catch (android.os.RemoteException e) {
                android.util.Log.wtf(TAG, "Error calling skipToItem(" + j + ").", e);
            }
        }

        public void pause() {
            try {
                android.media.session.MediaController.this.mSessionBinder.pause(android.media.session.MediaController.this.mContext.getPackageName());
            } catch (android.os.RemoteException e) {
                android.util.Log.wtf(TAG, "Error calling pause.", e);
            }
        }

        public void stop() {
            try {
                android.media.session.MediaController.this.mSessionBinder.stop(android.media.session.MediaController.this.mContext.getPackageName());
            } catch (android.os.RemoteException e) {
                android.util.Log.wtf(TAG, "Error calling stop.", e);
            }
        }

        public void seekTo(long j) {
            try {
                android.media.session.MediaController.this.mSessionBinder.seekTo(android.media.session.MediaController.this.mContext.getPackageName(), j);
            } catch (android.os.RemoteException e) {
                android.util.Log.wtf(TAG, "Error calling seekTo.", e);
            }
        }

        public void fastForward() {
            try {
                android.media.session.MediaController.this.mSessionBinder.fastForward(android.media.session.MediaController.this.mContext.getPackageName());
            } catch (android.os.RemoteException e) {
                android.util.Log.wtf(TAG, "Error calling fastForward.", e);
            }
        }

        public void skipToNext() {
            try {
                android.media.session.MediaController.this.mSessionBinder.next(android.media.session.MediaController.this.mContext.getPackageName());
            } catch (android.os.RemoteException e) {
                android.util.Log.wtf(TAG, "Error calling next.", e);
            }
        }

        public void rewind() {
            try {
                android.media.session.MediaController.this.mSessionBinder.rewind(android.media.session.MediaController.this.mContext.getPackageName());
            } catch (android.os.RemoteException e) {
                android.util.Log.wtf(TAG, "Error calling rewind.", e);
            }
        }

        public void skipToPrevious() {
            try {
                android.media.session.MediaController.this.mSessionBinder.previous(android.media.session.MediaController.this.mContext.getPackageName());
            } catch (android.os.RemoteException e) {
                android.util.Log.wtf(TAG, "Error calling previous.", e);
            }
        }

        public void setRating(android.media.Rating rating) {
            try {
                android.media.session.MediaController.this.mSessionBinder.rate(android.media.session.MediaController.this.mContext.getPackageName(), rating);
            } catch (android.os.RemoteException e) {
                android.util.Log.wtf(TAG, "Error calling rate.", e);
            }
        }

        public void setPlaybackSpeed(float f) {
            if (f == 0.0f) {
                throw new java.lang.IllegalArgumentException("speed must not be zero");
            }
            try {
                android.media.session.MediaController.this.mSessionBinder.setPlaybackSpeed(android.media.session.MediaController.this.mContext.getPackageName(), f);
            } catch (android.os.RemoteException e) {
                android.util.Log.wtf(TAG, "Error calling setPlaybackSpeed.", e);
            }
        }

        public void sendCustomAction(android.media.session.PlaybackState.CustomAction customAction, android.os.Bundle bundle) {
            if (customAction == null) {
                throw new java.lang.IllegalArgumentException("CustomAction cannot be null.");
            }
            sendCustomAction(customAction.getAction(), bundle);
        }

        public void sendCustomAction(java.lang.String str, android.os.Bundle bundle) {
            if (android.text.TextUtils.isEmpty(str)) {
                throw new java.lang.IllegalArgumentException("CustomAction cannot be null.");
            }
            try {
                android.media.session.MediaController.this.mSessionBinder.sendCustomAction(android.media.session.MediaController.this.mContext.getPackageName(), str, bundle);
            } catch (android.os.RemoteException e) {
                android.util.Log.d(TAG, "Dead object in sendCustomAction.", e);
            }
        }
    }

    public static final class PlaybackInfo implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.media.session.MediaController.PlaybackInfo> CREATOR = new android.os.Parcelable.Creator<android.media.session.MediaController.PlaybackInfo>() { // from class: android.media.session.MediaController.PlaybackInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.media.session.MediaController.PlaybackInfo createFromParcel(android.os.Parcel parcel) {
                return new android.media.session.MediaController.PlaybackInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.media.session.MediaController.PlaybackInfo[] newArray(int i) {
                return new android.media.session.MediaController.PlaybackInfo[i];
            }
        };
        public static final int PLAYBACK_TYPE_LOCAL = 1;
        public static final int PLAYBACK_TYPE_REMOTE = 2;
        private final android.media.AudioAttributes mAudioAttrs;
        private final int mCurrentVolume;
        private final int mMaxVolume;
        private final int mPlaybackType;
        private final int mVolumeControl;
        private final java.lang.String mVolumeControlId;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface PlaybackType {
        }

        @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
        public PlaybackInfo(int i, int i2, int i3, int i4, android.media.AudioAttributes audioAttributes, java.lang.String str) {
            this.mPlaybackType = i;
            this.mVolumeControl = i2;
            this.mMaxVolume = i3;
            this.mCurrentVolume = i4;
            this.mAudioAttrs = audioAttributes;
            this.mVolumeControlId = str;
        }

        PlaybackInfo(android.os.Parcel parcel) {
            this.mPlaybackType = parcel.readInt();
            this.mVolumeControl = parcel.readInt();
            this.mMaxVolume = parcel.readInt();
            this.mCurrentVolume = parcel.readInt();
            this.mAudioAttrs = (android.media.AudioAttributes) parcel.readParcelable(null, android.media.AudioAttributes.class);
            this.mVolumeControlId = parcel.readString();
        }

        public int getPlaybackType() {
            return this.mPlaybackType;
        }

        public int getVolumeControl() {
            return this.mVolumeControl;
        }

        public int getMaxVolume() {
            return this.mMaxVolume;
        }

        public int getCurrentVolume() {
            return this.mCurrentVolume;
        }

        public android.media.AudioAttributes getAudioAttributes() {
            return this.mAudioAttrs;
        }

        public java.lang.String getVolumeControlId() {
            return this.mVolumeControlId;
        }

        public java.lang.String toString() {
            return "playbackType=" + this.mPlaybackType + ", volumeControlType=" + this.mVolumeControl + ", maxVolume=" + this.mMaxVolume + ", currentVolume=" + this.mCurrentVolume + ", audioAttrs=" + this.mAudioAttrs + ", volumeControlId=" + this.mVolumeControlId;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mPlaybackType);
            parcel.writeInt(this.mVolumeControl);
            parcel.writeInt(this.mMaxVolume);
            parcel.writeInt(this.mCurrentVolume);
            parcel.writeParcelable(this.mAudioAttrs, i);
            parcel.writeString(this.mVolumeControlId);
        }
    }

    private static final class CallbackStub extends android.media.session.ISessionControllerCallback.Stub {
        private final java.lang.ref.WeakReference<android.media.session.MediaController> mController;

        CallbackStub(android.media.session.MediaController mediaController) {
            this.mController = new java.lang.ref.WeakReference<>(mediaController);
        }

        @Override // android.media.session.ISessionControllerCallback
        public void onSessionDestroyed() {
            android.media.session.MediaController mediaController = this.mController.get();
            if (mediaController != null) {
                mediaController.postMessage(8, null, null);
            }
        }

        @Override // android.media.session.ISessionControllerCallback
        public void onEvent(java.lang.String str, android.os.Bundle bundle) {
            android.media.session.MediaController mediaController = this.mController.get();
            if (mediaController != null) {
                mediaController.postMessage(1, str, bundle);
            }
        }

        @Override // android.media.session.ISessionControllerCallback
        public void onPlaybackStateChanged(android.media.session.PlaybackState playbackState) {
            android.media.session.MediaController mediaController = this.mController.get();
            if (mediaController != null) {
                mediaController.postMessage(2, playbackState, null);
            }
        }

        @Override // android.media.session.ISessionControllerCallback
        public void onMetadataChanged(android.media.MediaMetadata mediaMetadata) {
            android.media.session.MediaController mediaController = this.mController.get();
            if (mediaController != null) {
                mediaController.postMessage(3, mediaMetadata, null);
            }
        }

        @Override // android.media.session.ISessionControllerCallback
        public void onQueueChanged(android.content.pm.ParceledListSlice parceledListSlice) {
            android.media.session.MediaController mediaController = this.mController.get();
            if (mediaController != null) {
                mediaController.postMessage(5, parceledListSlice, null);
            }
        }

        @Override // android.media.session.ISessionControllerCallback
        public void onQueueTitleChanged(java.lang.CharSequence charSequence) {
            android.media.session.MediaController mediaController = this.mController.get();
            if (mediaController != null) {
                mediaController.postMessage(6, charSequence, null);
            }
        }

        @Override // android.media.session.ISessionControllerCallback
        public void onExtrasChanged(android.os.Bundle bundle) {
            android.media.session.MediaController mediaController = this.mController.get();
            if (mediaController != null) {
                mediaController.postMessage(7, bundle, null);
            }
        }

        @Override // android.media.session.ISessionControllerCallback
        public void onVolumeInfoChanged(android.media.session.MediaController.PlaybackInfo playbackInfo) {
            android.media.session.MediaController mediaController = this.mController.get();
            if (mediaController != null) {
                mediaController.postMessage(4, playbackInfo, null);
            }
        }
    }

    private static final class MessageHandler extends android.os.Handler {
        private final android.media.session.MediaController.Callback mCallback;
        private boolean mRegistered;

        MessageHandler(android.os.Looper looper, android.media.session.MediaController.Callback callback) {
            super(looper);
            this.mRegistered = false;
            this.mCallback = callback;
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            if (!this.mRegistered) {
            }
            switch (message.what) {
                case 1:
                    this.mCallback.onSessionEvent((java.lang.String) message.obj, message.getData());
                    break;
                case 2:
                    this.mCallback.onPlaybackStateChanged((android.media.session.PlaybackState) message.obj);
                    break;
                case 3:
                    this.mCallback.onMetadataChanged((android.media.MediaMetadata) message.obj);
                    break;
                case 4:
                    this.mCallback.onAudioInfoChanged((android.media.session.MediaController.PlaybackInfo) message.obj);
                    break;
                case 5:
                    this.mCallback.onQueueChanged(message.obj == null ? null : ((android.content.pm.ParceledListSlice) message.obj).getList());
                    break;
                case 6:
                    this.mCallback.onQueueTitleChanged((java.lang.CharSequence) message.obj);
                    break;
                case 7:
                    this.mCallback.onExtrasChanged((android.os.Bundle) message.obj);
                    break;
                case 8:
                    this.mCallback.onSessionDestroyed();
                    break;
            }
        }

        public void post(int i, java.lang.Object obj, android.os.Bundle bundle) {
            android.os.Message obtainMessage = obtainMessage(i, obj);
            obtainMessage.setAsynchronous(true);
            obtainMessage.setData(bundle);
            obtainMessage.sendToTarget();
        }
    }
}
