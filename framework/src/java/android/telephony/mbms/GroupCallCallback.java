package android.telephony.mbms;

/* loaded from: classes3.dex */
public interface GroupCallCallback {
    public static final int SIGNAL_STRENGTH_UNAVAILABLE = -1;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface GroupCallError {
    }

    default void onError(int i, java.lang.String str) {
    }

    default void onGroupCallStateChanged(int i, int i2) {
    }

    default void onBroadcastSignalStrengthUpdated(int i) {
    }
}
