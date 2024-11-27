package android.webkit;

@java.lang.Deprecated
/* loaded from: classes4.dex */
public class WebViewFragment extends android.app.Fragment {
    private boolean mIsWebViewAvailable;
    private android.webkit.WebView mWebView;

    @Override // android.app.Fragment
    public android.view.View onCreateView(android.view.LayoutInflater layoutInflater, android.view.ViewGroup viewGroup, android.os.Bundle bundle) {
        if (this.mWebView != null) {
            this.mWebView.destroy();
        }
        this.mWebView = new android.webkit.WebView(getContext());
        this.mIsWebViewAvailable = true;
        return this.mWebView;
    }

    @Override // android.app.Fragment
    public void onPause() {
        super.onPause();
        this.mWebView.onPause();
    }

    @Override // android.app.Fragment
    public void onResume() {
        this.mWebView.onResume();
        super.onResume();
    }

    @Override // android.app.Fragment
    public void onDestroyView() {
        this.mIsWebViewAvailable = false;
        super.onDestroyView();
    }

    @Override // android.app.Fragment
    public void onDestroy() {
        if (this.mWebView != null) {
            this.mWebView.destroy();
            this.mWebView = null;
        }
        super.onDestroy();
    }

    public android.webkit.WebView getWebView() {
        if (this.mIsWebViewAvailable) {
            return this.mWebView;
        }
        return null;
    }
}
