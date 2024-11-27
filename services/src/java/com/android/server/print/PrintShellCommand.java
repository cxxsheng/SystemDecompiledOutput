package com.android.server.print;

/* loaded from: classes2.dex */
final class PrintShellCommand extends android.os.ShellCommand {

    @android.annotation.NonNull
    final android.print.IPrintManager mService;

    PrintShellCommand(@android.annotation.NonNull android.print.IPrintManager iPrintManager) {
        this.mService = iPrintManager;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public int onCommand(@android.annotation.Nullable java.lang.String str) {
        boolean z;
        if (str == null) {
            return handleDefaultCommands(str);
        }
        switch (str.hashCode()) {
            case -859068373:
                if (str.equals("get-bind-instant-service-allowed")) {
                    z = false;
                    break;
                }
                z = -1;
                break;
            case 789489311:
                if (str.equals("set-bind-instant-service-allowed")) {
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
                return runGetBindInstantServiceAllowed();
            case true:
                return runSetBindInstantServiceAllowed();
            default:
                return -1;
        }
    }

    private int runGetBindInstantServiceAllowed() {
        java.lang.Integer parseUserId = parseUserId();
        if (parseUserId == null) {
            return -1;
        }
        try {
            getOutPrintWriter().println(java.lang.Boolean.toString(this.mService.getBindInstantServiceAllowed(parseUserId.intValue())));
            return 0;
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
            return 0;
        }
    }

    private int runSetBindInstantServiceAllowed() {
        java.lang.Integer parseUserId = parseUserId();
        if (parseUserId == null) {
            return -1;
        }
        java.lang.String nextArgRequired = getNextArgRequired();
        if (nextArgRequired == null) {
            getErrPrintWriter().println("Error: no true/false specified");
            return -1;
        }
        try {
            this.mService.setBindInstantServiceAllowed(parseUserId.intValue(), java.lang.Boolean.parseBoolean(nextArgRequired));
            return 0;
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
            return 0;
        }
    }

    @android.annotation.Nullable
    private java.lang.Integer parseUserId() {
        java.lang.String nextOption = getNextOption();
        if (nextOption != null) {
            if (nextOption.equals("--user")) {
                return java.lang.Integer.valueOf(android.os.UserHandle.parseUserArg(getNextArgRequired()));
            }
            getErrPrintWriter().println("Unknown option: " + nextOption);
            return null;
        }
        return 0;
    }

    public void onHelp() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Print service commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println("  set-bind-instant-service-allowed [--user <USER_ID>] true|false ");
        outPrintWriter.println("    Set whether binding to print services provided by instant apps is allowed.");
        outPrintWriter.println("  get-bind-instant-service-allowed [--user <USER_ID>]");
        outPrintWriter.println("    Get whether binding to print services provided by instant apps is allowed.");
    }
}
