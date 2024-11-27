package com.android.server.am;

/* loaded from: classes.dex */
final class ActivityManagerShellCommand extends android.os.ShellCommand {
    public static final java.lang.String NO_CLASS_ERROR_CODE = "Error type 3";
    private static final java.lang.String SHELL_PACKAGE_NAME = "com.android.shell";
    static final java.lang.String TAG = "ActivityManager";
    private static final int USER_OPERATION_TIMEOUT_MS = 120000;
    private int mActivityType;
    private java.lang.String mAgent;
    private boolean mAsync;
    private boolean mAttachAgentDuringBind;
    private boolean mAutoStop;
    private android.app.BroadcastOptions mBroadcastOptions;
    private int mClockType;
    private boolean mDismissKeyguardIfInsecure;
    private int mDisplayId;
    final boolean mDumping;
    final android.app.IActivityManager mInterface;
    final com.android.server.am.ActivityManagerService mInternal;
    private boolean mIsLockTask;
    private boolean mIsTaskOverlay;
    private java.lang.String mProfileFile;
    private java.lang.String mReceiverPermission;
    private int mSamplingInterval;
    private boolean mShowSplashScreen;
    private boolean mStreaming;
    private int mTaskDisplayAreaFeatureId;
    private int mTaskId;
    final android.app.IActivityTaskManager mTaskInterface;
    private int mUserId;
    private int mWindowingMode;
    private static final java.time.format.DateTimeFormatter LOG_NAME_TIME_FORMATTER = java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss", java.util.Locale.ROOT);
    private static final java.lang.String[] CAPABILITIES = {"start.suspend"};
    private int mStartFlags = 0;
    private boolean mWaitOption = false;
    private boolean mStopOption = false;
    private int mRepeat = 0;
    final android.content.pm.IPackageManager mPm = android.app.AppGlobals.getPackageManager();

