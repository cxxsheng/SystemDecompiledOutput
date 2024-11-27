package com.android.server.display;

@com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
/* loaded from: classes.dex */
public class VirtualDisplayAdapter extends com.android.server.display.DisplayAdapter {
    static final java.lang.String TAG = "VirtualDisplayAdapter";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String UNIQUE_ID_PREFIX = "virtual:";
    private static final java.util.concurrent.atomic.AtomicInteger sNextUniqueIndex = new java.util.concurrent.atomic.AtomicInteger(0);
    private final android.os.Handler mHandler;
    private final com.android.server.display.VirtualDisplayAdapter.SurfaceControlDisplayFactory mSurfaceControlDisplayFactory;
    private final android.util.ArrayMap<android.os.IBinder, com.android.server.display.VirtualDisplayAdapter.VirtualDisplayDevice> mVirtualDisplayDevices;

    @com.android.internal.annotations.VisibleForTesting
    public interface SurfaceControlDisplayFactory {
        android.os.IBinder createDisplay(java.lang.String str, boolean z, float f);

        void destroyDisplay(android.os.IBinder iBinder);
    }

    @Override // com.android.server.display.DisplayAdapter
    public /* bridge */ /* synthetic */ void dumpLocked(java.io.PrintWriter printWriter) {
        super.dumpLocked(printWriter);
    }

    @Override // com.android.server.display.DisplayAdapter
    public /* bridge */ /* synthetic */ void registerLocked() {
        super.registerLocked();
    }

    public VirtualDisplayAdapter(com.android.server.display.DisplayManagerService.SyncRoot syncRoot, android.content.Context context, android.os.Handler handler, com.android.server.display.DisplayAdapter.Listener listener, com.android.server.display.feature.DisplayManagerFlags displayManagerFlags) {
        this(syncRoot, context, handler, listener, new com.android.server.display.VirtualDisplayAdapter.SurfaceControlDisplayFactory() { // from class: com.android.server.display.VirtualDisplayAdapter.1
            @Override // com.android.server.display.VirtualDisplayAdapter.SurfaceControlDisplayFactory
            public android.os.IBinder createDisplay(java.lang.String str, boolean z, float f) {
                return com.android.server.display.DisplayControl.createDisplay(str, z, f);
            }

            @Override // com.android.server.display.VirtualDisplayAdapter.SurfaceControlDisplayFactory
            public void destroyDisplay(android.os.IBinder iBinder) {
                com.android.server.display.DisplayControl.destroyDisplay(iBinder);
            }
        }, displayManagerFlags);
    }

    @com.android.internal.annotations.VisibleForTesting
    VirtualDisplayAdapter(com.android.server.display.DisplayManagerService.SyncRoot syncRoot, android.content.Context context, android.os.Handler handler, com.android.server.display.DisplayAdapter.Listener listener, com.android.server.display.VirtualDisplayAdapter.SurfaceControlDisplayFactory surfaceControlDisplayFactory, com.android.server.display.feature.DisplayManagerFlags displayManagerFlags) {
        super(syncRoot, context, handler, listener, TAG, displayManagerFlags);
        this.mVirtualDisplayDevices = new android.util.ArrayMap<>();
        this.mHandler = handler;
        this.mSurfaceControlDisplayFactory = surfaceControlDisplayFactory;
    }

