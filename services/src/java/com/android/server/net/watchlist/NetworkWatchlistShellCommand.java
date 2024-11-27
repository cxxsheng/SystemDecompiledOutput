package com.android.server.net.watchlist;

/* loaded from: classes2.dex */
class NetworkWatchlistShellCommand extends android.os.ShellCommand {
    final android.content.Context mContext;
    final com.android.server.net.watchlist.NetworkWatchlistService mService;

    NetworkWatchlistShellCommand(com.android.server.net.watchlist.NetworkWatchlistService networkWatchlistService, android.content.Context context) {
        this.mContext = context;
        this.mService = networkWatchlistService;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public int onCommand(java.lang.String str) {
        boolean z;
        if (str == null) {
            return handleDefaultCommands(str);
        }
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        try {
            switch (str.hashCode()) {
                case 1757613042:
                    if (str.equals("set-test-config")) {
                        z = false;
                        break;
                    }
                    z = -1;
                    break;
                case 1854202282:
                    if (str.equals("force-generate-report")) {
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
            }
        } catch (java.lang.Exception e) {
            outPrintWriter.println("Exception: " + e);
            return -1;
        }
        return handleDefaultCommands(str);
    }

    private int runSetTestConfig() throws android.os.RemoteException {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        try {
            java.lang.String nextArgRequired = getNextArgRequired();
            android.os.ParcelFileDescriptor openFileForSystem = openFileForSystem(nextArgRequired, com.android.server.wm.ActivityTaskManagerService.DUMP_RECENTS_SHORT_CMD);
            if (openFileForSystem == null) {
                outPrintWriter.println("Error: can't open input file " + nextArgRequired);
                return -1;
            }
            android.os.ParcelFileDescriptor.AutoCloseInputStream autoCloseInputStream = new android.os.ParcelFileDescriptor.AutoCloseInputStream(openFileForSystem);
            try {
                com.android.server.net.watchlist.WatchlistConfig.getInstance().setTestMode(autoCloseInputStream);
                autoCloseInputStream.close();
                outPrintWriter.println("Success!");
                return 0;
            } finally {
            }
        } catch (java.lang.Exception e) {
            outPrintWriter.println("Error: " + e.toString());
            return -1;
        }
    }

    private int runForceGenerateReport() throws android.os.RemoteException {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (com.android.server.net.watchlist.WatchlistConfig.getInstance().isConfigSecure()) {
                outPrintWriter.println("Error: Cannot force generate report under production config");
                return -1;
            }
            android.provider.Settings.Global.putLong(this.mContext.getContentResolver(), "network_watchlist_last_report_time", 0L);
            this.mService.forceReportWatchlistForTest(java.lang.System.currentTimeMillis());
            outPrintWriter.println("Success!");
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return 0;
        } catch (java.lang.Exception e) {
            outPrintWriter.println("Error: " + e);
            return -1;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void onHelp() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Network watchlist manager commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println("  set-test-config your_watchlist_config.xml");
        outPrintWriter.println("    Set network watchlist test config file.");
        outPrintWriter.println("  force-generate-report");
        outPrintWriter.println("    Force generate watchlist test report.");
    }
}