    ActivityManagerShellCommand(com.android.server.am.ActivityManagerService activityManagerService, boolean z) {
        this.mInterface = activityManagerService;
        this.mTaskInterface = activityManagerService.mActivityTaskManager;
        this.mInternal = activityManagerService;
        this.mDumping = z;
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
                case -2121667104:
                    if (str.equals("dumpheap")) {
                        c = 18;
                        break;
                    }
                    c = 65535;
                    break;
                case -1969672196:
                    if (str.equals("set-debug-app")) {
                        c = 19;
                        break;
                    }
                    c = 65535;
                    break;
                case -1864717225:
                    if (str.equals("set-deterministic-uid-idle")) {
                        c = '#';
                        break;
                    }
                    c = 65535;
                    break;
                case -1860393403:
                    if (str.equals("get-isolated-pids")) {
                        c = 'R';
                        break;
                    }
                    c = 65535;
                    break;
                case -1719979774:
                    if (str.equals("get-inactive")) {
                        c = ';';
                        break;
                    }
                    c = 65535;
                    break;
                case -1710503333:
                    if (str.equals("package-importance")) {
                        c = '*';
                        break;
                    }
                    c = 65535;
                    break;
                case -1667670943:
                    if (str.equals("get-standby-bucket")) {
                        c = '=';
                        break;
                    }
                    c = 65535;
                    break;
                case -1619282346:
                    if (str.equals("start-user")) {
                        c = '0';
                        break;
                    }
                    c = 65535;
                    break;
                case -1618876223:
                    if (str.equals("broadcast")) {
                        c = 11;
                        break;
                    }
                    c = 65535;
                    break;
                case -1514943892:
                    if (str.equals("list-displays-for-starting-users")) {
                        c = 'Z';
                        break;
                    }
                    c = 65535;
                    break;
                case -1487597642:
                    if (str.equals("capabilities")) {
                        c = '\\';
                        break;
                    }
                    c = 65535;
                    break;
                case -1470725846:
                    if (str.equals("reset-dropbox-rate-limiter")) {
                        c = 'Y';
                        break;
                    }
                    c = 65535;
                    break;
                case -1354812542:
                    if (str.equals("compat")) {
                        c = 'N';
                        break;
                    }
                    c = 65535;
                    break;
                case -1324660647:
                    if (str.equals("suppress-resize-config-changes")) {
                        c = '9';
                        break;
                    }
                    c = 65535;
                    break;
                case -1303445945:
                    if (str.equals("send-trim-memory")) {
                        c = '>';
                        break;
                    }
                    c = 65535;
                    break;
                case -1275145137:
                    if (str.equals("wait-for-broadcast-barrier")) {
                        c = 'I';
                        break;
                    }
                    c = 65535;
                    break;
                case -1266402665:
                    if (str.equals("freeze")) {
                        c = '\r';
                        break;
                    }
                    c = 65535;
                    break;
                case -1182154244:
                    if (str.equals("set-foreground-service-delegate")) {
                        c = '[';
                        break;
                    }
                    c = 65535;
                    break;
                case -1131287478:
                    if (str.equals("start-service")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case -1002578147:
                    if (str.equals("get-uid-state")) {
                        c = '7';
                        break;
                    }
                    c = 65535;
                    break;
                case -965273485:
                    if (str.equals("stopservice")) {
                        c = '\t';
                        break;
                    }
                    c = 65535;
                    break;
                case -930080590:
                    if (str.equals("startfgservice")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case -907667276:
                    if (str.equals("unlock-user")) {
                        c = '1';
                        break;
                    }
                    c = 65535;
                    break;
                case -892396682:
                    if (str.equals("start-foreground-service")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case -878494906:
                    if (str.equals("set-bg-restriction-level")) {
                        c = 'V';
                        break;
                    }
                    c = 65535;
                    break;
                case -870018278:
                    if (str.equals("to-uri")) {
                        c = '+';
                        break;
                    }
                    c = 65535;
                    break;
                case -812219210:
                    if (str.equals("get-current-user")) {
                        c = '/';
                        break;
                    }
                    c = 65535;
                    break;
                case -747637291:
                    if (str.equals("set-standby-bucket")) {
                        c = '<';
                        break;
                    }
                    c = 65535;
                    break;
                case -699625063:
                    if (str.equals("get-config")) {
                        c = '8';
                        break;
                    }
                    c = 65535;
                    break;
                case -656088391:
                    if (str.equals("clear-start-info")) {
                        c = 24;
                        break;
                    }
                    c = 65535;
                    break;
                case -606123342:
                    if (str.equals("kill-all")) {
                        c = '!';
                        break;
                    }
                    c = 65535;
                    break;
                case -548621938:
                    if (str.equals("is-user-stopped")) {
                        c = '3';
                        break;
                    }
                    c = 65535;
                    break;
                case -541939658:
                    if (str.equals("observe-foreground-process")) {
                        c = 'X';
                        break;
                    }
                    c = 65535;
                    break;
                case -443938379:
                    if (str.equals("fgs-notification-rate-limit")) {
                        c = 30;
                        break;
                    }
                    c = 65535;
                    break;
                case -387147436:
                    if (str.equals("track-associations")) {
                        c = '5';
                        break;
                    }
                    c = 65535;
                    break;
                case -379899280:
                    if (str.equals("unfreeze")) {
                        c = 14;
                        break;
                    }
                    c = 65535;
                    break;
                case -354890749:
                    if (str.equals("screen-compat")) {
                        c = ')';
                        break;
                    }
                    c = 65535;
                    break;
                case -309425751:
                    if (str.equals("profile")) {
                        c = 17;
                        break;
                    }
                    c = 65535;
                    break;
                case -225973678:
                    if (str.equals("service-restart-backoff")) {
                        c = 'Q';
                        break;
                    }
                    c = 65535;
                    break;
                case -170987146:
                    if (str.equals("set-inactive")) {
                        c = ':';
                        break;
                    }
                    c = 65535;
                    break;
                case -149941524:
                    if (str.equals("list-bg-exemptions-config")) {
                        c = 'U';
                        break;
                    }
                    c = 65535;
                    break;
                case -146027423:
                    if (str.equals("watch-uids")) {
                        c = '%';
                        break;
                    }
                    c = 65535;
                    break;
                case -138040195:
                    if (str.equals("clear-exit-info")) {
                        c = 25;
                        break;
                    }
                    c = 65535;
                    break;
                case -100644880:
                    if (str.equals("startforegroundservice")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case -74413870:
                    if (str.equals("get-bg-restriction-level")) {
                        c = 'W';
                        break;
                    }
                    c = 65535;
                    break;
                case -27715536:
                    if (str.equals("make-uid-idle")) {
                        c = '\"';
                        break;
                    }
                    c = 65535;
                    break;
                case 3194994:
                    if (str.equals("hang")) {
                        c = '&';
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
                case 3291998:
                    if (str.equals("kill")) {
                        c = ' ';
                        break;
                    }
                    c = 65535;
                    break;
                case 3552645:
                    if (str.equals("task")) {
                        c = 'A';
                        break;
                    }
                    c = 65535;
                    break;
                case 88586660:
                    if (str.equals("force-stop")) {
                        c = 27;
                        break;
                    }
                    c = 65535;
                    break;
                case 94921639:
                    if (str.equals("crash")) {
                        c = 31;
                        break;
                    }
                    c = 65535;
                    break;
                case 109757064:
                    if (str.equals("stack")) {
                        c = '@';
                        break;
                    }
                    c = 65535;
                    break;
                case 109757538:
                    if (str.equals("start")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 113399775:
                    if (str.equals("write")) {
                        c = 'B';
                        break;
                    }
                    c = 65535;
                    break;
                case 135017371:
                    if (str.equals("memory-factor")) {
                        c = 'P';
                        break;
                    }
                    c = 65535;
                    break;
                case 185053203:
                    if (str.equals("startservice")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 237240942:
                    if (str.equals("to-app-uri")) {
                        c = '-';
                        break;
                    }
                    c = 65535;
                    break;
                case 549617690:
                    if (str.equals("start-activity")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 622433197:
                    if (str.equals("untrack-associations")) {
                        c = '6';
                        break;
                    }
                    c = 65535;
                    break;
                case 661133534:
                    if (str.equals("wait-for-application-barrier")) {
                        c = 'J';
                        break;
                    }
                    c = 65535;
                    break;
                case 667014829:
                    if (str.equals("bug-report")) {
                        c = 26;
                        break;
                    }
                    c = 65535;
                    break;
                case 680834441:
                    if (str.equals("supports-split-screen-multi-window")) {
                        c = 'E';
                        break;
                    }
                    c = 65535;
                    break;
                case 723112852:
                    if (str.equals("trace-ipc")) {
                        c = 16;
                        break;
                    }
                    c = 65535;
                    break;
                case 764545184:
                    if (str.equals("supports-multiwindow")) {
                        c = 'D';
                        break;
                    }
                    c = 65535;
                    break;
                case 782722708:
                    if (str.equals("set-bg-abusive-uids")) {
                        c = 'T';
                        break;
                    }
                    c = 65535;
                    break;
                case 808179021:
                    if (str.equals("to-intent-uri")) {
                        c = ',';
                        break;
                    }
                    c = 65535;
                    break;
                case 810242677:
                    if (str.equals("set-watch-heap")) {
                        c = 22;
                        break;
                    }
                    c = 65535;
                    break;
                case 817137578:
                    if (str.equals("clear-watch-heap")) {
                        c = 23;
                        break;
                    }
                    c = 65535;
                    break;
                case 822490030:
                    if (str.equals("set-agent-app")) {
                        c = 20;
                        break;
                    }
                    c = 65535;
                    break;
                case 847202110:
                    if (str.equals("clear-ignore-delivery-group-policy")) {
                        c = 'M';
                        break;
                    }
                    c = 65535;
                    break;
                case 900455412:
                    if (str.equals("start-fg-service")) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case 950483747:
                    if (str.equals("compact")) {
                        c = '\f';
                        break;
                    }
                    c = 65535;
                    break;
                case 1024703869:
                    if (str.equals("attach-agent")) {
                        c = 'C';
                        break;
                    }
                    c = 65535;
                    break;
                case 1070798153:
                    if (str.equals("set-ignore-delivery-group-policy")) {
                        c = 'L';
                        break;
                    }
                    c = 65535;
                    break;
                case 1078591527:
                    if (str.equals("clear-debug-app")) {
                        c = 21;
                        break;
                    }
                    c = 65535;
                    break;
                case 1097506319:
                    if (str.equals(com.android.server.am.HostingRecord.HOSTING_TYPE_RESTART)) {
                        c = '\'';
                        break;
                    }
                    c = 65535;
                    break;
                case 1129261387:
                    if (str.equals("update-appinfo")) {
                        c = 'F';
                        break;
                    }
                    c = 65535;
                    break;
                case 1147479778:
                    if (str.equals("wait-for-broadcast-dispatch")) {
                        c = 'K';
                        break;
                    }
                    c = 65535;
                    break;
                case 1180451466:
                    if (str.equals("refresh-settings-cache")) {
                        c = 'O';
                        break;
                    }
                    c = 65535;
                    break;
                case 1219773618:
                    if (str.equals("get-started-user-state")) {
                        c = '4';
                        break;
                    }
                    c = 65535;
                    break;
                case 1236319578:
                    if (str.equals("monitor")) {
                        c = '$';
                        break;
                    }
                    c = 65535;
                    break;
                case 1395483623:
                    if (str.equals("instrument")) {
                        c = 15;
                        break;
                    }
                    c = 65535;
                    break;
                case 1583986358:
                    if (str.equals("stop-user")) {
                        c = '2';
                        break;
                    }
                    c = 65535;
                    break;
                case 1618908732:
                    if (str.equals("wait-for-broadcast-idle")) {
                        c = 'H';
                        break;
                    }
                    c = 65535;
                    break;
                case 1671764162:
                    if (str.equals("display")) {
                        c = '?';
                        break;
                    }
                    c = 65535;
                    break;
                case 1713645014:
                    if (str.equals("stop-app")) {
                        c = 28;
                        break;
                    }
                    c = 65535;
                    break;
                case 1768693408:
                    if (str.equals("set-stop-user-on-switch")) {
                        c = 'S';
                        break;
                    }
                    c = 65535;
                    break;
                case 1852789518:
                    if (str.equals("no-home-screen")) {
                        c = 'G';
                        break;
                    }
                    c = 65535;
                    break;
                case 1861559962:
                    if (str.equals("idle-maintenance")) {
                        c = '(';
                        break;
                    }
                    c = 65535;
                    break;
                case 1863290858:
                    if (str.equals("stop-service")) {
                        c = '\n';
                        break;
                    }
                    c = 65535;
                    break;
                case 2030969636:
                    if (str.equals("clear-recent-apps")) {
                        c = 29;
                        break;
                    }
                    c = 65535;
                    break;
                case 2083239620:
                    if (str.equals("switch-user")) {
                        c = '.';
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
                    onHelp();
                    break;
                case 15:
                    getOutPrintWriter().println("Error: must be invoked through 'am instrument'.");
                    break;
            }
        } catch (android.os.RemoteException e) {
            outPrintWriter.println("Remote exception: " + e);
            return -1;
        }
        return handleDefaultCommands(str);
    }

    int runCapabilities(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        java.io.PrintWriter errPrintWriter = getErrPrintWriter();
        boolean z = false;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                if (nextOption.equals("--protobuf")) {
                    z = true;
                } else {
                    errPrintWriter.println("Error: Unknown option: " + nextOption);
                    return -1;
                }
            } else {
                if (z) {
                    com.android.server.am.nano.Capabilities capabilities = new com.android.server.am.nano.Capabilities();
                    capabilities.values = new com.android.server.am.nano.Capability[CAPABILITIES.length];
                    for (int i = 0; i < CAPABILITIES.length; i++) {
                        com.android.server.am.nano.Capability capability = new com.android.server.am.nano.Capability();
                        capability.name = CAPABILITIES[i];
                        capabilities.values[i] = capability;
                    }
                    try {
                        getRawOutputStream().write(com.android.server.am.nano.Capabilities.toByteArray(capabilities));
                    } catch (java.io.IOException e) {
                        printWriter.println("Error while serializing capabilities protobuffer");
                    }
                } else {
                    printWriter.println("Format: 1");
                    for (java.lang.String str : CAPABILITIES) {
                        printWriter.println(str);
                    }
                }
                return 0;
            }
        }
    }

    private android.content.Intent makeIntent(int i) throws java.net.URISyntaxException {
        this.mStartFlags = 0;
        this.mWaitOption = false;
        this.mStopOption = false;
        this.mRepeat = 0;
        this.mProfileFile = null;
        this.mSamplingInterval = 0;
        this.mAutoStop = false;
        this.mStreaming = false;
        this.mUserId = i;
        this.mDisplayId = -1;
        this.mTaskDisplayAreaFeatureId = -1;
        this.mWindowingMode = 0;
        this.mActivityType = 0;
        this.mTaskId = -1;
        this.mIsTaskOverlay = false;
        this.mIsLockTask = false;
        this.mAsync = false;
        this.mBroadcastOptions = null;
        return android.content.Intent.parseCommandArgs(this, new android.content.Intent.CommandOptionHandler() { // from class: com.android.server.am.ActivityManagerShellCommand.1
            public boolean handleOption(java.lang.String str, android.os.ShellCommand shellCommand) {
                if (str.equals("-D")) {
                    com.android.server.am.ActivityManagerShellCommand.this.mStartFlags |= 2;
                } else if (str.equals("--suspend")) {
                    com.android.server.am.ActivityManagerShellCommand.this.mStartFlags |= 16;
                } else if (str.equals("-N")) {
                    com.android.server.am.ActivityManagerShellCommand.this.mStartFlags |= 8;
                } else if (str.equals("-W")) {
                    com.android.server.am.ActivityManagerShellCommand.this.mWaitOption = true;
                } else if (str.equals("-P")) {
                    com.android.server.am.ActivityManagerShellCommand.this.mProfileFile = com.android.server.am.ActivityManagerShellCommand.this.getNextArgRequired();
                    com.android.server.am.ActivityManagerShellCommand.this.mAutoStop = true;
                } else if (str.equals("--start-profiler")) {
                    com.android.server.am.ActivityManagerShellCommand.this.mProfileFile = com.android.server.am.ActivityManagerShellCommand.this.getNextArgRequired();
                    com.android.server.am.ActivityManagerShellCommand.this.mAutoStop = false;
                } else if (str.equals("--sampling")) {
                    com.android.server.am.ActivityManagerShellCommand.this.mSamplingInterval = java.lang.Integer.parseInt(com.android.server.am.ActivityManagerShellCommand.this.getNextArgRequired());
                } else if (str.equals("--clock-type")) {
                    com.android.server.am.ActivityManagerShellCommand.this.mClockType = android.app.ProfilerInfo.getClockTypeFromString(com.android.server.am.ActivityManagerShellCommand.this.getNextArgRequired());
                } else if (str.equals("--streaming")) {
                    com.android.server.am.ActivityManagerShellCommand.this.mStreaming = true;
                } else if (str.equals("--attach-agent")) {
                    if (com.android.server.am.ActivityManagerShellCommand.this.mAgent != null) {
                        shellCommand.getErrPrintWriter().println("Multiple --attach-agent(-bind) not supported");
                        return false;
                    }
                    com.android.server.am.ActivityManagerShellCommand.this.mAgent = com.android.server.am.ActivityManagerShellCommand.this.getNextArgRequired();
                    com.android.server.am.ActivityManagerShellCommand.this.mAttachAgentDuringBind = false;
                } else if (str.equals("--attach-agent-bind")) {
                    if (com.android.server.am.ActivityManagerShellCommand.this.mAgent != null) {
                        shellCommand.getErrPrintWriter().println("Multiple --attach-agent(-bind) not supported");
                        return false;
                    }
                    com.android.server.am.ActivityManagerShellCommand.this.mAgent = com.android.server.am.ActivityManagerShellCommand.this.getNextArgRequired();
                    com.android.server.am.ActivityManagerShellCommand.this.mAttachAgentDuringBind = true;
                } else if (str.equals("-R")) {
                    com.android.server.am.ActivityManagerShellCommand.this.mRepeat = java.lang.Integer.parseInt(com.android.server.am.ActivityManagerShellCommand.this.getNextArgRequired());
                } else if (str.equals("-S")) {
                    com.android.server.am.ActivityManagerShellCommand.this.mStopOption = true;
                } else if (str.equals("--track-allocation")) {
                    com.android.server.am.ActivityManagerShellCommand.this.mStartFlags |= 4;
                } else if (str.equals("--user")) {
                    com.android.server.am.ActivityManagerShellCommand.this.mUserId = android.os.UserHandle.parseUserArg(com.android.server.am.ActivityManagerShellCommand.this.getNextArgRequired());
                } else if (str.equals("--receiver-permission")) {
                    com.android.server.am.ActivityManagerShellCommand.this.mReceiverPermission = com.android.server.am.ActivityManagerShellCommand.this.getNextArgRequired();
                } else if (str.equals("--display")) {
                    com.android.server.am.ActivityManagerShellCommand.this.mDisplayId = java.lang.Integer.parseInt(com.android.server.am.ActivityManagerShellCommand.this.getNextArgRequired());
                } else if (str.equals("--task-display-area-feature-id")) {
                    com.android.server.am.ActivityManagerShellCommand.this.mTaskDisplayAreaFeatureId = java.lang.Integer.parseInt(com.android.server.am.ActivityManagerShellCommand.this.getNextArgRequired());
                } else if (str.equals("--windowingMode")) {
                    com.android.server.am.ActivityManagerShellCommand.this.mWindowingMode = java.lang.Integer.parseInt(com.android.server.am.ActivityManagerShellCommand.this.getNextArgRequired());
                } else if (str.equals("--activityType")) {
                    com.android.server.am.ActivityManagerShellCommand.this.mActivityType = java.lang.Integer.parseInt(com.android.server.am.ActivityManagerShellCommand.this.getNextArgRequired());
                } else if (str.equals("--task")) {
                    com.android.server.am.ActivityManagerShellCommand.this.mTaskId = java.lang.Integer.parseInt(com.android.server.am.ActivityManagerShellCommand.this.getNextArgRequired());
                } else if (str.equals("--task-overlay")) {
                    com.android.server.am.ActivityManagerShellCommand.this.mIsTaskOverlay = true;
                } else if (str.equals("--lock-task")) {
                    com.android.server.am.ActivityManagerShellCommand.this.mIsLockTask = true;
                } else if (str.equals("--allow-background-activity-starts")) {
                    if (com.android.server.am.ActivityManagerShellCommand.this.mBroadcastOptions == null) {
                        com.android.server.am.ActivityManagerShellCommand.this.mBroadcastOptions = android.app.BroadcastOptions.makeBasic();
                    }
                    com.android.server.am.ActivityManagerShellCommand.this.mBroadcastOptions.setBackgroundActivityStartsAllowed(true);
                } else if (str.equals("--async")) {
                    com.android.server.am.ActivityManagerShellCommand.this.mAsync = true;
                } else if (str.equals("--splashscreen-show-icon")) {
                    com.android.server.am.ActivityManagerShellCommand.this.mShowSplashScreen = true;
                } else if (str.equals("--dismiss-keyguard-if-insecure") || str.equals("--dismiss-keyguard")) {
                    com.android.server.am.ActivityManagerShellCommand.this.mDismissKeyguardIfInsecure = true;
                } else {
                    if (!str.equals("--allow-fgs-start-reason")) {
                        return false;
                    }
                    int parseInt = java.lang.Integer.parseInt(com.android.server.am.ActivityManagerShellCommand.this.getNextArgRequired());
                    com.android.server.am.ActivityManagerShellCommand.this.mBroadcastOptions = android.app.BroadcastOptions.makeBasic();
                    com.android.server.am.ActivityManagerShellCommand.this.mBroadcastOptions.setTemporaryAppAllowlist(com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY, 0, parseInt, "");
                }
                return true;
            }
        });
    }

    private class ProgressWaiter extends android.os.IProgressListener.Stub {
        private final java.util.concurrent.CountDownLatch mFinishedLatch;
        private final int mUserId;

        private ProgressWaiter(int i) {
            this.mFinishedLatch = new java.util.concurrent.CountDownLatch(1);
            this.mUserId = i;
        }

        public void onStarted(int i, android.os.Bundle bundle) {
        }

        public void onProgress(int i, int i2, android.os.Bundle bundle) {
            com.android.server.utils.Slogf.d(com.android.server.am.ActivityManagerShellCommand.TAG, "ProgressWaiter[user=%d]: onProgress(%d, %d)", java.lang.Integer.valueOf(this.mUserId), java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
        }

        public void onFinished(int i, android.os.Bundle bundle) {
            com.android.server.utils.Slogf.d(com.android.server.am.ActivityManagerShellCommand.TAG, "ProgressWaiter[user=%d]: onFinished(%d)", java.lang.Integer.valueOf(this.mUserId), java.lang.Integer.valueOf(i));
            this.mFinishedLatch.countDown();
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("ProgressWaiter[userId=");
            sb.append(this.mUserId);
            sb.append(", finished=");
            sb.append(this.mFinishedLatch.getCount() == 0);
            sb.append("]");
            return sb.toString();
        }

        public boolean waitForFinish(long j) {
            try {
                return this.mFinishedLatch.await(j, java.util.concurrent.TimeUnit.MILLISECONDS);
            } catch (java.lang.InterruptedException e) {
                java.lang.System.err.println("Thread interrupted unexpectedly.");
                return false;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x00b4, code lost:
    
        getErrPrintWriter().println("Error: Intent does not match any activities: " + r15);
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00cc, code lost:
    
        return r13;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r13v0 */
    /* JADX WARN: Type inference failed for: r13v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r13v3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    int runStartActivity(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        android.os.ParcelFileDescriptor parcelFileDescriptor;
        android.app.ProfilerInfo profilerInfo;
        android.app.ActivityOptions activityOptions;
        int i;
        int i2;
        int i3;
        android.content.Intent intent;
        int startActivityAsUserWithFeature;
        android.os.Bundle bundle;
        java.lang.String str;
        try {
            android.content.Intent makeIntent = makeIntent(-2);
            int i4 = -1;
            ?? r13 = 1;
            if (this.mUserId == -1) {
                getErrPrintWriter().println("Error: Can't start service with user 'all'");
                return 1;
            }
            java.lang.String resolveType = makeIntent.resolveType(this.mInternal.mContext);
            while (true) {
                if (this.mStopOption) {
                    if (makeIntent.getComponent() != null) {
                        str = makeIntent.getComponent().getPackageName();
                    } else {
                        java.util.List list = this.mPm.queryIntentActivities(makeIntent, resolveType, 0L, this.mInternal.mUserController.handleIncomingUser(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), this.mUserId, false, 0, "ActivityManagerShellCommand", null)).getList();
                        if (list != null && list.size() > 0) {
                            if (list.size() > r13) {
                                getErrPrintWriter().println("Error: Intent matches multiple activities; can't stop: " + makeIntent);
                                return r13;
                            }
                            str = ((android.content.pm.ResolveInfo) list.get(0)).activityInfo.packageName;
                        }
                    }
                    printWriter.println("Stopping: " + str);
                    printWriter.flush();
                    this.mInterface.forceStopPackage(str, this.mUserId);
                    try {
                        java.lang.Thread.sleep(250L);
                    } catch (java.lang.InterruptedException e) {
                    }
                }
                if (this.mProfileFile == null && this.mAgent == null) {
                    profilerInfo = null;
                } else {
                    if (this.mProfileFile != null) {
                        android.os.ParcelFileDescriptor openFileForSystem = openFileForSystem(this.mProfileFile, "w");
                        if (openFileForSystem != null) {
                            parcelFileDescriptor = openFileForSystem;
                        } else {
                            return r13;
                        }
                    } else {
                        parcelFileDescriptor = null;
                    }
                    profilerInfo = new android.app.ProfilerInfo(this.mProfileFile, parcelFileDescriptor, this.mSamplingInterval, this.mAutoStop, this.mStreaming, this.mAgent, this.mAttachAgentDuringBind, this.mClockType);
                }
                printWriter.println("Starting: " + makeIntent);
                printWriter.flush();
                makeIntent.addFlags(268435456);
                long uptimeMillis = android.os.SystemClock.uptimeMillis();
                if (this.mDisplayId == i4) {
                    activityOptions = null;
                } else {
                    android.app.ActivityOptions makeBasic = android.app.ActivityOptions.makeBasic();
                    makeBasic.setLaunchDisplayId(this.mDisplayId);
                    activityOptions = makeBasic;
                }
                android.app.ActivityOptions activityOptions2 = activityOptions;
                android.app.ActivityOptions activityOptions3 = activityOptions;
                if (this.mTaskDisplayAreaFeatureId != i4) {
                    if (activityOptions == null) {
                        activityOptions2 = android.app.ActivityOptions.makeBasic();
                    }
                    activityOptions2.setLaunchTaskDisplayAreaFeatureId(this.mTaskDisplayAreaFeatureId);
                    activityOptions3 = activityOptions2;
                }
                android.app.ActivityOptions activityOptions4 = activityOptions3;
                android.app.ActivityOptions activityOptions5 = activityOptions3;
                if (this.mWindowingMode != 0) {
                    if (activityOptions3 == null) {
                        activityOptions4 = android.app.ActivityOptions.makeBasic();
                    }
                    activityOptions4.setLaunchWindowingMode(this.mWindowingMode);
                    activityOptions5 = activityOptions4;
                }
                android.app.ActivityOptions activityOptions6 = activityOptions5;
                android.app.ActivityOptions activityOptions7 = activityOptions5;
                if (this.mActivityType != 0) {
                    if (activityOptions5 == null) {
                        activityOptions6 = android.app.ActivityOptions.makeBasic();
                    }
                    activityOptions6.setLaunchActivityType(this.mActivityType);
                    activityOptions7 = activityOptions6;
                }
                android.app.ActivityOptions activityOptions8 = activityOptions7;
                android.app.ActivityOptions activityOptions9 = activityOptions7;
                if (this.mTaskId != i4) {
                    if (activityOptions7 == null) {
                        activityOptions8 = android.app.ActivityOptions.makeBasic();
                    }
                    activityOptions8.setLaunchTaskId(this.mTaskId);
                    activityOptions9 = activityOptions8;
                    if (this.mIsTaskOverlay) {
                        activityOptions8.setTaskOverlay(r13, r13);
                        activityOptions9 = activityOptions8;
                    }
                }
                android.app.ActivityOptions activityOptions10 = activityOptions9;
                android.app.ActivityOptions activityOptions11 = activityOptions9;
                if (this.mIsLockTask) {
                    if (activityOptions9 == null) {
                        activityOptions10 = android.app.ActivityOptions.makeBasic();
                    }
                    activityOptions10.setLockTaskEnabled(r13);
                    activityOptions11 = activityOptions10;
                }
                android.app.ActivityOptions activityOptions12 = activityOptions11;
                android.app.ActivityOptions activityOptions13 = activityOptions11;
                if (this.mShowSplashScreen) {
                    if (activityOptions11 == null) {
                        activityOptions12 = android.app.ActivityOptions.makeBasic();
                    }
                    activityOptions12.setSplashScreenStyle(r13);
                    activityOptions13 = activityOptions12;
                }
                android.app.ActivityOptions activityOptions14 = activityOptions13;
                android.app.ActivityOptions activityOptions15 = activityOptions13;
                if (this.mDismissKeyguardIfInsecure) {
                    if (activityOptions13 == null) {
                        activityOptions14 = android.app.ActivityOptions.makeBasic();
                    }
                    activityOptions14.setDismissKeyguardIfInsecure();
                    activityOptions15 = activityOptions14;
                }
                if (this.mWaitOption) {
                    i = 0;
                    i2 = r13;
                    i3 = i4;
                    intent = makeIntent;
                    android.os.Bundle startActivityAndWait = this.mInternal.startActivityAndWait(null, "com.android.shell", null, makeIntent, resolveType, null, null, 0, this.mStartFlags, profilerInfo, activityOptions15 != null ? activityOptions15.toBundle() : null, this.mUserId);
                    startActivityAsUserWithFeature = ((android.app.WaitResult) startActivityAndWait).result;
                    r17 = startActivityAndWait;
                } else {
                    i = 0;
                    i2 = r13;
                    i3 = i4;
                    intent = makeIntent;
                    startActivityAsUserWithFeature = this.mInternal.startActivityAsUserWithFeature(null, "com.android.shell", null, intent, resolveType, null, null, 0, this.mStartFlags, profilerInfo, activityOptions15 != null ? activityOptions15.toBundle() : null, this.mUserId);
                }
                long uptimeMillis2 = android.os.SystemClock.uptimeMillis();
                java.io.PrintWriter errPrintWriter = this.mWaitOption ? printWriter : getErrPrintWriter();
                switch (startActivityAsUserWithFeature) {
                    case -98:
                        errPrintWriter.println("Error: Not allowed to start background user activity that shouldn't be displayed for all users.");
                        return i2;
                    case -97:
                        errPrintWriter.println("Error: Activity not started, voice control not allowed for: " + intent);
                        return i2;
                    case -94:
                        errPrintWriter.println("Error: Activity not started, you do not have permission to access it.");
                        return i2;
                    case -93:
                        errPrintWriter.println("Error: Activity not started, you requested to both forward and receive its result");
                        return i2;
                    case -92:
                        errPrintWriter.println(NO_CLASS_ERROR_CODE);
                        errPrintWriter.println("Error: Activity class " + intent.getComponent().toShortString() + " does not exist.");
                        return i2;
                    case -91:
                        errPrintWriter.println("Error: Activity not started, unable to resolve " + intent.toString());
                        return i2;
                    case 0:
                        break;
                    case 1:
                        errPrintWriter.println("Warning: Activity not started because intent should be handled by the caller");
                        break;
                    case 2:
                        errPrintWriter.println("Warning: Activity not started, its current task has been brought to the front");
                        break;
                    case 3:
                        errPrintWriter.println("Warning: Activity not started, intent has been delivered to currently running top-most instance.");
                        break;
                    case 100:
                        errPrintWriter.println("Warning: Activity not started because the  current activity is being kept for the user.");
                        break;
                    default:
                        errPrintWriter.println("Error: Activity not started, unknown error code " + startActivityAsUserWithFeature);
                        return i2;
                }
                errPrintWriter.flush();
                if (this.mWaitOption) {
                    if (r17 != null) {
                        bundle = r17;
                    } else {
                        bundle = new android.app.WaitResult();
                        ((android.app.WaitResult) bundle).who = intent.getComponent();
                    }
                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                    sb.append("Status: ");
                    sb.append(((android.app.WaitResult) bundle).timeout ? "timeout" : "ok");
                    printWriter.println(sb.toString());
                    printWriter.println("LaunchState: " + android.app.WaitResult.launchStateToString(((android.app.WaitResult) bundle).launchState));
                    if (((android.app.WaitResult) bundle).who != null) {
                        printWriter.println("Activity: " + ((android.app.WaitResult) bundle).who.flattenToShortString());
                    }
                    if (((android.app.WaitResult) bundle).totalTime >= 0) {
                        printWriter.println("TotalTime: " + ((android.app.WaitResult) bundle).totalTime);
                    }
                    printWriter.println("WaitTime: " + (uptimeMillis2 - uptimeMillis));
                    printWriter.println("Complete");
                    printWriter.flush();
                }
                this.mRepeat--;
                if (this.mRepeat > 0) {
                    this.mTaskInterface.unhandledBack();
                }
                if (this.mRepeat > 0) {
                    r13 = i2;
                    i4 = i3;
                    makeIntent = intent;
                } else {
                    return i;
                }
            }
        } catch (java.net.URISyntaxException e2) {
            throw new java.lang.RuntimeException(e2.getMessage(), e2);
        }
    }

    int runStartService(java.io.PrintWriter printWriter, boolean z) throws android.os.RemoteException {
        java.io.PrintWriter errPrintWriter = getErrPrintWriter();
        try {
            android.content.Intent makeIntent = makeIntent(-2);
            if (this.mUserId == -1) {
                errPrintWriter.println("Error: Can't start activity with user 'all'");
                return -1;
            }
            printWriter.println("Starting service: " + makeIntent);
            printWriter.flush();
            android.content.ComponentName startService = this.mInterface.startService((android.app.IApplicationThread) null, makeIntent, makeIntent.getType(), z, "com.android.shell", (java.lang.String) null, this.mUserId);
            if (startService == null) {
                errPrintWriter.println("Error: Not found; no service started.");
                return -1;
            }
            if (startService.getPackageName().equals("!")) {
                errPrintWriter.println("Error: Requires permission " + startService.getClassName());
                return -1;
            }
            if (startService.getPackageName().equals("!!")) {
                errPrintWriter.println("Error: " + startService.getClassName());
                return -1;
            }
            if (startService.getPackageName().equals("?")) {
                errPrintWriter.println("Error: " + startService.getClassName());
                return -1;
            }
            return 0;
        } catch (java.net.URISyntaxException e) {
            throw new java.lang.RuntimeException(e.getMessage(), e);
        }
    }

    int runStopService(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        java.io.PrintWriter errPrintWriter = getErrPrintWriter();
        try {
            android.content.Intent makeIntent = makeIntent(-2);
            if (this.mUserId == -1) {
                errPrintWriter.println("Error: Can't stop activity with user 'all'");
                return -1;
            }
            printWriter.println("Stopping service: " + makeIntent);
            printWriter.flush();
            int stopService = this.mInterface.stopService((android.app.IApplicationThread) null, makeIntent, makeIntent.getType(), this.mUserId);
            if (stopService == 0) {
                errPrintWriter.println("Service not stopped: was not running.");
                return -1;
            }
            if (stopService == 1) {
                errPrintWriter.println("Service stopped");
                return -1;
            }
            if (stopService == -1) {
                errPrintWriter.println("Error stopping service");
                return -1;
            }
            return 0;
        } catch (java.net.URISyntaxException e) {
            throw new java.lang.RuntimeException(e.getMessage(), e);
        }
    }

    static final class IntentReceiver extends android.content.IIntentReceiver.Stub {
        private boolean mFinished = false;
        private final java.io.PrintWriter mPw;

        IntentReceiver(java.io.PrintWriter printWriter) {
            this.mPw = printWriter;
        }

        public void performReceive(android.content.Intent intent, int i, java.lang.String str, android.os.Bundle bundle, boolean z, boolean z2, int i2) {
            java.lang.String str2 = "Broadcast completed: result=" + i;
            if (str != null) {
                str2 = str2 + ", data=\"" + str + "\"";
            }
            if (bundle != null) {
                str2 = str2 + ", extras: " + bundle;
            }
            this.mPw.println(str2);
            this.mPw.flush();
            synchronized (this) {
                this.mFinished = true;
                notifyAll();
            }
        }

        public synchronized void waitForFinish() {
            while (!this.mFinished) {
                try {
                    wait();
                } catch (java.lang.InterruptedException e) {
                    throw new java.lang.IllegalStateException(e);
                }
            }
        }
    }

    int runSendBroadcast(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        java.io.PrintWriter printWriter2 = new java.io.PrintWriter((java.io.Writer) new android.util.TeeWriter(new java.io.Writer[]{com.android.server.am.ActivityManagerDebugConfig.LOG_WRITER_INFO, printWriter}));
        try {
            android.content.Intent makeIntent = makeIntent(-2);
            makeIntent.addFlags(4194304);
            com.android.server.am.ActivityManagerShellCommand.IntentReceiver intentReceiver = new com.android.server.am.ActivityManagerShellCommand.IntentReceiver(printWriter2);
            java.lang.String[] strArr = this.mReceiverPermission == null ? null : new java.lang.String[]{this.mReceiverPermission};
            printWriter2.println("Broadcasting: " + makeIntent);
            printWriter2.flush();
            int broadcastIntentWithFeature = this.mInterface.broadcastIntentWithFeature((android.app.IApplicationThread) null, (java.lang.String) null, makeIntent, (java.lang.String) null, intentReceiver, 0, (java.lang.String) null, (android.os.Bundle) null, strArr, (java.lang.String[]) null, (java.lang.String[]) null, -1, this.mBroadcastOptions == null ? null : this.mBroadcastOptions.toBundle(), true, false, this.mUserId);
            com.android.server.utils.Slogf.i(TAG, "Enqueued broadcast %s: " + broadcastIntentWithFeature, makeIntent);
            if (broadcastIntentWithFeature == 0 && !this.mAsync) {
                intentReceiver.waitForFinish();
            }
            return 0;
        } catch (java.net.URISyntaxException e) {
            throw new java.lang.RuntimeException(e.getMessage(), e);
        }
    }

    int runTraceIpc(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        java.lang.String nextArgRequired = getNextArgRequired();
        if (nextArgRequired.equals("start")) {
            return runTraceIpcStart(printWriter);
        }
        if (nextArgRequired.equals("stop")) {
            return runTraceIpcStop(printWriter);
        }
        getErrPrintWriter().println("Error: unknown trace ipc command '" + nextArgRequired + "'");
        return -1;
    }

    int runTraceIpcStart(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        printWriter.println("Starting IPC tracing.");
        printWriter.flush();
        this.mInterface.startBinderTracking();
        return 0;
    }

    int runTraceIpcStop(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        java.io.PrintWriter errPrintWriter = getErrPrintWriter();
        java.lang.String str = null;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                if (nextOption.equals("--dump-file")) {
                    str = getNextArgRequired();
                } else {
                    errPrintWriter.println("Error: Unknown option: " + nextOption);
                    return -1;
                }
            } else {
                if (str == null) {
                    errPrintWriter.println("Error: Specify filename to dump logs to.");
                    return -1;
                }
                android.os.ParcelFileDescriptor openFileForSystem = openFileForSystem(str, "w");
                if (openFileForSystem == null) {
                    return -1;
                }
                if (!this.mInterface.stopBinderTrackingAndDump(openFileForSystem)) {
                    errPrintWriter.println("STOP TRACE FAILED.");
                    return -1;
                }
                printWriter.println("Stopped IPC tracing. Dumping logs to: " + str);
                return 0;
            }
        }
    }

    private int runProfile(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        boolean z;
        int i;
        android.app.ProfilerInfo profilerInfo;
        java.io.PrintWriter errPrintWriter = getErrPrintWriter();
        this.mSamplingInterval = 0;
        this.mStreaming = false;
        this.mClockType = 0;
        java.lang.String nextArgRequired = getNextArgRequired();
        int i2 = -2;
        if ("start".equals(nextArgRequired)) {
            while (true) {
                java.lang.String nextOption = getNextOption();
                if (nextOption != null) {
                    if (nextOption.equals("--user")) {
                        i2 = android.os.UserHandle.parseUserArg(getNextArgRequired());
                    } else if (nextOption.equals("--clock-type")) {
                        this.mClockType = android.app.ProfilerInfo.getClockTypeFromString(getNextArgRequired());
                    } else if (nextOption.equals("--streaming")) {
                        this.mStreaming = true;
                    } else if (nextOption.equals("--sampling")) {
                        this.mSamplingInterval = java.lang.Integer.parseInt(getNextArgRequired());
                    } else {
                        errPrintWriter.println("Error: Unknown option: " + nextOption);
                        return -1;
                    }
                } else {
                    nextArgRequired = getNextArgRequired();
                    z = true;
                    i = i2;
                    break;
                }
            }
        } else if ("stop".equals(nextArgRequired)) {
            while (true) {
                java.lang.String nextOption2 = getNextOption();
                if (nextOption2 != null) {
                    if (nextOption2.equals("--user")) {
                        i2 = android.os.UserHandle.parseUserArg(getNextArgRequired());
                    } else {
                        errPrintWriter.println("Error: Unknown option: " + nextOption2);
                        return -1;
                    }
                } else {
                    nextArgRequired = getNextArgRequired();
                    z = false;
                    i = i2;
                    break;
                }
            }
        } else {
            java.lang.String nextArgRequired2 = getNextArgRequired();
            if ("start".equals(nextArgRequired2)) {
                z = true;
                i = -2;
            } else if ("stop".equals(nextArgRequired2)) {
                z = false;
                i = -2;
            } else {
                throw new java.lang.IllegalArgumentException("Profile command " + nextArgRequired + " not valid");
            }
        }
        if (i == -1) {
            errPrintWriter.println("Error: Can't profile with user 'all'");
            return -1;
        }
        if (!z) {
            profilerInfo = null;
        } else {
            java.lang.String nextArgRequired3 = getNextArgRequired();
            android.os.ParcelFileDescriptor openFileForSystem = openFileForSystem(nextArgRequired3, "w");
            if (openFileForSystem == null) {
                return -1;
            }
            profilerInfo = new android.app.ProfilerInfo(nextArgRequired3, openFileForSystem, this.mSamplingInterval, false, this.mStreaming, (java.lang.String) null, false, this.mClockType);
        }
        if (this.mInterface.profileControl(nextArgRequired, i, z, profilerInfo, 0)) {
            return 0;
        }
        errPrintWriter.println("PROFILE FAILED on process " + nextArgRequired);
        return -1;
    }

    @dalvik.annotation.optimization.NeverCompile
    int runCompact(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        java.lang.String nextArgRequired = getNextArgRequired();
        boolean equals = nextArgRequired.equals("full");
        boolean equals2 = nextArgRequired.equals("some");
        if (equals || equals2) {
            com.android.server.am.ProcessRecord processFromShell = getProcessFromShell();
            if (processFromShell == null) {
                getErrPrintWriter().println("Error: could not find process");
                return -1;
            }
            printWriter.println("Process record found pid: " + processFromShell.mPid);
            if (equals) {
                printWriter.println("Executing full compaction for " + processFromShell.mPid);
                com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock = this.mInternal.mProcLock;
                com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
                synchronized (activityManagerGlobalLock) {
                    try {
                        this.mInternal.mOomAdjuster.mCachedAppOptimizer.compactApp(processFromShell, com.android.server.am.CachedAppOptimizer.CompactProfile.FULL, com.android.server.am.CachedAppOptimizer.CompactSource.SHELL, true);
                    } finally {
                    }
                }
                com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                printWriter.println("Finished full compaction for " + processFromShell.mPid);
                return 0;
            }
            if (equals2) {
                printWriter.println("Executing some compaction for " + processFromShell.mPid);
                com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock2 = this.mInternal.mProcLock;
                com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
                synchronized (activityManagerGlobalLock2) {
                    try {
                        this.mInternal.mOomAdjuster.mCachedAppOptimizer.compactApp(processFromShell, com.android.server.am.CachedAppOptimizer.CompactProfile.SOME, com.android.server.am.CachedAppOptimizer.CompactSource.SHELL, true);
                    } finally {
                        com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                    }
                }
                com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                printWriter.println("Finished some compaction for " + processFromShell.mPid);
                return 0;
            }
            return 0;
        }
        if (nextArgRequired.equals("system")) {
            printWriter.println("Executing system compaction");
            com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock3 = this.mInternal.mProcLock;
            com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
            synchronized (activityManagerGlobalLock3) {
                try {
                    this.mInternal.mOomAdjuster.mCachedAppOptimizer.compactAllSystem();
                } finally {
                }
            }
            com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
            printWriter.println("Finished system compaction");
            return 0;
        }
        if (nextArgRequired.equals("native")) {
            java.lang.String nextArgRequired2 = getNextArgRequired();
            boolean equals3 = nextArgRequired2.equals("full");
            boolean equals4 = nextArgRequired2.equals("some");
            java.lang.String nextArgRequired3 = getNextArgRequired();
            try {
                int parseInt = java.lang.Integer.parseInt(nextArgRequired3);
                if (equals3) {
                    this.mInternal.mOomAdjuster.mCachedAppOptimizer.compactNative(com.android.server.am.CachedAppOptimizer.CompactProfile.FULL, parseInt);
                    return 0;
                }
                if (equals4) {
                    this.mInternal.mOomAdjuster.mCachedAppOptimizer.compactNative(com.android.server.am.CachedAppOptimizer.CompactProfile.SOME, parseInt);
                    return 0;
                }
                getErrPrintWriter().println("Error: unknown compaction type '" + nextArgRequired2 + "'");
                return -1;
            } catch (java.lang.Exception e) {
                getErrPrintWriter().println("Error: failed to parse '" + nextArgRequired3 + "' as a PID");
                return -1;
            }
        }
        getErrPrintWriter().println("Error: unknown compact command '" + nextArgRequired + "'");
        return -1;
    }

    @dalvik.annotation.optimization.NeverCompile
    int runFreeze(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        boolean z;
        java.lang.String nextOption = getNextOption();
        if (nextOption == null) {
            z = false;
        } else {
            z = nextOption.equals("--sticky");
        }
        com.android.server.am.ProcessRecord processFromShell = getProcessFromShell();
        if (processFromShell == null) {
            getErrPrintWriter().println("Error: could not find process");
            return -1;
        }
        printWriter.println("Freezing pid: " + processFromShell.mPid + " sticky=" + z);
        com.android.server.am.ActivityManagerService activityManagerService = this.mInternal;
        com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock = this.mInternal.mProcLock;
                com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
                synchronized (activityManagerGlobalLock) {
                    try {
                        processFromShell.mOptRecord.setFreezeSticky(z);
                        this.mInternal.mOomAdjuster.mCachedAppOptimizer.freezeAppAsyncInternalLSP(processFromShell, 0L, true);
                    } catch (java.lang.Throwable th) {
                        com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                        throw th;
                    }
                }
                com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
            } catch (java.lang.Throwable th2) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                throw th2;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
        return 0;
    }

    @dalvik.annotation.optimization.NeverCompile
    int runUnfreeze(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        boolean z;
        java.lang.String nextOption = getNextOption();
        if (nextOption == null) {
            z = false;
        } else {
            z = nextOption.equals("--sticky");
        }
        com.android.server.am.ProcessRecord processFromShell = getProcessFromShell();
        if (processFromShell == null) {
            getErrPrintWriter().println("Error: could not find process");
            return -1;
        }
        printWriter.println("Unfreezing pid: " + processFromShell.mPid);
        com.android.server.am.ActivityManagerService activityManagerService = this.mInternal;
        com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock = this.mInternal.mProcLock;
                com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
                synchronized (activityManagerGlobalLock) {
                    try {
                        synchronized (this.mInternal.mOomAdjuster.mCachedAppOptimizer.mFreezerLock) {
                            processFromShell.mOptRecord.setFreezeSticky(z);
                            this.mInternal.mOomAdjuster.mCachedAppOptimizer.unfreezeAppInternalLSP(processFromShell, 0, true);
                        }
                    } catch (java.lang.Throwable th) {
                        com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                        throw th;
                    }
                }
                com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
            } catch (java.lang.Throwable th2) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                throw th2;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
        return 0;
    }

    @dalvik.annotation.optimization.NeverCompile
    com.android.server.am.ProcessRecord getProcessFromShell() throws android.os.RemoteException {
        com.android.server.am.ProcessRecord processRecordLocked;
        java.lang.String nextArgRequired = getNextArgRequired();
        com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock = this.mInternal.mProcLock;
        com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
        synchronized (activityManagerGlobalLock) {
            try {
                processRecordLocked = this.mInternal.getProcessRecordLocked(nextArgRequired, this.mInternal.getPackageManagerInternal().getPackageUid(nextArgRequired, 0L, getUserIdFromShellOrFallback()));
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                throw th;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
        return processRecordLocked;
    }

    @dalvik.annotation.optimization.NeverCompile
    int getUserIdFromShellOrFallback() throws android.os.RemoteException {
        int parseUserArg;
        int currentUserId = this.mInterface.getCurrentUserId();
        java.lang.String nextOption = getNextOption();
        if (nextOption != null && "--user".equals(nextOption) && (parseUserArg = android.os.UserHandle.parseUserArg(getNextArgRequired())) != -2) {
            return parseUserArg;
        }
        return currentUserId;
    }

    int runDumpHeap(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        java.lang.String str;
        java.io.PrintWriter errPrintWriter = getErrPrintWriter();
        boolean z = true;
        boolean z2 = false;
        boolean z3 = false;
        int i = -2;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                if (nextOption.equals("--user")) {
                    i = android.os.UserHandle.parseUserArg(getNextArgRequired());
                    if (i == -1) {
                        errPrintWriter.println("Error: Can't dump heap with user 'all'");
                        return -1;
                    }
                } else if (nextOption.equals("-n")) {
                    z = false;
                } else if (nextOption.equals("-g")) {
                    z3 = true;
                } else if (nextOption.equals("-m")) {
                    z2 = true;
                    z = false;
                } else {
                    errPrintWriter.println("Error: Unknown option: " + nextOption);
                    return -1;
                }
            } else {
                java.lang.String nextArgRequired = getNextArgRequired();
                java.lang.String nextArg = getNextArg();
                if (nextArg != null) {
                    str = nextArg;
                } else {
                    str = "/data/local/tmp/heapdump-" + LOG_NAME_TIME_FORMATTER.format(java.time.LocalDateTime.now(java.time.Clock.systemDefaultZone())) + ".prof";
                }
                android.os.ParcelFileDescriptor openFileForSystem = openFileForSystem(str, "w");
                if (openFileForSystem == null) {
                    return -1;
                }
                printWriter.println("File: " + str);
                printWriter.flush();
                final java.util.concurrent.CountDownLatch countDownLatch = new java.util.concurrent.CountDownLatch(1);
                if (!this.mInterface.dumpHeap(nextArgRequired, i, z, z2, z3, str, openFileForSystem, new android.os.RemoteCallback(new android.os.RemoteCallback.OnResultListener() { // from class: com.android.server.am.ActivityManagerShellCommand.2
                    public void onResult(android.os.Bundle bundle) {
                        countDownLatch.countDown();
                    }
                }, (android.os.Handler) null))) {
                    errPrintWriter.println("HEAP DUMP FAILED on process " + nextArgRequired);
                    return -1;
                }
                printWriter.println("Waiting for dump to finish...");
                printWriter.flush();
                try {
                    countDownLatch.await();
                } catch (java.lang.InterruptedException e) {
                    errPrintWriter.println("Caught InterruptedException");
                }
                return 0;
            }
        }
    }

    int runSetDebugApp(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        boolean z = false;
        boolean z2 = false;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                if (nextOption.equals("-w")) {
                    z = true;
                } else if (nextOption.equals("--persistent")) {
                    z2 = true;
                } else {
                    getErrPrintWriter().println("Error: Unknown option: " + nextOption);
                    return -1;
                }
            } else {
                this.mInterface.setDebugApp(getNextArgRequired(), z, z2);
                return 0;
            }
        }
    }

    int runSetAgentApp(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        this.mInterface.setAgentApp(getNextArgRequired(), getNextArg());
        return 0;
    }

    int runClearDebugApp(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        this.mInterface.setDebugApp((java.lang.String) null, false, true);
        return 0;
    }

    int runSetWatchHeap(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        this.mInterface.setDumpHeapDebugLimit(getNextArgRequired(), 0, java.lang.Long.parseLong(getNextArgRequired()), (java.lang.String) null);
        return 0;
    }

    int runClearWatchHeap(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        this.mInterface.setDumpHeapDebugLimit(getNextArgRequired(), 0, -1L, (java.lang.String) null);
        return 0;
    }

    int runClearStartInfo(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        this.mInternal.enforceCallingPermission("android.permission.WRITE_SECURE_SETTINGS", "runClearStartInfo()");
        java.lang.String str = null;
        int i = -2;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption == null) {
                break;
            }
            if (nextOption.equals("--user")) {
                i = android.os.UserHandle.parseUserArg(getNextArgRequired());
            } else {
                str = nextOption;
            }
        }
        if (i == -2) {
            android.content.pm.UserInfo currentUser = this.mInterface.getCurrentUser();
            if (currentUser == null) {
                return -1;
            }
            i = currentUser.id;
        }
        this.mInternal.mProcessList.getAppStartInfoTracker().clearHistoryProcessStartInfo(str, i);
        return 0;
    }

    int runClearExitInfo(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        this.mInternal.enforceCallingPermission("android.permission.WRITE_SECURE_SETTINGS", "runClearExitInfo()");
        java.lang.String str = null;
        int i = -2;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption == null) {
                break;
            }
            if (nextOption.equals("--user")) {
                i = android.os.UserHandle.parseUserArg(getNextArgRequired());
            } else {
                str = nextOption;
            }
        }
        if (i == -2) {
            android.content.pm.UserInfo currentUser = this.mInterface.getCurrentUser();
            if (currentUser == null) {
                return -1;
            }
            i = currentUser.id;
        }
        this.mInternal.mProcessList.mAppExitInfoTracker.clearHistoryProcessExitInfo(str, i);
        return 0;
    }

    int runBugReport(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        boolean z = true;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                if (nextOption.equals("--progress")) {
                    this.mInterface.requestInteractiveBugReport();
                    z = false;
                } else if (nextOption.equals("--telephony")) {
                    this.mInterface.requestTelephonyBugReport("", "");
                    z = false;
                } else {
                    getErrPrintWriter().println("Error: Unknown option: " + nextOption);
                    return -1;
                }
            } else {
                if (z) {
                    this.mInterface.requestFullBugReport();
                }
                printWriter.println("Your lovely bug report is being created; please be patient.");
                return 0;
            }
        }
    }

    int runForceStop(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        int i = -1;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                if (nextOption.equals("--user")) {
                    i = android.os.UserHandle.parseUserArg(getNextArgRequired());
                } else {
                    getErrPrintWriter().println("Error: Unknown option: " + nextOption);
                    return -1;
                }
            } else {
                this.mInterface.forceStopPackage(getNextArgRequired(), i);
                return 0;
            }
        }
    }

    int runStopApp(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        int i = 0;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                if (nextOption.equals("--user")) {
                    i = android.os.UserHandle.parseUserArg(getNextArgRequired());
                } else {
                    getErrPrintWriter().println("Error: Unknown option: " + nextOption);
                    return -1;
                }
            } else {
                this.mInterface.stopAppForUser(getNextArgRequired(), i);
                return 0;
            }
        }
    }

    int runClearRecentApps(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        this.mTaskInterface.removeAllVisibleRecentTasks();
        return 0;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    int runFgsNotificationRateLimit(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        char c;
        java.lang.String nextArgRequired = getNextArgRequired();
        boolean z = true;
        switch (nextArgRequired.hashCode()) {
            case -1298848381:
                if (nextArgRequired.equals("enable")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 1671308008:
                if (nextArgRequired.equals("disable")) {
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
                break;
            case 1:
                z = false;
                break;
            default:
                throw new java.lang.IllegalArgumentException("Argument must be either 'enable' or 'disable'");
        }
        this.mInterface.enableFgsNotificationRateLimit(z);
        return 0;
    }

    int runCrash(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        java.lang.String str;
        int i;
        int i2 = -1;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                if (nextOption.equals("--user")) {
                    i2 = android.os.UserHandle.parseUserArg(getNextArgRequired());
                } else {
                    getErrPrintWriter().println("Error: Unknown option: " + nextOption);
                    return -1;
                }
            } else {
                java.lang.String nextArgRequired = getNextArgRequired();
                try {
                    i = java.lang.Integer.parseInt(nextArgRequired);
                    str = null;
                } catch (java.lang.NumberFormatException e) {
                    str = nextArgRequired;
                    i = -1;
                }
                for (int i3 : i2 == -1 ? this.mInternal.mUserController.getUserIds() : new int[]{i2}) {
                    if (this.mInternal.mUserController.hasUserRestriction("no_debugging_features", i3)) {
                        getOutPrintWriter().println("Shell does not have permission to crash packages for user " + i3);
                    } else {
                        this.mInterface.crashApplicationWithType(-1, i, str, i3, "shell-induced crash", false, 5);
                    }
                }
                return 0;
            }
        }
    }

    int runKill(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        int i = -1;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                if (nextOption.equals("--user")) {
                    i = android.os.UserHandle.parseUserArg(getNextArgRequired());
                } else {
                    getErrPrintWriter().println("Error: Unknown option: " + nextOption);
                    return -1;
                }
            } else {
                this.mInterface.killBackgroundProcesses(getNextArgRequired(), i);
                return 0;
            }
        }
    }

    int runKillAll(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        this.mInterface.killAllBackgroundProcesses();
        return 0;
    }

    int runMakeIdle(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        int i = -1;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                if (nextOption.equals("--user")) {
                    i = android.os.UserHandle.parseUserArg(getNextArgRequired());
                } else {
                    getErrPrintWriter().println("Error: Unknown option: " + nextOption);
                    return -1;
                }
            } else {
                this.mInterface.makePackageIdle(getNextArgRequired(), i);
                return 0;
            }
        }
    }

    int runSetDeterministicUidIdle(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                if (nextOption.equals("--user")) {
                    android.os.UserHandle.parseUserArg(getNextArgRequired());
                } else {
                    getErrPrintWriter().println("Error: Unknown option: " + nextOption);
                    return -1;
                }
            } else {
                this.mInterface.setDeterministicUidIdle(java.lang.Boolean.parseBoolean(getNextArgRequired()));
                return 0;
            }
        }
    }

    static final class MyActivityController extends android.app.IActivityController.Stub {
        static final int RESULT_ANR_DIALOG = 0;
        static final int RESULT_ANR_KILL = 1;
        static final int RESULT_ANR_WAIT = 2;
        static final int RESULT_CRASH_DIALOG = 0;
        static final int RESULT_CRASH_KILL = 1;
        static final int RESULT_DEFAULT = 0;
        static final int RESULT_EARLY_ANR_CONTINUE = 0;
        static final int RESULT_EARLY_ANR_KILL = 1;
        static final int STATE_ANR = 3;
        static final int STATE_CRASHED = 1;
        static final int STATE_EARLY_ANR = 2;
        static final int STATE_NORMAL = 0;
        final boolean mAlwaysContinue;
        final boolean mAlwaysKill;
        final java.lang.String mGdbPort;
        java.lang.Process mGdbProcess;
        java.lang.Thread mGdbThread;
        boolean mGotGdbPrint;
        final java.io.InputStream mInput;
        final android.app.IActivityManager mInterface;
        final boolean mMonkey;
        final java.io.PrintWriter mPw;
        int mResult;
        final boolean mSimpleMode;
        int mState;
        final java.lang.String mTarget;

        MyActivityController(android.app.IActivityManager iActivityManager, java.io.PrintWriter printWriter, java.io.InputStream inputStream, java.lang.String str, boolean z, boolean z2, java.lang.String str2, boolean z3, boolean z4) {
            this.mInterface = iActivityManager;
            this.mPw = printWriter;
            this.mInput = inputStream;
            this.mGdbPort = str;
            this.mMonkey = z;
            this.mSimpleMode = z2;
            this.mTarget = str2;
            this.mAlwaysContinue = z3;
            this.mAlwaysKill = z4;
        }

        private boolean shouldHandlePackageOrProcess(java.lang.String str) {
            if (this.mTarget == null) {
                return true;
            }
            return this.mTarget.equals(str);
        }

        public boolean activityResuming(java.lang.String str) {
            if (!shouldHandlePackageOrProcess(str)) {
                return true;
            }
            synchronized (this) {
                this.mPw.println("** Activity resuming: " + str);
                this.mPw.flush();
            }
            return true;
        }

        public boolean activityStarting(android.content.Intent intent, java.lang.String str) {
            if (!shouldHandlePackageOrProcess(str)) {
                return true;
            }
            synchronized (this) {
                this.mPw.println("** Activity starting: " + str);
                this.mPw.flush();
            }
            return true;
        }

        public boolean appCrashed(java.lang.String str, int i, java.lang.String str2, java.lang.String str3, long j, java.lang.String str4) {
            if (!shouldHandlePackageOrProcess(str)) {
                return true;
            }
            synchronized (this) {
                try {
                    if (this.mSimpleMode) {
                        this.mPw.println("** PROCESS CRASHED: " + str);
                    } else {
                        this.mPw.println("** ERROR: PROCESS CRASHED");
                        this.mPw.println("processName: " + str);
                        this.mPw.println("processPid: " + i);
                        this.mPw.println("shortMsg: " + str2);
                        this.mPw.println("longMsg: " + str3);
                        this.mPw.println("timeMillis: " + j);
                        this.mPw.println("uptime: " + android.os.SystemClock.uptimeMillis());
                        this.mPw.println("stack:");
                        this.mPw.print(str4);
                        this.mPw.println("#");
                    }
                    this.mPw.flush();
                    if (this.mAlwaysContinue) {
                        return true;
                    }
                    if (this.mAlwaysKill) {
                        return false;
                    }
                    return waitControllerLocked(i, 1) != 1;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public int appEarlyNotResponding(java.lang.String str, int i, java.lang.String str2) {
            if (!shouldHandlePackageOrProcess(str)) {
                return 0;
            }
            synchronized (this) {
                try {
                    if (this.mSimpleMode) {
                        this.mPw.println("** EARLY PROCESS NOT RESPONDING: " + str);
                    } else {
                        this.mPw.println("** ERROR: EARLY PROCESS NOT RESPONDING");
                        this.mPw.println("processName: " + str);
                        this.mPw.println("processPid: " + i);
                        this.mPw.println("annotation: " + str2);
                        this.mPw.println("uptime: " + android.os.SystemClock.uptimeMillis());
                    }
                    this.mPw.flush();
                    if (this.mAlwaysContinue) {
                        return 0;
                    }
                    if (this.mAlwaysKill) {
                        return -1;
                    }
                    return waitControllerLocked(i, 2) == 1 ? -1 : 0;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public int appNotResponding(java.lang.String str, int i, java.lang.String str2) {
            if (!shouldHandlePackageOrProcess(str)) {
                return 0;
            }
            synchronized (this) {
                try {
                    if (this.mSimpleMode) {
                        this.mPw.println("** PROCESS NOT RESPONDING: " + str);
                    } else {
                        this.mPw.println("** ERROR: PROCESS NOT RESPONDING");
                        this.mPw.println("processName: " + str);
                        this.mPw.println("processPid: " + i);
                        this.mPw.println("uptime: " + android.os.SystemClock.uptimeMillis());
                        this.mPw.println("processStats:");
                        this.mPw.print(str2);
                        this.mPw.println("#");
                    }
                    this.mPw.flush();
                    if (this.mAlwaysContinue) {
                        return 0;
                    }
                    if (this.mAlwaysKill) {
                        return -1;
                    }
                    int waitControllerLocked = waitControllerLocked(i, 3);
                    if (waitControllerLocked == 1) {
                        return -1;
                    }
                    return waitControllerLocked == 2 ? 1 : 0;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public int systemNotResponding(java.lang.String str) {
            if (this.mTarget != null) {
                return -1;
            }
            synchronized (this) {
                try {
                    this.mPw.println("** ERROR: PROCESS NOT RESPONDING");
                    if (!this.mSimpleMode) {
                        this.mPw.println("message: " + str);
                        this.mPw.println("#");
                        this.mPw.println("Allowing system to die.");
                    }
                    this.mPw.flush();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return -1;
        }

        void killGdbLocked() {
            this.mGotGdbPrint = false;
            if (this.mGdbProcess != null) {
                this.mPw.println("Stopping gdbserver");
                this.mPw.flush();
                this.mGdbProcess.destroy();
                this.mGdbProcess = null;
            }
            if (this.mGdbThread != null) {
                this.mGdbThread.interrupt();
                this.mGdbThread = null;
            }
        }

        int waitControllerLocked(int i, int i2) {
            if (this.mGdbPort != null) {
                killGdbLocked();
                try {
                    this.mPw.println("Starting gdbserver on port " + this.mGdbPort);
                    this.mPw.println("Do the following:");
                    this.mPw.println("  adb forward tcp:" + this.mGdbPort + " tcp:" + this.mGdbPort);
                    java.io.PrintWriter printWriter = this.mPw;
                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                    sb.append("  gdbclient app_process :");
                    sb.append(this.mGdbPort);
                    printWriter.println(sb.toString());
                    this.mPw.flush();
                    this.mGdbProcess = java.lang.Runtime.getRuntime().exec(new java.lang.String[]{"gdbserver", ":" + this.mGdbPort, "--attach", java.lang.Integer.toString(i)});
                    final java.io.InputStreamReader inputStreamReader = new java.io.InputStreamReader(this.mGdbProcess.getInputStream());
                    this.mGdbThread = new java.lang.Thread() { // from class: com.android.server.am.ActivityManagerShellCommand.MyActivityController.1
                        @Override // java.lang.Thread, java.lang.Runnable
                        public void run() {
                            java.io.BufferedReader bufferedReader = new java.io.BufferedReader(inputStreamReader);
                            int i3 = 0;
                            while (true) {
                                synchronized (com.android.server.am.ActivityManagerShellCommand.MyActivityController.this) {
                                    try {
                                        if (com.android.server.am.ActivityManagerShellCommand.MyActivityController.this.mGdbThread == null) {
                                            return;
                                        }
                                        if (i3 == 2) {
                                            com.android.server.am.ActivityManagerShellCommand.MyActivityController.this.mGotGdbPrint = true;
                                            com.android.server.am.ActivityManagerShellCommand.MyActivityController.this.notifyAll();
                                        }
                                        try {
                                            java.lang.String readLine = bufferedReader.readLine();
                                            if (readLine == null) {
                                                return;
                                            }
                                            com.android.server.am.ActivityManagerShellCommand.MyActivityController.this.mPw.println("GDB: " + readLine);
                                            com.android.server.am.ActivityManagerShellCommand.MyActivityController.this.mPw.flush();
                                            i3++;
                                        } catch (java.io.IOException e) {
                                            return;
                                        }
                                    } catch (java.lang.Throwable th) {
                                        throw th;
                                    }
                                }
                            }
                        }
                    };
                    this.mGdbThread.start();
                    try {
                        wait(500L);
                    } catch (java.lang.InterruptedException e) {
                    }
                } catch (java.io.IOException e2) {
                    this.mPw.println("Failure starting gdbserver: " + e2);
                    this.mPw.flush();
                    killGdbLocked();
                }
            }
            this.mState = i2;
            this.mPw.println("");
            printMessageForState();
            this.mPw.flush();
            while (this.mState != 0) {
                try {
                    wait();
                } catch (java.lang.InterruptedException e3) {
                }
            }
            killGdbLocked();
            return this.mResult;
        }

        void resumeController(int i) {
            synchronized (this) {
                this.mState = 0;
                this.mResult = i;
                notifyAll();
            }
        }

        void printMessageForState() {
            if ((this.mAlwaysContinue || this.mAlwaysKill) && this.mSimpleMode) {
                return;
            }
            switch (this.mState) {
                case 0:
                    this.mPw.println("Monitoring activity manager...  available commands:");
                    break;
                case 1:
                    this.mPw.println("Waiting after crash...  available commands:");
                    this.mPw.println("(c)ontinue: show crash dialog");
                    this.mPw.println("(k)ill: immediately kill app");
                    break;
                case 2:
                    this.mPw.println("Waiting after early ANR...  available commands:");
                    this.mPw.println("(c)ontinue: standard ANR processing");
                    this.mPw.println("(k)ill: immediately kill app");
                    break;
                case 3:
                    this.mPw.println("Waiting after ANR...  available commands:");
                    this.mPw.println("(c)ontinue: show ANR dialog");
                    this.mPw.println("(k)ill: immediately kill app");
                    this.mPw.println("(w)ait: wait some more");
                    break;
            }
            this.mPw.println("(q)uit: finish monitoring");
        }

        void run() throws android.os.RemoteException {
            boolean z;
            try {
                try {
                    printMessageForState();
                    this.mPw.flush();
                    this.mInterface.setActivityController(this, this.mMonkey);
                    this.mState = 0;
                    java.io.BufferedReader bufferedReader = new java.io.BufferedReader(new java.io.InputStreamReader(this.mInput));
                    while (true) {
                        java.lang.String readLine = bufferedReader.readLine();
                        if (readLine == null) {
                            break;
                        }
                        if (readLine.length() <= 0) {
                            z = false;
                        } else {
                            if ("q".equals(readLine) || "quit".equals(readLine)) {
                                break;
                            }
                            z = true;
                            if (this.mState == 1) {
                                if ("c".equals(readLine) || "continue".equals(readLine)) {
                                    resumeController(0);
                                } else if ("k".equals(readLine) || "kill".equals(readLine)) {
                                    resumeController(1);
                                } else {
                                    this.mPw.println("Invalid command: " + readLine);
                                }
                            } else if (this.mState == 3) {
                                if ("c".equals(readLine) || "continue".equals(readLine)) {
                                    resumeController(0);
                                } else if ("k".equals(readLine) || "kill".equals(readLine)) {
                                    resumeController(1);
                                } else if ("w".equals(readLine) || "wait".equals(readLine)) {
                                    resumeController(2);
                                } else {
                                    this.mPw.println("Invalid command: " + readLine);
                                }
                            } else if (this.mState == 2) {
                                if ("c".equals(readLine) || "continue".equals(readLine)) {
                                    resumeController(0);
                                } else if ("k".equals(readLine) || "kill".equals(readLine)) {
                                    resumeController(1);
                                } else {
                                    this.mPw.println("Invalid command: " + readLine);
                                }
                            } else {
                                this.mPw.println("Invalid command: " + readLine);
                            }
                        }
                        synchronized (this) {
                            if (z) {
                                try {
                                    this.mPw.println("");
                                } finally {
                                }
                            }
                            printMessageForState();
                            this.mPw.flush();
                        }
                    }
                    resumeController(0);
                } catch (java.io.IOException e) {
                    e.printStackTrace(this.mPw);
                    this.mPw.flush();
                }
                this.mInterface.setActivityController((android.app.IActivityController) null, this.mMonkey);
            } catch (java.lang.Throwable th) {
                this.mInterface.setActivityController((android.app.IActivityController) null, this.mMonkey);
                throw th;
            }
        }
    }

    int runMonitor(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        java.lang.String str = null;
        java.lang.String str2 = null;
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        boolean z4 = false;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                if (nextOption.equals("--gdb")) {
                    str = getNextArgRequired();
                } else if (nextOption.equals("-p")) {
                    str2 = getNextArgRequired();
                } else if (nextOption.equals("-m")) {
                    z = true;
                } else if (nextOption.equals("-s")) {
                    z2 = true;
                } else if (nextOption.equals("-c")) {
                    z3 = true;
                } else if (nextOption.equals("-k")) {
                    z4 = true;
                } else {
                    getErrPrintWriter().println("Error: Unknown option: " + nextOption);
                    return -1;
                }
            } else {
                if (z3 && z4) {
                    getErrPrintWriter().println("Error: -k and -c options can't be used together.");
                    return -1;
                }
                new com.android.server.am.ActivityManagerShellCommand.MyActivityController(this.mInterface, printWriter, getRawInputStream(), str, z, z2, str2, z3, z4).run();
                return 0;
            }
        }
    }

    static final class MyUidObserver extends android.app.UidObserver implements com.android.server.am.ActivityManagerService.OomAdjObserver {
        static final int STATE_NORMAL = 0;
        final java.io.InputStream mInput;
        final android.app.IActivityManager mInterface;
        final com.android.server.am.ActivityManagerService mInternal;
        final int mMask;
        final java.io.PrintWriter mPw;
        int mState;
        final int mUid;

        MyUidObserver(com.android.server.am.ActivityManagerService activityManagerService, java.io.PrintWriter printWriter, java.io.InputStream inputStream, int i, int i2) {
            this.mInterface = activityManagerService;
            this.mInternal = activityManagerService;
            this.mPw = printWriter;
            this.mInput = inputStream;
            this.mUid = i;
            this.mMask = i2;
        }

        public void onUidStateChanged(int i, int i2, long j, int i3) {
            synchronized (this) {
                try {
                    android.os.StrictMode.ThreadPolicy allowThreadDiskWrites = android.os.StrictMode.allowThreadDiskWrites();
                    try {
                        this.mPw.print(i);
                        this.mPw.print(" procstate ");
                        this.mPw.print(com.android.server.am.ProcessList.makeProcStateString(i2));
                        this.mPw.print(" seq ");
                        this.mPw.print(j);
                        this.mPw.print(" capability ");
                        this.mPw.println(this.mMask & i3);
                        this.mPw.flush();
                    } finally {
                        android.os.StrictMode.setThreadPolicy(allowThreadDiskWrites);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void onUidGone(int i, boolean z) {
            synchronized (this) {
                try {
                    android.os.StrictMode.ThreadPolicy allowThreadDiskWrites = android.os.StrictMode.allowThreadDiskWrites();
                    try {
                        this.mPw.print(i);
                        this.mPw.print(" gone");
                        if (z) {
                            this.mPw.print(" disabled");
                        }
                        this.mPw.println();
                        this.mPw.flush();
                        android.os.StrictMode.setThreadPolicy(allowThreadDiskWrites);
                    } catch (java.lang.Throwable th) {
                        android.os.StrictMode.setThreadPolicy(allowThreadDiskWrites);
                        throw th;
                    }
                } catch (java.lang.Throwable th2) {
                    throw th2;
                }
            }
        }

        public void onUidActive(int i) {
            synchronized (this) {
                try {
                    android.os.StrictMode.ThreadPolicy allowThreadDiskWrites = android.os.StrictMode.allowThreadDiskWrites();
                    try {
                        this.mPw.print(i);
                        this.mPw.println(" active");
                        this.mPw.flush();
                    } finally {
                        android.os.StrictMode.setThreadPolicy(allowThreadDiskWrites);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void onUidIdle(int i, boolean z) {
            synchronized (this) {
                try {
                    android.os.StrictMode.ThreadPolicy allowThreadDiskWrites = android.os.StrictMode.allowThreadDiskWrites();
                    try {
                        this.mPw.print(i);
                        this.mPw.print(" idle");
                        if (z) {
                            this.mPw.print(" disabled");
                        }
                        this.mPw.println();
                        this.mPw.flush();
                        android.os.StrictMode.setThreadPolicy(allowThreadDiskWrites);
                    } catch (java.lang.Throwable th) {
                        android.os.StrictMode.setThreadPolicy(allowThreadDiskWrites);
                        throw th;
                    }
                } catch (java.lang.Throwable th2) {
                    throw th2;
                }
            }
        }

        public void onUidCachedChanged(int i, boolean z) {
            synchronized (this) {
                try {
                    android.os.StrictMode.ThreadPolicy allowThreadDiskWrites = android.os.StrictMode.allowThreadDiskWrites();
                    try {
                        this.mPw.print(i);
                        this.mPw.println(z ? " cached" : " uncached");
                        this.mPw.flush();
                        android.os.StrictMode.setThreadPolicy(allowThreadDiskWrites);
                    } catch (java.lang.Throwable th) {
                        android.os.StrictMode.setThreadPolicy(allowThreadDiskWrites);
                        throw th;
                    }
                } catch (java.lang.Throwable th2) {
                    throw th2;
                }
            }
        }

        @Override // com.android.server.am.ActivityManagerService.OomAdjObserver
        public void onOomAdjMessage(java.lang.String str) {
            synchronized (this) {
                try {
                    android.os.StrictMode.ThreadPolicy allowThreadDiskWrites = android.os.StrictMode.allowThreadDiskWrites();
                    try {
                        this.mPw.print("# ");
                        this.mPw.println(str);
                        this.mPw.flush();
                    } finally {
                        android.os.StrictMode.setThreadPolicy(allowThreadDiskWrites);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        void printMessageForState() {
            switch (this.mState) {
                case 0:
                    this.mPw.println("Watching uid states...  available commands:");
                    break;
            }
            this.mPw.println("(q)uit: finish watching");
        }

        /* JADX WARN: Code restructure failed: missing block: B:36:0x00a3, code lost:
        
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:43:0x00a0, code lost:
        
            if (r6.mUid < 0) goto L34;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        void run() throws android.os.RemoteException {
            boolean z;
            try {
                try {
                    printMessageForState();
                    this.mPw.flush();
                    this.mInterface.registerUidObserver(this, 31, -1, (java.lang.String) null);
                    if (this.mUid >= 0) {
                        this.mInternal.setOomAdjObserver(this.mUid, this);
                    }
                    this.mState = 0;
                    java.io.BufferedReader bufferedReader = new java.io.BufferedReader(new java.io.InputStreamReader(this.mInput));
                    while (true) {
                        java.lang.String readLine = bufferedReader.readLine();
                        if (readLine == null) {
                            break;
                        }
                        if (readLine.length() > 0) {
                            if ("q".equals(readLine) || "quit".equals(readLine)) {
                                break;
                            }
                            this.mPw.println("Invalid command: " + readLine);
                            z = true;
                        } else {
                            z = false;
                        }
                        synchronized (this) {
                            if (z) {
                                try {
                                    this.mPw.println("");
                                } finally {
                                }
                            }
                            printMessageForState();
                            this.mPw.flush();
                        }
                    }
                } catch (java.io.IOException e) {
                    e.printStackTrace(this.mPw);
                    this.mPw.flush();
                }
            } finally {
                if (this.mUid >= 0) {
                    this.mInternal.clearOomAdjObserver();
                }
                this.mInterface.unregisterUidObserver(this);
            }
        }
    }

    int runWatchUids(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        int i = -1;
        int i2 = 15;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                if (nextOption.equals("--oom")) {
                    i = java.lang.Integer.parseInt(getNextArgRequired());
                } else if (nextOption.equals("--mask")) {
                    i2 = java.lang.Integer.parseInt(getNextArgRequired());
                } else {
                    getErrPrintWriter().println("Error: Unknown option: " + nextOption);
                    return -1;
                }
            } else {
                new com.android.server.am.ActivityManagerShellCommand.MyUidObserver(this.mInternal, printWriter, getRawInputStream(), i, i2).run();
                return 0;
            }
        }
    }

    int runHang(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        boolean z = false;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                if (nextOption.equals("--allow-restart")) {
                    z = true;
                } else {
                    getErrPrintWriter().println("Error: Unknown option: " + nextOption);
                    return -1;
                }
            } else {
                printWriter.println("Hanging the system...");
                printWriter.flush();
                try {
                    this.mInterface.hang(getShellCallback().getShellCallbackBinder(), z);
                    return 0;
                } catch (java.lang.NullPointerException e) {
                    printWriter.println("Hanging failed, since caller " + android.os.Binder.getCallingPid() + " did not provide a ShellCallback!");
                    printWriter.flush();
                    return 1;
                }
            }
        }
    }

    int runRestart(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        java.lang.String nextOption = getNextOption();
        if (nextOption != null) {
            getErrPrintWriter().println("Error: Unknown option: " + nextOption);
            return -1;
        }
        printWriter.println("Restart the system...");
        printWriter.flush();
        this.mInterface.restart();
        return 0;
    }

    int runIdleMaintenance(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        java.lang.String nextOption = getNextOption();
        if (nextOption != null) {
            getErrPrintWriter().println("Error: Unknown option: " + nextOption);
            return -1;
        }
        printWriter.println("Performing idle maintenance...");
        this.mInterface.sendIdleJobTrigger();
        this.mInternal.performIdleMaintenance();
        return 0;
    }

    int runScreenCompat(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        boolean z;
        int i;
        java.lang.String nextArgRequired = getNextArgRequired();
        if ("on".equals(nextArgRequired)) {
            z = true;
        } else if ("off".equals(nextArgRequired)) {
            z = false;
        } else {
            getErrPrintWriter().println("Error: enabled mode must be 'on' or 'off' at " + nextArgRequired);
            return -1;
        }
        java.lang.String nextArgRequired2 = getNextArgRequired();
        do {
            try {
                android.app.IActivityManager iActivityManager = this.mInterface;
                if (z) {
                    i = 1;
                } else {
                    i = 0;
                }
                iActivityManager.setPackageScreenCompatMode(nextArgRequired2, i);
            } catch (android.os.RemoteException e) {
            }
            nextArgRequired2 = getNextArg();
        } while (nextArgRequired2 != null);
        return 0;
    }

    int runPackageImportance(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        printWriter.println(android.app.ActivityManager.RunningAppProcessInfo.procStateToImportance(this.mInterface.getPackageProcessState(getNextArgRequired(), "com.android.shell")));
        return 0;
    }

    int runToUri(java.io.PrintWriter printWriter, int i) throws android.os.RemoteException {
        try {
            printWriter.println(makeIntent(-2).toUri(i));
            return 0;
        } catch (java.net.URISyntaxException e) {
            throw new java.lang.RuntimeException(e.getMessage(), e);
        }
    }

    private boolean switchUserAndWaitForComplete(final int i) throws android.os.RemoteException {
        android.content.pm.UserInfo currentUser = this.mInterface.getCurrentUser();
        if (currentUser != null && i == currentUser.id) {
            return true;
        }
        final java.util.concurrent.CountDownLatch countDownLatch = new java.util.concurrent.CountDownLatch(1);
        android.app.UserSwitchObserver userSwitchObserver = new android.app.UserSwitchObserver() { // from class: com.android.server.am.ActivityManagerShellCommand.3
            public void onUserSwitchComplete(int i2) {
                if (i == i2) {
                    countDownLatch.countDown();
                }
            }
        };
        try {
            this.mInterface.registerUserSwitchObserver(userSwitchObserver, com.android.server.am.ActivityManagerShellCommand.class.getName());
            boolean switchUser = this.mInterface.switchUser(i);
            if (!switchUser) {
                this.mInterface.unregisterUserSwitchObserver(userSwitchObserver);
                return false;
            }
            try {
                switchUser = countDownLatch.await(120000L, java.util.concurrent.TimeUnit.MILLISECONDS);
            } catch (java.lang.InterruptedException e) {
                getErrPrintWriter().println("Error: Thread interrupted unexpectedly.");
            }
            return switchUser;
        } finally {
            this.mInterface.unregisterUserSwitchObserver(userSwitchObserver);
        }
    }

    int runSwitchUser(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        boolean z = false;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption == null) {
                int parseInt = java.lang.Integer.parseInt(getNextArgRequired());
                int userSwitchability = ((android.os.UserManager) this.mInternal.mContext.getSystemService(android.os.UserManager.class)).getUserSwitchability(android.os.UserHandle.of(parseInt));
                if (userSwitchability != 0) {
                    getErrPrintWriter().println("Error: UserSwitchabilityResult=" + userSwitchability);
                    return -1;
                }
                android.os.Trace.traceBegin(64L, "shell_runSwitchUser");
                try {
                    if (z ? switchUserAndWaitForComplete(parseInt) : this.mInterface.switchUser(parseInt)) {
                        android.os.Trace.traceEnd(64L);
                        return 0;
                    }
                    printWriter.printf("Error: Failed to switch to user %d\n", java.lang.Integer.valueOf(parseInt));
                    android.os.Trace.traceEnd(64L);
                    return 1;
                } catch (java.lang.Throwable th) {
                    android.os.Trace.traceEnd(64L);
                    throw th;
                }
            }
            if (!"-w".equals(nextOption)) {
                getErrPrintWriter().println("Error: unknown option: " + nextOption);
                return -1;
            }
            z = true;
        }
    }

    int runGetCurrentUser(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        int currentUserId = this.mInterface.getCurrentUserId();
        if (currentUserId == -10000) {
            throw new java.lang.IllegalStateException("Current user not set");
        }
        printWriter.println(currentUserId);
        return 0;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    int runStartUser(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        boolean startUserInBackgroundVisibleOnDisplay;
        boolean z;
        boolean z2 = false;
        int i = -1;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption == null) {
                int parseInt = java.lang.Integer.parseInt(getNextArgRequired());
                android.os.IProgressListener progressWaiter = z2 ? new com.android.server.am.ActivityManagerShellCommand.ProgressWaiter(parseInt) : null;
                com.android.server.pm.UserManagerInternal userManagerInternal = (com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class);
                android.app.ActivityManagerInternal activityManagerInternal = (android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class);
                int profileParentId = userManagerInternal.getProfileParentId(parseInt);
                int currentUserId = activityManagerInternal.getCurrentUserId();
                boolean z3 = profileParentId != parseInt;
                boolean z4 = z3 && profileParentId == currentUserId;
                com.android.server.utils.Slogf.d(TAG, "runStartUser(): userId=%d, parentUserId=%d, currentUserId=%d, isProfile=%b, isVisibleProfile=%b, display=%d, waiter=%s", java.lang.Integer.valueOf(parseInt), java.lang.Integer.valueOf(profileParentId), java.lang.Integer.valueOf(currentUserId), java.lang.Boolean.valueOf(z3), java.lang.Boolean.valueOf(z4), java.lang.Integer.valueOf(i), progressWaiter);
                android.os.Trace.traceBegin(64L, "shell_runStartUser" + parseInt);
                java.lang.String str = "";
                try {
                    if (z4) {
                        com.android.server.utils.Slogf.d(TAG, "calling startProfileWithListener(%d, %s)", java.lang.Integer.valueOf(parseInt), progressWaiter);
                        startUserInBackgroundVisibleOnDisplay = this.mInterface.startProfileWithListener(parseInt, progressWaiter);
                    } else if (i == -1) {
                        com.android.server.utils.Slogf.d(TAG, "calling startUserInBackgroundWithListener(%d)", java.lang.Integer.valueOf(parseInt));
                        startUserInBackgroundVisibleOnDisplay = this.mInterface.startUserInBackgroundWithListener(parseInt, progressWaiter);
                    } else {
                        if (!android.os.UserManager.isVisibleBackgroundUsersEnabled()) {
                            printWriter.println("Not supported");
                            android.os.Trace.traceEnd(64L);
                            return -1;
                        }
                        com.android.server.utils.Slogf.d(TAG, "calling startUserInBackgroundVisibleOnDisplay(%d, %d, %s)", java.lang.Integer.valueOf(parseInt), java.lang.Integer.valueOf(i), progressWaiter);
                        startUserInBackgroundVisibleOnDisplay = this.mInterface.startUserInBackgroundVisibleOnDisplay(parseInt, i, progressWaiter);
                        str = " on display " + i;
                    }
                    if (z2 && startUserInBackgroundVisibleOnDisplay) {
                        com.android.server.utils.Slogf.d(TAG, "waiting %d ms", java.lang.Integer.valueOf(USER_OPERATION_TIMEOUT_MS));
                        startUserInBackgroundVisibleOnDisplay = progressWaiter.waitForFinish(120000L);
                    }
                    android.os.Trace.traceEnd(64L);
                    if (startUserInBackgroundVisibleOnDisplay) {
                        printWriter.println("Success: user started" + str);
                    } else {
                        getErrPrintWriter().println("Error: could not start user" + str);
                    }
                    return 0;
                } catch (java.lang.Throwable th) {
                    android.os.Trace.traceEnd(64L);
                    throw th;
                }
            }
            switch (nextOption.hashCode()) {
                case -1237221598:
                    if (nextOption.equals("--display")) {
                        z = true;
                        break;
                    }
                    z = -1;
                    break;
                case 1514:
                    if (nextOption.equals("-w")) {
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
                    z2 = true;
                    break;
                case true:
                    i = getDisplayIdFromNextArg();
                    break;
                default:
                    getErrPrintWriter().println("Error: unknown option: " + nextOption);
                    return -1;
            }
        }
    }

    int runUnlockUser(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        int parseInt = java.lang.Integer.parseInt(getNextArgRequired());
        java.lang.String nextArg = getNextArg();
        if (!android.text.TextUtils.isEmpty(nextArg) && !"!".equals(nextArg)) {
            getErrPrintWriter().println("Error: token parameter not supported");
            return -1;
        }
        java.lang.String nextArg2 = getNextArg();
        if (!android.text.TextUtils.isEmpty(nextArg2) && !"!".equals(nextArg2)) {
            getErrPrintWriter().println("Error: secret parameter not supported");
            return -1;
        }
        if (this.mInterface.unlockUser2(parseInt, (android.os.IProgressListener) null)) {
            printWriter.println("Success: user unlocked");
            return 0;
        }
        getErrPrintWriter().println("Error: could not unlock user");
        return 0;
    }

    static final class StopUserCallback extends android.app.IStopUserCallback.Stub {
        private boolean mFinished;
        private final int mUserId;

        private StopUserCallback(int i) {
            this.mFinished = false;
            this.mUserId = i;
        }

        public synchronized void waitForFinish() {
            while (!this.mFinished) {
                try {
                    wait();
                } catch (java.lang.InterruptedException e) {
                    throw new java.lang.IllegalStateException(e);
                }
            }
            com.android.server.utils.Slogf.d(com.android.server.am.ActivityManagerShellCommand.TAG, "user %d finished stopping", java.lang.Integer.valueOf(this.mUserId));
        }

        public synchronized void userStopped(int i) {
            com.android.server.utils.Slogf.d(com.android.server.am.ActivityManagerShellCommand.TAG, "StopUserCallback: userStopped(%d)", java.lang.Integer.valueOf(i));
            this.mFinished = true;
            notifyAll();
        }

        public synchronized void userStopAborted(int i) {
            com.android.server.utils.Slogf.d(com.android.server.am.ActivityManagerShellCommand.TAG, "StopUserCallback: userStopAborted(%d)", java.lang.Integer.valueOf(i));
            this.mFinished = true;
            notifyAll();
        }

        public java.lang.String toString() {
            return "ProgressWaiter[userId=" + this.mUserId + ", finished=" + this.mFinished + "]";
        }
    }

    int runStopUser(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        boolean z = false;
        boolean z2 = false;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption == null) {
                int parseInt = java.lang.Integer.parseInt(getNextArgRequired());
                android.app.IStopUserCallback stopUserCallback = z ? new com.android.server.am.ActivityManagerShellCommand.StopUserCallback(parseInt) : null;
                com.android.server.utils.Slogf.d(TAG, "Calling stopUser(%d, %b, %s)", java.lang.Integer.valueOf(parseInt), java.lang.Boolean.valueOf(z2), stopUserCallback);
                android.os.Trace.traceBegin(64L, "shell_runStopUser-" + parseInt + "-[stopUser]");
                try {
                    int stopUser = this.mInterface.stopUser(parseInt, z2, stopUserCallback);
                    if (stopUser == 0) {
                        if (stopUserCallback != null) {
                            stopUserCallback.waitForFinish();
                        }
                        android.os.Trace.traceEnd(64L);
                        return 0;
                    }
                    java.lang.String str = "";
                    switch (stopUser) {
                        case -4:
                            str = " (Can't stop user " + parseInt + " - one of its related users can't be stopped)";
                            break;
                        case -3:
                            str = " (System user cannot be stopped)";
                            break;
                        case -2:
                            str = " (Can't stop current user)";
                            break;
                        case -1:
                            str = " (Unknown user " + parseInt + ")";
                            break;
                    }
                    getErrPrintWriter().println("Switch failed: " + stopUser + str);
                    android.os.Trace.traceEnd(64L);
                    return -1;
                } catch (java.lang.Throwable th) {
                    android.os.Trace.traceEnd(64L);
                    throw th;
                }
            }
            if ("-w".equals(nextOption)) {
                z = true;
            } else {
                if (!"-f".equals(nextOption)) {
                    getErrPrintWriter().println("Error: unknown option: " + nextOption);
                    return -1;
                }
                z2 = true;
            }
        }
    }

    int runIsUserStopped(java.io.PrintWriter printWriter) {
        printWriter.println(this.mInternal.isUserStopped(android.os.UserHandle.parseUserArg(getNextArgRequired())));
        return 0;
    }

    int runGetStartedUserState(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        this.mInternal.enforceCallingPermission("android.permission.DUMP", "runGetStartedUserState()");
        int parseInt = java.lang.Integer.parseInt(getNextArgRequired());
        try {
            printWriter.println(this.mInternal.getStartedUserState(parseInt));
            return 0;
        } catch (java.lang.NullPointerException e) {
            printWriter.println("User is not started: " + parseInt);
            return 0;
        }
    }

    int runTrackAssociations(java.io.PrintWriter printWriter) {
        this.mInternal.enforceCallingPermission("android.permission.SET_ACTIVITY_WATCHER", "runTrackAssociations()");
        com.android.server.am.ActivityManagerService activityManagerService = this.mInternal;
        com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                if (!this.mInternal.mTrackingAssociations) {
                    this.mInternal.mTrackingAssociations = true;
                    printWriter.println("Association tracking started.");
                } else {
                    printWriter.println("Association tracking already enabled.");
                }
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
        return 0;
    }

    int runUntrackAssociations(java.io.PrintWriter printWriter) {
        this.mInternal.enforceCallingPermission("android.permission.SET_ACTIVITY_WATCHER", "runUntrackAssociations()");
        com.android.server.am.ActivityManagerService activityManagerService = this.mInternal;
        com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                if (this.mInternal.mTrackingAssociations) {
                    this.mInternal.mTrackingAssociations = false;
                    this.mInternal.mAssociations.clear();
                    printWriter.println("Association tracking stopped.");
                } else {
                    printWriter.println("Association tracking not running.");
                }
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
        return 0;
    }

    int getUidState(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        this.mInternal.enforceCallingPermission("android.permission.DUMP", "getUidState()");
        int uidState = this.mInternal.getUidState(java.lang.Integer.parseInt(getNextArgRequired()));
        printWriter.print(uidState);
        printWriter.print(" (");
        printWriter.printf(android.util.DebugUtils.valueToString(android.app.ActivityManager.class, "PROCESS_STATE_", uidState), new java.lang.Object[0]);
        printWriter.println(")");
        return 0;
    }

    private java.util.List<android.content.res.Configuration> getRecentConfigurations(int i) {
        android.app.usage.IUsageStatsManager asInterface = android.app.usage.IUsageStatsManager.Stub.asInterface(android.os.ServiceManager.getService("usagestats"));
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        try {
            android.content.pm.ParceledListSlice queryConfigurationStats = asInterface.queryConfigurationStats(4, currentTimeMillis - ((((i * 24) * 60) * 60) * 1000), currentTimeMillis, "com.android.shell");
            if (queryConfigurationStats == null) {
                return java.util.Collections.emptyList();
            }
            final android.util.ArrayMap arrayMap = new android.util.ArrayMap();
            java.util.List list = queryConfigurationStats.getList();
            int size = list.size();
            for (int i2 = 0; i2 < size; i2++) {
                android.app.usage.ConfigurationStats configurationStats = (android.app.usage.ConfigurationStats) list.get(i2);
                int indexOfKey = arrayMap.indexOfKey(configurationStats.getConfiguration());
                if (indexOfKey < 0) {
                    arrayMap.put(configurationStats.getConfiguration(), java.lang.Integer.valueOf(configurationStats.getActivationCount()));
                } else {
                    arrayMap.setValueAt(indexOfKey, java.lang.Integer.valueOf(((java.lang.Integer) arrayMap.valueAt(indexOfKey)).intValue() + configurationStats.getActivationCount()));
                }
            }
            java.util.Comparator<android.content.res.Configuration> comparator = new java.util.Comparator<android.content.res.Configuration>() { // from class: com.android.server.am.ActivityManagerShellCommand.4
                @Override // java.util.Comparator
                public int compare(android.content.res.Configuration configuration, android.content.res.Configuration configuration2) {
                    return ((java.lang.Integer) arrayMap.get(configuration2)).compareTo((java.lang.Integer) arrayMap.get(configuration));
                }
            };
            java.util.ArrayList arrayList = new java.util.ArrayList(arrayMap.size());
            arrayList.addAll(arrayMap.keySet());
            java.util.Collections.sort(arrayList, comparator);
            return arrayList;
        } catch (android.os.RemoteException e) {
            return java.util.Collections.emptyList();
        }
    }

    private static void addExtensionsForConfig(javax.microedition.khronos.egl.EGL10 egl10, javax.microedition.khronos.egl.EGLDisplay eGLDisplay, javax.microedition.khronos.egl.EGLConfig eGLConfig, int[] iArr, int[] iArr2, java.util.Set<java.lang.String> set) {
        javax.microedition.khronos.egl.EGLContext eglCreateContext = egl10.eglCreateContext(eGLDisplay, eGLConfig, javax.microedition.khronos.egl.EGL10.EGL_NO_CONTEXT, iArr2);
        if (eglCreateContext == javax.microedition.khronos.egl.EGL10.EGL_NO_CONTEXT) {
            return;
        }
        javax.microedition.khronos.egl.EGLSurface eglCreatePbufferSurface = egl10.eglCreatePbufferSurface(eGLDisplay, eGLConfig, iArr);
        if (eglCreatePbufferSurface == javax.microedition.khronos.egl.EGL10.EGL_NO_SURFACE) {
            egl10.eglDestroyContext(eGLDisplay, eglCreateContext);
            return;
        }
        egl10.eglMakeCurrent(eGLDisplay, eglCreatePbufferSurface, eglCreatePbufferSurface, eglCreateContext);
        java.lang.String glGetString = android.opengl.GLES10.glGetString(7939);
        if (!android.text.TextUtils.isEmpty(glGetString)) {
            for (java.lang.String str : glGetString.split(" ")) {
                set.add(str);
            }
        }
        egl10.eglMakeCurrent(eGLDisplay, javax.microedition.khronos.egl.EGL10.EGL_NO_SURFACE, javax.microedition.khronos.egl.EGL10.EGL_NO_SURFACE, javax.microedition.khronos.egl.EGL10.EGL_NO_CONTEXT);
        egl10.eglDestroySurface(eGLDisplay, eglCreatePbufferSurface);
        egl10.eglDestroyContext(eGLDisplay, eglCreateContext);
    }

    java.util.Set<java.lang.String> getGlExtensionsFromDriver() {
        int i;
        java.util.HashSet hashSet = new java.util.HashSet();
        javax.microedition.khronos.egl.EGL10 egl10 = (javax.microedition.khronos.egl.EGL10) javax.microedition.khronos.egl.EGLContext.getEGL();
        if (egl10 == null) {
            getErrPrintWriter().println("Warning: couldn't get EGL");
            return hashSet;
        }
        javax.microedition.khronos.egl.EGLDisplay eglGetDisplay = egl10.eglGetDisplay(javax.microedition.khronos.egl.EGL10.EGL_DEFAULT_DISPLAY);
        egl10.eglInitialize(eglGetDisplay, new int[2]);
        int[] iArr = new int[1];
        if (!egl10.eglGetConfigs(eglGetDisplay, null, 0, iArr)) {
            getErrPrintWriter().println("Warning: couldn't get EGL config count");
            return hashSet;
        }
        javax.microedition.khronos.egl.EGLConfig[] eGLConfigArr = new javax.microedition.khronos.egl.EGLConfig[iArr[0]];
        if (egl10.eglGetConfigs(eglGetDisplay, eGLConfigArr, iArr[0], iArr)) {
            int[] iArr2 = {12375, 1, 12374, 1, 12344};
            int[] iArr3 = {12440, 2, 12344};
            int[] iArr4 = new int[1];
            for (int i2 = 0; i2 < iArr[0]; i2 = i + 1) {
                egl10.eglGetConfigAttrib(eglGetDisplay, eGLConfigArr[i2], 12327, iArr4);
                if (iArr4[0] == 12368) {
                    i = i2;
                } else {
                    egl10.eglGetConfigAttrib(eglGetDisplay, eGLConfigArr[i2], 12339, iArr4);
                    if ((iArr4[0] & 1) == 0) {
                        i = i2;
                    } else {
                        egl10.eglGetConfigAttrib(eglGetDisplay, eGLConfigArr[i2], 12352, iArr4);
                        if ((iArr4[0] & 1) == 0) {
                            i = i2;
                        } else {
                            i = i2;
                            addExtensionsForConfig(egl10, eglGetDisplay, eGLConfigArr[i2], iArr2, null, hashSet);
                        }
                        if ((iArr4[0] & 4) != 0) {
                            addExtensionsForConfig(egl10, eglGetDisplay, eGLConfigArr[i], iArr2, iArr3, hashSet);
                        }
                    }
                }
            }
            egl10.eglTerminate(eglGetDisplay);
            return hashSet;
        }
        getErrPrintWriter().println("Warning: couldn't get EGL configs");
        return hashSet;
    }

    private void writeDeviceConfig(android.util.proto.ProtoOutputStream protoOutputStream, long j, java.io.PrintWriter printWriter, android.content.res.Configuration configuration, android.util.DisplayMetrics displayMetrics) {
        long j2;
        if (protoOutputStream == null) {
            j2 = -1;
        } else {
            j2 = protoOutputStream.start(j);
            protoOutputStream.write(1155346202625L, displayMetrics.widthPixels);
            protoOutputStream.write(1155346202626L, displayMetrics.heightPixels);
            protoOutputStream.write(1155346202627L, android.util.DisplayMetrics.DENSITY_DEVICE_STABLE);
        }
        if (printWriter != null) {
            printWriter.print("stable-width-px: ");
            printWriter.println(displayMetrics.widthPixels);
            printWriter.print("stable-height-px: ");
            printWriter.println(displayMetrics.heightPixels);
            printWriter.print("stable-density-dpi: ");
            printWriter.println(android.util.DisplayMetrics.DENSITY_DEVICE_STABLE);
        }
        com.android.internal.util.MemInfoReader memInfoReader = new com.android.internal.util.MemInfoReader();
        memInfoReader.readMemInfo();
        android.app.KeyguardManager keyguardManager = (android.app.KeyguardManager) this.mInternal.mContext.getSystemService(android.app.KeyguardManager.class);
        if (protoOutputStream != null) {
            protoOutputStream.write(1116691496964L, memInfoReader.getTotalSize());
            protoOutputStream.write(1133871366149L, android.app.ActivityManager.isLowRamDeviceStatic());
            protoOutputStream.write(1155346202630L, java.lang.Runtime.getRuntime().availableProcessors());
            protoOutputStream.write(1133871366151L, keyguardManager.isDeviceSecure());
        }
        if (printWriter != null) {
            printWriter.print("total-ram: ");
            printWriter.println(memInfoReader.getTotalSize());
            printWriter.print("low-ram: ");
            printWriter.println(android.app.ActivityManager.isLowRamDeviceStatic());
            printWriter.print("max-cores: ");
            printWriter.println(java.lang.Runtime.getRuntime().availableProcessors());
            printWriter.print("has-secure-screen-lock: ");
            printWriter.println(keyguardManager.isDeviceSecure());
        }
        try {
            android.content.pm.ConfigurationInfo deviceConfigurationInfo = this.mTaskInterface.getDeviceConfigurationInfo();
            if (deviceConfigurationInfo.reqGlEsVersion != 0) {
                if (protoOutputStream != null) {
                    protoOutputStream.write(1155346202632L, deviceConfigurationInfo.reqGlEsVersion);
                }
                if (printWriter != null) {
                    printWriter.print("opengl-version: 0x");
                    printWriter.println(java.lang.Integer.toHexString(deviceConfigurationInfo.reqGlEsVersion));
                }
            }
            java.util.Set<java.lang.String> glExtensionsFromDriver = getGlExtensionsFromDriver();
            java.lang.String[] strArr = (java.lang.String[]) glExtensionsFromDriver.toArray(new java.lang.String[glExtensionsFromDriver.size()]);
            java.util.Arrays.sort(strArr);
            for (int i = 0; i < strArr.length; i++) {
                if (protoOutputStream != null) {
                    protoOutputStream.write(2237677961225L, strArr[i]);
                }
                if (printWriter != null) {
                    printWriter.print("opengl-extensions: ");
                    printWriter.println(strArr[i]);
                }
            }
            android.content.pm.PackageManager packageManager = this.mInternal.mContext.getPackageManager();
            java.util.List<android.content.pm.SharedLibraryInfo> sharedLibraries = packageManager.getSharedLibraries(0);
            java.util.Collections.sort(sharedLibraries, java.util.Comparator.comparing(new java.util.function.Function() { // from class: com.android.server.am.ActivityManagerShellCommand$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    return ((android.content.pm.SharedLibraryInfo) obj).getName();
                }
            }));
            for (int i2 = 0; i2 < sharedLibraries.size(); i2++) {
                if (protoOutputStream != null) {
                    protoOutputStream.write(2237677961226L, sharedLibraries.get(i2).getName());
                }
                if (printWriter != null) {
                    printWriter.print("shared-libraries: ");
                    printWriter.println(sharedLibraries.get(i2).getName());
                }
            }
            android.content.pm.FeatureInfo[] systemAvailableFeatures = packageManager.getSystemAvailableFeatures();
            java.util.Arrays.sort(systemAvailableFeatures, new java.util.Comparator() { // from class: com.android.server.am.ActivityManagerShellCommand$$ExternalSyntheticLambda1
                @Override // java.util.Comparator
                public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                    int lambda$writeDeviceConfig$0;
                    lambda$writeDeviceConfig$0 = com.android.server.am.ActivityManagerShellCommand.lambda$writeDeviceConfig$0((android.content.pm.FeatureInfo) obj, (android.content.pm.FeatureInfo) obj2);
                    return lambda$writeDeviceConfig$0;
                }
            });
            for (int i3 = 0; i3 < systemAvailableFeatures.length; i3++) {
                if (systemAvailableFeatures[i3].name != null) {
                    if (protoOutputStream != null) {
                        protoOutputStream.write(2237677961227L, systemAvailableFeatures[i3].name);
                    }
                    if (printWriter != null) {
                        printWriter.print("features: ");
                        printWriter.println(systemAvailableFeatures[i3].name);
                    }
                }
            }
            if (protoOutputStream != null) {
                protoOutputStream.end(j2);
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$writeDeviceConfig$0(android.content.pm.FeatureInfo featureInfo, android.content.pm.FeatureInfo featureInfo2) {
        if (featureInfo.name == featureInfo2.name) {
            return 0;
        }
        if (featureInfo.name == null) {
            return -1;
        }
        if (featureInfo2.name == null) {
            return 1;
        }
        return featureInfo.name.compareTo(featureInfo2.name);
    }

    private int getDisplayIdFromNextArg() {
        int parseInt = java.lang.Integer.parseInt(getNextArgRequired());
        if (parseInt < 0) {
            throw new java.lang.IllegalArgumentException("--display must be a non-negative integer");
        }
        return parseInt;
    }

    int runGetConfig(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        java.util.List<android.content.res.Configuration> recentConfigurations;
        int size;
        int i = -1;
        int i2 = 0;
        boolean z = false;
        boolean z2 = false;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                if (nextOption.equals("--days")) {
                    i = java.lang.Integer.parseInt(getNextArgRequired());
                    if (i <= 0) {
                        throw new java.lang.IllegalArgumentException("--days must be a positive integer");
                    }
                } else if (nextOption.equals("--proto")) {
                    z = true;
                } else if (nextOption.equals("--device")) {
                    z2 = true;
                } else if (nextOption.equals("--display")) {
                    i2 = getDisplayIdFromNextArg();
                } else {
                    getErrPrintWriter().println("Error: Unknown option: " + nextOption);
                    return -1;
                }
            } else {
                android.content.res.Configuration configuration = this.mInterface.getConfiguration();
                if (configuration == null) {
                    getErrPrintWriter().println("Activity manager has no configuration");
                    return -1;
                }
                android.view.Display display = ((android.hardware.display.DisplayManager) this.mInternal.mContext.getSystemService(android.hardware.display.DisplayManager.class)).getDisplay(i2);
                if (display == null) {
                    getErrPrintWriter().println("Error: Display does not exist: " + i2);
                    return -1;
                }
                android.util.DisplayMetrics displayMetrics = new android.util.DisplayMetrics();
                display.getMetrics(displayMetrics);
                if (z) {
                    android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream(getOutFileDescriptor());
                    configuration.writeResConfigToProto(protoOutputStream, 1146756268033L, displayMetrics);
                    if (z2) {
                        writeDeviceConfig(protoOutputStream, 1146756268034L, null, configuration, displayMetrics);
                    }
                    protoOutputStream.flush();
                } else {
                    printWriter.println("config: " + android.content.res.Configuration.resourceQualifierString(configuration, displayMetrics));
                    printWriter.println("abi: " + android.text.TextUtils.join(",", android.os.Build.SUPPORTED_ABIS));
                    if (z2) {
                        writeDeviceConfig(null, -1L, printWriter, configuration, displayMetrics);
                    }
                    if (i >= 0 && (size = (recentConfigurations = getRecentConfigurations(i)).size()) > 0) {
                        printWriter.println("recentConfigs:");
                        for (int i3 = 0; i3 < size; i3++) {
                            printWriter.println("  config: " + android.content.res.Configuration.resourceQualifierString(recentConfigurations.get(i3)));
                        }
                    }
                }
                return 0;
            }
        }
    }

    int runSuppressResizeConfigChanges(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        this.mTaskInterface.suppressResizeConfigChanges(java.lang.Boolean.valueOf(getNextArgRequired()).booleanValue());
        return 0;
    }

    int runSetInactive(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        int i = -2;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                if (nextOption.equals("--user")) {
                    i = android.os.UserHandle.parseUserArg(getNextArgRequired());
                } else {
                    getErrPrintWriter().println("Error: Unknown option: " + nextOption);
                    return -1;
                }
            } else {
                android.app.usage.IUsageStatsManager.Stub.asInterface(android.os.ServiceManager.getService("usagestats")).setAppInactive(getNextArgRequired(), java.lang.Boolean.parseBoolean(getNextArgRequired()), i);
                return 0;
            }
        }
    }

    private int bucketNameToBucketValue(java.lang.String str) {
        java.lang.String lowerCase = str.toLowerCase();
        if (lowerCase.startsWith("ac")) {
            return 10;
        }
        if (lowerCase.startsWith("wo")) {
            return 20;
        }
        if (lowerCase.startsWith("fr")) {
            return 30;
        }
        if (lowerCase.startsWith("ra")) {
            return 40;
        }
        if (lowerCase.startsWith("re")) {
            return 45;
        }
        if (lowerCase.startsWith("ne")) {
            return 50;
        }
        try {
            return java.lang.Integer.parseInt(lowerCase);
        } catch (java.lang.NumberFormatException e) {
            getErrPrintWriter().println("Error: Unknown bucket: " + str);
            return -1;
        }
    }

    int runSetStandbyBucket(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        int i = -2;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                if (nextOption.equals("--user")) {
                    i = android.os.UserHandle.parseUserArg(getNextArgRequired());
                } else {
                    getErrPrintWriter().println("Error: Unknown option: " + nextOption);
                    return -1;
                }
            } else {
                java.lang.String nextArgRequired = getNextArgRequired();
                java.lang.String nextArgRequired2 = getNextArgRequired();
                int bucketNameToBucketValue = bucketNameToBucketValue(nextArgRequired2);
                if (bucketNameToBucketValue < 0) {
                    return -1;
                }
                boolean z = peekNextArg() != null;
                android.app.usage.IUsageStatsManager asInterface = android.app.usage.IUsageStatsManager.Stub.asInterface(android.os.ServiceManager.getService("usagestats"));
                if (!z) {
                    asInterface.setAppStandbyBucket(nextArgRequired, bucketNameToBucketValue(nextArgRequired2), i);
                } else {
                    java.util.ArrayList arrayList = new java.util.ArrayList();
                    arrayList.add(new android.app.usage.AppStandbyInfo(nextArgRequired, bucketNameToBucketValue));
                    while (true) {
                        java.lang.String nextArg = getNextArg();
                        if (nextArg == null) {
                            break;
                        }
                        int bucketNameToBucketValue2 = bucketNameToBucketValue(getNextArgRequired());
                        if (bucketNameToBucketValue2 >= 0) {
                            arrayList.add(new android.app.usage.AppStandbyInfo(nextArg, bucketNameToBucketValue2));
                        }
                    }
                    asInterface.setAppStandbyBuckets(new android.content.pm.ParceledListSlice(arrayList), i);
                }
                return 0;
            }
        }
    }

    int runGetStandbyBucket(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        int i = -2;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                if (nextOption.equals("--user")) {
                    i = android.os.UserHandle.parseUserArg(getNextArgRequired());
                } else {
                    getErrPrintWriter().println("Error: Unknown option: " + nextOption);
                    return -1;
                }
            } else {
                java.lang.String nextArg = getNextArg();
                android.app.usage.IUsageStatsManager asInterface = android.app.usage.IUsageStatsManager.Stub.asInterface(android.os.ServiceManager.getService("usagestats"));
                if (nextArg != null) {
                    printWriter.println(asInterface.getAppStandbyBucket(nextArg, (java.lang.String) null, i));
                    return 0;
                }
                for (android.app.usage.AppStandbyInfo appStandbyInfo : asInterface.getAppStandbyBuckets("com.android.shell", i).getList()) {
                    printWriter.print(appStandbyInfo.mPackageName);
                    printWriter.print(": ");
                    printWriter.println(appStandbyInfo.mStandbyBucket);
                }
                return 0;
            }
        }
    }

    int runGetInactive(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        int i = -2;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                if (nextOption.equals("--user")) {
                    i = android.os.UserHandle.parseUserArg(getNextArgRequired());
                } else {
                    getErrPrintWriter().println("Error: Unknown option: " + nextOption);
                    return -1;
                }
            } else {
                printWriter.println("Idle=" + android.app.usage.IUsageStatsManager.Stub.asInterface(android.os.ServiceManager.getService("usagestats")).isAppInactive(getNextArgRequired(), i, "com.android.shell"));
                return 0;
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    int runSendTrimMemory(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        char c;
        int i = -2;
        do {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                if (nextOption.equals("--user")) {
                    i = android.os.UserHandle.parseUserArg(getNextArgRequired());
                } else {
                    getErrPrintWriter().println("Error: Unknown option: " + nextOption);
                    return -1;
                }
            } else {
                java.lang.String nextArgRequired = getNextArgRequired();
                java.lang.String nextArgRequired2 = getNextArgRequired();
                int i2 = 5;
                switch (nextArgRequired2.hashCode()) {
                    case -1943119297:
                        if (nextArgRequired2.equals("RUNNING_CRITICAL")) {
                            c = 5;
                            break;
                        }
                        c = 65535;
                        break;
                    case -847101650:
                        if (nextArgRequired2.equals("BACKGROUND")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case -219160669:
                        if (nextArgRequired2.equals("RUNNING_MODERATE")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 163769603:
                        if (nextArgRequired2.equals("MODERATE")) {
                            c = 4;
                            break;
                        }
                        c = 65535;
                        break;
                    case 183181625:
                        if (nextArgRequired2.equals("COMPLETE")) {
                            c = 6;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1072631956:
                        if (nextArgRequired2.equals("RUNNING_LOW")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    case 2130809258:
                        if (nextArgRequired2.equals("HIDDEN")) {
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
                        i2 = 20;
                        break;
                    case 1:
                        break;
                    case 2:
                        i2 = 40;
                        break;
                    case 3:
                        i2 = 10;
                        break;
                    case 4:
                        i2 = 60;
                        break;
                    case 5:
                        i2 = 15;
                        break;
                    case 6:
                        i2 = 80;
                        break;
                    default:
                        try {
                            i2 = java.lang.Integer.parseInt(nextArgRequired2);
                            break;
                        } catch (java.lang.NumberFormatException e) {
                            getErrPrintWriter().println("Error: Unknown level option: " + nextArgRequired2);
                            return -1;
                        }
                }
                if (this.mInterface.setProcessMemoryTrimLevel(nextArgRequired, i, i2)) {
                    return 0;
                }
                getErrPrintWriter().println("Unknown error: failed to set trim level");
                return -1;
            }
        } while (i != -1);
        getErrPrintWriter().println("Error: Can't use user 'all'");
        return -1;
    }

    int runDisplay(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        boolean z;
        java.lang.String nextArgRequired = getNextArgRequired();
        switch (nextArgRequired.hashCode()) {
            case 1625698700:
                if (nextArgRequired.equals("move-stack")) {
                    z = false;
                    break;
                }
            default:
                z = -1;
                break;
        }
        switch (z) {
            case false:
                return runDisplayMoveStack(printWriter);
            default:
                getErrPrintWriter().println("Error: unknown command '" + nextArgRequired + "'");
                return -1;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    int runStack(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        char c;
        java.lang.String nextArgRequired = getNextArgRequired();
        switch (nextArgRequired.hashCode()) {
            case -934610812:
                if (nextArgRequired.equals("remove")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 3237038:
                if (nextArgRequired.equals("info")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 3322014:
                if (nextArgRequired.equals("list")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1022285313:
                if (nextArgRequired.equals("move-task")) {
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
                return runStackMoveTask(printWriter);
            case 1:
                return runStackList(printWriter);
            case 2:
                return runRootTaskInfo(printWriter);
            case 3:
                return runRootTaskRemove(printWriter);
            default:
                getErrPrintWriter().println("Error: unknown command '" + nextArgRequired + "'");
                return -1;
        }
    }

    private android.graphics.Rect getBounds() {
        java.lang.String nextArgRequired = getNextArgRequired();
        int parseInt = java.lang.Integer.parseInt(nextArgRequired);
        java.lang.String nextArgRequired2 = getNextArgRequired();
        int parseInt2 = java.lang.Integer.parseInt(nextArgRequired2);
        java.lang.String nextArgRequired3 = getNextArgRequired();
        int parseInt3 = java.lang.Integer.parseInt(nextArgRequired3);
        java.lang.String nextArgRequired4 = getNextArgRequired();
        int parseInt4 = java.lang.Integer.parseInt(nextArgRequired4);
        if (parseInt < 0) {
            getErrPrintWriter().println("Error: bad left arg: " + nextArgRequired);
            return null;
        }
        if (parseInt2 < 0) {
            getErrPrintWriter().println("Error: bad top arg: " + nextArgRequired2);
            return null;
        }
        if (parseInt3 <= 0) {
            getErrPrintWriter().println("Error: bad right arg: " + nextArgRequired3);
            return null;
        }
        if (parseInt4 <= 0) {
            getErrPrintWriter().println("Error: bad bottom arg: " + nextArgRequired4);
            return null;
        }
        return new android.graphics.Rect(parseInt, parseInt2, parseInt3, parseInt4);
    }

    int runDisplayMoveStack(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        this.mTaskInterface.moveRootTaskToDisplay(java.lang.Integer.parseInt(getNextArgRequired()), java.lang.Integer.parseInt(getNextArgRequired()));
        return 0;
    }

    int runStackMoveTask(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        boolean z;
        int parseInt = java.lang.Integer.parseInt(getNextArgRequired());
        int parseInt2 = java.lang.Integer.parseInt(getNextArgRequired());
        java.lang.String nextArgRequired = getNextArgRequired();
        if ("true".equals(nextArgRequired)) {
            z = true;
        } else if ("false".equals(nextArgRequired)) {
            z = false;
        } else {
            getErrPrintWriter().println("Error: bad toTop arg: " + nextArgRequired);
            return -1;
        }
        this.mTaskInterface.moveTaskToRootTask(parseInt, parseInt2, z);
        return 0;
    }

    int runStackList(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        java.util.Iterator it = this.mTaskInterface.getAllRootTaskInfos().iterator();
        while (it.hasNext()) {
            printWriter.println((android.app.ActivityTaskManager.RootTaskInfo) it.next());
        }
        return 0;
    }

    int runRootTaskInfo(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        printWriter.println(this.mTaskInterface.getRootTaskInfo(java.lang.Integer.parseInt(getNextArgRequired()), java.lang.Integer.parseInt(getNextArgRequired())));
        return 0;
    }

    int runRootTaskRemove(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        this.mTaskInterface.removeTask(java.lang.Integer.parseInt(getNextArgRequired()));
        return 0;
    }

    int runTask(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        java.lang.String nextArgRequired = getNextArgRequired();
        if (nextArgRequired.equals("lock")) {
            return runTaskLock(printWriter);
        }
        if (nextArgRequired.equals("resizeable")) {
            return runTaskResizeable(printWriter);
        }
        if (nextArgRequired.equals("resize")) {
            return runTaskResize(printWriter);
        }
        if (nextArgRequired.equals("focus")) {
            return runTaskFocus(printWriter);
        }
        getErrPrintWriter().println("Error: unknown command '" + nextArgRequired + "'");
        return -1;
    }

    int runTaskLock(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        java.lang.String nextArgRequired = getNextArgRequired();
        if (nextArgRequired.equals("stop")) {
            this.mTaskInterface.stopSystemLockTaskMode();
        } else {
            this.mTaskInterface.startSystemLockTaskMode(java.lang.Integer.parseInt(nextArgRequired));
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("Activity manager is ");
        sb.append(this.mTaskInterface.isInLockTaskMode() ? "" : "not ");
        sb.append("in lockTaskMode");
        printWriter.println(sb.toString());
        return 0;
    }

    int runTaskResizeable(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        this.mTaskInterface.setTaskResizeable(java.lang.Integer.parseInt(getNextArgRequired()), java.lang.Integer.parseInt(getNextArgRequired()));
        return 0;
    }

    int runTaskResize(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        int parseInt = java.lang.Integer.parseInt(getNextArgRequired());
        android.graphics.Rect bounds = getBounds();
        if (bounds == null) {
            getErrPrintWriter().println("Error: invalid input bounds");
            return -1;
        }
        taskResize(parseInt, bounds, 0, false);
        return 0;
    }

    void taskResize(int i, android.graphics.Rect rect, int i2, boolean z) throws android.os.RemoteException {
        this.mTaskInterface.resizeTask(i, rect, z ? 1 : 0);
        try {
            java.lang.Thread.sleep(i2);
        } catch (java.lang.InterruptedException e) {
        }
    }

    int moveTask(int i, android.graphics.Rect rect, android.graphics.Rect rect2, int i2, int i3, boolean z, boolean z2, int i4) throws android.os.RemoteException {
        if (z) {
            while (i3 > 0 && ((z2 && rect.right < rect2.right) || (!z2 && rect.bottom < rect2.bottom))) {
                if (z2) {
                    int min = java.lang.Math.min(i2, rect2.right - rect.right);
                    i3 -= min;
                    rect.right += min;
                    rect.left += min;
                } else {
                    int min2 = java.lang.Math.min(i2, rect2.bottom - rect.bottom);
                    i3 -= min2;
                    rect.top += min2;
                    rect.bottom += min2;
                }
                taskResize(i, rect, i4, false);
            }
        } else {
            while (i3 < 0 && ((z2 && rect.left > rect2.left) || (!z2 && rect.top > rect2.top))) {
                if (z2) {
                    int min3 = java.lang.Math.min(i2, rect.left - rect2.left);
                    i3 -= min3;
                    rect.right -= min3;
                    rect.left -= min3;
                } else {
                    int min4 = java.lang.Math.min(i2, rect.top - rect2.top);
                    i3 -= min4;
                    rect.top -= min4;
                    rect.bottom -= min4;
                }
                taskResize(i, rect, i4, false);
            }
        }
        return i3;
    }

    int getStepSize(int i, int i2, int i3, boolean z) {
        int i4;
        if (z && i2 < i) {
            i -= i3;
            if (i2 <= i) {
                i4 = i3;
            } else {
                i4 = i3 - (i2 - i);
            }
        } else {
            i4 = 0;
        }
        if (!z && i2 > i) {
            int i5 = i + i3;
            return i2 < i5 ? i3 + (i5 - i2) : i3;
        }
        return i4;
    }

    int runTaskFocus(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        int parseInt = java.lang.Integer.parseInt(getNextArgRequired());
        printWriter.println("Setting focus to task " + parseInt);
        this.mTaskInterface.setFocusedTask(parseInt);
        return 0;
    }

    int runWrite(java.io.PrintWriter printWriter) {
        this.mInternal.enforceCallingPermission("android.permission.SET_ACTIVITY_WATCHER", "registerUidObserver()");
        this.mInternal.mAtmInternal.flushRecentTasks();
        printWriter.println("All tasks persisted.");
        return 0;
    }

    int runAttachAgent(java.io.PrintWriter printWriter) {
        this.mInternal.enforceCallingPermission("android.permission.SET_ACTIVITY_WATCHER", "attach-agent");
        java.lang.String nextArgRequired = getNextArgRequired();
        java.lang.String nextArgRequired2 = getNextArgRequired();
        java.lang.String nextArg = getNextArg();
        if (nextArg != null) {
            printWriter.println("Error: Unknown option: " + nextArg);
            return -1;
        }
        this.mInternal.attachAgent(nextArgRequired, nextArgRequired2);
        return 0;
    }

    int runSupportsMultiwindow(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        if (getResources(printWriter) == null) {
            return -1;
        }
        printWriter.println(android.app.ActivityTaskManager.supportsMultiWindow(this.mInternal.mContext));
        return 0;
    }

    int runSupportsSplitScreenMultiwindow(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        if (getResources(printWriter) == null) {
            return -1;
        }
        printWriter.println(android.app.ActivityTaskManager.supportsSplitScreenMultiWindow(this.mInternal.mContext));
        return 0;
    }

    int runUpdateApplicationInfo(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        int parseUserArg = android.os.UserHandle.parseUserArg(getNextArgRequired());
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add(getNextArgRequired());
        while (true) {
            java.lang.String nextArg = getNextArg();
            if (nextArg != null) {
                arrayList.add(nextArg);
            } else {
                this.mInternal.scheduleApplicationInfoChanged(arrayList, parseUserArg);
                printWriter.println("Packages updated with most recent ApplicationInfos.");
                return 0;
            }
        }
    }

    int runNoHomeScreen(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        android.content.res.Resources resources = getResources(printWriter);
        if (resources == null) {
            return -1;
        }
        printWriter.println(resources.getBoolean(android.R.bool.config_navBarDefaultTransparent));
        return 0;
    }

    int runWaitForBroadcastIdle(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        java.io.PrintWriter printWriter2 = new java.io.PrintWriter((java.io.Writer) new android.util.TeeWriter(new java.io.Writer[]{com.android.server.am.ActivityManagerDebugConfig.LOG_WRITER_INFO, printWriter}));
        boolean z = false;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                if (nextOption.equals("--flush-broadcast-loopers")) {
                    z = true;
                } else {
                    getErrPrintWriter().println("Error: Unknown option: " + nextOption);
                    return -1;
                }
            } else {
                this.mInternal.waitForBroadcastIdle(printWriter2, z);
                return 0;
            }
        }
    }

    int runWaitForBroadcastBarrier(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        java.io.PrintWriter printWriter2 = new java.io.PrintWriter((java.io.Writer) new android.util.TeeWriter(new java.io.Writer[]{com.android.server.am.ActivityManagerDebugConfig.LOG_WRITER_INFO, printWriter}));
        boolean z = false;
        boolean z2 = false;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                if (nextOption.equals("--flush-broadcast-loopers")) {
                    z = true;
                } else if (nextOption.equals("--flush-application-threads")) {
                    z2 = true;
                } else {
                    getErrPrintWriter().println("Error: Unknown option: " + nextOption);
                    return -1;
                }
            } else {
                this.mInternal.waitForBroadcastBarrier(printWriter2, z, z2);
                return 0;
            }
        }
    }

    int runWaitForApplicationBarrier(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        this.mInternal.waitForApplicationBarrier(new java.io.PrintWriter((java.io.Writer) new android.util.TeeWriter(new java.io.Writer[]{com.android.server.am.ActivityManagerDebugConfig.LOG_WRITER_INFO, printWriter})));
        return 0;
    }

    int runWaitForBroadcastDispatch(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        try {
            this.mInternal.waitForBroadcastDispatch(new java.io.PrintWriter((java.io.Writer) new android.util.TeeWriter(new java.io.Writer[]{com.android.server.am.ActivityManagerDebugConfig.LOG_WRITER_INFO, printWriter})), makeIntent(-2));
            return 0;
        } catch (java.net.URISyntaxException e) {
            throw new java.lang.RuntimeException(e.getMessage(), e);
        }
    }

    int runSetIgnoreDeliveryGroupPolicy(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        this.mInternal.setIgnoreDeliveryGroupPolicy(getNextArgRequired());
        return 0;
    }

    int runClearIgnoreDeliveryGroupPolicy(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        this.mInternal.clearIgnoreDeliveryGroupPolicy(getNextArgRequired());
        return 0;
    }

    int runRefreshSettingsCache() throws android.os.RemoteException {
        this.mInternal.refreshSettingsCache();
        return 0;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int runCompat(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        long lookupChangeId;
        int i;
        boolean z;
        boolean clearOverrideForTest;
        com.android.server.compat.PlatformCompat platformCompat = (com.android.server.compat.PlatformCompat) android.os.ServiceManager.getService("platform_compat");
        java.lang.String nextArgRequired = getNextArgRequired();
        char c = 1;
        boolean z2 = !"--no-kill".equals(getNextOption());
        long j = -1;
        if (nextArgRequired.endsWith("-all")) {
            nextArgRequired = nextArgRequired.substring(0, nextArgRequired.lastIndexOf("-all"));
            if (nextArgRequired.equals("reset")) {
                z = true;
                i = -1;
            } else {
                try {
                    i = java.lang.Integer.parseInt(getNextArgRequired());
                    z = true;
                } catch (java.lang.NumberFormatException e) {
                    printWriter.println("Invalid targetSdkVersion!");
                    return -1;
                }
            }
        } else {
            java.lang.String nextArgRequired2 = getNextArgRequired();
            try {
                lookupChangeId = java.lang.Long.parseLong(nextArgRequired2);
            } catch (java.lang.NumberFormatException e2) {
                lookupChangeId = platformCompat.lookupChangeId(nextArgRequired2);
            }
            if (lookupChangeId != -1) {
                i = -1;
                z = false;
                j = lookupChangeId;
            } else {
                printWriter.println("Unknown or invalid change: '" + nextArgRequired2 + "'.");
                return -1;
            }
        }
        java.lang.String nextArgRequired3 = getNextArgRequired();
        if (!z && !platformCompat.isKnownChangeId(j)) {
            printWriter.println("Warning! Change " + j + " is not known yet. Enabling/disabling it could have no effect.");
        }
        android.util.ArraySet arraySet = new android.util.ArraySet();
        android.util.ArraySet arraySet2 = new android.util.ArraySet();
        try {
            switch (nextArgRequired.hashCode()) {
                case -1298848381:
                    if (nextArgRequired.equals("enable")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 108404047:
                    if (nextArgRequired.equals("reset")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 1671308008:
                    if (nextArgRequired.equals("disable")) {
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
                    if (z) {
                        int enableTargetSdkChanges = platformCompat.enableTargetSdkChanges(nextArgRequired3, i);
                        if (enableTargetSdkChanges == 0) {
                            printWriter.println("No changes were enabled.");
                            return -1;
                        }
                        printWriter.println("Enabled " + enableTargetSdkChanges + " changes gated by targetSdkVersion " + i + " for " + nextArgRequired3 + ".");
                        return 0;
                    }
                    arraySet.add(java.lang.Long.valueOf(j));
                    com.android.internal.compat.CompatibilityChangeConfig compatibilityChangeConfig = new com.android.internal.compat.CompatibilityChangeConfig(new android.compat.Compatibility.ChangeConfig(arraySet, arraySet2));
                    if (z2) {
                        platformCompat.setOverrides(compatibilityChangeConfig, nextArgRequired3);
                    } else {
                        platformCompat.setOverridesForTest(compatibilityChangeConfig, nextArgRequired3);
                    }
                    printWriter.println("Enabled change " + j + " for " + nextArgRequired3 + ".");
                    return 0;
                case 1:
                    if (z) {
                        int disableTargetSdkChanges = platformCompat.disableTargetSdkChanges(nextArgRequired3, i);
                        if (disableTargetSdkChanges == 0) {
                            printWriter.println("No changes were disabled.");
                            return -1;
                        }
                        printWriter.println("Disabled " + disableTargetSdkChanges + " changes gated by targetSdkVersion " + i + " for " + nextArgRequired3 + ".");
                        return 0;
                    }
                    arraySet2.add(java.lang.Long.valueOf(j));
                    com.android.internal.compat.CompatibilityChangeConfig compatibilityChangeConfig2 = new com.android.internal.compat.CompatibilityChangeConfig(new android.compat.Compatibility.ChangeConfig(arraySet, arraySet2));
                    if (z2) {
                        platformCompat.setOverrides(compatibilityChangeConfig2, nextArgRequired3);
                    } else {
                        platformCompat.setOverridesForTest(compatibilityChangeConfig2, nextArgRequired3);
                    }
                    printWriter.println("Disabled change " + j + " for " + nextArgRequired3 + ".");
                    return 0;
                case 2:
                    if (z) {
                        if (z2) {
                            platformCompat.clearOverrides(nextArgRequired3);
                        } else {
                            platformCompat.clearOverridesForTest(nextArgRequired3);
                        }
                        printWriter.println("Reset all changes for " + nextArgRequired3 + " to default value.");
                        return 0;
                    }
                    if (z2) {
                        clearOverrideForTest = platformCompat.clearOverride(j, nextArgRequired3);
                    } else {
                        clearOverrideForTest = platformCompat.clearOverrideForTest(j, nextArgRequired3);
                    }
                    if (clearOverrideForTest) {
                        printWriter.println("Reset change " + j + " for " + nextArgRequired3 + " to default value.");
                        return 0;
                    }
                    printWriter.println("No override exists for changeId " + j + ".");
                    return 0;
                default:
                    printWriter.println("Invalid toggle value: '" + nextArgRequired + "'.");
                    return -1;
            }
        } catch (java.lang.SecurityException e3) {
            printWriter.println(e3.getMessage());
            return -1;
        }
    }

    private int runGetCurrentForegroundProcess(java.io.PrintWriter printWriter, android.app.IActivityManager iActivityManager) throws android.os.RemoteException {
        boolean z;
        com.android.server.am.ActivityManagerShellCommand.ProcessObserver processObserver = new com.android.server.am.ActivityManagerShellCommand.ProcessObserver(printWriter, iActivityManager);
        iActivityManager.registerProcessObserver(processObserver);
        java.io.BufferedReader bufferedReader = new java.io.BufferedReader(new java.io.InputStreamReader(getRawInputStream()));
        while (true) {
            try {
                try {
                    java.lang.String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    if (readLine.length() <= 0) {
                        z = false;
                    } else {
                        if ("q".equals(readLine) || "quit".equals(readLine)) {
                            break;
                        }
                        printWriter.println("Invalid command: " + readLine);
                        z = true;
                    }
                    if (z) {
                        printWriter.println("");
                    }
                    printWriter.flush();
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                    printWriter.flush();
                }
            } finally {
                iActivityManager.unregisterProcessObserver(processObserver);
            }
        }
        return 0;
    }

    static final class ProcessObserver extends android.app.IProcessObserver.Stub {
        private android.app.IActivityManager mIam;
        private java.io.PrintWriter mPw;

        ProcessObserver(java.io.PrintWriter printWriter, android.app.IActivityManager iActivityManager) {
            this.mPw = printWriter;
            this.mIam = iActivityManager;
        }

        public void onForegroundActivitiesChanged(int i, int i2, boolean z) {
            if (z) {
                try {
                    if (this.mIam.getUidProcessState(i2, com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME) == 2) {
                        this.mPw.println("New foreground process: " + i);
                    } else {
                        this.mPw.println("No top app found");
                    }
                    this.mPw.flush();
                } catch (android.os.RemoteException e) {
                    this.mPw.println("Error occurred in binder call");
                    this.mPw.flush();
                }
            }
        }

        public void onProcessStarted(int i, int i2, int i3, java.lang.String str, java.lang.String str2) {
        }

        public void onForegroundServicesChanged(int i, int i2, int i3) {
        }

        public void onProcessDied(int i, int i2) {
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int runSetMemoryFactor(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        boolean z;
        java.lang.String nextArgRequired = getNextArgRequired();
        int i = 2;
        switch (nextArgRequired.hashCode()) {
            case -1986416409:
                if (nextArgRequired.equals(com.android.server.utils.PriorityDump.PRIORITY_ARG_NORMAL)) {
                    z = false;
                    break;
                }
                z = -1;
                break;
            case -1560189025:
                if (nextArgRequired.equals(com.android.server.utils.PriorityDump.PRIORITY_ARG_CRITICAL)) {
                    z = 3;
                    break;
                }
                z = -1;
                break;
            case 75572:
                if (nextArgRequired.equals("LOW")) {
                    z = 2;
                    break;
                }
                z = -1;
                break;
            case 163769603:
                if (nextArgRequired.equals("MODERATE")) {
                    z = true;
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
                i = 0;
                break;
            case true:
                i = 1;
                break;
            case true:
                break;
            case true:
                i = 3;
                break;
            default:
                try {
                    i = java.lang.Integer.parseInt(nextArgRequired);
                } catch (java.lang.NumberFormatException e) {
                    i = -1;
                }
                if (i < 0 || i > 3) {
                    getErrPrintWriter().println("Error: Unknown level option: " + nextArgRequired);
                    return -1;
                }
        }
        this.mInternal.setMemFactorOverride(i);
        return 0;
    }

    private int runShowMemoryFactor(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        switch (this.mInternal.getMemoryTrimLevel()) {
            case -1:
                printWriter.println("<UNKNOWN>");
                break;
            case 0:
                printWriter.println(com.android.server.utils.PriorityDump.PRIORITY_ARG_NORMAL);
                break;
            case 1:
                printWriter.println("MODERATE");
                break;
            case 2:
                printWriter.println("LOW");
                break;
            case 3:
                printWriter.println(com.android.server.utils.PriorityDump.PRIORITY_ARG_CRITICAL);
                break;
        }
        printWriter.flush();
        return 0;
    }

    private int runResetMemoryFactor(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        this.mInternal.setMemFactorOverride(-1);
        return 0;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int runMemoryFactor(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        char c;
        this.mInternal.enforceCallingPermission("android.permission.WRITE_SECURE_SETTINGS", "runMemoryFactor()");
        java.lang.String nextArgRequired = getNextArgRequired();
        switch (nextArgRequired.hashCode()) {
            case 113762:
                if (nextArgRequired.equals("set")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 3529469:
                if (nextArgRequired.equals("show")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 108404047:
                if (nextArgRequired.equals("reset")) {
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
            case 0:
                return runSetMemoryFactor(printWriter);
            case 1:
                return runShowMemoryFactor(printWriter);
            case 2:
                return runResetMemoryFactor(printWriter);
            default:
                getErrPrintWriter().println("Error: unknown command '" + nextArgRequired + "'");
                return -1;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int runServiceRestartBackoff(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        char c;
        this.mInternal.enforceCallingPermission("android.permission.SET_PROCESS_LIMIT", "runServiceRestartBackoff()");
        java.lang.String nextArgRequired = getNextArgRequired();
        switch (nextArgRequired.hashCode()) {
            case -1298848381:
                if (nextArgRequired.equals("enable")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 3529469:
                if (nextArgRequired.equals("show")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 1671308008:
                if (nextArgRequired.equals("disable")) {
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
                this.mInternal.setServiceRestartBackoffEnabled(getNextArgRequired(), true, "shell");
                return 0;
            case 1:
                this.mInternal.setServiceRestartBackoffEnabled(getNextArgRequired(), false, "shell");
                return 0;
            case 2:
                printWriter.println(this.mInternal.isServiceRestartBackoffEnabled(getNextArgRequired()) ? com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_ENABLED : com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_DISABLED);
                return 0;
            default:
                getErrPrintWriter().println("Error: unknown command '" + nextArgRequired + "'");
                return -1;
        }
    }

    private int runGetIsolatedProcesses(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        this.mInternal.enforceCallingPermission("android.permission.DUMP", "getIsolatedProcesses()");
        java.util.List isolatedProcesses = this.mInternal.mInternal.getIsolatedProcesses(java.lang.Integer.parseInt(getNextArgRequired()));
        printWriter.print("[");
        if (isolatedProcesses != null) {
            int size = isolatedProcesses.size();
            for (int i = 0; i < size; i++) {
                if (i > 0) {
                    printWriter.print(", ");
                }
                printWriter.print(isolatedProcesses.get(i));
            }
        }
        printWriter.println("]");
        return 0;
    }

    private int runSetStopUserOnSwitch(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        int i;
        this.mInternal.enforceCallingPermission("android.permission.INTERACT_ACROSS_USERS_FULL", "setStopUserOnSwitch()");
        java.lang.String nextArg = getNextArg();
        if (nextArg == null) {
            com.android.server.utils.Slogf.i(TAG, "setStopUserOnSwitch(): resetting to default value");
            this.mInternal.setStopUserOnSwitch(-1);
            printWriter.println("Reset to default value");
            return 0;
        }
        boolean parseBoolean = java.lang.Boolean.parseBoolean(nextArg);
        if (parseBoolean) {
            i = 1;
        } else {
            i = 0;
        }
        com.android.server.utils.Slogf.i(TAG, "runSetStopUserOnSwitch(): setting to %d (%b)", java.lang.Integer.valueOf(i), java.lang.Boolean.valueOf(parseBoolean));
        this.mInternal.setStopUserOnSwitch(i);
        printWriter.println("Set to " + parseBoolean);
        return 0;
    }

    private int runSetBgAbusiveUids(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        java.lang.String nextArg = getNextArg();
        com.android.server.am.AppBatteryTracker appBatteryTracker = (com.android.server.am.AppBatteryTracker) this.mInternal.mAppRestrictionController.getAppStateTracker(com.android.server.am.AppBatteryTracker.class);
        if (appBatteryTracker == null) {
            getErrPrintWriter().println("Unable to get bg battery tracker");
            return -1;
        }
        if (nextArg == null) {
            appBatteryTracker.clearDebugUidPercentage();
            return 0;
        }
        java.lang.String[] split = nextArg.split(",");
        int[] iArr = new int[split.length];
        double[][] dArr = new double[split.length][];
        for (int i = 0; i < split.length; i++) {
            try {
                java.lang.String[] split2 = split[i].split("=");
                if (split2.length != 2) {
                    getErrPrintWriter().println("Malformed input");
                    return -1;
                }
                iArr[i] = java.lang.Integer.parseInt(split2[0]);
                java.lang.String[] split3 = split2[1].split(":");
                if (split3.length != 5) {
                    getErrPrintWriter().println("Malformed input");
                    return -1;
                }
                dArr[i] = new double[split3.length];
                for (int i2 = 0; i2 < split3.length; i2++) {
                    dArr[i][i2] = java.lang.Double.parseDouble(split3[i2]);
                }
            } catch (java.lang.NumberFormatException e) {
                getErrPrintWriter().println("Malformed input");
                return -1;
            }
        }
        appBatteryTracker.setDebugUidPercentage(iArr, dArr);
        return 0;
    }

    private int runListBgExemptionsConfig(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        android.util.ArraySet<java.lang.String> arraySet = this.mInternal.mAppRestrictionController.mBgRestrictionExemptioFromSysConfig;
        if (arraySet != null) {
            int size = arraySet.size();
            for (int i = 0; i < size; i++) {
                printWriter.print(arraySet.valueAt(i));
                printWriter.print(' ');
            }
            printWriter.println();
        }
        return 0;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int restrictionNameToLevel(java.lang.String str) {
        char c;
        java.lang.String lowerCase = str.toLowerCase();
        switch (lowerCase.hashCode()) {
            case -1502662066:
                if (lowerCase.equals("restricted_bucket")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -1126569803:
                if (lowerCase.equals("hibernation")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -775446516:
                if (lowerCase.equals("background_restricted")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 824339380:
                if (lowerCase.equals("unrestricted")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 1351638995:
                if (lowerCase.equals("adaptive_bucket")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 2052103358:
                if (lowerCase.equals("exempted")) {
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
                return 10;
            case 1:
                return 20;
            case 2:
                return 30;
            case 3:
                return 40;
            case 4:
                return 50;
            case 5:
                return 60;
            default:
                return 0;
        }
    }

    int runSetBgRestrictionLevel(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        int i = -2;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                if (nextOption.equals("--user")) {
                    i = android.os.UserHandle.parseUserArg(getNextArgRequired());
                } else {
                    getErrPrintWriter().println("Error: Unknown option: " + nextOption);
                    return -1;
                }
            } else {
                java.lang.String nextArgRequired = getNextArgRequired();
                int restrictionNameToLevel = restrictionNameToLevel(getNextArgRequired());
                if (restrictionNameToLevel == 0) {
                    printWriter.println("Error: invalid restriction level");
                    return -1;
                }
                try {
                    this.mInternal.setBackgroundRestrictionLevel(nextArgRequired, this.mInternal.mContext.getPackageManager().getPackageUidAsUser(nextArgRequired, android.content.pm.PackageManager.PackageInfoFlags.of(4194304L), i), i, restrictionNameToLevel, 1024, 0);
                    return 0;
                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                    printWriter.println("Error: userId:" + i + " package:" + nextArgRequired + " is not found");
                    return -1;
                }
            }
        }
    }

    int runGetBgRestrictionLevel(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        int i = -2;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                if (nextOption.equals("--user")) {
                    i = android.os.UserHandle.parseUserArg(getNextArgRequired());
                } else {
                    getErrPrintWriter().println("Error: Unknown option: " + nextOption);
                    return -1;
                }
            } else {
                printWriter.println(android.app.ActivityManager.restrictionLevelToName(this.mInternal.getBackgroundRestrictionLevel(getNextArgRequired(), i)));
                return 0;
            }
        }
    }

    int runSetForegroundServiceDelegate(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        boolean z;
        int i = -2;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                if (nextOption.equals("--user")) {
                    i = android.os.UserHandle.parseUserArg(getNextArgRequired());
                } else {
                    getErrPrintWriter().println("Error: Unknown option: " + nextOption);
                    return -1;
                }
            } else {
                java.lang.String nextArgRequired = getNextArgRequired();
                java.lang.String nextArgRequired2 = getNextArgRequired();
                if ("start".equals(nextArgRequired2)) {
                    z = true;
                } else if ("stop".equals(nextArgRequired2)) {
                    z = false;
                } else {
                    printWriter.println("Error: action is either start or stop");
                    return -1;
                }
                try {
                    this.mInternal.setForegroundServiceDelegate(nextArgRequired, this.mInternal.mContext.getPackageManager().getPackageUidAsUser(nextArgRequired, android.content.pm.PackageManager.PackageInfoFlags.of(4194304L), i), z, 12, "FgsDelegate");
                    return 0;
                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                    printWriter.println("Error: userId:" + i + " package:" + nextArgRequired + " is not found");
                    return -1;
                }
            }
        }
    }

    int runResetDropboxRateLimiter() throws android.os.RemoteException {
        this.mInternal.resetDropboxRateLimiter();
        return 0;
    }

    int runListDisplaysForStartingUsers(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        java.lang.String str;
        int[] displayIdsForStartingVisibleBackgroundUsers = this.mInterface.getDisplayIdsForStartingVisibleBackgroundUsers();
        if (displayIdsForStartingVisibleBackgroundUsers == null || displayIdsForStartingVisibleBackgroundUsers.length == 0) {
            str = "none";
        } else {
            str = java.util.Arrays.toString(displayIdsForStartingVisibleBackgroundUsers);
        }
        printWriter.println(str);
        return 0;
    }

    private android.content.res.Resources getResources(java.io.PrintWriter printWriter) throws android.os.RemoteException {
        android.content.res.Configuration configuration = this.mInterface.getConfiguration();
        if (configuration == null) {
            printWriter.println("Error: Activity manager has no configuration");
            return null;
        }
        android.util.DisplayMetrics displayMetrics = new android.util.DisplayMetrics();
        displayMetrics.setToDefaults();
        return new android.content.res.Resources(android.content.res.AssetManager.getSystem(), displayMetrics, configuration);
    }

    public void onHelp() {
        dumpHelp(getOutPrintWriter(), this.mDumping);
    }

    @dalvik.annotation.optimization.NeverCompile
    static void dumpHelp(java.io.PrintWriter printWriter, boolean z) {
        if (z) {
            printWriter.println("Activity manager dump options:");
            printWriter.println("  [-a] [-c] [-p PACKAGE] [-h] [WHAT] ...");
            printWriter.println("  WHAT may be one of:");
            printWriter.println("    a[ctivities]: activity stack state");
            printWriter.println("    r[recents]: recent activities state");
            printWriter.println("    b[roadcasts] [PACKAGE_NAME] [history [-s]]: broadcast state");
            printWriter.println("    broadcast-stats [PACKAGE_NAME]: aggregated broadcast statistics");
            printWriter.println("    i[ntents] [PACKAGE_NAME]: pending intent state");
            printWriter.println("    p[rocesses] [PACKAGE_NAME]: process state");
            printWriter.println("    o[om]: out of memory management");
            printWriter.println("    perm[issions]: URI permission grant state");
            printWriter.println("    prov[iders] [COMP_SPEC ...]: content provider state");
            printWriter.println("    provider [COMP_SPEC]: provider client-side state");
            printWriter.println("    s[ervices] [COMP_SPEC ...]: service state");
            printWriter.println("    allowed-associations: current package association restrictions");
            printWriter.println("    as[sociations]: tracked app associations");
            printWriter.println("    start-info [PACKAGE_NAME]: historical process start information");
            printWriter.println("    exit-info [PACKAGE_NAME]: historical process exit information");
            printWriter.println("    lmk: stats on low memory killer");
            printWriter.println("    lru: raw LRU process list");
            printWriter.println("    binder-proxies: stats on binder objects and IPCs");
            printWriter.println("    settings: currently applied config settings");
            printWriter.println("    timers: the current ANR timer state");
            printWriter.println("    service [COMP_SPEC]: service client-side state");
            printWriter.println("    package [PACKAGE_NAME]: all state related to given package");
            printWriter.println("    all: dump all activities");
            printWriter.println("    top: dump the top activity");
            printWriter.println("    users: user state");
            printWriter.println("  WHAT may also be a COMP_SPEC to dump activities.");
            printWriter.println("  COMP_SPEC may be a component name (com.foo/.myApp),");
            printWriter.println("    a partial substring in a component name, a");
            printWriter.println("    hex object identifier.");
            printWriter.println("  -a: include all available server state.");
            printWriter.println("  -c: include client state.");
            printWriter.println("  -p: limit output to given package.");
            printWriter.println("  -d: limit output to given display.");
            printWriter.println("  --checkin: output checkin format, resetting data.");
            printWriter.println("  --C: output checkin format, not resetting data.");
            printWriter.println("  --proto: output dump in protocol buffer format.");
            printWriter.printf("  %s: dump just the DUMPABLE-related state of an activity. Use the %s option to list the supported DUMPABLEs\n", "--dump-dumpable", "--list-dumpables");
            printWriter.printf("  %s: show the available dumpables in an activity\n", "--list-dumpables");
            return;
        }
        printWriter.println("Activity manager (activity) commands:");
        printWriter.println("  help");
        printWriter.println("      Print this help text.");
        printWriter.println("  start-activity [-D] [-N] [-W] [-P <FILE>] [--start-profiler <FILE>]");
        printWriter.println("          [--sampling INTERVAL] [--clock-type <TYPE>] [--streaming]");
        printWriter.println("          [-R COUNT] [-S] [--track-allocation]");
        printWriter.println("          [--user <USER_ID> | current] [--suspend] <INTENT>");
        printWriter.println("      Start an Activity.  Options are:");
        printWriter.println("      -D: enable debugging");
        printWriter.println("      --suspend: debugged app suspend threads at startup (only with -D)");
        printWriter.println("      -N: enable native debugging");
        printWriter.println("      -W: wait for launch to complete (initial display)");
        printWriter.println("      --start-profiler <FILE>: start profiler and send results to <FILE>");
        printWriter.println("      --sampling INTERVAL: use sample profiling with INTERVAL microseconds");
        printWriter.println("          between samples (use with --start-profiler)");
        printWriter.println("      --clock-type <TYPE>: type can be wall / thread-cpu / dual. Specify");
        printWriter.println("          the clock that is used to report the timestamps when profiling");
        printWriter.println("          The default value is dual. (use with --start-profiler)");
        printWriter.println("      --streaming: stream the profiling output to the specified file");
        printWriter.println("          (use with --start-profiler)");
        printWriter.println("      -P <FILE>: like above, but profiling stops when app goes idle");
        printWriter.println("      --attach-agent <agent>: attach the given agent before binding");
        printWriter.println("      --attach-agent-bind <agent>: attach the given agent during binding");
        printWriter.println("      -R: repeat the activity launch <COUNT> times.  Prior to each repeat,");
        printWriter.println("          the top activity will be finished.");
        printWriter.println("      -S: force stop the target app before starting the activity");
        printWriter.println("      --track-allocation: enable tracking of object allocations");
        printWriter.println("      --user <USER_ID> | current: Specify which user to run as; if not");
        printWriter.println("          specified then run as the current user.");
        printWriter.println("      --windowingMode <WINDOWING_MODE>: The windowing mode to launch the activity into.");
        printWriter.println("      --activityType <ACTIVITY_TYPE>: The activity type to launch the activity as.");
        printWriter.println("      --display <DISPLAY_ID>: The display to launch the activity into.");
        printWriter.println("      --splashscreen-icon: Show the splash screen icon on launch.");
        printWriter.println("  start-service [--user <USER_ID> | current] <INTENT>");
        printWriter.println("      Start a Service.  Options are:");
        printWriter.println("      --user <USER_ID> | current: Specify which user to run as; if not");
        printWriter.println("          specified then run as the current user.");
        printWriter.println("  start-foreground-service [--user <USER_ID> | current] <INTENT>");
        printWriter.println("      Start a foreground Service.  Options are:");
        printWriter.println("      --user <USER_ID> | current: Specify which user to run as; if not");
        printWriter.println("          specified then run as the current user.");
        printWriter.println("  stop-service [--user <USER_ID> | current] <INTENT>");
        printWriter.println("      Stop a Service.  Options are:");
        printWriter.println("      --user <USER_ID> | current: Specify which user to run as; if not");
        printWriter.println("          specified then run as the current user.");
        printWriter.println("  broadcast [--user <USER_ID> | all | current]");
        printWriter.println("          [--receiver-permission <PERMISSION>]");
        printWriter.println("          [--allow-background-activity-starts]");
        printWriter.println("          [--async] <INTENT>");
        printWriter.println("      Send a broadcast Intent.  Options are:");
        printWriter.println("      --user <USER_ID> | all | current: Specify which user to send to; if not");
        printWriter.println("          specified then send to all users.");
        printWriter.println("      --receiver-permission <PERMISSION>: Require receiver to hold permission.");
        printWriter.println("      --allow-background-activity-starts: The receiver may start activities");
        printWriter.println("          even if in the background.");
        printWriter.println("      --async: Send without waiting for the completion of the receiver.");
        printWriter.println("  compact [some|full] <process_name> [--user <USER_ID>]");
        printWriter.println("      Perform a single process compaction.");
        printWriter.println("      some: execute file compaction.");
        printWriter.println("      full: execute anon + file compaction.");
        printWriter.println("      system: system compaction.");
        printWriter.println("  compact system");
        printWriter.println("      Perform a full system compaction.");
        printWriter.println("  compact native [some|full] <pid>");
        printWriter.println("      Perform a native compaction for process with <pid>.");
        printWriter.println("      some: execute file compaction.");
        printWriter.println("      full: execute anon + file compaction.");
        printWriter.println("  freeze [--sticky] <processname> [--user <USER_ID>]");
        printWriter.println("      Freeze a process.");
        printWriter.println("        --sticky: persists the frozen state for the process lifetime or");
        printWriter.println("                  until an unfreeze is triggered via shell");
        printWriter.println("  unfreeze [--sticky] <processname> [--user <USER_ID>]");
        printWriter.println("      Unfreeze a process.");
        printWriter.println("        --sticky: persists the unfrozen state for the process lifetime or");
        printWriter.println("                  until a freeze is triggered via shell");
        printWriter.println("  instrument [-r] [-e <NAME> <VALUE>] [-p <FILE>] [-w]");
        printWriter.println("          [--user <USER_ID> | current]");
        printWriter.println("          [--no-hidden-api-checks [--no-test-api-access]]");
        printWriter.println("          [--no-isolated-storage]");
        printWriter.println("          [--no-window-animation] [--abi <ABI>] <COMPONENT>");
        printWriter.println("      Start an Instrumentation.  Typically this target <COMPONENT> is in the");
        printWriter.println("      form <TEST_PACKAGE>/<RUNNER_CLASS> or only <TEST_PACKAGE> if there");
        printWriter.println("      is only one instrumentation.  Options are:");
        printWriter.println("      -r: print raw results (otherwise decode REPORT_KEY_STREAMRESULT).  Use with");
        printWriter.println("          [-e perf true] to generate raw output for performance measurements.");
        printWriter.println("      -e <NAME> <VALUE>: set argument <NAME> to <VALUE>.  For test runners a");
        printWriter.println("          common form is [-e <testrunner_flag> <value>[,<value>...]].");
        printWriter.println("      -p <FILE>: write profiling data to <FILE>");
        printWriter.println("      -m: Write output as protobuf to stdout (machine readable)");
        printWriter.println("      -f <Optional PATH/TO/FILE>: Write output as protobuf to a file (machine");
        printWriter.println("          readable). If path is not specified, default directory and file name will");
        printWriter.println("          be used: /sdcard/instrument-logs/log-yyyyMMdd-hhmmss-SSS.instrumentation_data_proto");
        printWriter.println("      -w: wait for instrumentation to finish before returning.  Required for");
        printWriter.println("          test runners.");
        printWriter.println("      --user <USER_ID> | current: Specify user instrumentation runs in;");
        printWriter.println("          current user if not specified.");
        printWriter.println("      --no-hidden-api-checks: disable restrictions on use of hidden API.");
        printWriter.println("      --no-test-api-access: do not allow access to test APIs, if hidden");
        printWriter.println("          API checks are enabled.");
        printWriter.println("      --no-isolated-storage: don't use isolated storage sandbox and ");
        printWriter.println("          mount full external storage");
        printWriter.println("      --no-window-animation: turn off window animations while running.");
        printWriter.println("      --abi <ABI>: Launch the instrumented process with the selected ABI.");
        printWriter.println("          This assumes that the process supports the selected ABI.");
        printWriter.println("  trace-ipc [start|stop] [--dump-file <FILE>]");
        printWriter.println("      Trace IPC transactions.");
        printWriter.println("      start: start tracing IPC transactions.");
        printWriter.println("      stop: stop tracing IPC transactions and dump the results to file.");
        printWriter.println("      --dump-file <FILE>: Specify the file the trace should be dumped to.");
        printWriter.println("  profile start [--user <USER_ID> current]");
        printWriter.println("          [--clock-type <TYPE>]");
        printWriter.println("          [--sampling INTERVAL | --streaming] <PROCESS> <FILE>");
        printWriter.println("      Start profiler on a process.  The given <PROCESS> argument");
        printWriter.println("        may be either a process name or pid.  Options are:");
        printWriter.println("      --user <USER_ID> | current: When supplying a process name,");
        printWriter.println("          specify user of process to profile; uses current user if not");
        printWriter.println("          specified.");
        printWriter.println("      --clock-type <TYPE>: use the specified clock to report timestamps.");
        printWriter.println("          The type can be one of wall | thread-cpu | dual. The default");
        printWriter.println("          value is dual.");
        printWriter.println("      --sampling INTERVAL: use sample profiling with INTERVAL microseconds");
        printWriter.println("          between samples.");
        printWriter.println("      --streaming: stream the profiling output to the specified file.");
        printWriter.println("  profile stop [--user <USER_ID> current] <PROCESS>");
        printWriter.println("      Stop profiler on a process.  The given <PROCESS> argument");
        printWriter.println("        may be either a process name or pid.  Options are:");
        printWriter.println("      --user <USER_ID> | current: When supplying a process name,");
        printWriter.println("          specify user of process to profile; uses current user if not");
        printWriter.println("          specified.");
        printWriter.println("  dumpheap [--user <USER_ID> current] [-n] [-g] <PROCESS> <FILE>");
        printWriter.println("      Dump the heap of a process.  The given <PROCESS> argument may");
        printWriter.println("        be either a process name or pid.  Options are:");
        printWriter.println("      -n: dump native heap instead of managed heap");
        printWriter.println("      -g: force GC before dumping the heap");
        printWriter.println("      --user <USER_ID> | current: When supplying a process name,");
        printWriter.println("          specify user of process to dump; uses current user if not specified.");
        printWriter.println("  set-debug-app [-w] [--persistent] <PACKAGE>");
        printWriter.println("      Set application <PACKAGE> to debug.  Options are:");
        printWriter.println("      -w: wait for debugger when application starts");
        printWriter.println("      --persistent: retain this value");
        printWriter.println("  clear-debug-app");
        printWriter.println("      Clear the previously set-debug-app.");
        printWriter.println("  set-watch-heap <PROCESS> <MEM-LIMIT>");
        printWriter.println("      Start monitoring pss size of <PROCESS>, if it is at or");
        printWriter.println("      above <HEAP-LIMIT> then a heap dump is collected for the user to report.");
        printWriter.println("  clear-watch-heap");
        printWriter.println("      Clear the previously set-watch-heap.");
        printWriter.println("  clear-start-info [--user <USER_ID> | all | current] [package]");
        printWriter.println("      Clear the process start-info for given package");
        printWriter.println("  clear-exit-info [--user <USER_ID> | all | current] [package]");
        printWriter.println("      Clear the process exit-info for given package");
        printWriter.println("  bug-report [--progress | --telephony]");
        printWriter.println("      Request bug report generation; will launch a notification");
        printWriter.println("        when done to select where it should be delivered. Options are:");
        printWriter.println("     --progress: will launch a notification right away to show its progress.");
        printWriter.println("     --telephony: will dump only telephony sections.");
        printWriter.println("  fgs-notification-rate-limit {enable | disable}");
        printWriter.println("     Enable/disable rate limit on FGS notification deferral policy.");
        printWriter.println("  force-stop [--user <USER_ID> | all | current] <PACKAGE>");
        printWriter.println("      Completely stop the given application package.");
        printWriter.println("  stop-app [--user <USER_ID> | all | current] <PACKAGE>");
        printWriter.println("      Stop an app and all of its services.  Unlike `force-stop` this does");
        printWriter.println("      not cancel the app's scheduled alarms and jobs.");
        printWriter.println("  crash [--user <USER_ID>] <PACKAGE|PID>");
        printWriter.println("      Induce a VM crash in the specified package or process");
        printWriter.println("  kill [--user <USER_ID> | all | current] <PACKAGE>");
        printWriter.println("      Kill all background processes associated with the given application.");
        printWriter.println("  kill-all");
        printWriter.println("      Kill all processes that are safe to kill (cached, etc).");
        printWriter.println("  make-uid-idle [--user <USER_ID> | all | current] <PACKAGE>");
        printWriter.println("      If the given application's uid is in the background and waiting to");
        printWriter.println("      become idle (not allowing background services), do that now.");
        printWriter.println("  set-deterministic-uid-idle [--user <USER_ID> | all | current] <true|false>");
        printWriter.println("      If true, sets the timing of making UIDs idle consistent and");
        printWriter.println("      deterministic. If false, the timing will be variable depending on");
        printWriter.println("      other activity on the device. The default is false.");
        printWriter.println("  monitor [--gdb <port>] [-p <TARGET>] [-s] [-c] [-k]");
        printWriter.println("      Start monitoring for crashes or ANRs.");
        printWriter.println("      --gdb: start gdbserv on the given port at crash/ANR");
        printWriter.println("      -p: only show events related to a specific process / package");
        printWriter.println("      -s: simple mode, only show a summary line for each event");
        printWriter.println("      -c: assume the input is always [c]ontinue");
        printWriter.println("      -k: assume the input is always [k]ill");
        printWriter.println("         -c and -k are mutually exclusive.");
        printWriter.println("  watch-uids [--oom <uid>] [--mask <capabilities integer>]");
        printWriter.println("      Start watching for and reporting uid state changes.");
        printWriter.println("      --oom: specify a uid for which to report detailed change messages.");
        printWriter.println("      --mask: Specify PROCESS_CAPABILITY_XXX mask to report. ");
        printWriter.println("              By default, it only reports FOREGROUND_LOCATION (1)");
        printWriter.println("              FOREGROUND_CAMERA (2), FOREGROUND_MICROPHONE (4)");
        printWriter.println("              and NETWORK (8). New capabilities added on or after");
        printWriter.println("              Android UDC will not be reported by default.");
        printWriter.println("  hang [--allow-restart]");
        printWriter.println("      Hang the system.");
        printWriter.println("      --allow-restart: allow watchdog to perform normal system restart");
        printWriter.println("  restart");
        printWriter.println("      Restart the user-space system.");
        printWriter.println("  idle-maintenance");
        printWriter.println("      Perform idle maintenance now.");
        printWriter.println("  screen-compat [on|off] <PACKAGE>");
        printWriter.println("      Control screen compatibility mode of <PACKAGE>.");
        printWriter.println("  package-importance <PACKAGE>");
        printWriter.println("      Print current importance of <PACKAGE>.");
        printWriter.println("  to-uri [INTENT]");
        printWriter.println("      Print the given Intent specification as a URI.");
        printWriter.println("  to-intent-uri [INTENT]");
        printWriter.println("      Print the given Intent specification as an intent: URI.");
        printWriter.println("  to-app-uri [INTENT]");
        printWriter.println("      Print the given Intent specification as an android-app: URI.");
        printWriter.println("  switch-user <USER_ID>");
        printWriter.println("      Switch to put USER_ID in the foreground, starting");
        printWriter.println("      execution of that user if it is currently stopped.");
        printWriter.println("  get-current-user");
        printWriter.println("      Returns id of the current foreground user.");
        printWriter.println("  start-user [-w] [--display DISPLAY_ID] <USER_ID>");
        printWriter.println("      Start USER_ID in background if it is currently stopped;");
        printWriter.println("      use switch-user if you want to start the user in foreground.");
        printWriter.println("      -w: wait for start-user to complete and the user to be unlocked.");
        printWriter.println("      --display <DISPLAY_ID>: starts the user visible in that display, which allows the user to launch activities on it.");
        printWriter.println("        (not supported on all devices; typically only on automotive builds where the vehicle has passenger displays)");
        printWriter.println("  unlock-user <USER_ID>");
        printWriter.println("      Unlock the given user.  This will only work if the user doesn't");
        printWriter.println("      have an LSKF (PIN/pattern/password).");
        printWriter.println("  stop-user [-w] [-f] <USER_ID>");
        printWriter.println("      Stop execution of USER_ID, not allowing it to run any");
        printWriter.println("      code until a later explicit start or switch to it.");
        printWriter.println("      -w: wait for stop-user to complete.");
        printWriter.println("      -f: force stop even if there are related users that cannot be stopped.");
        printWriter.println("  is-user-stopped <USER_ID>");
        printWriter.println("      Returns whether <USER_ID> has been stopped or not.");
        printWriter.println("  get-started-user-state <USER_ID>");
        printWriter.println("      Gets the current state of the given started user.");
        printWriter.println("  track-associations");
        printWriter.println("      Enable association tracking.");
        printWriter.println("  untrack-associations");
        printWriter.println("      Disable and clear association tracking.");
        printWriter.println("  get-uid-state <UID>");
        printWriter.println("      Gets the process state of an app given its <UID>.");
        printWriter.println("  attach-agent <PROCESS> <FILE>");
        printWriter.println("    Attach an agent to the specified <PROCESS>, which may be either a process name or a PID.");
        printWriter.println("  get-config [--days N] [--device] [--proto] [--display <DISPLAY_ID>]");
        printWriter.println("      Retrieve the configuration and any recent configurations of the device.");
        printWriter.println("      --days: also return last N days of configurations that have been seen.");
        printWriter.println("      --device: also output global device configuration info.");
        printWriter.println("      --proto: return result as a proto; does not include --days info.");
        printWriter.println("      --display: Specify for which display to run the command; if not ");
        printWriter.println("          specified then run for the default display.");
        printWriter.println("  supports-multiwindow");
        printWriter.println("      Returns true if the device supports multiwindow.");
        printWriter.println("  supports-split-screen-multi-window");
        printWriter.println("      Returns true if the device supports split screen multiwindow.");
        printWriter.println("  suppress-resize-config-changes <true|false>");
        printWriter.println("      Suppresses configuration changes due to user resizing an activity/task.");
        printWriter.println("  set-inactive [--user <USER_ID>] <PACKAGE> true|false");
        printWriter.println("      Sets the inactive state of an app.");
        printWriter.println("  get-inactive [--user <USER_ID>] <PACKAGE>");
        printWriter.println("      Returns the inactive state of an app.");
        printWriter.println("  set-standby-bucket [--user <USER_ID>] <PACKAGE> active|working_set|frequent|rare|restricted");
        printWriter.println("      Puts an app in the standby bucket.");
        printWriter.println("  get-standby-bucket [--user <USER_ID>] <PACKAGE>");
        printWriter.println("      Returns the standby bucket of an app.");
        printWriter.println("  send-trim-memory [--user <USER_ID>] <PROCESS>");
        printWriter.println("          [HIDDEN|RUNNING_MODERATE|BACKGROUND|RUNNING_LOW|MODERATE|RUNNING_CRITICAL|COMPLETE]");
        printWriter.println("      Send a memory trim event to a <PROCESS>.  May also supply a raw trim int level.");
        printWriter.println("  display [COMMAND] [...]: sub-commands for operating on displays.");
        printWriter.println("       move-stack <STACK_ID> <DISPLAY_ID>");
        printWriter.println("           Move <STACK_ID> from its current display to <DISPLAY_ID>.");
        printWriter.println("  stack [COMMAND] [...]: sub-commands for operating on activity stacks.");
        printWriter.println("       move-task <TASK_ID> <STACK_ID> [true|false]");
        printWriter.println("           Move <TASK_ID> from its current stack to the top (true) or");
        printWriter.println("           bottom (false) of <STACK_ID>.");
        printWriter.println("       list");
        printWriter.println("           List all of the activity stacks and their sizes.");
        printWriter.println("       info <WINDOWING_MODE> <ACTIVITY_TYPE>");
        printWriter.println("           Display the information about activity stack in <WINDOWING_MODE> and <ACTIVITY_TYPE>.");
        printWriter.println("       remove <STACK_ID>");
        printWriter.println("           Remove stack <STACK_ID>.");
        printWriter.println("  task [COMMAND] [...]: sub-commands for operating on activity tasks.");
        printWriter.println("       lock <TASK_ID>");
        printWriter.println("           Bring <TASK_ID> to the front and don't allow other tasks to run.");
        printWriter.println("       lock stop");
        printWriter.println("           End the current task lock.");
        printWriter.println("       resizeable <TASK_ID> [0|1|2|3]");
        printWriter.println("           Change resizeable mode of <TASK_ID> to one of the following:");
        printWriter.println("           0: unresizeable");
        printWriter.println("           1: crop_windows");
        printWriter.println("           2: resizeable");
        printWriter.println("           3: resizeable_and_pipable");
        printWriter.println("       resize <TASK_ID> <LEFT,TOP,RIGHT,BOTTOM>");
        printWriter.println("           Makes sure <TASK_ID> is in a stack with the specified bounds.");
        printWriter.println("           Forces the task to be resizeable and creates a stack if no existing stack");
        printWriter.println("           has the specified bounds.");
        printWriter.println("  update-appinfo <USER_ID> <PACKAGE_NAME> [<PACKAGE_NAME>...]");
        printWriter.println("      Update the ApplicationInfo objects of the listed packages for <USER_ID>");
        printWriter.println("      without restarting any processes.");
        printWriter.println("  write");
        printWriter.println("      Write all pending state to storage.");
        printWriter.println("  compat [COMMAND] [...]: sub-commands for toggling app-compat changes.");
        printWriter.println("         enable|disable [--no-kill] <CHANGE_ID|CHANGE_NAME> <PACKAGE_NAME>");
        printWriter.println("            Toggles a change either by id or by name for <PACKAGE_NAME>.");
        printWriter.println("            It kills <PACKAGE_NAME> (to allow the toggle to take effect) unless --no-kill is provided.");
        printWriter.println("         reset <CHANGE_ID|CHANGE_NAME> <PACKAGE_NAME>");
        printWriter.println("            Toggles a change either by id or by name for <PACKAGE_NAME>.");
        printWriter.println("            It kills <PACKAGE_NAME> (to allow the toggle to take effect).");
        printWriter.println("         enable-all|disable-all <targetSdkVersion> <PACKAGE_NAME>");
        printWriter.println("            Toggles all changes that are gated by <targetSdkVersion>.");
        printWriter.println("         reset-all [--no-kill] <PACKAGE_NAME>");
        printWriter.println("            Removes all existing overrides for all changes for ");
        printWriter.println("            <PACKAGE_NAME> (back to default behaviour).");
        printWriter.println("            It kills <PACKAGE_NAME> (to allow the toggle to take effect) unless --no-kill is provided.");
        printWriter.println("  memory-factor [command] [...]: sub-commands for overriding memory pressure factor");
        printWriter.println("         set <NORMAL|MODERATE|LOW|CRITICAL>");
        printWriter.println("            Overrides memory pressure factor. May also supply a raw int level");
        printWriter.println("         show");
        printWriter.println("            Shows the existing memory pressure factor");
        printWriter.println("         reset");
        printWriter.println("            Removes existing override for memory pressure factor");
        printWriter.println("  service-restart-backoff <COMMAND> [...]: sub-commands to toggle service restart backoff policy.");
        printWriter.println("         enable|disable <PACKAGE_NAME>");
        printWriter.println("            Toggles the restart backoff policy on/off for <PACKAGE_NAME>.");
        printWriter.println("         show <PACKAGE_NAME>");
        printWriter.println("            Shows the restart backoff policy state for <PACKAGE_NAME>.");
        printWriter.println("  get-isolated-pids <UID>");
        printWriter.println("         Get the PIDs of isolated processes with packages in this <UID>");
        printWriter.println("  set-stop-user-on-switch [true|false]");
        printWriter.println("         Sets whether the current user (and its profiles) should be stopped when switching to a different user.");
        printWriter.println("         Without arguments, it resets to the value defined by platform.");
        printWriter.println("  set-bg-abusive-uids [uid=percentage][,uid=percentage...]");
        printWriter.println("         Force setting the battery usage of the given UID.");
        printWriter.println("  set-bg-restriction-level [--user <USER_ID>] <PACKAGE> unrestricted|exempted|adaptive_bucket|restricted_bucket|background_restricted|hibernation");
        printWriter.println("         Set an app's background restriction level which in turn map to a app standby bucket.");
        printWriter.println("  get-bg-restriction-level [--user <USER_ID>] <PACKAGE>");
        printWriter.println("         Get an app's background restriction level.");
        printWriter.println("  list-displays-for-starting-users");
        printWriter.println("         Lists the id of displays that can be used to start users on background.");
        printWriter.println("  set-foreground-service-delegate [--user <USER_ID>] <PACKAGE> start|stop");
        printWriter.println("         Start/stop an app's foreground service delegate.");
        printWriter.println("  set-ignore-delivery-group-policy <ACTION>");
        printWriter.println("         Start ignoring delivery group policy set for a broadcast action");
        printWriter.println("  clear-ignore-delivery-group-policy <ACTION>");
        printWriter.println("         Stop ignoring delivery group policy set for a broadcast action");
        printWriter.println("  capabilities [--protobuf]");
        printWriter.println("         Output am supported features (text format). Options are:");
        printWriter.println("         --protobuf: format output using protobuffer");
        android.content.Intent.printIntentArgsHelp(printWriter, "");
    }
}
