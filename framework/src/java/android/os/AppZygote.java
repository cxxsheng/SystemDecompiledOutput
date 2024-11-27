package android.os;

/* loaded from: classes3.dex */
public class AppZygote {
    private static final java.lang.String LOG_TAG = "AppZygote";
    private final android.content.pm.ApplicationInfo mAppInfo;
    private final java.lang.Object mLock = new java.lang.Object();
    private final android.content.pm.ProcessInfo mProcessInfo;
    private android.os.ChildZygoteProcess mZygote;
    private final int mZygoteUid;
    private final int mZygoteUidGidMax;
    private final int mZygoteUidGidMin;

    public AppZygote(android.content.pm.ApplicationInfo applicationInfo, android.content.pm.ProcessInfo processInfo, int i, int i2, int i3) {
        this.mAppInfo = applicationInfo;
        this.mProcessInfo = processInfo;
        this.mZygoteUid = i;
        this.mZygoteUidGidMin = i2;
        this.mZygoteUidGidMax = i3;
    }

    public android.os.ChildZygoteProcess getProcess() {
        synchronized (this.mLock) {
            if (this.mZygote != null) {
                return this.mZygote;
            }
            connectToZygoteIfNeededLocked();
            return this.mZygote;
        }
    }

    public void stopZygote() {
        synchronized (this.mLock) {
            stopZygoteLocked();
        }
    }

    public android.content.pm.ApplicationInfo getAppInfo() {
        return this.mAppInfo;
    }

    private void stopZygoteLocked() {
        if (this.mZygote != null) {
            this.mZygote.close();
            android.os.Process.killProcessGroup(this.mZygoteUid, this.mZygote.getPid());
            this.mZygote = null;
        }
    }

    private void connectToZygoteIfNeededLocked() {
        java.lang.String str = this.mAppInfo.primaryCpuAbi != null ? this.mAppInfo.primaryCpuAbi : android.os.Build.SUPPORTED_ABIS[0];
        try {
            this.mZygote = android.os.Process.ZYGOTE_PROCESS.startChildZygote("com.android.internal.os.AppZygoteInit", this.mAppInfo.processName + "_zygote", this.mZygoteUid, this.mZygoteUid, null, com.android.internal.os.Zygote.getMemorySafetyRuntimeFlagsForSecondaryZygote(this.mAppInfo, this.mProcessInfo), "app_zygote", str, str, dalvik.system.VMRuntime.getInstructionSet(str), this.mZygoteUidGidMin, this.mZygoteUidGidMax);
            android.os.ZygoteProcess.waitForConnectionToZygote(this.mZygote.getPrimarySocketAddress());
            android.util.Log.i(LOG_TAG, "Starting application preload.");
            this.mZygote.preloadApp(this.mAppInfo, str);
            android.util.Log.i(LOG_TAG, "Application preload done.");
        } catch (java.lang.Exception e) {
            android.util.Log.e(LOG_TAG, "Error connecting to app zygote", e);
            stopZygoteLocked();
        }
    }
}
