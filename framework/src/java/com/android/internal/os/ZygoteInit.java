package com.android.internal.os;

/* loaded from: classes4.dex */
public class ZygoteInit {
    private static final java.lang.String ABI_LIST_ARG = "--abi-list=";
    private static final int LOG_BOOT_PROGRESS_PRELOAD_END = 3030;
    private static final int LOG_BOOT_PROGRESS_PRELOAD_START = 3020;
    private static final java.lang.String PRELOADED_CLASSES = "/system/etc/preloaded-classes";
    private static final java.lang.String PROPERTY_DISABLE_GRAPHICS_DRIVER_PRELOADING = "ro.zygote.disable_gl_preload";
    private static final int ROOT_GID = 0;
    private static final int ROOT_UID = 0;
    private static final java.lang.String SOCKET_NAME_ARG = "--socket-name=";
    private static final int UNPRIVILEGED_GID = 9999;
    private static final int UNPRIVILEGED_UID = 9999;
    private static boolean sPreloadComplete;
    private static final java.lang.String TAG = "Zygote";
    private static final boolean LOGGING_DEBUG = android.util.Log.isLoggable(TAG, 3);
    private static java.lang.ClassLoader sCachedSystemServerClassLoader = null;

    private static native void nativePreloadAppProcessHALs();

    static native void nativePreloadGraphicsDriver();

    private static native void nativeZygoteInit();

    static void preload(android.util.TimingsTraceLog timingsTraceLog) {
        android.util.Log.d(TAG, "begin preload");
        timingsTraceLog.traceBegin("BeginPreload");
        beginPreload();
        timingsTraceLog.traceEnd();
        timingsTraceLog.traceBegin("PreloadClasses");
        preloadClasses();
        timingsTraceLog.traceEnd();
        timingsTraceLog.traceBegin("CacheNonBootClasspathClassLoaders");
        cacheNonBootClasspathClassLoaders();
        timingsTraceLog.traceEnd();
        timingsTraceLog.traceBegin("PreloadResources");
        android.content.res.Resources.preloadResources();
        timingsTraceLog.traceEnd();
        android.os.Trace.traceBegin(16384L, "PreloadAppProcessHALs");
        nativePreloadAppProcessHALs();
        android.os.Trace.traceEnd(16384L);
        android.os.Trace.traceBegin(16384L, "PreloadGraphicsDriver");
        maybePreloadGraphicsDriver();
        android.os.Trace.traceEnd(16384L);
        preloadSharedLibraries();
        preloadTextResources();
        android.webkit.WebViewFactory.prepareWebViewInZygote();
        endPreload();
        warmUpJcaProviders();
        android.util.Log.d(TAG, "end preload");
        sPreloadComplete = true;
    }

    static void lazyPreload() {
        com.android.internal.util.Preconditions.checkState(!sPreloadComplete);
        android.util.Log.i(TAG, "Lazily preloading resources.");
        preload(new android.util.TimingsTraceLog("ZygoteInitTiming_lazy", 16384L));
    }

    private static void beginPreload() {
        android.util.Log.i(TAG, "Calling ZygoteHooks.beginPreload()");
        dalvik.system.ZygoteHooks.onBeginPreload();
    }

    private static void endPreload() {
        dalvik.system.ZygoteHooks.onEndPreload();
        android.util.Log.i(TAG, "Called ZygoteHooks.endPreload()");
    }

    private static void preloadSharedLibraries() {
        android.util.Log.i(TAG, "Preloading shared libraries...");
        java.lang.System.loadLibrary("android");
        java.lang.System.loadLibrary("jnigraphics");
        if (!android.os.SystemProperties.getBoolean("config.disable_renderscript", false)) {
            java.lang.System.loadLibrary("compiler_rt");
        }
    }

    private static void maybePreloadGraphicsDriver() {
        if (!android.os.SystemProperties.getBoolean(PROPERTY_DISABLE_GRAPHICS_DRIVER_PRELOADING, false)) {
            nativePreloadGraphicsDriver();
        }
    }

    private static void preloadTextResources() {
        android.text.Hyphenator.init();
        android.widget.TextView.preloadFontCache();
    }

