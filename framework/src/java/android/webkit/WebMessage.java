package android.webkit;

/* loaded from: classes4.dex */
public class WebMessage {
    private java.lang.String mData;
    private android.webkit.WebMessagePort[] mPorts;

    public WebMessage(java.lang.String str) {
        this.mData = str;
    }

    public WebMessage(java.lang.String str, android.webkit.WebMessagePort[] webMessagePortArr) {
        this.mData = str;
        this.mPorts = webMessagePortArr;
    }

    public java.lang.String getData() {
        return this.mData;
    }

    public android.webkit.WebMessagePort[] getPorts() {
        return this.mPorts;
    }
}
