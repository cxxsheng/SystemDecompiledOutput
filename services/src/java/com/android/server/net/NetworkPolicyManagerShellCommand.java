package com.android.server.net;

/* loaded from: classes2.dex */
class NetworkPolicyManagerShellCommand extends android.os.ShellCommand {
    private final com.android.server.net.NetworkPolicyManagerService mInterface;
    private final android.net.wifi.WifiManager mWifiManager;

    NetworkPolicyManagerShellCommand(android.content.Context context, com.android.server.net.NetworkPolicyManagerService networkPolicyManagerService) {
        this.mInterface = networkPolicyManagerService;
        this.mWifiManager = (android.net.wifi.WifiManager) context.getSystemService("wifi");
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
                case -1544226370:
                    if (str.equals("start-watching")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case -934610812:
                    if (str.equals("remove")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 96417:
                    if (str.equals("add")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 102230:
                    if (str.equals("get")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 113762:
                    if (str.equals("set")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 3322014:
                    if (str.equals("list")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 1093388830:
                    if (str.equals("stop-watching")) {
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
            }
        } catch (android.os.RemoteException e) {
            outPrintWriter.println("Remote exception: " + e);
            return -1;
        }
        return handleDefaultCommands(str);
    }

    public void onHelp() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Network policy manager (netpolicy) commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println("");
        outPrintWriter.println("  add restrict-background-whitelist UID");
        outPrintWriter.println("    Adds a UID to the whitelist for restrict background usage.");
        outPrintWriter.println("  add restrict-background-blacklist UID");
        outPrintWriter.println("    Adds a UID to the blacklist for restrict background usage.");
        outPrintWriter.println("  add app-idle-whitelist UID");
        outPrintWriter.println("    Adds a UID to the temporary app idle whitelist.");
        outPrintWriter.println("  get restrict-background");
        outPrintWriter.println("    Gets the global restrict background usage status.");
        outPrintWriter.println("  list wifi-networks [true|false]");
        outPrintWriter.println("    Lists all saved wifi networks and whether they are metered or not.");
        outPrintWriter.println("    If a boolean argument is passed, filters just the metered (or unmetered)");
        outPrintWriter.println("    networks.");
        outPrintWriter.println("  list restrict-background-whitelist");
        outPrintWriter.println("    Lists UIDs that are whitelisted for restrict background usage.");
        outPrintWriter.println("  list restrict-background-blacklist");
        outPrintWriter.println("    Lists UIDs that are blacklisted for restrict background usage.");
        outPrintWriter.println("  remove restrict-background-whitelist UID");
        outPrintWriter.println("    Removes a UID from the whitelist for restrict background usage.");
        outPrintWriter.println("  remove restrict-background-blacklist UID");
        outPrintWriter.println("    Removes a UID from the blacklist for restrict background usage.");
        outPrintWriter.println("  remove app-idle-whitelist UID");
        outPrintWriter.println("    Removes a UID from the temporary app idle whitelist.");
        outPrintWriter.println("  set metered-network ID [undefined|true|false]");
        outPrintWriter.println("    Toggles whether the given wi-fi network is metered.");
        outPrintWriter.println("  set restrict-background BOOLEAN");
        outPrintWriter.println("    Sets the global restrict background usage status.");
        outPrintWriter.println("  set sub-plan-owner subId [packageName]");
        outPrintWriter.println("    Sets the data plan owner package for subId.");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int runGet() throws android.os.RemoteException {
        boolean z;
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        java.lang.String nextArg = getNextArg();
        if (nextArg != null) {
            switch (nextArg.hashCode()) {
                case -747095841:
                    if (nextArg.equals("restrict-background")) {
                        z = false;
                        break;
                    }
                    z = -1;
                    break;
                case 909005781:
                    if (nextArg.equals("restricted-mode")) {
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
                    break;
                case true:
                    break;
                default:
                    outPrintWriter.println("Error: unknown get type '" + nextArg + "'");
                    break;
            }
            return -1;
        }
        outPrintWriter.println("Error: didn't specify type of data to get");
        return -1;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int runSet() throws android.os.RemoteException {
        char c;
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        java.lang.String nextArg = getNextArg();
        if (nextArg != null) {
            switch (nextArg.hashCode()) {
                case -983249079:
                    if (nextArg.equals("metered-network")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case -747095841:
                    if (nextArg.equals("restrict-background")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 1846940860:
                    if (nextArg.equals("sub-plan-owner")) {
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
                    break;
                case 1:
                    break;
                case 2:
                    break;
                default:
                    outPrintWriter.println("Error: unknown set type '" + nextArg + "'");
                    break;
            }
            return -1;
        }
        outPrintWriter.println("Error: didn't specify type of data to set");
        return -1;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int runList() throws android.os.RemoteException {
        char c;
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        java.lang.String nextArg = getNextArg();
        if (nextArg != null) {
            switch (nextArg.hashCode()) {
                case -1683867974:
                    if (nextArg.equals("app-idle-whitelist")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case -668534353:
                    if (nextArg.equals("restrict-background-blacklist")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case -363534403:
                    if (nextArg.equals("wifi-networks")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 639570137:
                    if (nextArg.equals("restrict-background-whitelist")) {
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
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                default:
                    outPrintWriter.println("Error: unknown list type '" + nextArg + "'");
                    break;
            }
            return -1;
        }
        outPrintWriter.println("Error: didn't specify type of data to list");
        return -1;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int runAdd() throws android.os.RemoteException {
        char c;
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        java.lang.String nextArg = getNextArg();
        if (nextArg != null) {
            switch (nextArg.hashCode()) {
                case -1683867974:
                    if (nextArg.equals("app-idle-whitelist")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case -668534353:
                    if (nextArg.equals("restrict-background-blacklist")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 639570137:
                    if (nextArg.equals("restrict-background-whitelist")) {
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
                    break;
                case 1:
                    break;
                case 2:
                    break;
                default:
                    outPrintWriter.println("Error: unknown add type '" + nextArg + "'");
                    break;
            }
            return -1;
        }
        outPrintWriter.println("Error: didn't specify type of data to add");
        return -1;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int runRemove() throws android.os.RemoteException {
        char c;
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        java.lang.String nextArg = getNextArg();
        if (nextArg != null) {
            switch (nextArg.hashCode()) {
                case -1683867974:
                    if (nextArg.equals("app-idle-whitelist")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case -668534353:
                    if (nextArg.equals("restrict-background-blacklist")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 639570137:
                    if (nextArg.equals("restrict-background-whitelist")) {
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
                    break;
                case 1:
                    break;
                case 2:
                    break;
                default:
                    outPrintWriter.println("Error: unknown remove type '" + nextArg + "'");
                    break;
            }
            return -1;
        }
        outPrintWriter.println("Error: didn't specify type of data to remove");
        return -1;
    }

    private int runStartWatching() {
        int parseInt = java.lang.Integer.parseInt(getNextArgRequired());
        if (parseInt < 0) {
            java.io.PrintWriter outPrintWriter = getOutPrintWriter();
            outPrintWriter.print("Invalid UID: ");
            outPrintWriter.println(parseInt);
            return -1;
        }
        this.mInterface.setDebugUid(parseInt);
        return 0;
    }

    private int runStopWatching() {
        this.mInterface.setDebugUid(-1);
        return 0;
    }

    private int listUidPolicies(java.lang.String str, int i) throws android.os.RemoteException {
        return listUidList(str, this.mInterface.getUidsWithPolicy(i));
    }

    private int listUidList(java.lang.String str, int[] iArr) {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.print(str);
        outPrintWriter.print(": ");
        if (iArr.length == 0) {
            outPrintWriter.println("none");
        } else {
            for (int i : iArr) {
                outPrintWriter.print(i);
                outPrintWriter.print(' ');
            }
        }
        outPrintWriter.println();
        return 0;
    }

    private int listRestrictBackgroundAllowlist() throws android.os.RemoteException {
        return listUidPolicies("Restrict background whitelisted UIDs", 4);
    }

    private int listRestrictBackgroundDenylist() throws android.os.RemoteException {
        return listUidPolicies("Restrict background blacklisted UIDs", 1);
    }

    private int listAppIdleAllowlist() throws android.os.RemoteException {
        getOutPrintWriter();
        return listUidList("App Idle whitelisted UIDs", this.mInterface.getAppIdleWhitelist());
    }

    private int getRestrictedModeState() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.print("Restricted mode status: ");
        outPrintWriter.println(this.mInterface.isRestrictedModeEnabled() ? com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_ENABLED : com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_DISABLED);
        return 0;
    }

    private int getRestrictBackground() throws android.os.RemoteException {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.print("Restrict background status: ");
        outPrintWriter.println(this.mInterface.getRestrictBackground() ? com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_ENABLED : com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_DISABLED);
        return 0;
    }

    private int setRestrictBackground() throws android.os.RemoteException {
        int nextBooleanArg = getNextBooleanArg();
        if (nextBooleanArg < 0) {
            return nextBooleanArg;
        }
        this.mInterface.setRestrictBackground(nextBooleanArg > 0);
        return 0;
    }

    private int setSubPlanOwner() throws android.os.RemoteException {
        this.mInterface.setSubscriptionPlansOwner(java.lang.Integer.parseInt(getNextArgRequired()), getNextArg());
        return 0;
    }

    private int setUidPolicy(int i) throws android.os.RemoteException {
        int uidFromNextArg = getUidFromNextArg();
        if (uidFromNextArg < 0) {
            return uidFromNextArg;
        }
        this.mInterface.setUidPolicy(uidFromNextArg, i);
        return 0;
    }

    private int resetUidPolicy(java.lang.String str, int i) throws android.os.RemoteException {
        int uidFromNextArg = getUidFromNextArg();
        if (uidFromNextArg < 0) {
            return uidFromNextArg;
        }
        if (this.mInterface.getUidPolicy(uidFromNextArg) != i) {
            java.io.PrintWriter outPrintWriter = getOutPrintWriter();
            outPrintWriter.print("Error: UID ");
            outPrintWriter.print(uidFromNextArg);
            outPrintWriter.print(' ');
            outPrintWriter.println(str);
            return -1;
        }
        this.mInterface.setUidPolicy(uidFromNextArg, 0);
        return 0;
    }

    private int addRestrictBackgroundAllowlist() throws android.os.RemoteException {
        return setUidPolicy(4);
    }

    private int removeRestrictBackgroundAllowlist() throws android.os.RemoteException {
        return resetUidPolicy("not whitelisted", 4);
    }

    private int addRestrictBackgroundDenylist() throws android.os.RemoteException {
        return setUidPolicy(1);
    }

    private int removeRestrictBackgroundDenylist() throws android.os.RemoteException {
        return resetUidPolicy("not blacklisted", 1);
    }

    private int setAppIdleAllowlist(boolean z) {
        int uidFromNextArg = getUidFromNextArg();
        if (uidFromNextArg < 0) {
            return uidFromNextArg;
        }
        this.mInterface.setAppIdleWhitelist(uidFromNextArg, z);
        return 0;
    }

    private int addAppIdleAllowlist() throws android.os.RemoteException {
        return setAppIdleAllowlist(true);
    }

    private int removeAppIdleAllowlist() throws android.os.RemoteException {
        return setAppIdleAllowlist(false);
    }

    private int listWifiNetworks() {
        int i;
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        java.lang.String nextArg = getNextArg();
        if (nextArg == null) {
            i = 0;
        } else if (java.lang.Boolean.parseBoolean(nextArg)) {
            i = 1;
        } else {
            i = 2;
        }
        for (android.net.wifi.WifiConfiguration wifiConfiguration : this.mWifiManager.getConfiguredNetworks()) {
            if (nextArg == null || wifiConfiguration.meteredOverride == i) {
                outPrintWriter.print(android.net.NetworkPolicyManager.resolveNetworkId(wifiConfiguration));
                outPrintWriter.print(';');
                outPrintWriter.println(overrideToString(wifiConfiguration.meteredOverride));
            }
        }
        return 0;
    }

    private int setMeteredWifiNetwork() throws android.os.RemoteException {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        java.lang.String nextArg = getNextArg();
        if (nextArg == null) {
            outPrintWriter.println("Error: didn't specify networkId");
            return -1;
        }
        java.lang.String nextArg2 = getNextArg();
        if (nextArg2 == null) {
            outPrintWriter.println("Error: didn't specify meteredOverride");
            return -1;
        }
        this.mInterface.setWifiMeteredOverride(android.net.NetworkPolicyManager.resolveNetworkId(nextArg), stringToOverride(nextArg2));
        return -1;
    }

    private static java.lang.String overrideToString(int i) {
        switch (i) {
            case 1:
                return "true";
            case 2:
                return "false";
            default:
                return "none";
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static int stringToOverride(java.lang.String str) {
        char c;
        switch (str.hashCode()) {
            case 3569038:
                if (str.equals("true")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 97196323:
                if (str.equals("false")) {
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
                return 1;
            case 1:
                return 2;
            default:
                return 0;
        }
    }

    private int getNextBooleanArg() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        java.lang.String nextArg = getNextArg();
        if (nextArg != null) {
            return java.lang.Boolean.valueOf(nextArg).booleanValue() ? 1 : 0;
        }
        outPrintWriter.println("Error: didn't specify BOOLEAN");
        return -1;
    }

    private int getUidFromNextArg() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        java.lang.String nextArg = getNextArg();
        if (nextArg == null) {
            outPrintWriter.println("Error: didn't specify UID");
            return -1;
        }
        try {
            return java.lang.Integer.parseInt(nextArg);
        } catch (java.lang.NumberFormatException e) {
            outPrintWriter.println("Error: UID (" + nextArg + ") should be a number");
            return -2;
        }
    }
}
