package android.hardware.display;

/* loaded from: classes2.dex */
public final class DisplayManager {
    public static final java.lang.String ACTION_WIFI_DISPLAY_STATUS_CHANGED = "android.hardware.display.action.WIFI_DISPLAY_STATUS_CHANGED";
    static final boolean DEBUG;
    public static final java.lang.String DISPLAY_CATEGORY_ALL_INCLUDING_DISABLED = "android.hardware.display.category.ALL_INCLUDING_DISABLED";
    public static final java.lang.String DISPLAY_CATEGORY_PRESENTATION = "android.hardware.display.category.PRESENTATION";
    public static final java.lang.String DISPLAY_CATEGORY_REAR = "android.hardware.display.category.REAR";
    private static final boolean ENABLE_VIRTUAL_DISPLAY_REFRESH_RATE = true;
    public static final long EVENT_FLAG_DISPLAY_ADDED = 1;
    public static final long EVENT_FLAG_DISPLAY_BRIGHTNESS = 8;
    public static final long EVENT_FLAG_DISPLAY_CHANGED = 4;
    public static final long EVENT_FLAG_DISPLAY_CONNECTION_CHANGED = 32;
    public static final long EVENT_FLAG_DISPLAY_REMOVED = 2;
    public static final long EVENT_FLAG_HDR_SDR_RATIO_CHANGED = 16;
    public static final java.lang.String EXTRA_WIFI_DISPLAY_STATUS = "android.hardware.display.extra.WIFI_DISPLAY_STATUS";
    public static final java.lang.String HDR_OUTPUT_CONTROL_FLAG = "enable_hdr_output_control";
    public static final int MATCH_CONTENT_FRAMERATE_ALWAYS = 2;
    public static final int MATCH_CONTENT_FRAMERATE_NEVER = 0;
    public static final int MATCH_CONTENT_FRAMERATE_SEAMLESSS_ONLY = 1;
    public static final int MATCH_CONTENT_FRAMERATE_UNKNOWN = -1;
    public static final int SWITCHING_TYPE_ACROSS_AND_WITHIN_GROUPS = 2;
    public static final int SWITCHING_TYPE_NONE = 0;
    public static final int SWITCHING_TYPE_RENDER_FRAME_RATE_ONLY = 3;
    public static final int SWITCHING_TYPE_WITHIN_GROUPS = 1;
    private static final java.lang.String TAG = "DisplayManager";
    public static final int VIRTUAL_DISPLAY_FLAG_ALWAYS_UNLOCKED = 4096;
    public static final int VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR = 16;
    public static final int VIRTUAL_DISPLAY_FLAG_CAN_SHOW_WITH_INSECURE_KEYGUARD = 32;
    public static final int VIRTUAL_DISPLAY_FLAG_DESTROY_CONTENT_ON_REMOVAL = 256;
    public static final int VIRTUAL_DISPLAY_FLAG_DEVICE_DISPLAY_GROUP = 32768;
    public static final int VIRTUAL_DISPLAY_FLAG_OWN_CONTENT_ONLY = 8;
    public static final int VIRTUAL_DISPLAY_FLAG_OWN_DISPLAY_GROUP = 2048;
    public static final int VIRTUAL_DISPLAY_FLAG_OWN_FOCUS = 16384;
    public static final int VIRTUAL_DISPLAY_FLAG_PRESENTATION = 2;
    public static final int VIRTUAL_DISPLAY_FLAG_PUBLIC = 1;

    @android.annotation.SystemApi
    public static final int VIRTUAL_DISPLAY_FLAG_ROTATES_WITH_CONTENT = 128;
    public static final int VIRTUAL_DISPLAY_FLAG_SECURE = 4;
    public static final int VIRTUAL_DISPLAY_FLAG_SHOULD_SHOW_SYSTEM_DECORATIONS = 512;

    @android.annotation.SystemApi
    public static final int VIRTUAL_DISPLAY_FLAG_STEAL_TOP_FOCUS_DISABLED = 65536;
    public static final int VIRTUAL_DISPLAY_FLAG_SUPPORTS_TOUCH = 64;
    public static final int VIRTUAL_DISPLAY_FLAG_TOUCH_FEEDBACK_DISABLED = 8192;

