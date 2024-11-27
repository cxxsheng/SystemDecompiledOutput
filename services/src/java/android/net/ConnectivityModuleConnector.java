package android.net;

/* loaded from: classes.dex */
public class ConnectivityModuleConnector {
    private static final java.lang.String CONFIG_ALWAYS_RATELIMIT_NETWORKSTACK_CRASH = "always_ratelimit_networkstack_crash";
    private static final java.lang.String CONFIG_MIN_CRASH_INTERVAL_MS = "min_crash_interval";
    private static final java.lang.String CONFIG_MIN_UPTIME_BEFORE_CRASH_MS = "min_uptime_before_crash";
    private static final long DEFAULT_MIN_CRASH_INTERVAL_MS = 21600000;
    private static final long DEFAULT_MIN_UPTIME_BEFORE_CRASH_MS = 1800000;
    private static final java.lang.String IN_PROCESS_SUFFIX = ".InProcess";
    private static final java.lang.String PREFS_FILE = "ConnectivityModuleConnector.xml";
    private static final java.lang.String PREF_KEY_LAST_CRASH_TIME = "lastcrash_time";
    private static final java.lang.String TAG = android.net.ConnectivityModuleConnector.class.getSimpleName();
    private static android.net.ConnectivityModuleConnector sInstance;
    private android.content.Context mContext;

    @android.annotation.NonNull
    private final android.net.ConnectivityModuleConnector.Dependencies mDeps;

    @com.android.internal.annotations.GuardedBy({"mHealthListeners"})
    private final android.util.ArraySet<android.net.ConnectivityModuleConnector.ConnectivityModuleHealthListener> mHealthListeners;

    public interface ConnectivityModuleHealthListener {
        void onNetworkStackFailure(@android.annotation.NonNull java.lang.String str);
    }

    @com.android.internal.annotations.VisibleForTesting
    protected interface Dependencies {
        @android.annotation.Nullable
        android.content.Intent getModuleServiceIntent(@android.annotation.NonNull android.content.pm.PackageManager packageManager, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, boolean z);
    }

    public interface ModuleServiceCallback {
        void onModuleServiceConnected(@android.annotation.NonNull android.os.IBinder iBinder);
    }

    private ConnectivityModuleConnector() {
        this(new android.net.ConnectivityModuleConnector.DependenciesImpl());
    }

    @com.android.internal.annotations.VisibleForTesting
    ConnectivityModuleConnector(@android.annotation.NonNull android.net.ConnectivityModuleConnector.Dependencies dependencies) {
        this.mHealthListeners = new android.util.ArraySet<>();
        this.mDeps = dependencies;
    }

