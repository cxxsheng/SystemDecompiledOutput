package com.android.server;

@android.ravenwood.annotation.RavenwoodKeepPartialClass
@android.ravenwood.annotation.RavenwoodKeepStaticInitializer
/* loaded from: classes.dex */
public final class SystemServiceManager implements android.util.Dumpable {
    private static final boolean DEBUG = false;
    private static final int DEFAULT_MAX_USER_POOL_THREADS = 3;
    private static final int SERVICE_CALL_WARN_TIME_MS = 50;
    private static final java.lang.String TAG = com.android.server.SystemServiceManager.class.getSimpleName();
    private static final java.lang.String USER_COMPLETED_EVENT = "CompletedEvent";
    private static final long USER_POOL_SHUTDOWN_TIMEOUT_SECONDS = 30;
    private static final java.lang.String USER_STARTING = "Start";
    private static final java.lang.String USER_STOPPED = "Cleanup";
    private static final java.lang.String USER_STOPPING = "Stop";
    private static final java.lang.String USER_SWITCHING = "Switch";
    private static final java.lang.String USER_UNLOCKED = "Unlocked";
    private static final java.lang.String USER_UNLOCKING = "Unlocking";
    private static volatile int sOtherServicesStartIndex;
    private static java.io.File sSystemDir;
    private final android.content.Context mContext;

    @com.android.internal.annotations.GuardedBy({"mTargetUsers"})
    @android.annotation.Nullable
    private com.android.server.SystemService.TargetUser mCurrentUser;
    private boolean mRuntimeRestarted;
    private long mRuntimeStartElapsedTime;
    private long mRuntimeStartUptime;
    private boolean mSafeMode;
    private com.android.server.pm.UserManagerInternal mUserManagerInternal;
    private int mCurrentPhase = -1;

    @com.android.internal.annotations.GuardedBy({"mTargetUsers"})
    private final android.util.SparseArray<com.android.server.SystemService.TargetUser> mTargetUsers = new android.util.SparseArray<>();
    private java.util.List<com.android.server.SystemService> mServices = new java.util.ArrayList();
    private java.util.Set<java.lang.String> mServiceClassnames = new android.util.ArraySet();
    private final int mNumUserPoolThreads = java.lang.Math.min(java.lang.Runtime.getRuntime().availableProcessors(), 3);

    @android.ravenwood.annotation.RavenwoodKeep
    public SystemServiceManager(android.content.Context context) {
        this.mContext = context;
    }

    @android.ravenwood.annotation.RavenwoodKeep
    public com.android.server.SystemService startService(java.lang.String str) {
        return startService(loadClassFromLoader(str, com.android.server.SystemServiceManager.class.getClassLoader()));
    }

    public com.android.server.SystemService startServiceFromJar(java.lang.String str, java.lang.String str2) {
        return startService(loadClassFromLoader(str, com.android.internal.os.SystemServerClassLoaderFactory.getOrCreateClassLoader(str2, com.android.server.SystemServiceManager.class.getClassLoader(), isJarInTestApex(str2))));
    }

    private boolean isJarInTestApex(java.lang.String str) {
        java.nio.file.Path path = java.nio.file.Paths.get(str, new java.lang.String[0]);
        if (path.getNameCount() >= 2 && path.getName(0).toString().equals("apex")) {
            try {
                if ((this.mContext.getPackageManager().getPackageInfo(com.android.server.pm.ApexManager.getInstance().getActivePackageNameForApexModuleName(path.getName(1).toString()), android.content.pm.PackageManager.PackageInfoFlags.of(1073741824L)).applicationInfo.flags & 256) == 0) {
                    return false;
                }
                return true;
            } catch (java.lang.Exception e) {
            }
        }
        return false;
    }