    public com.android.server.display.DisplayDevice createVirtualDisplayLocked(android.hardware.display.IVirtualDisplayCallback iVirtualDisplayCallback, android.media.projection.IMediaProjection iMediaProjection, int i, java.lang.String str, java.lang.String str2, android.view.Surface surface, int i2, android.hardware.display.VirtualDisplayConfig virtualDisplayConfig) {
        com.android.server.display.VirtualDisplayAdapter.MediaProjectionCallback mediaProjectionCallback;
        java.lang.String str3;
        boolean z;
        android.os.RemoteException e;
        android.os.IBinder asBinder = iVirtualDisplayCallback.asBinder();
        if (this.mVirtualDisplayDevices.containsKey(asBinder)) {
            android.util.Slog.wtfStack(TAG, "Can't create virtual display, display with same appToken already exists");
            return null;
        }
        android.os.IBinder createDisplay = this.mSurfaceControlDisplayFactory.createDisplay(virtualDisplayConfig.getName(), (i2 & 4) != 0, virtualDisplayConfig.getRequestedRefreshRate());
        if (iMediaProjection != null) {
            mediaProjectionCallback = new com.android.server.display.VirtualDisplayAdapter.MediaProjectionCallback(asBinder);
        } else {
            mediaProjectionCallback = null;
        }
        com.android.server.display.VirtualDisplayAdapter.Callback callback = new com.android.server.display.VirtualDisplayAdapter.Callback(iVirtualDisplayCallback, this.mHandler);
        com.android.server.display.VirtualDisplayAdapter.MediaProjectionCallback mediaProjectionCallback2 = mediaProjectionCallback;
        com.android.server.display.VirtualDisplayAdapter.VirtualDisplayDevice virtualDisplayDevice = new com.android.server.display.VirtualDisplayAdapter.VirtualDisplayDevice(createDisplay, asBinder, i, str, surface, i2, callback, iMediaProjection, mediaProjectionCallback2, str2, virtualDisplayConfig);
        this.mVirtualDisplayDevices.put(asBinder, virtualDisplayDevice);
        if (iMediaProjection == null) {
            str3 = TAG;
        } else {
            try {
                iMediaProjection.registerCallback(mediaProjectionCallback2);
                str3 = TAG;
                try {
                    android.util.Slog.d(str3, "Virtual Display: registered media projection callback for new VirtualDisplayDevice");
                } catch (android.os.RemoteException e2) {
                    e = e2;
                    z = false;
                    android.util.Slog.e(str3, "Virtual Display: error while setting up VirtualDisplayDevice", e);
                    this.mVirtualDisplayDevices.remove(asBinder);
                    virtualDisplayDevice.destroyLocked(z);
                    return null;
                }
            } catch (android.os.RemoteException e3) {
                e = e3;
                str3 = TAG;
            }
        }
        z = false;
        try {
            asBinder.linkToDeath(virtualDisplayDevice, 0);
            return virtualDisplayDevice;
        } catch (android.os.RemoteException e4) {
            e = e4;
            android.util.Slog.e(str3, "Virtual Display: error while setting up VirtualDisplayDevice", e);
            this.mVirtualDisplayDevices.remove(asBinder);
            virtualDisplayDevice.destroyLocked(z);
            return null;
        }
    }

