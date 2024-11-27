package com.android.server.musicrecognition;

/* loaded from: classes2.dex */
class MusicRecognitionManagerServiceShellCommand extends android.os.ShellCommand {
    private final com.android.server.musicrecognition.MusicRecognitionManagerService mService;

    MusicRecognitionManagerServiceShellCommand(com.android.server.musicrecognition.MusicRecognitionManagerService musicRecognitionManagerService) {
        this.mService = musicRecognitionManagerService;
    }

    public int onCommand(java.lang.String str) {
        if (str == null) {
            return handleDefaultCommands(str);
        }
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        if ("set".equals(str)) {
            return requestSet(outPrintWriter);
        }
        return handleDefaultCommands(str);
    }

    private int requestSet(java.io.PrintWriter printWriter) {
        java.lang.String nextArgRequired = getNextArgRequired();
        if ("temporary-service".equals(nextArgRequired)) {
            return setTemporaryService(printWriter);
        }
        printWriter.println("Invalid set: " + nextArgRequired);
        return -1;
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
        printWriter.println("MusicRecognitionService temporarily set to " + nextArg + " for " + parseInt2 + "ms");
        return 0;
    }

    public void onHelp() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        try {
            outPrintWriter.println("MusicRecognition Service (music_recognition) commands:");
            outPrintWriter.println("  help");
            outPrintWriter.println("    Prints this help text.");
            outPrintWriter.println("");
            outPrintWriter.println("  set temporary-service USER_ID [COMPONENT_NAME DURATION]");
            outPrintWriter.println("    Temporarily (for DURATION ms) changes the service implementation.");
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
