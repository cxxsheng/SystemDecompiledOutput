package com.android.server.biometrics.sensors.fingerprint;

/* loaded from: classes.dex */
public class FingerprintShellCommand extends android.os.ShellCommand {
    private final android.content.Context mContext;
    private final com.android.server.biometrics.sensors.fingerprint.FingerprintService mService;

    public FingerprintShellCommand(android.content.Context context, com.android.server.biometrics.sensors.fingerprint.FingerprintService fingerprintService) {
        this.mContext = context;
        this.mService = fingerprintService;
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
                case -1014576245:
                    if (str.equals("fingerdown")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
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
                        c = 3;
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
                return doSimulateVhalFingerDown();
            case 3:
                return doNotify();
            default:
                getOutPrintWriter().println("Unrecognized command: " + str);
                return -1;
        }
    }

    public void onHelp() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Fingerprint Service commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("      Print this help text.");
        outPrintWriter.println("  sync");
        outPrintWriter.println("      Sync enrollments now (virtualized sensors only).");
        outPrintWriter.println("  fingerdown");
        outPrintWriter.println("      Simulate finger down event (virtualized sensors only).");
        outPrintWriter.println("  notification");
        outPrintWriter.println("     Sends a Fingerprint re-enrollment notification");
    }

    private int doHelp() {
        onHelp();
        return 0;
    }

    private int doSync() {
        this.mService.syncEnrollmentsNow();
        return 0;
    }

    private int doSimulateVhalFingerDown() {
        this.mService.simulateVhalFingerDown();
        return 0;
    }

    private int doNotify() {
        this.mService.sendFingerprintReEnrollNotification();
        return 0;
    }
}
