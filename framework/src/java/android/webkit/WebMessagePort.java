package android.webkit;

/* loaded from: classes4.dex */
public abstract class WebMessagePort {
    public abstract void close();

    public abstract void postMessage(android.webkit.WebMessage webMessage);

    public abstract void setWebMessageCallback(android.webkit.WebMessagePort.WebMessageCallback webMessageCallback);

    public abstract void setWebMessageCallback(android.webkit.WebMessagePort.WebMessageCallback webMessageCallback, android.os.Handler handler);

    public static abstract class WebMessageCallback {
        public void onMessage(android.webkit.WebMessagePort webMessagePort, android.webkit.WebMessage webMessage) {
        }
    }

    @android.annotation.SystemApi
    public WebMessagePort() {
    }
}
