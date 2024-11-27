package com.android.server.timedetector;

/* loaded from: classes2.dex */
class GnssTimeUpdateServiceShellCommand extends android.os.ShellCommand {
    private static final java.lang.String SHELL_COMMAND_SERVICE_NAME = "gnss_time_update_service";
    private static final java.lang.String SHELL_COMMAND_START_GNSS_LISTENING = "start_gnss_listening";

    @android.annotation.NonNull
    private final com.android.server.timedetector.GnssTimeUpdateService mGnssTimeUpdateService;

    GnssTimeUpdateServiceShellCommand(com.android.server.timedetector.GnssTimeUpdateService gnssTimeUpdateService) {
        java.util.Objects.requireNonNull(gnssTimeUpdateService);
        this.mGnssTimeUpdateService = gnssTimeUpdateService;
    }

    public int onCommand(java.lang.String str) {
        char c;
        if (str == null) {
            return handleDefaultCommands(str);
        }
        switch (str.hashCode()) {
            case 1191671168:
                if (str.equals(SHELL_COMMAND_START_GNSS_LISTENING)) {
                    c = 0;
                    break;
                }
            default:
                c = 65535;
                break;
        }
        switch (c) {
        }
        return handleDefaultCommands(str);
    }

    private int runStartGnssListening() {
        getOutPrintWriter().println(this.mGnssTimeUpdateService.startGnssListening());
        return 0;
    }

    public void onHelp() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.printf("Network Time Update Service (%s) commands:\n", SHELL_COMMAND_SERVICE_NAME);
        outPrintWriter.printf("  help\n", new java.lang.Object[0]);
        outPrintWriter.printf("    Print this help text.\n", new java.lang.Object[0]);
        outPrintWriter.printf("  %s\n", SHELL_COMMAND_START_GNSS_LISTENING);
        outPrintWriter.printf("    Forces the service in to GNSS listening mode (if it isn't already).\n", new java.lang.Object[0]);
        outPrintWriter.printf("    Prints true if the service is listening after this command.\n", new java.lang.Object[0]);
        outPrintWriter.println();
    }
}
