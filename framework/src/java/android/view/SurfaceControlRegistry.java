package android.view;

/* loaded from: classes4.dex */
public class SurfaceControlRegistry {
    private static final int DUMP_LIMIT = 256;
    private static final int MAX_LAYERS_REPORTING_THRESHOLD = 1024;
    private static final int RESET_REPORTING_THRESHOLD = 256;
    private static final java.lang.String TAG = "SurfaceControlRegistry";
    static boolean sCallStackDebuggingEnabled;
    static boolean sCallStackDebuggingInitialized;
    private static java.lang.String sCallStackDebuggingMatchCall;
    private static java.lang.String sCallStackDebuggingMatchName;
    private static volatile android.view.SurfaceControlRegistry sProcessRegistry;
    private boolean mHasReportedExceedingMaxThreshold;
    private int mMaxLayersReportingThreshold;
    private android.view.SurfaceControlRegistry.Reporter mReporter;
    private int mResetReportingThreshold;
    private final java.util.WeakHashMap<android.view.SurfaceControl, java.lang.Long> mSurfaceControls;
    private static final android.view.SurfaceControlRegistry NO_OP_REGISTRY = new android.view.SurfaceControlRegistry.NoOpRegistry();
    private static final java.lang.Object sLock = new java.lang.Object();
    private static final android.view.SurfaceControlRegistry.DefaultReporter sDefaultReporter = new android.view.SurfaceControlRegistry.DefaultReporter();

