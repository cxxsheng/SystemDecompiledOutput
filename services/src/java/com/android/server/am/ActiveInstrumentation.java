package com.android.server.am;

/* loaded from: classes.dex */
class ActiveInstrumentation {
    android.os.Bundle mArguments;
    android.content.ComponentName mClass;
    android.os.Bundle mCurResults;
    boolean mFinished;
    boolean mHasBackgroundActivityStartsPermission;
    boolean mHasBackgroundForegroundServiceStartsPermission;
    boolean mIsSdkInSandbox;
    boolean mNoRestart;
    java.lang.String mProfileFile;
    android.content.ComponentName mResultClass;
    final java.util.ArrayList<com.android.server.am.ProcessRecord> mRunningProcesses = new java.util.ArrayList<>();
    final com.android.server.am.ActivityManagerService mService;
    int mSourceUid;
    android.content.pm.ApplicationInfo mTargetInfo;
    java.lang.String[] mTargetProcesses;
    android.app.IUiAutomationConnection mUiAutomationConnection;
    android.app.IInstrumentationWatcher mWatcher;

    ActiveInstrumentation(com.android.server.am.ActivityManagerService activityManagerService) {
        this.mService = activityManagerService;
    }

    void removeProcess(com.android.server.am.ProcessRecord processRecord) {
        this.mFinished = true;
        this.mRunningProcesses.remove(processRecord);
        if (this.mRunningProcesses.size() == 0) {
            this.mService.mActiveInstrumentation.remove(this);
        }
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
        sb.append("ActiveInstrumentation{");
        sb.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
        sb.append(' ');
        sb.append(this.mClass.toShortString());
        if (this.mFinished) {
            sb.append(" FINISHED");
        }
        sb.append(" ");
        sb.append(this.mRunningProcesses.size());
        sb.append(" procs");
        sb.append('}');
        return sb.toString();
    }

    void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.print(str);
        printWriter.print("mClass=");
        printWriter.print(this.mClass);
        printWriter.print(" mFinished=");
        printWriter.println(this.mFinished);
        printWriter.print(str);
        printWriter.println("mRunningProcesses:");
        for (int i = 0; i < this.mRunningProcesses.size(); i++) {
            printWriter.print(str);
            printWriter.print("  #");
            printWriter.print(i);
            printWriter.print(": ");
            printWriter.println(this.mRunningProcesses.get(i));
        }
        printWriter.print(str);
        printWriter.print("mTargetProcesses=");
        printWriter.println(java.util.Arrays.toString(this.mTargetProcesses));
        printWriter.print(str);
        printWriter.print("mTargetInfo=");
        printWriter.println(this.mTargetInfo);
        if (this.mTargetInfo != null) {
            this.mTargetInfo.dump(new android.util.PrintWriterPrinter(printWriter), str + "  ", 0);
        }
        if (this.mProfileFile != null) {
            printWriter.print(str);
            printWriter.print("mProfileFile=");
            printWriter.println(this.mProfileFile);
        }
        if (this.mWatcher != null) {
            printWriter.print(str);
            printWriter.print("mWatcher=");
            printWriter.println(this.mWatcher);
        }
        if (this.mUiAutomationConnection != null) {
            printWriter.print(str);
            printWriter.print("mUiAutomationConnection=");
            printWriter.println(this.mUiAutomationConnection);
        }
        printWriter.print("mHasBackgroundActivityStartsPermission=");
        printWriter.println(this.mHasBackgroundActivityStartsPermission);
        printWriter.print("mHasBackgroundForegroundServiceStartsPermission=");
        printWriter.println(this.mHasBackgroundForegroundServiceStartsPermission);
        printWriter.print(str);
        printWriter.print("mArguments=");
        printWriter.println(this.mArguments);
    }

    void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        this.mClass.dumpDebug(protoOutputStream, 1146756268033L);
        protoOutputStream.write(1133871366146L, this.mFinished);
        for (int i = 0; i < this.mRunningProcesses.size(); i++) {
            this.mRunningProcesses.get(i).dumpDebug(protoOutputStream, 2246267895811L);
        }
        for (java.lang.String str : this.mTargetProcesses) {
            protoOutputStream.write(2237677961220L, str);
        }
        if (this.mTargetInfo != null) {
            this.mTargetInfo.dumpDebug(protoOutputStream, 1146756268037L, 0);
        }
        protoOutputStream.write(1138166333446L, this.mProfileFile);
        protoOutputStream.write(1138166333447L, this.mWatcher.toString());
        protoOutputStream.write(1138166333448L, this.mUiAutomationConnection.toString());
        if (this.mArguments != null) {
            this.mArguments.dumpDebug(protoOutputStream, 1146756268042L);
        }
        protoOutputStream.end(start);
    }
}
