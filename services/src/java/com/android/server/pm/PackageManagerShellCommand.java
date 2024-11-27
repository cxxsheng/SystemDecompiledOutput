package com.android.server.pm;

/* loaded from: classes2.dex */
class PackageManagerShellCommand extends android.os.ShellCommand {
    private static final java.lang.String ART_PROFILE_SNAPSHOT_DEBUG_LOCATION = "/data/misc/profman/";
    private static final java.util.Set<java.lang.String> ART_SERVICE_COMMANDS;
    private static final int DEFAULT_STAGED_READY_TIMEOUT_MS = 60000;
    private static final java.security.SecureRandom RANDOM;
    private static final java.lang.String STDIN_PATH = "-";
    private static final java.lang.String TAG = "PackageManagerShellCommand";
    boolean mBrief;
    boolean mComponents;
    final android.content.Context mContext;
    final com.android.server.pm.verify.domain.DomainVerificationShell mDomainVerificationShell;
    final android.content.pm.IPackageManager mInterface;
    final android.permission.PermissionManager mPermissionManager;
    int mQueryFlags;
    int mTargetUser;
    private static final java.util.Set<java.lang.String> UNSUPPORTED_INSTALL_CMD_OPTS = java.util.Set.of("--multi-package");
    private static final java.util.Set<java.lang.String> UNSUPPORTED_SESSION_CREATE_OPTS = java.util.Collections.emptySet();
    private static final java.util.Map<java.lang.String, java.lang.Integer> SUPPORTED_PERMISSION_FLAGS = new android.util.ArrayMap();
    private static final java.util.List<java.lang.String> SUPPORTED_PERMISSION_FLAGS_LIST = java.util.List.of("review-required", "revoked-compat", "revoke-when-requested", "user-fixed", "user-set");
    private final java.util.WeakHashMap<java.lang.String, android.content.res.Resources> mResourceCache = new java.util.WeakHashMap<>();
    private final android.content.pm.PackageManagerInternal mPm = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
    final com.android.server.pm.permission.LegacyPermissionManagerInternal mLegacyPermissionManager = (com.android.server.pm.permission.LegacyPermissionManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.permission.LegacyPermissionManagerInternal.class);

    private static class SessionDump {
        boolean onlyParent;
        boolean onlyReady;
        boolean onlySessionId;

        private SessionDump() {
        }
    }

    static {
        SUPPORTED_PERMISSION_FLAGS.put("user-set", 1);
        SUPPORTED_PERMISSION_FLAGS.put("user-fixed", 2);
        SUPPORTED_PERMISSION_FLAGS.put("revoked-compat", 8);
        SUPPORTED_PERMISSION_FLAGS.put("review-required", 64);
        SUPPORTED_PERMISSION_FLAGS.put("revoke-when-requested", 128);
        ART_SERVICE_COMMANDS = java.util.Set.of("compile", "reconcile-secondary-dex-files", "force-dex-opt", "bg-dexopt-job", "cancel-bg-dexopt-job", "delete-dexopt", "dump-profiles", "snapshot-profile", "art");
        RANDOM = new java.security.SecureRandom();
    }

