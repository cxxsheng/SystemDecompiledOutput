package com.android.server.vr;

/* loaded from: classes2.dex */
class Vr2dDisplay {
    private static final boolean DEBUG = false;
    private static final java.lang.String DEBUG_ACTION_SET_MODE = "com.android.server.vr.Vr2dDisplay.SET_MODE";
    private static final java.lang.String DEBUG_ACTION_SET_SURFACE = "com.android.server.vr.Vr2dDisplay.SET_SURFACE";
    private static final java.lang.String DEBUG_EXTRA_MODE_ON = "com.android.server.vr.Vr2dDisplay.EXTRA_MODE_ON";
    private static final java.lang.String DEBUG_EXTRA_SURFACE = "com.android.server.vr.Vr2dDisplay.EXTRA_SURFACE";
    public static final int DEFAULT_VIRTUAL_DISPLAY_DPI = 320;
    public static final int DEFAULT_VIRTUAL_DISPLAY_HEIGHT = 1800;
    public static final int DEFAULT_VIRTUAL_DISPLAY_WIDTH = 1400;
    private static final java.lang.String DISPLAY_NAME = "VR 2D Display";
    public static final int MIN_VR_DISPLAY_DPI = 1;
    public static final int MIN_VR_DISPLAY_HEIGHT = 1;
    public static final int MIN_VR_DISPLAY_WIDTH = 1;
    private static final int STOP_VIRTUAL_DISPLAY_DELAY_MILLIS = 2000;
    private static final java.lang.String TAG = "Vr2dDisplay";
    private static final java.lang.String UNIQUE_DISPLAY_ID = "277f1a09-b88d-4d1e-8716-796f114d080b";
    private final android.app.ActivityManagerInternal mActivityManagerInternal;
    private final android.hardware.display.DisplayManager mDisplayManager;
    private android.media.ImageReader mImageReader;
    private boolean mIsPersistentVrModeEnabled;
    private boolean mIsVrModeOverrideEnabled;
    private java.lang.Runnable mStopVDRunnable;
    private android.view.Surface mSurface;
    private android.hardware.display.VirtualDisplay mVirtualDisplay;
    private final android.service.vr.IVrManager mVrManager;
    private final com.android.server.wm.WindowManagerInternal mWindowManagerInternal;
    private final java.lang.Object mVdLock = new java.lang.Object();
    private final android.os.Handler mHandler = new android.os.Handler();
    private final android.service.vr.IPersistentVrStateCallbacks mVrStateCallbacks = new android.service.vr.IPersistentVrStateCallbacks.Stub() { // from class: com.android.server.vr.Vr2dDisplay.1
        public void onPersistentVrStateChanged(boolean z) {
            if (z != com.android.server.vr.Vr2dDisplay.this.mIsPersistentVrModeEnabled) {
                com.android.server.vr.Vr2dDisplay.this.mIsPersistentVrModeEnabled = z;
                com.android.server.vr.Vr2dDisplay.this.updateVirtualDisplay();
            }
        }
    };
    private boolean mIsVirtualDisplayAllowed = true;
    private boolean mBootsToVr = false;
    private int mVirtualDisplayWidth = DEFAULT_VIRTUAL_DISPLAY_WIDTH;
    private int mVirtualDisplayHeight = 1800;
    private int mVirtualDisplayDpi = 320;

    public Vr2dDisplay(android.hardware.display.DisplayManager displayManager, android.app.ActivityManagerInternal activityManagerInternal, com.android.server.wm.WindowManagerInternal windowManagerInternal, android.service.vr.IVrManager iVrManager) {
        this.mDisplayManager = displayManager;
        this.mActivityManagerInternal = activityManagerInternal;
        this.mWindowManagerInternal = windowManagerInternal;
        this.mVrManager = iVrManager;
    }

