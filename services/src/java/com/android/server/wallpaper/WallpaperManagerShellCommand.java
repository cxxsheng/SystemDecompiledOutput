package com.android.server.wallpaper;

/* loaded from: classes.dex */
public class WallpaperManagerShellCommand extends android.os.ShellCommand {
    private static final java.lang.String TAG = "WallpaperManagerShellCommand";
    private final com.android.server.wallpaper.WallpaperManagerService mService;

    public WallpaperManagerShellCommand(com.android.server.wallpaper.WallpaperManagerService wallpaperManagerService) {
        this.mService = wallpaperManagerService;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0034, code lost:
    
        if (r4.equals("dim-with-uid") != false) goto L25;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int onCommand(java.lang.String str) {
        char c = 1;
        if (str == null) {
            onHelp();
            return 1;
        }
        switch (str.hashCode()) {
            case -1462105208:
                if (str.equals("set-dim-amount")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -296046994:
                break;
            case 1499:
                if (str.equals("-h")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 3198785:
                if (str.equals("help")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 309630996:
                if (str.equals("get-dim-amount")) {
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
                return setWallpaperDimAmount();
            case 1:
                return setDimmingWithUid();
            case 2:
                return getWallpaperDimAmount();
            case 3:
            case 4:
                onHelp();
                return 0;
            default:
                return handleDefaultCommands(str);
        }
    }

    public void onHelp() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Wallpaper manager commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println();
        outPrintWriter.println("  set-dim-amount DIMMING");
        outPrintWriter.println("    Sets the current dimming value to DIMMING (a number between 0 and 1).");
        outPrintWriter.println();
        outPrintWriter.println("  dim-with-uid UID DIMMING");
        outPrintWriter.println("    Sets the wallpaper dim amount to DIMMING as if an app with uid, UID, called it.");
        outPrintWriter.println();
        outPrintWriter.println("  get-dim-amount");
        outPrintWriter.println("    Get the current wallpaper dim amount.");
    }

    private int setWallpaperDimAmount() {
        float parseFloat = java.lang.Float.parseFloat(getNextArgRequired());
        try {
            this.mService.setWallpaperDimAmount(parseFloat);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Can't set wallpaper dim amount");
        }
        getOutPrintWriter().println("Dimming the wallpaper to: " + parseFloat);
        return 0;
    }

    private int getWallpaperDimAmount() {
        float wallpaperDimAmount = this.mService.getWallpaperDimAmount();
        getOutPrintWriter().println("The current wallpaper dim amount is: " + wallpaperDimAmount);
        return 0;
    }

    private int setDimmingWithUid() {
        int parseInt = java.lang.Integer.parseInt(getNextArgRequired());
        float parseFloat = java.lang.Float.parseFloat(getNextArgRequired());
        this.mService.setWallpaperDimAmountForUid(parseInt, parseFloat);
        getOutPrintWriter().println("Dimming the wallpaper for UID: " + parseInt + " to: " + parseFloat);
        return 0;
    }
}
