package com.android.server.wallpaper;

/* loaded from: classes.dex */
class WallpaperDisplayHelper {
    private static final java.lang.String TAG = com.android.server.wallpaper.WallpaperDisplayHelper.class.getSimpleName();
    private final android.hardware.display.DisplayManager mDisplayManager;
    private boolean mIsFoldable;
    private final com.android.server.wm.WindowManagerInternal mWindowManagerInternal;
    private final android.util.SparseArray<com.android.server.wallpaper.WallpaperDisplayHelper.DisplayData> mDisplayDatas = new android.util.SparseArray<>();
    private final android.util.SparseArray<android.graphics.Point> mDefaultDisplaySizes = new android.util.SparseArray<>();
    private final java.util.List<android.util.Pair<java.lang.Integer, java.lang.Integer>> mFoldableOrientationPairs = new java.util.ArrayList();

    static final class DisplayData {
        final int mDisplayId;
        int mWidth = -1;
        int mHeight = -1;
        final android.graphics.Rect mPadding = new android.graphics.Rect(0, 0, 0, 0);

        DisplayData(int i) {
            this.mDisplayId = i;
        }
    }

    WallpaperDisplayHelper(android.hardware.display.DisplayManager displayManager, android.view.WindowManager windowManager, com.android.server.wm.WindowManagerInternal windowManagerInternal, boolean z) {
        android.util.Pair<java.lang.Integer, java.lang.Integer> pair;
        this.mDisplayManager = displayManager;
        this.mWindowManagerInternal = windowManagerInternal;
        this.mIsFoldable = z;
        if (com.android.window.flags.Flags.multiCrop()) {
            boolean z2 = false;
            java.util.Set<android.view.WindowMetrics> possibleMaximumWindowMetrics = windowManager.getPossibleMaximumWindowMetrics(0);
            if (z && possibleMaximumWindowMetrics.size() == 2) {
                z2 = true;
            }
            int i = -1;
            float f = 0.0f;
            for (android.view.WindowMetrics windowMetrics : possibleMaximumWindowMetrics) {
                android.graphics.Rect bounds = windowMetrics.getBounds();
                android.graphics.Point point = new android.graphics.Point(bounds.width(), bounds.height());
                for (android.graphics.Point point2 : java.util.List.of(point, new android.graphics.Point(point.y, point.x))) {
                    int orientation = android.app.WallpaperManager.getOrientation(point2);
                    android.graphics.Point point3 = this.mDefaultDisplaySizes.get(orientation);
                    if (point3 == null || point3.x * point3.y < point2.x * point2.y) {
                        this.mDefaultDisplaySizes.put(orientation, point2);
                    }
                }
                if (z2) {
                    int orientation2 = android.app.WallpaperManager.getOrientation(point);
                    float density = (point.x * point.y) / (windowMetrics.getDensity() * windowMetrics.getDensity());
                    if (f <= com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                        i = orientation2;
                        f = density;
                    } else {
                        if (density > f) {
                            pair = new android.util.Pair<>(java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(orientation2));
                        } else {
                            pair = new android.util.Pair<>(java.lang.Integer.valueOf(orientation2), java.lang.Integer.valueOf(i));
                        }
                        android.util.Pair<java.lang.Integer, java.lang.Integer> pair2 = new android.util.Pair<>(java.lang.Integer.valueOf(android.app.WallpaperManager.getRotatedOrientation(((java.lang.Integer) pair.first).intValue())), java.lang.Integer.valueOf(android.app.WallpaperManager.getRotatedOrientation(((java.lang.Integer) pair.second).intValue())));
                        this.mFoldableOrientationPairs.add(pair);
                        this.mFoldableOrientationPairs.add(pair2);
                    }
                }
            }
        }
    }

    com.android.server.wallpaper.WallpaperDisplayHelper.DisplayData getDisplayDataOrCreate(int i) {
        com.android.server.wallpaper.WallpaperDisplayHelper.DisplayData displayData = this.mDisplayDatas.get(i);
        if (displayData == null) {
            com.android.server.wallpaper.WallpaperDisplayHelper.DisplayData displayData2 = new com.android.server.wallpaper.WallpaperDisplayHelper.DisplayData(i);
            ensureSaneWallpaperDisplaySize(displayData2, i);
            this.mDisplayDatas.append(i, displayData2);
            return displayData2;
        }
        return displayData;
    }

