package com.android.server.media.projection;

/* loaded from: classes2.dex */
public class MediaProjectionTimestampStore {
    private static final java.lang.String PREFERENCES_FILE_NAME = "media_projection_timestamp";
    private static final java.lang.String TIMESTAMP_PREF_KEY = "media_projection_timestamp_key";

    @com.android.internal.annotations.GuardedBy({"sInstanceLock"})
    private static com.android.server.media.projection.MediaProjectionTimestampStore sInstance;
    private static final java.lang.Object sInstanceLock = new java.lang.Object();
    private final java.time.InstantSource mInstantSource;

    @com.android.internal.annotations.GuardedBy({"mTimestampLock"})
    private final android.content.SharedPreferences mSharedPreferences;
    private final java.lang.Object mTimestampLock = new java.lang.Object();

    @com.android.internal.annotations.VisibleForTesting
    public MediaProjectionTimestampStore(android.content.SharedPreferences sharedPreferences, java.time.InstantSource instantSource) {
        this.mSharedPreferences = sharedPreferences;
        this.mInstantSource = instantSource;
    }

    public static com.android.server.media.projection.MediaProjectionTimestampStore getInstance(android.content.Context context) {
        com.android.server.media.projection.MediaProjectionTimestampStore mediaProjectionTimestampStore;
        synchronized (sInstanceLock) {
            try {
                if (sInstance == null) {
                    sInstance = new com.android.server.media.projection.MediaProjectionTimestampStore(context.createDeviceProtectedStorageContext().getSharedPreferences(new java.io.File(android.os.Environment.getDataSystemDirectory(), PREFERENCES_FILE_NAME), 0), java.time.InstantSource.system());
                }
                mediaProjectionTimestampStore = sInstance;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return mediaProjectionTimestampStore;
    }

    @android.annotation.Nullable
    public java.time.Duration timeSinceLastActiveSession() {
        synchronized (this.mTimestampLock) {
            try {
                java.time.Instant lastActiveSessionTimestamp = getLastActiveSessionTimestamp();
                if (lastActiveSessionTimestamp == null) {
                    return null;
                }
                return java.time.Duration.between(lastActiveSessionTimestamp, this.mInstantSource.instant());
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void registerActiveSessionEnded() {
        synchronized (this.mTimestampLock) {
            setLastActiveSessionTimestamp(this.mInstantSource.instant());
        }
    }

    @com.android.internal.annotations.GuardedBy({"mTimestampLock"})
    @android.annotation.Nullable
    private java.time.Instant getLastActiveSessionTimestamp() {
        long j = this.mSharedPreferences.getLong(TIMESTAMP_PREF_KEY, -1L);
        if (j == -1) {
            return null;
        }
        return java.time.Instant.ofEpochMilli(j);
    }

    @com.android.internal.annotations.GuardedBy({"mTimestampLock"})
    private void setLastActiveSessionTimestamp(@android.annotation.NonNull java.time.Instant instant) {
        this.mSharedPreferences.edit().putLong(TIMESTAMP_PREF_KEY, instant.toEpochMilli()).apply();
    }
}
