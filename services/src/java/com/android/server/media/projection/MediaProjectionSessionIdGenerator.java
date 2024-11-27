package com.android.server.media.projection;

/* loaded from: classes2.dex */
public class MediaProjectionSessionIdGenerator {
    private static final java.lang.String PREFERENCES_FILE_NAME = "media_projection_session_id";
    private static final int SESSION_ID_DEFAULT_VALUE = 0;
    private static final java.lang.String SESSION_ID_PREF_KEY = "media_projection_session_id_key";

    @com.android.internal.annotations.GuardedBy({"sInstanceLock"})
    private static com.android.server.media.projection.MediaProjectionSessionIdGenerator sInstance;
    private static final java.lang.Object sInstanceLock = new java.lang.Object();
    private final java.lang.Object mSessionIdLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mSessionIdLock"})
    private final android.content.SharedPreferences mSharedPreferences;

    public static com.android.server.media.projection.MediaProjectionSessionIdGenerator getInstance(android.content.Context context) {
        com.android.server.media.projection.MediaProjectionSessionIdGenerator mediaProjectionSessionIdGenerator;
        synchronized (sInstanceLock) {
            try {
                if (sInstance == null) {
                    sInstance = new com.android.server.media.projection.MediaProjectionSessionIdGenerator(context.createDeviceProtectedStorageContext().getSharedPreferences(new java.io.File(android.os.Environment.getDataSystemDirectory(), PREFERENCES_FILE_NAME), 0));
                }
                mediaProjectionSessionIdGenerator = sInstance;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return mediaProjectionSessionIdGenerator;
    }

    @com.android.internal.annotations.VisibleForTesting
    public MediaProjectionSessionIdGenerator(android.content.SharedPreferences sharedPreferences) {
        this.mSharedPreferences = sharedPreferences;
    }

    public int getCurrentSessionId() {
        int currentSessionIdInternal;
        synchronized (this.mSessionIdLock) {
            currentSessionIdInternal = getCurrentSessionIdInternal();
        }
        return currentSessionIdInternal;
    }

    public int createAndGetNewSessionId() {
        int currentSessionId;
        synchronized (this.mSessionIdLock) {
            currentSessionId = getCurrentSessionId() + 1;
            setSessionIdInternal(currentSessionId);
        }
        return currentSessionId;
    }

    @com.android.internal.annotations.GuardedBy({"mSessionIdLock"})
    private void setSessionIdInternal(int i) {
        this.mSharedPreferences.edit().putInt(SESSION_ID_PREF_KEY, i).apply();
    }

    @com.android.internal.annotations.GuardedBy({"mSessionIdLock"})
    private int getCurrentSessionIdInternal() {
        return this.mSharedPreferences.getInt(SESSION_ID_PREF_KEY, 0);
    }
}
