package com.android.server.wallpaper;

/* loaded from: classes.dex */
public class WallpaperManagerService extends android.app.IWallpaperManager.Stub implements com.android.server.wallpaper.IWallpaperManagerService {
    private static final boolean DEBUG = false;
    private static final boolean DEBUG_LIVE = true;
    private static final double LMK_LOW_THRESHOLD_MEMORY_PERCENTAGE = 10.0d;
    private static final long LMK_RECONNECT_DELAY_MS = 5000;
    private static final int LMK_RECONNECT_REBIND_RETRIES = 3;
    private static final int MAX_WALLPAPER_COMPONENT_LOG_LENGTH = 128;
    private static final long MIN_WALLPAPER_CRASH_TIME = 10000;
    private static final java.lang.String TAG = "WallpaperManagerService";
    private final android.app.ActivityManager mActivityManager;
    private final android.app.AppOpsManager mAppOpsManager;
    private android.app.WallpaperColors mCacheDefaultImageWallpaperColors;
    private final android.util.SparseArray<android.util.SparseArray<android.os.RemoteCallbackList<android.app.IWallpaperManagerCallback>>> mColorsChangedListeners;
    private final android.content.Context mContext;
    private final android.content.ComponentName mDefaultWallpaperComponent;
    protected com.android.server.wallpaper.WallpaperData mFallbackWallpaper;
    private com.android.server.ServiceThread mHandlerThread;
    private boolean mHomeWallpaperWaitingForUnlock;
    private final android.content.pm.IPackageManager mIPackageManager;
    private final android.content.ComponentName mImageWallpaper;
    private boolean mInAmbientMode;
    protected com.android.server.wallpaper.WallpaperData mLastLockWallpaper;
    protected com.android.server.wallpaper.WallpaperData mLastWallpaper;
    private boolean mLockWallpaperWaitingForUnlock;
    private final com.android.server.wallpaper.WallpaperManagerService.MyPackageMonitor mMonitor;
    private final android.content.pm.PackageManagerInternal mPackageManagerInternal;
    com.android.server.wallpaper.WallpaperManagerService.WallpaperDestinationChangeHandler mPendingMigrationViaStatic;
    final com.android.server.wallpaper.WallpaperCropper mWallpaperCropper;

    @com.android.internal.annotations.VisibleForTesting
    final com.android.server.wallpaper.WallpaperDataParser mWallpaperDataParser;

    @com.android.internal.annotations.VisibleForTesting
    final com.android.server.wallpaper.WallpaperDisplayHelper mWallpaperDisplayHelper;
    private final com.android.server.wm.WindowManagerInternal mWindowManagerInternal;

