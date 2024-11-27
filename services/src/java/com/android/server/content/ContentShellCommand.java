package com.android.server.content;

/* loaded from: classes.dex */
public class ContentShellCommand extends android.os.ShellCommand {
    final android.content.IContentService mInterface;

    ContentShellCommand(android.content.IContentService iContentService) {
        this.mInterface = iContentService;
    }

    public int onCommand(java.lang.String str) {
        boolean z;
        if (str == null) {
            return handleDefaultCommands(str);
        }
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        try {
            switch (str.hashCode()) {
                case -796331115:
                    if (str.equals("reset-today-stats")) {
                        z = false;
                        break;
                    }
                default:
                    z = -1;
                    break;
            }
            switch (z) {
            }
        } catch (android.os.RemoteException e) {
            outPrintWriter.println("Remote exception: " + e);
            return -1;
        }
        return handleDefaultCommands(str);
    }

    private int runResetTodayStats() throws android.os.RemoteException {
        this.mInterface.resetTodayStats();
        return 0;
    }

    public void onHelp() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Content service commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println("");
        outPrintWriter.println("  reset-today-stats");
        outPrintWriter.println("    Reset 1-day sync stats.");
        outPrintWriter.println();
    }
}
