package com.android.server.am;

/* loaded from: classes.dex */
final class StrictModeViolationDialog extends com.android.server.am.BaseErrorDialog {
    static final int ACTION_OK = 0;
    static final int ACTION_OK_AND_REPORT = 1;
    static final long DISMISS_TIMEOUT = 60000;
    private static final java.lang.String TAG = "StrictModeViolationDialog";
    private final android.os.Handler mHandler;
    private final com.android.server.am.ProcessRecord mProc;
    private final com.android.server.am.AppErrorResult mResult;
    private final com.android.server.am.ActivityManagerService mService;

    public StrictModeViolationDialog(android.content.Context context, com.android.server.am.ActivityManagerService activityManagerService, com.android.server.am.AppErrorResult appErrorResult, com.android.server.am.ProcessRecord processRecord) {
        super(context);
        java.lang.CharSequence applicationLabel;
        this.mHandler = new android.os.Handler() { // from class: com.android.server.am.StrictModeViolationDialog.1
            @Override // android.os.Handler
            public void handleMessage(android.os.Message message) {
                com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock = com.android.server.am.StrictModeViolationDialog.this.mService.mProcLock;
                com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
                synchronized (activityManagerGlobalLock) {
                    try {
                        if (com.android.server.am.StrictModeViolationDialog.this.mProc != null) {
                            com.android.server.am.StrictModeViolationDialog.this.mProc.mErrorState.getDialogController().clearViolationDialogs();
                        }
                    } catch (java.lang.Throwable th) {
                        com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                        throw th;
                    }
                }
                com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                com.android.server.am.StrictModeViolationDialog.this.mResult.set(message.what);
                com.android.server.am.StrictModeViolationDialog.this.dismiss();
            }
        };
        android.content.res.Resources resources = context.getResources();
        this.mService = activityManagerService;
        this.mProc = processRecord;
        this.mResult = appErrorResult;
        if (processRecord.getPkgList().size() == 1 && (applicationLabel = context.getPackageManager().getApplicationLabel(processRecord.info)) != null) {
            setMessage(resources.getString(android.R.string.shutdown_confirm, applicationLabel.toString(), processRecord.info.processName));
        } else {
            setMessage(resources.getString(android.R.string.shutdown_confirm_question, processRecord.processName.toString()));
        }
        setCancelable(false);
        setButton(-1, resources.getText(android.R.string.device_state_notification_turn_off_button), this.mHandler.obtainMessage(0));
        if (processRecord.mErrorState.getErrorReportReceiver() != null) {
            setButton(-2, resources.getText(android.R.string.reboot_to_update_package), this.mHandler.obtainMessage(1));
        }
        getWindow().addPrivateFlags(256);
        getWindow().setTitle("Strict Mode Violation: " + processRecord.info.processName);
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(0), 60000L);
    }

    @Override // com.android.server.am.BaseErrorDialog
    protected void closeDialog() {
        this.mHandler.obtainMessage(0).sendToTarget();
    }
}
