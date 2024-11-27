package com.android.server.timedetector;

/* loaded from: classes2.dex */
class NetworkTimeUpdateServiceShellCommand extends android.os.ShellCommand {
    private static final java.lang.String SET_SERVER_CONFIG_SERVER_ARG = "--server";
    private static final java.lang.String SET_SERVER_CONFIG_TIMEOUT_ARG = "--timeout_millis";
    private static final java.lang.String SHELL_COMMAND_FORCE_REFRESH = "force_refresh";
    private static final java.lang.String SHELL_COMMAND_RESET_SERVER_CONFIG = "reset_server_config_for_tests";
    private static final java.lang.String SHELL_COMMAND_SERVICE_NAME = "network_time_update_service";
    private static final java.lang.String SHELL_COMMAND_SET_SERVER_CONFIG = "set_server_config_for_tests";

    @android.annotation.NonNull
    private final com.android.server.timedetector.NetworkTimeUpdateService mNetworkTimeUpdateService;

    NetworkTimeUpdateServiceShellCommand(com.android.server.timedetector.NetworkTimeUpdateService networkTimeUpdateService) {
        java.util.Objects.requireNonNull(networkTimeUpdateService);
        this.mNetworkTimeUpdateService = networkTimeUpdateService;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public int onCommand(java.lang.String str) {
        char c;
        if (str == null) {
            return handleDefaultCommands(str);
        }
        switch (str.hashCode()) {
            case -1679617267:
                if (str.equals(SHELL_COMMAND_SET_SERVER_CONFIG)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 65977594:
                if (str.equals(SHELL_COMMAND_RESET_SERVER_CONFIG)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 1891346823:
                if (str.equals(SHELL_COMMAND_FORCE_REFRESH)) {
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

    private int runForceRefresh() {
        getOutPrintWriter().println(this.mNetworkTimeUpdateService.forceRefreshForTests());
        return 0;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x001b, code lost:
    
        if (r2.equals(com.android.server.timedetector.NetworkTimeUpdateServiceShellCommand.SET_SERVER_CONFIG_SERVER_ARG) != false) goto L15;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int runSetServerConfig() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.time.Duration duration = null;
        while (true) {
            java.lang.String nextArg = getNextArg();
            char c = 0;
            if (nextArg != null) {
                switch (nextArg.hashCode()) {
                    case -975021948:
                        if (nextArg.equals(SET_SERVER_CONFIG_TIMEOUT_ARG)) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1494187235:
                        break;
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                        try {
                            arrayList.add(android.util.NtpTrustedTime.parseNtpUriStrict(getNextArgRequired()));
                            break;
                        } catch (java.net.URISyntaxException e) {
                            throw new java.lang.IllegalArgumentException("Bad NTP server value", e);
                        }
                    case 1:
                        duration = java.time.Duration.ofMillis(java.lang.Integer.parseInt(getNextArgRequired()));
                        break;
                    default:
                        throw new java.lang.IllegalArgumentException("Unknown option: " + nextArg);
                }
            } else {
                if (arrayList.isEmpty()) {
                    throw new java.lang.IllegalArgumentException("Missing required option: ----server");
                }
                if (duration == null) {
                    throw new java.lang.IllegalArgumentException("Missing required option: ----timeout_millis");
                }
                this.mNetworkTimeUpdateService.setServerConfigForTests(new android.util.NtpTrustedTime.NtpConfig(arrayList, duration));
                return 0;
            }
        }
    }

    private int runResetServerConfig() {
        this.mNetworkTimeUpdateService.setServerConfigForTests(null);
        return 0;
    }

    public void onHelp() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.printf("Network Time Update Service (%s) commands:\n", SHELL_COMMAND_SERVICE_NAME);
        outPrintWriter.printf("  help\n", new java.lang.Object[0]);
        outPrintWriter.printf("    Print this help text.\n", new java.lang.Object[0]);
        outPrintWriter.printf("  %s\n", SHELL_COMMAND_FORCE_REFRESH);
        outPrintWriter.printf("    Refreshes the latest time. Prints whether it was successful.\n", new java.lang.Object[0]);
        outPrintWriter.printf("  %s\n", SHELL_COMMAND_SET_SERVER_CONFIG);
        outPrintWriter.printf("    Sets the NTP server config for tests. The config is not persisted.\n", new java.lang.Object[0]);
        outPrintWriter.printf("      Options: %s <uri> [%s <additional uris>]+ %s <millis>\n", SET_SERVER_CONFIG_SERVER_ARG, SET_SERVER_CONFIG_SERVER_ARG, SET_SERVER_CONFIG_TIMEOUT_ARG);
        outPrintWriter.printf("      NTP server URIs must be in the form \"ntp://hostname\" or \"ntp://hostname:port\"\n", new java.lang.Object[0]);
        outPrintWriter.printf("  %s\n", SHELL_COMMAND_RESET_SERVER_CONFIG);
        outPrintWriter.printf("    Resets/clears the NTP server config set via %s.\n", SHELL_COMMAND_SET_SERVER_CONFIG);
        outPrintWriter.println();
    }
}