    @android.ravenwood.annotation.RavenwoodKeep
    private static java.lang.Class<com.android.server.SystemService> loadClassFromLoader(java.lang.String str, java.lang.ClassLoader classLoader) {
        try {
            return java.lang.Class.forName(str, true, classLoader);
        } catch (java.lang.ClassNotFoundException e) {
            throw new java.lang.RuntimeException("Failed to create service " + str + " from class loader " + classLoader.toString() + ": service class not found, usually indicates that the caller should have called PackageManager.hasSystemFeature() to check whether the feature is available on this device before trying to start the services that implement it. Also ensure that the correct path for the classloader is supplied, if applicable.", e);
        }
    }

    @android.ravenwood.annotation.RavenwoodKeep
    public <T extends com.android.server.SystemService> T startService(java.lang.Class<T> cls) {
        try {
            java.lang.String name = cls.getName();
            android.util.Slog.i(TAG, "Starting " + name);
            android.os.Trace.traceBegin(524288L, "StartService " + name);
            if (!com.android.server.SystemService.class.isAssignableFrom(cls)) {
                throw new java.lang.RuntimeException("Failed to create " + name + ": service must extend " + com.android.server.SystemService.class.getName());
            }
            try {
                try {
                    T newInstance = cls.getConstructor(android.content.Context.class).newInstance(this.mContext);
                    startService(newInstance);
                    return newInstance;
                } catch (java.lang.InstantiationException e) {
                    throw new java.lang.RuntimeException("Failed to create service " + name + ": service could not be instantiated", e);
                } catch (java.lang.NoSuchMethodException e2) {
                    throw new java.lang.RuntimeException("Failed to create service " + name + ": service must have a public constructor with a Context argument", e2);
                }
            } catch (java.lang.IllegalAccessException e3) {
                throw new java.lang.RuntimeException("Failed to create service " + name + ": service must have a public constructor with a Context argument", e3);
            } catch (java.lang.reflect.InvocationTargetException e4) {
                throw new java.lang.RuntimeException("Failed to create service " + name + ": service constructor threw an exception", e4);
            }
        } finally {
            android.os.Trace.traceEnd(524288L);
        }
    }

    @android.ravenwood.annotation.RavenwoodKeep
    public void startService(@android.annotation.NonNull com.android.server.SystemService systemService) {
        java.lang.String name = systemService.getClass().getName();
        if (this.mServiceClassnames.contains(name)) {
            android.util.Slog.i(TAG, "Not starting an already started service " + name);
            return;
        }
        this.mServiceClassnames.add(name);
        this.mServices.add(systemService);
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        try {
            systemService.onStart();
            warnIfTooLong(android.os.SystemClock.elapsedRealtime() - elapsedRealtime, systemService, "onStart");
        } catch (java.lang.RuntimeException e) {
            throw new java.lang.RuntimeException("Failed to start service " + systemService.getClass().getName() + ": onStart threw an exception", e);
        }
    }

    @android.ravenwood.annotation.RavenwoodKeep
    public void sealStartedServices() {
        this.mServiceClassnames = java.util.Collections.emptySet();
        this.mServices = java.util.Collections.unmodifiableList(this.mServices);
    }

    @android.ravenwood.annotation.RavenwoodKeep
    public void startBootPhase(@android.annotation.NonNull com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog, int i) {
        if (i <= this.mCurrentPhase) {
            throw new java.lang.IllegalArgumentException("Next phase must be larger than previous");
        }
        this.mCurrentPhase = i;
        android.util.Slog.i(TAG, "Starting phase " + this.mCurrentPhase);
        try {
            timingsTraceAndSlog.traceBegin("OnBootPhase_" + i);
            int size = this.mServices.size();
            for (int i2 = 0; i2 < size; i2++) {
                com.android.server.SystemService systemService = this.mServices.get(i2);
                long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
                timingsTraceAndSlog.traceBegin("OnBootPhase_" + i + "_" + systemService.getClass().getName());
                try {
                    systemService.onBootPhase(this.mCurrentPhase);
                    warnIfTooLong(android.os.SystemClock.elapsedRealtime() - elapsedRealtime, systemService, "onBootPhase");
                    timingsTraceAndSlog.traceEnd();
                } catch (java.lang.Exception e) {
                    throw new java.lang.RuntimeException("Failed to boot service " + systemService.getClass().getName() + ": onBootPhase threw an exception during phase " + this.mCurrentPhase, e);
                }
            }
            timingsTraceAndSlog.traceEnd();
            if (i == 1000) {
                timingsTraceAndSlog.logDuration("TotalBootTime", android.os.SystemClock.uptimeMillis() - this.mRuntimeStartUptime);
                shutdownInitThreadPool();
            }
        } catch (java.lang.Throwable th) {
            timingsTraceAndSlog.traceEnd();
            throw th;
        }
    }

