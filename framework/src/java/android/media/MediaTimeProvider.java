package android.media;

/* loaded from: classes2.dex */
public interface MediaTimeProvider {
    public static final long NO_TIME = -1;

    public interface OnMediaTimeListener {
        void onSeek(long j);

        void onStop();

        void onTimedEvent(long j);
    }

    void cancelNotifications(android.media.MediaTimeProvider.OnMediaTimeListener onMediaTimeListener);

    long getCurrentTimeUs(boolean z, boolean z2) throws java.lang.IllegalStateException;

    void notifyAt(long j, android.media.MediaTimeProvider.OnMediaTimeListener onMediaTimeListener);

    void scheduleUpdate(android.media.MediaTimeProvider.OnMediaTimeListener onMediaTimeListener);
}