    private static void warmUpJcaProviders() {
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        android.os.Trace.traceBegin(16384L, "Starting installation of AndroidKeyStoreProvider");
        android.security.keystore2.AndroidKeyStoreProvider.install();
        android.util.Log.i(TAG, "Installed AndroidKeyStoreProvider in " + (android.os.SystemClock.uptimeMillis() - uptimeMillis) + "ms.");
        android.os.Trace.traceEnd(16384L);
        long uptimeMillis2 = android.os.SystemClock.uptimeMillis();
        android.os.Trace.traceBegin(16384L, "Starting warm up of JCA providers");
        for (java.security.Provider provider : java.security.Security.getProviders()) {
            provider.warmUpServiceProvision();
        }
        android.util.Log.i(TAG, "Warmed up JCA providers in " + (android.os.SystemClock.uptimeMillis() - uptimeMillis2) + "ms.");
        android.os.Trace.traceEnd(16384L);
    }

    private static boolean isExperimentEnabled(java.lang.String str) {
        return android.os.SystemProperties.getBoolean("persist.device_config.runtime_native_boot." + str, android.os.SystemProperties.getBoolean(com.android.internal.os.ZygoteConfig.PROPERTY_PREFIX_SYSTEM + str, false));
    }

    static boolean shouldProfileSystemServer() {
        return isExperimentEnabled("profilesystemserver");
    }

    private static void preloadClasses() {
        boolean z;
        int i;
        int i2;
        dalvik.system.VMRuntime runtime = dalvik.system.VMRuntime.getRuntime();
        try {
            java.io.FileInputStream fileInputStream = new java.io.FileInputStream(PRELOADED_CLASSES);
            android.util.Log.i(TAG, "Preloading classes...");
            long uptimeMillis = android.os.SystemClock.uptimeMillis();
            int i3 = android.system.Os.getuid();
            int i4 = android.system.Os.getgid();
            if (i3 == 0 && i4 == 0) {
                try {
                    android.system.Os.setregid(0, 9999);
                    android.system.Os.setreuid(0, 9999);
                    z = true;
                } catch (android.system.ErrnoException e) {
                    throw new java.lang.RuntimeException("Failed to drop root", e);
                }
            } else {
                z = false;
            }
            long j = 16384;
            try {
                try {
                    java.io.BufferedReader bufferedReader = new java.io.BufferedReader(new java.io.InputStreamReader(fileInputStream), 256);
                    int i5 = 0;
                    int i6 = 0;
                    while (true) {
                        java.lang.String readLine = bufferedReader.readLine();
                        if (readLine == null) {
                            break;
                        }
                        java.lang.String trim = readLine.trim();
                        if (trim.startsWith("#")) {
                            i = i6;
                        } else if (trim.equals("")) {
                            i = i6;
                        } else {
                            android.os.Trace.traceBegin(j, trim);
                            try {
                                java.lang.Class.forName(trim, true, null);
                                i5++;
                            } catch (java.lang.ClassNotFoundException e2) {
                                if (!trim.contains("$$Lambda$")) {
                                    i2 = i6;
                                    android.util.Log.w(TAG, "Class not found for preloading: " + trim);
                                } else if (LOGGING_DEBUG) {
                                    i6++;
                                } else {
                                    i2 = i6;
                                }
                                i6 = i2;
                            } catch (java.lang.UnsatisfiedLinkError e3) {
                                android.util.Log.w(TAG, "Problem preloading " + trim + ": " + e3);
                            } catch (java.lang.Throwable th) {
                                android.util.Log.e(TAG, "Error preloading " + trim + android.media.MediaMetrics.SEPARATOR, th);
                                if (th instanceof java.lang.Error) {
                                    throw ((java.lang.Error) th);
                                }
                                if (!(th instanceof java.lang.RuntimeException)) {
                                    throw new java.lang.RuntimeException(th);
                                }
                                throw ((java.lang.RuntimeException) th);
                            }
                            android.os.Trace.traceEnd(16384L);
                            j = 16384;
                        }
                        i6 = i;
                        j = 16384;
                    }
                    int i7 = i6;
                    android.util.Log.i(TAG, "...preloaded " + i5 + " classes in " + (android.os.SystemClock.uptimeMillis() - uptimeMillis) + "ms.");
                    if (LOGGING_DEBUG && i7 != 0) {
                        android.util.Log.i(TAG, "Unresolved lambda preloads: " + i7);
                    }
                    libcore.io.IoUtils.closeQuietly(fileInputStream);
                    android.os.Trace.traceBegin(16384L, "PreloadDexCaches");
                    runtime.preloadDexCaches();
                    android.os.Trace.traceEnd(16384L);
                    if (isExperimentEnabled("profilebootclasspath")) {
                        android.os.Trace.traceBegin(16384L, "ResetJitCounters");
                        dalvik.system.VMRuntime.resetJitCounters();
                        android.os.Trace.traceEnd(16384L);
                    }
                    if (z) {
                        try {
                            android.system.Os.setreuid(0, 0);
                            android.system.Os.setregid(0, 0);
                        } catch (android.system.ErrnoException e4) {
                            throw new java.lang.RuntimeException("Failed to restore root", e4);
                        }
                    }
                } catch (java.io.IOException e5) {
                    android.util.Log.e(TAG, "Error reading /system/etc/preloaded-classes.", e5);
                    libcore.io.IoUtils.closeQuietly(fileInputStream);
                    android.os.Trace.traceBegin(16384L, "PreloadDexCaches");
                    runtime.preloadDexCaches();
                    android.os.Trace.traceEnd(16384L);
                    if (isExperimentEnabled("profilebootclasspath")) {
                        android.os.Trace.traceBegin(16384L, "ResetJitCounters");
                        dalvik.system.VMRuntime.resetJitCounters();
                        android.os.Trace.traceEnd(16384L);
                    }
                    if (z) {
                        try {
                            android.system.Os.setreuid(0, 0);
                            android.system.Os.setregid(0, 0);
                        } catch (android.system.ErrnoException e6) {
                            throw new java.lang.RuntimeException("Failed to restore root", e6);
                        }
                    }
                }
            } catch (java.lang.Throwable th2) {
                libcore.io.IoUtils.closeQuietly(fileInputStream);
                android.os.Trace.traceBegin(16384L, "PreloadDexCaches");
                runtime.preloadDexCaches();
                android.os.Trace.traceEnd(16384L);
                if (isExperimentEnabled("profilebootclasspath")) {
                    android.os.Trace.traceBegin(16384L, "ResetJitCounters");
                    dalvik.system.VMRuntime.resetJitCounters();
                    android.os.Trace.traceEnd(16384L);
                }
                if (z) {
                    try {
                        android.system.Os.setreuid(0, 0);
                        android.system.Os.setregid(0, 0);
                    } catch (android.system.ErrnoException e7) {
                        throw new java.lang.RuntimeException("Failed to restore root", e7);
                    }
                }
                throw th2;
            }
        } catch (java.io.FileNotFoundException e8) {
            android.util.Log.e(TAG, "Couldn't find /system/etc/preloaded-classes.");
        }
    }

