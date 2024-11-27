package android.media;

@java.lang.Deprecated
/* loaded from: classes2.dex */
public class RemoteControlClient {
    private static final boolean DEBUG = false;
    public static final int DEFAULT_PLAYBACK_VOLUME = 15;
    public static final int DEFAULT_PLAYBACK_VOLUME_HANDLING = 1;
    public static final int FLAGS_KEY_MEDIA_NONE = 0;
    public static final int FLAG_INFORMATION_REQUEST_ALBUM_ART = 8;
    public static final int FLAG_INFORMATION_REQUEST_KEY_MEDIA = 2;
    public static final int FLAG_INFORMATION_REQUEST_METADATA = 1;
    public static final int FLAG_INFORMATION_REQUEST_PLAYSTATE = 4;
    public static final int FLAG_KEY_MEDIA_FAST_FORWARD = 64;
    public static final int FLAG_KEY_MEDIA_NEXT = 128;
    public static final int FLAG_KEY_MEDIA_PAUSE = 16;
    public static final int FLAG_KEY_MEDIA_PLAY = 4;
    public static final int FLAG_KEY_MEDIA_PLAY_PAUSE = 8;
    public static final int FLAG_KEY_MEDIA_POSITION_UPDATE = 256;
    public static final int FLAG_KEY_MEDIA_PREVIOUS = 1;
    public static final int FLAG_KEY_MEDIA_RATING = 512;
    public static final int FLAG_KEY_MEDIA_REWIND = 2;
    public static final int FLAG_KEY_MEDIA_STOP = 32;
    public static int MEDIA_POSITION_READABLE = 1;
    public static int MEDIA_POSITION_WRITABLE = 2;
    public static final int PLAYBACKINFO_INVALID_VALUE = Integer.MIN_VALUE;
    public static final int PLAYBACKINFO_PLAYBACK_TYPE = 1;
    public static final int PLAYBACKINFO_USES_STREAM = 5;
    public static final int PLAYBACKINFO_VOLUME = 2;
    public static final int PLAYBACKINFO_VOLUME_HANDLING = 4;
    public static final int PLAYBACKINFO_VOLUME_MAX = 3;
    public static final long PLAYBACK_POSITION_ALWAYS_UNKNOWN = -9216204211029966080L;
    public static final long PLAYBACK_POSITION_INVALID = -1;
    public static final float PLAYBACK_SPEED_1X = 1.0f;
    public static final int PLAYBACK_TYPE_LOCAL = 0;
    private static final int PLAYBACK_TYPE_MAX = 1;
    private static final int PLAYBACK_TYPE_MIN = 0;
    public static final int PLAYBACK_TYPE_REMOTE = 1;
    public static final int PLAYBACK_VOLUME_FIXED = 0;
    public static final int PLAYBACK_VOLUME_VARIABLE = 1;
    public static final int PLAYSTATE_BUFFERING = 8;
    public static final int PLAYSTATE_ERROR = 9;
    public static final int PLAYSTATE_FAST_FORWARDING = 4;
    public static final int PLAYSTATE_NONE = 0;
    public static final int PLAYSTATE_PAUSED = 2;
    public static final int PLAYSTATE_PLAYING = 3;
    public static final int PLAYSTATE_REWINDING = 5;
    public static final int PLAYSTATE_SKIPPING_BACKWARDS = 7;
    public static final int PLAYSTATE_SKIPPING_FORWARDS = 6;
    public static final int PLAYSTATE_STOPPED = 1;
    private static final long POSITION_DRIFT_MAX_MS = 500;
    private static final long POSITION_REFRESH_PERIOD_MIN_MS = 2000;
    private static final long POSITION_REFRESH_PERIOD_PLAYING_MS = 15000;
    public static final int RCSE_ID_UNREGISTERED = -1;
    private static final java.lang.String TAG = "RemoteControlClient";
    private android.media.MediaMetadata mMediaMetadata;
    private android.media.RemoteControlClient.OnMetadataUpdateListener mMetadataUpdateListener;
    private android.graphics.Bitmap mOriginalArtwork;
    private android.media.RemoteControlClient.OnGetPlaybackPositionListener mPositionProvider;
    private android.media.RemoteControlClient.OnPlaybackPositionUpdateListener mPositionUpdateListener;
    private final android.app.PendingIntent mRcMediaIntent;
    private android.media.session.MediaSession mSession;
    private final java.lang.Object mCacheLock = new java.lang.Object();
    private int mPlaybackState = 0;
    private long mPlaybackStateChangeTimeMs = 0;
    private long mPlaybackPositionMs = -1;
    private float mPlaybackSpeed = 1.0f;
    private int mTransportControlFlags = 0;
    private android.os.Bundle mMetadata = new android.os.Bundle();
    private int mCurrentClientGenId = -1;
    private boolean mNeedsPositionSync = false;
    private android.media.session.PlaybackState mSessionPlaybackState = null;
    private android.media.session.MediaSession.Callback mTransportListener = new android.media.session.MediaSession.Callback() { // from class: android.media.RemoteControlClient.1
        @Override // android.media.session.MediaSession.Callback
        public void onSeekTo(long j) {
            android.media.RemoteControlClient.this.onSeekTo(android.media.RemoteControlClient.this.mCurrentClientGenId, j);
        }

        @Override // android.media.session.MediaSession.Callback
        public void onSetRating(android.media.Rating rating) {
            if ((android.media.RemoteControlClient.this.mTransportControlFlags & 512) != 0) {
                android.media.RemoteControlClient.this.onUpdateMetadata(android.media.RemoteControlClient.this.mCurrentClientGenId, android.media.MediaMetadataEditor.RATING_KEY_BY_USER, rating);
            }
        }
    };

