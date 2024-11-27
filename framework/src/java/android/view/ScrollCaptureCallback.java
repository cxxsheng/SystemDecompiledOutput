package android.view;

/* loaded from: classes4.dex */
public interface ScrollCaptureCallback {
    void onScrollCaptureEnd(java.lang.Runnable runnable);

    void onScrollCaptureImageRequest(android.view.ScrollCaptureSession scrollCaptureSession, android.os.CancellationSignal cancellationSignal, android.graphics.Rect rect, java.util.function.Consumer<android.graphics.Rect> consumer);

    void onScrollCaptureSearch(android.os.CancellationSignal cancellationSignal, java.util.function.Consumer<android.graphics.Rect> consumer);

    void onScrollCaptureStart(android.view.ScrollCaptureSession scrollCaptureSession, android.os.CancellationSignal cancellationSignal, java.lang.Runnable runnable);
}
