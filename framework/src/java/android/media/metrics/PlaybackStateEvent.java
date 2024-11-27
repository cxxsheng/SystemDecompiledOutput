package android.media.metrics;

/* loaded from: classes2.dex */
public final class PlaybackStateEvent extends android.media.metrics.Event implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.metrics.PlaybackStateEvent> CREATOR = new android.os.Parcelable.Creator<android.media.metrics.PlaybackStateEvent>() { // from class: android.media.metrics.PlaybackStateEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.metrics.PlaybackStateEvent[] newArray(int i) {
            return new android.media.metrics.PlaybackStateEvent[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.metrics.PlaybackStateEvent createFromParcel(android.os.Parcel parcel) {
            return new android.media.metrics.PlaybackStateEvent(parcel);
        }
    };
    public static final int STATE_ABANDONED = 15;
    public static final int STATE_BUFFERING = 6;
    public static final int STATE_ENDED = 11;
    public static final int STATE_FAILED = 13;
    public static final int STATE_INTERRUPTED_BY_AD = 14;
    public static final int STATE_JOINING_BACKGROUND = 1;
    public static final int STATE_JOINING_FOREGROUND = 2;
    public static final int STATE_NOT_STARTED = 0;
    public static final int STATE_PAUSED = 4;
    public static final int STATE_PAUSED_BUFFERING = 7;
    public static final int STATE_PLAYING = 3;
    public static final int STATE_SEEKING = 5;
    public static final int STATE_STOPPED = 12;
    public static final int STATE_SUPPRESSED = 9;
    public static final int STATE_SUPPRESSED_BUFFERING = 10;
    private final int mState;
    private final long mTimeSinceCreatedMillis;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface State {
    }

    public static java.lang.String stateToString(int i) {
        switch (i) {
            case 0:
                return "STATE_NOT_STARTED";
            case 1:
                return "STATE_JOINING_BACKGROUND";
            case 2:
                return "STATE_JOINING_FOREGROUND";
            case 3:
                return "STATE_PLAYING";
            case 4:
                return "STATE_PAUSED";
            case 5:
                return "STATE_SEEKING";
            case 6:
                return "STATE_BUFFERING";
            case 7:
                return "STATE_PAUSED_BUFFERING";
            case 8:
            default:
                return java.lang.Integer.toHexString(i);
            case 9:
                return "STATE_SUPPRESSED";
            case 10:
                return "STATE_SUPPRESSED_BUFFERING";
            case 11:
                return "STATE_ENDED";
            case 12:
                return "STATE_STOPPED";
            case 13:
                return "STATE_FAILED";
            case 14:
                return "STATE_INTERRUPTED_BY_AD";
            case 15:
                return "STATE_ABANDONED";
        }
    }

    private PlaybackStateEvent(int i, long j, android.os.Bundle bundle) {
        this.mTimeSinceCreatedMillis = j;
        this.mState = i;
        this.mMetricsBundle = bundle.deepCopy();
    }

    public int getState() {
        return this.mState;
    }

    @Override // android.media.metrics.Event
    public long getTimeSinceCreatedMillis() {
        return this.mTimeSinceCreatedMillis;
    }

    @Override // android.media.metrics.Event
    public android.os.Bundle getMetricsBundle() {
        return this.mMetricsBundle;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.media.metrics.PlaybackStateEvent playbackStateEvent = (android.media.metrics.PlaybackStateEvent) obj;
        if (this.mState == playbackStateEvent.mState && this.mTimeSinceCreatedMillis == playbackStateEvent.mTimeSinceCreatedMillis) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mState), java.lang.Long.valueOf(this.mTimeSinceCreatedMillis));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mState);
        parcel.writeLong(this.mTimeSinceCreatedMillis);
        parcel.writeBundle(this.mMetricsBundle);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private PlaybackStateEvent(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        long readLong = parcel.readLong();
        android.os.Bundle readBundle = parcel.readBundle();
        this.mState = readInt;
        this.mTimeSinceCreatedMillis = readLong;
        this.mMetricsBundle = readBundle;
    }

    public static final class Builder {
        private int mState = 0;
        private long mTimeSinceCreatedMillis = -1;
        private android.os.Bundle mMetricsBundle = new android.os.Bundle();

        public android.media.metrics.PlaybackStateEvent.Builder setState(int i) {
            this.mState = i;
            return this;
        }

        public android.media.metrics.PlaybackStateEvent.Builder setTimeSinceCreatedMillis(long j) {
            this.mTimeSinceCreatedMillis = j;
            return this;
        }

        public android.media.metrics.PlaybackStateEvent.Builder setMetricsBundle(android.os.Bundle bundle) {
            this.mMetricsBundle = bundle;
            return this;
        }

        public android.media.metrics.PlaybackStateEvent build() {
            return new android.media.metrics.PlaybackStateEvent(this.mState, this.mTimeSinceCreatedMillis, this.mMetricsBundle);
        }
    }
}
