package com.android.server.locksettings;

/* loaded from: classes2.dex */
class LockSettingsShellCommand extends android.os.ShellCommand {
    private static final java.lang.String COMMAND_CLEAR = "clear";
    private static final java.lang.String COMMAND_GET_DISABLED = "get-disabled";
    private static final java.lang.String COMMAND_HELP = "help";
    private static final java.lang.String COMMAND_REMOVE_CACHE = "remove-cache";
    private static final java.lang.String COMMAND_REQUIRE_STRONG_AUTH = "require-strong-auth";
    private static final java.lang.String COMMAND_SET_DISABLED = "set-disabled";
    private static final java.lang.String COMMAND_SET_PASSWORD = "set-password";
    private static final java.lang.String COMMAND_SET_PATTERN = "set-pattern";
    private static final java.lang.String COMMAND_SET_PIN = "set-pin";
    private static final java.lang.String COMMAND_SET_ROR_PROVIDER_PACKAGE = "set-resume-on-reboot-provider-package";
    private static final java.lang.String COMMAND_VERIFY = "verify";
    private final int mCallingPid;
    private final int mCallingUid;
    private final android.content.Context mContext;
    private int mCurrentUserId;
    private final com.android.internal.widget.LockPatternUtils mLockPatternUtils;
    private java.lang.String mOld = "";
    private java.lang.String mNew = "";

