package android.app;

/* loaded from: classes.dex */
public interface AnrController {
    long getAnrDelayMillis(java.lang.String str, int i);

    boolean onAnrDelayCompleted(java.lang.String str, int i);

    void onAnrDelayStarted(java.lang.String str, int i);
}
