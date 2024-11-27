package android.media.metrics;

/* loaded from: classes2.dex */
public final class MediaMetricsManager {
    public static final long INVALID_TIMESTAMP = -1;
    private static final java.lang.String TAG = "MediaMetricsManager";
    private android.media.metrics.IMediaMetricsManager mService;
    private int mUserId;

    public MediaMetricsManager(android.media.metrics.IMediaMetricsManager iMediaMetricsManager, int i) {
        this.mService = iMediaMetricsManager;
        this.mUserId = i;
    }

    public void reportPlaybackMetrics(java.lang.String str, android.media.metrics.PlaybackMetrics playbackMetrics) {
        try {
            this.mService.reportPlaybackMetrics(str, playbackMetrics, this.mUserId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void reportBundleMetrics(java.lang.String str, android.os.PersistableBundle persistableBundle) {
        try {
            this.mService.reportBundleMetrics(str, persistableBundle, this.mUserId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void reportNetworkEvent(java.lang.String str, android.media.metrics.NetworkEvent networkEvent) {
        try {
            this.mService.reportNetworkEvent(str, networkEvent, this.mUserId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void reportPlaybackStateEvent(java.lang.String str, android.media.metrics.PlaybackStateEvent playbackStateEvent) {
        try {
            this.mService.reportPlaybackStateEvent(str, playbackStateEvent, this.mUserId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void reportTrackChangeEvent(java.lang.String str, android.media.metrics.TrackChangeEvent trackChangeEvent) {
        try {
            this.mService.reportTrackChangeEvent(str, trackChangeEvent, this.mUserId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.media.metrics.PlaybackSession createPlaybackSession() {
        try {
            return new android.media.metrics.PlaybackSession(this.mService.getPlaybackSessionId(this.mUserId), this);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.media.metrics.RecordingSession createRecordingSession() {
        try {
            return new android.media.metrics.RecordingSession(this.mService.getRecordingSessionId(this.mUserId), this);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.media.metrics.TranscodingSession createTranscodingSession() {
        try {
            return new android.media.metrics.TranscodingSession(this.mService.getTranscodingSessionId(this.mUserId), this);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.media.metrics.EditingSession createEditingSession() {
        try {
            return new android.media.metrics.EditingSession(this.mService.getEditingSessionId(this.mUserId), this);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.media.metrics.BundleSession createBundleSession() {
        try {
            return new android.media.metrics.BundleSession(this.mService.getBundleSessionId(this.mUserId), this);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void releaseSessionId(java.lang.String str) {
        try {
            this.mService.releaseSessionId(str, this.mUserId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void reportPlaybackErrorEvent(java.lang.String str, android.media.metrics.PlaybackErrorEvent playbackErrorEvent) {
        try {
            this.mService.reportPlaybackErrorEvent(str, playbackErrorEvent, this.mUserId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void reportEditingEndedEvent(java.lang.String str, android.media.metrics.EditingEndedEvent editingEndedEvent) {
        try {
            this.mService.reportEditingEndedEvent(str, editingEndedEvent, this.mUserId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
