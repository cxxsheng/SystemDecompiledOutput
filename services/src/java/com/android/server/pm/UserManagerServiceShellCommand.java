package com.android.server.pm;

/* loaded from: classes2.dex */
public class UserManagerServiceShellCommand extends android.os.ShellCommand {
    private static final java.lang.String LOG_TAG = "UserManagerServiceShellCommand";

    @android.annotation.NonNull
    private final android.content.Context mContext;

    @android.annotation.NonNull
    private final com.android.internal.widget.LockPatternUtils mLockPatternUtils;

    @android.annotation.NonNull
    private final com.android.server.pm.UserManagerService mService;

    @android.annotation.NonNull
    private final com.android.server.pm.UserSystemPackageInstaller mSystemPackageInstaller;

    UserManagerServiceShellCommand(com.android.server.pm.UserManagerService userManagerService, com.android.server.pm.UserSystemPackageInstaller userSystemPackageInstaller, com.android.internal.widget.LockPatternUtils lockPatternUtils, android.content.Context context) {
        this.mService = userManagerService;
        this.mSystemPackageInstaller = userSystemPackageInstaller;
        this.mLockPatternUtils = lockPatternUtils;
        this.mContext = context;
    }

    public void onHelp() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("User manager (user) commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("    Prints this help text.");
        outPrintWriter.println();
        outPrintWriter.println("  list [-v | --verbose] [--all]");
        outPrintWriter.println("    Prints all users on the system.");
        outPrintWriter.println();
        outPrintWriter.println("  report-system-user-package-whitelist-problems [-v | --verbose] [--critical-only] [--mode MODE]");
        outPrintWriter.println("    Reports all issues on user-type package allowlist XML files. Options:");
        outPrintWriter.println("    -v | --verbose: shows extra info, like number of issues");
        outPrintWriter.println("    --critical-only: show only critical issues, excluding warnings");
        outPrintWriter.println("    --mode MODE: shows what errors would be if device used mode MODE");
        outPrintWriter.println("      (where MODE is the allowlist mode integer as defined by config_userTypePackageWhitelistMode)");
        outPrintWriter.println();
        outPrintWriter.println("  set-system-user-mode-emulation [--reboot | --no-restart] <headless | full | default>");
        outPrintWriter.println("    Changes whether the system user is headless, full, or default (as defined by OEM).");
        outPrintWriter.println("    WARNING: this command is meant just for development and debugging purposes.");
        outPrintWriter.println("             It should NEVER be used on automated tests.");
        outPrintWriter.println("    NOTE: by default it restarts the Android runtime, unless called with");
        outPrintWriter.println("          --reboot (which does a full reboot) or");
        outPrintWriter.println("          --no-restart (which requires a manual restart)");
        outPrintWriter.println();
        outPrintWriter.println("  is-headless-system-user-mode [-v | --verbose]");
        outPrintWriter.println("    Checks whether the device uses headless system user mode.");
        outPrintWriter.println("  is-visible-background-users-on-default-display-supported [-v | --verbose]");
        outPrintWriter.println("    Checks whether the device allows users to be start visible on background in the default display.");
        outPrintWriter.println("    It returns the effective mode, even when using emulation");
        outPrintWriter.println("    (to get the real mode as well, use -v or --verbose)");
        outPrintWriter.println();
        outPrintWriter.println("  is-visible-background-users-supported [-v | --verbose]");
        outPrintWriter.println("    Checks whether the device allows users to be start visible on background.");
        outPrintWriter.println("    It returns the effective mode, even when using emulation");
        outPrintWriter.println("    (to get the real mode as well, use -v or --verbose)");
        outPrintWriter.println();
        outPrintWriter.println("  is-user-visible [--display DISPLAY_ID] <USER_ID>");
        outPrintWriter.println("    Checks if the given user is visible in the given display.");
        outPrintWriter.println("    If the display option is not set, it uses the user's context to check");
        outPrintWriter.println("    (so it emulates what apps would get from UserManager.isUserVisible())");
        outPrintWriter.println();
        outPrintWriter.println("  get-main-user ");
        outPrintWriter.println("    Displays main user id or message if there is no main user");
        outPrintWriter.println();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public int onCommand(java.lang.String str) {
        char c;
        if (str == null) {
            return handleDefaultCommands((java.lang.String) null);
        }
        try {
            switch (str.hashCode()) {
                case -1698126264:
                    if (str.equals("is-visible-background-users-supported")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case -66900680:
                    if (str.equals("is-headless-system-user-mode")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 3322014:
                    if (str.equals("list")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 340621931:
                    if (str.equals("can-switch-to-headless-system-user")) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case 681635871:
                    if (str.equals("is-main-user-permanent-admin")) {
                        c = '\t';
                        break;
                    }
                    c = 65535;
                    break;
                case 980857487:
                    if (str.equals("is-visible-background-users-on-default-display-supported")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case 1085270974:
                    if (str.equals("report-system-user-package-whitelist-problems")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 1453420968:
                    if (str.equals("get-main-user")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 1605325659:
                    if (str.equals("set-system-user-mode-emulation")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 1802440755:
                    if (str.equals("is-user-visible")) {
                        c = 6;
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
                    return runList();
                case 1:
                    return runReportPackageAllowlistProblems();
                case 2:
                    return runSetSystemUserModeEmulation();
                case 3:
                    return runIsHeadlessSystemUserMode();
                case 4:
                    return runIsVisibleBackgroundUserSupported();
                case 5:
                    return runIsVisibleBackgroundUserOnDefaultDisplaySupported();
                case 6:
                    return runIsUserVisible();
                case 7:
                    return runGetMainUserId();
                case '\b':
                    return canSwitchToHeadlessSystemUser();
                case '\t':
                    return isMainUserPermanentAdmin();
                default:
                    return handleDefaultCommands(str);
            }
        } catch (android.os.RemoteException e) {
            getOutPrintWriter().println("Remote exception: " + e);
            return -1;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int runList() throws android.os.RemoteException {
        int i;
        java.lang.String str;
        java.lang.String str2;
        java.lang.String str3;
        java.lang.String str4;
        java.lang.String str5;
        char c;
        com.android.server.pm.UserManagerServiceShellCommand userManagerServiceShellCommand = this;
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        int i2 = 0;
        boolean z = false;
        boolean z2 = false;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                switch (nextOption.hashCode()) {
                    case 1513:
                        if (nextOption.equals("-v")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case 42995713:
                        if (nextOption.equals("--all")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1737088994:
                        if (nextOption.equals("--verbose")) {
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
                    case 1:
                        z2 = true;
                        break;
                    case 2:
                        z = true;
                        break;
                    default:
                        outPrintWriter.println("Invalid option: " + nextOption);
                        return -1;
                }
            } else {
                android.app.IActivityManager service = android.app.ActivityManager.getService();
                java.util.List<android.content.pm.UserInfo> users = userManagerServiceShellCommand.mService.getUsers(!z, false, !z);
                if (users == null) {
                    outPrintWriter.println("Error: couldn't get users");
                    return 1;
                }
                int size = users.size();
                int i3 = com.android.server.am.ProcessList.INVALID_ADJ;
                if (z2) {
                    outPrintWriter.printf("%d users:\n\n", java.lang.Integer.valueOf(size));
                    i = service.getCurrentUser().id;
                } else {
                    outPrintWriter.println("Users:");
                    i = -10000;
                }
                int i4 = 0;
                while (i4 < size) {
                    android.content.pm.UserInfo userInfo = users.get(i4);
                    boolean isUserRunning = service.isUserRunning(userInfo.id, i2);
                    if (z2) {
                        android.app.admin.DevicePolicyManagerInternal devicePolicyManagerInternal = (android.app.admin.DevicePolicyManagerInternal) com.android.server.LocalServices.getService(android.app.admin.DevicePolicyManagerInternal.class);
                        if (devicePolicyManagerInternal == null) {
                            str = "";
                            str2 = str;
                        } else {
                            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                            try {
                                if (devicePolicyManagerInternal.getDeviceOwnerUserId() != userInfo.id) {
                                    str4 = "";
                                } else {
                                    str4 = " (device-owner)";
                                }
                                if (devicePolicyManagerInternal.getProfileOwnerAsUser(userInfo.id) == null) {
                                    str5 = "";
                                } else {
                                    str5 = " (profile-owner)";
                                }
                                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                                str = str4;
                                str2 = str5;
                            } catch (java.lang.Throwable th) {
                                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                                throw th;
                            }
                        }
                        boolean z3 = userInfo.id == i;
                        boolean z4 = (userInfo.profileGroupId == userInfo.id || userInfo.profileGroupId == i3) ? false : true;
                        boolean isUserVisible = userManagerServiceShellCommand.mService.isUserVisible(userInfo.id);
                        java.lang.Integer valueOf = java.lang.Integer.valueOf(i4);
                        java.lang.Integer valueOf2 = java.lang.Integer.valueOf(userInfo.id);
                        java.lang.String str6 = userInfo.name;
                        java.lang.String replace = userInfo.userType.replace("android.os.usertype.", "");
                        java.lang.String flagsToString = android.content.pm.UserInfo.flagsToString(userInfo.flags);
                        if (z4) {
                            str3 = " (parentId=" + userInfo.profileGroupId + ")";
                        } else {
                            str3 = "";
                        }
                        outPrintWriter.printf("%d: id=%d, name=%s, type=%s, flags=%s%s%s%s%s%s%s%s%s%s\n", valueOf, valueOf2, str6, replace, flagsToString, str3, isUserRunning ? " (running)" : "", userInfo.partial ? " (partial)" : "", userInfo.preCreated ? " (pre-created)" : "", userInfo.convertedFromPreCreated ? " (converted)" : "", str, str2, z3 ? " (current)" : "", isUserVisible ? " (visible)" : "");
                    } else {
                        outPrintWriter.printf("\t%s%s\n", userInfo, isUserRunning ? " running" : "");
                    }
                    i4++;
                    userManagerServiceShellCommand = this;
                    i2 = 0;
                    i3 = com.android.server.am.ProcessList.INVALID_ADJ;
                }
                return 0;
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int runReportPackageAllowlistProblems() {
        char c;
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        int i = -1000;
        boolean z = false;
        boolean z2 = false;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                switch (nextOption.hashCode()) {
                    case -1362766982:
                        if (nextOption.equals("--critical-only")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1513:
                        if (nextOption.equals("-v")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1333227331:
                        if (nextOption.equals("--mode")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1737088994:
                        if (nextOption.equals("--verbose")) {
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
                    case 1:
                        z = true;
                        break;
                    case 2:
                        z2 = true;
                        break;
                    case 3:
                        i = java.lang.Integer.parseInt(getNextArgRequired());
                        break;
                    default:
                        outPrintWriter.println("Invalid option: " + nextOption);
                        return -1;
                }
            } else {
                android.util.Slog.d(LOG_TAG, "runReportPackageAllowlistProblems(): verbose=" + z + ", criticalOnly=" + z2 + ", mode=" + com.android.server.pm.UserSystemPackageInstaller.modeToString(i));
                android.util.IndentingPrintWriter indentingPrintWriter = new android.util.IndentingPrintWriter(outPrintWriter, "  ");
                try {
                    this.mSystemPackageInstaller.dumpPackageWhitelistProblems(indentingPrintWriter, i, z, z2);
                    indentingPrintWriter.close();
                    return 0;
                } catch (java.lang.Throwable th) {
                    try {
                        indentingPrintWriter.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int runSetSystemUserModeEmulation() {
        char c;
        boolean z;
        if (!confirmBuildIsDebuggable() || !confirmIsCalledByRoot()) {
            return -1;
        }
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        if (this.mLockPatternUtils.isSecure(0)) {
            outPrintWriter.println("Cannot change system user mode when it has a credential");
            return -1;
        }
        boolean z2 = true;
        boolean z3 = true;
        boolean z4 = false;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                switch (nextOption.hashCode()) {
                    case 1269283747:
                        if (nextOption.equals("--no-restart")) {
                            z = true;
                            break;
                        }
                        z = -1;
                        break;
                    case 1465075013:
                        if (nextOption.equals("--reboot")) {
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
                        z4 = true;
                        break;
                    case true:
                        z3 = false;
                        break;
                    default:
                        outPrintWriter.println("Invalid option: " + nextOption);
                        return -1;
                }
            } else {
                if (z4 && !z3) {
                    getErrPrintWriter().println("You can use --reboot or --no-restart, but not both");
                    return -1;
                }
                java.lang.String nextArgRequired = getNextArgRequired();
                boolean isHeadlessSystemUserMode = android.os.UserManager.isHeadlessSystemUserMode();
                switch (nextArgRequired.hashCode()) {
                    case -1115062407:
                        if (nextArgRequired.equals("headless")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 3154575:
                        if (nextArgRequired.equals("full")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1544803905:
                        if (nextArgRequired.equals("default")) {
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
                        z2 = isHeadlessSystemUserMode;
                        break;
                    case 1:
                        z2 = !isHeadlessSystemUserMode;
                        break;
                    case 2:
                        break;
                    default:
                        getErrPrintWriter().printf("Invalid arg: %s\n", nextArgRequired);
                        return -1;
                }
                if (!z2) {
                    outPrintWriter.printf("No change needed, system user is already %s\n", isHeadlessSystemUserMode ? "headless" : "full");
                    return 0;
                }
                com.android.server.utils.Slogf.d(LOG_TAG, "Updating system property %s to %s", "persist.debug.user_mode_emulation", nextArgRequired);
                android.os.SystemProperties.set("persist.debug.user_mode_emulation", nextArgRequired);
                if (z4) {
                    android.util.Slog.i(LOG_TAG, "Rebooting to finalize the changes");
                    outPrintWriter.println("Rebooting to finalize changes");
                    com.android.server.UiThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.pm.UserManagerServiceShellCommand$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.pm.UserManagerServiceShellCommand.lambda$runSetSystemUserModeEmulation$0();
                        }
                    });
                } else if (z3) {
                    android.util.Slog.i(LOG_TAG, "Shutting PackageManager down");
                    ((android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class)).shutdown();
                    android.app.IActivityManager service = android.app.ActivityManager.getService();
                    if (service != null) {
                        try {
                            android.util.Slog.i(LOG_TAG, "Shutting ActivityManager down");
                            service.shutdown(10000);
                        } catch (android.os.RemoteException e) {
                            android.util.Slog.e(LOG_TAG, "Failed to shut down ActivityManager" + e);
                        }
                    }
                    int myPid = android.os.Process.myPid();
                    com.android.server.utils.Slogf.i(LOG_TAG, "Restarting Android runtime(PID=%d) to finalize changes", java.lang.Integer.valueOf(myPid));
                    outPrintWriter.println("Restarting Android runtime to finalize changes");
                    outPrintWriter.println("The restart may trigger a 'Broken pipe' message; this is to be expected.");
                    outPrintWriter.flush();
                    android.os.Process.killProcess(myPid);
                } else {
                    outPrintWriter.println("System user mode changed - please reboot (or restart Android runtime) to continue");
                    outPrintWriter.println("NOTICE: after restart, some apps might be uninstalled (and their data will be lost)");
                }
                return 0;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$runSetSystemUserModeEmulation$0() {
        com.android.server.power.ShutdownThread.reboot(android.app.ActivityThread.currentActivityThread().getSystemUiContext(), "To switch headless / full system user mode", false);
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x001b, code lost:
    
        if (r2.equals("--display") != false) goto L12;
     */
    @android.annotation.RequiresPermission(anyOf = {"android.permission.INTERACT_ACROSS_USERS", "android.permission.INTERACT_ACROSS_USERS_FULL"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int runIsUserVisible() {
        boolean isUserVisible;
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        java.lang.Integer num = null;
        while (true) {
            java.lang.String nextOption = getNextOption();
            boolean z = false;
            if (nextOption != null) {
                switch (nextOption.hashCode()) {
                    case -1237221598:
                        break;
                    default:
                        z = -1;
                        break;
                }
                switch (z) {
                    case false:
                        num = java.lang.Integer.valueOf(java.lang.Integer.parseInt(getNextArgRequired()));
                    default:
                        outPrintWriter.println("Invalid option: " + nextOption);
                        return -1;
                }
            } else {
                int parseUserArg = android.os.UserHandle.parseUserArg(getNextArgRequired());
                switch (parseUserArg) {
                    case com.android.server.am.ProcessList.INVALID_ADJ /* -10000 */:
                    case -3:
                    case -1:
                        outPrintWriter.printf("invalid value (%d) for --user option\n", java.lang.Integer.valueOf(parseUserArg));
                        return -1;
                    case -2:
                        parseUserArg = android.app.ActivityManager.getCurrentUser();
                        break;
                }
                if (num != null) {
                    isUserVisible = this.mService.isUserVisibleOnDisplay(parseUserArg, num.intValue());
                } else {
                    isUserVisible = getUserManagerForUser(parseUserArg).isUserVisible();
                }
                outPrintWriter.println(isUserVisible);
                return 0;
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int runIsHeadlessSystemUserMode() {
        boolean z;
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        boolean z2 = false;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                switch (nextOption.hashCode()) {
                    case 1513:
                        if (nextOption.equals("-v")) {
                            z = false;
                            break;
                        }
                        z = -1;
                        break;
                    case 1737088994:
                        if (nextOption.equals("--verbose")) {
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
                    case true:
                        z2 = true;
                    default:
                        outPrintWriter.println("Invalid option: " + nextOption);
                        return -1;
                }
            } else {
                boolean isHeadlessSystemUserMode = this.mService.isHeadlessSystemUserMode();
                if (!z2) {
                    outPrintWriter.println(isHeadlessSystemUserMode);
                } else {
                    outPrintWriter.printf("effective=%b real=%b\n", java.lang.Boolean.valueOf(isHeadlessSystemUserMode), java.lang.Boolean.valueOf(com.android.internal.os.RoSystemProperties.MULTIUSER_HEADLESS_SYSTEM_USER));
                }
                return 0;
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int runIsVisibleBackgroundUserSupported() {
        boolean z;
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        boolean z2 = false;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                switch (nextOption.hashCode()) {
                    case 1513:
                        if (nextOption.equals("-v")) {
                            z = false;
                            break;
                        }
                        z = -1;
                        break;
                    case 1737088994:
                        if (nextOption.equals("--verbose")) {
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
                    case true:
                        z2 = true;
                    default:
                        outPrintWriter.println("Invalid option: " + nextOption);
                        return -1;
                }
            } else {
                boolean isVisibleBackgroundUsersEnabled = android.os.UserManager.isVisibleBackgroundUsersEnabled();
                if (!z2) {
                    outPrintWriter.println(isVisibleBackgroundUsersEnabled);
                } else {
                    outPrintWriter.printf("effective=%b real=%b\n", java.lang.Boolean.valueOf(isVisibleBackgroundUsersEnabled), java.lang.Boolean.valueOf(android.content.res.Resources.getSystem().getBoolean(android.R.bool.config_maskSecondaryBuiltInDisplayCutout)));
                }
                return 0;
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int runIsVisibleBackgroundUserOnDefaultDisplaySupported() {
        boolean z;
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        boolean z2 = false;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                switch (nextOption.hashCode()) {
                    case 1513:
                        if (nextOption.equals("-v")) {
                            z = false;
                            break;
                        }
                        z = -1;
                        break;
                    case 1737088994:
                        if (nextOption.equals("--verbose")) {
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
                    case true:
                        z2 = true;
                    default:
                        outPrintWriter.println("Invalid option: " + nextOption);
                        return -1;
                }
            } else {
                boolean isVisibleBackgroundUsersOnDefaultDisplayEnabled = android.os.UserManager.isVisibleBackgroundUsersOnDefaultDisplayEnabled();
                if (!z2) {
                    outPrintWriter.println(isVisibleBackgroundUsersOnDefaultDisplayEnabled);
                } else {
                    outPrintWriter.printf("effective=%b real=%b\n", java.lang.Boolean.valueOf(isVisibleBackgroundUsersOnDefaultDisplayEnabled), java.lang.Boolean.valueOf(android.content.res.Resources.getSystem().getBoolean(android.R.bool.config_matchSecondaryInternalDisplaysOrientationToReverseDefaultDisplay)));
                }
                return 0;
            }
        }
    }

    private int runGetMainUserId() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        int mainUserId = this.mService.getMainUserId();
        if (mainUserId == -10000) {
            outPrintWriter.println(com.android.server.input.KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG);
            return 1;
        }
        outPrintWriter.println(mainUserId);
        return 0;
    }

    private int canSwitchToHeadlessSystemUser() {
        getOutPrintWriter().println(this.mService.canSwitchToHeadlessSystemUser());
        return 0;
    }

    private int isMainUserPermanentAdmin() {
        getOutPrintWriter().println(this.mService.isMainUserPermanentAdmin());
        return 0;
    }

    private android.os.UserManager getUserManagerForUser(int i) {
        return (android.os.UserManager) this.mContext.createContextAsUser(android.os.UserHandle.of(i), 0).getSystemService(android.os.UserManager.class);
    }

    private boolean confirmBuildIsDebuggable() {
        if (android.os.Build.isDebuggable()) {
            return true;
        }
        getErrPrintWriter().println("Command not available on user builds");
        return false;
    }

    private boolean confirmIsCalledByRoot() {
        if (android.os.Binder.getCallingUid() == 0) {
            return true;
        }
        getErrPrintWriter().println("Command only available on root user");
        return false;
    }
}