    private static void cacheNonBootClasspathClassLoaders() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add(new android.content.pm.SharedLibraryInfo("/system/framework/android.hidl.base-V1.0-java.jar", (java.lang.String) null, (java.util.List<java.lang.String>) null, (java.lang.String) null, 0L, 0, (android.content.pm.VersionedPackage) null, (java.util.List<android.content.pm.VersionedPackage>) null, (java.util.List<android.content.pm.SharedLibraryInfo>) null, false));
        arrayList.add(new android.content.pm.SharedLibraryInfo("/system/framework/android.hidl.manager-V1.0-java.jar", (java.lang.String) null, (java.util.List<java.lang.String>) null, (java.lang.String) null, 0L, 0, (android.content.pm.VersionedPackage) null, (java.util.List<android.content.pm.VersionedPackage>) null, (java.util.List<android.content.pm.SharedLibraryInfo>) null, false));
        arrayList.add(new android.content.pm.SharedLibraryInfo("/system/framework/android.test.base.jar", (java.lang.String) null, (java.util.List<java.lang.String>) null, (java.lang.String) null, 0L, 0, (android.content.pm.VersionedPackage) null, (java.util.List<android.content.pm.VersionedPackage>) null, (java.util.List<android.content.pm.SharedLibraryInfo>) null, false));
        if (android.view.WindowManager.hasWindowExtensionsEnabled()) {
            java.lang.String path = new java.io.File(android.os.Environment.getSystemExtDirectory(), "framework").getPath();
            arrayList.add(new android.content.pm.SharedLibraryInfo(path + "/androidx.window.extensions.jar", "androidx.window.extensions", (java.util.List<java.lang.String>) null, "androidx.window.extensions", -1L, 0, (android.content.pm.VersionedPackage) null, (java.util.List<android.content.pm.VersionedPackage>) null, (java.util.List<android.content.pm.SharedLibraryInfo>) null, false));
            arrayList.add(new android.content.pm.SharedLibraryInfo(path + "/androidx.window.sidecar.jar", "androidx.window.sidecar", (java.util.List<java.lang.String>) null, "androidx.window.sidecar", -1L, 0, (android.content.pm.VersionedPackage) null, (java.util.List<android.content.pm.VersionedPackage>) null, (java.util.List<android.content.pm.SharedLibraryInfo>) null, false));
        }
        android.app.ApplicationLoaders.getDefault().createAndCacheNonBootclasspathSystemClassLoaders(arrayList);
    }

    private static void gcAndFinalize() {
        dalvik.system.ZygoteHooks.gcAndFinalize();
    }

    private static java.lang.Runnable handleSystemServerProcess(com.android.internal.os.ZygoteArguments zygoteArguments) {
        java.lang.String[] strArr;
        java.lang.String str;
        android.system.Os.umask(android.system.OsConstants.S_IRWXG | android.system.OsConstants.S_IRWXO);
        if (zygoteArguments.mNiceName != null) {
            android.os.Process.setArgV0(zygoteArguments.mNiceName);
        }
        java.lang.String str2 = android.system.Os.getenv("SYSTEMSERVERCLASSPATH");
        if (str2 != null && shouldProfileSystemServer() && (android.os.Build.IS_USERDEBUG || android.os.Build.IS_ENG)) {
            try {
                android.util.Log.d(TAG, "Preparing system server profile");
                java.lang.String str3 = android.system.Os.getenv("STANDALONE_SYSTEMSERVER_JARS");
                if (str3 != null) {
                    str = java.lang.String.join(":", str2, str3);
                } else {
                    str = str2;
                }
                prepareSystemServerProfile(str);
            } catch (java.lang.Exception e) {
                android.util.Log.wtf(TAG, "Failed to set up system server profile", e);
            }
        }
        if (zygoteArguments.mInvokeWith != null) {
            java.lang.String[] strArr2 = zygoteArguments.mRemainingArgs;
            if (str2 == null) {
                strArr = strArr2;
            } else {
                java.lang.String[] strArr3 = new java.lang.String[strArr2.length + 2];
                strArr3[0] = "-cp";
                strArr3[1] = str2;
                java.lang.System.arraycopy(strArr2, 0, strArr3, 2, strArr2.length);
                strArr = strArr3;
            }
            com.android.internal.os.WrapperInit.execApplication(zygoteArguments.mInvokeWith, zygoteArguments.mNiceName, zygoteArguments.mTargetSdkVersion, dalvik.system.VMRuntime.getCurrentInstructionSet(), null, strArr);
            throw new java.lang.IllegalStateException("Unexpected return from WrapperInit.execApplication");
        }
        java.lang.ClassLoader orCreateSystemServerClassLoader = getOrCreateSystemServerClassLoader();
        if (orCreateSystemServerClassLoader != null) {
            java.lang.Thread.currentThread().setContextClassLoader(orCreateSystemServerClassLoader);
        }
        return zygoteInit(zygoteArguments.mTargetSdkVersion, zygoteArguments.mDisabledCompatChanges, zygoteArguments.mRemainingArgs, orCreateSystemServerClassLoader);
    }

    private static java.lang.ClassLoader getOrCreateSystemServerClassLoader() {
        java.lang.String str;
        if (sCachedSystemServerClassLoader == null && (str = android.system.Os.getenv("SYSTEMSERVERCLASSPATH")) != null) {
            sCachedSystemServerClassLoader = createPathClassLoader(str, 10000);
        }
        return sCachedSystemServerClassLoader;
    }

    private static void prefetchStandaloneSystemServerJars() {
        if (shouldProfileSystemServer()) {
            return;
        }
        java.lang.String str = android.system.Os.getenv("STANDALONE_SYSTEMSERVER_JARS");
        if (android.text.TextUtils.isEmpty(str)) {
            return;
        }
        for (java.lang.String str2 : str.split(":")) {
            try {
                com.android.internal.os.SystemServerClassLoaderFactory.createClassLoader(str2, getOrCreateSystemServerClassLoader());
            } catch (java.lang.Error e) {
                android.util.Log.e(TAG, java.lang.String.format("Failed to prefetch standalone system server jar \"%s\": %s", str2, e.toString()));
            }
        }
    }

    private static void prepareSystemServerProfile(java.lang.String str) throws android.os.RemoteException {
        if (str.isEmpty()) {
            return;
        }
        java.lang.String[] split = str.split(":");
        android.os.IInstalld.Stub.asInterface(android.os.ServiceManager.getService("installd")).prepareAppProfile("android", 0, android.os.UserHandle.getAppId(1000), "primary.prof", split[0], null);
        dalvik.system.VMRuntime.registerAppInfo("android", new java.io.File(android.os.Environment.getDataProfilesDePackageDirectory(0, "android"), "primary.prof").getAbsolutePath(), new java.io.File(android.os.Environment.getDataProfilesDePackageDirectory(0, "android"), "primary.prof").getAbsolutePath(), split, 1);
    }

    public static void setApiDenylistExemptions(java.lang.String[] strArr) {
        dalvik.system.VMRuntime.getRuntime().setHiddenApiExemptions(strArr);
    }

    public static void setHiddenApiAccessLogSampleRate(int i) {
        dalvik.system.VMRuntime.getRuntime().setHiddenApiAccessLogSamplingRate(i);
    }

    public static void setHiddenApiUsageLogger(dalvik.system.VMRuntime.HiddenApiUsageLogger hiddenApiUsageLogger) {
        dalvik.system.VMRuntime.getRuntime();
        dalvik.system.VMRuntime.setHiddenApiUsageLogger(hiddenApiUsageLogger);
    }

    static java.lang.ClassLoader createPathClassLoader(java.lang.String str, int i) {
        java.lang.String property = java.lang.System.getProperty("java.library.path");
        return com.android.internal.os.ClassLoaderFactory.createClassLoader(str, property, property, java.lang.ClassLoader.getSystemClassLoader().getParent(), i, true, null);
    }

    private static java.lang.Runnable forkSystemServer(java.lang.String str, java.lang.String str2, com.android.internal.os.ZygoteServer zygoteServer) {
        long posixCapabilitiesAsBits = posixCapabilitiesAsBits(android.system.OsConstants.CAP_IPC_LOCK, android.system.OsConstants.CAP_KILL, android.system.OsConstants.CAP_NET_ADMIN, android.system.OsConstants.CAP_NET_BIND_SERVICE, android.system.OsConstants.CAP_NET_BROADCAST, android.system.OsConstants.CAP_NET_RAW, android.system.OsConstants.CAP_SYS_MODULE, android.system.OsConstants.CAP_SYS_NICE, android.system.OsConstants.CAP_SYS_PTRACE, android.system.OsConstants.CAP_SYS_TIME, android.system.OsConstants.CAP_SYS_TTY_CONFIG, android.system.OsConstants.CAP_WAKE_ALARM, android.system.OsConstants.CAP_BLOCK_SUSPEND);
        try {
            android.system.StructCapUserData[] capget = android.system.Os.capget(new android.system.StructCapUserHeader(android.system.OsConstants._LINUX_CAPABILITY_VERSION_3, 0));
            long unsignedLong = posixCapabilitiesAsBits & (java.lang.Integer.toUnsignedLong(capget[0].effective) | (java.lang.Integer.toUnsignedLong(capget[1].effective) << 32));
            try {
                com.android.internal.os.ZygoteCommandBuffer zygoteCommandBuffer = new com.android.internal.os.ZygoteCommandBuffer(new java.lang.String[]{"--setuid=1000", "--setgid=1000", "--setgroups=1001,1002,1003,1004,1005,1006,1007,1008,1009,1010,1018,1021,1023,1024,1032,1065,3001,3002,3003,3005,3006,3007,3009,3010,3011,3012", "--capabilities=" + unsignedLong + "," + unsignedLong, "--nice-name=system_server", "--runtime-args", "--target-sdk-version=10000", "com.android.server.SystemServer"});
                try {
                    com.android.internal.os.ZygoteArguments zygoteArguments = com.android.internal.os.ZygoteArguments.getInstance(zygoteCommandBuffer);
                    zygoteCommandBuffer.close();
                    com.android.internal.os.Zygote.applyDebuggerSystemProperty(zygoteArguments);
                    com.android.internal.os.Zygote.applyInvokeWithSystemProperty(zygoteArguments);
                    if (com.android.internal.os.Zygote.nativeSupportsMemoryTagging()) {
                        java.lang.String str3 = android.os.SystemProperties.get("persist.arm64.memtag.system_server", "");
                        if (str3.isEmpty()) {
                            str3 = android.os.SystemProperties.get("persist.arm64.memtag.default", "async");
                        }
                        if (str3.equals("async")) {
                            zygoteArguments.mRuntimeFlags |= 1048576;
                        } else if (str3.equals("sync")) {
                            zygoteArguments.mRuntimeFlags |= 1572864;
                        } else if (!str3.equals("off")) {
                            zygoteArguments.mRuntimeFlags |= com.android.internal.os.Zygote.nativeCurrentTaggingLevel();
                            android.util.Slog.e(TAG, "Unknown memory tag level for the system server: \"" + str3 + "\"");
                        }
                    } else if (com.android.internal.os.Zygote.nativeSupportsTaggedPointers()) {
                        zygoteArguments.mRuntimeFlags |= 524288;
                    }
                    zygoteArguments.mRuntimeFlags |= 2097152;
                    if (shouldProfileSystemServer()) {
                        zygoteArguments.mRuntimeFlags |= 16384;
                    }
                    if (com.android.internal.os.Zygote.forkSystemServer(zygoteArguments.mUid, zygoteArguments.mGid, zygoteArguments.mGids, zygoteArguments.mRuntimeFlags, null, zygoteArguments.mPermittedCapabilities, zygoteArguments.mEffectiveCapabilities) == 0) {
                        if (hasSecondZygote(str)) {
                            waitForSecondaryZygote(str2);
                        }
                        zygoteServer.closeServerSocket();
                        return handleSystemServerProcess(zygoteArguments);
                    }
                    return null;
                } catch (java.io.EOFException e) {
                    throw new java.lang.AssertionError("Unexpected argument error for forking system server", e);
                }
            } catch (java.lang.IllegalArgumentException e2) {
                throw new java.lang.RuntimeException(e2);
            }
        } catch (android.system.ErrnoException e3) {
            throw new java.lang.RuntimeException("Failed to capget()", e3);
        }
    }

    private static long posixCapabilitiesAsBits(int... iArr) {
        long j = 0;
        for (int i : iArr) {
            if (i < 0 || i > android.system.OsConstants.CAP_LAST_CAP) {
                throw new java.lang.IllegalArgumentException(java.lang.String.valueOf(i));
            }
            j |= 1 << i;
        }
        return j;
    }

    public static void main(java.lang.String[] strArr) {
        com.android.internal.os.ZygoteServer zygoteServer;
        dalvik.system.ZygoteHooks.startZygoteNoThreadCreation();
        boolean z = false;
        try {
            android.system.Os.setpgid(0, 0);
            try {
                long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
                boolean equals = "1".equals(android.os.SystemProperties.get("sys.boot_completed"));
                android.util.TimingsTraceLog timingsTraceLog = new android.util.TimingsTraceLog(android.os.Process.is64Bit() ? "Zygote64Timing" : "Zygote32Timing", 16384L);
                timingsTraceLog.traceBegin("ZygoteInit");
                com.android.internal.os.RuntimeInit.preForkInit();
                boolean z2 = false;
                java.lang.String str = com.android.internal.os.Zygote.PRIMARY_SOCKET_NAME;
                java.lang.String str2 = null;
                for (int i = 1; i < strArr.length; i++) {
                    if ("start-system-server".equals(strArr[i])) {
                        z2 = true;
                    } else if ("--enable-lazy-preload".equals(strArr[i])) {
                        z = true;
                    } else if (strArr[i].startsWith("--abi-list=")) {
                        str2 = strArr[i].substring("--abi-list=".length());
                    } else if (strArr[i].startsWith(SOCKET_NAME_ARG)) {
                        str = strArr[i].substring(SOCKET_NAME_ARG.length());
                    } else {
                        throw new java.lang.RuntimeException("Unknown command line argument: " + strArr[i]);
                    }
                }
                boolean equals2 = str.equals(com.android.internal.os.Zygote.PRIMARY_SOCKET_NAME);
                if (!equals) {
                    if (equals2) {
                        com.android.internal.util.FrameworkStatsLog.write(240, 17, elapsedRealtime);
                    } else if (str.equals(com.android.internal.os.Zygote.SECONDARY_SOCKET_NAME)) {
                        com.android.internal.util.FrameworkStatsLog.write(240, 18, elapsedRealtime);
                    }
                }
                if (str2 == null) {
                    throw new java.lang.RuntimeException("No ABI list supplied.");
                }
                if (!z) {
                    timingsTraceLog.traceBegin("ZygotePreload");
                    android.util.EventLog.writeEvent(LOG_BOOT_PROGRESS_PRELOAD_START, android.os.SystemClock.uptimeMillis());
                    preload(timingsTraceLog);
                    android.util.EventLog.writeEvent(LOG_BOOT_PROGRESS_PRELOAD_END, android.os.SystemClock.uptimeMillis());
                    timingsTraceLog.traceEnd();
                }
                timingsTraceLog.traceBegin("PostZygoteInitGC");
                gcAndFinalize();
                timingsTraceLog.traceEnd();
                timingsTraceLog.traceEnd();
                com.android.internal.os.Zygote.initNativeState(equals2);
                dalvik.system.ZygoteHooks.stopZygoteNoThreadCreation();
                com.android.internal.os.ZygoteServer zygoteServer2 = new com.android.internal.os.ZygoteServer(equals2);
                if (z2) {
                    try {
                        java.lang.Runnable forkSystemServer = forkSystemServer(str2, str, zygoteServer2);
                        if (forkSystemServer != null) {
                            forkSystemServer.run();
                            zygoteServer2.closeServerSocket();
                            return;
                        }
                    } catch (java.lang.Throwable th) {
                        th = th;
                        zygoteServer = zygoteServer2;
                        try {
                            android.util.Log.e(TAG, "System zygote died with fatal exception", th);
                            throw th;
                        } catch (java.lang.Throwable th2) {
                            if (zygoteServer != null) {
                                zygoteServer.closeServerSocket();
                            }
                            throw th2;
                        }
                    }
                }
                android.util.Log.i(TAG, "Accepting command socket connections");
                java.lang.Runnable runSelectLoop = zygoteServer2.runSelectLoop(str2);
                zygoteServer2.closeServerSocket();
                if (runSelectLoop != null) {
                    runSelectLoop.run();
                }
            } catch (java.lang.Throwable th3) {
                th = th3;
                zygoteServer = null;
            }
        } catch (android.system.ErrnoException e) {
            throw new java.lang.RuntimeException("Failed to setpgid(0,0)", e);
        }
    }

    private static boolean hasSecondZygote(java.lang.String str) {
        return !android.os.SystemProperties.get("ro.product.cpu.abilist").equals(str);
    }

    private static void waitForSecondaryZygote(java.lang.String str) {
        java.lang.String str2 = com.android.internal.os.Zygote.PRIMARY_SOCKET_NAME;
        if (com.android.internal.os.Zygote.PRIMARY_SOCKET_NAME.equals(str)) {
            str2 = com.android.internal.os.Zygote.SECONDARY_SOCKET_NAME;
        }
        android.os.ZygoteProcess.waitForConnectionToZygote(str2);
    }

    static boolean isPreloadComplete() {
        return sPreloadComplete;
    }

    private ZygoteInit() {
    }

    public static java.lang.Runnable zygoteInit(int i, long[] jArr, java.lang.String[] strArr, java.lang.ClassLoader classLoader) {
        android.os.Trace.traceBegin(64L, "ZygoteInit");
        com.android.internal.os.RuntimeInit.redirectLogStreams();
        com.android.internal.os.RuntimeInit.commonInit();
        nativeZygoteInit();
        return com.android.internal.os.RuntimeInit.applicationInit(i, jArr, strArr, classLoader);
    }

    static java.lang.Runnable childZygoteInit(java.lang.String[] strArr) {
        com.android.internal.os.RuntimeInit.Arguments arguments = new com.android.internal.os.RuntimeInit.Arguments(strArr);
        return com.android.internal.os.RuntimeInit.findStaticMain(arguments.startClass, arguments.startArgs, null);
    }
}
