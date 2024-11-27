package android.telephony.mbms;

/* loaded from: classes3.dex */
public interface MbmsGroupCallSessionCallback {

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface GroupCallError {
    }

    default void onError(int i, java.lang.String str) {
    }

    default void onAvailableSaisUpdated(java.util.List<java.lang.Integer> list, java.util.List<java.util.List<java.lang.Integer>> list2) {
    }

    default void onServiceInterfaceAvailable(java.lang.String str, int i) {
    }

    default void onMiddlewareReady() {
    }
}
