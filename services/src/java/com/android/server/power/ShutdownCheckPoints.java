package com.android.server.power;

/* loaded from: classes2.dex */
public final class ShutdownCheckPoints {
    private static final int MAX_CHECK_POINTS = 100;
    private static final int MAX_DUMP_FILES = 20;
    private static final java.lang.String TAG = "ShutdownCheckPoints";
    private final java.util.ArrayList<com.android.server.power.ShutdownCheckPoints.CheckPoint> mCheckPoints;
    private final com.android.server.power.ShutdownCheckPoints.Injector mInjector;
    private static final com.android.server.power.ShutdownCheckPoints INSTANCE = new com.android.server.power.ShutdownCheckPoints();
    private static final java.text.SimpleDateFormat DATE_FORMAT = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS z");
    private static final java.io.File[] EMPTY_FILE_ARRAY = new java.io.File[0];

    @com.android.internal.annotations.VisibleForTesting
    interface Injector {
        android.app.IActivityManager activityManager();

        long currentTimeMillis();

        int maxCheckPoints();

        int maxDumpFiles();
    }

    private ShutdownCheckPoints() {
        this(new com.android.server.power.ShutdownCheckPoints.Injector() { // from class: com.android.server.power.ShutdownCheckPoints.1
            @Override // com.android.server.power.ShutdownCheckPoints.Injector
            public long currentTimeMillis() {
                return java.lang.System.currentTimeMillis();
            }

            @Override // com.android.server.power.ShutdownCheckPoints.Injector
            public int maxCheckPoints() {
                return 100;
            }

            @Override // com.android.server.power.ShutdownCheckPoints.Injector
            public int maxDumpFiles() {
                return 20;
            }

            @Override // com.android.server.power.ShutdownCheckPoints.Injector
            public android.app.IActivityManager activityManager() {
                return android.app.ActivityManager.getService();
            }
        });
    }

    @com.android.internal.annotations.VisibleForTesting
    ShutdownCheckPoints(com.android.server.power.ShutdownCheckPoints.Injector injector) {
        this.mCheckPoints = new java.util.ArrayList<>();
        this.mInjector = injector;
    }

    public static void recordCheckPoint(@android.annotation.Nullable java.lang.String str) {
        INSTANCE.recordCheckPointInternal(str);
    }

    public static void recordCheckPoint(int i, @android.annotation.Nullable java.lang.String str) {
        INSTANCE.recordCheckPointInternal(i, str);
    }

    public static void recordCheckPoint(java.lang.String str, java.lang.String str2, @android.annotation.Nullable java.lang.String str3) {
        INSTANCE.recordCheckPointInternal(str, str2, str3);
    }

    public static void dump(java.io.PrintWriter printWriter) {
        INSTANCE.dumpInternal(printWriter);
    }

    public static java.lang.Thread newDumpThread(java.io.File file) {
        return INSTANCE.newDumpThreadInternal(file);
    }

    @com.android.internal.annotations.VisibleForTesting
    void recordCheckPointInternal(@android.annotation.Nullable java.lang.String str) {
        recordCheckPointInternal(new com.android.server.power.ShutdownCheckPoints.SystemServerCheckPoint(this.mInjector.currentTimeMillis(), str));
        android.util.Slog.v(TAG, "System server shutdown checkpoint recorded");
    }

    @com.android.internal.annotations.VisibleForTesting
    void recordCheckPointInternal(int i, @android.annotation.Nullable java.lang.String str) {
        com.android.server.power.ShutdownCheckPoints.CheckPoint binderCheckPoint;
        long currentTimeMillis = this.mInjector.currentTimeMillis();
        if (i == android.os.Process.myPid()) {
            binderCheckPoint = new com.android.server.power.ShutdownCheckPoints.SystemServerCheckPoint(currentTimeMillis, str);
        } else {
            binderCheckPoint = new com.android.server.power.ShutdownCheckPoints.BinderCheckPoint(currentTimeMillis, i, str);
        }
        recordCheckPointInternal(binderCheckPoint);
        android.util.Slog.v(TAG, "Binder shutdown checkpoint recorded with pid=" + i);
    }

