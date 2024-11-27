package com.android.server.biometrics.sensors.face;

/* loaded from: classes.dex */
public class FaceShellCommand extends android.os.ShellCommand {
    private final com.android.server.biometrics.sensors.face.FaceService mService;

    public FaceShellCommand(com.android.server.biometrics.sensors.face.FaceService faceService) {
        this.mService = faceService;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public int onCommand(java.lang.String str) {
        char c = 1;
        if (str == null) {
            onHelp();
            return 1;
        }
        try {
            switch (str.hashCode()) {
                case 3198785:
                    if (str.equals("help")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 3545755:
                    if (str.equals("sync")) {
                        break;
                    }
                    c = 65535;
                    break;
                case 595233003:
                    if (str.equals("notification")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
        } catch (java.lang.Exception e) {
            getOutPrintWriter().println("Exception: " + e);
        }
        switch (c) {
            case 0:
                return doHelp();
            case 1:
                return doSync();
            case 2:
                return doNotify();
            default:
                getOutPrintWriter().println("Unrecognized command: " + str);
                return -1;
        }
    }

    public void onHelp() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Face Service commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("      Print this help text.");
        outPrintWriter.println("  sync");
        outPrintWriter.println("      Sync enrollments now (virtualized sensors only).");
        outPrintWriter.println("  notification");
        outPrintWriter.println("     Sends a Face re-enrollment notification");
    }

    private int doHelp() {
        onHelp();
        return 0;
    }

    private int doSync() {
        this.mService.syncEnrollmentsNow();
        return 0;
    }

    private int doNotify() {
        this.mService.sendFaceReEnrollNotification();
        return 0;
    }
}
