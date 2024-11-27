package android.hardware.display;

/* loaded from: classes2.dex */
public abstract class DisplayManagerInternal {
    public static final int REFRESH_RATE_LIMIT_HIGH_BRIGHTNESS_MODE = 1;

    public interface DisplayGroupListener {
        void onDisplayGroupAdded(int i);

        void onDisplayGroupChanged(int i);

        void onDisplayGroupRemoved(int i);
    }

    public interface DisplayOffloader {
        void onBlockingScreenOn(java.lang.Runnable runnable);

        boolean startOffload();

        void stopOffload();
    }

    public interface DisplayPowerCallbacks {
        void acquireSuspendBlocker(java.lang.String str);

        void onDisplayStateChange(boolean z, boolean z2);

        void onProximityNegative();

        void onProximityPositive();

        void onStateChanged();

        void releaseSuspendBlocker(java.lang.String str);
    }

    public interface DisplayTransactionListener {
        void onDisplayTransaction(android.view.SurfaceControl.Transaction transaction);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RefreshRateLimitType {
    }

    public abstract int createVirtualDisplay(android.hardware.display.VirtualDisplayConfig virtualDisplayConfig, android.hardware.display.IVirtualDisplayCallback iVirtualDisplayCallback, android.companion.virtual.IVirtualDevice iVirtualDevice, android.window.DisplayWindowPolicyController displayWindowPolicyController, java.lang.String str);

    public abstract android.hardware.display.DisplayManagerInternal.AmbientLightSensorData getAmbientLightSensorData(int i);

    public abstract android.util.IntArray getDisplayGroupIds();

    public abstract int getDisplayIdToMirror(int i);

    public abstract android.view.DisplayInfo getDisplayInfo(int i);

    public abstract android.view.SurfaceControl.DisplayPrimaries getDisplayNativePrimaries(int i);

    public abstract android.graphics.Point getDisplayPosition(int i);

    public abstract android.graphics.Point getDisplaySurfaceDefaultSize(int i);

    public abstract android.window.DisplayWindowPolicyController getDisplayWindowPolicyController(int i);

    public abstract android.hardware.display.DisplayedContentSample getDisplayedContentSample(int i, long j, long j2);

    public abstract android.hardware.display.DisplayedContentSamplingAttributes getDisplayedContentSamplingAttributes(int i);

    public abstract android.hardware.input.HostUsiVersion getHostUsiVersion(int i);

    public abstract void getNonOverrideDisplayInfo(int i, android.view.DisplayInfo displayInfo);

    public abstract java.util.Set<android.view.DisplayInfo> getPossibleDisplayInfo(int i);

    public abstract android.view.SurfaceControl.RefreshRateRange getRefreshRateForDisplayAndSensor(int i, java.lang.String str, java.lang.String str2);

    public abstract java.util.List<android.hardware.display.DisplayManagerInternal.RefreshRateLimitation> getRefreshRateLimitations(int i);

    public abstract int getRefreshRateSwitchingType();

    public abstract void ignoreProximitySensorUntilChanged();

    public abstract void initPowerManagement(android.hardware.display.DisplayManagerInternal.DisplayPowerCallbacks displayPowerCallbacks, android.os.Handler handler, android.hardware.SensorManager sensorManager);

    public abstract boolean isProximitySensorAvailable();

    public abstract void onEarlyInteractivityChange(boolean z);

    public abstract void onOverlayChanged();

    public abstract void onPresentation(int i, boolean z);

    public abstract void performTraversal(android.view.SurfaceControl.Transaction transaction, android.util.SparseArray<android.view.SurfaceControl.Transaction> sparseArray);

    public abstract void persistBrightnessTrackerState();

    public abstract void registerDisplayGroupListener(android.hardware.display.DisplayManagerInternal.DisplayGroupListener displayGroupListener);

    public abstract android.hardware.display.DisplayManagerInternal.DisplayOffloadSession registerDisplayOffloader(int i, android.hardware.display.DisplayManagerInternal.DisplayOffloader displayOffloader);

    public abstract void registerDisplayTransactionListener(android.hardware.display.DisplayManagerInternal.DisplayTransactionListener displayTransactionListener);

    public abstract boolean requestPowerState(int i, android.hardware.display.DisplayManagerInternal.DisplayPowerRequest displayPowerRequest, boolean z);

    public abstract void setDisplayAccessUIDs(android.util.SparseArray<android.util.IntArray> sparseArray);

    public abstract void setDisplayInfoOverrideFromWindowManager(int i, android.view.DisplayInfo displayInfo);

    public abstract void setDisplayOffsets(int i, int i2, int i3);

    public abstract void setDisplayProperties(int i, boolean z, float f, int i2, float f2, float f3, boolean z2, boolean z3, boolean z4);

    public abstract void setDisplayScalingDisabled(int i, boolean z);

    public abstract boolean setDisplayedContentSamplingEnabled(int i, boolean z, int i2, int i3);

    public abstract void setWindowManagerMirroring(int i, boolean z);

    public abstract android.window.ScreenCapture.ScreenshotHardwareBuffer systemScreenshot(int i);

    public abstract void unregisterDisplayGroupListener(android.hardware.display.DisplayManagerInternal.DisplayGroupListener displayGroupListener);

    public abstract void unregisterDisplayTransactionListener(android.hardware.display.DisplayManagerInternal.DisplayTransactionListener displayTransactionListener);

    public abstract android.window.ScreenCapture.ScreenshotHardwareBuffer userScreenshot(int i);