    PackageManagerShellCommand(@android.annotation.NonNull android.content.pm.IPackageManager iPackageManager, @android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.pm.verify.domain.DomainVerificationShell domainVerificationShell) {
        this.mInterface = iPackageManager;
        this.mPermissionManager = (android.permission.PermissionManager) context.getSystemService(android.permission.PermissionManager.class);
        this.mContext = context;
        this.mDomainVerificationShell = domainVerificationShell;
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
                case -2102802879:
                    if (str.equals("set-harmful-app-warning")) {
                        c = 'C';
                        break;
                    }
                    c = 65535;
                    break;
                case -1987936121:
                    if (str.equals("wait-for-background-handler")) {
                        c = 'Q';
                        break;
                    }
                    c = 65535;
                    break;
                case -1967190973:
                    if (str.equals("install-abandon")) {
                        c = '\r';
                        break;
                    }
                    c = 65535;
                    break;
                case -1938648150:
                    if (str.equals("get-archived-package-metadata")) {
                        c = 29;
                        break;
                    }
                    c = 65535;
                    break;
                case -1937348290:
                    if (str.equals("get-install-location")) {
                        c = 21;
                        break;
                    }
                    c = 65535;
                    break;
                case -1867202415:
                    if (str.equals("install-set-pre-verified-domains")) {
                        c = 23;
                        break;
                    }
                    c = 65535;
                    break;
                case -1852006340:
                    if (str.equals("suspend")) {
                        c = '\'';
                        break;
                    }
                    c = 65535;
                    break;
                case -1846646502:
                    if (str.equals("get-max-running-users")) {
                        c = '>';
                        break;
                    }
                    c = 65535;
                    break;
                case -1838891168:
                    if (str.equals("clear-package-preferred-activities")) {
                        c = 'O';
                        break;
                    }
                    c = 65535;
                    break;
                case -1741208611:
                    if (str.equals("set-installer")) {
                        c = '@';
                        break;
                    }
                    c = 65535;
                    break;
                case -1534455582:
                    if (str.equals("set-silent-updates-policy")) {
                        c = 'M';
                        break;
                    }
                    c = 65535;
                    break;
                case -1445787154:
                    if (str.equals("wait-for-handler")) {
                        c = 'P';
                        break;
                    }
                    c = 65535;
                    break;
                case -1347307837:
                    if (str.equals("has-feature")) {
                        c = 'B';
                        break;
                    }
                    c = 65535;
                    break;
                case -1298848381:
                    if (str.equals("enable")) {
                        c = 31;
                        break;
                    }
                    c = 65535;
                    break;
                case -1267782244:
                    if (str.equals("get-instantapp-resolver")) {
                        c = 'A';
                        break;
                    }
                    c = 65535;
                    break;
                case -1231004208:
                    if (str.equals("resolve-activity")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case -1102348235:
                    if (str.equals("get-privapp-deny-permissions")) {
                        c = '3';
                        break;
                    }
                    c = 65535;
                    break;
                case -1091400553:
                    if (str.equals("get-oem-permissions")) {
                        c = '4';
                        break;
                    }
                    c = 65535;
                    break;
                case -1070704814:
                    if (str.equals("get-privapp-permissions")) {
                        c = '2';
                        break;
                    }
                    c = 65535;
                    break;
                case -1063121379:
                    if (str.equals("install-get-pre-verified-domains")) {
                        c = 24;
                        break;
                    }
                    c = 65535;
                    break;
                case -1032029296:
                    if (str.equals("disable-user")) {
                        c = '!';
                        break;
                    }
                    c = 65535;
                    break;
                case -999678881:
                    if (str.equals("suspend-quarantine")) {
                        c = '(';
                        break;
                    }
                    c = 65535;
                    break;
                case -944325712:
                    if (str.equals("set-distracting-restriction")) {
                        c = '*';
                        break;
                    }
                    c = 65535;
                    break;
                case -934343034:
                    if (str.equals("revoke")) {
                        c = '-';
                        break;
                    }
                    c = 65535;
                    break;
                case -905841467:
                    if (str.equals("get-domain-verification-agent")) {
                        c = 'T';
                        break;
                    }
                    c = 65535;
                    break;
                case -840566949:
                    if (str.equals("unhide")) {
                        c = '%';
                        break;
                    }
                    c = 65535;
                    break;
                case -840228325:
                    if (str.equals("unstop")) {
                        c = '&';
                        break;
                    }
                    c = 65535;
                    break;
                case -761393825:
                    if (str.equals("disable-verification-for-uid")) {
                        c = 'L';
                        break;
                    }
                    c = 65535;
                    break;
                case -748101438:
                    if (str.equals("archive")) {
                        c = 'R';
                        break;
                    }
                    c = 65535;
                    break;
                case -740352344:
                    if (str.equals("install-incremental")) {
                        c = '\f';
                        break;
                    }
                    c = 65535;
                    break;
                case -703497631:
                    if (str.equals("bypass-staged-installer-check")) {
                        c = 'J';
                        break;
                    }
                    c = 65535;
                    break;
                case -625596190:
                    if (str.equals("uninstall")) {
                        c = 27;
                        break;
                    }
                    c = 65535;
                    break;
                case -539710980:
                    if (str.equals("create-user")) {
                        c = '6';
                        break;
                    }
                    c = 65535;
                    break;
                case -458695741:
                    if (str.equals("query-services")) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case -440994401:
                    if (str.equals("query-receivers")) {
                        c = '\t';
                        break;
                    }
                    c = 65535;
                    break;
                case -416698598:
                    if (str.equals("get-stagedsessions")) {
                        c = 'E';
                        break;
                    }
                    c = 65535;
                    break;
                case -339687564:
                    if (str.equals("remove-user")) {
                        c = '7';
                        break;
                    }
                    c = 65535;
                    break;
                case -220055275:
                    if (str.equals("set-permission-enforced")) {
                        c = '1';
                        break;
                    }
                    c = 65535;
                    break;
                case -174281478:
                    if (str.equals("rename-user")) {
                        c = '9';
                        break;
                    }
                    c = 65535;
                    break;
                case -140205181:
                    if (str.equals("unsuspend")) {
                        c = ')';
                        break;
                    }
                    c = 65535;
                    break;
                case -132384343:
                    if (str.equals("install-commit")) {
                        c = 15;
                        break;
                    }
                    c = 65535;
                    break;
                case -129863314:
                    if (str.equals("install-create")) {
                        c = 16;
                        break;
                    }
                    c = 65535;
                    break;
                case -115000827:
                    if (str.equals("default-state")) {
                        c = '#';
                        break;
                    }
                    c = 65535;
                    break;
                case -87258188:
                    if (str.equals("move-primary-storage")) {
                        c = 26;
                        break;
                    }
                    c = 65535;
                    break;
                case -18738613:
                    if (str.equals("request-unarchive")) {
                        c = 'S';
                        break;
                    }
                    c = 65535;
                    break;
                case 3292:
                    if (str.equals("gc")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case 3095028:
                    if (str.equals("dump")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 3198785:
                    if (str.equals("help")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 3202370:
                    if (str.equals("hide")) {
                        c = '$';
                        break;
                    }
                    c = 65535;
                    break;
                case 3322014:
                    if (str.equals("list")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 3433509:
                    if (str.equals("path")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 18936394:
                    if (str.equals("move-package")) {
                        c = 25;
                        break;
                    }
                    c = 65535;
                    break;
                case 26877293:
                    if (str.equals("dump-package")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 86600360:
                    if (str.equals("get-max-users")) {
                        c = '=';
                        break;
                    }
                    c = 65535;
                    break;
                case 88069748:
                    if (str.equals("supports-multiple-users")) {
                        c = '<';
                        break;
                    }
                    c = 65535;
                    break;
                case 93657776:
                    if (str.equals("install-streaming")) {
                        c = 11;
                        break;
                    }
                    c = 65535;
                    break;
                case 94746189:
                    if (str.equals("clear")) {
                        c = 28;
                        break;
                    }
                    c = 65535;
                    break;
                case 98615580:
                    if (str.equals("grant")) {
                        c = ',';
                        break;
                    }
                    c = 65535;
                    break;
                case 107262333:
                    if (str.equals("install-existing")) {
                        c = 19;
                        break;
                    }
                    c = 65535;
                    break;
                case 139892533:
                    if (str.equals("get-harmful-app-warning")) {
                        c = 'D';
                        break;
                    }
                    c = 65535;
                    break;
                case 237392952:
                    if (str.equals("install-add-session")) {
                        c = 22;
                        break;
                    }
                    c = 65535;
                    break;
                case 287820022:
                    if (str.equals("install-remove")) {
                        c = 17;
                        break;
                    }
                    c = 65535;
                    break;
                case 359572742:
                    if (str.equals("reset-permissions")) {
                        c = '.';
                        break;
                    }
                    c = 65535;
                    break;
                case 377019320:
                    if (str.equals("rollback-app")) {
                        c = 'G';
                        break;
                    }
                    c = 65535;
                    break;
                case 401207972:
                    if (str.equals("get-distracting-restriction")) {
                        c = '+';
                        break;
                    }
                    c = 65535;
                    break;
                case 513651668:
                    if (str.equals("install-archived")) {
                        c = 30;
                        break;
                    }
                    c = 65535;
                    break;
                case 798023112:
                    if (str.equals("install-destroy")) {
                        c = 14;
                        break;
                    }
                    c = 65535;
                    break;
                case 826473335:
                    if (str.equals("uninstall-system-updates")) {
                        c = 'F';
                        break;
                    }
                    c = 65535;
                    break;
                case 925176533:
                    if (str.equals("set-user-restriction")) {
                        c = ':';
                        break;
                    }
                    c = 65535;
                    break;
                case 1053409810:
                    if (str.equals("query-activities")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 1177857340:
                    if (str.equals("trim-caches")) {
                        c = '5';
                        break;
                    }
                    c = 65535;
                    break;
                case 1396442249:
                    if (str.equals("clear-permission-flags")) {
                        c = '0';
                        break;
                    }
                    c = 65535;
                    break;
                case 1429366290:
                    if (str.equals("set-home-activity")) {
                        c = '?';
                        break;
                    }
                    c = 65535;
                    break;
                case 1536099937:
                    if (str.equals("get-user-restriction")) {
                        c = ';';
                        break;
                    }
                    c = 65535;
                    break;
                case 1538306349:
                    if (str.equals("install-write")) {
                        c = 18;
                        break;
                    }
                    c = 65535;
                    break;
                case 1585252978:
                    if (str.equals("get-app-metadata")) {
                        c = 'N';
                        break;
                    }
                    c = 65535;
                    break;
                case 1661039911:
                    if (str.equals("mark-guest-for-deletion")) {
                        c = '8';
                        break;
                    }
                    c = 65535;
                    break;
                case 1671308008:
                    if (str.equals("disable")) {
                        c = ' ';
                        break;
                    }
                    c = 65535;
                    break;
                case 1697997009:
                    if (str.equals("disable-until-used")) {
                        c = '\"';
                        break;
                    }
                    c = 65535;
                    break;
                case 1738820372:
                    if (str.equals("set-permission-flags")) {
                        c = '/';
                        break;
                    }
                    c = 65535;
                    break;
                case 1746695602:
                    if (str.equals("set-install-location")) {
                        c = 20;
                        break;
                    }
                    c = 65535;
                    break;
                case 1757370437:
                    if (str.equals("bypass-allowed-apex-update-check")) {
                        c = 'K';
                        break;
                    }
                    c = 65535;
                    break;
                case 1824799035:
                    if (str.equals("log-visibility")) {
                        c = 'I';
                        break;
                    }
                    c = 65535;
                    break;
                case 1858863089:
                    if (str.equals("get-moduleinfo")) {
                        c = 'H';
                        break;
                    }
                    c = 65535;
                    break;
                case 1957569947:
                    if (str.equals("install")) {
                        c = '\n';
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
                    onHelp();
                    return 0;
                case 1:
                    return runPath();
                case 2:
                    return runDump();
                case 3:
                    return runDumpPackage();
                case 4:
                    return runList();
                case 5:
                    return runGc();
                case 6:
                    return runResolveActivity();
                case 7:
                    return runQueryIntentActivities();
                case '\b':
                    return runQueryIntentServices();
                case '\t':
                    return runQueryIntentReceivers();
                case '\n':
                    return runInstall();
                case 11:
                    return runStreamingInstall();
                case '\f':
                    return runIncrementalInstall();
                case '\r':
                case 14:
                    return runInstallAbandon();
                case 15:
                    return runInstallCommit();
                case 16:
                    return runInstallCreate();
                case 17:
                    return runInstallRemove();
                case 18:
                    return runInstallWrite();
                case 19:
                    return runInstallExisting();
                case 20:
                    return runSetInstallLocation();
                case 21:
                    return runGetInstallLocation();
                case 22:
                    return runInstallAddSession();
                case 23:
                    return runInstallSetPreVerifiedDomains();
                case 24:
                    return runInstallGetPreVerifiedDomains();
                case 25:
                    return runMovePackage();
                case 26:
                    return runMovePrimaryStorage();
                case 27:
                    return runUninstall();
                case 28:
                    return runClear();
                case 29:
                    return runGetArchivedPackageMetadata();
                case 30:
                    return runArchivedInstall();
                case 31:
                    return runSetEnabledSetting(1);
                case ' ':
                    return runSetEnabledSetting(2);
                case '!':
                    return runSetEnabledSetting(3);
                case '\"':
                    return runSetEnabledSetting(4);
                case '#':
                    return runSetEnabledSetting(0);
                case '$':
                    return runSetHiddenSetting(true);
                case '%':
                    return runSetHiddenSetting(false);
                case '&':
                    return runSetStoppedState(false);
                case '\'':
                    return runSuspend(true, 0);
                case '(':
                    return runSuspend(true, 1);
                case ')':
                    return runSuspend(false, 0);
                case '*':
                    return runSetDistractingRestriction();
                case '+':
                    return runGetDistractingRestriction();
                case ',':
                    return runGrantRevokePermission(true);
                case '-':
                    return runGrantRevokePermission(false);
                case '.':
                    return runResetPermissions();
                case '/':
                    return setOrClearPermissionFlags(true);
                case '0':
                    return setOrClearPermissionFlags(false);
                case '1':
                    return runSetPermissionEnforced();
                case '2':
                    return runGetPrivappPermissions();
                case '3':
                    return runGetPrivappDenyPermissions();
                case '4':
                    return runGetOemPermissions();
                case '5':
                    return runTrimCaches();
                case '6':
                    return runCreateUser();
                case '7':
                    return runRemoveUser();
                case '8':
                    return runMarkGuestForDeletion();
                case '9':
                    return runRenameUser();
                case ':':
                    return runSetUserRestriction();
                case ';':
                    return runGetUserRestriction();
                case '<':
                    return runSupportsMultipleUsers();
                case '=':
                    return runGetMaxUsers();
                case '>':
                    return runGetMaxRunningUsers();
                case '?':
                    return runSetHomeActivity();
                case '@':
                    return runSetInstaller();
                case 'A':
                    return runGetInstantAppResolver();
                case 'B':
                    return runHasFeature();
                case 'C':
                    return runSetHarmfulAppWarning();
                case 'D':
                    return runGetHarmfulAppWarning();
                case 'E':
                    return runListStagedSessions();
                case 'F':
                    return uninstallSystemUpdates(getNextArg());
                case 'G':
                    return runRollbackApp();
                case 'H':
                    return runGetModuleInfo();
                case 'I':
                    return runLogVisibility();
                case 'J':
                    return runBypassStagedInstallerCheck();
                case 'K':
                    return runBypassAllowedApexUpdateCheck();
                case 'L':
                    return runDisableVerificationForUid();
                case 'M':
                    return runSetSilentUpdatesPolicy();
                case 'N':
                    return runGetAppMetadata();
                case 'O':
                    return runClearPackagePreferredActivities();
                case 'P':
                    return runWaitForHandler(false);
                case 'Q':
                    return runWaitForHandler(true);
                case 'R':
                    return runArchive();
                case 'S':
                    return runUnarchive();
                case 'T':
                    return runGetDomainVerificationAgent();
                default:
                    if (ART_SERVICE_COMMANDS.contains(str)) {
                        if (com.android.server.pm.DexOptHelper.useArtService()) {
                            return runArtServiceCommand();
                        }
                        try {
                            return runLegacyDexoptCommand(str);
                        } catch (com.android.server.pm.Installer.LegacyDexoptDisabledException e) {
                            throw new java.lang.RuntimeException(e);
                        }
                    }
                    java.lang.Boolean runCommand = this.mDomainVerificationShell.runCommand(this, str);
                    if (runCommand != null) {
                        return !runCommand.booleanValue() ? 1 : 0;
                    }
                    java.lang.String nextArg = getNextArg();
                    if (nextArg == null) {
                        if (str.equalsIgnoreCase("-l")) {
                            return runListPackages(false);
                        }
                        if (str.equalsIgnoreCase("-lf")) {
                            return runListPackages(true);
                        }
                    } else if (getNextArg() == null && str.equalsIgnoreCase("-p")) {
                        return displayPackageFilePath(nextArg, 0);
                    }
                    return handleDefaultCommands(str);
            }
        } catch (android.os.RemoteException e2) {
            outPrintWriter.println("Remote exception: " + e2);
            return -1;
        }
        outPrintWriter.println("Remote exception: " + e2);
        return -1;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int runLegacyDexoptCommand(@android.annotation.NonNull java.lang.String str) throws android.os.RemoteException, com.android.server.pm.Installer.LegacyDexoptDisabledException {
        char c;
        com.android.server.pm.Installer.checkLegacyDexoptDisabled();
        if (!com.android.server.pm.PackageManagerServiceUtils.isRootOrShell(android.os.Binder.getCallingUid())) {
            throw new java.lang.SecurityException("Dexopt shell commands need root or shell access");
        }
        switch (str.hashCode()) {
            case -1921557090:
                if (str.equals("delete-dexopt")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -1440979423:
                if (str.equals("cancel-bg-dexopt-job")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -919935069:
                if (str.equals("dump-profiles")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case -444750796:
                if (str.equals("bg-dexopt-job")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 96867:
                if (str.equals("art")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case 467549856:
                if (str.equals("snapshot-profile")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 950491699:
                if (str.equals("compile")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 1124603675:
                if (str.equals("force-dex-opt")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 1783979817:
                if (str.equals("reconcile-secondary-dex-files")) {
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
                return runCompile();
            case 1:
                return runreconcileSecondaryDexFiles();
            case 2:
                return runForceDexOpt();
            case 3:
                return runBgDexOpt();
            case 4:
                return cancelBgDexOptJob();
            case 5:
                return runDeleteDexOpt();
            case 6:
                return runDumpProfiles();
            case 7:
                return runSnapshotProfile();
            case '\b':
                getOutPrintWriter().println("ART Service not enabled");
                return -1;
            default:
                throw new java.lang.IllegalArgumentException();
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x001c, code lost:
    
        if (r3.equals("--installed") != false) goto L15;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int runGetModuleInfo() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        int i = 0;
        while (true) {
            java.lang.String nextOption = getNextOption();
            boolean z = true;
            if (nextOption != null) {
                switch (nextOption.hashCode()) {
                    case 42995713:
                        if (nextOption.equals("--all")) {
                            z = false;
                            break;
                        }
                        z = -1;
                        break;
                    case 517440986:
                        break;
                    default:
                        z = -1;
                        break;
                }
                switch (z) {
                    case false:
                        i = 131072;
                        break;
                    case true:
                        break;
                    default:
                        outPrintWriter.println("Error: Unknown option: " + nextOption);
                        return -1;
                }
            } else {
                java.lang.String nextArg = getNextArg();
                try {
                    if (nextArg != null) {
                        android.content.pm.ModuleInfo moduleInfo = this.mInterface.getModuleInfo(nextArg, i);
                        outPrintWriter.println(moduleInfo.toString() + " packageName: " + moduleInfo.getPackageName());
                    } else {
                        for (android.content.pm.ModuleInfo moduleInfo2 : this.mInterface.getInstalledModules(i)) {
                            outPrintWriter.println(moduleInfo2.toString() + " packageName: " + moduleInfo2.getPackageName());
                        }
                    }
                    return 1;
                } catch (android.os.RemoteException e) {
                    outPrintWriter.println("Failure [" + e.getClass().getName() + " - " + e.getMessage() + "]");
                    return -1;
                }
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int runLogVisibility() {
        boolean z;
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        boolean z2 = true;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                switch (nextOption.hashCode()) {
                    case -1237677752:
                        if (nextOption.equals("--disable")) {
                            z = false;
                            break;
                        }
                        z = -1;
                        break;
                    case 1101165347:
                        if (nextOption.equals("--enable")) {
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
                        z2 = false;
                        break;
                    case true:
                        z2 = true;
                        break;
                    default:
                        outPrintWriter.println("Error: Unknown option: " + nextOption);
                        return -1;
                }
            } else {
                java.lang.String nextArg = getNextArg();
                if (nextArg != null) {
                    ((android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class)).setVisibilityLogging(nextArg, z2);
                    return 1;
                }
                getErrPrintWriter().println("Error: no package specified");
                return -1;
            }
        }
    }

    private int runBypassStagedInstallerCheck() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        try {
            this.mInterface.getPackageInstaller().bypassNextStagedInstallerCheck(java.lang.Boolean.parseBoolean(getNextArg()));
            return 0;
        } catch (android.os.RemoteException e) {
            outPrintWriter.println("Failure [" + e.getClass().getName() + " - " + e.getMessage() + "]");
            return -1;
        }
    }

    private int runBypassAllowedApexUpdateCheck() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        try {
            this.mInterface.getPackageInstaller().bypassNextAllowedApexUpdateCheck(java.lang.Boolean.parseBoolean(getNextArg()));
            return 0;
        } catch (android.os.RemoteException e) {
            outPrintWriter.println("Failure [" + e.getClass().getName() + " - " + e.getMessage() + "]");
            return -1;
        }
    }

    private int runDisableVerificationForUid() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        try {
            int parseInt = java.lang.Integer.parseInt(getNextArgRequired());
            if (((android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class)).getInstrumentationSourceUid(parseInt) != -1) {
                this.mInterface.getPackageInstaller().disableVerificationForUid(parseInt);
                return 0;
            }
            outPrintWriter.println("Error: must specify an instrumented uid");
            return -1;
        } catch (android.os.RemoteException e) {
            outPrintWriter.println("Failure [" + e.getClass().getName() + " - " + e.getMessage() + "]");
            return -1;
        }
    }

    private int uninstallSystemUpdates(java.lang.String str) {
        java.util.List<android.content.pm.ApplicationInfo> list;
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        try {
            android.content.pm.IPackageInstaller packageInstaller = this.mInterface.getPackageInstaller();
            if (str == null) {
                list = this.mInterface.getInstalledApplications(1056768L, 0).getList();
            } else {
                java.util.ArrayList arrayList = new java.util.ArrayList(1);
                arrayList.add(this.mInterface.getApplicationInfo(str, 1056768L, 0));
                list = arrayList;
            }
            boolean z = false;
            for (android.content.pm.ApplicationInfo applicationInfo : list) {
                if (applicationInfo.isUpdatedSystemApp()) {
                    outPrintWriter.println("Uninstalling updates to " + applicationInfo.packageName + "...");
                    com.android.server.pm.PackageManagerShellCommand.LocalIntentReceiver localIntentReceiver = new com.android.server.pm.PackageManagerShellCommand.LocalIntentReceiver();
                    packageInstaller.uninstall(new android.content.pm.VersionedPackage(applicationInfo.packageName, applicationInfo.versionCode), (java.lang.String) null, 0, localIntentReceiver.getIntentSender(), 0);
                    if (localIntentReceiver.getResult().getIntExtra("android.content.pm.extra.STATUS", 1) != 0) {
                        outPrintWriter.println("Couldn't uninstall package: " + applicationInfo.packageName);
                        z = true;
                    }
                }
            }
            if (z) {
                return 0;
            }
            outPrintWriter.println("Success");
            return 1;
        } catch (android.os.RemoteException e) {
            outPrintWriter.println("Failure [" + e.getClass().getName() + " - " + e.getMessage() + "]");
            return 0;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x001c, code lost:
    
        if (r3.equals("--staged-ready-timeout") != false) goto L12;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int runRollbackApp() throws android.os.RemoteException {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        long j = 60000;
        while (true) {
            java.lang.String nextOption = getNextOption();
            char c = 0;
            if (nextOption != null) {
                switch (nextOption.hashCode()) {
                    case -158482320:
                        break;
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                        j = java.lang.Long.parseLong(getNextArgRequired());
                    default:
                        throw new java.lang.IllegalArgumentException("Unknown option: " + nextOption);
                }
            } else {
                java.lang.String nextArgRequired = getNextArgRequired();
                if (nextArgRequired == null) {
                    outPrintWriter.println("Error: package name not specified");
                    return 1;
                }
                try {
                    android.content.Context createPackageContextAsUser = this.mContext.createPackageContextAsUser(com.android.server.vibrator.VibratorManagerService.VibratorManagerShellCommand.SHELL_PACKAGE_NAME, 0, android.os.Binder.getCallingUserHandle());
                    android.content.rollback.RollbackInfo rollbackInfo = null;
                    com.android.server.pm.PackageManagerShellCommand.LocalIntentReceiver localIntentReceiver = new com.android.server.pm.PackageManagerShellCommand.LocalIntentReceiver();
                    android.content.rollback.RollbackManager rollbackManager = (android.content.rollback.RollbackManager) createPackageContextAsUser.getSystemService(android.content.rollback.RollbackManager.class);
                    for (android.content.rollback.RollbackInfo rollbackInfo2 : rollbackManager.getAvailableRollbacks()) {
                        java.util.Iterator it = rollbackInfo2.getPackages().iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            if (nextArgRequired.equals(((android.content.rollback.PackageRollbackInfo) it.next()).getPackageName())) {
                                rollbackInfo = rollbackInfo2;
                            }
                        }
                    }
                    if (rollbackInfo == null) {
                        outPrintWriter.println("No available rollbacks for: " + nextArgRequired);
                        return 1;
                    }
                    rollbackManager.commitRollback(rollbackInfo.getRollbackId(), java.util.Collections.emptyList(), localIntentReceiver.getIntentSender());
                    android.content.Intent result = localIntentReceiver.getResult();
                    if (result.getIntExtra("android.content.rollback.extra.STATUS", 1) != 0) {
                        outPrintWriter.println("Failure [" + result.getStringExtra("android.content.rollback.extra.STATUS_MESSAGE") + "]");
                        return 1;
                    }
                    if (rollbackInfo.isStaged() && j > 0) {
                        return doWaitForStagedSessionReady(rollbackInfo.getCommittedSessionId(), j, outPrintWriter);
                    }
                    outPrintWriter.println("Success");
                    return 0;
                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                    throw new java.lang.RuntimeException(e);
                }
            }
        }
    }

    private void setParamsSize(com.android.server.pm.PackageManagerShellCommand.InstallParams installParams, java.util.List<java.lang.String> list) {
        if (installParams.sessionParams.sizeBytes != -1 || STDIN_PATH.equals(list.get(0))) {
            return;
        }
        android.content.pm.parsing.result.ParseTypeImpl forDefaultParsing = android.content.pm.parsing.result.ParseTypeImpl.forDefaultParsing();
        long j = 0;
        for (java.lang.String str : list) {
            android.os.ParcelFileDescriptor openFileForSystem = openFileForSystem(str, com.android.server.wm.ActivityTaskManagerService.DUMP_RECENTS_SHORT_CMD);
            if (openFileForSystem == null) {
                getErrPrintWriter().println("Error: Can't open file: " + str);
                throw new java.lang.IllegalArgumentException("Error: Can't open file: " + str);
            }
            try {
                try {
                    android.content.pm.parsing.result.ParseResult parseApkLite = android.content.pm.parsing.ApkLiteParseUtils.parseApkLite(forDefaultParsing.reset(), openFileForSystem.getFileDescriptor(), str, 0);
                    if (parseApkLite.isError()) {
                        throw new java.lang.IllegalArgumentException("Error: Failed to parse APK file: " + str + ": " + parseApkLite.getErrorMessage(), parseApkLite.getException());
                    }
                    android.content.pm.parsing.ApkLite apkLite = (android.content.pm.parsing.ApkLite) parseApkLite.getResult();
                    j += com.android.internal.content.InstallLocationUtils.calculateInstalledSize(new android.content.pm.parsing.PackageLite((java.lang.String) null, apkLite.getPath(), apkLite, (java.lang.String[]) null, (boolean[]) null, (java.lang.String[]) null, (java.lang.String[]) null, (java.lang.String[]) null, (int[]) null, apkLite.getTargetSdkVersion(), (java.util.Set[]) null, (java.util.Set[]) null), installParams.sessionParams.abiOverride, openFileForSystem.getFileDescriptor());
                    try {
                        openFileForSystem.close();
                    } catch (java.io.IOException e) {
                    }
                } finally {
                }
            } catch (java.io.IOException e2) {
                getErrPrintWriter().println("Error: Failed to parse APK file: " + str);
                throw new java.lang.IllegalArgumentException("Error: Failed to parse APK file: " + str, e2);
            }
        }
        installParams.sessionParams.setSize(j);
    }

    private int displayPackageFilePath(java.lang.String str, int i) throws android.os.RemoteException {
        android.content.pm.PackageInfo packageInfo = this.mInterface.getPackageInfo(str, 1073741824L, i);
        if (packageInfo != null && packageInfo.applicationInfo != null) {
            java.io.PrintWriter outPrintWriter = getOutPrintWriter();
            outPrintWriter.print("package:");
            outPrintWriter.println(packageInfo.applicationInfo.sourceDir);
            if (!com.android.internal.util.ArrayUtils.isEmpty(packageInfo.applicationInfo.splitSourceDirs)) {
                for (java.lang.String str2 : packageInfo.applicationInfo.splitSourceDirs) {
                    outPrintWriter.print("package:");
                    outPrintWriter.println(str2);
                }
            }
            return 0;
        }
        return 1;
    }

    private int runPath() throws android.os.RemoteException {
        int i;
        java.lang.String nextOption = getNextOption();
        if (nextOption != null && nextOption.equals("--user")) {
            i = android.os.UserHandle.parseUserArg(getNextArgRequired());
        } else {
            i = 0;
        }
        java.lang.String nextArgRequired = getNextArgRequired();
        if (nextArgRequired == null) {
            getErrPrintWriter().println("Error: no package specified");
            return 1;
        }
        return displayPackageFilePath(nextArgRequired, translateUserId(i, com.android.server.am.ProcessList.INVALID_ADJ, "runPath"));
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int runList() throws android.os.RemoteException {
        char c;
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        java.lang.String nextArg = getNextArg();
        if (nextArg != null) {
            switch (nextArg.hashCode()) {
                case -1126096540:
                    if (nextArg.equals("staged-sessions")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case -997447790:
                    if (nextArg.equals("permission-groups")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case -807062458:
                    if (nextArg.equals(com.android.server.pm.Settings.ATTR_PACKAGE)) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case -290659267:
                    if (nextArg.equals("features")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 3525497:
                    if (nextArg.equals("sdks")) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case 111578632:
                    if (nextArg.equals(com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_USERS)) {
                        c = '\t';
                        break;
                    }
                    c = 65535;
                    break;
                case 544550766:
                    if (nextArg.equals("instrumentation")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 750867693:
                    if (nextArg.equals("packages")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 812757657:
                    if (nextArg.equals("libraries")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 1133704324:
                    if (nextArg.equals("permissions")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case 1786251458:
                    if (nextArg.equals("initial-non-stopped-system-packages")) {
                        c = '\n';
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
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case '\b':
                    break;
                case '\t':
                    android.os.ServiceManager.getService("user").shellCommand(getInFileDescriptor(), getOutFileDescriptor(), getErrFileDescriptor(), new java.lang.String[]{"list"}, getShellCallback(), adoptResultReceiver());
                    break;
                case '\n':
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

    private int runGc() throws android.os.RemoteException {
        java.lang.Runtime.getRuntime().gc();
        getOutPrintWriter().println("Ok");
        return 0;
    }

    private int runListInitialNonStoppedSystemPackages() throws android.os.RemoteException {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        java.util.List<java.lang.String> initialNonStoppedSystemPackages = this.mInterface.getInitialNonStoppedSystemPackages();
        java.util.Collections.sort(initialNonStoppedSystemPackages);
        for (java.lang.String str : initialNonStoppedSystemPackages) {
            outPrintWriter.print("package:");
            outPrintWriter.print(str);
            outPrintWriter.println();
        }
        return 0;
    }

    private int runListFeatures() throws android.os.RemoteException {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        java.util.List list = this.mInterface.getSystemAvailableFeatures().getList();
        java.util.Collections.sort(list, new java.util.Comparator<android.content.pm.FeatureInfo>() { // from class: com.android.server.pm.PackageManagerShellCommand.1
            @Override // java.util.Comparator
            public int compare(android.content.pm.FeatureInfo featureInfo, android.content.pm.FeatureInfo featureInfo2) {
                if (featureInfo.name == featureInfo2.name) {
                    return 0;
                }
                if (featureInfo.name == null) {
                    return -1;
                }
                if (featureInfo2.name == null) {
                    return 1;
                }
                return featureInfo.name.compareTo(featureInfo2.name);
            }
        });
        int size = list != null ? list.size() : 0;
        for (int i = 0; i < size; i++) {
            android.content.pm.FeatureInfo featureInfo = (android.content.pm.FeatureInfo) list.get(i);
            outPrintWriter.print("feature:");
            if (featureInfo.name != null) {
                outPrintWriter.print(featureInfo.name);
                if (featureInfo.version > 0) {
                    outPrintWriter.print("=");
                    outPrintWriter.print(featureInfo.version);
                }
                outPrintWriter.println();
            } else {
                outPrintWriter.println("reqGlEsVersion=0x" + java.lang.Integer.toHexString(featureInfo.reqGlEsVersion));
            }
        }
        return 0;
    }

    private int runListInstrumentation() throws android.os.RemoteException {
        java.lang.String nextArg;
        boolean z;
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        java.lang.String str = null;
        boolean z2 = false;
        while (true) {
            try {
                nextArg = getNextArg();
            } catch (java.lang.RuntimeException e) {
                outPrintWriter.println("Error: " + e.toString());
                return -1;
            }
            if (nextArg != null) {
                switch (nextArg.hashCode()) {
                    case 1497:
                        if (nextArg.equals("-f")) {
                            z = false;
                            break;
                        }
                    default:
                        z = -1;
                        break;
                }
                switch (z) {
                    case false:
                        z2 = true;
                        continue;
                    default:
                        if (nextArg.charAt(0) == '-') {
                            outPrintWriter.println("Error: Unknown option: " + nextArg);
                            return -1;
                        }
                        str = nextArg;
                        continue;
                }
                outPrintWriter.println("Error: " + e.toString());
                return -1;
            }
            java.util.List list = this.mInterface.queryInstrumentationAsUser(str, 4202496, 0).getList();
            java.util.Collections.sort(list, new java.util.Comparator<android.content.pm.InstrumentationInfo>() { // from class: com.android.server.pm.PackageManagerShellCommand.2
                @Override // java.util.Comparator
                public int compare(android.content.pm.InstrumentationInfo instrumentationInfo, android.content.pm.InstrumentationInfo instrumentationInfo2) {
                    return instrumentationInfo.targetPackage.compareTo(instrumentationInfo2.targetPackage);
                }
            });
            int size = list != null ? list.size() : 0;
            for (int i = 0; i < size; i++) {
                android.content.pm.InstrumentationInfo instrumentationInfo = (android.content.pm.InstrumentationInfo) list.get(i);
                outPrintWriter.print("instrumentation:");
                if (z2) {
                    outPrintWriter.print(instrumentationInfo.sourceDir);
                    outPrintWriter.print("=");
                }
                outPrintWriter.print(new android.content.ComponentName(instrumentationInfo.packageName, instrumentationInfo.name).flattenToShortString());
                outPrintWriter.print(" (target=");
                outPrintWriter.print(instrumentationInfo.targetPackage);
                outPrintWriter.println(")");
            }
            return 0;
        }
    }

    private int runListLibraries() throws android.os.RemoteException {
        boolean z;
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        boolean z2 = false;
        while (true) {
            java.lang.String nextArg = getNextArg();
            if (nextArg != null) {
                switch (nextArg.hashCode()) {
                    case 1513:
                        if (nextArg.equals("-v")) {
                            z = false;
                            break;
                        }
                    default:
                        z = -1;
                        break;
                }
                switch (z) {
                    case false:
                        z2 = true;
                    default:
                        outPrintWriter.println("Error: Unknown option: " + nextArg);
                        return -1;
                }
            } else {
                java.util.Map systemSharedLibraryNamesAndPaths = this.mInterface.getSystemSharedLibraryNamesAndPaths();
                if (systemSharedLibraryNamesAndPaths.isEmpty()) {
                    return 0;
                }
                java.util.ArrayList arrayList = new java.util.ArrayList(systemSharedLibraryNamesAndPaths.keySet());
                java.util.Collections.sort(arrayList, new java.util.Comparator() { // from class: com.android.server.pm.PackageManagerShellCommand$$ExternalSyntheticLambda2
                    @Override // java.util.Comparator
                    public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                        int lambda$runListLibraries$0;
                        lambda$runListLibraries$0 = com.android.server.pm.PackageManagerShellCommand.lambda$runListLibraries$0((java.lang.String) obj, (java.lang.String) obj2);
                        return lambda$runListLibraries$0;
                    }
                });
                for (int i = 0; i < arrayList.size(); i++) {
                    java.lang.String str = (java.lang.String) arrayList.get(i);
                    outPrintWriter.print("library:");
                    outPrintWriter.print(str);
                    if (z2) {
                        outPrintWriter.print(" path:");
                        outPrintWriter.print((java.lang.String) systemSharedLibraryNamesAndPaths.get(str));
                    }
                    outPrintWriter.println();
                }
                return 0;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$runListLibraries$0(java.lang.String str, java.lang.String str2) {
        if (str == str2) {
            return 0;
        }
        if (str == null) {
            return -1;
        }
        if (str2 == null) {
            return 1;
        }
        return str.compareTo(str2);
    }

    private int runListPackages(boolean z) throws android.os.RemoteException {
        return runListPackages(z, false);
    }

    private int runListSdks() throws android.os.RemoteException {
        return runListPackages(false, true);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0112 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:160:0x0233  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x0243 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:174:0x0260 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:178:0x0270  */
    /* JADX WARN: Removed duplicated region for block: B:183:0x0289  */
    /* JADX WARN: Removed duplicated region for block: B:199:0x0302  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0128 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:202:0x0313  */
    /* JADX WARN: Removed duplicated region for block: B:207:0x032d  */
    /* JADX WARN: Removed duplicated region for block: B:213:0x0346  */
    /* JADX WARN: Removed duplicated region for block: B:216:0x036a A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:220:0x0377 A[ADDED_TO_REGION, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:223:0x02f3  */
    /* JADX WARN: Removed duplicated region for block: B:225:0x023e  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x012c A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x012f A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x013b A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0144 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0147 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x014d A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0151 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0154 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0157 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x015b A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:53:0x015e A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0162 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0166 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0169 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0172 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0175 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0030 A[SYNTHETIC] */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int runListPackages(boolean z, boolean z2) throws android.os.RemoteException {
        java.lang.String nextOption;
        java.lang.String str;
        java.lang.String str2;
        int i;
        boolean z3;
        int i2;
        int i3;
        java.util.HashMap hashMap;
        java.lang.String str3;
        int i4;
        java.lang.String str4;
        boolean z4;
        boolean z5;
        int i5;
        boolean z6;
        int i6;
        int i7;
        long j;
        java.lang.String str5;
        java.lang.String str6;
        java.util.HashMap hashMap2;
        java.util.List list;
        char c;
        java.lang.String str7 = z2 ? "sdk:" : "package:";
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        boolean z7 = z;
        int i8 = -1;
        int i9 = 0;
        int i10 = -1;
        boolean z8 = false;
        boolean z9 = false;
        boolean z10 = false;
        boolean z11 = false;
        boolean z12 = false;
        boolean z13 = false;
        boolean z14 = false;
        boolean z15 = false;
        boolean z16 = false;
        boolean z17 = false;
        while (true) {
            try {
                nextOption = getNextOption();
            } catch (java.lang.RuntimeException e) {
                outPrintWriter.println("Error: " + e.toString());
                return -1;
            }
            if (nextOption != null) {
                switch (nextOption.hashCode()) {
                    case -493830763:
                        if (nextOption.equals("--show-versioncode")) {
                            c = 11;
                            switch (c) {
                                case 0:
                                    z8 = true;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                case 1:
                                    z9 = true;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                case 2:
                                    i9 = 4202496 | i9 | 536870912;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                case 3:
                                    z7 = true;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                case 4:
                                    z16 = true;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                case 5:
                                case 6:
                                    z10 = true;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                case 7:
                                    z13 = true;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                case '\b':
                                    z17 = true;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                case '\t':
                                    i9 |= 8192;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                case '\n':
                                    z11 = true;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                case 11:
                                    z14 = true;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                case '\f':
                                    i9 |= 1073741824;
                                    z12 = true;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                case '\r':
                                    i9 |= 2097152;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                case 14:
                                    i8 = android.os.UserHandle.parseUserArg(getNextArgRequired());
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                case 15:
                                    i10 = java.lang.Integer.parseInt(getNextArgRequired());
                                    z17 = true;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                case 16:
                                    i9 |= 67108864;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                case 17:
                                    z15 = true;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                    continue;
                                default:
                                    outPrintWriter.println("Error: Unknown option: " + nextOption);
                                    return -1;
                            }
                            outPrintWriter.println("Error: " + e.toString());
                            return -1;
                        }
                        c = 65535;
                        switch (c) {
                        }
                        outPrintWriter.println("Error: " + e.toString());
                        return -1;
                    case 1446:
                        if (nextOption.equals("-3")) {
                            c = '\n';
                            switch (c) {
                            }
                            outPrintWriter.println("Error: " + e.toString());
                            return -1;
                        }
                        c = 65535;
                        switch (c) {
                        }
                        outPrintWriter.println("Error: " + e.toString());
                        return -1;
                    case 1480:
                        if (nextOption.equals("-U")) {
                            c = '\b';
                            switch (c) {
                            }
                            outPrintWriter.println("Error: " + e.toString());
                            return -1;
                        }
                        c = 65535;
                        switch (c) {
                        }
                        outPrintWriter.println("Error: " + e.toString());
                        return -1;
                    case 1492:
                        if (nextOption.equals("-a")) {
                            c = 2;
                            switch (c) {
                            }
                            outPrintWriter.println("Error: " + e.toString());
                            return -1;
                        }
                        c = 65535;
                        switch (c) {
                        }
                        outPrintWriter.println("Error: " + e.toString());
                        return -1;
                    case 1495:
                        if (nextOption.equals("-d")) {
                            c = 0;
                            switch (c) {
                            }
                            outPrintWriter.println("Error: " + e.toString());
                            return -1;
                        }
                        c = 65535;
                        switch (c) {
                        }
                        outPrintWriter.println("Error: " + e.toString());
                        return -1;
                    case 1496:
                        if (nextOption.equals("-e")) {
                            c = 1;
                            switch (c) {
                            }
                            outPrintWriter.println("Error: " + e.toString());
                            return -1;
                        }
                        c = 65535;
                        switch (c) {
                        }
                        outPrintWriter.println("Error: " + e.toString());
                        return -1;
                    case 1497:
                        if (nextOption.equals("-f")) {
                            c = 3;
                            switch (c) {
                            }
                            outPrintWriter.println("Error: " + e.toString());
                            return -1;
                        }
                        c = 65535;
                        switch (c) {
                        }
                        outPrintWriter.println("Error: " + e.toString());
                        return -1;
                    case android.net.util.NetworkConstants.ETHER_MTU /* 1500 */:
                        if (nextOption.equals("-i")) {
                            c = 4;
                            switch (c) {
                            }
                            outPrintWriter.println("Error: " + e.toString());
                            return -1;
                        }
                        c = 65535;
                        switch (c) {
                        }
                        outPrintWriter.println("Error: " + e.toString());
                        return -1;
                    case 1503:
                        if (nextOption.equals("-l")) {
                            c = 5;
                            switch (c) {
                            }
                            outPrintWriter.println("Error: " + e.toString());
                            return -1;
                        }
                        c = 65535;
                        switch (c) {
                        }
                        outPrintWriter.println("Error: " + e.toString());
                        return -1;
                    case 1508:
                        if (nextOption.equals("-q")) {
                            c = 7;
                            switch (c) {
                            }
                            outPrintWriter.println("Error: " + e.toString());
                            return -1;
                        }
                        c = 65535;
                        switch (c) {
                        }
                        outPrintWriter.println("Error: " + e.toString());
                        return -1;
                    case 1510:
                        if (nextOption.equals("-s")) {
                            c = 6;
                            switch (c) {
                            }
                            outPrintWriter.println("Error: " + e.toString());
                            return -1;
                        }
                        c = 65535;
                        switch (c) {
                        }
                        outPrintWriter.println("Error: " + e.toString());
                        return -1;
                    case 1512:
                        if (nextOption.equals("-u")) {
                            c = '\t';
                            switch (c) {
                            }
                            outPrintWriter.println("Error: " + e.toString());
                            return -1;
                        }
                        c = 65535;
                        switch (c) {
                        }
                        outPrintWriter.println("Error: " + e.toString());
                        return -1;
                    case 43014832:
                        if (nextOption.equals("--uid")) {
                            c = 15;
                            switch (c) {
                            }
                            outPrintWriter.println("Error: " + e.toString());
                            return -1;
                        }
                        c = 65535;
                        switch (c) {
                        }
                        outPrintWriter.println("Error: " + e.toString());
                        return -1;
                    case 774814767:
                        if (nextOption.equals("--factory-only")) {
                            c = '\r';
                            switch (c) {
                            }
                            outPrintWriter.println("Error: " + e.toString());
                            return -1;
                        }
                        c = 65535;
                        switch (c) {
                        }
                        outPrintWriter.println("Error: " + e.toString());
                        return -1;
                    case 1017551005:
                        if (nextOption.equals("--show-stopped")) {
                            c = 17;
                            switch (c) {
                            }
                            outPrintWriter.println("Error: " + e.toString());
                            return -1;
                        }
                        c = 65535;
                        switch (c) {
                        }
                        outPrintWriter.println("Error: " + e.toString());
                        return -1;
                    case 1333469547:
                        if (nextOption.equals("--user")) {
                            c = 14;
                            switch (c) {
                            }
                            outPrintWriter.println("Error: " + e.toString());
                            return -1;
                        }
                        c = 65535;
                        switch (c) {
                        }
                        outPrintWriter.println("Error: " + e.toString());
                        return -1;
                    case 1464517361:
                        if (nextOption.equals("--match-libraries")) {
                            c = 16;
                            switch (c) {
                            }
                            outPrintWriter.println("Error: " + e.toString());
                            return -1;
                        }
                        c = 65535;
                        switch (c) {
                        }
                        outPrintWriter.println("Error: " + e.toString());
                        return -1;
                    case 1809263575:
                        if (nextOption.equals("--apex-only")) {
                            c = '\f';
                            switch (c) {
                            }
                            outPrintWriter.println("Error: " + e.toString());
                            return -1;
                        }
                        c = 65535;
                        switch (c) {
                        }
                        outPrintWriter.println("Error: " + e.toString());
                        return -1;
                    default:
                        c = 65535;
                        switch (c) {
                        }
                        outPrintWriter.println("Error: " + e.toString());
                        return -1;
                }
            }
            java.lang.String nextArg = getNextArg();
            java.io.PrintWriter printWriter = outPrintWriter;
            int[] iArr = {i8};
            if (i8 == -1) {
                iArr = ((com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class)).getUserIds();
            }
            if (z2) {
                i9 |= 67108864;
            }
            java.util.HashMap hashMap3 = new java.util.HashMap();
            java.lang.String str8 = "Error: ";
            int length = iArr.length;
            java.util.HashMap hashMap4 = hashMap3;
            int i11 = 0;
            while (i11 < length) {
                int i12 = iArr[i11];
                int i13 = length;
                int[] iArr2 = iArr;
                try {
                    int translateUserId = translateUserId(i12, 0, "runListPackages");
                    i = i11;
                    z3 = z7;
                    long j2 = i9;
                    java.util.List list2 = this.mInterface.getInstalledPackages(j2, translateUserId).getList();
                    i2 = i9;
                    int size = list2.size();
                    java.lang.String str9 = str7;
                    int i14 = 0;
                    while (i14 < size) {
                        java.util.List list3 = list2;
                        android.content.pm.PackageInfo packageInfo = (android.content.pm.PackageInfo) list2.get(i14);
                        int i15 = size;
                        java.lang.StringBuilder sb = new java.lang.StringBuilder();
                        if (nextArg != null) {
                            i4 = i14;
                            if (!packageInfo.packageName.contains(nextArg)) {
                                i6 = i12;
                                i7 = translateUserId;
                                str4 = nextArg;
                                j = j2;
                                i5 = i10;
                                hashMap2 = hashMap4;
                                str6 = str9;
                                i14 = i4 + 1;
                                str9 = str6;
                                hashMap4 = hashMap2;
                                size = i15;
                                list2 = list3;
                                nextArg = str4;
                                i10 = i5;
                                i12 = i6;
                                translateUserId = i7;
                                j2 = j;
                            }
                        } else {
                            i4 = i14;
                        }
                        boolean z18 = packageInfo.isApex;
                        str4 = nextArg;
                        if (i10 != -1 && !z18 && packageInfo.applicationInfo.uid != i10) {
                            i6 = i12;
                            i7 = translateUserId;
                            j = j2;
                            i5 = i10;
                            hashMap2 = hashMap4;
                            str6 = str9;
                        } else {
                            if (z18) {
                                z4 = true;
                            } else {
                                z4 = true;
                                if ((packageInfo.applicationInfo.flags & 1) != 0) {
                                    z5 = true;
                                    if (z18) {
                                        i5 = i10;
                                        if (packageInfo.applicationInfo.enabled) {
                                            z6 = z4;
                                            if ((!z8 && z6) || ((z9 && !z6) || ((z10 && !z5) || (z11 && z5)))) {
                                                i6 = i12;
                                                i7 = translateUserId;
                                                j = j2;
                                                hashMap2 = hashMap4;
                                                str6 = str9;
                                            } else if (!z12 && !z18) {
                                                i6 = i12;
                                                i7 = translateUserId;
                                                j = j2;
                                                hashMap2 = hashMap4;
                                                str6 = str9;
                                            } else if (!z13 && !this.mInterface.isPackageQuarantinedForUser(packageInfo.packageName, translateUserId)) {
                                                i6 = i12;
                                                i7 = translateUserId;
                                                j = j2;
                                                hashMap2 = hashMap4;
                                                str6 = str9;
                                            } else {
                                                if (z2) {
                                                    android.content.pm.ParceledListSlice declaredSharedLibraries = this.mInterface.getDeclaredSharedLibraries(packageInfo.packageName, j2, i12);
                                                    if (declaredSharedLibraries == null) {
                                                        i6 = i12;
                                                        i7 = translateUserId;
                                                        j = j2;
                                                        hashMap2 = hashMap4;
                                                        str6 = str9;
                                                    } else {
                                                        java.util.List list4 = declaredSharedLibraries.getList();
                                                        int size2 = list4.size();
                                                        i6 = i12;
                                                        int i16 = 0;
                                                        while (true) {
                                                            if (i16 < size2) {
                                                                android.content.pm.SharedLibraryInfo sharedLibraryInfo = (android.content.pm.SharedLibraryInfo) list4.get(i16);
                                                                i7 = translateUserId;
                                                                java.util.List list5 = list4;
                                                                if (sharedLibraryInfo.getType() == 3) {
                                                                    java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
                                                                    sb2.append(sharedLibraryInfo.getName());
                                                                    sb2.append(":");
                                                                    j = j2;
                                                                    sb2.append(sharedLibraryInfo.getLongVersion());
                                                                    str5 = sb2.toString();
                                                                } else {
                                                                    i16++;
                                                                    translateUserId = i7;
                                                                    list4 = list5;
                                                                }
                                                            } else {
                                                                i7 = translateUserId;
                                                                j = j2;
                                                                str5 = null;
                                                            }
                                                        }
                                                        if (str5 == null) {
                                                            hashMap2 = hashMap4;
                                                            str6 = str9;
                                                        }
                                                    }
                                                } else {
                                                    i6 = i12;
                                                    i7 = translateUserId;
                                                    j = j2;
                                                    str5 = packageInfo.packageName;
                                                }
                                                str6 = str9;
                                                sb.append(str6);
                                                if (z3) {
                                                    sb.append(packageInfo.applicationInfo.sourceDir);
                                                    sb.append("=");
                                                }
                                                sb.append(str5);
                                                if (z14) {
                                                    sb.append(" versionCode:");
                                                    if (packageInfo.applicationInfo != null) {
                                                        sb.append(packageInfo.applicationInfo.longVersionCode);
                                                    } else {
                                                        sb.append(packageInfo.getLongVersionCode());
                                                    }
                                                }
                                                if (z15) {
                                                    sb.append(" stopped=");
                                                    sb.append((packageInfo.applicationInfo.flags & 2097152) != 0 ? "true" : "false");
                                                }
                                                if (z16) {
                                                    sb.append("  installer=");
                                                    sb.append(this.mInterface.getInstallerPackageName(packageInfo.packageName));
                                                }
                                                hashMap2 = hashMap4;
                                                list = (java.util.List) hashMap2.computeIfAbsent(sb.toString(), new java.util.function.Function() { // from class: com.android.server.pm.PackageManagerShellCommand$$ExternalSyntheticLambda1
                                                    @Override // java.util.function.Function
                                                    public final java.lang.Object apply(java.lang.Object obj) {
                                                        java.util.List lambda$runListPackages$1;
                                                        lambda$runListPackages$1 = com.android.server.pm.PackageManagerShellCommand.lambda$runListPackages$1((java.lang.String) obj);
                                                        return lambda$runListPackages$1;
                                                    }
                                                });
                                                if (!z17 && !z18) {
                                                    list.add(java.lang.String.valueOf(packageInfo.applicationInfo.uid));
                                                }
                                            }
                                        }
                                    } else {
                                        i5 = i10;
                                    }
                                    z6 = false;
                                    if (!z8) {
                                    }
                                    if (!z12) {
                                    }
                                    if (!z13) {
                                    }
                                    if (z2) {
                                    }
                                    str6 = str9;
                                    sb.append(str6);
                                    if (z3) {
                                    }
                                    sb.append(str5);
                                    if (z14) {
                                    }
                                    if (z15) {
                                    }
                                    if (z16) {
                                    }
                                    hashMap2 = hashMap4;
                                    list = (java.util.List) hashMap2.computeIfAbsent(sb.toString(), new java.util.function.Function() { // from class: com.android.server.pm.PackageManagerShellCommand$$ExternalSyntheticLambda1
                                        @Override // java.util.function.Function
                                        public final java.lang.Object apply(java.lang.Object obj) {
                                            java.util.List lambda$runListPackages$1;
                                            lambda$runListPackages$1 = com.android.server.pm.PackageManagerShellCommand.lambda$runListPackages$1((java.lang.String) obj);
                                            return lambda$runListPackages$1;
                                        }
                                    });
                                    if (!z17) {
                                        list.add(java.lang.String.valueOf(packageInfo.applicationInfo.uid));
                                    }
                                }
                            }
                            z5 = false;
                            if (z18) {
                            }
                            z6 = false;
                            if (!z8) {
                            }
                            if (!z12) {
                            }
                            if (!z13) {
                            }
                            if (z2) {
                            }
                            str6 = str9;
                            sb.append(str6);
                            if (z3) {
                            }
                            sb.append(str5);
                            if (z14) {
                            }
                            if (z15) {
                            }
                            if (z16) {
                            }
                            hashMap2 = hashMap4;
                            list = (java.util.List) hashMap2.computeIfAbsent(sb.toString(), new java.util.function.Function() { // from class: com.android.server.pm.PackageManagerShellCommand$$ExternalSyntheticLambda1
                                @Override // java.util.function.Function
                                public final java.lang.Object apply(java.lang.Object obj) {
                                    java.util.List lambda$runListPackages$1;
                                    lambda$runListPackages$1 = com.android.server.pm.PackageManagerShellCommand.lambda$runListPackages$1((java.lang.String) obj);
                                    return lambda$runListPackages$1;
                                }
                            });
                            if (!z17) {
                            }
                        }
                        i14 = i4 + 1;
                        str9 = str6;
                        hashMap4 = hashMap2;
                        size = i15;
                        list2 = list3;
                        nextArg = str4;
                        i10 = i5;
                        i12 = i6;
                        translateUserId = i7;
                        j2 = j;
                    }
                    str2 = nextArg;
                    i3 = i10;
                    hashMap = hashMap4;
                    str = str9;
                    str3 = str8;
                } catch (java.lang.RuntimeException e2) {
                    str = str7;
                    str2 = nextArg;
                    i = i11;
                    z3 = z7;
                    i2 = i9;
                    i3 = i10;
                    hashMap = hashMap4;
                    java.io.PrintWriter errPrintWriter = getErrPrintWriter();
                    java.lang.StringBuilder sb3 = new java.lang.StringBuilder();
                    str3 = str8;
                    sb3.append(str3);
                    sb3.append(e2.toString());
                    errPrintWriter.println(sb3.toString());
                }
                str7 = str;
                str8 = str3;
                hashMap4 = hashMap;
                length = i13;
                iArr = iArr2;
                z7 = z3;
                i9 = i2;
                nextArg = str2;
                i10 = i3;
                i11 = i + 1;
            }
            for (java.util.Map.Entry entry : hashMap4.entrySet()) {
                java.io.PrintWriter printWriter2 = printWriter;
                printWriter2.print((java.lang.String) entry.getKey());
                java.util.List list6 = (java.util.List) entry.getValue();
                if (!list6.isEmpty()) {
                    printWriter2.print(" uid:");
                    printWriter2.print(java.lang.String.join(",", list6));
                }
                printWriter2.println();
                printWriter = printWriter2;
            }
            return 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.util.List lambda$runListPackages$1(java.lang.String str) {
        return new java.util.ArrayList();
    }

    private int runListPermissionGroups() throws android.os.RemoteException {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        java.util.List allPermissionGroups = this.mPermissionManager.getAllPermissionGroups(0);
        int size = allPermissionGroups.size();
        for (int i = 0; i < size; i++) {
            android.content.pm.PermissionGroupInfo permissionGroupInfo = (android.content.pm.PermissionGroupInfo) allPermissionGroups.get(i);
            outPrintWriter.print("permission group:");
            outPrintWriter.println(permissionGroupInfo.name);
        }
        return 0;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int runListPermissions() throws android.os.RemoteException {
        char c;
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        boolean z4 = false;
        boolean z5 = false;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                switch (nextOption.hashCode()) {
                    case 1495:
                        if (nextOption.equals("-d")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1497:
                        if (nextOption.equals("-f")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1498:
                        if (nextOption.equals("-g")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1510:
                        if (nextOption.equals("-s")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1512:
                        if (nextOption.equals("-u")) {
                            c = 4;
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
                        z = true;
                        break;
                    case 1:
                        z3 = true;
                        break;
                    case 2:
                        z2 = true;
                        break;
                    case 3:
                        z2 = true;
                        z3 = true;
                        z4 = true;
                        break;
                    case 4:
                        z5 = true;
                        break;
                    default:
                        outPrintWriter.println("Error: Unknown option: " + nextOption);
                        return 1;
                }
            } else {
                java.util.ArrayList<java.lang.String> arrayList = new java.util.ArrayList<>();
                if (z2) {
                    java.util.List allPermissionGroups = this.mPermissionManager.getAllPermissionGroups(0);
                    int size = allPermissionGroups.size();
                    for (int i = 0; i < size; i++) {
                        arrayList.add(((android.content.pm.PermissionGroupInfo) allPermissionGroups.get(i)).name);
                    }
                    arrayList.add(null);
                } else {
                    arrayList.add(getNextArg());
                }
                if (z) {
                    outPrintWriter.println("Dangerous Permissions:");
                    outPrintWriter.println("");
                    doListPermissions(arrayList, z2, z3, z4, 1, 1);
                    if (z5) {
                        outPrintWriter.println("Normal Permissions:");
                        outPrintWriter.println("");
                        doListPermissions(arrayList, z2, z3, z4, 0, 0);
                    }
                } else if (z5) {
                    outPrintWriter.println("Dangerous and Normal Permissions:");
                    outPrintWriter.println("");
                    doListPermissions(arrayList, z2, z3, z4, 0, 1);
                } else {
                    outPrintWriter.println("All Permissions:");
                    outPrintWriter.println("");
                    doListPermissions(arrayList, z2, z3, z4, com.android.server.am.ProcessList.INVALID_ADJ, 10000);
                }
                return 0;
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private boolean setSessionFlag(java.lang.String str, com.android.server.pm.PackageManagerShellCommand.SessionDump sessionDump) {
        char c;
        switch (str.hashCode()) {
            case -2056597429:
                if (str.equals("--only-parent")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1847964944:
                if (str.equals("--only-sessionid")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 1321081314:
                if (str.equals("--only-ready")) {
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
                sessionDump.onlyParent = true;
                return true;
            case 1:
                sessionDump.onlyReady = true;
                return true;
            case 2:
                sessionDump.onlySessionId = true;
                return true;
            default:
                return false;
        }
    }

    private int runListStagedSessions() {
        java.lang.String nextOption;
        com.android.internal.util.IndentingPrintWriter indentingPrintWriter = new com.android.internal.util.IndentingPrintWriter(getOutPrintWriter(), "  ", 120);
        try {
            com.android.server.pm.PackageManagerShellCommand.SessionDump sessionDump = new com.android.server.pm.PackageManagerShellCommand.SessionDump();
            do {
                nextOption = getNextOption();
                if (nextOption == null) {
                    try {
                        printSessionList(indentingPrintWriter, this.mInterface.getPackageInstaller().getStagedSessions().getList(), sessionDump);
                        indentingPrintWriter.close();
                        return 1;
                    } catch (android.os.RemoteException e) {
                        indentingPrintWriter.println("Failure [" + e.getClass().getName() + " - " + e.getMessage() + "]");
                        indentingPrintWriter.close();
                        return -1;
                    }
                }
            } while (setSessionFlag(nextOption, sessionDump));
            indentingPrintWriter.println("Error: Unknown option: " + nextOption);
            indentingPrintWriter.close();
            return -1;
        } catch (java.lang.Throwable th) {
            try {
                indentingPrintWriter.close();
            } catch (java.lang.Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    private void printSessionList(com.android.internal.util.IndentingPrintWriter indentingPrintWriter, java.util.List<android.content.pm.PackageInstaller.SessionInfo> list, com.android.server.pm.PackageManagerShellCommand.SessionDump sessionDump) {
        android.util.SparseArray sparseArray = new android.util.SparseArray(list.size());
        for (android.content.pm.PackageInstaller.SessionInfo sessionInfo : list) {
            sparseArray.put(sessionInfo.getSessionId(), sessionInfo);
        }
        for (android.content.pm.PackageInstaller.SessionInfo sessionInfo2 : list) {
            if (!sessionDump.onlyReady || sessionInfo2.isStagedSessionReady()) {
                if (sessionInfo2.getParentSessionId() == -1) {
                    printSession(indentingPrintWriter, sessionInfo2, sessionDump);
                    if (sessionInfo2.isMultiPackage() && !sessionDump.onlyParent) {
                        indentingPrintWriter.increaseIndent();
                        int[] childSessionIds = sessionInfo2.getChildSessionIds();
                        for (int i = 0; i < childSessionIds.length; i++) {
                            android.content.pm.PackageInstaller.SessionInfo sessionInfo3 = (android.content.pm.PackageInstaller.SessionInfo) sparseArray.get(childSessionIds[i]);
                            if (sessionInfo3 == null) {
                                if (sessionDump.onlySessionId) {
                                    indentingPrintWriter.println(childSessionIds[i]);
                                } else {
                                    indentingPrintWriter.println("sessionId = " + childSessionIds[i] + "; not found");
                                }
                            } else {
                                printSession(indentingPrintWriter, sessionInfo3, sessionDump);
                            }
                        }
                        indentingPrintWriter.decreaseIndent();
                    }
                }
            }
        }
    }

    private static void printSession(java.io.PrintWriter printWriter, android.content.pm.PackageInstaller.SessionInfo sessionInfo, com.android.server.pm.PackageManagerShellCommand.SessionDump sessionDump) {
        if (sessionDump.onlySessionId) {
            printWriter.println(sessionInfo.getSessionId());
            return;
        }
        printWriter.println("sessionId = " + sessionInfo.getSessionId() + "; appPackageName = " + sessionInfo.getAppPackageName() + "; isStaged = " + sessionInfo.isStaged() + "; isReady = " + sessionInfo.isStagedSessionReady() + "; isApplied = " + sessionInfo.isStagedSessionApplied() + "; isFailed = " + sessionInfo.isStagedSessionFailed() + "; errorMsg = " + sessionInfo.getStagedSessionErrorMessage() + ";");
    }

    private android.content.Intent parseIntentAndUser() throws java.net.URISyntaxException {
        this.mTargetUser = -2;
        this.mBrief = false;
        this.mComponents = false;
        android.content.Intent parseCommandArgs = android.content.Intent.parseCommandArgs(this, new android.content.Intent.CommandOptionHandler() { // from class: com.android.server.pm.PackageManagerShellCommand.3
            public boolean handleOption(java.lang.String str, android.os.ShellCommand shellCommand) {
                if ("--user".equals(str)) {
                    com.android.server.pm.PackageManagerShellCommand.this.mTargetUser = android.os.UserHandle.parseUserArg(shellCommand.getNextArgRequired());
                    return true;
                }
                if ("--brief".equals(str)) {
                    com.android.server.pm.PackageManagerShellCommand.this.mBrief = true;
                    return true;
                }
                if ("--components".equals(str)) {
                    com.android.server.pm.PackageManagerShellCommand.this.mComponents = true;
                    return true;
                }
                if ("--query-flags".equals(str)) {
                    com.android.server.pm.PackageManagerShellCommand.this.mQueryFlags = java.lang.Integer.decode(shellCommand.getNextArgRequired()).intValue();
                    return true;
                }
                return false;
            }
        });
        this.mTargetUser = android.app.ActivityManager.handleIncomingUser(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), this.mTargetUser, false, false, null, null);
        return parseCommandArgs;
    }

    private void printResolveInfo(android.util.PrintWriterPrinter printWriterPrinter, java.lang.String str, android.content.pm.ResolveInfo resolveInfo, boolean z, boolean z2) {
        android.content.ComponentName componentName;
        if (z || z2) {
            if (resolveInfo.activityInfo != null) {
                componentName = new android.content.ComponentName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
            } else if (resolveInfo.serviceInfo != null) {
                componentName = new android.content.ComponentName(resolveInfo.serviceInfo.packageName, resolveInfo.serviceInfo.name);
            } else if (resolveInfo.providerInfo != null) {
                componentName = new android.content.ComponentName(resolveInfo.providerInfo.packageName, resolveInfo.providerInfo.name);
            } else {
                componentName = null;
            }
            if (componentName != null) {
                if (!z2) {
                    printWriterPrinter.println(str + "priority=" + resolveInfo.priority + " preferredOrder=" + resolveInfo.preferredOrder + " match=0x" + java.lang.Integer.toHexString(resolveInfo.match) + " specificIndex=" + resolveInfo.specificIndex + " isDefault=" + resolveInfo.isDefault);
                }
                printWriterPrinter.println(str + componentName.flattenToShortString());
                return;
            }
        }
        resolveInfo.dump(printWriterPrinter, str);
    }

    private int runResolveActivity() {
        try {
            android.content.Intent parseIntentAndUser = parseIntentAndUser();
            try {
                android.content.pm.ResolveInfo resolveIntent = this.mInterface.resolveIntent(parseIntentAndUser, parseIntentAndUser.getType(), this.mQueryFlags, this.mTargetUser);
                java.io.PrintWriter outPrintWriter = getOutPrintWriter();
                if (resolveIntent == null) {
                    outPrintWriter.println("No activity found");
                    return 0;
                }
                printResolveInfo(new android.util.PrintWriterPrinter(outPrintWriter), "", resolveIntent, this.mBrief, this.mComponents);
                return 0;
            } catch (android.os.RemoteException e) {
                throw new java.lang.RuntimeException("Failed calling service", e);
            }
        } catch (java.net.URISyntaxException e2) {
            throw new java.lang.RuntimeException(e2.getMessage(), e2);
        }
    }

    private int runQueryIntentActivities() {
        try {
            android.content.Intent parseIntentAndUser = parseIntentAndUser();
            try {
                java.util.List list = this.mInterface.queryIntentActivities(parseIntentAndUser, parseIntentAndUser.getType(), this.mQueryFlags, this.mTargetUser).getList();
                java.io.PrintWriter outPrintWriter = getOutPrintWriter();
                if (list == null || list.size() <= 0) {
                    outPrintWriter.println("No activities found");
                } else if (!this.mComponents) {
                    outPrintWriter.print(list.size());
                    outPrintWriter.println(" activities found:");
                    android.util.PrintWriterPrinter printWriterPrinter = new android.util.PrintWriterPrinter(outPrintWriter);
                    for (int i = 0; i < list.size(); i++) {
                        outPrintWriter.print("  Activity #");
                        outPrintWriter.print(i);
                        outPrintWriter.println(":");
                        printResolveInfo(printWriterPrinter, "    ", (android.content.pm.ResolveInfo) list.get(i), this.mBrief, this.mComponents);
                    }
                } else {
                    android.util.PrintWriterPrinter printWriterPrinter2 = new android.util.PrintWriterPrinter(outPrintWriter);
                    for (int i2 = 0; i2 < list.size(); i2++) {
                        printResolveInfo(printWriterPrinter2, "", (android.content.pm.ResolveInfo) list.get(i2), this.mBrief, this.mComponents);
                    }
                }
                return 0;
            } catch (android.os.RemoteException e) {
                throw new java.lang.RuntimeException("Failed calling service", e);
            }
        } catch (java.net.URISyntaxException e2) {
            throw new java.lang.RuntimeException(e2.getMessage(), e2);
        }
    }

    private int runQueryIntentServices() {
        try {
            android.content.Intent parseIntentAndUser = parseIntentAndUser();
            try {
                java.util.List list = this.mInterface.queryIntentServices(parseIntentAndUser, parseIntentAndUser.getType(), this.mQueryFlags, this.mTargetUser).getList();
                java.io.PrintWriter outPrintWriter = getOutPrintWriter();
                if (list == null || list.size() <= 0) {
                    outPrintWriter.println("No services found");
                } else if (!this.mComponents) {
                    outPrintWriter.print(list.size());
                    outPrintWriter.println(" services found:");
                    android.util.PrintWriterPrinter printWriterPrinter = new android.util.PrintWriterPrinter(outPrintWriter);
                    for (int i = 0; i < list.size(); i++) {
                        outPrintWriter.print("  Service #");
                        outPrintWriter.print(i);
                        outPrintWriter.println(":");
                        printResolveInfo(printWriterPrinter, "    ", (android.content.pm.ResolveInfo) list.get(i), this.mBrief, this.mComponents);
                    }
                } else {
                    android.util.PrintWriterPrinter printWriterPrinter2 = new android.util.PrintWriterPrinter(outPrintWriter);
                    for (int i2 = 0; i2 < list.size(); i2++) {
                        printResolveInfo(printWriterPrinter2, "", (android.content.pm.ResolveInfo) list.get(i2), this.mBrief, this.mComponents);
                    }
                }
                return 0;
            } catch (android.os.RemoteException e) {
                throw new java.lang.RuntimeException("Failed calling service", e);
            }
        } catch (java.net.URISyntaxException e2) {
            throw new java.lang.RuntimeException(e2.getMessage(), e2);
        }
    }

    private int runQueryIntentReceivers() {
        try {
            android.content.Intent parseIntentAndUser = parseIntentAndUser();
            try {
                java.util.List list = this.mInterface.queryIntentReceivers(parseIntentAndUser, parseIntentAndUser.getType(), this.mQueryFlags, this.mTargetUser).getList();
                java.io.PrintWriter outPrintWriter = getOutPrintWriter();
                if (list == null || list.size() <= 0) {
                    outPrintWriter.println("No receivers found");
                } else if (!this.mComponents) {
                    outPrintWriter.print(list.size());
                    outPrintWriter.println(" receivers found:");
                    android.util.PrintWriterPrinter printWriterPrinter = new android.util.PrintWriterPrinter(outPrintWriter);
                    for (int i = 0; i < list.size(); i++) {
                        outPrintWriter.print("  Receiver #");
                        outPrintWriter.print(i);
                        outPrintWriter.println(":");
                        printResolveInfo(printWriterPrinter, "    ", (android.content.pm.ResolveInfo) list.get(i), this.mBrief, this.mComponents);
                    }
                } else {
                    android.util.PrintWriterPrinter printWriterPrinter2 = new android.util.PrintWriterPrinter(outPrintWriter);
                    for (int i2 = 0; i2 < list.size(); i2++) {
                        printResolveInfo(printWriterPrinter2, "", (android.content.pm.ResolveInfo) list.get(i2), this.mBrief, this.mComponents);
                    }
                }
                return 0;
            } catch (android.os.RemoteException e) {
                throw new java.lang.RuntimeException("Failed calling service", e);
            }
        } catch (java.net.URISyntaxException e2) {
            throw new java.lang.RuntimeException(e2.getMessage(), e2);
        }
    }

    private int runStreamingInstall() throws android.os.RemoteException {
        com.android.server.pm.PackageManagerShellCommand.InstallParams makeInstallParams = makeInstallParams(UNSUPPORTED_INSTALL_CMD_OPTS);
        if (makeInstallParams.sessionParams.dataLoaderParams == null) {
            makeInstallParams.sessionParams.setDataLoaderParams(com.android.server.pm.PackageManagerShellCommandDataLoader.getStreamingDataLoaderParams(this));
        }
        return doRunInstall(makeInstallParams);
    }

    private int runArchivedInstall() throws android.os.RemoteException {
        com.android.server.pm.PackageManagerShellCommand.InstallParams makeInstallParams = makeInstallParams(UNSUPPORTED_INSTALL_CMD_OPTS);
        makeInstallParams.sessionParams.installFlags |= 134217728;
        if (makeInstallParams.sessionParams.dataLoaderParams == null) {
            makeInstallParams.sessionParams.setDataLoaderParams(com.android.server.pm.PackageManagerShellCommandDataLoader.getStreamingDataLoaderParams(this));
        }
        return doRunInstall(makeInstallParams);
    }

    private int runIncrementalInstall() throws android.os.RemoteException {
        com.android.server.pm.PackageManagerShellCommand.InstallParams makeInstallParams = makeInstallParams(UNSUPPORTED_INSTALL_CMD_OPTS);
        if (makeInstallParams.sessionParams.dataLoaderParams == null) {
            makeInstallParams.sessionParams.setDataLoaderParams(com.android.server.pm.PackageManagerShellCommandDataLoader.getIncrementalDataLoaderParams(this));
        }
        return doRunInstall(makeInstallParams);
    }

    private int runInstall() throws android.os.RemoteException {
        return doRunInstall(makeInstallParams(UNSUPPORTED_INSTALL_CMD_OPTS));
    }

    private int doRunInstall(com.android.server.pm.PackageManagerShellCommand.InstallParams installParams) throws android.os.RemoteException {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        int i = installParams.userId;
        boolean z = true;
        if (i != -1 && i != -2 && ((com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class)).getUserInfo(i) == null) {
            outPrintWriter.println("Failure [user " + i + " doesn't exist]");
            return 1;
        }
        boolean z2 = installParams.sessionParams.dataLoaderParams != null;
        boolean z3 = (installParams.sessionParams.installFlags & 131072) != 0;
        boolean z4 = (installParams.sessionParams.installFlags & 134217728) != 0;
        java.util.ArrayList<java.lang.String> remainingArgs = getRemainingArgs();
        boolean z5 = remainingArgs.isEmpty() || STDIN_PATH.equals(remainingArgs.get(0));
        boolean z6 = remainingArgs.size() > 1;
        if (z5 && installParams.sessionParams.sizeBytes == -1) {
            outPrintWriter.println("Error: must either specify a package size or an APK file");
            return 1;
        }
        if (z3 && z6) {
            outPrintWriter.println("Error: can't specify SPLIT(s) for APEX");
            return 1;
        }
        if (z4 && z6) {
            outPrintWriter.println("Error: can't have SPLIT(s) for Archival install");
            return 1;
        }
        if (!z2) {
            if (z5 && z6) {
                outPrintWriter.println("Error: can't specify SPLIT(s) along with STDIN");
                return 1;
            }
            if (remainingArgs.isEmpty()) {
                remainingArgs.add(STDIN_PATH);
            } else {
                setParamsSize(installParams, remainingArgs);
            }
        }
        int doCreateSession = doCreateSession(installParams.sessionParams, installParams.installerPackageName, installParams.userId);
        try {
            if (z2) {
                if (doAddFiles(doCreateSession, remainingArgs, installParams.sessionParams.sizeBytes, z3, z4) != 0) {
                    try {
                        doAbandonSession(doCreateSession, false);
                    } catch (java.lang.Exception e) {
                    }
                    return 1;
                }
            } else if (doWriteSplits(doCreateSession, remainingArgs, installParams.sessionParams.sizeBytes, z3) != 0) {
                try {
                    doAbandonSession(doCreateSession, false);
                } catch (java.lang.Exception e2) {
                }
                return 1;
            }
            if (doCommitSession(doCreateSession, false) != 0) {
                try {
                    doAbandonSession(doCreateSession, false);
                } catch (java.lang.Exception e3) {
                }
                return 1;
            }
            try {
                if (installParams.sessionParams.isStaged && installParams.stagedReadyTimeoutMs > 0) {
                    return doWaitForStagedSessionReady(doCreateSession, installParams.stagedReadyTimeoutMs, outPrintWriter);
                }
                outPrintWriter.println("Success");
                return 0;
            } catch (java.lang.Throwable th) {
                th = th;
                z = false;
                if (z) {
                    try {
                        doAbandonSession(doCreateSession, false);
                    } catch (java.lang.Exception e4) {
                    }
                }
                throw th;
            }
        } catch (java.lang.Throwable th2) {
            th = th2;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x008a, code lost:
    
        r14.println("Failure [failed to retrieve SessionInfo]");
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x008f, code lost:
    
        return 1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int doWaitForStagedSessionReady(int i, long j, java.io.PrintWriter printWriter) throws android.os.RemoteException {
        com.android.internal.util.Preconditions.checkArgument(j > 0);
        android.content.pm.PackageInstaller.SessionInfo sessionInfo = this.mInterface.getPackageInstaller().getSessionInfo(i);
        if (sessionInfo == null) {
            printWriter.println("Failure [Unknown session " + i + "]");
            return 1;
        }
        if (!sessionInfo.isStaged()) {
            printWriter.println("Failure [Session " + i + " is not a staged session]");
            return 1;
        }
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        long j2 = currentTimeMillis + j;
        while (sessionInfo != null && currentTimeMillis < j2 && !sessionInfo.isStagedSessionReady() && !sessionInfo.isStagedSessionFailed()) {
            android.os.SystemClock.sleep(java.lang.Math.min(j2 - currentTimeMillis, 100L));
            currentTimeMillis = java.lang.System.currentTimeMillis();
            sessionInfo = this.mInterface.getPackageInstaller().getSessionInfo(i);
        }
        if (!sessionInfo.isStagedSessionReady() && !sessionInfo.isStagedSessionFailed()) {
            printWriter.println("Failure [timed out after " + j + " ms]");
            return 1;
        }
        if (!sessionInfo.isStagedSessionReady()) {
            printWriter.println("Error [" + sessionInfo.getStagedSessionErrorCode() + "] [" + sessionInfo.getStagedSessionErrorMessage() + "]");
            return 1;
        }
        printWriter.println("Success. Reboot device to apply staged session");
        return 0;
    }

    private int runInstallAbandon() throws android.os.RemoteException {
        return doAbandonSession(java.lang.Integer.parseInt(getNextArg()), true);
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x001c, code lost:
    
        if (r3.equals("--staged-ready-timeout") != false) goto L12;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int runInstallCommit() throws android.os.RemoteException {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        long j = 60000;
        while (true) {
            java.lang.String nextOption = getNextOption();
            char c = 0;
            if (nextOption != null) {
                switch (nextOption.hashCode()) {
                    case -158482320:
                        break;
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                        j = java.lang.Long.parseLong(getNextArgRequired());
                    default:
                        throw new java.lang.IllegalArgumentException("Unknown option: " + nextOption);
                }
            } else {
                int parseInt = java.lang.Integer.parseInt(getNextArg());
                if (doCommitSession(parseInt, false) != 0) {
                    return 1;
                }
                android.content.pm.PackageInstaller.SessionInfo sessionInfo = this.mInterface.getPackageInstaller().getSessionInfo(parseInt);
                if (sessionInfo != null && sessionInfo.isStaged() && j > 0) {
                    return doWaitForStagedSessionReady(parseInt, j, outPrintWriter);
                }
                outPrintWriter.println("Success");
                return 0;
            }
        }
    }

    private int runInstallCreate() throws android.os.RemoteException {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        com.android.server.pm.PackageManagerShellCommand.InstallParams makeInstallParams = makeInstallParams(UNSUPPORTED_SESSION_CREATE_OPTS);
        outPrintWriter.println("Success: created install session [" + doCreateSession(makeInstallParams.sessionParams, makeInstallParams.installerPackageName, makeInstallParams.userId) + "]");
        return 0;
    }

    private int runInstallWrite() throws android.os.RemoteException {
        long j = -1;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                if (nextOption.equals("-S")) {
                    j = java.lang.Long.parseLong(getNextArg());
                } else {
                    throw new java.lang.IllegalArgumentException("Unknown option: " + nextOption);
                }
            } else {
                return doWriteSplit(java.lang.Integer.parseInt(getNextArg()), getNextArg(), j, getNextArg(), true);
            }
        }
    }

    private int runInstallAddSession() throws android.os.RemoteException {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        int parseInt = java.lang.Integer.parseInt(getNextArg());
        android.util.IntArray intArray = new android.util.IntArray();
        while (true) {
            java.lang.String nextArg = getNextArg();
            if (nextArg == null) {
                break;
            }
            intArray.add(java.lang.Integer.parseInt(nextArg));
        }
        if (intArray.size() == 0) {
            outPrintWriter.println("Error: At least two sessions are required.");
            return 1;
        }
        return doInstallAddSession(parseInt, intArray.toArray(), true);
    }

    private int runInstallSetPreVerifiedDomains() throws android.os.RemoteException {
        android.content.pm.PackageInstaller.Session session;
        getOutPrintWriter();
        int parseInt = java.lang.Integer.parseInt(getNextArg());
        java.lang.String[] split = getNextArg().split(",");
        android.content.pm.PackageInstaller.Session session2 = null;
        try {
            session = new android.content.pm.PackageInstaller.Session(this.mInterface.getPackageInstaller().openSession(parseInt));
        } catch (java.lang.Throwable th) {
            th = th;
        }
        try {
            session.setPreVerifiedDomains(new android.util.ArraySet(split));
            libcore.io.IoUtils.closeQuietly(session);
            return 0;
        } catch (java.lang.Throwable th2) {
            th = th2;
            session2 = session;
            libcore.io.IoUtils.closeQuietly(session2);
            throw th;
        }
    }

    private int runInstallGetPreVerifiedDomains() throws android.os.RemoteException {
        android.content.pm.PackageInstaller.Session session;
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        android.content.pm.PackageInstaller.Session session2 = null;
        try {
            session = new android.content.pm.PackageInstaller.Session(this.mInterface.getPackageInstaller().openSession(java.lang.Integer.parseInt(getNextArg())));
        } catch (java.lang.Throwable th) {
            th = th;
        }
        try {
            java.util.Set preVerifiedDomains = session.getPreVerifiedDomains();
            if (preVerifiedDomains.isEmpty()) {
                outPrintWriter.println("The session doesn't have any pre-verified domains specified.");
            } else {
                outPrintWriter.println(java.lang.String.join(",", preVerifiedDomains));
            }
            libcore.io.IoUtils.closeQuietly(session);
            return 0;
        } catch (java.lang.Throwable th2) {
            th = th2;
            session2 = session;
            libcore.io.IoUtils.closeQuietly(session2);
            throw th;
        }
    }

    private int runInstallRemove() throws android.os.RemoteException {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        int parseInt = java.lang.Integer.parseInt(getNextArg());
        java.util.ArrayList<java.lang.String> remainingArgs = getRemainingArgs();
        if (remainingArgs.isEmpty()) {
            outPrintWriter.println("Error: split name not specified");
            return 1;
        }
        return doRemoveSplits(parseInt, remainingArgs, true);
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x001c, code lost:
    
        if (r2.equals("--user") != false) goto L12;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int runGetArchivedPackageMetadata() throws android.os.RemoteException {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        int i = -2;
        while (true) {
            java.lang.String nextOption = getNextOption();
            boolean z = false;
            if (nextOption != null) {
                switch (nextOption.hashCode()) {
                    case 1333469547:
                        break;
                    default:
                        z = -1;
                        break;
                }
                switch (z) {
                    case false:
                        i = android.os.UserHandle.parseUserArg(getNextArgRequired());
                    default:
                        outPrintWriter.println("Error: Unknown option: " + nextOption);
                        return 1;
                }
            } else {
                java.lang.String nextArg = getNextArg();
                if (nextArg == null) {
                    outPrintWriter.println("Error: package name not specified");
                    return 1;
                }
                try {
                    android.os.Parcelable archivedPackage = this.mInterface.getArchivedPackage(nextArg, translateUserId(i, com.android.server.am.ProcessList.INVALID_ADJ, "runGetArchivedPackageMetadata"));
                    if (archivedPackage == null) {
                        outPrintWriter.write("Package not found " + nextArg);
                        return -1;
                    }
                    android.os.Parcel obtain = android.os.Parcel.obtain();
                    try {
                        obtain.writeParcelable(archivedPackage, 0);
                        byte[] marshall = obtain.marshall();
                        obtain.recycle();
                        outPrintWriter.write(libcore.util.HexEncoding.encodeToString(marshall));
                        return 0;
                    } catch (java.lang.Throwable th) {
                        obtain.recycle();
                        throw th;
                    }
                } catch (java.lang.Exception e) {
                    getErrPrintWriter().println("Failed to get archived package, reason: " + e);
                    outPrintWriter.println("Failure [failed to get archived package], reason: " + e);
                    return -1;
                }
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int runInstallExisting() throws android.os.RemoteException {
        char c;
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        int i = -2;
        int i2 = 4194304;
        boolean z = false;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                switch (nextOption.hashCode()) {
                    case -951415743:
                        if (nextOption.equals("--instant")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1051781117:
                        if (nextOption.equals("--ephemeral")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1333024815:
                        if (nextOption.equals("--full")) {
                            c = 3;
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
                    case 1333511957:
                        if (nextOption.equals("--wait")) {
                            c = 4;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1494514835:
                        if (nextOption.equals("--restrict-permissions")) {
                            c = 5;
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
                        i = android.os.UserHandle.parseUserArg(getNextArgRequired());
                        break;
                    case 1:
                    case 2:
                        i2 = (i2 | 2048) & (-16385);
                        break;
                    case 3:
                        i2 = (i2 & (-2049)) | 16384;
                        break;
                    case 4:
                        z = true;
                        break;
                    case 5:
                        i2 = (-4194305) & i2;
                        break;
                    default:
                        outPrintWriter.println("Error: Unknown option: " + nextOption);
                        return 1;
                }
            } else {
                java.lang.String nextArg = getNextArg();
                if (nextArg == null) {
                    outPrintWriter.println("Error: package name not specified");
                    return 1;
                }
                int translateUserId = translateUserId(i, com.android.server.am.ProcessList.INVALID_ADJ, "runInstallExisting");
                try {
                    if (z) {
                        com.android.server.pm.PackageManagerShellCommand.LocalIntentReceiver localIntentReceiver = new com.android.server.pm.PackageManagerShellCommand.LocalIntentReceiver();
                        android.content.pm.IPackageInstaller packageInstaller = this.mInterface.getPackageInstaller();
                        outPrintWriter.println("Installing package " + nextArg + " for user: " + translateUserId);
                        packageInstaller.installExistingPackage(nextArg, i2, 0, localIntentReceiver.getIntentSender(), translateUserId, (java.util.List) null);
                        int intExtra = localIntentReceiver.getResult().getIntExtra("android.content.pm.extra.STATUS", 1);
                        outPrintWriter.println("Received intent for package install");
                        return intExtra == 0 ? 0 : 1;
                    }
                    if (this.mInterface.installExistingPackageAsUser(nextArg, translateUserId, i2, 0, (java.util.List) null) == -3) {
                        throw new android.content.pm.PackageManager.NameNotFoundException("Package " + nextArg + " doesn't exist");
                    }
                    outPrintWriter.println("Package " + nextArg + " installed for user: " + translateUserId);
                    return 0;
                } catch (android.content.pm.PackageManager.NameNotFoundException | android.os.RemoteException e) {
                    outPrintWriter.println(e.toString());
                    return 1;
                }
            }
        }
    }

    private int runSetInstallLocation() throws android.os.RemoteException {
        java.lang.String nextArg = getNextArg();
        if (nextArg == null) {
            getErrPrintWriter().println("Error: no install location specified.");
            return 1;
        }
        try {
            if (!this.mInterface.setInstallLocation(java.lang.Integer.parseInt(nextArg))) {
                getErrPrintWriter().println("Error: install location has to be a number.");
                return 1;
            }
            return 0;
        } catch (java.lang.NumberFormatException e) {
            getErrPrintWriter().println("Error: install location has to be a number.");
            return 1;
        }
    }

    private int runGetInstallLocation() throws android.os.RemoteException {
        java.lang.String str;
        int installLocation = this.mInterface.getInstallLocation();
        if (installLocation == 0) {
            str = com.android.server.UiModeManagerService.Shell.NIGHT_MODE_STR_AUTO;
        } else if (installLocation == 1) {
            str = "internal";
        } else if (installLocation != 2) {
            str = "invalid";
        } else {
            str = "external";
        }
        getOutPrintWriter().println(installLocation + "[" + str + "]");
        return 0;
    }

    public int runMovePackage() throws android.os.RemoteException {
        java.lang.String nextArg = getNextArg();
        if (nextArg == null) {
            getErrPrintWriter().println("Error: package name not specified");
            return 1;
        }
        java.lang.String nextArg2 = getNextArg();
        if ("internal".equals(nextArg2)) {
            nextArg2 = null;
        }
        int movePackage = this.mInterface.movePackage(nextArg, nextArg2);
        int moveStatus = this.mInterface.getMoveStatus(movePackage);
        while (!android.content.pm.PackageManager.isMoveStatusFinished(moveStatus)) {
            android.os.SystemClock.sleep(1000L);
            moveStatus = this.mInterface.getMoveStatus(movePackage);
        }
        if (moveStatus == -100) {
            getOutPrintWriter().println("Success");
            return 0;
        }
        getErrPrintWriter().println("Failure [" + moveStatus + "]");
        return 1;
    }

    public int runMovePrimaryStorage() throws android.os.RemoteException {
        java.lang.String nextArg = getNextArg();
        if ("internal".equals(nextArg)) {
            nextArg = null;
        }
        int movePrimaryStorage = this.mInterface.movePrimaryStorage(nextArg);
        int moveStatus = this.mInterface.getMoveStatus(movePrimaryStorage);
        while (!android.content.pm.PackageManager.isMoveStatusFinished(moveStatus)) {
            android.os.SystemClock.sleep(1000L);
            moveStatus = this.mInterface.getMoveStatus(movePrimaryStorage);
        }
        if (moveStatus == -100) {
            getOutPrintWriter().println("Success");
            return 0;
        }
        getErrPrintWriter().println("Failure [" + moveStatus + "]");
        return 1;
    }

    private int runCompile() throws android.os.RemoteException {
        java.util.List<java.lang.String> singletonList;
        int i;
        java.lang.String str;
        boolean z;
        boolean performDexOptMode;
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        java.lang.String str2 = null;
        java.lang.String str3 = null;
        java.lang.String str4 = null;
        java.lang.String str5 = null;
        boolean z2 = false;
        boolean z3 = false;
        boolean z4 = false;
        boolean z5 = false;
        while (true) {
            java.lang.String nextOption = getNextOption();
            char c = 65535;
            if (nextOption != null) {
                switch (nextOption.hashCode()) {
                    case -1615291473:
                        if (nextOption.equals("--reset")) {
                            c = 6;
                            break;
                        }
                        break;
                    case -1614046854:
                        if (nextOption.equals("--split")) {
                            c = '\b';
                            break;
                        }
                        break;
                    case 1492:
                        if (nextOption.equals("-a")) {
                            c = 0;
                            break;
                        }
                        break;
                    case 1494:
                        if (nextOption.equals("-c")) {
                            c = 1;
                            break;
                        }
                        break;
                    case 1497:
                        if (nextOption.equals("-f")) {
                            c = 2;
                            break;
                        }
                        break;
                    case 1504:
                        if (nextOption.equals("-m")) {
                            c = 3;
                            break;
                        }
                        break;
                    case 1509:
                        if (nextOption.equals("-r")) {
                            c = 4;
                            break;
                        }
                        break;
                    case 1269477022:
                        if (nextOption.equals("--secondary-dex")) {
                            c = 7;
                            break;
                        }
                        break;
                    case 1690714782:
                        if (nextOption.equals("--check-prof")) {
                            c = 5;
                            break;
                        }
                        break;
                }
                switch (c) {
                    case 0:
                        z2 = true;
                        break;
                    case 1:
                        z5 = true;
                        break;
                    case 2:
                        z3 = true;
                        break;
                    case 3:
                        str3 = getNextArgRequired();
                        break;
                    case 4:
                        str4 = getNextArgRequired();
                        break;
                    case 5:
                        getNextArgRequired();
                        outPrintWriter.println("Warning: Ignoring obsolete flag --check-prof - it is unconditionally enabled now");
                        break;
                    case 6:
                        str4 = "install";
                        z3 = true;
                        z5 = true;
                        break;
                    case 7:
                        z4 = true;
                        break;
                    case '\b':
                        str5 = getNextArgRequired();
                        break;
                    default:
                        outPrintWriter.println("Error: Unknown option: " + nextOption);
                        return 1;
                }
            } else {
                boolean z6 = str3 != null;
                boolean z7 = str4 != null;
                if (z6 && z7) {
                    outPrintWriter.println("Cannot use compilation filter (\"-m\") and compilation reason (\"-r\") at the same time");
                    return 1;
                }
                if (!z6 && !z7) {
                    outPrintWriter.println("Cannot run without any of compilation filter (\"-m\") and compilation reason (\"-r\")");
                    return 1;
                }
                if (z2 && str5 != null) {
                    outPrintWriter.println("-a cannot be specified together with --split");
                    return 1;
                }
                if (z4 && str5 != null) {
                    outPrintWriter.println("--secondary-dex cannot be specified together with --split");
                    return 1;
                }
                if (z6) {
                    if (!dalvik.system.DexFile.isValidCompilerFilter(str3)) {
                        outPrintWriter.println("Error: \"" + str3 + "\" is not a valid compilation filter.");
                        return 1;
                    }
                    str2 = str3;
                }
                if (z7) {
                    int i2 = 0;
                    while (true) {
                        if (i2 >= com.android.server.pm.PackageManagerServiceCompilerMapping.REASON_STRINGS.length) {
                            i2 = -1;
                        } else if (!com.android.server.pm.PackageManagerServiceCompilerMapping.REASON_STRINGS[i2].equals(str4)) {
                            i2++;
                        }
                    }
                    if (i2 == -1) {
                        outPrintWriter.println("Error: Unknown compilation reason: " + str4);
                        return 1;
                    }
                    str2 = com.android.server.pm.PackageManagerServiceCompilerMapping.getCompilerFilterForReason(i2);
                }
                if (z2) {
                    singletonList = this.mInterface.getAllPackages();
                    singletonList.removeIf(new java.util.function.Predicate() { // from class: com.android.server.pm.PackageManagerShellCommand$$ExternalSyntheticLambda4
                        @Override // java.util.function.Predicate
                        public final boolean test(java.lang.Object obj) {
                            boolean lambda$runCompile$2;
                            lambda$runCompile$2 = com.android.server.pm.PackageManagerShellCommand.lambda$runCompile$2((java.lang.String) obj);
                            return lambda$runCompile$2;
                        }
                    });
                } else {
                    java.lang.String nextArg = getNextArg();
                    if (nextArg == null) {
                        outPrintWriter.println("Error: package name not specified");
                        return 1;
                    }
                    singletonList = java.util.Collections.singletonList(nextArg);
                }
                java.util.ArrayList<java.lang.String> arrayList = new java.util.ArrayList();
                int i3 = 0;
                for (java.lang.String str6 : singletonList) {
                    if (z5) {
                        this.mInterface.clearApplicationProfileData(str6);
                    }
                    if (!z2) {
                        i = i3;
                    } else {
                        java.lang.StringBuilder sb = new java.lang.StringBuilder();
                        int i4 = i3 + 1;
                        sb.append(i4);
                        sb.append(com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER);
                        sb.append(singletonList.size());
                        sb.append(": ");
                        sb.append(str6);
                        outPrintWriter.println(sb.toString());
                        outPrintWriter.flush();
                        i = i4;
                    }
                    if (z4) {
                        performDexOptMode = this.mInterface.performDexOptSecondary(str6, str2, z3);
                        str = str6;
                        z = z3;
                    } else {
                        str = str6;
                        z = z3;
                        performDexOptMode = this.mInterface.performDexOptMode(str6, true, str2, z3, true, str5);
                    }
                    if (!performDexOptMode) {
                        arrayList.add(str);
                    }
                    i3 = i;
                    z3 = z;
                }
                if (arrayList.isEmpty()) {
                    outPrintWriter.println("Success");
                    return 0;
                }
                if (arrayList.size() == 1) {
                    outPrintWriter.println("Failure: package " + ((java.lang.String) arrayList.get(0)) + " could not be compiled");
                    return 1;
                }
                outPrintWriter.print("Failure: the following packages could not be compiled: ");
                boolean z8 = true;
                for (java.lang.String str7 : arrayList) {
                    if (z8) {
                        z8 = false;
                    } else {
                        outPrintWriter.print(", ");
                    }
                    outPrintWriter.print(str7);
                }
                outPrintWriter.println();
                return 1;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$runCompile$2(java.lang.String str) {
        return com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME.equals(str);
    }

    private int runreconcileSecondaryDexFiles() throws android.os.RemoteException, com.android.server.pm.Installer.LegacyDexoptDisabledException {
        this.mPm.legacyReconcileSecondaryDexFiles(getNextArg());
        return 0;
    }

    public int runForceDexOpt() throws android.os.RemoteException, com.android.server.pm.Installer.LegacyDexoptDisabledException {
        this.mPm.legacyForceDexOpt(getNextArgRequired());
        return 0;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int runBgDexOpt() throws android.os.RemoteException, com.android.server.pm.Installer.LegacyDexoptDisabledException {
        char c;
        java.lang.String nextOption = getNextOption();
        if (nextOption == null) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            while (true) {
                java.lang.String nextArg = getNextArg();
                if (nextArg == null) {
                    break;
                }
                arrayList.add(nextArg);
            }
            com.android.server.pm.BackgroundDexOptService service = com.android.server.pm.BackgroundDexOptService.getService();
            if (arrayList.isEmpty()) {
                arrayList = null;
            }
            if (!service.runBackgroundDexoptJob(arrayList)) {
                getOutPrintWriter().println("Failure");
                return -1;
            }
        } else {
            java.lang.String nextArg2 = getNextArg();
            if (nextArg2 != null) {
                getErrPrintWriter().println("Invalid argument: " + nextArg2);
                return -1;
            }
            switch (nextOption.hashCode()) {
                case -1237677752:
                    if (nextOption.equals("--disable")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 1032289306:
                    if (nextOption.equals("--cancel")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 1101165347:
                    if (nextOption.equals("--enable")) {
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
                    return cancelBgDexOptJob();
                case 1:
                    com.android.server.pm.BackgroundDexOptService.getService().setDisableJobSchedulerJobs(true);
                    break;
                case 2:
                    com.android.server.pm.BackgroundDexOptService.getService().setDisableJobSchedulerJobs(false);
                    break;
                default:
                    getErrPrintWriter().println("Unknown option: " + nextOption);
                    return -1;
            }
        }
        getOutPrintWriter().println("Success");
        return 0;
    }

    private int cancelBgDexOptJob() throws android.os.RemoteException, com.android.server.pm.Installer.LegacyDexoptDisabledException {
        com.android.server.pm.BackgroundDexOptService.getService().cancelBackgroundDexoptJob();
        getOutPrintWriter().println("Success");
        return 0;
    }

    private int runDeleteDexOpt() throws android.os.RemoteException {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        java.lang.String nextArg = getNextArg();
        if (android.text.TextUtils.isEmpty(nextArg)) {
            outPrintWriter.println("Error: no package name");
            return 1;
        }
        long deleteOatArtifactsOfPackage = this.mPm.deleteOatArtifactsOfPackage(nextArg);
        if (deleteOatArtifactsOfPackage < 0) {
            outPrintWriter.println("Error: delete failed");
            return 1;
        }
        outPrintWriter.println("Success: freed " + deleteOatArtifactsOfPackage + " bytes");
        android.util.Slog.i(TAG, "delete-dexopt " + nextArg + " ,freed " + deleteOatArtifactsOfPackage + " bytes");
        return 0;
    }

    private int runDumpProfiles() throws android.os.RemoteException, com.android.server.pm.Installer.LegacyDexoptDisabledException {
        char c;
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        boolean z = false;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                switch (nextOption.hashCode()) {
                    case -2026131748:
                        if (nextOption.equals("--dump-classes-and-methods")) {
                            c = 0;
                            break;
                        }
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                        z = true;
                    default:
                        outPrintWriter.println("Error: Unknown option: " + nextOption);
                        return 1;
                }
            } else {
                this.mPm.legacyDumpProfiles(getNextArg(), z);
                return 0;
            }
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Failed to find switch 'out' block (already processed)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.calcSwitchOut(SwitchRegionMaker.java:202)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:61)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:115)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:94)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.makeEndlessLoop(LoopRegionMaker.java:281)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:64)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:92)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeMthRegion(RegionMaker.java:49)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:25)
        */
    private int runSnapshotProfile() throws android.os.RemoteException {
        /*
            Method dump skipped, instructions count: 358
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.PackageManagerShellCommand.runSnapshotProfile():int");
    }

    private java.util.ArrayList<java.lang.String> getRemainingArgs() {
        java.util.ArrayList<java.lang.String> arrayList = new java.util.ArrayList<>();
        while (true) {
            java.lang.String nextArg = getNextArg();
            if (nextArg != null) {
                arrayList.add(nextArg);
            } else {
                return arrayList;
            }
        }
    }

    private static class SnapshotRuntimeProfileCallback extends android.content.pm.dex.ISnapshotRuntimeProfileCallback.Stub {
        private final java.util.concurrent.CountDownLatch mDoneSignal;
        private int mErrCode;
        private android.os.ParcelFileDescriptor mProfileReadFd;
        private boolean mSuccess;

        private SnapshotRuntimeProfileCallback() {
            this.mSuccess = false;
            this.mErrCode = -1;
            this.mProfileReadFd = null;
            this.mDoneSignal = new java.util.concurrent.CountDownLatch(1);
        }

        public void onSuccess(android.os.ParcelFileDescriptor parcelFileDescriptor) {
            this.mSuccess = true;
            try {
                this.mProfileReadFd = parcelFileDescriptor.dup();
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
            this.mDoneSignal.countDown();
        }

        public void onError(int i) {
            this.mSuccess = false;
            this.mErrCode = i;
            this.mDoneSignal.countDown();
        }

        boolean waitTillDone() {
            boolean z;
            try {
                z = this.mDoneSignal.await(10000000L, java.util.concurrent.TimeUnit.MILLISECONDS);
            } catch (java.lang.InterruptedException e) {
                z = false;
            }
            return z && this.mSuccess;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int runUninstall() throws android.os.RemoteException {
        int i;
        int i2;
        java.lang.String str;
        char c;
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        long j = -1;
        int i3 = 0;
        int i4 = -1;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                switch (nextOption.hashCode()) {
                    case 1502:
                        if (nextOption.equals("-k")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1333469547:
                        if (nextOption.equals("--user")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1884113221:
                        if (nextOption.equals("--versionCode")) {
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
                        i3 = 1;
                        break;
                    case 1:
                        i4 = android.os.UserHandle.parseUserArg(getNextArgRequired());
                        if (i4 != -1 && i4 != -2 && ((com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class)).getUserInfo(i4) == null) {
                            outPrintWriter.println("Failure [user " + i4 + " doesn't exist]");
                            return 1;
                        }
                        break;
                    case 2:
                        j = java.lang.Long.parseLong(getNextArgRequired());
                        break;
                    default:
                        outPrintWriter.println("Error: Unknown option: " + nextOption);
                        return 1;
                }
            } else {
                java.lang.String nextArg = getNextArg();
                if (nextArg == null) {
                    outPrintWriter.println("Error: package name not specified");
                    return 1;
                }
                java.util.ArrayList<java.lang.String> remainingArgs = getRemainingArgs();
                if (!remainingArgs.isEmpty()) {
                    return runRemoveSplits(nextArg, remainingArgs);
                }
                if (i4 != -1) {
                    i = i3;
                } else {
                    i = i3 | 2;
                }
                int translateUserId = translateUserId(i4, 0, "runUninstall");
                com.android.server.pm.PackageManagerShellCommand.LocalIntentReceiver localIntentReceiver = new com.android.server.pm.PackageManagerShellCommand.LocalIntentReceiver();
                android.content.pm.PackageManagerInternal packageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
                if (packageManagerInternal.isApexPackage(nextArg)) {
                    packageManagerInternal.uninstallApex(nextArg, j, translateUserId, localIntentReceiver.getIntentSender(), i);
                    str = "]";
                } else {
                    if ((i & 2) == 0) {
                        android.content.pm.PackageInfo packageInfo = this.mInterface.getPackageInfo(nextArg, 67108864L, translateUserId);
                        if (packageInfo == null) {
                            outPrintWriter.println("Failure [not installed for " + translateUserId + "]");
                            return 1;
                        }
                        if ((packageInfo.applicationInfo.flags & 1) != 0) {
                            i2 = i | 4;
                            str = "]";
                            this.mInterface.getPackageInstaller().uninstall(new android.content.pm.VersionedPackage(nextArg, j), (java.lang.String) null, i2, localIntentReceiver.getIntentSender(), translateUserId);
                        }
                    }
                    i2 = i;
                    str = "]";
                    this.mInterface.getPackageInstaller().uninstall(new android.content.pm.VersionedPackage(nextArg, j), (java.lang.String) null, i2, localIntentReceiver.getIntentSender(), translateUserId);
                }
                android.content.Intent result = localIntentReceiver.getResult();
                if (result.getIntExtra("android.content.pm.extra.STATUS", 1) == 0) {
                    outPrintWriter.println("Success");
                    return 0;
                }
                outPrintWriter.println("Failure [" + result.getStringExtra("android.content.pm.extra.STATUS_MESSAGE") + str);
                return 1;
            }
        }
    }

    private int runRemoveSplits(java.lang.String str, java.util.Collection<java.lang.String> collection) throws android.os.RemoteException {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        android.content.pm.PackageInstaller.SessionParams sessionParams = new android.content.pm.PackageInstaller.SessionParams(2);
        sessionParams.installFlags = 2 | sessionParams.installFlags;
        sessionParams.appPackageName = str;
        int doCreateSession = doCreateSession(sessionParams, null, -1);
        boolean z = true;
        try {
            if (doRemoveSplits(doCreateSession, collection, false) != 0) {
                try {
                    doAbandonSession(doCreateSession, false);
                } catch (java.lang.RuntimeException e) {
                }
                return 1;
            }
            if (doCommitSession(doCreateSession, false) != 0) {
                try {
                    doAbandonSession(doCreateSession, false);
                } catch (java.lang.RuntimeException e2) {
                }
                return 1;
            }
            try {
                outPrintWriter.println("Success");
                return 0;
            } catch (java.lang.Throwable th) {
                th = th;
                z = false;
                if (z) {
                    try {
                        doAbandonSession(doCreateSession, false);
                    } catch (java.lang.RuntimeException e3) {
                    }
                }
                throw th;
            }
        } catch (java.lang.Throwable th2) {
            th = th2;
        }
    }

    static class ClearDataObserver extends android.content.pm.IPackageDataObserver.Stub {
        boolean finished;
        boolean result;

        ClearDataObserver() {
        }

        public void onRemoveCompleted(java.lang.String str, boolean z) throws android.os.RemoteException {
            synchronized (this) {
                this.finished = true;
                this.result = z;
                notifyAll();
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int runClear() throws android.os.RemoteException {
        char c;
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        int i = 0;
        boolean z = false;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                switch (nextOption.hashCode()) {
                    case -2056884041:
                        if (nextOption.equals("--cache-only")) {
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
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                        i = android.os.UserHandle.parseUserArg(getNextArgRequired());
                        break;
                    case 1:
                        z = true;
                        break;
                    default:
                        outPrintWriter.println("Error: Unknown option: " + nextOption);
                        return 1;
                }
            } else {
                java.lang.String nextArg = getNextArg();
                if (nextArg == null) {
                    getErrPrintWriter().println("Error: no package specified");
                    return 1;
                }
                int translateUserId = translateUserId(i, com.android.server.am.ProcessList.INVALID_ADJ, "runClear");
                com.android.server.pm.PackageManagerShellCommand.ClearDataObserver clearDataObserver = new com.android.server.pm.PackageManagerShellCommand.ClearDataObserver();
                if (!z) {
                    android.app.ActivityManager.getService().clearApplicationUserData(nextArg, false, clearDataObserver, translateUserId);
                } else {
                    this.mInterface.deleteApplicationCacheFilesAsUser(nextArg, translateUserId, clearDataObserver);
                }
                synchronized (clearDataObserver) {
                    while (!clearDataObserver.finished) {
                        try {
                            clearDataObserver.wait();
                        } catch (java.lang.InterruptedException e) {
                        }
                    }
                }
                if (clearDataObserver.result) {
                    getOutPrintWriter().println("Success");
                    return 0;
                }
                getErrPrintWriter().println("Failed");
                return 1;
            }
        }
    }

    private static java.lang.String enabledSettingToString(int i) {
        switch (i) {
            case 0:
                return "default";
            case 1:
                return com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_ENABLED;
            case 2:
                return com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_DISABLED;
            case 3:
                return "disabled-user";
            case 4:
                return "disabled-until-used";
            default:
                return "unknown";
        }
    }

    private int runSetEnabledSetting(int i) throws android.os.RemoteException {
        int i2;
        java.lang.String nextOption = getNextOption();
        if (nextOption != null && nextOption.equals("--user")) {
            i2 = android.os.UserHandle.parseUserArg(getNextArgRequired());
        } else {
            i2 = 0;
        }
        java.lang.String nextArg = getNextArg();
        if (nextArg != null) {
            int translateUserId = translateUserId(i2, com.android.server.am.ProcessList.INVALID_ADJ, "runSetEnabledSetting");
            android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(nextArg);
            if (unflattenFromString == null) {
                this.mInterface.setApplicationEnabledSetting(nextArg, i, 0, translateUserId, "shell:" + android.os.Process.myUid());
                getOutPrintWriter().println("Package " + nextArg + " new state: " + enabledSettingToString(this.mInterface.getApplicationEnabledSetting(nextArg, translateUserId)));
                return 0;
            }
            this.mInterface.setComponentEnabledSetting(unflattenFromString, i, 0, translateUserId, "shell");
            getOutPrintWriter().println("Component " + unflattenFromString.toShortString() + " new state: " + enabledSettingToString(this.mInterface.getComponentEnabledSetting(unflattenFromString, translateUserId)));
            return 0;
        }
        getErrPrintWriter().println("Error: no package or component specified");
        return 1;
    }

    private int runSetHiddenSetting(boolean z) throws android.os.RemoteException {
        int i;
        java.lang.String nextOption = getNextOption();
        if (nextOption != null && nextOption.equals("--user")) {
            i = android.os.UserHandle.parseUserArg(getNextArgRequired());
        } else {
            i = 0;
        }
        java.lang.String nextArg = getNextArg();
        if (nextArg == null) {
            getErrPrintWriter().println("Error: no package or component specified");
            return 1;
        }
        int translateUserId = translateUserId(i, com.android.server.am.ProcessList.INVALID_ADJ, "runSetHiddenSetting");
        this.mInterface.setApplicationHiddenSettingAsUser(nextArg, z, translateUserId);
        getOutPrintWriter().println("Package " + nextArg + " new hidden state: " + this.mInterface.getApplicationHiddenSettingAsUser(nextArg, translateUserId));
        return 0;
    }

    private int runSetStoppedState(boolean z) throws android.os.RemoteException {
        int i;
        java.lang.String nextOption = getNextOption();
        if (nextOption != null && nextOption.equals("--user")) {
            i = android.os.UserHandle.parseUserArg(getNextArgRequired());
        } else {
            i = 0;
        }
        java.lang.String nextArg = getNextArg();
        if (nextArg == null) {
            getErrPrintWriter().println("Error: no package specified");
            return 1;
        }
        int translateUserId = translateUserId(i, com.android.server.am.ProcessList.INVALID_ADJ, "runSetStoppedState");
        this.mInterface.setPackageStoppedState(nextArg, z, translateUserId);
        getOutPrintWriter().println("Package " + nextArg + " new stopped state: " + this.mInterface.isPackageStoppedForUser(nextArg, translateUserId));
        return 0;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int runSetDistractingRestriction() {
        boolean z;
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        int i = 0;
        int i2 = 0;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                char c = 65535;
                switch (nextOption.hashCode()) {
                    case 1333015820:
                        if (nextOption.equals("--flag")) {
                            z = true;
                            break;
                        }
                        z = -1;
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
                        i = android.os.UserHandle.parseUserArg(getNextArgRequired());
                        break;
                    case true:
                        java.lang.String nextArgRequired = getNextArgRequired();
                        switch (nextArgRequired.hashCode()) {
                            case -2125559907:
                                if (nextArgRequired.equals("hide-notifications")) {
                                    c = 0;
                                    break;
                                }
                                break;
                            case -1852537225:
                                if (nextArgRequired.equals("hide-from-suggestions")) {
                                    c = 1;
                                    break;
                                }
                                break;
                        }
                        switch (c) {
                            case 0:
                                i2 |= 2;
                                break;
                            case 1:
                                i2 |= 1;
                                break;
                            default:
                                outPrintWriter.println("Unrecognized flag: " + nextArgRequired);
                                return 1;
                        }
                    default:
                        outPrintWriter.println("Error: Unknown option: " + nextOption);
                        return 1;
                }
            } else {
                java.util.ArrayList<java.lang.String> remainingArgs = getRemainingArgs();
                if (remainingArgs.isEmpty()) {
                    outPrintWriter.println("Error: package name not specified");
                    return 1;
                }
                try {
                    java.lang.String[] distractingPackageRestrictionsAsUser = this.mInterface.setDistractingPackageRestrictionsAsUser((java.lang.String[]) remainingArgs.toArray(new java.lang.String[0]), i2, translateUserId(i, com.android.server.am.ProcessList.INVALID_ADJ, "set-distracting"));
                    if (distractingPackageRestrictionsAsUser.length <= 0) {
                        return 0;
                    }
                    outPrintWriter.println("Could not set restriction for: " + java.util.Arrays.toString(distractingPackageRestrictionsAsUser));
                    return 1;
                } catch (android.os.RemoteException | java.lang.IllegalArgumentException e) {
                    outPrintWriter.println(e.toString());
                    return 1;
                }
            }
        }
    }

    private int runGetDistractingRestriction() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        int i = 0;
        while (true) {
            java.lang.String nextOption = getNextOption();
            char c = 65535;
            if (nextOption != null) {
                switch (nextOption.hashCode()) {
                    case 1333469547:
                        if (nextOption.equals("--user")) {
                            c = 0;
                            break;
                        }
                        break;
                }
                switch (c) {
                    case 0:
                        i = android.os.UserHandle.parseUserArg(getNextArgRequired());
                    default:
                        outPrintWriter.println("Error: Unknown option: " + nextOption);
                        return 1;
                }
            } else {
                java.util.ArrayList<java.lang.String> remainingArgs = getRemainingArgs();
                if (remainingArgs.isEmpty()) {
                    outPrintWriter.println("Error: package name not specified");
                    return 1;
                }
                outPrintWriter.println("Distracting restrictions state for user " + i);
                int translateUserId = translateUserId(i, com.android.server.am.ProcessList.INVALID_ADJ, "get-distracting");
                java.lang.String[] strArr = (java.lang.String[]) remainingArgs.toArray(new java.lang.String[0]);
                int[] distractingPackageRestrictionsAsUser = this.mPm.getDistractingPackageRestrictionsAsUser(strArr, translateUserId);
                for (int i2 = 0; i2 < distractingPackageRestrictionsAsUser.length; i2++) {
                    int i3 = distractingPackageRestrictionsAsUser[i2];
                    if (i3 == -1) {
                        outPrintWriter.println(strArr[i2] + " not found ...");
                    } else {
                        outPrintWriter.println(strArr[i2] + "  state: " + stateToString(i3));
                    }
                }
                return 0;
            }
        }
    }

    private static java.lang.String stateToString(int i) {
        switch (i) {
            case 0:
                return "NONE";
            case 1:
                return "HIDE_FROM_SUGGESTIONS";
            case 2:
                return "HIDE_NOTIFICATIONS";
            default:
                return "UNKNOWN";
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int runSuspend(boolean z, int i) {
        android.content.pm.SuspendDialogInfo suspendDialogInfo;
        char c;
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        android.os.PersistableBundle persistableBundle = new android.os.PersistableBundle();
        android.os.PersistableBundle persistableBundle2 = new android.os.PersistableBundle();
        int i2 = 0;
        java.lang.String str = null;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                switch (nextOption.hashCode()) {
                    case -39471105:
                        if (nextOption.equals("--dialogMessage")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 42995488:
                        if (nextOption.equals("--aed")) {
                            c = 4;
                            break;
                        }
                        c = 65535;
                        break;
                    case 42995496:
                        if (nextOption.equals("--ael")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 42995503:
                        if (nextOption.equals("--aes")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    case 43006059:
                        if (nextOption.equals("--led")) {
                            c = 7;
                            break;
                        }
                        c = 65535;
                        break;
                    case 43006067:
                        if (nextOption.equals("--lel")) {
                            c = 5;
                            break;
                        }
                        c = 65535;
                        break;
                    case 43006074:
                        if (nextOption.equals("--les")) {
                            c = 6;
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
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                        i2 = android.os.UserHandle.parseUserArg(getNextArgRequired());
                        break;
                    case 1:
                        str = getNextArgRequired();
                        break;
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                        java.lang.String nextArgRequired = getNextArgRequired();
                        java.lang.String nextArgRequired2 = getNextArgRequired();
                        if (z) {
                            android.os.PersistableBundle persistableBundle3 = nextOption.startsWith("--a") ? persistableBundle : persistableBundle2;
                            switch (nextOption.charAt(4)) {
                                case 'd':
                                    persistableBundle3.putDouble(nextArgRequired, java.lang.Double.valueOf(nextArgRequired2).doubleValue());
                                    break;
                                case 'l':
                                    persistableBundle3.putLong(nextArgRequired, java.lang.Long.valueOf(nextArgRequired2).longValue());
                                    break;
                                case 's':
                                    persistableBundle3.putString(nextArgRequired, nextArgRequired2);
                                    break;
                            }
                        } else {
                            break;
                        }
                        break;
                    default:
                        outPrintWriter.println("Error: Unknown option: " + nextOption);
                        return 1;
                }
            } else {
                java.util.ArrayList<java.lang.String> remainingArgs = getRemainingArgs();
                if (remainingArgs.isEmpty()) {
                    outPrintWriter.println("Error: package name not specified");
                    return 1;
                }
                java.lang.String str2 = android.os.Binder.getCallingUid() == 0 ? "root" : com.android.server.vibrator.VibratorManagerService.VibratorManagerShellCommand.SHELL_PACKAGE_NAME;
                if (!android.text.TextUtils.isEmpty(str)) {
                    suspendDialogInfo = new android.content.pm.SuspendDialogInfo.Builder().setMessage(str).build();
                } else {
                    suspendDialogInfo = null;
                }
                try {
                    int translateUserId = translateUserId(i2, com.android.server.am.ProcessList.INVALID_ADJ, "runSuspend");
                    this.mInterface.setPackagesSuspendedAsUser((java.lang.String[]) remainingArgs.toArray(new java.lang.String[0]), z, persistableBundle.size() > 0 ? persistableBundle : null, persistableBundle2.size() > 0 ? persistableBundle2 : null, suspendDialogInfo, i, str2, 0, translateUserId);
                    for (int i3 = 0; i3 < remainingArgs.size(); i3++) {
                        java.lang.String str3 = remainingArgs.get(i3);
                        outPrintWriter.println("Package " + str3 + " new suspended state: " + this.mInterface.isPackageSuspendedForUser(str3, translateUserId));
                    }
                    return 0;
                } catch (android.os.RemoteException | java.lang.IllegalArgumentException e) {
                    outPrintWriter.println(e.toString());
                    return 1;
                }
            }
        }
    }

    private int runGrantRevokePermission(boolean z) throws android.os.RemoteException {
        java.util.List<android.content.pm.PackageInfo> singletonList;
        boolean z2 = false;
        int i = 0;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption == null) {
                break;
            }
            if (nextOption.equals("--user")) {
                i = android.os.UserHandle.parseUserArg(getNextArgRequired());
            }
            if (nextOption.equals("--all-permissions")) {
                z2 = true;
            }
        }
        java.lang.String nextArg = getNextArg();
        if (!z2 && nextArg == null) {
            getErrPrintWriter().println("Error: no package specified");
            return 1;
        }
        java.lang.String nextArg2 = getNextArg();
        if (!z2 && nextArg2 == null) {
            getErrPrintWriter().println("Error: no permission specified");
            return 1;
        }
        if (z2 && nextArg2 != null) {
            getErrPrintWriter().println("Error: permission specified but not expected");
            return 1;
        }
        android.os.UserHandle of = android.os.UserHandle.of(translateUserId(i, com.android.server.am.ProcessList.INVALID_ADJ, "runGrantRevokePermission"));
        android.content.pm.PackageManager packageManager = this.mContext.createContextAsUser(of, 0).getPackageManager();
        if (nextArg == null) {
            singletonList = packageManager.getInstalledPackages(4096);
        } else {
            try {
                singletonList = java.util.Collections.singletonList(packageManager.getPackageInfo(nextArg, 4096));
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                getErrPrintWriter().println("Error: package not found");
                getOutPrintWriter().println("Failure [package not found]");
                return 1;
            }
        }
        for (android.content.pm.PackageInfo packageInfo : singletonList) {
            java.util.List<java.lang.String> singletonList2 = java.util.Collections.singletonList(nextArg2);
            if (z2) {
                singletonList2 = getRequestedRuntimePermissions(packageInfo);
            }
            for (java.lang.String str : singletonList2) {
                if (z) {
                    try {
                        this.mPermissionManager.grantRuntimePermission(packageInfo.packageName, str, of);
                    } catch (java.lang.Exception e2) {
                        if (!z2) {
                            throw e2;
                        }
                        android.util.Slog.w(TAG, "Could not grant permission " + str, e2);
                    }
                } else {
                    try {
                        this.mPermissionManager.revokeRuntimePermission(packageInfo.packageName, str, of, (java.lang.String) null);
                    } catch (java.lang.Exception e3) {
                        if (!z2) {
                            throw e3;
                        }
                        android.util.Slog.w(TAG, "Could not grant permission " + str, e3);
                    }
                }
            }
        }
        return 0;
    }

    private java.util.List<java.lang.String> getRequestedRuntimePermissions(android.content.pm.PackageInfo packageInfo) {
        android.content.pm.PermissionInfo permissionInfo;
        if (packageInfo.requestedPermissions == null) {
            return new java.util.ArrayList();
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
        for (java.lang.String str : packageInfo.requestedPermissions) {
            try {
                permissionInfo = packageManager.getPermissionInfo(str, 0);
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                permissionInfo = null;
            }
            if (permissionInfo != null && permissionInfo.getProtection() == 1) {
                arrayList.add(str);
            }
        }
        return arrayList;
    }

    private int runResetPermissions() throws android.os.RemoteException {
        this.mLegacyPermissionManager.resetRuntimePermissions();
        return 0;
    }

    private int setOrClearPermissionFlags(boolean z) {
        int i = 0;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption == null) {
                break;
            }
            if (nextOption.equals("--user")) {
                i = android.os.UserHandle.parseUserArg(getNextArgRequired());
            }
        }
        java.lang.String nextArg = getNextArg();
        if (nextArg == null) {
            getErrPrintWriter().println("Error: no package specified");
            return 1;
        }
        java.lang.String nextArg2 = getNextArg();
        if (nextArg2 == null) {
            getErrPrintWriter().println("Error: no permission specified");
            return 1;
        }
        java.lang.String nextArg3 = getNextArg();
        if (nextArg3 != null) {
            int i2 = 0;
            while (nextArg3 != null) {
                if (!SUPPORTED_PERMISSION_FLAGS.containsKey(nextArg3)) {
                    getErrPrintWriter().println("Error: specified flag " + nextArg3 + " is not one of " + SUPPORTED_PERMISSION_FLAGS_LIST);
                    return 1;
                }
                i2 |= SUPPORTED_PERMISSION_FLAGS.get(nextArg3).intValue();
                nextArg3 = getNextArg();
            }
            this.mPermissionManager.updatePermissionFlags(nextArg, nextArg2, i2, z ? i2 : 0, android.os.UserHandle.of(translateUserId(i, com.android.server.am.ProcessList.INVALID_ADJ, "runGrantRevokePermission")));
            return 0;
        }
        getErrPrintWriter().println("Error: no permission flags specified");
        return 1;
    }

    private int runSetPermissionEnforced() throws android.os.RemoteException {
        if (getNextArg() == null) {
            getErrPrintWriter().println("Error: no permission specified");
            return 1;
        }
        if (getNextArg() == null) {
            getErrPrintWriter().println("Error: no enforcement specified");
            return 1;
        }
        return 0;
    }

    private boolean isVendorApp(java.lang.String str) {
        try {
            android.content.pm.PackageInfo packageInfo = this.mInterface.getPackageInfo(str, 4194304L, 0);
            if (packageInfo != null) {
                return packageInfo.applicationInfo.isVendor();
            }
            return false;
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    private boolean isProductApp(java.lang.String str) {
        try {
            android.content.pm.PackageInfo packageInfo = this.mInterface.getPackageInfo(str, 4194304L, 0);
            if (packageInfo != null) {
                return packageInfo.applicationInfo.isProduct();
            }
            return false;
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    private boolean isSystemExtApp(java.lang.String str) {
        try {
            android.content.pm.PackageInfo packageInfo = this.mInterface.getPackageInfo(str, 4194304L, 0);
            if (packageInfo != null) {
                return packageInfo.applicationInfo.isSystemExt();
            }
            return false;
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    private java.lang.String getApexPackageNameContainingPackage(java.lang.String str) {
        return com.android.server.pm.ApexManager.getInstance().getActiveApexPackageNameContainingPackage(str);
    }

    private boolean isApexApp(java.lang.String str) {
        return getApexPackageNameContainingPackage(str) != null;
    }

    private int runGetPrivappPermissions() {
        java.lang.String nextArg = getNextArg();
        if (nextArg != null) {
            getOutPrintWriter().println(getPrivAppPermissionsString(nextArg, true));
            return 0;
        }
        getErrPrintWriter().println("Error: no package specified.");
        return 1;
    }

    private int runGetPrivappDenyPermissions() {
        java.lang.String nextArg = getNextArg();
        if (nextArg == null) {
            getErrPrintWriter().println("Error: no package specified.");
            return 1;
        }
        getOutPrintWriter().println(getPrivAppPermissionsString(nextArg, false));
        return 0;
    }

    @android.annotation.NonNull
    private java.lang.String getPrivAppPermissionsString(@android.annotation.NonNull java.lang.String str, boolean z) {
        android.util.ArrayMap<java.lang.String, android.util.ArrayMap<java.lang.String, java.lang.Boolean>> privilegedAppAllowlist;
        com.android.server.pm.permission.PermissionAllowlist permissionAllowlist = com.android.server.SystemConfig.getInstance().getPermissionAllowlist();
        if (isVendorApp(str)) {
            privilegedAppAllowlist = permissionAllowlist.getVendorPrivilegedAppAllowlist();
        } else if (isProductApp(str)) {
            privilegedAppAllowlist = permissionAllowlist.getProductPrivilegedAppAllowlist();
        } else if (isSystemExtApp(str)) {
            privilegedAppAllowlist = permissionAllowlist.getSystemExtPrivilegedAppAllowlist();
        } else if (isApexApp(str)) {
            privilegedAppAllowlist = permissionAllowlist.getApexPrivilegedAppAllowlists().get(com.android.server.pm.ApexManager.getInstance().getApexModuleNameForPackageName(getApexPackageNameContainingPackage(str)));
        } else {
            privilegedAppAllowlist = permissionAllowlist.getPrivilegedAppAllowlist();
        }
        android.util.ArrayMap<java.lang.String, java.lang.Boolean> arrayMap = privilegedAppAllowlist != null ? privilegedAppAllowlist.get(str) : null;
        if (arrayMap == null) {
            return "{}";
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder("{");
        int size = arrayMap.size();
        boolean z2 = true;
        for (int i = 0; i < size; i++) {
            if (arrayMap.valueAt(i).booleanValue() == z) {
                if (z2) {
                    z2 = false;
                } else {
                    sb.append(", ");
                }
                sb.append(arrayMap.keyAt(i));
            }
        }
        sb.append("}");
        return sb.toString();
    }

    private int runGetOemPermissions() {
        java.lang.String nextArg = getNextArg();
        if (nextArg == null) {
            getErrPrintWriter().println("Error: no package specified.");
            return 1;
        }
        android.util.ArrayMap<java.lang.String, java.lang.Boolean> arrayMap = com.android.server.SystemConfig.getInstance().getPermissionAllowlist().getOemAppAllowlist().get(nextArg);
        if (arrayMap == null || arrayMap.isEmpty()) {
            getOutPrintWriter().println("{}");
            return 0;
        }
        arrayMap.forEach(new java.util.function.BiConsumer() { // from class: com.android.server.pm.PackageManagerShellCommand$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                com.android.server.pm.PackageManagerShellCommand.this.lambda$runGetOemPermissions$3((java.lang.String) obj, (java.lang.Boolean) obj2);
            }
        });
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$runGetOemPermissions$3(java.lang.String str, java.lang.Boolean bool) {
        getOutPrintWriter().println(str + " granted:" + bool);
    }

    private int runTrimCaches() throws android.os.RemoteException {
        long j;
        long j2;
        java.lang.String str;
        java.lang.String nextArg = getNextArg();
        if (nextArg == null) {
            getErrPrintWriter().println("Error: no size specified");
            return 1;
        }
        int length = nextArg.length() - 1;
        char charAt = nextArg.charAt(length);
        if (charAt < '0' || charAt > '9') {
            if (charAt == 'K' || charAt == 'k') {
                j = 1024;
            } else if (charAt == 'M' || charAt == 'm') {
                j = 1048576;
            } else if (charAt == 'G' || charAt == 'g') {
                j = 1073741824;
            } else {
                getErrPrintWriter().println("Invalid suffix: " + charAt);
                return 1;
            }
            nextArg = nextArg.substring(0, length);
            j2 = j;
        } else {
            j2 = 1;
        }
        try {
            long parseLong = java.lang.Long.parseLong(nextArg) * j2;
            java.lang.String nextArg2 = getNextArg();
            if (!"internal".equals(nextArg2)) {
                str = nextArg2;
            } else {
                str = null;
            }
            com.android.server.pm.PackageManagerShellCommand.ClearDataObserver clearDataObserver = new com.android.server.pm.PackageManagerShellCommand.ClearDataObserver();
            this.mInterface.freeStorageAndNotify(str, parseLong, 2, clearDataObserver);
            synchronized (clearDataObserver) {
                while (!clearDataObserver.finished) {
                    try {
                        clearDataObserver.wait();
                    } catch (java.lang.InterruptedException e) {
                    }
                }
            }
            return 0;
        } catch (java.lang.NumberFormatException e2) {
            getErrPrintWriter().println("Error: expected number at: " + nextArg);
            return 1;
        }
    }

    private static boolean isNumber(java.lang.String str) {
        try {
            java.lang.Integer.parseInt(str);
            return true;
        } catch (java.lang.NumberFormatException e) {
            return false;
        }
    }

    public int runCreateUser() throws android.os.RemoteException {
        java.lang.String str;
        java.lang.String nextArgRequired;
        android.content.pm.UserInfo userInfo = null;
        int i = -1;
        boolean z = false;
        int i2 = 0;
        java.lang.String str2 = null;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                if ("--profileOf".equals(nextOption)) {
                    i = translateUserId(android.os.UserHandle.parseUserArg(getNextArgRequired()), -1, "runCreateUser");
                    nextArgRequired = null;
                } else if ("--managed".equals(nextOption)) {
                    nextArgRequired = "android.os.usertype.profile.MANAGED";
                } else if ("--restricted".equals(nextOption)) {
                    nextArgRequired = "android.os.usertype.full.RESTRICTED";
                } else if ("--guest".equals(nextOption)) {
                    nextArgRequired = "android.os.usertype.full.GUEST";
                } else if ("--demo".equals(nextOption)) {
                    nextArgRequired = "android.os.usertype.full.DEMO";
                } else if ("--ephemeral".equals(nextOption)) {
                    i2 |= 256;
                    nextArgRequired = null;
                } else if ("--for-testing".equals(nextOption)) {
                    i2 |= 32768;
                    nextArgRequired = null;
                } else if ("--pre-create-only".equals(nextOption)) {
                    nextArgRequired = null;
                    z = true;
                } else if ("--user-type".equals(nextOption)) {
                    nextArgRequired = getNextArgRequired();
                } else {
                    getErrPrintWriter().println("Error: unknown option " + nextOption);
                    return 1;
                }
                if (nextArgRequired != null) {
                    if (str2 != null && !str2.equals(nextArgRequired)) {
                        getErrPrintWriter().println("Error: more than one user type was specified (" + str2 + " and " + nextArgRequired + ")");
                        return 1;
                    }
                    str2 = nextArgRequired;
                }
            } else {
                java.lang.String nextArg = getNextArg();
                if (nextArg == null && !z) {
                    getErrPrintWriter().println("Error: no user name specified.");
                    return 1;
                }
                if (nextArg != null && z) {
                    getErrPrintWriter().println("Warning: name is ignored for pre-created users");
                }
                android.os.IUserManager asInterface = android.os.IUserManager.Stub.asInterface(android.os.ServiceManager.getService("user"));
                android.accounts.IAccountManager asInterface2 = android.accounts.IAccountManager.Stub.asInterface(android.os.ServiceManager.getService("account"));
                if (str2 != null) {
                    str = str2;
                } else {
                    str = android.content.pm.UserInfo.getDefaultUserType(i2);
                }
                android.os.Trace.traceBegin(262144L, "shell_runCreateUser");
                try {
                    try {
                        if (android.os.UserManager.isUserTypeRestricted(str)) {
                            int i3 = i >= 0 ? i : 0;
                            userInfo = asInterface.createRestrictedProfileWithThrow(nextArg, i3);
                            asInterface2.addSharedAccountsFromParentUser(i3, i, android.os.Process.myUid() == 0 ? "root" : com.android.server.vibrator.VibratorManagerService.VibratorManagerShellCommand.SHELL_PACKAGE_NAME);
                        } else if (i < 0) {
                            if (z) {
                                userInfo = asInterface.preCreateUserWithThrow(str);
                            } else {
                                userInfo = asInterface.createUserWithThrow(nextArg, str, i2);
                            }
                        } else {
                            userInfo = asInterface.createProfileForUserWithThrow(nextArg, str, i2, i, (java.lang.String[]) null);
                        }
                    } catch (android.os.ServiceSpecificException e) {
                        getErrPrintWriter().println("Error: " + e);
                    }
                    if (userInfo != null) {
                        getOutPrintWriter().println("Success: created user id " + userInfo.id);
                        return 0;
                    }
                    getErrPrintWriter().println("Error: couldn't create User.");
                    return 1;
                } finally {
                    android.os.Trace.traceEnd(262144L);
                }
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public int runRemoveUser() throws android.os.RemoteException {
        char c;
        boolean z = false;
        boolean z2 = false;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                switch (nextOption.hashCode()) {
                    case -1095309356:
                        if (nextOption.equals("--set-ephemeral-if-in-use")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1514:
                        if (nextOption.equals("-w")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1333511957:
                        if (nextOption.equals("--wait")) {
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
                        z = true;
                        break;
                    case 1:
                    case 2:
                        z2 = true;
                        break;
                    default:
                        getErrPrintWriter().println("Error: unknown option: " + nextOption);
                        return -1;
                }
            } else {
                java.lang.String nextArg = getNextArg();
                if (nextArg == null) {
                    getErrPrintWriter().println("Error: no user id specified.");
                    return 1;
                }
                int parseUserArg = android.os.UserHandle.parseUserArg(nextArg);
                android.os.IUserManager asInterface = android.os.IUserManager.Stub.asInterface(android.os.ServiceManager.getService("user"));
                if (z) {
                    return removeUserWhenPossible(asInterface, parseUserArg);
                }
                if (!(z2 ? removeUserAndWait(asInterface, parseUserArg) : removeUser(asInterface, parseUserArg))) {
                    return 1;
                }
                getOutPrintWriter().println("Success: removed user");
                return 0;
            }
        }
    }

    private boolean removeUser(android.os.IUserManager iUserManager, int i) throws android.os.RemoteException {
        android.util.Slog.i(TAG, "Removing user " + i);
        if (iUserManager.removeUser(i)) {
            return true;
        }
        getErrPrintWriter().println("Error: couldn't remove user id " + i);
        return false;
    }

    private boolean removeUserAndWait(android.os.IUserManager iUserManager, final int i) throws android.os.RemoteException {
        android.util.Slog.i(TAG, "Removing (and waiting for completion) user " + i);
        final java.util.concurrent.CountDownLatch countDownLatch = new java.util.concurrent.CountDownLatch(1);
        com.android.server.pm.UserManagerInternal.UserLifecycleListener userLifecycleListener = new com.android.server.pm.UserManagerInternal.UserLifecycleListener() { // from class: com.android.server.pm.PackageManagerShellCommand.4
            @Override // com.android.server.pm.UserManagerInternal.UserLifecycleListener
            public void onUserRemoved(android.content.pm.UserInfo userInfo) {
                if (i == userInfo.id) {
                    countDownLatch.countDown();
                }
            }
        };
        com.android.server.pm.UserManagerInternal userManagerInternal = (com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class);
        userManagerInternal.addUserLifecycleListener(userLifecycleListener);
        try {
            if (iUserManager.removeUser(i)) {
                if (countDownLatch.await(10L, java.util.concurrent.TimeUnit.MINUTES)) {
                    return true;
                }
                getErrPrintWriter().printf("Error: Remove user %d timed out\n", java.lang.Integer.valueOf(i));
                return false;
            }
            getErrPrintWriter().println("Error: couldn't remove user id " + i);
            return false;
        } catch (java.lang.InterruptedException e) {
            getErrPrintWriter().printf("Error: Remove user %d wait interrupted: %s\n", java.lang.Integer.valueOf(i), e);
            java.lang.Thread.currentThread().interrupt();
            return false;
        } finally {
            userManagerInternal.removeUserLifecycleListener(userLifecycleListener);
        }
    }

    private int removeUserWhenPossible(android.os.IUserManager iUserManager, int i) throws android.os.RemoteException {
        android.util.Slog.i(TAG, "Removing " + i + " or set as ephemeral if in use.");
        switch (iUserManager.removeUserWhenPossible(i, false)) {
            case -5:
                getErrPrintWriter().printf("Error: user %d is a permanent admin main user\n", java.lang.Integer.valueOf(i));
                return 1;
            case 0:
                getOutPrintWriter().printf("Success: user %d removed\n", java.lang.Integer.valueOf(i));
                return 0;
            case 1:
                getOutPrintWriter().printf("Success: user %d set as ephemeral\n", java.lang.Integer.valueOf(i));
                return 0;
            case 2:
                getOutPrintWriter().printf("Success: user %d is already being removed\n", java.lang.Integer.valueOf(i));
                return 0;
            default:
                getErrPrintWriter().printf("Error: couldn't remove or mark ephemeral user id %d\n", java.lang.Integer.valueOf(i));
                return 1;
        }
    }

    private int runMarkGuestForDeletion() throws android.os.RemoteException {
        java.lang.String nextArg = getNextArg();
        if (nextArg == null) {
            getErrPrintWriter().println("Error: no user id specified.");
            return 1;
        }
        if (!android.os.IUserManager.Stub.asInterface(android.os.ServiceManager.getService("user")).markGuestForDeletion(resolveUserId(android.os.UserHandle.parseUserArg(nextArg)))) {
            getErrPrintWriter().println("Error: could not mark guest for deletion");
            return 1;
        }
        return 0;
    }

    private int runRenameUser() throws android.os.RemoteException {
        java.lang.String nextArg = getNextArg();
        if (nextArg == null) {
            getErrPrintWriter().println("Error: no user id specified.");
            return 1;
        }
        int resolveUserId = resolveUserId(android.os.UserHandle.parseUserArg(nextArg));
        java.lang.String nextArg2 = getNextArg();
        if (nextArg2 == null) {
            android.util.Slog.i(TAG, "Resetting name of user " + resolveUserId);
        } else {
            android.util.Slog.i(TAG, "Renaming user " + resolveUserId + " to '" + nextArg2 + "'");
        }
        android.os.IUserManager.Stub.asInterface(android.os.ServiceManager.getService("user")).setUserName(resolveUserId, nextArg2);
        return 0;
    }

    public int runSetUserRestriction() throws android.os.RemoteException {
        int i;
        java.lang.String nextOption = getNextOption();
        if (nextOption != null && "--user".equals(nextOption)) {
            i = android.os.UserHandle.parseUserArg(getNextArgRequired());
        } else {
            i = 0;
        }
        java.lang.String nextArg = getNextArg();
        java.lang.String nextArg2 = getNextArg();
        boolean z = true;
        if (!"1".equals(nextArg2)) {
            if ("0".equals(nextArg2)) {
                z = false;
            } else {
                getErrPrintWriter().println("Error: valid value not specified");
                return 1;
            }
        }
        android.os.IUserManager.Stub.asInterface(android.os.ServiceManager.getService("user")).setUserRestriction(nextArg, z, translateUserId(i, com.android.server.am.ProcessList.INVALID_ADJ, "runSetUserRestriction"));
        return 0;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int runGetUserRestriction() throws android.os.RemoteException {
        char c;
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        int i = 0;
        boolean z = false;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                switch (nextOption.hashCode()) {
                    case 42995713:
                        if (nextOption.equals("--all")) {
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
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                        i = android.os.UserHandle.parseUserArg(getNextArgRequired());
                        break;
                    case 1:
                        if (getNextArg() != null) {
                            throw new java.lang.IllegalArgumentException("Argument unexpected after \"--all\"");
                        }
                        z = true;
                        break;
                    default:
                        throw new java.lang.IllegalArgumentException("Unknown option " + nextOption);
                }
            } else {
                int translateUserId = translateUserId(i, com.android.server.am.ProcessList.INVALID_ADJ, "runGetUserRestriction");
                android.os.IUserManager asInterface = android.os.IUserManager.Stub.asInterface(android.os.ServiceManager.getService("user"));
                if (z) {
                    android.os.Bundle userRestrictions = asInterface.getUserRestrictions(translateUserId);
                    outPrintWriter.println("All restrictions:");
                    outPrintWriter.println(userRestrictions.toString());
                } else {
                    java.lang.String nextArg = getNextArg();
                    if (nextArg == null) {
                        throw new java.lang.IllegalArgumentException("No restriction key specified");
                    }
                    if (getNextArg() != null) {
                        throw new java.lang.IllegalArgumentException("Argument unexpected after restriction key");
                    }
                    outPrintWriter.println(asInterface.hasUserRestriction(nextArg, translateUserId));
                }
                return 0;
            }
        }
    }

    public int runSupportsMultipleUsers() {
        getOutPrintWriter().println("Is multiuser supported: " + android.os.UserManager.supportsMultipleUsers());
        return 0;
    }

    public int runGetMaxUsers() {
        getOutPrintWriter().println("Maximum supported users: " + android.os.UserManager.getMaxSupportedUsers());
        return 0;
    }

    public int runGetMaxRunningUsers() {
        android.app.ActivityManagerInternal activityManagerInternal = (android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class);
        getOutPrintWriter().println("Maximum supported running users: " + activityManagerInternal.getMaxRunningUsers());
        return 0;
    }

    private static class InstallParams {
        java.lang.String installerPackageName;
        android.content.pm.PackageInstaller.SessionParams sessionParams;
        long stagedReadyTimeoutMs;
        int userId;

        private InstallParams() {
            this.userId = -1;
            this.stagedReadyTimeoutMs = 60000L;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private com.android.server.pm.PackageManagerShellCommand.InstallParams makeInstallParams(java.util.Set<java.lang.String> set) {
        char c;
        int i;
        android.content.pm.PackageInstaller.SessionParams sessionParams = new android.content.pm.PackageInstaller.SessionParams(1);
        com.android.server.pm.PackageManagerShellCommand.InstallParams installParams = new com.android.server.pm.PackageManagerShellCommand.InstallParams();
        installParams.sessionParams = sessionParams;
        sessionParams.installFlags |= 4194304;
        sessionParams.setPackageSource(1);
        boolean z = true;
        java.lang.Boolean bool = null;
        boolean z2 = false;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                if (set.contains(nextOption)) {
                    throw new java.lang.IllegalArgumentException("Unsupported option " + nextOption);
                }
                switch (nextOption.hashCode()) {
                    case -2119202362:
                        if (nextOption.equals("--non-staged")) {
                            c = 30;
                            break;
                        }
                        c = 65535;
                        break;
                    case -2091380650:
                        if (nextOption.equals("--install-reason")) {
                            c = 22;
                            break;
                        }
                        c = 65535;
                        break;
                    case -2041347087:
                        if (nextOption.equals("--skip-enable")) {
                            c = '#';
                            break;
                        }
                        c = 65535;
                        break;
                    case -1950997763:
                        if (nextOption.equals("--force-uuid")) {
                            c = 24;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1816313368:
                        if (nextOption.equals("--force-non-staged")) {
                            c = 27;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1777984902:
                        if (nextOption.equals("--dont-kill")) {
                            c = '\b';
                            break;
                        }
                        c = 65535;
                        break;
                    case -1313152697:
                        if (nextOption.equals("--install-location")) {
                            c = 21;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1137116608:
                        if (nextOption.equals("--instantapp")) {
                            c = 17;
                            break;
                        }
                        c = 65535;
                        break;
                    case -951415743:
                        if (nextOption.equals("--instant")) {
                            c = 16;
                            break;
                        }
                        c = 65535;
                        break;
                    case -706813505:
                        if (nextOption.equals("--referrer")) {
                            c = '\n';
                            break;
                        }
                        c = 65535;
                        break;
                    case -653924786:
                        if (nextOption.equals("--enable-rollback")) {
                            c = ' ';
                            break;
                        }
                        c = 65535;
                        break;
                    case -365988597:
                        if (nextOption.equals("--update-ownership")) {
                            c = 23;
                            break;
                        }
                        c = 65535;
                        break;
                    case -170474990:
                        if (nextOption.equals("--multi-package")) {
                            c = 28;
                            break;
                        }
                        c = 65535;
                        break;
                    case -158482320:
                        if (nextOption.equals("--staged-ready-timeout")) {
                            c = '!';
                            break;
                        }
                        c = 65535;
                        break;
                    case 1477:
                        if (nextOption.equals("-R")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1478:
                        if (nextOption.equals("-S")) {
                            c = '\r';
                            break;
                        }
                        c = 65535;
                        break;
                    case 1495:
                        if (nextOption.equals("-d")) {
                            c = 5;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1497:
                        if (nextOption.equals("-f")) {
                            c = 4;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1498:
                        if (nextOption.equals("-g")) {
                            c = 6;
                            break;
                        }
                        c = 65535;
                        break;
                    case android.net.util.NetworkConstants.ETHER_MTU /* 1500 */:
                        if (nextOption.equals("-i")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1507:
                        if (nextOption.equals("-p")) {
                            c = 11;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1509:
                        if (nextOption.equals("-r")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1511:
                        if (nextOption.equals("-t")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    case 42995400:
                        if (nextOption.equals("--abi")) {
                            c = 14;
                            break;
                        }
                        c = 65535;
                        break;
                    case 43010092:
                        if (nextOption.equals("--pkg")) {
                            c = '\f';
                            break;
                        }
                        c = 65535;
                        break;
                    case 50011004:
                        if (nextOption.equals("--bypass-low-target-sdk-block")) {
                            c = '$';
                            break;
                        }
                        c = 65535;
                        break;
                    case 77141024:
                        if (nextOption.equals("--force-queryable")) {
                            c = 31;
                            break;
                        }
                        c = 65535;
                        break;
                    case 148207464:
                        if (nextOption.equals("--originating-uri")) {
                            c = '\t';
                            break;
                        }
                        c = 65535;
                        break;
                    case 458776531:
                        if (nextOption.equals("--ignore-dexopt-profile")) {
                            c = '%';
                            break;
                        }
                        c = 65535;
                        break;
                    case 1051781117:
                        if (nextOption.equals("--ephemeral")) {
                            c = 15;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1067504745:
                        if (nextOption.equals("--preload")) {
                            c = 19;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1332870850:
                        if (nextOption.equals("--apex")) {
                            c = 26;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1333024815:
                        if (nextOption.equals("--full")) {
                            c = 18;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1333469547:
                        if (nextOption.equals("--user")) {
                            c = 20;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1494514835:
                        if (nextOption.equals("--restrict-permissions")) {
                            c = 7;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1507519174:
                        if (nextOption.equals("--staged")) {
                            c = 29;
                            break;
                        }
                        c = 65535;
                        break;
                    case 2015272120:
                        if (nextOption.equals("--force-sdk")) {
                            c = 25;
                            break;
                        }
                        c = 65535;
                        break;
                    case 2037590537:
                        if (nextOption.equals("--skip-verification")) {
                            c = '\"';
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
                    case 25:
                        break;
                    case 1:
                        z = false;
                        break;
                    case 2:
                        installParams.installerPackageName = getNextArg();
                        if (installParams.installerPackageName == null) {
                            throw new java.lang.IllegalArgumentException("Missing installer package");
                        }
                        break;
                    case 3:
                        sessionParams.installFlags |= 4;
                        break;
                    case 4:
                        sessionParams.installFlags |= 16;
                        break;
                    case 5:
                        sessionParams.installFlags |= 128;
                        break;
                    case 6:
                        sessionParams.installFlags |= 256;
                        break;
                    case 7:
                        sessionParams.installFlags &= -4194305;
                        break;
                    case '\b':
                        sessionParams.installFlags |= 4096;
                        break;
                    case '\t':
                        sessionParams.originatingUri = android.net.Uri.parse(getNextArg());
                        break;
                    case '\n':
                        sessionParams.referrerUri = android.net.Uri.parse(getNextArg());
                        break;
                    case 11:
                        sessionParams.mode = 2;
                        sessionParams.appPackageName = getNextArg();
                        if (sessionParams.appPackageName == null) {
                            throw new java.lang.IllegalArgumentException("Missing inherit package name");
                        }
                        break;
                    case '\f':
                        sessionParams.appPackageName = getNextArg();
                        if (sessionParams.appPackageName == null) {
                            throw new java.lang.IllegalArgumentException("Missing package name");
                        }
                        break;
                    case '\r':
                        long parseLong = java.lang.Long.parseLong(getNextArg());
                        if (parseLong <= 0) {
                            throw new java.lang.IllegalArgumentException("Size must be positive");
                        }
                        sessionParams.setSize(parseLong);
                        break;
                    case 14:
                        sessionParams.abiOverride = checkAbiArgument(getNextArg());
                        break;
                    case 15:
                    case 16:
                    case 17:
                        sessionParams.setInstallAsInstantApp(true);
                        break;
                    case 18:
                        sessionParams.setInstallAsInstantApp(false);
                        break;
                    case 19:
                        sessionParams.setInstallAsVirtualPreload();
                        break;
                    case 20:
                        installParams.userId = android.os.UserHandle.parseUserArg(getNextArgRequired());
                        break;
                    case 21:
                        sessionParams.installLocation = java.lang.Integer.parseInt(getNextArg());
                        break;
                    case 22:
                        sessionParams.installReason = java.lang.Integer.parseInt(getNextArg());
                        break;
                    case 23:
                        if (installParams.installerPackageName == null) {
                            installParams.installerPackageName = com.android.server.vibrator.VibratorManagerService.VibratorManagerShellCommand.SHELL_PACKAGE_NAME;
                        }
                        sessionParams.installFlags |= 33554432;
                        break;
                    case 24:
                        sessionParams.installFlags |= 512;
                        sessionParams.volumeUuid = getNextArg();
                        if (!"internal".equals(sessionParams.volumeUuid)) {
                            break;
                        } else {
                            sessionParams.volumeUuid = null;
                            break;
                        }
                    case 26:
                        sessionParams.setInstallAsApex();
                        break;
                    case 27:
                        z2 = true;
                        break;
                    case 28:
                        sessionParams.setMultiPackage();
                        break;
                    case 29:
                        bool = true;
                        break;
                    case 30:
                        bool = false;
                        break;
                    case 31:
                        sessionParams.setForceQueryable();
                        break;
                    case ' ':
                        if (installParams.installerPackageName == null) {
                            installParams.installerPackageName = com.android.server.vibrator.VibratorManagerService.VibratorManagerShellCommand.SHELL_PACKAGE_NAME;
                        }
                        try {
                            i = java.lang.Integer.parseInt(peekNextArg());
                        } catch (java.lang.NumberFormatException e) {
                            i = 0;
                        }
                        if (i < 0 || i > 2) {
                            throw new java.lang.IllegalArgumentException(i + " is not a valid rollback data policy.");
                            break;
                        } else {
                            try {
                                getNextArg();
                            } catch (java.lang.NumberFormatException e2) {
                            }
                            sessionParams.setEnableRollback(true, i);
                            break;
                        }
                        sessionParams.setEnableRollback(true, i);
                    case '!':
                        installParams.stagedReadyTimeoutMs = java.lang.Long.parseLong(getNextArgRequired());
                        break;
                    case '\"':
                        sessionParams.installFlags |= 524288;
                        break;
                    case '#':
                        sessionParams.setApplicationEnabledSettingPersistent();
                        break;
                    case '$':
                        sessionParams.installFlags |= 16777216;
                        break;
                    case '%':
                        sessionParams.installFlags |= 268435456;
                        break;
                    default:
                        throw new java.lang.IllegalArgumentException("Unknown option " + nextOption);
                }
            } else {
                if (bool == null) {
                    bool = java.lang.Boolean.valueOf((sessionParams.installFlags & 131072) != 0);
                }
                if (z) {
                    sessionParams.installFlags |= 2;
                }
                if (z2) {
                    sessionParams.isStaged = false;
                    sessionParams.developmentInstallFlags |= 1;
                } else if (bool.booleanValue()) {
                    sessionParams.setStaged();
                }
                if ((131072 & sessionParams.installFlags) != 0 && (sessionParams.installFlags & 262144) != 0 && sessionParams.rollbackDataPolicy == 1) {
                    throw new java.lang.IllegalArgumentException("Data policy 'wipe' is not supported for apex.");
                }
                return installParams;
            }
        }
    }

    private int runSetHomeActivity() {
        java.lang.String packageName;
        char c;
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        int i = 0;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                switch (nextOption.hashCode()) {
                    case 1333469547:
                        if (nextOption.equals("--user")) {
                            c = 0;
                            break;
                        }
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                        i = android.os.UserHandle.parseUserArg(getNextArgRequired());
                    default:
                        outPrintWriter.println("Error: Unknown option: " + nextOption);
                        return 1;
                }
            } else {
                java.lang.String nextArg = getNextArg();
                if (nextArg.indexOf(47) < 0) {
                    packageName = nextArg;
                } else {
                    android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(nextArg);
                    if (unflattenFromString == null) {
                        outPrintWriter.println("Error: invalid component name");
                        return 1;
                    }
                    packageName = unflattenFromString.getPackageName();
                }
                int translateUserId = translateUserId(i, com.android.server.am.ProcessList.INVALID_ADJ, "runSetHomeActivity");
                final java.util.concurrent.CompletableFuture completableFuture = new java.util.concurrent.CompletableFuture();
                try {
                    android.app.role.RoleManager roleManager = (android.app.role.RoleManager) this.mContext.getSystemService(android.app.role.RoleManager.class);
                    android.os.UserHandle of = android.os.UserHandle.of(translateUserId);
                    java.util.concurrent.Executor executor = com.android.server.FgThread.getExecutor();
                    java.util.Objects.requireNonNull(completableFuture);
                    roleManager.addRoleHolderAsUser("android.app.role.HOME", packageName, 0, of, executor, new java.util.function.Consumer() { // from class: com.android.server.pm.PackageManagerShellCommand$$ExternalSyntheticLambda3
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            completableFuture.complete((java.lang.Boolean) obj);
                        }
                    });
                    if (((java.lang.Boolean) completableFuture.get()).booleanValue()) {
                        outPrintWriter.println("Success");
                        return 0;
                    }
                    outPrintWriter.println("Error: Failed to set default home.");
                    return 1;
                } catch (java.lang.Exception e) {
                    outPrintWriter.println(e.toString());
                    return 1;
                }
            }
        }
    }

    private int runSetInstaller() throws android.os.RemoteException {
        java.lang.String nextArg = getNextArg();
        java.lang.String nextArg2 = getNextArg();
        if (nextArg == null || nextArg2 == null) {
            getErrPrintWriter().println("Must provide both target and installer package names");
            return 1;
        }
        this.mInterface.setInstallerPackageName(nextArg, nextArg2);
        getOutPrintWriter().println("Success");
        return 0;
    }

    private int runGetInstantAppResolver() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        try {
            android.content.ComponentName instantAppResolverComponent = this.mInterface.getInstantAppResolverComponent();
            if (instantAppResolverComponent == null) {
                return 1;
            }
            outPrintWriter.println(instantAppResolverComponent.flattenToString());
            return 0;
        } catch (java.lang.Exception e) {
            outPrintWriter.println(e.toString());
            return 1;
        }
    }

    private int runHasFeature() {
        int parseInt;
        java.io.PrintWriter errPrintWriter = getErrPrintWriter();
        java.lang.String nextArg = getNextArg();
        if (nextArg == null) {
            errPrintWriter.println("Error: expected FEATURE name");
            return 1;
        }
        java.lang.String nextArg2 = getNextArg();
        if (nextArg2 == null) {
            parseInt = 0;
        } else {
            try {
                parseInt = java.lang.Integer.parseInt(nextArg2);
            } catch (android.os.RemoteException e) {
                errPrintWriter.println(e.toString());
                return 1;
            } catch (java.lang.NumberFormatException e2) {
                errPrintWriter.println("Error: illegal version number " + nextArg2);
                return 1;
            }
        }
        boolean hasSystemFeature = this.mInterface.hasSystemFeature(nextArg, parseInt);
        getOutPrintWriter().println(hasSystemFeature);
        return !hasSystemFeature ? 1 : 0;
    }

    private int runDump() {
        java.lang.String nextArg = getNextArg();
        if (nextArg == null) {
            getErrPrintWriter().println("Error: no package specified");
            return 1;
        }
        android.app.ActivityManager.dumpPackageStateStatic(getOutFileDescriptor(), nextArg);
        return 0;
    }

    private int runDumpPackage() {
        java.lang.String nextArg = getNextArg();
        if (nextArg == null) {
            getErrPrintWriter().println("Error: no package specified");
            return 1;
        }
        try {
            this.mInterface.dump(getOutFileDescriptor(), new java.lang.String[]{nextArg});
            return 0;
        } catch (java.lang.Throwable th) {
            java.io.PrintWriter errPrintWriter = getErrPrintWriter();
            errPrintWriter.println("Failure dumping service:");
            th.printStackTrace(errPrintWriter);
            errPrintWriter.flush();
            return 0;
        }
    }

    private int runSetHarmfulAppWarning() throws android.os.RemoteException {
        int i = -2;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                if (nextOption.equals("--user")) {
                    i = android.os.UserHandle.parseUserArg(getNextArgRequired());
                } else {
                    getErrPrintWriter().println("Error: Unknown option: " + nextOption);
                    return -1;
                }
            } else {
                this.mInterface.setHarmfulAppWarning(getNextArgRequired(), getNextArg(), translateUserId(i, com.android.server.am.ProcessList.INVALID_ADJ, "runSetHarmfulAppWarning"));
                return 0;
            }
        }
    }

    private int runGetHarmfulAppWarning() throws android.os.RemoteException {
        int i = -2;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                if (nextOption.equals("--user")) {
                    i = android.os.UserHandle.parseUserArg(getNextArgRequired());
                } else {
                    getErrPrintWriter().println("Error: Unknown option: " + nextOption);
                    return -1;
                }
            } else {
                java.lang.CharSequence harmfulAppWarning = this.mInterface.getHarmfulAppWarning(getNextArgRequired(), translateUserId(i, com.android.server.am.ProcessList.INVALID_ADJ, "runGetHarmfulAppWarning"));
                if (!android.text.TextUtils.isEmpty(harmfulAppWarning)) {
                    getOutPrintWriter().println(harmfulAppWarning);
                    return 0;
                }
                return 1;
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int runSetSilentUpdatesPolicy() {
        char c;
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        java.lang.Long l = null;
        java.lang.String str = null;
        boolean z = false;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                switch (nextOption.hashCode()) {
                    case -1615291473:
                        if (nextOption.equals("--reset")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 771584496:
                        if (nextOption.equals("--throttle-time")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1002172770:
                        if (nextOption.equals("--allow-unlimited-silent-updates")) {
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
                        str = getNextArgRequired();
                        break;
                    case 1:
                        l = java.lang.Long.valueOf(java.lang.Long.parseLong(getNextArgRequired()));
                        break;
                    case 2:
                        z = true;
                        break;
                    default:
                        outPrintWriter.println("Error: Unknown option: " + nextOption);
                        return -1;
                }
            } else {
                if (l != null && l.longValue() < 0) {
                    outPrintWriter.println("Error: Invalid value for \"--throttle-time\":" + l);
                    return -1;
                }
                try {
                    android.content.pm.IPackageInstaller packageInstaller = this.mInterface.getPackageInstaller();
                    if (z) {
                        packageInstaller.setAllowUnlimitedSilentUpdates((java.lang.String) null);
                        packageInstaller.setSilentUpdatesThrottleTime(-1L);
                    } else {
                        if (str != null) {
                            packageInstaller.setAllowUnlimitedSilentUpdates(str);
                        }
                        if (l != null) {
                            packageInstaller.setSilentUpdatesThrottleTime(l.longValue());
                        }
                    }
                    return 1;
                } catch (android.os.RemoteException e) {
                    outPrintWriter.println("Failure [" + e.getClass().getName() + " - " + e.getMessage() + "]");
                    return -1;
                }
            }
        }
    }

    private int runGetAppMetadata() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.GET_APP_METADATA", "getAppMetadataFd");
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        try {
            android.os.ParcelFileDescriptor appMetadataFd = this.mInterface.getAppMetadataFd(getNextArgRequired(), this.mContext.getUserId());
            if (appMetadataFd != null) {
                try {
                    java.io.BufferedReader bufferedReader = new java.io.BufferedReader(new java.io.InputStreamReader(new android.os.ParcelFileDescriptor.AutoCloseInputStream(appMetadataFd)));
                    while (bufferedReader.ready()) {
                        try {
                            outPrintWriter.println(bufferedReader.readLine());
                        } finally {
                        }
                    }
                    bufferedReader.close();
                    return 1;
                } catch (java.io.IOException e) {
                    outPrintWriter.println("Failure [" + e.getClass().getName() + " - " + e.getMessage() + "]");
                    return -1;
                }
            }
            return 1;
        } catch (android.os.RemoteException e2) {
            outPrintWriter.println("Failure [" + e2.getClass().getName() + " - " + e2.getMessage() + "]");
            return -1;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x001d, code lost:
    
        if (r3.equals("--timeout") != false) goto L12;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int runWaitForHandler(boolean z) {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        long j = 60000;
        while (true) {
            java.lang.String nextOption = getNextOption();
            boolean z2 = false;
            if (nextOption == null) {
                if (j <= 0) {
                    outPrintWriter.println("Error: --timeout value must be positive: " + j);
                    return -1;
                }
                try {
                    if (this.mInterface.waitForHandler(j, z)) {
                        outPrintWriter.println("Success");
                        return 0;
                    }
                    outPrintWriter.println("Timeout. PackageManager handlers are still busy.");
                    return -1;
                } catch (android.os.RemoteException e) {
                    outPrintWriter.println("Failure [" + e.getClass().getName() + " - " + e.getMessage() + "]");
                    return -1;
                }
            }
            switch (nextOption.hashCode()) {
                case 72070081:
                    break;
                default:
                    z2 = -1;
                    break;
            }
            switch (z2) {
                case false:
                    j = java.lang.Long.parseLong(getNextArgRequired());
                default:
                    outPrintWriter.println("Error: Unknown option: " + nextOption);
                    return -1;
            }
        }
    }

    private int runArtServiceCommand() {
        try {
            android.os.ParcelFileDescriptor dup = android.os.ParcelFileDescriptor.dup(getInFileDescriptor());
            try {
                android.os.ParcelFileDescriptor dup2 = android.os.ParcelFileDescriptor.dup(getOutFileDescriptor());
                try {
                    android.os.ParcelFileDescriptor dup3 = android.os.ParcelFileDescriptor.dup(getErrFileDescriptor());
                    try {
                        int handleShellCommand = ((com.android.server.art.ArtManagerLocal) com.android.server.LocalManagerRegistry.getManagerOrThrow(com.android.server.art.ArtManagerLocal.class)).handleShellCommand(getTarget(), dup, dup2, dup3, getAllArgs());
                        if (dup3 != null) {
                            dup3.close();
                        }
                        if (dup2 != null) {
                            dup2.close();
                        }
                        if (dup != null) {
                            dup.close();
                        }
                        return handleShellCommand;
                    } finally {
                    }
                } finally {
                }
            } catch (java.lang.Throwable th) {
                if (dup != null) {
                    try {
                        dup.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (com.android.server.LocalManagerRegistry.ManagerNotFoundException e) {
            getErrPrintWriter().println("ART Service is not ready. Please try again later");
            return -1;
        } catch (java.io.IOException e2) {
            throw new java.lang.IllegalStateException(e2);
        }
    }

    private static java.lang.String checkAbiArgument(java.lang.String str) {
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.IllegalArgumentException("Missing ABI argument");
        }
        if (STDIN_PATH.equals(str)) {
            return str;
        }
        for (java.lang.String str2 : android.os.Build.SUPPORTED_ABIS) {
            if (str2.equals(str)) {
                return str;
            }
        }
        throw new java.lang.IllegalArgumentException("ABI " + str + " not supported on this device");
    }

    private int translateUserId(int i, int i2, java.lang.String str) {
        int handleIncomingUser = android.app.ActivityManager.handleIncomingUser(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, i2 != -10000, true, str, "pm command");
        return handleIncomingUser == -1 ? i2 : handleIncomingUser;
    }

    private int doCreateSession(android.content.pm.PackageInstaller.SessionParams sessionParams, java.lang.String str, int i) throws android.os.RemoteException {
        if (i == -1) {
            sessionParams.installFlags |= 64;
        }
        return this.mInterface.getPackageInstaller().createSession(sessionParams, str, (java.lang.String) null, translateUserId(i, 0, "doCreateSession"));
    }

    private int doAddFiles(int i, java.util.ArrayList<java.lang.String> arrayList, long j, boolean z, boolean z2) throws android.os.RemoteException {
        android.content.pm.PackageInstaller.Session session;
        com.android.server.pm.PackageManagerShellCommandDataLoader.Metadata forArchived;
        long j2;
        android.content.pm.PackageInstaller.Session session2 = null;
        try {
            try {
                session = new android.content.pm.PackageInstaller.Session(this.mInterface.getPackageInstaller().openSession(i));
            } catch (java.lang.Throwable th) {
                th = th;
            }
            try {
                try {
                    if (arrayList.isEmpty() || STDIN_PATH.equals(arrayList.get(0))) {
                        java.lang.StringBuilder sb = new java.lang.StringBuilder();
                        sb.append("base");
                        sb.append(RANDOM.nextInt());
                        sb.append(".");
                        sb.append(z ? "apex" : "apk");
                        java.lang.String sb2 = sb.toString();
                        if (z2) {
                            forArchived = com.android.server.pm.PackageManagerShellCommandDataLoader.Metadata.forArchived(getArchivedPackage(STDIN_PATH, j));
                            j2 = -1;
                        } else {
                            forArchived = com.android.server.pm.PackageManagerShellCommandDataLoader.Metadata.forStdIn(sb2);
                            j2 = j;
                        }
                        session.addFile(0, sb2, j2, forArchived.toByteArray(), null);
                        libcore.io.IoUtils.closeQuietly(session);
                        return 0;
                    }
                    java.util.Iterator<java.lang.String> it = arrayList.iterator();
                    while (it.hasNext()) {
                        java.lang.String next = it.next();
                        if (next.indexOf(58) == -1) {
                            processArgForLocalFile(next, session, z2);
                        } else {
                            if (z2) {
                                getOutPrintWriter().println("Error: can't install with size from STDIN for Archival install");
                                libcore.io.IoUtils.closeQuietly(session);
                                return 1;
                            }
                            if (processArgForStdin(next, session) != 0) {
                                libcore.io.IoUtils.closeQuietly(session);
                                return 1;
                            }
                        }
                    }
                    libcore.io.IoUtils.closeQuietly(session);
                    return 0;
                } catch (java.io.IOException | java.lang.IllegalArgumentException e) {
                    e = e;
                    session2 = session;
                    getErrPrintWriter().println("Failed to add file(s), reason: " + e);
                    getOutPrintWriter().println("Failure [failed to add file(s)]");
                    libcore.io.IoUtils.closeQuietly(session2);
                    return 1;
                }
            } catch (java.lang.Throwable th2) {
                th = th2;
                session2 = session;
                libcore.io.IoUtils.closeQuietly(session2);
                throw th;
            }
        } catch (java.io.IOException | java.lang.IllegalArgumentException e2) {
            e = e2;
        }
    }

    private int processArgForStdin(java.lang.String str, android.content.pm.PackageInstaller.Session session) {
        java.lang.String str2;
        byte[] bArr;
        int i;
        com.android.server.pm.PackageManagerShellCommandDataLoader.Metadata forStdIn;
        java.lang.String[] split = str.split(":");
        try {
            if (split.length < 2) {
                getErrPrintWriter().println("Must specify file name and size");
                return 1;
            }
            java.lang.String str3 = split[0];
            long parseUnsignedLong = java.lang.Long.parseUnsignedLong(split[1]);
            if (split.length > 2 && !android.text.TextUtils.isEmpty(split[2])) {
                str2 = split[2];
            } else {
                str2 = str3;
            }
            if (split.length <= 3) {
                bArr = null;
            } else {
                bArr = java.util.Base64.getDecoder().decode(split[3]);
            }
            if (split.length <= 4) {
                i = 0;
            } else {
                i = java.lang.Integer.parseUnsignedInt(split[4]);
                if (i < 0 || i > 1) {
                    getErrPrintWriter().println("Unsupported streaming version: " + i);
                    return 1;
                }
            }
            if (android.text.TextUtils.isEmpty(str3)) {
                getErrPrintWriter().println("Empty file name in: " + str);
                return 1;
            }
            if (bArr != null) {
                forStdIn = i == 0 ? com.android.server.pm.PackageManagerShellCommandDataLoader.Metadata.forDataOnlyStreaming(str2) : com.android.server.pm.PackageManagerShellCommandDataLoader.Metadata.forStreaming(str2);
                try {
                    if (bArr.length > 0 && android.os.incremental.V4Signature.readFrom(bArr) == null) {
                        getErrPrintWriter().println("V4 signature is invalid in: " + str);
                        return 1;
                    }
                } catch (java.lang.Exception e) {
                    getErrPrintWriter().println("V4 signature is invalid: " + e + " in " + str);
                    return 1;
                }
            } else {
                forStdIn = com.android.server.pm.PackageManagerShellCommandDataLoader.Metadata.forStdIn(str2);
            }
            session.addFile(0, str3, parseUnsignedLong, forStdIn.toByteArray(), bArr);
            return 0;
        } catch (java.lang.IllegalArgumentException e2) {
            getErrPrintWriter().println("Unable to parse file parameters: " + str + ", reason: " + e2);
            return 1;
        }
    }

    private long getFileStatSize(java.io.File file) {
        android.os.ParcelFileDescriptor openFileForSystem = openFileForSystem(file.getPath(), com.android.server.wm.ActivityTaskManagerService.DUMP_RECENTS_SHORT_CMD);
        if (openFileForSystem == null) {
            throw new java.lang.IllegalArgumentException("Error: Can't open file: " + file.getPath());
        }
        try {
            return openFileForSystem.getStatSize();
        } finally {
            libcore.io.IoUtils.closeQuietly(openFileForSystem);
        }
    }

    private android.content.pm.ArchivedPackageParcel getArchivedPackage(java.lang.String str, long j) throws android.os.RemoteException, java.io.IOException {
        android.util.Pair<android.os.ParcelFileDescriptor, java.lang.Long> openInFile = openInFile(str, j);
        if (openInFile.first == null) {
            throw new java.lang.IllegalArgumentException("Error: Can't open file: " + str);
        }
        android.os.ParcelFileDescriptor parcelFileDescriptor = (android.os.ParcelFileDescriptor) openInFile.first;
        int longValue = (int) ((java.lang.Long) openInFile.second).longValue();
        try {
            android.os.ParcelFileDescriptor.AutoCloseInputStream autoCloseInputStream = new android.os.ParcelFileDescriptor.AutoCloseInputStream(parcelFileDescriptor);
            try {
                byte[] bArr = new byte[longValue];
                libcore.io.Streams.readFully(autoCloseInputStream, bArr);
                java.lang.String str2 = new java.lang.String(bArr);
                autoCloseInputStream.close();
                android.content.pm.ArchivedPackageParcel readArchivedPackageParcel = com.android.server.pm.PackageManagerShellCommandDataLoader.Metadata.readArchivedPackageParcel(libcore.util.HexEncoding.decode(str2));
                if (readArchivedPackageParcel == null) {
                    throw new java.lang.IllegalArgumentException("Error: Can't parse archived package from: " + str);
                }
                return readArchivedPackageParcel;
            } finally {
            }
        } catch (java.io.IOException e) {
            throw new java.lang.IllegalArgumentException("Error: Can't load archived package from: " + str, e);
        }
    }

    private void processArgForLocalFile(java.lang.String str, android.content.pm.PackageInstaller.Session session, boolean z) throws java.io.IOException, android.os.RemoteException {
        long fileStatSize;
        com.android.server.pm.PackageManagerShellCommandDataLoader.Metadata metadata;
        byte[] bArr;
        java.io.File file = new java.io.File(str);
        java.lang.String name = file.getName();
        if (z) {
            metadata = com.android.server.pm.PackageManagerShellCommandDataLoader.Metadata.forArchived(getArchivedPackage(str, -1L));
            fileStatSize = 0;
        } else {
            com.android.server.pm.PackageManagerShellCommandDataLoader.Metadata forLocalFile = com.android.server.pm.PackageManagerShellCommandDataLoader.Metadata.forLocalFile(str);
            fileStatSize = getFileStatSize(file);
            metadata = forLocalFile;
        }
        if (!z) {
            android.os.ParcelFileDescriptor openFileForSystem = openFileForSystem(str + ".idsig", com.android.server.wm.ActivityTaskManagerService.DUMP_RECENTS_SHORT_CMD);
            if (openFileForSystem != null) {
                try {
                    try {
                        byte[] byteArray = android.os.incremental.V4Signature.readFrom(openFileForSystem).toByteArray();
                        libcore.io.IoUtils.closeQuietly(openFileForSystem);
                        bArr = byteArray;
                    } catch (java.io.IOException e) {
                        android.util.Slog.e(TAG, "V4 signature file exists but failed to be parsed.", e);
                        libcore.io.IoUtils.closeQuietly(openFileForSystem);
                    }
                    session.addFile(0, name, fileStatSize, metadata.toByteArray(), bArr);
                } catch (java.lang.Throwable th) {
                    libcore.io.IoUtils.closeQuietly(openFileForSystem);
                    throw th;
                }
            }
        }
        bArr = null;
        session.addFile(0, name, fileStatSize, metadata.toByteArray(), bArr);
    }

    private int doWriteSplits(int i, java.util.ArrayList<java.lang.String> arrayList, long j, boolean z) throws android.os.RemoteException {
        java.lang.String sb;
        boolean z2 = arrayList.size() > 1;
        java.util.Iterator<java.lang.String> it = arrayList.iterator();
        while (it.hasNext()) {
            java.lang.String next = it.next();
            if (z2) {
                sb = new java.io.File(next).getName();
            } else {
                java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
                sb2.append("base.");
                sb2.append(z ? "apex" : "apk");
                sb = sb2.toString();
            }
            if (doWriteSplit(i, next, j, sb, false) != 0) {
                return 1;
            }
        }
        return 0;
    }

    private android.util.Pair<android.os.ParcelFileDescriptor, java.lang.Long> openInFile(java.lang.String str, long j) throws java.io.IOException {
        android.os.ParcelFileDescriptor dup;
        if (STDIN_PATH.equals(str)) {
            dup = android.os.ParcelFileDescriptor.dup(getInFileDescriptor());
        } else if (str != null) {
            android.os.ParcelFileDescriptor openFileForSystem = openFileForSystem(str, com.android.server.wm.ActivityTaskManagerService.DUMP_RECENTS_SHORT_CMD);
            if (openFileForSystem == null) {
                return android.util.Pair.create(null, -1L);
            }
            long statSize = openFileForSystem.getStatSize();
            if (statSize >= 0) {
                dup = openFileForSystem;
                j = statSize;
            } else {
                openFileForSystem.close();
                getErrPrintWriter().println("Unable to get size of: " + str);
                return android.util.Pair.create(null, -1L);
            }
        } else {
            dup = android.os.ParcelFileDescriptor.dup(getInFileDescriptor());
        }
        if (j <= 0) {
            getErrPrintWriter().println("Error: must specify an APK size");
            return android.util.Pair.create(null, 1L);
        }
        return android.util.Pair.create(dup, java.lang.Long.valueOf(j));
    }

    private int doWriteSplit(int i, java.lang.String str, long j, java.lang.String str2, boolean z) throws android.os.RemoteException {
        android.content.pm.PackageInstaller.Session session = null;
        try {
            try {
                android.content.pm.PackageInstaller.Session session2 = new android.content.pm.PackageInstaller.Session(this.mInterface.getPackageInstaller().openSession(i));
                try {
                    java.io.PrintWriter outPrintWriter = getOutPrintWriter();
                    android.util.Pair<android.os.ParcelFileDescriptor, java.lang.Long> openInFile = openInFile(str, j);
                    if (openInFile.first == null) {
                        int longValue = (int) ((java.lang.Long) openInFile.second).longValue();
                        libcore.io.IoUtils.closeQuietly(session2);
                        return longValue;
                    }
                    android.os.ParcelFileDescriptor parcelFileDescriptor = (android.os.ParcelFileDescriptor) openInFile.first;
                    long longValue2 = ((java.lang.Long) openInFile.second).longValue();
                    session2.write(str2, 0L, longValue2, parcelFileDescriptor);
                    if (z) {
                        outPrintWriter.println("Success: streamed " + longValue2 + " bytes");
                    }
                    libcore.io.IoUtils.closeQuietly(session2);
                    return 0;
                } catch (java.io.IOException e) {
                    e = e;
                    session = session2;
                    getErrPrintWriter().println("Error: failed to write; " + e.getMessage());
                    libcore.io.IoUtils.closeQuietly(session);
                    return 1;
                } catch (java.lang.Throwable th) {
                    th = th;
                    session = session2;
                    libcore.io.IoUtils.closeQuietly(session);
                    throw th;
                }
            } catch (java.lang.Throwable th2) {
                th = th2;
            }
        } catch (java.io.IOException e2) {
            e = e2;
        }
    }

    private int doInstallAddSession(int i, int[] iArr, boolean z) throws android.os.RemoteException {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        android.content.pm.PackageInstaller.Session session = null;
        try {
            android.content.pm.PackageInstaller.Session session2 = new android.content.pm.PackageInstaller.Session(this.mInterface.getPackageInstaller().openSession(i));
            try {
                if (!session2.isMultiPackage()) {
                    getErrPrintWriter().println("Error: parent session ID is not a multi-package session");
                    libcore.io.IoUtils.closeQuietly(session2);
                    return 1;
                }
                for (int i2 : iArr) {
                    session2.addChildSessionId(i2);
                }
                if (z) {
                    outPrintWriter.println("Success");
                }
                libcore.io.IoUtils.closeQuietly(session2);
                return 0;
            } catch (java.lang.Throwable th) {
                th = th;
                session = session2;
                libcore.io.IoUtils.closeQuietly(session);
                throw th;
            }
        } catch (java.lang.Throwable th2) {
            th = th2;
        }
    }

    private int doRemoveSplits(int i, java.util.Collection<java.lang.String> collection, boolean z) throws android.os.RemoteException {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        android.content.pm.PackageInstaller.Session session = null;
        try {
            try {
                android.content.pm.PackageInstaller.Session session2 = new android.content.pm.PackageInstaller.Session(this.mInterface.getPackageInstaller().openSession(i));
                try {
                    java.util.Iterator<java.lang.String> it = collection.iterator();
                    while (it.hasNext()) {
                        session2.removeSplit(it.next());
                    }
                    if (z) {
                        outPrintWriter.println("Success");
                    }
                    libcore.io.IoUtils.closeQuietly(session2);
                    return 0;
                } catch (java.io.IOException e) {
                    e = e;
                    session = session2;
                    outPrintWriter.println("Error: failed to remove split; " + e.getMessage());
                    libcore.io.IoUtils.closeQuietly(session);
                    return 1;
                } catch (java.lang.Throwable th) {
                    th = th;
                    session = session2;
                    libcore.io.IoUtils.closeQuietly(session);
                    throw th;
                }
            } catch (java.io.IOException e2) {
                e = e2;
            }
        } catch (java.lang.Throwable th2) {
            th = th2;
        }
    }

    private int doCommitSession(int i, boolean z) throws android.os.RemoteException {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        android.content.pm.PackageInstaller.Session session = null;
        byte b = 0;
        try {
            android.content.pm.PackageInstaller.Session session2 = new android.content.pm.PackageInstaller.Session(this.mInterface.getPackageInstaller().openSession(i));
            try {
                if (!session2.isMultiPackage() && !session2.isStaged()) {
                    try {
                        android.content.pm.dex.DexMetadataHelper.validateDexPaths(session2.getNames());
                    } catch (java.io.IOException | java.lang.IllegalStateException e) {
                        outPrintWriter.println("Warning [Could not validate the dex paths: " + e.getMessage() + "]");
                    }
                }
                com.android.server.pm.PackageManagerShellCommand.LocalIntentReceiver localIntentReceiver = new com.android.server.pm.PackageManagerShellCommand.LocalIntentReceiver();
                session2.commit(localIntentReceiver.getIntentSender());
                if (session2.isStaged()) {
                    if (z) {
                        outPrintWriter.println("Success");
                    }
                    libcore.io.IoUtils.closeQuietly(session2);
                    return 0;
                }
                android.content.Intent result = localIntentReceiver.getResult();
                int i2 = 1;
                int intExtra = result.getIntExtra("android.content.pm.extra.STATUS", 1);
                java.util.ArrayList<java.lang.String> stringArrayListExtra = result.getStringArrayListExtra("android.content.pm.extra.WARNINGS");
                if (intExtra != 0) {
                    outPrintWriter.println("Failure [" + result.getStringExtra("android.content.pm.extra.STATUS_MESSAGE") + "]");
                } else {
                    if (!com.android.internal.util.ArrayUtils.isEmpty(stringArrayListExtra)) {
                        java.util.Iterator<java.lang.String> it = stringArrayListExtra.iterator();
                        while (it.hasNext()) {
                            outPrintWriter.println("Warning: " + it.next());
                        }
                        outPrintWriter.println("Completed with warning(s)");
                        libcore.io.IoUtils.closeQuietly(session2);
                        return i2;
                    }
                    if (z) {
                        outPrintWriter.println("Success");
                    }
                }
                i2 = intExtra;
                libcore.io.IoUtils.closeQuietly(session2);
                return i2;
            } catch (java.lang.Throwable th) {
                th = th;
                session = session2;
                libcore.io.IoUtils.closeQuietly(session);
                throw th;
            }
        } catch (java.lang.Throwable th2) {
            th = th2;
        }
    }

    private int doAbandonSession(int i, boolean z) throws android.os.RemoteException {
        android.content.pm.PackageInstaller.Session session;
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        android.content.pm.PackageInstaller.Session session2 = null;
        try {
            session = new android.content.pm.PackageInstaller.Session(this.mInterface.getPackageInstaller().openSession(i));
        } catch (java.lang.Throwable th) {
            th = th;
        }
        try {
            session.abandon();
            if (z) {
                outPrintWriter.println("Success");
            }
            libcore.io.IoUtils.closeQuietly(session);
            return 0;
        } catch (java.lang.Throwable th2) {
            th = th2;
            session2 = session;
            libcore.io.IoUtils.closeQuietly(session2);
            throw th;
        }
    }

    private void doListPermissions(java.util.ArrayList<java.lang.String> arrayList, boolean z, boolean z2, boolean z3, int i, int i2) throws android.os.RemoteException {
        java.lang.String str;
        int i3;
        java.lang.String str2;
        java.lang.String str3;
        java.util.ArrayList<java.lang.String> arrayList2 = arrayList;
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        int size = arrayList.size();
        int i4 = 0;
        int i5 = 0;
        while (i5 < size) {
            java.lang.String str4 = arrayList2.get(i5);
            java.lang.String str5 = "  label:";
            if (!z) {
                str = "";
            } else {
                if (i5 > 0) {
                    outPrintWriter.println("");
                }
                if (str4 != null) {
                    android.content.pm.PermissionGroupInfo permissionGroupInfo = this.mInterface.getPermissionGroupInfo(str4, i4);
                    if (z3) {
                        if (getResources(permissionGroupInfo) != null) {
                            outPrintWriter.print(loadText(permissionGroupInfo, permissionGroupInfo.labelRes, permissionGroupInfo.nonLocalizedLabel) + ": ");
                        } else {
                            outPrintWriter.print(permissionGroupInfo.name + ": ");
                        }
                    } else {
                        java.lang.StringBuilder sb = new java.lang.StringBuilder();
                        sb.append(z2 ? "+ " : "");
                        sb.append("group:");
                        sb.append(permissionGroupInfo.name);
                        outPrintWriter.println(sb.toString());
                        if (z2) {
                            outPrintWriter.println("  package:" + permissionGroupInfo.packageName);
                            if (getResources(permissionGroupInfo) != null) {
                                outPrintWriter.println("  label:" + loadText(permissionGroupInfo, permissionGroupInfo.labelRes, permissionGroupInfo.nonLocalizedLabel));
                                outPrintWriter.println("  description:" + loadText(permissionGroupInfo, permissionGroupInfo.descriptionRes, permissionGroupInfo.nonLocalizedDescription));
                            }
                        }
                    }
                } else {
                    java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
                    sb2.append((!z2 || z3) ? "" : "+ ");
                    sb2.append("ungrouped:");
                    outPrintWriter.println(sb2.toString());
                }
                str = "  ";
            }
            int i6 = 0;
            java.util.List queryPermissionsByGroup = this.mPermissionManager.queryPermissionsByGroup(arrayList2.get(i5), 0);
            int size2 = queryPermissionsByGroup == null ? 0 : queryPermissionsByGroup.size();
            boolean z4 = true;
            while (i6 < size2) {
                android.content.pm.PermissionInfo permissionInfo = (android.content.pm.PermissionInfo) queryPermissionsByGroup.get(i6);
                if (z && str4 == null) {
                    i3 = size;
                    if (permissionInfo.group != null) {
                        str2 = str4;
                        str3 = str5;
                        i6++;
                        size = i3;
                        str4 = str2;
                        str5 = str3;
                    }
                } else {
                    i3 = size;
                }
                int i7 = permissionInfo.protectionLevel & 15;
                str2 = str4;
                if (i7 < i) {
                    str3 = str5;
                } else if (i7 > i2) {
                    str3 = str5;
                } else if (z3) {
                    if (z4) {
                        z4 = false;
                    } else {
                        outPrintWriter.print(", ");
                    }
                    if (getResources(permissionInfo) != null) {
                        outPrintWriter.print(loadText(permissionInfo, permissionInfo.labelRes, permissionInfo.nonLocalizedLabel));
                    } else {
                        outPrintWriter.print(permissionInfo.name);
                    }
                    str3 = str5;
                } else {
                    java.lang.StringBuilder sb3 = new java.lang.StringBuilder();
                    sb3.append(str);
                    sb3.append(z2 ? "+ " : "");
                    sb3.append("permission:");
                    sb3.append(permissionInfo.name);
                    outPrintWriter.println(sb3.toString());
                    if (!z2) {
                        str3 = str5;
                    } else {
                        outPrintWriter.println(str + "  package:" + permissionInfo.packageName);
                        if (getResources(permissionInfo) == null) {
                            str3 = str5;
                        } else {
                            java.lang.StringBuilder sb4 = new java.lang.StringBuilder();
                            sb4.append(str);
                            sb4.append(str5);
                            str3 = str5;
                            sb4.append(loadText(permissionInfo, permissionInfo.labelRes, permissionInfo.nonLocalizedLabel));
                            outPrintWriter.println(sb4.toString());
                            outPrintWriter.println(str + "  description:" + loadText(permissionInfo, permissionInfo.descriptionRes, permissionInfo.nonLocalizedDescription));
                        }
                        outPrintWriter.println(str + "  protectionLevel:" + android.content.pm.PermissionInfo.protectionToString(permissionInfo.protectionLevel));
                    }
                }
                i6++;
                size = i3;
                str4 = str2;
                str5 = str3;
            }
            int i8 = size;
            if (z3) {
                outPrintWriter.println("");
            }
            i5++;
            arrayList2 = arrayList;
            size = i8;
            i4 = 0;
        }
    }

    private java.lang.String loadText(android.content.pm.PackageItemInfo packageItemInfo, int i, java.lang.CharSequence charSequence) throws android.os.RemoteException {
        android.content.res.Resources resources;
        if (charSequence != null) {
            return charSequence.toString();
        }
        if (i != 0 && (resources = getResources(packageItemInfo)) != null) {
            try {
                return resources.getString(i);
            } catch (android.content.res.Resources.NotFoundException e) {
                return null;
            }
        }
        return null;
    }

    private android.content.res.Resources getResources(android.content.pm.PackageItemInfo packageItemInfo) throws android.os.RemoteException {
        android.content.res.Resources resources = this.mResourceCache.get(packageItemInfo.packageName);
        if (resources != null) {
            return resources;
        }
        android.content.pm.ApplicationInfo applicationInfo = this.mInterface.getApplicationInfo(packageItemInfo.packageName, 536904192L, 0);
        if (applicationInfo == null) {
            android.util.Slog.e(TAG, "Failed to get ApplicationInfo for package name(" + packageItemInfo.packageName + ").");
            return null;
        }
        android.content.res.AssetManager assetManager = new android.content.res.AssetManager();
        assetManager.addAssetPath(applicationInfo.publicSourceDir);
        android.content.res.Resources resources2 = new android.content.res.Resources(assetManager, null, null);
        this.mResourceCache.put(packageItemInfo.packageName, resources2);
        return resources2;
    }

    private int resolveUserId(int i) {
        return i == -2 ? android.app.ActivityManager.getCurrentUser() : i;
    }

    private int runClearPackagePreferredActivities() {
        java.io.PrintWriter errPrintWriter = getErrPrintWriter();
        java.lang.String nextArg = getNextArg();
        if (nextArg == null) {
            errPrintWriter.println("Error: package name not specified");
            return 1;
        }
        try {
            this.mContext.getPackageManager().clearPackagePreferredActivities(nextArg);
            return 0;
        } catch (java.lang.Exception e) {
            errPrintWriter.println(e.toString());
            return 1;
        }
    }

    private int runArchive() throws android.os.RemoteException {
        int i;
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        int i2 = -1;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                if (nextOption.equals("--user")) {
                    i2 = android.os.UserHandle.parseUserArg(getNextArgRequired());
                    if (i2 != -1 && i2 != -2 && ((com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class)).getUserInfo(i2) == null) {
                        outPrintWriter.println("Failure [user " + i2 + " doesn't exist]");
                        return 1;
                    }
                } else {
                    outPrintWriter.println("Error: Unknown option: " + nextOption);
                    return 1;
                }
            } else {
                java.lang.String nextArg = getNextArg();
                if (nextArg != null) {
                    if (i2 != -1) {
                        i = 0;
                    } else {
                        i = 2;
                    }
                    int translateUserId = translateUserId(i2, 0, "runArchive");
                    com.android.server.pm.PackageManagerShellCommand.LocalIntentReceiver localIntentReceiver = new com.android.server.pm.PackageManagerShellCommand.LocalIntentReceiver();
                    try {
                        this.mInterface.getPackageInstaller().requestArchive(nextArg, "", i, localIntentReceiver.getIntentSender(), new android.os.UserHandle(translateUserId));
                        android.content.Intent result = localIntentReceiver.getResult();
                        if (result.getIntExtra("android.content.pm.extra.STATUS", 1) == 0) {
                            outPrintWriter.println("Success");
                            return 0;
                        }
                        outPrintWriter.println("Failure [" + result.getStringExtra("android.content.pm.extra.STATUS_MESSAGE") + "]");
                        return 1;
                    } catch (java.lang.Exception e) {
                        outPrintWriter.println("Failure [" + e.getMessage() + "]");
                        return 1;
                    }
                }
                outPrintWriter.println("Error: package name not specified");
                return 1;
            }
        }
    }

    private int runUnarchive() throws android.os.RemoteException {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        int i = -1;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                if (nextOption.equals("--user")) {
                    i = android.os.UserHandle.parseUserArg(getNextArgRequired());
                    if (i != -1 && i != -2 && ((com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class)).getUserInfo(i) == null) {
                        outPrintWriter.println("Failure [user " + i + " doesn't exist]");
                        return 1;
                    }
                } else {
                    outPrintWriter.println("Error: Unknown option: " + nextOption);
                    return 1;
                }
            } else {
                java.lang.String nextArg = getNextArg();
                if (nextArg == null) {
                    outPrintWriter.println("Error: package name not specified");
                    return 1;
                }
                try {
                    this.mInterface.getPackageInstaller().requestUnarchive(nextArg, this.mContext.getPackageName(), new com.android.server.pm.PackageManagerShellCommand.LocalIntentReceiver().getIntentSender(), new android.os.UserHandle(translateUserId(i, 0, "runArchive")));
                    outPrintWriter.println("Success");
                    return 0;
                } catch (java.lang.Exception e) {
                    outPrintWriter.println("Failure [" + e.getMessage() + "]");
                    return 1;
                }
            }
        }
    }

    private int runGetDomainVerificationAgent() throws android.os.RemoteException {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        try {
            android.content.ComponentName domainVerificationAgent = this.mInterface.getDomainVerificationAgent();
            outPrintWriter.println(domainVerificationAgent == null ? "No Domain Verifier available!" : domainVerificationAgent.flattenToString());
            return 0;
        } catch (java.lang.Exception e) {
            outPrintWriter.println("Failure [" + e.getMessage() + "]");
            return 1;
        }
    }

    public void onHelp() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Package manager (package) commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println("");
        outPrintWriter.println("  path [--user USER_ID] PACKAGE");
        outPrintWriter.println("    Print the path to the .apk of the given PACKAGE.");
        outPrintWriter.println("");
        outPrintWriter.println("  dump PACKAGE");
        outPrintWriter.println("    Print various system state associated with the given PACKAGE.");
        outPrintWriter.println("");
        outPrintWriter.println("  dump-package PACKAGE");
        outPrintWriter.println("    Print package manager state associated with the given PACKAGE.");
        outPrintWriter.println("");
        outPrintWriter.println("  has-feature FEATURE_NAME [version]");
        outPrintWriter.println("    Prints true and returns exit status 0 when system has a FEATURE_NAME,");
        outPrintWriter.println("    otherwise prints false and returns exit status 1");
        outPrintWriter.println("");
        outPrintWriter.println("  list features");
        outPrintWriter.println("    Prints all features of the system.");
        outPrintWriter.println("");
        outPrintWriter.println("  list instrumentation [-f] [TARGET-PACKAGE]");
        outPrintWriter.println("    Prints all test packages; optionally only those targeting TARGET-PACKAGE");
        outPrintWriter.println("    Options:");
        outPrintWriter.println("      -f: dump the name of the .apk file containing the test package");
        outPrintWriter.println("");
        outPrintWriter.println("  list libraries [-v]");
        outPrintWriter.println("    Prints all system libraries.");
        outPrintWriter.println("    Options:");
        outPrintWriter.println("      -v: shows the location of the library in the device's filesystem");
        outPrintWriter.println("");
        outPrintWriter.println("  list packages [-f] [-d] [-e] [-s] [-q] [-3] [-i] [-l] [-u] [-U] ");
        outPrintWriter.println("      [--show-versioncode] [--apex-only] [--factory-only]");
        outPrintWriter.println("      [--uid UID] [--user USER_ID] [FILTER]");
        outPrintWriter.println("    Prints all packages; optionally only those whose name contains");
        outPrintWriter.println("    the text in FILTER.  Options are:");
        outPrintWriter.println("      -f: see their associated file");
        outPrintWriter.println("      -a: all known packages (but excluding APEXes)");
        outPrintWriter.println("      -d: filter to only show disabled packages");
        outPrintWriter.println("      -e: filter to only show enabled packages");
        outPrintWriter.println("      -s: filter to only show system packages");
        outPrintWriter.println("      -q: filter to only show quarantined packages");
        outPrintWriter.println("      -3: filter to only show third party packages");
        outPrintWriter.println("      -i: see the installer for the packages");
        outPrintWriter.println("      -l: ignored (used for compatibility with older releases)");
        outPrintWriter.println("      -U: also show the package UID");
        outPrintWriter.println("      -u: also include uninstalled packages");
        outPrintWriter.println("      --show-versioncode: also show the version code");
        outPrintWriter.println("      --apex-only: only show APEX packages");
        outPrintWriter.println("      --factory-only: only show system packages excluding updates");
        outPrintWriter.println("      --uid UID: filter to only show packages with the given UID");
        outPrintWriter.println("      --user USER_ID: only list packages belonging to the given user");
        outPrintWriter.println("      --match-libraries: include packages that declare static shared and SDK libraries");
        outPrintWriter.println("");
        outPrintWriter.println("  list permission-groups");
        outPrintWriter.println("    Prints all known permission groups.");
        outPrintWriter.println("");
        outPrintWriter.println("  list permissions [-g] [-f] [-d] [-u] [GROUP]");
        outPrintWriter.println("    Prints all known permissions; optionally only those in GROUP.  Options are:");
        outPrintWriter.println("      -g: organize by group");
        outPrintWriter.println("      -f: print all information");
        outPrintWriter.println("      -s: short summary");
        outPrintWriter.println("      -d: only list dangerous permissions");
        outPrintWriter.println("      -u: list only the permissions users will see");
        outPrintWriter.println("");
        outPrintWriter.println("  list staged-sessions [--only-ready] [--only-sessionid] [--only-parent]");
        outPrintWriter.println("    Prints all staged sessions.");
        outPrintWriter.println("      --only-ready: show only staged sessions that are ready");
        outPrintWriter.println("      --only-sessionid: show only sessionId of each session");
        outPrintWriter.println("      --only-parent: hide all children sessions");
        outPrintWriter.println("");
        outPrintWriter.println("  list users");
        outPrintWriter.println("    Prints all users.");
        outPrintWriter.println("");
        outPrintWriter.println("  resolve-activity [--brief] [--components] [--query-flags FLAGS]");
        outPrintWriter.println("       [--user USER_ID] INTENT");
        outPrintWriter.println("    Prints the activity that resolves to the given INTENT.");
        outPrintWriter.println("");
        outPrintWriter.println("  query-activities [--brief] [--components] [--query-flags FLAGS]");
        outPrintWriter.println("       [--user USER_ID] INTENT");
        outPrintWriter.println("    Prints all activities that can handle the given INTENT.");
        outPrintWriter.println("");
        outPrintWriter.println("  query-services [--brief] [--components] [--query-flags FLAGS]");
        outPrintWriter.println("       [--user USER_ID] INTENT");
        outPrintWriter.println("    Prints all services that can handle the given INTENT.");
        outPrintWriter.println("");
        outPrintWriter.println("  query-receivers [--brief] [--components] [--query-flags FLAGS]");
        outPrintWriter.println("       [--user USER_ID] INTENT");
        outPrintWriter.println("    Prints all broadcast receivers that can handle the given INTENT.");
        outPrintWriter.println("");
        outPrintWriter.println("  install [-rtfdg] [-i PACKAGE] [--user USER_ID|all|current]");
        outPrintWriter.println("       [-p INHERIT_PACKAGE] [--install-location 0/1/2]");
        outPrintWriter.println("       [--install-reason 0/1/2/3/4] [--originating-uri URI]");
        outPrintWriter.println("       [--referrer URI] [--abi ABI_NAME] [--force-sdk]");
        outPrintWriter.println("       [--preload] [--instant] [--full] [--dont-kill]");
        outPrintWriter.println("       [--enable-rollback [0/1/2]]");
        outPrintWriter.println("       [--force-uuid internal|UUID] [--pkg PACKAGE] [-S BYTES]");
        outPrintWriter.println("       [--apex] [--non-staged] [--force-non-staged]");
        outPrintWriter.println("       [--staged-ready-timeout TIMEOUT] [--ignore-dexopt-profile]");
        outPrintWriter.println("       [PATH [SPLIT...]|-]");
        outPrintWriter.println("    Install an application.  Must provide the apk data to install, either as");
        outPrintWriter.println("    file path(s) or '-' to read from stdin.  Options are:");
        outPrintWriter.println("      -R: disallow replacement of existing application");
        outPrintWriter.println("      -t: allow test packages");
        outPrintWriter.println("      -i: specify package name of installer owning the app");
        outPrintWriter.println("      -f: install application on internal flash");
        outPrintWriter.println("      -d: allow version code downgrade (debuggable packages only)");
        outPrintWriter.println("      -p: partial application install (new split on top of existing pkg)");
        outPrintWriter.println("      -g: grant all runtime permissions");
        outPrintWriter.println("      -S: size in bytes of package, required for stdin");
        outPrintWriter.println("      --user: install under the given user.");
        outPrintWriter.println("      --dont-kill: installing a new feature split, don't kill running app");
        outPrintWriter.println("      --restrict-permissions: don't whitelist restricted permissions at install");
        outPrintWriter.println("      --originating-uri: set URI where app was downloaded from");
        outPrintWriter.println("      --referrer: set URI that instigated the install of the app");
        outPrintWriter.println("      --pkg: specify expected package name of app being installed");
        outPrintWriter.println("      --abi: override the default ABI of the platform");
        outPrintWriter.println("      --instant: cause the app to be installed as an ephemeral install app");
        outPrintWriter.println("      --full: cause the app to be installed as a non-ephemeral full app");
        outPrintWriter.println("      --enable-rollback: enable rollbacks for the upgrade.");
        outPrintWriter.println("          0=restore (default), 1=wipe, 2=retain");
        outPrintWriter.println("      --install-location: force the install location:");
        outPrintWriter.println("          0=auto, 1=internal only, 2=prefer external");
        outPrintWriter.println("      --install-reason: indicates why the app is being installed:");
        outPrintWriter.println("          0=unknown, 1=admin policy, 2=device restore,");
        outPrintWriter.println("          3=device setup, 4=user request");
        outPrintWriter.println("      --update-ownership: request the update ownership enforcement");
        outPrintWriter.println("      --force-uuid: force install on to disk volume with given UUID");
        outPrintWriter.println("      --apex: install an .apex file, not an .apk");
        outPrintWriter.println("      --non-staged: explicitly set this installation to be non-staged.");
        outPrintWriter.println("          This flag is only useful for APEX installs that are implicitly");
        outPrintWriter.println("          assumed to be staged.");
        outPrintWriter.println("      --force-non-staged: force the installation to run under a non-staged");
        outPrintWriter.println("          session, which may complete without requiring a reboot. This will");
        outPrintWriter.println("          force a rebootless update even for APEXes that don't support it");
        outPrintWriter.println("      --staged-ready-timeout: By default, staged sessions wait 60000");
        outPrintWriter.println("          milliseconds for pre-reboot verification to complete when");
        outPrintWriter.println("          performing staged install. This flag is used to alter the waiting");
        outPrintWriter.println("          time. You can skip the waiting time by specifying a TIMEOUT of '0'");
        outPrintWriter.println("      --ignore-dexopt-profile: If set, all profiles are ignored by dexopt");
        outPrintWriter.println("          during the installation, including the profile in the DM file and");
        outPrintWriter.println("          the profile embedded in the APK file. If an invalid profile is");
        outPrintWriter.println("          provided during installation, no warning will be reported by `adb");
        outPrintWriter.println("          install`.");
        outPrintWriter.println("          This option does not affect later dexopt operations (e.g.,");
        outPrintWriter.println("          background dexopt and manual `pm compile` invocations).");
        outPrintWriter.println("");
        outPrintWriter.println("  install-existing [--user USER_ID|all|current]");
        outPrintWriter.println("       [--instant] [--full] [--wait] [--restrict-permissions] PACKAGE");
        outPrintWriter.println("    Installs an existing application for a new user.  Options are:");
        outPrintWriter.println("      --user: install for the given user.");
        outPrintWriter.println("      --instant: install as an instant app");
        outPrintWriter.println("      --full: install as a full app");
        outPrintWriter.println("      --wait: wait until the package is installed");
        outPrintWriter.println("      --restrict-permissions: don't whitelist restricted permissions");
        outPrintWriter.println("");
        outPrintWriter.println("  install-create [-lrtsfdg] [-i PACKAGE] [--user USER_ID|all|current]");
        outPrintWriter.println("       [-p INHERIT_PACKAGE] [--install-location 0/1/2]");
        outPrintWriter.println("       [--install-reason 0/1/2/3/4] [--originating-uri URI]");
        outPrintWriter.println("       [--referrer URI] [--abi ABI_NAME] [--force-sdk]");
        outPrintWriter.println("       [--preload] [--instant] [--full] [--dont-kill]");
        outPrintWriter.println("       [--force-uuid internal|UUID] [--pkg PACKAGE] [--apex] [-S BYTES]");
        outPrintWriter.println("       [--multi-package] [--staged] [--update-ownership]");
        outPrintWriter.println("    Like \"install\", but starts an install session.  Use \"install-write\"");
        outPrintWriter.println("    to push data into the session, and \"install-commit\" to finish.");
        outPrintWriter.println("");
        outPrintWriter.println("  install-write [-S BYTES] SESSION_ID SPLIT_NAME [PATH|-]");
        outPrintWriter.println("    Write an apk into the given install session.  If the path is '-', data");
        outPrintWriter.println("    will be read from stdin.  Options are:");
        outPrintWriter.println("      -S: size in bytes of package, required for stdin");
        outPrintWriter.println("");
        outPrintWriter.println("  install-remove SESSION_ID SPLIT...");
        outPrintWriter.println("    Mark SPLIT(s) as removed in the given install session.");
        outPrintWriter.println("");
        outPrintWriter.println("  install-add-session MULTI_PACKAGE_SESSION_ID CHILD_SESSION_IDs");
        outPrintWriter.println("    Add one or more session IDs to a multi-package session.");
        outPrintWriter.println("");
        outPrintWriter.println("  install-set-pre-verified-domains SESSION_ID PRE_VERIFIED_DOMAIN... ");
        outPrintWriter.println("    Specify a comma separated list of pre-verified domains for a session.");
        outPrintWriter.println("");
        outPrintWriter.println("  install-get-pre-verified-domains SESSION_ID");
        outPrintWriter.println("    List all the pre-verified domains that are specified in a session.");
        outPrintWriter.println("    The result list is comma separated.");
        outPrintWriter.println("");
        outPrintWriter.println("  install-commit SESSION_ID");
        outPrintWriter.println("    Commit the given active install session, installing the app.");
        outPrintWriter.println("");
        outPrintWriter.println("  install-abandon SESSION_ID");
        outPrintWriter.println("    Delete the given active install session.");
        outPrintWriter.println("");
        outPrintWriter.println("  set-install-location LOCATION");
        outPrintWriter.println("    Changes the default install location.  NOTE this is only intended for debugging;");
        outPrintWriter.println("    using this can cause applications to break and other undersireable behavior.");
        outPrintWriter.println("    LOCATION is one of:");
        outPrintWriter.println("    0 [auto]: Let system decide the best location");
        outPrintWriter.println("    1 [internal]: Install on internal device storage");
        outPrintWriter.println("    2 [external]: Install on external media");
        outPrintWriter.println("");
        outPrintWriter.println("  get-install-location");
        outPrintWriter.println("    Returns the current install location: 0, 1 or 2 as per set-install-location.");
        outPrintWriter.println("");
        outPrintWriter.println("  move-package PACKAGE [internal|UUID]");
        outPrintWriter.println("");
        outPrintWriter.println("  move-primary-storage [internal|UUID]");
        outPrintWriter.println("");
        outPrintWriter.println("  uninstall [-k] [--user USER_ID] [--versionCode VERSION_CODE]");
        outPrintWriter.println("       PACKAGE [SPLIT...]");
        outPrintWriter.println("    Remove the given package name from the system.  May remove an entire app");
        outPrintWriter.println("    if no SPLIT names specified, otherwise will remove only the splits of the");
        outPrintWriter.println("    given app.  Options are:");
        outPrintWriter.println("      -k: keep the data and cache directories around after package removal.");
        outPrintWriter.println("      --user: remove the app from the given user.");
        outPrintWriter.println("      --versionCode: only uninstall if the app has the given version code.");
        outPrintWriter.println("");
        outPrintWriter.println("  clear [--user USER_ID] [--cache-only] PACKAGE");
        outPrintWriter.println("    Deletes data associated with a package. Options are:");
        outPrintWriter.println("    --user: specifies the user for which we need to clear data");
        outPrintWriter.println("    --cache-only: a flag which tells if we only need to clear cache data");
        outPrintWriter.println("");
        outPrintWriter.println("  enable [--user USER_ID] PACKAGE_OR_COMPONENT");
        outPrintWriter.println("  disable [--user USER_ID] PACKAGE_OR_COMPONENT");
        outPrintWriter.println("  disable-user [--user USER_ID] PACKAGE_OR_COMPONENT");
        outPrintWriter.println("  disable-until-used [--user USER_ID] PACKAGE_OR_COMPONENT");
        outPrintWriter.println("  default-state [--user USER_ID] PACKAGE_OR_COMPONENT");
        outPrintWriter.println("    These commands change the enabled state of a given package or");
        outPrintWriter.println("    component (written as \"package/class\").");
        outPrintWriter.println("");
        outPrintWriter.println("  hide [--user USER_ID] PACKAGE_OR_COMPONENT");
        outPrintWriter.println("  unhide [--user USER_ID] PACKAGE_OR_COMPONENT");
        outPrintWriter.println("");
        outPrintWriter.println("  unstop [--user USER_ID] PACKAGE");
        outPrintWriter.println("");
        outPrintWriter.println("  suspend [--user USER_ID] PACKAGE [PACKAGE...]");
        outPrintWriter.println("    Suspends the specified package(s) (as user).");
        outPrintWriter.println("");
        outPrintWriter.println("  unsuspend [--user USER_ID] PACKAGE [PACKAGE...]");
        outPrintWriter.println("    Unsuspends the specified package(s) (as user).");
        outPrintWriter.println("");
        outPrintWriter.println("  set-distracting-restriction [--user USER_ID] [--flag FLAG ...]");
        outPrintWriter.println("      PACKAGE [PACKAGE...]");
        outPrintWriter.println("    Sets the specified restriction flags to given package(s) (for user).");
        outPrintWriter.println("    Flags are:");
        outPrintWriter.println("      hide-notifications: Hides notifications from this package");
        outPrintWriter.println("      hide-from-suggestions: Hides this package from suggestions");
        outPrintWriter.println("        (by the launcher, etc.)");
        outPrintWriter.println("    Any existing flags are overwritten, which also means that if no flags are");
        outPrintWriter.println("    specified then all existing flags will be cleared.");
        outPrintWriter.println("");
        outPrintWriter.println("  get-distracting-restriction [--user USER_ID] PACKAGE [PACKAGE...]");
        outPrintWriter.println("    Gets the specified restriction flags of given package(s) (of the user).");
        outPrintWriter.println("");
        outPrintWriter.println("  grant [--user USER_ID] [--all-permissions] PACKAGE PERMISSION");
        outPrintWriter.println("  revoke [--user USER_ID] [--all-permissions] PACKAGE PERMISSION");
        outPrintWriter.println("    These commands either grant or revoke permissions to apps.  The permissions");
        outPrintWriter.println("    must be declared as used in the app's manifest, be runtime permissions");
        outPrintWriter.println("    (protection level dangerous), and the app targeting SDK greater than Lollipop MR1.");
        outPrintWriter.println("    Flags are:");
        outPrintWriter.println("    --user: Specifies the user for which the operation needs to be performed");
        outPrintWriter.println("    --all-permissions: If specified all the missing runtime permissions will");
        outPrintWriter.println("       be granted to the PACKAGE or to all the packages if none is specified.");
        outPrintWriter.println("");
        outPrintWriter.println("  set-permission-flags [--user USER_ID] PACKAGE PERMISSION [FLAGS..]");
        outPrintWriter.println("  clear-permission-flags [--user USER_ID] PACKAGE PERMISSION [FLAGS..]");
        outPrintWriter.println("    These commands either set or clear permission flags on apps.  The permissions");
        outPrintWriter.println("    must be declared as used in the app's manifest, be runtime permissions");
        outPrintWriter.println("    (protection level dangerous), and the app targeting SDK greater than Lollipop MR1.");
        outPrintWriter.println("    The flags must be one or more of " + SUPPORTED_PERMISSION_FLAGS_LIST);
        outPrintWriter.println("");
        outPrintWriter.println("  reset-permissions");
        outPrintWriter.println("    Revert all runtime permissions to their default state.");
        outPrintWriter.println("");
        outPrintWriter.println("  set-permission-enforced PERMISSION [true|false]");
        outPrintWriter.println("");
        outPrintWriter.println("  get-privapp-permissions TARGET-PACKAGE");
        outPrintWriter.println("    Prints all privileged permissions for a package.");
        outPrintWriter.println("");
        outPrintWriter.println("  get-privapp-deny-permissions TARGET-PACKAGE");
        outPrintWriter.println("    Prints all privileged permissions that are denied for a package.");
        outPrintWriter.println("");
        outPrintWriter.println("  get-oem-permissions TARGET-PACKAGE");
        outPrintWriter.println("    Prints all OEM permissions for a package.");
        outPrintWriter.println("");
        outPrintWriter.println("  trim-caches DESIRED_FREE_SPACE [internal|UUID]");
        outPrintWriter.println("    Trim cache files to reach the given free space.");
        outPrintWriter.println("");
        outPrintWriter.println("  list users");
        outPrintWriter.println("    Lists the current users.");
        outPrintWriter.println("");
        outPrintWriter.println("  create-user [--profileOf USER_ID] [--managed] [--restricted] [--guest]");
        outPrintWriter.println("       [--user-type USER_TYPE] [--ephemeral] [--for-testing] [--pre-create-only]   USER_NAME");
        outPrintWriter.println("    Create a new user with the given USER_NAME, printing the new user identifier");
        outPrintWriter.println("    of the user.");
        outPrintWriter.println("    USER_TYPE is the name of a user type, e.g. android.os.usertype.profile.MANAGED.");
        outPrintWriter.println("      If not specified, the default user type is android.os.usertype.full.SECONDARY.");
        outPrintWriter.println("      --managed is shorthand for '--user-type android.os.usertype.profile.MANAGED'.");
        outPrintWriter.println("      --restricted is shorthand for '--user-type android.os.usertype.full.RESTRICTED'.");
        outPrintWriter.println("      --guest is shorthand for '--user-type android.os.usertype.full.GUEST'.");
        outPrintWriter.println("");
        outPrintWriter.println("  remove-user [--set-ephemeral-if-in-use | --wait] USER_ID");
        outPrintWriter.println("    Remove the user with the given USER_IDENTIFIER, deleting all data");
        outPrintWriter.println("    associated with that user.");
        outPrintWriter.println("      --set-ephemeral-if-in-use: If the user is currently running and");
        outPrintWriter.println("        therefore cannot be removed immediately, mark the user as ephemeral");
        outPrintWriter.println("        so that it will be automatically removed when possible (after user");
        outPrintWriter.println("        switch or reboot)");
        outPrintWriter.println("      --wait: Wait until user is removed. Ignored if set-ephemeral-if-in-use");
        outPrintWriter.println("");
        outPrintWriter.println("  mark-guest-for-deletion USER_ID");
        outPrintWriter.println("    Mark the guest user for deletion. After this, it is possible to create a");
        outPrintWriter.println("    new guest user and switch to it. This allows resetting the guest user");
        outPrintWriter.println("    without switching to another user.");
        outPrintWriter.println("");
        outPrintWriter.println("  rename-user USER_ID [USER_NAME]");
        outPrintWriter.println("    Rename USER_ID with USER_NAME (or null when [USER_NAME] is not set)");
        outPrintWriter.println("");
        outPrintWriter.println("  set-user-restriction [--user USER_ID] RESTRICTION VALUE");
        outPrintWriter.println("");
        outPrintWriter.println("  get-user-restriction [--user USER_ID] [--all] RESTRICTION_KEY");
        outPrintWriter.println("    Display the value of restriction for the given restriction key if the");
        outPrintWriter.println("    given user is valid.");
        outPrintWriter.println("      --all: display all restrictions for the given user");
        outPrintWriter.println("          This option is used without restriction key");
        outPrintWriter.println("");
        outPrintWriter.println("  get-max-users");
        outPrintWriter.println("");
        outPrintWriter.println("  get-max-running-users");
        outPrintWriter.println("");
        outPrintWriter.println("  set-home-activity [--user USER_ID] TARGET-COMPONENT");
        outPrintWriter.println("    Set the default home activity (aka launcher).");
        outPrintWriter.println("    TARGET-COMPONENT can be a package name (com.package.my) or a full");
        outPrintWriter.println("    component (com.package.my/component.name). However, only the package name");
        outPrintWriter.println("    matters: the actual component used will be determined automatically from");
        outPrintWriter.println("    the package.");
        outPrintWriter.println("");
        outPrintWriter.println("  set-installer PACKAGE INSTALLER");
        outPrintWriter.println("    Set installer package name");
        outPrintWriter.println("");
        outPrintWriter.println("  get-instantapp-resolver");
        outPrintWriter.println("    Return the name of the component that is the current instant app installer.");
        outPrintWriter.println("");
        outPrintWriter.println("  set-harmful-app-warning [--user <USER_ID>] <PACKAGE> [<WARNING>]");
        outPrintWriter.println("    Mark the app as harmful with the given warning message.");
        outPrintWriter.println("");
        outPrintWriter.println("  get-harmful-app-warning [--user <USER_ID>] <PACKAGE>");
        outPrintWriter.println("    Return the harmful app warning message for the given app, if present");
        outPrintWriter.println();
        outPrintWriter.println("  uninstall-system-updates [<PACKAGE>]");
        outPrintWriter.println("    Removes updates to the given system application and falls back to its");
        outPrintWriter.println("    /system version. Does nothing if the given package is not a system app.");
        outPrintWriter.println("    If no package is specified, removes updates to all system applications.");
        outPrintWriter.println("");
        outPrintWriter.println("  get-moduleinfo [--all | --installed] [module-name]");
        outPrintWriter.println("    Displays module info. If module-name is specified only that info is shown");
        outPrintWriter.println("    By default, without any argument only installed modules are shown.");
        outPrintWriter.println("      --all: show all module info");
        outPrintWriter.println("      --installed: show only installed modules");
        outPrintWriter.println("");
        outPrintWriter.println("  log-visibility [--enable|--disable] <PACKAGE>");
        outPrintWriter.println("    Turns on debug logging when visibility is blocked for the given package.");
        outPrintWriter.println("      --enable: turn on debug logging (default)");
        outPrintWriter.println("      --disable: turn off debug logging");
        outPrintWriter.println("");
        outPrintWriter.println("  set-silent-updates-policy [--allow-unlimited-silent-updates <INSTALLER>]");
        outPrintWriter.println("                            [--throttle-time <SECONDS>] [--reset]");
        outPrintWriter.println("    Sets the policies of the silent updates.");
        outPrintWriter.println("      --allow-unlimited-silent-updates: allows unlimited silent updated");
        outPrintWriter.println("        installation requests from the installer without the throttle time.");
        outPrintWriter.println("      --throttle-time: update the silent updates throttle time in seconds.");
        outPrintWriter.println("      --reset: restore the installer and throttle time to the default, and");
        outPrintWriter.println("        clear tracks of silent updates in the system.");
        outPrintWriter.println("");
        outPrintWriter.println("  clear-package-preferred-activities <PACKAGE>");
        outPrintWriter.println("    Remove the preferred activity mappings for the given package.");
        outPrintWriter.println("  wait-for-handler --timeout <MILLIS>");
        outPrintWriter.println("    Wait for a given amount of time till the package manager handler finishes");
        outPrintWriter.println("    handling all pending messages.");
        outPrintWriter.println("      --timeout: wait for a given number of milliseconds. If the handler(s)");
        outPrintWriter.println("        fail to finish before the timeout, the command returns error.");
        outPrintWriter.println("");
        outPrintWriter.println("  wait-for-background-handler --timeout <MILLIS>");
        outPrintWriter.println("    Wait for a given amount of time till the package manager's background");
        outPrintWriter.println("    handler finishes handling all pending messages.");
        outPrintWriter.println("      --timeout: wait for a given number of milliseconds. If the handler(s)");
        outPrintWriter.println("        fail to finish before the timeout, the command returns error.");
        outPrintWriter.println("");
        outPrintWriter.println("  archive [--user USER_ID] PACKAGE ");
        outPrintWriter.println("    During the archival process, the apps APKs and cache are removed from the");
        outPrintWriter.println("    device while the user data is kept. Options are:");
        outPrintWriter.println("      --user: archive the app from the given user.");
        outPrintWriter.println("");
        outPrintWriter.println("  request-unarchive [--user USER_ID] PACKAGE ");
        outPrintWriter.println("    Requests to unarchive a currently archived package by sending a request");
        outPrintWriter.println("    to unarchive an app to the responsible installer. Options are:");
        outPrintWriter.println("      --user: request unarchival of the app from the given user.");
        outPrintWriter.println("");
        outPrintWriter.println("  get-domain-verification-agent");
        outPrintWriter.println("    Displays the component name of the domain verification agent on device.");
        outPrintWriter.println("");
        if (com.android.server.pm.DexOptHelper.useArtService()) {
            printArtServiceHelp();
        } else {
            printLegacyDexoptHelp();
        }
        outPrintWriter.println("");
        this.mDomainVerificationShell.printHelp(outPrintWriter);
        outPrintWriter.println("");
        android.content.Intent.printIntentArgsHelp(outPrintWriter, "");
    }

    private void printArtServiceHelp() {
        com.android.internal.util.IndentingPrintWriter indentingPrintWriter = new com.android.internal.util.IndentingPrintWriter(getOutPrintWriter(), "  ");
        indentingPrintWriter.increaseIndent();
        try {
            ((com.android.server.art.ArtManagerLocal) com.android.server.LocalManagerRegistry.getManagerOrThrow(com.android.server.art.ArtManagerLocal.class)).printShellCommandHelp(indentingPrintWriter);
        } catch (com.android.server.LocalManagerRegistry.ManagerNotFoundException e) {
            indentingPrintWriter.println("ART Service is not ready. Please try again later");
        }
        indentingPrintWriter.decreaseIndent();
    }

    private void printLegacyDexoptHelp() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("  compile [-m MODE | -r REASON] [-f] [-c] [--split SPLIT_NAME]");
        outPrintWriter.println("          [--reset] [--check-prof (true | false)] (-a | TARGET-PACKAGE)");
        outPrintWriter.println("    Trigger compilation of TARGET-PACKAGE or all packages if \"-a\".  Options are:");
        outPrintWriter.println("      -a: compile all packages");
        outPrintWriter.println("      -c: clear profile data before compiling");
        outPrintWriter.println("      -f: force compilation even if not needed");
        outPrintWriter.println("      -m: select compilation mode");
        outPrintWriter.println("          MODE is one of the dex2oat compiler filters:");
        outPrintWriter.println("            verify");
        outPrintWriter.println("            speed-profile");
        outPrintWriter.println("            speed");
        outPrintWriter.println("      -r: select compilation reason");
        outPrintWriter.println("          REASON is one of:");
        for (int i = 0; i < com.android.server.pm.PackageManagerServiceCompilerMapping.REASON_STRINGS.length; i++) {
            outPrintWriter.println("            " + com.android.server.pm.PackageManagerServiceCompilerMapping.REASON_STRINGS[i]);
        }
        outPrintWriter.println("      --reset: restore package to its post-install state");
        outPrintWriter.println("      --check-prof (true | false): ignored - this is always true");
        outPrintWriter.println("      --secondary-dex: compile app secondary dex files");
        outPrintWriter.println("      --split SPLIT: compile only the given split name");
        outPrintWriter.println("");
        outPrintWriter.println("  force-dex-opt PACKAGE");
        outPrintWriter.println("    Force immediate execution of dex opt for the given PACKAGE.");
        outPrintWriter.println("");
        outPrintWriter.println("  delete-dexopt PACKAGE");
        outPrintWriter.println("    Delete dex optimization results for the given PACKAGE.");
        outPrintWriter.println("");
        outPrintWriter.println("  bg-dexopt-job [PACKAGE... | --cancel | --disable | --enable]");
        outPrintWriter.println("    Controls the background job that optimizes dex files:");
        outPrintWriter.println("    Without flags, run background optimization immediately on the given");
        outPrintWriter.println("    PACKAGEs, or all packages if none is specified, and wait until the job");
        outPrintWriter.println("    finishes. Note that the command only runs the background optimizer logic.");
        outPrintWriter.println("    It will run even if the device is not in the idle maintenance mode. If a");
        outPrintWriter.println("    job is already running (including one started automatically by the");
        outPrintWriter.println("    system) it will wait for it to finish before starting. A background job");
        outPrintWriter.println("    will not be started automatically while one started this way is running.");
        outPrintWriter.println("      --cancel: Cancels any currently running background optimization job");
        outPrintWriter.println("        immediately. This cancels jobs started either automatically by the");
        outPrintWriter.println("        system or through this command. Note that cancelling a currently");
        outPrintWriter.println("        running bg-dexopt-job command requires running this command from a");
        outPrintWriter.println("        separate adb shell.");
        outPrintWriter.println("      --disable: Disables background jobs from being started by the job");
        outPrintWriter.println("        scheduler. Does not affect bg-dexopt-job invocations from the shell.");
        outPrintWriter.println("        Does not imply --cancel. This state will be lost when the");
        outPrintWriter.println("        system_server process exits.");
        outPrintWriter.println("      --enable: Enables background jobs to be started by the job scheduler");
        outPrintWriter.println("        again, if previously disabled by --disable.");
        outPrintWriter.println("  cancel-bg-dexopt-job");
        outPrintWriter.println("    Same as bg-dexopt-job --cancel.");
        outPrintWriter.println("");
        outPrintWriter.println("  reconcile-secondary-dex-files TARGET-PACKAGE");
        outPrintWriter.println("    Reconciles the package secondary dex files with the generated oat files.");
        outPrintWriter.println("");
        outPrintWriter.println("  dump-profiles [--dump-classes-and-methods] TARGET-PACKAGE");
        outPrintWriter.println("    Dumps method/class profile files to");
        outPrintWriter.println("    /data/misc/profman/TARGET-PACKAGE-primary.prof.txt.");
        outPrintWriter.println("      --dump-classes-and-methods: passed along to the profman binary to");
        outPrintWriter.println("        switch to the format used by 'profman --create-profile-from'.");
        outPrintWriter.println("");
        outPrintWriter.println("  snapshot-profile TARGET-PACKAGE [--code-path path]");
        outPrintWriter.println("    Take a snapshot of the package profiles to");
        outPrintWriter.println("    /data/misc/profman/TARGET-PACKAGE[-code-path].prof");
        outPrintWriter.println("    If TARGET-PACKAGE=android it will take a snapshot of the boot image");
    }

    private static class LocalIntentReceiver {
        private final android.content.IIntentSender.Stub mLocalSender;
        private final java.util.concurrent.LinkedBlockingQueue<android.content.Intent> mResult;

        private LocalIntentReceiver() {
            this.mResult = new java.util.concurrent.LinkedBlockingQueue<>();
            this.mLocalSender = new android.content.IIntentSender.Stub() { // from class: com.android.server.pm.PackageManagerShellCommand.LocalIntentReceiver.1
                public void send(int i, android.content.Intent intent, java.lang.String str, android.os.IBinder iBinder, android.content.IIntentReceiver iIntentReceiver, java.lang.String str2, android.os.Bundle bundle) {
                    try {
                        com.android.server.pm.PackageManagerShellCommand.LocalIntentReceiver.this.mResult.offer(intent, 5L, java.util.concurrent.TimeUnit.SECONDS);
                    } catch (java.lang.InterruptedException e) {
                        throw new java.lang.RuntimeException(e);
                    }
                }
            };
        }

        public android.content.IntentSender getIntentSender() {
            return new android.content.IntentSender(this.mLocalSender);
        }

        public android.content.Intent getResult() {
            try {
                return this.mResult.take();
            } catch (java.lang.InterruptedException e) {
                throw new java.lang.RuntimeException(e);
            }
        }
    }
}
