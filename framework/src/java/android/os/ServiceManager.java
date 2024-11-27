package android.os;

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
/* loaded from: classes3.dex */
public final class ServiceManager {
    private static final int SLOW_LOG_INTERVAL_MS = 5000;
    private static final int STATS_LOG_INTERVAL_MS = 5000;
    private static final java.lang.String TAG = "ServiceManager";
    private static java.util.Map<java.lang.String, android.os.IBinder> sCache$ravenwood;
    private static int sGetServiceAccumulatedCallCount;
    private static int sGetServiceAccumulatedUs;
    private static long sLastSlowLogActualTime;
    private static long sLastSlowLogUptime;
    private static long sLastStatsLogUptime;
    private static android.os.IServiceManager sServiceManager;
    private static final java.lang.Object sLock = new java.lang.Object();
    private static java.util.Map<java.lang.String, android.os.IBinder> sCache = new android.util.ArrayMap();
    private static final long GET_SERVICE_SLOW_THRESHOLD_US_CORE = android.os.SystemProperties.getInt("debug.servicemanager.slow_call_core_ms", 10) * 1000;
    private static final long GET_SERVICE_SLOW_THRESHOLD_US_NON_CORE = android.os.SystemProperties.getInt("debug.servicemanager.slow_call_ms", 50) * 1000;
    private static final int GET_SERVICE_LOG_EVERY_CALLS_CORE = android.os.SystemProperties.getInt("debug.servicemanager.log_calls_core", 100);
    private static final int GET_SERVICE_LOG_EVERY_CALLS_NON_CORE = android.os.SystemProperties.getInt("debug.servicemanager.log_calls", 200);
    public static final com.android.internal.util.StatLogger sStatLogger = new com.android.internal.util.StatLogger(new java.lang.String[]{"getService()"});

    interface Stats {
        public static final int COUNT = 1;
        public static final int GET_SERVICE = 0;
    }

    private static native android.os.IBinder waitForServiceNative(java.lang.String str);

    public static void init$ravenwood() {
        synchronized (android.os.ServiceManager.class) {
            sCache$ravenwood = new android.util.ArrayMap();
        }
    }

    public static void reset$ravenwood() {
        synchronized (android.os.ServiceManager.class) {
            sCache$ravenwood.clear();
            sCache$ravenwood = null;
        }
    }

    private static android.os.IServiceManager getIServiceManager() {
        if (sServiceManager != null) {
            return sServiceManager;
        }
        sServiceManager = android.os.ServiceManagerNative.asInterface(android.os.Binder.allowBlocking(com.android.internal.os.BinderInternal.getContextObject()));
        return sServiceManager;
    }

