package android.content.res;

/* loaded from: classes.dex */
public final class ResourceTimer {
    private static final java.lang.String TAG = "ResourceTimer";
    private static android.os.Handler mHandler;
    private static int[] sApiMap;
    private static android.content.res.ResourceTimer.Config sConfig;
    private static int sCurrentPoint;
    private static android.content.res.ResourceTimer sManager;
    private static android.content.res.ResourceTimer.Timer[] sTimers;
    private static boolean sEnabled = true;
    private static boolean sIncrementalMetrics = true;
    private static boolean ENABLE_DEBUG = false;
    private static final java.lang.Object sLock = new java.lang.Object();
    private static final long sProcessStart = android.os.SystemClock.uptimeMillis();
    private static final long[] sPublicationPoints = {5, 60, 720};
    private static long sLastUpdated = 0;

    private static native int nativeEnableTimers(android.content.res.ResourceTimer.Config config);

    private static native int nativeGetTimers(android.content.res.ResourceTimer.Timer[] timerArr, boolean z);

    private static class Config {
        int maxBuckets;
        int maxLargest;
        int maxTimer;
        java.lang.String[] timers;

        private Config() {
        }
    }

    private static class Timer {
        int count;
        int[] largest;
        int maxtime;
        int mintime;
        int[] percentile;
        long total;

        private Timer() {
        }

        public java.lang.String toString() {
            return android.text.TextUtils.formatSimple("%d:%d:%d:%d", java.lang.Integer.valueOf(this.count), java.lang.Long.valueOf(this.total), java.lang.Integer.valueOf(this.mintime), java.lang.Integer.valueOf(this.maxtime));
        }
    }

    private ResourceTimer() {
        throw new java.lang.RuntimeException("ResourceTimer constructor");
    }

    public static void start() {
        synchronized (sLock) {
            if (sEnabled) {
                if (mHandler != null) {
                    return;
                }
                if (android.os.Looper.getMainLooper() == null) {
                    throw new java.lang.RuntimeException("ResourceTimer started too early");
                }
                mHandler = new android.os.Handler(android.os.Looper.getMainLooper()) { // from class: android.content.res.ResourceTimer.1
                    @Override // android.os.Handler
                    public void handleMessage(android.os.Message message) {
                        android.content.res.ResourceTimer.handleMessage(message);
                    }
                };
                byte b = 0;
                sConfig = new android.content.res.ResourceTimer.Config();
                nativeEnableTimers(sConfig);
                sTimers = new android.content.res.ResourceTimer.Timer[sConfig.maxTimer];
                for (int i = 0; i < sTimers.length; i++) {
                    sTimers[i] = new android.content.res.ResourceTimer.Timer();
                    sTimers[i].percentile = new int[sConfig.maxBuckets];
                    sTimers[i].largest = new int[sConfig.maxLargest];
                }
                sApiMap = new int[sConfig.maxTimer];
                for (int i2 = 0; i2 < sApiMap.length; i2++) {
                    if (sConfig.timers[i2].equals("GetResourceValue")) {
                        sApiMap[i2] = 1;
                    } else if (!sConfig.timers[i2].equals("RetrieveAttributes")) {
                        sApiMap[i2] = 0;
                    } else {
                        sApiMap[i2] = 2;
                    }
                }
                sCurrentPoint = 0;
                startTimer();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void handleMessage(android.os.Message message) {
        synchronized (sLock) {
            publish();
            startTimer();
        }
    }

    private static void startTimer() {
        long length;
        if (sCurrentPoint < sPublicationPoints.length) {
            length = sPublicationPoints[sCurrentPoint];
        } else {
            length = sPublicationPoints[sPublicationPoints.length - 1] * (sCurrentPoint - (sPublicationPoints.length - 1));
        }
        long j = length * 60000;
        if (ENABLE_DEBUG) {
            j /= 60;
        }
        mHandler.sendEmptyMessageAtTime(0, sProcessStart + j);
    }

    private static void update(boolean z) {
        nativeGetTimers(sTimers, z);
        sLastUpdated = android.os.SystemClock.uptimeMillis();
    }

    private static void publish() {
        update(true);
        char c = 0;
        int i = 0;
        while (i < sTimers.length) {
            android.content.res.ResourceTimer.Timer timer = sTimers[i];
            if (timer.count > 0) {
                android.util.Log.i(TAG, android.text.TextUtils.formatSimple("%s count=%d pvalues=%s", sConfig.timers[i], java.lang.Integer.valueOf(timer.count), packedString(timer.percentile)));
                if (sApiMap[i] != 0) {
                    com.android.internal.util.FrameworkStatsLog.write(517, sApiMap[i], timer.count, timer.total, timer.percentile[c], timer.percentile[1], timer.percentile[2], timer.percentile[3], timer.largest[c], timer.largest[1], timer.largest[2], timer.largest[3], timer.largest[4]);
                }
            }
            i++;
            c = 0;
        }
        sCurrentPoint++;
    }

    private static java.lang.String packedString(int[] iArr) {
        return java.util.Arrays.toString(iArr).replaceAll("[\\]\\[ ]", "");
    }

    public static void dumpTimers(android.os.ParcelFileDescriptor parcelFileDescriptor, java.lang.String[] strArr) {
        com.android.internal.util.FastPrintWriter fastPrintWriter = new com.android.internal.util.FastPrintWriter(new java.io.FileOutputStream(parcelFileDescriptor.getFileDescriptor()));
        synchronized (sLock) {
            if (sEnabled && sConfig != null) {
                boolean contains = java.util.Arrays.asList(strArr).contains("-refresh");
                synchronized (sLock) {
                    update(contains);
                    fastPrintWriter.format("  config runtime=%d proc=%s\n", java.lang.Long.valueOf(sLastUpdated - sProcessStart), android.os.Process.myProcessName());
                    for (int i = 0; i < sTimers.length; i++) {
                        android.content.res.ResourceTimer.Timer timer = sTimers[i];
                        if (timer.count != 0) {
                            fastPrintWriter.format("  stats timer=%s cnt=%d avg=%d min=%d max=%d pval=%s largest=%s\n", sConfig.timers[i], java.lang.Integer.valueOf(timer.count), java.lang.Long.valueOf(timer.total / timer.count), java.lang.Integer.valueOf(timer.mintime), java.lang.Integer.valueOf(timer.maxtime), packedString(timer.percentile), packedString(timer.largest));
                        }
                    }
                }
                fastPrintWriter.flush();
                return;
            }
            fastPrintWriter.println("  Timers are not enabled in this process");
            fastPrintWriter.flush();
        }
    }
}
