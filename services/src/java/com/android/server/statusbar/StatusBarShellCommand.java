package com.android.server.statusbar;

/* loaded from: classes2.dex */
public class StatusBarShellCommand extends android.os.ShellCommand {
    private static final android.os.IBinder sToken = new com.android.server.statusbar.StatusBarShellCommand.StatusBarShellCommandToken();
    private final android.content.Context mContext;
    private final com.android.server.statusbar.StatusBarManagerService mInterface;

    public StatusBarShellCommand(com.android.server.statusbar.StatusBarManagerService statusBarManagerService, android.content.Context context) {
        this.mInterface = statusBarManagerService;
        this.mContext = context;
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
                case -1282000806:
                    if (str.equals("add-tile")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case -1239176554:
                    if (str.equals("get-status-icons")) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case -1067396926:
                    if (str.equals("tracing")) {
                        c = 11;
                        break;
                    }
                    c = 65535;
                    break;
                case -1052548778:
                    if (str.equals("send-disable-flag")) {
                        c = '\n';
                        break;
                    }
                    c = 65535;
                    break;
                case -919868578:
                    if (str.equals("run-gc")) {
                        c = '\f';
                        break;
                    }
                    c = 65535;
                    break;
                case -823073837:
                    if (str.equals("click-tile")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case -632085587:
                    if (str.equals("collapse")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case -498761126:
                    if (str.equals("set-tiles")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case -339726761:
                    if (str.equals("remove-tile")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 1499:
                    if (str.equals("-h")) {
                        c = '\r';
                        break;
                    }
                    c = 65535;
                    break;
                case 3095028:
                    if (str.equals("dump")) {
                        c = 15;
                        break;
                    }
                    c = 65535;
                    break;
                case 3198785:
                    if (str.equals("help")) {
                        c = 14;
                        break;
                    }
                    c = 65535;
                    break;
                case 901899220:
                    if (str.equals("disable-for-setup")) {
                        c = '\t';
                        break;
                    }
                    c = 65535;
                    break;
                case 1612300298:
                    if (str.equals("check-support")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 1629310709:
                    if (str.equals("expand-notifications")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 1672031734:
                    if (str.equals("expand-settings")) {
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
                    return runExpandNotifications();
                case 1:
                    return runExpandSettings();
                case 2:
                    return runCollapse();
                case 3:
                    return runAddTile();
                case 4:
                    return runRemoveTile();
                case 5:
                    return runSetTiles();
                case 6:
                    return runClickTile();
                case 7:
                    getOutPrintWriter().println(java.lang.String.valueOf(android.service.quicksettings.TileService.isQuickSettingsSupported()));
                    return 0;
                case '\b':
                    return runGetStatusIcons();
                case '\t':
                    return runDisableForSetup();
                case '\n':
                    return runSendDisableFlag();
                case 11:
                    return runTracing();
                case '\f':
                    return runGc();
                case '\r':
                case 14:
                    onHelp();
                    return 0;
                case 15:
                    return super.handleDefaultCommands(str);
                default:
                    return runPassArgsToStatusBar();
            }
        } catch (android.os.RemoteException e) {
            getOutPrintWriter().println("Remote exception: " + e);
            return -1;
        }
    }

    private int runAddTile() throws android.os.RemoteException {
        this.mInterface.addTile(android.content.ComponentName.unflattenFromString(getNextArgRequired()));
        return 0;
    }

    private int runRemoveTile() throws android.os.RemoteException {
        this.mInterface.remTile(android.content.ComponentName.unflattenFromString(getNextArgRequired()));
        return 0;
    }

    private int runSetTiles() throws android.os.RemoteException {
        this.mInterface.setTiles(getNextArgRequired());
        return 0;
    }

    private int runClickTile() throws android.os.RemoteException {
        this.mInterface.clickTile(android.content.ComponentName.unflattenFromString(getNextArgRequired()));
        return 0;
    }

    private int runCollapse() throws android.os.RemoteException {
        this.mInterface.collapsePanels();
        return 0;
    }

    private int runExpandSettings() throws android.os.RemoteException {
        this.mInterface.expandSettingsPanel(null);
        return 0;
    }

    private int runExpandNotifications() throws android.os.RemoteException {
        this.mInterface.expandNotificationsPanel();
        return 0;
    }

    private int runGetStatusIcons() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        for (java.lang.String str : this.mInterface.getStatusBarIcons()) {
            outPrintWriter.println(str);
        }
        return 0;
    }

    private int runDisableForSetup() {
        java.lang.String nextArgRequired = getNextArgRequired();
        java.lang.String packageName = this.mContext.getPackageName();
        if (!java.lang.Boolean.parseBoolean(nextArgRequired)) {
            this.mInterface.disable(0, sToken, packageName);
            this.mInterface.disable2(0, sToken, packageName);
        } else {
            this.mInterface.disable(61145088, sToken, packageName);
            this.mInterface.disable2(0, sToken, packageName);
        }
        return 0;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0064, code lost:
    
        if (r2.equals("search") != false) goto L33;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int runSendDisableFlag() {
        java.lang.String packageName = this.mContext.getPackageName();
        android.app.StatusBarManager.DisableInfo disableInfo = new android.app.StatusBarManager.DisableInfo();
        java.lang.String nextArg = getNextArg();
        while (true) {
            char c = 0;
            if (nextArg != null) {
                switch (nextArg.hashCode()) {
                    case -1786496516:
                        if (nextArg.equals("system-icons")) {
                            c = 5;
                            break;
                        }
                        c = 65535;
                        break;
                    case -906336856:
                        break;
                    case -755976775:
                        if (nextArg.equals("notification-alerts")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    case 3208415:
                        if (nextArg.equals("home")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 94755854:
                        if (nextArg.equals("clock")) {
                            c = 6;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1011652819:
                        if (nextArg.equals("statusbar-expansion")) {
                            c = 4;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1082295672:
                        if (nextArg.equals(com.android.server.wm.ActivityTaskManagerService.DUMP_RECENTS_CMD)) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1368216504:
                        if (nextArg.equals("notification-icons")) {
                            c = 7;
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
                        disableInfo.setSearchDisabled(true);
                        break;
                    case 1:
                        disableInfo.setNagivationHomeDisabled(true);
                        break;
                    case 2:
                        disableInfo.setRecentsDisabled(true);
                        break;
                    case 3:
                        disableInfo.setNotificationPeekingDisabled(true);
                        break;
                    case 4:
                        disableInfo.setStatusBarExpansionDisabled(true);
                        break;
                    case 5:
                        disableInfo.setSystemIconsDisabled(true);
                        break;
                    case 6:
                        disableInfo.setClockDisabled(true);
                        break;
                    case 7:
                        disableInfo.setNotificationIconsDisabled(true);
                        break;
                }
                nextArg = getNextArg();
            } else {
                android.util.Pair flags = disableInfo.toFlags();
                this.mInterface.disable(((java.lang.Integer) flags.first).intValue(), sToken, packageName);
                this.mInterface.disable2(((java.lang.Integer) flags.second).intValue(), sToken, packageName);
                return 0;
            }
        }
    }

    private int runPassArgsToStatusBar() {
        this.mInterface.passThroughShellCommand(getAllArgs(), getOutFileDescriptor());
        return 0;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0034, code lost:
    
        return 0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int runTracing() {
        char c;
        java.lang.String nextArg = getNextArg();
        switch (nextArg.hashCode()) {
            case 3540994:
                if (nextArg.equals("stop")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 109757538:
                if (nextArg.equals("start")) {
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
                this.mInterface.startTracing();
                break;
            case 1:
                this.mInterface.stopTracing();
                break;
        }
    }

    private int runGc() {
        this.mInterface.runGcForTest();
        return 0;
    }

    public void onHelp() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Status bar commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println("");
        outPrintWriter.println("  expand-notifications");
        outPrintWriter.println("    Open the notifications panel.");
        outPrintWriter.println("");
        outPrintWriter.println("  expand-settings");
        outPrintWriter.println("    Open the notifications panel and expand quick settings if present.");
        outPrintWriter.println("");
        outPrintWriter.println("  collapse");
        outPrintWriter.println("    Collapse the notifications and settings panel.");
        outPrintWriter.println("");
        outPrintWriter.println("  add-tile COMPONENT");
        outPrintWriter.println("    Add a TileService of the specified component");
        outPrintWriter.println("");
        outPrintWriter.println("  remove-tile COMPONENT");
        outPrintWriter.println("    Remove a TileService of the specified component");
        outPrintWriter.println("");
        outPrintWriter.println("  set-tiles LIST-OF-TILES");
        outPrintWriter.println("    Sets the list of tiles as the current Quick Settings tiles");
        outPrintWriter.println("");
        outPrintWriter.println("  click-tile COMPONENT");
        outPrintWriter.println("    Click on a TileService of the specified component");
        outPrintWriter.println("");
        outPrintWriter.println("  check-support");
        outPrintWriter.println("    Check if this device supports QS + APIs");
        outPrintWriter.println("");
        outPrintWriter.println("  get-status-icons");
        outPrintWriter.println("    Print the list of status bar icons and the order they appear in");
        outPrintWriter.println("");
        outPrintWriter.println("  disable-for-setup DISABLE");
        outPrintWriter.println("    If true, disable status bar components unsuitable for device setup");
        outPrintWriter.println("");
        outPrintWriter.println("  send-disable-flag FLAG...");
        outPrintWriter.println("    Send zero or more disable flags (parsed individually) to StatusBarManager");
        outPrintWriter.println("    Valid options:");
        outPrintWriter.println("        <blank>             - equivalent to \"none\"");
        outPrintWriter.println("        none                - re-enables all components");
        outPrintWriter.println("        search              - disable search");
        outPrintWriter.println("        home                - disable naviagation home");
        outPrintWriter.println("        recents             - disable recents/overview");
        outPrintWriter.println("        notification-peek   - disable notification peeking");
        outPrintWriter.println("        statusbar-expansion - disable status bar expansion");
        outPrintWriter.println("        system-icons        - disable system icons appearing in status bar");
        outPrintWriter.println("        clock               - disable clock appearing in status bar");
        outPrintWriter.println("        notification-icons  - disable notification icons from status bar");
        outPrintWriter.println("");
        outPrintWriter.println("  tracing (start | stop)");
        outPrintWriter.println("    Start or stop SystemUI tracing");
        outPrintWriter.println("");
        outPrintWriter.println("  NOTE: any command not listed here will be passed through to IStatusBar");
        outPrintWriter.println("");
        outPrintWriter.println("  Commands implemented in SystemUI:");
        outPrintWriter.flush();
        this.mInterface.passThroughShellCommand(new java.lang.String[0], getOutFileDescriptor());
    }

    private static final class StatusBarShellCommandToken extends android.os.Binder {
        private StatusBarShellCommandToken() {
        }
    }
}
