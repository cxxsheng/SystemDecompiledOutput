package com.android.server.wallpaper;

/* loaded from: classes.dex */
class WallpaperUtils {
    static final java.lang.String RECORD_FILE = "decode_record";
    static final java.lang.String RECORD_LOCK_FILE = "decode_lock_record";
    private static int sWallpaperId;
    static final java.lang.String WALLPAPER = "wallpaper_orig";
    static final java.lang.String WALLPAPER_CROP = "wallpaper";
    static final java.lang.String WALLPAPER_LOCK_ORIG = "wallpaper_lock_orig";
    static final java.lang.String WALLPAPER_LOCK_CROP = "wallpaper_lock";
    static final java.lang.String WALLPAPER_INFO = "wallpaper_info.xml";
    private static final java.lang.String[] sPerUserFiles = {WALLPAPER, WALLPAPER_CROP, WALLPAPER_LOCK_ORIG, WALLPAPER_LOCK_CROP, WALLPAPER_INFO};

    WallpaperUtils() {
    }

    static java.io.File getWallpaperDir(int i) {
        return android.os.Environment.getUserSystemDirectory(i);
    }

    static int makeWallpaperIdLocked() {
        do {
            sWallpaperId++;
        } while (sWallpaperId == 0);
        return sWallpaperId;
    }

    static int getCurrentWallpaperId() {
        return sWallpaperId;
    }

    static void setCurrentWallpaperId(int i) {
        sWallpaperId = i;
    }

    static java.util.List<java.io.File> getWallpaperFiles(int i) {
        java.io.File wallpaperDir = getWallpaperDir(i);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i2 = 0; i2 < sPerUserFiles.length; i2++) {
            arrayList.add(new java.io.File(wallpaperDir, sPerUserFiles[i2]));
        }
        return arrayList;
    }
}