    public interface OnGetPlaybackPositionListener {
        long onGetPlaybackPosition();
    }

    public interface OnMetadataUpdateListener {
        void onMetadataUpdate(int i, java.lang.Object obj);
    }

    public interface OnPlaybackPositionUpdateListener {
        void onPlaybackPositionUpdate(long j);
    }

    public RemoteControlClient(android.app.PendingIntent pendingIntent) {
        this.mRcMediaIntent = pendingIntent;
    }

    public RemoteControlClient(android.app.PendingIntent pendingIntent, android.os.Looper looper) {
        this.mRcMediaIntent = pendingIntent;
    }

    public void registerWithSession(android.media.session.MediaSessionLegacyHelper mediaSessionLegacyHelper) {
        mediaSessionLegacyHelper.addRccListener(this.mRcMediaIntent, this.mTransportListener);
        this.mSession = mediaSessionLegacyHelper.getSession(this.mRcMediaIntent);
        setTransportControlFlags(this.mTransportControlFlags);
    }

    public void unregisterWithSession(android.media.session.MediaSessionLegacyHelper mediaSessionLegacyHelper) {
        mediaSessionLegacyHelper.removeRccListener(this.mRcMediaIntent);
        this.mSession = null;
    }

    public android.media.session.MediaSession getMediaSession() {
        return this.mSession;
    }

    @java.lang.Deprecated
    public class MetadataEditor extends android.media.MediaMetadataEditor {
        public static final int BITMAP_KEY_ARTWORK = 100;
        public static final int METADATA_KEY_ARTWORK = 100;

        private MetadataEditor() {
        }

        public java.lang.Object clone() throws java.lang.CloneNotSupportedException {
            throw new java.lang.CloneNotSupportedException();
        }

        @Override // android.media.MediaMetadataEditor
        public synchronized android.media.RemoteControlClient.MetadataEditor putString(int i, java.lang.String str) throws java.lang.IllegalArgumentException {
            java.lang.String keyFromMetadataEditorKey;
            super.putString(i, str);
            if (this.mMetadataBuilder != null && (keyFromMetadataEditorKey = android.media.MediaMetadata.getKeyFromMetadataEditorKey(i)) != null) {
                this.mMetadataBuilder.putText(keyFromMetadataEditorKey, str);
            }
            return this;
        }

        @Override // android.media.MediaMetadataEditor
        public synchronized android.media.RemoteControlClient.MetadataEditor putLong(int i, long j) throws java.lang.IllegalArgumentException {
            java.lang.String keyFromMetadataEditorKey;
            super.putLong(i, j);
            if (this.mMetadataBuilder != null && (keyFromMetadataEditorKey = android.media.MediaMetadata.getKeyFromMetadataEditorKey(i)) != null) {
                this.mMetadataBuilder.putLong(keyFromMetadataEditorKey, j);
            }
            return this;
        }

        @Override // android.media.MediaMetadataEditor
        public synchronized android.media.RemoteControlClient.MetadataEditor putBitmap(int i, android.graphics.Bitmap bitmap) throws java.lang.IllegalArgumentException {
            java.lang.String keyFromMetadataEditorKey;
            super.putBitmap(i, bitmap);
            if (this.mMetadataBuilder != null && (keyFromMetadataEditorKey = android.media.MediaMetadata.getKeyFromMetadataEditorKey(i)) != null) {
                this.mMetadataBuilder.putBitmap(keyFromMetadataEditorKey, bitmap);
            }
            return this;
        }