    @android.ravenwood.annotation.RavenwoodReplace
    private void shutdownInitThreadPool() {
        com.android.server.SystemServerInitThreadPool.shutdown();
    }

    private void shutdownInitThreadPool$ravenwood() {
    }

    @android.ravenwood.annotation.RavenwoodKeep
    public boolean isBootCompleted() {
        return this.mCurrentPhase >= 1000;
    }

    public void updateOtherServicesStartIndex() {
        if (!isBootCompleted()) {
            sOtherServicesStartIndex = this.mServices.size();
        }
    }

    public void preSystemReady() {
        this.mUserManagerInternal = (com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class);
    }

    @android.annotation.Nullable
    private com.android.server.SystemService.TargetUser getTargetUser(int i) {
        com.android.server.SystemService.TargetUser targetUser;
        synchronized (this.mTargetUsers) {
            targetUser = this.mTargetUsers.get(i);
        }
        return targetUser;
    }

    @android.annotation.NonNull
    private com.android.server.SystemService.TargetUser newTargetUser(int i) {
        android.content.pm.UserInfo userInfo = this.mUserManagerInternal.getUserInfo(i);
        com.android.internal.util.Preconditions.checkState(userInfo != null, "No UserInfo for " + i);
        return new com.android.server.SystemService.TargetUser(userInfo);
    }

