package com.android.server.usage;

/* loaded from: classes2.dex */
class UsageStatsShellCommand extends android.os.ShellCommand {
    private final com.android.server.usage.UsageStatsService mService;

    UsageStatsShellCommand(com.android.server.usage.UsageStatsService usageStatsService) {
        this.mService = usageStatsService;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public int onCommand(java.lang.String str) {
        char c;
        if (str == null) {
            return handleDefaultCommands((java.lang.String) null);
        }
        switch (str.hashCode()) {
            case 949945779:
                if (str.equals("delete-package-data")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 2135796854:
                if (str.equals("clear-last-used-timestamps")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return runClearLastUsedTimestamps();
            case 1:
                return deletePackageData();
            default:
                return handleDefaultCommands(str);
        }
    }

    public void onHelp() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("UsageStats service (usagestats) commands:");
        outPrintWriter.println("help");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println();
        outPrintWriter.println("clear-last-used-timestamps PACKAGE_NAME [-u | --user USER_ID]");
        outPrintWriter.println("    Clears the last used timestamps for the given package.");
        outPrintWriter.println();
        outPrintWriter.println("delete-package-data PACKAGE_NAME [-u | --user USER_ID]");
        outPrintWriter.println("    Deletes all the usage stats for the given package.");
        outPrintWriter.println();
    }

    @android.annotation.SuppressLint({"AndroidFrameworkRequiresPermission"})
    private int runClearLastUsedTimestamps() {
        java.lang.String nextArgRequired = getNextArgRequired();
        int userId = getUserId();
        if (userId == -1) {
            return -1;
        }
        this.mService.clearLastUsedTimestamps(nextArgRequired, userId);
        return 0;
    }

    @android.annotation.SuppressLint({"AndroidFrameworkRequiresPermission"})
    private int deletePackageData() {
        java.lang.String nextArgRequired = getNextArgRequired();
        int userId = getUserId();
        if (userId == -1) {
            return -1;
        }
        this.mService.deletePackageData(nextArgRequired, userId);
        return 0;
    }

    private int getUserId() {
        int i = -2;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                if ("-u".equals(nextOption) || "--user".equals(nextOption)) {
                    i = android.os.UserHandle.parseUserArg(getNextArgRequired());
                } else {
                    getErrPrintWriter().println("Error: unknown option: " + nextOption);
                    return -1;
                }
            } else {
                if (i == -2) {
                    return android.app.ActivityManager.getCurrentUser();
                }
                return i;
            }
        }
    }
}
