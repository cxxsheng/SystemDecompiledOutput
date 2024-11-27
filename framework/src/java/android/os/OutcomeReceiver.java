package android.os;

/* loaded from: classes3.dex */
public interface OutcomeReceiver<R, E extends java.lang.Throwable> {
    void onResult(R r);

    default void onError(E e) {
    }
}
