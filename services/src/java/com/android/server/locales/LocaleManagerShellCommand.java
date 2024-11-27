package com.android.server.locales;

/* loaded from: classes2.dex */
public class LocaleManagerShellCommand extends android.os.ShellCommand {
    private final android.app.ILocaleManager mBinderService;

    LocaleManagerShellCommand(android.app.ILocaleManager iLocaleManager) {
        this.mBinderService = iLocaleManager;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public int onCommand(java.lang.String str) {
        char c;
        if (str == null) {
            return handleDefaultCommands(str);
        }
        switch (str.hashCode()) {
            case -843437997:
                if (str.equals("set-app-localeconfig")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -232514593:
                if (str.equals("get-app-localeconfig")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 819706294:
                if (str.equals("get-app-locales")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1730458818:
                if (str.equals("set-app-locales")) {
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

    public void onHelp() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Locale manager (locale) shell commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("      Print this help text.");
        outPrintWriter.println("  set-app-locales <PACKAGE_NAME> [--user <USER_ID>] [--locales <LOCALE_INFO>][--delegate <FROM_DELEGATE>]");
        outPrintWriter.println("      Set the locales for the specified app.");
        outPrintWriter.println("      --user <USER_ID>: apply for the given user, the current user is used when unspecified.");
        outPrintWriter.println("      --locales <LOCALE_INFO>: The language tags of locale to be included as a single String separated by commas.");
        outPrintWriter.println("                 eg. en,en-US,hi ");
        outPrintWriter.println("                 Empty locale list is used when unspecified.");
        outPrintWriter.println("      --delegate <FROM_DELEGATE>: The locales are set from a delegate, the value could be true or false. false is the default when unspecified.");
        outPrintWriter.println("  get-app-locales <PACKAGE_NAME> [--user <USER_ID>]");
        outPrintWriter.println("      Get the locales for the specified app.");
        outPrintWriter.println("      --user <USER_ID>: get for the given user, the current user is used when unspecified.");
        outPrintWriter.println("  set-app-localeconfig <PACKAGE_NAME> [--user <USER_ID>] [--locales <LOCALE_INFO>]");
        outPrintWriter.println("      Set the override LocaleConfig for the specified app.");
        outPrintWriter.println("      --user <USER_ID>: apply for the given user, the current user is used when unspecified.");
        outPrintWriter.println("      --locales <LOCALE_INFO>: The language tags of locale to be included as a single String separated by commas.");
        outPrintWriter.println("                 eg. en,en-US,hi ");
        outPrintWriter.println("                 Empty locale list is used when typing a 'empty' word");
        outPrintWriter.println("                 NULL is used when unspecified.");
        outPrintWriter.println("  get-app-localeconfig <PACKAGE_NAME> [--user <USER_ID>]");
        outPrintWriter.println("      Get the locales within the override LocaleConfig for the specified app.");
        outPrintWriter.println("      --user <USER_ID>: get for the given user, the current user is used when unspecified.");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int runSetAppLocales() {
        char c;
        java.io.PrintWriter errPrintWriter = getErrPrintWriter();
        java.lang.String nextArg = getNextArg();
        if (nextArg != null) {
            int currentUser = android.app.ActivityManager.getCurrentUser();
            android.os.LocaleList emptyLocaleList = android.os.LocaleList.getEmptyLocaleList();
            boolean z = false;
            while (true) {
                java.lang.String nextOption = getNextOption();
                if (nextOption != null) {
                    switch (nextOption.hashCode()) {
                        case 835076901:
                            if (nextOption.equals("--delegate")) {
                                c = 2;
                                break;
                            }
                            c = 65535;
                            break;
                        case 1333469547:
                            if (nextOption.equals("--user")) {
                                c = 0;
                                break;
                            }
                            c = 65535;
                            break;
                        case 1724392377:
                            if (nextOption.equals("--locales")) {
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
                            currentUser = android.os.UserHandle.parseUserArg(getNextArgRequired());
                            break;
                        case 1:
                            emptyLocaleList = parseLocales();
                            break;
                        case 2:
                            z = parseFromDelegate();
                            break;
                        default:
                            throw new java.lang.IllegalArgumentException("Unknown option: " + nextOption);
                    }
                } else {
                    try {
                        this.mBinderService.setApplicationLocales(nextArg, currentUser, emptyLocaleList, z);
                    } catch (android.os.RemoteException e) {
                        getOutPrintWriter().println("Remote Exception: " + e);
                    } catch (java.lang.IllegalArgumentException e2) {
                        getOutPrintWriter().println("Unknown package " + nextArg + " for userId " + currentUser);
                    }
                    return 0;
                }
            }
        } else {
            errPrintWriter.println("Error: no package specified");
            return -1;
        }
    }

    private int runGetAppLocales() {
        java.io.PrintWriter errPrintWriter = getErrPrintWriter();
        java.lang.String nextArg = getNextArg();
        if (nextArg != null) {
            int currentUser = android.app.ActivityManager.getCurrentUser();
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                if ("--user".equals(nextOption)) {
                    currentUser = android.os.UserHandle.parseUserArg(getNextArgRequired());
                } else {
                    throw new java.lang.IllegalArgumentException("Unknown option: " + nextOption);
                }
            }
            try {
                android.os.LocaleList applicationLocales = this.mBinderService.getApplicationLocales(nextArg, currentUser);
                getOutPrintWriter().println("Locales for " + nextArg + " for user " + currentUser + " are [" + applicationLocales.toLanguageTags() + "]");
                return 0;
            } catch (android.os.RemoteException e) {
                getOutPrintWriter().println("Remote Exception: " + e);
                return 0;
            } catch (java.lang.IllegalArgumentException e2) {
                getOutPrintWriter().println("Unknown package " + nextArg + " for userId " + currentUser);
                return 0;
            }
        }
        errPrintWriter.println("Error: no package specified");
        return -1;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0057, code lost:
    
        if (r5.equals("--user") != false) goto L26;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int runSetAppOverrideLocaleConfig() {
        java.lang.String nextArg = getNextArg();
        if (nextArg != null) {
            int currentUser = android.app.ActivityManager.getCurrentUser();
            android.app.LocaleConfig localeConfig = null;
            android.os.LocaleList localeList = null;
            while (true) {
                java.lang.String nextOption = getNextOption();
                boolean z = false;
                if (nextOption != null) {
                    switch (nextOption.hashCode()) {
                        case 1333469547:
                            break;
                        case 1724392377:
                            if (nextOption.equals("--locales")) {
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
                            currentUser = android.os.UserHandle.parseUserArg(getNextArgRequired());
                            break;
                        case true:
                            localeList = parseOverrideLocales();
                            break;
                        default:
                            throw new java.lang.IllegalArgumentException("Unknown option: " + nextOption);
                    }
                } else {
                    if (localeList != null) {
                        try {
                            localeConfig = new android.app.LocaleConfig(localeList);
                        } catch (android.os.RemoteException e) {
                            getOutPrintWriter().println("Remote Exception: " + e);
                        }
                    }
                    this.mBinderService.setOverrideLocaleConfig(nextArg, currentUser, localeConfig);
                    return 0;
                }
            }
        } else {
            getErrPrintWriter().println("Error: no package specified");
            return -1;
        }
    }

    private int runGetAppOverrideLocaleConfig() {
        java.lang.String nextArg = getNextArg();
        if (nextArg != null) {
            int currentUser = android.app.ActivityManager.getCurrentUser();
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                if ("--user".equals(nextOption)) {
                    currentUser = android.os.UserHandle.parseUserArg(getNextArgRequired());
                } else {
                    throw new java.lang.IllegalArgumentException("Unknown option: " + nextOption);
                }
            }
            try {
                android.app.LocaleConfig overrideLocaleConfig = this.mBinderService.getOverrideLocaleConfig(nextArg, currentUser);
                if (overrideLocaleConfig == null) {
                    getOutPrintWriter().println("LocaleConfig for " + nextArg + " for user " + currentUser + " is null");
                    return 0;
                }
                android.os.LocaleList supportedLocales = overrideLocaleConfig.getSupportedLocales();
                if (supportedLocales == null) {
                    getOutPrintWriter().println("Locales within the LocaleConfig for " + nextArg + " for user " + currentUser + " are null");
                    return 0;
                }
                getOutPrintWriter().println("Locales within the LocaleConfig for " + nextArg + " for user " + currentUser + " are [" + supportedLocales.toLanguageTags() + "]");
                return 0;
            } catch (android.os.RemoteException e) {
                getOutPrintWriter().println("Remote Exception: " + e);
                return 0;
            }
        }
        getErrPrintWriter().println("Error: no package specified");
        return -1;
    }

    private android.os.LocaleList parseOverrideLocales() {
        java.lang.String nextArg = getNextArg();
        if (nextArg == null) {
            return null;
        }
        if (nextArg.equals("empty")) {
            return android.os.LocaleList.getEmptyLocaleList();
        }
        if (nextArg.startsWith("-")) {
            throw new java.lang.IllegalArgumentException("Unknown locales: " + nextArg);
        }
        return android.os.LocaleList.forLanguageTags(nextArg);
    }

    private android.os.LocaleList parseLocales() {
        java.lang.String nextArg = getNextArg();
        if (nextArg == null) {
            return android.os.LocaleList.getEmptyLocaleList();
        }
        if (nextArg.startsWith("-")) {
            throw new java.lang.IllegalArgumentException("Unknown locales: " + nextArg);
        }
        return android.os.LocaleList.forLanguageTags(nextArg);
    }

    private boolean parseFromDelegate() {
        java.lang.String nextArg = getNextArg();
        if (nextArg == null) {
            return false;
        }
        if (nextArg.startsWith("-")) {
            throw new java.lang.IllegalArgumentException("Unknown source: " + nextArg);
        }
        return java.lang.Boolean.parseBoolean(nextArg);
    }
}
