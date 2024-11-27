package com.android.server.grammaticalinflection;

/* loaded from: classes.dex */
class GrammaticalInflectionShellCommand extends android.os.ShellCommand {
    private static final android.util.SparseArray<java.lang.String> GRAMMATICAL_GENDER_MAP = new android.util.SparseArray<>();
    private android.content.AttributionSource mAttributionSource;
    private final android.app.IGrammaticalInflectionManager mBinderService;

    static {
        GRAMMATICAL_GENDER_MAP.put(0, "Not specified (0)");
        GRAMMATICAL_GENDER_MAP.put(1, "Neuter (1)");
        GRAMMATICAL_GENDER_MAP.put(2, "Feminine (2)");
        GRAMMATICAL_GENDER_MAP.put(3, "Masculine (3)");
    }

    GrammaticalInflectionShellCommand(android.app.IGrammaticalInflectionManager iGrammaticalInflectionManager, android.content.AttributionSource attributionSource) {
        this.mBinderService = iGrammaticalInflectionManager;
        this.mAttributionSource = attributionSource;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public int onCommand(java.lang.String str) {
        char c;
        if (str == null) {
            return handleDefaultCommands(str);
        }
        switch (str.hashCode()) {
            case -1249285581:
                if (str.equals("set-system-grammatical-gender")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -976571353:
                if (str.equals("get-system-grammatical-gender")) {
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
        return handleDefaultCommands(str);
    }

    public void onHelp() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Grammatical inflection manager (grammatical_inflection) shell commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("      Print this help text.");
        outPrintWriter.println("  set-system-grammatical-gender [--user <USER_ID>] [--grammaticalGender <GRAMMATICAL_GENDER>]");
        outPrintWriter.println("      Set the system grammatical gender for system.");
        outPrintWriter.println("      --user <USER_ID>: apply for the given user, the current user is used when unspecified.");
        outPrintWriter.println("      --grammaticalGender <GRAMMATICAL_GENDER>: The terms of address the user preferred in system, not specified (0) is used when unspecified.");
        outPrintWriter.println("                 eg. 0 = not_specified, 1 = neuter, 2 = feminine, 3 = masculine.");
        outPrintWriter.println("  get-system-grammatical-gender [--user <USER_ID>]");
        outPrintWriter.println("      Get the system grammatical gender for system.");
        outPrintWriter.println("      --user <USER_ID>: apply for the given user, the current user is used when unspecified.");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int runSetSystemWideGrammaticalGender() {
        char c;
        int currentUser = android.app.ActivityManager.getCurrentUser();
        int i = 0;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                switch (nextOption.hashCode()) {
                    case 1498:
                        if (nextOption.equals("-g")) {
                            c = 1;
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
                    case 2015742127:
                        if (nextOption.equals("--grammaticalGender")) {
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
                        currentUser = android.os.UserHandle.parseUserArg(getNextArgRequired());
                        break;
                    case 1:
                    case 2:
                        i = parseGrammaticalGender();
                        break;
                    default:
                        throw new java.lang.IllegalArgumentException("Unknown option: " + nextOption);
                }
            } else {
                try {
                    this.mBinderService.setSystemWideGrammaticalGender(i, currentUser);
                } catch (android.os.RemoteException e) {
                    getOutPrintWriter().println("Remote Exception: " + e);
                }
                return 0;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x004c, code lost:
    
        if (r1.equals("--user") != false) goto L17;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int runGetSystemGrammaticalGender() {
        int currentUser = android.app.ActivityManager.getCurrentUser();
        while (true) {
            java.lang.String nextOption = getNextOption();
            char c = 0;
            if (nextOption != null) {
                switch (nextOption.hashCode()) {
                    case 1333469547:
                        break;
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                        currentUser = android.os.UserHandle.parseUserArg(getNextArgRequired());
                    default:
                        throw new java.lang.IllegalArgumentException("Unknown option: " + nextOption);
                }
            } else {
                try {
                    getOutPrintWriter().println(GRAMMATICAL_GENDER_MAP.get(this.mBinderService.getSystemGrammaticalGender(this.mAttributionSource, currentUser)));
                } catch (android.os.RemoteException e) {
                    getOutPrintWriter().println("Remote Exception: " + e);
                }
                return 0;
            }
        }
    }

    private int parseGrammaticalGender() {
        java.lang.String nextArg = getNextArg();
        if (nextArg == null) {
            return 0;
        }
        int parseInt = java.lang.Integer.parseInt(nextArg);
        if (!android.app.GrammaticalInflectionManager.VALID_GRAMMATICAL_GENDER_VALUES.contains(java.lang.Integer.valueOf(parseInt))) {
            return 0;
        }
        return parseInt;
    }
}
