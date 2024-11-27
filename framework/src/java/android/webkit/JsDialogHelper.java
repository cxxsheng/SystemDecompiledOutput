package android.webkit;

@android.annotation.SystemApi
/* loaded from: classes4.dex */
public class JsDialogHelper {
    public static final int ALERT = 1;
    public static final int CONFIRM = 2;
    public static final int PROMPT = 3;
    private static final java.lang.String TAG = "JsDialogHelper";
    public static final int UNLOAD = 4;
    private final java.lang.String mDefaultValue;
    private final java.lang.String mMessage;
    private final android.webkit.JsPromptResult mResult;
    private final int mType;
    private final java.lang.String mUrl;

    public JsDialogHelper(android.webkit.JsPromptResult jsPromptResult, int i, java.lang.String str, java.lang.String str2, java.lang.String str3) {
        this.mResult = jsPromptResult;
        this.mDefaultValue = str;
        this.mMessage = str2;
        this.mType = i;
        this.mUrl = str3;
    }

    public JsDialogHelper(android.webkit.JsPromptResult jsPromptResult, android.os.Message message) {
        this.mResult = jsPromptResult;
        this.mDefaultValue = message.getData().getString("default");
        this.mMessage = message.getData().getString(android.app.slice.Slice.SUBTYPE_MESSAGE);
        this.mType = message.getData().getInt("type");
        this.mUrl = message.getData().getString("url");
    }

    public boolean invokeCallback(android.webkit.WebChromeClient webChromeClient, android.webkit.WebView webView) {
        switch (this.mType) {
            case 1:
                return webChromeClient.onJsAlert(webView, this.mUrl, this.mMessage, this.mResult);
            case 2:
                return webChromeClient.onJsConfirm(webView, this.mUrl, this.mMessage, this.mResult);
            case 3:
                return webChromeClient.onJsPrompt(webView, this.mUrl, this.mMessage, this.mDefaultValue, this.mResult);
            case 4:
                return webChromeClient.onJsBeforeUnload(webView, this.mUrl, this.mMessage, this.mResult);
            default:
                throw new java.lang.IllegalArgumentException("Unexpected type: " + this.mType);
        }
    }

    public void showDialog(android.content.Context context) {
        java.lang.String jsDialogTitle;
        java.lang.String str;
        int i;
        int i2;
        if (!canShowAlertDialog(context)) {
            android.util.Log.w(TAG, "Cannot create a dialog, the WebView context is not an Activity");
            this.mResult.cancel();
            return;
        }
        if (this.mType == 4) {
            jsDialogTitle = context.getString(com.android.internal.R.string.js_dialog_before_unload_title);
            str = context.getString(com.android.internal.R.string.js_dialog_before_unload, this.mMessage);
            i = com.android.internal.R.string.js_dialog_before_unload_positive_button;
            i2 = com.android.internal.R.string.js_dialog_before_unload_negative_button;
        } else {
            jsDialogTitle = getJsDialogTitle(context);
            str = this.mMessage;
            i = 17039370;
            i2 = 17039360;
        }
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setTitle(jsDialogTitle);
        builder.setOnCancelListener(new android.webkit.JsDialogHelper.CancelListener());
        if (this.mType != 3) {
            builder.setMessage(str);
            builder.setPositiveButton(i, new android.webkit.JsDialogHelper.PositiveListener(null));
        } else {
            android.view.View inflate = android.view.LayoutInflater.from(context).inflate(com.android.internal.R.layout.js_prompt, (android.view.ViewGroup) null);
            android.widget.EditText editText = (android.widget.EditText) inflate.findViewById(com.android.internal.R.id.value);
            editText.lambda$setTextAsync$0(this.mDefaultValue);
            builder.setPositiveButton(i, new android.webkit.JsDialogHelper.PositiveListener(editText));
            ((android.widget.TextView) inflate.findViewById(16908299)).lambda$setTextAsync$0(this.mMessage);
            builder.setView(inflate);
        }
        if (this.mType != 1) {
            builder.setNegativeButton(i2, new android.webkit.JsDialogHelper.CancelListener());
        }
        builder.show();
    }

    private class CancelListener implements android.content.DialogInterface.OnCancelListener, android.content.DialogInterface.OnClickListener {
        private CancelListener() {
        }

        @Override // android.content.DialogInterface.OnCancelListener
        public void onCancel(android.content.DialogInterface dialogInterface) {
            android.webkit.JsDialogHelper.this.mResult.cancel();
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(android.content.DialogInterface dialogInterface, int i) {
            android.webkit.JsDialogHelper.this.mResult.cancel();
        }
    }

    private class PositiveListener implements android.content.DialogInterface.OnClickListener {
        private final android.widget.EditText mEdit;

        public PositiveListener(android.widget.EditText editText) {
            this.mEdit = editText;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(android.content.DialogInterface dialogInterface, int i) {
            if (this.mEdit == null) {
                android.webkit.JsDialogHelper.this.mResult.confirm();
            } else {
                android.webkit.JsDialogHelper.this.mResult.confirm(this.mEdit.getText().toString());
            }
        }
    }

    private java.lang.String getJsDialogTitle(android.content.Context context) {
        java.lang.String str = this.mUrl;
        if (android.webkit.URLUtil.isDataUrl(this.mUrl)) {
            return context.getString(com.android.internal.R.string.js_dialog_title_default);
        }
        try {
            java.net.URL url = new java.net.URL(this.mUrl);
            return context.getString(com.android.internal.R.string.js_dialog_title, url.getProtocol() + "://" + url.getHost());
        } catch (java.net.MalformedURLException e) {
            return str;
        }
    }

    private static boolean canShowAlertDialog(android.content.Context context) {
        return context instanceof android.app.Activity;
    }
}
