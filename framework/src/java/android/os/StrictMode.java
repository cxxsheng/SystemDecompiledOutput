package android.os;

/* loaded from: classes3.dex */
public final class StrictMode {
    private static final java.lang.String CLEARTEXT_PROPERTY = "persist.sys.strictmode.clear";
    static final long DETECT_EXPLICIT_GC = 3400644;
    private static final int DETECT_THREAD_ALL = 65535;
    private static final int DETECT_THREAD_CUSTOM = 8;
    private static final int DETECT_THREAD_DISK_READ = 2;
    private static final int DETECT_THREAD_DISK_WRITE = 1;
    private static final int DETECT_THREAD_EXPLICIT_GC = 64;
    private static final int DETECT_THREAD_NETWORK = 4;
    private static final int DETECT_THREAD_RESOURCE_MISMATCH = 16;
    private static final int DETECT_THREAD_UNBUFFERED_IO = 32;
    private static final int DETECT_VM_ACTIVITY_LEAKS = 4;
    private static final int DETECT_VM_ALL = 65535;
    private static final int DETECT_VM_CLEARTEXT_NETWORK = 64;
    private static final int DETECT_VM_CLOSABLE_LEAKS = 2;
    private static final int DETECT_VM_CONTENT_URI_WITHOUT_PERMISSION = 128;
    private static final int DETECT_VM_CREDENTIAL_PROTECTED_WHILE_LOCKED = 2048;
    private static final int DETECT_VM_CURSOR_LEAKS = 1;
    private static final int DETECT_VM_FILE_URI_EXPOSURE = 32;
    private static final int DETECT_VM_IMPLICIT_DIRECT_BOOT = 1024;
    private static final int DETECT_VM_INCORRECT_CONTEXT_USE = 4096;
    private static final int DETECT_VM_INSTANCE_LEAKS = 8;
    private static final int DETECT_VM_NON_SDK_API_USAGE = 512;
    private static final int DETECT_VM_REGISTRATION_LEAKS = 16;
    private static final int DETECT_VM_UNSAFE_INTENT_LAUNCH = 8192;
    private static final int DETECT_VM_UNTAGGED_SOCKET = 256;
    private static final boolean DISABLE = false;
    public static final java.lang.String DISABLE_PROPERTY = "persist.sys.strictmode.disable";
    private static final int MAX_OFFENSES_PER_LOOP = 10;
    private static final int MAX_SPAN_TAGS = 20;
    private static final long MIN_DIALOG_INTERVAL_MS = 30000;
    private static final long MIN_DROPBOX_INTERVAL_MS = 3000;
    private static final long MIN_LOG_INTERVAL_MS = 1000;
    private static final long MIN_VM_INTERVAL_MS = 1000;
    public static final int NETWORK_POLICY_ACCEPT = 0;
    public static final int NETWORK_POLICY_LOG = 1;
    public static final int NETWORK_POLICY_REJECT = 2;
    public static final int PENALTY_ALL = -65536;
    public static final int PENALTY_DEATH = 268435456;
    public static final int PENALTY_DEATH_ON_CLEARTEXT_NETWORK = 16777216;
    public static final int PENALTY_DEATH_ON_FILE_URI_EXPOSURE = 8388608;
    public static final int PENALTY_DEATH_ON_NETWORK = 33554432;
    public static final int PENALTY_DIALOG = 536870912;
    public static final int PENALTY_DROPBOX = 67108864;
    public static final int PENALTY_FLASH = 134217728;
    public static final int PENALTY_GATHER = Integer.MIN_VALUE;
    public static final int PENALTY_LOG = 1073741824;
    public static final java.lang.String VISUAL_PROPERTY = "persist.sys.strictmode.visual";
    private static volatile android.os.storage.IStorageManager sStorageManager;
    private static final java.lang.String TAG = "StrictMode";
    private static final boolean LOG_V = android.util.Log.isLoggable(TAG, 2);
    private static final java.util.HashMap<java.lang.Class, java.lang.Integer> EMPTY_CLASS_LIMIT_MAP = new java.util.HashMap<>();
    private static volatile android.os.StrictMode.VmPolicy sVmPolicy = android.os.StrictMode.VmPolicy.LAX;
    private static final android.os.StrictMode.ViolationLogger LOGCAT_LOGGER = new android.os.StrictMode.ViolationLogger() { // from class: android.os.StrictMode$$ExternalSyntheticLambda1
        @Override // android.os.StrictMode.ViolationLogger
        public final void log(android.os.StrictMode.ViolationInfo violationInfo) {
            android.os.StrictMode.lambda$static$0(violationInfo);
        }
    };
    private static volatile android.os.StrictMode.ViolationLogger sLogger = LOGCAT_LOGGER;
    private static final java.lang.ThreadLocal<android.os.StrictMode.OnThreadViolationListener> sThreadViolationListener = new java.lang.ThreadLocal<>();
    private static final java.lang.ThreadLocal<java.util.concurrent.Executor> sThreadViolationExecutor = new java.lang.ThreadLocal<>();
    private static final java.util.concurrent.atomic.AtomicInteger sDropboxCallsInFlight = new java.util.concurrent.atomic.AtomicInteger(0);
    private static final java.util.function.Consumer<java.lang.String> sNonSdkApiUsageConsumer = new java.util.function.Consumer() { // from class: android.os.StrictMode$$ExternalSyntheticLambda2
        @Override // java.util.function.Consumer
        public final void accept(java.lang.Object obj) {
            android.os.StrictMode.onVmPolicyViolation(new android.os.strictmode.NonSdkApiUsedViolation((java.lang.String) obj));
        }
    };
    private static final java.lang.ThreadLocal<java.util.ArrayList<android.os.StrictMode.ViolationInfo>> gatheredViolations = new java.lang.ThreadLocal<java.util.ArrayList<android.os.StrictMode.ViolationInfo>>() { // from class: android.os.StrictMode.1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // java.lang.ThreadLocal
        public java.util.ArrayList<android.os.StrictMode.ViolationInfo> initialValue() {
            return null;
        }
    };
    private static final java.lang.ThreadLocal<java.util.ArrayList<android.os.StrictMode.ViolationInfo>> violationsBeingTimed = new java.lang.ThreadLocal<java.util.ArrayList<android.os.StrictMode.ViolationInfo>>() { // from class: android.os.StrictMode.2
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // java.lang.ThreadLocal
        public java.util.ArrayList<android.os.StrictMode.ViolationInfo> initialValue() {
            return new java.util.ArrayList<>();
        }
    };
    private static final java.lang.ThreadLocal<android.os.Handler> THREAD_HANDLER = new java.lang.ThreadLocal<android.os.Handler>() { // from class: android.os.StrictMode.3
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.lang.ThreadLocal
        public android.os.Handler initialValue() {
            return new android.os.Handler();
        }
    };
    private static final java.lang.ThreadLocal<android.os.StrictMode.AndroidBlockGuardPolicy> THREAD_ANDROID_POLICY = new java.lang.ThreadLocal<android.os.StrictMode.AndroidBlockGuardPolicy>() { // from class: android.os.StrictMode.4
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.lang.ThreadLocal
        public android.os.StrictMode.AndroidBlockGuardPolicy initialValue() {
            return new android.os.StrictMode.AndroidBlockGuardPolicy(0);
        }
    };
    private static final dalvik.system.BlockGuard.VmPolicy VM_ANDROID_POLICY = new dalvik.system.BlockGuard.VmPolicy() { // from class: android.os.StrictMode.5
        public void onPathAccess(java.lang.String str) {
            if (str == null) {
                return;
            }
            if (str.startsWith("/data/user/") || str.startsWith("/data/media/") || str.startsWith("/data/system_ce/") || str.startsWith("/data/misc_ce/") || str.startsWith("/data/vendor_ce/") || str.startsWith("/storage/emulated/")) {
                int indexOf = str.indexOf(47, str.indexOf(47, 1) + 1) + 1;
                int indexOf2 = str.indexOf(47, indexOf);
                if (indexOf2 == -1) {
                    return;
                }
                try {
                    android.os.StrictMode.onCredentialProtectedPathAccess(str, java.lang.Integer.parseInt(str.substring(indexOf, indexOf2)));
                    return;
                } catch (java.lang.NumberFormatException e) {
                    return;
                }
            }
            if (str.startsWith("/data/data/")) {
                android.os.StrictMode.onCredentialProtectedPathAccess(str, 0);
            }
        }
    };
    private static long sLastInstanceCountCheckMillis = 0;
    private static boolean sIsIdlerRegistered = false;
    private static final android.os.MessageQueue.IdleHandler sProcessIdleHandler = new android.os.MessageQueue.IdleHandler() { // from class: android.os.StrictMode.6
        @Override // android.os.MessageQueue.IdleHandler
        public boolean queueIdle() {
            long uptimeMillis = android.os.SystemClock.uptimeMillis();
            if (uptimeMillis - android.os.StrictMode.sLastInstanceCountCheckMillis > 30000) {
                android.os.StrictMode.sLastInstanceCountCheckMillis = uptimeMillis;
                android.os.StrictMode.conditionallyCheckInstanceCounts();
                return true;
            }
            return true;
        }
    };
    private static volatile boolean sCeStorageUnlocked = false;
    private static final java.util.HashMap<java.lang.Integer, java.lang.Long> sLastVmViolationTime = new java.util.HashMap<>();
    private static final android.util.SparseLongArray sRealLastVmViolationTime = new android.util.SparseLongArray();
    private static final android.os.StrictMode.Span NO_OP_SPAN = new android.os.StrictMode.Span() { // from class: android.os.StrictMode.7
        @Override // android.os.StrictMode.Span
        public void finish() {
        }
    };
    private static final java.lang.ThreadLocal<android.os.StrictMode.ThreadSpanState> sThisThreadSpanState = new java.lang.ThreadLocal<android.os.StrictMode.ThreadSpanState>() { // from class: android.os.StrictMode.8
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.lang.ThreadLocal
        public android.os.StrictMode.ThreadSpanState initialValue() {
            return new android.os.StrictMode.ThreadSpanState();
        }
    };
    private static android.util.Singleton<android.view.IWindowManager> sWindowManager = new android.util.Singleton<android.view.IWindowManager>() { // from class: android.os.StrictMode.9
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.util.Singleton
        public android.view.IWindowManager create() {
            return android.view.IWindowManager.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.WINDOW_SERVICE));
        }
    };
    private static final java.util.HashMap<java.lang.Class, java.lang.Integer> sExpectedActivityInstanceCount = new java.util.HashMap<>();

    public interface OnThreadViolationListener {
        void onThreadViolation(android.os.strictmode.Violation violation);
    }

    public interface OnVmViolationListener {
        void onVmViolation(android.os.strictmode.Violation violation);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ThreadPolicyMask {
    }

    public interface ViolationLogger {
        void log(android.os.StrictMode.ViolationInfo violationInfo);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface VmPolicyMask {
    }

    static /* synthetic */ void lambda$static$0(android.os.StrictMode.ViolationInfo violationInfo) {
        java.lang.String str;
        if (violationInfo.durationMillis != -1) {
            str = "StrictMode policy violation; ~duration=" + violationInfo.durationMillis + " ms:";
        } else {
            str = "StrictMode policy violation:";
        }
        android.util.Log.d(TAG, str + " " + violationInfo.getStackTrace());
    }

    public static void setViolationLogger(android.os.StrictMode.ViolationLogger violationLogger) {
        if (violationLogger == null) {
            violationLogger = LOGCAT_LOGGER;
        }
        sLogger = violationLogger;
    }

    private StrictMode() {
    }

    public static final class ThreadPolicy {
        public static final android.os.StrictMode.ThreadPolicy LAX = new android.os.StrictMode.ThreadPolicy(0, null, null);
        final java.util.concurrent.Executor mCallbackExecutor;
        final android.os.StrictMode.OnThreadViolationListener mListener;
        final int mask;

        private ThreadPolicy(int i, android.os.StrictMode.OnThreadViolationListener onThreadViolationListener, java.util.concurrent.Executor executor) {
            this.mask = i;
            this.mListener = onThreadViolationListener;
            this.mCallbackExecutor = executor;
        }

        public java.lang.String toString() {
            return "[StrictMode.ThreadPolicy; mask=" + this.mask + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
        }

        public static final class Builder {
            private java.util.concurrent.Executor mExecutor;
            private android.os.StrictMode.OnThreadViolationListener mListener;
            private int mMask;

            public Builder() {
                this.mMask = 0;
                this.mMask = 0;
            }

            public Builder(android.os.StrictMode.ThreadPolicy threadPolicy) {
                this.mMask = 0;
                this.mMask = threadPolicy.mask;
                this.mListener = threadPolicy.mListener;
                this.mExecutor = threadPolicy.mCallbackExecutor;
            }

            public android.os.StrictMode.ThreadPolicy.Builder detectAll() {
                detectDiskReads();
                detectDiskWrites();
                detectNetwork();
                int targetSdkVersion = dalvik.system.VMRuntime.getRuntime().getTargetSdkVersion();
                if (targetSdkVersion >= 11) {
                    detectCustomSlowCalls();
                }
                if (targetSdkVersion >= 23) {
                    detectResourceMismatches();
                }
                if (targetSdkVersion >= 26) {
                    detectUnbufferedIo();
                }
                if (android.app.compat.CompatChanges.isChangeEnabled(android.os.StrictMode.DETECT_EXPLICIT_GC)) {
                    detectExplicitGc();
                }
                return this;
            }

            public android.os.StrictMode.ThreadPolicy.Builder permitAll() {
                return disable(65535);
            }

            public android.os.StrictMode.ThreadPolicy.Builder detectNetwork() {
                return enable(4);
            }

            public android.os.StrictMode.ThreadPolicy.Builder permitNetwork() {
                return disable(4);
            }

            public android.os.StrictMode.ThreadPolicy.Builder detectDiskReads() {
                return enable(2);
            }

            public android.os.StrictMode.ThreadPolicy.Builder permitDiskReads() {
                return disable(2);
            }

            public android.os.StrictMode.ThreadPolicy.Builder detectCustomSlowCalls() {
                return enable(8);
            }

            public android.os.StrictMode.ThreadPolicy.Builder permitCustomSlowCalls() {
                return disable(8);
            }

            public android.os.StrictMode.ThreadPolicy.Builder permitResourceMismatches() {
                return disable(16);
            }

            public android.os.StrictMode.ThreadPolicy.Builder detectUnbufferedIo() {
                return enable(32);
            }

            public android.os.StrictMode.ThreadPolicy.Builder permitUnbufferedIo() {
                return disable(32);
            }

            public android.os.StrictMode.ThreadPolicy.Builder detectResourceMismatches() {
                return enable(16);
            }

            public android.os.StrictMode.ThreadPolicy.Builder detectDiskWrites() {
                return enable(1);
            }

            public android.os.StrictMode.ThreadPolicy.Builder permitDiskWrites() {
                return disable(1);
            }

            public android.os.StrictMode.ThreadPolicy.Builder detectExplicitGc() {
                return enable(64);
            }

            public android.os.StrictMode.ThreadPolicy.Builder permitExplicitGc() {
                return disable(64);
            }

            public android.os.StrictMode.ThreadPolicy.Builder penaltyDialog() {
                return enable(536870912);
            }

            public android.os.StrictMode.ThreadPolicy.Builder penaltyDeath() {
                return enable(268435456);
            }

            public android.os.StrictMode.ThreadPolicy.Builder penaltyDeathOnNetwork() {
                return enable(33554432);
            }

            public android.os.StrictMode.ThreadPolicy.Builder penaltyFlashScreen() {
                return enable(134217728);
            }

            public android.os.StrictMode.ThreadPolicy.Builder penaltyLog() {
                return enable(1073741824);
            }

            public android.os.StrictMode.ThreadPolicy.Builder penaltyDropBox() {
                return enable(67108864);
            }

            public android.os.StrictMode.ThreadPolicy.Builder penaltyListener(java.util.concurrent.Executor executor, android.os.StrictMode.OnThreadViolationListener onThreadViolationListener) {
                if (executor == null) {
                    throw new java.lang.NullPointerException("executor must not be null");
                }
                this.mListener = onThreadViolationListener;
                this.mExecutor = executor;
                return this;
            }

            public android.os.StrictMode.ThreadPolicy.Builder penaltyListener(android.os.StrictMode.OnThreadViolationListener onThreadViolationListener, java.util.concurrent.Executor executor) {
                return penaltyListener(executor, onThreadViolationListener);
            }

            private android.os.StrictMode.ThreadPolicy.Builder enable(int i) {
                this.mMask = i | this.mMask;
                return this;
            }

            private android.os.StrictMode.ThreadPolicy.Builder disable(int i) {
                this.mMask = (~i) & this.mMask;
                return this;
            }

            public android.os.StrictMode.ThreadPolicy build() {
                if (this.mListener == null && this.mMask != 0 && (this.mMask & 1946157056) == 0) {
                    penaltyLog();
                }
                return new android.os.StrictMode.ThreadPolicy(this.mMask, this.mListener, this.mExecutor);
            }
        }
    }

    public static final class VmPolicy {
        public static final android.os.StrictMode.VmPolicy LAX = new android.os.StrictMode.VmPolicy(0, android.os.StrictMode.EMPTY_CLASS_LIMIT_MAP, null, null);
        final java.util.HashMap<java.lang.Class, java.lang.Integer> classInstanceLimit;
        final java.util.concurrent.Executor mCallbackExecutor;
        final android.os.StrictMode.OnVmViolationListener mListener;
        final int mask;

        private VmPolicy(int i, java.util.HashMap<java.lang.Class, java.lang.Integer> hashMap, android.os.StrictMode.OnVmViolationListener onVmViolationListener, java.util.concurrent.Executor executor) {
            if (hashMap == null) {
                throw new java.lang.NullPointerException("classInstanceLimit == null");
            }
            this.mask = i;
            this.classInstanceLimit = hashMap;
            this.mListener = onVmViolationListener;
            this.mCallbackExecutor = executor;
        }

        public java.lang.String toString() {
            return "[StrictMode.VmPolicy; mask=" + this.mask + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
        }

        public static final class Builder {
            private java.util.HashMap<java.lang.Class, java.lang.Integer> mClassInstanceLimit;
            private boolean mClassInstanceLimitNeedCow;
            private java.util.concurrent.Executor mExecutor;
            private android.os.StrictMode.OnVmViolationListener mListener;
            private int mMask;

            public Builder() {
                this.mClassInstanceLimitNeedCow = false;
                this.mMask = 0;
            }

            public Builder(android.os.StrictMode.VmPolicy vmPolicy) {
                this.mClassInstanceLimitNeedCow = false;
                this.mMask = vmPolicy.mask;
                this.mClassInstanceLimitNeedCow = true;
                this.mClassInstanceLimit = vmPolicy.classInstanceLimit;
                this.mListener = vmPolicy.mListener;
                this.mExecutor = vmPolicy.mCallbackExecutor;
            }

            public android.os.StrictMode.VmPolicy.Builder setClassInstanceLimit(java.lang.Class cls, int i) {
                if (cls == null) {
                    throw new java.lang.NullPointerException("klass == null");
                }
                if (this.mClassInstanceLimitNeedCow) {
                    if (this.mClassInstanceLimit.containsKey(cls) && this.mClassInstanceLimit.get(cls).intValue() == i) {
                        return this;
                    }
                    this.mClassInstanceLimitNeedCow = false;
                    this.mClassInstanceLimit = (java.util.HashMap) this.mClassInstanceLimit.clone();
                } else if (this.mClassInstanceLimit == null) {
                    this.mClassInstanceLimit = new java.util.HashMap<>();
                }
                this.mMask |= 8;
                this.mClassInstanceLimit.put(cls, java.lang.Integer.valueOf(i));
                return this;
            }

            public android.os.StrictMode.VmPolicy.Builder detectActivityLeaks() {
                return enable(4);
            }

            public android.os.StrictMode.VmPolicy.Builder permitActivityLeaks() {
                synchronized (android.os.StrictMode.class) {
                    android.os.StrictMode.sExpectedActivityInstanceCount.clear();
                }
                return disable(4);
            }

            public android.os.StrictMode.VmPolicy.Builder detectNonSdkApiUsage() {
                return enable(512);
            }

            public android.os.StrictMode.VmPolicy.Builder permitNonSdkApiUsage() {
                return disable(512);
            }

            public android.os.StrictMode.VmPolicy.Builder detectAll() {
                detectLeakedSqlLiteObjects();
                int targetSdkVersion = dalvik.system.VMRuntime.getRuntime().getTargetSdkVersion();
                if (targetSdkVersion >= 11) {
                    detectActivityLeaks();
                    detectLeakedClosableObjects();
                }
                if (targetSdkVersion >= 16) {
                    detectLeakedRegistrationObjects();
                }
                if (targetSdkVersion >= 18) {
                    detectFileUriExposure();
                }
                if (targetSdkVersion >= 23 && android.os.SystemProperties.getBoolean(android.os.StrictMode.CLEARTEXT_PROPERTY, false)) {
                    detectCleartextNetwork();
                }
                if (targetSdkVersion >= 26) {
                    detectContentUriWithoutPermission();
                    detectUntaggedSockets();
                }
                if (targetSdkVersion >= 29) {
                    detectCredentialProtectedWhileLocked();
                }
                if (targetSdkVersion >= 30) {
                    detectIncorrectContextUse();
                }
                if (targetSdkVersion >= 31) {
                    detectUnsafeIntentLaunch();
                }
                return this;
            }

            public android.os.StrictMode.VmPolicy.Builder detectLeakedSqlLiteObjects() {
                return enable(1);
            }

            public android.os.StrictMode.VmPolicy.Builder detectLeakedClosableObjects() {
                return enable(2);
            }

            public android.os.StrictMode.VmPolicy.Builder detectLeakedRegistrationObjects() {
                return enable(16);
            }

            public android.os.StrictMode.VmPolicy.Builder detectFileUriExposure() {
                return enable(32);
            }

            public android.os.StrictMode.VmPolicy.Builder detectCleartextNetwork() {
                return enable(64);
            }

            public android.os.StrictMode.VmPolicy.Builder detectContentUriWithoutPermission() {
                return enable(128);
            }

            public android.os.StrictMode.VmPolicy.Builder detectUntaggedSockets() {
                return enable(256);
            }

            public android.os.StrictMode.VmPolicy.Builder permitUntaggedSockets() {
                return disable(256);
            }

            public android.os.StrictMode.VmPolicy.Builder detectImplicitDirectBoot() {
                return enable(1024);
            }

            public android.os.StrictMode.VmPolicy.Builder permitImplicitDirectBoot() {
                return disable(1024);
            }

            public android.os.StrictMode.VmPolicy.Builder detectCredentialProtectedWhileLocked() {
                return enable(2048);
            }

            public android.os.StrictMode.VmPolicy.Builder permitCredentialProtectedWhileLocked() {
                return disable(2048);
            }

            public android.os.StrictMode.VmPolicy.Builder detectIncorrectContextUse() {
                return enable(4096);
            }

            public android.os.StrictMode.VmPolicy.Builder permitIncorrectContextUse() {
                return disable(4096);
            }

            public android.os.StrictMode.VmPolicy.Builder detectUnsafeIntentLaunch() {
                return enable(8192);
            }

            public android.os.StrictMode.VmPolicy.Builder permitUnsafeIntentLaunch() {
                return disable(8192);
            }

            public android.os.StrictMode.VmPolicy.Builder penaltyDeath() {
                return enable(268435456);
            }

            public android.os.StrictMode.VmPolicy.Builder penaltyDeathOnCleartextNetwork() {
                return enable(16777216);
            }

            public android.os.StrictMode.VmPolicy.Builder penaltyDeathOnFileUriExposure() {
                return enable(8388608);
            }

            public android.os.StrictMode.VmPolicy.Builder penaltyLog() {
                return enable(1073741824);
            }

            public android.os.StrictMode.VmPolicy.Builder penaltyDropBox() {
                return enable(67108864);
            }

            public android.os.StrictMode.VmPolicy.Builder penaltyListener(java.util.concurrent.Executor executor, android.os.StrictMode.OnVmViolationListener onVmViolationListener) {
                if (executor == null) {
                    throw new java.lang.NullPointerException("executor must not be null");
                }
                this.mListener = onVmViolationListener;
                this.mExecutor = executor;
                return this;
            }

            public android.os.StrictMode.VmPolicy.Builder penaltyListener(android.os.StrictMode.OnVmViolationListener onVmViolationListener, java.util.concurrent.Executor executor) {
                return penaltyListener(executor, onVmViolationListener);
            }

            private android.os.StrictMode.VmPolicy.Builder enable(int i) {
                this.mMask = i | this.mMask;
                return this;
            }

            android.os.StrictMode.VmPolicy.Builder disable(int i) {
                this.mMask = (~i) & this.mMask;
                return this;
            }

            public android.os.StrictMode.VmPolicy build() {
                if (this.mListener == null && this.mMask != 0 && (this.mMask & 1946157056) == 0) {
                    penaltyLog();
                }
                return new android.os.StrictMode.VmPolicy(this.mMask, this.mClassInstanceLimit != null ? this.mClassInstanceLimit : android.os.StrictMode.EMPTY_CLASS_LIMIT_MAP, this.mListener, this.mExecutor);
            }
        }
    }

    public static void setThreadPolicy(android.os.StrictMode.ThreadPolicy threadPolicy) {
        setThreadPolicyMask(threadPolicy.mask);
        sThreadViolationListener.set(threadPolicy.mListener);
        sThreadViolationExecutor.set(threadPolicy.mCallbackExecutor);
    }

    public static void setThreadPolicyMask(int i) {
        setBlockGuardPolicy(i);
        android.os.Binder.setThreadStrictModePolicy(i);
    }

    private static void setBlockGuardPolicy(int i) {
        android.os.StrictMode.AndroidBlockGuardPolicy androidBlockGuardPolicy;
        if (i == 0) {
            dalvik.system.BlockGuard.setThreadPolicy(dalvik.system.BlockGuard.LAX_POLICY);
            return;
        }
        dalvik.system.BlockGuard.Policy threadPolicy = dalvik.system.BlockGuard.getThreadPolicy();
        if (threadPolicy instanceof android.os.StrictMode.AndroidBlockGuardPolicy) {
            androidBlockGuardPolicy = (android.os.StrictMode.AndroidBlockGuardPolicy) threadPolicy;
        } else {
            androidBlockGuardPolicy = THREAD_ANDROID_POLICY.get();
            dalvik.system.BlockGuard.setThreadPolicy(androidBlockGuardPolicy);
        }
        androidBlockGuardPolicy.setThreadPolicyMask(i);
    }

    private static void setBlockGuardVmPolicy(int i) {
        if ((i & 2048) != 0) {
            dalvik.system.BlockGuard.setVmPolicy(VM_ANDROID_POLICY);
        } else {
            dalvik.system.BlockGuard.setVmPolicy(dalvik.system.BlockGuard.LAX_VM_POLICY);
        }
    }

    private static void setCloseGuardEnabled(boolean z) {
        if (!(dalvik.system.CloseGuard.getReporter() instanceof android.os.StrictMode.AndroidCloseGuardReporter)) {
            dalvik.system.CloseGuard.setReporter(new android.os.StrictMode.AndroidCloseGuardReporter());
        }
        dalvik.system.CloseGuard.setEnabled(z);
    }

    public static int getThreadPolicyMask() {
        dalvik.system.BlockGuard.Policy threadPolicy = dalvik.system.BlockGuard.getThreadPolicy();
        if (threadPolicy instanceof android.os.StrictMode.AndroidBlockGuardPolicy) {
            return ((android.os.StrictMode.AndroidBlockGuardPolicy) threadPolicy).getThreadPolicyMask();
        }
        return 0;
    }

    public static int getThreadPolicyMask$ravenwood() {
        return 0;
    }

    public static android.os.StrictMode.ThreadPolicy getThreadPolicy() {
        return new android.os.StrictMode.ThreadPolicy(getThreadPolicyMask(), sThreadViolationListener.get(), sThreadViolationExecutor.get());
    }

    public static android.os.StrictMode.ThreadPolicy allowThreadDiskWrites() {
        return new android.os.StrictMode.ThreadPolicy(allowThreadDiskWritesMask(), sThreadViolationListener.get(), sThreadViolationExecutor.get());
    }

    public static int allowThreadDiskWritesMask() {
        int threadPolicyMask = getThreadPolicyMask();
        int i = threadPolicyMask & (-4);
        if (i != threadPolicyMask) {
            setThreadPolicyMask(i);
        }
        return threadPolicyMask;
    }

    public static android.os.StrictMode.ThreadPolicy allowThreadDiskReads() {
        return new android.os.StrictMode.ThreadPolicy(allowThreadDiskReadsMask(), sThreadViolationListener.get(), sThreadViolationExecutor.get());
    }

    public static int allowThreadDiskReadsMask() {
        int threadPolicyMask = getThreadPolicyMask();
        int i = threadPolicyMask & (-3);
        if (i != threadPolicyMask) {
            setThreadPolicyMask(i);
        }
        return threadPolicyMask;
    }

    public static android.os.StrictMode.ThreadPolicy allowThreadViolations() {
        android.os.StrictMode.ThreadPolicy threadPolicy = getThreadPolicy();
        setThreadPolicyMask(0);
        return threadPolicy;
    }

    public static android.os.StrictMode.VmPolicy allowVmViolations() {
        android.os.StrictMode.VmPolicy vmPolicy = getVmPolicy();
        sVmPolicy = android.os.StrictMode.VmPolicy.LAX;
        return vmPolicy;
    }

    public static boolean isBundledSystemApp(android.content.pm.ApplicationInfo applicationInfo) {
        if (applicationInfo == null || applicationInfo.packageName == null) {
            return true;
        }
        if (!applicationInfo.isSystemApp() || applicationInfo.packageName.equals("com.android.vending") || applicationInfo.packageName.equals("com.android.chrome") || applicationInfo.packageName.equals("com.android.phone")) {
            return false;
        }
        return applicationInfo.packageName.equals("android") || applicationInfo.packageName.startsWith("android.") || applicationInfo.packageName.startsWith("com.android.");
    }

    public static void initThreadDefaults(android.content.pm.ApplicationInfo applicationInfo) {
        android.os.StrictMode.ThreadPolicy.Builder builder = new android.os.StrictMode.ThreadPolicy.Builder();
        if ((applicationInfo != null ? applicationInfo.targetSdkVersion : 10000) >= 11) {
            builder.detectNetwork();
            builder.penaltyDeathOnNetwork();
        }
        if (!android.os.Build.IS_USER && !android.os.SystemProperties.getBoolean(DISABLE_PROPERTY, false) && ((android.os.Build.IS_USERDEBUG || android.os.Build.IS_ENG) && isBundledSystemApp(applicationInfo))) {
            builder.detectAll();
            builder.penaltyDropBox();
            if (android.os.SystemProperties.getBoolean(VISUAL_PROPERTY, false)) {
                builder.penaltyFlashScreen();
            }
            if (android.os.Build.IS_ENG) {
                builder.penaltyLog();
            }
        }
        setThreadPolicy(builder.build());
    }

    public static void initVmDefaults(android.content.pm.ApplicationInfo applicationInfo) {
        android.os.StrictMode.VmPolicy.Builder builder = new android.os.StrictMode.VmPolicy.Builder();
        if ((applicationInfo != null ? applicationInfo.targetSdkVersion : 10000) >= 24) {
            builder.detectFileUriExposure();
            builder.penaltyDeathOnFileUriExposure();
        }
        if (!android.os.Build.IS_USER && !android.os.SystemProperties.getBoolean(DISABLE_PROPERTY, false)) {
            if (android.os.Build.IS_USERDEBUG) {
                if (isBundledSystemApp(applicationInfo)) {
                    builder.detectAll();
                    builder.permitActivityLeaks();
                    builder.penaltyDropBox();
                }
            } else if (android.os.Build.IS_ENG && isBundledSystemApp(applicationInfo)) {
                builder.detectAll();
                builder.penaltyDropBox();
                builder.penaltyLog();
            }
        }
        setVmPolicy(builder.build());
    }

    public static void enableDeathOnFileUriExposure() {
        sVmPolicy = new android.os.StrictMode.VmPolicy(8388608 | sVmPolicy.mask | 32, sVmPolicy.classInstanceLimit, sVmPolicy.mListener, sVmPolicy.mCallbackExecutor);
    }

    public static void disableDeathOnFileUriExposure() {
        sVmPolicy = new android.os.StrictMode.VmPolicy((-8388641) & sVmPolicy.mask, sVmPolicy.classInstanceLimit, sVmPolicy.mListener, sVmPolicy.mCallbackExecutor);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean tooManyViolationsThisLoop() {
        return violationsBeingTimed.get().size() >= 10;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class AndroidBlockGuardPolicy implements dalvik.system.BlockGuard.Policy {
        private android.util.ArrayMap<java.lang.Integer, java.lang.Long> mLastViolationTime;
        private android.util.SparseLongArray mRealLastViolationTime;
        private int mThreadPolicyMask;

        public AndroidBlockGuardPolicy(int i) {
            this.mThreadPolicyMask = i;
        }

        public java.lang.String toString() {
            return "AndroidBlockGuardPolicy; mPolicyMask=" + this.mThreadPolicyMask;
        }

        public int getPolicyMask() {
            return this.mThreadPolicyMask;
        }

        public void onWriteToDisk() {
            if ((this.mThreadPolicyMask & 1) == 0 || android.os.StrictMode.tooManyViolationsThisLoop()) {
                return;
            }
            startHandlingViolationException(new android.os.strictmode.DiskWriteViolation());
        }

        void onCustomSlowCall(java.lang.String str) {
            if ((this.mThreadPolicyMask & 8) == 0 || android.os.StrictMode.tooManyViolationsThisLoop()) {
                return;
            }
            startHandlingViolationException(new android.os.strictmode.CustomViolation(str));
        }

        void onResourceMismatch(java.lang.Object obj) {
            if ((this.mThreadPolicyMask & 16) == 0 || android.os.StrictMode.tooManyViolationsThisLoop()) {
                return;
            }
            startHandlingViolationException(new android.os.strictmode.ResourceMismatchViolation(obj));
        }

        public void onUnbufferedIO() {
            if ((this.mThreadPolicyMask & 32) == 0 || android.os.StrictMode.tooManyViolationsThisLoop()) {
                return;
            }
            startHandlingViolationException(new android.os.strictmode.UnbufferedIoViolation());
        }

        public void onReadFromDisk() {
            if ((this.mThreadPolicyMask & 2) == 0 || android.os.StrictMode.tooManyViolationsThisLoop()) {
                return;
            }
            startHandlingViolationException(new android.os.strictmode.DiskReadViolation());
        }

        public void onNetwork() {
            if ((this.mThreadPolicyMask & 4) == 0) {
                return;
            }
            if ((this.mThreadPolicyMask & 33554432) != 0) {
                throw new android.os.NetworkOnMainThreadException();
            }
            if (android.os.StrictMode.tooManyViolationsThisLoop()) {
                return;
            }
            startHandlingViolationException(new android.os.strictmode.NetworkViolation());
        }

        public void onExplicitGc() {
            if ((this.mThreadPolicyMask & 64) == 0 || android.os.StrictMode.tooManyViolationsThisLoop()) {
                return;
            }
            startHandlingViolationException(new android.os.strictmode.ExplicitGcViolation());
        }

        public int getThreadPolicyMask() {
            return this.mThreadPolicyMask;
        }

        public void setThreadPolicyMask(int i) {
            this.mThreadPolicyMask = i;
        }

        void startHandlingViolationException(android.os.strictmode.Violation violation) {
            android.os.StrictMode.ViolationInfo violationInfo = new android.os.StrictMode.ViolationInfo(violation, this.mThreadPolicyMask & (-65536));
            violationInfo.violationUptimeMillis = android.os.SystemClock.uptimeMillis();
            handleViolationWithTimingAttempt(violationInfo);
        }

        void handleViolationWithTimingAttempt(android.os.StrictMode.ViolationInfo violationInfo) {
            if (android.os.Looper.myLooper() == null || violationInfo.mPenaltyMask == 268435456) {
                violationInfo.durationMillis = -1;
                onThreadPolicyViolation(violationInfo);
                return;
            }
            final java.util.ArrayList arrayList = (java.util.ArrayList) android.os.StrictMode.violationsBeingTimed.get();
            if (arrayList.size() >= 10) {
                return;
            }
            arrayList.add(violationInfo);
            if (arrayList.size() > 1) {
                return;
            }
            final android.view.IWindowManager iWindowManager = violationInfo.penaltyEnabled(134217728) ? (android.view.IWindowManager) android.os.StrictMode.sWindowManager.get() : null;
            if (iWindowManager != null) {
                try {
                    iWindowManager.showStrictModeViolation(true);
                } catch (android.os.RemoteException e) {
                }
            }
            ((android.os.Handler) android.os.StrictMode.THREAD_HANDLER.get()).postAtFrontOfQueue(new java.lang.Runnable() { // from class: android.os.StrictMode$AndroidBlockGuardPolicy$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.StrictMode.AndroidBlockGuardPolicy.this.lambda$handleViolationWithTimingAttempt$0(iWindowManager, arrayList);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleViolationWithTimingAttempt$0(android.view.IWindowManager iWindowManager, java.util.ArrayList arrayList) {
            long uptimeMillis = android.os.SystemClock.uptimeMillis();
            int i = 0;
            if (iWindowManager != null) {
                try {
                    iWindowManager.showStrictModeViolation(false);
                } catch (android.os.RemoteException e) {
                }
            }
            while (i < arrayList.size()) {
                android.os.StrictMode.ViolationInfo violationInfo = (android.os.StrictMode.ViolationInfo) arrayList.get(i);
                i++;
                violationInfo.violationNumThisLoop = i;
                violationInfo.durationMillis = (int) (uptimeMillis - violationInfo.violationUptimeMillis);
                onThreadPolicyViolation(violationInfo);
            }
            arrayList.clear();
        }

        void onThreadPolicyViolation(android.os.StrictMode.ViolationInfo violationInfo) {
            long j;
            long j2;
            if (android.os.StrictMode.LOG_V) {
                android.util.Log.d(android.os.StrictMode.TAG, "onThreadPolicyViolation; penalty=" + violationInfo.mPenaltyMask);
            }
            if (violationInfo.penaltyEnabled(Integer.MIN_VALUE)) {
                java.util.ArrayList arrayList = (java.util.ArrayList) android.os.StrictMode.gatheredViolations.get();
                if (arrayList == null) {
                    arrayList = new java.util.ArrayList(1);
                    android.os.StrictMode.gatheredViolations.set(arrayList);
                }
                java.util.Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    if (violationInfo.getStackTrace().equals(((android.os.StrictMode.ViolationInfo) it.next()).getStackTrace())) {
                        return;
                    }
                }
                arrayList.add(violationInfo);
                return;
            }
            java.lang.Integer valueOf = java.lang.Integer.valueOf(violationInfo.hashCode());
            long uptimeMillis = android.os.SystemClock.uptimeMillis();
            if (android.os.StrictMode.sLogger != android.os.StrictMode.LOGCAT_LOGGER) {
                j = 0;
            } else {
                if (this.mRealLastViolationTime != null) {
                    java.lang.Long valueOf2 = java.lang.Long.valueOf(this.mRealLastViolationTime.get(valueOf.intValue()));
                    if (valueOf2 == null) {
                        j = 0;
                    } else {
                        j = valueOf2.longValue();
                    }
                    j2 = uptimeMillis;
                    android.os.StrictMode.clampViolationTimeMap(this.mRealLastViolationTime, java.lang.Math.max(1000L, java.lang.Math.max(30000L, 3000L)));
                } else {
                    j2 = uptimeMillis;
                    this.mRealLastViolationTime = new android.util.SparseLongArray(1);
                    j = 0;
                }
                uptimeMillis = j2;
                this.mRealLastViolationTime.put(valueOf.intValue(), uptimeMillis);
            }
            long j3 = j == 0 ? Long.MAX_VALUE : uptimeMillis - j;
            if (violationInfo.penaltyEnabled(1073741824) && j3 > 1000) {
                android.os.StrictMode.sLogger.log(violationInfo);
            }
            final android.os.strictmode.Violation violation = violationInfo.mViolation;
            int i = 536870912;
            if (!violationInfo.penaltyEnabled(536870912) || j3 <= 30000) {
                i = 0;
            }
            if (violationInfo.penaltyEnabled(67108864) && j3 > 3000) {
                i |= 67108864;
            }
            if (i != 0) {
                if (violationInfo.mPenaltyMask == 67108864) {
                    android.os.StrictMode.dropboxViolationAsync(i, violationInfo);
                } else {
                    android.os.StrictMode.handleApplicationStrictModeViolation(i, violationInfo);
                }
            }
            if (violationInfo.penaltyEnabled(268435456)) {
                throw new java.lang.RuntimeException("StrictMode ThreadPolicy violation", violation);
            }
            final android.os.StrictMode.OnThreadViolationListener onThreadViolationListener = (android.os.StrictMode.OnThreadViolationListener) android.os.StrictMode.sThreadViolationListener.get();
            java.util.concurrent.Executor executor = (java.util.concurrent.Executor) android.os.StrictMode.sThreadViolationExecutor.get();
            if (onThreadViolationListener != null && executor != null) {
                try {
                    executor.execute(new java.lang.Runnable() { // from class: android.os.StrictMode$AndroidBlockGuardPolicy$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.os.StrictMode.AndroidBlockGuardPolicy.lambda$onThreadPolicyViolation$1(android.os.StrictMode.OnThreadViolationListener.this, violation);
                        }
                    });
                } catch (java.util.concurrent.RejectedExecutionException e) {
                    android.util.Log.e(android.os.StrictMode.TAG, "ThreadPolicy penaltyCallback failed", e);
                }
            }
        }

        static /* synthetic */ void lambda$onThreadPolicyViolation$1(android.os.StrictMode.OnThreadViolationListener onThreadViolationListener, android.os.strictmode.Violation violation) {
            android.os.StrictMode.ThreadPolicy allowThreadViolations = android.os.StrictMode.allowThreadViolations();
            try {
                onThreadViolationListener.onThreadViolation(violation);
            } finally {
                android.os.StrictMode.setThreadPolicy(allowThreadViolations);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void dropboxViolationAsync(final int i, final android.os.StrictMode.ViolationInfo violationInfo) {
        int incrementAndGet = sDropboxCallsInFlight.incrementAndGet();
        if (incrementAndGet > 20) {
            sDropboxCallsInFlight.decrementAndGet();
            return;
        }
        if (LOG_V) {
            android.util.Log.d(TAG, "Dropboxing async; in-flight=" + incrementAndGet);
        }
        com.android.internal.os.BackgroundThread.getHandler().post(new java.lang.Runnable() { // from class: android.os.StrictMode$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                android.os.StrictMode.lambda$dropboxViolationAsync$2(i, violationInfo);
            }
        });
    }

    static /* synthetic */ void lambda$dropboxViolationAsync$2(int i, android.os.StrictMode.ViolationInfo violationInfo) {
        handleApplicationStrictModeViolation(i, violationInfo);
        int decrementAndGet = sDropboxCallsInFlight.decrementAndGet();
        if (LOG_V) {
            android.util.Log.d(TAG, "Dropbox complete; in-flight=" + decrementAndGet);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void handleApplicationStrictModeViolation(int i, android.os.StrictMode.ViolationInfo violationInfo) {
        int threadPolicyMask = getThreadPolicyMask();
        try {
            try {
                setThreadPolicyMask(0);
                android.app.IActivityManager service = android.app.ActivityManager.getService();
                if (service == null) {
                    android.util.Log.w(TAG, "No activity manager; failed to Dropbox violation.");
                } else {
                    service.handleApplicationStrictModeViolation(com.android.internal.os.RuntimeInit.getApplicationObject(), i, violationInfo);
                }
            } catch (android.os.RemoteException e) {
                if (!(e instanceof android.os.DeadObjectException)) {
                    android.util.Log.e(TAG, "RemoteException handling StrictMode violation", e);
                }
            }
        } finally {
            setThreadPolicyMask(threadPolicyMask);
        }
    }

    private static class AndroidCloseGuardReporter implements dalvik.system.CloseGuard.Reporter {
        private AndroidCloseGuardReporter() {
        }

        public void report(java.lang.String str, java.lang.Throwable th) {
            android.os.StrictMode.onVmPolicyViolation(new android.os.strictmode.LeakedClosableViolation(str, th));
        }

        public void report(java.lang.String str) {
            android.os.StrictMode.onVmPolicyViolation(new android.os.strictmode.LeakedClosableViolation(str));
        }
    }

    static boolean hasGatheredViolations() {
        return gatheredViolations.get() != null;
    }

    static void clearGatheredViolations() {
        gatheredViolations.set(null);
    }

    public static void conditionallyCheckInstanceCounts() {
        android.os.StrictMode.VmPolicy vmPolicy = getVmPolicy();
        int size = vmPolicy.classInstanceLimit.size();
        if (size == 0) {
            return;
        }
        int threadPolicyMask = getThreadPolicyMask();
        setThreadPolicyMask(0);
        java.lang.System.gc();
        java.lang.System.runFinalization();
        java.lang.System.gc();
        setThreadPolicyMask(threadPolicyMask);
        java.lang.Class[] clsArr = (java.lang.Class[]) vmPolicy.classInstanceLimit.keySet().toArray(new java.lang.Class[size]);
        long[] countInstancesOfClasses = dalvik.system.VMDebug.countInstancesOfClasses(clsArr, false);
        for (int i = 0; i < clsArr.length; i++) {
            java.lang.Class cls = clsArr[i];
            int intValue = vmPolicy.classInstanceLimit.get(cls).intValue();
            long j = countInstancesOfClasses[i];
            if (j > intValue) {
                onVmPolicyViolation(new android.os.strictmode.InstanceCountViolation(cls, j, intValue));
            }
        }
    }

    public static void setVmPolicy(android.os.StrictMode.VmPolicy vmPolicy) {
        int i;
        synchronized (android.os.StrictMode.class) {
            sVmPolicy = vmPolicy;
            setCloseGuardEnabled(vmClosableObjectLeaksEnabled());
            android.os.Looper mainLooper = android.os.Looper.getMainLooper();
            if (mainLooper != null) {
                android.os.MessageQueue messageQueue = mainLooper.mQueue;
                if (vmPolicy.classInstanceLimit.size() != 0 && (sVmPolicy.mask & (-65536)) != 0) {
                    if (!sIsIdlerRegistered) {
                        messageQueue.addIdleHandler(sProcessIdleHandler);
                        sIsIdlerRegistered = true;
                    }
                }
                messageQueue.removeIdleHandler(sProcessIdleHandler);
                sIsIdlerRegistered = false;
            }
            if ((sVmPolicy.mask & 64) == 0) {
                i = 0;
            } else {
                if ((sVmPolicy.mask & 268435456) == 0 && (sVmPolicy.mask & 16777216) == 0) {
                    i = 1;
                }
                i = 2;
            }
            android.os.INetworkManagementService asInterface = android.os.INetworkManagementService.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.NETWORKMANAGEMENT_SERVICE));
            if (asInterface != null) {
                try {
                    asInterface.setUidCleartextNetworkPolicy(android.os.Process.myUid(), i);
                } catch (android.os.RemoteException e) {
                }
            } else if (i != 0) {
                android.util.Log.w(TAG, "Dropping requested network policy due to missing service!");
            }
            if ((sVmPolicy.mask & 512) != 0) {
                dalvik.system.VMRuntime.setNonSdkApiUsageConsumer(sNonSdkApiUsageConsumer);
                dalvik.system.VMRuntime.setDedupeHiddenApiWarnings(false);
            } else {
                dalvik.system.VMRuntime.setNonSdkApiUsageConsumer((java.util.function.Consumer) null);
                dalvik.system.VMRuntime.setDedupeHiddenApiWarnings(true);
            }
            if ((sVmPolicy.mask & 8192) != 0) {
                registerIntentMatchingRestrictionCallback();
            }
            setBlockGuardVmPolicy(sVmPolicy.mask);
        }
    }

    private static void registerIntentMatchingRestrictionCallback() {
        try {
            android.app.ActivityManager.getService().registerStrictModeCallback(new android.os.StrictMode.UnsafeIntentStrictModeCallback());
        } catch (android.os.RemoteException e) {
            if (!(e instanceof android.os.DeadObjectException)) {
                android.util.Log.e(TAG, "RemoteException handling StrictMode violation", e);
            }
        }
    }

    private static final class UnsafeIntentStrictModeCallback extends android.app.IUnsafeIntentStrictModeCallback.Stub {
        private UnsafeIntentStrictModeCallback() {
        }

        @Override // android.app.IUnsafeIntentStrictModeCallback
        public void onImplicitIntentMatchedInternalComponent(android.content.Intent intent) {
            if (android.os.StrictMode.vmUnsafeIntentLaunchEnabled()) {
                android.os.StrictMode.onUnsafeIntentLaunch(intent, "Launch of unsafe implicit intent: " + intent);
            }
        }
    }

    public static android.os.StrictMode.VmPolicy getVmPolicy() {
        android.os.StrictMode.VmPolicy vmPolicy;
        synchronized (android.os.StrictMode.class) {
            vmPolicy = sVmPolicy;
        }
        return vmPolicy;
    }

    public static void enableDefaults() {
        setThreadPolicy(new android.os.StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
        setVmPolicy(new android.os.StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
    }

    public static boolean vmSqliteObjectLeaksEnabled() {
        return (sVmPolicy.mask & 1) != 0;
    }

    public static boolean vmClosableObjectLeaksEnabled() {
        return (sVmPolicy.mask & 2) != 0;
    }

    public static boolean vmRegistrationLeaksEnabled() {
        return (sVmPolicy.mask & 16) != 0;
    }

    public static boolean vmFileUriExposureEnabled() {
        return (sVmPolicy.mask & 32) != 0;
    }

    public static boolean vmCleartextNetworkEnabled() {
        return (sVmPolicy.mask & 64) != 0;
    }

    public static boolean vmContentUriWithoutPermissionEnabled() {
        return (sVmPolicy.mask & 128) != 0;
    }

    public static boolean vmUntaggedSocketEnabled() {
        return (sVmPolicy.mask & 256) != 0;
    }

    public static boolean vmImplicitDirectBootEnabled() {
        return (sVmPolicy.mask & 1024) != 0;
    }

    public static boolean vmCredentialProtectedWhileLockedEnabled() {
        return (sVmPolicy.mask & 2048) != 0;
    }

    public static boolean vmIncorrectContextUseEnabled() {
        return (sVmPolicy.mask & 4096) != 0;
    }

    public static boolean vmUnsafeIntentLaunchEnabled() {
        return (sVmPolicy.mask & 8192) != 0;
    }

    public static void onSqliteObjectLeaked(java.lang.String str, java.lang.Throwable th) {
        onVmPolicyViolation(new android.os.strictmode.SqliteObjectLeakedViolation(str, th));
    }

    public static void onWebViewMethodCalledOnWrongThread(java.lang.Throwable th) {
        onVmPolicyViolation(new android.os.strictmode.WebViewMethodCalledOnWrongThreadViolation(th));
    }

    public static void onIntentReceiverLeaked(java.lang.Throwable th) {
        onVmPolicyViolation(new android.os.strictmode.IntentReceiverLeakedViolation(th));
    }

    public static void onServiceConnectionLeaked(java.lang.Throwable th) {
        onVmPolicyViolation(new android.os.strictmode.ServiceConnectionLeakedViolation(th));
    }

    public static void onFileUriExposed(android.net.Uri uri, java.lang.String str) {
        java.lang.String str2 = uri + " exposed beyond app through " + str;
        if ((sVmPolicy.mask & 8388608) != 0) {
            throw new android.os.FileUriExposedException(str2);
        }
        onVmPolicyViolation(new android.os.strictmode.FileUriExposedViolation(str2));
    }

    public static void onContentUriWithoutPermission(android.net.Uri uri, java.lang.String str) {
        onVmPolicyViolation(new android.os.strictmode.ContentUriWithoutPermissionViolation(uri, str));
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0041 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void onCleartextNetworkDetected(byte[] bArr) {
        byte[] bArr2;
        if (bArr != null) {
            if (bArr.length >= 20 && (bArr[0] & 240) == 64) {
                bArr2 = new byte[4];
                java.lang.System.arraycopy(bArr, 16, bArr2, 0, 4);
            } else if (bArr.length >= 40 && (bArr[0] & 240) == 96) {
                bArr2 = new byte[16];
                java.lang.System.arraycopy(bArr, 24, bArr2, 0, 16);
            }
            java.lang.StringBuilder append = new java.lang.StringBuilder("Detected cleartext network traffic from UID ").append(android.os.Process.myUid());
            if (bArr2 != null) {
                try {
                    append.append(" to ").append(java.net.InetAddress.getByAddress(bArr2));
                } catch (java.net.UnknownHostException e) {
                }
            }
            append.append(com.android.internal.util.HexDump.dumpHexString(bArr).trim()).append(' ');
            onVmPolicyViolation(new android.os.strictmode.CleartextNetworkViolation(append.toString()), (sVmPolicy.mask & 16777216) != 0);
        }
        bArr2 = null;
        java.lang.StringBuilder append2 = new java.lang.StringBuilder("Detected cleartext network traffic from UID ").append(android.os.Process.myUid());
        if (bArr2 != null) {
        }
        append2.append(com.android.internal.util.HexDump.dumpHexString(bArr).trim()).append(' ');
        onVmPolicyViolation(new android.os.strictmode.CleartextNetworkViolation(append2.toString()), (sVmPolicy.mask & 16777216) != 0);
    }

    public static void onUntaggedSocket() {
        onVmPolicyViolation(new android.os.strictmode.UntaggedSocketViolation());
    }

    public static void onImplicitDirectBoot() {
        onVmPolicyViolation(new android.os.strictmode.ImplicitDirectBootViolation());
    }

    public static void onIncorrectContextUsed(java.lang.String str, java.lang.Throwable th) {
        onVmPolicyViolation(new android.os.strictmode.IncorrectContextUseViolation(str, th));
    }

    public static void assertConfigurationContext(android.content.Context context, java.lang.String str) {
        if (vmIncorrectContextUseEnabled() && !context.isConfigurationContext()) {
            java.lang.String str2 = "Tried to access the API:" + str + " which needs to have proper configuration from a non-UI Context:" + context;
            java.lang.String str3 = "The API:" + str + " needs a proper configuration. Use UI contexts such as an activity or a context created via createWindowContext(Display, int, Bundle) or  createConfigurationContext(Configuration) with a proper configuration.";
            java.lang.IllegalAccessException illegalAccessException = new java.lang.IllegalAccessException(str2);
            onIncorrectContextUsed(str3, illegalAccessException);
            android.util.Log.e(TAG, str2 + " " + str3, illegalAccessException);
        }
    }

    public static void assertUiContext(android.content.Context context, java.lang.String str) {
        if (vmIncorrectContextUseEnabled() && !context.isUiContext()) {
            java.lang.String str2 = "Tried to access UI related API:" + str + " from a non-UI Context:" + context;
            java.lang.String str3 = str + " should be accessed from Activity or other UI Contexts. Use an Activity or a Context created with Context#createWindowContext(int, Bundle), which are adjusted to the configuration and visual bounds of an area on screen.";
            java.lang.IllegalAccessException illegalAccessException = new java.lang.IllegalAccessException(str2);
            onIncorrectContextUsed(str3, illegalAccessException);
            android.util.Log.e(TAG, str2 + " " + str3, illegalAccessException);
        }
    }

    public static void onUnsafeIntentLaunch(android.content.Intent intent) {
        onVmPolicyViolation(new android.os.strictmode.UnsafeIntentLaunchViolation(intent));
    }

    public static void onUnsafeIntentLaunch(android.content.Intent intent, java.lang.String str) {
        onVmPolicyViolation(new android.os.strictmode.UnsafeIntentLaunchViolation(intent, str));
    }

    private static boolean isCeStorageUnlocked(int i) {
        android.os.storage.IStorageManager iStorageManager = sStorageManager;
        if (iStorageManager == null && (iStorageManager = android.os.storage.IStorageManager.Stub.asInterface(android.os.ServiceManager.getService("mount"))) != null) {
            sStorageManager = iStorageManager;
        }
        if (iStorageManager != null) {
            try {
                return iStorageManager.isCeStorageUnlocked(i);
            } catch (android.os.RemoteException e) {
                sStorageManager = null;
                return false;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void onCredentialProtectedPathAccess(java.lang.String str, int i) {
        if (i == android.os.UserHandle.myUserId()) {
            if (sCeStorageUnlocked) {
                return;
            }
            if (isCeStorageUnlocked(i)) {
                sCeStorageUnlocked = true;
                return;
            }
        } else if (isCeStorageUnlocked(i)) {
            return;
        }
        onVmPolicyViolation(new android.os.strictmode.CredentialProtectedWhileLockedViolation("Accessed credential protected path " + str + " while user " + i + " was locked"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void clampViolationTimeMap(android.util.SparseLongArray sparseLongArray, long j) {
        int i = 0;
        while (i < sparseLongArray.size()) {
            if (sparseLongArray.valueAt(i) < j) {
                sparseLongArray.removeAt(i);
            } else {
                i++;
            }
        }
    }

    public static void onVmPolicyViolation(android.os.strictmode.Violation violation) {
        onVmPolicyViolation(violation, false);
    }

    public static void onVmPolicyViolation(final android.os.strictmode.Violation violation, boolean z) {
        long j;
        android.os.StrictMode.VmPolicy vmPolicy = getVmPolicy();
        boolean z2 = (vmPolicy.mask & 67108864) != 0;
        boolean z3 = (vmPolicy.mask & 268435456) != 0 || z;
        boolean z4 = (vmPolicy.mask & 1073741824) != 0;
        android.os.StrictMode.ViolationInfo violationInfo = new android.os.StrictMode.ViolationInfo(violation, vmPolicy.mask & (-65536));
        violationInfo.numAnimationsRunning = 0;
        violationInfo.tags = null;
        violationInfo.broadcastIntentAction = null;
        java.lang.Integer valueOf = java.lang.Integer.valueOf(violationInfo.hashCode());
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        if (sLogger != LOGCAT_LOGGER) {
            j = Long.MAX_VALUE;
        } else {
            synchronized (sRealLastVmViolationTime) {
                if (sRealLastVmViolationTime.indexOfKey(valueOf.intValue()) < 0) {
                    j = Long.MAX_VALUE;
                } else {
                    j = uptimeMillis - sRealLastVmViolationTime.get(valueOf.intValue());
                }
                if (j > 1000) {
                    sRealLastVmViolationTime.put(valueOf.intValue(), uptimeMillis);
                }
                clampViolationTimeMap(sRealLastVmViolationTime, uptimeMillis - java.lang.Math.max(1000L, 1000L));
            }
        }
        if (j <= 1000) {
            return;
        }
        if (z4 && sLogger != null && j > 1000) {
            sLogger.log(violationInfo);
        }
        if (z2) {
            if (z3) {
                handleApplicationStrictModeViolation(67108864, violationInfo);
            } else {
                dropboxViolationAsync(67108864, violationInfo);
            }
        }
        if (z3) {
            java.lang.System.err.println("StrictMode VmPolicy violation with POLICY_DEATH; shutting down.");
            android.os.Process.killProcess(android.os.Process.myPid());
            java.lang.System.exit(10);
        }
        if (vmPolicy.mListener != null && vmPolicy.mCallbackExecutor != null) {
            final android.os.StrictMode.OnVmViolationListener onVmViolationListener = vmPolicy.mListener;
            try {
                vmPolicy.mCallbackExecutor.execute(new java.lang.Runnable() { // from class: android.os.StrictMode$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.os.StrictMode.lambda$onVmPolicyViolation$3(android.os.StrictMode.OnVmViolationListener.this, violation);
                    }
                });
            } catch (java.util.concurrent.RejectedExecutionException e) {
                android.util.Log.e(TAG, "VmPolicy penaltyCallback failed", e);
            }
        }
    }

    static /* synthetic */ void lambda$onVmPolicyViolation$3(android.os.StrictMode.OnVmViolationListener onVmViolationListener, android.os.strictmode.Violation violation) {
        android.os.StrictMode.VmPolicy allowVmViolations = allowVmViolations();
        try {
            onVmViolationListener.onVmViolation(violation);
        } finally {
            setVmPolicy(allowVmViolations);
        }
    }

    static void writeGatheredViolationsToParcel(android.os.Parcel parcel) {
        java.util.ArrayList<android.os.StrictMode.ViolationInfo> arrayList = gatheredViolations.get();
        if (arrayList == null) {
            parcel.writeInt(0);
        } else {
            int min = java.lang.Math.min(arrayList.size(), 3);
            parcel.writeInt(min);
            for (int i = 0; i < min; i++) {
                arrayList.get(i).writeToParcel(parcel, 0);
            }
        }
        gatheredViolations.set(null);
    }

    static void readAndHandleBinderCallViolations(android.os.Parcel parcel) {
        java.lang.Throwable th = new java.lang.Throwable();
        boolean z = (getThreadPolicyMask() & Integer.MIN_VALUE) != 0;
        int readInt = parcel.readInt();
        for (int i = 0; i < readInt; i++) {
            android.os.StrictMode.ViolationInfo violationInfo = new android.os.StrictMode.ViolationInfo(parcel, !z);
            violationInfo.addLocalStack(th);
            dalvik.system.BlockGuard.Policy threadPolicy = dalvik.system.BlockGuard.getThreadPolicy();
            if (threadPolicy instanceof android.os.StrictMode.AndroidBlockGuardPolicy) {
                ((android.os.StrictMode.AndroidBlockGuardPolicy) threadPolicy).handleViolationWithTimingAttempt(violationInfo);
            }
        }
    }

    private static void onBinderStrictModePolicyChange(int i) {
        setBlockGuardPolicy(i);
    }

    public static class Span {
        private final android.os.StrictMode.ThreadSpanState mContainerState;
        private long mCreateMillis;
        private java.lang.String mName;
        private android.os.StrictMode.Span mNext;
        private android.os.StrictMode.Span mPrev;

        Span(android.os.StrictMode.ThreadSpanState threadSpanState) {
            this.mContainerState = threadSpanState;
        }

        protected Span() {
            this.mContainerState = null;
        }

        public void finish() {
            android.os.StrictMode.ThreadSpanState threadSpanState = this.mContainerState;
            synchronized (threadSpanState) {
                if (this.mName == null) {
                    return;
                }
                if (this.mPrev != null) {
                    this.mPrev.mNext = this.mNext;
                }
                if (this.mNext != null) {
                    this.mNext.mPrev = this.mPrev;
                }
                if (threadSpanState.mActiveHead == this) {
                    threadSpanState.mActiveHead = this.mNext;
                }
                threadSpanState.mActiveSize--;
                if (android.os.StrictMode.LOG_V) {
                    android.util.Log.d(android.os.StrictMode.TAG, "Span finished=" + this.mName + "; size=" + threadSpanState.mActiveSize);
                }
                this.mCreateMillis = -1L;
                this.mName = null;
                this.mPrev = null;
                this.mNext = null;
                if (threadSpanState.mFreeListSize < 5) {
                    this.mNext = threadSpanState.mFreeListHead;
                    threadSpanState.mFreeListHead = this;
                    threadSpanState.mFreeListSize++;
                }
            }
        }
    }

    private static class ThreadSpanState {
        public android.os.StrictMode.Span mActiveHead;
        public int mActiveSize;
        public android.os.StrictMode.Span mFreeListHead;
        public int mFreeListSize;

        private ThreadSpanState() {
        }
    }

    public static android.os.StrictMode.Span enterCriticalSpan(java.lang.String str) {
        android.os.StrictMode.Span span;
        if (android.os.Build.IS_USER) {
            return NO_OP_SPAN;
        }
        if (str == null || str.isEmpty()) {
            throw new java.lang.IllegalArgumentException("name must be non-null and non-empty");
        }
        android.os.StrictMode.ThreadSpanState threadSpanState = sThisThreadSpanState.get();
        synchronized (threadSpanState) {
            if (threadSpanState.mFreeListHead != null) {
                span = threadSpanState.mFreeListHead;
                threadSpanState.mFreeListHead = span.mNext;
                threadSpanState.mFreeListSize--;
            } else {
                span = new android.os.StrictMode.Span(threadSpanState);
            }
            span.mName = str;
            span.mCreateMillis = android.os.SystemClock.uptimeMillis();
            span.mNext = threadSpanState.mActiveHead;
            span.mPrev = null;
            threadSpanState.mActiveHead = span;
            threadSpanState.mActiveSize++;
            if (span.mNext != null) {
                span.mNext.mPrev = span;
            }
            if (LOG_V) {
                android.util.Log.d(TAG, "Span enter=" + str + "; size=" + threadSpanState.mActiveSize);
            }
        }
        return span;
    }

    public static void noteSlowCall(java.lang.String str) {
        dalvik.system.BlockGuard.Policy threadPolicy = dalvik.system.BlockGuard.getThreadPolicy();
        if (!(threadPolicy instanceof android.os.StrictMode.AndroidBlockGuardPolicy)) {
            return;
        }
        ((android.os.StrictMode.AndroidBlockGuardPolicy) threadPolicy).onCustomSlowCall(str);
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static void noteUntaggedSocket() {
        if (vmUntaggedSocketEnabled()) {
            onUntaggedSocket();
        }
    }

    public static void noteResourceMismatch(java.lang.Object obj) {
        dalvik.system.BlockGuard.Policy threadPolicy = dalvik.system.BlockGuard.getThreadPolicy();
        if (!(threadPolicy instanceof android.os.StrictMode.AndroidBlockGuardPolicy)) {
            return;
        }
        ((android.os.StrictMode.AndroidBlockGuardPolicy) threadPolicy).onResourceMismatch(obj);
    }

    public static void noteUnbufferedIO() {
        dalvik.system.BlockGuard.Policy threadPolicy = dalvik.system.BlockGuard.getThreadPolicy();
        if (!(threadPolicy instanceof android.os.StrictMode.AndroidBlockGuardPolicy)) {
            return;
        }
        threadPolicy.onUnbufferedIO();
    }

    public static void noteDiskRead() {
        dalvik.system.BlockGuard.Policy threadPolicy = dalvik.system.BlockGuard.getThreadPolicy();
        if (!(threadPolicy instanceof android.os.StrictMode.AndroidBlockGuardPolicy)) {
            return;
        }
        threadPolicy.onReadFromDisk();
    }

    public static void noteDiskWrite() {
        dalvik.system.BlockGuard.Policy threadPolicy = dalvik.system.BlockGuard.getThreadPolicy();
        if (!(threadPolicy instanceof android.os.StrictMode.AndroidBlockGuardPolicy)) {
            return;
        }
        threadPolicy.onWriteToDisk();
    }

    public static java.lang.Object trackActivity(java.lang.Object obj) {
        return new android.os.StrictMode.InstanceTracker(obj);
    }

    public static void incrementExpectedActivityCount(java.lang.Class cls) {
        if (cls == null) {
            return;
        }
        synchronized (android.os.StrictMode.class) {
            if ((sVmPolicy.mask & 4) == 0) {
                return;
            }
            java.lang.Integer num = sExpectedActivityInstanceCount.get(cls);
            sExpectedActivityInstanceCount.put(cls, java.lang.Integer.valueOf(num == null ? android.os.StrictMode.InstanceTracker.getInstanceCount(cls) + 1 : num.intValue() + 1));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x002c A[Catch: all -> 0x0060, TryCatch #0 {, blocks: (B:7:0x0006, B:9:0x000e, B:11:0x0010, B:13:0x001b, B:16:0x0022, B:18:0x002c, B:19:0x003b, B:20:0x003d, B:29:0x0032), top: B:6:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0044 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0045  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0032 A[Catch: all -> 0x0060, TryCatch #0 {, blocks: (B:7:0x0006, B:9:0x000e, B:11:0x0010, B:13:0x001b, B:16:0x0022, B:18:0x002c, B:19:0x003b, B:20:0x003d, B:29:0x0032), top: B:6:0x0006 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void decrementExpectedActivityCount(java.lang.Class cls) {
        int i;
        int i2;
        if (cls == null) {
            return;
        }
        synchronized (android.os.StrictMode.class) {
            if ((sVmPolicy.mask & 4) == 0) {
                return;
            }
            java.lang.Integer num = sExpectedActivityInstanceCount.get(cls);
            if (num != null && num.intValue() != 0) {
                i = num.intValue() - 1;
                if (i != 0) {
                    sExpectedActivityInstanceCount.remove(cls);
                } else {
                    sExpectedActivityInstanceCount.put(cls, java.lang.Integer.valueOf(i));
                }
                i2 = i + 1;
                if (android.os.StrictMode.InstanceTracker.getInstanceCount(cls) > i2) {
                    return;
                }
                java.lang.System.gc();
                java.lang.System.runFinalization();
                java.lang.System.gc();
                long countInstancesOfClass = dalvik.system.VMDebug.countInstancesOfClass(cls, false);
                if (countInstancesOfClass > i2) {
                    onVmPolicyViolation(new android.os.strictmode.InstanceCountViolation(cls, countInstancesOfClass, i2));
                    return;
                }
                return;
            }
            i = 0;
            if (i != 0) {
            }
            i2 = i + 1;
            if (android.os.StrictMode.InstanceTracker.getInstanceCount(cls) > i2) {
            }
        }
    }

    public static final class ViolationInfo implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.os.StrictMode.ViolationInfo> CREATOR = new android.os.Parcelable.Creator<android.os.StrictMode.ViolationInfo>() { // from class: android.os.StrictMode.ViolationInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.os.StrictMode.ViolationInfo createFromParcel(android.os.Parcel parcel) {
                return new android.os.StrictMode.ViolationInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.os.StrictMode.ViolationInfo[] newArray(int i) {
                return new android.os.StrictMode.ViolationInfo[i];
            }
        };
        public java.lang.String broadcastIntentAction;
        public int durationMillis;
        private final java.util.Deque<java.lang.StackTraceElement[]> mBinderStack;
        private final int mPenaltyMask;
        private java.lang.String mStackTrace;
        private final android.os.strictmode.Violation mViolation;
        public int numAnimationsRunning;
        public long numInstances;
        public java.lang.String[] tags;
        public int violationNumThisLoop;
        public long violationUptimeMillis;

        ViolationInfo(android.os.strictmode.Violation violation, int i) {
            this.mBinderStack = new java.util.ArrayDeque();
            this.durationMillis = -1;
            int i2 = 0;
            this.numAnimationsRunning = 0;
            this.numInstances = -1L;
            this.mViolation = violation;
            this.mPenaltyMask = i;
            this.violationUptimeMillis = android.os.SystemClock.uptimeMillis();
            this.numAnimationsRunning = android.animation.ValueAnimator.getCurrentAnimationsCount();
            android.content.Intent intentBeingBroadcast = android.app.ActivityThread.getIntentBeingBroadcast();
            if (intentBeingBroadcast != null) {
                this.broadcastIntentAction = intentBeingBroadcast.getAction();
            }
            android.os.StrictMode.ThreadSpanState threadSpanState = (android.os.StrictMode.ThreadSpanState) android.os.StrictMode.sThisThreadSpanState.get();
            if (violation instanceof android.os.strictmode.InstanceCountViolation) {
                this.numInstances = ((android.os.strictmode.InstanceCountViolation) violation).getNumberOfInstances();
            }
            synchronized (threadSpanState) {
                int i3 = threadSpanState.mActiveSize;
                i3 = i3 > 20 ? 20 : i3;
                if (i3 != 0) {
                    this.tags = new java.lang.String[i3];
                    for (android.os.StrictMode.Span span = threadSpanState.mActiveHead; span != null && i2 < i3; span = span.mNext) {
                        this.tags[i2] = span.mName;
                        i2++;
                    }
                }
            }
        }

        public java.lang.String getStackTrace() {
            if (this.mStackTrace == null) {
                java.io.StringWriter stringWriter = new java.io.StringWriter();
                com.android.internal.util.FastPrintWriter fastPrintWriter = new com.android.internal.util.FastPrintWriter((java.io.Writer) stringWriter, false, 256);
                this.mViolation.printStackTrace(fastPrintWriter);
                for (java.lang.StackTraceElement[] stackTraceElementArr : this.mBinderStack) {
                    fastPrintWriter.append((java.lang.CharSequence) "# via Binder call with stack:\n");
                    for (java.lang.StackTraceElement stackTraceElement : stackTraceElementArr) {
                        fastPrintWriter.append((java.lang.CharSequence) "\tat ");
                        fastPrintWriter.append((java.lang.CharSequence) stackTraceElement.toString());
                        fastPrintWriter.append('\n');
                    }
                }
                fastPrintWriter.flush();
                fastPrintWriter.close();
                this.mStackTrace = stringWriter.toString();
            }
            return this.mStackTrace;
        }

        public java.lang.Class<? extends android.os.strictmode.Violation> getViolationClass() {
            return this.mViolation.getClass();
        }

        public java.lang.String getViolationDetails() {
            return this.mViolation.getMessage();
        }

        boolean penaltyEnabled(int i) {
            return (i & this.mPenaltyMask) != 0;
        }

        void addLocalStack(java.lang.Throwable th) {
            this.mBinderStack.addFirst(th.getStackTrace());
        }

        public int hashCode() {
            int i;
            if (this.mViolation == null) {
                i = 17;
            } else {
                i = 629 + this.mViolation.hashCode();
            }
            if (this.numAnimationsRunning != 0) {
                i *= 37;
            }
            if (this.broadcastIntentAction != null) {
                i = (i * 37) + this.broadcastIntentAction.hashCode();
            }
            if (this.tags != null) {
                for (java.lang.String str : this.tags) {
                    i = (i * 37) + str.hashCode();
                }
            }
            return i;
        }

        public ViolationInfo(android.os.Parcel parcel) {
            this(parcel, false);
        }

        public ViolationInfo(android.os.Parcel parcel, boolean z) {
            this.mBinderStack = new java.util.ArrayDeque();
            this.durationMillis = -1;
            this.numAnimationsRunning = 0;
            this.numInstances = -1L;
            this.mViolation = (android.os.strictmode.Violation) parcel.readSerializable(android.os.strictmode.Violation.class.getClassLoader(), android.os.strictmode.Violation.class);
            int readInt = parcel.readInt();
            for (int i = 0; i < readInt; i++) {
                int readInt2 = parcel.readInt();
                java.lang.StackTraceElement[] stackTraceElementArr = new java.lang.StackTraceElement[readInt2];
                for (int i2 = 0; i2 < readInt2; i2++) {
                    stackTraceElementArr[i2] = new java.lang.StackTraceElement(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readInt());
                }
                this.mBinderStack.add(stackTraceElementArr);
            }
            int readInt3 = parcel.readInt();
            if (z) {
                this.mPenaltyMask = Integer.MAX_VALUE & readInt3;
            } else {
                this.mPenaltyMask = readInt3;
            }
            this.durationMillis = parcel.readInt();
            this.violationNumThisLoop = parcel.readInt();
            this.numAnimationsRunning = parcel.readInt();
            this.violationUptimeMillis = parcel.readLong();
            this.numInstances = parcel.readLong();
            this.broadcastIntentAction = parcel.readString();
            this.tags = parcel.readStringArray();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeSerializable(this.mViolation);
            parcel.writeInt(this.mBinderStack.size());
            for (java.lang.StackTraceElement[] stackTraceElementArr : this.mBinderStack) {
                parcel.writeInt(stackTraceElementArr.length);
                for (java.lang.StackTraceElement stackTraceElement : stackTraceElementArr) {
                    parcel.writeString(stackTraceElement.getClassName());
                    parcel.writeString(stackTraceElement.getMethodName());
                    parcel.writeString(stackTraceElement.getFileName());
                    parcel.writeInt(stackTraceElement.getLineNumber());
                }
            }
            parcel.dataPosition();
            parcel.writeInt(this.mPenaltyMask);
            parcel.writeInt(this.durationMillis);
            parcel.writeInt(this.violationNumThisLoop);
            parcel.writeInt(this.numAnimationsRunning);
            parcel.writeLong(this.violationUptimeMillis);
            parcel.writeLong(this.numInstances);
            parcel.writeString(this.broadcastIntentAction);
            parcel.writeStringArray(this.tags);
            parcel.dataPosition();
        }

        public void dump(android.util.Printer printer, java.lang.String str) {
            printer.println(str + "stackTrace: " + getStackTrace());
            printer.println(str + "penalty: " + this.mPenaltyMask);
            if (this.durationMillis != -1) {
                printer.println(str + "durationMillis: " + this.durationMillis);
            }
            if (this.numInstances != -1) {
                printer.println(str + "numInstances: " + this.numInstances);
            }
            if (this.violationNumThisLoop != 0) {
                printer.println(str + "violationNumThisLoop: " + this.violationNumThisLoop);
            }
            if (this.numAnimationsRunning != 0) {
                printer.println(str + "numAnimationsRunning: " + this.numAnimationsRunning);
            }
            printer.println(str + "violationUptimeMillis: " + this.violationUptimeMillis);
            if (this.broadcastIntentAction != null) {
                printer.println(str + "broadcastIntentAction: " + this.broadcastIntentAction);
            }
            if (this.tags != null) {
                java.lang.String[] strArr = this.tags;
                int length = strArr.length;
                int i = 0;
                int i2 = 0;
                while (i < length) {
                    printer.println(str + "tag[" + i2 + "]: " + strArr[i]);
                    i++;
                    i2++;
                }
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }
    }

    private static final class InstanceTracker {
        private static final java.util.HashMap<java.lang.Class<?>, java.lang.Integer> sInstanceCounts = new java.util.HashMap<>();
        private final java.lang.Class<?> mKlass;

        public InstanceTracker(java.lang.Object obj) {
            this.mKlass = obj.getClass();
            synchronized (sInstanceCounts) {
                java.lang.Integer num = sInstanceCounts.get(this.mKlass);
                sInstanceCounts.put(this.mKlass, java.lang.Integer.valueOf(num != null ? 1 + num.intValue() : 1));
            }
        }

        protected void finalize() throws java.lang.Throwable {
            try {
                synchronized (sInstanceCounts) {
                    java.lang.Integer num = sInstanceCounts.get(this.mKlass);
                    if (num != null) {
                        int intValue = num.intValue() - 1;
                        if (intValue > 0) {
                            sInstanceCounts.put(this.mKlass, java.lang.Integer.valueOf(intValue));
                        } else {
                            sInstanceCounts.remove(this.mKlass);
                        }
                    }
                }
            } finally {
                super.finalize();
            }
        }

        public static int getInstanceCount(java.lang.Class<?> cls) {
            int intValue;
            synchronized (sInstanceCounts) {
                java.lang.Integer num = sInstanceCounts.get(cls);
                intValue = num != null ? num.intValue() : 0;
            }
            return intValue;
        }
    }
}
