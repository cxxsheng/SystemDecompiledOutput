package android.telephony.mbms;

/* loaded from: classes3.dex */
public class StreamingServiceCallback {
    public static final int SIGNAL_STRENGTH_UNAVAILABLE = -1;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    private @interface StreamingServiceError {
    }

    public void onError(int i, java.lang.String str) {
    }

    public void onStreamStateUpdated(int i, int i2) {
    }

    public void onMediaDescriptionUpdated() {
    }

    public void onBroadcastSignalStrengthUpdated(int i) {
    }

    public void onStreamMethodUpdated(int i) {
    }
}