    public void onUserStarting(@android.annotation.NonNull com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog, int i) {
        com.android.server.SystemService.TargetUser newTargetUser = newTargetUser(i);
        synchronized (this.mTargetUsers) {
            if (i == 0) {
                try {
                    if (this.mTargetUsers.contains(i)) {
                        android.util.Slog.e(TAG, "Skipping starting system user twice");
                        return;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            this.mTargetUsers.put(i, newTargetUser);
            android.util.EventLog.writeEvent(com.android.server.am.EventLogTags.SSM_USER_STARTING, i);
            onUser(timingsTraceAndSlog, USER_STARTING, null, newTargetUser);
        }
    }

    public void onUserUnlocking(int i) {
        android.util.EventLog.writeEvent(com.android.server.am.EventLogTags.SSM_USER_UNLOCKING, i);
        onUser(USER_UNLOCKING, i);
    }

    public void onUserUnlocked(int i) {
        android.util.EventLog.writeEvent(com.android.server.am.EventLogTags.SSM_USER_UNLOCKED, i);
        onUser(USER_UNLOCKED, i);
    }

    public void onUserSwitching(int i, int i2) {
        com.android.server.SystemService.TargetUser targetUser;
        com.android.server.SystemService.TargetUser targetUser2;
        android.util.EventLog.writeEvent(com.android.server.am.EventLogTags.SSM_USER_SWITCHING, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
        synchronized (this.mTargetUsers) {
            try {
                if (this.mCurrentUser == null) {
                    targetUser = newTargetUser(i);
                } else {
                    if (i != this.mCurrentUser.getUserIdentifier()) {
                        android.util.Slog.wtf(TAG, "switchUser(" + i + "," + i2 + "): mCurrentUser is " + this.mCurrentUser + ", it should be " + i);
                    }
                    targetUser = this.mCurrentUser;
                }
                targetUser2 = getTargetUser(i2);
                this.mCurrentUser = targetUser2;
                com.android.internal.util.Preconditions.checkState(targetUser2 != null, "No TargetUser for " + i2);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        onUser(com.android.server.utils.TimingsTraceAndSlog.newAsyncLog(), USER_SWITCHING, targetUser, targetUser2);
    }

    public void onUserStopping(int i) {
        android.util.EventLog.writeEvent(com.android.server.am.EventLogTags.SSM_USER_STOPPING, i);
        onUser(USER_STOPPING, i);
    }

    public void onUserStopped(int i) {
        android.util.EventLog.writeEvent(com.android.server.am.EventLogTags.SSM_USER_STOPPED, i);
        onUser(USER_STOPPED, i);
        synchronized (this.mTargetUsers) {
            this.mTargetUsers.remove(i);
        }
    }

    public void onUserCompletedEvent(int i, int i2) {
        com.android.server.SystemService.TargetUser targetUser;
        android.util.EventLog.writeEvent(com.android.server.am.EventLogTags.SSM_USER_COMPLETED_EVENT, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
        if (i2 == 0 || (targetUser = getTargetUser(i)) == null) {
            return;
        }
        onUser(com.android.server.utils.TimingsTraceAndSlog.newAsyncLog(), USER_COMPLETED_EVENT, null, targetUser, new com.android.server.SystemService.UserCompletedEventType(i2));
    }

    private void onUser(@android.annotation.NonNull java.lang.String str, int i) {
        com.android.server.SystemService.TargetUser targetUser = getTargetUser(i);
        com.android.internal.util.Preconditions.checkState(targetUser != null, "No TargetUser for " + i);
        onUser(com.android.server.utils.TimingsTraceAndSlog.newAsyncLog(), str, null, targetUser);
    }

    private void onUser(@android.annotation.NonNull com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable com.android.server.SystemService.TargetUser targetUser, @android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser2) {
        onUser(timingsTraceAndSlog, str, targetUser, targetUser2, null);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0203  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0227 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void onUser(@android.annotation.NonNull com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable com.android.server.SystemService.TargetUser targetUser, @android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser2, @android.annotation.Nullable com.android.server.SystemService.UserCompletedEventType userCompletedEventType) {
        java.lang.String str2;
        java.lang.String str3;
        com.android.server.SystemService systemService;
        int i;
        int i2;
        boolean z;
        java.util.concurrent.ExecutorService executorService;
        char c;
        int userIdentifier = targetUser2.getUserIdentifier();
        timingsTraceAndSlog.traceBegin("ssm." + str + "User-" + userIdentifier);
        java.lang.String str4 = TAG;
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("Calling on");
        sb.append(str);
        sb.append("User ");
        sb.append(userIdentifier);
        if (targetUser != null) {
            str2 = " (from " + targetUser + ")";
        } else {
            str2 = "";
        }
        sb.append(str2);
        android.util.Slog.i(str4, sb.toString());
        boolean useThreadPool = useThreadPool(userIdentifier, str);
        java.util.concurrent.ExecutorService newFixedThreadPool = useThreadPool ? java.util.concurrent.Executors.newFixedThreadPool(this.mNumUserPoolThreads) : null;
        int size = this.mServices.size();
        boolean z2 = false;
        int i3 = 0;
        while (i3 < size) {
            com.android.server.SystemService systemService2 = this.mServices.get(i3);
            java.lang.String name = systemService2.getClass().getName();
            boolean isUserSupported = systemService2.isUserSupported(targetUser2);
            if (!isUserSupported && targetUser != null) {
                isUserSupported = systemService2.isUserSupported(targetUser);
            }
            if (isUserSupported) {
                boolean z3 = useThreadPool && useThreadPoolForService(str, i3);
                if (!z3) {
                    timingsTraceAndSlog.traceBegin("ssm.on" + str + "User-" + userIdentifier + "_" + name);
                }
                long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
                try {
                    switch (str.hashCode()) {
                        case -1805606060:
                            if (str.equals(USER_SWITCHING)) {
                                c = 0;
                                break;
                            }
                            c = 65535;
                            break;
                        case -1773539708:
                            if (str.equals(USER_STOPPED)) {
                                c = 5;
                                break;
                            }
                            c = 65535;
                            break;
                        case -240492034:
                            if (str.equals(USER_UNLOCKING)) {
                                c = 2;
                                break;
                            }
                            c = 65535;
                            break;
                        case -146305277:
                            if (str.equals(USER_UNLOCKED)) {
                                c = 3;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2587682:
                            if (str.equals(USER_STOPPING)) {
                                c = 4;
                                break;
                            }
                            c = 65535;
                            break;
                        case 80204866:
                            if (str.equals(USER_STARTING)) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        case 537825071:
                            if (str.equals(USER_COMPLETED_EVENT)) {
                                c = 6;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
                } catch (java.lang.Exception e) {
                    e = e;
                    str3 = name;
                    systemService = systemService2;
                    i = i3;
                    i2 = size;
                    z = useThreadPool;
                    executorService = newFixedThreadPool;
                }
                switch (c) {
                    case 0:
                        systemService = systemService2;
                        i = i3;
                        i2 = size;
                        z = useThreadPool;
                        executorService = newFixedThreadPool;
                        systemService.onUserSwitching(targetUser, targetUser2);
                        if (!z3) {
                            warnIfTooLong(android.os.SystemClock.elapsedRealtime() - elapsedRealtime, systemService, "on" + str + "User-" + userIdentifier);
                            timingsTraceAndSlog.traceEnd();
                            break;
                        } else {
                            break;
                        }
                    case 1:
                        systemService = systemService2;
                        i = i3;
                        i2 = size;
                        z = useThreadPool;
                        executorService = newFixedThreadPool;
                        if (z3) {
                            executorService.submit(getOnUserStartingRunnable(timingsTraceAndSlog, systemService, targetUser2));
                        } else {
                            systemService.onUserStarting(targetUser2);
                        }
                        if (!z3) {
                        }
                        break;
                    case 2:
                        systemService = systemService2;
                        i = i3;
                        i2 = size;
                        z = useThreadPool;
                        executorService = newFixedThreadPool;
                        systemService.onUserUnlocking(targetUser2);
                        if (!z3) {
                        }
                        break;
                    case 3:
                        systemService = systemService2;
                        i = i3;
                        i2 = size;
                        z = useThreadPool;
                        executorService = newFixedThreadPool;
                        systemService.onUserUnlocked(targetUser2);
                        if (!z3) {
                        }
                        break;
                    case 4:
                        systemService = systemService2;
                        i = i3;
                        i2 = size;
                        z = useThreadPool;
                        executorService = newFixedThreadPool;
                        systemService.onUserStopping(targetUser2);
                        if (!z3) {
                        }
                        break;
                    case 5:
                        str3 = name;
                        i = i3;
                        i2 = size;
                        z = useThreadPool;
                        executorService = newFixedThreadPool;
                        systemService = systemService2;
                        try {
                            systemService.onUserStopped(targetUser2);
                        } catch (java.lang.Exception e2) {
                            e = e2;
                            logFailure(str, targetUser2, str3, e);
                            if (!z3) {
                            }
                            i3 = i + 1;
                            newFixedThreadPool = executorService;
                            size = i2;
                            useThreadPool = z;
                        }
                        if (!z3) {
                        }
                        break;
                    case 6:
                        str3 = name;
                        i = i3;
                        i2 = size;
                        z = useThreadPool;
                        executorService = newFixedThreadPool;
                        try {
                            executorService.submit(getOnUserCompletedEventRunnable(timingsTraceAndSlog, systemService2, str3, targetUser2, userCompletedEventType));
                            systemService = systemService2;
                        } catch (java.lang.Exception e3) {
                            e = e3;
                            systemService = systemService2;
                            logFailure(str, targetUser2, str3, e);
                            if (!z3) {
                            }
                            i3 = i + 1;
                            newFixedThreadPool = executorService;
                            size = i2;
                            useThreadPool = z;
                        }
                        if (!z3) {
                        }
                        break;
                    default:
                        throw new java.lang.IllegalArgumentException(str + " what?");
                        break;
                }
            } else {
                android.util.Slog.i(TAG, "Skipping " + str + "User-" + userIdentifier + " on " + name);
                i = i3;
                i2 = size;
                z = useThreadPool;
                executorService = newFixedThreadPool;
            }
            i3 = i + 1;
            newFixedThreadPool = executorService;
            size = i2;
            useThreadPool = z;
        }
        boolean z4 = useThreadPool;
        java.util.concurrent.ExecutorService executorService2 = newFixedThreadPool;
        if (z4) {
            executorService2.shutdown();
            try {
                z2 = executorService2.awaitTermination(USER_POOL_SHUTDOWN_TIMEOUT_SECONDS, java.util.concurrent.TimeUnit.SECONDS);
            } catch (java.lang.InterruptedException e4) {
                logFailure(str, targetUser2, "(user lifecycle threadpool was interrupted)", e4);
            }
            if (!z2) {
                logFailure(str, targetUser2, "(user lifecycle threadpool was not terminated)", null);
            }
        }
        timingsTraceAndSlog.traceEnd();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private boolean useThreadPool(int i, @android.annotation.NonNull java.lang.String str) {
        char c;
        switch (str.hashCode()) {
            case 80204866:
                if (str.equals(USER_STARTING)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 537825071:
                if (str.equals(USER_COMPLETED_EVENT)) {
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
                return (android.app.ActivityManager.isLowRamDeviceStatic() || i == 0) ? false : true;
            case 1:
                return true;
            default:
                return false;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private boolean useThreadPoolForService(@android.annotation.NonNull java.lang.String str, int i) {
        char c;
        switch (str.hashCode()) {
            case 80204866:
                if (str.equals(USER_STARTING)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 537825071:
                if (str.equals(USER_COMPLETED_EVENT)) {
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
                return i >= sOtherServicesStartIndex;
            case 1:
                return true;
            default:
                return false;
        }
    }

    private java.lang.Runnable getOnUserStartingRunnable(final com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog, final com.android.server.SystemService systemService, final com.android.server.SystemService.TargetUser targetUser) {
        return new java.lang.Runnable() { // from class: com.android.server.SystemServiceManager$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.SystemServiceManager.this.lambda$getOnUserStartingRunnable$0(timingsTraceAndSlog, systemService, targetUser);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getOnUserStartingRunnable$0(com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog, com.android.server.SystemService systemService, com.android.server.SystemService.TargetUser targetUser) {
        com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog2 = new com.android.server.utils.TimingsTraceAndSlog(timingsTraceAndSlog);
        java.lang.String name = systemService.getClass().getName();
        int userIdentifier = targetUser.getUserIdentifier();
        timingsTraceAndSlog2.traceBegin("ssm.onStartUser-" + userIdentifier + "_" + name);
        try {
            try {
                long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
                systemService.onUserStarting(targetUser);
                warnIfTooLong(android.os.SystemClock.elapsedRealtime() - elapsedRealtime, systemService, "onStartUser-" + userIdentifier);
            } catch (java.lang.Exception e) {
                logFailure(USER_STARTING, targetUser, name, e);
            }
        } finally {
            timingsTraceAndSlog2.traceEnd();
        }
    }

    private java.lang.Runnable getOnUserCompletedEventRunnable(final com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog, final com.android.server.SystemService systemService, final java.lang.String str, final com.android.server.SystemService.TargetUser targetUser, final com.android.server.SystemService.UserCompletedEventType userCompletedEventType) {
        return new java.lang.Runnable() { // from class: com.android.server.SystemServiceManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.SystemServiceManager.this.lambda$getOnUserCompletedEventRunnable$1(timingsTraceAndSlog, targetUser, userCompletedEventType, str, systemService);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getOnUserCompletedEventRunnable$1(com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog, com.android.server.SystemService.TargetUser targetUser, com.android.server.SystemService.UserCompletedEventType userCompletedEventType, java.lang.String str, com.android.server.SystemService systemService) {
        com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog2 = new com.android.server.utils.TimingsTraceAndSlog(timingsTraceAndSlog);
        int userIdentifier = targetUser.getUserIdentifier();
        timingsTraceAndSlog2.traceBegin("ssm.onCompletedEventUser-" + userIdentifier + "_" + userCompletedEventType + "_" + str);
        try {
            try {
                long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
                systemService.onUserCompletedEvent(targetUser, userCompletedEventType);
                warnIfTooLong(android.os.SystemClock.elapsedRealtime() - elapsedRealtime, systemService, "onCompletedEventUser-" + userIdentifier);
            } catch (java.lang.Exception e) {
                logFailure(USER_COMPLETED_EVENT, targetUser, str, e);
                throw e;
            }
        } finally {
            timingsTraceAndSlog2.traceEnd();
        }
    }

    @android.ravenwood.annotation.RavenwoodKeep
    private void logFailure(java.lang.String str, com.android.server.SystemService.TargetUser targetUser, java.lang.String str2, java.lang.Exception exc) {
        android.util.Slog.wtf(TAG, "SystemService failure: Failure reporting " + str + " of user " + targetUser + " to service " + str2, exc);
    }

    @android.ravenwood.annotation.RavenwoodKeep
    void setSafeMode(boolean z) {
        this.mSafeMode = z;
    }

    @android.ravenwood.annotation.RavenwoodKeep
    public boolean isSafeMode() {
        return this.mSafeMode;
    }

    @android.ravenwood.annotation.RavenwoodKeep
    public boolean isRuntimeRestarted() {
        return this.mRuntimeRestarted;
    }

    @android.ravenwood.annotation.RavenwoodKeep
    public long getRuntimeStartElapsedTime() {
        return this.mRuntimeStartElapsedTime;
    }

    @android.ravenwood.annotation.RavenwoodKeep
    public long getRuntimeStartUptime() {
        return this.mRuntimeStartUptime;
    }

    @android.ravenwood.annotation.RavenwoodKeep
    public void setStartInfo(boolean z, long j, long j2) {
        this.mRuntimeRestarted = z;
        this.mRuntimeStartElapsedTime = j;
        this.mRuntimeStartUptime = j2;
    }

    @android.ravenwood.annotation.RavenwoodKeep
    private void warnIfTooLong(long j, com.android.server.SystemService systemService, java.lang.String str) {
        if (j > 50) {
            android.util.Slog.w(TAG, "Service " + systemService.getClass().getName() + " took " + j + " ms in " + str);
        }
    }

    @java.lang.Deprecated
    public static java.io.File ensureSystemDir() {
        if (sSystemDir == null) {
            sSystemDir = new java.io.File(android.os.Environment.getDataDirectory(), "system");
            sSystemDir.mkdirs();
        }
        return sSystemDir;
    }

    @Override // android.util.Dumpable
    public java.lang.String getDumpableName() {
        return com.android.server.SystemServiceManager.class.getSimpleName();
    }

    @Override // android.util.Dumpable
    public void dump(java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        int i;
        printWriter.printf("Current phase: %d\n", java.lang.Integer.valueOf(this.mCurrentPhase));
        synchronized (this.mTargetUsers) {
            try {
                if (this.mCurrentUser != null) {
                    printWriter.print("Current user: ");
                    this.mCurrentUser.dump(printWriter);
                    printWriter.println();
                } else {
                    printWriter.println("Current user not set!");
                }
                int size = this.mTargetUsers.size();
                if (size > 0) {
                    printWriter.printf("%d target users: ", java.lang.Integer.valueOf(size));
                    for (int i2 = 0; i2 < size; i2++) {
                        this.mTargetUsers.valueAt(i2).dump(printWriter);
                        if (i2 != size - 1) {
                            printWriter.print(", ");
                        }
                    }
                    printWriter.println();
                } else {
                    printWriter.println("No target users");
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        int size2 = this.mServices.size();
        if (size2 > 0) {
            printWriter.printf("%d started services:\n", java.lang.Integer.valueOf(size2));
            for (i = 0; i < size2; i++) {
                com.android.server.SystemService systemService = this.mServices.get(i);
                printWriter.print("  ");
                printWriter.println(systemService.getClass().getCanonicalName());
            }
            return;
        }
        printWriter.println("No started services");
    }
}
