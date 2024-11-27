package com.android.server.recoverysystem;

/* loaded from: classes2.dex */
public class RecoverySystemShellCommand extends android.os.ShellCommand {
    private final android.os.IRecoverySystem mService;

    public RecoverySystemShellCommand(com.android.server.recoverysystem.RecoverySystemService recoverySystemService) {
        this.mService = recoverySystemService;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public int onCommand(java.lang.String str) {
        char c;
        if (str == null) {
            return handleDefaultCommands(str);
        }
        try {
            switch (str.hashCode()) {
                case -779212638:
                    if (str.equals("clear-lskf")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 3649607:
                    if (str.equals("wipe")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 1214227142:
                    if (str.equals("is-lskf-captured")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 1256867232:
                    if (str.equals("request-lskf")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 1405182928:
                    if (str.equals("reboot-and-apply")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
            }
        } catch (java.lang.Exception e) {
            getErrPrintWriter().println("Error while executing command: " + str);
            e.printStackTrace(getErrPrintWriter());
            return -1;
        }
        return handleDefaultCommands(str);
    }

    private int wipe() throws android.os.RemoteException {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        java.lang.String nextArg = getNextArg();
        java.lang.String str = "--wipe_data";
        if (nextArg != null && !nextArg.isEmpty()) {
            str = "--wipe_data\n--reformat_data=" + nextArg;
        }
        outPrintWriter.println("Rebooting into recovery with " + str.replaceAll("\n", " "));
        this.mService.rebootRecoveryWithCommand(str);
        return 0;
    }

    private int requestLskf() throws android.os.RemoteException {
        java.lang.String nextArgRequired = getNextArgRequired();
        getOutPrintWriter().printf("Request LSKF for packageName: %s, status: %s\n", nextArgRequired, this.mService.requestLskf(nextArgRequired, (android.content.IntentSender) null) ? com.android.server.content.SyncStorageEngine.MESG_SUCCESS : "failure");
        return 0;
    }

    private int clearLskf() throws android.os.RemoteException {
        java.lang.String nextArgRequired = getNextArgRequired();
        getOutPrintWriter().printf("Clear LSKF for packageName: %s, status: %s\n", nextArgRequired, this.mService.clearLskf(nextArgRequired) ? com.android.server.content.SyncStorageEngine.MESG_SUCCESS : "failure");
        return 0;
    }

    private int isLskfCaptured() throws android.os.RemoteException {
        java.lang.String nextArgRequired = getNextArgRequired();
        getOutPrintWriter().printf("%s LSKF capture status: %s\n", nextArgRequired, this.mService.isLskfCaptured(nextArgRequired) ? "true" : "false");
        return 0;
    }

    private int rebootAndApply() throws android.os.RemoteException {
        java.lang.String nextArgRequired = getNextArgRequired();
        getOutPrintWriter().printf("%s Reboot and apply status: %s\n", nextArgRequired, this.mService.rebootWithLskf(nextArgRequired, getNextArgRequired(), false) == 0 ? com.android.server.content.SyncStorageEngine.MESG_SUCCESS : "failure");
        return 0;
    }

    public void onHelp() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Recovery system commands:");
        outPrintWriter.println("  request-lskf <package_name>");
        outPrintWriter.println("  clear-lskf");
        outPrintWriter.println("  is-lskf-captured <package_name>");
        outPrintWriter.println("  reboot-and-apply <package_name> <reason>");
        outPrintWriter.println("  wipe <new filesystem type ext4/f2fs>");
    }
}
