package android.os;

/* loaded from: classes3.dex */
public class Process {
    public static final int AUDIOSERVER_UID = 1041;
    public static final int BLUETOOTH_UID = 1002;
    public static final int CAMERASERVER_UID = 1047;
    public static final int CLAT_UID = 1029;
    public static final int CREDSTORE_UID = 1076;
    public static final int DNS_TETHER_UID = 1052;
    public static final int DRM_UID = 1019;
    public static final int EXTERNAL_STORAGE_GID = 1077;
    public static final int EXT_DATA_RW_GID = 1078;
    public static final int EXT_OBB_RW_GID = 1079;
    public static final int FIRST_APPLICATION_CACHE_GID = 20000;
    public static final int FIRST_APPLICATION_UID = 10000;
    public static final int FIRST_APP_ZYGOTE_ISOLATED_UID = 90000;
    public static final int FIRST_ISOLATED_UID = 99000;
    public static final int FIRST_SDK_SANDBOX_UID = 20000;
    public static final int FIRST_SHARED_APPLICATION_GID = 50000;
    public static final int FSVERITY_CERT_UID = 1075;
    public static final int INCIDENTD_UID = 1067;
    public static final int INET_GID = 3003;
    public static final int INVALID_PID = -1;
    public static final int INVALID_UID = -1;
    public static final int KEYSTORE_UID = 1017;
    public static final int LAST_APPLICATION_CACHE_GID = 29999;
    public static final int LAST_APPLICATION_UID = 19999;
    public static final int LAST_APP_ZYGOTE_ISOLATED_UID = 98999;
    public static final int LAST_ISOLATED_UID = 99999;
    public static final int LAST_SDK_SANDBOX_UID = 29999;
    public static final int LAST_SHARED_APPLICATION_GID = 59999;
    private static final java.lang.String LOG_TAG = "Process";
    public static final int LOG_UID = 1007;
    public static final int MEDIA_RW_GID = 1023;
    public static final int MEDIA_UID = 1013;
    public static final int NETWORK_STACK_UID = 1073;

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static final int NFC_UID = 1027;
    public static final int NOBODY_UID = 9999;
    public static final int NUM_UIDS_PER_APP_ZYGOTE = 100;
    public static final int OTA_UPDATE_UID = 1061;
    public static final int PACKAGE_INFO_GID = 1032;
    public static final int PHONE_UID = 1001;
    private static final int PIDFD_SUPPORTED = 1;
    private static final int PIDFD_UNKNOWN = 0;
    private static final int PIDFD_UNSUPPORTED = 2;
    public static final int PROC_CHAR = 2048;
    public static final int PROC_COMBINE = 256;
    public static final int PROC_NEWLINE_TERM = 10;
    public static final int PROC_OUT_FLOAT = 16384;
    public static final int PROC_OUT_LONG = 8192;
    public static final int PROC_OUT_STRING = 4096;
    public static final int PROC_PARENS = 512;
    public static final int PROC_QUOTES = 1024;
    public static final int PROC_SPACE_TERM = 32;
    public static final int PROC_TAB_TERM = 9;
    public static final int PROC_TERM_MASK = 255;
    public static final int PROC_ZERO_TERM = 0;
    public static final int ROOT_UID = 0;
    public static final int SCHED_BATCH = 3;
    public static final int SCHED_FIFO = 1;
    public static final int SCHED_IDLE = 5;
    public static final int SCHED_OTHER = 0;
    public static final int SCHED_RESET_ON_FORK = 1073741824;
    public static final int SCHED_RR = 2;
    public static final int SDCARD_RW_GID = 1015;
    public static final int SDK_SANDBOX_VIRTUAL_UID = 1090;
    public static final int SE_UID = 1068;
    public static final int SHARED_RELRO_UID = 1037;
    public static final int SHARED_USER_GID = 9997;
    public static final int SHELL_UID = 2000;
    public static final int SIGNAL_KILL = 9;
    public static final int SIGNAL_QUIT = 3;
    public static final int SIGNAL_USR1 = 10;
    public static final int STATSD_UID = 1066;
    public static final int SYSTEM_UID = 1000;
    public static final int THREAD_GROUP_AUDIO_APP = 3;
    public static final int THREAD_GROUP_AUDIO_SYS = 4;
    public static final int THREAD_GROUP_BACKGROUND = 0;
    public static final int THREAD_GROUP_DEFAULT = -1;
    private static final int THREAD_GROUP_FOREGROUND = 1;
    public static final int THREAD_GROUP_RESTRICTED = 7;
    public static final int THREAD_GROUP_RT_APP = 6;
    public static final int THREAD_GROUP_SYSTEM = 2;
    public static final int THREAD_GROUP_TOP_APP = 5;
    public static final int THREAD_PRIORITY_AUDIO = -16;
    public static final int THREAD_PRIORITY_BACKGROUND = 10;
    public static final int THREAD_PRIORITY_DEFAULT = 0;
    public static final int THREAD_PRIORITY_DISPLAY = -4;
    public static final int THREAD_PRIORITY_FOREGROUND = -2;
    public static final int THREAD_PRIORITY_LESS_FAVORABLE = 1;
    public static final int THREAD_PRIORITY_LOWEST = 19;
    public static final int THREAD_PRIORITY_MORE_FAVORABLE = -1;
    public static final int THREAD_PRIORITY_TOP_APP_BOOST = -10;
    public static final int THREAD_PRIORITY_URGENT_AUDIO = -19;
    public static final int THREAD_PRIORITY_URGENT_DISPLAY = -8;
    public static final int THREAD_PRIORITY_VIDEO = -10;
    public static final int UWB_UID = 1083;

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static final int VPN_UID = 1016;
    public static final int WEBVIEW_ZYGOTE_UID = 1053;
    public static final int WIFI_UID = 1010;
    public static final int ZYGOTE_POLICY_FLAG_BATCH_LAUNCH = 2;
    public static final int ZYGOTE_POLICY_FLAG_EMPTY = 0;
    public static final int ZYGOTE_POLICY_FLAG_LATENCY_SENSITIVE = 1;
    public static final int ZYGOTE_POLICY_FLAG_SYSTEM_PROCESS = 4;
    private static java.lang.String sArgV0;
    private static volatile java.lang.ThreadLocal<com.android.internal.os.SomeArgs> sIdentity$ravenwood;
    private static long sStartElapsedRealtime;
    private static long sStartRequestedElapsedRealtime;
    private static long sStartRequestedUptimeMillis;
    private static long sStartUptimeMillis;
    private static int sPidFdSupported = 0;
    public static final android.os.ZygoteProcess ZYGOTE_PROCESS = new android.os.ZygoteProcess();

