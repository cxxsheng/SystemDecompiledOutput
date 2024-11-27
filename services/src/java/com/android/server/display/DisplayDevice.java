package com.android.server.display;

/* loaded from: classes.dex */
abstract class DisplayDevice {
    private static final android.view.Display.Mode EMPTY_DISPLAY_MODE = new android.view.Display.Mode.Builder().build();
    private static final java.lang.String TAG = "DisplayDevice";
    private final android.content.Context mContext;
    private android.graphics.Rect mCurrentDisplayRect;
    private android.graphics.Rect mCurrentLayerStackRect;
    private android.view.Surface mCurrentSurface;
    com.android.server.display.DisplayDeviceInfo mDebugLastLoggedDeviceInfo;
    private final com.android.server.display.DisplayAdapter mDisplayAdapter;
    private final android.os.IBinder mDisplayToken;
    private final java.lang.String mUniqueId;
    private int mCurrentLayerStack = -1;
    private int mCurrentFlags = 0;
    private int mCurrentOrientation = -1;
    protected com.android.server.display.DisplayDeviceConfig mDisplayDeviceConfig = null;

    public abstract com.android.server.display.DisplayDeviceInfo getDisplayDeviceInfoLocked();

    public abstract boolean hasStableUniqueId();

    public DisplayDevice(com.android.server.display.DisplayAdapter displayAdapter, android.os.IBinder iBinder, java.lang.String str, android.content.Context context) {
        this.mDisplayAdapter = displayAdapter;
        this.mDisplayToken = iBinder;
        this.mUniqueId = str;
        this.mContext = context;
    }

    public final com.android.server.display.DisplayAdapter getAdapterLocked() {
        return this.mDisplayAdapter;
    }

    public com.android.server.display.DisplayDeviceConfig getDisplayDeviceConfig() {
        if (this.mDisplayDeviceConfig == null) {
            this.mDisplayDeviceConfig = loadDisplayDeviceConfig();
        }
        return this.mDisplayDeviceConfig;
    }

    public final android.os.IBinder getDisplayTokenLocked() {
        return this.mDisplayToken;
    }

    public int getDisplayIdToMirrorLocked() {
        return 0;
    }

    public boolean isWindowManagerMirroringLocked() {
        return false;
    }

    public void setWindowManagerMirroringLocked(boolean z) {
    }

    @android.annotation.Nullable
    public android.graphics.Point getDisplaySurfaceDefaultSizeLocked() {
        com.android.server.display.DisplayDeviceInfo displayDeviceInfoLocked = getDisplayDeviceInfoLocked();
        boolean z = true;
        if (this.mCurrentOrientation != 1 && this.mCurrentOrientation != 3) {
            z = false;
        }
        return z ? new android.graphics.Point(displayDeviceInfoLocked.height, displayDeviceInfoLocked.width) : new android.graphics.Point(displayDeviceInfoLocked.width, displayDeviceInfoLocked.height);
    }

    public final java.lang.String getNameLocked() {
        return getDisplayDeviceInfoLocked().name;
    }

    public final java.lang.String getUniqueId() {
        return this.mUniqueId;
    }

    public void applyPendingDisplayDeviceInfoChangesLocked() {
    }

    public void performTraversalLocked(android.view.SurfaceControl.Transaction transaction) {
    }

    public java.lang.Runnable requestDisplayStateLocked(int i, float f, float f2, @android.annotation.Nullable com.android.server.display.DisplayOffloadSessionImpl displayOffloadSessionImpl) {
        return null;
    }

    public void setDesiredDisplayModeSpecsLocked(com.android.server.display.mode.DisplayModeDirector.DesiredDisplayModeSpecs desiredDisplayModeSpecs) {
    }

    public void setUserPreferredDisplayModeLocked(android.view.Display.Mode mode) {
    }

    public android.view.Display.Mode getUserPreferredDisplayModeLocked() {
        return EMPTY_DISPLAY_MODE;
    }

    public android.view.Display.Mode getSystemPreferredDisplayModeLocked() {
        return EMPTY_DISPLAY_MODE;
    }

    public android.view.Display.Mode getActiveDisplayModeAtStartLocked() {
        return EMPTY_DISPLAY_MODE;
    }

    public void setRequestedColorModeLocked(int i) {
    }

    public void setAutoLowLatencyModeLocked(boolean z) {
    }

    public void setGameContentTypeLocked(boolean z) {
    }

