package android.media.session;

/* loaded from: classes2.dex */
public class MediaSessionLegacyHelper {
    private static android.media.session.MediaSessionLegacyHelper sInstance;
    private android.media.MediaCommunicationManager mCommunicationManager;
    private android.content.Context mContext;
    private android.media.session.MediaSessionManager mSessionManager;
    private static final java.lang.String TAG = "MediaSessionHelper";
    private static final boolean DEBUG = android.util.Log.isLoggable(TAG, 3);
    private static final java.lang.Object sLock = new java.lang.Object();
    private android.os.Handler mHandler = new android.os.Handler(android.os.Looper.getMainLooper());
    private android.util.ArrayMap<android.app.PendingIntent, android.media.session.MediaSessionLegacyHelper.SessionHolder> mSessions = new android.util.ArrayMap<>();

    private MediaSessionLegacyHelper(android.content.Context context) {
        this.mContext = context;
        this.mSessionManager = (android.media.session.MediaSessionManager) context.getSystemService(android.content.Context.MEDIA_SESSION_SERVICE);
        this.mCommunicationManager = (android.media.MediaCommunicationManager) context.getSystemService(android.media.MediaCommunicationManager.class);
    }

    public static android.media.session.MediaSessionLegacyHelper getHelper(android.content.Context context) {
        synchronized (sLock) {
            if (sInstance == null) {
                sInstance = new android.media.session.MediaSessionLegacyHelper(context.getApplicationContext());
            }
        }
        return sInstance;
    }

