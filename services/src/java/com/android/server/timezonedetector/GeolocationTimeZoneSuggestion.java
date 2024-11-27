package com.android.server.timezonedetector;

/* loaded from: classes2.dex */
public final class GeolocationTimeZoneSuggestion {
    private final long mEffectiveFromElapsedMillis;

    @android.annotation.Nullable
    private final java.util.List<java.lang.String> mZoneIds;

    private GeolocationTimeZoneSuggestion(long j, @android.annotation.Nullable java.util.List<java.lang.String> list) {
        this.mEffectiveFromElapsedMillis = j;
        if (list == null) {
            this.mZoneIds = null;
        } else {
            this.mZoneIds = java.util.Collections.unmodifiableList(new java.util.ArrayList(list));
        }
    }

    @android.annotation.NonNull
    public static com.android.server.timezonedetector.GeolocationTimeZoneSuggestion createUncertainSuggestion(long j) {
        return new com.android.server.timezonedetector.GeolocationTimeZoneSuggestion(j, null);
    }

    @android.annotation.NonNull
    public static com.android.server.timezonedetector.GeolocationTimeZoneSuggestion createCertainSuggestion(long j, @android.annotation.NonNull java.util.List<java.lang.String> list) {
        return new com.android.server.timezonedetector.GeolocationTimeZoneSuggestion(j, list);
    }

    public long getEffectiveFromElapsedMillis() {
        return this.mEffectiveFromElapsedMillis;
    }

    @android.annotation.Nullable
    public java.util.List<java.lang.String> getZoneIds() {
        return this.mZoneIds;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || com.android.server.timezonedetector.GeolocationTimeZoneSuggestion.class != obj.getClass()) {
            return false;
        }
        com.android.server.timezonedetector.GeolocationTimeZoneSuggestion geolocationTimeZoneSuggestion = (com.android.server.timezonedetector.GeolocationTimeZoneSuggestion) obj;
        if (this.mEffectiveFromElapsedMillis == geolocationTimeZoneSuggestion.mEffectiveFromElapsedMillis && java.util.Objects.equals(this.mZoneIds, geolocationTimeZoneSuggestion.mZoneIds)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Long.valueOf(this.mEffectiveFromElapsedMillis), this.mZoneIds);
    }

    public java.lang.String toString() {
        return "GeolocationTimeZoneSuggestion{mEffectiveFromElapsedMillis=" + this.mEffectiveFromElapsedMillis + ", mZoneIds=" + this.mZoneIds + '}';
    }
}
