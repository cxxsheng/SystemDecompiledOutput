package android.webkit;

/* loaded from: classes4.dex */
public class WebViewLibraryLoader {
    private static final java.lang.String CHROMIUM_WEBVIEW_NATIVE_RELRO_32 = "/data/misc/shared_relro/libwebviewchromium32.relro";
    private static final java.lang.String CHROMIUM_WEBVIEW_NATIVE_RELRO_64 = "/data/misc/shared_relro/libwebviewchromium64.relro";
    private static final boolean DEBUG = false;
    private static final java.lang.String LOGTAG = android.webkit.WebViewLibraryLoader.class.getSimpleName();
    private static boolean sAddressSpaceReserved = false;

    static native boolean nativeCreateRelroFile(java.lang.String str, java.lang.String str2, java.lang.ClassLoader classLoader);

    static native int nativeLoadWithRelroFile(java.lang.String str, java.lang.String str2, java.lang.ClassLoader classLoader);

    static native boolean nativeReserveAddressSpace(long j);

    private static class RelroFileCreator {
        private RelroFileCreator() {
        }

        public static void main(java.lang.String[] strArr) {
            boolean is64Bit = dalvik.system.VMRuntime.getRuntime().is64Bit();
            try {
                if (strArr.length == 2 && strArr[0] != null && strArr[1] != null) {
                    java.lang.String str = strArr[0];
                    java.lang.String str2 = strArr[1];
                    android.util.Log.v(android.webkit.WebViewLibraryLoader.LOGTAG, "RelroFileCreator (64bit = " + is64Bit + "), package: " + str + " library: " + str2);
                    if (!android.webkit.WebViewLibraryLoader.sAddressSpaceReserved) {
                        android.util.Log.e(android.webkit.WebViewLibraryLoader.LOGTAG, "can't create relro file; address space not reserved");
                        return;
                    }
                    boolean nativeCreateRelroFile = android.webkit.WebViewLibraryLoader.nativeCreateRelroFile(str2, is64Bit ? android.webkit.WebViewLibraryLoader.CHROMIUM_WEBVIEW_NATIVE_RELRO_64 : android.webkit.WebViewLibraryLoader.CHROMIUM_WEBVIEW_NATIVE_RELRO_32, android.app.ActivityThread.currentActivityThread().getPackageInfo(str, (android.content.res.CompatibilityInfo) null, 3).getClassLoader());
                    try {
                        if (android.webkit.Flags.updateServiceIpcWrapper()) {
                            android.webkit.WebViewUpdateManager.getInstance().notifyRelroCreationCompleted();
                        } else {
                            android.webkit.WebViewFactory.getUpdateServiceUnchecked().notifyRelroCreationCompleted();
                        }
                    } catch (java.lang.Exception e) {
                        android.util.Log.e(android.webkit.WebViewLibraryLoader.LOGTAG, "error notifying update service", e);
                    }
                    if (!nativeCreateRelroFile) {
                        android.util.Log.e(android.webkit.WebViewLibraryLoader.LOGTAG, "failed to create relro file");
                    }
                    java.lang.System.exit(0);
                    return;
                }
                android.util.Log.e(android.webkit.WebViewLibraryLoader.LOGTAG, "Invalid RelroFileCreator args: " + java.util.Arrays.toString(strArr));
                try {
                    if (android.webkit.Flags.updateServiceIpcWrapper()) {
                        android.webkit.WebViewUpdateManager.getInstance().notifyRelroCreationCompleted();
                    } else {
                        android.webkit.WebViewFactory.getUpdateServiceUnchecked().notifyRelroCreationCompleted();
                    }
                } catch (java.lang.Exception e2) {
                    android.util.Log.e(android.webkit.WebViewLibraryLoader.LOGTAG, "error notifying update service", e2);
                }
                android.util.Log.e(android.webkit.WebViewLibraryLoader.LOGTAG, "failed to create relro file");
                java.lang.System.exit(0);
            } finally {
                try {
                    if (android.webkit.Flags.updateServiceIpcWrapper()) {
                        android.webkit.WebViewUpdateManager.getInstance().notifyRelroCreationCompleted();
                    } else {
                        android.webkit.WebViewFactory.getUpdateServiceUnchecked().notifyRelroCreationCompleted();
                    }
                } catch (java.lang.Exception e3) {
                    android.util.Log.e(android.webkit.WebViewLibraryLoader.LOGTAG, "error notifying update service", e3);
                }
                android.util.Log.e(android.webkit.WebViewLibraryLoader.LOGTAG, "failed to create relro file");
                java.lang.System.exit(0);
            }
        }
    }