    public void onOverlayChangedLocked() {
    }

    public final void setLayerStackLocked(android.view.SurfaceControl.Transaction transaction, int i, int i2) {
        if (this.mCurrentLayerStack != i) {
            this.mCurrentLayerStack = i;
            transaction.setDisplayLayerStack(this.mDisplayToken, i);
            android.util.Slog.i(TAG, "[" + i2 + "] Layerstack set to " + i + " for " + this.mUniqueId);
        }
    }

    public final void setDisplayFlagsLocked(android.view.SurfaceControl.Transaction transaction, int i) {
        if (this.mCurrentFlags != i) {
            this.mCurrentFlags = i;
            transaction.setDisplayFlags(this.mDisplayToken, i);
        }
    }

    public final void setProjectionLocked(android.view.SurfaceControl.Transaction transaction, int i, android.graphics.Rect rect, android.graphics.Rect rect2) {
        if (this.mCurrentOrientation != i || this.mCurrentLayerStackRect == null || !this.mCurrentLayerStackRect.equals(rect) || this.mCurrentDisplayRect == null || !this.mCurrentDisplayRect.equals(rect2)) {
            this.mCurrentOrientation = i;
            if (this.mCurrentLayerStackRect == null) {
                this.mCurrentLayerStackRect = new android.graphics.Rect();
            }
            this.mCurrentLayerStackRect.set(rect);
            if (this.mCurrentDisplayRect == null) {
                this.mCurrentDisplayRect = new android.graphics.Rect();
            }
            this.mCurrentDisplayRect.set(rect2);
            transaction.setDisplayProjection(this.mDisplayToken, i, rect, rect2);
        }
    }

    public final void setSurfaceLocked(android.view.SurfaceControl.Transaction transaction, android.view.Surface surface) {
        if (this.mCurrentSurface != surface) {
            this.mCurrentSurface = surface;
            transaction.setDisplaySurface(this.mDisplayToken, surface);
        }
    }

    public final void populateViewportLocked(android.hardware.display.DisplayViewport displayViewport) {
        displayViewport.orientation = this.mCurrentOrientation;
        if (this.mCurrentLayerStackRect != null) {
            displayViewport.logicalFrame.set(this.mCurrentLayerStackRect);
        } else {
            displayViewport.logicalFrame.setEmpty();
        }
        if (this.mCurrentDisplayRect != null) {
            displayViewport.physicalFrame.set(this.mCurrentDisplayRect);
        } else {
            displayViewport.physicalFrame.setEmpty();
        }
        boolean z = true;
        if (this.mCurrentOrientation != 1 && this.mCurrentOrientation != 3) {
            z = false;
        }
        com.android.server.display.DisplayDeviceInfo displayDeviceInfoLocked = getDisplayDeviceInfoLocked();
        displayViewport.deviceWidth = z ? displayDeviceInfoLocked.height : displayDeviceInfoLocked.width;
        displayViewport.deviceHeight = z ? displayDeviceInfoLocked.width : displayDeviceInfoLocked.height;
        displayViewport.uniqueId = displayDeviceInfoLocked.uniqueId;
        if (displayDeviceInfoLocked.address instanceof android.view.DisplayAddress.Physical) {
            displayViewport.physicalPort = java.lang.Integer.valueOf(displayDeviceInfoLocked.address.getPort());
        } else {
            displayViewport.physicalPort = null;
        }
    }

    public void dumpLocked(java.io.PrintWriter printWriter) {
        printWriter.println("mAdapter=" + this.mDisplayAdapter.getName());
        printWriter.println("mUniqueId=" + this.mUniqueId);
        printWriter.println("mDisplayToken=" + this.mDisplayToken);
        printWriter.println("mCurrentLayerStack=" + this.mCurrentLayerStack);
        printWriter.println("mCurrentFlags=" + this.mCurrentFlags);
        printWriter.println("mCurrentOrientation=" + this.mCurrentOrientation);
        printWriter.println("mCurrentLayerStackRect=" + this.mCurrentLayerStackRect);
        printWriter.println("mCurrentDisplayRect=" + this.mCurrentDisplayRect);
        printWriter.println("mCurrentSurface=" + this.mCurrentSurface);
    }

    private com.android.server.display.DisplayDeviceConfig loadDisplayDeviceConfig() {
        return com.android.server.display.DisplayDeviceConfig.create(this.mContext, false, this.mDisplayAdapter.getFeatureFlags());
    }
}
