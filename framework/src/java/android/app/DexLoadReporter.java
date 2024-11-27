package android.app;

/* loaded from: classes.dex */
class DexLoadReporter implements dalvik.system.BaseDexClassLoader.Reporter {
    private static final boolean DEBUG = false;
    private static final android.app.DexLoadReporter INSTANCE = new android.app.DexLoadReporter();
    private static final java.lang.String TAG = "DexLoadReporter";
    private final java.util.Set<java.lang.String> mDataDirs = new java.util.HashSet();

    private DexLoadReporter() {
    }

    static android.app.DexLoadReporter getInstance() {
        return INSTANCE;
    }

    void registerAppDataDir(java.lang.String str, java.lang.String str2) {
        if (str2 != null) {
            synchronized (this.mDataDirs) {
                this.mDataDirs.add(str2);
            }
        }
    }

    public void report(java.util.Map<java.lang.String, java.lang.String> map) {
        if (map.isEmpty()) {
            android.util.Slog.wtf(TAG, "Bad call to DexLoadReporter: empty classLoaderContextMap");
        } else {
            notifyPackageManager(map);
            registerSecondaryDexForProfiling(map.keySet());
        }
    }

    private void notifyPackageManager(java.util.Map<java.lang.String, java.lang.String> map) {
        java.lang.String currentPackageName = android.app.ActivityThread.currentPackageName();
        try {
            android.app.ActivityThread.getPackageManager().notifyDexLoad(currentPackageName, map, dalvik.system.VMRuntime.getRuntime().vmInstructionSet());
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Failed to notify PM about dex load for package " + currentPackageName, e);
        }
    }

    private void registerSecondaryDexForProfiling(java.util.Set<java.lang.String> set) {
        java.lang.String[] strArr;
        if (!android.os.SystemProperties.getBoolean("dalvik.vm.dexopt.secondary", false)) {
            return;
        }
        synchronized (this.mDataDirs) {
            strArr = (java.lang.String[]) this.mDataDirs.toArray(new java.lang.String[0]);
        }
        java.util.Iterator<java.lang.String> it = set.iterator();
        while (it.hasNext()) {
            registerSecondaryDexForProfiling(it.next(), strArr);
        }
    }

    private void registerSecondaryDexForProfiling(java.lang.String str, java.lang.String[] strArr) {
        if (!isSecondaryDexFile(str, strArr)) {
            return;
        }
        java.io.File file = new java.io.File(str);
        java.io.File file2 = new java.io.File(file.getParent(), "oat");
        java.io.File file3 = new java.io.File(file2, file.getName() + ".cur.prof");
        java.io.File file4 = new java.io.File(file2, file.getName() + ".prof");
        if (!file2.exists() && !file2.mkdir()) {
            android.util.Slog.e(TAG, "Could not create the profile directory: " + file3);
            return;
        }
        try {
            file3.createNewFile();
            dalvik.system.VMRuntime.registerAppInfo(android.app.ActivityThread.currentPackageName(), file3.getPath(), file4.getPath(), new java.lang.String[]{str}, 4);
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Failed to create profile for secondary dex " + str + ":" + e.getMessage());
        }
    }

    private boolean isSecondaryDexFile(java.lang.String str, java.lang.String[] strArr) {
        for (java.lang.String str2 : strArr) {
            if (android.os.FileUtils.contains(str2, str)) {
                return true;
            }
        }
        return false;
    }
}