    public void resizeVirtualDisplayLocked(android.os.IBinder iBinder, int i, int i2, int i3) {
        com.android.server.display.VirtualDisplayAdapter.VirtualDisplayDevice virtualDisplayDevice = this.mVirtualDisplayDevices.get(iBinder);
        if (virtualDisplayDevice != null) {
            android.util.Slog.v(TAG, "Resize VirtualDisplay " + virtualDisplayDevice.mName + " to " + i + " " + i2);
            virtualDisplayDevice.resizeLocked(i, i2, i3);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    android.view.Surface getVirtualDisplaySurfaceLocked(android.os.IBinder iBinder) {
        com.android.server.display.VirtualDisplayAdapter.VirtualDisplayDevice virtualDisplayDevice = this.mVirtualDisplayDevices.get(iBinder);
        if (virtualDisplayDevice != null) {
            return virtualDisplayDevice.getSurfaceLocked();
        }
        return null;
    }

    public void setVirtualDisplaySurfaceLocked(android.os.IBinder iBinder, android.view.Surface surface) {
        com.android.server.display.VirtualDisplayAdapter.VirtualDisplayDevice virtualDisplayDevice = this.mVirtualDisplayDevices.get(iBinder);
        if (virtualDisplayDevice != null) {
            android.util.Slog.v(TAG, "Update surface for VirtualDisplay " + virtualDisplayDevice.mName);
            virtualDisplayDevice.setSurfaceLocked(surface);
        }
    }

    void setDisplayIdToMirror(android.os.IBinder iBinder, int i) {
        com.android.server.display.VirtualDisplayAdapter.VirtualDisplayDevice virtualDisplayDevice = this.mVirtualDisplayDevices.get(iBinder);
        if (virtualDisplayDevice != null) {
            virtualDisplayDevice.setDisplayIdToMirror(i);
        }
    }

    public com.android.server.display.DisplayDevice releaseVirtualDisplayLocked(android.os.IBinder iBinder) {
        com.android.server.display.VirtualDisplayAdapter.VirtualDisplayDevice remove = this.mVirtualDisplayDevices.remove(iBinder);
        if (remove != null) {
            android.util.Slog.v(TAG, "Release VirtualDisplay " + remove.mName);
            remove.destroyLocked(true);
            iBinder.unlinkToDeath(remove, 0);
        }
        return remove;
    }

    void setVirtualDisplayStateLocked(android.os.IBinder iBinder, boolean z) {
        com.android.server.display.VirtualDisplayAdapter.VirtualDisplayDevice virtualDisplayDevice = this.mVirtualDisplayDevices.get(iBinder);
        if (virtualDisplayDevice != null) {
            virtualDisplayDevice.setDisplayState(z);
        }
    }

    static java.lang.String generateDisplayUniqueId(java.lang.String str, int i, android.hardware.display.VirtualDisplayConfig virtualDisplayConfig) {
        java.lang.String str2;
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(UNIQUE_ID_PREFIX);
        sb.append(str);
        if (virtualDisplayConfig.getUniqueId() != null) {
            str2 = ":" + virtualDisplayConfig.getUniqueId();
        } else {
            str2 = "," + i + "," + virtualDisplayConfig.getName() + "," + sNextUniqueIndex.getAndIncrement();
        }
        sb.append(str2);
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleBinderDiedLocked(android.os.IBinder iBinder) {
        this.mVirtualDisplayDevices.remove(iBinder);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleMediaProjectionStoppedLocked(android.os.IBinder iBinder) {
        com.android.server.display.VirtualDisplayAdapter.VirtualDisplayDevice virtualDisplayDevice = this.mVirtualDisplayDevices.get(iBinder);
        if (virtualDisplayDevice != null) {
            android.util.Slog.i(TAG, "Virtual display device released because media projection stopped: " + virtualDisplayDevice.mName);
            virtualDisplayDevice.stopLocked();
        }
    }

    private final class VirtualDisplayDevice extends com.android.server.display.DisplayDevice implements android.os.IBinder.DeathRecipient {
        private static final int PENDING_RESIZE = 2;
        private static final int PENDING_SURFACE_CHANGE = 1;
        private static final float REFRESH_RATE = 60.0f;
        private final android.os.IBinder mAppToken;
        private final com.android.server.display.VirtualDisplayAdapter.Callback mCallback;
        private int mDensityDpi;
        private int mDisplayIdToMirror;
        private int mDisplayState;
        private final int mFlags;
        private int mHeight;
        private com.android.server.display.DisplayDeviceInfo mInfo;
        private boolean mIsDisplayOn;
        private boolean mIsWindowManagerMirroring;

        @android.annotation.Nullable
        private final android.media.projection.IMediaProjectionCallback mMediaProjectionCallback;
        private android.view.Display.Mode mMode;
        final java.lang.String mName;
        final java.lang.String mOwnerPackageName;
        private final int mOwnerUid;
        private int mPendingChanges;

        @android.annotation.Nullable
        private final android.media.projection.IMediaProjection mProjection;
        private float mRequestedRefreshRate;
        private boolean mStopped;
        private android.view.Surface mSurface;
        private int mWidth;

        public VirtualDisplayDevice(android.os.IBinder iBinder, android.os.IBinder iBinder2, int i, java.lang.String str, android.view.Surface surface, int i2, com.android.server.display.VirtualDisplayAdapter.Callback callback, android.media.projection.IMediaProjection iMediaProjection, android.media.projection.IMediaProjectionCallback iMediaProjectionCallback, java.lang.String str2, android.hardware.display.VirtualDisplayConfig virtualDisplayConfig) {
            super(com.android.server.display.VirtualDisplayAdapter.this, iBinder, str2, com.android.server.display.VirtualDisplayAdapter.this.getContext());
            this.mAppToken = iBinder2;
            this.mOwnerUid = i;
            this.mOwnerPackageName = str;
            this.mName = virtualDisplayConfig.getName();
            this.mWidth = virtualDisplayConfig.getWidth();
            this.mHeight = virtualDisplayConfig.getHeight();
            this.mDensityDpi = virtualDisplayConfig.getDensityDpi();
            this.mRequestedRefreshRate = virtualDisplayConfig.getRequestedRefreshRate();
            this.mMode = com.android.server.display.DisplayAdapter.createMode(this.mWidth, this.mHeight, getRefreshRate());
            this.mSurface = surface;
            this.mFlags = i2;
            this.mCallback = callback;
            this.mProjection = iMediaProjection;
            this.mMediaProjectionCallback = iMediaProjectionCallback;
            this.mDisplayState = 0;
            this.mPendingChanges |= 1;
            this.mIsDisplayOn = surface != null;
            this.mDisplayIdToMirror = virtualDisplayConfig.getDisplayIdToMirror();
            this.mIsWindowManagerMirroring = virtualDisplayConfig.isWindowManagerMirroringEnabled();
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            synchronized (com.android.server.display.VirtualDisplayAdapter.this.getSyncRoot()) {
                com.android.server.display.VirtualDisplayAdapter.this.handleBinderDiedLocked(this.mAppToken);
                android.util.Slog.i(com.android.server.display.VirtualDisplayAdapter.TAG, "Virtual display device released because application token died: " + this.mOwnerPackageName);
                destroyLocked(false);
                if (this.mProjection != null && this.mMediaProjectionCallback != null) {
                    try {
                        this.mProjection.unregisterCallback(this.mMediaProjectionCallback);
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.w(com.android.server.display.VirtualDisplayAdapter.TAG, "Failed to unregister callback in binderDied", e);
                    }
                }
                com.android.server.display.VirtualDisplayAdapter.this.sendDisplayDeviceEventLocked(this, 3);
            }
        }

        public void destroyLocked(boolean z) {
            if (this.mSurface != null) {
                this.mSurface.release();
                this.mSurface = null;
            }
            com.android.server.display.VirtualDisplayAdapter.this.mSurfaceControlDisplayFactory.destroyDisplay(getDisplayTokenLocked());
            if (this.mProjection != null && this.mMediaProjectionCallback != null) {
                try {
                    this.mProjection.unregisterCallback(this.mMediaProjectionCallback);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.w(com.android.server.display.VirtualDisplayAdapter.TAG, "Failed to unregister callback in destroy", e);
                }
            }
            if (z) {
                this.mCallback.dispatchDisplayStopped();
            }
        }

        @Override // com.android.server.display.DisplayDevice
        public int getDisplayIdToMirrorLocked() {
            return this.mDisplayIdToMirror;
        }

        void setDisplayIdToMirror(int i) {
            if (this.mDisplayIdToMirror != i) {
                this.mDisplayIdToMirror = i;
                this.mInfo = null;
                com.android.server.display.VirtualDisplayAdapter.this.sendDisplayDeviceEventLocked(this, 2);
                com.android.server.display.VirtualDisplayAdapter.this.sendTraversalRequestLocked();
            }
        }

        @Override // com.android.server.display.DisplayDevice
        public boolean isWindowManagerMirroringLocked() {
            return this.mIsWindowManagerMirroring;
        }

        @Override // com.android.server.display.DisplayDevice
        public void setWindowManagerMirroringLocked(boolean z) {
            if (this.mIsWindowManagerMirroring != z) {
                this.mIsWindowManagerMirroring = z;
                com.android.server.display.VirtualDisplayAdapter.this.sendDisplayDeviceEventLocked(this, 2);
                com.android.server.display.VirtualDisplayAdapter.this.sendTraversalRequestLocked();
            }
        }

        @Override // com.android.server.display.DisplayDevice
        public android.graphics.Point getDisplaySurfaceDefaultSizeLocked() {
            if (this.mSurface == null) {
                return null;
            }
            return this.mSurface.getDefaultSize();
        }

        @com.android.internal.annotations.VisibleForTesting
        android.view.Surface getSurfaceLocked() {
            return this.mSurface;
        }

        @Override // com.android.server.display.DisplayDevice
        public boolean hasStableUniqueId() {
            return false;
        }

        @Override // com.android.server.display.DisplayDevice
        public java.lang.Runnable requestDisplayStateLocked(int i, float f, float f2, com.android.server.display.DisplayOffloadSessionImpl displayOffloadSessionImpl) {
            if (i != this.mDisplayState) {
                this.mDisplayState = i;
                if (i == 1) {
                    this.mCallback.dispatchDisplayPaused();
                    return null;
                }
                this.mCallback.dispatchDisplayResumed();
                return null;
            }
            return null;
        }

        @Override // com.android.server.display.DisplayDevice
        public void performTraversalLocked(android.view.SurfaceControl.Transaction transaction) {
            if ((this.mPendingChanges & 2) != 0) {
                transaction.setDisplaySize(getDisplayTokenLocked(), this.mWidth, this.mHeight);
            }
            if ((this.mPendingChanges & 1) != 0) {
                setSurfaceLocked(transaction, this.mSurface);
            }
            this.mPendingChanges = 0;
        }

        public void setSurfaceLocked(android.view.Surface surface) {
            if (!this.mStopped && this.mSurface != surface) {
                if ((this.mSurface != null) != (surface != null)) {
                    com.android.server.display.VirtualDisplayAdapter.this.sendDisplayDeviceEventLocked(this, 2);
                }
                com.android.server.display.VirtualDisplayAdapter.this.sendTraversalRequestLocked();
                this.mSurface = surface;
                this.mInfo = null;
                this.mPendingChanges |= 1;
            }
        }

        public void resizeLocked(int i, int i2, int i3) {
            if (this.mWidth != i || this.mHeight != i2 || this.mDensityDpi != i3) {
                com.android.server.display.VirtualDisplayAdapter.this.sendDisplayDeviceEventLocked(this, 2);
                com.android.server.display.VirtualDisplayAdapter.this.sendTraversalRequestLocked();
                this.mWidth = i;
                this.mHeight = i2;
                this.mMode = com.android.server.display.DisplayAdapter.createMode(i, i2, getRefreshRate());
                this.mDensityDpi = i3;
                this.mInfo = null;
                this.mPendingChanges |= 2;
            }
        }

        void setDisplayState(boolean z) {
            if (this.mIsDisplayOn != z) {
                this.mIsDisplayOn = z;
                this.mInfo = null;
                com.android.server.display.VirtualDisplayAdapter.this.sendDisplayDeviceEventLocked(this, 2);
            }
        }

        public void stopLocked() {
            android.util.Slog.d(com.android.server.display.VirtualDisplayAdapter.TAG, "Virtual Display: stopping device " + this.mName);
            setSurfaceLocked(null);
            this.mStopped = true;
        }

        @Override // com.android.server.display.DisplayDevice
        public void dumpLocked(java.io.PrintWriter printWriter) {
            super.dumpLocked(printWriter);
            printWriter.println("mFlags=" + this.mFlags);
            printWriter.println("mDisplayState=" + android.view.Display.stateToString(this.mDisplayState));
            printWriter.println("mStopped=" + this.mStopped);
            printWriter.println("mDisplayIdToMirror=" + this.mDisplayIdToMirror);
            printWriter.println("mWindowManagerMirroring=" + this.mIsWindowManagerMirroring);
            printWriter.println("mRequestedRefreshRate=" + this.mRequestedRefreshRate);
        }

        @Override // com.android.server.display.DisplayDevice
        public com.android.server.display.DisplayDeviceInfo getDisplayDeviceInfoLocked() {
            if (this.mInfo == null) {
                this.mInfo = new com.android.server.display.DisplayDeviceInfo();
                this.mInfo.name = this.mName;
                this.mInfo.uniqueId = getUniqueId();
                this.mInfo.width = this.mWidth;
                this.mInfo.height = this.mHeight;
                this.mInfo.modeId = this.mMode.getModeId();
                this.mInfo.renderFrameRate = this.mMode.getRefreshRate();
                this.mInfo.defaultModeId = this.mMode.getModeId();
                this.mInfo.supportedModes = new android.view.Display.Mode[]{this.mMode};
                this.mInfo.densityDpi = this.mDensityDpi;
                this.mInfo.xDpi = this.mDensityDpi;
                this.mInfo.yDpi = this.mDensityDpi;
                this.mInfo.presentationDeadlineNanos = 1000000000 / ((int) getRefreshRate());
                this.mInfo.flags = 0;
                if ((this.mFlags & 1) == 0) {
                    this.mInfo.flags |= 48;
                }
                if ((this.mFlags & 16) != 0) {
                    this.mInfo.flags &= -33;
                } else {
                    this.mInfo.flags |= 128;
                    if ((this.mFlags & 2048) != 0) {
                        this.mInfo.flags |= 16384;
                    }
                }
                if ((this.mFlags & 32768) != 0) {
                    this.mInfo.flags |= 262144;
                }
                if ((this.mFlags & 4) != 0) {
                    this.mInfo.flags |= 4;
                }
                if ((this.mFlags & 2) != 0) {
                    this.mInfo.flags |= 64;
                    if ((this.mFlags & 1) != 0 && "portrait".equals(android.os.SystemProperties.get("persist.demo.remoterotation"))) {
                        this.mInfo.rotation = 3;
                    }
                }
                if ((this.mFlags & 32) != 0) {
                    this.mInfo.flags |= 512;
                }
                if ((this.mFlags & 128) != 0) {
                    this.mInfo.flags |= 2;
                }
                if ((this.mFlags & 256) != 0) {
                    this.mInfo.flags |= 1024;
                }
                if ((this.mFlags & 512) != 0) {
                    this.mInfo.flags |= 4096;
                }
                if ((this.mFlags & 1024) != 0) {
                    this.mInfo.flags |= 8192;
                }
                if ((this.mFlags & 4096) != 0) {
                    if ((this.mInfo.flags & 16384) != 0 || (this.mFlags & 32768) != 0) {
                        com.android.server.display.DisplayDeviceInfo displayDeviceInfo = this.mInfo;
                        displayDeviceInfo.flags = 32768 | displayDeviceInfo.flags;
                    } else {
                        android.util.Slog.w(com.android.server.display.VirtualDisplayAdapter.TAG, "Ignoring VIRTUAL_DISPLAY_FLAG_ALWAYS_UNLOCKED as it requires VIRTUAL_DISPLAY_FLAG_DEVICE_DISPLAY_GROUP or VIRTUAL_DISPLAY_FLAG_OWN_DISPLAY_GROUP.");
                    }
                }
                if ((this.mFlags & 8192) != 0) {
                    this.mInfo.flags |= 65536;
                }
                if ((this.mFlags & 16384) != 0) {
                    if ((this.mFlags & 1024) != 0) {
                        this.mInfo.flags |= 131072;
                    } else {
                        android.util.Slog.w(com.android.server.display.VirtualDisplayAdapter.TAG, "Ignoring VIRTUAL_DISPLAY_FLAG_OWN_FOCUS as it requires VIRTUAL_DISPLAY_FLAG_TRUSTED.");
                    }
                }
                if ((this.mFlags & 65536) != 0) {
                    if ((this.mFlags & 1024) != 0 && (this.mFlags & 16384) != 0) {
                        this.mInfo.flags |= 524288;
                    } else {
                        android.util.Slog.w(com.android.server.display.VirtualDisplayAdapter.TAG, "Ignoring VIRTUAL_DISPLAY_FLAG_STEAL_TOP_FOCUS_DISABLED as it requires VIRTUAL_DISPLAY_FLAG_OWN_FOCUS which requires VIRTUAL_DISPLAY_FLAG_TRUSTED.");
                    }
                }
                this.mInfo.type = 5;
                this.mInfo.touch = (this.mFlags & 64) == 0 ? 0 : 3;
                this.mInfo.state = this.mIsDisplayOn ? 2 : 1;
                this.mInfo.ownerUid = this.mOwnerUid;
                this.mInfo.ownerPackageName = this.mOwnerPackageName;
                this.mInfo.displayShape = android.view.DisplayShape.createDefaultDisplayShape(this.mInfo.width, this.mInfo.height, false);
            }
            return this.mInfo;
        }

        private float getRefreshRate() {
            if (this.mRequestedRefreshRate != com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                return this.mRequestedRefreshRate;
            }
            return 60.0f;
        }
    }

    private static class Callback extends android.os.Handler {
        private static final int MSG_ON_DISPLAY_PAUSED = 0;
        private static final int MSG_ON_DISPLAY_RESUMED = 1;
        private static final int MSG_ON_DISPLAY_STOPPED = 2;
        private final android.hardware.display.IVirtualDisplayCallback mCallback;

        public Callback(android.hardware.display.IVirtualDisplayCallback iVirtualDisplayCallback, android.os.Handler handler) {
            super(handler.getLooper());
            this.mCallback = iVirtualDisplayCallback;
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            try {
                switch (message.what) {
                    case 0:
                        this.mCallback.onPaused();
                        break;
                    case 1:
                        this.mCallback.onResumed();
                        break;
                    case 2:
                        this.mCallback.onStopped();
                        break;
                }
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.display.VirtualDisplayAdapter.TAG, "Failed to notify listener of virtual display event.", e);
            }
        }

        public void dispatchDisplayPaused() {
            sendEmptyMessage(0);
        }

        public void dispatchDisplayResumed() {
            sendEmptyMessage(1);
        }

        public void dispatchDisplayStopped() {
            sendEmptyMessage(2);
        }
    }

    private final class MediaProjectionCallback extends android.media.projection.IMediaProjectionCallback.Stub {
        private android.os.IBinder mAppToken;

        public MediaProjectionCallback(android.os.IBinder iBinder) {
            this.mAppToken = iBinder;
        }

        public void onStop() {
            synchronized (com.android.server.display.VirtualDisplayAdapter.this.getSyncRoot()) {
                com.android.server.display.VirtualDisplayAdapter.this.handleMediaProjectionStoppedLocked(this.mAppToken);
            }
        }

        public void onCapturedContentResize(int i, int i2) {
        }

        public void onCapturedContentVisibilityChanged(boolean z) {
        }
    }
}
