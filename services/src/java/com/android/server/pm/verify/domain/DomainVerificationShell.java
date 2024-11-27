package com.android.server.pm.verify.domain;

/* loaded from: classes2.dex */
public class DomainVerificationShell {

    @android.annotation.NonNull
    private final com.android.server.pm.verify.domain.DomainVerificationShell.Callback mCallback;

    public interface Callback {
        void clearDomainVerificationState(@android.annotation.Nullable java.util.List<java.lang.String> list);

        void clearUserStates(@android.annotation.Nullable java.util.List<java.lang.String> list, int i);

        @android.annotation.Nullable
        android.content.pm.verify.domain.DomainVerificationUserState getDomainVerificationUserState(@android.annotation.NonNull java.lang.String str, int i) throws android.content.pm.PackageManager.NameNotFoundException;

        void printOwnersForDomains(@android.annotation.NonNull android.util.IndentingPrintWriter indentingPrintWriter, @android.annotation.NonNull java.util.List<java.lang.String> list, @android.annotation.Nullable java.lang.Integer num);

        void printOwnersForPackage(@android.annotation.NonNull android.util.IndentingPrintWriter indentingPrintWriter, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.Integer num) throws android.content.pm.PackageManager.NameNotFoundException;

        void printState(@android.annotation.NonNull android.util.IndentingPrintWriter indentingPrintWriter, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.Integer num) throws android.content.pm.PackageManager.NameNotFoundException;

        void setDomainVerificationLinkHandlingAllowedInternal(@android.annotation.Nullable java.lang.String str, boolean z, int i) throws android.content.pm.PackageManager.NameNotFoundException;

        void setDomainVerificationStatusInternal(@android.annotation.Nullable java.lang.String str, int i, @android.annotation.Nullable android.util.ArraySet<java.lang.String> arraySet) throws android.content.pm.PackageManager.NameNotFoundException;

        void setDomainVerificationUserSelectionInternal(int i, @android.annotation.NonNull java.lang.String str, boolean z, @android.annotation.Nullable android.util.ArraySet<java.lang.String> arraySet) throws android.content.pm.PackageManager.NameNotFoundException;

        void verifyPackages(@android.annotation.Nullable java.util.List<java.lang.String> list, boolean z);
    }

    public DomainVerificationShell(@android.annotation.NonNull com.android.server.pm.verify.domain.DomainVerificationShell.Callback callback) {
        this.mCallback = callback;
    }

