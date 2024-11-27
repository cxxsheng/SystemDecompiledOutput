package com.android.server.display.color;

/* loaded from: classes.dex */
class AppSaturationController {

    @com.android.internal.annotations.VisibleForTesting
    static final float[] TRANSLATION_VECTOR = {com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE};
    private final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.Map<java.lang.String, android.util.SparseArray<com.android.server.display.color.AppSaturationController.SaturationController>> mAppsMap = new java.util.HashMap();

    AppSaturationController() {
    }

    boolean addColorTransformController(java.lang.String str, int i, java.lang.ref.WeakReference<com.android.server.display.color.ColorDisplayService.ColorTransformController> weakReference) {
        boolean addColorTransformController;
        synchronized (this.mLock) {
            addColorTransformController = getSaturationControllerLocked(str, i).addColorTransformController(weakReference);
        }
        return addColorTransformController;
    }

    public boolean setSaturationLevel(java.lang.String str, java.lang.String str2, int i, int i2) {
        boolean saturationLevel;
        synchronized (this.mLock) {
            saturationLevel = getSaturationControllerLocked(str2, i).setSaturationLevel(str, i2);
        }
        return saturationLevel;
    }

    public void dump(java.io.PrintWriter printWriter) {
        synchronized (this.mLock) {
            try {
                printWriter.println("App Saturation: ");
                if (this.mAppsMap.size() == 0) {
                    printWriter.println("    No packages");
                    return;
                }
                java.util.ArrayList<java.lang.String> arrayList = new java.util.ArrayList(this.mAppsMap.keySet());
                java.util.Collections.sort(arrayList);
                for (java.lang.String str : arrayList) {
                    printWriter.println("    " + str + ":");
                    android.util.SparseArray<com.android.server.display.color.AppSaturationController.SaturationController> sparseArray = this.mAppsMap.get(str);
                    for (int i = 0; i < sparseArray.size(); i++) {
                        printWriter.println("        " + sparseArray.keyAt(i) + ":");
                        sparseArray.valueAt(i).dump(printWriter);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private com.android.server.display.color.AppSaturationController.SaturationController getSaturationControllerLocked(java.lang.String str, int i) {
        return getOrCreateSaturationControllerLocked(getOrCreateUserIdMapLocked(str), i);
    }

    private android.util.SparseArray<com.android.server.display.color.AppSaturationController.SaturationController> getOrCreateUserIdMapLocked(java.lang.String str) {
        if (this.mAppsMap.get(str) != null) {
            return this.mAppsMap.get(str);
        }
        android.util.SparseArray<com.android.server.display.color.AppSaturationController.SaturationController> sparseArray = new android.util.SparseArray<>();
        this.mAppsMap.put(str, sparseArray);
        return sparseArray;
    }

    private com.android.server.display.color.AppSaturationController.SaturationController getOrCreateSaturationControllerLocked(android.util.SparseArray<com.android.server.display.color.AppSaturationController.SaturationController> sparseArray, int i) {
        if (sparseArray.get(i) != null) {
            return sparseArray.get(i);
        }
        com.android.server.display.color.AppSaturationController.SaturationController saturationController = new com.android.server.display.color.AppSaturationController.SaturationController();
        sparseArray.put(i, saturationController);
        return saturationController;
    }

    @com.android.internal.annotations.VisibleForTesting
    static void computeGrayscaleTransformMatrix(float f, float[] fArr) {
        float f2 = 1.0f - f;
        float[] fArr2 = {0.231f * f2, 0.715f * f2, f2 * 0.072f};
        fArr[0] = fArr2[0] + f;
        fArr[1] = fArr2[0];
        fArr[2] = fArr2[0];
        fArr[3] = fArr2[1];
        fArr[4] = fArr2[1] + f;
        fArr[5] = fArr2[1];
        fArr[6] = fArr2[2];
        fArr[7] = fArr2[2];
        fArr[8] = fArr2[2] + f;
    }

    private static class SaturationController {
        private static final int FULL_SATURATION = 100;
        private final java.util.List<java.lang.ref.WeakReference<com.android.server.display.color.ColorDisplayService.ColorTransformController>> mControllerRefs;
        private final android.util.ArrayMap<java.lang.String, java.lang.Integer> mSaturationLevels;
        private float[] mTransformMatrix;

        private SaturationController() {
            this.mControllerRefs = new java.util.ArrayList();
            this.mSaturationLevels = new android.util.ArrayMap<>();
            this.mTransformMatrix = new float[9];
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean setSaturationLevel(java.lang.String str, int i) {
            if (i == 100) {
                this.mSaturationLevels.remove(str);
            } else {
                this.mSaturationLevels.put(str, java.lang.Integer.valueOf(i));
            }
            if (!this.mControllerRefs.isEmpty()) {
                return updateState();
            }
            return false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean addColorTransformController(java.lang.ref.WeakReference<com.android.server.display.color.ColorDisplayService.ColorTransformController> weakReference) {
            clearExpiredReferences();
            this.mControllerRefs.add(weakReference);
            if (!this.mSaturationLevels.isEmpty()) {
                return updateState();
            }
            return false;
        }

        private int calculateSaturationLevel() {
            int i = 100;
            for (int i2 = 0; i2 < this.mSaturationLevels.size(); i2++) {
                int intValue = this.mSaturationLevels.valueAt(i2).intValue();
                if (intValue < i) {
                    i = intValue;
                }
            }
            return i;
        }

        private boolean updateState() {
            com.android.server.display.color.AppSaturationController.computeGrayscaleTransformMatrix(calculateSaturationLevel() / 100.0f, this.mTransformMatrix);
            java.util.Iterator<java.lang.ref.WeakReference<com.android.server.display.color.ColorDisplayService.ColorTransformController>> it = this.mControllerRefs.iterator();
            boolean z = false;
            while (it.hasNext()) {
                com.android.server.display.color.ColorDisplayService.ColorTransformController colorTransformController = it.next().get();
                if (colorTransformController != null) {
                    colorTransformController.applyAppSaturation(this.mTransformMatrix, com.android.server.display.color.AppSaturationController.TRANSLATION_VECTOR);
                    z = true;
                } else {
                    it.remove();
                }
            }
            return z;
        }

        private void clearExpiredReferences() {
            java.util.Iterator<java.lang.ref.WeakReference<com.android.server.display.color.ColorDisplayService.ColorTransformController>> it = this.mControllerRefs.iterator();
            while (it.hasNext()) {
                if (it.next().get() == null) {
                    it.remove();
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void dump(java.io.PrintWriter printWriter) {
            printWriter.println("            mSaturationLevels: " + this.mSaturationLevels);
            printWriter.println("            mControllerRefs count: " + this.mControllerRefs.size());
        }
    }
}
