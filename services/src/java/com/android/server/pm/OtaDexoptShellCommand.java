package com.android.server.pm;

/* loaded from: classes2.dex */
class OtaDexoptShellCommand extends android.os.ShellCommand {
    final android.content.pm.IOtaDexopt mInterface;

    OtaDexoptShellCommand(com.android.server.pm.OtaDexoptService otaDexoptService) {
        this.mInterface = otaDexoptService;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public int onCommand(java.lang.String str) {
        char c;
        if (str == null) {
            return handleDefaultCommands((java.lang.String) null);
        }
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        try {
            switch (str.hashCode()) {
                case -1001078227:
                    if (str.equals("progress")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case -318370553:
                    if (str.equals("prepare")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 3089282:
                    if (str.equals("done")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 3377907:
                    if (str.equals("next")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 3540684:
                    if (str.equals("step")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 856774308:
                    if (str.equals("cleanup")) {
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
                case 0:
                    return runOtaPrepare();
                case 1:
                    return runOtaCleanup();
                case 2:
                    return runOtaDone();
                case 3:
                    return runOtaStep();
                case 4:
                    return runOtaNext();
                case 5:
                    return runOtaProgress();
                default:
                    return handleDefaultCommands(str);
            }
        } catch (android.os.RemoteException e) {
            outPrintWriter.println("Remote exception: " + e);
            return -1;
        }
    }

    private int runOtaPrepare() throws android.os.RemoteException {
        this.mInterface.prepare();
        getOutPrintWriter().println("Success");
        return 0;
    }

    private int runOtaCleanup() throws android.os.RemoteException {
        this.mInterface.cleanup();
        return 0;
    }

    private int runOtaDone() throws android.os.RemoteException {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        if (this.mInterface.isDone()) {
            outPrintWriter.println("OTA complete.");
            return 0;
        }
        outPrintWriter.println("OTA incomplete.");
        return 0;
    }

    private int runOtaStep() throws android.os.RemoteException {
        this.mInterface.dexoptNextPackage();
        return 0;
    }

    private int runOtaNext() throws android.os.RemoteException {
        getOutPrintWriter().println(this.mInterface.nextDexoptCommand());
        return 0;
    }

    private int runOtaProgress() throws android.os.RemoteException {
        getOutPrintWriter().format(java.util.Locale.ROOT, "%.2f", java.lang.Float.valueOf(this.mInterface.getProgress()));
        return 0;
    }

    public void onHelp() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("OTA Dexopt (ota) commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println("");
        outPrintWriter.println("  prepare");
        outPrintWriter.println("    Prepare an OTA dexopt pass, collecting all packages.");
        outPrintWriter.println("  done");
        outPrintWriter.println("    Replies whether the OTA is complete or not.");
        outPrintWriter.println("  step");
        outPrintWriter.println("    OTA dexopt the next package.");
        outPrintWriter.println("  next");
        outPrintWriter.println("    Get parameters for OTA dexopt of the next package.");
        outPrintWriter.println("  cleanup");
        outPrintWriter.println("    Clean up internal states. Ends an OTA session.");
    }
}