    LockSettingsShellCommand(com.android.internal.widget.LockPatternUtils lockPatternUtils, android.content.Context context, int i, int i2) {
        this.mLockPatternUtils = lockPatternUtils;
        this.mCallingPid = i;
        this.mCallingUid = i2;
        this.mContext = context;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public int onCommand(java.lang.String str) {
        char c;
        boolean z;
        if (str == null) {
            return handleDefaultCommands(str);
        }
        try {
            this.mCurrentUserId = android.app.ActivityManager.getService().getCurrentUser().id;
            parseArgs();
            char c2 = 2;
            boolean z2 = true;
            if (!this.mLockPatternUtils.hasSecureLockScreen()) {
                switch (str.hashCode()) {
                    case -1473704173:
                        if (str.equals(COMMAND_GET_DISABLED)) {
                            z = true;
                            break;
                        }
                        z = -1;
                        break;
                    case 3198785:
                        if (str.equals(COMMAND_HELP)) {
                            z = false;
                            break;
                        }
                        z = -1;
                        break;
                    case 75288455:
                        if (str.equals(COMMAND_SET_DISABLED)) {
                            z = 2;
                            break;
                        }
                        z = -1;
                        break;
                    case 1062640281:
                        if (str.equals(COMMAND_SET_ROR_PROVIDER_PACKAGE)) {
                            z = 3;
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
                    case true:
                    case true:
                        break;
                    default:
                        getErrPrintWriter().println("The device does not support lock screen - ignoring the command.");
                        return -1;
                }
            }
            switch (str.hashCode()) {
                case -1957541639:
                    if (str.equals(COMMAND_REMOVE_CACHE)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case -1473704173:
                    if (str.equals(COMMAND_GET_DISABLED)) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case -868666698:
                    if (str.equals(COMMAND_REQUIRE_STRONG_AUTH)) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 3198785:
                    if (str.equals(COMMAND_HELP)) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 75288455:
                    if (str.equals(COMMAND_SET_DISABLED)) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case 1062640281:
                    if (str.equals(COMMAND_SET_ROR_PROVIDER_PACKAGE)) {
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
                    runRemoveCache();
                    return 0;
                case 1:
                    runSetResumeOnRebootProviderPackage();
                    return 0;
                case 2:
                    runRequireStrongAuth();
                    return 0;
                case 3:
                    onHelp();
                    return 0;
                case 4:
                    runGetDisabled();
                    return 0;
                case 5:
                    runSetDisabled();
                    return 0;
                default:
                    if (!checkCredential()) {
                        return -1;
                    }
                    switch (str.hashCode()) {
                        case -2044327643:
                            if (str.equals(COMMAND_SET_PATTERN)) {
                                c2 = 0;
                                break;
                            }
                            c2 = 65535;
                            break;
                        case -819951495:
                            if (str.equals(COMMAND_VERIFY)) {
                                c2 = 4;
                                break;
                            }
                            c2 = 65535;
                            break;
                        case 94746189:
                            if (str.equals(COMMAND_CLEAR)) {
                                c2 = 3;
                                break;
                            }
                            c2 = 65535;
                            break;
                        case 1021333414:
                            if (str.equals(COMMAND_SET_PASSWORD)) {
                                c2 = 1;
                                break;
                            }
                            c2 = 65535;
                            break;
                        case 1983832490:
                            if (str.equals(COMMAND_SET_PIN)) {
                                break;
                            }
                            c2 = 65535;
                            break;
                        default:
                            c2 = 65535;
                            break;
                    }
                    switch (c2) {
                        case 0:
                            z2 = runSetPattern();
                            break;
                        case 1:
                            z2 = runSetPassword();
                            break;
                        case 2:
                            z2 = runSetPin();
                            break;
                        case 3:
                            z2 = runClear();
                            break;
                        case 4:
                            runVerify();
                            break;
                        default:
                            getErrPrintWriter().println("Unknown command: " + str);
                            break;
                    }
                    return z2 ? 0 : -1;
            }
        } catch (java.lang.Exception e) {
            getErrPrintWriter().println("Error while executing command: " + str);
            e.printStackTrace(getErrPrintWriter());
            return -1;
        }
    }

    private void runVerify() {
        getOutPrintWriter().println("Lock credential verified successfully");
    }

    public void onHelp() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        try {
            outPrintWriter.println("lockSettings service commands:");
            outPrintWriter.println("");
            outPrintWriter.println("NOTE: when a secure lock screen is set, most commands require the");
            outPrintWriter.println("--old <CREDENTIAL> option.");
            outPrintWriter.println("");
            outPrintWriter.println("  help");
            outPrintWriter.println("    Prints this help text.");
            outPrintWriter.println("");
            outPrintWriter.println("  get-disabled [--user USER_ID]");
            outPrintWriter.println("    Prints true if the lock screen is completely disabled, i.e. set to None.");
            outPrintWriter.println("    Otherwise prints false.");
            outPrintWriter.println("");
            outPrintWriter.println("  set-disabled [--user USER_ID] <true|false>");
            outPrintWriter.println("    Sets whether the lock screen is disabled. If the lock screen is secure, this");
            outPrintWriter.println("    has no immediate effect. I.e. this can only change between Swipe and None.");
            outPrintWriter.println("");
            outPrintWriter.println("  set-pattern [--old <CREDENTIAL>] [--user USER_ID] <PATTERN>");
            outPrintWriter.println("    Sets a secure lock screen that uses the given PATTERN. PATTERN is a series");
            outPrintWriter.println("    of digits 1-9 that identify the cells of the pattern.");
            outPrintWriter.println("");
            outPrintWriter.println("  set-pin [--old <CREDENTIAL>] [--user USER_ID] <PIN>");
            outPrintWriter.println("    Sets a secure lock screen that uses the given PIN.");
            outPrintWriter.println("");
            outPrintWriter.println("  set-password [--old <CREDENTIAL>] [--user USER_ID] <PASSWORD>");
            outPrintWriter.println("    Sets a secure lock screen that uses the given PASSWORD.");
            outPrintWriter.println("");
            outPrintWriter.println("  clear [--old <CREDENTIAL>] [--user USER_ID]");
            outPrintWriter.println("    Clears the lock credential.");
            outPrintWriter.println("");
            outPrintWriter.println("  verify [--old <CREDENTIAL>] [--user USER_ID]");
            outPrintWriter.println("    Verifies the lock credential.");
            outPrintWriter.println("");
            outPrintWriter.println("  remove-cache [--user USER_ID]");
            outPrintWriter.println("    Removes cached unified challenge for the managed profile.");
            outPrintWriter.println("");
            outPrintWriter.println("  set-resume-on-reboot-provider-package <package_name>");
            outPrintWriter.println("    Sets the package name for server based resume on reboot service provider.");
            outPrintWriter.println("");
            outPrintWriter.println("  require-strong-auth [--user USER_ID] <reason>");
            outPrintWriter.println("    Requires strong authentication. The current supported reasons:");
            outPrintWriter.println("    STRONG_AUTH_REQUIRED_AFTER_USER_LOCKDOWN.");
            outPrintWriter.println("");
            outPrintWriter.close();
        } catch (java.lang.Throwable th) {
            if (outPrintWriter != null) {
                try {
                    outPrintWriter.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    private void parseArgs() {
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                if ("--old".equals(nextOption)) {
                    this.mOld = getNextArgRequired();
                } else if ("--user".equals(nextOption)) {
                    this.mCurrentUserId = android.os.UserHandle.parseUserArg(getNextArgRequired());
                    if (this.mCurrentUserId == -2) {
                        this.mCurrentUserId = android.app.ActivityManager.getCurrentUser();
                    }
                } else {
                    getErrPrintWriter().println("Unknown option: " + nextOption);
                    throw new java.lang.IllegalArgumentException();
                }
            } else {
                this.mNew = getNextArg();
                return;
            }
        }
    }

    private com.android.internal.widget.LockscreenCredential getOldCredential() {
        if (android.text.TextUtils.isEmpty(this.mOld)) {
            return com.android.internal.widget.LockscreenCredential.createNone();
        }
        if (this.mLockPatternUtils.isLockPasswordEnabled(this.mCurrentUserId)) {
            if (com.android.internal.widget.LockPatternUtils.isQualityAlphabeticPassword(this.mLockPatternUtils.getKeyguardStoredPasswordQuality(this.mCurrentUserId))) {
                return com.android.internal.widget.LockscreenCredential.createPassword(this.mOld);
            }
            return com.android.internal.widget.LockscreenCredential.createPin(this.mOld);
        }
        if (this.mLockPatternUtils.isLockPatternEnabled(this.mCurrentUserId)) {
            byte lockPatternSize = this.mLockPatternUtils.getLockPatternSize(this.mCurrentUserId);
            return com.android.internal.widget.LockscreenCredential.createPattern(com.android.internal.widget.LockPatternUtils.byteArrayToPattern(this.mOld.getBytes(), lockPatternSize), lockPatternSize);
        }
        return com.android.internal.widget.LockscreenCredential.createPassword(this.mOld);
    }

    private boolean runSetPattern() {
        byte lockPatternSize = this.mLockPatternUtils.getLockPatternSize(this.mCurrentUserId);
        com.android.internal.widget.LockscreenCredential createPattern = com.android.internal.widget.LockscreenCredential.createPattern(com.android.internal.widget.LockPatternUtils.byteArrayToPattern(this.mNew.getBytes(), lockPatternSize), lockPatternSize);
        if (!isNewCredentialSufficient(createPattern)) {
            return false;
        }
        this.mLockPatternUtils.setLockCredential(createPattern, getOldCredential(), this.mCurrentUserId);
        getOutPrintWriter().println("Pattern set to '" + this.mNew + "'");
        return true;
    }

    private boolean runSetPassword() {
        com.android.internal.widget.LockscreenCredential createPassword = com.android.internal.widget.LockscreenCredential.createPassword(this.mNew);
        if (!isNewCredentialSufficient(createPassword)) {
            return false;
        }
        this.mLockPatternUtils.setLockCredential(createPassword, getOldCredential(), this.mCurrentUserId);
        getOutPrintWriter().println("Password set to '" + this.mNew + "'");
        return true;
    }

    private boolean runSetPin() {
        com.android.internal.widget.LockscreenCredential createPin = com.android.internal.widget.LockscreenCredential.createPin(this.mNew);
        if (!isNewCredentialSufficient(createPin)) {
            return false;
        }
        this.mLockPatternUtils.setLockCredential(createPin, getOldCredential(), this.mCurrentUserId);
        getOutPrintWriter().println("Pin set to '" + this.mNew + "'");
        return true;
    }

    private boolean runSetResumeOnRebootProviderPackage() {
        java.lang.String str = this.mNew;
        android.util.Slog.i("ShellCommand", "Setting persist.sys.resume_on_reboot_provider_package to " + str);
        this.mContext.enforcePermission("android.permission.BIND_RESUME_ON_REBOOT_SERVICE", this.mCallingPid, this.mCallingUid, "ShellCommand");
        android.os.SystemProperties.set("persist.sys.resume_on_reboot_provider_package", str);
        return true;
    }

    private boolean runRequireStrongAuth() {
        boolean z;
        java.lang.String str = this.mNew;
        switch (str.hashCode()) {
            case 1785592813:
                if (str.equals("STRONG_AUTH_REQUIRED_AFTER_USER_LOCKDOWN")) {
                    z = false;
                    break;
                }
            default:
                z = -1;
                break;
        }
        switch (z) {
            case false:
                this.mCurrentUserId = -1;
                this.mLockPatternUtils.requireStrongAuth(32, this.mCurrentUserId);
                getOutPrintWriter().println("Require strong auth for USER_ID " + this.mCurrentUserId + " because of " + this.mNew);
                return true;
            default:
                getErrPrintWriter().println("Unsupported reason: " + str);
                return false;
        }
    }

    private boolean runClear() {
        com.android.internal.widget.LockscreenCredential createNone = com.android.internal.widget.LockscreenCredential.createNone();
        if (!isNewCredentialSufficient(createNone)) {
            return false;
        }
        this.mLockPatternUtils.setLockCredential(createNone, getOldCredential(), this.mCurrentUserId);
        getOutPrintWriter().println("Lock credential cleared");
        return true;
    }

    private boolean isNewCredentialSufficient(com.android.internal.widget.LockscreenCredential lockscreenCredential) {
        java.util.List validateCredential = android.app.admin.PasswordMetrics.validateCredential(this.mLockPatternUtils.getRequestedPasswordMetrics(this.mCurrentUserId), this.mLockPatternUtils.getRequestedPasswordComplexity(this.mCurrentUserId), lockscreenCredential);
        if (!validateCredential.isEmpty()) {
            getOutPrintWriter().println("New credential doesn't satisfy admin policies: " + validateCredential.get(0));
            return false;
        }
        return true;
    }

    private void runSetDisabled() {
        boolean parseBoolean = java.lang.Boolean.parseBoolean(this.mNew);
        this.mLockPatternUtils.setLockScreenDisabled(parseBoolean, this.mCurrentUserId);
        getOutPrintWriter().println("Lock screen disabled set to " + parseBoolean);
    }

    private void runGetDisabled() {
        getOutPrintWriter().println(this.mLockPatternUtils.isLockScreenDisabled(this.mCurrentUserId));
    }

    private boolean checkCredential() {
        if (this.mLockPatternUtils.isSecure(this.mCurrentUserId)) {
            if (this.mLockPatternUtils.isManagedProfileWithUnifiedChallenge(this.mCurrentUserId)) {
                getOutPrintWriter().println("Profile uses unified challenge");
                return false;
            }
            try {
                boolean checkCredential = this.mLockPatternUtils.checkCredential(getOldCredential(), this.mCurrentUserId, (com.android.internal.widget.LockPatternUtils.CheckCredentialProgressCallback) null);
                if (!checkCredential) {
                    if (!this.mLockPatternUtils.isManagedProfileWithUnifiedChallenge(this.mCurrentUserId)) {
                        this.mLockPatternUtils.reportFailedPasswordAttempt(this.mCurrentUserId);
                    }
                    getOutPrintWriter().println("Old password '" + this.mOld + "' didn't match");
                } else {
                    this.mLockPatternUtils.reportSuccessfulPasswordAttempt(this.mCurrentUserId);
                }
                return checkCredential;
            } catch (com.android.internal.widget.LockPatternUtils.RequestThrottledException e) {
                getOutPrintWriter().println("Request throttled");
                return false;
            }
        }
        if (!this.mOld.isEmpty()) {
            getOutPrintWriter().println("Old password provided but user has no password");
            return false;
        }
        return true;
    }

    private void runRemoveCache() {
        this.mLockPatternUtils.removeCachedUnifiedChallenge(this.mCurrentUserId);
        getOutPrintWriter().println("Password cached removed for user " + this.mCurrentUserId);
    }
}
