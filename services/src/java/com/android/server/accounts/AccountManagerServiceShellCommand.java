package com.android.server.accounts;

/* loaded from: classes.dex */
final class AccountManagerServiceShellCommand extends android.os.ShellCommand {

    @android.annotation.NonNull
    final com.android.server.accounts.AccountManagerService mService;

    AccountManagerServiceShellCommand(@android.annotation.NonNull com.android.server.accounts.AccountManagerService accountManagerService) {
        this.mService = accountManagerService;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public int onCommand(java.lang.String str) {
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
        getOutPrintWriter().println(java.lang.Boolean.toString(this.mService.getBindInstantServiceAllowed(parseUserId.intValue())));
        return 0;
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
        this.mService.setBindInstantServiceAllowed(parseUserId.intValue(), java.lang.Boolean.parseBoolean(nextArgRequired));
        return 0;
    }

    private java.lang.Integer parseUserId() {
        java.lang.String nextOption = getNextOption();
        if (nextOption != null) {
            if (nextOption.equals("--user")) {
                int parseUserArg = android.os.UserHandle.parseUserArg(getNextArgRequired());
                if (parseUserArg == -2) {
                    return java.lang.Integer.valueOf(android.app.ActivityManager.getCurrentUser());
                }
                if (parseUserArg == -1) {
                    getErrPrintWriter().println("USER_ALL not supported. Specify a user.");
                    return null;
                }
                if (parseUserArg < 0) {
                    getErrPrintWriter().println("Invalid user: " + parseUserArg);
                    return null;
                }
                return java.lang.Integer.valueOf(parseUserArg);
            }
            getErrPrintWriter().println("Unknown option: " + nextOption);
            return null;
        }
        return java.lang.Integer.valueOf(android.app.ActivityManager.getCurrentUser());
    }

    public void onHelp() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Account manager service commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println("  set-bind-instant-service-allowed [--user <USER_ID> (current user if not specified)] true|false ");
        outPrintWriter.println("    Set whether binding to services provided by instant apps is allowed.");
        outPrintWriter.println("  get-bind-instant-service-allowed [--user <USER_ID> (current user if not specified)]");
        outPrintWriter.println("    Get whether binding to services provided by instant apps is allowed.");
    }
}
