package com.android.server.webkit;

/* loaded from: classes.dex */
class WebViewUpdateServiceShellCommand extends android.os.ShellCommand {
    final android.webkit.IWebViewUpdateService mInterface;

    WebViewUpdateServiceShellCommand(android.webkit.IWebViewUpdateService iWebViewUpdateService) {
        this.mInterface = iWebViewUpdateService;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public int onCommand(java.lang.String str) {
        char c;
        if (str == null) {
            return handleDefaultCommands(str);
        }
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        try {
            switch (str.hashCode()) {
                case -1857752288:
                    if (str.equals("enable-multiprocess")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case -1381305903:
                    if (str.equals("set-webview-implementation")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 436183515:
                    if (str.equals("disable-multiprocess")) {
                        c = 2;
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
        } catch (android.os.RemoteException e) {
            outPrintWriter.println("Remote exception: " + e);
            return -1;
        }
        return handleDefaultCommands(str);
    }

    private int setWebViewImplementation() throws android.os.RemoteException {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        java.lang.String nextArg = getNextArg();
        if (nextArg == null) {
            outPrintWriter.println("Failed to switch, no PACKAGE provided.");
            outPrintWriter.println("");
            helpSetWebViewImplementation();
            return 1;
        }
        java.lang.String changeProviderAndSetting = this.mInterface.changeProviderAndSetting(nextArg);
        if (nextArg.equals(changeProviderAndSetting)) {
            outPrintWriter.println("Success");
            return 0;
        }
        outPrintWriter.println(java.lang.String.format("Failed to switch to %s, the WebView implementation is now provided by %s.", nextArg, changeProviderAndSetting));
        return 1;
    }

    private int enableMultiProcess(boolean z) throws android.os.RemoteException {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        this.mInterface.enableMultiProcess(z);
        outPrintWriter.println("Success");
        return 0;
    }

    public void helpSetWebViewImplementation() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("  set-webview-implementation PACKAGE");
        outPrintWriter.println("    Set the WebView implementation to the specified package.");
    }

    public void onHelp() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("WebView updater commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println("");
        helpSetWebViewImplementation();
        outPrintWriter.println("  enable-multiprocess");
        outPrintWriter.println("    Enable multi-process mode for WebView");
        outPrintWriter.println("  disable-multiprocess");
        outPrintWriter.println("    Disable multi-process mode for WebView");
        outPrintWriter.println();
    }
}
