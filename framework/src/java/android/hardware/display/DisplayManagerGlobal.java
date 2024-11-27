package android.hardware.display;

/* loaded from: classes2.dex */
public final class DisplayManagerGlobal {
    public static final java.lang.String CACHE_KEY_DISPLAY_INFO_PROPERTY = "cache_key.display_info";
    private static final boolean DEBUG;
    public static final int EVENT_DISPLAY_ADDED = 1;
    public static final int EVENT_DISPLAY_BRIGHTNESS_CHANGED = 4;
    public static final int EVENT_DISPLAY_CHANGED = 2;
    public static final int EVENT_DISPLAY_CONNECTED = 6;
    public static final int EVENT_DISPLAY_DISCONNECTED = 7;
    public static final int EVENT_DISPLAY_HDR_SDR_RATIO_CHANGED = 5;
    public static final int EVENT_DISPLAY_REMOVED = 3;
    private static final java.lang.String TAG = "DisplayManager";
    private static final boolean USE_CACHE = false;
    private static android.hardware.display.DisplayManagerGlobal sInstance;
    private android.hardware.display.DisplayManagerGlobal.DisplayManagerCallback mCallback;
    private int[] mDisplayIdCache;
    private final android.hardware.display.IDisplayManager mDm;
    private float mNativeCallbackReportedRefreshRate;
    private final android.hardware.OverlayProperties mOverlayProperties;
    private final android.graphics.ColorSpace mWideColorSpace;
    private int mWifiDisplayScanNestCount;
    private static final java.lang.String EXTRA_LOGGING_PACKAGE_NAME = android.sysprop.DisplayProperties.debug_vri_package().orElse(null);
    private static java.lang.String sCurrentPackageName = android.app.ActivityThread.currentPackageName();
    private static boolean sExtraDisplayListenerLogging = initExtraLogging();
    private boolean mDispatchNativeCallbacks = false;
    private final java.lang.Object mLock = new java.lang.Object();
    private long mRegisteredEventsMask = 0;
    private final java.util.concurrent.CopyOnWriteArrayList<android.hardware.display.DisplayManagerGlobal.DisplayListenerDelegate> mDisplayListeners = new java.util.concurrent.CopyOnWriteArrayList<>();
    private final android.util.SparseArray<android.view.DisplayInfo> mDisplayInfoCache = new android.util.SparseArray<>();
    private android.app.PropertyInvalidatedCache<java.lang.Integer, android.view.DisplayInfo> mDisplayCache = new android.app.PropertyInvalidatedCache<java.lang.Integer, android.view.DisplayInfo>(8, CACHE_KEY_DISPLAY_INFO_PROPERTY) { // from class: android.hardware.display.DisplayManagerGlobal.1
        @Override // android.app.PropertyInvalidatedCache
        public android.view.DisplayInfo recompute(java.lang.Integer num) {
            try {
                return android.hardware.display.DisplayManagerGlobal.this.mDm.getDisplayInfo(num.intValue());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    };

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DisplayEvent {
    }

    private static native void nSignalNativeCallbacks(float f);

    static {
        DEBUG = android.hardware.display.DisplayManager.DEBUG || sExtraDisplayListenerLogging;
    }

    public DisplayManagerGlobal(android.hardware.display.IDisplayManager iDisplayManager) {
        this.mDm = iDisplayManager;
        initExtraLogging();
        try {
            this.mWideColorSpace = android.graphics.ColorSpace.get(android.graphics.ColorSpace.Named.values()[this.mDm.getPreferredWideGamutColorSpaceId()]);
            this.mOverlayProperties = this.mDm.getOverlaySupport();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static android.hardware.display.DisplayManagerGlobal getInstance() {
        android.hardware.display.DisplayManagerGlobal displayManagerGlobal;
        android.os.IBinder service;
        synchronized (android.hardware.display.DisplayManagerGlobal.class) {
            if (sInstance == null && (service = android.os.ServiceManager.getService(android.content.Context.DISPLAY_SERVICE)) != null) {
                sInstance = new android.hardware.display.DisplayManagerGlobal(android.hardware.display.IDisplayManager.Stub.asInterface(service));
            }
            displayManagerGlobal = sInstance;
        }
        return displayManagerGlobal;
    }

    public android.view.DisplayInfo getDisplayInfo(int i) {
        android.view.DisplayInfo displayInfoLocked;
        synchronized (this.mLock) {
            displayInfoLocked = getDisplayInfoLocked(i);
        }
        return displayInfoLocked;
    }

    private android.view.DisplayInfo getDisplayInfoLocked(int i) {
        android.view.DisplayInfo displayInfo;
        if (this.mDisplayCache != null) {
            displayInfo = this.mDisplayCache.query(java.lang.Integer.valueOf(i));
        } else {
            try {
                displayInfo = this.mDm.getDisplayInfo(i);
            } catch (android.os.RemoteException e) {
                e.rethrowFromSystemServer();
                displayInfo = null;
            }
        }
        if (displayInfo == null) {
            return null;
        }
        registerCallbackIfNeededLocked();
        if (DEBUG) {
            android.util.Log.d(TAG, "getDisplayInfo: displayId=" + i + ", info=" + displayInfo);
        }
        return displayInfo;
    }

    public int[] getDisplayIds() {
        return getDisplayIds(false);
    }

    public int[] getDisplayIds(boolean z) {
        int[] displayIds;
        try {
            synchronized (this.mLock) {
                displayIds = this.mDm.getDisplayIds(z);
                registerCallbackIfNeededLocked();
            }
            return displayIds;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isUidPresentOnDisplay(int i, int i2) {
        try {
            return this.mDm.isUidPresentOnDisplay(i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.view.Display getCompatibleDisplay(int i, android.view.DisplayAdjustments displayAdjustments) {
        android.view.DisplayInfo displayInfo = getDisplayInfo(i);
        if (displayInfo == null) {
            return null;
        }
        return new android.view.Display(this, i, displayInfo, displayAdjustments);
    }

    public android.view.Display getCompatibleDisplay(int i, android.content.res.Resources resources) {
        android.view.DisplayInfo displayInfo = getDisplayInfo(i);
        if (displayInfo == null) {
            return null;
        }
        return new android.view.Display(this, i, displayInfo, resources);
    }

    public android.view.Display getRealDisplay(int i) {
        return getCompatibleDisplay(i, android.view.DisplayAdjustments.DEFAULT_DISPLAY_ADJUSTMENTS);
    }

    public void registerDisplayListener(android.hardware.display.DisplayManager.DisplayListener displayListener, android.os.Handler handler, long j, java.lang.String str) {
        registerDisplayListener(displayListener, new android.os.HandlerExecutor(new android.os.Handler(getLooperForHandler(handler))), j, str);
    }

    public void registerDisplayListener(android.hardware.display.DisplayManager.DisplayListener displayListener, java.util.concurrent.Executor executor, long j, java.lang.String str) {
        if (displayListener == null) {
            throw new java.lang.IllegalArgumentException("listener must not be null");
        }
        if (j == 0) {
            throw new java.lang.IllegalArgumentException("The set of events to listen to must not be empty.");
        }
        if (extraLogging()) {
            android.util.Slog.i(TAG, "Registering Display Listener: " + java.lang.Long.toBinaryString(j) + ", packageName: " + str);
        }
        synchronized (this.mLock) {
            int findDisplayListenerLocked = findDisplayListenerLocked(displayListener);
            if (findDisplayListenerLocked < 0) {
                this.mDisplayListeners.add(new android.hardware.display.DisplayManagerGlobal.DisplayListenerDelegate(displayListener, executor, j, str));
                registerCallbackIfNeededLocked();
            } else {
                this.mDisplayListeners.get(findDisplayListenerLocked).setEventsMask(j);
            }
            updateCallbackIfNeededLocked();
            maybeLogAllDisplayListeners();
        }
    }

    public void unregisterDisplayListener(android.hardware.display.DisplayManager.DisplayListener displayListener) {
        if (displayListener == null) {
            throw new java.lang.IllegalArgumentException("listener must not be null");
        }
        if (extraLogging()) {
            android.util.Slog.i(TAG, "Unregistering Display Listener: " + displayListener);
        }
        synchronized (this.mLock) {
            int findDisplayListenerLocked = findDisplayListenerLocked(displayListener);
            if (findDisplayListenerLocked >= 0) {
                this.mDisplayListeners.get(findDisplayListenerLocked).clearEvents();
                this.mDisplayListeners.remove(findDisplayListenerLocked);
                updateCallbackIfNeededLocked();
            }
        }
        maybeLogAllDisplayListeners();
    }

    private void maybeLogAllDisplayListeners() {
        if (!extraLogging()) {
            return;
        }
        android.util.Slog.i(TAG, "Currently Registered Display Listeners:");
        for (int i = 0; i < this.mDisplayListeners.size(); i++) {
            android.util.Slog.i(TAG, i + ": " + this.mDisplayListeners.get(i));
        }
    }

    public void handleDisplayChangeFromWindowManager(int i) {
        handleDisplayEvent(i, 2, true);
    }

    private static android.os.Looper getLooperForHandler(android.os.Handler handler) {
        android.os.Looper looper = handler != null ? handler.getLooper() : android.os.Looper.myLooper();
        if (looper == null) {
            looper = android.os.Looper.getMainLooper();
        }
        if (looper == null) {
            throw new java.lang.RuntimeException("Could not get Looper for the UI thread.");
        }
        return looper;
    }

    private int findDisplayListenerLocked(android.hardware.display.DisplayManager.DisplayListener displayListener) {
        int size = this.mDisplayListeners.size();
        for (int i = 0; i < size; i++) {
            if (this.mDisplayListeners.get(i).mListener == displayListener) {
                return i;
            }
        }
        return -1;
    }

    private int calculateEventsMaskLocked() {
        int size = this.mDisplayListeners.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            i = (int) (i | this.mDisplayListeners.get(i2).mEventsMask);
        }
        if (this.mDispatchNativeCallbacks) {
            return (int) (i | 7);
        }
        return i;
    }

    private void registerCallbackIfNeededLocked() {
        if (this.mCallback == null) {
            this.mCallback = new android.hardware.display.DisplayManagerGlobal.DisplayManagerCallback();
            updateCallbackIfNeededLocked();
        }
    }

    private void updateCallbackIfNeededLocked() {
        int calculateEventsMaskLocked = calculateEventsMaskLocked();
        if (DEBUG) {
            android.util.Log.d(TAG, "Mask for listener: " + calculateEventsMaskLocked);
        }
        long j = calculateEventsMaskLocked;
        if (j != this.mRegisteredEventsMask) {
            try {
                this.mDm.registerCallbackWithEventMask(this.mCallback, j);
                this.mRegisteredEventsMask = j;
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDisplayEvent(int i, int i2, boolean z) {
        android.view.DisplayInfo displayInfoLocked;
        android.view.DisplayInfo displayInfoLocked2;
        synchronized (this.mLock) {
            displayInfoLocked = getDisplayInfoLocked(i);
            if (i2 == 2 && this.mDispatchNativeCallbacks && i == 0 && (displayInfoLocked2 = getDisplayInfoLocked(i)) != null && this.mNativeCallbackReportedRefreshRate != displayInfoLocked2.getRefreshRate()) {
                this.mNativeCallbackReportedRefreshRate = displayInfoLocked2.getRefreshRate();
                nSignalNativeCallbacks(this.mNativeCallbackReportedRefreshRate);
            }
        }
        java.util.Iterator<android.hardware.display.DisplayManagerGlobal.DisplayListenerDelegate> it = this.mDisplayListeners.iterator();
        while (it.hasNext()) {
            it.next().sendDisplayEvent(i, i2, displayInfoLocked, z);
        }
    }

    public void enableConnectedDisplay(int i) {
        try {
            this.mDm.enableConnectedDisplay(i);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error trying to enable external display", e);
        }
    }

    public void disableConnectedDisplay(int i) {
        try {
            this.mDm.disableConnectedDisplay(i);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error trying to enable external display", e);
        }
    }

    public void startWifiDisplayScan() {
        synchronized (this.mLock) {
            int i = this.mWifiDisplayScanNestCount;
            this.mWifiDisplayScanNestCount = i + 1;
            if (i == 0) {
                registerCallbackIfNeededLocked();
                try {
                    this.mDm.startWifiDisplayScan();
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    public void stopWifiDisplayScan() {
        synchronized (this.mLock) {
            int i = this.mWifiDisplayScanNestCount - 1;
            this.mWifiDisplayScanNestCount = i;
            if (i == 0) {
                try {
                    this.mDm.stopWifiDisplayScan();
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            } else if (this.mWifiDisplayScanNestCount < 0) {
                android.util.Log.wtf(TAG, "Wifi display scan nest count became negative: " + this.mWifiDisplayScanNestCount);
                this.mWifiDisplayScanNestCount = 0;
            }
        }
    }

    public void connectWifiDisplay(java.lang.String str) {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("deviceAddress must not be null");
        }
        try {
            this.mDm.connectWifiDisplay(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void pauseWifiDisplay() {
        try {
            this.mDm.pauseWifiDisplay();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void resumeWifiDisplay() {
        try {
            this.mDm.resumeWifiDisplay();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void disconnectWifiDisplay() {
        try {
            this.mDm.disconnectWifiDisplay();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void renameWifiDisplay(java.lang.String str, java.lang.String str2) {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("deviceAddress must not be null");
        }
        try {
            this.mDm.renameWifiDisplay(str, str2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void forgetWifiDisplay(java.lang.String str) {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("deviceAddress must not be null");
        }
        try {
            this.mDm.forgetWifiDisplay(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.hardware.display.WifiDisplayStatus getWifiDisplayStatus() {
        try {
            return this.mDm.getWifiDisplayStatus();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setUserDisabledHdrTypes(int[] iArr) {
        try {
            this.mDm.setUserDisabledHdrTypes(iArr);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setAreUserDisabledHdrTypesAllowed(boolean z) {
        try {
            this.mDm.setAreUserDisabledHdrTypesAllowed(z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean areUserDisabledHdrTypesAllowed() {
        try {
            return this.mDm.areUserDisabledHdrTypesAllowed();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int[] getUserDisabledHdrTypes() {
        try {
            return this.mDm.getUserDisabledHdrTypes();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void overrideHdrTypes(int i, int[] iArr) {
        try {
            this.mDm.overrideHdrTypes(i, iArr);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void requestColorMode(int i, int i2) {
        try {
            this.mDm.requestColorMode(i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.hardware.display.VirtualDisplay createVirtualDisplay(android.content.Context context, android.media.projection.MediaProjection mediaProjection, android.hardware.display.VirtualDisplayConfig virtualDisplayConfig, android.hardware.display.VirtualDisplay.Callback callback, java.util.concurrent.Executor executor) {
        android.hardware.display.DisplayManagerGlobal.VirtualDisplayCallback virtualDisplayCallback = new android.hardware.display.DisplayManagerGlobal.VirtualDisplayCallback(callback, executor);
        try {
            return createVirtualDisplayWrapper(virtualDisplayConfig, virtualDisplayCallback, this.mDm.createVirtualDisplay(virtualDisplayConfig, virtualDisplayCallback, mediaProjection != null ? mediaProjection.getProjection() : null, context.getPackageName()));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.hardware.display.VirtualDisplay createVirtualDisplayWrapper(android.hardware.display.VirtualDisplayConfig virtualDisplayConfig, android.hardware.display.IVirtualDisplayCallback iVirtualDisplayCallback, int i) {
        if (i < 0) {
            android.util.Log.e(TAG, "Could not create virtual display: " + virtualDisplayConfig.getName());
            return null;
        }
        android.view.Display realDisplay = getRealDisplay(i);
        if (realDisplay == null) {
            android.util.Log.wtf(TAG, "Could not obtain display info for newly created virtual display: " + virtualDisplayConfig.getName());
            try {
                this.mDm.releaseVirtualDisplay(iVirtualDisplayCallback);
                return null;
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return new android.hardware.display.VirtualDisplay(this, realDisplay, iVirtualDisplayCallback, virtualDisplayConfig.getSurface());
    }

    public void setVirtualDisplaySurface(android.hardware.display.IVirtualDisplayCallback iVirtualDisplayCallback, android.view.Surface surface) {
        try {
            this.mDm.setVirtualDisplaySurface(iVirtualDisplayCallback, surface);
            setVirtualDisplayState(iVirtualDisplayCallback, surface != null);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void resizeVirtualDisplay(android.hardware.display.IVirtualDisplayCallback iVirtualDisplayCallback, int i, int i2, int i3) {
        try {
            this.mDm.resizeVirtualDisplay(iVirtualDisplayCallback, i, i2, i3);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void releaseVirtualDisplay(android.hardware.display.IVirtualDisplayCallback iVirtualDisplayCallback) {
        try {
            this.mDm.releaseVirtualDisplay(iVirtualDisplayCallback);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void setVirtualDisplayState(android.hardware.display.IVirtualDisplayCallback iVirtualDisplayCallback, boolean z) {
        try {
            this.mDm.setVirtualDisplayState(iVirtualDisplayCallback, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.graphics.Point getStableDisplaySize() {
        try {
            return this.mDm.getStableDisplaySize();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.hardware.display.BrightnessChangeEvent> getBrightnessEvents(java.lang.String str) {
        try {
            android.content.pm.ParceledListSlice brightnessEvents = this.mDm.getBrightnessEvents(str);
            if (brightnessEvents == null) {
                return java.util.Collections.emptyList();
            }
            return brightnessEvents.getList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.hardware.display.BrightnessInfo getBrightnessInfo(int i) {
        try {
            return this.mDm.getBrightnessInfo(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.graphics.ColorSpace getPreferredWideGamutColorSpace() {
        return this.mWideColorSpace;
    }

    public android.hardware.OverlayProperties getOverlaySupport() {
        return this.mOverlayProperties;
    }

    public void setBrightnessConfigurationForUser(android.hardware.display.BrightnessConfiguration brightnessConfiguration, int i, java.lang.String str) {
        try {
            this.mDm.setBrightnessConfigurationForUser(brightnessConfiguration, i, str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setBrightnessConfigurationForDisplay(android.hardware.display.BrightnessConfiguration brightnessConfiguration, java.lang.String str, int i, java.lang.String str2) {
        try {
            this.mDm.setBrightnessConfigurationForDisplay(brightnessConfiguration, str, i, str2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.hardware.display.BrightnessConfiguration getBrightnessConfigurationForDisplay(java.lang.String str, int i) {
        try {
            return this.mDm.getBrightnessConfigurationForDisplay(str, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.hardware.display.BrightnessConfiguration getBrightnessConfigurationForUser(int i) {
        try {
            return this.mDm.getBrightnessConfigurationForUser(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.hardware.display.BrightnessConfiguration getDefaultBrightnessConfiguration() {
        try {
            return this.mDm.getDefaultBrightnessConfiguration();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isMinimalPostProcessingRequested(int i) {
        try {
            return this.mDm.isMinimalPostProcessingRequested(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setTemporaryBrightness(int i, float f) {
        try {
            this.mDm.setTemporaryBrightness(i, f);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setBrightness(int i, float f) {
        try {
            this.mDm.setBrightness(i, f);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.hardware.graphics.common.DisplayDecorationSupport getDisplayDecorationSupport(int i) {
        try {
            return this.mDm.getDisplayDecorationSupport(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public float getBrightness(int i) {
        try {
            return this.mDm.getBrightness(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setTemporaryAutoBrightnessAdjustment(float f) {
        try {
            this.mDm.setTemporaryAutoBrightnessAdjustment(f);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.util.Pair<float[], float[]> getMinimumBrightnessCurve() {
        try {
            android.hardware.display.Curve minimumBrightnessCurve = this.mDm.getMinimumBrightnessCurve();
            return android.util.Pair.create(minimumBrightnessCurve.getX(), minimumBrightnessCurve.getY());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.hardware.display.AmbientBrightnessDayStats> getAmbientBrightnessStats() {
        try {
            android.content.pm.ParceledListSlice ambientBrightnessStats = this.mDm.getAmbientBrightnessStats();
            if (ambientBrightnessStats == null) {
                return java.util.Collections.emptyList();
            }
            return ambientBrightnessStats.getList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setUserPreferredDisplayMode(int i, android.view.Display.Mode mode) {
        try {
            this.mDm.setUserPreferredDisplayMode(i, mode);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.view.Display.Mode getUserPreferredDisplayMode(int i) {
        try {
            return this.mDm.getUserPreferredDisplayMode(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.view.Display.Mode getSystemPreferredDisplayMode(int i) {
        try {
            return this.mDm.getSystemPreferredDisplayMode(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setHdrConversionMode(android.hardware.display.HdrConversionMode hdrConversionMode) {
        try {
            this.mDm.setHdrConversionMode(hdrConversionMode);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.hardware.display.HdrConversionMode getHdrConversionModeSetting() {
        try {
            return this.mDm.getHdrConversionModeSetting();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.hardware.display.HdrConversionMode getHdrConversionMode() {
        try {
            return this.mDm.getHdrConversionMode();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int[] getSupportedHdrOutputTypes() {
        try {
            return this.mDm.getSupportedHdrOutputTypes();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setShouldAlwaysRespectAppRequestedMode(boolean z) {
        try {
            this.mDm.setShouldAlwaysRespectAppRequestedMode(z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean shouldAlwaysRespectAppRequestedMode() {
        try {
            return this.mDm.shouldAlwaysRespectAppRequestedMode();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setRefreshRateSwitchingType(int i) {
        try {
            this.mDm.setRefreshRateSwitchingType(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getRefreshRateSwitchingType() {
        try {
            return this.mDm.getRefreshRateSwitchingType();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private final class DisplayManagerCallback extends android.hardware.display.IDisplayManagerCallback.Stub {
        private DisplayManagerCallback() {
        }

        @Override // android.hardware.display.IDisplayManagerCallback
        public void onDisplayEvent(int i, int i2) {
            if (android.hardware.display.DisplayManagerGlobal.DEBUG) {
                android.util.Log.d(android.hardware.display.DisplayManagerGlobal.TAG, "onDisplayEvent: displayId=" + i + ", event=" + android.hardware.display.DisplayManagerGlobal.eventToString(i2));
            }
            android.hardware.display.DisplayManagerGlobal.this.handleDisplayEvent(i, i2, false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class DisplayListenerDelegate {
        public volatile long mEventsMask;
        private final java.util.concurrent.Executor mExecutor;
        public final android.hardware.display.DisplayManager.DisplayListener mListener;
        private final java.lang.String mPackageName;
        private final android.view.DisplayInfo mDisplayInfo = new android.view.DisplayInfo();
        private java.util.concurrent.atomic.AtomicLong mGenerationId = new java.util.concurrent.atomic.AtomicLong(1);

        DisplayListenerDelegate(android.hardware.display.DisplayManager.DisplayListener displayListener, java.util.concurrent.Executor executor, long j, java.lang.String str) {
            this.mExecutor = executor;
            this.mListener = displayListener;
            this.mEventsMask = j;
            this.mPackageName = str;
        }

        void sendDisplayEvent(final int i, final int i2, final android.view.DisplayInfo displayInfo, final boolean z) {
            if (android.hardware.display.DisplayManagerGlobal.extraLogging()) {
                android.util.Slog.i(android.hardware.display.DisplayManagerGlobal.TAG, "Sending Display Event: " + android.hardware.display.DisplayManagerGlobal.eventToString(i2));
            }
            final long j = this.mGenerationId.get();
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.display.DisplayManagerGlobal$DisplayListenerDelegate$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.hardware.display.DisplayManagerGlobal.DisplayListenerDelegate.this.lambda$sendDisplayEvent$0(j, i, i2, displayInfo, z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$sendDisplayEvent$0(long j, int i, int i2, android.view.DisplayInfo displayInfo, boolean z) {
            if (j == this.mGenerationId.get()) {
                handleDisplayEventInner(i, i2, displayInfo, z);
            }
        }

        void clearEvents() {
            this.mGenerationId.incrementAndGet();
        }

        void setEventsMask(long j) {
            this.mEventsMask = j;
        }

        private void handleDisplayEventInner(int i, int i2, android.view.DisplayInfo displayInfo, boolean z) {
            if (android.hardware.display.DisplayManagerGlobal.extraLogging()) {
                android.util.Slog.i(android.hardware.display.DisplayManagerGlobal.TAG, "DLD(" + android.hardware.display.DisplayManagerGlobal.eventToString(i2) + ", display=" + i + ", mEventsMask=" + java.lang.Long.toBinaryString(this.mEventsMask) + ", mPackageName=" + this.mPackageName + ", displayInfo=" + displayInfo + ", listener=" + this.mListener.getClass() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            }
            if (android.hardware.display.DisplayManagerGlobal.DEBUG) {
                android.os.Trace.beginSection((java.lang.String) android.text.TextUtils.trimToSize("DLD(" + android.hardware.display.DisplayManagerGlobal.eventToString(i2) + ", display=" + i + ", listener=" + this.mListener.getClass() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END, 127));
            }
            switch (i2) {
                case 1:
                    if ((this.mEventsMask & 1) != 0) {
                        this.mListener.onDisplayAdded(i);
                        break;
                    }
                    break;
                case 2:
                    if ((this.mEventsMask & 4) != 0 && displayInfo != null && (z || !displayInfo.equals(this.mDisplayInfo))) {
                        if (android.hardware.display.DisplayManagerGlobal.extraLogging()) {
                            android.util.Slog.i(android.hardware.display.DisplayManagerGlobal.TAG, "Sending onDisplayChanged: Display Changed. Info: " + displayInfo);
                        }
                        this.mDisplayInfo.copyFrom(displayInfo);
                        this.mListener.onDisplayChanged(i);
                        break;
                    }
                    break;
                case 3:
                    if ((this.mEventsMask & 2) != 0) {
                        this.mListener.onDisplayRemoved(i);
                        break;
                    }
                    break;
                case 4:
                    if ((this.mEventsMask & 8) != 0) {
                        this.mListener.onDisplayChanged(i);
                        break;
                    }
                    break;
                case 5:
                    if ((this.mEventsMask & 16) != 0) {
                        this.mListener.onDisplayChanged(i);
                        break;
                    }
                    break;
                case 6:
                    if ((this.mEventsMask & 32) != 0) {
                        this.mListener.onDisplayConnected(i);
                        break;
                    }
                    break;
                case 7:
                    if ((this.mEventsMask & 32) != 0) {
                        this.mListener.onDisplayDisconnected(i);
                        break;
                    }
                    break;
            }
            if (android.hardware.display.DisplayManagerGlobal.DEBUG) {
                android.os.Trace.endSection();
            }
        }

        public java.lang.String toString() {
            return "mask: {" + this.mEventsMask + "}, for " + this.mListener.getClass();
        }
    }

    public static final class VirtualDisplayCallback extends android.hardware.display.IVirtualDisplayCallback.Stub {
        private final android.hardware.display.VirtualDisplay.Callback mCallback;
        private final java.util.concurrent.Executor mExecutor;

        public VirtualDisplayCallback(android.hardware.display.VirtualDisplay.Callback callback, java.util.concurrent.Executor executor) {
            this.mCallback = callback;
            this.mExecutor = this.mCallback != null ? (java.util.concurrent.Executor) java.util.Objects.requireNonNull(executor) : null;
        }

        @Override // android.hardware.display.IVirtualDisplayCallback
        public void onPaused() {
            if (this.mCallback != null) {
                java.util.concurrent.Executor executor = this.mExecutor;
                final android.hardware.display.VirtualDisplay.Callback callback = this.mCallback;
                java.util.Objects.requireNonNull(callback);
                executor.execute(new java.lang.Runnable() { // from class: android.hardware.display.DisplayManagerGlobal$VirtualDisplayCallback$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.hardware.display.VirtualDisplay.Callback.this.onPaused();
                    }
                });
            }
        }

        @Override // android.hardware.display.IVirtualDisplayCallback
        public void onResumed() {
            if (this.mCallback != null) {
                java.util.concurrent.Executor executor = this.mExecutor;
                final android.hardware.display.VirtualDisplay.Callback callback = this.mCallback;
                java.util.Objects.requireNonNull(callback);
                executor.execute(new java.lang.Runnable() { // from class: android.hardware.display.DisplayManagerGlobal$VirtualDisplayCallback$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.hardware.display.VirtualDisplay.Callback.this.onResumed();
                    }
                });
            }
        }

        @Override // android.hardware.display.IVirtualDisplayCallback
        public void onStopped() {
            if (this.mCallback != null) {
                java.util.concurrent.Executor executor = this.mExecutor;
                final android.hardware.display.VirtualDisplay.Callback callback = this.mCallback;
                java.util.Objects.requireNonNull(callback);
                executor.execute(new java.lang.Runnable() { // from class: android.hardware.display.DisplayManagerGlobal$VirtualDisplayCallback$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.hardware.display.VirtualDisplay.Callback.this.onStopped();
                    }
                });
            }
        }
    }

    public static void invalidateLocalDisplayInfoCaches() {
        android.app.PropertyInvalidatedCache.invalidateCache(CACHE_KEY_DISPLAY_INFO_PROPERTY);
    }

    public void disableLocalDisplayInfoCaches() {
        this.mDisplayCache = null;
    }

    public void registerNativeChoreographerForRefreshRateCallbacks() {
        synchronized (this.mLock) {
            this.mDispatchNativeCallbacks = true;
            registerCallbackIfNeededLocked();
            updateCallbackIfNeededLocked();
            android.view.DisplayInfo displayInfoLocked = getDisplayInfoLocked(0);
            if (displayInfoLocked != null) {
                this.mNativeCallbackReportedRefreshRate = displayInfoLocked.getRefreshRate();
                nSignalNativeCallbacks(this.mNativeCallbackReportedRefreshRate);
            }
        }
    }

    public void unregisterNativeChoreographerForRefreshRateCallbacks() {
        synchronized (this.mLock) {
            this.mDispatchNativeCallbacks = false;
            updateCallbackIfNeededLocked();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String eventToString(int i) {
        switch (i) {
            case 1:
                return "ADDED";
            case 2:
                return "CHANGED";
            case 3:
                return "REMOVED";
            case 4:
                return "BRIGHTNESS_CHANGED";
            case 5:
                return "HDR_SDR_RATIO_CHANGED";
            case 6:
                return "EVENT_DISPLAY_CONNECTED";
            case 7:
                return "EVENT_DISPLAY_DISCONNECTED";
            default:
                return "UNKNOWN";
        }
    }

    private static boolean initExtraLogging() {
        if (sCurrentPackageName == null) {
            sCurrentPackageName = android.app.ActivityThread.currentPackageName();
            sExtraDisplayListenerLogging = !android.text.TextUtils.isEmpty(EXTRA_LOGGING_PACKAGE_NAME) && EXTRA_LOGGING_PACKAGE_NAME.equals(sCurrentPackageName);
        }
        return sExtraDisplayListenerLogging;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean extraLogging() {
        return sExtraDisplayListenerLogging;
    }
}
