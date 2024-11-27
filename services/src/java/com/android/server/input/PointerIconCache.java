package com.android.server.input;

/* loaded from: classes2.dex */
final class PointerIconCache {
    private static final java.lang.String TAG = com.android.server.input.PointerIconCache.class.getSimpleName();
    private final android.content.Context mContext;
    private final com.android.server.input.NativeInputManagerService mNative;
    private final android.os.Handler mUiThreadHandler = com.android.server.UiThread.getHandler();

    @com.android.internal.annotations.GuardedBy({"mLoadedPointerIconsByDisplayAndType"})
    private final android.util.SparseArray<android.util.SparseArray<android.view.PointerIcon>> mLoadedPointerIconsByDisplayAndType = new android.util.SparseArray<>();

    @com.android.internal.annotations.GuardedBy({"mLoadedPointerIconsByDisplayAndType"})
    private boolean mUseLargePointerIcons = false;

    @com.android.internal.annotations.GuardedBy({"mLoadedPointerIconsByDisplayAndType"})
    private final android.util.SparseArray<android.content.Context> mDisplayContexts = new android.util.SparseArray<>();

    @com.android.internal.annotations.GuardedBy({"mLoadedPointerIconsByDisplayAndType"})
    private final android.util.SparseIntArray mDisplayDensities = new android.util.SparseIntArray();
    private final android.hardware.display.DisplayManager.DisplayListener mDisplayListener = new android.hardware.display.DisplayManager.DisplayListener() { // from class: com.android.server.input.PointerIconCache.1
        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayAdded(int i) {
            synchronized (com.android.server.input.PointerIconCache.this.mLoadedPointerIconsByDisplayAndType) {
                com.android.server.input.PointerIconCache.this.updateDisplayDensityLocked(i);
            }
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayRemoved(int i) {
            synchronized (com.android.server.input.PointerIconCache.this.mLoadedPointerIconsByDisplayAndType) {
                com.android.server.input.PointerIconCache.this.mLoadedPointerIconsByDisplayAndType.remove(i);
                com.android.server.input.PointerIconCache.this.mDisplayContexts.remove(i);
                com.android.server.input.PointerIconCache.this.mDisplayDensities.delete(i);
            }
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayChanged(int i) {
            com.android.server.input.PointerIconCache.this.handleDisplayChanged(i);
        }
    };

    PointerIconCache(android.content.Context context, com.android.server.input.NativeInputManagerService nativeInputManagerService) {
        this.mContext = context;
        this.mNative = nativeInputManagerService;
    }

    public void systemRunning() {
        android.hardware.display.DisplayManager displayManager = (android.hardware.display.DisplayManager) this.mContext.getSystemService(android.hardware.display.DisplayManager.class);
        java.util.Objects.requireNonNull(displayManager);
        displayManager.registerDisplayListener(this.mDisplayListener, this.mUiThreadHandler);
        for (android.view.Display display : displayManager.getDisplays()) {
            this.mDisplayListener.onDisplayAdded(display.getDisplayId());
        }
    }

    public void monitor() {
        synchronized (this.mLoadedPointerIconsByDisplayAndType) {
        }
    }

    public void setUseLargePointerIcons(final boolean z) {
        this.mUiThreadHandler.post(new java.lang.Runnable() { // from class: com.android.server.input.PointerIconCache$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.input.PointerIconCache.this.lambda$setUseLargePointerIcons$0(z);
            }
        });
    }

    @android.annotation.NonNull
    public android.view.PointerIcon getLoadedPointerIcon(int i, int i2) {
        android.view.PointerIcon pointerIcon;
        synchronized (this.mLoadedPointerIconsByDisplayAndType) {
            try {
                android.util.SparseArray<android.view.PointerIcon> sparseArray = this.mLoadedPointerIconsByDisplayAndType.get(i);
                if (sparseArray == null) {
                    sparseArray = new android.util.SparseArray<>();
                    this.mLoadedPointerIconsByDisplayAndType.put(i, sparseArray);
                }
                pointerIcon = sparseArray.get(i2);
                if (pointerIcon == null) {
                    pointerIcon = android.view.PointerIcon.getLoadedSystemIcon(getContextForDisplayLocked(i), i2, this.mUseLargePointerIcons);
                    sparseArray.put(i2, pointerIcon);
                }
                java.util.Objects.requireNonNull(pointerIcon);
                android.view.PointerIcon pointerIcon2 = pointerIcon;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return pointerIcon;
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLoadedPointerIconsByDisplayAndType"})
    private android.content.Context getContextForDisplayLocked(int i) {
        if (i == -1) {
            return this.mContext;
        }
        if (i == this.mContext.getDisplay().getDisplayId()) {
            return this.mContext;
        }
        android.content.Context context = this.mDisplayContexts.get(i);
        if (context == null) {
            android.hardware.display.DisplayManager displayManager = (android.hardware.display.DisplayManager) this.mContext.getSystemService(android.hardware.display.DisplayManager.class);
            java.util.Objects.requireNonNull(displayManager);
            android.view.Display display = displayManager.getDisplay(i);
            if (display == null) {
                return this.mContext;
            }
            android.content.Context createDisplayContext = this.mContext.createDisplayContext(display);
            this.mDisplayContexts.put(i, createDisplayContext);
            return createDisplayContext;
        }
        return context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDisplayChanged(int i) {
        synchronized (this.mLoadedPointerIconsByDisplayAndType) {
            try {
                if (updateDisplayDensityLocked(i)) {
                    android.util.Slog.i(TAG, "Reloading pointer icons due to density change on display: " + i);
                    android.util.SparseArray<android.view.PointerIcon> sparseArray = this.mLoadedPointerIconsByDisplayAndType.get(i);
                    if (sparseArray == null) {
                        return;
                    }
                    sparseArray.clear();
                    this.mDisplayContexts.remove(i);
                    this.mNative.reloadPointerIcons();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: handleSetUseLargePointerIcons, reason: merged with bridge method [inline-methods] */
    public void lambda$setUseLargePointerIcons$0(boolean z) {
        synchronized (this.mLoadedPointerIconsByDisplayAndType) {
            try {
                if (this.mUseLargePointerIcons == z) {
                    return;
                }
                this.mUseLargePointerIcons = z;
                this.mLoadedPointerIconsByDisplayAndType.clear();
                this.mNative.reloadPointerIcons();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLoadedPointerIconsByDisplayAndType"})
    public boolean updateDisplayDensityLocked(int i) {
        android.hardware.display.DisplayManager displayManager = (android.hardware.display.DisplayManager) this.mContext.getSystemService(android.hardware.display.DisplayManager.class);
        java.util.Objects.requireNonNull(displayManager);
        android.view.Display display = displayManager.getDisplay(i);
        if (display == null) {
            return false;
        }
        android.view.DisplayInfo displayInfo = new android.view.DisplayInfo();
        display.getDisplayInfo(displayInfo);
        if (this.mDisplayDensities.get(i, 0) == displayInfo.logicalDensityDpi) {
            return false;
        }
        this.mDisplayDensities.put(i, displayInfo.logicalDensityDpi);
        return true;
    }
}
