package com.android.server.am;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class ProcessRecord implements com.android.server.wm.WindowProcessListener {
    static final java.lang.String TAG = "ActivityManager";
    final boolean appZygote;
    volatile android.content.pm.ApplicationInfo info;
    public final boolean isSdkSandbox;
    final boolean isolated;

    @com.android.internal.annotations.GuardedBy({"mBackgroundStartPrivileges"})
    private final android.util.ArrayMap<android.os.Binder, android.app.BackgroundStartPrivileges> mBackgroundStartPrivileges;

    @com.android.internal.annotations.GuardedBy({"mBackgroundStartPrivileges"})
    @android.annotation.Nullable
    private android.app.BackgroundStartPrivileges mBackgroundStartPrivilegesMerged;
    private volatile long mBindApplicationTime;
    private volatile boolean mBindMountPending;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private android.content.res.CompatibilityInfo mCompat;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private android.os.IBinder.DeathRecipient mDeathRecipient;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private boolean mDebugging;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private long[] mDisabledCompatChanges;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private int mDyingPid;
    final com.android.server.am.ProcessErrorStateRecord mErrorState;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private int[] mGids;
    private volatile com.android.server.am.HostingRecord mHostingRecord;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private boolean mInFullBackup;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private com.android.server.am.ActiveInstrumentation mInstr;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private java.lang.String mInstructionSet;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private java.lang.String mIsolatedEntryPoint;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private java.lang.String[] mIsolatedEntryPointArgs;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private long mKillTime;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private boolean mKilled;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private boolean mKilledByAm;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private long mLastActivityTime;
    final com.android.server.am.OomAdjusterModernImpl.ProcessRecordNode[] mLinkedNodes;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private int mLruSeq;
    private volatile int mMountMode;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private android.app.IApplicationThread mOnewayThread;
    final com.android.server.am.ProcessCachedOptimizerRecord mOptRecord;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private boolean mPendingFinishAttach;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private boolean mPendingStart;
    private volatile boolean mPersistent;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    int mPid;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private android.util.ArraySet<java.lang.String> mPkgDeps;
    private final com.android.server.am.PackageList mPkgList;
    volatile com.android.server.am.ProcessRecord mPredecessor;
    private final com.android.server.am.ActivityManagerGlobalLock mProcLock;
    volatile boolean mProcessGroupCreated;
    final com.android.server.am.ProcessProfileRecord mProfile;
    final com.android.server.am.ProcessProviderRecord mProviders;
    final com.android.server.am.ProcessReceiverRecord mReceivers;
    private volatile boolean mRemoved;

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    private int mRenderThreadTid;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private java.lang.String mRequiredAbi;
    private volatile java.lang.String mSeInfo;
    final com.android.server.am.ActivityManagerService mService;
    final com.android.server.am.ProcessServiceRecord mServices;
    private java.lang.String mShortStringName;
    volatile boolean mSkipProcessGroupCreation;
    private volatile long mStartElapsedTime;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private long mStartSeq;
    private volatile int mStartUid;
    private volatile long mStartUptime;
    com.android.server.am.ProcessStateRecord mState;
    private java.lang.String mStringName;
    volatile com.android.server.am.ProcessRecord mSuccessor;
    java.lang.Runnable mSuccessorStartRunnable;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private android.app.IApplicationThread mThread;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private com.android.server.am.UidRecord mUidRecord;

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    private boolean mUnlocked;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private boolean mUsingWrapper;

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    private boolean mWaitedForDebugger;

    @com.android.internal.annotations.GuardedBy({"mService"})
    private java.lang.String mWaitingToKill;
    volatile boolean mWasForceStopped;
    private final com.android.server.wm.WindowProcessController mWindowProcessController;
    final android.content.pm.ProcessInfo processInfo;
    final java.lang.String processName;
    final java.lang.String sdkSandboxClientAppPackage;
    final java.lang.String sdkSandboxClientAppVolumeUuid;
    final int uid;
    final int userId;

    void setStartParams(int i, com.android.server.am.HostingRecord hostingRecord, java.lang.String str, long j, long j2) {
        this.mStartUid = i;
        this.mHostingRecord = hostingRecord;
        this.mSeInfo = str;
        this.mStartUptime = j;
        this.mStartElapsedTime = j2;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        printWriter.print(str);
        printWriter.print("user #");
        printWriter.print(this.userId);
        printWriter.print(" uid=");
        printWriter.print(this.info.uid);
        if (this.uid != this.info.uid) {
            printWriter.print(" ISOLATED uid=");
            printWriter.print(this.uid);
        }
        printWriter.print(" gids={");
        if (this.mGids != null) {
            for (int i = 0; i < this.mGids.length; i++) {
                if (i != 0) {
                    printWriter.print(", ");
                }
                printWriter.print(this.mGids[i]);
            }
        }
        printWriter.println("}");
        if (this.processInfo != null) {
            printWriter.print(str);
            printWriter.println("processInfo:");
            if (this.processInfo.deniedPermissions != null) {
                for (int i2 = 0; i2 < this.processInfo.deniedPermissions.size(); i2++) {
                    printWriter.print(str);
                    printWriter.print("  deny: ");
                    printWriter.println((java.lang.String) this.processInfo.deniedPermissions.valueAt(i2));
                }
            }
            if (this.processInfo.gwpAsanMode != -1) {
                printWriter.print(str);
                printWriter.println("  gwpAsanMode=" + this.processInfo.gwpAsanMode);
            }
            if (this.processInfo.memtagMode != -1) {
                printWriter.print(str);
                printWriter.println("  memtagMode=" + this.processInfo.memtagMode);
            }
        }
        printWriter.print(str);
        printWriter.print("mRequiredAbi=");
        printWriter.print(this.mRequiredAbi);
        printWriter.print(" instructionSet=");
        printWriter.println(this.mInstructionSet);
        if (this.info.className != null) {
            printWriter.print(str);
            printWriter.print("class=");
            printWriter.println(this.info.className);
        }
        if (this.info.manageSpaceActivityName != null) {
            printWriter.print(str);
            printWriter.print("manageSpaceActivityName=");
            printWriter.println(this.info.manageSpaceActivityName);
        }
        printWriter.print(str);
        printWriter.print("dir=");
        printWriter.print(this.info.sourceDir);
        printWriter.print(" publicDir=");
        printWriter.print(this.info.publicSourceDir);
        printWriter.print(" data=");
        printWriter.println(this.info.dataDir);
        this.mPkgList.dump(printWriter, str);
        if (this.mPkgDeps != null) {
            printWriter.print(str);
            printWriter.print("packageDependencies={");
            for (int i3 = 0; i3 < this.mPkgDeps.size(); i3++) {
                if (i3 > 0) {
                    printWriter.print(", ");
                }
                printWriter.print(this.mPkgDeps.valueAt(i3));
            }
            printWriter.println("}");
        }
        printWriter.print(str);
        printWriter.print("compat=");
        printWriter.println(this.mCompat);
        if (this.mInstr != null) {
            printWriter.print(str);
            printWriter.print("mInstr=");
            printWriter.println(this.mInstr);
        }
        printWriter.print(str);
        printWriter.print("thread=");
        printWriter.println(this.mThread);
        printWriter.print(str);
        printWriter.print("pid=");
        printWriter.println(this.mPid);
        printWriter.print(str);
        printWriter.print("lastActivityTime=");
        android.util.TimeUtils.formatDuration(this.mLastActivityTime, uptimeMillis, printWriter);
        printWriter.print(str);
        printWriter.print("startUpTime=");
        android.util.TimeUtils.formatDuration(this.mStartUptime, uptimeMillis, printWriter);
        printWriter.print(str);
        printWriter.print("startElapsedTime=");
        android.util.TimeUtils.formatDuration(this.mStartElapsedTime, elapsedRealtime, printWriter);
        printWriter.println();
        if (this.mPersistent || this.mRemoved) {
            printWriter.print(str);
            printWriter.print("persistent=");
            printWriter.print(this.mPersistent);
            printWriter.print(" removed=");
            printWriter.println(this.mRemoved);
        }
        if (this.mDebugging) {
            printWriter.print(str);
            printWriter.print("mDebugging=");
            printWriter.println(this.mDebugging);
        }
        if (this.mPendingStart) {
            printWriter.print(str);
            printWriter.print("pendingStart=");
            printWriter.println(this.mPendingStart);
        }
        printWriter.print(str);
        printWriter.print("startSeq=");
        printWriter.println(this.mStartSeq);
        printWriter.print(str);
        printWriter.print("mountMode=");
        printWriter.println(android.util.DebugUtils.valueToString(com.android.internal.os.Zygote.class, "MOUNT_EXTERNAL_", this.mMountMode));
        if (this.mKilled || this.mKilledByAm || this.mWaitingToKill != null) {
            printWriter.print(str);
            printWriter.print("killed=");
            printWriter.print(this.mKilled);
            printWriter.print(" killedByAm=");
            printWriter.print(this.mKilledByAm);
            printWriter.print(" waitingToKill=");
            printWriter.println(this.mWaitingToKill);
        }
        if (this.mIsolatedEntryPoint != null || this.mIsolatedEntryPointArgs != null) {
            printWriter.print(str);
            printWriter.print("isolatedEntryPoint=");
            printWriter.println(this.mIsolatedEntryPoint);
            printWriter.print(str);
            printWriter.print("isolatedEntryPointArgs=");
            printWriter.println(java.util.Arrays.toString(this.mIsolatedEntryPointArgs));
        }
        if (this.mState.getSetProcState() > 10) {
            this.mProfile.dumpCputime(printWriter, str);
        }
        this.mProfile.dumpPss(printWriter, str, uptimeMillis);
        this.mState.dump(printWriter, str, uptimeMillis);
        this.mErrorState.dump(printWriter, str, uptimeMillis);
        this.mServices.dump(printWriter, str, uptimeMillis);
        this.mProviders.dump(printWriter, str, uptimeMillis);
        this.mReceivers.dump(printWriter, str, uptimeMillis);
        this.mOptRecord.dump(printWriter, str, uptimeMillis);
        this.mWindowProcessController.dump(printWriter, str);
    }

    ProcessRecord(com.android.server.am.ActivityManagerService activityManagerService, android.content.pm.ApplicationInfo applicationInfo, java.lang.String str, int i) {
        this(activityManagerService, applicationInfo, str, i, null, -1, null);
    }

    ProcessRecord(com.android.server.am.ActivityManagerService activityManagerService, android.content.pm.ApplicationInfo applicationInfo, java.lang.String str, int i, java.lang.String str2, int i2, java.lang.String str3) {
        android.content.pm.ProcessInfo processInfo;
        this.mPkgList = new com.android.server.am.PackageList(this);
        this.mBackgroundStartPrivileges = new android.util.ArrayMap<>();
        this.mBackgroundStartPrivilegesMerged = android.app.BackgroundStartPrivileges.NONE;
        this.mLinkedNodes = new com.android.server.am.OomAdjusterModernImpl.ProcessRecordNode[2];
        this.mService = activityManagerService;
        this.mProcLock = activityManagerService.mProcLock;
        this.info = applicationInfo;
        if (activityManagerService.mPackageManagerInt == null) {
            processInfo = null;
        } else {
            if (i2 > 0) {
                android.util.ArrayMap<java.lang.String, android.content.pm.ProcessInfo> processesForUid = activityManagerService.mPackageManagerInt.getProcessesForUid(i2);
                processInfo = processesForUid != null ? processesForUid.get(str3) : null;
            } else {
                android.util.ArrayMap<java.lang.String, android.content.pm.ProcessInfo> processesForUid2 = activityManagerService.mPackageManagerInt.getProcessesForUid(i);
                processInfo = processesForUid2 != null ? processesForUid2.get(str) : null;
            }
            if (processInfo != null && processInfo.deniedPermissions == null && processInfo.gwpAsanMode == -1 && processInfo.memtagMode == -1 && processInfo.nativeHeapZeroInitialized == -1) {
                processInfo = null;
            }
        }
        this.processInfo = processInfo;
        this.isolated = android.os.Process.isIsolated(i);
        this.isSdkSandbox = android.os.Process.isSdkSandboxUid(i);
        this.appZygote = android.os.UserHandle.getAppId(i) >= 90000 && android.os.UserHandle.getAppId(i) <= 98999;
        this.uid = i;
        this.userId = android.os.UserHandle.getUserId(i);
        this.processName = str;
        this.sdkSandboxClientAppPackage = str2;
        if (this.isSdkSandbox) {
            android.content.pm.ApplicationInfo clientInfoForSdkSandbox = getClientInfoForSdkSandbox();
            this.sdkSandboxClientAppVolumeUuid = clientInfoForSdkSandbox != null ? clientInfoForSdkSandbox.volumeUuid : null;
        } else {
            this.sdkSandboxClientAppVolumeUuid = null;
        }
        this.mPersistent = false;
        this.mRemoved = false;
        this.mProfile = new com.android.server.am.ProcessProfileRecord(this);
        this.mServices = new com.android.server.am.ProcessServiceRecord(this);
        this.mProviders = new com.android.server.am.ProcessProviderRecord(this);
        this.mReceivers = new com.android.server.am.ProcessReceiverRecord(this);
        this.mErrorState = new com.android.server.am.ProcessErrorStateRecord(this);
        this.mState = new com.android.server.am.ProcessStateRecord(this);
        this.mOptRecord = new com.android.server.am.ProcessCachedOptimizerRecord(this);
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        this.mProfile.init(uptimeMillis);
        this.mOptRecord.init(uptimeMillis);
        this.mState.init(uptimeMillis);
        this.mWindowProcessController = new com.android.server.wm.WindowProcessController(this.mService.mActivityTaskManager, this.info, this.processName, this.uid, this.userId, this, this);
        this.mPkgList.put(applicationInfo.packageName, new com.android.internal.app.procstats.ProcessStats.ProcessStateHolder(applicationInfo.longVersionCode));
        updateProcessRecordNodes(this);
    }

    @com.android.internal.annotations.VisibleForTesting
    static void updateProcessRecordNodes(@android.annotation.NonNull com.android.server.am.ProcessRecord processRecord) {
        if (processRecord.mService.mConstants.ENABLE_NEW_OOMADJ) {
            for (int i = 0; i < processRecord.mLinkedNodes.length; i++) {
                processRecord.mLinkedNodes[i] = new com.android.server.am.OomAdjusterModernImpl.ProcessRecordNode(processRecord);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void doEarlyCleanupIfNecessaryLocked() {
        if (getThread() == null) {
            this.mService.mOomAdjuster.onProcessEndLocked(this);
        }
    }

    void resetCrashingOnRestart() {
        this.mErrorState.setCrashing(false);
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    com.android.server.am.UidRecord getUidRecord() {
        return this.mUidRecord;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void setUidRecord(com.android.server.am.UidRecord uidRecord) {
        this.mUidRecord = uidRecord;
    }

    com.android.server.am.PackageList getPkgList() {
        return this.mPkgList;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    android.util.ArraySet<java.lang.String> getPkgDeps() {
        return this.mPkgDeps;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void setPkgDeps(android.util.ArraySet<java.lang.String> arraySet) {
        this.mPkgDeps = arraySet;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    int getPid() {
        return this.mPid;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void setPid(int i) {
        this.mPid = i;
        this.mWindowProcessController.setPid(i);
        this.mShortStringName = null;
        this.mStringName = null;
        synchronized (this.mProfile.mProfilerLock) {
            this.mProfile.setPid(i);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    int getSetAdj() {
        return this.mState.getSetAdj();
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    android.app.IApplicationThread getThread() {
        return this.mThread;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    android.app.IApplicationThread getOnewayThread() {
        return this.mOnewayThread;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    int getCurProcState() {
        return this.mState.getCurProcState();
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    int getSetProcState() {
        return this.mState.getSetProcState();
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    int getSetCapability() {
        return this.mState.getSetCapability();
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    public void makeActive(android.app.IApplicationThread iApplicationThread, com.android.server.am.ProcessStatsService processStatsService) {
        this.mProfile.onProcessActive(iApplicationThread, processStatsService);
        this.mThread = iApplicationThread;
        if (this.mPid == android.os.Process.myPid()) {
            this.mOnewayThread = new com.android.server.am.SameProcessApplicationThread(iApplicationThread, com.android.server.FgThread.getHandler());
        } else {
            this.mOnewayThread = iApplicationThread;
        }
        this.mWindowProcessController.setThread(iApplicationThread);
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    public void makeInactive(com.android.server.am.ProcessStatsService processStatsService) {
        this.mThread = null;
        this.mOnewayThread = null;
        this.mWindowProcessController.setThread(null);
        this.mProfile.onProcessInactive(processStatsService);
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    int getDyingPid() {
        return this.mDyingPid;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void setDyingPid(int i) {
        this.mDyingPid = i;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    int[] getGids() {
        return this.mGids;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void setGids(int[] iArr) {
        this.mGids = iArr;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    java.lang.String getRequiredAbi() {
        return this.mRequiredAbi;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void setRequiredAbi(java.lang.String str) {
        this.mRequiredAbi = str;
        this.mWindowProcessController.setRequiredAbi(str);
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    java.lang.String getInstructionSet() {
        return this.mInstructionSet;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void setInstructionSet(java.lang.String str) {
        this.mInstructionSet = str;
    }

    void setPersistent(boolean z) {
        this.mPersistent = z;
        this.mWindowProcessController.setPersistent(z);
    }

    boolean isPersistent() {
        return this.mPersistent;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    boolean isPendingStart() {
        return this.mPendingStart;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void setPendingStart(boolean z) {
        this.mPendingStart = z;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void setPendingFinishAttach(boolean z) {
        this.mPendingFinishAttach = z;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    boolean isPendingFinishAttach() {
        return this.mPendingFinishAttach;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    boolean isThreadReady() {
        return (this.mThread == null || this.mPendingFinishAttach) ? false : true;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    long getStartSeq() {
        return this.mStartSeq;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void setStartSeq(long j) {
        this.mStartSeq = j;
    }

    com.android.server.am.HostingRecord getHostingRecord() {
        return this.mHostingRecord;
    }

    void setHostingRecord(com.android.server.am.HostingRecord hostingRecord) {
        this.mHostingRecord = hostingRecord;
    }

    java.lang.String getSeInfo() {
        return this.mSeInfo;
    }

    void setSeInfo(java.lang.String str) {
        this.mSeInfo = str;
    }

    long getStartUptime() {
        return this.mStartUptime;
    }

    @java.lang.Deprecated
    long getStartTime() {
        return this.mStartUptime;
    }

    long getStartElapsedTime() {
        return this.mStartElapsedTime;
    }

    long getBindApplicationTime() {
        return this.mBindApplicationTime;
    }

    void setBindApplicationTime(long j) {
        this.mBindApplicationTime = j;
    }

    int getStartUid() {
        return this.mStartUid;
    }

    void setStartUid(int i) {
        this.mStartUid = i;
    }

    int getMountMode() {
        return this.mMountMode;
    }

    void setMountMode(int i) {
        this.mMountMode = i;
    }

    boolean isBindMountPending() {
        return this.mBindMountPending;
    }

    void setBindMountPending(boolean z) {
        this.mBindMountPending = z;
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    boolean isUnlocked() {
        return this.mUnlocked;
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    void setUnlocked(boolean z) {
        this.mUnlocked = z;
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    int getRenderThreadTid() {
        return this.mRenderThreadTid;
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    void setRenderThreadTid(int i) {
        this.mRenderThreadTid = i;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    android.content.res.CompatibilityInfo getCompat() {
        return this.mCompat;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void setCompat(android.content.res.CompatibilityInfo compatibilityInfo) {
        this.mCompat = compatibilityInfo;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    long[] getDisabledCompatChanges() {
        return this.mDisabledCompatChanges;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void setDisabledCompatChanges(long[] jArr) {
        this.mDisabledCompatChanges = jArr;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void unlinkDeathRecipient() {
        if (this.mDeathRecipient != null && this.mThread != null) {
            this.mThread.asBinder().unlinkToDeath(this.mDeathRecipient, 0);
        }
        this.mDeathRecipient = null;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void setDeathRecipient(android.os.IBinder.DeathRecipient deathRecipient) {
        this.mDeathRecipient = deathRecipient;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    android.os.IBinder.DeathRecipient getDeathRecipient() {
        return this.mDeathRecipient;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void setActiveInstrumentation(com.android.server.am.ActiveInstrumentation activeInstrumentation) {
        this.mInstr = activeInstrumentation;
        boolean z = false;
        boolean z2 = activeInstrumentation != null;
        com.android.server.wm.WindowProcessController windowProcessController = this.mWindowProcessController;
        int i = z2 ? activeInstrumentation.mSourceUid : -1;
        if (z2 && activeInstrumentation.mHasBackgroundActivityStartsPermission) {
            z = true;
        }
        windowProcessController.setInstrumenting(z2, i, z);
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    com.android.server.am.ActiveInstrumentation getActiveInstrumentation() {
        return this.mInstr;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    boolean isKilledByAm() {
        return this.mKilledByAm;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void setKilledByAm(boolean z) {
        this.mKilledByAm = z;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    boolean isKilled() {
        return this.mKilled;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void setKilled(boolean z) {
        this.mKilled = z;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    long getKillTime() {
        return this.mKillTime;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void setKillTime(long j) {
        this.mKillTime = j;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    java.lang.String getWaitingToKill() {
        return this.mWaitingToKill;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void setWaitingToKill(java.lang.String str) {
        this.mWaitingToKill = str;
    }

    @Override // com.android.server.wm.WindowProcessListener
    public boolean isRemoved() {
        return this.mRemoved;
    }

    void setRemoved(boolean z) {
        this.mRemoved = z;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    boolean isDebugging() {
        return this.mDebugging;
    }

    @android.annotation.Nullable
    public android.content.pm.ApplicationInfo getClientInfoForSdkSandbox() {
        if (!this.isSdkSandbox || this.sdkSandboxClientAppPackage == null) {
            throw new java.lang.IllegalStateException("getClientInfoForSdkSandbox called for non-sandbox process");
        }
        return this.mService.getPackageManagerInternal().getApplicationInfo(this.sdkSandboxClientAppPackage, 0L, 1000, this.userId);
    }

    public boolean isDebuggable() {
        if ((this.info.flags & 2) != 0) {
            return true;
        }
        if (!this.isSdkSandbox) {
            return false;
        }
        android.content.pm.ApplicationInfo clientInfoForSdkSandbox = getClientInfoForSdkSandbox();
        return (clientInfoForSdkSandbox == null || (clientInfoForSdkSandbox.flags & 2) == 0) ? false : true;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void setDebugging(boolean z) {
        this.mDebugging = z;
        this.mWindowProcessController.setDebugging(z);
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    boolean hasWaitedForDebugger() {
        return this.mWaitedForDebugger;
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    void setWaitedForDebugger(boolean z) {
        this.mWaitedForDebugger = z;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    long getLastActivityTime() {
        return this.mLastActivityTime;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void setLastActivityTime(long j) {
        this.mLastActivityTime = j;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    boolean isUsingWrapper() {
        return this.mUsingWrapper;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void setUsingWrapper(boolean z) {
        this.mUsingWrapper = z;
        this.mWindowProcessController.setUsingWrapper(z);
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    int getLruSeq() {
        return this.mLruSeq;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void setLruSeq(int i) {
        this.mLruSeq = i;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    java.lang.String getIsolatedEntryPoint() {
        return this.mIsolatedEntryPoint;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void setIsolatedEntryPoint(java.lang.String str) {
        this.mIsolatedEntryPoint = str;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    java.lang.String[] getIsolatedEntryPointArgs() {
        return this.mIsolatedEntryPointArgs;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void setIsolatedEntryPointArgs(java.lang.String[] strArr) {
        this.mIsolatedEntryPointArgs = strArr;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    boolean isInFullBackup() {
        return this.mInFullBackup;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void setInFullBackup(boolean z) {
        this.mInFullBackup = z;
    }

    @Override // com.android.server.wm.WindowProcessListener
    @com.android.internal.annotations.GuardedBy({"mService"})
    public boolean isCached() {
        return this.mState.isCached();
    }

    boolean hasActivities() {
        return this.mWindowProcessController.hasActivities();
    }

    boolean hasActivitiesOrRecentTasks() {
        return this.mWindowProcessController.hasActivitiesOrRecentTasks();
    }

    boolean hasRecentTasks() {
        return this.mWindowProcessController.hasRecentTasks();
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    public android.content.pm.ApplicationInfo getApplicationInfo() {
        return this.info;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    boolean onCleanupApplicationRecordLSP(com.android.server.am.ProcessStatsService processStatsService, boolean z, boolean z2) {
        this.mErrorState.onCleanupApplicationRecordLSP();
        resetPackageList(processStatsService);
        if (z2) {
            unlinkDeathRecipient();
        }
        makeInactive(processStatsService);
        setWaitingToKill(null);
        this.mState.onCleanupApplicationRecordLSP();
        this.mServices.onCleanupApplicationRecordLocked();
        this.mReceivers.onCleanupApplicationRecordLocked();
        this.mService.mOomAdjuster.onProcessEndLocked(this);
        return this.mProviders.onCleanupApplicationRecordLocked(z);
    }

    public boolean isInterestingToUserLocked() {
        if (this.mWindowProcessController.isInterestingToUser()) {
            return true;
        }
        return this.mServices.hasForegroundServices();
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void scheduleCrashLocked(java.lang.String str, int i, @android.annotation.Nullable android.os.Bundle bundle) {
        if (!this.mKilledByAm && this.mThread != null) {
            if (this.mPid == android.os.Process.myPid()) {
                android.util.Slog.w(TAG, "scheduleCrash: trying to crash system process!");
                return;
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                try {
                    this.mThread.scheduleCrash(str, i, bundle);
                } catch (android.os.RemoteException e) {
                    killLocked("scheduleCrash for '" + str + "' failed", 4, true);
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public long getRss(int i) {
        long[] rss = android.os.Process.getRss(i);
        if (rss == null || rss.length <= 0) {
            return 0L;
        }
        return rss[0];
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void killLocked(java.lang.String str, int i, boolean z) {
        killLocked(str, i, 0, z, true);
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void killLocked(java.lang.String str, int i, int i2, boolean z) {
        killLocked(str, str, i, i2, z, true);
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void killLocked(java.lang.String str, java.lang.String str2, int i, int i2, boolean z) {
        killLocked(str, str2, i, i2, z, true);
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void killLocked(java.lang.String str, int i, int i2, boolean z, boolean z2) {
        killLocked(str, str, i, i2, z, z2);
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void killLocked(java.lang.String str, java.lang.String str2, int i, int i2, boolean z, boolean z2) {
        if (!this.mKilledByAm) {
            android.os.Trace.traceBegin(64L, "kill");
            if (i == 6 && this.mErrorState.getAnrAnnotation() != null) {
                str2 = str2 + ": " + this.mErrorState.getAnrAnnotation();
            }
            if (this.mService != null && (z || this.info.uid == this.mService.mCurOomAdjUid)) {
                this.mService.reportUidInfoMessageLocked(TAG, "Killing " + toShortString() + " (adj " + this.mState.getSetAdj() + "): " + str, this.info.uid);
            }
            this.mOptRecord.setPendingFreeze(false);
            this.mOptRecord.setFrozen(false);
            if (this.mPid > 0) {
                this.mService.mProcessList.noteAppKill(this, i, i2, str2);
                android.util.EventLog.writeEvent(com.android.server.am.EventLogTags.AM_KILL, java.lang.Integer.valueOf(this.userId), java.lang.Integer.valueOf(this.mPid), this.processName, java.lang.Integer.valueOf(this.mState.getSetAdj()), str, java.lang.Long.valueOf(getRss(this.mPid)));
                android.os.Process.killProcessQuiet(this.mPid);
                killProcessGroupIfNecessaryLocked(z2);
            } else {
                this.mPendingStart = false;
            }
            if (!this.mPersistent) {
                com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
                com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
                synchronized (activityManagerGlobalLock) {
                    try {
                        this.mKilled = true;
                        this.mKilledByAm = true;
                        this.mKillTime = android.os.SystemClock.uptimeMillis();
                    } catch (java.lang.Throwable th) {
                        com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                        throw th;
                    }
                }
                com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
            }
            android.os.Trace.traceEnd(64L);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void killProcessGroupIfNecessaryLocked(boolean z) {
        boolean z2;
        boolean z3 = true;
        if (this.mHostingRecord != null && (this.mHostingRecord.usesWebviewZygote() || this.mHostingRecord.usesAppZygote())) {
            synchronized (this) {
                try {
                    z2 = this.mProcessGroupCreated;
                    if (!z2) {
                        this.mSkipProcessGroupCreation = true;
                    }
                } finally {
                }
            }
            z3 = z2;
        }
        if (z3) {
            if (!z) {
                android.os.Process.sendSignalToProcessGroup(this.uid, this.mPid, android.system.OsConstants.SIGKILL);
            }
            com.android.server.am.ProcessList.killProcessGroup(this.uid, this.mPid);
        }
    }

    @Override // com.android.server.wm.WindowProcessListener
    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        dumpDebug(protoOutputStream, j, -1);
    }

    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j, int i) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1120986464257L, this.mPid);
        protoOutputStream.write(1138166333442L, this.processName);
        protoOutputStream.write(1120986464259L, this.info.uid);
        if (android.os.UserHandle.getAppId(this.info.uid) >= 10000) {
            protoOutputStream.write(1120986464260L, this.userId);
            protoOutputStream.write(1120986464261L, android.os.UserHandle.getAppId(this.info.uid));
        }
        if (this.uid != this.info.uid) {
            protoOutputStream.write(1120986464262L, android.os.UserHandle.getAppId(this.uid));
        }
        protoOutputStream.write(1133871366151L, this.mPersistent);
        if (i >= 0) {
            protoOutputStream.write(1120986464264L, i);
        }
        protoOutputStream.end(start);
    }

    public java.lang.String toShortString() {
        java.lang.String str = this.mShortStringName;
        if (str != null) {
            return str;
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
        toShortString(sb);
        java.lang.String sb2 = sb.toString();
        this.mShortStringName = sb2;
        return sb2;
    }

    void toShortString(java.lang.StringBuilder sb) {
        sb.append(this.mPid);
        sb.append(':');
        sb.append(this.processName);
        sb.append('/');
        if (this.info.uid < 10000) {
            sb.append(this.uid);
            return;
        }
        sb.append('u');
        sb.append(this.userId);
        int appId = android.os.UserHandle.getAppId(this.info.uid);
        if (appId >= 10000) {
            sb.append('a');
            sb.append(appId - 10000);
        } else {
            sb.append('s');
            sb.append(appId);
        }
        if (this.uid != this.info.uid) {
            sb.append('i');
            sb.append(android.os.UserHandle.getAppId(this.uid) - 99000);
        }
    }

    public java.lang.String toString() {
        java.lang.String str = this.mStringName;
        if (str != null) {
            return str;
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
        sb.append("ProcessRecord{");
        sb.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
        sb.append(' ');
        toShortString(sb);
        sb.append('}');
        java.lang.String sb2 = sb.toString();
        this.mStringName = sb2;
        return sb2;
    }

    public boolean addPackage(java.lang.String str, long j, com.android.server.am.ProcessStatsService processStatsService) {
        synchronized (processStatsService.mLock) {
            synchronized (this.mPkgList) {
                try {
                    if (!this.mPkgList.containsKey(str)) {
                        com.android.internal.app.procstats.ProcessStats.ProcessStateHolder processStateHolder = new com.android.internal.app.procstats.ProcessStats.ProcessStateHolder(j);
                        com.android.internal.app.procstats.ProcessState baseProcessTracker = this.mProfile.getBaseProcessTracker();
                        if (baseProcessTracker != null) {
                            processStatsService.updateProcessStateHolderLocked(processStateHolder, str, this.info.uid, j, this.processName);
                            this.mPkgList.put(str, processStateHolder);
                            if (processStateHolder.state != baseProcessTracker) {
                                processStateHolder.state.makeActive();
                            }
                        } else {
                            this.mPkgList.put(str, processStateHolder);
                        }
                        return true;
                    }
                    return false;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    void onProcessFrozen() {
        this.mProfile.onProcessFrozen();
    }

    void onProcessUnfrozen() {
        this.mProfile.onProcessUnfrozen();
        this.mServices.onProcessUnfrozen();
    }

    void onProcessFrozenCancelled() {
        this.mServices.onProcessFrozenCancelled();
    }

    public void resetPackageList(com.android.server.am.ProcessStatsService processStatsService) {
        com.android.server.am.PackageList packageList;
        synchronized (processStatsService.mLock) {
            final com.android.internal.app.procstats.ProcessState baseProcessTracker = this.mProfile.getBaseProcessTracker();
            com.android.server.am.PackageList packageList2 = this.mPkgList;
            try {
                synchronized (packageList2) {
                    try {
                        int size = this.mPkgList.size();
                        if (baseProcessTracker == null) {
                            packageList = packageList2;
                            if (size != 1) {
                                this.mPkgList.clear();
                                this.mPkgList.put(this.info.packageName, new com.android.internal.app.procstats.ProcessStats.ProcessStateHolder(this.info.longVersionCode));
                            }
                        } else {
                            baseProcessTracker.setState(-1, processStatsService.getMemFactorLocked(), android.os.SystemClock.uptimeMillis(), this.mPkgList.getPackageListLocked());
                            if (size == 1) {
                                packageList = packageList2;
                            } else {
                                this.mPkgList.forEachPackageProcessStats(new java.util.function.Consumer() { // from class: com.android.server.am.ProcessRecord$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Consumer
                                    public final void accept(java.lang.Object obj) {
                                        com.android.server.am.ProcessRecord.lambda$resetPackageList$0(baseProcessTracker, (com.android.internal.app.procstats.ProcessStats.ProcessStateHolder) obj);
                                    }
                                });
                                this.mPkgList.clear();
                                com.android.internal.app.procstats.ProcessStats.ProcessStateHolder processStateHolder = new com.android.internal.app.procstats.ProcessStats.ProcessStateHolder(this.info.longVersionCode);
                                packageList = packageList2;
                                processStatsService.updateProcessStateHolderLocked(processStateHolder, this.info.packageName, this.info.uid, this.info.longVersionCode, this.processName);
                                this.mPkgList.put(this.info.packageName, processStateHolder);
                                if (processStateHolder.state != baseProcessTracker) {
                                    processStateHolder.state.makeActive();
                                }
                            }
                        }
                    } catch (java.lang.Throwable th) {
                        th = th;
                        throw th;
                    }
                }
            } catch (java.lang.Throwable th2) {
                th = th2;
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$resetPackageList$0(com.android.internal.app.procstats.ProcessState processState, com.android.internal.app.procstats.ProcessStats.ProcessStateHolder processStateHolder) {
        if (processStateHolder.state != null && processStateHolder.state != processState) {
            processStateHolder.state.makeInactive();
        }
    }

    java.lang.String[] getPackageList() {
        return this.mPkgList.getPackageList();
    }

    java.util.List<android.content.pm.VersionedPackage> getPackageListWithVersionCode() {
        return this.mPkgList.getPackageListWithVersionCode();
    }

    com.android.server.wm.WindowProcessController getWindowProcessController() {
        return this.mWindowProcessController;
    }

    void addOrUpdateBackgroundStartPrivileges(@android.annotation.NonNull android.os.Binder binder, @android.annotation.NonNull android.app.BackgroundStartPrivileges backgroundStartPrivileges) {
        java.util.Objects.requireNonNull(binder, "entity");
        java.util.Objects.requireNonNull(backgroundStartPrivileges, "backgroundStartPrivileges");
        com.android.internal.util.Preconditions.checkArgument(backgroundStartPrivileges.allowsAny(), "backgroundStartPrivileges does not allow anything");
        this.mWindowProcessController.addOrUpdateBackgroundStartPrivileges(binder, backgroundStartPrivileges);
        setBackgroundStartPrivileges(binder, backgroundStartPrivileges);
    }

    void removeBackgroundStartPrivileges(@android.annotation.NonNull android.os.Binder binder) {
        java.util.Objects.requireNonNull(binder, "entity");
        this.mWindowProcessController.removeBackgroundStartPrivileges(binder);
        setBackgroundStartPrivileges(binder, null);
    }

    @android.annotation.NonNull
    android.app.BackgroundStartPrivileges getBackgroundStartPrivileges() {
        android.app.BackgroundStartPrivileges backgroundStartPrivileges;
        synchronized (this.mBackgroundStartPrivileges) {
            try {
                if (this.mBackgroundStartPrivilegesMerged == null) {
                    this.mBackgroundStartPrivilegesMerged = android.app.BackgroundStartPrivileges.NONE;
                    for (int size = this.mBackgroundStartPrivileges.size() - 1; size >= 0; size--) {
                        this.mBackgroundStartPrivilegesMerged = this.mBackgroundStartPrivilegesMerged.merge(this.mBackgroundStartPrivileges.valueAt(size));
                    }
                }
                backgroundStartPrivileges = this.mBackgroundStartPrivilegesMerged;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return backgroundStartPrivileges;
    }

    private void setBackgroundStartPrivileges(@android.annotation.NonNull android.os.Binder binder, @android.annotation.Nullable android.app.BackgroundStartPrivileges backgroundStartPrivileges) {
        synchronized (this.mBackgroundStartPrivileges) {
            boolean z = false;
            try {
                if (backgroundStartPrivileges == null) {
                    if (this.mBackgroundStartPrivileges.remove(binder) != null) {
                        z = true;
                    }
                } else if (backgroundStartPrivileges != this.mBackgroundStartPrivileges.put(binder, backgroundStartPrivileges)) {
                    z = true;
                }
                if (z) {
                    this.mBackgroundStartPrivilegesMerged = null;
                }
            } finally {
            }
        }
    }

    @Override // com.android.server.wm.WindowProcessListener
    public void clearProfilerIfNeeded() {
        synchronized (this.mService.mAppProfiler.mProfilerLock) {
            this.mService.mAppProfiler.clearProfilerLPf();
        }
    }

    @Override // com.android.server.wm.WindowProcessListener
    public void updateServiceConnectionActivities() {
        com.android.server.am.ActivityManagerService activityManagerService = this.mService;
        com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                this.mService.mServices.updateServiceConnectionActivitiesLocked(this.mServices);
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
    }

    @Override // com.android.server.wm.WindowProcessListener
    public void setPendingUiClean(boolean z) {
        com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
        com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
        synchronized (activityManagerGlobalLock) {
            try {
                this.mProfile.setPendingUiClean(z);
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                throw th;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
    }

    @Override // com.android.server.wm.WindowProcessListener
    public void setPendingUiCleanAndForceProcessStateUpTo(int i) {
        com.android.server.am.ActivityManagerService activityManagerService = this.mService;
        com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                setPendingUiClean(true);
                this.mState.forceProcessStateUpTo(i);
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
    }

    @Override // com.android.server.wm.WindowProcessListener
    public void updateProcessInfo(boolean z, boolean z2, boolean z3) {
        com.android.server.am.ActivityManagerService activityManagerService = this.mService;
        com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            if (z) {
                try {
                    this.mService.mServices.updateServiceConnectionActivitiesLocked(this.mServices);
                } catch (java.lang.Throwable th) {
                    com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            if (this.mThread == null) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                return;
            }
            this.mService.updateLruProcessLocked(this, z2, null);
            if (z3) {
                this.mService.updateOomAdjLocked(this, 1);
            }
            com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
        }
    }

    @Override // com.android.server.wm.WindowProcessListener
    public long getCpuTime() {
        return this.mService.mAppProfiler.getCpuTimeForPid(this.mPid);
    }

    public long getCpuDelayTime() {
        return this.mService.mAppProfiler.getCpuDelayTimeForPid(this.mPid);
    }

    @Override // com.android.server.wm.WindowProcessListener
    public void onStartActivity(int i, boolean z, java.lang.String str, long j) {
        com.android.server.am.ActivityManagerService activityManagerService = this.mService;
        com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                this.mWaitingToKill = null;
                if (z) {
                    synchronized (this.mService.mAppProfiler.mProfilerLock) {
                        this.mService.mAppProfiler.setProfileProcLPf(this);
                    }
                }
                if (str != null) {
                    addPackage(str, j, this.mService.mProcessStats);
                }
                updateProcessInfo(false, true, true);
                setPendingUiClean(true);
                this.mState.setHasShownUi(true);
                this.mState.forceProcessStateUpTo(i);
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
    }

    @Override // com.android.server.wm.WindowProcessListener
    public void appDied(java.lang.String str) {
        com.android.server.am.ActivityManagerService activityManagerService = this.mService;
        com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                this.mService.appDiedLocked(this, str);
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
    }

    @Override // com.android.server.wm.WindowProcessListener
    public void setRunningRemoteAnimation(boolean z) {
        if (this.mPid == android.os.Process.myPid()) {
            android.util.Slog.wtf(TAG, "system can't run remote animation");
            return;
        }
        com.android.server.am.ActivityManagerService activityManagerService = this.mService;
        com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                this.mState.setRunningRemoteAnimation(z);
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
    }

    public long getInputDispatchingTimeoutMillis() {
        return this.mWindowProcessController.getInputDispatchingTimeoutMillis();
    }

    public int getProcessClassEnum() {
        if (this.mPid == com.android.server.am.ActivityManagerService.MY_PID) {
            return 3;
        }
        if (this.info == null) {
            return 0;
        }
        return (this.info.flags & 1) != 0 ? 2 : 1;
    }

    @com.android.internal.annotations.VisibleForTesting
    java.util.List<com.android.server.am.ProcessRecord> getLruProcessList() {
        return this.mService.mProcessList.getLruProcessesLOSP();
    }

    public void setWasForceStopped(boolean z) {
        this.mWasForceStopped = z;
    }

    public boolean wasForceStopped() {
        return this.mWasForceStopped;
    }

    boolean isFreezable() {
        return this.mService.mOomAdjuster.mCachedAppOptimizer.useFreezer() && !this.mOptRecord.isFreezeExempt() && !this.mOptRecord.shouldNotFreeze() && this.mState.getCurAdj() >= 900;
    }

    @com.android.internal.annotations.GuardedBy({"mProcLock"})
    void forEachClient(@android.annotation.NonNull java.util.function.Consumer<com.android.server.am.ProcessRecord> consumer) {
        for (int numberOfRunningServices = this.mServices.numberOfRunningServices() - 1; numberOfRunningServices >= 0; numberOfRunningServices--) {
            android.util.ArrayMap<android.os.IBinder, java.util.ArrayList<com.android.server.am.ConnectionRecord>> connections = this.mServices.getRunningServiceAt(numberOfRunningServices).getConnections();
            for (int size = connections.size() - 1; size >= 0; size--) {
                java.util.ArrayList<com.android.server.am.ConnectionRecord> valueAt = connections.valueAt(size);
                for (int size2 = valueAt.size() - 1; size2 >= 0; size2--) {
                    com.android.server.am.ConnectionRecord connectionRecord = valueAt.get(size2);
                    if (this.isSdkSandbox && connectionRecord.binding.attributedClient != null) {
                        consumer.accept(connectionRecord.binding.attributedClient);
                    } else {
                        consumer.accept(connectionRecord.binding.client);
                    }
                }
            }
        }
        for (int numberOfProviders = this.mProviders.numberOfProviders() - 1; numberOfProviders >= 0; numberOfProviders--) {
            com.android.server.am.ContentProviderRecord providerAt = this.mProviders.getProviderAt(numberOfProviders);
            for (int size3 = providerAt.connections.size() - 1; size3 >= 0; size3--) {
                consumer.accept(providerAt.connections.get(size3).client);
            }
        }
    }
}
