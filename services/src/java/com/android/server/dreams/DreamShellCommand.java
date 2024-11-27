package com.android.server.dreams;

/* loaded from: classes.dex */
public class DreamShellCommand extends android.os.ShellCommand {
    private static final boolean DEBUG = true;
    private static final java.lang.String TAG = "DreamShellCommand";

    @android.annotation.NonNull
    private final com.android.server.dreams.DreamManagerService mService;

    DreamShellCommand(@android.annotation.NonNull com.android.server.dreams.DreamManagerService dreamManagerService) {
        this.mService = dreamManagerService;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public int onCommand(java.lang.String str) {
        boolean z;
        android.util.Slog.d(TAG, "onCommand:" + str);
        try {
            switch (str.hashCode()) {
                case -183711126:
                    if (str.equals("stop-dreaming")) {
                        z = true;
                        break;
                    }
                    z = -1;
                    break;
                case 1473640970:
                    if (str.equals("start-dreaming")) {
                        z = false;
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
                    enforceCallerIsRoot();
                    return startDreaming();
                case true:
                    enforceCallerIsRoot();
                    return stopDreaming();
                default:
                    return super.handleDefaultCommands(str);
            }
        } catch (java.lang.SecurityException e) {
            getOutPrintWriter().println(e);
            return -1;
        }
    }

    private int startDreaming() {
        this.mService.requestStartDreamFromShell();
        return 0;
    }

    private int stopDreaming() {
        this.mService.requestStopDreamFromShell();
        return 0;
    }

    private void enforceCallerIsRoot() {
        if (android.os.Binder.getCallingUid() != 0) {
            throw new java.lang.SecurityException("Must be root to call Dream shell commands");
        }
    }

    public void onHelp() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Dream manager (dreams) commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("      Print this help text.");
        outPrintWriter.println("  start-dreaming");
        outPrintWriter.println("      Start the currently configured dream.");
        outPrintWriter.println("  stop-dreaming");
        outPrintWriter.println("      Stops any active dream");
    }
}
