package com.android.server.am;

/* loaded from: classes.dex */
class AppErrors {
    private static final java.lang.String TAG = "ActivityManager";

    @com.android.internal.annotations.GuardedBy({"mBadProcessLock"})
    private android.util.ArraySet<java.lang.String> mAppsNotReportingCrashes;
    private final android.content.Context mContext;
    private final com.android.server.PackageWatchdog mPackageWatchdog;
    private final com.android.server.am.ActivityManagerGlobalLock mProcLock;
    private final com.android.server.am.ActivityManagerService mService;

    @com.android.internal.annotations.GuardedBy({"mBadProcessLock"})
    private final com.android.internal.app.ProcessMap<java.lang.Long> mProcessCrashTimes = new com.android.internal.app.ProcessMap<>();

    @com.android.internal.annotations.GuardedBy({"mBadProcessLock"})
    private final com.android.internal.app.ProcessMap<java.lang.Long> mProcessCrashTimesPersistent = new com.android.internal.app.ProcessMap<>();

    @com.android.internal.annotations.GuardedBy({"mBadProcessLock"})
    private final com.android.internal.app.ProcessMap<java.lang.Long> mProcessCrashShowDialogTimes = new com.android.internal.app.ProcessMap<>();

    @com.android.internal.annotations.GuardedBy({"mBadProcessLock"})
    private final com.android.internal.app.ProcessMap<android.util.Pair<java.lang.Long, java.lang.Integer>> mProcessCrashCounts = new com.android.internal.app.ProcessMap<>();
    private volatile com.android.internal.app.ProcessMap<com.android.server.am.AppErrors.BadProcessInfo> mBadProcesses = new com.android.internal.app.ProcessMap<>();
    private final java.lang.Object mBadProcessLock = new java.lang.Object();

    AppErrors(android.content.Context context, com.android.server.am.ActivityManagerService activityManagerService, com.android.server.PackageWatchdog packageWatchdog) {
        context.assertRuntimeOverlayThemable();
        this.mService = activityManagerService;
        this.mProcLock = activityManagerService.mProcLock;
        this.mContext = context;
        this.mPackageWatchdog = packageWatchdog;
    }

