package android.webkit;

/* loaded from: classes4.dex */
public class JsResult {
    private final android.webkit.JsResult.ResultReceiver mReceiver;
    private boolean mResult;

    @android.annotation.SystemApi
    public interface ResultReceiver {
        void onJsResultComplete(android.webkit.JsResult jsResult);
    }

    public final void cancel() {
        this.mResult = false;
        wakeUp();
    }

    public final void confirm() {
        this.mResult = true;
        wakeUp();
    }

    @android.annotation.SystemApi
    public JsResult(android.webkit.JsResult.ResultReceiver resultReceiver) {
        this.mReceiver = resultReceiver;
    }

    @android.annotation.SystemApi
    public final boolean getResult() {
        return this.mResult;
    }

    private final void wakeUp() {
        this.mReceiver.onJsResultComplete(this);
    }
}
