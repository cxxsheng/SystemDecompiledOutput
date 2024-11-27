package com.android.server.adb;

/* loaded from: classes.dex */
class AdbShellCommand extends com.android.modules.utils.BasicShellCommandHandler {
    private final com.android.server.adb.AdbService mService;

    AdbShellCommand(com.android.server.adb.AdbService adbService) {
        java.util.Objects.requireNonNull(adbService);
        this.mService = adbService;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public int onCommand(java.lang.String str) {
        char c;
        if (str == null) {
            return handleDefaultCommands((java.lang.String) null);
        }
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        switch (str.hashCode()) {
            case -138263081:
                if (str.equals("is-wifi-qr-supported")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 434812665:
                if (str.equals("is-wifi-supported")) {
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
                outPrintWriter.println(java.lang.Boolean.toString(this.mService.isAdbWifiSupported()));
                return 0;
            case 1:
                outPrintWriter.println(java.lang.Boolean.toString(this.mService.isAdbWifiQrSupported()));
                return 0;
            default:
                return handleDefaultCommands(str);
        }
    }

    public void onHelp() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Adb service commands:");
        outPrintWriter.println("  help or -h");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println("  is-wifi-supported");
        outPrintWriter.println("    Returns \"true\" if adb over wifi is supported.");
        outPrintWriter.println("  is-wifi-qr-supported");
        outPrintWriter.println("    Returns \"true\" if adb over wifi + QR pairing is supported.");
        outPrintWriter.println();
    }
}