    public static android.os.Bundle getOldMetadata(android.media.MediaMetadata mediaMetadata, int i, int i2) {
        boolean z = (i == -1 || i2 == -1) ? false : true;
        android.os.Bundle bundle = new android.os.Bundle();
        if (mediaMetadata.containsKey(android.media.MediaMetadata.METADATA_KEY_ALBUM)) {
            bundle.putString(java.lang.String.valueOf(1), mediaMetadata.getString(android.media.MediaMetadata.METADATA_KEY_ALBUM));
        }
        if (z && mediaMetadata.containsKey(android.media.MediaMetadata.METADATA_KEY_ART)) {
            bundle.putParcelable(java.lang.String.valueOf(100), scaleBitmapIfTooBig(mediaMetadata.getBitmap(android.media.MediaMetadata.METADATA_KEY_ART), i, i2));
        } else if (z && mediaMetadata.containsKey(android.media.MediaMetadata.METADATA_KEY_ALBUM_ART)) {
            bundle.putParcelable(java.lang.String.valueOf(100), scaleBitmapIfTooBig(mediaMetadata.getBitmap(android.media.MediaMetadata.METADATA_KEY_ALBUM_ART), i, i2));
        }
        if (mediaMetadata.containsKey(android.media.MediaMetadata.METADATA_KEY_ALBUM_ARTIST)) {
            bundle.putString(java.lang.String.valueOf(13), mediaMetadata.getString(android.media.MediaMetadata.METADATA_KEY_ALBUM_ARTIST));
        }
        if (mediaMetadata.containsKey(android.media.MediaMetadata.METADATA_KEY_ARTIST)) {
            bundle.putString(java.lang.String.valueOf(2), mediaMetadata.getString(android.media.MediaMetadata.METADATA_KEY_ARTIST));
        }
        if (mediaMetadata.containsKey(android.media.MediaMetadata.METADATA_KEY_AUTHOR)) {
            bundle.putString(java.lang.String.valueOf(3), mediaMetadata.getString(android.media.MediaMetadata.METADATA_KEY_AUTHOR));
        }
        if (mediaMetadata.containsKey(android.media.MediaMetadata.METADATA_KEY_COMPILATION)) {
            bundle.putString(java.lang.String.valueOf(15), mediaMetadata.getString(android.media.MediaMetadata.METADATA_KEY_COMPILATION));
        }
        if (mediaMetadata.containsKey(android.media.MediaMetadata.METADATA_KEY_COMPOSER)) {
            bundle.putString(java.lang.String.valueOf(4), mediaMetadata.getString(android.media.MediaMetadata.METADATA_KEY_COMPOSER));
        }
        if (mediaMetadata.containsKey(android.media.MediaMetadata.METADATA_KEY_DATE)) {
            bundle.putString(java.lang.String.valueOf(5), mediaMetadata.getString(android.media.MediaMetadata.METADATA_KEY_DATE));
        }
        if (mediaMetadata.containsKey(android.media.MediaMetadata.METADATA_KEY_DISC_NUMBER)) {
            bundle.putLong(java.lang.String.valueOf(14), mediaMetadata.getLong(android.media.MediaMetadata.METADATA_KEY_DISC_NUMBER));
        }
        if (mediaMetadata.containsKey(android.media.MediaMetadata.METADATA_KEY_DURATION)) {
            bundle.putLong(java.lang.String.valueOf(9), mediaMetadata.getLong(android.media.MediaMetadata.METADATA_KEY_DURATION));
        }
        if (mediaMetadata.containsKey(android.media.MediaMetadata.METADATA_KEY_GENRE)) {
            bundle.putString(java.lang.String.valueOf(6), mediaMetadata.getString(android.media.MediaMetadata.METADATA_KEY_GENRE));
        }
        if (mediaMetadata.containsKey(android.media.MediaMetadata.METADATA_KEY_NUM_TRACKS)) {
            bundle.putLong(java.lang.String.valueOf(10), mediaMetadata.getLong(android.media.MediaMetadata.METADATA_KEY_NUM_TRACKS));
        }
        if (mediaMetadata.containsKey(android.media.MediaMetadata.METADATA_KEY_RATING)) {
            bundle.putParcelable(java.lang.String.valueOf(101), mediaMetadata.getRating(android.media.MediaMetadata.METADATA_KEY_RATING));
        }
        if (mediaMetadata.containsKey(android.media.MediaMetadata.METADATA_KEY_USER_RATING)) {
            bundle.putParcelable(java.lang.String.valueOf(android.media.MediaMetadataEditor.RATING_KEY_BY_USER), mediaMetadata.getRating(android.media.MediaMetadata.METADATA_KEY_USER_RATING));
        }
        if (mediaMetadata.containsKey(android.media.MediaMetadata.METADATA_KEY_TITLE)) {
            bundle.putString(java.lang.String.valueOf(7), mediaMetadata.getString(android.media.MediaMetadata.METADATA_KEY_TITLE));
        }
        if (mediaMetadata.containsKey(android.media.MediaMetadata.METADATA_KEY_TRACK_NUMBER)) {
            bundle.putLong(java.lang.String.valueOf(0), mediaMetadata.getLong(android.media.MediaMetadata.METADATA_KEY_TRACK_NUMBER));
        }
        if (mediaMetadata.containsKey(android.media.MediaMetadata.METADATA_KEY_WRITER)) {
            bundle.putString(java.lang.String.valueOf(11), mediaMetadata.getString(android.media.MediaMetadata.METADATA_KEY_WRITER));
        }
        if (mediaMetadata.containsKey(android.media.MediaMetadata.METADATA_KEY_YEAR)) {
            bundle.putLong(java.lang.String.valueOf(8), mediaMetadata.getLong(android.media.MediaMetadata.METADATA_KEY_YEAR));
        }
        return bundle;
    }

    public android.media.session.MediaSession getSession(android.app.PendingIntent pendingIntent) {
        android.media.session.MediaSessionLegacyHelper.SessionHolder sessionHolder = this.mSessions.get(pendingIntent);
        if (sessionHolder == null) {
            return null;
        }
        return sessionHolder.mSession;
    }

    public void sendMediaButtonEvent(android.view.KeyEvent keyEvent, boolean z) {
        if (keyEvent == null) {
            android.util.Log.w(TAG, "Tried to send a null key event. Ignoring.");
            return;
        }
        this.mCommunicationManager.dispatchMediaKeyEvent(keyEvent, z);
        if (DEBUG) {
            android.util.Log.d(TAG, "dispatched media key " + keyEvent);
        }
    }

    public void sendVolumeKeyEvent(android.view.KeyEvent keyEvent, int i, boolean z) {
        if (keyEvent == null) {
            android.util.Log.w(TAG, "Tried to send a null key event. Ignoring.");
        } else {
            this.mSessionManager.dispatchVolumeKeyEvent(keyEvent, i, z);
        }
    }

    public void sendAdjustVolumeBy(int i, int i2, int i3) {
        this.mSessionManager.dispatchAdjustVolume(i, i2, i3);
        if (DEBUG) {
            android.util.Log.d(TAG, "dispatched volume adjustment");
        }
    }

