package com.android.server.display;

/* loaded from: classes.dex */
class DisplayManagerShellCommand extends android.os.ShellCommand {
    private static final java.lang.String NOTIFICATION_TYPES = "on-hotplug-error, on-link-training-failure, on-cable-dp-incapable";
    private static final java.lang.String TAG = "DisplayManagerShellCommand";
    private final com.android.server.display.feature.DisplayManagerFlags mFlags;
    private final com.android.server.display.DisplayManagerService mService;

    DisplayManagerShellCommand(com.android.server.display.DisplayManagerService displayManagerService, com.android.server.display.feature.DisplayManagerFlags displayManagerFlags) {
        this.mService = displayManagerService;
        this.mFlags = displayManagerFlags;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public int onCommand(java.lang.String str) {
        char c;
        if (str == null) {
            return handleDefaultCommands(str);
        }
        getOutPrintWriter();
        switch (str.hashCode()) {
            case -1505467592:
                if (str.equals("reset-brightness-configuration")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -1459563384:
                if (str.equals("get-displays")) {
                    c = 19;
                    break;
                }
                c = 65535;
                break;
            case -1226903944:
                if (str.equals("enable-display")) {
                    c = 22;
                    break;
                }
                c = 65535;
                break;
            case -1021080420:
                if (str.equals("get-user-disabled-hdr-types")) {
                    c = 18;
                    break;
                }
                c = 65535;
                break;
            case -840680372:
                if (str.equals("undock")) {
                    c = 21;
                    break;
                }
                c = 65535;
                break;
            case -731435249:
                if (str.equals("dwb-logging-enable")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case -687141135:
                if (str.equals("set-user-preferred-display-mode")) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case -601773083:
                if (str.equals("get-user-preferred-display-mode")) {
                    c = '\r';
                    break;
                }
                c = 65535;
                break;
            case -464562757:
                if (str.equals("show-notification")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 3088947:
                if (str.equals("dock")) {
                    c = 20;
                    break;
                }
                c = 65535;
                break;
            case 10139357:
                if (str.equals("disable-display")) {
                    c = 23;
                    break;
                }
                c = 65535;
                break;
            case 276125397:
                if (str.equals("cancel-notifications")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 483509981:
                if (str.equals("ab-logging-enable")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 847215243:
                if (str.equals("dwb-set-cct")) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case 1089842382:
                if (str.equals("ab-logging-disable")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 1265268983:
                if (str.equals("get-active-display-mode-at-start")) {
                    c = 14;
                    break;
                }
                c = 65535;
                break;
            case 1428935945:
                if (str.equals("set-match-content-frame-rate-pref")) {
                    c = 15;
                    break;
                }
                c = 65535;
                break;
            case 1604823708:
                if (str.equals("set-brightness")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 1863255293:
                if (str.equals("get-match-content-frame-rate-pref")) {
                    c = 16;
                    break;
                }
                c = 65535;
                break;
            case 1873686952:
                if (str.equals("dmd-logging-disable")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case 1894268611:
                if (str.equals("dmd-logging-enable")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case 1928353192:
                if (str.equals("set-user-disabled-hdr-types")) {
                    c = 17;
                    break;
                }
                c = 65535;
                break;
            case 2076592732:
                if (str.equals("clear-user-preferred-display-mode")) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case 2081245916:
                if (str.equals("dwb-logging-disable")) {
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
        }
        return handleDefaultCommands(str);
    }

    public void onHelp() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Display manager commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println();
        outPrintWriter.println("  show-notification NOTIFICATION_TYPE");
        outPrintWriter.println("    Show notification for one of the following types: on-hotplug-error, on-link-training-failure, on-cable-dp-incapable");
        outPrintWriter.println("  cancel-notifications");
        outPrintWriter.println("    Cancel notifications.");
        outPrintWriter.println("  set-brightness BRIGHTNESS");
        outPrintWriter.println("    Sets the current brightness to BRIGHTNESS (a number between 0 and 1).");
        outPrintWriter.println("  reset-brightness-configuration");
        outPrintWriter.println("    Reset the brightness to its default configuration.");
        outPrintWriter.println("  ab-logging-enable");
        outPrintWriter.println("    Enable auto-brightness logging.");
        outPrintWriter.println("  ab-logging-disable");
        outPrintWriter.println("    Disable auto-brightness logging.");
        outPrintWriter.println("  dwb-logging-enable");
        outPrintWriter.println("    Enable display white-balance logging.");
        outPrintWriter.println("  dwb-logging-disable");
        outPrintWriter.println("    Disable display white-balance logging.");
        outPrintWriter.println("  dmd-logging-enable");
        outPrintWriter.println("    Enable display mode director logging.");
        outPrintWriter.println("  dmd-logging-disable");
        outPrintWriter.println("    Disable display mode director logging.");
        outPrintWriter.println("  dwb-set-cct CCT");
        outPrintWriter.println("    Sets the ambient color temperature override to CCT (use -1 to disable).");
        outPrintWriter.println("  set-user-preferred-display-mode WIDTH HEIGHT REFRESH-RATE DISPLAY_ID (optional)");
        outPrintWriter.println("    Sets the user preferred display mode which has fields WIDTH, HEIGHT and REFRESH-RATE. If DISPLAY_ID is passed, the mode change is applied to displaywith id = DISPLAY_ID, else mode change is applied globally.");
        outPrintWriter.println("  clear-user-preferred-display-mode DISPLAY_ID (optional)");
        outPrintWriter.println("    Clears the user preferred display mode. If DISPLAY_ID is passed, the mode is cleared for  display with id = DISPLAY_ID, else mode is cleared globally.");
        outPrintWriter.println("  get-user-preferred-display-mode DISPLAY_ID (optional)");
        outPrintWriter.println("    Returns the user preferred display mode or null if no mode is set by user.If DISPLAY_ID is passed, the mode for display with id = DISPLAY_ID is returned, else global display mode is returned.");
        outPrintWriter.println("  get-active-display-mode-at-start DISPLAY_ID");
        outPrintWriter.println("    Returns the display mode which was found at boot time of display with id = DISPLAY_ID");
        outPrintWriter.println("  set-match-content-frame-rate-pref PREFERENCE");
        outPrintWriter.println("    Sets the match content frame rate preference as PREFERENCE ");
        outPrintWriter.println("  get-match-content-frame-rate-pref");
        outPrintWriter.println("    Returns the match content frame rate preference");
        outPrintWriter.println("  set-user-disabled-hdr-types TYPES...");
        outPrintWriter.println("    Sets the user disabled HDR types as TYPES");
        outPrintWriter.println("  get-user-disabled-hdr-types");
        outPrintWriter.println("    Returns the user disabled HDR types");
        outPrintWriter.println("  get-displays [-c|--category CATEGORY] [-i|--ids-only] [-t|--type TYPE]");
        outPrintWriter.println("    [CATEGORY]");
        outPrintWriter.println("    Returns the current displays. Can specify string category among");
        outPrintWriter.println("    DisplayManager.DISPLAY_CATEGORY_*; must use the actual string value.");
        outPrintWriter.println("    Can choose to print only the ids of the displays. Can filter by");
        outPrintWriter.println("    display types. For example, '--type external'");
        outPrintWriter.println("  dock");
        outPrintWriter.println("    Sets brightness to docked + idle screen brightness mode");
        outPrintWriter.println("  undock");
        outPrintWriter.println("    Sets brightness to active (normal) screen brightness mode");
        if (this.mFlags.isConnectedDisplayManagementEnabled()) {
            outPrintWriter.println("  enable-display DISPLAY_ID");
            outPrintWriter.println("    Enable the DISPLAY_ID. Only possible if this is a connected display.");
            outPrintWriter.println("  disable-display DISPLAY_ID");
            outPrintWriter.println("    Disable the DISPLAY_ID. Only possible if this is a connected display.");
        }
        outPrintWriter.println();
        android.content.Intent.printIntentArgsHelp(outPrintWriter, "");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Multi-variable type inference failed */
    private int getDisplays() {
        char c;
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        final java.util.ArrayList arrayList = new java.util.ArrayList();
        java.lang.String str = null;
        boolean z = false;
        boolean z2 = false;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                switch (nextOption.hashCode()) {
                    case 0:
                        if (nextOption.equals("")) {
                            c = 6;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1494:
                        if (nextOption.equals("-c")) {
                            c = 4;
                            break;
                        }
                        c = 65535;
                        break;
                    case android.net.util.NetworkConstants.ETHER_MTU /* 1500 */:
                        if (nextOption.equals("-i")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1511:
                        if (nextOption.equals("-t")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 66265758:
                        if (nextOption.equals("--category")) {
                            c = 5;
                            break;
                        }
                        c = 65535;
                        break;
                    case 220627777:
                        if (nextOption.equals("--ids-only")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1333445850:
                        if (nextOption.equals("--type")) {
                            c = 3;
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
                    case 1:
                        z = true;
                        break;
                    case 2:
                    case 3:
                        int type = getType(getNextArgRequired(), outPrintWriter);
                        if (type == -1) {
                            return 1;
                        }
                        arrayList.add(java.lang.Integer.valueOf(type));
                        z2 = true;
                        break;
                    case 4:
                    case 5:
                        if (str != null) {
                            outPrintWriter.println("Error: the category has been specified more than one time. Please select only one category.");
                            return 1;
                        }
                        str = getNextArgRequired();
                        break;
                    case 6:
                        break;
                    default:
                        outPrintWriter.println("Error: unknown option '" + nextOption + "'");
                        return 1;
                }
            } else {
                java.lang.String nextArg = getNextArg();
                if (nextArg != null) {
                    if (str != null) {
                        outPrintWriter.println("Error: the category has been specified both with the -c option and the positional argument. Please select only one category.");
                        return 1;
                    }
                    str = nextArg;
                }
                android.view.Display[] displays = ((android.hardware.display.DisplayManager) this.mService.getContext().getSystemService(android.hardware.display.DisplayManager.class)).getDisplays(str);
                java.lang.Object[] objArr = displays;
                if (z2) {
                    objArr = (android.view.Display[]) java.util.Arrays.stream(displays).filter(new java.util.function.Predicate() { // from class: com.android.server.display.DisplayManagerShellCommand$$ExternalSyntheticLambda0
                        @Override // java.util.function.Predicate
                        public final boolean test(java.lang.Object obj) {
                            boolean lambda$getDisplays$0;
                            lambda$getDisplays$0 = com.android.server.display.DisplayManagerShellCommand.lambda$getDisplays$0(arrayList, (android.view.Display) obj);
                            return lambda$getDisplays$0;
                        }
                    }).toArray(new java.util.function.IntFunction() { // from class: com.android.server.display.DisplayManagerShellCommand$$ExternalSyntheticLambda1
                        @Override // java.util.function.IntFunction
                        public final java.lang.Object apply(int i) {
                            android.view.Display[] lambda$getDisplays$1;
                            lambda$getDisplays$1 = com.android.server.display.DisplayManagerShellCommand.lambda$getDisplays$1(i);
                            return lambda$getDisplays$1;
                        }
                    });
                }
                if (!z) {
                    outPrintWriter.println("Displays:");
                }
                for (int i = 0; i < objArr.length; i++) {
                    outPrintWriter.println(z ? java.lang.Integer.valueOf(objArr[i].getDisplayId()) : objArr[i]);
                }
                return 0;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getDisplays$0(java.util.List list, android.view.Display display) {
        return list.contains(java.lang.Integer.valueOf(display.getType()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.view.Display[] lambda$getDisplays$1(int i) {
        return new android.view.Display[i];
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int getType(java.lang.String str, java.io.PrintWriter printWriter) {
        boolean z;
        java.lang.String upperCase = str.toUpperCase(java.util.Locale.ENGLISH);
        switch (upperCase.hashCode()) {
            case -1038134325:
                if (upperCase.equals("EXTERNAL")) {
                    z = 2;
                    break;
                }
                z = -1;
                break;
            case -373305296:
                if (upperCase.equals("OVERLAY")) {
                    z = 4;
                    break;
                }
                z = -1;
                break;
            case 2664213:
                if (upperCase.equals("WIFI")) {
                    z = 3;
                    break;
                }
                z = -1;
                break;
            case 433141802:
                if (upperCase.equals("UNKNOWN")) {
                    z = false;
                    break;
                }
                z = -1;
                break;
            case 1184148203:
                if (upperCase.equals("VIRTUAL")) {
                    z = 5;
                    break;
                }
                z = -1;
                break;
            case 1353037501:
                if (upperCase.equals("INTERNAL")) {
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
                return 0;
            case true:
                return 1;
            case true:
                return 2;
            case true:
                return 3;
            case true:
                return 4;
            case true:
                return 5;
            default:
                printWriter.println("Error: argument for display type should be one of 'UNKNOWN', 'INTERNAL', 'EXTERNAL', 'WIFI', 'OVERLAY', 'VIRTUAL', but got '" + upperCase + "' instead.");
                return -1;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int showNotification() {
        char c;
        java.lang.String nextArg = getNextArg();
        if (nextArg == null) {
            getErrPrintWriter().println("Error: no notificationType specified, use one of: on-hotplug-error, on-link-training-failure, on-cable-dp-incapable");
            return 1;
        }
        switch (nextArg.hashCode()) {
            case -1348657756:
                if (nextArg.equals("on-cable-dp-incapable")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 1400911272:
                if (nextArg.equals("on-hotplug-error")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 1997686684:
                if (nextArg.equals("on-link-training-failure")) {
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
                this.mService.getDisplayNotificationManager().onHotplugConnectionError();
                return 0;
            case 1:
                this.mService.getDisplayNotificationManager().onDisplayPortLinkTrainingFailure();
                return 0;
            case 2:
                this.mService.getDisplayNotificationManager().onCableNotCapableDisplayPort();
                return 0;
            default:
                getErrPrintWriter().println("Error: unexpected notification type=" + nextArg + ", use one of: " + NOTIFICATION_TYPES);
                return 1;
        }
    }

    private int cancelNotifications() {
        this.mService.getDisplayNotificationManager().cancelNotifications();
        return 0;
    }

    private int setBrightness() {
        float f;
        java.lang.String nextArg = getNextArg();
        if (nextArg == null) {
            getErrPrintWriter().println("Error: no brightness specified");
            return 1;
        }
        try {
            f = java.lang.Float.parseFloat(nextArg);
        } catch (java.lang.NumberFormatException e) {
            f = -1.0f;
        }
        if (f < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE || f > 1.0f) {
            getErrPrintWriter().println("Error: brightness should be a number between 0 and 1");
            return 1;
        }
        ((android.hardware.display.DisplayManager) this.mService.getContext().getSystemService(android.hardware.display.DisplayManager.class)).setBrightness(0, f);
        return 0;
    }

    private int resetBrightnessConfiguration() {
        this.mService.resetBrightnessConfigurations();
        return 0;
    }

    private int setAutoBrightnessLoggingEnabled(boolean z) {
        this.mService.setAutoBrightnessLoggingEnabled(z);
        return 0;
    }

    private int setDisplayWhiteBalanceLoggingEnabled(boolean z) {
        this.mService.setDisplayWhiteBalanceLoggingEnabled(z);
        return 0;
    }

    private int setDisplayModeDirectorLoggingEnabled(boolean z) {
        this.mService.setDisplayModeDirectorLoggingEnabled(z);
        return 0;
    }

    private int setAmbientColorTemperatureOverride() {
        java.lang.String nextArg = getNextArg();
        if (nextArg == null) {
            getErrPrintWriter().println("Error: no cct specified");
            return 1;
        }
        try {
            this.mService.setAmbientColorTemperatureOverride(java.lang.Float.parseFloat(nextArg));
            return 0;
        } catch (java.lang.NumberFormatException e) {
            getErrPrintWriter().println("Error: cct should be a number");
            return 1;
        }
    }

    private int setUserPreferredDisplayMode() {
        int parseInt;
        java.lang.String nextArg = getNextArg();
        if (nextArg == null) {
            getErrPrintWriter().println("Error: no width specified");
            return 1;
        }
        java.lang.String nextArg2 = getNextArg();
        if (nextArg2 == null) {
            getErrPrintWriter().println("Error: no height specified");
            return 1;
        }
        java.lang.String nextArg3 = getNextArg();
        if (nextArg3 == null) {
            getErrPrintWriter().println("Error: no refresh-rate specified");
            return 1;
        }
        try {
            int parseInt2 = java.lang.Integer.parseInt(nextArg);
            int parseInt3 = java.lang.Integer.parseInt(nextArg2);
            float parseFloat = java.lang.Float.parseFloat(nextArg3);
            if ((parseInt2 < 0 || parseInt3 < 0) && parseFloat <= com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                getErrPrintWriter().println("Error: invalid value of resolution (width, height) and refresh rate");
                return 1;
            }
            java.lang.String nextArg4 = getNextArg();
            if (nextArg4 == null) {
                parseInt = -1;
            } else {
                try {
                    parseInt = java.lang.Integer.parseInt(nextArg4);
                } catch (java.lang.NumberFormatException e) {
                    getErrPrintWriter().println("Error: invalid format of display ID");
                    return 1;
                }
            }
            this.mService.setUserPreferredDisplayModeInternal(parseInt, new android.view.Display.Mode(parseInt2, parseInt3, parseFloat));
            return 0;
        } catch (java.lang.NumberFormatException e2) {
            getErrPrintWriter().println("Error: invalid format of width, height or refresh rate");
            return 1;
        }
    }

    private int clearUserPreferredDisplayMode() {
        int parseInt;
        java.lang.String nextArg = getNextArg();
        if (nextArg == null) {
            parseInt = -1;
        } else {
            try {
                parseInt = java.lang.Integer.parseInt(nextArg);
            } catch (java.lang.NumberFormatException e) {
                getErrPrintWriter().println("Error: invalid format of display ID");
                return 1;
            }
        }
        this.mService.setUserPreferredDisplayModeInternal(parseInt, null);
        return 0;
    }

    private int getUserPreferredDisplayMode() {
        int parseInt;
        java.lang.String nextArg = getNextArg();
        if (nextArg == null) {
            parseInt = -1;
        } else {
            try {
                parseInt = java.lang.Integer.parseInt(nextArg);
            } catch (java.lang.NumberFormatException e) {
                getErrPrintWriter().println("Error: invalid format of display ID");
                return 1;
            }
        }
        android.view.Display.Mode userPreferredDisplayModeInternal = this.mService.getUserPreferredDisplayModeInternal(parseInt);
        if (userPreferredDisplayModeInternal == null) {
            getOutPrintWriter().println("User preferred display mode: null");
            return 0;
        }
        getOutPrintWriter().println("User preferred display mode: " + userPreferredDisplayModeInternal.getPhysicalWidth() + " " + userPreferredDisplayModeInternal.getPhysicalHeight() + " " + userPreferredDisplayModeInternal.getRefreshRate());
        return 0;
    }

    private int getActiveDisplayModeAtStart() {
        java.lang.String nextArg = getNextArg();
        if (nextArg == null) {
            getErrPrintWriter().println("Error: no displayId specified");
            return 1;
        }
        try {
            android.view.Display.Mode activeDisplayModeAtStart = this.mService.getActiveDisplayModeAtStart(java.lang.Integer.parseInt(nextArg));
            if (activeDisplayModeAtStart == null) {
                getOutPrintWriter().println("Boot display mode: null");
                return 0;
            }
            getOutPrintWriter().println("Boot display mode: " + activeDisplayModeAtStart.getPhysicalWidth() + " " + activeDisplayModeAtStart.getPhysicalHeight() + " " + activeDisplayModeAtStart.getRefreshRate());
            return 0;
        } catch (java.lang.NumberFormatException e) {
            getErrPrintWriter().println("Error: invalid displayId");
            return 1;
        }
    }

    private int setMatchContentFrameRateUserPreference() {
        java.lang.String nextArg = getNextArg();
        if (nextArg == null) {
            getErrPrintWriter().println("Error: no matchContentFrameRatePref specified");
            return 1;
        }
        try {
            int parseInt = java.lang.Integer.parseInt(nextArg);
            if (parseInt < 0) {
                getErrPrintWriter().println("Error: invalid value of matchContentFrameRatePreference");
                return 1;
            }
            ((android.hardware.display.DisplayManager) this.mService.getContext().getSystemService(android.hardware.display.DisplayManager.class)).setRefreshRateSwitchingType(toRefreshRateSwitchingType(parseInt));
            return 0;
        } catch (java.lang.NumberFormatException e) {
            getErrPrintWriter().println("Error: invalid format of matchContentFrameRatePreference");
            return 1;
        }
    }

    private int getMatchContentFrameRateUserPreference() {
        android.hardware.display.DisplayManager displayManager = (android.hardware.display.DisplayManager) this.mService.getContext().getSystemService(android.hardware.display.DisplayManager.class);
        getOutPrintWriter().println("Match content frame rate type: " + displayManager.getMatchContentFrameRateUserPreference());
        return 0;
    }

    private int setUserDisabledHdrTypes() {
        java.lang.String[] peekRemainingArgs = peekRemainingArgs();
        if (peekRemainingArgs == null) {
            getErrPrintWriter().println("Error: no userDisabledHdrTypes specified");
            return 1;
        }
        int[] iArr = new int[peekRemainingArgs.length];
        try {
            int length = peekRemainingArgs.length;
            int i = 0;
            int i2 = 0;
            while (i < length) {
                int i3 = i2 + 1;
                iArr[i2] = java.lang.Integer.parseInt(peekRemainingArgs[i]);
                i++;
                i2 = i3;
            }
            ((android.hardware.display.DisplayManager) this.mService.getContext().getSystemService(android.hardware.display.DisplayManager.class)).setUserDisabledHdrTypes(iArr);
            return 0;
        } catch (java.lang.NumberFormatException e) {
            getErrPrintWriter().println("Error: invalid format of userDisabledHdrTypes");
            return 1;
        }
    }

    private int getUserDisabledHdrTypes() {
        int[] userDisabledHdrTypes = ((android.hardware.display.DisplayManager) this.mService.getContext().getSystemService(android.hardware.display.DisplayManager.class)).getUserDisabledHdrTypes();
        getOutPrintWriter().println("User disabled HDR types: " + java.util.Arrays.toString(userDisabledHdrTypes));
        return 0;
    }

    private int toRefreshRateSwitchingType(int i) {
        switch (i) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            default:
                android.util.Slog.e(TAG, i + " is not a valid value of matchContentFrameRate type.");
                return -1;
        }
    }

    private int setDockedAndIdle() {
        this.mService.setDockedAndIdleEnabled(true, 0);
        return 0;
    }

    private int unsetDockedAndIdle() {
        this.mService.setDockedAndIdleEnabled(false, 0);
        return 0;
    }

    private int setDisplayEnabled(boolean z) {
        if (!this.mFlags.isConnectedDisplayManagementEnabled()) {
            getErrPrintWriter().println("Error: external display management is not available on this device.");
            return 1;
        }
        java.lang.String nextArg = getNextArg();
        if (nextArg == null) {
            getErrPrintWriter().println("Error: no displayId specified");
            return 1;
        }
        try {
            this.mService.enableConnectedDisplay(java.lang.Integer.parseInt(nextArg), z);
            return 0;
        } catch (java.lang.NumberFormatException e) {
            getErrPrintWriter().println("Error: invalid displayId: '" + nextArg + "'");
            return 1;
        }
    }
}
