package com.android.server.wm;

/* loaded from: classes3.dex */
class SplashScreenExceptionList {
    private static final boolean DEBUG = android.os.Build.isDebuggable();
    private static final java.lang.String KEY_SPLASH_SCREEN_EXCEPTION_LIST = "splash_screen_exception_list";
    private static final java.lang.String LOG_TAG = "SplashScreenExceptionList";
    private static final java.lang.String NAMESPACE = "window_manager";
    private static final java.lang.String OPT_OUT_METADATA_FLAG = "android.splashscreen.exception_opt_out";

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.HashSet<java.lang.String> mDeviceConfigExcludedPackages = new java.util.HashSet<>();
    private final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.VisibleForTesting
    final android.provider.DeviceConfig.OnPropertiesChangedListener mOnPropertiesChangedListener;

    SplashScreenExceptionList(@android.annotation.NonNull java.util.concurrent.Executor executor) {
        updateDeviceConfig(android.provider.DeviceConfig.getString(NAMESPACE, KEY_SPLASH_SCREEN_EXCEPTION_LIST, ""));
        this.mOnPropertiesChangedListener = new android.provider.DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.wm.SplashScreenExceptionList$$ExternalSyntheticLambda0
            public final void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
                com.android.server.wm.SplashScreenExceptionList.this.lambda$new$0(properties);
            }
        };
        android.provider.DeviceConfig.addOnPropertiesChangedListener(NAMESPACE, executor, this.mOnPropertiesChangedListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(android.provider.DeviceConfig.Properties properties) {
        updateDeviceConfig(properties.getString(KEY_SPLASH_SCREEN_EXCEPTION_LIST, ""));
    }

    @com.android.internal.annotations.VisibleForTesting
    void updateDeviceConfig(java.lang.String str) {
        parseDeviceConfigPackageList(str);
    }

    public boolean isException(@android.annotation.NonNull java.lang.String str, int i, @android.annotation.Nullable java.util.function.Supplier<android.content.pm.ApplicationInfo> supplier) {
        if (i > 35) {
            return false;
        }
        synchronized (this.mLock) {
            try {
                if (DEBUG) {
                    android.util.Slog.v(LOG_TAG, java.lang.String.format(java.util.Locale.US, "SplashScreen checking exception for package %s (target sdk:%d) -> %s", str, java.lang.Integer.valueOf(i), java.lang.Boolean.valueOf(this.mDeviceConfigExcludedPackages.contains(str))));
                }
                if (this.mDeviceConfigExcludedPackages.contains(str)) {
                    return !isOptedOut(supplier);
                }
                return false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private static boolean isOptedOut(@android.annotation.Nullable java.util.function.Supplier<android.content.pm.ApplicationInfo> supplier) {
        android.content.pm.ApplicationInfo applicationInfo;
        return (supplier == null || (applicationInfo = supplier.get()) == null || applicationInfo.metaData == null || !applicationInfo.metaData.getBoolean(OPT_OUT_METADATA_FLAG, false)) ? false : true;
    }

    private void parseDeviceConfigPackageList(java.lang.String str) {
        synchronized (this.mLock) {
            try {
                this.mDeviceConfigExcludedPackages.clear();
                for (java.lang.String str2 : str.split(",")) {
                    java.lang.String trim = str2.trim();
                    if (!trim.isEmpty()) {
                        this.mDeviceConfigExcludedPackages.add(trim);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }
}
