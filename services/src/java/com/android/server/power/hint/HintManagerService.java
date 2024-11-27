package com.android.server.power.hint;

/* loaded from: classes2.dex */
public final class HintManagerService extends com.android.server.SystemService {
    private static final boolean DEBUG = false;
    private static final java.lang.String PROPERTY_HWUI_ENABLE_HINT_MANAGER = "debug.hwui.use_hint_manager";
    private static final java.lang.String PROPERTY_SF_ENABLE_CPU_HINT = "debug.sf.enable_adpf_cpu_hint";
    private static final java.lang.String TAG = "HintManagerService";

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArrayMap<java.lang.Integer, android.util.ArrayMap<android.os.IBinder, android.util.ArraySet<com.android.server.power.hint.HintManagerService.AppHintSession>>> mActiveSessions;
    private final android.app.ActivityManagerInternal mAmInternal;
    private final android.content.Context mContext;

    @com.android.internal.annotations.VisibleForTesting
    final long mHintSessionPreferredRate;
    private final java.lang.Object mLock;
    private final com.android.server.power.hint.HintManagerService.NativeWrapper mNativeWrapper;

    @com.android.internal.annotations.VisibleForTesting
    final android.os.IHintManager.Stub mService;

    @com.android.internal.annotations.VisibleForTesting
    final com.android.server.power.hint.HintManagerService.MyUidObserver mUidObserver;

    public HintManagerService(android.content.Context context) {
        this(context, new com.android.server.power.hint.HintManagerService.Injector());
    }

    @com.android.internal.annotations.VisibleForTesting
    HintManagerService(android.content.Context context, com.android.server.power.hint.HintManagerService.Injector injector) {
        super(context);
        this.mLock = new java.lang.Object();
        this.mService = new com.android.server.power.hint.HintManagerService.BinderService();
        this.mContext = context;
        this.mActiveSessions = new android.util.ArrayMap<>();
        this.mNativeWrapper = injector.createNativeWrapper();
        this.mNativeWrapper.halInit();
        this.mHintSessionPreferredRate = this.mNativeWrapper.halGetHintSessionPreferredRate();
        this.mUidObserver = new com.android.server.power.hint.HintManagerService.MyUidObserver();
        android.app.ActivityManagerInternal activityManagerInternal = (android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class);
        java.util.Objects.requireNonNull(activityManagerInternal);
        this.mAmInternal = activityManagerInternal;
    }

    @com.android.internal.annotations.VisibleForTesting
    static class Injector {
        Injector() {
        }

