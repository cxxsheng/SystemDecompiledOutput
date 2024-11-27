package com.android.server.am;

/* loaded from: classes.dex */
final class ServiceRecord extends android.os.Binder implements android.content.ComponentName.WithComponentName {
    static final int MAX_DELIVERY_COUNT = 3;
    static final int MAX_DONE_EXECUTING_COUNT = 6;
    private static final java.lang.String TAG = "ActivityManager";
    static final long USE_NEW_BFSL_LOGIC = 311208749;
    static final long USE_NEW_WIU_LOGIC_FOR_CAPABILITIES = 313677553;
    static final long USE_NEW_WIU_LOGIC_FOR_START = 311208629;
    boolean allowlistManager;
    final com.android.server.am.ActivityManagerService ams;
    com.android.server.am.ProcessRecord app;
    android.content.pm.ApplicationInfo appInfo;
    final android.util.ArrayMap<android.content.Intent.FilterComparison, com.android.server.am.IntentBindRecord> bindings;
    boolean callStart;
    private final android.util.ArrayMap<android.os.IBinder, java.util.ArrayList<com.android.server.am.ConnectionRecord>> connections;
    int crashCount;
    final long createRealTime;
    boolean createdFromFg;
    final java.lang.String definingPackageName;
    final int definingUid;
    boolean delayed;
    boolean delayedStop;
    final java.util.ArrayList<com.android.server.am.ServiceRecord.StartItem> deliveredStarts;
    long destroyTime;
    boolean destroying;
    boolean executeFg;
    int executeNesting;
    long executingStart;
    final boolean exported;
    long fgDisplayTime;
    boolean fgRequired;
    boolean fgWaiting;
    int foregroundId;
    android.app.Notification foregroundNoti;
    int foregroundServiceType;
    boolean inSharedIsolatedProcess;
    final android.content.ComponentName instanceName;
    final android.content.Intent.FilterComparison intent;
    boolean isForeground;
    boolean isNotAppComponentUsage;
    final boolean isSdkSandbox;
    com.android.server.am.ProcessRecord isolationHostProc;
    long lastActivity;
    private int lastStartId;
    long lastTopAlmostPerceptibleBindRequestUptimeMs;
    int mAllowStartForegroundAtEntering;
    int mAllowStart_byBindings;
    int mAllowStart_inBindService;
    int mAllowStart_noBinding;
    boolean mAllowUiJobScheduling;
    boolean mAllowWhileInUsePermissionInFgsAtEntering;
    int mAllowWiu_byBindings;
    int mAllowWiu_inBindService;
    int mAllowWiu_noBinding;
    private com.android.server.am.ProcessRecord mAppForAllowingBgActivityStartsByStart;

    @com.android.internal.annotations.GuardedBy({"ams"})
    private java.util.ArrayList<android.app.BackgroundStartPrivileges> mBackgroundStartPrivilegesByStart;
    private android.app.BackgroundStartPrivileges mBackgroundStartPrivilegesByStartMerged;
    private java.lang.Runnable mCleanUpAllowBgActivityStartsByStartCallback;
    long mEarliestRestartTime;

    @android.annotation.Nullable
    com.android.server.am.ForegroundServiceDelegation mFgsDelegation;
    long mFgsEnterTime;
    long mFgsExitTime;
    boolean mFgsHasNotificationPermission;
    private boolean mFgsIsTimeLimited;
    boolean mFgsNotificationDeferred;
    boolean mFgsNotificationShown;
    boolean mFgsNotificationWasDeferred;
    java.lang.String mInfoAllowStartForeground;
    com.android.server.am.ActivityManagerService.FgsTempAllowListItem mInfoTempFgsAllowListReason;
    private boolean mIsAllowedBgActivityStartsByBinding;
    boolean mIsFgsDelegate;
    boolean mKeepWarming;
    boolean mLoggedInfoAllowStartForeground;
    boolean mOomAdjBumpedInExec;
    int mProcessStateOnRequest;

    @android.annotation.Nullable
    android.content.pm.ApplicationInfo mRecentCallerApplicationInfo;
    java.lang.String mRecentCallingPackage;
    int mRecentCallingUid;
    long mRestartSchedulingTime;
    private com.android.server.am.ServiceRecord.ShortFgsInfo mShortFgsInfo;
    int mStartForegroundCount;
    final android.content.ComponentName name;
    long nextRestartTime;
    final java.lang.String packageName;
    int pendingConnectionGroup;
    int pendingConnectionImportance;
    final java.util.ArrayList<com.android.server.am.ServiceRecord.StartItem> pendingStarts;
    final java.lang.String permission;
    final java.lang.String processName;
    int restartCount;
    long restartDelay;
    long restartTime;
    com.android.internal.app.procstats.ServiceState restartTracker;
    final java.lang.Runnable restarter;
    final java.lang.String sdkSandboxClientAppPackage;
    final int sdkSandboxClientAppUid;
    final android.content.pm.ServiceInfo serviceInfo;
    final java.lang.String shortInstanceName;
    int startCommandResult;
    boolean startRequested;
    long startingBgTimeout;
    boolean stopIfKilled;
    java.lang.String stringName;
    int totalRestartCount;
    com.android.internal.app.procstats.ServiceState tracker;
    final int userId;

    private boolean useNewWiuLogic_forStart() {
        com.android.server.am.Flags.newFgsRestrictionLogic();
        return false;
    }

    private boolean useNewWiuLogic_forCapabilities() {
        com.android.server.am.Flags.newFgsRestrictionLogic();
        return false;
    }

    private boolean useNewBfslLogic() {
        com.android.server.am.Flags.newFgsRestrictionLogic();
        return false;
    }

    private int getFgsAllowWiu_legacy() {
        return reasonOr(this.mAllowWiu_noBinding, this.mAllowWiu_inBindService);
    }

    private int getFgsAllowWiu_new() {
        return reasonOr(this.mAllowWiu_noBinding, this.mAllowWiu_byBindings);
    }

    int getFgsAllowWiu_forStart() {
        if (useNewWiuLogic_forStart()) {
            return getFgsAllowWiu_new();
        }
        return getFgsAllowWiu_legacy();
    }

    int getFgsAllowWiu_forCapabilities() {
        if (useNewWiuLogic_forCapabilities()) {
            return getFgsAllowWiu_new();
        }
        return getFgsAllowWiu_forStart();
    }

    boolean isFgsAllowedWiu_forStart() {
        return getFgsAllowWiu_forStart() != -1;
    }

    boolean isFgsAllowedWiu_forCapabilities() {
        return getFgsAllowWiu_forCapabilities() != -1;
    }

    private int getFgsAllowStart_legacy() {
        return reasonOr(this.mAllowStart_noBinding, this.mAllowStart_inBindService, this.mAllowStart_byBindings);
    }

    private int getFgsAllowStart_new() {
        return reasonOr(this.mAllowStart_noBinding, this.mAllowStart_byBindings);
    }

    int getFgsAllowStart() {
        if (useNewBfslLogic()) {
            return getFgsAllowStart_new();
        }
        return getFgsAllowStart_legacy();
    }

    boolean isFgsAllowedStart() {
        return getFgsAllowStart() != -1;
    }

    void clearFgsAllowWiu() {
        this.mAllowWiu_noBinding = -1;
        this.mAllowWiu_inBindService = -1;
        this.mAllowWiu_byBindings = -1;
    }

    void clearFgsAllowStart() {
        this.mAllowStart_noBinding = -1;
        this.mAllowStart_inBindService = -1;
        this.mAllowStart_byBindings = -1;
    }

    static int reasonOr(int i, int i2) {
        return i != -1 ? i : i2;
    }

    static int reasonOr(int i, int i2, int i3) {
        return i != -1 ? i : reasonOr(i2, i3);
    }

    boolean allowedChanged(int i, int i2) {
        return (i == -1) != (i2 == -1);
    }

    private java.lang.String getFgsInfoForWtf() {
        return " cmp: " + getComponentName().toShortString() + " sdk: " + this.appInfo.targetSdkVersion;
    }