    public boolean isGlobalPriorityActive() {
        return this.mSessionManager.isGlobalPriorityActive();
    }

    public void addRccListener(android.app.PendingIntent pendingIntent, android.media.session.MediaSession.Callback callback) {
        if (pendingIntent == null) {
            android.util.Log.w(TAG, "Pending intent was null, can't add rcc listener.");
            return;
        }
        android.media.session.MediaSessionLegacyHelper.SessionHolder holder = getHolder(pendingIntent, true);
        if (holder == null) {
            return;
        }
        if (holder.mRccListener != null && holder.mRccListener == callback) {
            if (DEBUG) {
                android.util.Log.d(TAG, "addRccListener listener already added.");
                return;
            }
            return;
        }
        holder.mRccListener = callback;
        holder.mFlags |= 2;
        holder.mSession.setFlags(holder.mFlags);
        holder.update();
        if (DEBUG) {
            android.util.Log.d(TAG, "Added rcc listener for " + pendingIntent + android.media.MediaMetrics.SEPARATOR);
        }
    }

    public void removeRccListener(android.app.PendingIntent pendingIntent) {
        android.media.session.MediaSessionLegacyHelper.SessionHolder holder;
        if (pendingIntent != null && (holder = getHolder(pendingIntent, false)) != null && holder.mRccListener != null) {
            holder.mRccListener = null;
            holder.mFlags &= -3;
            holder.mSession.setFlags(holder.mFlags);
            holder.update();
            if (DEBUG) {
                android.util.Log.d(TAG, "Removed rcc listener for " + pendingIntent + android.media.MediaMetrics.SEPARATOR);
            }
        }
    }

    public void addMediaButtonListener(android.app.PendingIntent pendingIntent, android.content.ComponentName componentName, android.content.Context context) {
        if (pendingIntent == null) {
            android.util.Log.w(TAG, "Pending intent was null, can't addMediaButtonListener.");
            return;
        }
        android.media.session.MediaSessionLegacyHelper.SessionHolder holder = getHolder(pendingIntent, true);
        if (holder == null) {
            return;
        }
        if (holder.mMediaButtonListener != null && DEBUG) {
            android.util.Log.d(TAG, "addMediaButtonListener already added " + pendingIntent);
        }
        holder.mMediaButtonListener = new android.media.session.MediaSessionLegacyHelper.MediaButtonListener(pendingIntent, context);
        holder.mFlags |= 1;
        holder.mSession.setFlags(holder.mFlags);
        holder.mSession.setMediaButtonReceiver(pendingIntent);
        holder.update();
        if (DEBUG) {
            android.util.Log.d(TAG, "addMediaButtonListener added " + pendingIntent);
        }
    }

    public void removeMediaButtonListener(android.app.PendingIntent pendingIntent) {
        android.media.session.MediaSessionLegacyHelper.SessionHolder holder;
        if (pendingIntent != null && (holder = getHolder(pendingIntent, false)) != null && holder.mMediaButtonListener != null) {
            holder.mFlags &= -2;
            holder.mSession.setFlags(holder.mFlags);
            holder.mMediaButtonListener = null;
            holder.update();
            if (DEBUG) {
                android.util.Log.d(TAG, "removeMediaButtonListener removed " + pendingIntent);
            }
        }
    }

