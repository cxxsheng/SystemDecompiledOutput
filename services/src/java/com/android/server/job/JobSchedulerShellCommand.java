package com.android.server.job;

/* loaded from: classes2.dex */
public final class JobSchedulerShellCommand extends com.android.modules.utils.BasicShellCommandHandler {
    static final int BYTE_OPTION_DOWNLOAD = 0;
    static final int BYTE_OPTION_UPLOAD = 1;
    public static final int CMD_ERR_CONSTRAINTS = -1002;
    public static final int CMD_ERR_NO_JOB = -1001;
    public static final int CMD_ERR_NO_PACKAGE = -1000;
    com.android.server.job.JobSchedulerService mInternal;
    android.content.pm.IPackageManager mPM = android.app.AppGlobals.getPackageManager();

    JobSchedulerShellCommand(com.android.server.job.JobSchedulerService jobSchedulerService) {
        this.mInternal = jobSchedulerService;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public int onCommand(java.lang.String str) {
        char c;
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        java.lang.String str2 = str != null ? str : "";
        try {
            switch (str2.hashCode()) {
                case -1894245460:
                    if (str2.equals("trigger-dock-state")) {
                        c = 21;
                        break;
                    }
                    c = 65535;
                    break;
                case -1845752298:
                    if (str2.equals("get-storage-seq")) {
                        c = 11;
                        break;
                    }
                    c = 65535;
                    break;
                case -1711668249:
                    if (str2.equals("get-estimated-upload-bytes")) {
                        c = '\n';
                        break;
                    }
                    c = 65535;
                    break;
                case -1687551032:
                    if (str2.equals("get-battery-charging")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case -1367724422:
                    if (str2.equals("cancel")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case -1313911455:
                    if (str2.equals("timeout")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case -1274672338:
                    if (str2.equals("get-estimated-download-bytes")) {
                        c = '\t';
                        break;
                    }
                    c = 65535;
                    break;
                case -1040109341:
                    if (str2.equals("cache-config-changes")) {
                        c = 17;
                        break;
                    }
                    c = 65535;
                    break;
                case 113291:
                    if (str2.equals("run")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 3540994:
                    if (str2.equals("stop")) {
                        c = 20;
                        break;
                    }
                    c = 65535;
                    break;
                case 55361425:
                    if (str2.equals("get-storage-not-low")) {
                        c = '\f';
                        break;
                    }
                    c = 65535;
                    break;
                case 200896764:
                    if (str2.equals("heartbeat")) {
                        c = 16;
                        break;
                    }
                    c = 65535;
                    break;
                case 298223069:
                    if (str2.equals("get-transferred-upload-bytes")) {
                        c = 14;
                        break;
                    }
                    c = 65535;
                    break;
                case 703160488:
                    if (str2.equals("get-battery-seq")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case 826231557:
                    if (str2.equals("reset-execution-quota")) {
                        c = 18;
                        break;
                    }
                    c = 65535;
                    break;
                case 859357184:
                    if (str2.equals("reset-schedule-quota")) {
                        c = 19;
                        break;
                    }
                    c = 65535;
                    break;
                case 1025586161:
                    if (str2.equals("get-aconfig-flag-state")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 1173086269:
                    if (str2.equals("get-config-value")) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case 1749711139:
                    if (str2.equals("get-battery-not-low")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 1790568356:
                    if (str2.equals("get-transferred-download-bytes")) {
                        c = '\r';
                        break;
                    }
                    c = 65535;
                    break;
                case 1791471818:
                    if (str2.equals("get-job-state")) {
                        c = 15;
                        break;
                    }
                    c = 65535;
                    break;
                case 1854493850:
                    if (str2.equals("monitor-battery")) {
                        c = 3;
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
                    return runJob(outPrintWriter);
                case 1:
                    return timeout(outPrintWriter);
                case 2:
                    return cancelJob(outPrintWriter);
                case 3:
                    return monitorBattery(outPrintWriter);
                case 4:
                    return getAconfigFlagState(outPrintWriter);
                case 5:
                    return getBatterySeq(outPrintWriter);
                case 6:
                    return getBatteryCharging(outPrintWriter);
                case 7:
                    return getBatteryNotLow(outPrintWriter);
                case '\b':
                    return getConfigValue(outPrintWriter);
                case '\t':
                    return getEstimatedNetworkBytes(outPrintWriter, 0);
                case '\n':
                    return getEstimatedNetworkBytes(outPrintWriter, 1);
                case 11:
                    return getStorageSeq(outPrintWriter);
                case '\f':
                    return getStorageNotLow(outPrintWriter);
                case '\r':
                    return getTransferredNetworkBytes(outPrintWriter, 0);
                case 14:
                    return getTransferredNetworkBytes(outPrintWriter, 1);
                case 15:
                    return getJobState(outPrintWriter);
                case 16:
                    return doHeartbeat(outPrintWriter);
                case 17:
                    return cacheConfigChanges(outPrintWriter);
                case 18:
                    return resetExecutionQuota(outPrintWriter);
                case 19:
                    return resetScheduleQuota(outPrintWriter);
                case 20:
                    return stop(outPrintWriter);
                case 21:
                    return triggerDockState(outPrintWriter);
                default:
                    return handleDefaultCommands(str);
            }
        } catch (java.lang.Exception e) {
            outPrintWriter.println("Exception: " + e);
            return -1;
        }
    }

    private void checkPermission(java.lang.String str) throws java.lang.Exception {
        checkPermission(str, "android.permission.CHANGE_APP_IDLE_STATE");
    }

    private void checkPermission(java.lang.String str, java.lang.String str2) throws java.lang.Exception {
        int callingUid = android.os.Binder.getCallingUid();
        if (callingUid != 0 && this.mPM.checkUidPermission(str2, callingUid) != 0) {
            throw new java.lang.SecurityException("Uid " + callingUid + " not permitted to " + str);
        }
    }

    private boolean printError(int i, java.lang.String str, int i2, @android.annotation.Nullable java.lang.String str2, int i3) {
        switch (i) {
            case CMD_ERR_CONSTRAINTS /* -1002 */:
                java.io.PrintWriter errPrintWriter = getErrPrintWriter();
                errPrintWriter.print("Job ");
                errPrintWriter.print(i3);
                errPrintWriter.print(" in package ");
                errPrintWriter.print(str);
                if (str2 != null) {
                    errPrintWriter.print(" / namespace ");
                    errPrintWriter.print(str2);
                }
                errPrintWriter.print(" / user ");
                errPrintWriter.print(i2);
                errPrintWriter.println(" has functional constraints but --force not specified");
                break;
            case CMD_ERR_NO_JOB /* -1001 */:
                java.io.PrintWriter errPrintWriter2 = getErrPrintWriter();
                errPrintWriter2.print("Could not find job ");
                errPrintWriter2.print(i3);
                errPrintWriter2.print(" in package ");
                errPrintWriter2.print(str);
                if (str2 != null) {
                    errPrintWriter2.print(" / namespace ");
                    errPrintWriter2.print(str2);
                }
                errPrintWriter2.print(" / user ");
                errPrintWriter2.println(i2);
                break;
            case -1000:
                java.io.PrintWriter errPrintWriter3 = getErrPrintWriter();
                errPrintWriter3.print("Package not found: ");
                errPrintWriter3.print(str);
                errPrintWriter3.print(" / user ");
                errPrintWriter3.println(i2);
                break;
        }
        return true;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int runJob(java.io.PrintWriter printWriter) throws java.lang.Exception {
        char c;
        checkPermission("force scheduled jobs");
        java.lang.String str = null;
        int i = 0;
        boolean z = false;
        boolean z2 = false;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption == null) {
                if (z2 && z) {
                    printWriter.println("Cannot specify both --force and --satisfied");
                    return -1;
                }
                int currentUser = i == -2 ? android.app.ActivityManager.getCurrentUser() : i;
                java.lang.String nextArgRequired = getNextArgRequired();
                int parseInt = java.lang.Integer.parseInt(getNextArgRequired());
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    int executeRunCommand = this.mInternal.executeRunCommand(nextArgRequired, currentUser, str, parseInt, z, z2);
                    if (printError(executeRunCommand, nextArgRequired, currentUser, str, parseInt)) {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return executeRunCommand;
                    }
                    printWriter.print("Running job");
                    if (z2) {
                        printWriter.print(" [FORCED]");
                    }
                    printWriter.println();
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    return executeRunCommand;
                } catch (java.lang.Throwable th) {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            }
            switch (nextOption.hashCode()) {
                case -1626076853:
                    if (nextOption.equals("--force")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case -969907566:
                    if (nextOption.equals("--satisfied")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 1497:
                    if (nextOption.equals("-f")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 1505:
                    if (nextOption.equals("-n")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case 1510:
                    if (nextOption.equals("-s")) {
                        c = 2;
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
                case 1333469547:
                    if (nextOption.equals("--user")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case 1740612539:
                    if (nextOption.equals("--namespace")) {
                        c = 7;
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
                    z2 = true;
                    break;
                case 2:
                case 3:
                    z = true;
                    break;
                case 4:
                case 5:
                    i = android.os.UserHandle.parseUserArg(getNextArgRequired());
                    break;
                case 6:
                case 7:
                    str = getNextArgRequired();
                    break;
                default:
                    printWriter.println("Error: unknown option '" + nextOption + "'");
                    return -1;
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0034, code lost:
    
        if (r2.equals("-u") != false) goto L21;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int timeout(java.io.PrintWriter printWriter) throws java.lang.Exception {
        int i;
        checkPermission("force timeout jobs");
        java.lang.String str = null;
        int i2 = -1;
        while (true) {
            java.lang.String nextOption = getNextOption();
            char c = 0;
            if (nextOption != null) {
                switch (nextOption.hashCode()) {
                    case 1505:
                        if (nextOption.equals("-n")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1512:
                        break;
                    case 1333469547:
                        if (nextOption.equals("--user")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1740612539:
                        if (nextOption.equals("--namespace")) {
                            c = 3;
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
                        i2 = android.os.UserHandle.parseUserArg(getNextArgRequired());
                        break;
                    case 2:
                    case 3:
                        str = getNextArgRequired();
                        break;
                    default:
                        printWriter.println("Error: unknown option '" + nextOption + "'");
                        return -1;
                }
            } else {
                if (i2 != -2) {
                    i = i2;
                } else {
                    i = android.app.ActivityManager.getCurrentUser();
                }
                java.lang.String nextArg = getNextArg();
                java.lang.String nextArg2 = getNextArg();
                int parseInt = nextArg2 != null ? java.lang.Integer.parseInt(nextArg2) : -1;
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    return this.mInternal.executeStopCommand(printWriter, nextArg, i, str, nextArg2 != null, parseInt, 3, 3);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x002a, code lost:
    
        if (r1.equals("--user") != false) goto L21;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int cancelJob(java.io.PrintWriter printWriter) throws java.lang.Exception {
        checkPermission("cancel jobs");
        int i = 0;
        java.lang.String str = null;
        while (true) {
            java.lang.String nextOption = getNextOption();
            char c = 1;
            if (nextOption != null) {
                switch (nextOption.hashCode()) {
                    case 1505:
                        if (nextOption.equals("-n")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1512:
                        if (nextOption.equals("-u")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1333469547:
                        break;
                    case 1740612539:
                        if (nextOption.equals("--namespace")) {
                            c = 3;
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
                        i = android.os.UserHandle.parseUserArg(getNextArgRequired());
                        break;
                    case 2:
                    case 3:
                        str = getNextArgRequired();
                        break;
                    default:
                        printWriter.println("Error: unknown option '" + nextOption + "'");
                        return -1;
                }
            } else {
                if (i < 0) {
                    printWriter.println("Error: must specify a concrete user ID");
                    return -1;
                }
                java.lang.String nextArg = getNextArg();
                java.lang.String nextArg2 = getNextArg();
                int parseInt = nextArg2 != null ? java.lang.Integer.parseInt(nextArg2) : -1;
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    return this.mInternal.executeCancelCommand(printWriter, nextArg, i, str, nextArg2 != null, parseInt);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }
    }

    private int monitorBattery(java.io.PrintWriter printWriter) throws java.lang.Exception {
        checkPermission("change battery monitoring");
        java.lang.String nextArgRequired = getNextArgRequired();
        boolean z = true;
        if (!"on".equals(nextArgRequired)) {
            if ("off".equals(nextArgRequired)) {
                z = false;
            } else {
                getErrPrintWriter().println("Error: unknown option " + nextArgRequired);
                return 1;
            }
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mInternal.setMonitorBattery(z);
            if (z) {
                printWriter.println("Battery monitoring enabled");
            } else {
                printWriter.println("Battery monitoring disabled");
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return 0;
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int getAconfigFlagState(java.io.PrintWriter printWriter) throws java.lang.Exception {
        char c;
        checkPermission("get aconfig flag state", "android.permission.DUMP");
        java.lang.String nextArgRequired = getNextArgRequired();
        switch (nextArgRequired.hashCode()) {
            case -963776836:
                if (nextArgRequired.equals("android.app.job.enforce_minimum_time_windows")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -946452577:
                if (nextArgRequired.equals("android.app.job.job_debug_info_apis")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -930760458:
                if (nextArgRequired.equals(com.android.server.job.Flags.FLAG_BATCH_CONNECTIVITY_JOBS_PER_NETWORK)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -616213836:
                if (nextArgRequired.equals(com.android.server.job.Flags.FLAG_DO_NOT_FORCE_RUSH_EXECUTION_AT_BOOT)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -355604736:
                if (nextArgRequired.equals("android.app.job.backup_jobs_exemption")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -198167065:
                if (nextArgRequired.equals(com.android.server.job.Flags.FLAG_BATCH_ACTIVE_BUCKET_JOBS)) {
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
                printWriter.println(android.app.job.Flags.enforceMinimumTimeWindows());
                return 0;
            case 1:
                printWriter.println(android.app.job.Flags.jobDebugInfoApis());
                return 0;
            case 2:
                com.android.server.job.Flags.batchActiveBucketJobs();
                printWriter.println(false);
                return 0;
            case 3:
                com.android.server.job.Flags.batchConnectivityJobsPerNetwork();
                printWriter.println(false);
                return 0;
            case 4:
                com.android.server.job.Flags.doNotForceRushExecutionAtBoot();
                printWriter.println(false);
                return 0;
            case 5:
                printWriter.println(android.app.job.Flags.backupJobsExemption());
                return 0;
            default:
                printWriter.println("Unknown flag: " + nextArgRequired);
                return 0;
        }
    }

    private int getBatterySeq(java.io.PrintWriter printWriter) {
        printWriter.println(this.mInternal.getBatterySeq());
        return 0;
    }

    private int getBatteryCharging(java.io.PrintWriter printWriter) {
        printWriter.println(this.mInternal.isBatteryCharging());
        return 0;
    }

    private int getBatteryNotLow(java.io.PrintWriter printWriter) {
        printWriter.println(this.mInternal.isBatteryNotLow());
        return 0;
    }

    private int getConfigValue(java.io.PrintWriter printWriter) throws java.lang.Exception {
        checkPermission("get device config value", "android.permission.DUMP");
        java.lang.String nextArgRequired = getNextArgRequired();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            printWriter.println(this.mInternal.getConfigValue(nextArgRequired));
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return 0;
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int getEstimatedNetworkBytes(java.io.PrintWriter printWriter, int i) throws java.lang.Exception {
        int i2;
        char c;
        checkPermission("get estimated bytes");
        java.lang.String str = null;
        int i3 = 0;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                switch (nextOption.hashCode()) {
                    case 1505:
                        if (nextOption.equals("-n")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1512:
                        if (nextOption.equals("-u")) {
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
                    case 1740612539:
                        if (nextOption.equals("--namespace")) {
                            c = 3;
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
                        i3 = android.os.UserHandle.parseUserArg(getNextArgRequired());
                        break;
                    case 2:
                    case 3:
                        str = getNextArgRequired();
                        break;
                    default:
                        printWriter.println("Error: unknown option '" + nextOption + "'");
                        return -1;
                }
            } else {
                if (i3 != -2) {
                    i2 = i3;
                } else {
                    i2 = android.app.ActivityManager.getCurrentUser();
                }
                java.lang.String nextArgRequired = getNextArgRequired();
                int parseInt = java.lang.Integer.parseInt(getNextArgRequired());
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    int estimatedNetworkBytes = this.mInternal.getEstimatedNetworkBytes(printWriter, nextArgRequired, i2, str, parseInt, i);
                    printError(estimatedNetworkBytes, nextArgRequired, i2, str, parseInt);
                    return estimatedNetworkBytes;
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }
    }

    private int getStorageSeq(java.io.PrintWriter printWriter) {
        printWriter.println(this.mInternal.getStorageSeq());
        return 0;
    }

    private int getStorageNotLow(java.io.PrintWriter printWriter) {
        printWriter.println(this.mInternal.getStorageNotLow());
        return 0;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int getTransferredNetworkBytes(java.io.PrintWriter printWriter, int i) throws java.lang.Exception {
        int i2;
        char c;
        checkPermission("get transferred bytes");
        java.lang.String str = null;
        int i3 = 0;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                switch (nextOption.hashCode()) {
                    case 1505:
                        if (nextOption.equals("-n")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1512:
                        if (nextOption.equals("-u")) {
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
                    case 1740612539:
                        if (nextOption.equals("--namespace")) {
                            c = 3;
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
                        i3 = android.os.UserHandle.parseUserArg(getNextArgRequired());
                        break;
                    case 2:
                    case 3:
                        str = getNextArgRequired();
                        break;
                    default:
                        printWriter.println("Error: unknown option '" + nextOption + "'");
                        return -1;
                }
            } else {
                if (i3 != -2) {
                    i2 = i3;
                } else {
                    i2 = android.app.ActivityManager.getCurrentUser();
                }
                java.lang.String nextArgRequired = getNextArgRequired();
                int parseInt = java.lang.Integer.parseInt(getNextArgRequired());
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    int transferredNetworkBytes = this.mInternal.getTransferredNetworkBytes(printWriter, nextArgRequired, i2, str, parseInt, i);
                    printError(transferredNetworkBytes, nextArgRequired, i2, str, parseInt);
                    return transferredNetworkBytes;
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int getJobState(java.io.PrintWriter printWriter) throws java.lang.Exception {
        int i;
        char c;
        checkPermission("get job state");
        java.lang.String str = null;
        int i2 = 0;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                switch (nextOption.hashCode()) {
                    case 1505:
                        if (nextOption.equals("-n")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1512:
                        if (nextOption.equals("-u")) {
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
                    case 1740612539:
                        if (nextOption.equals("--namespace")) {
                            c = 3;
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
                        i2 = android.os.UserHandle.parseUserArg(getNextArgRequired());
                        break;
                    case 2:
                    case 3:
                        str = getNextArgRequired();
                        break;
                    default:
                        printWriter.println("Error: unknown option '" + nextOption + "'");
                        return -1;
                }
            } else {
                if (i2 != -2) {
                    i = i2;
                } else {
                    i = android.app.ActivityManager.getCurrentUser();
                }
                java.lang.String nextArgRequired = getNextArgRequired();
                int parseInt = java.lang.Integer.parseInt(getNextArgRequired());
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    int jobState = this.mInternal.getJobState(printWriter, nextArgRequired, i, str, parseInt);
                    printError(jobState, nextArgRequired, i, str, parseInt);
                    return jobState;
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }
    }

    private int doHeartbeat(java.io.PrintWriter printWriter) throws java.lang.Exception {
        checkPermission("manipulate scheduler heartbeat");
        printWriter.println("Heartbeat command is no longer supported");
        return -1;
    }

    private int cacheConfigChanges(java.io.PrintWriter printWriter) throws java.lang.Exception {
        checkPermission("change config caching", "android.permission.DUMP");
        java.lang.String nextArgRequired = getNextArgRequired();
        boolean z = true;
        if (!"on".equals(nextArgRequired)) {
            if ("off".equals(nextArgRequired)) {
                z = false;
            } else {
                getErrPrintWriter().println("Error: unknown option " + nextArgRequired);
                return 1;
            }
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mInternal.setCacheConfigChanges(z);
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("Config caching ");
            sb.append(z ? com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_ENABLED : com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_DISABLED);
            printWriter.println(sb.toString());
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return 0;
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int resetExecutionQuota(java.io.PrintWriter printWriter) throws java.lang.Exception {
        boolean z;
        checkPermission("reset execution quota");
        int i = 0;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                switch (nextOption.hashCode()) {
                    case 1512:
                        if (nextOption.equals("-u")) {
                            z = false;
                            break;
                        }
                        z = -1;
                        break;
                    case 1333469547:
                        if (nextOption.equals("--user")) {
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
                    case true:
                        i = android.os.UserHandle.parseUserArg(getNextArgRequired());
                    default:
                        printWriter.println("Error: unknown option '" + nextOption + "'");
                        return -1;
                }
            } else {
                if (i == -2) {
                    i = android.app.ActivityManager.getCurrentUser();
                }
                java.lang.String nextArgRequired = getNextArgRequired();
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    this.mInternal.resetExecutionQuota(nextArgRequired, i);
                    return 0;
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }
    }

    private int resetScheduleQuota(java.io.PrintWriter printWriter) throws java.lang.Exception {
        checkPermission("reset schedule quota");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mInternal.resetScheduleQuota();
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return 0;
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0045, code lost:
    
        if (r2.equals("-u") != false) goto L33;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int stop(java.io.PrintWriter printWriter) throws java.lang.Exception {
        int i;
        checkPermission("stop jobs");
        int i2 = -1;
        java.lang.String str = null;
        int i3 = 13;
        int i4 = -1;
        while (true) {
            java.lang.String nextOption = getNextOption();
            char c = 0;
            if (nextOption != null) {
                switch (nextOption.hashCode()) {
                    case -1405909809:
                        if (nextOption.equals("--stop-reason")) {
                            c = 5;
                            break;
                        }
                        c = 65535;
                        break;
                    case android.net.util.NetworkConstants.ETHER_MTU /* 1500 */:
                        if (nextOption.equals("-i")) {
                            c = 6;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1505:
                        if (nextOption.equals("-n")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1510:
                        if (nextOption.equals("-s")) {
                            c = 4;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1512:
                        break;
                    case 617801983:
                        if (nextOption.equals("--internal-stop-reason")) {
                            c = 7;
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
                    case 1740612539:
                        if (nextOption.equals("--namespace")) {
                            c = 3;
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
                        i4 = android.os.UserHandle.parseUserArg(getNextArgRequired());
                        break;
                    case 2:
                    case 3:
                        str = getNextArgRequired();
                        break;
                    case 4:
                    case 5:
                        i3 = java.lang.Integer.parseInt(getNextArgRequired());
                        break;
                    case 6:
                    case 7:
                        i2 = java.lang.Integer.parseInt(getNextArgRequired());
                        break;
                    default:
                        printWriter.println("Error: unknown option '" + nextOption + "'");
                        return -1;
                }
            } else {
                if (i4 != -2) {
                    i = i4;
                } else {
                    i = android.app.ActivityManager.getCurrentUser();
                }
                java.lang.String nextArg = getNextArg();
                java.lang.String nextArg2 = getNextArg();
                int parseInt = nextArg2 != null ? java.lang.Integer.parseInt(nextArg2) : -1;
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    return this.mInternal.executeStopCommand(printWriter, nextArg, i, str, nextArg2 != null, parseInt, i3, i2);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }
    }

    private int triggerDockState(java.io.PrintWriter printWriter) throws java.lang.Exception {
        checkPermission("trigger wireless charging dock state");
        java.lang.String nextArgRequired = getNextArgRequired();
        boolean z = true;
        if (!"idle".equals(nextArgRequired)) {
            if (com.android.server.pm.verify.domain.DomainVerificationPersistence.TAG_ACTIVE.equals(nextArgRequired)) {
                z = false;
            } else {
                getErrPrintWriter().println("Error: unknown option " + nextArgRequired);
                return 1;
            }
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mInternal.triggerDockState(z);
            return 0;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void onHelp() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Job scheduler (jobscheduler) commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println("  run [-f | --force] [-s | --satisfied] [-u | --user USER_ID] [-n | --namespace NAMESPACE] PACKAGE JOB_ID");
        outPrintWriter.println("    Trigger immediate execution of a specific scheduled job. For historical");
        outPrintWriter.println("    reasons, some constraints, such as battery, are ignored when this");
        outPrintWriter.println("    command is called. If you don't want any constraints to be ignored,");
        outPrintWriter.println("    include the -s flag.");
        outPrintWriter.println("    Options:");
        outPrintWriter.println("      -f or --force: run the job even if technical constraints such as");
        outPrintWriter.println("         connectivity are not currently met. This is incompatible with -f ");
        outPrintWriter.println("         and so an error will be reported if both are given.");
        outPrintWriter.println("      -n or --namespace: specify the namespace this job sits in; the default");
        outPrintWriter.println("         is null (no namespace).");
        outPrintWriter.println("      -s or --satisfied: run the job only if all constraints are met.");
        outPrintWriter.println("         This is incompatible with -f and so an error will be reported");
        outPrintWriter.println("         if both are given.");
        outPrintWriter.println("      -u or --user: specify which user's job is to be run; the default is");
        outPrintWriter.println("         the primary or system user");
        outPrintWriter.println("  stop [-u | --user USER_ID] [-n | --namespace NAMESPACE] [-s | --stop-reason STOP_REASON] [-i | --internal-stop-reason STOP_REASON] [PACKAGE] [JOB_ID]");
        outPrintWriter.println("    Trigger immediate stop of currently executing jobs using the specified");
        outPrintWriter.println("    stop reasons.");
        outPrintWriter.println("    Options:");
        outPrintWriter.println("      -u or --user: specify which user's job is to be run; the default is");
        outPrintWriter.println("         all users");
        outPrintWriter.println("      -n or --namespace: specify the namespace this job sits in; the default");
        outPrintWriter.println("         is null (no namespace).");
        outPrintWriter.println("      -s or --stop-reason: specify the stop reason given to the job.");
        outPrintWriter.println("         Valid values are those that can be returned from");
        outPrintWriter.println("         JobParameters.getStopReason().");
        outPrintWriter.println("          The default value is STOP_REASON_USER.");
        outPrintWriter.println("      -i or --internal-stop-reason: specify the internal stop reason.");
        outPrintWriter.println("         JobScheduler will use for internal processing.");
        outPrintWriter.println("         Valid values are those that can be returned from");
        outPrintWriter.println("         JobParameters.getInternalStopReason().");
        outPrintWriter.println("          The default value is INTERNAL_STOP_REASON_UNDEFINED.");
        outPrintWriter.println("  timeout [-u | --user USER_ID] [-n | --namespace NAMESPACE] [PACKAGE] [JOB_ID]");
        outPrintWriter.println("    Trigger immediate timeout of currently executing jobs, as if their");
        outPrintWriter.println("    execution timeout had expired.");
        outPrintWriter.println("    This is the equivalent of calling `stop -s 3 -i 3`.");
        outPrintWriter.println("    Options:");
        outPrintWriter.println("      -u or --user: specify which user's job is to be run; the default is");
        outPrintWriter.println("         all users");
        outPrintWriter.println("      -n or --namespace: specify the namespace this job sits in; the default");
        outPrintWriter.println("         is null (no namespace).");
        outPrintWriter.println("  cancel [-u | --user USER_ID] [-n | --namespace NAMESPACE] PACKAGE [JOB_ID]");
        outPrintWriter.println("    Cancel a scheduled job.  If a job ID is not supplied, all jobs scheduled");
        outPrintWriter.println("    by that package will be canceled.  USE WITH CAUTION.");
        outPrintWriter.println("    Options:");
        outPrintWriter.println("      -u or --user: specify which user's job is to be run; the default is");
        outPrintWriter.println("         the primary or system user");
        outPrintWriter.println("      -n or --namespace: specify the namespace this job sits in; the default");
        outPrintWriter.println("         is null (no namespace).");
        outPrintWriter.println("  heartbeat [num]");
        outPrintWriter.println("    No longer used.");
        outPrintWriter.println("  cache-config-changes [on|off]");
        outPrintWriter.println("    Control caching the set of most recently processed config flags.");
        outPrintWriter.println("    Off by default.  Turning on makes get-config-value useful.");
        outPrintWriter.println("  monitor-battery [on|off]");
        outPrintWriter.println("    Control monitoring of all battery changes.  Off by default.  Turning");
        outPrintWriter.println("    on makes get-battery-seq useful.");
        outPrintWriter.println("  get-aconfig-flag-state FULL_FLAG_NAME");
        outPrintWriter.println("    Return the state of the specified aconfig flag, if known. The flag name");
        outPrintWriter.println("         must be fully qualified.");
        outPrintWriter.println("  get-battery-seq");
        outPrintWriter.println("    Return the last battery update sequence number that was received.");
        outPrintWriter.println("  get-battery-charging");
        outPrintWriter.println("    Return whether the battery is currently considered to be charging.");
        outPrintWriter.println("  get-battery-not-low");
        outPrintWriter.println("    Return whether the battery is currently considered to not be low.");
        outPrintWriter.println("  get-config-value KEY");
        outPrintWriter.println("    Return the most recently processed and cached config value for the KEY.");
        outPrintWriter.println("    Only useful if caching is turned on with cache-config-changes.");
        outPrintWriter.println("  get-estimated-download-bytes [-u | --user USER_ID] [-n | --namespace NAMESPACE] PACKAGE JOB_ID");
        outPrintWriter.println("    Return the most recent estimated download bytes for the job.");
        outPrintWriter.println("    Options:");
        outPrintWriter.println("      -u or --user: specify which user's job is to be run; the default is");
        outPrintWriter.println("         the primary or system user");
        outPrintWriter.println("  get-estimated-upload-bytes [-u | --user USER_ID] [-n | --namespace NAMESPACE] PACKAGE JOB_ID");
        outPrintWriter.println("    Return the most recent estimated upload bytes for the job.");
        outPrintWriter.println("    Options:");
        outPrintWriter.println("      -u or --user: specify which user's job is to be run; the default is");
        outPrintWriter.println("         the primary or system user");
        outPrintWriter.println("  get-storage-seq");
        outPrintWriter.println("    Return the last storage update sequence number that was received.");
        outPrintWriter.println("  get-storage-not-low");
        outPrintWriter.println("    Return whether storage is currently considered to not be low.");
        outPrintWriter.println("  get-transferred-download-bytes [-u | --user USER_ID] [-n | --namespace NAMESPACE] PACKAGE JOB_ID");
        outPrintWriter.println("    Return the most recent transferred download bytes for the job.");
        outPrintWriter.println("    Options:");
        outPrintWriter.println("      -u or --user: specify which user's job is to be run; the default is");
        outPrintWriter.println("         the primary or system user");
        outPrintWriter.println("  get-transferred-upload-bytes [-u | --user USER_ID] [-n | --namespace NAMESPACE] PACKAGE JOB_ID");
        outPrintWriter.println("    Return the most recent transferred upload bytes for the job.");
        outPrintWriter.println("    Options:");
        outPrintWriter.println("      -u or --user: specify which user's job is to be run; the default is");
        outPrintWriter.println("         the primary or system user");
        outPrintWriter.println("  get-job-state [-u | --user USER_ID] [-n | --namespace NAMESPACE] PACKAGE JOB_ID");
        outPrintWriter.println("    Return the current state of a job, may be any combination of:");
        outPrintWriter.println("      pending: currently on the pending list, waiting to be active");
        outPrintWriter.println("      active: job is actively running");
        outPrintWriter.println("      user-stopped: job can't run because its user is stopped");
        outPrintWriter.println("      backing-up: job can't run because app is currently backing up its data");
        outPrintWriter.println("      no-component: job can't run because its component is not available");
        outPrintWriter.println("      ready: job is ready to run (all constraints satisfied or bypassed)");
        outPrintWriter.println("      waiting: if nothing else above is printed, job not ready to run");
        outPrintWriter.println("    Options:");
        outPrintWriter.println("      -u or --user: specify which user's job is to be run; the default is");
        outPrintWriter.println("         the primary or system user");
        outPrintWriter.println("      -n or --namespace: specify the namespace this job sits in; the default");
        outPrintWriter.println("         is null (no namespace).");
        outPrintWriter.println("  trigger-dock-state [idle|active]");
        outPrintWriter.println("    Trigger wireless charging dock state.  Active by default.");
        outPrintWriter.println();
    }
}
