package android.webkit;

/* loaded from: classes4.dex */
public abstract class TracingController {
    public abstract boolean isTracing();

    public abstract void start(android.webkit.TracingConfig tracingConfig);

    public abstract boolean stop(java.io.OutputStream outputStream, java.util.concurrent.Executor executor);

    @java.lang.Deprecated
    public TracingController() {
    }

    public static android.webkit.TracingController getInstance() {
        return android.webkit.WebViewFactory.getProvider().getTracingController();
    }
}
