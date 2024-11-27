package android.media;

/* loaded from: classes2.dex */
public final class MediaTimestamp {
    public static final android.media.MediaTimestamp TIMESTAMP_UNKNOWN = new android.media.MediaTimestamp(-1, -1, 0.0f);
    public final float clockRate;
    public final long mediaTimeUs;
    public final long nanoTime;

    public long getAnchorMediaTimeUs() {
        return this.mediaTimeUs;
    }

    @java.lang.Deprecated
    public long getAnchorSytemNanoTime() {
        return getAnchorSystemNanoTime();
    }

    public long getAnchorSystemNanoTime() {
        return this.nanoTime;
    }

    public float getMediaClockRate() {
        return this.clockRate;
    }

    public MediaTimestamp(long j, long j2, float f) {
        this.mediaTimeUs = j;
        this.nanoTime = j2;
        this.clockRate = f;
    }

    MediaTimestamp() {
        this.mediaTimeUs = 0L;
        this.nanoTime = 0L;
        this.clockRate = 1.0f;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.media.MediaTimestamp mediaTimestamp = (android.media.MediaTimestamp) obj;
        if (this.mediaTimeUs == mediaTimestamp.mediaTimeUs && this.nanoTime == mediaTimestamp.nanoTime && this.clockRate == mediaTimestamp.clockRate) {
            return true;
        }
        return false;
    }

    public java.lang.String toString() {
        return getClass().getName() + "{AnchorMediaTimeUs=" + this.mediaTimeUs + " AnchorSystemNanoTime=" + this.nanoTime + " clockRate=" + this.clockRate + "}";
    }
}
