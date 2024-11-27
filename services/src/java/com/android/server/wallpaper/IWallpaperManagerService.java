package com.android.server.wallpaper;

/* loaded from: classes.dex */
interface IWallpaperManagerService extends android.app.IWallpaperManager, android.os.IBinder {
    void onBootPhase(int i);

    void onUnlockUser(int i);
}
