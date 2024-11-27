package com.android.server;

/* loaded from: classes.dex */
class WatchdogDiagnostics {
    WatchdogDiagnostics() {
    }

    private static java.lang.String getBlockedOnString(java.lang.Object obj) {
        return java.lang.String.format("- waiting to lock <0x%08x> (a %s)", java.lang.Integer.valueOf(java.lang.System.identityHashCode(obj)), obj.getClass().getName());
    }

    private static java.lang.String getLockedString(java.lang.Object obj) {
        return java.lang.String.format("- locked <0x%08x> (a %s)", java.lang.Integer.valueOf(java.lang.System.identityHashCode(obj)), obj.getClass().getName());
    }

    @com.android.internal.annotations.VisibleForTesting
    public static boolean printAnnotatedStack(java.lang.Thread thread, java.io.PrintWriter printWriter) {
        dalvik.system.AnnotatedStackTraceElement[] annotatedThreadStackTrace = dalvik.system.VMStack.getAnnotatedThreadStackTrace(thread);
        if (annotatedThreadStackTrace == null) {
            return false;
        }
        printWriter.println(thread.getName() + " annotated stack trace:");
        int length = annotatedThreadStackTrace.length;
        for (int i = 0; i < length; i++) {
            dalvik.system.AnnotatedStackTraceElement annotatedStackTraceElement = annotatedThreadStackTrace[i];
            printWriter.println("    at " + annotatedStackTraceElement.getStackTraceElement());
            if (annotatedStackTraceElement.getBlockedOn() != null) {
                printWriter.println("    " + getBlockedOnString(annotatedStackTraceElement.getBlockedOn()));
            }
            if (annotatedStackTraceElement.getHeldLocks() != null) {
                for (java.lang.Object obj : annotatedStackTraceElement.getHeldLocks()) {
                    printWriter.println("    " + getLockedString(obj));
                }
            }
        }
        return true;
    }

    public static void diagnoseCheckers(java.util.List<com.android.server.Watchdog.HandlerChecker> list) {
        java.io.PrintWriter printWriter = new java.io.PrintWriter((java.io.Writer) new android.util.LogWriter(5, "Watchdog", 3), true);
        for (int i = 0; i < list.size(); i++) {
            java.lang.Thread thread = list.get(i).getThread();
            if (!printAnnotatedStack(thread, printWriter)) {
                android.util.Slog.w("Watchdog", thread.getName() + " stack trace:");
                java.lang.StackTraceElement[] stackTrace = thread.getStackTrace();
                int length = stackTrace.length;
                for (int i2 = 0; i2 < length; i2++) {
                    android.util.Slog.w("Watchdog", "    at " + stackTrace[i2]);
                }
            }
        }
    }
}
