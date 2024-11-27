package com.android.server.wm;

/* loaded from: classes3.dex */
class AppSnapshotLoader {
    private static final java.lang.String TAG = "WindowManager";
    private final com.android.server.wm.BaseAppSnapshotPersister.PersistInfoProvider mPersistInfoProvider;

    AppSnapshotLoader(com.android.server.wm.BaseAppSnapshotPersister.PersistInfoProvider persistInfoProvider) {
        this.mPersistInfoProvider = persistInfoProvider;
    }

    static class PreRLegacySnapshotConfig {
        final boolean mForceLoadReducedJpeg;
        final float mScale;

        PreRLegacySnapshotConfig(float f, boolean z) {
            this.mScale = f;
            this.mForceLoadReducedJpeg = z;
        }
    }

    com.android.server.wm.AppSnapshotLoader.PreRLegacySnapshotConfig getLegacySnapshotConfig(int i, float f, boolean z, boolean z2) {
        boolean z3 = true;
        boolean z4 = i == 0;
        if (!z4) {
            return null;
        }
        if (z4 && java.lang.Float.compare(f, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) == 0) {
            if (android.app.ActivityManager.isLowRamDeviceStatic() && !z) {
                f = 0.6f;
            } else {
                f = z2 ? 0.5f : 1.0f;
                z3 = false;
            }
        } else if (!z4) {
            z3 = false;
            f = 0.0f;
        } else if (!android.app.ActivityManager.isLowRamDeviceStatic()) {
            if (z2) {
                f *= 0.5f;
            }
            z3 = false;
        }
        return new com.android.server.wm.AppSnapshotLoader.PreRLegacySnapshotConfig(f, z3);
    }

    android.window.TaskSnapshot loadTask(int i, int i2, boolean z) {
        java.lang.String str;
        android.graphics.Point point;
        android.graphics.Rect rect;
        android.graphics.Rect rect2;
        boolean z2;
        int i3;
        int i4;
        boolean z3;
        java.io.File protoFile = this.mPersistInfoProvider.getProtoFile(i, i2);
        if (!protoFile.exists()) {
            return null;
        }
        try {
            com.android.server.wm.nano.WindowManagerProtos.TaskSnapshotProto parseFrom = com.android.server.wm.nano.WindowManagerProtos.TaskSnapshotProto.parseFrom(java.nio.file.Files.readAllBytes(protoFile.toPath()));
            java.io.File highResolutionBitmapFile = this.mPersistInfoProvider.getHighResolutionBitmapFile(i, i2);
            com.android.server.wm.AppSnapshotLoader.PreRLegacySnapshotConfig legacySnapshotConfig = getLegacySnapshotConfig(parseFrom.taskWidth, parseFrom.legacyScale, highResolutionBitmapFile.exists(), z);
            boolean z4 = legacySnapshotConfig != null && legacySnapshotConfig.mForceLoadReducedJpeg;
            if (z || z4) {
                highResolutionBitmapFile = this.mPersistInfoProvider.getLowResolutionBitmapFile(i, i2);
            }
            if (!highResolutionBitmapFile.exists()) {
                return null;
            }
            android.graphics.BitmapFactory.Options options = new android.graphics.BitmapFactory.Options();
            options.inPreferredConfig = (!this.mPersistInfoProvider.use16BitFormat() || parseFrom.isTranslucent) ? android.graphics.Bitmap.Config.ARGB_8888 : android.graphics.Bitmap.Config.RGB_565;
            android.graphics.Bitmap decodeFile = android.graphics.BitmapFactory.decodeFile(highResolutionBitmapFile.getPath(), options);
            if (decodeFile != null) {
                android.graphics.Bitmap copy = decodeFile.copy(android.graphics.Bitmap.Config.HARDWARE, false);
                decodeFile.recycle();
                if (copy == null) {
                    android.util.Slog.w(TAG, "Failed to create hardware bitmap: " + highResolutionBitmapFile.getPath());
                    return null;
                }
                android.hardware.HardwareBuffer hardwareBuffer = copy.getHardwareBuffer();
                if (hardwareBuffer == null) {
                    android.util.Slog.w(TAG, "Failed to retrieve gralloc buffer for bitmap: " + highResolutionBitmapFile.getPath());
                    return null;
                }
                android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(parseFrom.topActivityComponent);
                if (legacySnapshotConfig != null) {
                    point = new android.graphics.Point((int) (copy.getWidth() / legacySnapshotConfig.mScale), (int) (copy.getHeight() / legacySnapshotConfig.mScale));
                } else {
                    point = new android.graphics.Point(parseFrom.taskWidth, parseFrom.taskHeight);
                }
                long j = parseFrom.id;
                long elapsedRealtimeNanos = android.os.SystemClock.elapsedRealtimeNanos();
                android.graphics.ColorSpace colorSpace = copy.getColorSpace();
                int i5 = parseFrom.orientation;
                int i6 = parseFrom.rotation;
                try {
                    rect = new android.graphics.Rect(parseFrom.insetLeft, parseFrom.insetTop, parseFrom.insetRight, parseFrom.insetBottom);
                    rect2 = new android.graphics.Rect(parseFrom.letterboxInsetLeft, parseFrom.letterboxInsetTop, parseFrom.letterboxInsetRight, parseFrom.letterboxInsetBottom);
                    z2 = parseFrom.isRealSnapshot;
                    i3 = parseFrom.windowingMode;
                    i4 = parseFrom.appearance;
                    z3 = parseFrom.isTranslucent;
                    str = TAG;
                } catch (java.io.IOException e) {
                    str = TAG;
                }
                try {
                    return new android.window.TaskSnapshot(j, elapsedRealtimeNanos, unflattenFromString, hardwareBuffer, colorSpace, i5, i6, point, rect, rect2, z, z2, i3, i4, z3, false);
                } catch (java.io.IOException e2) {
                    android.util.Slog.w(str, "Unable to load task snapshot data for Id=" + i);
                    return null;
                }
            }
            android.util.Slog.w(TAG, "Failed to load bitmap: " + highResolutionBitmapFile.getPath());
            return null;
        } catch (java.io.IOException e3) {
            str = TAG;
        }
    }
}
