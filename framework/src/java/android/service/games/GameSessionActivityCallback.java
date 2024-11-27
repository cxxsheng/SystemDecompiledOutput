package android.service.games;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public interface GameSessionActivityCallback {
    void onActivityResult(int i, android.content.Intent intent);

    default void onActivityStartFailed(java.lang.Throwable th) {
    }
}