        com.android.server.power.hint.HintManagerService.NativeWrapper createNativeWrapper() {
            return new com.android.server.power.hint.HintManagerService.NativeWrapper();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isHalSupported() {
        return this.mHintSessionPreferredRate != -1;
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("performance_hint", this.mService);
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        if (i == 500) {
            systemReady();
        }
        if (i == 1000) {
            registerStatsCallbacks();
        }
    }

    private void systemReady() {
        com.android.server.utils.Slogf.v(TAG, "Initializing HintManager service...");
        try {
            android.app.ActivityManager.getService().registerUidObserver(this.mUidObserver, 3, -1, (java.lang.String) null);
        } catch (android.os.RemoteException e) {
        }
    }

    private void registerStatsCallbacks() {
        ((android.app.StatsManager) this.mContext.getSystemService(android.app.StatsManager.class)).setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.ADPF_SYSTEM_COMPONENT_INFO, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, new android.app.StatsManager.StatsPullAtomCallback() { // from class: com.android.server.power.hint.HintManagerService$$ExternalSyntheticLambda0
            public final int onPullAtom(int i, java.util.List list) {
                int onPullAtom;
                onPullAtom = com.android.server.power.hint.HintManagerService.this.onPullAtom(i, list);
                return onPullAtom;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int onPullAtom(int i, @android.annotation.NonNull java.util.List<android.util.StatsEvent> list) {
        if (i == 10173) {
            list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(com.android.internal.util.FrameworkStatsLog.ADPF_SYSTEM_COMPONENT_INFO, android.os.SystemProperties.getBoolean(PROPERTY_SF_ENABLE_CPU_HINT, false), android.os.SystemProperties.getBoolean(PROPERTY_HWUI_ENABLE_HINT_MANAGER, false)));
        }
        return 0;
    }

    @com.android.internal.annotations.VisibleForTesting
    public static class NativeWrapper {
        private static native void nativeCloseHintSession(long j);

        private static native long nativeCreateHintSession(int i, int i2, int[] iArr, long j);

        private static native long nativeGetHintSessionPreferredRate();

        private native void nativeInit();

        private static native void nativePauseHintSession(long j);

        private static native void nativeReportActualWorkDuration(long j, long[] jArr, long[] jArr2);

        private static native void nativeReportActualWorkDuration(long j, android.os.WorkDuration[] workDurationArr);

        private static native void nativeResumeHintSession(long j);

        private static native void nativeSendHint(long j, int i);

        private static native void nativeSetMode(long j, int i, boolean z);

        private static native void nativeSetThreads(long j, int[] iArr);

        private static native void nativeUpdateTargetWorkDuration(long j, long j2);

        public void halInit() {
            nativeInit();
        }

        public long halGetHintSessionPreferredRate() {
            return nativeGetHintSessionPreferredRate();
        }

        public long halCreateHintSession(int i, int i2, int[] iArr, long j) {
            return nativeCreateHintSession(i, i2, iArr, j);
        }

        public void halPauseHintSession(long j) {
            nativePauseHintSession(j);
        }

        public void halResumeHintSession(long j) {
            nativeResumeHintSession(j);
        }

        public void halCloseHintSession(long j) {
            nativeCloseHintSession(j);
        }

        public void halUpdateTargetWorkDuration(long j, long j2) {
            nativeUpdateTargetWorkDuration(j, j2);
        }

        public void halReportActualWorkDuration(long j, long[] jArr, long[] jArr2) {
            nativeReportActualWorkDuration(j, jArr, jArr2);
        }

        public void halSendHint(long j, int i) {
            nativeSendHint(j, i);
        }

        public void halSetThreads(long j, int[] iArr) {
            nativeSetThreads(j, iArr);
        }

        public void halSetMode(long j, int i, boolean z) {
            nativeSetMode(j, i, z);
        }

        public void halReportActualWorkDuration(long j, android.os.WorkDuration[] workDurationArr) {
            nativeReportActualWorkDuration(j, workDurationArr);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    final class MyUidObserver extends android.app.UidObserver {
        private final java.lang.Object mCacheLock = new java.lang.Object();

        @com.android.internal.annotations.GuardedBy({"mCacheLock"})
        private final android.util.SparseIntArray mProcStatesCache = new android.util.SparseIntArray();

        MyUidObserver() {
        }

        public boolean isUidForeground(int i) {
            boolean z;
            synchronized (this.mCacheLock) {
                z = this.mProcStatesCache.get(i, 6) <= 6;
            }
            return z;
        }

        public void onUidGone(final int i, boolean z) {
            com.android.server.FgThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.power.hint.HintManagerService$MyUidObserver$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.power.hint.HintManagerService.MyUidObserver.this.lambda$onUidGone$0(i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onUidGone$0(int i) {
            synchronized (this.mCacheLock) {
                this.mProcStatesCache.delete(i);
            }
            synchronized (com.android.server.power.hint.HintManagerService.this.mLock) {
                try {
                    android.util.ArrayMap arrayMap = (android.util.ArrayMap) com.android.server.power.hint.HintManagerService.this.mActiveSessions.get(java.lang.Integer.valueOf(i));
                    if (arrayMap == null) {
                        return;
                    }
                    for (int size = arrayMap.size() - 1; size >= 0; size--) {
                        android.util.ArraySet arraySet = (android.util.ArraySet) arrayMap.valueAt(size);
                        for (int size2 = arraySet.size() - 1; size2 >= 0; size2--) {
                            ((com.android.server.power.hint.HintManagerService.AppHintSession) arraySet.valueAt(size2)).close();
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void onUidStateChanged(final int i, final int i2, long j, int i3) {
            com.android.server.FgThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.power.hint.HintManagerService$MyUidObserver$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.power.hint.HintManagerService.MyUidObserver.this.lambda$onUidStateChanged$1(i, i2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onUidStateChanged$1(int i, int i2) {
            synchronized (this.mCacheLock) {
                this.mProcStatesCache.put(i, i2);
            }
            boolean isUidForeground = isUidForeground(i);
            synchronized (com.android.server.power.hint.HintManagerService.this.mLock) {
                try {
                    android.util.ArrayMap arrayMap = (android.util.ArrayMap) com.android.server.power.hint.HintManagerService.this.mActiveSessions.get(java.lang.Integer.valueOf(i));
                    if (arrayMap == null) {
                        return;
                    }
                    java.util.Iterator it = arrayMap.values().iterator();
                    while (it.hasNext()) {
                        java.util.Iterator it2 = ((android.util.ArraySet) it.next()).iterator();
                        while (it2.hasNext()) {
                            ((com.android.server.power.hint.HintManagerService.AppHintSession) it2.next()).onProcStateChanged(isUidForeground);
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    android.os.IHintManager.Stub getBinderServiceInstance() {
        return this.mService;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.lang.Integer checkTidValid(int i, int i2, int[] iArr) {
        java.util.List list = null;
        for (int i3 : iArr) {
            long[] jArr = new long[2];
            android.os.Process.readProcLines("/proc/" + i3 + "/status", new java.lang.String[]{"Uid:", "Tgid:"}, jArr);
            int i4 = (int) jArr[0];
            int i5 = (int) jArr[1];
            if (i5 != i2 && i4 != i) {
                if (list == null) {
                    if (i == 1000) {
                        return java.lang.Integer.valueOf(i3);
                    }
                    list = this.mAmInternal.getIsolatedProcesses(i);
                    if (list == null) {
                        return java.lang.Integer.valueOf(i3);
                    }
                }
                if (!list.contains(java.lang.Integer.valueOf(i5))) {
                    return java.lang.Integer.valueOf(i3);
                }
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.lang.String formatTidCheckErrMsg(int i, int[] iArr, java.lang.Integer num) {
        return "Tid" + num + " from list " + java.util.Arrays.toString(iArr) + " doesn't belong to the calling application" + i;
    }

    @com.android.internal.annotations.VisibleForTesting
    final class BinderService extends android.os.IHintManager.Stub {
        BinderService() {
        }

        public android.os.IHintSession createHintSession(android.os.IBinder iBinder, int[] iArr, long j) {
            if (!com.android.server.power.hint.HintManagerService.this.isHalSupported()) {
                return null;
            }
            java.util.Objects.requireNonNull(iBinder);
            java.util.Objects.requireNonNull(iArr);
            com.android.internal.util.Preconditions.checkArgument(iArr.length != 0, "tids should not be empty.");
            int callingUid = android.os.Binder.getCallingUid();
            int threadGroupLeader = android.os.Process.getThreadGroupLeader(android.os.Binder.getCallingPid());
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.lang.Integer checkTidValid = com.android.server.power.hint.HintManagerService.this.checkTidValid(callingUid, threadGroupLeader, iArr);
                if (checkTidValid != null) {
                    java.lang.String formatTidCheckErrMsg = com.android.server.power.hint.HintManagerService.this.formatTidCheckErrMsg(callingUid, iArr, checkTidValid);
                    com.android.server.utils.Slogf.w(com.android.server.power.hint.HintManagerService.TAG, formatTidCheckErrMsg);
                    throw new java.lang.SecurityException(formatTidCheckErrMsg);
                }
                long halCreateHintSession = com.android.server.power.hint.HintManagerService.this.mNativeWrapper.halCreateHintSession(threadGroupLeader, callingUid, iArr, j);
                if (halCreateHintSession == 0) {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    return null;
                }
                com.android.server.power.hint.HintManagerService.AppHintSession appHintSession = com.android.server.power.hint.HintManagerService.this.new AppHintSession(callingUid, threadGroupLeader, iArr, iBinder, halCreateHintSession, j);
                logPerformanceHintSessionAtom(callingUid, halCreateHintSession, j, iArr);
                synchronized (com.android.server.power.hint.HintManagerService.this.mLock) {
                    try {
                        android.util.ArrayMap arrayMap = (android.util.ArrayMap) com.android.server.power.hint.HintManagerService.this.mActiveSessions.get(java.lang.Integer.valueOf(callingUid));
                        if (arrayMap == null) {
                            arrayMap = new android.util.ArrayMap(1);
                            com.android.server.power.hint.HintManagerService.this.mActiveSessions.put(java.lang.Integer.valueOf(callingUid), arrayMap);
                        }
                        android.util.ArraySet arraySet = (android.util.ArraySet) arrayMap.get(iBinder);
                        if (arraySet == null) {
                            arraySet = new android.util.ArraySet(1);
                            arrayMap.put(iBinder, arraySet);
                        }
                        arraySet.add(appHintSession);
                    } finally {
                    }
                }
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return appHintSession;
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public long getHintSessionPreferredRate() {
            return com.android.server.power.hint.HintManagerService.this.mHintSessionPreferredRate;
        }

        public void setHintSessionThreads(@android.annotation.NonNull android.os.IHintSession iHintSession, @android.annotation.NonNull int[] iArr) {
            ((com.android.server.power.hint.HintManagerService.AppHintSession) iHintSession).setThreads(iArr);
        }

        public int[] getHintSessionThreadIds(@android.annotation.NonNull android.os.IHintSession iHintSession) {
            return ((com.android.server.power.hint.HintManagerService.AppHintSession) iHintSession).getThreadIds();
        }

        public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            if (!com.android.internal.util.DumpUtils.checkDumpPermission(com.android.server.power.hint.HintManagerService.this.getContext(), com.android.server.power.hint.HintManagerService.TAG, printWriter)) {
                return;
            }
            printWriter.println("HintSessionPreferredRate: " + com.android.server.power.hint.HintManagerService.this.mHintSessionPreferredRate);
            printWriter.println("HAL Support: " + com.android.server.power.hint.HintManagerService.this.isHalSupported());
            printWriter.println("Active Sessions:");
            synchronized (com.android.server.power.hint.HintManagerService.this.mLock) {
                for (int i = 0; i < com.android.server.power.hint.HintManagerService.this.mActiveSessions.size(); i++) {
                    try {
                        printWriter.println("Uid " + ((java.lang.Integer) com.android.server.power.hint.HintManagerService.this.mActiveSessions.keyAt(i)).toString() + ":");
                        android.util.ArrayMap arrayMap = (android.util.ArrayMap) com.android.server.power.hint.HintManagerService.this.mActiveSessions.valueAt(i);
                        for (int i2 = 0; i2 < arrayMap.size(); i2++) {
                            android.util.ArraySet arraySet = (android.util.ArraySet) arrayMap.valueAt(i2);
                            for (int i3 = 0; i3 < arraySet.size(); i3++) {
                                printWriter.println("  Session:");
                                ((com.android.server.power.hint.HintManagerService.AppHintSession) arraySet.valueAt(i3)).dump(printWriter, "    ");
                            }
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        }

        private void logPerformanceHintSessionAtom(int i, long j, long j2, int[] iArr) {
            com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.PERFORMANCE_HINT_SESSION_REPORTED, i, j, j2, iArr.length);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    final class AppHintSession extends android.os.IHintSession.Stub implements android.os.IBinder.DeathRecipient {
        protected long mHalSessionPtr;
        protected int[] mNewThreadIds;
        protected final int mPid;
        protected long mTargetDurationNanos;
        protected int[] mThreadIds;
        protected final android.os.IBinder mToken;
        protected final int mUid;
        protected boolean mUpdateAllowed = true;
        protected boolean mPowerEfficient = false;

        private enum SessionModes {
            POWER_EFFICIENCY
        }

        protected AppHintSession(int i, int i2, int[] iArr, android.os.IBinder iBinder, long j, long j2) {
            this.mUid = i;
            this.mPid = i2;
            this.mToken = iBinder;
            this.mThreadIds = iArr;
            this.mHalSessionPtr = j;
            this.mTargetDurationNanos = j2;
            updateHintAllowed(com.android.server.power.hint.HintManagerService.this.mUidObserver.isUidForeground(this.mUid));
            try {
                iBinder.linkToDeath(this, 0);
            } catch (android.os.RemoteException e) {
                com.android.server.power.hint.HintManagerService.this.mNativeWrapper.halCloseHintSession(this.mHalSessionPtr);
                throw new java.lang.IllegalStateException("Client already dead", e);
            }
        }

        @com.android.internal.annotations.VisibleForTesting
        boolean updateHintAllowed(boolean z) {
            boolean z2;
            synchronized (this) {
                if (z) {
                    try {
                        if (!this.mUpdateAllowed) {
                            resume();
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                if (!z && this.mUpdateAllowed) {
                    pause();
                }
                this.mUpdateAllowed = z;
                z2 = this.mUpdateAllowed;
            }
            return z2;
        }

        public void updateTargetWorkDuration(long j) {
            synchronized (this) {
                try {
                    if (this.mHalSessionPtr == 0 || !this.mUpdateAllowed) {
                        return;
                    }
                    com.android.internal.util.Preconditions.checkArgument(j > 0, "Expected the target duration to be greater than 0.");
                    com.android.server.power.hint.HintManagerService.this.mNativeWrapper.halUpdateTargetWorkDuration(this.mHalSessionPtr, j);
                    this.mTargetDurationNanos = j;
                } finally {
                }
            }
        }

        public void reportActualWorkDuration(long[] jArr, long[] jArr2) {
            synchronized (this) {
                try {
                    if (this.mHalSessionPtr == 0 || !this.mUpdateAllowed) {
                        return;
                    }
                    boolean z = true;
                    com.android.internal.util.Preconditions.checkArgument(jArr.length != 0, "the count of hint durations shouldn't be 0.");
                    if (jArr.length != jArr2.length) {
                        z = false;
                    }
                    com.android.internal.util.Preconditions.checkArgument(z, "The length of durations and timestamps should be the same.");
                    for (int i = 0; i < jArr.length; i++) {
                        if (jArr[i] <= 0) {
                            throw new java.lang.IllegalArgumentException(java.lang.String.format("durations[%d]=%d should be greater than 0", java.lang.Integer.valueOf(i), java.lang.Long.valueOf(jArr[i])));
                        }
                    }
                    com.android.server.power.hint.HintManagerService.this.mNativeWrapper.halReportActualWorkDuration(this.mHalSessionPtr, jArr, jArr2);
                } finally {
                }
            }
        }

        public void close() {
            synchronized (this) {
                try {
                    if (this.mHalSessionPtr == 0) {
                        return;
                    }
                    com.android.server.power.hint.HintManagerService.this.mNativeWrapper.halCloseHintSession(this.mHalSessionPtr);
                    this.mHalSessionPtr = 0L;
                    try {
                        this.mToken.unlinkToDeath(this, 0);
                    } catch (java.util.NoSuchElementException e) {
                        com.android.server.utils.Slogf.d(com.android.server.power.hint.HintManagerService.TAG, "Death link does not exist for session with UID " + this.mUid);
                    }
                    synchronized (com.android.server.power.hint.HintManagerService.this.mLock) {
                        try {
                            android.util.ArrayMap arrayMap = (android.util.ArrayMap) com.android.server.power.hint.HintManagerService.this.mActiveSessions.get(java.lang.Integer.valueOf(this.mUid));
                            if (arrayMap == null) {
                                com.android.server.utils.Slogf.w(com.android.server.power.hint.HintManagerService.TAG, "UID %d is not present in active session map", java.lang.Integer.valueOf(this.mUid));
                                return;
                            }
                            android.util.ArraySet arraySet = (android.util.ArraySet) arrayMap.get(this.mToken);
                            if (arraySet == null) {
                                com.android.server.utils.Slogf.w(com.android.server.power.hint.HintManagerService.TAG, "Token %s is not present in token map", this.mToken.toString());
                                return;
                            }
                            arraySet.remove(this);
                            if (arraySet.isEmpty()) {
                                arrayMap.remove(this.mToken);
                            }
                            if (arrayMap.isEmpty()) {
                                com.android.server.power.hint.HintManagerService.this.mActiveSessions.remove(java.lang.Integer.valueOf(this.mUid));
                            }
                        } finally {
                        }
                    }
                } finally {
                }
            }
        }

        public void sendHint(int i) {
            synchronized (this) {
                try {
                    if (this.mHalSessionPtr == 0 || !this.mUpdateAllowed) {
                        return;
                    }
                    com.android.internal.util.Preconditions.checkArgument(i >= 0, "the hint ID value should be greater than zero.");
                    com.android.server.power.hint.HintManagerService.this.mNativeWrapper.halSendHint(this.mHalSessionPtr, i);
                } finally {
                }
            }
        }

        public void setThreads(@android.annotation.NonNull int[] iArr) {
            synchronized (this) {
                try {
                    if (this.mHalSessionPtr == 0) {
                        return;
                    }
                    if (iArr.length == 0) {
                        throw new java.lang.IllegalArgumentException("Thread id list can't be empty.");
                    }
                    int callingUid = android.os.Binder.getCallingUid();
                    int threadGroupLeader = android.os.Process.getThreadGroupLeader(android.os.Binder.getCallingPid());
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        java.lang.Integer checkTidValid = com.android.server.power.hint.HintManagerService.this.checkTidValid(callingUid, threadGroupLeader, iArr);
                        if (checkTidValid != null) {
                            java.lang.String formatTidCheckErrMsg = com.android.server.power.hint.HintManagerService.this.formatTidCheckErrMsg(callingUid, iArr, checkTidValid);
                            com.android.server.utils.Slogf.w(com.android.server.power.hint.HintManagerService.TAG, formatTidCheckErrMsg);
                            throw new java.lang.SecurityException(formatTidCheckErrMsg);
                        }
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        if (!this.mUpdateAllowed) {
                            com.android.server.utils.Slogf.v(com.android.server.power.hint.HintManagerService.TAG, "update hint not allowed, storing tids.");
                            this.mNewThreadIds = iArr;
                        } else {
                            com.android.server.power.hint.HintManagerService.this.mNativeWrapper.halSetThreads(this.mHalSessionPtr, iArr);
                            this.mThreadIds = iArr;
                        }
                    } catch (java.lang.Throwable th) {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                } catch (java.lang.Throwable th2) {
                    throw th2;
                }
            }
        }

        public int[] getThreadIds() {
            int[] copyOf;
            synchronized (this) {
                copyOf = java.util.Arrays.copyOf(this.mThreadIds, this.mThreadIds.length);
            }
            return copyOf;
        }

        public void setMode(int i, boolean z) {
            synchronized (this) {
                try {
                    if (this.mHalSessionPtr == 0 || !this.mUpdateAllowed) {
                        return;
                    }
                    com.android.internal.util.Preconditions.checkArgument(i >= 0, "the mode Id value should be greater than zero.");
                    if (i == com.android.server.power.hint.HintManagerService.AppHintSession.SessionModes.POWER_EFFICIENCY.ordinal()) {
                        this.mPowerEfficient = z;
                    }
                    com.android.server.power.hint.HintManagerService.this.mNativeWrapper.halSetMode(this.mHalSessionPtr, i, z);
                } finally {
                }
            }
        }

        public void reportActualWorkDuration2(android.os.WorkDuration[] workDurationArr) {
            synchronized (this) {
                try {
                    if (this.mHalSessionPtr == 0 || !this.mUpdateAllowed) {
                        return;
                    }
                    com.android.internal.util.Preconditions.checkArgument(workDurationArr.length != 0, "the count of work durations shouldn't be 0.");
                    for (android.os.WorkDuration workDuration : workDurationArr) {
                        validateWorkDuration(workDuration);
                    }
                    com.android.server.power.hint.HintManagerService.this.mNativeWrapper.halReportActualWorkDuration(this.mHalSessionPtr, workDurationArr);
                } finally {
                }
            }
        }

        public boolean isPowerEfficient() {
            boolean z;
            synchronized (this) {
                z = this.mPowerEfficient;
            }
            return z;
        }

        void validateWorkDuration(android.os.WorkDuration workDuration) {
            if (workDuration.getWorkPeriodStartTimestampNanos() < 0) {
                throw new java.lang.IllegalArgumentException(android.text.TextUtils.formatSimple("Work period start timestamp (%d) should be greater than 0", new java.lang.Object[]{java.lang.Long.valueOf(workDuration.getWorkPeriodStartTimestampNanos())}));
            }
            if (workDuration.getActualTotalDurationNanos() <= 0) {
                throw new java.lang.IllegalArgumentException(android.text.TextUtils.formatSimple("Actual total duration (%d) should be greater than 0", new java.lang.Object[]{java.lang.Long.valueOf(workDuration.getActualTotalDurationNanos())}));
            }
            if (workDuration.getActualCpuDurationNanos() < 0) {
                throw new java.lang.IllegalArgumentException(android.text.TextUtils.formatSimple("Actual CPU duration (%d) should be greater than or equal to 0", new java.lang.Object[]{java.lang.Long.valueOf(workDuration.getActualCpuDurationNanos())}));
            }
            if (workDuration.getActualGpuDurationNanos() < 0) {
                throw new java.lang.IllegalArgumentException(android.text.TextUtils.formatSimple("Actual GPU duration (%d) should greater than or equal to 0", new java.lang.Object[]{java.lang.Long.valueOf(workDuration.getActualGpuDurationNanos())}));
            }
            if (workDuration.getActualCpuDurationNanos() + workDuration.getActualGpuDurationNanos() <= 0) {
                throw new java.lang.IllegalArgumentException(android.text.TextUtils.formatSimple("The actual CPU duration (%d) and the actual GPU duration (%d) should not both be 0", new java.lang.Object[]{java.lang.Long.valueOf(workDuration.getActualCpuDurationNanos()), java.lang.Long.valueOf(workDuration.getActualGpuDurationNanos())}));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onProcStateChanged(boolean z) {
            updateHintAllowed(z);
        }

        private void pause() {
            synchronized (this) {
                try {
                    if (this.mHalSessionPtr == 0) {
                        return;
                    }
                    com.android.server.power.hint.HintManagerService.this.mNativeWrapper.halPauseHintSession(this.mHalSessionPtr);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        private void resume() {
            synchronized (this) {
                try {
                    if (this.mHalSessionPtr == 0) {
                        return;
                    }
                    com.android.server.power.hint.HintManagerService.this.mNativeWrapper.halResumeHintSession(this.mHalSessionPtr);
                    if (this.mNewThreadIds != null) {
                        com.android.server.power.hint.HintManagerService.this.mNativeWrapper.halSetThreads(this.mHalSessionPtr, this.mNewThreadIds);
                        this.mThreadIds = this.mNewThreadIds;
                        this.mNewThreadIds = null;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
            synchronized (this) {
                try {
                    printWriter.println(str + "SessionPID: " + this.mPid);
                    printWriter.println(str + "SessionUID: " + this.mUid);
                    printWriter.println(str + "SessionTIDs: " + java.util.Arrays.toString(this.mThreadIds));
                    printWriter.println(str + "SessionTargetDurationNanos: " + this.mTargetDurationNanos);
                    printWriter.println(str + "SessionAllowed: " + this.mUpdateAllowed);
                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                    sb.append(str);
                    sb.append("PowerEfficient: ");
                    sb.append(this.mPowerEfficient ? "true" : "false");
                    printWriter.println(sb.toString());
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            close();
        }
    }
}
