package com.android.server.am;

/* loaded from: classes.dex */
final class AppNotRespondingDialog extends com.android.server.am.BaseErrorDialog implements android.view.View.OnClickListener {
    public static final int ALREADY_SHOWING = -2;
    public static final int CANT_SHOW = -1;
    static final int FORCE_CLOSE = 1;
    private static final java.lang.String TAG = "AppNotRespondingDialog";
    static final int WAIT = 2;
    static final int WAIT_AND_REPORT = 3;
    private final com.android.server.am.AppNotRespondingDialog.Data mData;
    private final android.os.Handler mHandler;
    private final com.android.server.am.ProcessRecord mProc;
    private final com.android.server.am.ActivityManagerService mService;

    public AppNotRespondingDialog(com.android.server.am.ActivityManagerService activityManagerService, android.content.Context context, com.android.server.am.AppNotRespondingDialog.Data data) {
        super(context);
        java.lang.CharSequence charSequence;
        int i;
        java.lang.String string;
        this.mHandler = new android.os.Handler() { // from class: com.android.server.am.AppNotRespondingDialog.1
            @Override // android.os.Handler
            public void handleMessage(android.os.Message message) {
                com.android.internal.logging.MetricsLogger.action(com.android.server.am.AppNotRespondingDialog.this.getContext(), com.android.internal.util.FrameworkStatsLog.APP_BACKGROUND_RESTRICTIONS_INFO__EXEMPTION_REASON__REASON_MEDIA_SESSION_CALLBACK, message.what);
                switch (message.what) {
                    case 1:
                        com.android.server.am.AppNotRespondingDialog.this.mService.killAppAtUsersRequest(com.android.server.am.AppNotRespondingDialog.this.mProc);
                        break;
                    case 2:
                    case 3:
                        com.android.server.am.ActivityManagerService activityManagerService2 = com.android.server.am.AppNotRespondingDialog.this.mService;
                        com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
                        synchronized (activityManagerService2) {
                            try {
                                com.android.server.am.ProcessRecord processRecord = com.android.server.am.AppNotRespondingDialog.this.mProc;
                                com.android.server.am.ProcessErrorStateRecord processErrorStateRecord = processRecord.mErrorState;
                                r1 = message.what == 3 ? com.android.server.am.AppNotRespondingDialog.this.mService.mAppErrors.createAppErrorIntentLOSP(processRecord, java.lang.System.currentTimeMillis(), null) : null;
                                com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock = com.android.server.am.AppNotRespondingDialog.this.mService.mProcLock;
                                com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
                                synchronized (activityManagerGlobalLock) {
                                    try {
                                        processErrorStateRecord.setNotResponding(false);
                                        processErrorStateRecord.getDialogController().clearAnrDialogs();
                                    } catch (java.lang.Throwable th) {
                                        com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                                        throw th;
                                    }
                                }
                                com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                                com.android.server.am.AppNotRespondingDialog.this.mService.mServices.scheduleServiceTimeoutLocked(processRecord);
                                if (com.android.server.am.AppNotRespondingDialog.this.mData.isContinuousAnr) {
                                    com.android.server.am.AppNotRespondingDialog.this.mService.mInternal.rescheduleAnrDialog(com.android.server.am.AppNotRespondingDialog.this.mData);
                                }
                            } catch (java.lang.Throwable th2) {
                                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                                throw th2;
                            }
                        }
                        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                        break;
                }
                if (r1 != null) {
                    try {
                        com.android.server.am.AppNotRespondingDialog.this.getContext().startActivity(r1);
                    } catch (android.content.ActivityNotFoundException e) {
                        android.util.Slog.w(com.android.server.am.AppNotRespondingDialog.TAG, "bug report receiver dissappeared", e);
                    }
                }
                com.android.server.am.AppNotRespondingDialog.this.dismiss();
            }
        };
        this.mService = activityManagerService;
        this.mProc = data.proc;
        this.mData = data;
        android.content.res.Resources resources = context.getResources();
        setCancelable(false);
        java.lang.CharSequence charSequence2 = null;
        if (data.aInfo != null) {
            charSequence = data.aInfo.loadLabel(context.getPackageManager());
        } else {
            charSequence = null;
        }
        if (this.mProc.getPkgList().size() == 1 && (charSequence2 = context.getPackageManager().getApplicationLabel(this.mProc.info)) != null) {
            if (charSequence != null) {
                i = android.R.string.android_start_title;
            } else {
                charSequence2 = this.mProc.processName;
                i = 17039681;
                charSequence = charSequence2;
            }
        } else if (charSequence != null) {
            charSequence2 = this.mProc.processName;
            i = android.R.string.android_system_label;
        } else {
            charSequence = this.mProc.processName;
            i = android.R.string.android_upgrading_notification_title;
        }
        android.text.BidiFormatter bidiFormatter = android.text.BidiFormatter.getInstance();
        if (charSequence2 != null) {
            string = resources.getString(i, bidiFormatter.unicodeWrap(charSequence.toString()), bidiFormatter.unicodeWrap(charSequence2.toString()));
        } else {
            string = resources.getString(i, bidiFormatter.unicodeWrap(charSequence.toString()));
        }
        setTitle(string);
        if (data.aboveSystem) {
            getWindow().setType(2010);
        }
        android.view.WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.setTitle("Application Not Responding: " + this.mProc.info.processName);
        attributes.privateFlags = 272;
        getWindow().setAttributes(attributes);
    }

    @Override // android.app.AlertDialog, android.app.Dialog
    protected void onCreate(android.os.Bundle bundle) {
        super.onCreate(bundle);
        android.view.LayoutInflater.from(getContext()).inflate(android.R.layout.app_anr_dialog, (android.view.ViewGroup) findViewById(android.R.id.custom), true);
        android.widget.TextView textView = (android.widget.TextView) findViewById(android.R.id.aerr_report);
        textView.setOnClickListener(this);
        textView.setVisibility(this.mProc.mErrorState.getErrorReportReceiver() != null ? 0 : 8);
        ((android.widget.TextView) findViewById(android.R.id.aerr_close)).setOnClickListener(this);
        ((android.widget.TextView) findViewById(android.R.id.aerr_wait)).setOnClickListener(this);
        findViewById(android.R.id.customPanel).setVisibility(0);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(android.view.View view) {
        switch (view.getId()) {
            case android.R.id.aerr_close:
                this.mHandler.obtainMessage(1).sendToTarget();
                break;
            case android.R.id.aerr_report:
                this.mHandler.obtainMessage(3).sendToTarget();
                break;
            case android.R.id.aerr_wait:
                this.mHandler.obtainMessage(2).sendToTarget();
                break;
        }
    }

    @Override // com.android.server.am.BaseErrorDialog
    protected void closeDialog() {
        this.mHandler.obtainMessage(1).sendToTarget();
    }

    static class Data {
        final android.content.pm.ApplicationInfo aInfo;
        final boolean aboveSystem;
        final boolean isContinuousAnr;
        final com.android.server.am.ProcessRecord proc;

        Data(com.android.server.am.ProcessRecord processRecord, android.content.pm.ApplicationInfo applicationInfo, boolean z, boolean z2) {
            this.proc = processRecord;
            this.aInfo = applicationInfo;
            this.aboveSystem = z;
            this.isContinuousAnr = z2;
        }
    }
}
