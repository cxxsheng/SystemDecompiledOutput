package com.android.server.apphibernation;

/* loaded from: classes.dex */
final class AppHibernationShellCommand extends android.os.ShellCommand {
    private static final int ERROR = -1;
    private static final java.lang.String GLOBAL_OPT = "--global";
    private static final int SUCCESS = 0;
    private static final java.lang.String USER_OPT = "--user";
    private final com.android.server.apphibernation.AppHibernationService mService;

    AppHibernationShellCommand(com.android.server.apphibernation.AppHibernationService appHibernationService) {
        this.mService = appHibernationService;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public int onCommand(java.lang.String str) {
        char c;
        if (str == null) {
            return handleDefaultCommands(str);
        }
        switch (str.hashCode()) {
            case -499367066:
                if (str.equals("set-state")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -284749990:
                if (str.equals("get-state")) {
                    c = 1;
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
        return handleDefaultCommands(str);
    }

    private int runSetState() {
        int i = -2;
        boolean z = false;
        while (true) {
            java.lang.String nextOption = getNextOption();
            char c = 65535;
            if (nextOption != null) {
                switch (nextOption.hashCode()) {
                    case 1156993347:
                        if (nextOption.equals(GLOBAL_OPT)) {
                            c = 1;
                            break;
                        }
                        break;
                    case 1333469547:
                        if (nextOption.equals(USER_OPT)) {
                            c = 0;
                            break;
                        }
                        break;
                }
                switch (c) {
                    case 0:
                        i = android.os.UserHandle.parseUserArg(getNextArgRequired());
                        break;
                    case 1:
                        z = true;
                        break;
                    default:
                        getErrPrintWriter().println("Error: Unknown option: " + nextOption);
                        break;
                }
            } else {
                java.lang.String nextArgRequired = getNextArgRequired();
                if (nextArgRequired == null) {
                    getErrPrintWriter().println("Error: no package specified");
                    return -1;
                }
                java.lang.String nextArgRequired2 = getNextArgRequired();
                if (nextArgRequired2 == null) {
                    getErrPrintWriter().println("Error: No state to set specified");
                    return -1;
                }
                boolean parseBoolean = java.lang.Boolean.parseBoolean(nextArgRequired2);
                if (z) {
                    this.mService.setHibernatingGlobally(nextArgRequired, parseBoolean);
                } else {
                    this.mService.setHibernatingForUser(nextArgRequired, i, parseBoolean);
                }
                return 0;
            }
        }
    }

    private int runGetState() {
        int i = -2;
        boolean z = false;
        while (true) {
            java.lang.String nextOption = getNextOption();
            char c = 65535;
            if (nextOption != null) {
                switch (nextOption.hashCode()) {
                    case 1156993347:
                        if (nextOption.equals(GLOBAL_OPT)) {
                            c = 1;
                            break;
                        }
                        break;
                    case 1333469547:
                        if (nextOption.equals(USER_OPT)) {
                            c = 0;
                            break;
                        }
                        break;
                }
                switch (c) {
                    case 0:
                        i = android.os.UserHandle.parseUserArg(getNextArgRequired());
                        break;
                    case 1:
                        z = true;
                        break;
                    default:
                        getErrPrintWriter().println("Error: Unknown option: " + nextOption);
                        break;
                }
            } else {
                java.lang.String nextArgRequired = getNextArgRequired();
                if (nextArgRequired == null) {
                    getErrPrintWriter().println("Error: No package specified");
                    return -1;
                }
                getOutPrintWriter().println(z ? this.mService.isHibernatingGlobally(nextArgRequired) : this.mService.isHibernatingForUser(nextArgRequired, i));
                return 0;
            }
        }
    }

    public void onHelp() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("App hibernation (app_hibernation) commands: ");
        outPrintWriter.println("  help");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println("");
        outPrintWriter.println("  set-state [--user USER_ID] [--global] PACKAGE true|false");
        outPrintWriter.println("    Sets the hibernation state of the package to value specified. Optionally");
        outPrintWriter.println("    may specify a user id or set global hibernation state.");
        outPrintWriter.println("");
        outPrintWriter.println("  get-state [--user USER_ID] [--global] PACKAGE");
        outPrintWriter.println("    Gets the hibernation state of the package. Optionally may specify a user");
        outPrintWriter.println("    id or request global hibernation state.");
        outPrintWriter.println("");
    }
}
