package com.android.server.wm;

/* loaded from: classes3.dex */
class WallpaperVisibilityListeners {
    private final android.util.SparseArray<android.os.RemoteCallbackList<android.view.IWallpaperVisibilityListener>> mDisplayListeners = new android.util.SparseArray<>();

    WallpaperVisibilityListeners() {
    }

    void registerWallpaperVisibilityListener(android.view.IWallpaperVisibilityListener iWallpaperVisibilityListener, int i) {
        android.os.RemoteCallbackList<android.view.IWallpaperVisibilityListener> remoteCallbackList = this.mDisplayListeners.get(i);
        if (remoteCallbackList == null) {
            remoteCallbackList = new android.os.RemoteCallbackList<>();
            this.mDisplayListeners.append(i, remoteCallbackList);
        }
        remoteCallbackList.register(iWallpaperVisibilityListener);
    }

    void unregisterWallpaperVisibilityListener(android.view.IWallpaperVisibilityListener iWallpaperVisibilityListener, int i) {
        android.os.RemoteCallbackList<android.view.IWallpaperVisibilityListener> remoteCallbackList = this.mDisplayListeners.get(i);
        if (remoteCallbackList == null) {
            return;
        }
        remoteCallbackList.unregister(iWallpaperVisibilityListener);
    }

    void notifyWallpaperVisibilityChanged(com.android.server.wm.DisplayContent displayContent) {
        int displayId = displayContent.getDisplayId();
        boolean isWallpaperVisible = displayContent.mWallpaperController.isWallpaperVisible();
        android.os.RemoteCallbackList<android.view.IWallpaperVisibilityListener> remoteCallbackList = this.mDisplayListeners.get(displayId);
        if (remoteCallbackList == null) {
            return;
        }
        int beginBroadcast = remoteCallbackList.beginBroadcast();
        while (beginBroadcast > 0) {
            beginBroadcast--;
            try {
                remoteCallbackList.getBroadcastItem(beginBroadcast).onWallpaperVisibilityChanged(isWallpaperVisible, displayId);
            } catch (android.os.RemoteException e) {
            }
        }
        remoteCallbackList.finishBroadcast();
    }
}