    public interface Reporter {
        void onMaxLayersExceeded(java.util.WeakHashMap<android.view.SurfaceControl, java.lang.Long> weakHashMap, int i, java.io.PrintWriter printWriter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class DefaultReporter implements android.view.SurfaceControlRegistry.Reporter {
        private DefaultReporter() {
        }

        @Override // android.view.SurfaceControlRegistry.Reporter
        public void onMaxLayersExceeded(java.util.WeakHashMap<android.view.SurfaceControl, java.lang.Long> weakHashMap, int i, java.io.PrintWriter printWriter) {
            long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            java.util.ArrayList arrayList = new java.util.ArrayList();
            java.util.Iterator<java.util.Map.Entry<android.view.SurfaceControl, java.lang.Long>> it = weakHashMap.entrySet().iterator();
            while (it.hasNext()) {
                arrayList.add(it.next());
            }
            arrayList.sort(new java.util.Comparator() { // from class: android.view.SurfaceControlRegistry$DefaultReporter$$ExternalSyntheticLambda0
                @Override // java.util.Comparator
                public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                    return android.view.SurfaceControlRegistry.DefaultReporter.lambda$onMaxLayersExceeded$0((java.util.Map.Entry) obj, (java.util.Map.Entry) obj2);
                }
            });
            int min = java.lang.Math.min(arrayList.size(), i);
            printWriter.println(android.view.SurfaceControlRegistry.TAG);
            printWriter.println("----------------------");
            printWriter.println("Listing oldest " + min + " of " + weakHashMap.size());
            for (int i2 = 0; i2 < min; i2++) {
                java.util.Map.Entry entry = (java.util.Map.Entry) arrayList.get(i2);
                android.view.SurfaceControl surfaceControl = (android.view.SurfaceControl) entry.getKey();
                if (surfaceControl != null) {
                    long longValue = ((java.lang.Long) entry.getValue()).longValue();
                    printWriter.print("  ");
                    printWriter.print(surfaceControl.getName());
                    printWriter.print(" (" + surfaceControl.getCallsite() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
                    printWriter.println(" [" + ((elapsedRealtime - longValue) / 1000) + "s ago]");
                }
            }
        }

        static /* synthetic */ int lambda$onMaxLayersExceeded$0(java.util.Map.Entry entry, java.util.Map.Entry entry2) {
            return (int) (((java.lang.Long) entry.getValue()).longValue() - ((java.lang.Long) entry2.getValue()).longValue());
        }
    }

    private SurfaceControlRegistry() {
        this.mMaxLayersReportingThreshold = 1024;
        this.mResetReportingThreshold = 256;
        this.mHasReportedExceedingMaxThreshold = false;
        this.mReporter = sDefaultReporter;
        this.mSurfaceControls = new java.util.WeakHashMap<>(256);
    }

    public void setReportingThresholds(int i, int i2, android.view.SurfaceControlRegistry.Reporter reporter) {
        synchronized (sLock) {
            if (i <= 0 || i2 >= i) {
                throw new java.lang.IllegalArgumentException("Expected maxLayersReportingThreshold (" + i + ") to be > 0 and resetReportingThreshold (" + i2 + ") to be < maxLayersReportingThreshold");
            }
            if (reporter == null) {
                throw new java.lang.IllegalArgumentException("Expected non-null reporter");
            }
            this.mMaxLayersReportingThreshold = i;
            this.mResetReportingThreshold = i2;
            this.mHasReportedExceedingMaxThreshold = false;
            this.mReporter = reporter;
        }
    }

    public void setCallStackDebuggingParams(java.lang.String str, java.lang.String str2) {
        sCallStackDebuggingMatchName = str.toLowerCase();
        sCallStackDebuggingMatchCall = str2.toLowerCase();
    }

    public static void createProcessInstance(android.content.Context context) {
        if (context.checkSelfPermission(android.Manifest.permission.READ_FRAME_BUFFER) != 0) {
            throw new java.lang.SecurityException("Expected caller to hold READ_FRAME_BUFFER");
        }
        synchronized (sLock) {
            if (sProcessRegistry == null) {
                sProcessRegistry = new android.view.SurfaceControlRegistry();
            }
        }
    }

    public static void destroyProcessInstance() {
        synchronized (sLock) {
            if (sProcessRegistry == null) {
                return;
            }
            sProcessRegistry = null;
        }
    }

    public static android.view.SurfaceControlRegistry getProcessInstance() {
        android.view.SurfaceControlRegistry surfaceControlRegistry;
        synchronized (sLock) {
            surfaceControlRegistry = sProcessRegistry != null ? sProcessRegistry : NO_OP_REGISTRY;
        }
        return surfaceControlRegistry;
    }

    void add(android.view.SurfaceControl surfaceControl) {
        synchronized (sLock) {
            this.mSurfaceControls.put(surfaceControl, java.lang.Long.valueOf(android.os.SystemClock.elapsedRealtime()));
            if (!this.mHasReportedExceedingMaxThreshold && this.mSurfaceControls.size() >= this.mMaxLayersReportingThreshold) {
                this.mReporter.onMaxLayersExceeded(this.mSurfaceControls, 256, new java.io.PrintWriter((java.io.OutputStream) java.lang.System.out, true));
                this.mHasReportedExceedingMaxThreshold = true;
            }
        }
    }

    void remove(android.view.SurfaceControl surfaceControl) {
        synchronized (sLock) {
            this.mSurfaceControls.remove(surfaceControl);
            if (this.mHasReportedExceedingMaxThreshold && this.mSurfaceControls.size() <= this.mResetReportingThreshold) {
                this.mHasReportedExceedingMaxThreshold = false;
            }
        }
    }

    public int hashCode() {
        int hashCode;
        synchronized (sLock) {
            hashCode = this.mSurfaceControls.keySet().hashCode();
        }
        return hashCode;
    }

    static final void initializeCallStackDebugging() {
        if (sCallStackDebuggingInitialized || !android.os.Build.IS_DEBUGGABLE) {
            return;
        }
        boolean z = true;
        sCallStackDebuggingInitialized = true;
        sCallStackDebuggingMatchCall = android.os.SystemProperties.get("persist.wm.debug.sc.tx.log_match_call", null).toLowerCase();
        sCallStackDebuggingMatchName = android.os.SystemProperties.get("persist.wm.debug.sc.tx.log_match_name", null).toLowerCase();
        if (sCallStackDebuggingMatchCall.isEmpty() && sCallStackDebuggingMatchName.isEmpty()) {
            z = false;
        }
        sCallStackDebuggingEnabled = z;
        if (sCallStackDebuggingEnabled) {
            android.util.Log.d(TAG, "Enabling transaction call stack debugging: matchCall=" + sCallStackDebuggingMatchCall + " matchName=" + sCallStackDebuggingMatchName);
        }
    }

    final void checkCallStackDebugging(java.lang.String str, android.view.SurfaceControl.Transaction transaction, android.view.SurfaceControl surfaceControl, java.lang.String str2) {
        if (sCallStackDebuggingEnabled) {
            if (!matchesForCallStackDebugging(surfaceControl != null ? surfaceControl.getName() : null, str)) {
                return;
            }
            java.lang.String str3 = transaction != null ? "tx=" + transaction.getId() + " " : "";
            java.lang.String str4 = surfaceControl != null ? " sc=" + surfaceControl.getName() + "" : "";
            android.util.Log.e(TAG, str2 != null ? str + " (" + str3 + str4 + ") " + str2 : str + " (" + str3 + str4 + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END, new java.lang.Throwable());
        }
    }

    public final boolean matchesForCallStackDebugging(java.lang.String str, java.lang.String str2) {
        if (!(!sCallStackDebuggingMatchCall.isEmpty()) || sCallStackDebuggingMatchCall.contains(str2.toLowerCase())) {
            return !(sCallStackDebuggingMatchName.isEmpty() ^ true) || (str != null && sCallStackDebuggingMatchName.contains(str.toLowerCase()));
        }
        return false;
    }

    static final boolean isCallStackDebuggingEnabled() {
        return sCallStackDebuggingEnabled;
    }

    private static void runGcAndFinalizers() {
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        com.android.internal.util.GcUtils.runGcAndFinalizersSync();
        android.util.Log.i(TAG, "Ran gc and finalizers (" + (android.os.SystemClock.elapsedRealtime() - elapsedRealtime) + "ms)");
    }

    public static void dump(int i, boolean z, java.io.PrintWriter printWriter) {
        if (z) {
            runGcAndFinalizers();
        }
        synchronized (sLock) {
            if (sProcessRegistry != null) {
                sDefaultReporter.onMaxLayersExceeded(sProcessRegistry.mSurfaceControls, i, printWriter);
                printWriter.println("sCallStackDebuggingInitialized=" + sCallStackDebuggingInitialized);
                printWriter.println("sCallStackDebuggingEnabled=" + sCallStackDebuggingEnabled);
                printWriter.println("sCallStackDebuggingMatchName=" + sCallStackDebuggingMatchName);
                printWriter.println("sCallStackDebuggingMatchCall=" + sCallStackDebuggingMatchCall);
            }
        }
    }

    private static class NoOpRegistry extends android.view.SurfaceControlRegistry {
        private NoOpRegistry() {
            super();
        }

        @Override // android.view.SurfaceControlRegistry
        public void setReportingThresholds(int i, int i2, android.view.SurfaceControlRegistry.Reporter reporter) {
        }

        @Override // android.view.SurfaceControlRegistry
        void add(android.view.SurfaceControl surfaceControl) {
        }

        @Override // android.view.SurfaceControlRegistry
        void remove(android.view.SurfaceControl surfaceControl) {
        }
    }
}
