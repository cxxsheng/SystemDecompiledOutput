package com.android.server.accessibility;

/* loaded from: classes.dex */
public class AccessibilityTraceManager implements android.accessibilityservice.AccessibilityTrace {
    private static com.android.server.accessibility.AccessibilityTraceManager sInstance = null;
    private final com.android.server.wm.WindowManagerInternal.AccessibilityControllerInternal mA11yController;
    private final java.lang.Object mA11yMSLock;
    private volatile long mEnabledLoggingFlags = 0;
    private final com.android.server.accessibility.AccessibilityManagerService mService;

    static com.android.server.accessibility.AccessibilityTraceManager getInstance(@android.annotation.NonNull com.android.server.wm.WindowManagerInternal.AccessibilityControllerInternal accessibilityControllerInternal, @android.annotation.NonNull com.android.server.accessibility.AccessibilityManagerService accessibilityManagerService, @android.annotation.NonNull java.lang.Object obj) {
        if (sInstance == null) {
            sInstance = new com.android.server.accessibility.AccessibilityTraceManager(accessibilityControllerInternal, accessibilityManagerService, obj);
        }
        return sInstance;
    }

    private AccessibilityTraceManager(@android.annotation.NonNull com.android.server.wm.WindowManagerInternal.AccessibilityControllerInternal accessibilityControllerInternal, @android.annotation.NonNull com.android.server.accessibility.AccessibilityManagerService accessibilityManagerService, @android.annotation.NonNull java.lang.Object obj) {
        this.mA11yController = accessibilityControllerInternal;
        this.mService = accessibilityManagerService;
        this.mA11yMSLock = obj;
    }

    public boolean isA11yTracingEnabled() {
        return this.mEnabledLoggingFlags != 0;
    }

    public boolean isA11yTracingEnabledForTypes(long j) {
        return (j & this.mEnabledLoggingFlags) != 0;
    }

    public int getTraceStateForAccessibilityManagerClientState() {
        int i;
        if (!isA11yTracingEnabledForTypes(16L)) {
            i = 0;
        } else {
            i = 256;
        }
        if (isA11yTracingEnabledForTypes(32L)) {
            i |= 512;
        }
        if (isA11yTracingEnabledForTypes(262144L)) {
            i |= 1024;
        }
        if (isA11yTracingEnabledForTypes(16384L)) {
            return i | 2048;
        }
        return i;
    }

    public void startTrace(long j) {
        if (j == 0) {
            return;
        }
        long j2 = this.mEnabledLoggingFlags;
        this.mEnabledLoggingFlags = j;
        if (needToNotifyClients(j2)) {
            synchronized (this.mA11yMSLock) {
                this.mService.scheduleUpdateClientsIfNeededLocked(this.mService.getCurrentUserState());
            }
        }
        this.mA11yController.startTrace(j);
    }

    public void stopTrace() {
        boolean isA11yTracingEnabled = isA11yTracingEnabled();
        long j = this.mEnabledLoggingFlags;
        this.mEnabledLoggingFlags = 0L;
        if (needToNotifyClients(j)) {
            synchronized (this.mA11yMSLock) {
                this.mService.scheduleUpdateClientsIfNeededLocked(this.mService.getCurrentUserState());
            }
        }
        if (isA11yTracingEnabled) {
            this.mA11yController.stopTrace();
        }
    }

    public void logTrace(java.lang.String str, long j) {
        logTrace(str, j, "");
    }

    public void logTrace(java.lang.String str, long j, java.lang.String str2) {
        if (isA11yTracingEnabledForTypes(j)) {
            this.mA11yController.logTrace(str, j, str2, "".getBytes(), android.os.Binder.getCallingUid(), java.lang.Thread.currentThread().getStackTrace(), new java.util.HashSet(java.util.Arrays.asList("logTrace")));
        }
    }

    public void logTrace(long j, java.lang.String str, long j2, java.lang.String str2, int i, long j3, int i2, java.lang.StackTraceElement[] stackTraceElementArr, java.util.Set<java.lang.String> set) {
        if (isA11yTracingEnabledForTypes(j2)) {
            this.mA11yController.logTrace(str, j2, str2, "".getBytes(), i2, stackTraceElementArr, j, i, j3, set == null ? new java.util.HashSet() : set);
        }
    }

    private boolean needToNotifyClients(long j) {
        return (this.mEnabledLoggingFlags & 278576) != (j & 278576);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:17:0x003c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    int onShellCommand(java.lang.String str, android.os.ShellCommand shellCommand) {
        boolean z;
        boolean z2;
        switch (str.hashCode()) {
            case 1340897306:
                if (str.equals("start-trace")) {
                    z = false;
                    break;
                }
                z = -1;
                break;
            case 1857979322:
                if (str.equals("stop-trace")) {
                    z = true;
                    break;
                }
                z = -1;
                break;
            default:
                z = -1;
                break;
        }
        switch (z) {
            case false:
                java.lang.String nextOption = shellCommand.getNextOption();
                if (nextOption == null) {
                    startTrace(-1L);
                    return 0;
                }
                java.util.ArrayList arrayList = new java.util.ArrayList();
                while (nextOption != null) {
                    switch (nextOption.hashCode()) {
                        case 1511:
                            if (nextOption.equals("-t")) {
                                z2 = false;
                                break;
                            }
                        default:
                            z2 = -1;
                            break;
                    }
                    switch (z2) {
                        case false:
                            java.lang.String nextArg = shellCommand.getNextArg();
                            while (nextArg != null) {
                                arrayList.add(nextArg);
                                nextArg = shellCommand.getNextArg();
                            }
                            nextOption = shellCommand.getNextOption();
                        default:
                            shellCommand.getErrPrintWriter().println("Error: option not recognized " + nextOption);
                            stopTrace();
                            return -1;
                    }
                    while (nextOption != null) {
                    }
                }
                startTrace(android.accessibilityservice.AccessibilityTrace.getLoggingFlagsFromNames(arrayList));
                return 0;
            case true:
                stopTrace();
                return 0;
            default:
                return -1;
        }
    }

    void onHelp(java.io.PrintWriter printWriter) {
        printWriter.println("  start-trace [-t LOGGING_TYPE [LOGGING_TYPE...]]");
        printWriter.println("    Start the debug tracing. If no option is present, full trace will be");
        printWriter.println("    generated. Options are:");
        printWriter.println("      -t: Only generate tracing for the logging type(s) specified here.");
        printWriter.println("          LOGGING_TYPE can be any one of below:");
        printWriter.println("            IAccessibilityServiceConnection");
        printWriter.println("            IAccessibilityServiceClient");
        printWriter.println("            IAccessibilityManager");
        printWriter.println("            IAccessibilityManagerClient");
        printWriter.println("            IAccessibilityInteractionConnection");
        printWriter.println("            IAccessibilityInteractionConnectionCallback");
        printWriter.println("            IRemoteMagnificationAnimationCallback");
        printWriter.println("            IMagnificationConnection");
        printWriter.println("            IMagnificationConnectionCallback");
        printWriter.println("            WindowManagerInternal");
        printWriter.println("            WindowsForAccessibilityCallback");
        printWriter.println("            MagnificationCallbacks");
        printWriter.println("            InputFilter");
        printWriter.println("            Gesture");
        printWriter.println("            AccessibilityService");
        printWriter.println("            PMBroadcastReceiver");
        printWriter.println("            UserBroadcastReceiver");
        printWriter.println("            FingerprintGesture");
        printWriter.println("  stop-trace");
        printWriter.println("    Stop the debug tracing.");
    }
}