    @android.annotation.NonNull
    private static final android.graphics.RectF LOCAL_COLOR_BOUNDS = new android.graphics.RectF(com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, 1.0f, 1.0f);
    private static final java.util.Map<java.lang.Integer, java.lang.String> sWallpaperType = java.util.Map.of(1, "decode_record", 2, "decode_lock_record");
    private final java.lang.Object mLock = new java.lang.Object();
    private boolean mInitialUserSwitch = true;
    private final android.hardware.display.DisplayManager.DisplayListener mDisplayListener = new android.hardware.display.DisplayManager.DisplayListener() { // from class: com.android.server.wallpaper.WallpaperManagerService.1
        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayAdded(int i) {
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayRemoved(int i) {
            com.android.server.wallpaper.WallpaperData wallpaperData;
            synchronized (com.android.server.wallpaper.WallpaperManagerService.this.mLock) {
                try {
                    if (com.android.server.wallpaper.WallpaperManagerService.this.mLastWallpaper != null) {
                        if (com.android.server.wallpaper.WallpaperManagerService.this.mLastWallpaper.connection.containsDisplay(i)) {
                            wallpaperData = com.android.server.wallpaper.WallpaperManagerService.this.mLastWallpaper;
                        } else if (!com.android.server.wallpaper.WallpaperManagerService.this.mFallbackWallpaper.connection.containsDisplay(i)) {
                            wallpaperData = null;
                        } else {
                            wallpaperData = com.android.server.wallpaper.WallpaperManagerService.this.mFallbackWallpaper;
                        }
                        if (wallpaperData == null) {
                            return;
                        }
                        com.android.server.wallpaper.WallpaperManagerService.DisplayConnector displayConnectorOrCreate = wallpaperData.connection.getDisplayConnectorOrCreate(i);
                        if (displayConnectorOrCreate == null) {
                            return;
                        }
                        displayConnectorOrCreate.disconnectLocked(wallpaperData.connection);
                        wallpaperData.connection.removeDisplayConnector(i);
                        com.android.server.wallpaper.WallpaperManagerService.this.mWallpaperDisplayHelper.removeDisplayData(i);
                    }
                    for (int size = com.android.server.wallpaper.WallpaperManagerService.this.mColorsChangedListeners.size() - 1; size >= 0; size--) {
                        ((android.util.SparseArray) com.android.server.wallpaper.WallpaperManagerService.this.mColorsChangedListeners.valueAt(size)).delete(i);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayChanged(int i) {
        }
    };
    private final android.util.SparseArray<com.android.server.wallpaper.WallpaperData> mWallpaperMap = new android.util.SparseArray<>();
    private final android.util.SparseArray<com.android.server.wallpaper.WallpaperData> mLockWallpaperMap = new android.util.SparseArray<>();
    private final android.util.SparseBooleanArray mUserRestorecon = new android.util.SparseBooleanArray();
    private int mCurrentUserId = com.android.server.am.ProcessList.INVALID_ADJ;
    private com.android.server.wallpaper.LocalColorRepository mLocalColorRepo = new com.android.server.wallpaper.LocalColorRepository();
    private boolean mShuttingDown = false;

    public static class Lifecycle extends com.android.server.SystemService {
        private com.android.server.wallpaper.IWallpaperManagerService mService;

        public Lifecycle(android.content.Context context) {
            super(context);
        }

        @Override // com.android.server.SystemService
        public void onStart() {
            try {
                this.mService = (com.android.server.wallpaper.IWallpaperManagerService) java.lang.Class.forName(getContext().getResources().getString(android.R.string.config_systemTelevisionRemoteService)).getConstructor(android.content.Context.class).newInstance(getContext());
                publishBinderService("wallpaper", this.mService);
            } catch (java.lang.Exception e) {
                android.util.Slog.wtf(com.android.server.wallpaper.WallpaperManagerService.TAG, "Failed to instantiate WallpaperManagerService", e);
            }
        }

        @Override // com.android.server.SystemService
        public void onBootPhase(int i) {
            if (this.mService != null) {
                this.mService.onBootPhase(i);
            }
        }

        @Override // com.android.server.SystemService
        public void onUserUnlocking(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
            if (this.mService != null) {
                this.mService.onUnlockUser(targetUser.getUserIdentifier());
            }
        }
    }

    class WallpaperObserver extends android.os.FileObserver {
        final int mUserId;
        final com.android.server.wallpaper.WallpaperData mWallpaper;
        final java.io.File mWallpaperDir;
        final java.io.File mWallpaperFile;
        final java.io.File mWallpaperLockFile;

        public WallpaperObserver(com.android.server.wallpaper.WallpaperData wallpaperData) {
            super(com.android.server.wallpaper.WallpaperUtils.getWallpaperDir(wallpaperData.userId).getAbsolutePath(), 1672);
            this.mUserId = wallpaperData.userId;
            this.mWallpaperDir = com.android.server.wallpaper.WallpaperUtils.getWallpaperDir(wallpaperData.userId);
            this.mWallpaper = wallpaperData;
            this.mWallpaperFile = new java.io.File(this.mWallpaperDir, "wallpaper_orig");
            this.mWallpaperLockFile = new java.io.File(this.mWallpaperDir, "wallpaper_lock_orig");
        }

        com.android.server.wallpaper.WallpaperData dataForEvent(boolean z) {
            com.android.server.wallpaper.WallpaperData wallpaperData;
            synchronized (com.android.server.wallpaper.WallpaperManagerService.this.mLock) {
                if (!z) {
                    wallpaperData = null;
                } else {
                    try {
                        wallpaperData = (com.android.server.wallpaper.WallpaperData) com.android.server.wallpaper.WallpaperManagerService.this.mLockWallpaperMap.get(this.mUserId);
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                if (wallpaperData == null) {
                    wallpaperData = (com.android.server.wallpaper.WallpaperData) com.android.server.wallpaper.WallpaperManagerService.this.mWallpaperMap.get(this.mUserId);
                }
            }
            return wallpaperData != null ? wallpaperData : this.mWallpaper;
        }

        /* JADX WARN: Removed duplicated region for block: B:46:0x00f3 A[Catch: all -> 0x0080, TryCatch #0 {all -> 0x0080, blocks: (B:32:0x005e, B:35:0x0069, B:37:0x0077, B:38:0x0083, B:40:0x008e, B:42:0x00a7, B:44:0x00ea, B:46:0x00f3, B:47:0x00f6, B:51:0x00c1, B:53:0x00fd), top: B:31:0x005e }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private void updateWallpapers(int i, java.lang.String str) {
            java.io.File file = new java.io.File(this.mWallpaperDir, str);
            boolean equals = this.mWallpaperFile.equals(file);
            boolean equals2 = this.mWallpaperLockFile.equals(file);
            final com.android.server.wallpaper.WallpaperData dataForEvent = dataForEvent(equals2);
            boolean z = i == 128;
            boolean z2 = i == 8 || z;
            boolean z3 = z && equals2;
            boolean z4 = z && !z3;
            boolean z5 = (dataForEvent.mWhich & 2) != 0;
            boolean z6 = dataForEvent.wallpaperComponent == null || i != 8 || dataForEvent.imageWallpaperPending;
            if (z3) {
                return;
            }
            if (!equals && !equals2) {
                return;
            }
            synchronized (com.android.server.wallpaper.WallpaperManagerService.this.mLock) {
                try {
                    com.android.server.wallpaper.WallpaperManagerService.this.notifyCallbacksLocked(dataForEvent);
                    if (z2 && z6) {
                        com.android.server.wallpaper.WallpaperManagerService.WallpaperDestinationChangeHandler wallpaperDestinationChangeHandler = com.android.server.wallpaper.WallpaperManagerService.this.mPendingMigrationViaStatic;
                        com.android.server.wallpaper.WallpaperManagerService.this.mPendingMigrationViaStatic = null;
                        android.os.SELinux.restorecon(file);
                        if (z4) {
                            com.android.server.wallpaper.WallpaperManagerService.this.loadSettingsLocked(dataForEvent.userId, true, 3);
                        }
                        com.android.server.wallpaper.WallpaperManagerService.this.mWallpaperCropper.generateCrop(dataForEvent);
                        dataForEvent.imageWallpaperPending = false;
                        if (equals) {
                            android.os.IRemoteCallback iRemoteCallback = new android.os.IRemoteCallback.Stub() { // from class: com.android.server.wallpaper.WallpaperManagerService.WallpaperObserver.1
                                public void sendResult(android.os.Bundle bundle) throws android.os.RemoteException {
                                    com.android.server.wallpaper.WallpaperManagerService.this.notifyWallpaperChanged(dataForEvent);
                                }
                            };
                            dataForEvent.mBindSource = com.android.server.wallpaper.WallpaperData.BindSource.SET_STATIC;
                            com.android.server.wallpaper.WallpaperManagerService.this.bindWallpaperComponentLocked(com.android.server.wallpaper.WallpaperManagerService.this.mImageWallpaper, true, false, dataForEvent, iRemoteCallback);
                        }
                        if (equals2) {
                            android.os.IRemoteCallback iRemoteCallback2 = new android.os.IRemoteCallback.Stub() { // from class: com.android.server.wallpaper.WallpaperManagerService.WallpaperObserver.2
                                public void sendResult(android.os.Bundle bundle) throws android.os.RemoteException {
                                    com.android.server.wallpaper.WallpaperManagerService.this.notifyWallpaperChanged(dataForEvent);
                                }
                            };
                            dataForEvent.mBindSource = com.android.server.wallpaper.WallpaperData.BindSource.SET_STATIC;
                            com.android.server.wallpaper.WallpaperManagerService.this.bindWallpaperComponentLocked(com.android.server.wallpaper.WallpaperManagerService.this.mImageWallpaper, true, false, dataForEvent, iRemoteCallback2);
                        } else if (z5) {
                            com.android.server.wallpaper.WallpaperManagerService.this.detachWallpaperLocked((com.android.server.wallpaper.WallpaperData) com.android.server.wallpaper.WallpaperManagerService.this.mLockWallpaperMap.get(this.mWallpaper.userId));
                            com.android.server.wallpaper.WallpaperManagerService.this.clearWallpaperBitmaps(this.mWallpaper.userId, 2);
                            com.android.server.wallpaper.WallpaperManagerService.this.mLockWallpaperMap.remove(dataForEvent.userId);
                            com.android.server.wallpaper.WallpaperManagerService.this.saveSettingsLocked(dataForEvent.userId);
                            if (wallpaperDestinationChangeHandler != null) {
                                wallpaperDestinationChangeHandler.complete();
                            }
                            com.android.server.wallpaper.WallpaperManagerService.this.notifyWallpaperColorsChanged(dataForEvent);
                        }
                        com.android.server.wallpaper.WallpaperManagerService.this.saveSettingsLocked(dataForEvent.userId);
                        if (wallpaperDestinationChangeHandler != null) {
                        }
                        com.android.server.wallpaper.WallpaperManagerService.this.notifyWallpaperColorsChanged(dataForEvent);
                    }
                } finally {
                }
            }
        }

        @Override // android.os.FileObserver
        public void onEvent(int i, java.lang.String str) {
            if (str != null) {
                updateWallpapers(i, str);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyWallpaperChanged(com.android.server.wallpaper.WallpaperData wallpaperData) {
        if (wallpaperData.setComplete != null) {
            try {
                wallpaperData.setComplete.onWallpaperChanged();
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(TAG, "onWallpaperChanged threw an exception", e);
            }
        }
    }

    void notifyWallpaperColorsChanged(@android.annotation.NonNull final com.android.server.wallpaper.WallpaperData wallpaperData) {
        if (wallpaperData.connection != null) {
            wallpaperData.connection.forEachDisplayConnector(new java.util.function.Consumer() { // from class: com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda17
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.wallpaper.WallpaperManagerService.this.lambda$notifyWallpaperColorsChanged$0(wallpaperData, (com.android.server.wallpaper.WallpaperManagerService.DisplayConnector) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyWallpaperColorsChanged$0(com.android.server.wallpaper.WallpaperData wallpaperData, com.android.server.wallpaper.WallpaperManagerService.DisplayConnector displayConnector) {
        notifyWallpaperColorsChangedOnDisplay(wallpaperData, displayConnector.mDisplayId);
    }

    private android.os.RemoteCallbackList<android.app.IWallpaperManagerCallback> getWallpaperCallbacks(int i, int i2) {
        android.util.SparseArray<android.os.RemoteCallbackList<android.app.IWallpaperManagerCallback>> sparseArray = this.mColorsChangedListeners.get(i);
        if (sparseArray == null) {
            return null;
        }
        return sparseArray.get(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyWallpaperColorsChangedOnDisplay(@android.annotation.NonNull com.android.server.wallpaper.WallpaperData wallpaperData, int i) {
        synchronized (this.mLock) {
            try {
                android.os.RemoteCallbackList<android.app.IWallpaperManagerCallback> wallpaperCallbacks = getWallpaperCallbacks(wallpaperData.userId, i);
                android.os.RemoteCallbackList<android.app.IWallpaperManagerCallback> wallpaperCallbacks2 = getWallpaperCallbacks(-1, i);
                if (emptyCallbackList(wallpaperCallbacks) && emptyCallbackList(wallpaperCallbacks2)) {
                    return;
                }
                if (wallpaperData.primaryColors == null || wallpaperData.mIsColorExtractedFromDim ? extractColors(wallpaperData) : true) {
                    notifyColorListeners(getAdjustedWallpaperColorsOnDimming(wallpaperData), wallpaperData.mWhich, wallpaperData.userId, i);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private static <T extends android.os.IInterface> boolean emptyCallbackList(android.os.RemoteCallbackList<T> remoteCallbackList) {
        return remoteCallbackList == null || remoteCallbackList.getRegisteredCallbackCount() == 0;
    }

    private void notifyColorListeners(@android.annotation.NonNull android.app.WallpaperColors wallpaperColors, int i, int i2, int i3) {
        int i4;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mLock) {
            try {
                android.os.RemoteCallbackList<android.app.IWallpaperManagerCallback> wallpaperCallbacks = getWallpaperCallbacks(i2, i3);
                android.os.RemoteCallbackList<android.app.IWallpaperManagerCallback> wallpaperCallbacks2 = getWallpaperCallbacks(-1, i3);
                if (wallpaperCallbacks != null) {
                    int beginBroadcast = wallpaperCallbacks.beginBroadcast();
                    for (int i5 = 0; i5 < beginBroadcast; i5++) {
                        arrayList.add(wallpaperCallbacks.getBroadcastItem(i5));
                    }
                    wallpaperCallbacks.finishBroadcast();
                }
                if (wallpaperCallbacks2 != null) {
                    int beginBroadcast2 = wallpaperCallbacks2.beginBroadcast();
                    for (int i6 = 0; i6 < beginBroadcast2; i6++) {
                        arrayList.add(wallpaperCallbacks2.getBroadcastItem(i6));
                    }
                    wallpaperCallbacks2.finishBroadcast();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        int size = arrayList.size();
        for (i4 = 0; i4 < size; i4++) {
            try {
                ((android.app.IWallpaperManagerCallback) arrayList.get(i4)).onWallpaperColorsChanged(wallpaperColors, i, i2);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(TAG, "onWallpaperColorsChanged() threw an exception", e);
            }
        }
    }

    private boolean extractColors(com.android.server.wallpaper.WallpaperData wallpaperData) {
        boolean z;
        android.app.WallpaperColors wallpaperColors;
        boolean z2;
        java.lang.String str;
        int i;
        float f;
        synchronized (this.mLock) {
            wallpaperData.mIsColorExtractedFromDim = false;
        }
        if (wallpaperData.equals(this.mFallbackWallpaper)) {
            synchronized (this.mLock) {
                try {
                    if (this.mFallbackWallpaper.primaryColors != null) {
                        return true;
                    }
                    android.app.WallpaperColors extractDefaultImageWallpaperColors = extractDefaultImageWallpaperColors(wallpaperData);
                    synchronized (this.mLock) {
                        this.mFallbackWallpaper.primaryColors = extractDefaultImageWallpaperColors;
                    }
                    return true;
                } finally {
                }
            }
        }
        synchronized (this.mLock) {
            try {
                if (!this.mImageWallpaper.equals(wallpaperData.wallpaperComponent) && wallpaperData.wallpaperComponent != null) {
                    z = false;
                } else {
                    z = true;
                }
                wallpaperColors = null;
                if (z && wallpaperData.getCropFile().exists()) {
                    str = wallpaperData.getCropFile().getAbsolutePath();
                    z2 = false;
                } else if (z && !wallpaperData.cropExists() && !wallpaperData.sourceExists()) {
                    z2 = true;
                    str = null;
                } else {
                    z2 = false;
                    str = null;
                }
                i = wallpaperData.wallpaperId;
                f = wallpaperData.mWallpaperDimAmount;
            } finally {
            }
        }
        if (str != null) {
            android.graphics.Bitmap decodeFile = android.graphics.BitmapFactory.decodeFile(str);
            if (decodeFile != null) {
                wallpaperColors = android.app.WallpaperColors.fromBitmap(decodeFile, f);
                decodeFile.recycle();
            }
        } else if (z2) {
            wallpaperColors = extractDefaultImageWallpaperColors(wallpaperData);
        }
        if (wallpaperColors == null) {
            android.util.Slog.w(TAG, "Cannot extract colors because wallpaper could not be read.");
            return true;
        }
        synchronized (this.mLock) {
            try {
                if (wallpaperData.wallpaperId == i) {
                    wallpaperData.primaryColors = wallpaperColors;
                    saveSettingsLocked(wallpaperData.userId);
                    return true;
                }
                android.util.Slog.w(TAG, "Not setting primary colors since wallpaper changed");
                return false;
            } finally {
            }
        }
    }

    private android.app.WallpaperColors extractDefaultImageWallpaperColors(com.android.server.wallpaper.WallpaperData wallpaperData) {
        java.io.InputStream openDefaultWallpaper;
        synchronized (this.mLock) {
            try {
                if (this.mCacheDefaultImageWallpaperColors != null) {
                    return this.mCacheDefaultImageWallpaperColors;
                }
                float f = wallpaperData.mWallpaperDimAmount;
                android.app.WallpaperColors wallpaperColors = null;
                try {
                    openDefaultWallpaper = android.app.WallpaperManager.openDefaultWallpaper(this.mContext, 1);
                    try {
                    } finally {
                    }
                } catch (java.io.IOException e) {
                    android.util.Slog.w(TAG, "Can't close default wallpaper stream", e);
                } catch (java.lang.OutOfMemoryError e2) {
                    android.util.Slog.w(TAG, "Can't decode default wallpaper stream", e2);
                }
                if (openDefaultWallpaper == null) {
                    android.util.Slog.w(TAG, "Can't open default wallpaper stream");
                    if (openDefaultWallpaper != null) {
                        openDefaultWallpaper.close();
                    }
                    return null;
                }
                android.graphics.Bitmap decodeStream = android.graphics.BitmapFactory.decodeStream(openDefaultWallpaper, null, new android.graphics.BitmapFactory.Options());
                if (decodeStream != null) {
                    wallpaperColors = android.app.WallpaperColors.fromBitmap(decodeStream, f);
                    decodeStream.recycle();
                }
                openDefaultWallpaper.close();
                if (wallpaperColors == null) {
                    android.util.Slog.e(TAG, "Extract default image wallpaper colors failed");
                } else {
                    synchronized (this.mLock) {
                        this.mCacheDefaultImageWallpaperColors = wallpaperColors;
                    }
                }
                return wallpaperColors;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean supportsMultiDisplay(com.android.server.wallpaper.WallpaperManagerService.WallpaperConnection wallpaperConnection) {
        if (wallpaperConnection != null) {
            return wallpaperConnection.mInfo == null || wallpaperConnection.mInfo.supportsMultipleDisplays();
        }
        return false;
    }

    private void updateFallbackConnection() {
        if (this.mLastWallpaper == null || this.mFallbackWallpaper == null) {
            return;
        }
        com.android.server.wallpaper.WallpaperManagerService.WallpaperConnection wallpaperConnection = this.mLastWallpaper.connection;
        final com.android.server.wallpaper.WallpaperManagerService.WallpaperConnection wallpaperConnection2 = this.mFallbackWallpaper.connection;
        if (wallpaperConnection2 == null) {
            android.util.Slog.w(TAG, "Fallback wallpaper connection has not been created yet!!");
            return;
        }
        if (supportsMultiDisplay(wallpaperConnection)) {
            if (wallpaperConnection2.mDisplayConnector.size() != 0) {
                wallpaperConnection2.forEachDisplayConnector(new java.util.function.Consumer() { // from class: com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda2
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.server.wallpaper.WallpaperManagerService.lambda$updateFallbackConnection$1(com.android.server.wallpaper.WallpaperManagerService.WallpaperConnection.this, (com.android.server.wallpaper.WallpaperManagerService.DisplayConnector) obj);
                    }
                });
                wallpaperConnection2.mDisplayConnector.clear();
                return;
            }
            return;
        }
        wallpaperConnection2.appendConnectorWithCondition(new java.util.function.Predicate() { // from class: com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$updateFallbackConnection$2;
                lambda$updateFallbackConnection$2 = com.android.server.wallpaper.WallpaperManagerService.this.lambda$updateFallbackConnection$2(wallpaperConnection2, (android.view.Display) obj);
                return lambda$updateFallbackConnection$2;
            }
        });
        wallpaperConnection2.forEachDisplayConnector(new java.util.function.Consumer() { // from class: com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wallpaper.WallpaperManagerService.this.lambda$updateFallbackConnection$3(wallpaperConnection2, (com.android.server.wallpaper.WallpaperManagerService.DisplayConnector) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$updateFallbackConnection$1(com.android.server.wallpaper.WallpaperManagerService.WallpaperConnection wallpaperConnection, com.android.server.wallpaper.WallpaperManagerService.DisplayConnector displayConnector) {
        if (displayConnector.mEngine != null) {
            displayConnector.disconnectLocked(wallpaperConnection);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$updateFallbackConnection$2(com.android.server.wallpaper.WallpaperManagerService.WallpaperConnection wallpaperConnection, android.view.Display display) {
        return (!this.mWallpaperDisplayHelper.isUsableDisplay(display, wallpaperConnection.mClientUid) || display.getDisplayId() == 0 || wallpaperConnection.containsDisplay(display.getDisplayId())) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateFallbackConnection$3(com.android.server.wallpaper.WallpaperManagerService.WallpaperConnection wallpaperConnection, com.android.server.wallpaper.WallpaperManagerService.DisplayConnector displayConnector) {
        if (displayConnector.mEngine == null) {
            displayConnector.connectLocked(wallpaperConnection, this.mFallbackWallpaper);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    final class DisplayConnector {
        boolean mDimensionsChanged;
        final int mDisplayId;
        android.service.wallpaper.IWallpaperEngine mEngine;
        boolean mPaddingChanged;
        final android.os.Binder mToken = new android.os.Binder();

        DisplayConnector(int i) {
            this.mDisplayId = i;
        }

        void ensureStatusHandled() {
            com.android.server.wallpaper.WallpaperDisplayHelper.DisplayData displayDataOrCreate = com.android.server.wallpaper.WallpaperManagerService.this.mWallpaperDisplayHelper.getDisplayDataOrCreate(this.mDisplayId);
            if (this.mDimensionsChanged) {
                try {
                    this.mEngine.setDesiredSize(displayDataOrCreate.mWidth, displayDataOrCreate.mHeight);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.w(com.android.server.wallpaper.WallpaperManagerService.TAG, "Failed to set wallpaper dimensions", e);
                }
                this.mDimensionsChanged = false;
            }
            if (this.mPaddingChanged) {
                try {
                    this.mEngine.setDisplayPadding(displayDataOrCreate.mPadding);
                } catch (android.os.RemoteException e2) {
                    android.util.Slog.w(com.android.server.wallpaper.WallpaperManagerService.TAG, "Failed to set wallpaper padding", e2);
                }
                this.mPaddingChanged = false;
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public void connectLocked(com.android.server.wallpaper.WallpaperManagerService.WallpaperConnection wallpaperConnection, com.android.server.wallpaper.WallpaperData wallpaperData) {
            com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog;
            if (wallpaperConnection.mService == null) {
                android.util.Slog.w(com.android.server.wallpaper.WallpaperManagerService.TAG, "WallpaperService is not connected yet");
                return;
            }
            com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog2 = new com.android.server.utils.TimingsTraceAndSlog(com.android.server.wallpaper.WallpaperManagerService.TAG);
            timingsTraceAndSlog2.traceBegin("WPMS.connectLocked-" + wallpaperData.wallpaperComponent);
            com.android.server.wallpaper.WallpaperManagerService.this.mWindowManagerInternal.addWindowToken(this.mToken, 2013, this.mDisplayId, null);
            com.android.server.wallpaper.WallpaperManagerService.this.mWindowManagerInternal.setWallpaperShowWhenLocked(this.mToken, (wallpaperData.mWhich & 2) != 0);
            if (!com.android.window.flags.Flags.multiCrop() || !wallpaperData.mSupportsMultiCrop) {
                com.android.server.wallpaper.WallpaperManagerService.this.mWindowManagerInternal.setWallpaperCropHints(this.mToken, new android.util.SparseArray<>());
            } else {
                com.android.server.wallpaper.WallpaperManagerService.this.mWindowManagerInternal.setWallpaperCropHints(this.mToken, com.android.server.wallpaper.WallpaperManagerService.this.mWallpaperCropper.getRelativeCropHints(wallpaperData));
            }
            com.android.server.wallpaper.WallpaperDisplayHelper.DisplayData displayDataOrCreate = com.android.server.wallpaper.WallpaperManagerService.this.mWallpaperDisplayHelper.getDisplayDataOrCreate(this.mDisplayId);
            try {
                timingsTraceAndSlog = timingsTraceAndSlog2;
            } catch (android.os.RemoteException e) {
                e = e;
                timingsTraceAndSlog = timingsTraceAndSlog2;
            }
            try {
                wallpaperConnection.mService.attach(wallpaperConnection, this.mToken, 2013, false, displayDataOrCreate.mWidth, displayDataOrCreate.mHeight, displayDataOrCreate.mPadding, this.mDisplayId, wallpaperData.mWhich, wallpaperConnection.mInfo);
            } catch (android.os.RemoteException e2) {
                e = e2;
                android.util.Slog.w(com.android.server.wallpaper.WallpaperManagerService.TAG, "Failed attaching wallpaper on display", e);
                if (!wallpaperData.wallpaperUpdating && wallpaperConnection.getConnectedEngineSize() == 0) {
                    wallpaperData.mBindSource = com.android.server.wallpaper.WallpaperData.BindSource.CONNECT_LOCKED;
                    com.android.server.wallpaper.WallpaperManagerService.this.bindWallpaperComponentLocked(null, false, false, wallpaperData, null);
                }
                timingsTraceAndSlog.traceEnd();
            }
            timingsTraceAndSlog.traceEnd();
        }

        void disconnectLocked(com.android.server.wallpaper.WallpaperManagerService.WallpaperConnection wallpaperConnection) {
            com.android.server.wallpaper.WallpaperManagerService.this.mWindowManagerInternal.removeWindowToken(this.mToken, false, this.mDisplayId);
            try {
                if (wallpaperConnection.mService != null) {
                    wallpaperConnection.mService.detach(this.mToken);
                }
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.wallpaper.WallpaperManagerService.TAG, "connection.mService.destroy() threw a RemoteException", e);
            }
            this.mEngine = null;
        }
    }

    class WallpaperConnection extends android.service.wallpaper.IWallpaperConnection.Stub implements android.content.ServiceConnection {
        private static final long WALLPAPER_RECONNECT_TIMEOUT_MS = 10000;
        final int mClientUid;
        final android.app.WallpaperInfo mInfo;
        android.os.IRemoteCallback mReply;
        android.service.wallpaper.IWallpaperService mService;
        com.android.server.wallpaper.WallpaperData mWallpaper;
        private final android.util.SparseArray<com.android.server.wallpaper.WallpaperManagerService.DisplayConnector> mDisplayConnector = new android.util.SparseArray<>();
        private int mLmkLimitRebindRetries = 3;
        private java.lang.Runnable mResetRunnable = new java.lang.Runnable() { // from class: com.android.server.wallpaper.WallpaperManagerService$WallpaperConnection$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wallpaper.WallpaperManagerService.WallpaperConnection.this.lambda$new$0();
            }
        };
        private java.lang.Runnable mTryToRebindRunnable = new java.lang.Runnable() { // from class: com.android.server.wallpaper.WallpaperManagerService$WallpaperConnection$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wallpaper.WallpaperManagerService.WallpaperConnection.this.tryToRebind();
            }
        };
        private java.lang.Runnable mDisconnectRunnable = new java.lang.Runnable() { // from class: com.android.server.wallpaper.WallpaperManagerService$WallpaperConnection$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wallpaper.WallpaperManagerService.WallpaperConnection.this.lambda$new$5();
            }
        };

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$0() {
            synchronized (com.android.server.wallpaper.WallpaperManagerService.this.mLock) {
                try {
                    if (com.android.server.wallpaper.WallpaperManagerService.this.mShuttingDown) {
                        android.util.Slog.i(com.android.server.wallpaper.WallpaperManagerService.TAG, "Ignoring relaunch timeout during shutdown");
                        return;
                    }
                    if (!this.mWallpaper.wallpaperUpdating && this.mWallpaper.userId == com.android.server.wallpaper.WallpaperManagerService.this.mCurrentUserId) {
                        android.util.Slog.w(com.android.server.wallpaper.WallpaperManagerService.TAG, "Wallpaper reconnect timed out for " + this.mWallpaper.wallpaperComponent + ", reverting to built-in wallpaper!");
                        com.android.server.wallpaper.WallpaperManagerService.this.clearWallpaperLocked(this.mWallpaper.mWhich, this.mWallpaper.userId, false, null);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        WallpaperConnection(android.app.WallpaperInfo wallpaperInfo, com.android.server.wallpaper.WallpaperData wallpaperData, int i) {
            this.mInfo = wallpaperInfo;
            this.mWallpaper = wallpaperData;
            this.mClientUid = i;
            initDisplayState();
        }

        private void initDisplayState() {
            if (!this.mWallpaper.equals(com.android.server.wallpaper.WallpaperManagerService.this.mFallbackWallpaper)) {
                if (com.android.server.wallpaper.WallpaperManagerService.this.supportsMultiDisplay(this)) {
                    appendConnectorWithCondition(new java.util.function.Predicate() { // from class: com.android.server.wallpaper.WallpaperManagerService$WallpaperConnection$$ExternalSyntheticLambda3
                        @Override // java.util.function.Predicate
                        public final boolean test(java.lang.Object obj) {
                            boolean lambda$initDisplayState$1;
                            lambda$initDisplayState$1 = com.android.server.wallpaper.WallpaperManagerService.WallpaperConnection.this.lambda$initDisplayState$1((android.view.Display) obj);
                            return lambda$initDisplayState$1;
                        }
                    });
                } else {
                    this.mDisplayConnector.append(0, com.android.server.wallpaper.WallpaperManagerService.this.new DisplayConnector(0));
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ boolean lambda$initDisplayState$1(android.view.Display display) {
            return com.android.server.wallpaper.WallpaperManagerService.this.mWallpaperDisplayHelper.isUsableDisplay(display, this.mClientUid);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void appendConnectorWithCondition(java.util.function.Predicate<android.view.Display> predicate) {
            for (android.view.Display display : com.android.server.wallpaper.WallpaperManagerService.this.mWallpaperDisplayHelper.getDisplays()) {
                if (predicate.test(display)) {
                    int displayId = display.getDisplayId();
                    if (this.mDisplayConnector.get(displayId) == null) {
                        this.mDisplayConnector.append(displayId, com.android.server.wallpaper.WallpaperManagerService.this.new DisplayConnector(displayId));
                    }
                }
            }
        }

        void forEachDisplayConnector(java.util.function.Consumer<com.android.server.wallpaper.WallpaperManagerService.DisplayConnector> consumer) {
            for (int size = this.mDisplayConnector.size() - 1; size >= 0; size--) {
                consumer.accept(this.mDisplayConnector.valueAt(size));
            }
        }

        int getConnectedEngineSize() {
            int i = 0;
            for (int size = this.mDisplayConnector.size() - 1; size >= 0; size--) {
                if (this.mDisplayConnector.valueAt(size).mEngine != null) {
                    i++;
                }
            }
            return i;
        }

        com.android.server.wallpaper.WallpaperManagerService.DisplayConnector getDisplayConnectorOrCreate(int i) {
            com.android.server.wallpaper.WallpaperManagerService.DisplayConnector displayConnector = this.mDisplayConnector.get(i);
            if (displayConnector == null && com.android.server.wallpaper.WallpaperManagerService.this.mWallpaperDisplayHelper.isUsableDisplay(i, this.mClientUid)) {
                com.android.server.wallpaper.WallpaperManagerService.DisplayConnector displayConnector2 = com.android.server.wallpaper.WallpaperManagerService.this.new DisplayConnector(i);
                this.mDisplayConnector.append(i, displayConnector2);
                return displayConnector2;
            }
            return displayConnector;
        }

        boolean containsDisplay(int i) {
            return this.mDisplayConnector.get(i) != null;
        }

        void removeDisplayConnector(int i) {
            if (this.mDisplayConnector.get(i) != null) {
                this.mDisplayConnector.remove(i);
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
            com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog = new com.android.server.utils.TimingsTraceAndSlog(com.android.server.wallpaper.WallpaperManagerService.TAG);
            timingsTraceAndSlog.traceBegin("WPMS.onServiceConnected-" + componentName);
            synchronized (com.android.server.wallpaper.WallpaperManagerService.this.mLock) {
                try {
                    if (this.mWallpaper.connection == this) {
                        this.mService = android.service.wallpaper.IWallpaperService.Stub.asInterface(iBinder);
                        com.android.server.wallpaper.WallpaperManagerService.this.attachServiceLocked(this, this.mWallpaper);
                        if (!this.mWallpaper.equals(com.android.server.wallpaper.WallpaperManagerService.this.mFallbackWallpaper)) {
                            com.android.server.wallpaper.WallpaperManagerService.this.saveSettingsLocked(this.mWallpaper.userId);
                        }
                        com.android.server.FgThread.getHandler().removeCallbacks(this.mResetRunnable);
                        com.android.server.wallpaper.WallpaperManagerService.this.mContext.getMainThreadHandler().removeCallbacks(this.mTryToRebindRunnable);
                        com.android.server.wallpaper.WallpaperManagerService.this.mContext.getMainThreadHandler().removeCallbacks(this.mDisconnectRunnable);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            timingsTraceAndSlog.traceEnd();
        }

        public void onLocalWallpaperColorsChanged(final android.graphics.RectF rectF, final android.app.WallpaperColors wallpaperColors, final int i) {
            forEachDisplayConnector(new java.util.function.Consumer() { // from class: com.android.server.wallpaper.WallpaperManagerService$WallpaperConnection$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.wallpaper.WallpaperManagerService.WallpaperConnection.this.lambda$onLocalWallpaperColorsChanged$3(rectF, wallpaperColors, i, (com.android.server.wallpaper.WallpaperManagerService.DisplayConnector) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onLocalWallpaperColorsChanged$3(final android.graphics.RectF rectF, final android.app.WallpaperColors wallpaperColors, int i, com.android.server.wallpaper.WallpaperManagerService.DisplayConnector displayConnector) {
            java.util.function.Consumer<android.app.ILocalWallpaperColorConsumer> consumer = new java.util.function.Consumer() { // from class: com.android.server.wallpaper.WallpaperManagerService$WallpaperConnection$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.wallpaper.WallpaperManagerService.WallpaperConnection.lambda$onLocalWallpaperColorsChanged$2(rectF, wallpaperColors, (android.app.ILocalWallpaperColorConsumer) obj);
                }
            };
            synchronized (com.android.server.wallpaper.WallpaperManagerService.this.mLock) {
                com.android.server.wallpaper.WallpaperManagerService.this.mLocalColorRepo.forEachCallback(consumer, rectF, i);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onLocalWallpaperColorsChanged$2(android.graphics.RectF rectF, android.app.WallpaperColors wallpaperColors, android.app.ILocalWallpaperColorConsumer iLocalWallpaperColorConsumer) {
            try {
                iLocalWallpaperColorConsumer.onColorsChanged(rectF, wallpaperColors);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.wallpaper.WallpaperManagerService.TAG, "Failed to notify local color callbacks", e);
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(android.content.ComponentName componentName) {
            synchronized (com.android.server.wallpaper.WallpaperManagerService.this.mLock) {
                try {
                    android.util.Slog.w(com.android.server.wallpaper.WallpaperManagerService.TAG, "Wallpaper service gone: " + componentName);
                    if (!java.util.Objects.equals(componentName, this.mWallpaper.wallpaperComponent)) {
                        android.util.Slog.e(com.android.server.wallpaper.WallpaperManagerService.TAG, "Does not match expected wallpaper component " + this.mWallpaper.wallpaperComponent);
                    }
                    this.mService = null;
                    forEachDisplayConnector(new java.util.function.Consumer() { // from class: com.android.server.wallpaper.WallpaperManagerService$WallpaperConnection$$ExternalSyntheticLambda6
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            ((com.android.server.wallpaper.WallpaperManagerService.DisplayConnector) obj).mEngine = null;
                        }
                    });
                    if (this.mWallpaper.connection == this && !this.mWallpaper.wallpaperUpdating) {
                        com.android.server.wallpaper.WallpaperManagerService.this.mContext.getMainThreadHandler().postDelayed(this.mDisconnectRunnable, 1000L);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        private void scheduleTimeoutLocked() {
            android.os.Handler handler = com.android.server.FgThread.getHandler();
            handler.removeCallbacks(this.mResetRunnable);
            handler.postDelayed(this.mResetRunnable, 10000L);
            android.util.Slog.i(com.android.server.wallpaper.WallpaperManagerService.TAG, "Started wallpaper reconnect timeout for " + this.mWallpaper.wallpaperComponent);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void tryToRebind() {
            synchronized (com.android.server.wallpaper.WallpaperManagerService.this.mLock) {
                try {
                    if (this.mWallpaper.wallpaperUpdating) {
                        return;
                    }
                    android.content.ComponentName componentName = this.mWallpaper.wallpaperComponent;
                    this.mWallpaper.mBindSource = com.android.server.wallpaper.WallpaperData.BindSource.CONNECTION_TRY_TO_REBIND;
                    if (com.android.server.wallpaper.WallpaperManagerService.this.bindWallpaperComponentLocked(componentName, true, false, this.mWallpaper, null)) {
                        this.mWallpaper.connection.scheduleTimeoutLocked();
                    } else if (android.os.SystemClock.uptimeMillis() - this.mWallpaper.lastDiedTime < 10000) {
                        android.util.Slog.w(com.android.server.wallpaper.WallpaperManagerService.TAG, "Rebind fail! Try again later");
                        com.android.server.wallpaper.WallpaperManagerService.this.mContext.getMainThreadHandler().postDelayed(this.mTryToRebindRunnable, 1000L);
                    } else {
                        android.util.Slog.w(com.android.server.wallpaper.WallpaperManagerService.TAG, "Reverting to built-in wallpaper!");
                        com.android.server.wallpaper.WallpaperManagerService.this.clearWallpaperLocked(this.mWallpaper.mWhich, this.mWallpaper.userId, false, null);
                        java.lang.String flattenToString = componentName.flattenToString();
                        android.util.EventLog.writeEvent(com.android.server.EventLogTags.WP_WALLPAPER_CRASHED, flattenToString.substring(0, java.lang.Math.min(flattenToString.length(), 128)));
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$5() {
            int i;
            synchronized (com.android.server.wallpaper.WallpaperManagerService.this.mLock) {
                try {
                    if (this == this.mWallpaper.connection) {
                        android.content.ComponentName componentName = this.mWallpaper.wallpaperComponent;
                        if (!this.mWallpaper.wallpaperUpdating && this.mWallpaper.userId == com.android.server.wallpaper.WallpaperManagerService.this.mCurrentUserId && !java.util.Objects.equals(com.android.server.wallpaper.WallpaperManagerService.this.mDefaultWallpaperComponent, componentName) && !java.util.Objects.equals(com.android.server.wallpaper.WallpaperManagerService.this.mImageWallpaper, componentName)) {
                            java.util.List<android.app.ApplicationExitInfo> historicalProcessExitReasons = com.android.server.wallpaper.WallpaperManagerService.this.mActivityManager.getHistoricalProcessExitReasons(componentName.getPackageName(), 0, 1);
                            if (historicalProcessExitReasons != null && !historicalProcessExitReasons.isEmpty()) {
                                i = historicalProcessExitReasons.get(0).getReason();
                            } else {
                                i = 0;
                            }
                            android.util.Slog.d(com.android.server.wallpaper.WallpaperManagerService.TAG, "exitReason: " + i);
                            if (i == 3) {
                                if (isRunningOnLowMemory()) {
                                    android.util.Slog.i(com.android.server.wallpaper.WallpaperManagerService.TAG, "Rebind is delayed due to lmk");
                                    com.android.server.wallpaper.WallpaperManagerService.this.mContext.getMainThreadHandler().postDelayed(this.mTryToRebindRunnable, com.android.server.wallpaper.WallpaperManagerService.LMK_RECONNECT_DELAY_MS);
                                    this.mLmkLimitRebindRetries = 3;
                                } else if (this.mLmkLimitRebindRetries > 0) {
                                    this.mLmkLimitRebindRetries--;
                                    com.android.server.wallpaper.WallpaperManagerService.this.mContext.getMainThreadHandler().postDelayed(this.mTryToRebindRunnable, com.android.server.wallpaper.WallpaperManagerService.LMK_RECONNECT_DELAY_MS);
                                } else {
                                    android.util.Slog.w(com.android.server.wallpaper.WallpaperManagerService.TAG, "Reverting to built-in wallpaper due to lmk!");
                                    com.android.server.wallpaper.WallpaperManagerService.this.clearWallpaperLocked(this.mWallpaper.mWhich, this.mWallpaper.userId, false, null);
                                    this.mLmkLimitRebindRetries = 3;
                                }
                            } else if (this.mWallpaper.lastDiedTime != 0 && this.mWallpaper.lastDiedTime + 10000 > android.os.SystemClock.uptimeMillis()) {
                                android.util.Slog.w(com.android.server.wallpaper.WallpaperManagerService.TAG, "Reverting to built-in wallpaper!");
                                com.android.server.wallpaper.WallpaperManagerService.this.clearWallpaperLocked(1, this.mWallpaper.userId, false, null);
                            } else {
                                this.mWallpaper.lastDiedTime = android.os.SystemClock.uptimeMillis();
                                tryToRebind();
                            }
                        }
                    } else {
                        android.util.Slog.i(com.android.server.wallpaper.WallpaperManagerService.TAG, "Wallpaper changed during disconnect tracking; ignoring");
                    }
                } finally {
                }
            }
        }

        private boolean isRunningOnLowMemory() {
            android.app.ActivityManager.MemoryInfo memoryInfo = new android.app.ActivityManager.MemoryInfo();
            com.android.server.wallpaper.WallpaperManagerService.this.mActivityManager.getMemoryInfo(memoryInfo);
            return (((double) memoryInfo.availMem) / ((double) memoryInfo.totalMem)) * 100.0d < com.android.server.wallpaper.WallpaperManagerService.LMK_LOW_THRESHOLD_MEMORY_PERCENTAGE;
        }

        public void onWallpaperColorsChanged(android.app.WallpaperColors wallpaperColors, int i) {
            synchronized (com.android.server.wallpaper.WallpaperManagerService.this.mLock) {
                try {
                    if (com.android.server.wallpaper.WallpaperManagerService.this.mImageWallpaper.equals(this.mWallpaper.wallpaperComponent)) {
                        return;
                    }
                    this.mWallpaper.primaryColors = wallpaperColors;
                    com.android.server.wallpaper.WallpaperManagerService.this.notifyWallpaperColorsChangedOnDisplay(this.mWallpaper, i);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void attachEngine(android.service.wallpaper.IWallpaperEngine iWallpaperEngine, int i) {
            synchronized (com.android.server.wallpaper.WallpaperManagerService.this.mLock) {
                com.android.server.wallpaper.WallpaperManagerService.DisplayConnector displayConnectorOrCreate = getDisplayConnectorOrCreate(i);
                if (displayConnectorOrCreate == null) {
                    throw new java.lang.IllegalStateException("Connector has already been destroyed");
                }
                displayConnectorOrCreate.mEngine = iWallpaperEngine;
                displayConnectorOrCreate.ensureStatusHandled();
                if (this.mInfo != null && this.mInfo.supportsAmbientMode() && i == 0) {
                    try {
                        displayConnectorOrCreate.mEngine.setInAmbientMode(com.android.server.wallpaper.WallpaperManagerService.this.mInAmbientMode, 0L);
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.w(com.android.server.wallpaper.WallpaperManagerService.TAG, "Failed to set ambient mode state", e);
                    }
                }
                try {
                    displayConnectorOrCreate.mEngine.requestWallpaperColors();
                } catch (android.os.RemoteException e2) {
                    android.util.Slog.w(com.android.server.wallpaper.WallpaperManagerService.TAG, "Failed to request wallpaper colors", e2);
                }
                java.util.List<android.graphics.RectF> areasByDisplayId = com.android.server.wallpaper.WallpaperManagerService.this.mLocalColorRepo.getAreasByDisplayId(i);
                if (areasByDisplayId != null && areasByDisplayId.size() != 0) {
                    try {
                        displayConnectorOrCreate.mEngine.addLocalColorsAreas(areasByDisplayId);
                    } catch (android.os.RemoteException e3) {
                        android.util.Slog.w(com.android.server.wallpaper.WallpaperManagerService.TAG, "Failed to register local colors areas", e3);
                    }
                }
                if (this.mWallpaper.mWallpaperDimAmount != com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                    try {
                        displayConnectorOrCreate.mEngine.applyDimming(this.mWallpaper.mWallpaperDimAmount);
                    } catch (android.os.RemoteException e4) {
                        android.util.Slog.w(com.android.server.wallpaper.WallpaperManagerService.TAG, "Failed to dim wallpaper", e4);
                    }
                }
            }
        }

        public void engineShown(android.service.wallpaper.IWallpaperEngine iWallpaperEngine) {
            synchronized (com.android.server.wallpaper.WallpaperManagerService.this.mLock) {
                if (this.mReply != null) {
                    com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog = new com.android.server.utils.TimingsTraceAndSlog(com.android.server.wallpaper.WallpaperManagerService.TAG);
                    timingsTraceAndSlog.traceBegin("WPMS.mReply.sendResult");
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        try {
                            this.mReply.sendResult((android.os.Bundle) null);
                        } catch (android.os.RemoteException e) {
                            android.util.Slog.d(com.android.server.wallpaper.WallpaperManagerService.TAG, "Failed to send callback!", e);
                        }
                        timingsTraceAndSlog.traceEnd();
                        this.mReply = null;
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }
            }
        }

        public android.os.ParcelFileDescriptor setWallpaper(java.lang.String str) {
            synchronized (com.android.server.wallpaper.WallpaperManagerService.this.mLock) {
                try {
                    if (this.mWallpaper.connection != this) {
                        return null;
                    }
                    return com.android.server.wallpaper.WallpaperManagerService.this.updateWallpaperBitmapLocked(str, this.mWallpaper, null);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    class WallpaperDestinationChangeHandler {
        final com.android.server.wallpaper.WallpaperData mNewWallpaper;
        final com.android.server.wallpaper.WallpaperData mOriginalSystem;

        WallpaperDestinationChangeHandler(com.android.server.wallpaper.WallpaperData wallpaperData) {
            this.mNewWallpaper = wallpaperData;
            this.mOriginalSystem = new com.android.server.wallpaper.WallpaperData((com.android.server.wallpaper.WallpaperData) com.android.server.wallpaper.WallpaperManagerService.this.mWallpaperMap.get(wallpaperData.userId));
        }

        void complete() {
            if (this.mNewWallpaper.mSystemWasBoth) {
                if (this.mNewWallpaper.mWhich == 1) {
                    if (com.android.server.wallpaper.WallpaperManagerService.this.mImageWallpaper.equals(this.mOriginalSystem.wallpaperComponent)) {
                        com.android.server.wallpaper.WallpaperData wallpaperData = (com.android.server.wallpaper.WallpaperData) com.android.server.wallpaper.WallpaperManagerService.this.mLockWallpaperMap.get(this.mNewWallpaper.userId);
                        if (wallpaperData != null && this.mOriginalSystem.connection != null) {
                            wallpaperData.wallpaperComponent = this.mOriginalSystem.wallpaperComponent;
                            wallpaperData.connection = this.mOriginalSystem.connection;
                            wallpaperData.connection.mWallpaper = wallpaperData;
                            this.mOriginalSystem.mWhich = 2;
                            com.android.server.wallpaper.WallpaperManagerService.this.updateEngineFlags(this.mOriginalSystem);
                        } else {
                            com.android.server.wallpaper.WallpaperData wallpaperData2 = (com.android.server.wallpaper.WallpaperData) com.android.server.wallpaper.WallpaperManagerService.this.mWallpaperMap.get(this.mNewWallpaper.userId);
                            wallpaperData2.mWhich = 3;
                            com.android.server.wallpaper.WallpaperManagerService.this.updateEngineFlags(wallpaperData2);
                            com.android.server.wallpaper.WallpaperManagerService.this.mLockWallpaperMap.remove(this.mNewWallpaper.userId);
                        }
                    } else {
                        this.mOriginalSystem.mWhich = 2;
                        com.android.server.wallpaper.WallpaperManagerService.this.updateEngineFlags(this.mOriginalSystem);
                        com.android.server.wallpaper.WallpaperManagerService.this.mLockWallpaperMap.put(this.mNewWallpaper.userId, this.mOriginalSystem);
                        com.android.server.wallpaper.WallpaperManagerService.this.mLastLockWallpaper = this.mOriginalSystem;
                    }
                } else if (this.mNewWallpaper.mWhich == 2) {
                    com.android.server.wallpaper.WallpaperData wallpaperData3 = (com.android.server.wallpaper.WallpaperData) com.android.server.wallpaper.WallpaperManagerService.this.mWallpaperMap.get(this.mNewWallpaper.userId);
                    if (wallpaperData3.wallpaperId == this.mOriginalSystem.wallpaperId) {
                        wallpaperData3.mWhich = 1;
                        com.android.server.wallpaper.WallpaperManagerService.this.updateEngineFlags(wallpaperData3);
                    }
                }
            }
            com.android.server.wallpaper.WallpaperManagerService.this.saveSettingsLocked(this.mNewWallpaper.userId);
        }
    }

    class MyPackageMonitor extends com.android.internal.content.PackageMonitor {
        MyPackageMonitor() {
        }

        public void onPackageUpdateFinished(java.lang.String str, int i) {
            synchronized (com.android.server.wallpaper.WallpaperManagerService.this.mLock) {
                try {
                    if (com.android.server.wallpaper.WallpaperManagerService.this.mCurrentUserId != getChangingUserId()) {
                        return;
                    }
                    for (com.android.server.wallpaper.WallpaperData wallpaperData : com.android.server.wallpaper.WallpaperManagerService.this.getWallpapers()) {
                        android.content.ComponentName componentName = wallpaperData.wallpaperComponent;
                        if (componentName != null && componentName.getPackageName().equals(str)) {
                            android.util.Slog.i(com.android.server.wallpaper.WallpaperManagerService.TAG, "Wallpaper " + componentName + " update has finished");
                            wallpaperData.wallpaperUpdating = false;
                            com.android.server.wallpaper.WallpaperManagerService.this.lambda$clearWallpaperLocked$8(wallpaperData);
                            wallpaperData.mBindSource = com.android.server.wallpaper.WallpaperData.BindSource.PACKAGE_UPDATE_FINISHED;
                            if (!com.android.server.wallpaper.WallpaperManagerService.this.bindWallpaperComponentLocked(componentName, false, false, wallpaperData, null)) {
                                android.util.Slog.w(com.android.server.wallpaper.WallpaperManagerService.TAG, "Wallpaper " + componentName + " no longer available; reverting to default");
                                com.android.server.wallpaper.WallpaperManagerService.this.clearWallpaperLocked(wallpaperData.mWhich, wallpaperData.userId, false, null);
                            }
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void onPackageModified(java.lang.String str) {
            synchronized (com.android.server.wallpaper.WallpaperManagerService.this.mLock) {
                try {
                    if (com.android.server.wallpaper.WallpaperManagerService.this.mCurrentUserId != getChangingUserId()) {
                        return;
                    }
                    for (com.android.server.wallpaper.WallpaperData wallpaperData : com.android.server.wallpaper.WallpaperManagerService.this.getWallpapers()) {
                        if (wallpaperData.wallpaperComponent != null && wallpaperData.wallpaperComponent.getPackageName().equals(str)) {
                            doPackagesChangedLocked(true, wallpaperData);
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void onPackageUpdateStarted(java.lang.String str, int i) {
            synchronized (com.android.server.wallpaper.WallpaperManagerService.this.mLock) {
                try {
                    if (com.android.server.wallpaper.WallpaperManagerService.this.mCurrentUserId != getChangingUserId()) {
                        return;
                    }
                    for (com.android.server.wallpaper.WallpaperData wallpaperData : com.android.server.wallpaper.WallpaperManagerService.this.getWallpapers()) {
                        if (wallpaperData.wallpaperComponent != null && wallpaperData.wallpaperComponent.getPackageName().equals(str)) {
                            android.util.Slog.i(com.android.server.wallpaper.WallpaperManagerService.TAG, "Wallpaper service " + wallpaperData.wallpaperComponent + " is updating");
                            wallpaperData.wallpaperUpdating = true;
                            if (wallpaperData.connection != null) {
                                com.android.server.FgThread.getHandler().removeCallbacks(wallpaperData.connection.mResetRunnable);
                            }
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public boolean onHandleForceStop(android.content.Intent intent, java.lang.String[] strArr, int i, boolean z) {
            synchronized (com.android.server.wallpaper.WallpaperManagerService.this.mLock) {
                try {
                    if (com.android.server.wallpaper.WallpaperManagerService.this.mCurrentUserId != getChangingUserId()) {
                        return false;
                    }
                    boolean z2 = false;
                    for (com.android.server.wallpaper.WallpaperData wallpaperData : com.android.server.wallpaper.WallpaperManagerService.this.getWallpapers()) {
                        z2 |= doPackagesChangedLocked(z, wallpaperData);
                    }
                    return z2;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void onSomePackagesChanged() {
            synchronized (com.android.server.wallpaper.WallpaperManagerService.this.mLock) {
                try {
                    if (com.android.server.wallpaper.WallpaperManagerService.this.mCurrentUserId != getChangingUserId()) {
                        return;
                    }
                    for (com.android.server.wallpaper.WallpaperData wallpaperData : com.android.server.wallpaper.WallpaperManagerService.this.getWallpapers()) {
                        doPackagesChangedLocked(true, wallpaperData);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        boolean doPackagesChangedLocked(boolean z, com.android.server.wallpaper.WallpaperData wallpaperData) {
            boolean z2;
            int isPackageDisappearing;
            int isPackageDisappearing2;
            if (wallpaperData.wallpaperComponent == null || !((isPackageDisappearing2 = isPackageDisappearing(wallpaperData.wallpaperComponent.getPackageName())) == 3 || isPackageDisappearing2 == 2)) {
                z2 = false;
            } else {
                if (z) {
                    android.util.Slog.w(com.android.server.wallpaper.WallpaperManagerService.TAG, "Wallpaper uninstalled, removing: " + wallpaperData.wallpaperComponent);
                    com.android.server.wallpaper.WallpaperManagerService.this.clearWallpaperLocked(wallpaperData.mWhich, wallpaperData.userId, false, null);
                }
                z2 = true;
            }
            if (wallpaperData.nextWallpaperComponent != null && ((isPackageDisappearing = isPackageDisappearing(wallpaperData.nextWallpaperComponent.getPackageName())) == 3 || isPackageDisappearing == 2)) {
                wallpaperData.nextWallpaperComponent = null;
            }
            if (wallpaperData.wallpaperComponent != null && isPackageModified(wallpaperData.wallpaperComponent.getPackageName())) {
                try {
                    com.android.server.wallpaper.WallpaperManagerService.this.mContext.getPackageManager().getServiceInfo(wallpaperData.wallpaperComponent, 786432);
                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                    android.util.Slog.w(com.android.server.wallpaper.WallpaperManagerService.TAG, "Wallpaper component gone, removing: " + wallpaperData.wallpaperComponent);
                    com.android.server.wallpaper.WallpaperManagerService.this.clearWallpaperLocked(wallpaperData.mWhich, wallpaperData.userId, false, null);
                }
            }
            if (wallpaperData.nextWallpaperComponent != null && isPackageModified(wallpaperData.nextWallpaperComponent.getPackageName())) {
                try {
                    com.android.server.wallpaper.WallpaperManagerService.this.mContext.getPackageManager().getServiceInfo(wallpaperData.nextWallpaperComponent, 786432);
                } catch (android.content.pm.PackageManager.NameNotFoundException e2) {
                    wallpaperData.nextWallpaperComponent = null;
                }
            }
            return z2;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.wallpaper.WallpaperData getCurrentWallpaperData(int i, int i2) {
        com.android.server.wallpaper.WallpaperData wallpaperData;
        synchronized (this.mLock) {
            try {
                wallpaperData = (i == 1 ? this.mWallpaperMap : this.mLockWallpaperMap).get(i2);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return wallpaperData;
    }

    public WallpaperManagerService(android.content.Context context) {
        this.mContext = context;
        this.mImageWallpaper = android.content.ComponentName.unflattenFromString(context.getResources().getString(android.R.string.httpErrorFile));
        android.content.ComponentName cmfDefaultWallpaperComponent = android.app.WallpaperManager.getCmfDefaultWallpaperComponent(context);
        this.mDefaultWallpaperComponent = cmfDefaultWallpaperComponent == null ? this.mImageWallpaper : cmfDefaultWallpaperComponent;
        this.mWindowManagerInternal = (com.android.server.wm.WindowManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.WindowManagerInternal.class);
        this.mPackageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
        this.mIPackageManager = android.app.AppGlobals.getPackageManager();
        this.mAppOpsManager = (android.app.AppOpsManager) this.mContext.getSystemService("appops");
        android.hardware.display.DisplayManager displayManager = (android.hardware.display.DisplayManager) this.mContext.getSystemService(android.hardware.display.DisplayManager.class);
        displayManager.registerDisplayListener(this.mDisplayListener, null);
        this.mWallpaperDisplayHelper = new com.android.server.wallpaper.WallpaperDisplayHelper(displayManager, (android.view.WindowManager) this.mContext.getSystemService(android.view.WindowManager.class), this.mWindowManagerInternal, this.mContext.getResources().getIntArray(android.R.array.config_face_acquire_vendor_keyguard_ignorelist).length > 0);
        this.mWallpaperCropper = new com.android.server.wallpaper.WallpaperCropper(this.mWallpaperDisplayHelper);
        com.android.server.wm.WindowManagerInternal windowManagerInternal = this.mWindowManagerInternal;
        final com.android.server.wallpaper.WallpaperCropper wallpaperCropper = this.mWallpaperCropper;
        java.util.Objects.requireNonNull(wallpaperCropper);
        windowManagerInternal.setWallpaperCropUtils(new com.android.server.wallpaper.WallpaperCropper.WallpaperCropUtils() { // from class: com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda6
            @Override // com.android.server.wallpaper.WallpaperCropper.WallpaperCropUtils
            public final android.graphics.Rect getCrop(android.graphics.Point point, android.graphics.Point point2, android.util.SparseArray sparseArray, boolean z) {
                return com.android.server.wallpaper.WallpaperCropper.this.getCrop(point, point2, sparseArray, z);
            }
        });
        this.mActivityManager = (android.app.ActivityManager) this.mContext.getSystemService(android.app.ActivityManager.class);
        if (this.mContext.getResources().getBoolean(android.R.bool.config_oem_enabled_satellite_access_allow)) {
            java.lang.String[] stringArray = this.mContext.getResources().getStringArray(android.R.array.networks_not_clear_data);
            android.util.IntArray intArray = new android.util.IntArray();
            for (java.lang.String str : stringArray) {
                try {
                    intArray.add(this.mContext.getPackageManager().getApplicationInfo(str, 0).uid);
                } catch (java.lang.Exception e) {
                    android.util.Slog.e(TAG, e.toString());
                }
            }
            if (intArray.size() > 0) {
                try {
                    android.app.ActivityManager.getService().registerUidObserverForUids(new android.app.UidObserver() { // from class: com.android.server.wallpaper.WallpaperManagerService.2
                        public void onUidStateChanged(int i, int i2, long j, int i3) {
                            com.android.server.wallpaper.WallpaperManagerService.this.pauseOrResumeRenderingImmediately(i2 == 2);
                        }
                    }, 1, 2, com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME, intArray.toArray());
                } catch (android.os.RemoteException e2) {
                    android.util.Slog.e(TAG, e2.toString());
                }
            }
        }
        this.mMonitor = new com.android.server.wallpaper.WallpaperManagerService.MyPackageMonitor();
        this.mColorsChangedListeners = new android.util.SparseArray<>();
        this.mWallpaperDataParser = new com.android.server.wallpaper.WallpaperDataParser(this.mContext, this.mWallpaperDisplayHelper, this.mWallpaperCropper);
        com.android.server.LocalServices.addService(com.android.server.wallpaper.WallpaperManagerInternal.class, new com.android.server.wallpaper.WallpaperManagerService.LocalService());
    }

    private final class LocalService extends com.android.server.wallpaper.WallpaperManagerInternal {
        private LocalService() {
        }

        @Override // com.android.server.wallpaper.WallpaperManagerInternal
        public void onDisplayReady(int i) {
            com.android.server.wallpaper.WallpaperManagerService.this.onDisplayReadyInternal(i);
        }

        @Override // com.android.server.wallpaper.WallpaperManagerInternal
        public void onScreenTurnedOn(int i) {
            com.android.server.wallpaper.WallpaperManagerService.this.notifyScreenTurnedOn(i);
        }

        @Override // com.android.server.wallpaper.WallpaperManagerInternal
        public void onScreenTurningOn(int i) {
            com.android.server.wallpaper.WallpaperManagerService.this.notifyScreenTurningOn(i);
        }

        @Override // com.android.server.wallpaper.WallpaperManagerInternal
        public void onKeyguardGoingAway() {
            com.android.server.wallpaper.WallpaperManagerService.this.notifyKeyguardGoingAway();
        }
    }

    void initialize() {
        this.mMonitor.register(this.mContext, (android.os.Looper) null, android.os.UserHandle.ALL, true);
        com.android.server.wallpaper.WallpaperUtils.getWallpaperDir(0).mkdirs();
        loadSettingsLocked(0, false, 3);
        getWallpaperSafeLocked(0, 1);
    }

    protected void finalize() throws java.lang.Throwable {
        super.finalize();
        for (int i = 0; i < this.mWallpaperMap.size(); i++) {
            this.mWallpaperMap.valueAt(i).wallpaperObserver.stopWatching();
        }
    }

    void systemReady() {
        initialize();
        com.android.server.wallpaper.WallpaperData wallpaperData = this.mWallpaperMap.get(0);
        if (this.mImageWallpaper.equals(wallpaperData.nextWallpaperComponent)) {
            if (!wallpaperData.cropExists()) {
                this.mWallpaperCropper.generateCrop(wallpaperData);
            }
            if (!wallpaperData.cropExists()) {
                clearWallpaperLocked(wallpaperData.mWhich, 0, false, null);
            }
        }
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.USER_REMOVED");
        this.mContext.registerReceiver(new android.content.BroadcastReceiver() { // from class: com.android.server.wallpaper.WallpaperManagerService.3
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context, android.content.Intent intent) {
                if ("android.intent.action.USER_REMOVED".equals(intent.getAction())) {
                    com.android.server.wallpaper.WallpaperManagerService.this.onRemoveUser(intent.getIntExtra("android.intent.extra.user_handle", com.android.server.am.ProcessList.INVALID_ADJ));
                }
            }
        }, intentFilter);
        this.mContext.registerReceiver(new android.content.BroadcastReceiver() { // from class: com.android.server.wallpaper.WallpaperManagerService.4
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context, android.content.Intent intent) {
                if ("android.intent.action.ACTION_SHUTDOWN".equals(intent.getAction())) {
                    synchronized (com.android.server.wallpaper.WallpaperManagerService.this.mLock) {
                        com.android.server.wallpaper.WallpaperManagerService.this.mShuttingDown = true;
                    }
                }
            }
        }, new android.content.IntentFilter("android.intent.action.ACTION_SHUTDOWN"));
        try {
            android.app.ActivityManager.getService().registerUserSwitchObserver(new android.app.UserSwitchObserver() { // from class: com.android.server.wallpaper.WallpaperManagerService.5
                public void onUserSwitching(int i, android.os.IRemoteCallback iRemoteCallback) {
                    com.android.server.wallpaper.WallpaperManagerService.this.errorCheck(i);
                    com.android.server.wallpaper.WallpaperManagerService.this.switchUser(i, iRemoteCallback);
                }
            }, TAG);
        } catch (android.os.RemoteException e) {
            e.rethrowAsRuntimeException();
        }
    }

    public java.lang.String getName() {
        java.lang.String str;
        if (android.os.Binder.getCallingUid() != 1000) {
            throw new java.lang.RuntimeException("getName() can only be called from the system process");
        }
        synchronized (this.mLock) {
            str = this.mWallpaperMap.get(0).name;
        }
        return str;
    }

    void stopObserver(com.android.server.wallpaper.WallpaperData wallpaperData) {
        if (wallpaperData != null && wallpaperData.wallpaperObserver != null) {
            wallpaperData.wallpaperObserver.stopWatching();
            wallpaperData.wallpaperObserver = null;
        }
    }

    void stopObserversLocked(int i) {
        stopObserver(this.mWallpaperMap.get(i));
        stopObserver(this.mLockWallpaperMap.get(i));
        this.mWallpaperMap.remove(i);
        this.mLockWallpaperMap.remove(i);
    }

    @Override // com.android.server.wallpaper.IWallpaperManagerService
    public void onBootPhase(int i) {
        errorCheck(0);
        if (i == 550) {
            systemReady();
        } else if (i == 600) {
            switchUser(0, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void errorCheck(final int i) {
        sWallpaperType.forEach(new java.util.function.BiConsumer() { // from class: com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda16
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                com.android.server.wallpaper.WallpaperManagerService.this.lambda$errorCheck$4(i, (java.lang.Integer) obj, (java.lang.String) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$errorCheck$4(int i, java.lang.Integer num, java.lang.String str) {
        java.io.File file = new java.io.File(com.android.server.wallpaper.WallpaperUtils.getWallpaperDir(i), str);
        if (file.exists()) {
            android.util.Slog.w(TAG, "User:" + i + ", wallpaper type = " + num + ", wallpaper fail detect!! reset to default wallpaper");
            clearWallpaperBitmaps(i, num.intValue());
            file.delete();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearWallpaperBitmaps(int i, int i2) {
        clearWallpaperBitmaps(new com.android.server.wallpaper.WallpaperData(i, i2));
    }

    private boolean clearWallpaperBitmaps(com.android.server.wallpaper.WallpaperData wallpaperData) {
        boolean sourceExists = wallpaperData.sourceExists();
        boolean cropExists = wallpaperData.cropExists();
        if (sourceExists) {
            wallpaperData.getWallpaperFile().delete();
        }
        if (cropExists) {
            wallpaperData.getCropFile().delete();
        }
        return sourceExists || cropExists;
    }

    @Override // com.android.server.wallpaper.IWallpaperManagerService
    public void onUnlockUser(final int i) {
        synchronized (this.mLock) {
            try {
                if (this.mCurrentUserId == i) {
                    if (this.mHomeWallpaperWaitingForUnlock) {
                        com.android.server.wallpaper.WallpaperData wallpaperSafeLocked = getWallpaperSafeLocked(i, 1);
                        wallpaperSafeLocked.mBindSource = com.android.server.wallpaper.WallpaperData.BindSource.SWITCH_WALLPAPER_UNLOCK_USER;
                        switchWallpaper(wallpaperSafeLocked, null);
                        notifyCallbacksLocked(wallpaperSafeLocked);
                    }
                    if (this.mLockWallpaperWaitingForUnlock) {
                        com.android.server.wallpaper.WallpaperData wallpaperSafeLocked2 = getWallpaperSafeLocked(i, 2);
                        wallpaperSafeLocked2.mBindSource = com.android.server.wallpaper.WallpaperData.BindSource.SWITCH_WALLPAPER_UNLOCK_USER;
                        switchWallpaper(wallpaperSafeLocked2, null);
                        notifyCallbacksLocked(wallpaperSafeLocked2);
                    }
                    if (!this.mUserRestorecon.get(i)) {
                        this.mUserRestorecon.put(i, true);
                        com.android.internal.os.BackgroundThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda7
                            @Override // java.lang.Runnable
                            public final void run() {
                                com.android.server.wallpaper.WallpaperManagerService.lambda$onUnlockUser$5(i);
                            }
                        });
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onUnlockUser$5(int i) {
        com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog = new com.android.server.utils.TimingsTraceAndSlog(TAG);
        timingsTraceAndSlog.traceBegin("Wallpaper_selinux_restorecon-" + i);
        try {
            for (java.io.File file : com.android.server.wallpaper.WallpaperUtils.getWallpaperFiles(i)) {
                if (file.exists()) {
                    android.os.SELinux.restorecon(file);
                }
            }
        } finally {
            timingsTraceAndSlog.traceEnd();
        }
    }

    void onRemoveUser(int i) {
        if (i < 1) {
            return;
        }
        synchronized (this.mLock) {
            stopObserversLocked(i);
            com.android.server.wallpaper.WallpaperUtils.getWallpaperFiles(i).forEach(new java.util.function.Consumer() { // from class: com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda13
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((java.io.File) obj).delete();
                }
            });
            this.mUserRestorecon.delete(i);
        }
    }

    void switchUser(int i, android.os.IRemoteCallback iRemoteCallback) {
        com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog = new com.android.server.utils.TimingsTraceAndSlog(TAG);
        timingsTraceAndSlog.traceBegin("Wallpaper_switch-user-" + i);
        try {
            synchronized (this.mLock) {
                if (this.mCurrentUserId == i) {
                    return;
                }
                this.mCurrentUserId = i;
                boolean z = true;
                final com.android.server.wallpaper.WallpaperData wallpaperSafeLocked = getWallpaperSafeLocked(i, 1);
                final com.android.server.wallpaper.WallpaperData wallpaperSafeLocked2 = wallpaperSafeLocked.mWhich == 3 ? wallpaperSafeLocked : getWallpaperSafeLocked(i, 2);
                if (wallpaperSafeLocked.wallpaperObserver == null) {
                    wallpaperSafeLocked.wallpaperObserver = new com.android.server.wallpaper.WallpaperManagerService.WallpaperObserver(wallpaperSafeLocked);
                    wallpaperSafeLocked.wallpaperObserver.startWatching();
                }
                if (android.multiuser.Flags.reorderWallpaperDuringUserSwitch()) {
                    detachWallpaperLocked(this.mLastLockWallpaper);
                    detachWallpaperLocked(this.mLastWallpaper);
                    if (wallpaperSafeLocked2 == wallpaperSafeLocked) {
                        switchWallpaper(wallpaperSafeLocked, iRemoteCallback);
                    } else {
                        android.app.KeyguardManager keyguardManager = (android.app.KeyguardManager) this.mContext.getSystemService(android.app.KeyguardManager.class);
                        if (keyguardManager == null || !keyguardManager.isDeviceSecure(i)) {
                            z = false;
                        }
                        switchWallpaper(z ? wallpaperSafeLocked2 : wallpaperSafeLocked, iRemoteCallback);
                        switchWallpaper(z ? wallpaperSafeLocked : wallpaperSafeLocked2, null);
                    }
                } else {
                    if (wallpaperSafeLocked2 != wallpaperSafeLocked) {
                        switchWallpaper(wallpaperSafeLocked2, null);
                    }
                    switchWallpaper(wallpaperSafeLocked, iRemoteCallback);
                }
                this.mInitialUserSwitch = false;
                com.android.server.FgThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda9
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.wallpaper.WallpaperManagerService.this.lambda$switchUser$6(wallpaperSafeLocked, wallpaperSafeLocked2);
                    }
                });
            }
        } finally {
            timingsTraceAndSlog.traceEnd();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$switchUser$6(com.android.server.wallpaper.WallpaperData wallpaperData, com.android.server.wallpaper.WallpaperData wallpaperData2) {
        notifyWallpaperColorsChanged(wallpaperData);
        if (wallpaperData2 != wallpaperData) {
            notifyWallpaperColorsChanged(wallpaperData2);
        }
        notifyWallpaperColorsChanged(this.mFallbackWallpaper);
    }

    void switchWallpaper(com.android.server.wallpaper.WallpaperData wallpaperData, android.os.IRemoteCallback iRemoteCallback) {
        android.content.pm.ServiceInfo serviceInfo;
        synchronized (this.mLock) {
            try {
                if ((wallpaperData.mWhich & 1) != 0) {
                    this.mHomeWallpaperWaitingForUnlock = false;
                }
                if ((wallpaperData.mWhich & 2) != 0) {
                    this.mLockWallpaperWaitingForUnlock = false;
                }
                android.content.ComponentName componentName = wallpaperData.wallpaperComponent != null ? wallpaperData.wallpaperComponent : wallpaperData.nextWallpaperComponent;
                if (!bindWallpaperComponentLocked(componentName, true, false, wallpaperData, iRemoteCallback)) {
                    try {
                        serviceInfo = this.mIPackageManager.getServiceInfo(componentName, 262144L, wallpaperData.userId);
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.w(TAG, "Failure starting previous wallpaper; clearing", e);
                        serviceInfo = null;
                    }
                    onSwitchWallpaperFailLocked(wallpaperData, iRemoteCallback, serviceInfo);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void onSwitchWallpaperFailLocked(com.android.server.wallpaper.WallpaperData wallpaperData, android.os.IRemoteCallback iRemoteCallback, android.content.pm.ServiceInfo serviceInfo) {
        if (serviceInfo == null) {
            clearWallpaperLocked(wallpaperData.mWhich, wallpaperData.userId, false, iRemoteCallback);
            return;
        }
        android.util.Slog.w(TAG, "Wallpaper isn't direct boot aware; using fallback until unlocked");
        wallpaperData.wallpaperComponent = wallpaperData.nextWallpaperComponent;
        com.android.server.wallpaper.WallpaperData wallpaperData2 = new com.android.server.wallpaper.WallpaperData(wallpaperData.userId, wallpaperData.mWhich);
        clearWallpaperBitmaps(wallpaperData);
        wallpaperData2.mBindSource = com.android.server.wallpaper.WallpaperData.BindSource.SWITCH_WALLPAPER_FAILURE;
        bindWallpaperComponentLocked(this.mImageWallpaper, true, false, wallpaperData2, iRemoteCallback);
        if ((wallpaperData.mWhich & 1) != 0) {
            this.mHomeWallpaperWaitingForUnlock = true;
        }
        if ((wallpaperData.mWhich & 2) != 0) {
            this.mLockWallpaperWaitingForUnlock = true;
        }
    }

    public void clearWallpaper(java.lang.String str, int i, int i2) {
        checkPermission("android.permission.SET_WALLPAPER");
        if (!isWallpaperSupported(str) || !isSetWallpaperAllowed(str)) {
            return;
        }
        int handleIncomingUser = android.app.ActivityManager.handleIncomingUser(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i2, false, true, "clearWallpaper", null);
        synchronized (this.mLock) {
            try {
                com.android.server.wallpaper.WallpaperData wallpaperData = null;
                clearWallpaperLocked(i, handleIncomingUser, isFromForegroundApp(str), null);
                if (i == 2) {
                    wallpaperData = this.mLockWallpaperMap.get(handleIncomingUser);
                }
                if (i == 1 || wallpaperData == null) {
                    this.mWallpaperMap.get(handleIncomingUser);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearWallpaperLocked(int i, final int i2, final boolean z, final android.os.IRemoteCallback iRemoteCallback) {
        int i3;
        android.content.ComponentName componentName;
        if (!this.mWallpaperMap.contains(i2)) {
            loadSettingsLocked(i2, false, 3);
        }
        final com.android.server.wallpaper.WallpaperData wallpaperData = this.mWallpaperMap.get(i2);
        com.android.server.wallpaper.WallpaperData wallpaperData2 = this.mLockWallpaperMap.get(i2);
        if (i == 2 && wallpaperData2 == null) {
            return;
        }
        try {
        } catch (java.lang.IllegalArgumentException e) {
            e = e;
        }
        if (i2 == this.mCurrentUserId || hasCrossUserPermission()) {
            if ((i & 2) > 0 && wallpaperData2 != null) {
                clearWallpaperBitmaps(wallpaperData2);
            }
            if ((i & 1) > 0) {
                clearWallpaperBitmaps(wallpaperData);
            }
            if (i == 2) {
                componentName = wallpaperData.wallpaperComponent;
                i3 = 3;
            } else {
                i3 = i;
                componentName = null;
            }
            final android.content.ComponentName componentName2 = componentName;
            final int i4 = i3;
            final boolean z2 = i != 2;
            if (((java.lang.Boolean) android.app.IWallpaperManager.Stub.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda14
                public final java.lang.Object getOrThrow() {
                    java.lang.Boolean lambda$clearWallpaperLocked$7;
                    lambda$clearWallpaperLocked$7 = com.android.server.wallpaper.WallpaperManagerService.this.lambda$clearWallpaperLocked$7(componentName2, i4, i2, z2, z, iRemoteCallback);
                    return lambda$clearWallpaperLocked$7;
                }
            })).booleanValue()) {
                return;
            }
            e = null;
            android.util.Slog.e(TAG, "Default wallpaper component not found!", e);
            android.app.IWallpaperManager.Stub.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda15
                public final void runOrThrow() {
                    com.android.server.wallpaper.WallpaperManagerService.this.lambda$clearWallpaperLocked$8(wallpaperData);
                }
            });
            if (iRemoteCallback != null) {
                try {
                    iRemoteCallback.sendResult((android.os.Bundle) null);
                } catch (android.os.RemoteException e2) {
                    android.util.Slog.w(TAG, "Failed to notify callback after wallpaper clear", e2);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Boolean lambda$clearWallpaperLocked$7(android.content.ComponentName componentName, int i, int i2, boolean z, boolean z2, android.os.IRemoteCallback iRemoteCallback) throws java.lang.Exception {
        return java.lang.Boolean.valueOf(setWallpaperComponentInternal(componentName, i, i2, z, z2, iRemoteCallback));
    }

    private boolean hasCrossUserPermission() {
        return this.mContext.checkCallingPermission("android.permission.INTERACT_ACROSS_USERS_FULL") == 0;
    }

    public boolean hasNamedWallpaper(java.lang.String str) {
        int callingUserId = android.os.UserHandle.getCallingUserId();
        boolean hasCrossUserPermission = hasCrossUserPermission();
        synchronized (this.mLock) {
            try {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    java.util.List<android.content.pm.UserInfo> users = ((android.os.UserManager) this.mContext.getSystemService("user")).getUsers();
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    for (android.content.pm.UserInfo userInfo : users) {
                        if (hasCrossUserPermission || callingUserId == userInfo.id) {
                            if (!userInfo.isProfile()) {
                                com.android.server.wallpaper.WallpaperData wallpaperData = this.mWallpaperMap.get(userInfo.id);
                                if (wallpaperData == null) {
                                    loadSettingsLocked(userInfo.id, false, 3);
                                    wallpaperData = this.mWallpaperMap.get(userInfo.id);
                                }
                                if (wallpaperData != null && str.equals(wallpaperData.name)) {
                                    return true;
                                }
                            }
                        }
                    }
                    return false;
                } catch (java.lang.Throwable th) {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            } catch (java.lang.Throwable th2) {
                throw th2;
            }
        }
    }

    public void setDimensionHints(int i, int i2, java.lang.String str, int i3) throws android.os.RemoteException {
        checkPermission("android.permission.SET_WALLPAPER_HINTS");
        if (!isWallpaperSupported(str)) {
            return;
        }
        int min = java.lang.Math.min(i, com.android.server.wallpaper.GLHelper.getMaxTextureSize());
        int min2 = java.lang.Math.min(i2, com.android.server.wallpaper.GLHelper.getMaxTextureSize());
        synchronized (this.mLock) {
            try {
                int callingUserId = android.os.UserHandle.getCallingUserId();
                com.android.server.wallpaper.WallpaperData wallpaperSafeLocked = getWallpaperSafeLocked(callingUserId, 1);
                if (min <= 0 || min2 <= 0) {
                    throw new java.lang.IllegalArgumentException("width and height must be > 0");
                }
                if (!this.mWallpaperDisplayHelper.isValidDisplay(i3)) {
                    throw new java.lang.IllegalArgumentException("Cannot find display with id=" + i3);
                }
                com.android.server.wallpaper.WallpaperDisplayHelper.DisplayData displayDataOrCreate = this.mWallpaperDisplayHelper.getDisplayDataOrCreate(i3);
                if (min != displayDataOrCreate.mWidth || min2 != displayDataOrCreate.mHeight) {
                    displayDataOrCreate.mWidth = min;
                    displayDataOrCreate.mHeight = min2;
                    if (i3 == 0) {
                        saveSettingsLocked(callingUserId);
                    }
                    if (this.mCurrentUserId != callingUserId) {
                        return;
                    }
                    if (wallpaperSafeLocked.connection != null) {
                        com.android.server.wallpaper.WallpaperManagerService.DisplayConnector displayConnectorOrCreate = wallpaperSafeLocked.connection.getDisplayConnectorOrCreate(i3);
                        android.service.wallpaper.IWallpaperEngine iWallpaperEngine = displayConnectorOrCreate != null ? displayConnectorOrCreate.mEngine : null;
                        if (iWallpaperEngine != null) {
                            try {
                                iWallpaperEngine.setDesiredSize(min, min2);
                            } catch (android.os.RemoteException e) {
                                android.util.Slog.w(TAG, "Failed to set desired size", e);
                            }
                            notifyCallbacksLocked(wallpaperSafeLocked);
                        } else if (wallpaperSafeLocked.connection.mService != null && displayConnectorOrCreate != null) {
                            displayConnectorOrCreate.mDimensionsChanged = true;
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public int getWidthHint(int i) throws android.os.RemoteException {
        synchronized (this.mLock) {
            try {
                if (!this.mWallpaperDisplayHelper.isValidDisplay(i)) {
                    throw new java.lang.IllegalArgumentException("Cannot find display with id=" + i);
                }
                if (this.mWallpaperMap.get(android.os.UserHandle.getCallingUserId()) == null) {
                    return 0;
                }
                return this.mWallpaperDisplayHelper.getDisplayDataOrCreate(i).mWidth;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public int getHeightHint(int i) throws android.os.RemoteException {
        synchronized (this.mLock) {
            try {
                if (!this.mWallpaperDisplayHelper.isValidDisplay(i)) {
                    throw new java.lang.IllegalArgumentException("Cannot find display with id=" + i);
                }
                if (this.mWallpaperMap.get(android.os.UserHandle.getCallingUserId()) == null) {
                    return 0;
                }
                return this.mWallpaperDisplayHelper.getDisplayDataOrCreate(i).mHeight;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void setDisplayPadding(android.graphics.Rect rect, java.lang.String str, int i) {
        checkPermission("android.permission.SET_WALLPAPER_HINTS");
        if (!isWallpaperSupported(str)) {
            return;
        }
        synchronized (this.mLock) {
            try {
                if (!this.mWallpaperDisplayHelper.isValidDisplay(i)) {
                    throw new java.lang.IllegalArgumentException("Cannot find display with id=" + i);
                }
                int callingUserId = android.os.UserHandle.getCallingUserId();
                com.android.server.wallpaper.WallpaperData wallpaperSafeLocked = getWallpaperSafeLocked(callingUserId, 1);
                if (rect.left < 0 || rect.top < 0 || rect.right < 0 || rect.bottom < 0) {
                    throw new java.lang.IllegalArgumentException("padding must be positive: " + rect);
                }
                int maximumSizeDimension = this.mWallpaperDisplayHelper.getMaximumSizeDimension(i);
                int i2 = rect.left + rect.right;
                int i3 = rect.top + rect.bottom;
                if (i2 > maximumSizeDimension) {
                    throw new java.lang.IllegalArgumentException("padding width " + i2 + " exceeds max width " + maximumSizeDimension);
                }
                if (i3 > maximumSizeDimension) {
                    throw new java.lang.IllegalArgumentException("padding height " + i3 + " exceeds max height " + maximumSizeDimension);
                }
                com.android.server.wallpaper.WallpaperDisplayHelper.DisplayData displayDataOrCreate = this.mWallpaperDisplayHelper.getDisplayDataOrCreate(i);
                if (!rect.equals(displayDataOrCreate.mPadding)) {
                    displayDataOrCreate.mPadding.set(rect);
                    if (i == 0) {
                        saveSettingsLocked(callingUserId);
                    }
                    if (this.mCurrentUserId != callingUserId) {
                        return;
                    }
                    if (wallpaperSafeLocked.connection != null) {
                        com.android.server.wallpaper.WallpaperManagerService.DisplayConnector displayConnectorOrCreate = wallpaperSafeLocked.connection.getDisplayConnectorOrCreate(i);
                        android.service.wallpaper.IWallpaperEngine iWallpaperEngine = displayConnectorOrCreate != null ? displayConnectorOrCreate.mEngine : null;
                        if (iWallpaperEngine != null) {
                            try {
                                iWallpaperEngine.setDisplayPadding(rect);
                            } catch (android.os.RemoteException e) {
                                android.util.Slog.w(TAG, "Failed to set display padding", e);
                            }
                            notifyCallbacksLocked(wallpaperSafeLocked);
                        } else if (wallpaperSafeLocked.connection.mService != null && displayConnectorOrCreate != null) {
                            displayConnectorOrCreate.mPaddingChanged = true;
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @java.lang.Deprecated
    public android.os.ParcelFileDescriptor getWallpaper(java.lang.String str, android.app.IWallpaperManagerCallback iWallpaperManagerCallback, int i, android.os.Bundle bundle, int i2) {
        return getWallpaperWithFeature(str, null, iWallpaperManagerCallback, i, bundle, i2, true);
    }

    public android.os.ParcelFileDescriptor getWallpaperWithFeature(java.lang.String str, java.lang.String str2, android.app.IWallpaperManagerCallback iWallpaperManagerCallback, int i, android.os.Bundle bundle, int i2, boolean z) {
        android.util.SparseArray<com.android.server.wallpaper.WallpaperData> sparseArray;
        if (!hasPermission("android.permission.READ_WALLPAPER_INTERNAL")) {
            ((android.os.storage.StorageManager) this.mContext.getSystemService(android.os.storage.StorageManager.class)).checkPermissionReadImages(true, android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), str, str2);
        }
        int handleIncomingUser = android.app.ActivityManager.handleIncomingUser(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i2, false, true, "getWallpaper", null);
        if (i != 1 && i != 2) {
            throw new java.lang.IllegalArgumentException("Must specify exactly one kind of wallpaper to read");
        }
        synchronized (this.mLock) {
            try {
                if (i == 2) {
                    sparseArray = this.mLockWallpaperMap;
                } else {
                    sparseArray = this.mWallpaperMap;
                }
                com.android.server.wallpaper.WallpaperData wallpaperData = sparseArray.get(handleIncomingUser);
                if (wallpaperData == null) {
                    return null;
                }
                com.android.server.wallpaper.WallpaperDisplayHelper.DisplayData displayDataOrCreate = this.mWallpaperDisplayHelper.getDisplayDataOrCreate(0);
                if (bundle != null) {
                    try {
                        bundle.putInt("width", displayDataOrCreate.mWidth);
                        bundle.putInt("height", displayDataOrCreate.mHeight);
                    } catch (java.io.FileNotFoundException e) {
                        android.util.Slog.w(TAG, "Error getting wallpaper", e);
                        return null;
                    }
                }
                if (iWallpaperManagerCallback != null) {
                    wallpaperData.callbacks.register(iWallpaperManagerCallback);
                }
                java.io.File cropFile = z ? wallpaperData.getCropFile() : wallpaperData.getWallpaperFile();
                if (!cropFile.exists()) {
                    return null;
                }
                return android.os.ParcelFileDescriptor.open(cropFile, 268435456);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public java.util.List<android.graphics.Rect> getBitmapCrops(java.util.List<android.graphics.Point> list, int i, boolean z, int i2) {
        int handleIncomingUser = android.app.ActivityManager.handleIncomingUser(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i2, false, true, "getBitmapCrop", null);
        synchronized (this.mLock) {
            try {
                checkPermission("android.permission.READ_WALLPAPER_INTERNAL");
                com.android.server.wallpaper.WallpaperData wallpaperData = i == 2 ? this.mLockWallpaperMap.get(handleIncomingUser) : this.mWallpaperMap.get(handleIncomingUser);
                if (wallpaperData == null || !wallpaperData.mSupportsMultiCrop) {
                    return null;
                }
                android.util.SparseArray<android.graphics.Rect> relativeCropHints = this.mWallpaperCropper.getRelativeCropHints(wallpaperData);
                android.graphics.Point point = new android.graphics.Point((int) ((wallpaperData.cropHint.width() / wallpaperData.mSampleSize) + 0.5f), (int) ((wallpaperData.cropHint.height() / wallpaperData.mSampleSize) + 0.5f));
                android.util.SparseArray<android.graphics.Rect> defaultCrops = this.mWallpaperCropper.getDefaultCrops(relativeCropHints, point);
                android.util.SparseArray<android.graphics.Rect> sparseArray = new android.util.SparseArray<>();
                for (int i3 = 0; i3 < defaultCrops.size(); i3++) {
                    int keyAt = defaultCrops.keyAt(i3);
                    if (relativeCropHints.contains(keyAt)) {
                        sparseArray.put(keyAt, defaultCrops.get(keyAt));
                    }
                }
                java.util.List<android.graphics.Rect> arrayList = new java.util.ArrayList<>();
                boolean z2 = android.text.TextUtils.getLayoutDirectionFromLocale(java.util.Locale.getDefault()) == 1;
                java.util.Iterator<android.graphics.Point> it = list.iterator();
                while (it.hasNext()) {
                    arrayList.add(this.mWallpaperCropper.getCrop(it.next(), point, sparseArray, z2));
                }
                if (z) {
                    arrayList = com.android.server.wallpaper.WallpaperCropper.getOriginalCropHints(wallpaperData, arrayList);
                }
                return arrayList;
            } finally {
            }
        }
    }

    public java.util.List<android.graphics.Rect> getFutureBitmapCrops(android.graphics.Point point, java.util.List<android.graphics.Point> list, int[] iArr, java.util.List<android.graphics.Rect> list2) {
        android.util.SparseArray<android.graphics.Rect> defaultCrops = this.mWallpaperCropper.getDefaultCrops(getCropMap(iArr, list2, -1), point);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        boolean z = android.text.TextUtils.getLayoutDirectionFromLocale(java.util.Locale.getDefault()) == 1;
        java.util.Iterator<android.graphics.Point> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(this.mWallpaperCropper.getCrop(it.next(), point, defaultCrops, z));
        }
        return arrayList;
    }

    public android.graphics.Rect getBitmapCrop(android.graphics.Point point, int[] iArr, java.util.List<android.graphics.Rect> list) {
        if (!com.android.window.flags.Flags.multiCrop()) {
            throw new java.lang.UnsupportedOperationException("This method should only be called with the multi crop flag enabled");
        }
        return com.android.server.wallpaper.WallpaperCropper.getTotalCrop(this.mWallpaperCropper.getDefaultCrops(getCropMap(iArr, list, -1), point));
    }

    private boolean hasPermission(java.lang.String str) {
        return this.mContext.checkCallingOrSelfPermission(str) == 0;
    }

    public android.app.WallpaperInfo getWallpaperInfo(int i) {
        return getWallpaperInfoWithFlags(1, i);
    }

    public android.app.WallpaperInfo getWallpaperInfoWithFlags(int i, int i2) {
        int handleIncomingUser = android.app.ActivityManager.handleIncomingUser(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i2, false, true, "getWallpaperInfo", null);
        synchronized (this.mLock) {
            try {
                com.android.server.wallpaper.WallpaperData wallpaperData = i == 2 ? this.mLockWallpaperMap.get(handleIncomingUser) : this.mWallpaperMap.get(handleIncomingUser);
                if (wallpaperData == null || wallpaperData.connection == null || wallpaperData.connection.mInfo == null) {
                    return null;
                }
                android.app.WallpaperInfo wallpaperInfo = wallpaperData.connection.mInfo;
                if (!hasPermission("android.permission.READ_WALLPAPER_INTERNAL") && !this.mPackageManagerInternal.canQueryPackage(android.os.Binder.getCallingUid(), wallpaperInfo.getComponent().getPackageName())) {
                    return null;
                }
                return wallpaperInfo;
            } finally {
            }
        }
    }

    public android.os.ParcelFileDescriptor getWallpaperInfoFile(int i) {
        synchronized (this.mLock) {
            try {
                java.io.File file = new java.io.File(com.android.server.wallpaper.WallpaperUtils.getWallpaperDir(i), "wallpaper_info.xml");
                if (!file.exists()) {
                    return null;
                }
                return android.os.ParcelFileDescriptor.open(file, 268435456);
            } catch (java.io.FileNotFoundException e) {
                android.util.Slog.w(TAG, "Error getting wallpaper info file", e);
                return null;
            }
        }
    }

    public int getWallpaperIdForUser(int i, int i2) {
        int handleIncomingUser = android.app.ActivityManager.handleIncomingUser(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i2, false, true, "getWallpaperIdForUser", null);
        if (i != 1 && i != 2) {
            throw new java.lang.IllegalArgumentException("Must specify exactly one kind of wallpaper");
        }
        android.util.SparseArray<com.android.server.wallpaper.WallpaperData> sparseArray = i == 2 ? this.mLockWallpaperMap : this.mWallpaperMap;
        synchronized (this.mLock) {
            try {
                com.android.server.wallpaper.WallpaperData wallpaperData = sparseArray.get(handleIncomingUser);
                if (wallpaperData != null) {
                    return wallpaperData.wallpaperId;
                }
                return -1;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void registerWallpaperColorsCallback(android.app.IWallpaperManagerCallback iWallpaperManagerCallback, int i, int i2) {
        int handleIncomingUser = android.app.ActivityManager.handleIncomingUser(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, true, true, "registerWallpaperColorsCallback", null);
        synchronized (this.mLock) {
            try {
                android.util.SparseArray<android.os.RemoteCallbackList<android.app.IWallpaperManagerCallback>> sparseArray = this.mColorsChangedListeners.get(handleIncomingUser);
                if (sparseArray == null) {
                    sparseArray = new android.util.SparseArray<>();
                    this.mColorsChangedListeners.put(handleIncomingUser, sparseArray);
                }
                android.os.RemoteCallbackList<android.app.IWallpaperManagerCallback> remoteCallbackList = sparseArray.get(i2);
                if (remoteCallbackList == null) {
                    remoteCallbackList = new android.os.RemoteCallbackList<>();
                    sparseArray.put(i2, remoteCallbackList);
                }
                remoteCallbackList.register(iWallpaperManagerCallback);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void unregisterWallpaperColorsCallback(android.app.IWallpaperManagerCallback iWallpaperManagerCallback, int i, int i2) {
        android.os.RemoteCallbackList<android.app.IWallpaperManagerCallback> remoteCallbackList;
        int handleIncomingUser = android.app.ActivityManager.handleIncomingUser(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, true, true, "unregisterWallpaperColorsCallback", null);
        synchronized (this.mLock) {
            try {
                android.util.SparseArray<android.os.RemoteCallbackList<android.app.IWallpaperManagerCallback>> sparseArray = this.mColorsChangedListeners.get(handleIncomingUser);
                if (sparseArray != null && (remoteCallbackList = sparseArray.get(i2)) != null) {
                    remoteCallbackList.unregister(iWallpaperManagerCallback);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void setInAmbientMode(boolean z, long j) {
        int i;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mLock) {
            try {
                this.mInAmbientMode = z;
                for (com.android.server.wallpaper.WallpaperData wallpaperData : getActiveWallpapers()) {
                    i = (wallpaperData.connection.mInfo == null || wallpaperData.connection.mInfo.supportsAmbientMode()) ? 0 : i + 1;
                    android.service.wallpaper.IWallpaperEngine iWallpaperEngine = wallpaperData.connection.getDisplayConnectorOrCreate(0).mEngine;
                    if (iWallpaperEngine != null) {
                        arrayList.add(iWallpaperEngine);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        java.util.Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            try {
                ((android.service.wallpaper.IWallpaperEngine) it.next()).setInAmbientMode(z, j);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(TAG, "Failed to set ambient mode", e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void pauseOrResumeRenderingImmediately(final boolean z) {
        synchronized (this.mLock) {
            try {
                for (com.android.server.wallpaper.WallpaperData wallpaperData : getActiveWallpapers()) {
                    if (wallpaperData.connection.mInfo != null) {
                        if (!z && !((com.android.server.wm.ActivityTaskManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.ActivityTaskManagerInternal.class)).isUidForeground(wallpaperData.connection.mInfo.getServiceInfo().applicationInfo.uid)) {
                        }
                        if (wallpaperData.connection.containsDisplay(this.mWindowManagerInternal.getTopFocusedDisplayId())) {
                            wallpaperData.connection.forEachDisplayConnector(new java.util.function.Consumer() { // from class: com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda1
                                @Override // java.util.function.Consumer
                                public final void accept(java.lang.Object obj) {
                                    com.android.server.wallpaper.WallpaperManagerService.lambda$pauseOrResumeRenderingImmediately$9(z, (com.android.server.wallpaper.WallpaperManagerService.DisplayConnector) obj);
                                }
                            });
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$pauseOrResumeRenderingImmediately$9(boolean z, com.android.server.wallpaper.WallpaperManagerService.DisplayConnector displayConnector) {
        if (displayConnector.mEngine != null) {
            try {
                displayConnector.mEngine.setVisibility(!z);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(TAG, "Failed to set visibility", e);
            }
        }
    }

    public void notifyWakingUp(final int i, final int i2, @android.annotation.NonNull final android.os.Bundle bundle) {
        checkCallerIsSystemOrSystemUi();
        synchronized (this.mLock) {
            try {
                for (com.android.server.wallpaper.WallpaperData wallpaperData : getActiveWallpapers()) {
                    wallpaperData.connection.forEachDisplayConnector(new java.util.function.Consumer() { // from class: com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda11
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            com.android.server.wallpaper.WallpaperManagerService.lambda$notifyWakingUp$10(i, i2, bundle, (com.android.server.wallpaper.WallpaperManagerService.DisplayConnector) obj);
                        }
                    });
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$notifyWakingUp$10(int i, int i2, android.os.Bundle bundle, com.android.server.wallpaper.WallpaperManagerService.DisplayConnector displayConnector) {
        if (displayConnector.mEngine != null) {
            try {
                displayConnector.mEngine.dispatchWallpaperCommand("android.wallpaper.wakingup", i, i2, -1, bundle);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(TAG, "Failed to dispatch COMMAND_WAKING_UP", e);
            }
        }
    }

    public void notifyGoingToSleep(final int i, final int i2, @android.annotation.NonNull final android.os.Bundle bundle) {
        checkCallerIsSystemOrSystemUi();
        synchronized (this.mLock) {
            try {
                for (com.android.server.wallpaper.WallpaperData wallpaperData : getActiveWallpapers()) {
                    wallpaperData.connection.forEachDisplayConnector(new java.util.function.Consumer() { // from class: com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda21
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            com.android.server.wallpaper.WallpaperManagerService.lambda$notifyGoingToSleep$11(i, i2, bundle, (com.android.server.wallpaper.WallpaperManagerService.DisplayConnector) obj);
                        }
                    });
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$notifyGoingToSleep$11(int i, int i2, android.os.Bundle bundle, com.android.server.wallpaper.WallpaperManagerService.DisplayConnector displayConnector) {
        if (displayConnector.mEngine != null) {
            try {
                displayConnector.mEngine.dispatchWallpaperCommand("android.wallpaper.goingtosleep", i, i2, -1, bundle);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(TAG, "Failed to dispatch COMMAND_GOING_TO_SLEEP", e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyScreenTurnedOn(int i) {
        android.service.wallpaper.IWallpaperEngine iWallpaperEngine;
        synchronized (this.mLock) {
            for (com.android.server.wallpaper.WallpaperData wallpaperData : getActiveWallpapers()) {
                if (wallpaperData.connection.containsDisplay(i) && (iWallpaperEngine = wallpaperData.connection.getDisplayConnectorOrCreate(i).mEngine) != null) {
                    try {
                        iWallpaperEngine.onScreenTurnedOn();
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.w(TAG, "Failed to notify that the screen turned on", e);
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyScreenTurningOn(int i) {
        android.service.wallpaper.IWallpaperEngine iWallpaperEngine;
        synchronized (this.mLock) {
            for (com.android.server.wallpaper.WallpaperData wallpaperData : getActiveWallpapers()) {
                if (wallpaperData.connection.containsDisplay(i) && (iWallpaperEngine = wallpaperData.connection.getDisplayConnectorOrCreate(i).mEngine) != null) {
                    try {
                        iWallpaperEngine.onScreenTurningOn();
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.w(TAG, "Failed to notify that the screen is turning on", e);
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyKeyguardGoingAway() {
        synchronized (this.mLock) {
            try {
                for (com.android.server.wallpaper.WallpaperData wallpaperData : getActiveWallpapers()) {
                    wallpaperData.connection.forEachDisplayConnector(new java.util.function.Consumer() { // from class: com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda10
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            com.android.server.wallpaper.WallpaperManagerService.lambda$notifyKeyguardGoingAway$12((com.android.server.wallpaper.WallpaperManagerService.DisplayConnector) obj);
                        }
                    });
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$notifyKeyguardGoingAway$12(com.android.server.wallpaper.WallpaperManagerService.DisplayConnector displayConnector) {
        if (displayConnector.mEngine != null) {
            try {
                displayConnector.mEngine.dispatchWallpaperCommand("android.wallpaper.keyguardgoingaway", -1, -1, -1, new android.os.Bundle());
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(TAG, "Failed to notify that the keyguard is going away", e);
            }
        }
    }

    private com.android.server.wallpaper.WallpaperData[] getActiveWallpapers() {
        com.android.server.wallpaper.WallpaperData wallpaperData = this.mWallpaperMap.get(this.mCurrentUserId);
        com.android.server.wallpaper.WallpaperData wallpaperData2 = this.mLockWallpaperMap.get(this.mCurrentUserId);
        boolean z = (wallpaperData == null || wallpaperData.connection == null) ? false : true;
        boolean z2 = (wallpaperData2 == null || wallpaperData2.connection == null) ? false : true;
        if (z && z2) {
            return new com.android.server.wallpaper.WallpaperData[]{wallpaperData, wallpaperData2};
        }
        if (z) {
            return new com.android.server.wallpaper.WallpaperData[]{wallpaperData};
        }
        if (z2) {
            return new com.android.server.wallpaper.WallpaperData[]{wallpaperData2};
        }
        return new com.android.server.wallpaper.WallpaperData[0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.server.wallpaper.WallpaperData[] getWallpapers() {
        com.android.server.wallpaper.WallpaperData wallpaperData = this.mWallpaperMap.get(this.mCurrentUserId);
        com.android.server.wallpaper.WallpaperData wallpaperData2 = this.mLockWallpaperMap.get(this.mCurrentUserId);
        boolean z = wallpaperData != null;
        boolean z2 = wallpaperData2 != null;
        if (z && z2) {
            return new com.android.server.wallpaper.WallpaperData[]{wallpaperData, wallpaperData2};
        }
        if (z) {
            return new com.android.server.wallpaper.WallpaperData[]{wallpaperData};
        }
        if (z2) {
            return new com.android.server.wallpaper.WallpaperData[]{wallpaperData2};
        }
        return new com.android.server.wallpaper.WallpaperData[0];
    }

    private android.service.wallpaper.IWallpaperEngine getEngine(int i, int i2, int i3) {
        com.android.server.wallpaper.WallpaperManagerService.WallpaperConnection wallpaperConnection;
        com.android.server.wallpaper.WallpaperData findWallpaperAtDisplay = findWallpaperAtDisplay(i2, i3);
        android.service.wallpaper.IWallpaperEngine iWallpaperEngine = null;
        if (findWallpaperAtDisplay == null || (wallpaperConnection = findWallpaperAtDisplay.connection) == null) {
            return null;
        }
        synchronized (this.mLock) {
            for (int i4 = 0; i4 < wallpaperConnection.mDisplayConnector.size(); i4++) {
                try {
                    int i5 = ((com.android.server.wallpaper.WallpaperManagerService.DisplayConnector) wallpaperConnection.mDisplayConnector.get(i4)).mDisplayId;
                    int i6 = ((com.android.server.wallpaper.WallpaperManagerService.DisplayConnector) wallpaperConnection.mDisplayConnector.get(i4)).mDisplayId;
                    if (i5 == i3 || i6 == i) {
                        iWallpaperEngine = ((com.android.server.wallpaper.WallpaperManagerService.DisplayConnector) wallpaperConnection.mDisplayConnector.get(i4)).mEngine;
                        break;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
        return iWallpaperEngine;
    }

    public void addOnLocalColorsChangedListener(@android.annotation.NonNull android.app.ILocalWallpaperColorConsumer iLocalWallpaperColorConsumer, @android.annotation.NonNull java.util.List<android.graphics.RectF> list, int i, int i2, int i3) throws android.os.RemoteException {
        if (i != 2 && i != 1) {
            throw new java.lang.IllegalArgumentException("which should be either FLAG_LOCK or FLAG_SYSTEM");
        }
        android.service.wallpaper.IWallpaperEngine engine = getEngine(i, i2, i3);
        if (engine == null) {
            return;
        }
        synchronized (this.mLock) {
            this.mLocalColorRepo.addAreas(iLocalWallpaperColorConsumer, list, i3);
        }
        engine.addLocalColorsAreas(list);
    }

    public void removeOnLocalColorsChangedListener(@android.annotation.NonNull android.app.ILocalWallpaperColorConsumer iLocalWallpaperColorConsumer, java.util.List<android.graphics.RectF> list, int i, int i2, int i3) throws android.os.RemoteException {
        if (i != 2 && i != 1) {
            throw new java.lang.IllegalArgumentException("which should be either FLAG_LOCK or FLAG_SYSTEM");
        }
        if (android.os.Binder.getCallingUserHandle().getIdentifier() != i2) {
            throw new java.lang.SecurityException("calling user id does not match");
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        java.util.List<android.graphics.RectF> list2 = null;
        try {
            synchronized (this.mLock) {
                list2 = this.mLocalColorRepo.removeAreas(iLocalWallpaperColorConsumer, list, i3);
            }
        } catch (java.lang.Exception e) {
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        android.service.wallpaper.IWallpaperEngine engine = getEngine(i, i2, i3);
        if (engine == null || list2 == null || list2.size() <= 0) {
            return;
        }
        engine.removeLocalColorsAreas(list2);
    }

    public boolean lockScreenWallpaperExists() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mLockWallpaperMap.get(this.mCurrentUserId) != null;
        }
        return z;
    }

    public boolean isStaticWallpaper(int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.wallpaper.WallpaperData wallpaperData = (i == 2 ? this.mLockWallpaperMap : this.mWallpaperMap).get(this.mCurrentUserId);
                if (wallpaperData == null) {
                    return false;
                }
                return this.mImageWallpaper.equals(wallpaperData.wallpaperComponent);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void setWallpaperDimAmount(float f) throws android.os.RemoteException {
        setWallpaperDimAmountForUid(android.os.Binder.getCallingUid(), f);
    }

    public void setWallpaperDimAmountForUid(int i, float f) {
        checkPermission("android.permission.SET_WALLPAPER_DIM_AMOUNT");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            synchronized (this.mLock) {
                try {
                    com.android.server.wallpaper.WallpaperData wallpaperData = this.mWallpaperMap.get(this.mCurrentUserId);
                    com.android.server.wallpaper.WallpaperData wallpaperData2 = this.mLockWallpaperMap.get(this.mCurrentUserId);
                    if (f == com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                        wallpaperData.mUidToDimAmount.remove(i);
                    } else {
                        wallpaperData.mUidToDimAmount.put(i, java.lang.Float.valueOf(f));
                    }
                    final float highestDimAmountFromMap = getHighestDimAmountFromMap(wallpaperData.mUidToDimAmount);
                    wallpaperData.mWallpaperDimAmount = highestDimAmountFromMap;
                    if (wallpaperData2 != null) {
                        wallpaperData2.mWallpaperDimAmount = highestDimAmountFromMap;
                    }
                    boolean z = false;
                    for (com.android.server.wallpaper.WallpaperData wallpaperData3 : getActiveWallpapers()) {
                        if (wallpaperData3 != null && wallpaperData3.connection != null) {
                            wallpaperData3.connection.forEachDisplayConnector(new java.util.function.Consumer() { // from class: com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda5
                                @Override // java.util.function.Consumer
                                public final void accept(java.lang.Object obj) {
                                    com.android.server.wallpaper.WallpaperManagerService.lambda$setWallpaperDimAmountForUid$13(highestDimAmountFromMap, (com.android.server.wallpaper.WallpaperManagerService.DisplayConnector) obj);
                                }
                            });
                            z = true;
                            wallpaperData3.mIsColorExtractedFromDim = true;
                            arrayList.add(wallpaperData3);
                        }
                    }
                    if (z) {
                        saveSettingsLocked(wallpaperData.userId);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            java.util.Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                notifyWallpaperColorsChanged((com.android.server.wallpaper.WallpaperData) it.next());
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$setWallpaperDimAmountForUid$13(float f, com.android.server.wallpaper.WallpaperManagerService.DisplayConnector displayConnector) {
        if (displayConnector.mEngine != null) {
            try {
                displayConnector.mEngine.applyDimming(f);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(TAG, "Can't apply dimming on wallpaper display connector", e);
            }
        }
    }

    public float getWallpaperDimAmount() {
        checkPermission("android.permission.SET_WALLPAPER_DIM_AMOUNT");
        synchronized (this.mLock) {
            try {
                com.android.server.wallpaper.WallpaperData wallpaperData = this.mWallpaperMap.get(this.mCurrentUserId);
                if (wallpaperData == null && (wallpaperData = this.mWallpaperMap.get(0)) == null) {
                    android.util.Slog.e(TAG, "getWallpaperDimAmount: wallpaperData is null");
                    return com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
                }
                return wallpaperData.mWallpaperDimAmount;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private float getHighestDimAmountFromMap(android.util.SparseArray<java.lang.Float> sparseArray) {
        float f = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
        for (int i = 0; i < sparseArray.size(); i++) {
            f = java.lang.Math.max(f, sparseArray.valueAt(i).floatValue());
        }
        return f;
    }

    public android.app.WallpaperColors getWallpaperColors(int i, int i2, int i3) throws android.os.RemoteException {
        com.android.server.wallpaper.WallpaperData wallpaperData;
        boolean z = true;
        if (i != 2 && i != 1) {
            throw new java.lang.IllegalArgumentException("which should be either FLAG_LOCK or FLAG_SYSTEM");
        }
        int handleIncomingUser = android.app.ActivityManager.handleIncomingUser(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i2, false, true, "getWallpaperColors", null);
        synchronized (this.mLock) {
            if (i != 2) {
                wallpaperData = null;
            } else {
                try {
                    wallpaperData = this.mLockWallpaperMap.get(handleIncomingUser);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            if (wallpaperData == null) {
                wallpaperData = findWallpaperAtDisplay(handleIncomingUser, i3);
            }
            if (wallpaperData == null) {
                return null;
            }
            if (wallpaperData.primaryColors != null && !wallpaperData.mIsColorExtractedFromDim) {
                z = false;
            }
            if (z) {
                extractColors(wallpaperData);
            }
            return getAdjustedWallpaperColorsOnDimming(wallpaperData);
        }
    }

    android.app.WallpaperColors getAdjustedWallpaperColorsOnDimming(com.android.server.wallpaper.WallpaperData wallpaperData) {
        synchronized (this.mLock) {
            try {
                android.app.WallpaperColors wallpaperColors = wallpaperData.primaryColors;
                if (wallpaperColors == null || (wallpaperColors.getColorHints() & 4) != 0 || wallpaperData.mWallpaperDimAmount == com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                    return wallpaperColors;
                }
                return new android.app.WallpaperColors(wallpaperColors.getPrimaryColor(), wallpaperColors.getSecondaryColor(), wallpaperColors.getTertiaryColor(), wallpaperColors.getColorHints() & (-2) & (-3));
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private com.android.server.wallpaper.WallpaperData findWallpaperAtDisplay(int i, int i2) {
        if (this.mFallbackWallpaper != null && this.mFallbackWallpaper.connection != null && this.mFallbackWallpaper.connection.containsDisplay(i2)) {
            return this.mFallbackWallpaper;
        }
        return this.mWallpaperMap.get(i);
    }

    public android.os.ParcelFileDescriptor setWallpaper(java.lang.String str, java.lang.String str2, int[] iArr, java.util.List<android.graphics.Rect> list, boolean z, android.os.Bundle bundle, int i, android.app.IWallpaperManagerCallback iWallpaperManagerCallback, int i2) {
        boolean z2;
        android.os.ParcelFileDescriptor updateWallpaperBitmapLocked;
        int handleIncomingUser = android.app.ActivityManager.handleIncomingUser(android.app.IWallpaperManager.Stub.getCallingPid(), android.app.IWallpaperManager.Stub.getCallingUid(), i2, false, true, "changing wallpaper", null);
        checkPermission("android.permission.SET_WALLPAPER");
        if ((i & 3) == 0) {
            android.util.Slog.e(TAG, "Must specify a valid wallpaper category to set");
            throw new java.lang.IllegalArgumentException("Must specify a valid wallpaper category to set");
        }
        android.graphics.Rect rect = null;
        if (!isWallpaperSupported(str2) || !isSetWallpaperAllowed(str2)) {
            return null;
        }
        int defaultDisplayCurrentOrientation = this.mWallpaperDisplayHelper.getDefaultDisplayCurrentOrientation();
        android.util.SparseArray<android.graphics.Rect> cropMap = !com.android.window.flags.Flags.multiCrop() ? null : getCropMap(iArr, list, defaultDisplayCurrentOrientation);
        if (!com.android.window.flags.Flags.multiCrop() && list != null) {
            rect = list.get(0);
        }
        boolean isFromForegroundApp = !com.android.window.flags.Flags.multiCrop() ? false : isFromForegroundApp(str2);
        if (rect == null && !com.android.window.flags.Flags.multiCrop()) {
            rect = new android.graphics.Rect(0, 0, 0, 0);
        } else if (!com.android.window.flags.Flags.multiCrop() && (rect.width() < 0 || rect.height() < 0 || rect.left < 0 || rect.top < 0)) {
            throw new java.lang.IllegalArgumentException("Invalid crop rect supplied: " + rect);
        }
        synchronized (this.mLock) {
            try {
                com.android.server.wallpaper.WallpaperData wallpaperData = this.mWallpaperMap.get(handleIncomingUser);
                if (wallpaperData != null && this.mImageWallpaper.equals(wallpaperData.wallpaperComponent)) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                boolean z3 = this.mLockWallpaperMap.get(handleIncomingUser) == null;
                if (i == 1 && z2 && z3) {
                    android.util.Slog.i(TAG, "Migrating current wallpaper to be lock-only before updating system wallpaper");
                    migrateStaticSystemToLockWallpaperLocked(handleIncomingUser);
                }
                com.android.server.wallpaper.WallpaperData wallpaperSafeLocked = getWallpaperSafeLocked(handleIncomingUser, i);
                if (this.mPendingMigrationViaStatic != null) {
                    android.util.Slog.w(TAG, "Starting new static wp migration before previous migration finished");
                }
                this.mPendingMigrationViaStatic = new com.android.server.wallpaper.WallpaperManagerService.WallpaperDestinationChangeHandler(wallpaperSafeLocked);
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    updateWallpaperBitmapLocked = updateWallpaperBitmapLocked(str, wallpaperSafeLocked, bundle);
                    if (updateWallpaperBitmapLocked != null) {
                        wallpaperSafeLocked.imageWallpaperPending = true;
                        wallpaperSafeLocked.mSystemWasBoth = z3;
                        wallpaperSafeLocked.mWhich = i;
                        wallpaperSafeLocked.setComplete = iWallpaperManagerCallback;
                        if (!com.android.window.flags.Flags.multiCrop()) {
                            isFromForegroundApp = isFromForegroundApp(str2);
                        }
                        wallpaperSafeLocked.fromForegroundApp = isFromForegroundApp;
                        if (!com.android.window.flags.Flags.multiCrop()) {
                            wallpaperSafeLocked.cropHint.set(rect);
                        }
                        if (com.android.window.flags.Flags.multiCrop()) {
                            wallpaperSafeLocked.mSupportsMultiCrop = true;
                        }
                        if (com.android.window.flags.Flags.multiCrop()) {
                            wallpaperSafeLocked.mCropHints = cropMap;
                        }
                        wallpaperSafeLocked.allowBackup = z;
                        wallpaperSafeLocked.mWallpaperDimAmount = getWallpaperDimAmount();
                        wallpaperSafeLocked.mOrientationWhenSet = defaultDisplayCurrentOrientation;
                    }
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                } catch (java.lang.Throwable th) {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            } catch (java.lang.Throwable th2) {
                throw th2;
            }
        }
        return updateWallpaperBitmapLocked;
    }

    private android.util.SparseArray<android.graphics.Rect> getCropMap(int[] iArr, java.util.List<android.graphics.Rect> list, int i) {
        int i2 = 0;
        if (((list == null) ^ (iArr == null)) || (list != null && list.size() != iArr.length)) {
            throw new java.lang.IllegalArgumentException("Illegal crops/orientations lists: must both be null, or both the same size");
        }
        android.util.SparseArray<android.graphics.Rect> sparseArray = new android.util.SparseArray<>();
        if (list != null && list.size() != 0) {
            int i3 = 0;
            while (i2 < list.size()) {
                android.graphics.Rect rect = list.get(i2);
                int width = rect.width();
                int height = rect.height();
                if (width < 0 || height < 0 || rect.left < 0 || rect.top < 0) {
                    throw new java.lang.IllegalArgumentException("Invalid crop rect supplied: " + rect);
                }
                int i4 = iArr[i2];
                if (i4 == -1) {
                    if (i == -1) {
                        throw new java.lang.IllegalArgumentException("Invalid orientation: -1");
                    }
                    i4 = i;
                    i3 = 1;
                }
                sparseArray.put(i4, rect);
                i2++;
            }
            i2 = i3;
        }
        if (i2 != 0 && sparseArray.size() > 1) {
            throw new java.lang.IllegalArgumentException("Invalid crops supplied: the UNKNOWN screen orientation should only be used in a singleton map (in which case itrepresents the current orientation of the default display)");
        }
        return sparseArray;
    }

    private void migrateStaticSystemToLockWallpaperLocked(int i) {
        com.android.server.wallpaper.WallpaperData wallpaperData = this.mWallpaperMap.get(i);
        if (wallpaperData == null) {
            return;
        }
        com.android.server.wallpaper.WallpaperData wallpaperData2 = new com.android.server.wallpaper.WallpaperData(i, 2);
        wallpaperData2.wallpaperId = wallpaperData.wallpaperId;
        wallpaperData2.cropHint.set(wallpaperData.cropHint);
        wallpaperData2.mSupportsMultiCrop = wallpaperData.mSupportsMultiCrop;
        if (wallpaperData.mCropHints != null) {
            wallpaperData2.mCropHints = wallpaperData.mCropHints.clone();
        }
        wallpaperData2.allowBackup = wallpaperData.allowBackup;
        wallpaperData2.primaryColors = wallpaperData.primaryColors;
        wallpaperData2.mWallpaperDimAmount = wallpaperData.mWallpaperDimAmount;
        wallpaperData2.mWhich = 2;
        try {
            if (wallpaperData.getWallpaperFile().exists()) {
                android.system.Os.rename(wallpaperData.getWallpaperFile().getAbsolutePath(), wallpaperData2.getWallpaperFile().getAbsolutePath());
            }
            if (wallpaperData.getCropFile().exists()) {
                android.system.Os.rename(wallpaperData.getCropFile().getAbsolutePath(), wallpaperData2.getCropFile().getAbsolutePath());
            }
            this.mLockWallpaperMap.put(i, wallpaperData2);
            android.os.SELinux.restorecon(wallpaperData2.getWallpaperFile());
            this.mLastLockWallpaper = wallpaperData2;
        } catch (android.system.ErrnoException e) {
            android.util.Slog.w(TAG, "Couldn't migrate system wallpaper: " + e.getMessage());
            clearWallpaperBitmaps(wallpaperData2);
        }
    }

    android.os.ParcelFileDescriptor updateWallpaperBitmapLocked(java.lang.String str, com.android.server.wallpaper.WallpaperData wallpaperData, android.os.Bundle bundle) {
        if (str == null) {
            str = "";
        }
        try {
            java.io.File wallpaperDir = com.android.server.wallpaper.WallpaperUtils.getWallpaperDir(wallpaperData.userId);
            if (!wallpaperDir.exists()) {
                wallpaperDir.mkdir();
                android.os.FileUtils.setPermissions(wallpaperDir.getPath(), 505, -1, -1);
            }
            android.os.ParcelFileDescriptor open = android.os.ParcelFileDescriptor.open(wallpaperData.getWallpaperFile(), 1006632960);
            if (!android.os.SELinux.restorecon(wallpaperData.getWallpaperFile())) {
                android.util.Slog.w(TAG, "restorecon failed for wallpaper file: " + wallpaperData.getWallpaperFile().getPath());
                return null;
            }
            wallpaperData.name = str;
            wallpaperData.wallpaperId = com.android.server.wallpaper.WallpaperUtils.makeWallpaperIdLocked();
            if (bundle != null) {
                bundle.putInt("android.service.wallpaper.extra.ID", wallpaperData.wallpaperId);
            }
            wallpaperData.primaryColors = null;
            android.util.Slog.v(TAG, "updateWallpaperBitmapLocked() : id=" + wallpaperData.wallpaperId + " name=" + str + " file=" + wallpaperData.getWallpaperFile().getName());
            return open;
        } catch (java.io.FileNotFoundException e) {
            android.util.Slog.w(TAG, "Error setting wallpaper", e);
            return null;
        }
    }

    public void setWallpaperComponentChecked(android.content.ComponentName componentName, java.lang.String str, int i, int i2) {
        if (isWallpaperSupported(str) && isSetWallpaperAllowed(str)) {
            setWallpaperComponent(componentName, str, i, i2);
        }
    }

    public void setWallpaperComponent(android.content.ComponentName componentName) {
        setWallpaperComponent(componentName, "", android.os.UserHandle.getCallingUserId(), 1);
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean setWallpaperComponent(android.content.ComponentName componentName, java.lang.String str, int i, int i2) {
        return setWallpaperComponentInternal(componentName, i, i2, false, isFromForegroundApp(str), null);
    }

    private boolean setWallpaperComponentInternal(android.content.ComponentName componentName, int i, int i2, boolean z, boolean z2, android.os.IRemoteCallback iRemoteCallback) {
        boolean z3;
        com.android.server.wallpaper.WallpaperData wallpaperSafeLocked;
        boolean bindWallpaperComponentLocked;
        int handleIncomingUser = android.app.ActivityManager.handleIncomingUser(android.app.IWallpaperManager.Stub.getCallingPid(), android.app.IWallpaperManager.Stub.getCallingUid(), i2, false, true, "changing live wallpaper", null);
        checkPermission("android.permission.SET_WALLPAPER_COMPONENT");
        synchronized (this.mLock) {
            try {
                android.util.Slog.v(TAG, "setWallpaperComponent name=" + componentName + ", which = " + i);
                com.android.server.wallpaper.WallpaperData wallpaperData = this.mWallpaperMap.get(handleIncomingUser);
                if (wallpaperData == null) {
                    throw new java.lang.IllegalStateException("Wallpaper not yet initialized for user " + handleIncomingUser);
                }
                boolean equals = this.mImageWallpaper.equals(wallpaperData.wallpaperComponent);
                z3 = true;
                boolean z4 = false;
                boolean z5 = this.mLockWallpaperMap.get(handleIncomingUser) == null;
                if (i == 1 && z5 && equals) {
                    android.util.Slog.i(TAG, "Migrating current wallpaper to be lock-only beforeupdating system wallpaper");
                    migrateStaticSystemToLockWallpaperLocked(handleIncomingUser);
                }
                wallpaperSafeLocked = getWallpaperSafeLocked(handleIncomingUser, i);
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    wallpaperSafeLocked.mSupportsMultiCrop = this.mImageWallpaper.equals(componentName);
                    wallpaperSafeLocked.imageWallpaperPending = false;
                    wallpaperSafeLocked.mWhich = i;
                    wallpaperSafeLocked.mSystemWasBoth = z5;
                    wallpaperSafeLocked.fromForegroundApp = z2;
                    com.android.server.wallpaper.WallpaperManagerService.WallpaperDestinationChangeHandler wallpaperDestinationChangeHandler = new com.android.server.wallpaper.WallpaperManagerService.WallpaperDestinationChangeHandler(wallpaperSafeLocked);
                    boolean changingToSame = changingToSame(componentName, wallpaperSafeLocked);
                    boolean z6 = z || (changingToSame && z5 && i == 1);
                    wallpaperSafeLocked.mBindSource = componentName == null ? com.android.server.wallpaper.WallpaperData.BindSource.SET_LIVE_TO_CLEAR : com.android.server.wallpaper.WallpaperData.BindSource.SET_LIVE;
                    bindWallpaperComponentLocked = bindWallpaperComponentLocked(componentName, z6, true, wallpaperSafeLocked, iRemoteCallback);
                    if (!bindWallpaperComponentLocked) {
                        z3 = false;
                    } else {
                        if (!changingToSame) {
                            wallpaperSafeLocked.primaryColors = null;
                        } else if (wallpaperSafeLocked.connection != null) {
                            wallpaperSafeLocked.connection.forEachDisplayConnector(new java.util.function.Consumer() { // from class: com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda0
                                @Override // java.util.function.Consumer
                                public final void accept(java.lang.Object obj) {
                                    com.android.server.wallpaper.WallpaperManagerService.lambda$setWallpaperComponentInternal$14((com.android.server.wallpaper.WallpaperManagerService.DisplayConnector) obj);
                                }
                            });
                        }
                        if (!this.mImageWallpaper.equals(wallpaperSafeLocked.wallpaperComponent)) {
                            clearWallpaperBitmaps(wallpaperSafeLocked);
                            if (wallpaperSafeLocked.mWhich == 2) {
                                z4 = true;
                            }
                        }
                        wallpaperSafeLocked.wallpaperId = com.android.server.wallpaper.WallpaperUtils.makeWallpaperIdLocked();
                        notifyCallbacksLocked(wallpaperSafeLocked);
                        if (i == 3) {
                            com.android.server.wallpaper.WallpaperData wallpaperData2 = this.mLockWallpaperMap.get(wallpaperSafeLocked.userId);
                            if (wallpaperData2 != null) {
                                detachWallpaperLocked(wallpaperData2);
                                if (changingToSame) {
                                    updateEngineFlags(wallpaperSafeLocked);
                                }
                            }
                            if (!z4) {
                                clearWallpaperBitmaps(wallpaperSafeLocked.userId, 2);
                            }
                            this.mLockWallpaperMap.remove(wallpaperSafeLocked.userId);
                        }
                        wallpaperDestinationChangeHandler.complete();
                    }
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                } catch (java.lang.Throwable th) {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            } catch (java.lang.Throwable th2) {
                throw th2;
            }
        }
        if (z3) {
            notifyWallpaperColorsChanged(wallpaperSafeLocked);
        }
        return bindWallpaperComponentLocked;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$setWallpaperComponentInternal$14(com.android.server.wallpaper.WallpaperManagerService.DisplayConnector displayConnector) {
        try {
            if (displayConnector.mEngine != null) {
                displayConnector.mEngine.dispatchWallpaperCommand("android.wallpaper.reapply", 0, 0, 0, (android.os.Bundle) null);
            }
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Error sending apply message to wallpaper", e);
        }
    }

    private boolean isDefaultComponent(android.content.ComponentName componentName) {
        return componentName == null || componentName.equals(this.mDefaultWallpaperComponent);
    }

    private boolean changingToSame(android.content.ComponentName componentName, com.android.server.wallpaper.WallpaperData wallpaperData) {
        if (wallpaperData.connection != null) {
            android.content.ComponentName componentName2 = wallpaperData.wallpaperComponent;
            if (isDefaultComponent(componentName) && isDefaultComponent(componentName2)) {
                return true;
            }
            return componentName2 != null && componentName2.equals(componentName);
        }
        return false;
    }

    boolean bindWallpaperComponentLocked(android.content.ComponentName componentName, boolean z, boolean z2, com.android.server.wallpaper.WallpaperData wallpaperData, android.os.IRemoteCallback iRemoteCallback) {
        android.content.Intent intent;
        android.app.WallpaperInfo wallpaperInfo;
        android.app.WallpaperInfo wallpaperInfo2;
        android.content.ComponentName componentName2 = componentName;
        android.util.Slog.v(TAG, "bindWallpaperComponentLocked: componentName=" + componentName2);
        if (!z && changingToSame(componentName2, wallpaperData)) {
            try {
                android.util.Slog.v(TAG, "Changing to the same component, ignoring");
                if (iRemoteCallback != null) {
                    iRemoteCallback.sendResult((android.os.Bundle) null);
                }
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Failed to send callback", e);
            }
            return true;
        }
        com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog = new com.android.server.utils.TimingsTraceAndSlog(TAG);
        timingsTraceAndSlog.traceBegin("WPMS.bindWallpaperComponentLocked-" + componentName2);
        try {
            if (componentName2 == null) {
                try {
                    componentName2 = this.mDefaultWallpaperComponent;
                } catch (android.os.RemoteException e2) {
                    java.lang.String str = "Remote exception for " + componentName2 + "\n" + e2;
                    if (z2) {
                        throw new java.lang.IllegalArgumentException(str);
                    }
                    android.util.Slog.w(TAG, str);
                    timingsTraceAndSlog.traceEnd();
                    return false;
                }
            }
            int i = wallpaperData.userId;
            android.content.pm.ServiceInfo serviceInfo = this.mIPackageManager.getServiceInfo(componentName2, 4224L, i);
            if (serviceInfo == null) {
                android.util.Slog.w(TAG, "Attempted wallpaper " + componentName2 + " is unavailable");
                timingsTraceAndSlog.traceEnd();
                return false;
            }
            if (!"android.permission.BIND_WALLPAPER".equals(serviceInfo.permission)) {
                java.lang.String str2 = "Selected service does not have android.permission.BIND_WALLPAPER: " + componentName2;
                if (z2) {
                    throw new java.lang.SecurityException(str2);
                }
                android.util.Slog.w(TAG, str2);
                timingsTraceAndSlog.traceEnd();
                return false;
            }
            android.content.Intent intent2 = new android.content.Intent("android.service.wallpaper.WallpaperService");
            if (componentName2 == null || componentName2.equals(this.mImageWallpaper)) {
                intent = intent2;
                wallpaperInfo = null;
            } else {
                intent = intent2;
                java.util.List list = this.mIPackageManager.queryIntentServices(intent2, intent2.resolveTypeIfNeeded(this.mContext.getContentResolver()), 128L, i).getList();
                int i2 = 0;
                while (true) {
                    if (i2 >= list.size()) {
                        wallpaperInfo2 = null;
                        break;
                    }
                    android.content.pm.ServiceInfo serviceInfo2 = ((android.content.pm.ResolveInfo) list.get(i2)).serviceInfo;
                    if (serviceInfo2.name.equals(serviceInfo.name) && serviceInfo2.packageName.equals(serviceInfo.packageName)) {
                        try {
                            wallpaperInfo2 = new android.app.WallpaperInfo(this.mContext, (android.content.pm.ResolveInfo) list.get(i2));
                            break;
                        } catch (java.io.IOException e3) {
                            if (z2) {
                                throw new java.lang.IllegalArgumentException(e3);
                            }
                            android.util.Slog.w(TAG, e3);
                            timingsTraceAndSlog.traceEnd();
                            return false;
                        } catch (org.xmlpull.v1.XmlPullParserException e4) {
                            if (z2) {
                                throw new java.lang.IllegalArgumentException(e4);
                            }
                            android.util.Slog.w(TAG, e4);
                            timingsTraceAndSlog.traceEnd();
                            return false;
                        }
                    }
                    i2++;
                }
                if (wallpaperInfo2 == null) {
                    java.lang.String str3 = "Selected service is not a wallpaper: " + componentName2;
                    if (z2) {
                        throw new java.lang.SecurityException(str3);
                    }
                    android.util.Slog.w(TAG, str3);
                    timingsTraceAndSlog.traceEnd();
                    return false;
                }
                wallpaperInfo = wallpaperInfo2;
            }
            if (wallpaperInfo != null && wallpaperInfo.supportsAmbientMode() && this.mIPackageManager.checkPermission("android.permission.AMBIENT_WALLPAPER", wallpaperInfo.getPackageName(), i) != 0 && !this.mIPackageManager.hasSystemFeature("android.hardware.type.watch", 0)) {
                java.lang.String str4 = "Selected service does not have android.permission.AMBIENT_WALLPAPER: " + componentName2;
                if (z2) {
                    throw new java.lang.SecurityException(str4);
                }
                android.util.Slog.w(TAG, str4);
                timingsTraceAndSlog.traceEnd();
                return false;
            }
            android.app.PendingIntent activityAsUser = android.app.PendingIntent.getActivityAsUser(this.mContext, 0, android.content.Intent.createChooser(new android.content.Intent("android.intent.action.SET_WALLPAPER"), this.mContext.getText(android.R.string.cfTemplateRegistered)), 67108864, android.app.ActivityOptions.makeBasic().setPendingIntentCreatorBackgroundActivityStartMode(2).toBundle(), android.os.UserHandle.of(i));
            com.android.server.wallpaper.WallpaperManagerService.WallpaperConnection wallpaperConnection = new com.android.server.wallpaper.WallpaperManagerService.WallpaperConnection(wallpaperInfo, wallpaperData, this.mIPackageManager.getPackageUid(componentName2.getPackageName(), 268435456L, wallpaperData.userId));
            intent.setComponent(componentName2);
            intent.putExtra("android.intent.extra.client_label", android.R.string.validity_period);
            intent.putExtra("android.intent.extra.client_intent", activityAsUser);
            if (this.mContext.bindServiceAsUser(intent, wallpaperConnection, this.mContext.getResources().getBoolean(android.R.bool.config_volume_up_to_exit_silent) ? 570953729 : 570429441, getHandlerForBindingWallpaperLocked(), new android.os.UserHandle(i))) {
                maybeDetachLastWallpapers(wallpaperData);
                wallpaperData.wallpaperComponent = componentName2;
                wallpaperData.connection = wallpaperConnection;
                wallpaperConnection.mReply = iRemoteCallback;
                updateCurrentWallpapers(wallpaperData);
                updateFallbackConnection();
                timingsTraceAndSlog.traceEnd();
                return true;
            }
            java.lang.String str5 = "Unable to bind service: " + componentName2;
            if (z2) {
                throw new java.lang.IllegalArgumentException(str5);
            }
            android.util.Slog.w(TAG, str5);
            timingsTraceAndSlog.traceEnd();
            return false;
        } catch (java.lang.Throwable th) {
            timingsTraceAndSlog.traceEnd();
            throw th;
        }
    }

    private android.os.Handler getHandlerForBindingWallpaperLocked() {
        if (!android.multiuser.Flags.bindWallpaperServiceOnItsOwnThreadDuringAUserSwitch()) {
            return this.mContext.getMainThreadHandler();
        }
        if (this.mInitialUserSwitch) {
            return this.mContext.getMainThreadHandler();
        }
        if (this.mHandlerThread == null) {
            this.mHandlerThread = new com.android.server.ServiceThread(TAG, -2, true);
            this.mHandlerThread.start();
        }
        return this.mHandlerThread.getThreadHandler();
    }

    private void updateCurrentWallpapers(com.android.server.wallpaper.WallpaperData wallpaperData) {
        if (wallpaperData.userId != this.mCurrentUserId || wallpaperData.equals(this.mFallbackWallpaper)) {
            return;
        }
        if (wallpaperData.mWhich == 3) {
            this.mLastWallpaper = wallpaperData;
        } else if (wallpaperData.mWhich == 1) {
            this.mLastWallpaper = wallpaperData;
        } else if (wallpaperData.mWhich == 2) {
            this.mLastLockWallpaper = wallpaperData;
        }
    }

    private void maybeDetachLastWallpapers(com.android.server.wallpaper.WallpaperData wallpaperData) {
        if (wallpaperData.userId != this.mCurrentUserId || wallpaperData.equals(this.mFallbackWallpaper)) {
            return;
        }
        boolean z = (wallpaperData.mWhich & 1) != 0;
        boolean z2 = (wallpaperData.mWhich & 2) != 0;
        boolean z3 = wallpaperData.mSystemWasBoth && !z2;
        if (z && !z3) {
            detachWallpaperLocked(this.mLastWallpaper);
        }
        if (z2) {
            detachWallpaperLocked(this.mLastLockWallpaper);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void detachWallpaperLocked(final com.android.server.wallpaper.WallpaperData wallpaperData) {
        if (wallpaperData != null && wallpaperData.connection != null) {
            if (wallpaperData.connection.mReply != null) {
                try {
                    wallpaperData.connection.mReply.sendResult((android.os.Bundle) null);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.w(TAG, "Error sending reply to wallpaper before disconnect", e);
                }
                wallpaperData.connection.mReply = null;
            }
            wallpaperData.connection.forEachDisplayConnector(new java.util.function.Consumer() { // from class: com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda22
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.wallpaper.WallpaperManagerService.lambda$detachWallpaperLocked$15(com.android.server.wallpaper.WallpaperData.this, (com.android.server.wallpaper.WallpaperManagerService.DisplayConnector) obj);
                }
            });
            wallpaperData.connection.mService = null;
            wallpaperData.connection.mDisplayConnector.clear();
            com.android.server.FgThread.getHandler().removeCallbacks(wallpaperData.connection.mResetRunnable);
            this.mContext.getMainThreadHandler().removeCallbacks(wallpaperData.connection.mDisconnectRunnable);
            this.mContext.getMainThreadHandler().removeCallbacks(wallpaperData.connection.mTryToRebindRunnable);
            try {
                this.mContext.unbindService(wallpaperData.connection);
            } catch (java.lang.IllegalArgumentException e2) {
                android.util.Slog.w(TAG, "Error unbinding wallpaper when detaching", e2);
            }
            wallpaperData.connection = null;
            if (wallpaperData == this.mLastWallpaper) {
                this.mLastWallpaper = null;
            }
            if (wallpaperData == this.mLastLockWallpaper) {
                this.mLastLockWallpaper = null;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$detachWallpaperLocked$15(com.android.server.wallpaper.WallpaperData wallpaperData, com.android.server.wallpaper.WallpaperManagerService.DisplayConnector displayConnector) {
        displayConnector.disconnectLocked(wallpaperData.connection);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateEngineFlags(final com.android.server.wallpaper.WallpaperData wallpaperData) {
        if (wallpaperData.connection == null) {
            return;
        }
        wallpaperData.connection.forEachDisplayConnector(new java.util.function.Consumer() { // from class: com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wallpaper.WallpaperManagerService.this.lambda$updateEngineFlags$16(wallpaperData, (com.android.server.wallpaper.WallpaperManagerService.DisplayConnector) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateEngineFlags$16(com.android.server.wallpaper.WallpaperData wallpaperData, com.android.server.wallpaper.WallpaperManagerService.DisplayConnector displayConnector) {
        try {
            if (displayConnector.mEngine != null) {
                displayConnector.mEngine.setWallpaperFlags(wallpaperData.mWhich);
                this.mWindowManagerInternal.setWallpaperShowWhenLocked(displayConnector.mToken, (wallpaperData.mWhich & 2) != 0);
            }
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Failed to update wallpaper engine flags", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: clearWallpaperComponentLocked, reason: merged with bridge method [inline-methods] */
    public void lambda$clearWallpaperLocked$8(com.android.server.wallpaper.WallpaperData wallpaperData) {
        wallpaperData.wallpaperComponent = null;
        detachWallpaperLocked(wallpaperData);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void attachServiceLocked(final com.android.server.wallpaper.WallpaperManagerService.WallpaperConnection wallpaperConnection, final com.android.server.wallpaper.WallpaperData wallpaperData) {
        com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog = new com.android.server.utils.TimingsTraceAndSlog(TAG);
        timingsTraceAndSlog.traceBegin("WPMS.attachServiceLocked");
        wallpaperConnection.forEachDisplayConnector(new java.util.function.Consumer() { // from class: com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda12
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.wallpaper.WallpaperManagerService.DisplayConnector) obj).connectLocked(com.android.server.wallpaper.WallpaperManagerService.WallpaperConnection.this, wallpaperData);
            }
        });
        timingsTraceAndSlog.traceEnd();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyCallbacksLocked(com.android.server.wallpaper.WallpaperData wallpaperData) {
        int beginBroadcast = wallpaperData.callbacks.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                wallpaperData.callbacks.getBroadcastItem(i).onWallpaperChanged();
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(TAG, "Failed to notify callbacks about wallpaper changes", e);
            }
        }
        wallpaperData.callbacks.finishBroadcast();
        android.content.Intent intent = new android.content.Intent("android.intent.action.WALLPAPER_CHANGED");
        intent.putExtra("android.service.wallpaper.extra.FROM_FOREGROUND_APP", wallpaperData.fromForegroundApp);
        this.mContext.sendBroadcastAsUser(intent, new android.os.UserHandle(this.mCurrentUserId));
    }

    private void checkPermission(java.lang.String str) {
        if (!hasPermission(str)) {
            throw new java.lang.SecurityException("Access denied to process: " + android.os.Binder.getCallingPid() + ", must have permission " + str);
        }
    }

    private boolean packageBelongsToUid(java.lang.String str, int i) {
        try {
            return this.mContext.getPackageManager().getPackageUidAsUser(str, android.os.UserHandle.getUserId(i)) == i;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private void enforcePackageBelongsToUid(java.lang.String str, int i) {
        if (!packageBelongsToUid(str, i)) {
            throw new java.lang.IllegalArgumentException("Invalid package or package does not belong to uid:" + i);
        }
    }

    private boolean isFromForegroundApp(final java.lang.String str) {
        return ((java.lang.Boolean) android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda18
            public final java.lang.Object getOrThrow() {
                java.lang.Boolean lambda$isFromForegroundApp$18;
                lambda$isFromForegroundApp$18 = com.android.server.wallpaper.WallpaperManagerService.this.lambda$isFromForegroundApp$18(str);
                return lambda$isFromForegroundApp$18;
            }
        })).booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Boolean lambda$isFromForegroundApp$18(java.lang.String str) throws java.lang.Exception {
        return java.lang.Boolean.valueOf(this.mActivityManager.getPackageImportance(str) == 100);
    }

    private void checkCallerIsSystemOrSystemUi() {
        if (android.os.Binder.getCallingUid() != android.os.Process.myUid() && this.mContext.checkCallingPermission("android.permission.STATUS_BAR_SERVICE") != 0) {
            throw new java.lang.SecurityException("Access denied: only system processes can call this");
        }
    }

    public boolean isWallpaperSupported(java.lang.String str) {
        int callingUid = android.os.Binder.getCallingUid();
        enforcePackageBelongsToUid(str, callingUid);
        return this.mAppOpsManager.checkOpNoThrow(48, callingUid, str) == 0;
    }

    public boolean isSetWallpaperAllowed(java.lang.String str) {
        if (!java.util.Arrays.asList(this.mContext.getPackageManager().getPackagesForUid(android.os.Binder.getCallingUid())).contains(str)) {
            return false;
        }
        android.app.admin.DevicePolicyManagerInternal devicePolicyManagerInternal = (android.app.admin.DevicePolicyManagerInternal) com.android.server.LocalServices.getService(android.app.admin.DevicePolicyManagerInternal.class);
        if (devicePolicyManagerInternal != null && devicePolicyManagerInternal.isDeviceOrProfileOwnerInCallingUser(str)) {
            return true;
        }
        int callingUserId = android.os.UserHandle.getCallingUserId();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return !((com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class)).hasUserRestriction("no_set_wallpaper", callingUserId);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean isWallpaperBackupEligible(int i, int i2) {
        com.android.server.wallpaper.WallpaperData wallpaperData;
        if (i == 2) {
            wallpaperData = this.mLockWallpaperMap.get(i2);
        } else {
            wallpaperData = this.mWallpaperMap.get(i2);
        }
        if (wallpaperData != null) {
            return wallpaperData.allowBackup;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onDisplayReadyInternal(int i) {
        synchronized (this.mLock) {
            try {
                if (this.mLastWallpaper == null) {
                    return;
                }
                if (supportsMultiDisplay(this.mLastWallpaper.connection)) {
                    com.android.server.wallpaper.WallpaperManagerService.DisplayConnector displayConnectorOrCreate = this.mLastWallpaper.connection.getDisplayConnectorOrCreate(i);
                    if (displayConnectorOrCreate == null) {
                        return;
                    }
                    displayConnectorOrCreate.connectLocked(this.mLastWallpaper.connection, this.mLastWallpaper);
                    return;
                }
                if (this.mFallbackWallpaper != null) {
                    com.android.server.wallpaper.WallpaperManagerService.DisplayConnector displayConnectorOrCreate2 = this.mFallbackWallpaper.connection.getDisplayConnectorOrCreate(i);
                    if (displayConnectorOrCreate2 == null) {
                    } else {
                        displayConnectorOrCreate2.connectLocked(this.mFallbackWallpaper.connection, this.mFallbackWallpaper);
                    }
                } else {
                    android.util.Slog.w(TAG, "No wallpaper can be added to the new display");
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void saveSettingsLocked(int i) {
        com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog = new com.android.server.utils.TimingsTraceAndSlog(TAG);
        timingsTraceAndSlog.traceBegin("WPMS.saveSettingsLocked-" + i);
        this.mWallpaperDataParser.saveSettingsLocked(i, this.mWallpaperMap.get(i), this.mLockWallpaperMap.get(i));
        timingsTraceAndSlog.traceEnd();
    }

    com.android.server.wallpaper.WallpaperData getWallpaperSafeLocked(int i, int i2) {
        android.util.SparseArray<com.android.server.wallpaper.WallpaperData> sparseArray = i2 == 2 ? this.mLockWallpaperMap : this.mWallpaperMap;
        com.android.server.wallpaper.WallpaperData wallpaperData = sparseArray.get(i);
        if (wallpaperData == null) {
            loadSettingsLocked(i, false, i2 == 2 ? 2 : 3);
            com.android.server.wallpaper.WallpaperData wallpaperData2 = sparseArray.get(i);
            if (wallpaperData2 == null) {
                if (i2 == 2) {
                    com.android.server.wallpaper.WallpaperData wallpaperData3 = new com.android.server.wallpaper.WallpaperData(i, 2);
                    this.mLockWallpaperMap.put(i, wallpaperData3);
                    return wallpaperData3;
                }
                android.util.Slog.wtf(TAG, "Didn't find wallpaper in non-lock case!");
                com.android.server.wallpaper.WallpaperData wallpaperData4 = new com.android.server.wallpaper.WallpaperData(i, 1);
                this.mWallpaperMap.put(i, wallpaperData4);
                return wallpaperData4;
            }
            return wallpaperData2;
        }
        return wallpaperData;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadSettingsLocked(int i, boolean z, int i2) {
        initializeFallbackWallpaper();
        com.android.server.wallpaper.WallpaperDataParser.WallpaperLoadingResult loadSettingsLocked = this.mWallpaperDataParser.loadSettingsLocked(i, z, !this.mWallpaperMap.contains(i), i2);
        boolean z2 = (i2 & 1) != 0;
        boolean z3 = (i2 & 2) != 0;
        if (z2) {
            this.mWallpaperMap.put(i, loadSettingsLocked.getSystemWallpaperData());
        }
        if (z3) {
            if (loadSettingsLocked.success()) {
                this.mLockWallpaperMap.put(i, loadSettingsLocked.getLockWallpaperData());
            } else {
                this.mLockWallpaperMap.remove(i);
            }
        }
    }

    private void initializeFallbackWallpaper() {
        if (this.mFallbackWallpaper == null) {
            this.mFallbackWallpaper = new com.android.server.wallpaper.WallpaperData(0, 1);
            this.mFallbackWallpaper.allowBackup = false;
            this.mFallbackWallpaper.wallpaperId = com.android.server.wallpaper.WallpaperUtils.makeWallpaperIdLocked();
            this.mFallbackWallpaper.mBindSource = com.android.server.wallpaper.WallpaperData.BindSource.INITIALIZE_FALLBACK;
            bindWallpaperComponentLocked(this.mDefaultWallpaperComponent, true, false, this.mFallbackWallpaper, null);
        }
    }

    public void settingsRestored() {
        com.android.server.wallpaper.WallpaperData wallpaperData;
        boolean z;
        if (android.os.Binder.getCallingUid() != 1000) {
            throw new java.lang.RuntimeException("settingsRestored() can only be called from the system process");
        }
        synchronized (this.mLock) {
            try {
                loadSettingsLocked(0, false, 3);
                wallpaperData = this.mWallpaperMap.get(0);
                wallpaperData.wallpaperId = com.android.server.wallpaper.WallpaperUtils.makeWallpaperIdLocked();
                z = true;
                wallpaperData.allowBackup = true;
                if (wallpaperData.nextWallpaperComponent != null && !wallpaperData.nextWallpaperComponent.equals(this.mImageWallpaper)) {
                    wallpaperData.mBindSource = com.android.server.wallpaper.WallpaperData.BindSource.RESTORE_SETTINGS_LIVE_SUCCESS;
                    if (!bindWallpaperComponentLocked(wallpaperData.nextWallpaperComponent, false, false, wallpaperData, null)) {
                        wallpaperData.mBindSource = com.android.server.wallpaper.WallpaperData.BindSource.RESTORE_SETTINGS_LIVE_FAILURE;
                        bindWallpaperComponentLocked(null, false, false, wallpaperData, null);
                    }
                } else {
                    if (!"".equals(wallpaperData.name)) {
                        z = this.mWallpaperDataParser.restoreNamedResourceLocked(wallpaperData);
                    }
                    if (z) {
                        this.mWallpaperCropper.generateCrop(wallpaperData);
                        wallpaperData.mBindSource = com.android.server.wallpaper.WallpaperData.BindSource.RESTORE_SETTINGS_STATIC;
                        bindWallpaperComponentLocked(wallpaperData.nextWallpaperComponent, true, false, wallpaperData, null);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (!z) {
            android.util.Slog.e(TAG, "Failed to restore wallpaper: '" + wallpaperData.name + "'");
            wallpaperData.name = "";
            com.android.server.wallpaper.WallpaperUtils.getWallpaperDir(0).delete();
        }
        synchronized (this.mLock) {
            saveSettingsLocked(0);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onShellCommand(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, java.io.FileDescriptor fileDescriptor3, java.lang.String[] strArr, android.os.ShellCallback shellCallback, android.os.ResultReceiver resultReceiver) {
        new com.android.server.wallpaper.WallpaperManagerShellCommand(this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
    }

    private void dumpWallpaper(com.android.server.wallpaper.WallpaperData wallpaperData, final java.io.PrintWriter printWriter) {
        if (wallpaperData == null) {
            printWriter.println(" (null entry)");
            return;
        }
        printWriter.print(" User ");
        printWriter.print(wallpaperData.userId);
        printWriter.print(": id=");
        printWriter.print(wallpaperData.wallpaperId);
        printWriter.print(": mWhich=");
        printWriter.print(wallpaperData.mWhich);
        printWriter.print(": mSystemWasBoth=");
        printWriter.print(wallpaperData.mSystemWasBoth);
        printWriter.print(": mBindSource=");
        printWriter.println(wallpaperData.mBindSource.name());
        printWriter.println(" Display state:");
        this.mWallpaperDisplayHelper.forEachDisplayData(new java.util.function.Consumer() { // from class: com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda19
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wallpaper.WallpaperManagerService.lambda$dumpWallpaper$19(printWriter, (com.android.server.wallpaper.WallpaperDisplayHelper.DisplayData) obj);
            }
        });
        printWriter.print("  mCropHint=");
        printWriter.println(wallpaperData.cropHint);
        printWriter.print("  mName=");
        printWriter.println(wallpaperData.name);
        printWriter.print("  mAllowBackup=");
        printWriter.println(wallpaperData.allowBackup);
        printWriter.print("  mWallpaperComponent=");
        printWriter.println(wallpaperData.wallpaperComponent);
        printWriter.print("  mWallpaperDimAmount=");
        printWriter.println(wallpaperData.mWallpaperDimAmount);
        printWriter.print("  isColorExtracted=");
        printWriter.println(wallpaperData.mIsColorExtractedFromDim);
        printWriter.println("  mUidToDimAmount:");
        for (int i = 0; i < wallpaperData.mUidToDimAmount.size(); i++) {
            printWriter.print("    UID=");
            printWriter.print(wallpaperData.mUidToDimAmount.keyAt(i));
            printWriter.print(" dimAmount=");
            printWriter.println(wallpaperData.mUidToDimAmount.valueAt(i));
        }
        if (wallpaperData.connection != null) {
            com.android.server.wallpaper.WallpaperManagerService.WallpaperConnection wallpaperConnection = wallpaperData.connection;
            printWriter.print("  Wallpaper connection ");
            printWriter.print(wallpaperConnection);
            printWriter.println(":");
            if (wallpaperConnection.mInfo != null) {
                printWriter.print("    mInfo.component=");
                printWriter.println(wallpaperConnection.mInfo.getComponent());
            }
            wallpaperConnection.forEachDisplayConnector(new java.util.function.Consumer() { // from class: com.android.server.wallpaper.WallpaperManagerService$$ExternalSyntheticLambda20
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.wallpaper.WallpaperManagerService.lambda$dumpWallpaper$20(printWriter, (com.android.server.wallpaper.WallpaperManagerService.DisplayConnector) obj);
                }
            });
            printWriter.print("    mService=");
            printWriter.println(wallpaperConnection.mService);
            printWriter.print("    mLastDiedTime=");
            printWriter.println(wallpaperData.lastDiedTime - android.os.SystemClock.uptimeMillis());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dumpWallpaper$19(java.io.PrintWriter printWriter, com.android.server.wallpaper.WallpaperDisplayHelper.DisplayData displayData) {
        printWriter.print("  displayId=");
        printWriter.println(displayData.mDisplayId);
        printWriter.print("  mWidth=");
        printWriter.print(displayData.mWidth);
        printWriter.print("  mHeight=");
        printWriter.println(displayData.mHeight);
        printWriter.print("  mPadding=");
        printWriter.println(displayData.mPadding);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dumpWallpaper$20(java.io.PrintWriter printWriter, com.android.server.wallpaper.WallpaperManagerService.DisplayConnector displayConnector) {
        printWriter.print("     mDisplayId=");
        printWriter.println(displayConnector.mDisplayId);
        printWriter.print("     mToken=");
        printWriter.println(displayConnector.mToken);
        printWriter.print("     mEngine=");
        printWriter.println(displayConnector.mEngine);
    }

    protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        if (com.android.internal.util.DumpUtils.checkDumpPermission(this.mContext, TAG, printWriter)) {
            printWriter.print("mDefaultWallpaperComponent=");
            printWriter.println(this.mDefaultWallpaperComponent);
            printWriter.print("mImageWallpaper=");
            printWriter.println(this.mImageWallpaper);
            synchronized (this.mLock) {
                try {
                    printWriter.println("System wallpaper state:");
                    for (int i = 0; i < this.mWallpaperMap.size(); i++) {
                        dumpWallpaper(this.mWallpaperMap.valueAt(i), printWriter);
                    }
                    printWriter.println("Lock wallpaper state:");
                    for (int i2 = 0; i2 < this.mLockWallpaperMap.size(); i2++) {
                        dumpWallpaper(this.mLockWallpaperMap.valueAt(i2), printWriter);
                    }
                    printWriter.println("Fallback wallpaper state:");
                    if (this.mFallbackWallpaper != null) {
                        dumpWallpaper(this.mFallbackWallpaper, printWriter);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }
}