    public static final class ProcessStartResult {
        public int pid;
        public boolean usingWrapper;
    }

    public static final native int createProcessGroup(int i, int i2);

    public static final native void freezeCgroupUid(int i, boolean z);

    public static final native long getElapsedCpuTime();

    public static final native int[] getExclusiveCores();

    public static final native long getFreeMemory();

    public static final native int getGidForName(java.lang.String str);

    public static final native int[] getPids(java.lang.String str, int[] iArr);

    public static final native int[] getPidsForCommands(java.lang.String[] strArr);

    public static final native int getProcessGroup(int i) throws java.lang.IllegalArgumentException, java.lang.SecurityException;

    public static final native long getPss(int i);

    public static final native long[] getRss(int i);

    public static final native int getThreadPriority(int i) throws java.lang.IllegalArgumentException;

    public static final native int getThreadScheduler(int i) throws java.lang.IllegalArgumentException;

    public static final native long getTotalMemory();

    public static final native int getUidForName(java.lang.String str);

    public static final native int killProcessGroup(int i, int i2);

    private static native int nativePidFdOpen(int i, int i2) throws android.system.ErrnoException;

    public static final native boolean parseProcLine(byte[] bArr, int i, int i2, int[] iArr, java.lang.String[] strArr, long[] jArr, float[] fArr);

    public static final native boolean readProcFile(java.lang.String str, int[] iArr, java.lang.String[] strArr, long[] jArr, float[] fArr);

    public static final native void readProcLines(java.lang.String str, java.lang.String[] strArr, long[] jArr);

    public static final native void removeAllProcessGroups();

    public static final native void sendSignal(int i, int i2);

    public static final native void sendSignalQuiet(int i, int i2);

    public static final native boolean sendSignalToProcessGroup(int i, int i2, int i3);

    private static native void setArgV0Native(java.lang.String str);

    public static final native void setCanSelfBackground(boolean z);

    public static final native int setGid(int i);

    public static final native void setProcessFrozen(int i, int i2, boolean z);

    public static final native void setProcessGroup(int i, int i2) throws java.lang.IllegalArgumentException, java.lang.SecurityException;

    public static final native boolean setSwappiness(int i, boolean z);

