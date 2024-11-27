package com.android.server.am;

/* loaded from: classes.dex */
final class AppWaitingForDebuggerDialog extends com.android.server.am.BaseErrorDialog {
    private java.lang.CharSequence mAppName;
    private final android.os.Handler mHandler;
    final com.android.server.am.ProcessRecord mProc;
    final com.android.server.am.ActivityManagerService mService;

    public AppWaitingForDebuggerDialog(com.android.server.am.ActivityManagerService activityManagerService, android.content.Context context, com.android.server.am.ProcessRecord processRecord) {
        super(context);
        this.mHandler = new android.os.Handler() { // from class: com.android.server.am.AppWaitingForDebuggerDialog.1
            @Override // android.os.Handler
            public void handleMessage(android.os.Message message) {
                switch (message.what) {
                    case 1:
                        com.android.server.am.AppWaitingForDebuggerDialog.this.mService.killAppAtUsersRequest(com.android.server.am.AppWaitingForDebuggerDialog.this.mProc);
                        break;
                }
            }
        };
        this.mService = activityManagerService;
        this.mProc = processRecord;
        this.mAppName = context.getPackageManager().getApplicationLabel(processRecord.info);
        setCancelable(false);
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        if (this.mAppName != null && this.mAppName.length() > 0) {
            sb.append("Application ");
            sb.append(this.mAppName);
            sb.append(" (process ");
            sb.append(processRecord.processName);
            sb.append(")");
        } else {
            sb.append("Process ");
            sb.append(processRecord.processName);
        }
        sb.append(" is waiting for the debugger to attach.");
        setMessage(sb.toString());
        setButton(-1, "Force Close", this.mHandler.obtainMessage(1, processRecord));
        setTitle("Waiting For Debugger");
        android.view.WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.privateFlags |= 16;
        attributes.setTitle("Waiting For Debugger: " + processRecord.info.processName);
        getWindow().setAttributes(attributes);
    }

    @Override // com.android.server.am.BaseErrorDialog
    protected void closeDialog() {
    }
}
