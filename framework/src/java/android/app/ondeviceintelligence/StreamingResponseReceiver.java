package android.app.ondeviceintelligence;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public interface StreamingResponseReceiver<R, T, E extends java.lang.Throwable> extends android.os.OutcomeReceiver<R, E> {
    void onNewContent(T t);
}