    public static android.os.IBinder getService(java.lang.String str) {
        try {
            android.os.IBinder iBinder = sCache.get(str);
            if (iBinder != null) {
                return iBinder;
            }
            return android.os.Binder.allowBlocking(rawGetService(str));
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "error in getService", e);
            return null;
        }
    }

    public static android.os.IBinder getService$ravenwood(java.lang.String str) {
        android.os.IBinder iBinder;
        synchronized (android.os.ServiceManager.class) {
            iBinder = (android.os.IBinder) ((java.util.Map) com.android.internal.util.Preconditions.requireNonNullViaRavenwoodRule(sCache$ravenwood)).get(str);
        }
        return iBinder;
    }

    public static android.os.IBinder getServiceOrThrow(java.lang.String str) throws android.os.ServiceManager.ServiceNotFoundException {
        android.os.IBinder service = getService(str);
        if (service != null) {
            return service;
        }
        throw new android.os.ServiceManager.ServiceNotFoundException(str);
    }

    public static void addService(java.lang.String str, android.os.IBinder iBinder) {
        addService(str, iBinder, false, 8);
    }

    public static void addService(java.lang.String str, android.os.IBinder iBinder, boolean z) {
        addService(str, iBinder, z, 8);
    }

    public static void addService(java.lang.String str, android.os.IBinder iBinder, boolean z, int i) {
        try {
            getIServiceManager().addService(str, iBinder, z, i);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "error in addService", e);
        }
    }

    public static void addService$ravenwood(java.lang.String str, android.os.IBinder iBinder, boolean z, int i) {
        synchronized (android.os.ServiceManager.class) {
            ((java.util.Map) com.android.internal.util.Preconditions.requireNonNullViaRavenwoodRule(sCache$ravenwood)).put(str, iBinder);
        }
    }

    public static android.os.IBinder checkService(java.lang.String str) {
        try {
            android.os.IBinder iBinder = sCache.get(str);
            if (iBinder != null) {
                return iBinder;
            }
            return android.os.Binder.allowBlocking(getIServiceManager().checkService(str));
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "error in checkService", e);
            return null;
        }
    }

    public static boolean isDeclared(java.lang.String str) {
        try {
            return getIServiceManager().isDeclared(str);
        } catch (android.os.RemoteException | java.lang.SecurityException e) {
            android.util.Log.e(TAG, "error in isDeclared", e);
            return false;
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static java.lang.String[] getDeclaredInstances(java.lang.String str) {
        try {
            return getIServiceManager().getDeclaredInstances(str);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "error in getDeclaredInstances", e);
            throw e.rethrowFromSystemServer();
        }
    }

    public static android.os.IBinder waitForService(java.lang.String str) {
        return android.os.Binder.allowBlocking(waitForServiceNative(str));
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static android.os.IBinder waitForDeclaredService(java.lang.String str) {
        if (isDeclared(str)) {
            return waitForService(str);
        }
        return null;
    }

    public static void registerForNotifications(java.lang.String str, android.os.IServiceCallback iServiceCallback) throws android.os.RemoteException {
        getIServiceManager().registerForNotifications(str, iServiceCallback);
    }

    public static java.lang.String[] listServices() {
        try {
            return getIServiceManager().listServices(15);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "error in listServices", e);
            return null;
        }
    }

    public static android.os.ServiceDebugInfo[] getServiceDebugInfo() {
        try {
            return getIServiceManager().getServiceDebugInfo();
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "error in getServiceDebugInfo", e);
            return null;
        }
    }

    public static void initServiceCache(java.util.Map<java.lang.String, android.os.IBinder> map) {
        if (sCache.size() != 0) {
            throw new java.lang.IllegalStateException("setServiceCache may only be called once");
        }
        sCache.putAll(map);
    }

    public static class ServiceNotFoundException extends java.lang.Exception {
        public ServiceNotFoundException(java.lang.String str) {
            super("No service published for: " + str);
        }
    }

    private static android.os.IBinder rawGetService(java.lang.String str) throws android.os.RemoteException {
        long j;
        int i;
        long time = sStatLogger.getTime();
        android.os.IBinder service = getIServiceManager().getService(str);
        int logDurationStat = (int) sStatLogger.logDurationStat(0, time);
        boolean isCore = android.os.UserHandle.isCore(android.os.Process.myUid());
        if (isCore) {
            j = GET_SERVICE_SLOW_THRESHOLD_US_CORE;
        } else {
            j = GET_SERVICE_SLOW_THRESHOLD_US_NON_CORE;
        }
        synchronized (sLock) {
            sGetServiceAccumulatedUs += logDurationStat;
            sGetServiceAccumulatedCallCount++;
            long uptimeMillis = android.os.SystemClock.uptimeMillis();
            long j2 = logDurationStat;
            if (j2 >= j && (uptimeMillis > sLastSlowLogUptime + 5000 || sLastSlowLogActualTime < j2)) {
                android.os.EventLogTags.writeServiceManagerSlow(logDurationStat / 1000, str);
                sLastSlowLogUptime = uptimeMillis;
                sLastSlowLogActualTime = j2;
            }
            if (isCore) {
                i = GET_SERVICE_LOG_EVERY_CALLS_CORE;
            } else {
                i = GET_SERVICE_LOG_EVERY_CALLS_NON_CORE;
            }
            if (sGetServiceAccumulatedCallCount >= i && uptimeMillis >= sLastStatsLogUptime + 5000) {
                android.os.EventLogTags.writeServiceManagerStats(sGetServiceAccumulatedCallCount, sGetServiceAccumulatedUs / 1000, (int) (uptimeMillis - sLastStatsLogUptime));
                sGetServiceAccumulatedCallCount = 0;
                sGetServiceAccumulatedUs = 0;
                sLastStatsLogUptime = uptimeMillis;
            }
        }
        return service;
    }
}
