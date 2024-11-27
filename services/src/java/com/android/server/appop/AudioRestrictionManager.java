package com.android.server.appop;

/* loaded from: classes.dex */
public class AudioRestrictionManager {
    static final android.util.SparseArray<android.util.SparseBooleanArray> CAMERA_AUDIO_RESTRICTIONS;
    static final java.lang.String TAG = "AudioRestriction";
    final android.util.SparseArray<android.util.SparseArray<com.android.server.appop.AudioRestrictionManager.Restriction>> mZenModeAudioRestrictions = new android.util.SparseArray<>();
    int mCameraAudioRestriction = 0;

    static {
        android.util.SparseBooleanArray sparseBooleanArray = new android.util.SparseBooleanArray();
        android.util.SparseBooleanArray sparseBooleanArray2 = new android.util.SparseBooleanArray();
        for (int i : android.media.AudioAttributes.SDK_USAGES.toArray()) {
            int i2 = android.media.AudioAttributes.SUPPRESSIBLE_USAGES.get(i);
            if (i2 == 1 || i2 == 2 || i2 == 4) {
                sparseBooleanArray.append(i, true);
                sparseBooleanArray2.append(i, true);
            } else if (i2 != 5 && i2 != 6 && i2 != 3) {
                android.util.Slog.e(TAG, "Unknown audio suppression behavior" + i2);
            }
        }
        CAMERA_AUDIO_RESTRICTIONS = new android.util.SparseArray<>();
        CAMERA_AUDIO_RESTRICTIONS.append(28, sparseBooleanArray);
        CAMERA_AUDIO_RESTRICTIONS.append(3, sparseBooleanArray2);
    }

    private static final class Restriction {
        private static final android.util.ArraySet<java.lang.String> NO_EXCEPTIONS = new android.util.ArraySet<>();
        android.util.ArraySet<java.lang.String> exceptionPackages;
        int mode;

        private Restriction() {
            this.exceptionPackages = NO_EXCEPTIONS;
        }
    }

    public int checkAudioOperation(int i, int i2, int i3, java.lang.String str) {
        android.util.SparseBooleanArray sparseBooleanArray;
        synchronized (this) {
            try {
                if (this.mCameraAudioRestriction != 0 && ((i == 3 || (i == 28 && this.mCameraAudioRestriction == 3)) && (sparseBooleanArray = CAMERA_AUDIO_RESTRICTIONS.get(i)) != null && sparseBooleanArray.get(i2))) {
                    return 1;
                }
                int checkZenModeRestrictionLocked = checkZenModeRestrictionLocked(i, i2, i3, str);
                if (checkZenModeRestrictionLocked != 0) {
                    return checkZenModeRestrictionLocked;
                }
                return 0;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private int checkZenModeRestrictionLocked(int i, int i2, int i3, java.lang.String str) {
        com.android.server.appop.AudioRestrictionManager.Restriction restriction;
        android.util.SparseArray<com.android.server.appop.AudioRestrictionManager.Restriction> sparseArray = this.mZenModeAudioRestrictions.get(i);
        if (sparseArray != null && (restriction = sparseArray.get(i2)) != null && !restriction.exceptionPackages.contains(str)) {
            return restriction.mode;
        }
        return 0;
    }

    public void setZenModeAudioRestriction(int i, int i2, int i3, int i4, java.lang.String[] strArr) {
        synchronized (this) {
            try {
                android.util.SparseArray<com.android.server.appop.AudioRestrictionManager.Restriction> sparseArray = this.mZenModeAudioRestrictions.get(i);
                if (sparseArray == null) {
                    sparseArray = new android.util.SparseArray<>();
                    this.mZenModeAudioRestrictions.put(i, sparseArray);
                }
                sparseArray.remove(i2);
                if (i4 != 0) {
                    com.android.server.appop.AudioRestrictionManager.Restriction restriction = new com.android.server.appop.AudioRestrictionManager.Restriction();
                    restriction.mode = i4;
                    if (strArr != null) {
                        restriction.exceptionPackages = new android.util.ArraySet<>(strArr.length);
                        for (java.lang.String str : strArr) {
                            if (str != null) {
                                restriction.exceptionPackages.add(str.trim());
                            }
                        }
                    }
                    sparseArray.put(i2, restriction);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void setCameraAudioRestriction(int i) {
        synchronized (this) {
            this.mCameraAudioRestriction = i;
        }
    }

    public boolean hasActiveRestrictions() {
        boolean z;
        synchronized (this) {
            try {
                z = this.mZenModeAudioRestrictions.size() > 0 || this.mCameraAudioRestriction != 0;
            } finally {
            }
        }
        return z;
    }

    public boolean dump(java.io.PrintWriter printWriter) {
        boolean hasActiveRestrictions = hasActiveRestrictions();
        synchronized (this) {
            boolean z = false;
            for (int i = 0; i < this.mZenModeAudioRestrictions.size(); i++) {
                try {
                    java.lang.String opToName = android.app.AppOpsManager.opToName(this.mZenModeAudioRestrictions.keyAt(i));
                    android.util.SparseArray<com.android.server.appop.AudioRestrictionManager.Restriction> valueAt = this.mZenModeAudioRestrictions.valueAt(i);
                    for (int i2 = 0; i2 < valueAt.size(); i2++) {
                        if (!z) {
                            printWriter.println("  Zen Mode Audio Restrictions:");
                            z = true;
                        }
                        int keyAt = valueAt.keyAt(i2);
                        printWriter.print("    ");
                        printWriter.print(opToName);
                        printWriter.print(" usage=");
                        printWriter.print(android.media.AudioAttributes.usageToString(keyAt));
                        com.android.server.appop.AudioRestrictionManager.Restriction valueAt2 = valueAt.valueAt(i2);
                        printWriter.print(": mode=");
                        printWriter.println(android.app.AppOpsManager.modeToName(valueAt2.mode));
                        if (!valueAt2.exceptionPackages.isEmpty()) {
                            printWriter.println("      Exceptions:");
                            for (int i3 = 0; i3 < valueAt2.exceptionPackages.size(); i3++) {
                                printWriter.print("        ");
                                printWriter.println(valueAt2.exceptionPackages.valueAt(i3));
                            }
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            if (this.mCameraAudioRestriction != 0) {
                printWriter.println("  Camera Audio Restriction Mode: " + cameraRestrictionModeToName(this.mCameraAudioRestriction));
            }
        }
        return hasActiveRestrictions;
    }

    private static java.lang.String cameraRestrictionModeToName(int i) {
        switch (i) {
            case 0:
                return com.android.server.input.KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG;
            case 1:
                return "MuteVibration";
            case 2:
            default:
                return "Unknown";
            case 3:
                return "MuteVibrationAndSound";
        }
    }
}
