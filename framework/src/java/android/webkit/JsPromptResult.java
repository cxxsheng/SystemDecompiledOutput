package android.webkit;

/* loaded from: classes4.dex */
public class JsPromptResult extends android.webkit.JsResult {
    private java.lang.String mStringResult;

    public void confirm(java.lang.String str) {
        this.mStringResult = str;
        confirm();
    }

    @android.annotation.SystemApi
    public JsPromptResult(android.webkit.JsResult.ResultReceiver resultReceiver) {
        super(resultReceiver);
    }

    @android.annotation.SystemApi
    public java.lang.String getStringResult() {
        return this.mStringResult;
    }
}