    public void init(android.content.Context context, boolean z) {
        startVrModeListener();
        startDebugOnlyBroadcastReceiver(context);
        this.mBootsToVr = z;
        if (this.mBootsToVr) {
            updateVirtualDisplay();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateVirtualDisplay() {
        if (shouldRunVirtualDisplay()) {
            android.util.Log.i(TAG, "Attempting to start virtual display");
            startVirtualDisplay();
        } else {
            stopVirtualDisplay();
        }
    }

    private void startDebugOnlyBroadcastReceiver(android.content.Context context) {
    }

    private void startVrModeListener() {
        if (this.mVrManager != null) {
            try {
                this.mVrManager.registerPersistentVrStateListener(this.mVrStateCallbacks);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Could not register VR State listener.", e);
            }
        }
    }

    public void setVirtualDisplayProperties(android.app.Vr2dDisplayProperties vr2dDisplayProperties) {
        boolean z;
        synchronized (this.mVdLock) {
            try {
                int width = vr2dDisplayProperties.getWidth();
                int height = vr2dDisplayProperties.getHeight();
                int dpi = vr2dDisplayProperties.getDpi();
                if (width < 1 || height < 1 || dpi < 1) {
                    android.util.Log.i(TAG, "Ignoring Width/Height/Dpi values of " + width + "," + height + "," + dpi);
                    z = false;
                } else {
                    android.util.Log.i(TAG, "Setting width/height/dpi to " + width + "," + height + "," + dpi);
                    this.mVirtualDisplayWidth = width;
                    this.mVirtualDisplayHeight = height;
                    this.mVirtualDisplayDpi = dpi;
                    z = true;
                }
                if ((vr2dDisplayProperties.getAddedFlags() & 1) == 1) {
                    this.mIsVirtualDisplayAllowed = true;
                } else if ((vr2dDisplayProperties.getRemovedFlags() & 1) == 1) {
                    this.mIsVirtualDisplayAllowed = false;
                }
                if (this.mVirtualDisplay != null && z && this.mIsVirtualDisplayAllowed) {
                    this.mVirtualDisplay.resize(this.mVirtualDisplayWidth, this.mVirtualDisplayHeight, this.mVirtualDisplayDpi);
                    android.media.ImageReader imageReader = this.mImageReader;
                    this.mImageReader = null;
                    startImageReader();
                    imageReader.close();
                }
                updateVirtualDisplay();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public int getVirtualDisplayId() {
        synchronized (this.mVdLock) {
            try {
                if (this.mVirtualDisplay != null) {
                    return this.mVirtualDisplay.getDisplay().getDisplayId();
                }
                return -1;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void startVirtualDisplay() {
        if (this.mDisplayManager == null) {
            android.util.Log.w(TAG, "Cannot create virtual display because mDisplayManager == null");
            return;
        }
        synchronized (this.mVdLock) {
            try {
                if (this.mVirtualDisplay != null) {
                    android.util.Log.i(TAG, "VD already exists, ignoring request");
                    return;
                }
                android.hardware.display.VirtualDisplayConfig.Builder builder = new android.hardware.display.VirtualDisplayConfig.Builder(DISPLAY_NAME, this.mVirtualDisplayWidth, this.mVirtualDisplayHeight, this.mVirtualDisplayDpi);
                builder.setUniqueId(UNIQUE_DISPLAY_ID);
                builder.setFlags(1485);
                this.mVirtualDisplay = this.mDisplayManager.createVirtualDisplay(null, builder.build(), null, null);
                if (this.mVirtualDisplay != null) {
                    updateDisplayId(this.mVirtualDisplay.getDisplay().getDisplayId());
                    startImageReader();
                    android.util.Log.i(TAG, "VD created: " + this.mVirtualDisplay);
                    return;
                }
                android.util.Log.w(TAG, "Virtual display id is null after createVirtualDisplay");
                updateDisplayId(-1);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateDisplayId(int i) {
        ((com.android.server.wm.ActivityTaskManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.ActivityTaskManagerInternal.class)).setVr2dDisplayId(i);
        this.mWindowManagerInternal.setVr2dDisplayId(i);
    }

    private void stopVirtualDisplay() {
        if (this.mStopVDRunnable == null) {
            this.mStopVDRunnable = new java.lang.Runnable() { // from class: com.android.server.vr.Vr2dDisplay.3
                @Override // java.lang.Runnable
                public void run() {
                    if (com.android.server.vr.Vr2dDisplay.this.shouldRunVirtualDisplay()) {
                        android.util.Log.i(com.android.server.vr.Vr2dDisplay.TAG, "Virtual Display destruction stopped: VrMode is back on.");
                        return;
                    }
                    android.util.Log.i(com.android.server.vr.Vr2dDisplay.TAG, "Stopping Virtual Display");
                    synchronized (com.android.server.vr.Vr2dDisplay.this.mVdLock) {
                        try {
                            com.android.server.vr.Vr2dDisplay.this.updateDisplayId(-1);
                            com.android.server.vr.Vr2dDisplay.this.setSurfaceLocked(null);
                            if (com.android.server.vr.Vr2dDisplay.this.mVirtualDisplay != null) {
                                com.android.server.vr.Vr2dDisplay.this.mVirtualDisplay.release();
                                com.android.server.vr.Vr2dDisplay.this.mVirtualDisplay = null;
                            }
                            com.android.server.vr.Vr2dDisplay.this.stopImageReader();
                        } catch (java.lang.Throwable th) {
                            throw th;
                        }
                    }
                }
            };
        }
        this.mHandler.removeCallbacks(this.mStopVDRunnable);
        this.mHandler.postDelayed(this.mStopVDRunnable, 2000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSurfaceLocked(android.view.Surface surface) {
        if (this.mSurface != surface) {
            if (surface == null || surface.isValid()) {
                android.util.Log.i(TAG, "Setting the new surface from " + this.mSurface + " to " + surface);
                if (this.mVirtualDisplay != null) {
                    this.mVirtualDisplay.setSurface(surface);
                }
                if (this.mSurface != null) {
                    this.mSurface.release();
                }
                this.mSurface = surface;
            }
        }
    }

    private void startImageReader() {
        if (this.mImageReader == null) {
            this.mImageReader = android.media.ImageReader.newInstance(this.mVirtualDisplayWidth, this.mVirtualDisplayHeight, 1, 2);
            android.util.Log.i(TAG, "VD startImageReader: res = " + this.mVirtualDisplayWidth + "X" + this.mVirtualDisplayHeight + ", dpi = " + this.mVirtualDisplayDpi);
        }
        synchronized (this.mVdLock) {
            setSurfaceLocked(this.mImageReader.getSurface());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopImageReader() {
        if (this.mImageReader != null) {
            this.mImageReader.close();
            this.mImageReader = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean shouldRunVirtualDisplay() {
        return this.mIsVirtualDisplayAllowed && (this.mBootsToVr || this.mIsPersistentVrModeEnabled || this.mIsVrModeOverrideEnabled);
    }
}
