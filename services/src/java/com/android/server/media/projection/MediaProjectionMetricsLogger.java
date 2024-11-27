package com.android.server.media.projection;

/* loaded from: classes2.dex */
public class MediaProjectionMetricsLogger {
    private static final java.lang.String TAG = "MediaProjectionMetricsLogger";
    private static final int TARGET_UID_UNKNOWN = -2;
    private static final int TIME_SINCE_LAST_ACTIVE_UNKNOWN = -1;
    private static com.android.server.media.projection.MediaProjectionMetricsLogger sSingleton = null;
    private final com.android.server.media.projection.FrameworkStatsLogWrapper mFrameworkStatsLogWrapper;
    private int mPreviousState = 0;
    private final com.android.server.media.projection.MediaProjectionSessionIdGenerator mSessionIdGenerator;
    private final com.android.server.media.projection.MediaProjectionTimestampStore mTimestampStore;

    MediaProjectionMetricsLogger(com.android.server.media.projection.FrameworkStatsLogWrapper frameworkStatsLogWrapper, com.android.server.media.projection.MediaProjectionSessionIdGenerator mediaProjectionSessionIdGenerator, com.android.server.media.projection.MediaProjectionTimestampStore mediaProjectionTimestampStore) {
        this.mFrameworkStatsLogWrapper = frameworkStatsLogWrapper;
        this.mSessionIdGenerator = mediaProjectionSessionIdGenerator;
        this.mTimestampStore = mediaProjectionTimestampStore;
    }

    public static com.android.server.media.projection.MediaProjectionMetricsLogger getInstance(android.content.Context context) {
        if (sSingleton == null) {
            sSingleton = new com.android.server.media.projection.MediaProjectionMetricsLogger(new com.android.server.media.projection.FrameworkStatsLogWrapper(), com.android.server.media.projection.MediaProjectionSessionIdGenerator.getInstance(context), com.android.server.media.projection.MediaProjectionTimestampStore.getInstance(context));
        }
        return sSingleton;
    }

    public void logInitiated(int i, int i2) {
        int seconds;
        android.util.Log.d(TAG, "logInitiated");
        java.time.Duration timeSinceLastActiveSession = this.mTimestampStore.timeSinceLastActiveSession();
        if (timeSinceLastActiveSession == null) {
            seconds = -1;
        } else {
            seconds = (int) timeSinceLastActiveSession.toSeconds();
        }
        writeStateChanged(this.mSessionIdGenerator.createAndGetNewSessionId(), 1, i, -2, seconds, i2);
    }

    public void logPermissionRequestDisplayed(int i) {
        android.util.Log.d(TAG, "logPermissionRequestDisplayed");
        writeStateChanged(this.mSessionIdGenerator.getCurrentSessionId(), 2, i, -2, -1, 0);
    }

    public void logProjectionPermissionRequestCancelled(int i) {
        writeStateChanged(this.mSessionIdGenerator.getCurrentSessionId(), 8, i, -2, -1, 0);
    }

    public void logAppSelectorDisplayed(int i) {
        android.util.Log.d(TAG, "logAppSelectorDisplayed");
        writeStateChanged(this.mSessionIdGenerator.getCurrentSessionId(), 3, i, -2, -1, 0);
    }

    public void logInProgress(int i, int i2) {
        android.util.Log.d(TAG, "logInProgress");
        writeStateChanged(this.mSessionIdGenerator.getCurrentSessionId(), 4, i, i2, -1, 0);
    }

    public void logChangedWindowingMode(int i, int i2, int i3, int i4) {
        android.util.Log.d(TAG, "logChangedWindowingMode");
        writeTargetChanged(this.mSessionIdGenerator.getCurrentSessionId(), contentToRecordToTargetType(i), i2, i3, windowingModeToTargetWindowingMode(i4));
    }

    @com.android.internal.annotations.VisibleForTesting
    public int contentToRecordToTargetType(int i) {
        switch (i) {
            case 0:
                return 1;
            case 1:
                return 2;
            default:
                return 0;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public int windowingModeToTargetWindowingMode(int i) {
        switch (i) {
            case 1:
                return 2;
            case 5:
                return 4;
            case 6:
                return 3;
            default:
                return 0;
        }
    }

    public void logStopped(int i, int i2) {
        boolean z = this.mPreviousState == 4;
        android.util.Log.d(TAG, "logStopped: wasCaptureInProgress=" + z);
        writeStateChanged(this.mSessionIdGenerator.getCurrentSessionId(), 7, i, i2, -1, 0);
        if (z) {
            this.mTimestampStore.registerActiveSessionEnded();
        }
    }

    public void notifyProjectionStateChange(int i, int i2, int i3) {
        writeStateChanged(i, i2, i3);
    }

    private void writeStateChanged(int i, int i2, int i3) {
        this.mFrameworkStatsLogWrapper.writeStateChanged(com.android.internal.util.FrameworkStatsLog.MEDIA_PROJECTION_STATE_CHANGED, 123, i2, 0, i, -1, 0, i3);
    }

    private void writeStateChanged(int i, int i2, int i3, int i4, int i5, int i6) {
        this.mFrameworkStatsLogWrapper.writeStateChanged(com.android.internal.util.FrameworkStatsLog.MEDIA_PROJECTION_STATE_CHANGED, i, i2, this.mPreviousState, i3, i4, i5, i6);
        this.mPreviousState = i2;
    }

    private void writeTargetChanged(int i, int i2, int i3, int i4, int i5) {
        this.mFrameworkStatsLogWrapper.writeTargetChanged(com.android.internal.util.FrameworkStatsLog.MEDIA_PROJECTION_TARGET_CHANGED, i, i2, i3, i4, i5);
    }
}
