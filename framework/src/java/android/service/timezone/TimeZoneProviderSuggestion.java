package android.service.timezone;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class TimeZoneProviderSuggestion implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.timezone.TimeZoneProviderSuggestion> CREATOR = new android.os.Parcelable.Creator<android.service.timezone.TimeZoneProviderSuggestion>() { // from class: android.service.timezone.TimeZoneProviderSuggestion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.timezone.TimeZoneProviderSuggestion createFromParcel(android.os.Parcel parcel) {
            return new android.service.timezone.TimeZoneProviderSuggestion(parcel.readArrayList(null, java.lang.String.class), parcel.readLong());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.timezone.TimeZoneProviderSuggestion[] newArray(int i) {
            return new android.service.timezone.TimeZoneProviderSuggestion[i];
        }
    };
    private final long mElapsedRealtimeMillis;
    private final java.util.List<java.lang.String> mTimeZoneIds;

    private TimeZoneProviderSuggestion(java.util.List<java.lang.String> list, long j) {
        this.mTimeZoneIds = immutableList(list);
        this.mElapsedRealtimeMillis = j;
    }

    public long getElapsedRealtimeMillis() {
        return this.mElapsedRealtimeMillis;
    }

    public java.util.List<java.lang.String> getTimeZoneIds() {
        return this.mTimeZoneIds;
    }

    public java.lang.String toString() {
        return "TimeZoneProviderSuggestion{mTimeZoneIds=" + this.mTimeZoneIds + ", mElapsedRealtimeMillis=" + this.mElapsedRealtimeMillis + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START + java.time.Duration.ofMillis(this.mElapsedRealtimeMillis) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END + '}';
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeList(this.mTimeZoneIds);
        parcel.writeLong(this.mElapsedRealtimeMillis);
    }

    public boolean isEquivalentTo(android.service.timezone.TimeZoneProviderSuggestion timeZoneProviderSuggestion) {
        if (this == timeZoneProviderSuggestion) {
            return true;
        }
        if (timeZoneProviderSuggestion == null) {
            return false;
        }
        return this.mTimeZoneIds.equals(timeZoneProviderSuggestion.mTimeZoneIds);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.service.timezone.TimeZoneProviderSuggestion timeZoneProviderSuggestion = (android.service.timezone.TimeZoneProviderSuggestion) obj;
        if (this.mElapsedRealtimeMillis == timeZoneProviderSuggestion.mElapsedRealtimeMillis && this.mTimeZoneIds.equals(timeZoneProviderSuggestion.mTimeZoneIds)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mTimeZoneIds, java.lang.Long.valueOf(this.mElapsedRealtimeMillis));
    }

    public static final class Builder {
        private java.util.List<java.lang.String> mTimeZoneIds = java.util.Collections.emptyList();
        private long mElapsedRealtimeMillis = android.os.SystemClock.elapsedRealtime();

        public android.service.timezone.TimeZoneProviderSuggestion.Builder setTimeZoneIds(java.util.List<java.lang.String> list) {
            this.mTimeZoneIds = (java.util.List) java.util.Objects.requireNonNull(list);
            return this;
        }

        public android.service.timezone.TimeZoneProviderSuggestion.Builder setElapsedRealtimeMillis(long j) {
            this.mElapsedRealtimeMillis = j;
            return this;
        }

        public android.service.timezone.TimeZoneProviderSuggestion build() {
            return new android.service.timezone.TimeZoneProviderSuggestion(this.mTimeZoneIds, this.mElapsedRealtimeMillis);
        }
    }

    private static java.util.List<java.lang.String> immutableList(java.util.List<java.lang.String> list) {
        java.util.Objects.requireNonNull(list);
        if (list.isEmpty()) {
            return java.util.Collections.emptyList();
        }
        return java.util.Collections.unmodifiableList(new java.util.ArrayList(list));
    }
}
