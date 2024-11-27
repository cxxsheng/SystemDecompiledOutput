package android.webkit;

@java.lang.Deprecated
/* loaded from: classes4.dex */
public class Plugin {
    private java.lang.String mDescription;
    private java.lang.String mFileName;
    private android.webkit.Plugin.PreferencesClickHandler mHandler = new android.webkit.Plugin.DefaultClickHandler();
    private java.lang.String mName;
    private java.lang.String mPath;

    public interface PreferencesClickHandler {
        void handleClickEvent(android.content.Context context);
    }

    @java.lang.Deprecated
    public Plugin(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4) {
        this.mName = str;
        this.mPath = str2;
        this.mFileName = str3;
        this.mDescription = str4;
    }

    @java.lang.Deprecated
    public java.lang.String toString() {
        return this.mName;
    }

    @java.lang.Deprecated
    public java.lang.String getName() {
        return this.mName;
    }

    @java.lang.Deprecated
    public java.lang.String getPath() {
        return this.mPath;
    }

    @java.lang.Deprecated
    public java.lang.String getFileName() {
        return this.mFileName;
    }

    @java.lang.Deprecated
    public java.lang.String getDescription() {
        return this.mDescription;
    }

    @java.lang.Deprecated
    public void setName(java.lang.String str) {
        this.mName = str;
    }

    @java.lang.Deprecated
    public void setPath(java.lang.String str) {
        this.mPath = str;
    }

    @java.lang.Deprecated
    public void setFileName(java.lang.String str) {
        this.mFileName = str;
    }

    @java.lang.Deprecated
    public void setDescription(java.lang.String str) {
        this.mDescription = str;
    }

    @java.lang.Deprecated
    public void setClickHandler(android.webkit.Plugin.PreferencesClickHandler preferencesClickHandler) {
        this.mHandler = preferencesClickHandler;
    }

    @java.lang.Deprecated
    public void dispatchClickEvent(android.content.Context context) {
        if (this.mHandler != null) {
            this.mHandler.handleClickEvent(context);
        }
    }

    @java.lang.Deprecated
    private class DefaultClickHandler implements android.webkit.Plugin.PreferencesClickHandler, android.content.DialogInterface.OnClickListener {
        private android.app.AlertDialog mDialog;

        private DefaultClickHandler() {
        }

        @Override // android.webkit.Plugin.PreferencesClickHandler
        @java.lang.Deprecated
        public void handleClickEvent(android.content.Context context) {
            if (this.mDialog == null) {
                this.mDialog = new android.app.AlertDialog.Builder(context).setTitle(android.webkit.Plugin.this.mName).setMessage(android.webkit.Plugin.this.mDescription).setPositiveButton(17039370, this).setCancelable(false).show();
            }
        }

        @Override // android.content.DialogInterface.OnClickListener
        @java.lang.Deprecated
        public void onClick(android.content.DialogInterface dialogInterface, int i) {
            this.mDialog.dismiss();
            this.mDialog = null;
        }
    }
}
