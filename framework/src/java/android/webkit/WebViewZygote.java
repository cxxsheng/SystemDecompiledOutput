package android.webkit;

/* loaded from: classes4.dex */
public class WebViewZygote {
    private static final java.lang.String LOGTAG = "WebViewZygote";
    private static final java.lang.Object sLock = new java.lang.Object();
    private static boolean sMultiprocessEnabled = false;
    private static android.content.pm.PackageInfo sPackage;
    private static android.os.ChildZygoteProcess sZygote;

    public static android.os.ZygoteProcess getProcess() {
        synchronized (sLock) {
            if (sZygote != null) {
                return sZygote;
            }
            connectToZygoteIfNeededLocked();
            return sZygote;
        }
    }

    public static java.lang.String getPackageName() {
        java.lang.String str;
        synchronized (sLock) {
            str = sPackage.packageName;
        }
        return str;
    }

    public static boolean isMultiprocessEnabled() {
        synchronized (sLock) {
            boolean z = true;
            if (android.webkit.Flags.updateServiceV2()) {
                if (sPackage == null) {
                    z = false;
                }
                return z;
            }
            if (!sMultiprocessEnabled || sPackage == null) {
                z = false;
            }
            return z;
        }
    }

    public static void setMultiprocessEnabled(boolean z) {
        if (android.webkit.Flags.updateServiceV2()) {
            throw new java.lang.IllegalStateException("setMultiprocessEnabled shouldn't be called if update_service_v2 flag is set.");
        }
        synchronized (sLock) {
            sMultiprocessEnabled = z;
            if (!z) {
                stopZygoteLocked();
            }
        }
    }

    static void onWebViewProviderChanged(android.content.pm.PackageInfo packageInfo) {
        synchronized (sLock) {
            sPackage = packageInfo;
            if (sMultiprocessEnabled) {
                stopZygoteLocked();
            }
        }
    }

    private static void stopZygoteLocked() {
        if (sZygote != null) {
            sZygote.close();
            android.os.Process.killProcess(sZygote.getPid());
            sZygote = null;
        }
    }

    private static void connectToZygoteIfNeededLocked() {
        if (sZygote != null) {
            return;
        }
        if (sPackage == null) {
            android.util.Log.e(LOGTAG, "Cannot connect to zygote, no package specified");
            return;
        }
        try {
            java.lang.String str = sPackage.applicationInfo.primaryCpuAbi;
            sZygote = android.os.Process.ZYGOTE_PROCESS.startChildZygote("com.android.internal.os.WebViewZygoteInit", "webview_zygote", 1053, 1053, null, com.android.internal.os.Zygote.getMemorySafetyRuntimeFlagsForSecondaryZygote(sPackage.applicationInfo, null), "webview_zygote", str, android.text.TextUtils.join(",", android.os.Build.SUPPORTED_ABIS), null, android.os.Process.FIRST_ISOLATED_UID, Integer.MAX_VALUE);
            android.os.ZygoteProcess.waitForConnectionToZygote(sZygote.getPrimarySocketAddress());
            sZygote.preloadApp(sPackage.applicationInfo, str);
        } catch (java.lang.Exception e) {
            android.util.Log.e(LOGTAG, "Error connecting to webview zygote", e);
            stopZygoteLocked();
        }
    }
}
