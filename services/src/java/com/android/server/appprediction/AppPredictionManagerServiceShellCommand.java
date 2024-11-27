package com.android.server.appprediction;

/* loaded from: classes.dex */
public class AppPredictionManagerServiceShellCommand extends android.os.ShellCommand {
    private static final java.lang.String TAG = com.android.server.appprediction.AppPredictionManagerServiceShellCommand.class.getSimpleName();
    private final com.android.server.appprediction.AppPredictionManagerService mService;

    public AppPredictionManagerServiceShellCommand(@android.annotation.NonNull com.android.server.appprediction.AppPredictionManagerService appPredictionManagerService) {
        this.mService = appPredictionManagerService;
    }

    public int onCommand(java.lang.String str) {
        boolean z;
        if (str == null) {
            return handleDefaultCommands(str);
        }
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        char c = 65535;
        switch (str.hashCode()) {
            case 113762:
                if (str.equals("set")) {
                    z = false;
                    break;
                }
            default:
                z = -1;
                break;
        }
        switch (z) {
            case false:
                java.lang.String nextArgRequired = getNextArgRequired();
                switch (nextArgRequired.hashCode()) {
                    case 2003978041:
                        if (nextArgRequired.equals("temporary-service")) {
                            c = 0;
                            break;
                        }
                        break;
                }
                switch (c) {
                    case 0:
                        int parseInt = java.lang.Integer.parseInt(getNextArgRequired());
                        java.lang.String nextArg = getNextArg();
                        if (nextArg == null) {
                            this.mService.resetTemporaryService(parseInt);
                            break;
                        } else {
                            int parseInt2 = java.lang.Integer.parseInt(getNextArgRequired());
                            this.mService.setTemporaryService(parseInt, nextArg, parseInt2);
                            outPrintWriter.println("AppPredictionService temporarily set to " + nextArg + " for " + parseInt2 + "ms");
                        }
                    default:
                        return 0;
                }
        }
        return handleDefaultCommands(str);
    }

    public void onHelp() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        try {
            outPrintWriter.println("AppPredictionManagerService commands:");
            outPrintWriter.println("  help");
            outPrintWriter.println("    Prints this help text.");
            outPrintWriter.println("");
            outPrintWriter.println("  set temporary-service USER_ID [COMPONENT_NAME DURATION]");
            outPrintWriter.println("    Temporarily (for DURATION ms) changes the service implemtation.");
            outPrintWriter.println("    To reset, call with just the USER_ID argument.");
            outPrintWriter.println("");
            outPrintWriter.close();
        } catch (java.lang.Throwable th) {
            if (outPrintWriter != null) {
                try {
                    outPrintWriter.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }
}