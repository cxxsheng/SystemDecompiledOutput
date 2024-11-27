package android.webkit;

@android.annotation.SystemApi
/* loaded from: classes4.dex */
public class FindActionModeCallback implements android.view.ActionMode.Callback, android.text.TextWatcher, android.view.View.OnClickListener, android.webkit.WebView.FindListener {
    private android.view.ActionMode mActionMode;
    private int mActiveMatchIndex;
    private android.view.View mCustomView;
    private android.widget.EditText mEditText;
    private android.view.inputmethod.InputMethodManager mInput;
    private android.widget.TextView mMatches;
    private boolean mMatchesFound;
    private int mNumberOfMatches;
    private android.content.res.Resources mResources;
    private android.webkit.WebView mWebView;
    private android.graphics.Rect mGlobalVisibleRect = new android.graphics.Rect();
    private android.graphics.Point mGlobalVisibleOffset = new android.graphics.Point();

    public FindActionModeCallback(android.content.Context context) {
        this.mCustomView = android.view.LayoutInflater.from(context).inflate(com.android.internal.R.layout.webview_find, (android.view.ViewGroup) null);
        this.mEditText = (android.widget.EditText) this.mCustomView.findViewById(16908291);
        this.mEditText.setCustomSelectionActionModeCallback(new android.webkit.FindActionModeCallback.NoAction());
        this.mEditText.setOnClickListener(this);
        setText("");
        this.mMatches = (android.widget.TextView) this.mCustomView.findViewById(com.android.internal.R.id.matches);
        this.mInput = (android.view.inputmethod.InputMethodManager) context.getSystemService(android.view.inputmethod.InputMethodManager.class);
        this.mResources = context.getResources();
    }

    public void finish() {
        this.mActionMode.finish();
    }

    public void setText(java.lang.String str) {
        this.mEditText.lambda$setTextAsync$0(str);
        android.text.Editable text = this.mEditText.getText();
        int length = text.length();
        android.text.Selection.setSelection(text, length, length);
        text.setSpan(this, 0, length, 18);
        this.mMatchesFound = false;
    }

    public void setWebView(android.webkit.WebView webView) {
        if (webView == null) {
            throw new java.lang.AssertionError("WebView supplied to FindActionModeCallback cannot be null");
        }
        this.mWebView = webView;
        this.mWebView.setFindDialogFindListener(this);
    }

    @Override // android.webkit.WebView.FindListener
    public void onFindResultReceived(int i, int i2, boolean z) {
        if (z) {
            updateMatchCount(i, i2, i2 == 0);
        }
    }

    private void findNext(boolean z) {
        if (this.mWebView == null) {
            throw new java.lang.AssertionError("No WebView for FindActionModeCallback::findNext");
        }
        if (!this.mMatchesFound) {
            findAll();
        } else {
            if (this.mNumberOfMatches == 0) {
                return;
            }
            this.mWebView.findNext(z);
            updateMatchesString();
        }
    }

    public void findAll() {
        if (this.mWebView == null) {
            throw new java.lang.AssertionError("No WebView for FindActionModeCallback::findAll");
        }
        android.text.Editable text = this.mEditText.getText();
        if (text.length() == 0) {
            this.mWebView.clearMatches();
            this.mMatches.setVisibility(8);
            this.mMatchesFound = false;
            this.mWebView.findAll(null);
            return;
        }
        this.mMatchesFound = true;
        this.mMatches.setVisibility(4);
        this.mNumberOfMatches = 0;
        this.mWebView.findAllAsync(text.toString());
    }

    public void showSoftInput() {
        if (this.mEditText.requestFocus()) {
            this.mInput.showSoftInput(this.mEditText, 0);
        }
    }

    public void updateMatchCount(int i, int i2, boolean z) {
        if (!z) {
            this.mNumberOfMatches = i2;
            this.mActiveMatchIndex = i;
            updateMatchesString();
        } else {
            this.mMatches.setVisibility(8);
            this.mNumberOfMatches = 0;
        }
    }

