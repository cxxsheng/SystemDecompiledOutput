package com.android.internal.os;

/* loaded from: classes4.dex */
public final class Zygote {
    public static final java.lang.String ALLOWLISTED_DATA_INFO_MAP = "--allowlisted-data-info-map";
    private static final java.lang.String ANDROID_SOCKET_PREFIX = "ANDROID_SOCKET_";
    public static final int API_ENFORCEMENT_POLICY_MASK = 12288;
    public static final java.lang.String BIND_MOUNT_APP_DATA_DIRS = "--bind-mount-data-dirs";
    public static final java.lang.String BIND_MOUNT_APP_STORAGE_DIRS = "--bind-mount-storage-dirs";
    public static final java.lang.String BIND_MOUNT_SYSPROP_OVERRIDES = "--bind-mount-sysprop-overrides";
    public static final java.lang.String CHILD_ZYGOTE_ABI_LIST_ARG = "--abi-list=";
    public static final java.lang.String CHILD_ZYGOTE_SOCKET_NAME_ARG = "--zygote-socket=";
    public static final java.lang.String CHILD_ZYGOTE_UID_RANGE_END = "--uid-range-end=";
    public static final java.lang.String CHILD_ZYGOTE_UID_RANGE_START = "--uid-range-start=";
    public static final int DEBUG_ALWAYS_JIT = 64;
    public static final int DEBUG_ENABLE_ASSERT = 4;
    public static final int DEBUG_ENABLE_CHECKJNI = 2;
    public static final int DEBUG_ENABLE_JDWP = 1;
    public static final int DEBUG_ENABLE_JNI_LOGGING = 16;
    public static final int DEBUG_ENABLE_PTRACE = 33554432;
    public static final int DEBUG_ENABLE_SAFEMODE = 8;
    public static final int DEBUG_GENERATE_DEBUG_INFO = 32;
    public static final int DEBUG_GENERATE_MINI_DEBUG_INFO = 2048;
    public static final int DEBUG_IGNORE_APP_SIGNAL_HANDLER = 131072;
    public static final int DEBUG_JAVA_DEBUGGABLE = 256;
    public static final int DEBUG_NATIVE_DEBUGGABLE = 128;
    public static final int DISABLE_TEST_API_ENFORCEMENT_POLICY = 262144;
    public static final int DISABLE_VERIFIER = 512;
    private static final long GWP_ASAN = 135634846;
    public static final int GWP_ASAN_LEVEL_ALWAYS = 4194304;
    public static final int GWP_ASAN_LEVEL_DEFAULT = 6291456;
    public static final int GWP_ASAN_LEVEL_LOTTERY = 2097152;
    public static final int GWP_ASAN_LEVEL_MASK = 6291456;
    public static final int GWP_ASAN_LEVEL_NEVER = 0;
    public static final int MEMORY_TAG_LEVEL_ASYNC = 1048576;
    public static final int MEMORY_TAG_LEVEL_MASK = 1572864;
    public static final int MEMORY_TAG_LEVEL_NONE = 0;
    public static final int MEMORY_TAG_LEVEL_SYNC = 1572864;
    public static final int MEMORY_TAG_LEVEL_TBI = 524288;
    public static final int MOUNT_EXTERNAL_ANDROID_WRITABLE = 4;
    public static final int MOUNT_EXTERNAL_DEFAULT = 1;
    public static final int MOUNT_EXTERNAL_INSTALLER = 2;
    public static final int MOUNT_EXTERNAL_NONE = 0;
    public static final int MOUNT_EXTERNAL_PASS_THROUGH = 3;
    private static final long NATIVE_HEAP_POINTER_TAGGING = 135754954;
    private static final long NATIVE_HEAP_POINTER_TAGGING_SECONDARY_ZYGOTE = 207557677;
    private static final long NATIVE_HEAP_ZERO_INIT = 178038272;
    public static final int NATIVE_HEAP_ZERO_INIT_ENABLED = 8388608;
    private static final long NATIVE_MEMTAG_ASYNC = 135772972;
    private static final long NATIVE_MEMTAG_SYNC = 177438394;
    public static final int ONLY_USE_SYSTEM_OAT_FILES = 1024;
    public static final java.lang.String PKG_DATA_INFO_MAP = "--pkg-data-info-map";
    public static final java.lang.String PRIMARY_SOCKET_NAME = "zygote";
    private static final int PRIORITY_MAX = -20;
    public static final int PROFILEABLE = 16777216;
    public static final int PROFILE_FROM_SHELL = 32768;
    public static final int PROFILE_SYSTEM_SERVER = 16384;
    public static final long PROPERTY_CHECK_INTERVAL = 60000;
    public static final java.lang.String SECONDARY_SOCKET_NAME = "zygote_secondary";
    public static final int SOCKET_BUFFER_SIZE = 256;
    public static final java.lang.String START_AS_TOP_APP_ARG = "--is-top-app";
    private static final java.lang.String TAG = "Zygote";
    private static final java.lang.String USAP_ERROR_PREFIX = "Invalid command to USAP: ";
    static final int USAP_MANAGEMENT_MESSAGE_BYTES = 8;
    public static final java.lang.String USAP_POOL_PRIMARY_SOCKET_NAME = "usap_pool_primary";
    public static final java.lang.String USAP_POOL_SECONDARY_SOCKET_NAME = "usap_pool_secondary";
    public static final int USE_APP_IMAGE_STARTUP_CACHE = 65536;
    public static final int API_ENFORCEMENT_POLICY_SHIFT = java.lang.Integer.numberOfTrailingZeros(12288);
    static final int[][] INT_ARRAY_2D = (int[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Integer.TYPE, 0, 0);
    private static final boolean ENABLE_JDWP = android.os.SystemProperties.get("persist.debug.dalvik.vm.jdwp.enabled").equals("1");
    private static final boolean ENABLE_PTRACE = android.os.SystemProperties.get("persist.debug.ptrace.enabled").equals("1");

    @dalvik.annotation.optimization.CriticalNative
    private static native void nativeAddUsapTableEntry(int i, int i2);

    protected static native void nativeAllowFileAcrossFork(java.lang.String str);

    private static native void nativeAllowFilesOpenedByPreload();

    private static native void nativeBlockSigTerm();

    private static native void nativeBoostUsapPriority();

    public static native int nativeCurrentTaggingLevel();

    private static native void nativeEmptyUsapPool();

    private static native int nativeForkAndSpecialize(int i, int i2, int[] iArr, int i3, int[][] iArr2, int i4, java.lang.String str, java.lang.String str2, int[] iArr3, int[] iArr4, boolean z, java.lang.String str3, java.lang.String str4, boolean z2, java.lang.String[] strArr, java.lang.String[] strArr2, boolean z3, boolean z4, boolean z5);

    private static native int nativeForkApp(int i, int i2, int[] iArr, boolean z, boolean z2);

    private static native int nativeForkSystemServer(int i, int i2, int[] iArr, int i3, int[][] iArr2, long j, long j2);

    private static native int[] nativeGetUsapPipeFDs();

    private static native int nativeGetUsapPoolCount();

    private static native int nativeGetUsapPoolEventFD();

    protected static native void nativeInitNativeState(boolean z);

    protected static native void nativeInstallSeccompUidGidFilter(int i, int i2);

    private static native void nativeMarkOpenedFilesBeforePreload();

    @dalvik.annotation.optimization.FastNative
    public static native int nativeParseSigChld(byte[] bArr, int i, int[] iArr);

    static native void nativePreApplicationInit();

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nativeRemoveUsapTableEntry(int i);

    private static native void nativeSpecializeAppProcess(int i, int i2, int[] iArr, int i3, int[][] iArr2, int i4, java.lang.String str, java.lang.String str2, boolean z, java.lang.String str3, java.lang.String str4, boolean z2, java.lang.String[] strArr, java.lang.String[] strArr2, boolean z3, boolean z4, boolean z5);

    public static native boolean nativeSupportsMemoryTagging();

    public static native boolean nativeSupportsTaggedPointers();

    private static native void nativeUnblockSigTerm();

    private Zygote() {
    }

    private static boolean containsInetGid(int[] iArr) {
        for (int i : iArr) {
            if (i == 3003) {
                return true;
            }
        }
        return false;
    }

    static int forkAndSpecialize(int i, int i2, int[] iArr, int i3, int[][] iArr2, int i4, java.lang.String str, java.lang.String str2, int[] iArr3, int[] iArr4, boolean z, java.lang.String str3, java.lang.String str4, boolean z2, java.lang.String[] strArr, java.lang.String[] strArr2, boolean z3, boolean z4, boolean z5) {
        dalvik.system.ZygoteHooks.preFork();
        int nativeForkAndSpecialize = nativeForkAndSpecialize(i, i2, iArr, i3, iArr2, i4, str, str2, iArr3, iArr4, z, str3, str4, z2, strArr, strArr2, z3, z4, z5);
        if (nativeForkAndSpecialize == 0) {
            android.os.Trace.traceBegin(64L, "PostFork");
            if (iArr != null && iArr.length > 0) {
                com.android.internal.net.NetworkUtilsInternal.setAllowNetworkingForProcess(containsInetGid(iArr));
            }
        }
        java.lang.Thread.currentThread().setPriority(5);
        dalvik.system.ZygoteHooks.postForkCommon();
        return nativeForkAndSpecialize;
    }

    private static void specializeAppProcess(int i, int i2, int[] iArr, int i3, int[][] iArr2, int i4, java.lang.String str, java.lang.String str2, boolean z, java.lang.String str3, java.lang.String str4, boolean z2, java.lang.String[] strArr, java.lang.String[] strArr2, boolean z3, boolean z4, boolean z5) {
        nativeSpecializeAppProcess(i, i2, iArr, i3, iArr2, i4, str, str2, z, str3, str4, z2, strArr, strArr2, z3, z4, z5);
        android.os.Trace.traceBegin(64L, "PostFork");
        if (iArr != null && iArr.length > 0) {
            com.android.internal.net.NetworkUtilsInternal.setAllowNetworkingForProcess(containsInetGid(iArr));
        }
        java.lang.Thread.currentThread().setPriority(5);
        dalvik.system.ZygoteHooks.postForkCommon();
    }

    static int forkSystemServer(int i, int i2, int[] iArr, int i3, int[][] iArr2, long j, long j2) {
        dalvik.system.ZygoteHooks.preFork();
        int nativeForkSystemServer = nativeForkSystemServer(i, i2, iArr, i3, iArr2, j, j2);
        java.lang.Thread.currentThread().setPriority(5);
        dalvik.system.ZygoteHooks.postForkCommon();
        return nativeForkSystemServer;
    }

    static void allowAppFilesAcrossFork(android.content.pm.ApplicationInfo applicationInfo) {
        for (java.lang.String str : applicationInfo.getAllApkPaths()) {
            nativeAllowFileAcrossFork(str);
        }
    }

    static void markOpenedFilesBeforePreload() {
        nativeMarkOpenedFilesBeforePreload();
    }

    static void allowFilesOpenedByPreload() {
        nativeAllowFilesOpenedByPreload();
    }

    static void initNativeState(boolean z) {
        nativeInitNativeState(z);
    }

    public static java.lang.String getConfigurationProperty(java.lang.String str, java.lang.String str2) {
        return android.os.SystemProperties.get(java.lang.String.join(android.media.MediaMetrics.SEPARATOR, com.android.internal.os.ZygoteConfig.PROPERTY_PREFIX_DEVICE_CONFIG, "runtime_native", str), str2);
    }

    static void emptyUsapPool() {
        nativeEmptyUsapPool();
    }

    public static boolean getConfigurationPropertyBoolean(java.lang.String str, java.lang.Boolean bool) {
        return android.os.SystemProperties.getBoolean(java.lang.String.join(android.media.MediaMetrics.SEPARATOR, com.android.internal.os.ZygoteConfig.PROPERTY_PREFIX_DEVICE_CONFIG, "runtime_native", str), bool.booleanValue());
    }

    static int getUsapPoolCount() {
        return nativeGetUsapPoolCount();
    }

    static java.io.FileDescriptor getUsapPoolEventFD() {
        java.io.FileDescriptor fileDescriptor = new java.io.FileDescriptor();
        fileDescriptor.setInt$(nativeGetUsapPoolEventFD());
        return fileDescriptor;
    }

    static java.lang.Runnable forkUsap(android.net.LocalServerSocket localServerSocket, int[] iArr, boolean z) {
        try {
            java.io.FileDescriptor[] pipe2 = android.system.Os.pipe2(android.system.OsConstants.O_CLOEXEC);
            java.io.FileDescriptor fileDescriptor = pipe2[0];
            java.io.FileDescriptor fileDescriptor2 = pipe2[1];
            int nativeForkApp = nativeForkApp(fileDescriptor.getInt$(), fileDescriptor2.getInt$(), iArr, false, z);
            if (nativeForkApp == 0) {
                libcore.io.IoUtils.closeQuietly(fileDescriptor);
                return childMain(null, localServerSocket, fileDescriptor2);
            }
            if (nativeForkApp == -1) {
                return null;
            }
            libcore.io.IoUtils.closeQuietly(fileDescriptor2);
            nativeAddUsapTableEntry(nativeForkApp, fileDescriptor.getInt$());
            return null;
        } catch (android.system.ErrnoException e) {
            throw new java.lang.IllegalStateException("Unable to create USAP pipe.", e);
        }
    }

    static java.lang.Runnable forkSimpleApps(com.android.internal.os.ZygoteCommandBuffer zygoteCommandBuffer, java.io.FileDescriptor fileDescriptor, int i, int i2, java.lang.String str) {
        if (!zygoteCommandBuffer.forkRepeatedly(fileDescriptor, i, i2, str)) {
            return null;
        }
        return childMain(zygoteCommandBuffer, null, null);
    }

    /* JADX WARN: Finally extract failed */
    private static java.lang.Runnable childMain(com.android.internal.os.ZygoteCommandBuffer zygoteCommandBuffer, android.net.LocalServerSocket localServerSocket, java.io.FileDescriptor fileDescriptor) {
        com.android.internal.os.ZygoteArguments zygoteArguments;
        java.io.DataOutputStream dataOutputStream;
        int[][] iArr;
        com.android.internal.os.ZygoteCommandBuffer zygoteCommandBuffer2;
        int myPid = android.os.Process.myPid();
        if (zygoteCommandBuffer == null) {
            android.os.Process.setArgV0(android.os.Process.is64Bit() ? "usap64" : "usap32");
            boostUsapPriority();
            android.net.LocalSocket localSocket = null;
            while (true) {
                try {
                    localSocket = localServerSocket.accept();
                    blockSigTerm();
                    dataOutputStream = new java.io.DataOutputStream(localSocket.getOutputStream());
                    android.net.Credentials peerCredentials = localSocket.getPeerCredentials();
                    zygoteCommandBuffer2 = new com.android.internal.os.ZygoteCommandBuffer(localSocket);
                    try {
                        zygoteArguments = com.android.internal.os.ZygoteArguments.getInstance(zygoteCommandBuffer2);
                        applyUidSecurityPolicy(zygoteArguments, peerCredentials);
                        validateUsapCommand(zygoteArguments);
                        break;
                    } catch (java.lang.Exception e) {
                        e = e;
                    }
                } catch (java.lang.Exception e2) {
                    e = e2;
                    zygoteCommandBuffer2 = null;
                }
                android.util.Log.e("USAP", e.getMessage());
                unblockSigTerm();
                libcore.io.IoUtils.closeQuietly(localSocket);
                libcore.io.IoUtils.closeQuietly(zygoteCommandBuffer2);
            }
        } else {
            blockSigTerm();
            try {
                zygoteArguments = com.android.internal.os.ZygoteArguments.getInstance(zygoteCommandBuffer);
                dataOutputStream = null;
            } catch (java.lang.Exception e3) {
                android.util.Log.e("AppStartup", e3.getMessage());
                throw new java.lang.AssertionError("Failed to parse application start command", e3);
            }
        }
        if (zygoteArguments == null) {
            throw new java.lang.AssertionError("Empty command line");
        }
        try {
            applyDebuggerSystemProperty(zygoteArguments);
            if (zygoteArguments.mRLimits == null) {
                iArr = null;
            } else {
                iArr = (int[][]) zygoteArguments.mRLimits.toArray(INT_ARRAY_2D);
            }
            if (zygoteCommandBuffer == null) {
                try {
                    try {
                        dataOutputStream.writeInt(myPid);
                        try {
                            java.io.FileDescriptor fileDescriptor2 = localServerSocket.getFileDescriptor();
                            localServerSocket.close();
                            android.system.Os.close(fileDescriptor2);
                        } catch (android.system.ErrnoException | java.io.IOException e4) {
                            android.util.Log.e("USAP", "Failed to close USAP pool socket");
                            throw new java.lang.RuntimeException(e4);
                        }
                    } catch (java.lang.Throwable th) {
                        try {
                            java.io.FileDescriptor fileDescriptor3 = localServerSocket.getFileDescriptor();
                            localServerSocket.close();
                            android.system.Os.close(fileDescriptor3);
                            throw th;
                        } catch (android.system.ErrnoException | java.io.IOException e5) {
                            android.util.Log.e("USAP", "Failed to close USAP pool socket");
                            throw new java.lang.RuntimeException(e5);
                        }
                    }
                } catch (java.io.IOException e6) {
                    android.util.Log.e("USAP", "Failed to write response to session socket: " + e6.getMessage());
                    throw new java.lang.RuntimeException(e6);
                }
            }
            try {
                if (fileDescriptor != null) {
                    try {
                        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream(8);
                        java.io.DataOutputStream dataOutputStream2 = new java.io.DataOutputStream(byteArrayOutputStream);
                        dataOutputStream2.writeLong(myPid);
                        dataOutputStream2.flush();
                        android.system.Os.write(fileDescriptor, byteArrayOutputStream.toByteArray(), 0, byteArrayOutputStream.size());
                    } catch (java.lang.Exception e7) {
                        android.util.Log.e("USAP", java.lang.String.format("Failed to write PID (%d) to pipe (%d): %s", java.lang.Integer.valueOf(myPid), java.lang.Integer.valueOf(fileDescriptor.getInt$()), e7.getMessage()));
                        throw new java.lang.RuntimeException(e7);
                    }
                }
                specializeAppProcess(zygoteArguments.mUid, zygoteArguments.mGid, zygoteArguments.mGids, zygoteArguments.mRuntimeFlags, iArr, zygoteArguments.mMountExternal, zygoteArguments.mSeInfo, zygoteArguments.mNiceName, zygoteArguments.mStartChildZygote, zygoteArguments.mInstructionSet, zygoteArguments.mAppDataDir, zygoteArguments.mIsTopApp, zygoteArguments.mPkgDataInfoList, zygoteArguments.mAllowlistedDataInfoList, zygoteArguments.mBindMountAppDataDirs, zygoteArguments.mBindMountAppStorageDirs, zygoteArguments.mBindMountSyspropOverrides);
                setAppProcessName(zygoteArguments, TAG);
                android.os.Trace.traceEnd(64L);
                java.lang.Runnable zygoteInit = com.android.internal.os.ZygoteInit.zygoteInit(zygoteArguments.mTargetSdkVersion, zygoteArguments.mDisabledCompatChanges, zygoteArguments.mRemainingArgs, null);
                unblockSigTerm();
                return zygoteInit;
            } finally {
                libcore.io.IoUtils.closeQuietly(fileDescriptor);
            }
        } catch (java.lang.Throwable th2) {
            unblockSigTerm();
            throw th2;
        }
    }

    private static void blockSigTerm() {
        nativeBlockSigTerm();
    }

    private static void unblockSigTerm() {
        nativeUnblockSigTerm();
    }

    private static void boostUsapPriority() {
        nativeBoostUsapPriority();
    }

    static void setAppProcessName(com.android.internal.os.ZygoteArguments zygoteArguments, java.lang.String str) {
        if (zygoteArguments.mNiceName != null) {
            android.os.Process.setArgV0(zygoteArguments.mNiceName);
        } else if (zygoteArguments.mPackageName != null) {
            android.os.Process.setArgV0(zygoteArguments.mPackageName);
        } else {
            android.util.Log.w(str, "Unable to set package name.");
        }
    }

    private static void validateUsapCommand(com.android.internal.os.ZygoteArguments zygoteArguments) {
        if (zygoteArguments.mAbiListQuery) {
            throw new java.lang.IllegalArgumentException("Invalid command to USAP: --query-abi-list");
        }
        if (zygoteArguments.mPidQuery) {
            throw new java.lang.IllegalArgumentException("Invalid command to USAP: --get-pid");
        }
        if (zygoteArguments.mPreloadDefault) {
            throw new java.lang.IllegalArgumentException("Invalid command to USAP: --preload-default");
        }
        if (zygoteArguments.mPreloadPackage != null) {
            throw new java.lang.IllegalArgumentException("Invalid command to USAP: --preload-package");
        }
        if (zygoteArguments.mPreloadApp != null) {
            throw new java.lang.IllegalArgumentException("Invalid command to USAP: --preload-app");
        }
        if (zygoteArguments.mStartChildZygote) {
            throw new java.lang.IllegalArgumentException("Invalid command to USAP: --start-child-zygote");
        }
        if (zygoteArguments.mApiDenylistExemptions != null) {
            throw new java.lang.IllegalArgumentException("Invalid command to USAP: --set-api-denylist-exemptions");
        }
        if (zygoteArguments.mHiddenApiAccessLogSampleRate != -1) {
            throw new java.lang.IllegalArgumentException("Invalid command to USAP: --hidden-api-log-sampling-rate=");
        }
        if (zygoteArguments.mHiddenApiAccessStatslogSampleRate != -1) {
            throw new java.lang.IllegalArgumentException("Invalid command to USAP: --hidden-api-statslog-sampling-rate=");
        }
        if (zygoteArguments.mInvokeWith != null) {
            throw new java.lang.IllegalArgumentException("Invalid command to USAP: --invoke-with");
        }
        if (zygoteArguments.mPermittedCapabilities != 0 || zygoteArguments.mEffectiveCapabilities != 0) {
            throw new com.android.internal.os.ZygoteSecurityException("Client may not specify capabilities: permitted=0x" + java.lang.Long.toHexString(zygoteArguments.mPermittedCapabilities) + ", effective=0x" + java.lang.Long.toHexString(zygoteArguments.mEffectiveCapabilities));
        }
    }

    static int[] getUsapPipeFDs() {
        return nativeGetUsapPipeFDs();
    }

    static boolean removeUsapTableEntry(int i) {
        return nativeRemoveUsapTableEntry(i);
    }

    static int minChildUid(android.net.Credentials credentials) {
        return (credentials.getUid() == 1000 && android.os.FactoryTest.getMode() == 0) ? 1000 : 0;
    }

    static void applyUidSecurityPolicy(com.android.internal.os.ZygoteArguments zygoteArguments, android.net.Credentials credentials) throws com.android.internal.os.ZygoteSecurityException {
        if (zygoteArguments.mUidSpecified && zygoteArguments.mUid < minChildUid(credentials)) {
            throw new com.android.internal.os.ZygoteSecurityException("System UID may not launch process with UID < 1000");
        }
        if (!zygoteArguments.mUidSpecified) {
            zygoteArguments.mUid = credentials.getUid();
            zygoteArguments.mUidSpecified = true;
        }
        if (!zygoteArguments.mGidSpecified) {
            zygoteArguments.mGid = credentials.getGid();
            zygoteArguments.mGidSpecified = true;
        }
    }

    static void applyDebuggerSystemProperty(com.android.internal.os.ZygoteArguments zygoteArguments) {
        if (android.os.Build.IS_ENG || (android.os.Build.IS_USERDEBUG && ENABLE_JDWP)) {
            zygoteArguments.mRuntimeFlags |= 1;
            zygoteArguments.mRuntimeFlags |= 33554432;
        }
        if (android.os.Build.IS_ENG || (android.os.Build.IS_USERDEBUG && ENABLE_PTRACE)) {
            zygoteArguments.mRuntimeFlags |= 33554432;
        }
    }

    static void applyInvokeWithSecurityPolicy(com.android.internal.os.ZygoteArguments zygoteArguments, android.net.Credentials credentials) throws com.android.internal.os.ZygoteSecurityException {
        int uid = credentials.getUid();
        if (zygoteArguments.mInvokeWith != null && uid != 0 && (zygoteArguments.mRuntimeFlags & android.view.InputDevice.SOURCE_HDMI) == 0) {
            throw new com.android.internal.os.ZygoteSecurityException("Peer is permitted to specify an explicit invoke-with wrapper command only for debuggable applications.");
        }
    }

    public static java.lang.String getWrapProperty(java.lang.String str) {
        java.lang.String str2;
        if (str == null || str.isEmpty() || (str2 = android.os.SystemProperties.get("wrap." + str)) == null || str2.isEmpty()) {
            return null;
        }
        return str2;
    }

    static void applyInvokeWithSystemProperty(com.android.internal.os.ZygoteArguments zygoteArguments) {
        if (zygoteArguments.mInvokeWith == null) {
            zygoteArguments.mInvokeWith = getWrapProperty(zygoteArguments.mNiceName);
        }
    }

    static android.net.LocalServerSocket createManagedSocketFromInitSocket(java.lang.String str) {
        java.lang.String str2 = ANDROID_SOCKET_PREFIX + str;
        try {
            int parseInt = java.lang.Integer.parseInt(java.lang.System.getenv(str2));
            try {
                java.io.FileDescriptor fileDescriptor = new java.io.FileDescriptor();
                fileDescriptor.setInt$(parseInt);
                return new android.net.LocalServerSocket(fileDescriptor);
            } catch (java.io.IOException e) {
                throw new java.lang.RuntimeException("Error building socket from file descriptor: " + parseInt, e);
            }
        } catch (java.lang.RuntimeException e2) {
            throw new java.lang.RuntimeException("Socket unset or invalid: " + str2, e2);
        }
    }

    private static void callPostForkSystemServerHooks(int i) {
        dalvik.system.ZygoteHooks.postForkSystemServer(i);
    }

    private static void callPostForkChildHooks(int i, boolean z, boolean z2, java.lang.String str) {
        dalvik.system.ZygoteHooks.postForkChild(i, z, z2, str);
    }

    static void execShell(java.lang.String str) {
        java.lang.String[] strArr = {"/system/bin/sh", "-c", str};
        try {
            android.system.Os.execv(strArr[0], strArr);
        } catch (android.system.ErrnoException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    static void appendQuotedShellArgs(java.lang.StringBuilder sb, java.lang.String[] strArr) {
        for (java.lang.String str : strArr) {
            sb.append(" '").append(str.replace("'", "'\\''")).append("'");
        }
    }

    private static int memtagModeToZygoteMemtagLevel(int i) {
        switch (i) {
            case 1:
                return 1048576;
            case 2:
                return 1572864;
            default:
                return 0;
        }
    }

    private static boolean isCompatChangeEnabled(long j, android.content.pm.ApplicationInfo applicationInfo, com.android.internal.compat.IPlatformCompat iPlatformCompat, int i) {
        if (iPlatformCompat != null) {
            try {
                return iPlatformCompat.isChangeEnabled(j, applicationInfo);
            } catch (android.os.RemoteException e) {
            }
        }
        return i > 0 && applicationInfo.targetSdkVersion > i;
    }

    private static int getRequestedMemtagLevel(android.content.pm.ApplicationInfo applicationInfo, android.content.pm.ProcessInfo processInfo, com.android.internal.compat.IPlatformCompat iPlatformCompat) {
        java.lang.String str = android.os.SystemProperties.get("persist.arm64.memtag.app." + applicationInfo.packageName);
        if ("sync".equals(str)) {
            return 1572864;
        }
        if ("async".equals(str)) {
            return 1048576;
        }
        if ("off".equals(str)) {
            return 0;
        }
        if (processInfo == null || processInfo.memtagMode == -1) {
            if (applicationInfo.getMemtagMode() != -1) {
                return memtagModeToZygoteMemtagLevel(applicationInfo.getMemtagMode());
            }
            if (isCompatChangeEnabled(NATIVE_MEMTAG_SYNC, applicationInfo, iPlatformCompat, 0)) {
                return 1572864;
            }
            if (isCompatChangeEnabled(NATIVE_MEMTAG_ASYNC, applicationInfo, iPlatformCompat, 0)) {
                return 1048576;
            }
            if (!applicationInfo.allowsNativeHeapPointerTagging()) {
                return 0;
            }
            java.lang.String str2 = android.os.SystemProperties.get("persist.arm64.memtag.app_default");
            if ("sync".equals(str2)) {
                return 1572864;
            }
            if ("async".equals(str2)) {
                return 1048576;
            }
            return isCompatChangeEnabled(NATIVE_HEAP_POINTER_TAGGING, applicationInfo, iPlatformCompat, 29) ? 524288 : 0;
        }
        return memtagModeToZygoteMemtagLevel(processInfo.memtagMode);
    }

    private static int decideTaggingLevel(android.content.pm.ApplicationInfo applicationInfo, android.content.pm.ProcessInfo processInfo, com.android.internal.compat.IPlatformCompat iPlatformCompat) {
        int requestedMemtagLevel = getRequestedMemtagLevel(applicationInfo, processInfo, iPlatformCompat);
        if (nativeSupportsMemoryTagging()) {
            if (requestedMemtagLevel == 524288) {
                requestedMemtagLevel = 0;
            }
        } else if (nativeSupportsTaggedPointers()) {
            if (requestedMemtagLevel == 1048576 || requestedMemtagLevel == 1572864) {
                requestedMemtagLevel = 524288;
            }
        } else {
            requestedMemtagLevel = 0;
        }
        if (requestedMemtagLevel == 1048576 && ((android.os.Build.IS_USERDEBUG || android.os.Build.IS_ENG) && "sync".equals(android.os.SystemProperties.get("persist.arm64.memtag.default")))) {
            return 1572864;
        }
        return requestedMemtagLevel;
    }

    private static int decideGwpAsanLevel(android.content.pm.ApplicationInfo applicationInfo, android.content.pm.ProcessInfo processInfo, com.android.internal.compat.IPlatformCompat iPlatformCompat) {
        if (processInfo != null && processInfo.gwpAsanMode != -1) {
            return processInfo.gwpAsanMode == 1 ? 4194304 : 0;
        }
        if (applicationInfo.getGwpAsanMode() != -1) {
            return applicationInfo.getGwpAsanMode() == 1 ? 4194304 : 0;
        }
        if (isCompatChangeEnabled(GWP_ASAN, applicationInfo, iPlatformCompat, 0)) {
            return 4194304;
        }
        if ((applicationInfo.flags & 1) != 0) {
            return 2097152;
        }
        return 6291456;
    }

    private static boolean enableNativeHeapZeroInit(android.content.pm.ApplicationInfo applicationInfo, android.content.pm.ProcessInfo processInfo, com.android.internal.compat.IPlatformCompat iPlatformCompat) {
        return (processInfo == null || processInfo.nativeHeapZeroInitialized == -1) ? applicationInfo.getNativeHeapZeroInitialized() != -1 ? applicationInfo.getNativeHeapZeroInitialized() == 1 : isCompatChangeEnabled(NATIVE_HEAP_ZERO_INIT, applicationInfo, iPlatformCompat, 0) : processInfo.nativeHeapZeroInitialized == 1;
    }

    public static int getMemorySafetyRuntimeFlags(android.content.pm.ApplicationInfo applicationInfo, android.content.pm.ProcessInfo processInfo, java.lang.String str, com.android.internal.compat.IPlatformCompat iPlatformCompat) {
        int decideGwpAsanLevel = decideGwpAsanLevel(applicationInfo, processInfo, iPlatformCompat);
        if (str == null || str.equals("arm64")) {
            decideGwpAsanLevel |= decideTaggingLevel(applicationInfo, processInfo, iPlatformCompat);
        }
        if (enableNativeHeapZeroInit(applicationInfo, processInfo, iPlatformCompat)) {
            return decideGwpAsanLevel | 8388608;
        }
        return decideGwpAsanLevel;
    }

    public static int getMemorySafetyRuntimeFlagsForSecondaryZygote(android.content.pm.ApplicationInfo applicationInfo, android.content.pm.ProcessInfo processInfo) {
        com.android.internal.compat.IPlatformCompat asInterface = com.android.internal.compat.IPlatformCompat.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.PLATFORM_COMPAT_SERVICE));
        int memorySafetyRuntimeFlags = getMemorySafetyRuntimeFlags(applicationInfo, processInfo, null, asInterface);
        if ((1572864 & memorySafetyRuntimeFlags) == 524288 && isCompatChangeEnabled(NATIVE_HEAP_POINTER_TAGGING_SECONDARY_ZYGOTE, applicationInfo, asInterface, 31)) {
            return ((-1572865) & memorySafetyRuntimeFlags) | 0;
        }
        return memorySafetyRuntimeFlags;
    }
}