    private static android.graphics.Bitmap scaleBitmapIfTooBig(android.graphics.Bitmap bitmap, int i, int i2) {
        if (bitmap != null) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            if (width > i || height > i2) {
                float f = width;
                float f2 = height;
                float min = java.lang.Math.min(i / f, i2 / f2);
                int round = java.lang.Math.round(f * min);
                int round2 = java.lang.Math.round(min * f2);
                android.graphics.Bitmap.Config config = bitmap.getConfig();
                if (config == null) {
                    config = android.graphics.Bitmap.Config.ARGB_8888;
                }
                android.graphics.Bitmap createBitmap = android.graphics.Bitmap.createBitmap(round, round2, config);
                android.graphics.Canvas canvas = new android.graphics.Canvas(createBitmap);
                android.graphics.Paint paint = new android.graphics.Paint();
                paint.setAntiAlias(true);
                paint.setFilterBitmap(true);
                canvas.drawBitmap(bitmap, (android.graphics.Rect) null, new android.graphics.RectF(0.0f, 0.0f, createBitmap.getWidth(), createBitmap.getHeight()), paint);
                return createBitmap;
            }
            return bitmap;
        }
        return bitmap;
    }

    private android.media.session.MediaSessionLegacyHelper.SessionHolder getHolder(android.app.PendingIntent pendingIntent, boolean z) {
        android.media.session.MediaSessionLegacyHelper.SessionHolder sessionHolder = this.mSessions.get(pendingIntent);
        if (sessionHolder == null && z) {
            android.media.session.MediaSession mediaSession = new android.media.session.MediaSession(this.mContext, "MediaSessionHelper-" + pendingIntent.getCreatorPackage());
            mediaSession.setActive(true);
            android.media.session.MediaSessionLegacyHelper.SessionHolder sessionHolder2 = new android.media.session.MediaSessionLegacyHelper.SessionHolder(mediaSession, pendingIntent);
            this.mSessions.put(pendingIntent, sessionHolder2);
            return sessionHolder2;
        }
        return sessionHolder;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void sendKeyEvent(android.app.PendingIntent pendingIntent, android.content.Context context, android.content.Intent intent) {
        try {
            pendingIntent.send(context, 0, intent);
        } catch (android.app.PendingIntent.CanceledException e) {
            android.util.Log.e(TAG, "Error sending media key down event:", e);
        }
    }

    private static final class MediaButtonListener extends android.media.session.MediaSession.Callback {
        private final android.content.Context mContext;
        private final android.app.PendingIntent mPendingIntent;

        public MediaButtonListener(android.app.PendingIntent pendingIntent, android.content.Context context) {
            this.mPendingIntent = pendingIntent;
            this.mContext = context;
        }

        @Override // android.media.session.MediaSession.Callback
        public boolean onMediaButtonEvent(android.content.Intent intent) {
            android.media.session.MediaSessionLegacyHelper.sendKeyEvent(this.mPendingIntent, this.mContext, intent);
            return true;
        }

        @Override // android.media.session.MediaSession.Callback
        public void onPlay() {
            sendKeyEvent(126);
        }

        @Override // android.media.session.MediaSession.Callback
        public void onPause() {
            sendKeyEvent(127);
        }

        @Override // android.media.session.MediaSession.Callback
        public void onSkipToNext() {
            sendKeyEvent(87);
        }

        @Override // android.media.session.MediaSession.Callback
        public void onSkipToPrevious() {
            sendKeyEvent(88);
        }

        @Override // android.media.session.MediaSession.Callback
        public void onFastForward() {
            sendKeyEvent(90);
        }

        @Override // android.media.session.MediaSession.Callback
        public void onRewind() {
            sendKeyEvent(89);
        }

        @Override // android.media.session.MediaSession.Callback
        public void onStop() {
            sendKeyEvent(86);
        }

        private void sendKeyEvent(int i) {
            android.view.KeyEvent keyEvent = new android.view.KeyEvent(0, i);
            android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_MEDIA_BUTTON);
            intent.addFlags(268435456);
            intent.putExtra(android.content.Intent.EXTRA_KEY_EVENT, keyEvent);
            android.media.session.MediaSessionLegacyHelper.sendKeyEvent(this.mPendingIntent, this.mContext, intent);
            intent.putExtra(android.content.Intent.EXTRA_KEY_EVENT, new android.view.KeyEvent(1, i));
            android.media.session.MediaSessionLegacyHelper.sendKeyEvent(this.mPendingIntent, this.mContext, intent);
            if (android.media.session.MediaSessionLegacyHelper.DEBUG) {
                android.util.Log.d(android.media.session.MediaSessionLegacyHelper.TAG, "Sent " + i + " to pending intent " + this.mPendingIntent);
            }
        }
    }

    private class SessionHolder {
        public android.media.session.MediaSessionLegacyHelper.SessionHolder.SessionCallback mCb;
        public int mFlags;
        public android.media.session.MediaSessionLegacyHelper.MediaButtonListener mMediaButtonListener;
        public final android.app.PendingIntent mPi;
        public android.media.session.MediaSession.Callback mRccListener;
        public final android.media.session.MediaSession mSession;

        public SessionHolder(android.media.session.MediaSession mediaSession, android.app.PendingIntent pendingIntent) {
            this.mSession = mediaSession;
            this.mPi = pendingIntent;
        }

        public void update() {
            if (this.mMediaButtonListener == null && this.mRccListener == null) {
                this.mSession.setCallback(null);
                this.mSession.release();
                this.mCb = null;
                android.media.session.MediaSessionLegacyHelper.this.mSessions.remove(this.mPi);
                return;
            }
            if (this.mCb == null) {
                this.mCb = new android.media.session.MediaSessionLegacyHelper.SessionHolder.SessionCallback();
                this.mSession.setCallback(this.mCb, new android.os.Handler(android.os.Looper.getMainLooper()));
            }
        }

        private class SessionCallback extends android.media.session.MediaSession.Callback {
            private SessionCallback() {
            }

            @Override // android.media.session.MediaSession.Callback
            public boolean onMediaButtonEvent(android.content.Intent intent) {
                if (android.media.session.MediaSessionLegacyHelper.SessionHolder.this.mMediaButtonListener != null) {
                    android.media.session.MediaSessionLegacyHelper.SessionHolder.this.mMediaButtonListener.onMediaButtonEvent(intent);
                    return true;
                }
                return true;
            }

            @Override // android.media.session.MediaSession.Callback
            public void onPlay() {
                if (android.media.session.MediaSessionLegacyHelper.SessionHolder.this.mMediaButtonListener != null) {
                    android.media.session.MediaSessionLegacyHelper.SessionHolder.this.mMediaButtonListener.onPlay();
                }
            }

            @Override // android.media.session.MediaSession.Callback
            public void onPause() {
                if (android.media.session.MediaSessionLegacyHelper.SessionHolder.this.mMediaButtonListener != null) {
                    android.media.session.MediaSessionLegacyHelper.SessionHolder.this.mMediaButtonListener.onPause();
                }
            }

            @Override // android.media.session.MediaSession.Callback
            public void onSkipToNext() {
                if (android.media.session.MediaSessionLegacyHelper.SessionHolder.this.mMediaButtonListener != null) {
                    android.media.session.MediaSessionLegacyHelper.SessionHolder.this.mMediaButtonListener.onSkipToNext();
                }
            }

            @Override // android.media.session.MediaSession.Callback
            public void onSkipToPrevious() {
                if (android.media.session.MediaSessionLegacyHelper.SessionHolder.this.mMediaButtonListener != null) {
                    android.media.session.MediaSessionLegacyHelper.SessionHolder.this.mMediaButtonListener.onSkipToPrevious();
                }
            }

            @Override // android.media.session.MediaSession.Callback
            public void onFastForward() {
                if (android.media.session.MediaSessionLegacyHelper.SessionHolder.this.mMediaButtonListener != null) {
                    android.media.session.MediaSessionLegacyHelper.SessionHolder.this.mMediaButtonListener.onFastForward();
                }
            }

            @Override // android.media.session.MediaSession.Callback
            public void onRewind() {
                if (android.media.session.MediaSessionLegacyHelper.SessionHolder.this.mMediaButtonListener != null) {
                    android.media.session.MediaSessionLegacyHelper.SessionHolder.this.mMediaButtonListener.onRewind();
                }
            }

            @Override // android.media.session.MediaSession.Callback
            public void onStop() {
                if (android.media.session.MediaSessionLegacyHelper.SessionHolder.this.mMediaButtonListener != null) {
                    android.media.session.MediaSessionLegacyHelper.SessionHolder.this.mMediaButtonListener.onStop();
                }
            }

            @Override // android.media.session.MediaSession.Callback
            public void onSeekTo(long j) {
                if (android.media.session.MediaSessionLegacyHelper.SessionHolder.this.mRccListener != null) {
                    android.media.session.MediaSessionLegacyHelper.SessionHolder.this.mRccListener.onSeekTo(j);
                }
            }

            @Override // android.media.session.MediaSession.Callback
            public void onSetRating(android.media.Rating rating) {
                if (android.media.session.MediaSessionLegacyHelper.SessionHolder.this.mRccListener != null) {
                    android.media.session.MediaSessionLegacyHelper.SessionHolder.this.mRccListener.onSetRating(rating);
                }
            }
        }
    }
}
