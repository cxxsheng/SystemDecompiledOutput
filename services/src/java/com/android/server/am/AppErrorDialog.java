package com.android.server.am;

/* loaded from: classes.dex */
final class AppErrorDialog extends com.android.server.am.BaseErrorDialog implements android.view.View.OnClickListener {
    static final int APP_INFO = 8;
    static final int CANCEL = 7;
    static final long DISMISS_TIMEOUT = 300000;
    static final int FORCE_QUIT = 1;
    static final int FORCE_QUIT_AND_REPORT = 2;
    static final int MUTE = 5;
    static final int RESTART = 3;
    static final int TIMEOUT = 6;
    private final android.os.Handler mHandler;
    private final boolean mIsRestartable;
    private final com.android.server.am.ProcessRecord mProc;
    private final com.android.server.am.ActivityManagerGlobalLock mProcLock;
    private final com.android.server.am.AppErrorResult mResult;
    private final com.android.server.am.ActivityManagerService mService;
    static int CANT_SHOW = -1;
    static int BACKGROUND_USER = -2;
    static int ALREADY_SHOWING = -3;

    static class Data {
        boolean isRestartableForService;
        com.android.server.am.ProcessRecord proc;
        boolean repeating;
        com.android.server.am.AppErrorResult result;
        int taskId;

        Data() {
        }
    }

    public AppErrorDialog(android.content.Context context, com.android.server.am.ActivityManagerService activityManagerService, com.android.server.am.AppErrorDialog.Data data) {
        super(context);
        java.lang.CharSequence applicationLabel;
        this.mHandler = new android.os.Handler() { // from class: com.android.server.am.AppErrorDialog.1
            @Override // android.os.Handler
            public void handleMessage(android.os.Message message) {
                com.android.server.am.AppErrorDialog.this.setResult(message.what);
                com.android.server.am.AppErrorDialog.this.dismiss();
            }
        };
        android.content.res.Resources resources = context.getResources();
        this.mService = activityManagerService;
        this.mProcLock = activityManagerService.mProcLock;
        this.mProc = data.proc;
        this.mResult = data.result;
        boolean z = false;
        if ((data.taskId != -1 || data.isRestartableForService) && android.provider.Settings.Global.getInt(context.getContentResolver(), "show_restart_in_crash_dialog", 0) != 0) {
            z = true;
        }
        this.mIsRestartable = z;
        android.text.BidiFormatter bidiFormatter = android.text.BidiFormatter.getInstance();
        if (this.mProc.getPkgList().size() == 1 && (applicationLabel = context.getPackageManager().getApplicationLabel(this.mProc.info)) != null) {
            setTitle(resources.getString(data.repeating ? android.R.string.adbwifi_active_notification_message : android.R.string.adb_debugging_notification_channel_tv, bidiFormatter.unicodeWrap(applicationLabel.toString()), bidiFormatter.unicodeWrap(this.mProc.info.processName)));
        } else {
            setTitle(resources.getString(data.repeating ? android.R.string.aerr_application : android.R.string.add_account_label, bidiFormatter.unicodeWrap(this.mProc.processName.toString())));
        }
        setCancelable(true);
        setCancelMessage(this.mHandler.obtainMessage(7));
        android.view.WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.setTitle("Application Error: " + this.mProc.info.processName);
        attributes.privateFlags = attributes.privateFlags | 272;
        getWindow().setAttributes(attributes);
        if (this.mProc.isPersistent()) {
            getWindow().setType(2010);
        }
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(6), 300000L);
    }

    @Override // android.app.AlertDialog, android.app.Dialog
    protected void onCreate(android.os.Bundle bundle) {
        super.onCreate(bundle);
        android.widget.FrameLayout frameLayout = (android.widget.FrameLayout) findViewById(android.R.id.custom);
        android.content.Context context = getContext();
        android.view.LayoutInflater.from(context).inflate(android.R.layout.app_error_dialog, (android.view.ViewGroup) frameLayout, true);
        boolean z = this.mProc.mErrorState.getErrorReportReceiver() != null;
        android.widget.TextView textView = (android.widget.TextView) findViewById(android.R.id.aerr_restart);
        textView.setOnClickListener(this);
        textView.setVisibility(this.mIsRestartable ? 0 : 8);
        android.widget.TextView textView2 = (android.widget.TextView) findViewById(android.R.id.aerr_report);
        textView2.setOnClickListener(this);
        textView2.setVisibility(z ? 0 : 8);
        ((android.widget.TextView) findViewById(android.R.id.aerr_close)).setOnClickListener(this);
        ((android.widget.TextView) findViewById(android.R.id.aerr_app_info)).setOnClickListener(this);
        boolean z2 = (android.os.Build.IS_USER || android.provider.Settings.Global.getInt(context.getContentResolver(), "development_settings_enabled", 0) == 0 || android.provider.Settings.Global.getInt(context.getContentResolver(), "show_mute_in_crash_dialog", 0) == 0) ? false : true;
        android.widget.TextView textView3 = (android.widget.TextView) findViewById(android.R.id.aerr_mute);
        textView3.setOnClickListener(this);
        textView3.setVisibility(z2 ? 0 : 8);
        findViewById(android.R.id.customPanel).setVisibility(0);
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        if (!this.mResult.mHasResult) {
            setResult(1);
        }
        super.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setResult(int i) {
        com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
        com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
        synchronized (activityManagerGlobalLock) {
            try {
                if (this.mProc != null) {
                    this.mProc.mErrorState.getDialogController().clearCrashDialogs(false);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                throw th;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
        this.mResult.set(i);
        this.mHandler.removeMessages(6);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(android.view.View view) {
        switch (view.getId()) {
            case android.R.id.aerr_app_info:
                this.mHandler.obtainMessage(8).sendToTarget();
                break;
            case android.R.id.aerr_close:
                this.mHandler.obtainMessage(1).sendToTarget();
                break;
            case android.R.id.aerr_mute:
                this.mHandler.obtainMessage(5).sendToTarget();
                break;
            case android.R.id.aerr_report:
                this.mHandler.obtainMessage(2).sendToTarget();
                break;
            case android.R.id.aerr_restart:
                this.mHandler.obtainMessage(3).sendToTarget();
                break;
        }
    }
}