    public static final native void setThreadGroup(int i, int i2) throws java.lang.IllegalArgumentException, java.lang.SecurityException;

    public static final native void setThreadGroupAndCpuset(int i, int i2) throws java.lang.IllegalArgumentException, java.lang.SecurityException;

    public static final native void setThreadPriority(int i) throws java.lang.IllegalArgumentException, java.lang.SecurityException;

    public static final native void setThreadPriority(int i, int i2) throws java.lang.IllegalArgumentException, java.lang.SecurityException;

    public static final native void setThreadScheduler(int i, int i2, int i3) throws java.lang.IllegalArgumentException;

    public static final native int setUid(int i);

    public static android.os.Process.ProcessStartResult start(java.lang.String str, java.lang.String str2, int i, int i2, int[] iArr, int i3, int i4, int i5, java.lang.String str3, java.lang.String str4, java.lang.String str5, java.lang.String str6, java.lang.String str7, java.lang.String str8, int i6, boolean z, long[] jArr, java.util.Map<java.lang.String, android.util.Pair<java.lang.String, java.lang.Long>> map, java.util.Map<java.lang.String, android.util.Pair<java.lang.String, java.lang.Long>> map2, boolean z2, boolean z3, boolean z4, java.lang.String[] strArr) {
        return ZYGOTE_PROCESS.start(str, str2, i, i2, iArr, i3, i4, i5, str3, str4, str5, str6, str7, str8, i6, z, jArr, map, map2, z2, z3, z4, strArr);
    }

    public static android.os.Process.ProcessStartResult startWebView(java.lang.String str, java.lang.String str2, int i, int i2, int[] iArr, int i3, int i4, int i5, java.lang.String str3, java.lang.String str4, java.lang.String str5, java.lang.String str6, java.lang.String str7, java.lang.String str8, long[] jArr, java.lang.String[] strArr) {
        return android.webkit.WebViewZygote.getProcess().start(str, str2, i, i2, iArr, i3, i4, i5, str3, str4, str5, str6, str7, str8, 0, false, jArr, null, null, false, false, false, strArr);
    }

    public static long getStartElapsedRealtime() {
        return sStartElapsedRealtime;
    }

    public static long getStartUptimeMillis() {
        return sStartUptimeMillis;
    }

    public static long getStartRequestedElapsedRealtime() {
        return sStartRequestedElapsedRealtime;
    }

    public static long getStartRequestedUptimeMillis() {
        return sStartRequestedUptimeMillis;
    }

    public static final void setStartTimes(long j, long j2, long j3, long j4) {
        sStartElapsedRealtime = j;
        sStartUptimeMillis = j2;
        sStartRequestedElapsedRealtime = j3;
        sStartRequestedUptimeMillis = j4;
    }

    public static final boolean is64Bit() {
        return dalvik.system.VMRuntime.getRuntime().is64Bit();
    }

    static /* synthetic */ com.android.internal.os.SomeArgs lambda$init$ravenwood$0(int i, int i2) {
        com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
        obtain.argi1 = i;
        obtain.argi2 = i2;
        obtain.argi3 = java.lang.Long.hashCode(java.lang.Thread.currentThread().getId());
        obtain.argi4 = 0;
        obtain.arg1 = java.lang.Boolean.TRUE;
        return obtain;
    }

    public static final int myPid() {
        return android.system.Os.getpid();
    }

    public static final int myPpid() {
        return android.system.Os.getppid();
    }

    public static final int myTid() {
        return android.system.Os.gettid();
    }

    public static final int myUid() {
        return android.system.Os.getuid();
    }

    public static android.os.UserHandle myUserHandle() {
        return android.os.UserHandle.of(android.os.UserHandle.getUserId(myUid()));
    }

    public static boolean isCoreUid(int i) {
        return android.os.UserHandle.isCore(i);
    }

    public static boolean isApplicationUid(int i) {
        return android.os.UserHandle.isApp(i);
    }

    public static final boolean isIsolated() {
        return isIsolated(myUid());
    }

    @java.lang.Deprecated
    public static final boolean isIsolated(int i) {
        return isIsolatedUid(i);
    }

    public static final boolean isIsolatedUid(int i) {
        int appId = android.os.UserHandle.getAppId(i);
        return (appId >= 99000 && appId <= 99999) || (appId >= 90000 && appId <= 98999);
    }

    public static final boolean isSdkSandboxUid(int i) {
        int appId = android.os.UserHandle.getAppId(i);
        return appId >= 20000 && appId <= 29999;
    }

