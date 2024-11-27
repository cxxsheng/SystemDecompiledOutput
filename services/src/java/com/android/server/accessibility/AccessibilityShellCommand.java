package com.android.server.accessibility;

/* loaded from: classes.dex */
final class AccessibilityShellCommand extends android.os.ShellCommand {

    @android.annotation.NonNull
    final android.content.Context mContext;

    @android.annotation.NonNull
    final com.android.server.accessibility.AccessibilityManagerService mService;

    @android.annotation.NonNull
    final com.android.server.accessibility.SystemActionPerformer mSystemActionPerformer;

    @android.annotation.NonNull
    final com.android.server.wm.WindowManagerInternal mWindowManagerService = (com.android.server.wm.WindowManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.WindowManagerInternal.class);

    AccessibilityShellCommand(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.accessibility.AccessibilityManagerService accessibilityManagerService, @android.annotation.NonNull com.android.server.accessibility.SystemActionPerformer systemActionPerformer) {
        this.mContext = context;
        this.mService = accessibilityManagerService;
        this.mSystemActionPerformer = systemActionPerformer;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public int onCommand(java.lang.String str) {
        char c;
        if (str == null) {
            return handleDefaultCommands(str);
        }
        switch (str.hashCode()) {
            case -1659822550:
                if (str.equals("check-hidraw")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -859068373:
                if (str.equals("get-bind-instant-service-allowed")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 789489311:
                if (str.equals("set-bind-instant-service-allowed")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1340897306:
                if (str.equals("start-trace")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 1748820581:
                if (str.equals("call-system-action")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 1857979322:
                if (str.equals("stop-trace")) {
                    c = 4;
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
                return runGetBindInstantServiceAllowed();
            case 1:
                return runSetBindInstantServiceAllowed();
            case 2:
                return runCallSystemAction();
            case 3:
            case 4:
                return this.mService.getTraceManager().onShellCommand(str, this);
            case 5:
                return checkHidraw();
            default:
                return -1;
        }
    }

    private int runGetBindInstantServiceAllowed() {
        java.lang.Integer parseUserId = parseUserId();
        if (parseUserId == null) {
            return -1;
        }
        getOutPrintWriter().println(java.lang.Boolean.toString(this.mService.getBindInstantServiceAllowed(parseUserId.intValue())));
        return 0;
    }

    private int runSetBindInstantServiceAllowed() {
        java.lang.Integer parseUserId = parseUserId();
        if (parseUserId == null) {
            return -1;
        }
        java.lang.String nextArgRequired = getNextArgRequired();
        if (nextArgRequired == null) {
            getErrPrintWriter().println("Error: no true/false specified");
            return -1;
        }
        this.mService.setBindInstantServiceAllowed(parseUserId.intValue(), java.lang.Boolean.parseBoolean(nextArgRequired));
        return 0;
    }

    private int runCallSystemAction() {
        java.lang.String nextArg;
        int callingUid = android.os.Binder.getCallingUid();
        if ((callingUid != 0 && callingUid != 1000 && callingUid != 2000) || (nextArg = getNextArg()) == null) {
            return -1;
        }
        this.mSystemActionPerformer.performSystemAction(java.lang.Integer.parseInt(nextArg));
        return 0;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int checkHidraw() {
        char c;
        this.mContext.enforceCallingPermission("android.permission.MANAGE_ACCESSIBILITY", "Missing MANAGE_ACCESSIBILITY permission");
        java.lang.String nextArgRequired = getNextArgRequired();
        java.io.File file = new java.io.File(getNextArgRequired());
        switch (nextArgRequired.hashCode()) {
            case -748366993:
                if (nextArgRequired.equals("descriptor")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 3496342:
                if (nextArgRequired.equals("read")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 113399775:
                if (nextArgRequired.equals("write")) {
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
                return checkHidrawRead(file);
            case 1:
                return checkHidrawWrite(file);
            case 2:
                return checkHidrawDescriptor(file);
            default:
                getErrPrintWriter().print("Unknown subcommand " + nextArgRequired);
                return -1;
        }
    }

    private int checkHidrawRead(java.io.File file) {
        if (!file.canRead()) {
            getErrPrintWriter().println("Unable to read from " + file);
            return -1;
        }
        getOutPrintWriter().print(file.getAbsolutePath());
        return 0;
    }

    private int checkHidrawWrite(java.io.File file) {
        if (!file.canWrite()) {
            getErrPrintWriter().println("Unable to write to " + file);
            return -1;
        }
        getOutPrintWriter().print(file.getAbsolutePath());
        return 0;
    }

    private int checkHidrawDescriptor(java.io.File file) {
        byte[] deviceReportDescriptor = com.android.server.accessibility.BrailleDisplayConnection.createScannerForShell().getDeviceReportDescriptor(file.toPath());
        if (deviceReportDescriptor == null) {
            getErrPrintWriter().println("Unable to read descriptor for " + file);
            return -1;
        }
        try {
            getRawOutputStream().write(deviceReportDescriptor);
            return 0;
        } catch (java.io.IOException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    private java.lang.Integer parseUserId() {
        java.lang.String nextOption = getNextOption();
        if (nextOption != null) {
            if (nextOption.equals("--user")) {
                return java.lang.Integer.valueOf(android.os.UserHandle.parseUserArg(getNextArgRequired()));
            }
            getErrPrintWriter().println("Unknown option: " + nextOption);
            return null;
        }
        return java.lang.Integer.valueOf(android.app.ActivityManager.getCurrentUser());
    }

    public void onHelp() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Accessibility service (accessibility) commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println("  set-bind-instant-service-allowed [--user <USER_ID>] true|false ");
        outPrintWriter.println("    Set whether binding to services provided by instant apps is allowed.");
        outPrintWriter.println("  get-bind-instant-service-allowed [--user <USER_ID>]");
        outPrintWriter.println("    Get whether binding to services provided by instant apps is allowed.");
        outPrintWriter.println("  call-system-action <ACTION_ID>");
        outPrintWriter.println("    Calls the system action with the given action id.");
        outPrintWriter.println("  check-hidraw [read|write|descriptor] <HIDRAW_NODE_PATH>");
        outPrintWriter.println("    Checks if the system can perform various actions on the HIDRAW node.");
        this.mService.getTraceManager().onHelp(outPrintWriter);
    }
}
