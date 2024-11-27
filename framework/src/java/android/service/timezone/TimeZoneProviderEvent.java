package android.service.timezone;

/* loaded from: classes3.dex */
public final class TimeZoneProviderEvent implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.timezone.TimeZoneProviderEvent> CREATOR = new android.os.Parcelable.Creator<android.service.timezone.TimeZoneProviderEvent>() { // from class: android.service.timezone.TimeZoneProviderEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.timezone.TimeZoneProviderEvent createFromParcel(android.os.Parcel parcel) {
            return new android.service.timezone.TimeZoneProviderEvent(parcel.readInt(), parcel.readLong(), (android.service.timezone.TimeZoneProviderSuggestion) parcel.readParcelable(getClass().getClassLoader(), android.service.timezone.TimeZoneProviderSuggestion.class), parcel.readString8(), (android.service.timezone.TimeZoneProviderStatus) parcel.readParcelable(getClass().getClassLoader(), android.service.timezone.TimeZoneProviderStatus.class));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.timezone.TimeZoneProviderEvent[] newArray(int i) {
            return new android.service.timezone.TimeZoneProviderEvent[i];
        }
    };
    public static final int EVENT_TYPE_PERMANENT_FAILURE = 1;
    public static final int EVENT_TYPE_SUGGESTION = 2;
    public static final int EVENT_TYPE_UNCERTAIN = 3;
    private final long mCreationElapsedMillis;
    private final java.lang.String mFailureCause;
    private final android.service.timezone.TimeZoneProviderSuggestion mSuggestion;
    private final android.service.timezone.TimeZoneProviderStatus mTimeZoneProviderStatus;
    private final int mType;

    @java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE_USE, java.lang.annotation.ElementType.TYPE_PARAMETER})
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface EventType {
    }

    private TimeZoneProviderEvent(int i, long j, android.service.timezone.TimeZoneProviderSuggestion timeZoneProviderSuggestion, java.lang.String str, android.service.timezone.TimeZoneProviderStatus timeZoneProviderStatus) {
        this.mType = validateEventType(i);
        this.mCreationElapsedMillis = j;
        this.mSuggestion = timeZoneProviderSuggestion;
        this.mFailureCause = str;
        this.mTimeZoneProviderStatus = timeZoneProviderStatus;
        if (this.mType == 1 && this.mTimeZoneProviderStatus != null) {
            throw new java.lang.IllegalArgumentException("Unexpected status: mType=" + this.mType + ", mTimeZoneProviderStatus=" + this.mTimeZoneProviderStatus);
        }
    }

    private static int validateEventType(int i) {
        if (i < 1 || i > 3) {
            throw new java.lang.IllegalArgumentException(java.lang.Integer.toString(i));
        }
        return i;
    }

    public static android.service.timezone.TimeZoneProviderEvent createSuggestionEvent(long j, android.service.timezone.TimeZoneProviderSuggestion timeZoneProviderSuggestion, android.service.timezone.TimeZoneProviderStatus timeZoneProviderStatus) {
        return new android.service.timezone.TimeZoneProviderEvent(2, j, (android.service.timezone.TimeZoneProviderSuggestion) java.util.Objects.requireNonNull(timeZoneProviderSuggestion), null, timeZoneProviderStatus);
    }

    public static android.service.timezone.TimeZoneProviderEvent createUncertainEvent(long j, android.service.timezone.TimeZoneProviderStatus timeZoneProviderStatus) {
        return new android.service.timezone.TimeZoneProviderEvent(3, j, null, null, timeZoneProviderStatus);
    }

    public static android.service.timezone.TimeZoneProviderEvent createPermanentFailureEvent(long j, java.lang.String str) {
        return new android.service.timezone.TimeZoneProviderEvent(1, j, null, (java.lang.String) java.util.Objects.requireNonNull(str), null);
    }

    public int getType() {
        return this.mType;
    }

    public long getCreationElapsedMillis() {
        return this.mCreationElapsedMillis;
    }

    public android.service.timezone.TimeZoneProviderSuggestion getSuggestion() {
        return this.mSuggestion;
    }

    public java.lang.String getFailureCause() {
        return this.mFailureCause;
    }

    public android.service.timezone.TimeZoneProviderStatus getTimeZoneProviderStatus() {
        return this.mTimeZoneProviderStatus;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mType);
        parcel.writeLong(this.mCreationElapsedMillis);
        parcel.writeParcelable(this.mSuggestion, 0);
        parcel.writeString8(this.mFailureCause);
        parcel.writeParcelable(this.mTimeZoneProviderStatus, 0);
    }

    public java.lang.String toString() {
        return "TimeZoneProviderEvent{mType=" + this.mType + ", mCreationElapsedMillis=" + java.time.Duration.ofMillis(this.mCreationElapsedMillis).toString() + ", mSuggestion=" + this.mSuggestion + ", mFailureCause=" + this.mFailureCause + ", mTimeZoneProviderStatus=" + this.mTimeZoneProviderStatus + '}';
    }

    public boolean isEquivalentTo(android.service.timezone.TimeZoneProviderEvent timeZoneProviderEvent) {
        if (this == timeZoneProviderEvent) {
            return true;
        }
        if (timeZoneProviderEvent == null || this.mType != timeZoneProviderEvent.mType) {
            return false;
        }
        if (this.mType == 2) {
            if (this.mSuggestion.isEquivalentTo(timeZoneProviderEvent.mSuggestion) && java.util.Objects.equals(this.mTimeZoneProviderStatus, timeZoneProviderEvent.mTimeZoneProviderStatus)) {
                return true;
            }
            return false;
        }
        return java.util.Objects.equals(this.mTimeZoneProviderStatus, timeZoneProviderEvent.mTimeZoneProviderStatus);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.service.timezone.TimeZoneProviderEvent timeZoneProviderEvent = (android.service.timezone.TimeZoneProviderEvent) obj;
        if (this.mType == timeZoneProviderEvent.mType && this.mCreationElapsedMillis == timeZoneProviderEvent.mCreationElapsedMillis && java.util.Objects.equals(this.mSuggestion, timeZoneProviderEvent.mSuggestion) && java.util.Objects.equals(this.mFailureCause, timeZoneProviderEvent.mFailureCause) && java.util.Objects.equals(this.mTimeZoneProviderStatus, timeZoneProviderEvent.mTimeZoneProviderStatus)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mType), java.lang.Long.valueOf(this.mCreationElapsedMillis), this.mSuggestion, this.mFailureCause, this.mTimeZoneProviderStatus);
    }
}