    public static synchronized android.net.ConnectivityModuleConnector getInstance() {
        android.net.ConnectivityModuleConnector connectivityModuleConnector;
        synchronized (android.net.ConnectivityModuleConnector.class) {
            try {
                if (sInstance == null) {
                    sInstance = new android.net.ConnectivityModuleConnector();
                }
                connectivityModuleConnector = sInstance;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return connectivityModuleConnector;
    }

    public void init(android.content.Context context) {
        log("Network stack init");
        this.mContext = context;
    }

    private static class DependenciesImpl implements android.net.ConnectivityModuleConnector.Dependencies {
        private DependenciesImpl() {
        }

        @Override // android.net.ConnectivityModuleConnector.Dependencies
        @android.annotation.Nullable
        public android.content.Intent getModuleServiceIntent(@android.annotation.NonNull android.content.pm.PackageManager packageManager, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, boolean z) {
            if (z) {
                str = str + android.net.ConnectivityModuleConnector.IN_PROCESS_SUFFIX;
            }
            android.content.Intent intent = new android.content.Intent(str);
            android.content.ComponentName resolveSystemService = intent.resolveSystemService(packageManager, 0);
            if (resolveSystemService == null) {
                return null;
            }
            intent.setComponent(resolveSystemService);
            try {
                int packageUidAsUser = packageManager.getPackageUidAsUser(resolveSystemService.getPackageName(), 0);
                if (packageUidAsUser != (z ? 1000 : 1073)) {
                    throw new java.lang.SecurityException("Invalid network stack UID: " + packageUidAsUser);
                }
                if (!z) {
                    android.net.ConnectivityModuleConnector.checkModuleServicePermission(packageManager, resolveSystemService, str2);
                }
                return intent;
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                throw new java.lang.SecurityException("Could not check network stack UID; package not found.", e);
            }
        }
    }

    public void registerHealthListener(@android.annotation.NonNull android.net.ConnectivityModuleConnector.ConnectivityModuleHealthListener connectivityModuleHealthListener) {
        synchronized (this.mHealthListeners) {
            this.mHealthListeners.add(connectivityModuleHealthListener);
        }
    }

    public void startModuleService(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull android.net.ConnectivityModuleConnector.ModuleServiceCallback moduleServiceCallback) {
        log("Starting networking module " + str);
        android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
        android.content.Intent moduleServiceIntent = this.mDeps.getModuleServiceIntent(packageManager, str, str2, true);
        if (moduleServiceIntent == null) {
            moduleServiceIntent = this.mDeps.getModuleServiceIntent(packageManager, str, str2, false);
            log("Starting networking module in network_stack process");
        } else {
            log("Starting networking module in system_server process");
        }
        if (moduleServiceIntent == null) {
            maybeCrashWithTerribleFailure("Could not resolve the networking module", null);
            return;
        }
        java.lang.String packageName = moduleServiceIntent.getComponent().getPackageName();
        if (!this.mContext.bindServiceAsUser(moduleServiceIntent, new android.net.ConnectivityModuleConnector.ModuleServiceConnection(packageName, moduleServiceCallback), 65, android.os.UserHandle.SYSTEM)) {
            maybeCrashWithTerribleFailure("Could not bind to networking module in-process, or in app with " + moduleServiceIntent, packageName);
            return;
        }
        log("Networking module service start requested");
    }

    private class ModuleServiceConnection implements android.content.ServiceConnection {

        @android.annotation.NonNull
        private final android.net.ConnectivityModuleConnector.ModuleServiceCallback mModuleServiceCallback;

        @android.annotation.NonNull
        private final java.lang.String mPackageName;

        private ModuleServiceConnection(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.net.ConnectivityModuleConnector.ModuleServiceCallback moduleServiceCallback) {
            this.mPackageName = str;
            this.mModuleServiceCallback = moduleServiceCallback;
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
            android.net.ConnectivityModuleConnector.this.logi("Networking module service connected");
            this.mModuleServiceCallback.onModuleServiceConnected(iBinder);
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(android.content.ComponentName componentName) {
            android.net.ConnectivityModuleConnector.this.maybeCrashWithTerribleFailure("Lost network stack. This is not the root cause of any issue, it is a side effect of a crash that happened earlier. Earlier logs should point to the actual issue.", this.mPackageName);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void checkModuleServicePermission(@android.annotation.NonNull android.content.pm.PackageManager packageManager, @android.annotation.NonNull android.content.ComponentName componentName, @android.annotation.NonNull java.lang.String str) {
        if (packageManager.checkPermission(str, componentName.getPackageName()) != 0) {
            throw new java.lang.SecurityException("Networking module does not have permission " + str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void maybeCrashWithTerribleFailure(@android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2) {
        android.util.ArraySet arraySet;
        logWtf(str, null);
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        long j = android.provider.DeviceConfig.getLong("connectivity", CONFIG_MIN_CRASH_INTERVAL_MS, DEFAULT_MIN_CRASH_INTERVAL_MS);
        long j2 = android.provider.DeviceConfig.getLong("connectivity", CONFIG_MIN_UPTIME_BEFORE_CRASH_MS, 1800000L);
        boolean z = false;
        boolean z2 = android.provider.DeviceConfig.getBoolean("connectivity", CONFIG_ALWAYS_RATELIMIT_NETWORKSTACK_CRASH, false);
        android.content.SharedPreferences sharedPreferences = getSharedPreferences();
        long tryGetLastCrashTime = tryGetLastCrashTime(sharedPreferences);
        boolean z3 = android.os.Build.IS_DEBUGGABLE && !z2;
        boolean z4 = elapsedRealtime < j2;
        if ((tryGetLastCrashTime != 0 && tryGetLastCrashTime < currentTimeMillis) && currentTimeMillis < tryGetLastCrashTime + j) {
            z = true;
        }
        if (z3 || (!z4 && !z)) {
            tryWriteLastCrashTime(sharedPreferences, currentTimeMillis);
            throw new java.lang.IllegalStateException(str);
        }
        if (str2 != null) {
            synchronized (this.mHealthListeners) {
                arraySet = new android.util.ArraySet((android.util.ArraySet) this.mHealthListeners);
            }
            java.util.Iterator it = arraySet.iterator();
            while (it.hasNext()) {
                ((android.net.ConnectivityModuleConnector.ConnectivityModuleHealthListener) it.next()).onNetworkStackFailure(str2);
            }
        }
    }

    @android.annotation.Nullable
    private android.content.SharedPreferences getSharedPreferences() {
        try {
            return this.mContext.createDeviceProtectedStorageContext().getSharedPreferences(new java.io.File(android.os.Environment.getDataSystemDeDirectory(0), PREFS_FILE), 0);
        } catch (java.lang.Throwable th) {
            logWtf("Error loading shared preferences", th);
            return null;
        }
    }

    private long tryGetLastCrashTime(@android.annotation.Nullable android.content.SharedPreferences sharedPreferences) {
        if (sharedPreferences == null) {
            return 0L;
        }
        try {
            return sharedPreferences.getLong(PREF_KEY_LAST_CRASH_TIME, 0L);
        } catch (java.lang.Throwable th) {
            logWtf("Error getting last crash time", th);
            return 0L;
        }
    }

    private void tryWriteLastCrashTime(@android.annotation.Nullable android.content.SharedPreferences sharedPreferences, long j) {
        if (sharedPreferences == null) {
            return;
        }
        try {
            sharedPreferences.edit().putLong(PREF_KEY_LAST_CRASH_TIME, j).commit();
        } catch (java.lang.Throwable th) {
            logWtf("Error writing last crash time", th);
        }
    }

    private void log(@android.annotation.NonNull java.lang.String str) {
        android.util.Log.d(TAG, str);
    }

    private void logWtf(@android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.Throwable th) {
        android.util.Slog.wtf(TAG, str, th);
        android.util.Log.e(TAG, str, th);
    }

    private void loge(@android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.Throwable th) {
        android.util.Log.e(TAG, str, th);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void logi(@android.annotation.NonNull java.lang.String str) {
        android.util.Log.i(TAG, str);
    }
}