    public static final int getAppUidForSdkSandboxUid(int i) {
        if (!isSdkSandboxUid(i)) {
            throw new java.lang.IllegalArgumentException("Input UID is not an SDK sandbox UID");
        }
        return i - 10000;
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static final int toSdkSandboxUid(int i) {
        return i + 10000;
    }

    public static final int getSdkSandboxUidForAppUid(int i) {
        if (!isApplicationUid(i)) {
            throw new java.lang.IllegalArgumentException("Input UID is not an app UID");
        }
        return i + 10000;
    }

    public static final boolean isSdkSandbox() {
        return isSdkSandboxUid(myUid());
    }

    public static final int getUidForPid(int i) {
        long[] jArr = {-1};
        readProcLines("/proc/" + i + "/status", new java.lang.String[]{"Uid:"}, jArr);
        return (int) jArr[0];
    }

    public static final int getParentPid(int i) {
        long[] jArr = {-1};
        readProcLines("/proc/" + i + "/status", new java.lang.String[]{"PPid:"}, jArr);
        return (int) jArr[0];
    }

    public static final int getThreadGroupLeader(int i) {
        long[] jArr = {-1};
        readProcLines("/proc/" + i + "/status", new java.lang.String[]{"Tgid:"}, jArr);
        return (int) jArr[0];
    }

    public static final void setThreadPriority$ravenwood(int i, int i2) {
        com.android.internal.os.SomeArgs someArgs = (com.android.internal.os.SomeArgs) ((java.lang.ThreadLocal) com.android.internal.util.Preconditions.requireNonNullViaRavenwoodRule(sIdentity$ravenwood)).get();
        if (someArgs.argi3 == i) {
            boolean z = someArgs.arg1 == java.lang.Boolean.TRUE;
            if (i2 >= 10 && !z) {
                throw new java.lang.IllegalArgumentException("Priority " + i2 + " blocked by setCanSelfBackground()");
            }
            someArgs.argi4 = i2;
            return;
        }
        throw new java.lang.UnsupportedOperationException("Cross-thread priority management not yet available in Ravenwood");
    }

    public static final int getThreadPriority$ravenwood(int i) {
        com.android.internal.os.SomeArgs someArgs = (com.android.internal.os.SomeArgs) ((java.lang.ThreadLocal) com.android.internal.util.Preconditions.requireNonNullViaRavenwoodRule(sIdentity$ravenwood)).get();
        if (someArgs.argi3 == i) {
            return someArgs.argi4;
        }
        throw new java.lang.UnsupportedOperationException("Cross-thread priority management not yet available in Ravenwood");
    }

    @java.lang.Deprecated
    public static final boolean supportsProcesses() {
        return true;
    }

    public static void setArgV0(java.lang.String str) {
        sArgV0 = str;
        setArgV0Native(str);
    }

    public static java.lang.String myProcessName() {
        return sArgV0;
    }

    public static final void killProcess(int i) {
        sendSignal(i, 9);
    }

    public static final void killProcessQuiet(int i) {
        sendSignalQuiet(i, 9);
    }

    public static final long getAdvertisedMem() {
        long parseSize = android.os.FileUtils.parseSize(android.sysprop.MemoryProperties.memory_ddr_size().orElse("0KB"));
        if (parseSize <= 0) {
            return android.os.FileUtils.roundStorageSize(getTotalMemory());
        }
        return parseSize;
    }

    public static final boolean isThreadInProcess(int i, int i2) {
        android.os.StrictMode.ThreadPolicy allowThreadDiskReads = android.os.StrictMode.allowThreadDiskReads();
        try {
            if (!android.system.Os.access("/proc/" + i + "/task/" + i2, android.system.OsConstants.F_OK)) {
                return false;
            }
            android.os.StrictMode.setThreadPolicy(allowThreadDiskReads);
            return true;
        } catch (java.lang.Exception e) {
            return false;
        } finally {
            android.os.StrictMode.setThreadPolicy(allowThreadDiskReads);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x0079  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void waitForProcessDeath(int i, int i2) throws java.lang.InterruptedException, java.util.concurrent.TimeoutException {
        java.io.FileDescriptor fileDescriptor;
        boolean supportsPidFd = supportsPidFd();
        if (!supportsPidFd) {
            java.io.FileDescriptor fileDescriptor2 = null;
            try {
                try {
                    int nativePidFdOpen = nativePidFdOpen(i, 0);
                    if (nativePidFdOpen >= 0) {
                        fileDescriptor = new java.io.FileDescriptor();
                        try {
                            fileDescriptor.setInt$(nativePidFdOpen);
                        } catch (android.system.ErrnoException e) {
                            e = e;
                            fileDescriptor2 = fileDescriptor;
                            if (e.errno == android.system.OsConstants.EINTR) {
                                throw new java.lang.InterruptedException();
                            }
                            if (fileDescriptor2 != null) {
                                libcore.io.IoUtils.closeQuietly(fileDescriptor2);
                            }
                            supportsPidFd = true;
                            if (supportsPidFd) {
                            }
                            throw new java.util.concurrent.TimeoutException();
                        } catch (java.lang.Throwable th) {
                            th = th;
                            fileDescriptor2 = fileDescriptor;
                            if (fileDescriptor2 != null) {
                                libcore.io.IoUtils.closeQuietly(fileDescriptor2);
                            }
                            throw th;
                        }
                    } else {
                        supportsPidFd = true;
                        fileDescriptor = null;
                    }
                    if (fileDescriptor != null) {
                        android.system.StructPollfd[] structPollfdArr = {new android.system.StructPollfd()};
                        structPollfdArr[0].fd = fileDescriptor;
                        structPollfdArr[0].events = (short) android.system.OsConstants.POLLIN;
                        structPollfdArr[0].revents = (short) 0;
                        structPollfdArr[0].userData = null;
                        int poll = android.system.Os.poll(structPollfdArr, i2);
                        if (poll > 0) {
                            if (fileDescriptor != null) {
                                libcore.io.IoUtils.closeQuietly(fileDescriptor);
                                return;
                            }
                            return;
                        } else if (poll == 0) {
                            throw new java.util.concurrent.TimeoutException();
                        }
                    }
                    if (fileDescriptor != null) {
                        libcore.io.IoUtils.closeQuietly(fileDescriptor);
                    }
                } catch (android.system.ErrnoException e2) {
                    e = e2;
                }
            } catch (java.lang.Throwable th2) {
                th = th2;
            }
        }
        if (supportsPidFd) {
            boolean z = i2 < 0;
            long currentTimeMillis = java.lang.System.currentTimeMillis();
            long j = i2 + currentTimeMillis;
            while (true) {
                if (!z && currentTimeMillis >= j) {
                    break;
                }
                try {
                    android.system.Os.kill(i, 0);
                } catch (android.system.ErrnoException e3) {
                    if (e3.errno == android.system.OsConstants.ESRCH) {
                        return;
                    }
                }
                java.lang.Thread.sleep(1L);
                currentTimeMillis = java.lang.System.currentTimeMillis();
            }
        }
        throw new java.util.concurrent.TimeoutException();
    }

    public static boolean supportsPidFd() {
        java.io.FileDescriptor fileDescriptor;
        if (sPidFdSupported == 0) {
            int i = -1;
            try {
                try {
                    i = nativePidFdOpen(myPid(), 0);
                    sPidFdSupported = 1;
                } catch (android.system.ErrnoException e) {
                    sPidFdSupported = e.errno != android.system.OsConstants.ENOSYS ? 1 : 2;
                    if (i >= 0) {
                        fileDescriptor = new java.io.FileDescriptor();
                    }
                }
                if (i >= 0) {
                    fileDescriptor = new java.io.FileDescriptor();
                    fileDescriptor.setInt$(i);
                    libcore.io.IoUtils.closeQuietly(fileDescriptor);
                }
            } catch (java.lang.Throwable th) {
                if (i >= 0) {
                    java.io.FileDescriptor fileDescriptor2 = new java.io.FileDescriptor();
                    fileDescriptor2.setInt$(i);
                    libcore.io.IoUtils.closeQuietly(fileDescriptor2);
                }
                throw th;
            }
        }
        return sPidFdSupported == 1;
    }

    public static java.io.FileDescriptor openPidFd(int i, int i2) throws java.io.IOException {
        if (!supportsPidFd()) {
            return null;
        }
        if (i2 != 0) {
            throw new java.lang.IllegalArgumentException();
        }
        try {
            java.io.FileDescriptor fileDescriptor = new java.io.FileDescriptor();
            fileDescriptor.setInt$(nativePidFdOpen(i, i2));
            return fileDescriptor;
        } catch (android.system.ErrnoException e) {
            java.io.IOException iOException = new java.io.IOException();
            iOException.initCause(e);
            throw iOException;
        }
    }
}
