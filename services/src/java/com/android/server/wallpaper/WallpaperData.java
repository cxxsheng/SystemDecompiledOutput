package com.android.server.wallpaper;

/* loaded from: classes.dex */
class WallpaperData {
    boolean allowBackup;
    android.os.RemoteCallbackList<android.app.IWallpaperManagerCallback> callbacks;
    com.android.server.wallpaper.WallpaperManagerService.WallpaperConnection connection;
    final android.graphics.Rect cropHint;
    public boolean fromForegroundApp;
    boolean imageWallpaperPending;
    long lastDiedTime;
    com.android.server.wallpaper.WallpaperData.BindSource mBindSource;
    private final android.util.SparseArray<java.io.File> mCropFiles;
    android.util.SparseArray<android.graphics.Rect> mCropHints;
    boolean mIsColorExtractedFromDim;
    int mOrientationWhenSet;
    float mSampleSize;
    boolean mSupportsMultiCrop;
    boolean mSystemWasBoth;
    android.util.SparseArray<java.lang.Float> mUidToDimAmount;
    float mWallpaperDimAmount;
    private final android.util.SparseArray<java.io.File> mWallpaperFiles;
    int mWhich;
    java.lang.String name;
    android.content.ComponentName nextWallpaperComponent;
    android.app.WallpaperColors primaryColors;
    android.app.IWallpaperManagerCallback setComplete;
    final int userId;
    android.content.ComponentName wallpaperComponent;
    int wallpaperId;
    com.android.server.wallpaper.WallpaperManagerService.WallpaperObserver wallpaperObserver;
    boolean wallpaperUpdating;

    enum BindSource {
        UNKNOWN,
        CONNECT_LOCKED,
        CONNECTION_TRY_TO_REBIND,
        INITIALIZE_FALLBACK,
        PACKAGE_UPDATE_FINISHED,
        RESTORE_SETTINGS_LIVE_FAILURE,
        RESTORE_SETTINGS_LIVE_SUCCESS,
        RESTORE_SETTINGS_STATIC,
        SET_LIVE,
        SET_LIVE_TO_CLEAR,
        SET_STATIC,
        SWITCH_WALLPAPER_FAILURE,
        SWITCH_WALLPAPER_SWITCH_USER,
        SWITCH_WALLPAPER_UNLOCK_USER
    }

    WallpaperData(int i, int i2) {
        this.name = "";
        this.mWallpaperDimAmount = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
        this.mUidToDimAmount = new android.util.SparseArray<>();
        this.callbacks = new android.os.RemoteCallbackList<>();
        this.cropHint = new android.graphics.Rect(0, 0, 0, 0);
        this.mSampleSize = 1.0f;
        this.mBindSource = com.android.server.wallpaper.WallpaperData.BindSource.UNKNOWN;
        this.mWallpaperFiles = new android.util.SparseArray<>();
        this.mCropFiles = new android.util.SparseArray<>();
        this.mCropHints = new android.util.SparseArray<>();
        this.mOrientationWhenSet = -1;
        this.userId = i;
        this.mWhich = i2;
    }

    WallpaperData(com.android.server.wallpaper.WallpaperData wallpaperData) {
        this.name = "";
        this.mWallpaperDimAmount = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
        this.mUidToDimAmount = new android.util.SparseArray<>();
        this.callbacks = new android.os.RemoteCallbackList<>();
        this.cropHint = new android.graphics.Rect(0, 0, 0, 0);
        this.mSampleSize = 1.0f;
        this.mBindSource = com.android.server.wallpaper.WallpaperData.BindSource.UNKNOWN;
        this.mWallpaperFiles = new android.util.SparseArray<>();
        this.mCropFiles = new android.util.SparseArray<>();
        this.mCropHints = new android.util.SparseArray<>();
        this.mOrientationWhenSet = -1;
        this.userId = wallpaperData.userId;
        this.wallpaperComponent = wallpaperData.wallpaperComponent;
        this.mWhich = wallpaperData.mWhich;
        this.wallpaperId = wallpaperData.wallpaperId;
        this.cropHint.set(wallpaperData.cropHint);
        if (wallpaperData.mCropHints != null) {
            this.mCropHints = wallpaperData.mCropHints.clone();
        }
        this.mSupportsMultiCrop = wallpaperData.mSupportsMultiCrop;
        this.allowBackup = wallpaperData.allowBackup;
        this.primaryColors = wallpaperData.primaryColors;
        this.mWallpaperDimAmount = wallpaperData.mWallpaperDimAmount;
        this.connection = wallpaperData.connection;
        if (this.connection != null) {
            this.connection.mWallpaper = this;
        }
    }

    java.io.File getWallpaperFile() {
        return getFile(this.mWallpaperFiles, this.mWhich == 2 ? "wallpaper_lock_orig" : "wallpaper_orig");
    }

    java.io.File getCropFile() {
        return getFile(this.mCropFiles, this.mWhich == 2 ? "wallpaper_lock" : "wallpaper");
    }

    private java.io.File getFile(android.util.SparseArray<java.io.File> sparseArray, java.lang.String str) {
        java.io.File file = sparseArray.get(this.mWhich);
        if (file == null) {
            java.io.File file2 = new java.io.File(com.android.server.wallpaper.WallpaperUtils.getWallpaperDir(this.userId), str);
            sparseArray.put(this.userId, file2);
            return file2;
        }
        return file;
    }

    public java.lang.String toString() {
        final java.lang.StringBuilder sb = new java.lang.StringBuilder(defaultString(this));
        sb.append(", id: ");
        sb.append(this.wallpaperId);
        sb.append(", which: ");
        sb.append(this.mWhich);
        sb.append(", file mod: ");
        sb.append(getWallpaperFile() != null ? java.lang.Long.valueOf(getWallpaperFile().lastModified()) : "null");
        if (this.connection == null) {
            sb.append(", no connection");
        } else {
            sb.append(", info: ");
            sb.append(this.connection.mInfo);
            sb.append(", engine(s):");
            this.connection.forEachDisplayConnector(new java.util.function.Consumer() { // from class: com.android.server.wallpaper.WallpaperData$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.wallpaper.WallpaperData.lambda$toString$0(sb, (com.android.server.wallpaper.WallpaperManagerService.DisplayConnector) obj);
                }
            });
        }
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$toString$0(java.lang.StringBuilder sb, com.android.server.wallpaper.WallpaperManagerService.DisplayConnector displayConnector) {
        if (displayConnector.mEngine != null) {
            sb.append(" ");
            sb.append(defaultString(displayConnector.mEngine));
        } else {
            sb.append(" null");
        }
    }

    private static java.lang.String defaultString(java.lang.Object obj) {
        return obj.getClass().getSimpleName() + "@" + java.lang.Integer.toHexString(obj.hashCode());
    }

    boolean cropExists() {
        return getCropFile().exists();
    }

    boolean sourceExists() {
        return getWallpaperFile().exists();
    }
}
