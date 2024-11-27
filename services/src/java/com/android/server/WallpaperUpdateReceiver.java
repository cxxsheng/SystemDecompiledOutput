package com.android.server;

/* loaded from: classes.dex */
public class WallpaperUpdateReceiver extends android.content.BroadcastReceiver {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "WallpaperUpdateReceiver";

    @Override // android.content.BroadcastReceiver
    public void onReceive(android.content.Context context, android.content.Intent intent) {
        if (intent != null && "android.intent.action.DEVICE_CUSTOMIZATION_READY".equals(intent.getAction())) {
            android.os.AsyncTask.execute(new java.lang.Runnable() { // from class: com.android.server.WallpaperUpdateReceiver$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.WallpaperUpdateReceiver.this.updateWallpaper();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateWallpaper() {
        try {
            android.app.ContextImpl systemUiContext = android.app.ActivityThread.currentActivityThread().getSystemUiContext();
            android.app.WallpaperManager wallpaperManager = android.app.WallpaperManager.getInstance(systemUiContext);
            if (isUserSetWallpaper(wallpaperManager, systemUiContext)) {
                android.util.Slog.i(TAG, "User has set wallpaper, skip to resetting");
            } else {
                wallpaperManager.setBitmap(android.graphics.Bitmap.createBitmap(1, 1, android.graphics.Bitmap.Config.ALPHA_8));
                wallpaperManager.setResource(android.R.drawable.default_lock_wallpaper);
            }
        } catch (java.lang.Exception e) {
            android.util.Slog.w(TAG, "Failed to customize system wallpaper." + e);
        }
    }

    @android.annotation.RequiresPermission("android.permission.READ_WALLPAPER_INTERNAL")
    private boolean isUserSetWallpaper(android.app.WallpaperManager wallpaperManager, android.content.Context context) {
        android.app.WallpaperInfo wallpaperInfo = wallpaperManager.getWallpaperInfo();
        if (wallpaperInfo == null) {
            android.os.ParcelFileDescriptor wallpaperFile = wallpaperManager.getWallpaperFile(1);
            android.os.ParcelFileDescriptor wallpaperFile2 = wallpaperManager.getWallpaperFile(2);
            if (wallpaperFile != null || wallpaperFile2 != null) {
                return true;
            }
            return false;
        }
        if (!wallpaperInfo.getComponent().equals(android.app.WallpaperManager.getCmfDefaultWallpaperComponent(context))) {
            return true;
        }
        return false;
    }
}