    void maybeLogFgsLogicChange() {
        int fgsAllowWiu_legacy = getFgsAllowWiu_legacy();
        int fgsAllowWiu_new = getFgsAllowWiu_new();
        int fgsAllowStart_legacy = getFgsAllowStart_legacy();
        int fgsAllowStart_new = getFgsAllowStart_new();
        boolean allowedChanged = allowedChanged(fgsAllowWiu_legacy, fgsAllowWiu_new);
        boolean allowedChanged2 = allowedChanged(fgsAllowStart_legacy, fgsAllowStart_new);
        if (!allowedChanged && !allowedChanged2) {
            return;
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("FGS logic changed:");
        sb.append(allowedChanged ? " [WIU changed]" : "");
        sb.append(allowedChanged2 ? " [BFSL changed]" : "");
        sb.append(" Orig WIU:");
        sb.append(android.os.PowerExemptionManager.reasonCodeToString(fgsAllowWiu_legacy));
        sb.append(" New WIU:");
        sb.append(android.os.PowerExemptionManager.reasonCodeToString(fgsAllowWiu_new));
        sb.append(" Orig BFSL:");
        sb.append(android.os.PowerExemptionManager.reasonCodeToString(fgsAllowStart_legacy));
        sb.append(" New BFSL:");
        sb.append(android.os.PowerExemptionManager.reasonCodeToString(fgsAllowStart_new));
        sb.append(getFgsInfoForWtf());
        android.util.Slog.wtf(TAG, sb.toString());
    }

    static class StartItem {
        final int callingId;
        long deliveredTime;
        int deliveryCount;
        int doneExecutingCount;
        final int id;
        final android.content.Intent intent;

        @android.annotation.Nullable
        final java.lang.String mCallingPackageName;
        final java.lang.String mCallingProcessName;
        final int mCallingProcessState;
        final com.android.server.uri.NeededUriGrants neededGrants;
        final com.android.server.am.ServiceRecord sr;
        java.lang.String stringName;
        final boolean taskRemoved;
        com.android.server.uri.UriPermissionOwner uriPermissions;

        StartItem(com.android.server.am.ServiceRecord serviceRecord, boolean z, int i, android.content.Intent intent, com.android.server.uri.NeededUriGrants neededUriGrants, int i2, java.lang.String str, @android.annotation.Nullable java.lang.String str2, int i3) {
            this.sr = serviceRecord;
            this.taskRemoved = z;
            this.id = i;
            this.intent = intent;
            this.neededGrants = neededUriGrants;
            this.callingId = i2;
            this.mCallingProcessName = str;
            this.mCallingPackageName = str2;
            this.mCallingProcessState = i3;
        }

        com.android.server.uri.UriPermissionOwner getUriPermissionsLocked() {
            if (this.uriPermissions == null) {
                this.uriPermissions = new com.android.server.uri.UriPermissionOwner(this.sr.ams.mUgmInternal, this);
            }
            return this.uriPermissions;
        }

        void removeUriPermissionsLocked() {
            if (this.uriPermissions != null) {
                this.uriPermissions.removeUriPermissions();
                this.uriPermissions = null;
            }
        }

        public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j, long j2) {
            long start = protoOutputStream.start(j);
            protoOutputStream.write(1120986464257L, this.id);
            android.util.proto.ProtoUtils.toDuration(protoOutputStream, 1146756268034L, this.deliveredTime, j2);
            protoOutputStream.write(1120986464259L, this.deliveryCount);
            protoOutputStream.write(1120986464260L, this.doneExecutingCount);
            if (this.intent != null) {
                this.intent.dumpDebug(protoOutputStream, 1146756268037L, true, true, true, false);
            }
            if (this.neededGrants != null) {
                this.neededGrants.dumpDebug(protoOutputStream, 1146756268038L);
            }
            if (this.uriPermissions != null) {
                this.uriPermissions.dumpDebug(protoOutputStream, 1146756268039L);
            }
            protoOutputStream.end(start);
        }

        public java.lang.String toString() {
            if (this.stringName != null) {
                return this.stringName;
            }
            java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
            sb.append("ServiceRecord{");
            sb.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this.sr)));
            sb.append(' ');
            sb.append(this.sr.shortInstanceName);
            sb.append(" StartItem ");
            sb.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
            sb.append(" id=");
            sb.append(this.id);
            sb.append('}');
            java.lang.String sb2 = sb.toString();
            this.stringName = sb2;
            return sb2;
        }
    }

    class ShortFgsInfo {
        private int mStartForegroundCount;
        private int mStartId;
        private final long mStartTime;

        ShortFgsInfo(long j) {
            this.mStartTime = j;
            update();
        }

        public void update() {
            this.mStartForegroundCount = com.android.server.am.ServiceRecord.this.mStartForegroundCount;
            this.mStartId = com.android.server.am.ServiceRecord.this.getLastStartId();
        }

        long getStartTime() {
            return this.mStartTime;
        }

        int getStartForegroundCount() {
            return this.mStartForegroundCount;
        }

        int getStartId() {
            return this.mStartId;
        }

        boolean isCurrent() {
            return this.mStartForegroundCount == com.android.server.am.ServiceRecord.this.mStartForegroundCount;
        }

        long getTimeoutTime() {
            return this.mStartTime + com.android.server.am.ServiceRecord.this.ams.mConstants.mShortFgsTimeoutDuration;
        }

        long getProcStateDemoteTime() {
            return this.mStartTime + com.android.server.am.ServiceRecord.this.ams.mConstants.mShortFgsTimeoutDuration + com.android.server.am.ServiceRecord.this.ams.mConstants.mShortFgsProcStateExtraWaitDuration;
        }

        long getAnrTime() {
            return this.mStartTime + com.android.server.am.ServiceRecord.this.ams.mConstants.mShortFgsTimeoutDuration + com.android.server.am.ServiceRecord.this.ams.mConstants.mShortFgsAnrExtraWaitDuration;
        }

        java.lang.String getDescription() {
            return "sfc=" + this.mStartForegroundCount + " sid=" + this.mStartId + " stime=" + this.mStartTime + " tt=" + getTimeoutTime() + " dt=" + getProcStateDemoteTime() + " at=" + getAnrTime();
        }
    }

    void dumpStartList(java.io.PrintWriter printWriter, java.lang.String str, java.util.List<com.android.server.am.ServiceRecord.StartItem> list, long j) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            com.android.server.am.ServiceRecord.StartItem startItem = list.get(i);
            printWriter.print(str);
            printWriter.print("#");
            printWriter.print(i);
            printWriter.print(" id=");
            printWriter.print(startItem.id);
            if (j != 0) {
                printWriter.print(" dur=");
                android.util.TimeUtils.formatDuration(startItem.deliveredTime, j, printWriter);
            }
            if (startItem.deliveryCount != 0) {
                printWriter.print(" dc=");
                printWriter.print(startItem.deliveryCount);
            }
            if (startItem.doneExecutingCount != 0) {
                printWriter.print(" dxc=");
                printWriter.print(startItem.doneExecutingCount);
            }
            printWriter.println("");
            printWriter.print(str);
            printWriter.print("  intent=");
            if (startItem.intent != null) {
                printWriter.println(startItem.intent.toString());
            } else {
                printWriter.println("null");
            }
            if (startItem.neededGrants != null) {
                printWriter.print(str);
                printWriter.print("  neededGrants=");
                printWriter.println(startItem.neededGrants);
            }
            if (startItem.uriPermissions != null) {
                startItem.uriPermissions.dump(printWriter, str);
            }
        }
    }

    void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long j2;
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1138166333441L, this.shortInstanceName);
        protoOutputStream.write(1133871366146L, this.app != null);
        if (this.app != null) {
            protoOutputStream.write(1120986464259L, this.app.getPid());
        }
        if (this.intent != null) {
            this.intent.getIntent().dumpDebug(protoOutputStream, 1146756268036L, false, true, false, false);
        }
        protoOutputStream.write(1138166333445L, this.packageName);
        protoOutputStream.write(1138166333446L, this.processName);
        protoOutputStream.write(1138166333447L, this.permission);
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        if (this.appInfo != null) {
            long start2 = protoOutputStream.start(1146756268040L);
            protoOutputStream.write(1138166333441L, this.appInfo.sourceDir);
            if (!java.util.Objects.equals(this.appInfo.sourceDir, this.appInfo.publicSourceDir)) {
                protoOutputStream.write(1138166333442L, this.appInfo.publicSourceDir);
            }
            protoOutputStream.write(1138166333443L, this.appInfo.dataDir);
            protoOutputStream.write(1120986464260L, this.appInfo.targetSdkVersion);
            protoOutputStream.end(start2);
        }
        if (this.app != null) {
            this.app.dumpDebug(protoOutputStream, 1146756268041L);
        }
        if (this.isolationHostProc != null) {
            this.isolationHostProc.dumpDebug(protoOutputStream, 1146756268042L);
        }
        protoOutputStream.write(1133871366155L, this.allowlistManager);
        protoOutputStream.write(1133871366156L, this.delayed);
        if (this.isForeground || this.foregroundId != 0) {
            long start3 = protoOutputStream.start(1146756268045L);
            protoOutputStream.write(1120986464257L, this.foregroundId);
            this.foregroundNoti.dumpDebug(protoOutputStream, 1146756268034L);
            protoOutputStream.write(1120986464259L, this.foregroundServiceType);
            protoOutputStream.end(start3);
        }
        android.util.proto.ProtoUtils.toDuration(protoOutputStream, 1146756268046L, this.createRealTime, elapsedRealtime);
        android.util.proto.ProtoUtils.toDuration(protoOutputStream, 1146756268047L, this.startingBgTimeout, uptimeMillis);
        android.util.proto.ProtoUtils.toDuration(protoOutputStream, 1146756268048L, this.lastActivity, uptimeMillis);
        android.util.proto.ProtoUtils.toDuration(protoOutputStream, 1146756268049L, this.restartTime, uptimeMillis);
        protoOutputStream.write(1133871366162L, this.createdFromFg);
        protoOutputStream.write(1133871366171L, isFgsAllowedWiu_forCapabilities());
        if (this.startRequested || this.delayedStop || this.lastStartId != 0) {
            long start4 = protoOutputStream.start(1146756268051L);
            protoOutputStream.write(1133871366145L, this.startRequested);
            j2 = 1133871366146L;
            protoOutputStream.write(1133871366146L, this.delayedStop);
            protoOutputStream.write(1133871366147L, this.stopIfKilled);
            protoOutputStream.write(1120986464261L, this.lastStartId);
            protoOutputStream.write(1120986464262L, this.startCommandResult);
            protoOutputStream.end(start4);
        } else {
            j2 = 1133871366146L;
        }
        if (this.executeNesting != 0) {
            long start5 = protoOutputStream.start(1146756268052L);
            protoOutputStream.write(1120986464257L, this.executeNesting);
            protoOutputStream.write(j2, this.executeFg);
            android.util.proto.ProtoUtils.toDuration(protoOutputStream, 1146756268035L, this.executingStart, uptimeMillis);
            protoOutputStream.end(start5);
        }
        if (this.destroying || this.destroyTime != 0) {
            android.util.proto.ProtoUtils.toDuration(protoOutputStream, 1146756268053L, this.destroyTime, uptimeMillis);
        }
        if (this.crashCount != 0 || this.restartCount != 0 || this.nextRestartTime - this.mRestartSchedulingTime != 0 || this.nextRestartTime != 0) {
            long start6 = protoOutputStream.start(1146756268054L);
            protoOutputStream.write(1120986464257L, this.restartCount);
            android.util.proto.ProtoUtils.toDuration(protoOutputStream, 1146756268034L, this.nextRestartTime - this.mRestartSchedulingTime, uptimeMillis);
            android.util.proto.ProtoUtils.toDuration(protoOutputStream, 1146756268035L, this.nextRestartTime, uptimeMillis);
            protoOutputStream.write(1120986464260L, this.crashCount);
            protoOutputStream.end(start6);
        }
        if (this.deliveredStarts.size() > 0) {
            int size = this.deliveredStarts.size();
            for (int i = 0; i < size; i++) {
                this.deliveredStarts.get(i).dumpDebug(protoOutputStream, 2246267895831L, uptimeMillis);
            }
        }
        if (this.pendingStarts.size() > 0) {
            int size2 = this.pendingStarts.size();
            for (int i2 = 0; i2 < size2; i2++) {
                this.pendingStarts.get(i2).dumpDebug(protoOutputStream, 2246267895832L, uptimeMillis);
            }
        }
        if (this.bindings.size() > 0) {
            int size3 = this.bindings.size();
            for (int i3 = 0; i3 < size3; i3++) {
                this.bindings.valueAt(i3).dumpDebug(protoOutputStream, 2246267895833L);
            }
        }
        if (this.connections.size() > 0) {
            int size4 = this.connections.size();
            for (int i4 = 0; i4 < size4; i4++) {
                java.util.ArrayList<com.android.server.am.ConnectionRecord> valueAt = this.connections.valueAt(i4);
                for (int i5 = 0; i5 < valueAt.size(); i5++) {
                    valueAt.get(i5).dumpDebug(protoOutputStream, 2246267895834L);
                }
            }
        }
        if (this.mShortFgsInfo != null && this.mShortFgsInfo.isCurrent()) {
            long start7 = protoOutputStream.start(1146756268060L);
            protoOutputStream.write(1112396529665L, this.mShortFgsInfo.getStartTime());
            protoOutputStream.write(1120986464259L, this.mShortFgsInfo.getStartId());
            protoOutputStream.write(1112396529668L, this.mShortFgsInfo.getTimeoutTime());
            protoOutputStream.write(1112396529669L, this.mShortFgsInfo.getProcStateDemoteTime());
            protoOutputStream.write(1112396529670L, this.mShortFgsInfo.getAnrTime());
            protoOutputStream.end(start7);
        }
        protoOutputStream.end(start);
    }

    void dumpReasonCode(java.io.PrintWriter printWriter, java.lang.String str, java.lang.String str2, int i) {
        printWriter.print(str);
        printWriter.print(str2);
        printWriter.print("=");
        printWriter.println(android.os.PowerExemptionManager.reasonCodeToString(i));
    }

    void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.print(str);
        printWriter.print("intent={");
        printWriter.print(this.intent.getIntent().toShortString(false, true, false, false));
        printWriter.println('}');
        printWriter.print(str);
        printWriter.print("packageName=");
        printWriter.println(this.packageName);
        printWriter.print(str);
        printWriter.print("processName=");
        printWriter.println(this.processName);
        printWriter.print(str);
        printWriter.print("targetSdkVersion=");
        printWriter.println(this.appInfo.targetSdkVersion);
        if (this.permission != null) {
            printWriter.print(str);
            printWriter.print("permission=");
            printWriter.println(this.permission);
        }
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        if (this.appInfo != null) {
            printWriter.print(str);
            printWriter.print("baseDir=");
            printWriter.println(this.appInfo.sourceDir);
            if (!java.util.Objects.equals(this.appInfo.sourceDir, this.appInfo.publicSourceDir)) {
                printWriter.print(str);
                printWriter.print("resDir=");
                printWriter.println(this.appInfo.publicSourceDir);
            }
            printWriter.print(str);
            printWriter.print("dataDir=");
            printWriter.println(this.appInfo.dataDir);
        }
        printWriter.print(str);
        printWriter.print("app=");
        printWriter.println(this.app);
        if (this.isolationHostProc != null) {
            printWriter.print(str);
            printWriter.print("isolationHostProc=");
            printWriter.println(this.isolationHostProc);
        }
        if (this.allowlistManager) {
            printWriter.print(str);
            printWriter.print("allowlistManager=");
            printWriter.println(this.allowlistManager);
        }
        if (this.mIsAllowedBgActivityStartsByBinding) {
            printWriter.print(str);
            printWriter.print("mIsAllowedBgActivityStartsByBinding=");
            printWriter.println(this.mIsAllowedBgActivityStartsByBinding);
        }
        if (this.mBackgroundStartPrivilegesByStartMerged.allowsAny()) {
            printWriter.print(str);
            printWriter.print("mIsAllowedBgActivityStartsByStart=");
            printWriter.println(this.mBackgroundStartPrivilegesByStartMerged);
        }
        printWriter.print(str);
        printWriter.print("useNewWiuLogic_forCapabilities()=");
        printWriter.println(useNewWiuLogic_forCapabilities());
        printWriter.print(str);
        printWriter.print("useNewWiuLogic_forStart()=");
        printWriter.println(useNewWiuLogic_forStart());
        printWriter.print(str);
        printWriter.print("useNewBfslLogic()=");
        printWriter.println(useNewBfslLogic());
        dumpReasonCode(printWriter, str, "mAllowWiu_noBinding", this.mAllowWiu_noBinding);
        dumpReasonCode(printWriter, str, "mAllowWiu_inBindService", this.mAllowWiu_inBindService);
        dumpReasonCode(printWriter, str, "mAllowWiu_byBindings", this.mAllowWiu_byBindings);
        dumpReasonCode(printWriter, str, "getFgsAllowWiu_legacy", getFgsAllowWiu_legacy());
        dumpReasonCode(printWriter, str, "getFgsAllowWiu_new", getFgsAllowWiu_new());
        dumpReasonCode(printWriter, str, "getFgsAllowWiu_forStart", getFgsAllowWiu_forStart());
        dumpReasonCode(printWriter, str, "getFgsAllowWiu_forCapabilities", getFgsAllowWiu_forCapabilities());
        printWriter.print(str);
        printWriter.print("allowUiJobScheduling=");
        printWriter.println(this.mAllowUiJobScheduling);
        printWriter.print(str);
        printWriter.print("recentCallingPackage=");
        printWriter.println(this.mRecentCallingPackage);
        printWriter.print(str);
        printWriter.print("recentCallingUid=");
        printWriter.println(this.mRecentCallingUid);
        dumpReasonCode(printWriter, str, "mAllowStart_noBinding", this.mAllowStart_noBinding);
        dumpReasonCode(printWriter, str, "mAllowStart_inBindService", this.mAllowStart_inBindService);
        dumpReasonCode(printWriter, str, "mAllowStart_byBindings", this.mAllowStart_byBindings);
        dumpReasonCode(printWriter, str, "getFgsAllowStart_legacy", getFgsAllowStart_legacy());
        dumpReasonCode(printWriter, str, "getFgsAllowStart_new", getFgsAllowStart_new());
        dumpReasonCode(printWriter, str, "getFgsAllowStart", getFgsAllowStart());
        printWriter.print(str);
        printWriter.print("startForegroundCount=");
        printWriter.println(this.mStartForegroundCount);
        printWriter.print(str);
        printWriter.print("infoAllowStartForeground=");
        printWriter.println(this.mInfoAllowStartForeground);
        if (this.delayed) {
            printWriter.print(str);
            printWriter.print("delayed=");
            printWriter.println(this.delayed);
        }
        if (this.isForeground || this.foregroundId != 0) {
            printWriter.print(str);
            printWriter.print("isForeground=");
            printWriter.print(this.isForeground);
            printWriter.print(" foregroundId=");
            printWriter.print(this.foregroundId);
            printWriter.printf(" types=%08X", java.lang.Integer.valueOf(this.foregroundServiceType));
            printWriter.print(" fgsHasTimeLimitedType=");
            printWriter.print(this.mFgsIsTimeLimited);
            printWriter.print(" foregroundNoti=");
            printWriter.println(this.foregroundNoti);
            if (isShortFgs() && this.mShortFgsInfo != null) {
                printWriter.print(str);
                printWriter.print("isShortFgs=true");
                printWriter.print(" startId=");
                printWriter.print(this.mShortFgsInfo.getStartId());
                printWriter.print(" startForegroundCount=");
                printWriter.print(this.mShortFgsInfo.getStartForegroundCount());
                printWriter.print(" startTime=");
                android.util.TimeUtils.formatDuration(this.mShortFgsInfo.getStartTime(), uptimeMillis, printWriter);
                printWriter.print(" timeout=");
                android.util.TimeUtils.formatDuration(this.mShortFgsInfo.getTimeoutTime(), uptimeMillis, printWriter);
                printWriter.print(" demoteTime=");
                android.util.TimeUtils.formatDuration(this.mShortFgsInfo.getProcStateDemoteTime(), uptimeMillis, printWriter);
                printWriter.print(" anrTime=");
                android.util.TimeUtils.formatDuration(this.mShortFgsInfo.getAnrTime(), uptimeMillis, printWriter);
                printWriter.println();
            }
        }
        if (this.mIsFgsDelegate) {
            printWriter.print(str);
            printWriter.print("isFgsDelegate=");
            printWriter.println(this.mIsFgsDelegate);
        }
        printWriter.print(str);
        printWriter.print("createTime=");
        android.util.TimeUtils.formatDuration(this.createRealTime, elapsedRealtime, printWriter);
        printWriter.print(" startingBgTimeout=");
        android.util.TimeUtils.formatDuration(this.startingBgTimeout, uptimeMillis, printWriter);
        printWriter.println();
        printWriter.print(str);
        printWriter.print("lastActivity=");
        android.util.TimeUtils.formatDuration(this.lastActivity, uptimeMillis, printWriter);
        printWriter.print(" restartTime=");
        android.util.TimeUtils.formatDuration(this.restartTime, uptimeMillis, printWriter);
        printWriter.print(" createdFromFg=");
        printWriter.println(this.createdFromFg);
        if (this.pendingConnectionGroup != 0) {
            printWriter.print(str);
            printWriter.print(" pendingConnectionGroup=");
            printWriter.print(this.pendingConnectionGroup);
            printWriter.print(" Importance=");
            printWriter.println(this.pendingConnectionImportance);
        }
        if (this.startRequested || this.delayedStop || this.lastStartId != 0) {
            printWriter.print(str);
            printWriter.print("startRequested=");
            printWriter.print(this.startRequested);
            printWriter.print(" delayedStop=");
            printWriter.print(this.delayedStop);
            printWriter.print(" stopIfKilled=");
            printWriter.print(this.stopIfKilled);
            printWriter.print(" callStart=");
            printWriter.print(this.callStart);
            printWriter.print(" lastStartId=");
            printWriter.println(this.lastStartId);
            printWriter.print(" startCommandResult=");
            printWriter.println(this.startCommandResult);
        }
        if (this.executeNesting != 0) {
            printWriter.print(str);
            printWriter.print("executeNesting=");
            printWriter.print(this.executeNesting);
            printWriter.print(" executeFg=");
            printWriter.print(this.executeFg);
            printWriter.print(" executingStart=");
            android.util.TimeUtils.formatDuration(this.executingStart, uptimeMillis, printWriter);
            printWriter.println();
        }
        if (this.destroying || this.destroyTime != 0) {
            printWriter.print(str);
            printWriter.print("destroying=");
            printWriter.print(this.destroying);
            printWriter.print(" destroyTime=");
            android.util.TimeUtils.formatDuration(this.destroyTime, uptimeMillis, printWriter);
            printWriter.println();
        }
        if (this.crashCount != 0 || this.restartCount != 0 || this.nextRestartTime - this.mRestartSchedulingTime != 0 || this.nextRestartTime != 0) {
            printWriter.print(str);
            printWriter.print("restartCount=");
            printWriter.print(this.restartCount);
            printWriter.print(" restartDelay=");
            android.util.TimeUtils.formatDuration(this.nextRestartTime - this.mRestartSchedulingTime, uptimeMillis, printWriter);
            printWriter.print(" nextRestartTime=");
            android.util.TimeUtils.formatDuration(this.nextRestartTime, uptimeMillis, printWriter);
            printWriter.print(" crashCount=");
            printWriter.println(this.crashCount);
        }
        if (this.deliveredStarts.size() > 0) {
            printWriter.print(str);
            printWriter.println("Delivered Starts:");
            dumpStartList(printWriter, str, this.deliveredStarts, uptimeMillis);
        }
        if (this.pendingStarts.size() > 0) {
            printWriter.print(str);
            printWriter.println("Pending Starts:");
            dumpStartList(printWriter, str, this.pendingStarts, 0L);
        }
        if (this.bindings.size() > 0) {
            printWriter.print(str);
            printWriter.println("Bindings:");
            for (int i = 0; i < this.bindings.size(); i++) {
                com.android.server.am.IntentBindRecord valueAt = this.bindings.valueAt(i);
                printWriter.print(str);
                printWriter.print("* IntentBindRecord{");
                printWriter.print(java.lang.Integer.toHexString(java.lang.System.identityHashCode(valueAt)));
                if ((valueAt.collectFlags() & 1) != 0) {
                    printWriter.append(" CREATE");
                }
                printWriter.println("}:");
                valueAt.dumpInService(printWriter, str + "  ");
            }
        }
        if (this.connections.size() > 0) {
            printWriter.print(str);
            printWriter.println("All Connections:");
            for (int i2 = 0; i2 < this.connections.size(); i2++) {
                java.util.ArrayList<com.android.server.am.ConnectionRecord> valueAt2 = this.connections.valueAt(i2);
                for (int i3 = 0; i3 < valueAt2.size(); i3++) {
                    printWriter.print(str);
                    printWriter.print("  ");
                    printWriter.println(valueAt2.get(i3));
                }
            }
        }
    }

    private ServiceRecord(com.android.server.am.ActivityManagerService activityManagerService) {
        this.bindings = new android.util.ArrayMap<>();
        this.connections = new android.util.ArrayMap<>();
        this.mBackgroundStartPrivilegesByStart = new java.util.ArrayList<>();
        this.mBackgroundStartPrivilegesByStartMerged = android.app.BackgroundStartPrivileges.NONE;
        this.mAllowWiu_noBinding = -1;
        this.mFgsEnterTime = 0L;
        this.mFgsExitTime = 0L;
        this.mAllowStart_noBinding = -1;
        this.mAllowStartForegroundAtEntering = -1;
        this.mAllowWiu_inBindService = -1;
        this.mAllowWiu_byBindings = -1;
        this.mAllowStart_inBindService = -1;
        this.mAllowStart_byBindings = -1;
        this.deliveredStarts = new java.util.ArrayList<>();
        this.pendingStarts = new java.util.ArrayList<>();
        this.ams = activityManagerService;
        this.name = null;
        this.instanceName = null;
        this.shortInstanceName = null;
        this.definingPackageName = null;
        this.definingUid = 0;
        this.intent = null;
        this.serviceInfo = null;
        this.userId = 0;
        this.packageName = null;
        this.processName = null;
        this.permission = null;
        this.exported = false;
        this.restarter = null;
        this.createRealTime = 0L;
        this.isSdkSandbox = false;
        this.sdkSandboxClientAppUid = 0;
        this.sdkSandboxClientAppPackage = null;
        this.inSharedIsolatedProcess = false;
    }

    public static com.android.server.am.ServiceRecord newEmptyInstanceForTest(com.android.server.am.ActivityManagerService activityManagerService) {
        return new com.android.server.am.ServiceRecord(activityManagerService);
    }

    ServiceRecord(com.android.server.am.ActivityManagerService activityManagerService, android.content.ComponentName componentName, android.content.ComponentName componentName2, java.lang.String str, int i, android.content.Intent.FilterComparison filterComparison, android.content.pm.ServiceInfo serviceInfo, boolean z, java.lang.Runnable runnable) {
        this(activityManagerService, componentName, componentName2, str, i, filterComparison, serviceInfo, z, runnable, serviceInfo.processName, -1, null, false);
    }

    ServiceRecord(com.android.server.am.ActivityManagerService activityManagerService, android.content.ComponentName componentName, android.content.ComponentName componentName2, java.lang.String str, int i, android.content.Intent.FilterComparison filterComparison, android.content.pm.ServiceInfo serviceInfo, boolean z, java.lang.Runnable runnable, java.lang.String str2, int i2, java.lang.String str3, boolean z2) {
        this.bindings = new android.util.ArrayMap<>();
        this.connections = new android.util.ArrayMap<>();
        this.mBackgroundStartPrivilegesByStart = new java.util.ArrayList<>();
        this.mBackgroundStartPrivilegesByStartMerged = android.app.BackgroundStartPrivileges.NONE;
        this.mAllowWiu_noBinding = -1;
        this.mFgsEnterTime = 0L;
        this.mFgsExitTime = 0L;
        this.mAllowStart_noBinding = -1;
        this.mAllowStartForegroundAtEntering = -1;
        this.mAllowWiu_inBindService = -1;
        this.mAllowWiu_byBindings = -1;
        this.mAllowStart_inBindService = -1;
        this.mAllowStart_byBindings = -1;
        this.deliveredStarts = new java.util.ArrayList<>();
        this.pendingStarts = new java.util.ArrayList<>();
        this.ams = activityManagerService;
        this.name = componentName;
        this.instanceName = componentName2;
        this.shortInstanceName = componentName2.flattenToShortString();
        this.definingPackageName = str;
        this.definingUid = i;
        this.intent = filterComparison;
        this.serviceInfo = serviceInfo;
        this.appInfo = serviceInfo.applicationInfo;
        this.packageName = serviceInfo.applicationInfo.packageName;
        this.isSdkSandbox = i2 != -1;
        this.sdkSandboxClientAppUid = i2;
        this.sdkSandboxClientAppPackage = str3;
        this.inSharedIsolatedProcess = z2;
        this.processName = str2;
        this.permission = serviceInfo.permission;
        this.exported = serviceInfo.exported;
        this.restarter = runnable;
        this.createRealTime = android.os.SystemClock.elapsedRealtime();
        this.lastActivity = android.os.SystemClock.uptimeMillis();
        this.userId = android.os.UserHandle.getUserId(this.appInfo.uid);
        this.createdFromFg = z;
        updateKeepWarmLocked();
        updateFgsHasNotificationPermission();
    }

    public com.android.internal.app.procstats.ServiceState getTracker() {
        if (this.tracker != null) {
            return this.tracker;
        }
        if ((this.serviceInfo.applicationInfo.flags & 8) == 0) {
            this.tracker = this.ams.mProcessStats.getServiceState(this.serviceInfo.packageName, this.serviceInfo.applicationInfo.uid, this.serviceInfo.applicationInfo.longVersionCode, this.serviceInfo.processName, this.serviceInfo.name);
            if (this.tracker != null) {
                this.tracker.applyNewOwner(this);
            }
        }
        return this.tracker;
    }

    public void forceClearTracker() {
        if (this.tracker != null) {
            this.tracker.clearCurrentOwner(this, true);
            this.tracker = null;
        }
    }

    public void makeRestarting(int i, long j) {
        if (this.restartTracker == null) {
            if ((this.serviceInfo.applicationInfo.flags & 8) == 0) {
                this.restartTracker = this.ams.mProcessStats.getServiceState(this.serviceInfo.packageName, this.serviceInfo.applicationInfo.uid, this.serviceInfo.applicationInfo.longVersionCode, this.serviceInfo.processName, this.serviceInfo.name);
            }
            if (this.restartTracker == null) {
                return;
            }
        }
        this.restartTracker.setRestarting(true, i, j);
    }

    public void setProcess(com.android.server.am.ProcessRecord processRecord, android.app.IApplicationThread iApplicationThread, int i, com.android.server.am.UidRecord uidRecord) {
        if (processRecord != null) {
            if (this.mAppForAllowingBgActivityStartsByStart != null && this.mAppForAllowingBgActivityStartsByStart != processRecord) {
                this.mAppForAllowingBgActivityStartsByStart.removeBackgroundStartPrivileges(this);
                this.ams.mHandler.removeCallbacks(this.mCleanUpAllowBgActivityStartsByStartCallback);
            }
            this.mAppForAllowingBgActivityStartsByStart = this.mBackgroundStartPrivilegesByStartMerged.allowsAny() ? processRecord : null;
            android.app.BackgroundStartPrivileges backgroundStartPrivilegesWithExclusiveToken = getBackgroundStartPrivilegesWithExclusiveToken();
            if (backgroundStartPrivilegesWithExclusiveToken.allowsAny()) {
                processRecord.addOrUpdateBackgroundStartPrivileges(this, backgroundStartPrivilegesWithExclusiveToken);
            } else {
                processRecord.removeBackgroundStartPrivileges(this);
            }
        }
        if (this.app != null && this.app != processRecord) {
            if (this.mBackgroundStartPrivilegesByStartMerged.allowsNothing()) {
                this.app.removeBackgroundStartPrivileges(this);
            }
            this.app.mServices.updateBoundClientUids();
            this.app.mServices.updateHostingComonentTypeForBindingsLocked();
        }
        this.app = processRecord;
        updateProcessStateOnRequest();
        if (this.pendingConnectionGroup > 0 && processRecord != null) {
            com.android.server.am.ProcessServiceRecord processServiceRecord = processRecord.mServices;
            processServiceRecord.setConnectionService(this);
            processServiceRecord.setConnectionGroup(this.pendingConnectionGroup);
            processServiceRecord.setConnectionImportance(this.pendingConnectionImportance);
            this.pendingConnectionImportance = 0;
            this.pendingConnectionGroup = 0;
        }
        for (int size = this.connections.size() - 1; size >= 0; size--) {
            java.util.ArrayList<com.android.server.am.ConnectionRecord> valueAt = this.connections.valueAt(size);
            for (int i2 = 0; i2 < valueAt.size(); i2++) {
                com.android.server.am.ConnectionRecord connectionRecord = valueAt.get(i2);
                if (processRecord != null) {
                    connectionRecord.startAssociationIfNeeded();
                } else {
                    connectionRecord.stopAssociation();
                }
            }
        }
        if (processRecord != null) {
            processRecord.mServices.updateBoundClientUids();
            processRecord.mServices.updateHostingComonentTypeForBindingsLocked();
        }
    }

    void updateProcessStateOnRequest() {
        this.mProcessStateOnRequest = (this.app == null || this.app.getThread() == null || this.app.isKilled()) ? 20 : this.app.mState.getCurProcState();
    }

    @android.annotation.NonNull
    android.util.ArrayMap<android.os.IBinder, java.util.ArrayList<com.android.server.am.ConnectionRecord>> getConnections() {
        return this.connections;
    }

    void addConnection(android.os.IBinder iBinder, com.android.server.am.ConnectionRecord connectionRecord) {
        java.util.ArrayList<com.android.server.am.ConnectionRecord> arrayList = this.connections.get(iBinder);
        if (arrayList == null) {
            arrayList = new java.util.ArrayList<>();
            this.connections.put(iBinder, arrayList);
        }
        arrayList.add(connectionRecord);
        if (this.app != null) {
            this.app.mServices.addBoundClientUid(connectionRecord.clientUid, connectionRecord.clientPackageName, connectionRecord.getFlags());
            this.app.mProfile.addHostingComponentType(512);
        }
    }

    void removeConnection(android.os.IBinder iBinder) {
        this.connections.remove(iBinder);
        if (this.app != null) {
            this.app.mServices.updateBoundClientUids();
            this.app.mServices.updateHostingComonentTypeForBindingsLocked();
        }
    }

    boolean canStopIfKilled(boolean z) {
        if (isShortFgs()) {
            return true;
        }
        return this.startRequested && (this.stopIfKilled || z) && this.pendingStarts.isEmpty();
    }

    void updateIsAllowedBgActivityStartsByBinding() {
        boolean z = false;
        for (int size = this.connections.size() - 1; size >= 0; size--) {
            java.util.ArrayList<com.android.server.am.ConnectionRecord> valueAt = this.connections.valueAt(size);
            int i = 0;
            while (true) {
                if (i >= valueAt.size()) {
                    break;
                }
                if (!valueAt.get(i).hasFlag(1048576)) {
                    i++;
                } else {
                    z = true;
                    break;
                }
            }
            if (z) {
                break;
            }
        }
        setAllowedBgActivityStartsByBinding(z);
    }

    void setAllowedBgActivityStartsByBinding(boolean z) {
        this.mIsAllowedBgActivityStartsByBinding = z;
        updateParentProcessBgActivityStartsToken();
    }

    void allowBgActivityStartsOnServiceStart(android.app.BackgroundStartPrivileges backgroundStartPrivileges) {
        com.android.internal.util.Preconditions.checkArgument(backgroundStartPrivileges.allowsAny());
        this.mBackgroundStartPrivilegesByStart.add(backgroundStartPrivileges);
        setAllowedBgActivityStartsByStart(backgroundStartPrivileges.merge(this.mBackgroundStartPrivilegesByStartMerged));
        if (this.app != null) {
            this.mAppForAllowingBgActivityStartsByStart = this.app;
        }
        if (this.mCleanUpAllowBgActivityStartsByStartCallback == null) {
            this.mCleanUpAllowBgActivityStartsByStartCallback = new java.lang.Runnable() { // from class: com.android.server.am.ServiceRecord$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.ServiceRecord.this.lambda$allowBgActivityStartsOnServiceStart$0();
                }
            };
        }
        this.ams.mHandler.postDelayed(this.mCleanUpAllowBgActivityStartsByStartCallback, this.ams.mConstants.SERVICE_BG_ACTIVITY_START_TIMEOUT);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$allowBgActivityStartsOnServiceStart$0() {
        com.android.server.am.ActivityManagerService activityManagerService = this.ams;
        com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                this.mBackgroundStartPrivilegesByStart.remove(0);
                if (!this.mBackgroundStartPrivilegesByStart.isEmpty()) {
                    this.mBackgroundStartPrivilegesByStartMerged = android.app.BackgroundStartPrivileges.merge(this.mBackgroundStartPrivilegesByStart);
                    if (this.mBackgroundStartPrivilegesByStartMerged.allowsAny()) {
                        if (this.mAppForAllowingBgActivityStartsByStart != null) {
                            this.mAppForAllowingBgActivityStartsByStart.addOrUpdateBackgroundStartPrivileges(this, getBackgroundStartPrivilegesWithExclusiveToken());
                        }
                    } else {
                        android.util.Slog.wtf(TAG, "Service callback to revoke bg activity starts by service start triggered but mBackgroundStartPrivilegesByStartMerged = " + this.mBackgroundStartPrivilegesByStartMerged + ". This should never happen.");
                    }
                } else {
                    if (this.app == this.mAppForAllowingBgActivityStartsByStart) {
                        setAllowedBgActivityStartsByStart(android.app.BackgroundStartPrivileges.NONE);
                    } else if (this.mAppForAllowingBgActivityStartsByStart != null) {
                        this.mAppForAllowingBgActivityStartsByStart.removeBackgroundStartPrivileges(this);
                    }
                    this.mAppForAllowingBgActivityStartsByStart = null;
                }
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
    }

    void updateAllowUiJobScheduling(boolean z) {
        if (this.mAllowUiJobScheduling == z) {
            return;
        }
        this.mAllowUiJobScheduling = z;
    }

    private void setAllowedBgActivityStartsByStart(android.app.BackgroundStartPrivileges backgroundStartPrivileges) {
        if (this.mBackgroundStartPrivilegesByStartMerged == backgroundStartPrivileges) {
            return;
        }
        this.mBackgroundStartPrivilegesByStartMerged = backgroundStartPrivileges;
        updateParentProcessBgActivityStartsToken();
    }

    private void updateParentProcessBgActivityStartsToken() {
        if (this.app == null) {
            return;
        }
        android.app.BackgroundStartPrivileges backgroundStartPrivilegesWithExclusiveToken = getBackgroundStartPrivilegesWithExclusiveToken();
        if (backgroundStartPrivilegesWithExclusiveToken.allowsAny()) {
            this.app.addOrUpdateBackgroundStartPrivileges(this, backgroundStartPrivilegesWithExclusiveToken);
        } else {
            this.app.removeBackgroundStartPrivileges(this);
        }
    }

    @android.annotation.Nullable
    private android.app.BackgroundStartPrivileges getBackgroundStartPrivilegesWithExclusiveToken() {
        if (this.mIsAllowedBgActivityStartsByBinding) {
            return android.app.BackgroundStartPrivileges.ALLOW_BAL;
        }
        if (this.mBackgroundStartPrivilegesByStart.isEmpty()) {
            return android.app.BackgroundStartPrivileges.NONE;
        }
        return this.mBackgroundStartPrivilegesByStartMerged;
    }

    @com.android.internal.annotations.GuardedBy({"ams"})
    void updateKeepWarmLocked() {
        this.mKeepWarming = this.ams.mConstants.KEEP_WARMING_SERVICES.contains(this.name) && (this.ams.mUserController.getCurrentUserId() == this.userId || this.ams.mUserController.isCurrentProfile(this.userId) || this.ams.isSingleton(this.processName, this.appInfo, this.instanceName.getClassName(), this.serviceInfo.flags));
    }

    public com.android.server.am.AppBindRecord retrieveAppBindingLocked(android.content.Intent intent, com.android.server.am.ProcessRecord processRecord, com.android.server.am.ProcessRecord processRecord2) {
        android.content.Intent.FilterComparison filterComparison = new android.content.Intent.FilterComparison(intent);
        com.android.server.am.IntentBindRecord intentBindRecord = this.bindings.get(filterComparison);
        if (intentBindRecord == null) {
            intentBindRecord = new com.android.server.am.IntentBindRecord(this, filterComparison);
            this.bindings.put(filterComparison, intentBindRecord);
        }
        com.android.server.am.AppBindRecord appBindRecord = intentBindRecord.apps.get(processRecord);
        if (appBindRecord != null) {
            return appBindRecord;
        }
        com.android.server.am.AppBindRecord appBindRecord2 = new com.android.server.am.AppBindRecord(this, intentBindRecord, processRecord, processRecord2);
        intentBindRecord.apps.put(processRecord, appBindRecord2);
        return appBindRecord2;
    }

    public boolean hasAutoCreateConnections() {
        int size = this.connections.size() - 1;
        while (true) {
            if (size < 0) {
                return false;
            }
            java.util.ArrayList<com.android.server.am.ConnectionRecord> valueAt = this.connections.valueAt(size);
            for (int i = 0; i < valueAt.size(); i++) {
                if (valueAt.get(i).hasFlag(1)) {
                    return true;
                }
            }
            size--;
        }
    }

    public void updateAllowlistManager() {
        this.allowlistManager = false;
        for (int size = this.connections.size() - 1; size >= 0; size--) {
            java.util.ArrayList<com.android.server.am.ConnectionRecord> valueAt = this.connections.valueAt(size);
            for (int i = 0; i < valueAt.size(); i++) {
                if (valueAt.get(i).hasFlag(16777216)) {
                    this.allowlistManager = true;
                    return;
                }
            }
        }
    }

    public void resetRestartCounter() {
        this.restartCount = 0;
        this.restartDelay = 0L;
        this.restartTime = 0L;
        this.mEarliestRestartTime = 0L;
        this.mRestartSchedulingTime = 0L;
    }

    public com.android.server.am.ServiceRecord.StartItem findDeliveredStart(int i, boolean z, boolean z2) {
        int size = this.deliveredStarts.size();
        for (int i2 = 0; i2 < size; i2++) {
            com.android.server.am.ServiceRecord.StartItem startItem = this.deliveredStarts.get(i2);
            if (startItem.id == i && startItem.taskRemoved == z) {
                if (z2) {
                    this.deliveredStarts.remove(i2);
                }
                return startItem;
            }
        }
        return null;
    }

    public int getLastStartId() {
        return this.lastStartId;
    }

    public int makeNextStartId() {
        this.lastStartId++;
        if (this.lastStartId < 1) {
            this.lastStartId = 1;
        }
        return this.lastStartId;
    }

    private void updateFgsHasNotificationPermission() {
        final java.lang.String str = this.packageName;
        final int i = this.appInfo.uid;
        this.ams.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.ServiceRecord.1
            @Override // java.lang.Runnable
            public void run() {
                com.android.server.notification.NotificationManagerInternal notificationManagerInternal = (com.android.server.notification.NotificationManagerInternal) com.android.server.LocalServices.getService(com.android.server.notification.NotificationManagerInternal.class);
                if (notificationManagerInternal == null) {
                    return;
                }
                com.android.server.am.ServiceRecord.this.mFgsHasNotificationPermission = notificationManagerInternal.areNotificationsEnabledForPackage(str, i);
            }
        });
    }

    public void postNotification(final boolean z) {
        if (this.isForeground && this.foregroundNoti != null && this.app != null) {
            final int i = this.appInfo.uid;
            final int pid = this.app.getPid();
            final java.lang.String str = this.packageName;
            final int i2 = this.foregroundId;
            final android.app.Notification notification = this.foregroundNoti;
            this.ams.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.ServiceRecord.2
                /* JADX WARN: Removed duplicated region for block: B:17:0x011d  */
                /* JADX WARN: Removed duplicated region for block: B:28:0x0161 A[Catch: RuntimeException -> 0x0065, TryCatch #1 {RuntimeException -> 0x0065, blocks: (B:7:0x001e, B:9:0x0025, B:11:0x005e, B:13:0x0069, B:15:0x010e, B:19:0x011f, B:21:0x013f, B:22:0x015a, B:26:0x015b, B:28:0x0161, B:30:0x018c, B:31:0x01a7), top: B:6:0x001e }] */
                /* JADX WARN: Removed duplicated region for block: B:30:0x018c A[Catch: RuntimeException -> 0x0065, TryCatch #1 {RuntimeException -> 0x0065, blocks: (B:7:0x001e, B:9:0x0025, B:11:0x005e, B:13:0x0069, B:15:0x010e, B:19:0x011f, B:21:0x013f, B:22:0x015a, B:26:0x015b, B:28:0x0161, B:30:0x018c, B:31:0x01a7), top: B:6:0x001e }] */
                @Override // java.lang.Runnable
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public void run() {
                    android.app.Notification notification2;
                    int i3;
                    com.android.server.notification.NotificationManagerInternal notificationManagerInternal = (com.android.server.notification.NotificationManagerInternal) com.android.server.LocalServices.getService(com.android.server.notification.NotificationManagerInternal.class);
                    if (notificationManagerInternal == null) {
                        return;
                    }
                    com.android.server.am.ServiceRecord.this.mFgsHasNotificationPermission = notificationManagerInternal.areNotificationsEnabledForPackage(str, i);
                    android.app.Notification notification3 = notification;
                    try {
                        if (notification3.getSmallIcon() == null) {
                            android.util.Slog.v(com.android.server.am.ServiceRecord.TAG, "Attempted to start a foreground service (" + com.android.server.am.ServiceRecord.this.shortInstanceName + ") with a broken notification (no icon: " + notification3 + ")");
                            java.lang.CharSequence loadLabel = com.android.server.am.ServiceRecord.this.appInfo.loadLabel(com.android.server.am.ServiceRecord.this.ams.mContext.getPackageManager());
                            if (loadLabel == null) {
                                loadLabel = com.android.server.am.ServiceRecord.this.appInfo.packageName;
                            }
                            try {
                                android.app.Notification.Builder builder = new android.app.Notification.Builder(com.android.server.am.ServiceRecord.this.ams.mContext.createPackageContextAsUser(com.android.server.am.ServiceRecord.this.appInfo.packageName, 0, new android.os.UserHandle(com.android.server.am.ServiceRecord.this.userId)), notification3.getChannelId());
                                builder.setSmallIcon(com.android.server.am.ServiceRecord.this.appInfo.icon);
                                builder.setFlag(64, true);
                                android.content.Intent intent = new android.content.Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                                intent.setData(android.net.Uri.fromParts(com.android.server.pm.Settings.ATTR_PACKAGE, com.android.server.am.ServiceRecord.this.appInfo.packageName, null));
                                android.app.PendingIntent activityAsUser = android.app.PendingIntent.getActivityAsUser(com.android.server.am.ServiceRecord.this.ams.mContext, 0, intent, android.hardware.audio.common.V2_0.AudioFormat.DTS_HD, null, android.os.UserHandle.of(com.android.server.am.ServiceRecord.this.userId));
                                builder.setColor(com.android.server.am.ServiceRecord.this.ams.mContext.getColor(android.R.color.system_notification_accent_color));
                                builder.setContentTitle(com.android.server.am.ServiceRecord.this.ams.mContext.getString(android.R.string.app_category_productivity, loadLabel));
                                builder.setContentText(com.android.server.am.ServiceRecord.this.ams.mContext.getString(android.R.string.app_category_news, loadLabel));
                                builder.setContentIntent(activityAsUser);
                                notification2 = builder.build();
                            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                            }
                            if (notificationManagerInternal.getNotificationChannel(str, i, notification2.getChannelId()) == null) {
                                try {
                                    i3 = com.android.server.am.ServiceRecord.this.ams.mContext.getPackageManager().getApplicationInfoAsUser(com.android.server.am.ServiceRecord.this.appInfo.packageName, 0, com.android.server.am.ServiceRecord.this.userId).targetSdkVersion;
                                } catch (android.content.pm.PackageManager.NameNotFoundException e2) {
                                    i3 = 27;
                                }
                                if (i3 >= 27) {
                                    throw new java.lang.RuntimeException("invalid channel for service notification: " + com.android.server.am.ServiceRecord.this.foregroundNoti);
                                }
                            }
                            if (notification2.getSmallIcon() == null) {
                                notificationManagerInternal.enqueueNotification(str, str, i, pid, null, i2, notification2, com.android.server.am.ServiceRecord.this.userId, z);
                                com.android.server.am.ServiceRecord.this.foregroundNoti = notification2;
                                com.android.server.am.ServiceRecord.this.signalForegroundServiceNotification(com.android.server.am.ServiceRecord.this.packageName, com.android.server.am.ServiceRecord.this.appInfo.uid, i2, false);
                                return;
                            }
                            throw new java.lang.RuntimeException("invalid service notification: " + com.android.server.am.ServiceRecord.this.foregroundNoti);
                        }
                        notification2 = notification3;
                        if (notificationManagerInternal.getNotificationChannel(str, i, notification2.getChannelId()) == null) {
                        }
                        if (notification2.getSmallIcon() == null) {
                        }
                    } catch (java.lang.RuntimeException e3) {
                        android.util.Slog.w(com.android.server.am.ServiceRecord.TAG, "Error showing notification for service", e3);
                        com.android.server.am.ServiceRecord.this.ams.mServices.killMisbehavingService(this, i, pid, str, 2);
                    }
                }
            });
        }
    }

    public void cancelNotification() {
        final java.lang.String str = this.packageName;
        final int i = this.foregroundId;
        final int i2 = this.appInfo.uid;
        final int pid = this.app != null ? this.app.getPid() : 0;
        this.ams.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.ServiceRecord.3
            @Override // java.lang.Runnable
            public void run() {
                com.android.server.notification.NotificationManagerInternal notificationManagerInternal = (com.android.server.notification.NotificationManagerInternal) com.android.server.LocalServices.getService(com.android.server.notification.NotificationManagerInternal.class);
                if (notificationManagerInternal == null) {
                    return;
                }
                try {
                    notificationManagerInternal.cancelNotification(str, str, i2, pid, null, i, com.android.server.am.ServiceRecord.this.userId);
                } catch (java.lang.RuntimeException e) {
                    android.util.Slog.w(com.android.server.am.ServiceRecord.TAG, "Error canceling notification for service", e);
                }
                com.android.server.am.ServiceRecord.this.signalForegroundServiceNotification(com.android.server.am.ServiceRecord.this.packageName, com.android.server.am.ServiceRecord.this.appInfo.uid, i, true);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void signalForegroundServiceNotification(java.lang.String str, int i, int i2, boolean z) {
        com.android.server.am.ActivityManagerService activityManagerService = this.ams;
        com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                for (int size = this.ams.mForegroundServiceStateListeners.size() - 1; size >= 0; size--) {
                    this.ams.mForegroundServiceStateListeners.get(size).onForegroundServiceNotificationUpdated(str, this.appInfo.uid, i2, z);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
    }

    public void stripForegroundServiceFlagFromNotification() {
        final int i = this.foregroundId;
        final int i2 = this.userId;
        final java.lang.String str = this.packageName;
        this.ams.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.ServiceRecord.4
            @Override // java.lang.Runnable
            public void run() {
                com.android.server.notification.NotificationManagerInternal notificationManagerInternal = (com.android.server.notification.NotificationManagerInternal) com.android.server.LocalServices.getService(com.android.server.notification.NotificationManagerInternal.class);
                if (notificationManagerInternal == null) {
                    return;
                }
                notificationManagerInternal.removeForegroundServiceFlagFromNotification(str, i, i2);
            }
        });
    }

    public void clearDeliveredStartsLocked() {
        for (int size = this.deliveredStarts.size() - 1; size >= 0; size--) {
            this.deliveredStarts.get(size).removeUriPermissionsLocked();
        }
        this.deliveredStarts.clear();
    }

    public java.lang.String toString() {
        if (this.stringName != null) {
            return this.stringName;
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
        sb.append("ServiceRecord{");
        sb.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
        sb.append(" u");
        sb.append(this.userId);
        sb.append(' ');
        sb.append(this.shortInstanceName);
        if (this.mRecentCallingPackage != null) {
            sb.append(" c:");
            sb.append(this.mRecentCallingPackage);
        }
        sb.append('}');
        java.lang.String sb2 = sb.toString();
        this.stringName = sb2;
        return sb2;
    }

    public android.content.ComponentName getComponentName() {
        return this.name;
    }

    public boolean isShortFgs() {
        return this.startRequested && this.isForeground && this.foregroundServiceType == 2048;
    }

    public com.android.server.am.ServiceRecord.ShortFgsInfo getShortFgsInfo() {
        if (isShortFgs()) {
            return this.mShortFgsInfo;
        }
        return null;
    }

    public void setShortFgsInfo(long j) {
        this.mShortFgsInfo = new com.android.server.am.ServiceRecord.ShortFgsInfo(j);
    }

    public boolean hasShortFgsInfo() {
        return this.mShortFgsInfo != null;
    }

    public void clearShortFgsInfo() {
        this.mShortFgsInfo = null;
    }

    private boolean shouldTriggerShortFgsTimedEvent(long j, long j2) {
        return isAppAlive() && this.startRequested && isShortFgs() && this.mShortFgsInfo != null && this.mShortFgsInfo.isCurrent() && j <= j2;
    }

    public boolean shouldTriggerShortFgsTimeout(long j) {
        return shouldTriggerShortFgsTimedEvent(this.mShortFgsInfo == null ? 0L : this.mShortFgsInfo.getTimeoutTime(), j);
    }

    public boolean shouldDemoteShortFgsProcState(long j) {
        return shouldTriggerShortFgsTimedEvent(this.mShortFgsInfo == null ? 0L : this.mShortFgsInfo.getProcStateDemoteTime(), j);
    }

    public boolean shouldTriggerShortFgsAnr(long j) {
        return shouldTriggerShortFgsTimedEvent(this.mShortFgsInfo == null ? 0L : this.mShortFgsInfo.getAnrTime(), j);
    }

    public java.lang.String getShortFgsTimedEventDescription(long j) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("aa=");
        sb.append(isAppAlive());
        sb.append(" sreq=");
        sb.append(this.startRequested);
        sb.append(" isfg=");
        sb.append(this.isForeground);
        sb.append(" type=");
        sb.append(java.lang.Integer.toHexString(this.foregroundServiceType));
        sb.append(" sfc=");
        sb.append(this.mStartForegroundCount);
        sb.append(" now=");
        sb.append(j);
        sb.append(" ");
        sb.append(this.mShortFgsInfo == null ? "" : this.mShortFgsInfo.getDescription());
        return sb.toString();
    }

    public boolean isFgsTimeLimited() {
        return this.startRequested && this.isForeground && canFgsTypeTimeOut(this.foregroundServiceType);
    }

    public void setIsFgsTimeLimited(boolean z) {
        this.mFgsIsTimeLimited = z;
    }

    public boolean wasFgsPreviouslyTimeLimited() {
        return this.mFgsIsTimeLimited;
    }

    public int getTimedOutFgsType(long j) {
        if (!isAppAlive() || !isFgsTimeLimited()) {
            return -1;
        }
        android.util.Pair<java.lang.Integer, java.lang.Long> earliestStopTypeAndTime = getEarliestStopTypeAndTime();
        if (((java.lang.Integer) earliestStopTypeAndTime.first).intValue() == -1 || ((java.lang.Long) earliestStopTypeAndTime.second).longValue() > j) {
            return -1;
        }
        return ((java.lang.Integer) earliestStopTypeAndTime.first).intValue();
    }

    android.util.Pair<java.lang.Integer, java.lang.Long> getEarliestStopTypeAndTime() {
        long j;
        int i = 8192;
        if ((this.foregroundServiceType & 8192) != 8192) {
            i = -1;
            j = 0;
        } else {
            j = this.ams.mConstants.mMediaProcessingFgsTimeoutDuration;
        }
        if ((this.foregroundServiceType & 1) == 1 && (j == 0 || this.ams.mConstants.mDataSyncFgsTimeoutDuration < j)) {
            j = this.ams.mConstants.mDataSyncFgsTimeoutDuration;
            i = 1;
        }
        return android.util.Pair.create(java.lang.Integer.valueOf(i), java.lang.Long.valueOf(j != 0 ? this.mFgsEnterTime + j : 0L));
    }

    boolean canFgsTypeTimeOut(int i) {
        return (i & 8192) == 8192 || (i & 1) == 1;
    }

    private boolean isAppAlive() {
        return (this.app == null || this.app.getThread() == null || this.app.isKilled() || this.app.isKilledByAm()) ? false : true;
    }
}