        @Override // android.media.MediaMetadataEditor
        public synchronized android.media.RemoteControlClient.MetadataEditor putObject(int i, java.lang.Object obj) throws java.lang.IllegalArgumentException {
            java.lang.String keyFromMetadataEditorKey;
            super.putObject(i, obj);
            if (this.mMetadataBuilder != null && ((i == 268435457 || i == 101) && (keyFromMetadataEditorKey = android.media.MediaMetadata.getKeyFromMetadataEditorKey(i)) != null)) {
                this.mMetadataBuilder.putRating(keyFromMetadataEditorKey, (android.media.Rating) obj);
            }
            return this;
        }

        @Override // android.media.MediaMetadataEditor
        public synchronized void clear() {
            super.clear();
        }

        @Override // android.media.MediaMetadataEditor
        public synchronized void apply() {
            if (this.mApplied) {
                android.util.Log.e(android.media.RemoteControlClient.TAG, "Can't apply a previously applied MetadataEditor");
                return;
            }
            synchronized (android.media.RemoteControlClient.this.mCacheLock) {
                android.media.RemoteControlClient.this.mMetadata = new android.os.Bundle(this.mEditorMetadata);
                android.media.RemoteControlClient.this.mMetadata.putLong(java.lang.String.valueOf(android.media.MediaMetadataEditor.KEY_EDITABLE_MASK), this.mEditableKeys);
                if (android.media.RemoteControlClient.this.mOriginalArtwork != null && !android.media.RemoteControlClient.this.mOriginalArtwork.equals(this.mEditorArtwork)) {
                    android.media.RemoteControlClient.this.mOriginalArtwork.recycle();
                }
                android.media.RemoteControlClient.this.mOriginalArtwork = this.mEditorArtwork;
                this.mEditorArtwork = null;
                if (android.media.RemoteControlClient.this.mSession != null && this.mMetadataBuilder != null) {
                    android.media.RemoteControlClient.this.mMediaMetadata = this.mMetadataBuilder.build();
                    android.media.RemoteControlClient.this.mSession.setMetadata(android.media.RemoteControlClient.this.mMediaMetadata);
                }
                this.mApplied = true;
            }
        }
    }

    public android.media.RemoteControlClient.MetadataEditor editMetadata(boolean z) {
        android.media.RemoteControlClient.MetadataEditor metadataEditor = new android.media.RemoteControlClient.MetadataEditor();
        if (z) {
            metadataEditor.mEditorMetadata = new android.os.Bundle();
            metadataEditor.mEditorArtwork = null;
            metadataEditor.mMetadataChanged = true;
            metadataEditor.mArtworkChanged = true;
            metadataEditor.mEditableKeys = 0L;
        } else {
            metadataEditor.mEditorMetadata = new android.os.Bundle(this.mMetadata);
            metadataEditor.mEditorArtwork = this.mOriginalArtwork;
            metadataEditor.mMetadataChanged = false;
            metadataEditor.mArtworkChanged = false;
        }
        if (z || this.mMediaMetadata == null) {
            metadataEditor.mMetadataBuilder = new android.media.MediaMetadata.Builder();
        } else {
            metadataEditor.mMetadataBuilder = new android.media.MediaMetadata.Builder(this.mMediaMetadata);
        }
        return metadataEditor;
    }

    public void setPlaybackState(int i) {
        setPlaybackStateInt(i, PLAYBACK_POSITION_ALWAYS_UNKNOWN, 1.0f, false);
    }

    public void setPlaybackState(int i, long j, float f) {
        setPlaybackStateInt(i, j, f, true);
    }

    private void setPlaybackStateInt(int i, long j, float f, boolean z) {
        synchronized (this.mCacheLock) {
            if (this.mPlaybackState != i || this.mPlaybackPositionMs != j || this.mPlaybackSpeed != f) {
                this.mPlaybackState = i;
                if (!z) {
                    this.mPlaybackPositionMs = PLAYBACK_POSITION_ALWAYS_UNKNOWN;
                } else if (j < 0) {
                    this.mPlaybackPositionMs = -1L;
                } else {
                    this.mPlaybackPositionMs = j;
                }
                this.mPlaybackSpeed = f;
                this.mPlaybackStateChangeTimeMs = android.os.SystemClock.elapsedRealtime();
                if (this.mSession != null) {
                    int stateFromRccState = getStateFromRccState(i);
                    long j2 = z ? this.mPlaybackPositionMs : -1L;
                    android.media.session.PlaybackState.Builder builder = new android.media.session.PlaybackState.Builder(this.mSessionPlaybackState);
                    builder.setState(stateFromRccState, j2, f, android.os.SystemClock.elapsedRealtime());
                    builder.setErrorMessage(null);
                    this.mSessionPlaybackState = builder.build();
                    this.mSession.setPlaybackState(this.mSessionPlaybackState);
                }
            }
        }
    }

