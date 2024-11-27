package com.android.server.display;

/* loaded from: classes.dex */
final class SmallAreaDetectionController {
    private static final java.lang.String KEY_SMALL_AREA_DETECTION_ALLOWLIST = "small_area_detection_allowlist";
    private final android.content.Context mContext;
    private final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.Map<java.lang.String, java.lang.Float> mAllowPkgMap = new android.util.ArrayMap();
    private final android.content.pm.PackageManagerInternal mPackageManager = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);

    private static native void nativeSetSmallAreaDetectionThreshold(int i, float f);

    private static native void nativeUpdateSmallAreaDetection(int[] iArr, float[] fArr);

    static com.android.server.display.SmallAreaDetectionController create(@android.annotation.NonNull android.content.Context context) {
        com.android.server.display.SmallAreaDetectionController smallAreaDetectionController = new com.android.server.display.SmallAreaDetectionController(context, android.provider.DeviceConfigInterface.REAL);
        smallAreaDetectionController.updateAllowlist(android.provider.DeviceConfigInterface.REAL.getProperty("display_manager", KEY_SMALL_AREA_DETECTION_ALLOWLIST));
        return smallAreaDetectionController;
    }

    @com.android.internal.annotations.VisibleForTesting
    SmallAreaDetectionController(android.content.Context context, android.provider.DeviceConfigInterface deviceConfigInterface) {
        this.mContext = context;
        deviceConfigInterface.addOnPropertiesChangedListener("display_manager", com.android.internal.os.BackgroundThread.getExecutor(), new com.android.server.display.SmallAreaDetectionController.OnPropertiesChangedListener());
        this.mPackageManager.getPackageList(new com.android.server.display.SmallAreaDetectionController.PackageReceiver());
    }

    @com.android.internal.annotations.VisibleForTesting
    void updateAllowlist(@android.annotation.Nullable java.lang.String str) {
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        synchronized (this.mLock) {
            try {
                this.mAllowPkgMap.clear();
                int i = 0;
                if (str != null) {
                    java.lang.String[] split = str.split(",");
                    int length = split.length;
                    while (i < length) {
                        putToAllowlist(split[i]);
                        i++;
                    }
                } else {
                    java.lang.String[] stringArray = this.mContext.getResources().getStringArray(android.R.array.config_sfps_enroll_stage_thresholds);
                    int length2 = stringArray.length;
                    while (i < length2) {
                        putToAllowlist(stringArray[i]);
                        i++;
                    }
                }
                if (this.mAllowPkgMap.isEmpty()) {
                    return;
                }
                arrayMap.putAll(this.mAllowPkgMap);
                updateSmallAreaDetection(arrayMap);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void putToAllowlist(java.lang.String str) {
        java.lang.String[] split = str.split(":");
        if (split.length == 2) {
            try {
                this.mAllowPkgMap.put(split[0], java.lang.Float.valueOf(java.lang.Float.valueOf(split[1]).floatValue()));
            } catch (java.lang.Exception e) {
            }
        }
    }

    private void updateSmallAreaDetection(java.util.Map<java.lang.String, java.lang.Float> map) {
        android.util.SparseArray sparseArray = new android.util.SparseArray(map.size());
        for (java.lang.String str : map.keySet()) {
            float floatValue = map.get(str).floatValue();
            com.android.server.pm.pkg.PackageStateInternal packageStateInternal = this.mPackageManager.getPackageStateInternal(str);
            if (packageStateInternal != null) {
                sparseArray.put(packageStateInternal.getAppId(), java.lang.Float.valueOf(floatValue));
            }
        }
        int[] iArr = new int[sparseArray.size()];
        float[] fArr = new float[sparseArray.size()];
        for (int i = 0; i < sparseArray.size(); i++) {
            iArr[i] = sparseArray.keyAt(i);
            fArr[i] = ((java.lang.Float) sparseArray.valueAt(i)).floatValue();
        }
        updateSmallAreaDetection(iArr, fArr);
    }

    @com.android.internal.annotations.VisibleForTesting
    void updateSmallAreaDetection(int[] iArr, float[] fArr) {
        nativeUpdateSmallAreaDetection(iArr, fArr);
    }

    void setSmallAreaDetectionThreshold(int i, float f) {
        nativeSetSmallAreaDetectionThreshold(i, f);
    }

    void dump(java.io.PrintWriter printWriter) {
        printWriter.println("Small area detection allowlist");
        printWriter.println("  Packages:");
        synchronized (this.mLock) {
            try {
                for (java.lang.String str : this.mAllowPkgMap.keySet()) {
                    printWriter.println("    " + str + " threshold = " + this.mAllowPkgMap.get(str));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private class OnPropertiesChangedListener implements android.provider.DeviceConfig.OnPropertiesChangedListener {
        private OnPropertiesChangedListener() {
        }

        public void onPropertiesChanged(@android.annotation.NonNull android.provider.DeviceConfig.Properties properties) {
            if (properties.getKeyset().contains(com.android.server.display.SmallAreaDetectionController.KEY_SMALL_AREA_DETECTION_ALLOWLIST)) {
                com.android.server.display.SmallAreaDetectionController.this.updateAllowlist(properties.getString(com.android.server.display.SmallAreaDetectionController.KEY_SMALL_AREA_DETECTION_ALLOWLIST, (java.lang.String) null));
            }
        }
    }

    private final class PackageReceiver implements android.content.pm.PackageManagerInternal.PackageListObserver {
        private PackageReceiver() {
        }

        @Override // android.content.pm.PackageManagerInternal.PackageListObserver
        public void onPackageAdded(@android.annotation.NonNull java.lang.String str, int i) {
            float f;
            synchronized (com.android.server.display.SmallAreaDetectionController.this.mLock) {
                try {
                    if (!com.android.server.display.SmallAreaDetectionController.this.mAllowPkgMap.containsKey(str)) {
                        f = 0.0f;
                    } else {
                        f = ((java.lang.Float) com.android.server.display.SmallAreaDetectionController.this.mAllowPkgMap.get(str)).floatValue();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            if (f > com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                com.android.server.display.SmallAreaDetectionController.this.setSmallAreaDetectionThreshold(android.os.UserHandle.getAppId(i), f);
            }
        }
    }
}