    private void updateMatchesString() {
        if (this.mNumberOfMatches == 0) {
            this.mMatches.setText(com.android.internal.R.string.no_matches);
        } else {
            java.util.HashMap hashMap = new java.util.HashMap();
            hashMap.put("count", java.lang.Integer.valueOf(this.mActiveMatchIndex + 1));
            hashMap.put("total", java.lang.Integer.valueOf(this.mNumberOfMatches));
            this.mMatches.lambda$setTextAsync$0(android.util.PluralsMessageFormatter.format(this.mResources, hashMap, com.android.internal.R.string.matches_found));
        }
        this.mMatches.setVisibility(0);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(android.view.View view) {
        findNext(true);
    }

    @Override // android.view.ActionMode.Callback
    public boolean onCreateActionMode(android.view.ActionMode actionMode, android.view.Menu menu) {
        if (!actionMode.isUiFocusable()) {
            return false;
        }
        actionMode.setCustomView(this.mCustomView);
        actionMode.getMenuInflater().inflate(com.android.internal.R.menu.webview_find, menu);
        this.mActionMode = actionMode;
        android.text.Editable text = this.mEditText.getText();
        android.text.Selection.setSelection(text, text.length());
        this.mMatches.setVisibility(8);
        this.mMatchesFound = false;
        this.mMatches.lambda$setTextAsync$0(android.media.AudioSystem.LEGACY_REMOTE_SUBMIX_ADDRESS);
        this.mEditText.requestFocus();
        return true;
    }

    @Override // android.view.ActionMode.Callback
    public void onDestroyActionMode(android.view.ActionMode actionMode) {
        this.mActionMode = null;
        this.mWebView.notifyFindDialogDismissed();
        this.mWebView.setFindDialogFindListener(null);
        this.mInput.hideSoftInputFromWindow(this.mWebView.getWindowToken(), 0);
    }

    @Override // android.view.ActionMode.Callback
    public boolean onPrepareActionMode(android.view.ActionMode actionMode, android.view.Menu menu) {
        return false;
    }

    @Override // android.view.ActionMode.Callback
    public boolean onActionItemClicked(android.view.ActionMode actionMode, android.view.MenuItem menuItem) {
        if (this.mWebView == null) {
            throw new java.lang.AssertionError("No WebView for FindActionModeCallback::onActionItemClicked");
        }
        this.mInput.hideSoftInputFromWindow(this.mWebView.getWindowToken(), 0);
        switch (menuItem.getItemId()) {
            case com.android.internal.R.id.find_next /* 16909029 */:
                findNext(true);
                return true;
            case com.android.internal.R.id.find_prev /* 16909030 */:
                findNext(false);
                return true;
            default:
                return false;
        }
    }

    @Override // android.text.TextWatcher
    public void beforeTextChanged(java.lang.CharSequence charSequence, int i, int i2, int i3) {
    }

    @Override // android.text.TextWatcher
    public void onTextChanged(java.lang.CharSequence charSequence, int i, int i2, int i3) {
        findAll();
    }

    @Override // android.text.TextWatcher
    public void afterTextChanged(android.text.Editable editable) {
    }

    public int getActionModeGlobalBottom() {
        if (this.mActionMode == null) {
            return 0;
        }
        android.view.View view = (android.view.View) this.mCustomView.getParent();
        if (view == null) {
            view = this.mCustomView;
        }
        view.getGlobalVisibleRect(this.mGlobalVisibleRect, this.mGlobalVisibleOffset);
        return this.mGlobalVisibleRect.bottom;
    }

    public static class NoAction implements android.view.ActionMode.Callback {
        @Override // android.view.ActionMode.Callback
        public boolean onCreateActionMode(android.view.ActionMode actionMode, android.view.Menu menu) {
            return false;
        }

        @Override // android.view.ActionMode.Callback
        public boolean onPrepareActionMode(android.view.ActionMode actionMode, android.view.Menu menu) {
            return false;
        }

        @Override // android.view.ActionMode.Callback
        public boolean onActionItemClicked(android.view.ActionMode actionMode, android.view.MenuItem menuItem) {
            return false;
        }

        @Override // android.view.ActionMode.Callback
        public void onDestroyActionMode(android.view.ActionMode actionMode) {
        }
    }
}