    @com.android.internal.annotations.VisibleForTesting
    void recordCheckPointInternal(java.lang.String str, java.lang.String str2, @android.annotation.Nullable java.lang.String str3) {
        com.android.server.power.ShutdownCheckPoints.CheckPoint intentCheckPoint;
        long currentTimeMillis = this.mInjector.currentTimeMillis();
        if (com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME.equals(str2)) {
            intentCheckPoint = new com.android.server.power.ShutdownCheckPoints.SystemServerCheckPoint(currentTimeMillis, str3);
        } else {
            intentCheckPoint = new com.android.server.power.ShutdownCheckPoints.IntentCheckPoint(currentTimeMillis, str, str2, str3);
        }
        recordCheckPointInternal(intentCheckPoint);
        android.util.Slog.v(TAG, java.lang.String.format("Shutdown intent checkpoint recorded intent=%s from package=%s", str, str2));
    }

    private void recordCheckPointInternal(com.android.server.power.ShutdownCheckPoints.CheckPoint checkPoint) {
        synchronized (this.mCheckPoints) {
            try {
                this.mCheckPoints.add(checkPoint);
                if (this.mCheckPoints.size() > this.mInjector.maxCheckPoints()) {
                    this.mCheckPoints.remove(0);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void dumpInternal(java.io.PrintWriter printWriter) {
        java.util.ArrayList arrayList;
        synchronized (this.mCheckPoints) {
            arrayList = new java.util.ArrayList(this.mCheckPoints);
        }
        java.util.Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((com.android.server.power.ShutdownCheckPoints.CheckPoint) it.next()).dump(this.mInjector, printWriter);
            printWriter.println();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    java.lang.Thread newDumpThreadInternal(java.io.File file) {
        return new com.android.server.power.ShutdownCheckPoints.FileDumperThread(this, file, this.mInjector.maxDumpFiles());
    }

    private static abstract class CheckPoint {

        @android.annotation.Nullable
        private final java.lang.String mReason;
        private final long mTimestamp;

        abstract void dumpDetails(com.android.server.power.ShutdownCheckPoints.Injector injector, java.io.PrintWriter printWriter);

        abstract java.lang.String getOrigin();

        CheckPoint(long j, @android.annotation.Nullable java.lang.String str) {
            this.mTimestamp = j;
            this.mReason = str;
        }

        final void dump(com.android.server.power.ShutdownCheckPoints.Injector injector, java.io.PrintWriter printWriter) {
            printWriter.print("Shutdown request from ");
            printWriter.print(getOrigin());
            if (this.mReason != null) {
                printWriter.print(" for reason ");
                printWriter.print(this.mReason);
            }
            printWriter.print(" at ");
            printWriter.print(com.android.server.power.ShutdownCheckPoints.DATE_FORMAT.format(new java.util.Date(this.mTimestamp)));
            printWriter.println(" (epoch=" + this.mTimestamp + ")");
            dumpDetails(injector, printWriter);
        }
    }

    private static class SystemServerCheckPoint extends com.android.server.power.ShutdownCheckPoints.CheckPoint {
        private final java.lang.StackTraceElement[] mStackTraceElements;

        SystemServerCheckPoint(long j, @android.annotation.Nullable java.lang.String str) {
            super(j, str);
            this.mStackTraceElements = java.lang.Thread.currentThread().getStackTrace();
        }

        @Override // com.android.server.power.ShutdownCheckPoints.CheckPoint
        java.lang.String getOrigin() {
            return "SYSTEM";
        }

        @Override // com.android.server.power.ShutdownCheckPoints.CheckPoint
        void dumpDetails(com.android.server.power.ShutdownCheckPoints.Injector injector, java.io.PrintWriter printWriter) {
            java.lang.String findMethodName = findMethodName();
            if (findMethodName == null) {
                findMethodName = "Failed to get method name";
            }
            printWriter.println(findMethodName);
            printStackTrace(printWriter);
        }

        @android.annotation.Nullable
        java.lang.String findMethodName() {
            int findCallSiteIndex = findCallSiteIndex();
            if (findCallSiteIndex < this.mStackTraceElements.length) {
                java.lang.StackTraceElement stackTraceElement = this.mStackTraceElements[findCallSiteIndex];
                return java.lang.String.format("%s.%s", stackTraceElement.getClassName(), stackTraceElement.getMethodName());
            }
            return null;
        }

        void printStackTrace(java.io.PrintWriter printWriter) {
            int findCallSiteIndex = findCallSiteIndex();
            while (true) {
                findCallSiteIndex++;
                if (findCallSiteIndex < this.mStackTraceElements.length) {
                    printWriter.print(" at ");
                    printWriter.println(this.mStackTraceElements[findCallSiteIndex]);
                } else {
                    return;
                }
            }
        }

        private int findCallSiteIndex() {
            java.lang.String canonicalName = com.android.server.power.ShutdownCheckPoints.class.getCanonicalName();
            int i = 0;
            while (i < this.mStackTraceElements.length && !this.mStackTraceElements[i].getClassName().equals(canonicalName)) {
                i++;
            }
            while (i < this.mStackTraceElements.length && this.mStackTraceElements[i].getClassName().equals(canonicalName)) {
                i++;
            }
            return i;
        }
    }

    private static class BinderCheckPoint extends com.android.server.power.ShutdownCheckPoints.SystemServerCheckPoint {
        private final int mCallerProcessId;

        BinderCheckPoint(long j, int i, @android.annotation.Nullable java.lang.String str) {
            super(j, str);
            this.mCallerProcessId = i;
        }

        @Override // com.android.server.power.ShutdownCheckPoints.SystemServerCheckPoint, com.android.server.power.ShutdownCheckPoints.CheckPoint
        java.lang.String getOrigin() {
            return "BINDER";
        }

        @Override // com.android.server.power.ShutdownCheckPoints.SystemServerCheckPoint, com.android.server.power.ShutdownCheckPoints.CheckPoint
        void dumpDetails(com.android.server.power.ShutdownCheckPoints.Injector injector, java.io.PrintWriter printWriter) {
            java.lang.String findMethodName = findMethodName();
            if (findMethodName == null) {
                findMethodName = "Failed to get method name";
            }
            printWriter.println(findMethodName);
            java.lang.String findProcessName = findProcessName(injector.activityManager());
            printWriter.print("From process ");
            if (findProcessName == null) {
                findProcessName = "?";
            }
            printWriter.print(findProcessName);
            printWriter.println(" (pid=" + this.mCallerProcessId + ")");
        }

        @android.annotation.Nullable
        private java.lang.String findProcessName(@android.annotation.Nullable android.app.IActivityManager iActivityManager) {
            java.util.List<android.app.ActivityManager.RunningAppProcessInfo> list;
            try {
                if (iActivityManager == null) {
                    android.util.Slog.v(com.android.server.power.ShutdownCheckPoints.TAG, "No ActivityManager to find name of process with pid=" + this.mCallerProcessId);
                    list = null;
                } else {
                    list = iActivityManager.getRunningAppProcesses();
                }
                if (list != null) {
                    for (android.app.ActivityManager.RunningAppProcessInfo runningAppProcessInfo : list) {
                        if (runningAppProcessInfo.pid == this.mCallerProcessId) {
                            return runningAppProcessInfo.processName;
                        }
                    }
                }
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.power.ShutdownCheckPoints.TAG, "Failed to get running app processes from ActivityManager", e);
            }
            return null;
        }
    }

    private static class IntentCheckPoint extends com.android.server.power.ShutdownCheckPoints.CheckPoint {
        private final java.lang.String mIntentName;
        private final java.lang.String mPackageName;

        IntentCheckPoint(long j, java.lang.String str, java.lang.String str2, @android.annotation.Nullable java.lang.String str3) {
            super(j, str3);
            this.mIntentName = str;
            this.mPackageName = str2;
        }

        @Override // com.android.server.power.ShutdownCheckPoints.CheckPoint
        java.lang.String getOrigin() {
            return "INTENT";
        }

        @Override // com.android.server.power.ShutdownCheckPoints.CheckPoint
        void dumpDetails(com.android.server.power.ShutdownCheckPoints.Injector injector, java.io.PrintWriter printWriter) {
            printWriter.print("Intent: ");
            printWriter.println(this.mIntentName);
            printWriter.print("Package: ");
            printWriter.println(this.mPackageName);
        }
    }

    private static final class FileDumperThread extends java.lang.Thread {
        private final java.io.File mBaseFile;
        private final int mFileCountLimit;
        private final com.android.server.power.ShutdownCheckPoints mInstance;

        FileDumperThread(com.android.server.power.ShutdownCheckPoints shutdownCheckPoints, java.io.File file, int i) {
            this.mInstance = shutdownCheckPoints;
            this.mBaseFile = file;
            this.mFileCountLimit = i;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            this.mBaseFile.getParentFile().mkdirs();
            java.io.File[] listCheckPointsFiles = listCheckPointsFiles();
            int length = (listCheckPointsFiles.length - this.mFileCountLimit) + 1;
            for (int i = 0; i < length; i++) {
                listCheckPointsFiles[i].delete();
            }
            writeCheckpoints(new java.io.File(java.lang.String.format("%s-%d", this.mBaseFile.getAbsolutePath(), java.lang.Long.valueOf(java.lang.System.currentTimeMillis()))));
        }

        private java.io.File[] listCheckPointsFiles() {
            final java.lang.String str = this.mBaseFile.getName() + "-";
            java.io.File[] listFiles = this.mBaseFile.getParentFile().listFiles(new java.io.FilenameFilter() { // from class: com.android.server.power.ShutdownCheckPoints.FileDumperThread.1
                @Override // java.io.FilenameFilter
                public boolean accept(java.io.File file, java.lang.String str2) {
                    if (!str2.startsWith(str)) {
                        return false;
                    }
                    try {
                        java.lang.Long.valueOf(str2.substring(str.length()));
                        return true;
                    } catch (java.lang.NumberFormatException e) {
                        return false;
                    }
                }
            });
            if (listFiles == null) {
                return com.android.server.power.ShutdownCheckPoints.EMPTY_FILE_ARRAY;
            }
            java.util.Arrays.sort(listFiles);
            return listFiles;
        }

        private void writeCheckpoints(java.io.File file) {
            java.io.FileOutputStream fileOutputStream;
            android.util.AtomicFile atomicFile = new android.util.AtomicFile(this.mBaseFile);
            try {
                fileOutputStream = atomicFile.startWrite();
                try {
                    java.io.PrintWriter printWriter = new java.io.PrintWriter(fileOutputStream);
                    this.mInstance.dumpInternal(printWriter);
                    printWriter.flush();
                    atomicFile.finishWrite(fileOutputStream);
                } catch (java.io.IOException e) {
                    e = e;
                    android.util.Log.e(com.android.server.power.ShutdownCheckPoints.TAG, "Failed to write shutdown checkpoints", e);
                    if (fileOutputStream != null) {
                        atomicFile.failWrite(fileOutputStream);
                    }
                    this.mBaseFile.renameTo(file);
                }
            } catch (java.io.IOException e2) {
                e = e2;
                fileOutputStream = null;
            }
            this.mBaseFile.renameTo(file);
        }
    }
}
