package com.android.server.tare;

/* loaded from: classes2.dex */
public class TareShellCommand extends com.android.modules.utils.BasicShellCommandHandler {
    static final int COMMAND_ERROR = -1;
    static final int COMMAND_SUCCESS = 0;
    private final com.android.server.tare.InternalResourceService mIrs;

    public TareShellCommand(@android.annotation.NonNull com.android.server.tare.InternalResourceService internalResourceService) {
        this.mIrs = internalResourceService;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public int onCommand(java.lang.String str) {
        boolean z;
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        java.lang.String str2 = str != null ? str : "";
        try {
            switch (str2.hashCode()) {
                case -1272052579:
                    if (str2.equals("clear-vip")) {
                        z = false;
                        break;
                    }
                    z = -1;
                    break;
                case 1983838258:
                    if (str2.equals("set-vip")) {
                        z = true;
                        break;
                    }
                    z = -1;
                    break;
                default:
                    z = -1;
                    break;
            }
            switch (z) {
                case false:
                    return runClearVip(outPrintWriter);
                case true:
                    return runSetVip(outPrintWriter);
                default:
                    return handleDefaultCommands(str);
            }
        } catch (java.lang.Exception e) {
            outPrintWriter.println("Exception: " + e);
            return -1;
        }
    }

    public void onHelp() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("TARE commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println("  clear-vip");
        outPrintWriter.println("    Clears all VIP settings resulting from previous calls using `set-vip` and");
        outPrintWriter.println("    resets them all to default.");
        outPrintWriter.println("  set-vip <USER_ID> <PACKAGE> <true|false|default>");
        outPrintWriter.println("    Designate the app as a Very Important Package or not. A VIP is allowed to");
        outPrintWriter.println("    do as much work as it wants, regardless of TARE state.");
        outPrintWriter.println("    The user ID must be an explicit user ID. USER_ALL, CURRENT, etc. are not");
        outPrintWriter.println("    supported.");
        outPrintWriter.println();
    }

    private void checkPermission(@android.annotation.NonNull java.lang.String str) throws java.lang.Exception {
        if (this.mIrs.getContext().checkCallingOrSelfPermission("android.permission.CHANGE_APP_IDLE_STATE") != 0) {
            throw new java.lang.SecurityException("Uid " + android.os.Binder.getCallingUid() + " not permitted to " + str);
        }
    }

    private int runClearVip(@android.annotation.NonNull java.io.PrintWriter printWriter) throws java.lang.Exception {
        checkPermission("clear vip");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return this.mIrs.executeClearVip(printWriter);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private int runSetVip(@android.annotation.NonNull java.io.PrintWriter printWriter) throws java.lang.Exception {
        checkPermission("modify vip");
        int parseInt = java.lang.Integer.parseInt(getNextArgRequired());
        java.lang.String nextArgRequired = getNextArgRequired();
        java.lang.String nextArgRequired2 = getNextArgRequired();
        java.lang.Boolean valueOf = "default".equals(nextArgRequired2) ? null : java.lang.Boolean.valueOf(nextArgRequired2);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return this.mIrs.executeSetVip(printWriter, parseInt, nextArgRequired, valueOf);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
