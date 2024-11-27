package com.android.server.display.color;

/* loaded from: classes.dex */
class ColorDisplayShellCommand extends android.os.ShellCommand {
    private static final int ERROR = -1;
    private static final int SUCCESS = 0;
    private static final java.lang.String USAGE = "usage: cmd color_display SUBCOMMAND [ARGS]\n    help\n      Shows this message.\n    set-saturation LEVEL\n      Sets the device saturation to the given LEVEL, 0-100 inclusive.\n    set-layer-saturation LEVEL CALLER_PACKAGE TARGET_PACKAGE\n      Sets the saturation LEVEL for all layers of the TARGET_PACKAGE, attributed\n      to the CALLER_PACKAGE. The lowest LEVEL from any CALLER_PACKAGE is applied.\n";
    private final com.android.server.display.color.ColorDisplayService mService;

    ColorDisplayShellCommand(com.android.server.display.color.ColorDisplayService colorDisplayService) {
        this.mService = colorDisplayService;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public int onCommand(java.lang.String str) {
        char c;
        if (str == null) {
            return handleDefaultCommands(str);
        }
        switch (str.hashCode()) {
            case 245833689:
                if (str.equals("set-layer-saturation")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 726170141:
                if (str.equals("set-saturation")) {
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

    private int setSaturation() {
        int level = getLevel();
        if (level == -1) {
            return -1;
        }
        this.mService.setSaturationLevelInternal(level);
        return 0;
    }

    private int setLayerSaturation() {
        int level = getLevel();
        if (level == -1) {
            return -1;
        }
        java.lang.String packageName = getPackageName();
        if (packageName == null) {
            getErrPrintWriter().println("Error: CALLER_PACKAGE must be an installed package name");
            return -1;
        }
        java.lang.String packageName2 = getPackageName();
        if (packageName2 == null) {
            getErrPrintWriter().println("Error: TARGET_PACKAGE must be an installed package name");
            return -1;
        }
        this.mService.setAppSaturationLevelInternal(packageName, packageName2, level);
        return 0;
    }

    private java.lang.String getPackageName() {
        java.lang.String nextArg = getNextArg();
        if (((android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class)).getPackage(nextArg) != null) {
            return nextArg;
        }
        return null;
    }

    private int getLevel() {
        java.lang.String nextArg = getNextArg();
        if (nextArg == null) {
            getErrPrintWriter().println("Error: Required argument LEVEL is unspecified");
            return -1;
        }
        try {
            int parseInt = java.lang.Integer.parseInt(nextArg);
            if (parseInt < 0 || parseInt > 100) {
                getErrPrintWriter().println("Error: LEVEL argument must be an integer between 0 and 100");
                return -1;
            }
            return parseInt;
        } catch (java.lang.NumberFormatException e) {
            getErrPrintWriter().println("Error: LEVEL argument is not an integer");
            return -1;
        }
    }

    public void onHelp() {
        getOutPrintWriter().print(USAGE);
    }
}