    public void resetState() {
        android.util.Slog.i(TAG, "Resetting AppErrors");
        synchronized (this.mBadProcessLock) {
            this.mAppsNotReportingCrashes.clear();
            this.mProcessCrashTimes.clear();
            this.mProcessCrashTimesPersistent.clear();
            this.mProcessCrashShowDialogTimes.clear();
            this.mProcessCrashCounts.clear();
            this.mBadProcesses = new com.android.internal.app.ProcessMap<>();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    void dumpDebugLPr(android.util.proto.ProtoOutputStream protoOutputStream, long j, java.lang.String str) {
        long j2;
        android.util.ArrayMap arrayMap;
        int i;
        java.lang.String str2;
        android.util.SparseArray sparseArray;
        long j3;
        java.lang.String str3;
        android.util.SparseArray sparseArray2;
        android.util.ArrayMap arrayMap2;
        int i2;
        com.android.internal.app.ProcessMap<com.android.server.am.AppErrors.BadProcessInfo> processMap = this.mBadProcesses;
        if (this.mProcessCrashTimes.getMap().isEmpty() && processMap.getMap().isEmpty()) {
            return;
        }
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1112396529665L, android.os.SystemClock.uptimeMillis());
        long j4 = 1138166333441L;
        if (processMap.getMap().isEmpty()) {
            j2 = start;
        } else {
            android.util.ArrayMap map = processMap.getMap();
            int size = map.size();
            int i3 = 0;
            while (i3 < size) {
                long start2 = protoOutputStream.start(2246267895811L);
                java.lang.String str4 = (java.lang.String) map.keyAt(i3);
                android.util.SparseArray sparseArray3 = (android.util.SparseArray) map.valueAt(i3);
                int size2 = sparseArray3.size();
                protoOutputStream.write(j4, str4);
                int i4 = 0;
                while (i4 < size2) {
                    int keyAt = sparseArray3.keyAt(i4);
                    com.android.server.am.ProcessRecord processRecord = (com.android.server.am.ProcessRecord) this.mService.getProcessNamesLOSP().get(str4, keyAt);
                    if (str != null) {
                        if (processRecord == null) {
                            arrayMap2 = map;
                            j3 = start;
                            i2 = size;
                            str3 = str4;
                            sparseArray2 = sparseArray3;
                        } else if (!processRecord.getPkgList().containsKey(str)) {
                            arrayMap2 = map;
                            j3 = start;
                            i2 = size;
                            str3 = str4;
                            sparseArray2 = sparseArray3;
                        }
                        i4++;
                        str4 = str3;
                        size = i2;
                        sparseArray3 = sparseArray2;
                        start = j3;
                        map = arrayMap2;
                    }
                    com.android.server.am.AppErrors.BadProcessInfo badProcessInfo = (com.android.server.am.AppErrors.BadProcessInfo) sparseArray3.valueAt(i4);
                    j3 = start;
                    str3 = str4;
                    sparseArray2 = sparseArray3;
                    android.util.ArrayMap arrayMap3 = map;
                    long start3 = protoOutputStream.start(2246267895810L);
                    protoOutputStream.write(1120986464257L, keyAt);
                    arrayMap2 = arrayMap3;
                    i2 = size;
                    protoOutputStream.write(1112396529666L, badProcessInfo.time);
                    protoOutputStream.write(1138166333443L, badProcessInfo.shortMsg);
                    protoOutputStream.write(1138166333444L, badProcessInfo.longMsg);
                    protoOutputStream.write(1138166333445L, badProcessInfo.stack);
                    protoOutputStream.end(start3);
                    i4++;
                    str4 = str3;
                    size = i2;
                    sparseArray3 = sparseArray2;
                    start = j3;
                    map = arrayMap2;
                }
                protoOutputStream.end(start2);
                i3++;
                j4 = 1138166333441L;
            }
            j2 = start;
        }
        synchronized (this.mBadProcessLock) {
            try {
                if (!this.mProcessCrashTimes.getMap().isEmpty()) {
                    android.util.ArrayMap map2 = this.mProcessCrashTimes.getMap();
                    int size3 = map2.size();
                    int i5 = 0;
                    while (i5 < size3) {
                        long start4 = protoOutputStream.start(2246267895810L);
                        java.lang.String str5 = (java.lang.String) map2.keyAt(i5);
                        android.util.SparseArray sparseArray4 = (android.util.SparseArray) map2.valueAt(i5);
                        int size4 = sparseArray4.size();
                        protoOutputStream.write(1138166333441L, str5);
                        int i6 = 0;
                        while (i6 < size4) {
                            int keyAt2 = sparseArray4.keyAt(i6);
                            com.android.server.am.ProcessRecord processRecord2 = (com.android.server.am.ProcessRecord) this.mService.getProcessNamesLOSP().get(str5, keyAt2);
                            if (str != null) {
                                if (processRecord2 == null) {
                                    arrayMap = map2;
                                    i = size3;
                                    str2 = str5;
                                    sparseArray = sparseArray4;
                                } else if (!processRecord2.getPkgList().containsKey(str)) {
                                    arrayMap = map2;
                                    i = size3;
                                    str2 = str5;
                                    sparseArray = sparseArray4;
                                }
                                i6++;
                                map2 = arrayMap;
                                str5 = str2;
                                size3 = i;
                                sparseArray4 = sparseArray;
                            }
                            arrayMap = map2;
                            i = size3;
                            long start5 = protoOutputStream.start(2246267895810L);
                            protoOutputStream.write(1120986464257L, keyAt2);
                            str2 = str5;
                            sparseArray = sparseArray4;
                            protoOutputStream.write(1112396529666L, ((java.lang.Long) sparseArray4.valueAt(i6)).longValue());
                            protoOutputStream.end(start5);
                            i6++;
                            map2 = arrayMap;
                            str5 = str2;
                            size3 = i;
                            sparseArray4 = sparseArray;
                        }
                        protoOutputStream.end(start4);
                        i5++;
                        map2 = map2;
                        size3 = size3;
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        protoOutputStream.end(j2);
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    boolean dumpLPr(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, boolean z, java.lang.String str) {
        boolean z2;
        int i;
        int i2;
        com.android.server.am.AppErrors appErrors = this;
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        synchronized (appErrors.mBadProcessLock) {
            try {
                if (appErrors.mProcessCrashTimes.getMap().isEmpty()) {
                    z2 = z;
                } else {
                    android.util.ArrayMap map = appErrors.mProcessCrashTimes.getMap();
                    int size = map.size();
                    z2 = z;
                    int i3 = 0;
                    boolean z3 = false;
                    while (i3 < size) {
                        java.lang.String str2 = (java.lang.String) map.keyAt(i3);
                        android.util.SparseArray sparseArray = (android.util.SparseArray) map.valueAt(i3);
                        int size2 = sparseArray.size();
                        int i4 = 0;
                        while (i4 < size2) {
                            int keyAt = sparseArray.keyAt(i4);
                            android.util.ArrayMap arrayMap = map;
                            com.android.server.am.ProcessRecord processRecord = (com.android.server.am.ProcessRecord) appErrors.mService.getProcessNamesLOSP().get(str2, keyAt);
                            if (str == null || (processRecord != null && processRecord.getPkgList().containsKey(str))) {
                                if (!z3) {
                                    if (z2) {
                                        printWriter.println();
                                    }
                                    printWriter.println("  Time since processes crashed:");
                                    z2 = true;
                                    z3 = true;
                                }
                                printWriter.print("    Process ");
                                printWriter.print(str2);
                                printWriter.print(" uid ");
                                printWriter.print(keyAt);
                                printWriter.print(": last crashed ");
                                i2 = size;
                                android.util.TimeUtils.formatDuration(uptimeMillis - ((java.lang.Long) sparseArray.valueAt(i4)).longValue(), printWriter);
                                printWriter.println(" ago");
                                i4++;
                                size = i2;
                                map = arrayMap;
                            }
                            i2 = size;
                            i4++;
                            size = i2;
                            map = arrayMap;
                        }
                        i3++;
                        map = map;
                    }
                }
                if (!appErrors.mProcessCrashCounts.getMap().isEmpty()) {
                    android.util.ArrayMap map2 = appErrors.mProcessCrashCounts.getMap();
                    int size3 = map2.size();
                    boolean z4 = false;
                    for (int i5 = 0; i5 < size3; i5++) {
                        java.lang.String str3 = (java.lang.String) map2.keyAt(i5);
                        android.util.SparseArray sparseArray2 = (android.util.SparseArray) map2.valueAt(i5);
                        int size4 = sparseArray2.size();
                        int i6 = 0;
                        while (i6 < size4) {
                            int keyAt2 = sparseArray2.keyAt(i6);
                            android.util.ArrayMap arrayMap2 = map2;
                            com.android.server.am.ProcessRecord processRecord2 = (com.android.server.am.ProcessRecord) appErrors.mService.getProcessNamesLOSP().get(str3, keyAt2);
                            if (str != null && (processRecord2 == null || !processRecord2.getPkgList().containsKey(str))) {
                                i = size3;
                            } else {
                                if (!z4) {
                                    if (z2) {
                                        printWriter.println();
                                    }
                                    printWriter.println("  First time processes crashed and counts:");
                                    z4 = true;
                                    z2 = true;
                                }
                                printWriter.print("    Process ");
                                printWriter.print(str3);
                                printWriter.print(" uid ");
                                printWriter.print(keyAt2);
                                printWriter.print(": first crashed ");
                                i = size3;
                                android.util.TimeUtils.formatDuration(uptimeMillis - ((java.lang.Long) ((android.util.Pair) sparseArray2.valueAt(i6)).first).longValue(), printWriter);
                                printWriter.print(" ago; crashes since then: ");
                                printWriter.println(((android.util.Pair) sparseArray2.valueAt(i6)).second);
                            }
                            i6++;
                            map2 = arrayMap2;
                            size3 = i;
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        com.android.internal.app.ProcessMap<com.android.server.am.AppErrors.BadProcessInfo> processMap = appErrors.mBadProcesses;
        if (!processMap.getMap().isEmpty()) {
            android.util.ArrayMap map3 = processMap.getMap();
            int size5 = map3.size();
            int i7 = 0;
            boolean z5 = false;
            while (i7 < size5) {
                java.lang.String str4 = (java.lang.String) map3.keyAt(i7);
                android.util.SparseArray sparseArray3 = (android.util.SparseArray) map3.valueAt(i7);
                int size6 = sparseArray3.size();
                int i8 = 0;
                while (i8 < size6) {
                    int keyAt3 = sparseArray3.keyAt(i8);
                    com.android.server.am.ProcessRecord processRecord3 = (com.android.server.am.ProcessRecord) appErrors.mService.getProcessNamesLOSP().get(str4, keyAt3);
                    if (str == null || (processRecord3 != null && processRecord3.getPkgList().containsKey(str))) {
                        if (!z5) {
                            if (z2) {
                                printWriter.println();
                            }
                            printWriter.println("  Bad processes:");
                            z5 = true;
                            z2 = true;
                        }
                        com.android.server.am.AppErrors.BadProcessInfo badProcessInfo = (com.android.server.am.AppErrors.BadProcessInfo) sparseArray3.valueAt(i8);
                        printWriter.print("    Bad process ");
                        printWriter.print(str4);
                        printWriter.print(" uid ");
                        printWriter.print(keyAt3);
                        printWriter.print(": crashed at time ");
                        printWriter.println(badProcessInfo.time);
                        if (badProcessInfo.shortMsg != null) {
                            printWriter.print("      Short msg: ");
                            printWriter.println(badProcessInfo.shortMsg);
                        }
                        if (badProcessInfo.longMsg != null) {
                            printWriter.print("      Long msg: ");
                            printWriter.println(badProcessInfo.longMsg);
                        }
                        if (badProcessInfo.stack != null) {
                            printWriter.println("      Stack:");
                            int i9 = 0;
                            for (int i10 = 0; i10 < badProcessInfo.stack.length(); i10++) {
                                if (badProcessInfo.stack.charAt(i10) == '\n') {
                                    printWriter.print("        ");
                                    printWriter.write(badProcessInfo.stack, i9, i10 - i9);
                                    printWriter.println();
                                    i9 = i10 + 1;
                                }
                            }
                            if (i9 < badProcessInfo.stack.length()) {
                                printWriter.print("        ");
                                printWriter.write(badProcessInfo.stack, i9, badProcessInfo.stack.length() - i9);
                                printWriter.println();
                            }
                        }
                    }
                    i8++;
                    appErrors = this;
                }
                i7++;
                appErrors = this;
            }
        }
        return z2;
    }

    boolean isBadProcess(java.lang.String str, int i) {
        return this.mBadProcesses.get(str, i) != null;
    }

    void clearBadProcess(java.lang.String str, int i) {
        synchronized (this.mBadProcessLock) {
            com.android.internal.app.ProcessMap<com.android.server.am.AppErrors.BadProcessInfo> processMap = new com.android.internal.app.ProcessMap<>();
            processMap.putAll(this.mBadProcesses);
            processMap.remove(str, i);
            this.mBadProcesses = processMap;
        }
    }

    void markBadProcess(java.lang.String str, int i, com.android.server.am.AppErrors.BadProcessInfo badProcessInfo) {
        synchronized (this.mBadProcessLock) {
            com.android.internal.app.ProcessMap<com.android.server.am.AppErrors.BadProcessInfo> processMap = new com.android.internal.app.ProcessMap<>();
            processMap.putAll(this.mBadProcesses);
            processMap.put(str, i, badProcessInfo);
            this.mBadProcesses = processMap;
        }
    }

    void resetProcessCrashTime(java.lang.String str, int i) {
        synchronized (this.mBadProcessLock) {
            this.mProcessCrashTimes.remove(str, i);
            this.mProcessCrashCounts.remove(str, i);
        }
    }

    void resetProcessCrashTime(boolean z, int i, int i2) {
        synchronized (this.mBadProcessLock) {
            try {
                android.util.ArrayMap map = this.mProcessCrashTimes.getMap();
                for (int size = map.size() - 1; size >= 0; size--) {
                    android.util.SparseArray<?> sparseArray = (android.util.SparseArray) map.valueAt(size);
                    resetProcessCrashMapLBp(sparseArray, z, i, i2);
                    if (sparseArray.size() == 0) {
                        map.removeAt(size);
                    }
                }
                android.util.ArrayMap map2 = this.mProcessCrashCounts.getMap();
                for (int size2 = map2.size() - 1; size2 >= 0; size2--) {
                    android.util.SparseArray<?> sparseArray2 = (android.util.SparseArray) map2.valueAt(size2);
                    resetProcessCrashMapLBp(sparseArray2, z, i, i2);
                    if (sparseArray2.size() == 0) {
                        map2.removeAt(size2);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mBadProcessLock"})
    private void resetProcessCrashMapLBp(android.util.SparseArray<?> sparseArray, boolean z, int i, int i2) {
        boolean z2;
        for (int size = sparseArray.size() - 1; size >= 0; size--) {
            int keyAt = sparseArray.keyAt(size);
            if (!z) {
                if (i2 == -1) {
                    if (android.os.UserHandle.getAppId(keyAt) == i) {
                        z2 = true;
                    }
                    z2 = false;
                } else {
                    if (keyAt == android.os.UserHandle.getUid(i2, i)) {
                        z2 = true;
                    }
                    z2 = false;
                }
            } else {
                if (android.os.UserHandle.getUserId(keyAt) == i2) {
                    z2 = true;
                }
                z2 = false;
            }
            if (z2) {
                sparseArray.removeAt(size);
            }
        }
    }

    void loadAppsNotReportingCrashesFromConfig(java.lang.String str) {
        if (str != null) {
            java.lang.String[] split = str.split(",");
            if (split.length > 0) {
                synchronized (this.mBadProcessLock) {
                    this.mAppsNotReportingCrashes = new android.util.ArraySet<>();
                    java.util.Collections.addAll(this.mAppsNotReportingCrashes, split);
                }
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void killAppAtUserRequestLocked(com.android.server.am.ProcessRecord processRecord) {
        int i;
        int i2;
        com.android.server.am.ErrorDialogController dialogController = processRecord.mErrorState.getDialogController();
        com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
        com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
        synchronized (activityManagerGlobalLock) {
            try {
                if (!dialogController.hasDebugWaitingDialog()) {
                    i = 6;
                    i2 = 0;
                } else {
                    i = 13;
                    i2 = 1;
                }
                dialogController.clearAllErrorDialogs();
                killAppImmediateLSP(processRecord, i, i2, "user-terminated", "user request after error");
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                throw th;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    private void killAppImmediateLSP(com.android.server.am.ProcessRecord processRecord, int i, int i2, java.lang.String str, java.lang.String str2) {
        com.android.server.am.ProcessErrorStateRecord processErrorStateRecord = processRecord.mErrorState;
        processErrorStateRecord.setCrashing(false);
        processErrorStateRecord.setCrashingReport(null);
        processErrorStateRecord.setNotResponding(false);
        processErrorStateRecord.setNotRespondingReport(null);
        int pid = processErrorStateRecord.mApp.getPid();
        if (pid > 0 && pid != com.android.server.am.ActivityManagerService.MY_PID) {
            synchronized (this.mBadProcessLock) {
                handleAppCrashLSPB(processRecord, str, null, null, null, null);
            }
            processRecord.killLocked(str2, i, i2, true);
        }
    }

    void scheduleAppCrashLocked(int i, int i2, java.lang.String str, int i3, java.lang.String str2, boolean z, int i4, @android.annotation.Nullable android.os.Bundle bundle) {
        int i5;
        final com.android.server.am.ProcessRecord processRecord;
        synchronized (this.mService.mPidsSelfLocked) {
            processRecord = null;
            int i6 = 0;
            while (true) {
                try {
                    if (i6 >= this.mService.mPidsSelfLocked.size()) {
                        break;
                    }
                    com.android.server.am.ProcessRecord valueAt = this.mService.mPidsSelfLocked.valueAt(i6);
                    if (i < 0 || valueAt.uid == i) {
                        if (valueAt.getPid() == i2) {
                            processRecord = valueAt;
                            break;
                        } else if (valueAt.getPkgList().containsKey(str) && (i3 < 0 || valueAt.userId == i3)) {
                            processRecord = valueAt;
                        }
                    }
                    i6++;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
        if (processRecord == null) {
            android.util.Slog.w(TAG, "crashApplication: nothing for uid=" + i + " initialPid=" + i2 + " packageName=" + str + " userId=" + i3);
            return;
        }
        if (i4 == 5) {
            java.lang.String[] packageList = processRecord.getPackageList();
            for (i5 = 0; i5 < packageList.length; i5++) {
                if (this.mService.mPackageManagerInt.isPackageStateProtected(packageList[i5], processRecord.userId)) {
                    android.util.Slog.w(TAG, "crashApplication: Can not crash protected package " + packageList[i5]);
                    return;
                }
            }
        }
        this.mService.mOomAdjuster.mCachedAppOptimizer.unfreezeProcess(i2, 12);
        processRecord.scheduleCrashLocked(str2, i4, bundle);
        if (z) {
            this.mService.mHandler.postDelayed(new java.lang.Runnable() { // from class: com.android.server.am.AppErrors$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.AppErrors.this.lambda$scheduleAppCrashLocked$0(processRecord);
                }
            }, 5000L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleAppCrashLocked$0(com.android.server.am.ProcessRecord processRecord) {
        com.android.server.am.ActivityManagerService activityManagerService = this.mService;
        com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
                com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
                synchronized (activityManagerGlobalLock) {
                    try {
                        killAppImmediateLSP(processRecord, 13, 14, "forced", "killed for invalid state");
                    } catch (java.lang.Throwable th) {
                        com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                        throw th;
                    }
                }
                com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
            } catch (java.lang.Throwable th2) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                throw th2;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
    }

    void sendRecoverableCrashToAppExitInfo(com.android.server.am.ProcessRecord processRecord, android.app.ApplicationErrorReport.CrashInfo crashInfo) {
        if (processRecord == null || crashInfo == null || !"Native crash".equals(crashInfo.exceptionClassName)) {
            return;
        }
        com.android.server.am.ActivityManagerService activityManagerService = this.mService;
        com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                this.mService.mProcessList.noteAppRecoverableCrash(processRecord);
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
    }

    void crashApplication(com.android.server.am.ProcessRecord processRecord, android.app.ApplicationErrorReport.CrashInfo crashInfo) {
        int callingPid = android.os.Binder.getCallingPid();
        int callingUid = android.os.Binder.getCallingUid();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            crashApplicationInner(processRecord, crashInfo, callingPid, callingUid);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:61:0x01f0 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:68:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void crashApplicationInner(com.android.server.am.ProcessRecord processRecord, android.app.ApplicationErrorReport.CrashInfo crashInfo, int i, int i2) {
        java.lang.String str;
        int i3;
        android.content.Intent intent;
        int i4;
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        java.lang.String str2 = crashInfo.exceptionClassName;
        java.lang.String str3 = crashInfo.exceptionMessage;
        java.lang.String str4 = crashInfo.stackTrace;
        if (str2 != null && str3 != null) {
            str = str2 + ": " + str3;
        } else if (str2 == null) {
            str = str3;
        } else {
            str = str2;
        }
        if (processRecord != null) {
            this.mPackageWatchdog.onPackageFailure(processRecord.getPackageListWithVersionCode(), 3);
            com.android.server.am.ActivityManagerService activityManagerService = this.mService;
            com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService) {
                try {
                    com.android.server.am.ProcessList processList = this.mService.mProcessList;
                    if ("Native crash".equals(crashInfo.exceptionClassName)) {
                        i4 = 5;
                    } else {
                        i4 = 4;
                    }
                    processList.noteAppKill(processRecord, i4, 0, "crash");
                } finally {
                }
            }
            com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
        }
        if (processRecord == null) {
            i3 = 0;
        } else {
            i3 = processRecord.getWindowProcessController().computeRelaunchReason();
        }
        com.android.server.am.AppErrorResult appErrorResult = new com.android.server.am.AppErrorResult();
        com.android.server.am.ActivityManagerService activityManagerService2 = this.mService;
        com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService2) {
            int i5 = i3;
            try {
                if (handleAppCrashInActivityController(processRecord, crashInfo, str2, str, str4, currentTimeMillis, i, i2)) {
                    return;
                }
                if (i5 == 2) {
                    com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                if (processRecord != null && processRecord.getActiveInstrumentation() != null) {
                    com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                if (processRecord != null) {
                    this.mService.mBatteryStatsService.noteProcessCrash(processRecord.processName, processRecord.uid);
                }
                com.android.server.am.AppErrorDialog.Data data = new com.android.server.am.AppErrorDialog.Data();
                data.result = appErrorResult;
                data.proc = processRecord;
                if (processRecord != null && makeAppCrashingLocked(processRecord, str2, str, str4, data)) {
                    android.os.Message obtain = android.os.Message.obtain();
                    int i6 = 1;
                    obtain.what = 1;
                    int i7 = data.taskId;
                    obtain.obj = data;
                    this.mService.mUiHandler.sendMessage(obtain);
                    com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                    int i8 = appErrorResult.get();
                    com.android.internal.logging.MetricsLogger.action(this.mContext, com.android.internal.util.FrameworkStatsLog.APP_BACKGROUND_RESTRICTIONS_INFO__EXEMPTION_REASON__REASON_SHELL, i8);
                    if (i8 != 6 && i8 != 7) {
                        i6 = i8;
                    }
                    switch (i6) {
                        case 1:
                            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                            try {
                                this.mService.mAtmInternal.onHandleAppCrash(processRecord.getWindowProcessController());
                                if (!processRecord.isPersistent()) {
                                    com.android.server.am.ActivityManagerService activityManagerService3 = this.mService;
                                    com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
                                    synchronized (activityManagerService3) {
                                        try {
                                            this.mService.mProcessList.removeProcessLocked(processRecord, false, false, 4, "crash");
                                        } finally {
                                            com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                                        }
                                    }
                                    com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                                    this.mService.mAtmInternal.resumeTopActivities(false);
                                }
                                intent = null;
                                if (intent == null) {
                                    try {
                                        this.mContext.startActivityAsUser(intent, new android.os.UserHandle(processRecord.userId));
                                        return;
                                    } catch (android.content.ActivityNotFoundException e) {
                                        android.util.Slog.w(TAG, "bug report receiver dissappeared", e);
                                        return;
                                    }
                                }
                                return;
                            } finally {
                                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                            }
                        case 2:
                            com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
                            com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
                            synchronized (activityManagerGlobalLock) {
                                try {
                                    intent = createAppErrorIntentLOSP(processRecord, currentTimeMillis, crashInfo);
                                } catch (java.lang.Throwable th) {
                                    com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                                    throw th;
                                }
                            }
                            com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                            if (intent == null) {
                            }
                            break;
                        case 3:
                            com.android.server.am.ActivityManagerService activityManagerService4 = this.mService;
                            com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
                            synchronized (activityManagerService4) {
                                try {
                                    this.mService.mProcessList.removeProcessLocked(processRecord, false, true, 4, "crash");
                                } finally {
                                    com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                                }
                            }
                            com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                            if (i7 != -1) {
                                try {
                                    this.mService.startActivityFromRecents(i7, android.app.ActivityOptions.makeBasic().toBundle());
                                } catch (java.lang.IllegalArgumentException e2) {
                                    android.util.Slog.e(TAG, "Could not restart taskId=" + i7, e2);
                                }
                            }
                            intent = null;
                            if (intent == null) {
                            }
                            break;
                        case 4:
                        case 6:
                        case 7:
                        default:
                            intent = null;
                            if (intent == null) {
                            }
                            break;
                        case 5:
                            synchronized (this.mBadProcessLock) {
                                stopReportingCrashesLBp(processRecord);
                            }
                            intent = null;
                            if (intent == null) {
                            }
                            break;
                        case 8:
                            intent = new android.content.Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                            intent.setData(android.net.Uri.parse("package:" + processRecord.info.packageName));
                            intent.addFlags(268435456);
                            if (intent == null) {
                            }
                            break;
                    }
                } else {
                    com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                }
            } finally {
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    private boolean handleAppCrashInActivityController(final com.android.server.am.ProcessRecord processRecord, final android.app.ApplicationErrorReport.CrashInfo crashInfo, final java.lang.String str, final java.lang.String str2, final java.lang.String str3, long j, int i, int i2) {
        final java.lang.String str4 = processRecord != null ? processRecord.processName : null;
        int pid = processRecord != null ? processRecord.getPid() : i;
        final int i3 = processRecord != null ? processRecord.info.uid : i2;
        final int i4 = pid;
        return this.mService.mAtmInternal.handleAppCrashInActivityController(str4, pid, str, str2, j, crashInfo.stackTrace, new java.lang.Runnable() { // from class: com.android.server.am.AppErrors$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.am.AppErrors.this.lambda$handleAppCrashInActivityController$1(crashInfo, str4, i4, processRecord, str, str2, str3, i3);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleAppCrashInActivityController$1(android.app.ApplicationErrorReport.CrashInfo crashInfo, java.lang.String str, int i, com.android.server.am.ProcessRecord processRecord, java.lang.String str2, java.lang.String str3, java.lang.String str4, int i2) {
        if (android.os.Build.IS_DEBUGGABLE && "Native crash".equals(crashInfo.exceptionClassName)) {
            android.util.Slog.w(TAG, "Skip killing native crashed app " + str + "(" + i + ") during testing");
            return;
        }
        android.util.Slog.w(TAG, "Force-killing crashed app " + str + " at watcher's request");
        if (processRecord != null) {
            if (!makeAppCrashingLocked(processRecord, str2, str3, str4, null)) {
                processRecord.killLocked("crash", 4, true);
            }
        } else {
            android.os.Process.killProcess(i);
            com.android.server.am.ProcessList.killProcessGroup(i2, i);
            this.mService.mProcessList.noteAppKill(i, i2, 4, 0, "crash");
        }
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    private boolean makeAppCrashingLocked(com.android.server.am.ProcessRecord processRecord, java.lang.String str, java.lang.String str2, java.lang.String str3, com.android.server.am.AppErrorDialog.Data data) {
        boolean handleAppCrashLSPB;
        com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
        com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
        synchronized (activityManagerGlobalLock) {
            try {
                com.android.server.am.ProcessErrorStateRecord processErrorStateRecord = processRecord.mErrorState;
                processErrorStateRecord.setCrashing(true);
                processErrorStateRecord.setCrashingReport(generateProcessError(processRecord, 1, null, str, str2, str3));
                processErrorStateRecord.startAppProblemLSP();
                processRecord.getWindowProcessController().stopFreezingActivities();
                synchronized (this.mBadProcessLock) {
                    handleAppCrashLSPB = handleAppCrashLSPB(processRecord, "force-crash", str, str2, str3, data);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                throw th;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
        return handleAppCrashLSPB;
    }

    android.app.ActivityManager.ProcessErrorStateInfo generateProcessError(com.android.server.am.ProcessRecord processRecord, int i, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4) {
        android.app.ActivityManager.ProcessErrorStateInfo processErrorStateInfo = new android.app.ActivityManager.ProcessErrorStateInfo();
        processErrorStateInfo.condition = i;
        processErrorStateInfo.processName = processRecord.processName;
        processErrorStateInfo.pid = processRecord.getPid();
        processErrorStateInfo.uid = processRecord.info.uid;
        processErrorStateInfo.tag = str;
        processErrorStateInfo.shortMsg = str2;
        processErrorStateInfo.longMsg = str3;
        processErrorStateInfo.stackTrace = str4;
        return processErrorStateInfo;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    android.content.Intent createAppErrorIntentLOSP(com.android.server.am.ProcessRecord processRecord, long j, android.app.ApplicationErrorReport.CrashInfo crashInfo) {
        android.app.ApplicationErrorReport createAppErrorReportLOSP = createAppErrorReportLOSP(processRecord, j, crashInfo);
        if (createAppErrorReportLOSP == null) {
            return null;
        }
        android.content.Intent intent = new android.content.Intent("android.intent.action.APP_ERROR");
        intent.setComponent(processRecord.mErrorState.getErrorReportReceiver());
        intent.putExtra("android.intent.extra.BUG_REPORT", createAppErrorReportLOSP);
        intent.addFlags(268435456);
        return intent;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    private android.app.ApplicationErrorReport createAppErrorReportLOSP(com.android.server.am.ProcessRecord processRecord, long j, android.app.ApplicationErrorReport.CrashInfo crashInfo) {
        com.android.server.am.ProcessErrorStateRecord processErrorStateRecord = processRecord.mErrorState;
        if (processErrorStateRecord.getErrorReportReceiver() == null) {
            return null;
        }
        if (!processErrorStateRecord.isCrashing() && !processErrorStateRecord.isNotResponding() && !processErrorStateRecord.isForceCrashReport()) {
            return null;
        }
        android.app.ApplicationErrorReport applicationErrorReport = new android.app.ApplicationErrorReport();
        applicationErrorReport.packageName = processRecord.info.packageName;
        applicationErrorReport.installerPackageName = processErrorStateRecord.getErrorReportReceiver().getPackageName();
        applicationErrorReport.processName = processRecord.processName;
        applicationErrorReport.time = j;
        applicationErrorReport.systemApp = (processRecord.info.flags & 1) != 0;
        if (processErrorStateRecord.isCrashing() || processErrorStateRecord.isForceCrashReport()) {
            applicationErrorReport.type = 1;
            applicationErrorReport.crashInfo = crashInfo;
        } else if (processErrorStateRecord.isNotResponding()) {
            android.app.ActivityManager.ProcessErrorStateInfo notRespondingReport = processErrorStateRecord.getNotRespondingReport();
            if (notRespondingReport == null) {
                return null;
            }
            applicationErrorReport.type = 2;
            applicationErrorReport.anrInfo = new android.app.ApplicationErrorReport.AnrInfo();
            applicationErrorReport.anrInfo.activity = notRespondingReport.tag;
            applicationErrorReport.anrInfo.cause = notRespondingReport.shortMsg;
            applicationErrorReport.anrInfo.info = notRespondingReport.longMsg;
        }
        return applicationErrorReport;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x00d3  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0100  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0198 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x01a3  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x01b6  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x01d5  */
    /* JADX WARN: Removed duplicated region for block: B:52:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0184  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00d6  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x00bd  */
    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock", "mBadProcessLock"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean handleAppCrashLSPB(com.android.server.am.ProcessRecord processRecord, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, com.android.server.am.AppErrorDialog.Data data) {
        java.lang.Long l;
        java.lang.Long l2;
        com.android.server.am.ProcessErrorStateRecord processErrorStateRecord;
        boolean z;
        boolean z2;
        long j;
        boolean z3;
        com.android.server.wm.WindowProcessController windowProcessController;
        int i;
        com.android.server.am.ProcessErrorStateRecord processErrorStateRecord2;
        boolean z4;
        int i2;
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        boolean z5 = android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "anr_show_background", 0, this.mService.mUserController.getCurrentUserId()) != 0;
        java.lang.String str5 = processRecord.processName;
        int i3 = processRecord.uid;
        int i4 = processRecord.userId;
        boolean z6 = processRecord.isolated;
        boolean isPersistent = processRecord.isPersistent();
        com.android.server.wm.WindowProcessController windowProcessController2 = processRecord.getWindowProcessController();
        com.android.server.am.ProcessErrorStateRecord processErrorStateRecord3 = processRecord.mErrorState;
        if (!processRecord.isolated) {
            l = (java.lang.Long) this.mProcessCrashTimes.get(str5, i3);
            l2 = (java.lang.Long) this.mProcessCrashTimesPersistent.get(str5, i3);
        } else {
            l = null;
            l2 = null;
        }
        boolean incServiceCrashCountLocked = processRecord.mServices.incServiceCrashCountLocked(uptimeMillis);
        if (l != null) {
            processErrorStateRecord = processErrorStateRecord3;
            if (uptimeMillis < l.longValue() + com.android.server.am.ActivityManagerConstants.MIN_CRASH_INTERVAL) {
                z = true;
                if (z) {
                    if (!isProcOverCrashLimitLBp(processRecord, uptimeMillis)) {
                        int finishTopCrashedActivities = this.mService.mAtmInternal.finishTopCrashedActivities(windowProcessController2, str);
                        if (data != null) {
                            data.taskId = finishTopCrashedActivities;
                        }
                        if (data == null || l2 == null) {
                            z2 = incServiceCrashCountLocked;
                            z3 = z6;
                            j = uptimeMillis;
                            i = i3;
                            processErrorStateRecord2 = processErrorStateRecord;
                            windowProcessController = windowProcessController2;
                        } else {
                            z2 = incServiceCrashCountLocked;
                            if (uptimeMillis >= l2.longValue() + com.android.server.am.ActivityManagerConstants.MIN_CRASH_INTERVAL) {
                                z3 = z6;
                                j = uptimeMillis;
                                i = i3;
                                processErrorStateRecord2 = processErrorStateRecord;
                                windowProcessController = windowProcessController2;
                            } else {
                                data.repeating = true;
                                z3 = z6;
                                j = uptimeMillis;
                                i = i3;
                                processErrorStateRecord2 = processErrorStateRecord;
                                windowProcessController = windowProcessController2;
                            }
                        }
                        if (data != null && z2) {
                            data.isRestartableForService = true;
                        }
                        if (windowProcessController.isHomeProcess() && windowProcessController.hasActivities() && (processRecord.info.flags & 1) == 0) {
                            windowProcessController.clearPackagePreferredForHomeActivities();
                        }
                        if (!z3) {
                            int i5 = i;
                            this.mProcessCrashTimes.put(str5, i5, java.lang.Long.valueOf(j));
                            this.mProcessCrashTimesPersistent.put(str5, i5, java.lang.Long.valueOf(j));
                            updateProcessCrashCountLBp(str5, i5, j);
                        }
                        if (processErrorStateRecord2.getCrashHandler() != null) {
                            this.mService.mHandler.post(processErrorStateRecord2.getCrashHandler());
                            return true;
                        }
                        return true;
                    }
                    z2 = incServiceCrashCountLocked;
                } else {
                    z2 = incServiceCrashCountLocked;
                }
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append("Process ");
                sb.append(str5);
                sb.append(" has crashed too many times, killing! Reason: ");
                sb.append(!z ? "crashed quickly" : "over process crash limit");
                android.util.Slog.w(TAG, sb.toString());
                android.util.EventLog.writeEvent(com.android.server.am.EventLogTags.AM_PROCESS_CRASHED_TOO_MUCH, java.lang.Integer.valueOf(i4), str5, java.lang.Integer.valueOf(i3));
                this.mService.mAtmInternal.onHandleAppCrash(windowProcessController2);
                if (!isPersistent) {
                    z3 = z6;
                    j = uptimeMillis;
                    i = i3;
                    processErrorStateRecord2 = processErrorStateRecord;
                    z4 = false;
                    windowProcessController = windowProcessController2;
                } else {
                    android.util.EventLog.writeEvent(com.android.server.am.EventLogTags.AM_PROC_BAD, java.lang.Integer.valueOf(i4), java.lang.Integer.valueOf(i3), str5);
                    if (!z6) {
                        i = i3;
                        processErrorStateRecord2 = processErrorStateRecord;
                        windowProcessController = windowProcessController2;
                        z3 = z6;
                        j = uptimeMillis;
                        i2 = i4;
                        markBadProcess(str5, processRecord.uid, new com.android.server.am.AppErrors.BadProcessInfo(uptimeMillis, str2, str3, str4));
                        this.mProcessCrashTimes.remove(str5, processRecord.uid);
                        this.mProcessCrashCounts.remove(str5, processRecord.uid);
                    } else {
                        z3 = z6;
                        j = uptimeMillis;
                        i = i3;
                        processErrorStateRecord2 = processErrorStateRecord;
                        windowProcessController = windowProcessController2;
                        i2 = i4;
                    }
                    processErrorStateRecord2.setBad(true);
                    processRecord.setRemoved(true);
                    com.android.server.usage.AppStandbyInternal appStandbyInternal = (com.android.server.usage.AppStandbyInternal) com.android.server.LocalServices.getService(com.android.server.usage.AppStandbyInternal.class);
                    if (appStandbyInternal != null) {
                        appStandbyInternal.restrictApp(processRecord.info != null ? processRecord.info.packageName : str5, i2, 4);
                    }
                    this.mService.mProcessList.removeProcessLocked(processRecord, false, z2, 4, "crash");
                    z4 = false;
                    this.mService.mAtmInternal.resumeTopActivities(false);
                    if (!z5) {
                        return false;
                    }
                }
                this.mService.mAtmInternal.resumeTopActivities(z4);
                if (data != null) {
                    data.isRestartableForService = true;
                }
                if (windowProcessController.isHomeProcess()) {
                    windowProcessController.clearPackagePreferredForHomeActivities();
                }
                if (!z3) {
                }
                if (processErrorStateRecord2.getCrashHandler() != null) {
                }
            }
        } else {
            processErrorStateRecord = processErrorStateRecord3;
        }
        z = false;
        if (z) {
        }
        java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
        sb2.append("Process ");
        sb2.append(str5);
        sb2.append(" has crashed too many times, killing! Reason: ");
        sb2.append(!z ? "crashed quickly" : "over process crash limit");
        android.util.Slog.w(TAG, sb2.toString());
        android.util.EventLog.writeEvent(com.android.server.am.EventLogTags.AM_PROCESS_CRASHED_TOO_MUCH, java.lang.Integer.valueOf(i4), str5, java.lang.Integer.valueOf(i3));
        this.mService.mAtmInternal.onHandleAppCrash(windowProcessController2);
        if (!isPersistent) {
        }
        this.mService.mAtmInternal.resumeTopActivities(z4);
        if (data != null) {
        }
        if (windowProcessController.isHomeProcess()) {
        }
        if (!z3) {
        }
        if (processErrorStateRecord2.getCrashHandler() != null) {
        }
    }

    @com.android.internal.annotations.GuardedBy({"mBadProcessLock"})
    private void updateProcessCrashCountLBp(java.lang.String str, int i, long j) {
        android.util.Pair pair;
        android.util.Pair pair2 = (android.util.Pair) this.mProcessCrashCounts.get(str, i);
        if (pair2 == null || ((java.lang.Long) pair2.first).longValue() + com.android.server.am.ActivityManagerConstants.PROCESS_CRASH_COUNT_RESET_INTERVAL < j) {
            pair = new android.util.Pair(java.lang.Long.valueOf(j), 1);
        } else {
            pair = new android.util.Pair((java.lang.Long) pair2.first, java.lang.Integer.valueOf(((java.lang.Integer) pair2.second).intValue() + 1));
        }
        this.mProcessCrashCounts.put(str, i, pair);
    }

    @com.android.internal.annotations.GuardedBy({"mBadProcessLock"})
    private boolean isProcOverCrashLimitLBp(com.android.server.am.ProcessRecord processRecord, long j) {
        android.util.Pair pair = (android.util.Pair) this.mProcessCrashCounts.get(processRecord.processName, processRecord.uid);
        return !processRecord.isolated && pair != null && j < ((java.lang.Long) pair.first).longValue() + com.android.server.am.ActivityManagerConstants.PROCESS_CRASH_COUNT_RESET_INTERVAL && ((java.lang.Integer) pair.second).intValue() >= com.android.server.am.ActivityManagerConstants.PROCESS_CRASH_COUNT_LIMIT;
    }

    /* JADX WARN: Removed duplicated region for block: B:85:0x017d A[Catch: all -> 0x00d9, TryCatch #0 {all -> 0x00d9, blocks: (B:48:0x00c8, B:50:0x00cc, B:51:0x00dd, B:54:0x00ef, B:57:0x0109, B:59:0x0111, B:62:0x0120, B:64:0x0126, B:67:0x013a, B:71:0x018d, B:72:0x0192, B:81:0x014e, B:83:0x0152, B:85:0x017d), top: B:47:0x00c8, outer: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void handleShowAppErrorUi(android.os.Message message) {
        java.lang.Long l;
        com.android.server.am.ProcessRecord processRecord;
        boolean z;
        com.android.server.am.ProcessRecord processRecord2;
        com.android.server.am.AppErrorDialog.Data data = (com.android.server.am.AppErrorDialog.Data) message.obj;
        boolean z2 = android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "anr_show_background", 0, this.mService.mUserController.getCurrentUserId()) != 0;
        com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
        com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
        synchronized (activityManagerGlobalLock) {
            try {
                com.android.server.am.ProcessRecord processRecord3 = data.proc;
                com.android.server.am.AppErrorResult appErrorResult = data.result;
                if (processRecord3 == null) {
                    android.util.Slog.e(TAG, "handleShowAppErrorUi: proc is null");
                    com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                    return;
                }
                com.android.server.am.ProcessErrorStateRecord processErrorStateRecord = processRecord3.mErrorState;
                int i = processRecord3.userId;
                if (processErrorStateRecord.getDialogController().hasCrashDialogs()) {
                    android.util.Slog.e(TAG, "App already has crash dialog: " + processRecord3);
                    if (appErrorResult != null) {
                        appErrorResult.set(com.android.server.am.AppErrorDialog.ALREADY_SHOWING);
                    }
                    com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                    return;
                }
                boolean z3 = android.os.UserHandle.getAppId(processRecord3.uid) >= 10000 && processRecord3.getPid() != com.android.server.am.ActivityManagerService.MY_PID;
                for (int i2 : this.mService.mUserController.getCurrentProfileIds()) {
                    z3 &= i != i2;
                }
                if (z3 && !z2) {
                    android.util.Slog.w(TAG, "Skipping crash dialog of " + processRecord3 + ": background");
                    if (appErrorResult != null) {
                        appErrorResult.set(com.android.server.am.AppErrorDialog.BACKGROUND_USER);
                    }
                    com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                    return;
                }
                synchronized (this.mBadProcessLock) {
                    try {
                        if (processRecord3.isolated) {
                            l = null;
                        } else {
                            l = (java.lang.Long) this.mProcessCrashShowDialogTimes.get(processRecord3.processName, processRecord3.uid);
                        }
                        boolean z4 = android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), "show_first_crash_dialog", 0) != 0;
                        boolean z5 = android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "show_first_crash_dialog_dev_option", 0, this.mService.mUserController.getCurrentUserId()) != 0;
                        java.lang.String str = processRecord3.info.packageName;
                        boolean z6 = this.mAppsNotReportingCrashes != null && this.mAppsNotReportingCrashes.contains(processRecord3.info.packageName);
                        long uptimeMillis = android.os.SystemClock.uptimeMillis();
                        if (l != null) {
                            processRecord = processRecord3;
                            if (uptimeMillis < l.longValue() + com.android.server.am.ActivityManagerConstants.MIN_CRASH_INTERVAL) {
                                z = true;
                                if ((!this.mService.mAtmInternal.canShowErrorDialogs() || z2) && !z6 && !z && (z4 || z5 || data.repeating)) {
                                    android.util.Slog.i(TAG, "Showing crash dialog for package " + str + " u" + i);
                                    processErrorStateRecord.getDialogController().showCrashDialogs(data);
                                    processRecord2 = processRecord;
                                    if (!processRecord2.isolated) {
                                        this.mProcessCrashShowDialogTimes.put(processRecord2.processName, processRecord2.uid, java.lang.Long.valueOf(uptimeMillis));
                                    }
                                } else if (appErrorResult != null) {
                                    appErrorResult.set(com.android.server.am.AppErrorDialog.CANT_SHOW);
                                }
                            }
                        } else {
                            processRecord = processRecord3;
                        }
                        z = false;
                        if (!this.mService.mAtmInternal.canShowErrorDialogs()) {
                        }
                        android.util.Slog.i(TAG, "Showing crash dialog for package " + str + " u" + i);
                        processErrorStateRecord.getDialogController().showCrashDialogs(data);
                        processRecord2 = processRecord;
                        if (!processRecord2.isolated) {
                        }
                    } finally {
                    }
                }
                com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mBadProcessLock"})
    private void stopReportingCrashesLBp(com.android.server.am.ProcessRecord processRecord) {
        if (this.mAppsNotReportingCrashes == null) {
            this.mAppsNotReportingCrashes = new android.util.ArraySet<>();
        }
        this.mAppsNotReportingCrashes.add(processRecord.info.packageName);
    }

    void handleShowAnrUi(android.os.Message message) {
        java.util.List<android.content.pm.VersionedPackage> list;
        com.android.server.am.AppNotRespondingDialog.Data data = (com.android.server.am.AppNotRespondingDialog.Data) message.obj;
        com.android.server.am.ProcessRecord processRecord = data.proc;
        if (processRecord == null) {
            android.util.Slog.e(TAG, "handleShowAnrUi: proc is null");
            return;
        }
        com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
        com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
        synchronized (activityManagerGlobalLock) {
            try {
                com.android.server.am.ProcessErrorStateRecord processErrorStateRecord = processRecord.mErrorState;
                processErrorStateRecord.setAnrData(data);
                if (processRecord.isPersistent()) {
                    list = null;
                } else {
                    list = processRecord.getPackageListWithVersionCode();
                }
                if (processErrorStateRecord.getDialogController().hasAnrDialogs()) {
                    android.util.Slog.e(TAG, "App already has anr dialog: " + processRecord);
                    com.android.internal.logging.MetricsLogger.action(this.mContext, com.android.internal.util.FrameworkStatsLog.APP_BACKGROUND_RESTRICTIONS_INFO__EXEMPTION_REASON__REASON_MEDIA_SESSION_CALLBACK, -2);
                    com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                    return;
                }
                boolean z = false;
                boolean z2 = android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "anr_show_background", 0, this.mService.mUserController.getCurrentUserId()) != 0;
                if (this.mService.mAtmInternal.canShowErrorDialogs() || z2) {
                    android.app.AnrController anrController = processErrorStateRecord.getDialogController().getAnrController();
                    if (anrController == null) {
                        processErrorStateRecord.getDialogController().showAnrDialogs(data);
                    } else {
                        java.lang.String str = processRecord.info.packageName;
                        if (anrController.onAnrDelayCompleted(str, processRecord.info.uid)) {
                            android.util.Slog.d(TAG, "ANR delay completed. Showing ANR dialog for package: " + str);
                            processErrorStateRecord.getDialogController().showAnrDialogs(data);
                        } else {
                            android.util.Slog.d(TAG, "ANR delay completed. Cancelling ANR dialog for package: " + str);
                            processErrorStateRecord.setNotResponding(false);
                            processErrorStateRecord.setNotRespondingReport(null);
                            processErrorStateRecord.getDialogController().clearAnrDialogs();
                        }
                    }
                } else {
                    com.android.internal.logging.MetricsLogger.action(this.mContext, com.android.internal.util.FrameworkStatsLog.APP_BACKGROUND_RESTRICTIONS_INFO__EXEMPTION_REASON__REASON_MEDIA_SESSION_CALLBACK, -1);
                    z = true;
                }
                com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                if (z) {
                    this.mService.killAppAtUsersRequest(processRecord);
                }
                if (list != null) {
                    this.mPackageWatchdog.onPackageFailure(list, 4);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                throw th;
            }
        }
    }

    void handleDismissAnrDialogs(com.android.server.am.ProcessRecord processRecord) {
        com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
        com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
        synchronized (activityManagerGlobalLock) {
            try {
                com.android.server.am.ProcessErrorStateRecord processErrorStateRecord = processRecord.mErrorState;
                this.mService.mUiHandler.removeMessages(2, processErrorStateRecord.getAnrData());
                if (processErrorStateRecord.getDialogController().hasAnrDialogs()) {
                    processErrorStateRecord.setNotResponding(false);
                    processErrorStateRecord.setNotRespondingReport(null);
                    processErrorStateRecord.getDialogController().clearAnrDialogs();
                }
                processRecord.mErrorState.setAnrData(null);
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                throw th;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
    }

    static final class BadProcessInfo {
        final java.lang.String longMsg;
        final java.lang.String shortMsg;
        final java.lang.String stack;
        final long time;

        BadProcessInfo(long j, java.lang.String str, java.lang.String str2, java.lang.String str3) {
            this.time = j;
            this.shortMsg = str;
            this.longMsg = str2;
            this.stack = str3;
        }
    }
}
