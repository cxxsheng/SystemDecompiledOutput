package android.media.metrics;

/* loaded from: classes2.dex */
public abstract class Event {
    android.os.Bundle mMetricsBundle;
    final long mTimeSinceCreatedMillis;

    Event() {
        this.mMetricsBundle = new android.os.Bundle();
        this.mTimeSinceCreatedMillis = -1L;
    }

    Event(long j, android.os.Bundle bundle) {
        this.mMetricsBundle = new android.os.Bundle();
        this.mTimeSinceCreatedMillis = j;
        this.mMetricsBundle = bundle;
    }

    public long getTimeSinceCreatedMillis() {
        return this.mTimeSinceCreatedMillis;
    }

    public android.os.Bundle getMetricsBundle() {
        return this.mMetricsBundle;
    }
}