    @android.annotation.SystemApi
    public static final int VIRTUAL_DISPLAY_FLAG_TRUSTED = 1024;
    private final android.content.Context mContext;
    private final java.lang.Object mLock = new java.lang.Object();
    private final android.hardware.display.DisplayManager.WeakDisplayCache mDisplayCache = new android.hardware.display.DisplayManager.WeakDisplayCache();
    private final android.hardware.display.DisplayManagerGlobal mGlobal = android.hardware.display.DisplayManagerGlobal.getInstance();

    public interface DeviceConfig {
        public static final java.lang.String KEY_BRIGHTNESS_THROTTLING_DATA = "brightness_throttling_data";
        public static final java.lang.String KEY_DISABLE_SCREEN_WAKE_LOCKS_WHILE_CACHED = "disable_screen_wake_locks_while_cached";
        public static final java.lang.String KEY_FIXED_REFRESH_RATE_HIGH_AMBIENT_BRIGHTNESS_THRESHOLDS = "fixed_refresh_rate_high_ambient_brightness_thresholds";
        public static final java.lang.String KEY_FIXED_REFRESH_RATE_HIGH_DISPLAY_BRIGHTNESS_THRESHOLDS = "fixed_refresh_rate_high_display_brightness_thresholds";
        public static final java.lang.String KEY_FIXED_REFRESH_RATE_LOW_AMBIENT_BRIGHTNESS_THRESHOLDS = "peak_refresh_rate_ambient_thresholds";
        public static final java.lang.String KEY_FIXED_REFRESH_RATE_LOW_DISPLAY_BRIGHTNESS_THRESHOLDS = "peak_refresh_rate_brightness_thresholds";
        public static final java.lang.String KEY_HIGH_REFRESH_RATE_BLACKLIST = "high_refresh_rate_blacklist";
        public static final java.lang.String KEY_PEAK_REFRESH_RATE_DEFAULT = "peak_refresh_rate_default";
        public static final java.lang.String KEY_POWER_THROTTLING_DATA = "power_throttling_data";
        public static final java.lang.String KEY_REFRESH_RATE_IN_HBM_HDR = "refresh_rate_in_hbm_hdr";
        public static final java.lang.String KEY_REFRESH_RATE_IN_HBM_SUNLIGHT = "refresh_rate_in_hbm_sunlight";
        public static final java.lang.String KEY_REFRESH_RATE_IN_HIGH_ZONE = "refresh_rate_in_high_zone";
        public static final java.lang.String KEY_REFRESH_RATE_IN_LOW_ZONE = "refresh_rate_in_zone";
        public static final java.lang.String KEY_USE_NORMAL_BRIGHTNESS_MODE_CONTROLLER = "use_normal_brightness_mode_controller";
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface EventsMask {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface MatchContentFrameRateType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SwitchingType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface VirtualDisplayFlag {
    }

    static {
        DEBUG = android.util.Log.isLoggable(TAG, 3) || android.util.Log.isLoggable("DisplayManager_All", 3);
    }

    public DisplayManager(android.content.Context context) {
        this.mContext = context;
    }

    public android.view.Display getDisplay(int i) {
        return getOrCreateDisplay(i, false);
    }

    public android.view.Display[] getDisplays() {
        return getDisplays(null);
    }

    public android.view.Display[] getDisplays(java.lang.String str) {
        int[] displayIds = this.mGlobal.getDisplayIds(str != null && str.equals(DISPLAY_CATEGORY_ALL_INCLUDING_DISABLED));
        if (DISPLAY_CATEGORY_PRESENTATION.equals(str)) {
            return getDisplays(displayIds, new java.util.function.Predicate() { // from class: android.hardware.display.DisplayManager$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean isPresentationDisplay;
                    isPresentationDisplay = android.hardware.display.DisplayManager.isPresentationDisplay((android.view.Display) obj);
                    return isPresentationDisplay;
                }
            });
        }
        if (DISPLAY_CATEGORY_REAR.equals(str)) {
            return getDisplays(displayIds, new java.util.function.Predicate() { // from class: android.hardware.display.DisplayManager$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean isRearDisplay;
                    isRearDisplay = android.hardware.display.DisplayManager.isRearDisplay((android.view.Display) obj);
                    return isRearDisplay;
                }
            });
        }
        if (str == null || DISPLAY_CATEGORY_ALL_INCLUDING_DISABLED.equals(str)) {
            return getDisplays(displayIds, new java.util.function.Predicate() { // from class: android.hardware.display.DisplayManager$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    return java.util.Objects.nonNull((android.view.Display) obj);
                }
            });
        }
        return new android.view.Display[0];
    }

    private android.view.Display[] getDisplays(int[] iArr, java.util.function.Predicate<android.view.Display> predicate) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i : iArr) {
            android.view.Display orCreateDisplay = getOrCreateDisplay(i, true);
            if (predicate.test(orCreateDisplay)) {
                arrayList.add(orCreateDisplay);
            }
        }
        return (android.view.Display[]) arrayList.toArray(new android.view.Display[arrayList.size()]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isPresentationDisplay(android.view.Display display) {
        if (display == null || display.getDisplayId() == 0 || (display.getFlags() & 8) == 0) {
            return false;
        }
        switch (display.getType()) {
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isRearDisplay(android.view.Display display) {
        return (display == null || display.getDisplayId() == 0 || display.getType() != 1 || (display.getFlags() & 8192) == 0) ? false : true;
    }

    private android.view.Display getOrCreateDisplay(int i, boolean z) {
        android.view.Display display;
        synchronized (this.mLock) {
            display = this.mDisplayCache.get(i);
            android.content.res.Resources resources = null;
            if (display == null) {
                if (this.mContext.getDisplayId() == i) {
                    resources = this.mContext.getResources();
                }
                display = this.mGlobal.getCompatibleDisplay(i, resources);
                if (display != null) {
                    this.mDisplayCache.put(display);
                }
            } else if (!z && !display.isValid()) {
                display = null;
            }
        }
        return display;
    }

    public void registerDisplayListener(android.hardware.display.DisplayManager.DisplayListener displayListener, android.os.Handler handler) {
        registerDisplayListener(displayListener, handler, 7L);
    }

    public void registerDisplayListener(android.hardware.display.DisplayManager.DisplayListener displayListener, android.os.Handler handler, long j) {
        this.mGlobal.registerDisplayListener(displayListener, handler, j, android.app.ActivityThread.currentPackageName());
    }

    public void unregisterDisplayListener(android.hardware.display.DisplayManager.DisplayListener displayListener) {
        this.mGlobal.unregisterDisplayListener(displayListener);
    }

    public void startWifiDisplayScan() {
        this.mGlobal.startWifiDisplayScan();
    }

    public void stopWifiDisplayScan() {
        this.mGlobal.stopWifiDisplayScan();
    }

    public void connectWifiDisplay(java.lang.String str) {
        this.mGlobal.connectWifiDisplay(str);
    }

    public void pauseWifiDisplay() {
        this.mGlobal.pauseWifiDisplay();
    }

    public void resumeWifiDisplay() {
        this.mGlobal.resumeWifiDisplay();
    }

    public void disconnectWifiDisplay() {
        this.mGlobal.disconnectWifiDisplay();
    }

    public void renameWifiDisplay(java.lang.String str, java.lang.String str2) {
        this.mGlobal.renameWifiDisplay(str, str2);
    }

    public void forgetWifiDisplay(java.lang.String str) {
        this.mGlobal.forgetWifiDisplay(str);
    }

    public android.hardware.display.WifiDisplayStatus getWifiDisplayStatus() {
        return this.mGlobal.getWifiDisplayStatus();
    }

    public void enableConnectedDisplay(int i) {
        this.mGlobal.enableConnectedDisplay(i);
    }

    public void disableConnectedDisplay(int i) {
        this.mGlobal.disableConnectedDisplay(i);
    }

    @android.annotation.SystemApi
    public void setSaturationLevel(float f) {
        if (f < 0.0f || f > 1.0f) {
            throw new java.lang.IllegalArgumentException("Saturation level must be between 0 and 1");
        }
        ((android.hardware.display.ColorDisplayManager) this.mContext.getSystemService(android.hardware.display.ColorDisplayManager.class)).setSaturationLevel(java.lang.Math.round(f * 100.0f));
    }

    public void setUserDisabledHdrTypes(int[] iArr) {
        this.mGlobal.setUserDisabledHdrTypes(iArr);
    }

    public void setAreUserDisabledHdrTypesAllowed(boolean z) {
        this.mGlobal.setAreUserDisabledHdrTypesAllowed(z);
    }

    public boolean areUserDisabledHdrTypesAllowed() {
        return this.mGlobal.areUserDisabledHdrTypesAllowed();
    }

    public int[] getUserDisabledHdrTypes() {
        return this.mGlobal.getUserDisabledHdrTypes();
    }

    public void overrideHdrTypes(int i, int[] iArr) {
        this.mGlobal.overrideHdrTypes(i, iArr);
    }

    public android.hardware.display.VirtualDisplay createVirtualDisplay(java.lang.String str, int i, int i2, int i3, android.view.Surface surface, int i4) {
        return createVirtualDisplay(str, i, i2, i3, surface, i4, null, null);
    }

    public android.hardware.display.VirtualDisplay createVirtualDisplay(java.lang.String str, int i, int i2, int i3, android.view.Surface surface, int i4, android.hardware.display.VirtualDisplay.Callback callback, android.os.Handler handler) {
        android.hardware.display.VirtualDisplayConfig.Builder builder = new android.hardware.display.VirtualDisplayConfig.Builder(str, i, i2, i3);
        builder.setFlags(i4);
        if (surface != null) {
            builder.setSurface(surface);
        }
        return createVirtualDisplay(builder.build(), handler, callback);
    }

    public android.hardware.display.VirtualDisplay createVirtualDisplay(android.hardware.display.VirtualDisplayConfig virtualDisplayConfig) {
        return createVirtualDisplay(virtualDisplayConfig, null, null);
    }

    public android.hardware.display.VirtualDisplay createVirtualDisplay(android.hardware.display.VirtualDisplayConfig virtualDisplayConfig, android.os.Handler handler, android.hardware.display.VirtualDisplay.Callback callback) {
        return createVirtualDisplay(null, virtualDisplayConfig, callback, handler);
    }

    public android.hardware.display.VirtualDisplay createVirtualDisplay(android.media.projection.MediaProjection mediaProjection, java.lang.String str, int i, int i2, int i3, android.view.Surface surface, int i4, android.hardware.display.VirtualDisplay.Callback callback, android.os.Handler handler, java.lang.String str2) {
        android.hardware.display.VirtualDisplayConfig.Builder builder = new android.hardware.display.VirtualDisplayConfig.Builder(str, i, i2, i3);
        builder.setFlags(i4);
        if (str2 != null) {
            builder.setUniqueId(str2);
        }
        if (surface != null) {
            builder.setSurface(surface);
        }
        return createVirtualDisplay(mediaProjection, builder.build(), callback, handler);
    }

    public android.hardware.display.VirtualDisplay createVirtualDisplay(android.media.projection.MediaProjection mediaProjection, android.hardware.display.VirtualDisplayConfig virtualDisplayConfig, android.hardware.display.VirtualDisplay.Callback callback, android.os.Handler handler) {
        android.os.HandlerExecutor handlerExecutor;
        if (callback == null) {
            handlerExecutor = null;
        } else {
            handlerExecutor = new android.os.HandlerExecutor(android.os.Handler.createAsync(handler != null ? handler.getLooper() : android.os.Looper.myLooper()));
        }
        return this.mGlobal.createVirtualDisplay(this.mContext, mediaProjection, virtualDisplayConfig, callback, handlerExecutor);
    }

    @android.annotation.SystemApi
    public android.graphics.Point getStableDisplaySize() {
        return this.mGlobal.getStableDisplaySize();
    }

    @android.annotation.SystemApi
    public java.util.List<android.hardware.display.BrightnessChangeEvent> getBrightnessEvents() {
        return this.mGlobal.getBrightnessEvents(this.mContext.getOpPackageName());
    }

    @android.annotation.SystemApi
    public java.util.List<android.hardware.display.AmbientBrightnessDayStats> getAmbientBrightnessStats() {
        return this.mGlobal.getAmbientBrightnessStats();
    }

    @android.annotation.SystemApi
    public void setBrightnessConfiguration(android.hardware.display.BrightnessConfiguration brightnessConfiguration) {
        setBrightnessConfigurationForUser(brightnessConfiguration, this.mContext.getUserId(), this.mContext.getPackageName());
    }

    @android.annotation.SystemApi
    public void setBrightnessConfigurationForDisplay(android.hardware.display.BrightnessConfiguration brightnessConfiguration, java.lang.String str) {
        this.mGlobal.setBrightnessConfigurationForDisplay(brightnessConfiguration, str, this.mContext.getUserId(), this.mContext.getPackageName());
    }

    @android.annotation.SystemApi
    public android.hardware.display.BrightnessConfiguration getBrightnessConfigurationForDisplay(java.lang.String str) {
        return this.mGlobal.getBrightnessConfigurationForDisplay(str, this.mContext.getUserId());
    }

    public void setBrightnessConfigurationForUser(android.hardware.display.BrightnessConfiguration brightnessConfiguration, int i, java.lang.String str) {
        this.mGlobal.setBrightnessConfigurationForUser(brightnessConfiguration, i, str);
    }

    @android.annotation.SystemApi
    public android.hardware.display.BrightnessConfiguration getBrightnessConfiguration() {
        return getBrightnessConfigurationForUser(this.mContext.getUserId());
    }

    public android.hardware.display.BrightnessConfiguration getBrightnessConfigurationForUser(int i) {
        return this.mGlobal.getBrightnessConfigurationForUser(i);
    }

    @android.annotation.SystemApi
    public android.hardware.display.BrightnessConfiguration getDefaultBrightnessConfiguration() {
        return this.mGlobal.getDefaultBrightnessConfiguration();
    }

    public boolean isMinimalPostProcessingRequested(int i) {
        return this.mGlobal.isMinimalPostProcessingRequested(i);
    }

    public void setTemporaryBrightness(int i, float f) {
        this.mGlobal.setTemporaryBrightness(i, f);
    }

    public void setBrightness(int i, float f) {
        this.mGlobal.setBrightness(i, f);
    }

    public float getBrightness(int i) {
        return this.mGlobal.getBrightness(i);
    }

    public void setTemporaryAutoBrightnessAdjustment(float f) {
        this.mGlobal.setTemporaryAutoBrightnessAdjustment(f);
    }

    @android.annotation.SystemApi
    public android.util.Pair<float[], float[]> getMinimumBrightnessCurve() {
        return this.mGlobal.getMinimumBrightnessCurve();
    }

    public void setGlobalUserPreferredDisplayMode(android.view.Display.Mode mode) {
        this.mGlobal.setUserPreferredDisplayMode(-1, new android.view.Display.Mode(mode.getPhysicalWidth(), mode.getPhysicalHeight(), mode.getRefreshRate()));
    }

    public void clearGlobalUserPreferredDisplayMode() {
        this.mGlobal.setUserPreferredDisplayMode(-1, null);
    }

    public android.view.Display.Mode getGlobalUserPreferredDisplayMode() {
        return this.mGlobal.getUserPreferredDisplayMode(-1);
    }

    public void setHdrConversionMode(android.hardware.display.HdrConversionMode hdrConversionMode) {
        this.mGlobal.setHdrConversionMode(hdrConversionMode);
    }

    public android.hardware.display.HdrConversionMode getHdrConversionMode() {
        return this.mGlobal.getHdrConversionMode();
    }

    public android.hardware.display.HdrConversionMode getHdrConversionModeSetting() {
        return this.mGlobal.getHdrConversionModeSetting();
    }

    public int[] getSupportedHdrOutputTypes() {
        return this.mGlobal.getSupportedHdrOutputTypes();
    }

    public void setShouldAlwaysRespectAppRequestedMode(boolean z) {
        this.mGlobal.setShouldAlwaysRespectAppRequestedMode(z);
    }

    public boolean shouldAlwaysRespectAppRequestedMode() {
        return this.mGlobal.shouldAlwaysRespectAppRequestedMode();
    }

    public boolean supportsSeamlessRefreshRateSwitching() {
        return this.mContext.getResources().getBoolean(com.android.internal.R.bool.config_supportsSeamlessRefreshRateSwitching);
    }

    public void setRefreshRateSwitchingType(int i) {
        this.mGlobal.setRefreshRateSwitchingType(i);
    }

    public int getMatchContentFrameRateUserPreference() {
        return toMatchContentFrameRateSetting(this.mGlobal.getRefreshRateSwitchingType());
    }

    private int toMatchContentFrameRateSetting(int i) {
        switch (i) {
            case 0:
                return 0;
            case 1:
            case 3:
                return 1;
            case 2:
                return 2;
            default:
                android.util.Slog.e(TAG, i + " is not a valid value of switching type.");
                return -1;
        }
    }

    @android.annotation.SystemApi
    public static android.hardware.display.VirtualDisplay createVirtualDisplay(java.lang.String str, int i, int i2, int i3, android.view.Surface surface) {
        android.hardware.display.IDisplayManager asInterface = android.hardware.display.IDisplayManager.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.DISPLAY_SERVICE));
        android.content.pm.IPackageManager asInterface2 = android.content.pm.IPackageManager.Stub.asInterface(android.os.ServiceManager.getService("package"));
        android.hardware.display.VirtualDisplayConfig.Builder displayIdToMirror = new android.hardware.display.VirtualDisplayConfig.Builder(str, i, i2, 1).setFlags(16).setDisplayIdToMirror(i3);
        if (surface != null) {
            displayIdToMirror.setSurface(surface);
        }
        android.hardware.display.VirtualDisplayConfig build = displayIdToMirror.build();
        try {
            java.lang.String[] packagesForUid = asInterface2.getPackagesForUid(android.os.Process.myUid());
            java.lang.String str2 = packagesForUid == null ? null : packagesForUid[0];
            android.hardware.display.DisplayManagerGlobal.VirtualDisplayCallback virtualDisplayCallback = new android.hardware.display.DisplayManagerGlobal.VirtualDisplayCallback(null, null);
            try {
                return android.hardware.display.DisplayManagerGlobal.getInstance().createVirtualDisplayWrapper(build, virtualDisplayCallback, asInterface.createVirtualDisplay(build, virtualDisplayCallback, null, str2));
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        } catch (android.os.RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    public interface DisplayListener {
        void onDisplayAdded(int i);

        void onDisplayChanged(int i);

        void onDisplayRemoved(int i);

        default void onDisplayConnected(int i) {
        }

        default void onDisplayDisconnected(int i) {
        }
    }

    private static final class WeakDisplayCache {
        private final android.util.SparseArray<java.lang.ref.WeakReference<android.view.Display>> mDisplayCache;

        private WeakDisplayCache() {
            this.mDisplayCache = new android.util.SparseArray<>();
        }

        android.view.Display get(int i) {
            java.lang.ref.WeakReference<android.view.Display> weakReference = this.mDisplayCache.get(i);
            if (weakReference == null) {
                return null;
            }
            return weakReference.get();
        }

        void put(android.view.Display display) {
            removeStaleEntries();
            this.mDisplayCache.put(display.getDisplayId(), new java.lang.ref.WeakReference<>(display));
        }

        private void removeStaleEntries() {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            for (int i = 0; i < this.mDisplayCache.size(); i++) {
                if (this.mDisplayCache.valueAt(i).get() == null) {
                    arrayList.add(java.lang.Integer.valueOf(i));
                }
            }
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                this.mDisplayCache.removeAt(((java.lang.Integer) arrayList.get(i2)).intValue());
            }
        }
    }
}
