package com.android.server.timezonedetector.location;

/* loaded from: classes2.dex */
final class TimeZoneProviderRequest {

    @android.annotation.NonNull
    private static final com.android.server.timezonedetector.location.TimeZoneProviderRequest STOP_UPDATES = new com.android.server.timezonedetector.location.TimeZoneProviderRequest(false, null, null);

    @android.annotation.Nullable
    private final java.time.Duration mEventFilteringAgeThreshold;

    @android.annotation.Nullable
    private final java.time.Duration mInitializationTimeout;
    private final boolean mSendUpdates;

    private TimeZoneProviderRequest(boolean z, @android.annotation.Nullable java.time.Duration duration, @android.annotation.Nullable java.time.Duration duration2) {
        this.mSendUpdates = z;
        this.mInitializationTimeout = duration;
        this.mEventFilteringAgeThreshold = duration2;
    }

    public static com.android.server.timezonedetector.location.TimeZoneProviderRequest createStartUpdatesRequest(@android.annotation.NonNull java.time.Duration duration, @android.annotation.NonNull java.time.Duration duration2) {
        java.util.Objects.requireNonNull(duration);
        java.util.Objects.requireNonNull(duration2);
        return new com.android.server.timezonedetector.location.TimeZoneProviderRequest(true, duration, duration2);
    }

    public static com.android.server.timezonedetector.location.TimeZoneProviderRequest createStopUpdatesRequest() {
        return STOP_UPDATES;
    }

    public boolean sendUpdates() {
        return this.mSendUpdates;
    }

    @android.annotation.Nullable
    public java.time.Duration getInitializationTimeout() {
        return this.mInitializationTimeout;
    }

    @android.annotation.NonNull
    public java.time.Duration getEventFilteringAgeThreshold() {
        return this.mEventFilteringAgeThreshold;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || com.android.server.timezonedetector.location.TimeZoneProviderRequest.class != obj.getClass()) {
            return false;
        }
        com.android.server.timezonedetector.location.TimeZoneProviderRequest timeZoneProviderRequest = (com.android.server.timezonedetector.location.TimeZoneProviderRequest) obj;
        if (this.mSendUpdates == timeZoneProviderRequest.mSendUpdates && java.util.Objects.equals(this.mInitializationTimeout, timeZoneProviderRequest.mInitializationTimeout) && java.util.Objects.equals(this.mEventFilteringAgeThreshold, timeZoneProviderRequest.mEventFilteringAgeThreshold)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Boolean.valueOf(this.mSendUpdates), this.mInitializationTimeout, this.mEventFilteringAgeThreshold);
    }

    public java.lang.String toString() {
        return "TimeZoneProviderRequest{mSendUpdates=" + this.mSendUpdates + ", mInitializationTimeout=" + this.mInitializationTimeout + ", mEventFilteringAgeThreshold=" + this.mEventFilteringAgeThreshold + "}";
    }
}