    public static class DisplayPowerRequest {
        public static final int POLICY_BRIGHT = 3;
        public static final int POLICY_DIM = 2;
        public static final int POLICY_DOZE = 1;
        public static final int POLICY_OFF = 0;
        public boolean blockScreenOn;
        public boolean boostScreenBrightness;
        public float dozeScreenBrightness;
        public int dozeScreenState;
        public boolean lowPowerMode;
        public int policy;
        public float screenAutoBrightnessAdjustmentOverride;
        public float screenBrightnessOverride;
        public float screenLowPowerBrightnessFactor;
        public boolean useProximitySensor;

        public DisplayPowerRequest() {
            this.policy = 3;
            this.useProximitySensor = false;
            this.screenBrightnessOverride = Float.NaN;
            this.screenAutoBrightnessAdjustmentOverride = Float.NaN;
            this.screenLowPowerBrightnessFactor = 0.5f;
            this.blockScreenOn = false;
            this.dozeScreenBrightness = Float.NaN;
            this.dozeScreenState = 0;
        }

        public DisplayPowerRequest(android.hardware.display.DisplayManagerInternal.DisplayPowerRequest displayPowerRequest) {
            copyFrom(displayPowerRequest);
        }

        public boolean isBrightOrDim() {
            return this.policy == 3 || this.policy == 2;
        }

        public void copyFrom(android.hardware.display.DisplayManagerInternal.DisplayPowerRequest displayPowerRequest) {
            this.policy = displayPowerRequest.policy;
            this.useProximitySensor = displayPowerRequest.useProximitySensor;
            this.screenBrightnessOverride = displayPowerRequest.screenBrightnessOverride;
            this.screenAutoBrightnessAdjustmentOverride = displayPowerRequest.screenAutoBrightnessAdjustmentOverride;
            this.screenLowPowerBrightnessFactor = displayPowerRequest.screenLowPowerBrightnessFactor;
            this.blockScreenOn = displayPowerRequest.blockScreenOn;
            this.lowPowerMode = displayPowerRequest.lowPowerMode;
            this.boostScreenBrightness = displayPowerRequest.boostScreenBrightness;
            this.dozeScreenBrightness = displayPowerRequest.dozeScreenBrightness;
            this.dozeScreenState = displayPowerRequest.dozeScreenState;
        }

        public boolean equals(java.lang.Object obj) {
            return (obj instanceof android.hardware.display.DisplayManagerInternal.DisplayPowerRequest) && equals((android.hardware.display.DisplayManagerInternal.DisplayPowerRequest) obj);
        }

        public boolean equals(android.hardware.display.DisplayManagerInternal.DisplayPowerRequest displayPowerRequest) {
            return displayPowerRequest != null && this.policy == displayPowerRequest.policy && this.useProximitySensor == displayPowerRequest.useProximitySensor && floatEquals(this.screenBrightnessOverride, displayPowerRequest.screenBrightnessOverride) && floatEquals(this.screenAutoBrightnessAdjustmentOverride, displayPowerRequest.screenAutoBrightnessAdjustmentOverride) && this.screenLowPowerBrightnessFactor == displayPowerRequest.screenLowPowerBrightnessFactor && this.blockScreenOn == displayPowerRequest.blockScreenOn && this.lowPowerMode == displayPowerRequest.lowPowerMode && this.boostScreenBrightness == displayPowerRequest.boostScreenBrightness && floatEquals(this.dozeScreenBrightness, displayPowerRequest.dozeScreenBrightness) && this.dozeScreenState == displayPowerRequest.dozeScreenState;
        }

        private boolean floatEquals(float f, float f2) {
            return f == f2 || (java.lang.Float.isNaN(f) && java.lang.Float.isNaN(f2));
        }

        public int hashCode() {
            return 0;
        }

        public java.lang.String toString() {
            return "policy=" + policyToString(this.policy) + ", useProximitySensor=" + this.useProximitySensor + ", screenBrightnessOverride=" + this.screenBrightnessOverride + ", screenAutoBrightnessAdjustmentOverride=" + this.screenAutoBrightnessAdjustmentOverride + ", screenLowPowerBrightnessFactor=" + this.screenLowPowerBrightnessFactor + ", blockScreenOn=" + this.blockScreenOn + ", lowPowerMode=" + this.lowPowerMode + ", boostScreenBrightness=" + this.boostScreenBrightness + ", dozeScreenBrightness=" + this.dozeScreenBrightness + ", dozeScreenState=" + android.view.Display.stateToString(this.dozeScreenState);
        }

        public static java.lang.String policyToString(int i) {
            switch (i) {
                case 0:
                    return "OFF";
                case 1:
                    return "DOZE";
                case 2:
                    return "DIM";
                case 3:
                    return "BRIGHT";
                default:
                    return java.lang.Integer.toString(i);
            }
        }
    }

    public static final class RefreshRateLimitation {
        public android.view.SurfaceControl.RefreshRateRange range;
        public int type;

        public RefreshRateLimitation(int i, float f, float f2) {
            this.type = i;
            this.range = new android.view.SurfaceControl.RefreshRateRange(f, f2);
        }

        public java.lang.String toString() {
            return "RefreshRateLimitation(" + this.type + ": " + this.range + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    public static final class AmbientLightSensorData {
        public java.lang.String sensorName;
        public java.lang.String sensorType;

        public AmbientLightSensorData(java.lang.String str, java.lang.String str2) {
            this.sensorName = str;
            this.sensorType = str2;
        }

        public java.lang.String toString() {
            return "AmbientLightSensorData(" + this.sensorName + ", " + this.sensorType + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    public interface DisplayOffloadSession {
        boolean blockScreenOn(java.lang.Runnable runnable);

        float[] getAutoBrightnessLevels(int i);

        float[] getAutoBrightnessLuxLevels(int i);

        boolean isActive();

        void setDozeStateOverride(int i);

        void updateBrightness(float f);

        static boolean isSupportedOffloadState(int i) {
            return android.view.Display.isSuspendedState(i);
        }
    }
}
