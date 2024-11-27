package android.view;

/* loaded from: classes4.dex */
public class FrameMetricsObserver implements android.graphics.HardwareRendererObserver.OnFrameMetricsAvailableListener {
    private final android.view.FrameMetrics mFrameMetrics = new android.view.FrameMetrics();
    final android.view.Window.OnFrameMetricsAvailableListener mListener;
    private final android.graphics.HardwareRendererObserver mObserver;
    private final java.lang.ref.WeakReference<android.view.Window> mWindow;

    FrameMetricsObserver(android.view.Window window, android.os.Handler handler, android.view.Window.OnFrameMetricsAvailableListener onFrameMetricsAvailableListener) {
        this.mWindow = new java.lang.ref.WeakReference<>(window);
        this.mListener = onFrameMetricsAvailableListener;
        this.mObserver = new android.graphics.HardwareRendererObserver(this, this.mFrameMetrics.mTimingData, handler, false);
    }

    @Override // android.graphics.HardwareRendererObserver.OnFrameMetricsAvailableListener
    public void onFrameMetricsAvailable(int i) {
        if (this.mWindow.get() != null) {
            this.mListener.onFrameMetricsAvailable(this.mWindow.get(), this.mFrameMetrics, i);
        }
    }

    android.graphics.HardwareRendererObserver getRendererObserver() {
        return this.mObserver;
    }
}
