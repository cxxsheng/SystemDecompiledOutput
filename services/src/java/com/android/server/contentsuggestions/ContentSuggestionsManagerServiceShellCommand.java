package com.android.server.contentsuggestions;

/* loaded from: classes.dex */
public class ContentSuggestionsManagerServiceShellCommand extends android.os.ShellCommand {
    private static final java.lang.String TAG = com.android.server.contentsuggestions.ContentSuggestionsManagerServiceShellCommand.class.getSimpleName();
    private final com.android.server.contentsuggestions.ContentSuggestionsManagerService mService;

    public ContentSuggestionsManagerServiceShellCommand(@android.annotation.NonNull com.android.server.contentsuggestions.ContentSuggestionsManagerService contentSuggestionsManagerService) {
        this.mService = contentSuggestionsManagerService;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public int onCommand(java.lang.String str) {
        char c;
        if (str == null) {
            return handleDefaultCommands(str);
        }
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        switch (str.hashCode()) {
            case 102230:
                if (str.equals("get")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 113762:
                if (str.equals("set")) {
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
        }
        return handleDefaultCommands(str);
    }

    public void onHelp() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        try {
            outPrintWriter.println("ContentSuggestionsManagerService commands:");
            outPrintWriter.println("  help");
            outPrintWriter.println("    Prints this help text.");
            outPrintWriter.println("");
            outPrintWriter.println("  set temporary-service USER_ID [COMPONENT_NAME DURATION]");
            outPrintWriter.println("    Temporarily (for DURATION ms) changes the service implementation.");
            outPrintWriter.println("    To reset, call with just the USER_ID argument.");
            outPrintWriter.println("");
            outPrintWriter.println("  set default-service-enabled USER_ID [true|false]");
            outPrintWriter.println("    Enable / disable the default service for the user.");
            outPrintWriter.println("");
            outPrintWriter.println("  get default-service-enabled USER_ID");
            outPrintWriter.println("    Checks whether the default service is enabled for the user.");
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

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int requestSet(java.io.PrintWriter printWriter) {
        boolean z;
        java.lang.String nextArgRequired = getNextArgRequired();
        switch (nextArgRequired.hashCode()) {
            case 529654941:
                if (nextArgRequired.equals("default-service-enabled")) {
                    z = true;
                    break;
                }
                z = -1;
                break;
            case 2003978041:
                if (nextArgRequired.equals("temporary-service")) {
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
                return setTemporaryService(printWriter);
            case true:
                return setDefaultServiceEnabled();
            default:
                printWriter.println("Invalid set: " + nextArgRequired);
                return -1;
        }
    }

    private int requestGet(java.io.PrintWriter printWriter) {
        boolean z;
        java.lang.String nextArgRequired = getNextArgRequired();
        switch (nextArgRequired.hashCode()) {
            case 529654941:
                if (nextArgRequired.equals("default-service-enabled")) {
                    z = false;
                    break;
                }
            default:
                z = -1;
                break;
        }
        switch (z) {
            case false:
                return getDefaultServiceEnabled(printWriter);
            default:
                printWriter.println("Invalid get: " + nextArgRequired);
                return -1;
        }
    }

    private int setTemporaryService(java.io.PrintWriter printWriter) {
        int parseInt = java.lang.Integer.parseInt(getNextArgRequired());
        java.lang.String nextArg = getNextArg();
        if (nextArg == null) {
            this.mService.resetTemporaryService(parseInt);
            return 0;
        }
        int parseInt2 = java.lang.Integer.parseInt(getNextArgRequired());
        this.mService.setTemporaryService(parseInt, nextArg, parseInt2);
        printWriter.println("ContentSuggestionsService temporarily set to " + nextArg + " for " + parseInt2 + "ms");
        return 0;
    }

    private int setDefaultServiceEnabled() {
        this.mService.setDefaultServiceEnabled(getNextIntArgRequired(), java.lang.Boolean.parseBoolean(getNextArg()));
        return 0;
    }

    private int getDefaultServiceEnabled(java.io.PrintWriter printWriter) {
        printWriter.println(this.mService.isDefaultServiceEnabled(getNextIntArgRequired()));
        return 0;
    }

    private int getNextIntArgRequired() {
        return java.lang.Integer.parseInt(getNextArgRequired());
    }
}