    public void printHelp(@android.annotation.NonNull java.io.PrintWriter printWriter) {
        printWriter.println("  get-app-links [--user <USER_ID>] [<PACKAGE>]");
        printWriter.println("    Prints the domain verification state for the given package, or for all");
        printWriter.println("    packages if none is specified. State codes are defined as follows:");
        printWriter.println("        - none: nothing has been recorded for this domain");
        printWriter.println("        - verified: the domain has been successfully verified");
        printWriter.println("        - approved: force approved, usually through shell");
        printWriter.println("        - denied: force denied, usually through shell");
        printWriter.println("        - migrated: preserved verification from a legacy response");
        printWriter.println("        - restored: preserved verification from a user data restore");
        printWriter.println("        - legacy_failure: rejected by a legacy verifier, unknown reason");
        printWriter.println("        - system_configured: automatically approved by the device config");
        printWriter.println("        - pre_verified: the domain was pre-verified by the installer");
        printWriter.println("        - >= 1024: Custom error code which is specific to the device verifier");
        printWriter.println("      --user <USER_ID>: include user selections (includes all domains, not");
        printWriter.println("        just autoVerify ones)");
        printWriter.println("  reset-app-links [--user <USER_ID>] [<PACKAGE>]");
        printWriter.println("    Resets domain verification state for the given package, or for all");
        printWriter.println("    packages if none is specified.");
        printWriter.println("      --user <USER_ID>: clear user selection state instead; note this means");
        printWriter.println("        domain verification state will NOT be cleared");
        printWriter.println("      <PACKAGE>: the package to reset, or \"all\" to reset all packages");
        printWriter.println("  verify-app-links [--re-verify] [<PACKAGE>]");
        printWriter.println("    Broadcasts a verification request for the given package, or for all");
        printWriter.println("    packages if none is specified. Only sends if the package has previously");
        printWriter.println("    not recorded a response.");
        printWriter.println("      --re-verify: send even if the package has recorded a response");
        printWriter.println("  set-app-links [--package <PACKAGE>] <STATE> <DOMAINS>...");
        printWriter.println("    Manually set the state of a domain for a package. The domain must be");
        printWriter.println("    declared by the package as autoVerify for this to work. This command");
        printWriter.println("    will not report a failure for domains that could not be applied.");
        printWriter.println("      --package <PACKAGE>: the package to set, or \"all\" to set all packages");
        printWriter.println("      <STATE>: the code to set the domains to, valid values are:");
        printWriter.println("        STATE_NO_RESPONSE (0): reset as if no response was ever recorded.");
        printWriter.println("        STATE_SUCCESS (1): treat domain as successfully verified by domain.");
        printWriter.println("          verification agent. Note that the domain verification agent can");
        printWriter.println("          override this.");
        printWriter.println("        STATE_APPROVED (2): treat domain as always approved, preventing the");
        printWriter.println("           domain verification agent from changing it.");
        printWriter.println("        STATE_DENIED (3): treat domain as always denied, preveting the domain");
        printWriter.println("          verification agent from changing it.");
        printWriter.println("      <DOMAINS>: space separated list of domains to change, or \"all\" to");
        printWriter.println("        change every domain.");
        printWriter.println("  set-app-links-user-selection --user <USER_ID> [--package <PACKAGE>]");
        printWriter.println("      <ENABLED> <DOMAINS>...");
        printWriter.println("    Manually set the state of a host user selection for a package. The domain");
        printWriter.println("    must be declared by the package for this to work. This command will not");
        printWriter.println("    report a failure for domains that could not be applied.");
        printWriter.println("      --user <USER_ID>: the user to change selections for");
        printWriter.println("      --package <PACKAGE>: the package to set");
        printWriter.println("      <ENABLED>: whether or not to approve the domain");
        printWriter.println("      <DOMAINS>: space separated list of domains to change, or \"all\" to");
        printWriter.println("        change every domain.");
        printWriter.println("  set-app-links-allowed --user <USER_ID> [--package <PACKAGE>] <ALLOWED>");
        printWriter.println("    Toggle the auto verified link handling setting for a package.");
        printWriter.println("      --user <USER_ID>: the user to change selections for");
        printWriter.println("      --package <PACKAGE>: the package to set, or \"all\" to set all packages");
        printWriter.println("        packages will be reset if no one package is specified.");
        printWriter.println("      <ALLOWED>: true to allow the package to open auto verified links, false");
        printWriter.println("        to disable");
        printWriter.println("  get-app-link-owners [--user <USER_ID>] [--package <PACKAGE>] [<DOMAINS>]");
        printWriter.println("    Print the owners for a specific domain for a given user in low to high");
        printWriter.println("    priority order.");
        printWriter.println("      --user <USER_ID>: the user to query for");
        printWriter.println("      --package <PACKAGE>: optionally also print for all web domains declared");
        printWriter.println("        by a package, or \"all\" to print all packages");
        printWriter.println("      --<DOMAINS>: space separated list of domains to query for");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public java.lang.Boolean runCommand(@android.annotation.NonNull com.android.modules.utils.BasicShellCommandHandler basicShellCommandHandler, @android.annotation.NonNull java.lang.String str) {
        char c;
        switch (str.hashCode()) {
            case -2140094634:
                if (str.equals("get-app-links")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -2092945963:
                if (str.equals("set-app-links-user-selection")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -1850904515:
                if (str.equals("set-app-links-allowed")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -1365963422:
                if (str.equals("set-app-links")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -825562609:
                if (str.equals("reset-app-links")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1161008944:
                if (str.equals("get-app-link-owners")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 1328605369:
                if (str.equals("verify-app-links")) {
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
                return java.lang.Boolean.valueOf(runGetAppLinks(basicShellCommandHandler));
            case 1:
                return java.lang.Boolean.valueOf(runResetAppLinks(basicShellCommandHandler));
            case 2:
                return java.lang.Boolean.valueOf(runVerifyAppLinks(basicShellCommandHandler));
            case 3:
                return java.lang.Boolean.valueOf(runSetAppLinks(basicShellCommandHandler));
            case 4:
                return java.lang.Boolean.valueOf(runSetAppLinksUserState(basicShellCommandHandler));
            case 5:
                return java.lang.Boolean.valueOf(runSetAppLinksAllowed(basicShellCommandHandler));
            case 6:
                return java.lang.Boolean.valueOf(runGetAppLinkOwners(basicShellCommandHandler));
            default:
                return null;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private boolean runSetAppLinks(@android.annotation.NonNull com.android.modules.utils.BasicShellCommandHandler basicShellCommandHandler) {
        char c;
        android.util.ArraySet<java.lang.String> arraySet = null;
        java.lang.String str = null;
        while (true) {
            java.lang.String nextOption = basicShellCommandHandler.getNextOption();
            if (nextOption != null) {
                if (nextOption.equals("--package")) {
                    str = basicShellCommandHandler.getNextArgRequired();
                } else {
                    basicShellCommandHandler.getErrPrintWriter().println("Error: unknown option: " + nextOption);
                    return false;
                }
            } else {
                if (android.text.TextUtils.isEmpty(str)) {
                    basicShellCommandHandler.getErrPrintWriter().println("Error: no package specified");
                    return false;
                }
                if (str.equalsIgnoreCase("all")) {
                    str = null;
                }
                java.lang.String nextArgRequired = basicShellCommandHandler.getNextArgRequired();
                int i = 3;
                switch (nextArgRequired.hashCode()) {
                    case -1618466107:
                        if (nextArgRequired.equals("STATE_APPROVED")) {
                            c = 4;
                            break;
                        }
                        c = 65535;
                        break;
                    case 48:
                        if (nextArgRequired.equals("0")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 49:
                        if (nextArgRequired.equals("1")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    case 50:
                        if (nextArgRequired.equals("2")) {
                            c = 5;
                            break;
                        }
                        c = 65535;
                        break;
                    case 51:
                        if (nextArgRequired.equals("3")) {
                            c = 7;
                            break;
                        }
                        c = 65535;
                        break;
                    case 259145237:
                        if (nextArgRequired.equals("STATE_SUCCESS")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 482582065:
                        if (nextArgRequired.equals("STATE_NO_RESPONSE")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case 534310697:
                        if (nextArgRequired.equals("STATE_DENIED")) {
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
                    case 1:
                        i = 0;
                        break;
                    case 2:
                    case 3:
                        i = 1;
                        break;
                    case 4:
                    case 5:
                        i = 2;
                        break;
                    case 6:
                    case 7:
                        break;
                    default:
                        basicShellCommandHandler.getErrPrintWriter().println("Invalid state option: " + nextArgRequired);
                        return false;
                }
                android.util.ArraySet<java.lang.String> arraySet2 = new android.util.ArraySet<>(getRemainingArgs(basicShellCommandHandler));
                if (arraySet2.isEmpty()) {
                    basicShellCommandHandler.getErrPrintWriter().println("No domains specified");
                    return false;
                }
                if (arraySet2.size() != 1 || !arraySet2.contains("all")) {
                    arraySet = arraySet2;
                }
                try {
                    this.mCallback.setDomainVerificationStatusInternal(str, i, arraySet);
                    return true;
                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                    basicShellCommandHandler.getErrPrintWriter().println("Package not found: " + str);
                    return false;
                }
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0024, code lost:
    
        if (r3.equals("--package") != false) goto L15;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean runSetAppLinksUserState(@android.annotation.NonNull com.android.modules.utils.BasicShellCommandHandler basicShellCommandHandler) {
        android.util.ArraySet<java.lang.String> arraySet = null;
        java.lang.String str = null;
        java.lang.Integer num = null;
        while (true) {
            java.lang.String nextOption = basicShellCommandHandler.getNextOption();
            char c = 1;
            if (nextOption != null) {
                switch (nextOption.hashCode()) {
                    case 578919078:
                        break;
                    case 1333469547:
                        if (nextOption.equals("--user")) {
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
                        num = java.lang.Integer.valueOf(android.os.UserHandle.parseUserArg(basicShellCommandHandler.getNextArgRequired()));
                        break;
                    case 1:
                        str = basicShellCommandHandler.getNextArgRequired();
                        break;
                    default:
                        basicShellCommandHandler.getErrPrintWriter().println("Error: unknown option: " + nextOption);
                        return false;
                }
            } else {
                if (android.text.TextUtils.isEmpty(str)) {
                    basicShellCommandHandler.getErrPrintWriter().println("Error: no package specified");
                    return false;
                }
                if (num == null) {
                    basicShellCommandHandler.getErrPrintWriter().println("Error: User ID not specified");
                    return false;
                }
                java.lang.Integer valueOf = java.lang.Integer.valueOf(translateUserId(num.intValue(), "runSetAppLinksUserState"));
                java.lang.String nextArg = basicShellCommandHandler.getNextArg();
                if (android.text.TextUtils.isEmpty(nextArg)) {
                    basicShellCommandHandler.getErrPrintWriter().println("Error: enabled param not specified");
                    return false;
                }
                try {
                    boolean parseEnabled = parseEnabled(nextArg);
                    android.util.ArraySet<java.lang.String> arraySet2 = new android.util.ArraySet<>(getRemainingArgs(basicShellCommandHandler));
                    if (arraySet2.isEmpty()) {
                        basicShellCommandHandler.getErrPrintWriter().println("No domains specified");
                        return false;
                    }
                    if (arraySet2.size() != 1 || !arraySet2.contains("all")) {
                        arraySet = arraySet2;
                    }
                    try {
                        this.mCallback.setDomainVerificationUserSelectionInternal(valueOf.intValue(), str, parseEnabled, arraySet);
                        return true;
                    } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                        basicShellCommandHandler.getErrPrintWriter().println("Package not found: " + str);
                        return false;
                    }
                } catch (java.lang.IllegalArgumentException e2) {
                    basicShellCommandHandler.getErrPrintWriter().println("Error: invalid enabled param: " + e2.getMessage());
                    return false;
                }
            }
        }
    }

    private boolean runGetAppLinks(@android.annotation.NonNull com.android.modules.utils.BasicShellCommandHandler basicShellCommandHandler) {
        java.lang.Integer num = null;
        while (true) {
            java.lang.String nextOption = basicShellCommandHandler.getNextOption();
            if (nextOption != null) {
                if (nextOption.equals("--user")) {
                    num = java.lang.Integer.valueOf(android.os.UserHandle.parseUserArg(basicShellCommandHandler.getNextArgRequired()));
                } else {
                    basicShellCommandHandler.getErrPrintWriter().println("Error: unknown option: " + nextOption);
                    return false;
                }
            } else {
                java.lang.Integer valueOf = num != null ? java.lang.Integer.valueOf(translateUserId(num.intValue(), "runGetAppLinks")) : null;
                java.lang.String nextArg = basicShellCommandHandler.getNextArg();
                android.util.IndentingPrintWriter indentingPrintWriter = new android.util.IndentingPrintWriter(basicShellCommandHandler.getOutPrintWriter(), "  ", 120);
                try {
                    indentingPrintWriter.increaseIndent();
                    try {
                        this.mCallback.printState(indentingPrintWriter, nextArg, valueOf);
                        indentingPrintWriter.decreaseIndent();
                        indentingPrintWriter.close();
                        return true;
                    } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                        basicShellCommandHandler.getErrPrintWriter().println("Error: package " + nextArg + " unavailable");
                        indentingPrintWriter.close();
                        return false;
                    }
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

    private boolean runResetAppLinks(@android.annotation.NonNull com.android.modules.utils.BasicShellCommandHandler basicShellCommandHandler) {
        java.util.List<java.lang.String> list = null;
        java.lang.Integer num = null;
        while (true) {
            java.lang.String nextOption = basicShellCommandHandler.getNextOption();
            if (nextOption != null) {
                if (nextOption.equals("--user")) {
                    num = java.lang.Integer.valueOf(android.os.UserHandle.parseUserArg(basicShellCommandHandler.getNextArgRequired()));
                } else {
                    basicShellCommandHandler.getErrPrintWriter().println("Error: unknown option: " + nextOption);
                    return false;
                }
            } else {
                java.lang.Integer valueOf = num == null ? null : java.lang.Integer.valueOf(translateUserId(num.intValue(), "runResetAppLinks"));
                java.lang.String peekNextArg = basicShellCommandHandler.peekNextArg();
                if (android.text.TextUtils.isEmpty(peekNextArg)) {
                    basicShellCommandHandler.getErrPrintWriter().println("Error: no package specified");
                    return false;
                }
                if (!peekNextArg.equalsIgnoreCase("all")) {
                    list = java.util.Arrays.asList(basicShellCommandHandler.peekRemainingArgs());
                }
                if (valueOf != null) {
                    this.mCallback.clearUserStates(list, valueOf.intValue());
                    return true;
                }
                this.mCallback.clearDomainVerificationState(list);
                return true;
            }
        }
    }

    private boolean runVerifyAppLinks(@android.annotation.NonNull com.android.modules.utils.BasicShellCommandHandler basicShellCommandHandler) {
        java.util.List<java.lang.String> list;
        boolean z = false;
        while (true) {
            java.lang.String nextOption = basicShellCommandHandler.getNextOption();
            if (nextOption != null) {
                if (nextOption.equals("--re-verify")) {
                    z = true;
                } else {
                    basicShellCommandHandler.getErrPrintWriter().println("Error: unknown option: " + nextOption);
                    return false;
                }
            } else {
                java.lang.String nextArg = basicShellCommandHandler.getNextArg();
                if (android.text.TextUtils.isEmpty(nextArg)) {
                    list = null;
                } else {
                    list = java.util.Collections.singletonList(nextArg);
                }
                this.mCallback.verifyPackages(list, z);
                return true;
            }
        }
    }

    private boolean runSetAppLinksAllowed(@android.annotation.NonNull com.android.modules.utils.BasicShellCommandHandler basicShellCommandHandler) {
        java.lang.String str = null;
        java.lang.String str2 = null;
        java.lang.Integer num = null;
        while (true) {
            java.lang.String nextOption = basicShellCommandHandler.getNextOption();
            if (nextOption != null) {
                if (nextOption.equals("--package")) {
                    str2 = basicShellCommandHandler.getNextArg();
                } else if (nextOption.equals("--user")) {
                    num = java.lang.Integer.valueOf(android.os.UserHandle.parseUserArg(basicShellCommandHandler.getNextArgRequired()));
                } else {
                    basicShellCommandHandler.getErrPrintWriter().println("Error: unexpected option: " + nextOption);
                    return false;
                }
            } else {
                if (android.text.TextUtils.isEmpty(str2)) {
                    basicShellCommandHandler.getErrPrintWriter().println("Error: no package specified");
                    return false;
                }
                if (!str2.equalsIgnoreCase("all")) {
                    str = str2;
                }
                if (num == null) {
                    basicShellCommandHandler.getErrPrintWriter().println("Error: user ID not specified");
                    return false;
                }
                java.lang.String nextArg = basicShellCommandHandler.getNextArg();
                if (android.text.TextUtils.isEmpty(nextArg)) {
                    basicShellCommandHandler.getErrPrintWriter().println("Error: allowed setting not specified");
                    return false;
                }
                try {
                    try {
                        this.mCallback.setDomainVerificationLinkHandlingAllowedInternal(str, parseEnabled(nextArg), java.lang.Integer.valueOf(translateUserId(num.intValue(), "runSetAppLinksAllowed")).intValue());
                        return true;
                    } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                        basicShellCommandHandler.getErrPrintWriter().println("Package not found: " + str);
                        return false;
                    }
                } catch (java.lang.IllegalArgumentException e2) {
                    basicShellCommandHandler.getErrPrintWriter().println("Error: invalid allowed setting: " + e2.getMessage());
                    return false;
                }
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0024, code lost:
    
        if (r3.equals("--package") != false) goto L15;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean runGetAppLinkOwners(@android.annotation.NonNull com.android.modules.utils.BasicShellCommandHandler basicShellCommandHandler) {
        java.lang.String str = null;
        java.lang.String str2 = null;
        java.lang.Integer num = null;
        while (true) {
            java.lang.String nextOption = basicShellCommandHandler.getNextOption();
            char c = 1;
            if (nextOption != null) {
                switch (nextOption.hashCode()) {
                    case 578919078:
                        break;
                    case 1333469547:
                        if (nextOption.equals("--user")) {
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
                        num = java.lang.Integer.valueOf(android.os.UserHandle.parseUserArg(basicShellCommandHandler.getNextArgRequired()));
                        break;
                    case 1:
                        str2 = basicShellCommandHandler.getNextArgRequired();
                        if (!android.text.TextUtils.isEmpty(str2)) {
                            break;
                        } else {
                            basicShellCommandHandler.getErrPrintWriter().println("Error: no package specified");
                            return false;
                        }
                    default:
                        basicShellCommandHandler.getErrPrintWriter().println("Error: unexpected option: " + nextOption);
                        return false;
                }
            } else {
                java.util.ArrayList<java.lang.String> remainingArgs = getRemainingArgs(basicShellCommandHandler);
                if (remainingArgs.isEmpty() && android.text.TextUtils.isEmpty(str2)) {
                    basicShellCommandHandler.getErrPrintWriter().println("Error: no package name or domain specified");
                    return false;
                }
                if (num != null) {
                    num = java.lang.Integer.valueOf(translateUserId(num.intValue(), "runSetAppLinksAllowed"));
                }
                android.util.IndentingPrintWriter indentingPrintWriter = new android.util.IndentingPrintWriter(basicShellCommandHandler.getOutPrintWriter(), "  ", 120);
                try {
                    indentingPrintWriter.increaseIndent();
                    if (str2 != null) {
                        if (!str2.equals("all")) {
                            str = str2;
                        }
                        try {
                            this.mCallback.printOwnersForPackage(indentingPrintWriter, str, num);
                        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                            basicShellCommandHandler.getErrPrintWriter().println("Error: package not found: " + str);
                            indentingPrintWriter.close();
                            return false;
                        }
                    }
                    if (!remainingArgs.isEmpty()) {
                        this.mCallback.printOwnersForDomains(indentingPrintWriter, remainingArgs, num);
                    }
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.close();
                    return true;
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

    @android.annotation.NonNull
    private java.util.ArrayList<java.lang.String> getRemainingArgs(@android.annotation.NonNull com.android.modules.utils.BasicShellCommandHandler basicShellCommandHandler) {
        java.util.ArrayList<java.lang.String> arrayList = new java.util.ArrayList<>();
        while (true) {
            java.lang.String nextArg = basicShellCommandHandler.getNextArg();
            if (nextArg != null) {
                arrayList.add(nextArg);
            } else {
                return arrayList;
            }
        }
    }

    private int translateUserId(int i, @android.annotation.NonNull java.lang.String str) {
        return android.app.ActivityManager.handleIncomingUser(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, true, true, str, "pm command");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @android.annotation.NonNull
    private boolean parseEnabled(@android.annotation.NonNull java.lang.String str) throws java.lang.IllegalArgumentException {
        char c;
        java.lang.String lowerCase = str.toLowerCase(java.util.Locale.US);
        switch (lowerCase.hashCode()) {
            case 3569038:
                if (lowerCase.equals("true")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 97196323:
                if (lowerCase.equals("false")) {
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
                return true;
            case 1:
                return false;
            default:
                throw new java.lang.IllegalArgumentException(str + " is not a valid boolean");
        }
    }
}