    int getDefaultDisplayCurrentOrientation() {
        android.graphics.Point point = new android.graphics.Point();
        this.mDisplayManager.getDisplay(0).getSize(point);
        return android.app.WallpaperManager.getOrientation(point);
    }

    void removeDisplayData(int i) {
        this.mDisplayDatas.remove(i);
    }

    void ensureSaneWallpaperDisplaySize(com.android.server.wallpaper.WallpaperDisplayHelper.DisplayData displayData, int i) {
        int maximumSizeDimension = getMaximumSizeDimension(i);
        if (displayData.mWidth < maximumSizeDimension) {
            displayData.mWidth = maximumSizeDimension;
        }
        if (displayData.mHeight < maximumSizeDimension) {
            displayData.mHeight = maximumSizeDimension;
        }
    }

    int getMaximumSizeDimension(int i) {
        android.view.Display display = this.mDisplayManager.getDisplay(i);
        if (display == null) {
            android.util.Slog.w(TAG, "Invalid displayId=" + i + " " + android.os.Debug.getCallers(4));
            display = this.mDisplayManager.getDisplay(0);
        }
        return display.getMaximumSizeDimension();
    }

    void forEachDisplayData(java.util.function.Consumer<com.android.server.wallpaper.WallpaperDisplayHelper.DisplayData> consumer) {
        for (int size = this.mDisplayDatas.size() - 1; size >= 0; size--) {
            consumer.accept(this.mDisplayDatas.valueAt(size));
        }
    }

    android.view.Display[] getDisplays() {
        return this.mDisplayManager.getDisplays();
    }

    android.view.DisplayInfo getDisplayInfo(int i) {
        android.view.DisplayInfo displayInfo = new android.view.DisplayInfo();
        this.mDisplayManager.getDisplay(i).getDisplayInfo(displayInfo);
        return displayInfo;
    }

    boolean isUsableDisplay(int i, int i2) {
        return isUsableDisplay(this.mDisplayManager.getDisplay(i), i2);
    }

    boolean isUsableDisplay(android.view.Display display, int i) {
        if (display == null || !display.hasAccess(i)) {
            return false;
        }
        int displayId = display.getDisplayId();
        if (displayId == 0) {
            return true;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return this.mWindowManagerInternal.isHomeSupportedOnDisplay(displayId);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    boolean isValidDisplay(int i) {
        return this.mDisplayManager.getDisplay(i) != null;
    }

    android.util.SparseArray<android.graphics.Point> getDefaultDisplaySizes() {
        return this.mDefaultDisplaySizes;
    }

    int getDefaultDisplayLargestDimension() {
        int i = -1;
        for (int i2 = 0; i2 < this.mDefaultDisplaySizes.size(); i2++) {
            android.graphics.Point valueAt = this.mDefaultDisplaySizes.valueAt(i2);
            i = java.lang.Math.max(i, java.lang.Math.max(valueAt.x, valueAt.y));
        }
        return i;
    }

    boolean isFoldable() {
        return this.mIsFoldable;
    }

    int getFoldedOrientation(int i) {
        for (android.util.Pair<java.lang.Integer, java.lang.Integer> pair : this.mFoldableOrientationPairs) {
            if (((java.lang.Integer) pair.second).equals(java.lang.Integer.valueOf(i))) {
                return ((java.lang.Integer) pair.first).intValue();
            }
        }
        return -1;
    }

    int getUnfoldedOrientation(int i) {
        for (android.util.Pair<java.lang.Integer, java.lang.Integer> pair : this.mFoldableOrientationPairs) {
            if (((java.lang.Integer) pair.first).equals(java.lang.Integer.valueOf(i))) {
                return ((java.lang.Integer) pair.second).intValue();
            }
        }
        return -1;
    }
}