    public void setTransportControlFlags(int i) {
        synchronized (this.mCacheLock) {
            this.mTransportControlFlags = i;
            if (this.mSession != null) {
                android.media.session.PlaybackState.Builder builder = new android.media.session.PlaybackState.Builder(this.mSessionPlaybackState);
                builder.setActions(getActionsFromRccControlFlags(i));
                this.mSessionPlaybackState = builder.build();
                this.mSession.setPlaybackState(this.mSessionPlaybackState);
            }
        }
    }

    public void setMetadataUpdateListener(android.media.RemoteControlClient.OnMetadataUpdateListener onMetadataUpdateListener) {
        synchronized (this.mCacheLock) {
            this.mMetadataUpdateListener = onMetadataUpdateListener;
        }
    }

    public void setPlaybackPositionUpdateListener(android.media.RemoteControlClient.OnPlaybackPositionUpdateListener onPlaybackPositionUpdateListener) {
        synchronized (this.mCacheLock) {
            this.mPositionUpdateListener = onPlaybackPositionUpdateListener;
        }
    }

    public void setOnGetPlaybackPositionListener(android.media.RemoteControlClient.OnGetPlaybackPositionListener onGetPlaybackPositionListener) {
        synchronized (this.mCacheLock) {
            this.mPositionProvider = onGetPlaybackPositionListener;
        }
    }

    public android.app.PendingIntent getRcMediaIntent() {
        return this.mRcMediaIntent;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSeekTo(int i, long j) {
        synchronized (this.mCacheLock) {
            if (this.mCurrentClientGenId == i && this.mPositionUpdateListener != null) {
                this.mPositionUpdateListener.onPlaybackPositionUpdate(j);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUpdateMetadata(int i, int i2, java.lang.Object obj) {
        synchronized (this.mCacheLock) {
            if (this.mCurrentClientGenId == i && this.mMetadataUpdateListener != null) {
                this.mMetadataUpdateListener.onMetadataUpdate(i2, obj);
            }
        }
    }

    static boolean playbackPositionShouldMove(int i) {
        switch (i) {
            case 1:
            case 2:
            case 6:
            case 7:
            case 8:
            case 9:
                return false;
            case 3:
            case 4:
            case 5:
            default:
                return true;
        }
    }

    private static long getCheckPeriodFromSpeed(float f) {
        if (java.lang.Math.abs(f) <= 1.0f) {
            return POSITION_REFRESH_PERIOD_PLAYING_MS;
        }
        return java.lang.Math.max((long) (15000.0f / java.lang.Math.abs(f)), POSITION_REFRESH_PERIOD_MIN_MS);
    }

    private static int getStateFromRccState(int i) {
        switch (i) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
            case 5:
                return 5;
            case 6:
                return 10;
            case 7:
                return 9;
            case 8:
                return 6;
            case 9:
                return 7;
            default:
                return -1;
        }
    }

    static int getRccStateFromState(int i) {
        switch (i) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
            case 5:
                return 5;
            case 6:
                return 8;
            case 7:
                return 9;
            case 8:
            default:
                return -1;
            case 9:
                return 7;
            case 10:
                return 6;
        }
    }

    private static long getActionsFromRccControlFlags(int i) {
        long j = 1;
        long j2 = 0;
        while (true) {
            long j3 = i;
            if (j <= j3) {
                if ((j3 & j) != 0) {
                    j2 |= getActionForRccFlag((int) j);
                }
                j <<= 1;
            } else {
                return j2;
            }
        }
    }

    static int getRccControlFlagsFromActions(long j) {
        int i = 0;
        for (long j2 = 1; j2 <= j && j2 < 2147483647L; j2 <<= 1) {
            if ((j2 & j) != 0) {
                i |= getRccFlagForAction(j2);
            }
        }
        return i;
    }

    private static long getActionForRccFlag(int i) {
        switch (i) {
            case 1:
                return 16L;
            case 2:
                return 8L;
            case 4:
                return 4L;
            case 8:
                return 512L;
            case 16:
                return 2L;
            case 32:
                return 1L;
            case 64:
                return 64L;
            case 128:
                return 32L;
            case 256:
                return 256L;
            case 512:
                return 128L;
            default:
                return 0L;
        }
    }

    private static int getRccFlagForAction(long j) {
        switch (j < 2147483647L ? (int) j : 0) {
            case 1:
                return 32;
            case 2:
                return 16;
            case 4:
                return 4;
            case 8:
                return 2;
            case 16:
                return 1;
            case 32:
                return 128;
            case 64:
                return 64;
            case 128:
                return 512;
            case 256:
                return 256;
            case 512:
                return 8;
            default:
                return 0;
        }
    }
}