    static void createRelroFile(boolean z, java.lang.String str, java.lang.String str2) {
        final java.lang.String str3 = z ? android.os.Build.SUPPORTED_64_BIT_ABIS[0] : android.os.Build.SUPPORTED_32_BIT_ABIS[0];
        java.lang.Runnable runnable = new java.lang.Runnable() { // from class: android.webkit.WebViewLibraryLoader.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    android.util.Log.e(android.webkit.WebViewLibraryLoader.LOGTAG, "relro file creator for " + str3 + " crashed. Proceeding without");
                    if (android.webkit.Flags.updateServiceIpcWrapper()) {
                        android.webkit.WebViewUpdateManager.getInstance().notifyRelroCreationCompleted();
                    } else {
                        android.webkit.WebViewFactory.getUpdateService().notifyRelroCreationCompleted();
                    }
                } catch (java.lang.Exception e) {
                    android.util.Log.e(android.webkit.WebViewLibraryLoader.LOGTAG, "Cannot reach WebViewUpdateService. " + e.getMessage());
                }
            }
        };
        try {
            if (!((android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class)).startIsolatedProcess(android.webkit.WebViewLibraryLoader.RelroFileCreator.class.getName(), new java.lang.String[]{str, str2}, "WebViewLoader-" + str3, str3, 1037, runnable)) {
                throw new java.lang.Exception("Failed to start the relro file creator process");
            }
        } catch (java.lang.Throwable th) {
            android.util.Log.e(LOGTAG, "error starting relro file creator for abi " + str3, th);
            runnable.run();
        }
    }

    static int prepareNativeLibraries(android.content.pm.PackageInfo packageInfo) {
        java.lang.String webViewLibrary = android.webkit.WebViewFactory.getWebViewLibrary(packageInfo.applicationInfo);
        if (webViewLibrary == null) {
            return 0;
        }
        return createRelros(packageInfo.packageName, webViewLibrary);
    }

    private static int createRelros(java.lang.String str, java.lang.String str2) {
        int i = 0;
        if (android.os.Build.SUPPORTED_32_BIT_ABIS.length > 0) {
            createRelroFile(false, str, str2);
            i = 1;
        }
        if (android.os.Build.SUPPORTED_64_BIT_ABIS.length > 0) {
            createRelroFile(true, str, str2);
            return i + 1;
        }
        return i;
    }

    static void reserveAddressSpaceInZygote() {
        long j;
        java.lang.System.loadLibrary("webviewchromium_loader");
        if (dalvik.system.VMRuntime.getRuntime().is64Bit()) {
            j = 1073741824;
        } else if (dalvik.system.VMRuntime.getRuntime().vmInstructionSet().equals("arm")) {
            j = 136314880;
        } else {
            j = 199229440;
        }
        sAddressSpaceReserved = nativeReserveAddressSpace(j);
        if (!sAddressSpaceReserved) {
            android.util.Log.e(LOGTAG, "reserving " + j + " bytes of address space failed");
        }
    }

    public static int loadNativeLibrary(java.lang.ClassLoader classLoader, java.lang.String str) {
        if (!sAddressSpaceReserved) {
            android.util.Log.e(LOGTAG, "can't load with relro file; address space not reserved");
            return 2;
        }
        int nativeLoadWithRelroFile = nativeLoadWithRelroFile(str, dalvik.system.VMRuntime.getRuntime().is64Bit() ? CHROMIUM_WEBVIEW_NATIVE_RELRO_64 : CHROMIUM_WEBVIEW_NATIVE_RELRO_32, classLoader);
        if (nativeLoadWithRelroFile != 0) {
            android.util.Log.w(LOGTAG, "failed to load with relro file, proceeding without");
        }
        return nativeLoadWithRelroFile;
    }
}
