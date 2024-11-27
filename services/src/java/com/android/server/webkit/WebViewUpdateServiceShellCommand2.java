package com.android.server.webkit;

/* loaded from: classes.dex */
class WebViewUpdateServiceShellCommand2 extends android.os.ShellCommand {
    final android.webkit.IWebViewUpdateService mInterface;

    WebViewUpdateServiceShellCommand2(android.webkit.IWebViewUpdateService iWebViewUpdateService) {
        this.mInterface = iWebViewUpdateService;
    }

    public int onCommand(java.lang.String str) {
        boolean z;
        if (str == null) {
            return handleDefaultCommands(str);
        }
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        try {
            switch (str.hashCode()) {
                case -1381305903:
                    if (str.equals("set-webview-implementation")) {
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
        outPrintWriter.println(android.text.TextUtils.formatSimple("Failed to switch to %s, the WebView implementation is now provided by %s.", new java.lang.Object[]{nextArg, changeProviderAndSetting}));
        return 1;
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
        outPrintWriter.println();
    }
}
