package com.android.server.app;

/* loaded from: classes.dex */
public class GameManagerShellCommand extends android.os.ShellCommand {
    private static final java.lang.String BATTERY_MODE_NUM = "3";
    private static final java.lang.String BATTERY_MODE_STR = "battery";
    private static final java.lang.String CUSTOM_MODE_NUM = "4";
    private static final java.lang.String CUSTOM_MODE_STR = "custom";
    private static final java.lang.String PERFORMANCE_MODE_NUM = "2";
    private static final java.lang.String PERFORMANCE_MODE_STR = "performance";
    private static final java.lang.String STANDARD_MODE_NUM = "1";
    private static final java.lang.String STANDARD_MODE_STR = "standard";
    private static final java.lang.String UNSUPPORTED_MODE_NUM = java.lang.String.valueOf(0);
    private static final java.lang.String UNSUPPORTED_MODE_STR = "unsupported";

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public int onCommand(java.lang.String str) {
        char c;
        if (str == null) {
            return handleDefaultCommands(str);
        }
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        try {
            switch (str.hashCode()) {
                case -1207633086:
                    if (str.equals("list-configs")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case -729460415:
                    if (str.equals("list-modes")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 113762:
                    if (str.equals("set")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 3357091:
                    if (str.equals(com.android.server.app.GameManagerService.GamePackageConfiguration.GameModeConfiguration.MODE_KEY)) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 108404047:
                    if (str.equals("reset")) {
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
            }
        } catch (java.lang.Exception e) {
            outPrintWriter.println("Error: " + e);
            return -1;
        }
        return handleDefaultCommands(str);
    }

    private int runListGameModes(java.io.PrintWriter printWriter) throws android.os.ServiceManager.ServiceNotFoundException, android.os.RemoteException {
        java.lang.String nextArgRequired = getNextArgRequired();
        int currentUser = android.app.ActivityManager.getCurrentUser();
        com.android.server.app.GameManagerService gameManagerService = (com.android.server.app.GameManagerService) android.os.ServiceManager.getService("game");
        java.lang.String gameModeIntToString = gameModeIntToString(gameManagerService.getGameMode(nextArgRequired, currentUser));
        java.util.StringJoiner stringJoiner = new java.util.StringJoiner(",");
        for (int i : gameManagerService.getAvailableGameModes(nextArgRequired, currentUser)) {
            stringJoiner.add(gameModeIntToString(i));
        }
        printWriter.println(nextArgRequired + " current mode: " + gameModeIntToString + ", available game modes: [" + stringJoiner + "]");
        return 0;
    }

    private int runListGameModeConfigs(java.io.PrintWriter printWriter) throws android.os.ServiceManager.ServiceNotFoundException, android.os.RemoteException {
        java.lang.String nextArgRequired = getNextArgRequired();
        java.lang.String interventionList = ((com.android.server.app.GameManagerService) android.os.ServiceManager.getService("game")).getInterventionList(nextArgRequired, android.app.ActivityManager.getCurrentUser());
        if (interventionList == null) {
            printWriter.println("No interventions found for " + nextArgRequired);
            return 0;
        }
        printWriter.println(nextArgRequired + " interventions: " + interventionList);
        return 0;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int runSetGameMode(java.io.PrintWriter printWriter) throws android.os.ServiceManager.ServiceNotFoundException, android.os.RemoteException {
        java.lang.String str;
        char c;
        java.lang.String nextOption = getNextOption();
        if (nextOption != null && nextOption.equals("--user")) {
            str = getNextArgRequired();
        } else {
            str = null;
        }
        java.lang.String nextArgRequired = getNextArgRequired();
        java.lang.String nextArgRequired2 = getNextArgRequired();
        android.app.IGameManagerService asInterface = android.app.IGameManagerService.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow("game"));
        int parseInt = str != null ? java.lang.Integer.parseInt(str) : android.app.ActivityManager.getCurrentUser();
        boolean z = false;
        boolean z2 = false;
        for (int i : asInterface.getAvailableGameModes(nextArgRequired2, parseInt)) {
            if (i == 2) {
                z2 = true;
            } else if (i == 3) {
                z = true;
            }
        }
        java.lang.String lowerCase = nextArgRequired.toLowerCase();
        switch (lowerCase.hashCode()) {
            case -1480388560:
                if (lowerCase.equals(PERFORMANCE_MODE_STR)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -1349088399:
                if (lowerCase.equals(CUSTOM_MODE_STR)) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case -331239923:
                if (lowerCase.equals(BATTERY_MODE_STR)) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 49:
                if (lowerCase.equals(STANDARD_MODE_NUM)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 50:
                if (lowerCase.equals(PERFORMANCE_MODE_NUM)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 51:
                if (lowerCase.equals(BATTERY_MODE_NUM)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 52:
                if (lowerCase.equals(CUSTOM_MODE_NUM)) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 1312628413:
                if (lowerCase.equals(STANDARD_MODE_STR)) {
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
                asInterface.setGameMode(nextArgRequired2, 1, parseInt);
                printWriter.println("Set game mode to `STANDARD` for user `" + parseInt + "` in game `" + nextArgRequired2 + "`");
                return 0;
            case 2:
            case 3:
                if (z2) {
                    asInterface.setGameMode(nextArgRequired2, 2, parseInt);
                    printWriter.println("Set game mode to `PERFORMANCE` for user `" + parseInt + "` in game `" + nextArgRequired2 + "`");
                    return 0;
                }
                printWriter.println("Game mode: " + nextArgRequired + " not supported by " + nextArgRequired2);
                return -1;
            case 4:
            case 5:
                if (z) {
                    asInterface.setGameMode(nextArgRequired2, 3, parseInt);
                    printWriter.println("Set game mode to `BATTERY` for user `" + parseInt + "` in game `" + nextArgRequired2 + "`");
                    return 0;
                }
                printWriter.println("Game mode: " + nextArgRequired + " not supported by " + nextArgRequired2);
                return -1;
            case 6:
            case 7:
                asInterface.setGameMode(nextArgRequired2, 4, parseInt);
                printWriter.println("Set game mode to `CUSTOM` for user `" + parseInt + "` in game `" + nextArgRequired2 + "`");
                return 0;
            default:
                printWriter.println("Invalid game mode: " + nextArgRequired);
                return -1;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x0032, code lost:
    
        if (r1.equals("--mode") != false) goto L21;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int runSetGameModeConfig(java.io.PrintWriter printWriter) throws android.os.ServiceManager.ServiceNotFoundException, android.os.RemoteException {
        java.lang.String str = null;
        java.lang.String str2 = null;
        java.lang.String str3 = null;
        int i = 4;
        while (true) {
            java.lang.String nextOption = getNextOption();
            char c = 0;
            if (nextOption != null) {
                switch (nextOption.hashCode()) {
                    case 43000649:
                        if (nextOption.equals("--fps")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1333227331:
                        break;
                    case 1333469547:
                        if (nextOption.equals("--user")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1807206472:
                        if (nextOption.equals("--downscale")) {
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
                        i = java.lang.Integer.parseInt(getNextArgRequired());
                        break;
                    case 1:
                        if (str == null) {
                            str = getNextArgRequired();
                            break;
                        } else {
                            printWriter.println("Duplicate option '" + nextOption + "'");
                            return -1;
                        }
                    case 2:
                        if (str3 == null) {
                            java.lang.String nextArgRequired = getNextArgRequired();
                            if ("disable".equals(nextArgRequired)) {
                                str3 = "-1";
                                break;
                            } else {
                                try {
                                    java.lang.Float.parseFloat(nextArgRequired);
                                    str3 = nextArgRequired;
                                    break;
                                } catch (java.lang.NumberFormatException e) {
                                    printWriter.println("Invalid scaling ratio '" + nextArgRequired + "'");
                                    return -1;
                                }
                            }
                        } else {
                            printWriter.println("Duplicate option '" + nextOption + "'");
                            return -1;
                        }
                    case 3:
                        if (str2 == null) {
                            java.lang.String nextArgRequired2 = getNextArgRequired();
                            try {
                                java.lang.Integer.parseInt(nextArgRequired2);
                                str2 = nextArgRequired2;
                                break;
                            } catch (java.lang.NumberFormatException e2) {
                                printWriter.println("Invalid frame rate: '" + nextArgRequired2 + "'");
                                return -1;
                            }
                        } else {
                            printWriter.println("Duplicate option '" + nextOption + "'");
                            return -1;
                        }
                    default:
                        printWriter.println("Invalid option '" + nextOption + "'");
                        return -1;
                }
            } else {
                java.lang.String nextArgRequired3 = getNextArgRequired();
                int parseInt = str != null ? java.lang.Integer.parseInt(str) : android.app.ActivityManager.getCurrentUser();
                com.android.server.app.GameManagerService gameManagerService = (com.android.server.app.GameManagerService) android.os.ServiceManager.getService("game");
                if (gameManagerService == null) {
                    printWriter.println("Failed to find GameManagerService on device");
                    return -1;
                }
                gameManagerService.setGameModeConfigOverride(nextArgRequired3, parseInt, i, str2, str3);
                printWriter.println("Set custom mode intervention config for user `" + parseInt + "` in game `" + nextArgRequired3 + "` as: `downscaling-ratio: " + str3 + ";fps-override: " + str2 + "`");
                return 0;
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0025, code lost:
    
        if (r2.equals("--mode") != false) goto L15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x00d7, code lost:
    
        if (r7.equals(com.android.server.app.GameManagerShellCommand.PERFORMANCE_MODE_STR) != false) goto L51;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int runResetGameModeConfig(java.io.PrintWriter printWriter) throws android.os.ServiceManager.ServiceNotFoundException, android.os.RemoteException {
        java.lang.String str = null;
        java.lang.String str2 = null;
        while (true) {
            java.lang.String nextOption = getNextOption();
            boolean z = true;
            if (nextOption != null) {
                switch (nextOption.hashCode()) {
                    case 1333227331:
                        break;
                    case 1333469547:
                        if (nextOption.equals("--user")) {
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
                        if (str == null) {
                            str = getNextArgRequired();
                            break;
                        } else {
                            printWriter.println("Duplicate option '" + nextOption + "'");
                            return -1;
                        }
                    case true:
                        if (str2 == null) {
                            str2 = getNextArgRequired();
                            break;
                        } else {
                            printWriter.println("Duplicate option '" + nextOption + "'");
                            return -1;
                        }
                    default:
                        printWriter.println("Invalid option '" + nextOption + "'");
                        return -1;
                }
            } else {
                java.lang.String nextArgRequired = getNextArgRequired();
                com.android.server.app.GameManagerService gameManagerService = (com.android.server.app.GameManagerService) android.os.ServiceManager.getService("game");
                int parseInt = str != null ? java.lang.Integer.parseInt(str) : android.app.ActivityManager.getCurrentUser();
                if (str2 == null) {
                    gameManagerService.resetGameModeConfigOverride(nextArgRequired, parseInt, -1);
                    return 0;
                }
                java.lang.String lowerCase = str2.toLowerCase(java.util.Locale.getDefault());
                switch (lowerCase.hashCode()) {
                    case -1480388560:
                        break;
                    case -331239923:
                        if (lowerCase.equals(BATTERY_MODE_STR)) {
                            z = 3;
                            break;
                        }
                        z = -1;
                        break;
                    case 50:
                        if (lowerCase.equals(PERFORMANCE_MODE_NUM)) {
                            z = false;
                            break;
                        }
                        z = -1;
                        break;
                    case 51:
                        if (lowerCase.equals(BATTERY_MODE_NUM)) {
                            z = 2;
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
                        gameManagerService.resetGameModeConfigOverride(nextArgRequired, parseInt, 2);
                        return 0;
                    case true:
                    case true:
                        gameManagerService.resetGameModeConfigOverride(nextArgRequired, parseInt, 3);
                        return 0;
                    default:
                        printWriter.println("Invalid game mode: " + str2);
                        return -1;
                }
            }
        }
    }

    private static java.lang.String gameModeIntToString(int i) {
        switch (i) {
            case 0:
                return UNSUPPORTED_MODE_STR;
            case 1:
                return STANDARD_MODE_STR;
            case 2:
                return PERFORMANCE_MODE_STR;
            case 3:
                return BATTERY_MODE_STR;
            case 4:
                return CUSTOM_MODE_STR;
            default:
                return "";
        }
    }

    public void onHelp() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Game manager (game) commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("      Print this help text.");
        outPrintWriter.println("  downscale");
        outPrintWriter.println("      Deprecated. Please use `custom` command.");
        outPrintWriter.println("  list-configs <PACKAGE_NAME>");
        outPrintWriter.println("      Lists the current intervention configs of an app.");
        outPrintWriter.println("  list-modes <PACKAGE_NAME>");
        outPrintWriter.println("      Lists the current selected and available game modes of an app.");
        outPrintWriter.println("  mode [--user <USER_ID>] [1|2|3|4|standard|performance|battery|custom] <PACKAGE_NAME>");
        outPrintWriter.println("      Set app to run in the specified game mode, if supported.");
        outPrintWriter.println("      --user <USER_ID>: apply for the given user,");
        outPrintWriter.println("                        the current user is used when unspecified.");
        outPrintWriter.println("  set [intervention configs] <PACKAGE_NAME>");
        outPrintWriter.println("      Set app to run at custom mode using provided intervention configs");
        outPrintWriter.println("      Intervention configs consists of:");
        outPrintWriter.println("      --downscale [0.3|0.35|0.4|0.45|0.5|0.55|0.6|0.65");
        outPrintWriter.println("                  |0.7|0.75|0.8|0.85|0.9|disable]: Set app to run at the");
        outPrintWriter.println("                                                   specified scaling ratio.");
        outPrintWriter.println("      --fps: Integer value to set app to run at the specified fps,");
        outPrintWriter.println("             if supported. 0 to disable.");
        outPrintWriter.println("  reset [--mode [2|3|performance|battery] --user <USER_ID>] <PACKAGE_NAME>");
        outPrintWriter.println("      Resets the game mode of the app to device configuration.");
        outPrintWriter.println("      This should only be used to reset any override to non custom game mode");
        outPrintWriter.println("      applied using the deprecated `set` command");
        outPrintWriter.println("      --mode [2|3|performance|battery]: apply for the given mode,");
        outPrintWriter.println("                                        resets all modes when unspecified.");
        outPrintWriter.println("      --user <USER_ID>: apply for the given user,");
        outPrintWriter.println("                        the current user is used when unspecified.");
    }
}
