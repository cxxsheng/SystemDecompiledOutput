package com.android.server.slice;

/* loaded from: classes2.dex */
public class SliceShellCommand extends android.os.ShellCommand {
    private final com.android.server.slice.SliceManagerService mService;

    public SliceShellCommand(com.android.server.slice.SliceManagerService sliceManagerService) {
        this.mService = sliceManagerService;
    }

    public int onCommand(java.lang.String str) {
        char c;
        if (str == null) {
            return handleDefaultCommands(str);
        }
        switch (str.hashCode()) {
            case -185318259:
                if (str.equals("get-permissions")) {
                    c = 0;
                    break;
                }
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return runGetPermissions(getNextArgRequired());
            default:
                return 0;
        }
    }

    public void onHelp() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Status bar commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println("");
        outPrintWriter.println("  get-permissions <authority>");
        outPrintWriter.println("    List the pkgs that have permission to an authority.");
        outPrintWriter.println("");
    }

    private int runGetPermissions(java.lang.String str) {
        if (android.os.Binder.getCallingUid() != 2000 && android.os.Binder.getCallingUid() != 0) {
            getOutPrintWriter().println("Only shell can get permissions");
            return -1;
        }
        android.content.Context context = this.mService.getContext();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.net.Uri build = new android.net.Uri.Builder().scheme(com.android.server.wm.ActivityTaskManagerInternal.ASSIST_KEY_CONTENT).authority(str).build();
            if (!"vnd.android.slice".equals(context.getContentResolver().getType(build))) {
                getOutPrintWriter().println(str + " is not a slice provider");
                return -1;
            }
            android.os.Bundle call = context.getContentResolver().call(build, "get_permissions", (java.lang.String) null, (android.os.Bundle) null);
            if (call == null) {
                getOutPrintWriter().println("An error occurred getting permissions");
                return -1;
            }
            java.lang.String[] stringArray = call.getStringArray("result");
            java.io.PrintWriter outPrintWriter = getOutPrintWriter();
            android.util.ArraySet arraySet = new android.util.ArraySet();
            if (stringArray != null && stringArray.length != 0) {
                for (android.content.pm.PackageInfo packageInfo : context.getPackageManager().getPackagesHoldingPermissions(stringArray, 0)) {
                    outPrintWriter.println(packageInfo.packageName);
                    arraySet.add(packageInfo.packageName);
                }
            }
            for (java.lang.String str2 : this.mService.getAllPackagesGranted(str)) {
                if (!arraySet.contains(str2)) {
                    outPrintWriter.println(str2);
                    arraySet.add(str2);
                }
            }
            return 0;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
