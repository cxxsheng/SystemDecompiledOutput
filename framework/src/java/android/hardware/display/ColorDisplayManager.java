package android.hardware.display;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class ColorDisplayManager {

    @android.annotation.SystemApi
    public static final int AUTO_MODE_CUSTOM_TIME = 1;

    @android.annotation.SystemApi
    public static final int AUTO_MODE_DISABLED = 0;

    @android.annotation.SystemApi
    public static final int AUTO_MODE_TWILIGHT = 2;

    @android.annotation.SystemApi
    public static final int CAPABILITY_HARDWARE_ACCELERATION_GLOBAL = 2;

    @android.annotation.SystemApi
    public static final int CAPABILITY_HARDWARE_ACCELERATION_PER_APP = 4;

    @android.annotation.SystemApi
    public static final int CAPABILITY_NONE = 0;

    @android.annotation.SystemApi
    public static final int CAPABILITY_PROTECTED_CONTENT = 1;
    public static final int COLOR_MODE_AUTOMATIC = 3;
    public static final int COLOR_MODE_BOOSTED = 1;
    public static final int COLOR_MODE_NATURAL = 0;
    public static final int COLOR_MODE_SATURATED = 2;
    public static final int VENDOR_COLOR_MODE_RANGE_MAX = 511;
    public static final int VENDOR_COLOR_MODE_RANGE_MIN = 256;
    private final android.hardware.display.ColorDisplayManager.ColorDisplayManagerInternal mManager = android.hardware.display.ColorDisplayManager.ColorDisplayManagerInternal.getInstance();
    private com.android.internal.logging.MetricsLogger mMetricsLogger;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AutoMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CapabilityType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ColorMode {
    }

    public boolean setNightDisplayActivated(boolean z) {
        return this.mManager.setNightDisplayActivated(z);
    }

    public boolean isNightDisplayActivated() {
        return this.mManager.isNightDisplayActivated();
    }

    public boolean setNightDisplayColorTemperature(int i) {
        return this.mManager.setNightDisplayColorTemperature(i);
    }

    public int getNightDisplayColorTemperature() {
        return this.mManager.getNightDisplayColorTemperature();
    }

    @android.annotation.SystemApi
    public int getNightDisplayAutoMode() {
        return this.mManager.getNightDisplayAutoMode();
    }

    public int getNightDisplayAutoModeRaw() {
        return this.mManager.getNightDisplayAutoModeRaw();
    }

    @android.annotation.SystemApi
    public boolean setNightDisplayAutoMode(int i) {
        if (i != 0 && i != 1 && i != 2) {
            throw new java.lang.IllegalArgumentException("Invalid autoMode: " + i);
        }
        if (this.mManager.getNightDisplayAutoMode() != i) {
            getMetricsLogger().write(new android.metrics.LogMaker(com.android.internal.logging.nano.MetricsProto.MetricsEvent.ACTION_NIGHT_DISPLAY_AUTO_MODE_CHANGED).setType(4).setSubtype(i));
        }
        return this.mManager.setNightDisplayAutoMode(i);
    }

    public java.time.LocalTime getNightDisplayCustomStartTime() {
        return this.mManager.getNightDisplayCustomStartTime().getLocalTime();
    }

    @android.annotation.SystemApi
    public boolean setNightDisplayCustomStartTime(java.time.LocalTime localTime) {
        if (localTime == null) {
            throw new java.lang.IllegalArgumentException("startTime cannot be null");
        }
        getMetricsLogger().write(new android.metrics.LogMaker(com.android.internal.logging.nano.MetricsProto.MetricsEvent.ACTION_NIGHT_DISPLAY_AUTO_MODE_CUSTOM_TIME_CHANGED).setType(4).setSubtype(0));
        return this.mManager.setNightDisplayCustomStartTime(new android.hardware.display.Time(localTime));
    }

    public java.time.LocalTime getNightDisplayCustomEndTime() {
        return this.mManager.getNightDisplayCustomEndTime().getLocalTime();
    }

    @android.annotation.SystemApi
    public boolean setNightDisplayCustomEndTime(java.time.LocalTime localTime) {
        if (localTime == null) {
            throw new java.lang.IllegalArgumentException("endTime cannot be null");
        }
        getMetricsLogger().write(new android.metrics.LogMaker(com.android.internal.logging.nano.MetricsProto.MetricsEvent.ACTION_NIGHT_DISPLAY_AUTO_MODE_CUSTOM_TIME_CHANGED).setType(4).setSubtype(1));
        return this.mManager.setNightDisplayCustomEndTime(new android.hardware.display.Time(localTime));
    }

    public void setColorMode(int i) {
        this.mManager.setColorMode(i);
    }

    public int getColorMode() {
        return this.mManager.getColorMode();
    }

    public static boolean isStandardColorMode(int i) {
        return i == 0 || i == 1 || i == 2 || i == 3;
    }

    public boolean isDeviceColorManaged() {
        return this.mManager.isDeviceColorManaged();
    }

    @android.annotation.SystemApi
    public boolean setSaturationLevel(int i) {
        return this.mManager.setSaturationLevel(i);
    }

    public boolean isSaturationActivated() {
        return this.mManager.isSaturationActivated();
    }

    @android.annotation.SystemApi
    public boolean setAppSaturationLevel(java.lang.String str, int i) {
        return this.mManager.setAppSaturationLevel(str, i);
    }

    public boolean setDisplayWhiteBalanceEnabled(boolean z) {
        return this.mManager.setDisplayWhiteBalanceEnabled(z);
    }

    public boolean isDisplayWhiteBalanceEnabled() {
        return this.mManager.isDisplayWhiteBalanceEnabled();
    }

    public boolean setReduceBrightColorsActivated(boolean z) {
        return this.mManager.setReduceBrightColorsActivated(z);
    }

    public boolean isReduceBrightColorsActivated() {
        return this.mManager.isReduceBrightColorsActivated();
    }

    public boolean setReduceBrightColorsStrength(int i) {
        return this.mManager.setReduceBrightColorsStrength(i);
    }

    public int getReduceBrightColorsStrength() {
        return this.mManager.getReduceBrightColorsStrength();
    }

    public float getReduceBrightColorsOffsetFactor() {
        return this.mManager.getReduceBrightColorsOffsetFactor();
    }

    public static boolean isNightDisplayAvailable(android.content.Context context) {
        return context.getResources().getBoolean(com.android.internal.R.bool.config_nightDisplayAvailable);
    }

    public static int getMinimumColorTemperature(android.content.Context context) {
        return context.getResources().getInteger(com.android.internal.R.integer.config_nightDisplayColorTemperatureMin);
    }

    public static int getMaximumColorTemperature(android.content.Context context) {
        return context.getResources().getInteger(com.android.internal.R.integer.config_nightDisplayColorTemperatureMax);
    }

    public static boolean isDisplayWhiteBalanceAvailable(android.content.Context context) {
        return context.getResources().getBoolean(com.android.internal.R.bool.config_displayWhiteBalanceAvailable);
    }

    public static boolean isReduceBrightColorsAvailable(android.content.Context context) {
        return context.getResources().getBoolean(com.android.internal.R.bool.config_reduceBrightColorsAvailable);
    }

    public static int getMinimumReduceBrightColorsStrength(android.content.Context context) {
        return context.getResources().getInteger(com.android.internal.R.integer.config_reduceBrightColorsStrengthMin);
    }

    public static int getMaximumReduceBrightColorsStrength(android.content.Context context) {
        return context.getResources().getInteger(com.android.internal.R.integer.config_reduceBrightColorsStrengthMax);
    }

    public static boolean isColorTransformAccelerated(android.content.Context context) {
        return context.getResources().getBoolean(com.android.internal.R.bool.config_setColorTransformAccelerated);
    }

    @android.annotation.SystemApi
    public int getTransformCapabilities() {
        return this.mManager.getTransformCapabilities();
    }

    public static boolean areAccessibilityTransformsEnabled(android.content.Context context) {
        android.content.ContentResolver contentResolver = context.getContentResolver();
        return android.provider.Settings.Secure.getInt(contentResolver, android.provider.Settings.Secure.ACCESSIBILITY_DISPLAY_INVERSION_ENABLED, 0) == 1 || android.provider.Settings.Secure.getInt(contentResolver, android.provider.Settings.Secure.ACCESSIBILITY_DISPLAY_DALTONIZER_ENABLED, 0) == 1;
    }

    private com.android.internal.logging.MetricsLogger getMetricsLogger() {
        if (this.mMetricsLogger == null) {
            this.mMetricsLogger = new com.android.internal.logging.MetricsLogger();
        }
        return this.mMetricsLogger;
    }

    private static class ColorDisplayManagerInternal {
        private static android.hardware.display.ColorDisplayManager.ColorDisplayManagerInternal sInstance;
        private final android.hardware.display.IColorDisplayManager mCdm;

        private ColorDisplayManagerInternal(android.hardware.display.IColorDisplayManager iColorDisplayManager) {
            this.mCdm = iColorDisplayManager;
        }

        public static android.hardware.display.ColorDisplayManager.ColorDisplayManagerInternal getInstance() {
            android.hardware.display.ColorDisplayManager.ColorDisplayManagerInternal colorDisplayManagerInternal;
            synchronized (android.hardware.display.ColorDisplayManager.ColorDisplayManagerInternal.class) {
                if (sInstance == null) {
                    try {
                        sInstance = new android.hardware.display.ColorDisplayManager.ColorDisplayManagerInternal(android.hardware.display.IColorDisplayManager.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow(android.content.Context.COLOR_DISPLAY_SERVICE)));
                    } catch (android.os.ServiceManager.ServiceNotFoundException e) {
                        throw new java.lang.IllegalStateException(e);
                    }
                }
                colorDisplayManagerInternal = sInstance;
            }
            return colorDisplayManagerInternal;
        }

        boolean isNightDisplayActivated() {
            try {
                return this.mCdm.isNightDisplayActivated();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        boolean setNightDisplayActivated(boolean z) {
            try {
                return this.mCdm.setNightDisplayActivated(z);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        int getNightDisplayColorTemperature() {
            try {
                return this.mCdm.getNightDisplayColorTemperature();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        boolean setNightDisplayColorTemperature(int i) {
            try {
                return this.mCdm.setNightDisplayColorTemperature(i);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        int getNightDisplayAutoMode() {
            try {
                return this.mCdm.getNightDisplayAutoMode();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        int getNightDisplayAutoModeRaw() {
            try {
                return this.mCdm.getNightDisplayAutoModeRaw();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        boolean setNightDisplayAutoMode(int i) {
            try {
                return this.mCdm.setNightDisplayAutoMode(i);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        android.hardware.display.Time getNightDisplayCustomStartTime() {
            try {
                return this.mCdm.getNightDisplayCustomStartTime();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        boolean setNightDisplayCustomStartTime(android.hardware.display.Time time) {
            try {
                return this.mCdm.setNightDisplayCustomStartTime(time);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        android.hardware.display.Time getNightDisplayCustomEndTime() {
            try {
                return this.mCdm.getNightDisplayCustomEndTime();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        boolean setNightDisplayCustomEndTime(android.hardware.display.Time time) {
            try {
                return this.mCdm.setNightDisplayCustomEndTime(time);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        boolean isDeviceColorManaged() {
            try {
                return this.mCdm.isDeviceColorManaged();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        boolean setSaturationLevel(int i) {
            try {
                return this.mCdm.setSaturationLevel(i);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        boolean isSaturationActivated() {
            try {
                return this.mCdm.isSaturationActivated();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        boolean setAppSaturationLevel(java.lang.String str, int i) {
            try {
                return this.mCdm.setAppSaturationLevel(str, i);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        boolean isDisplayWhiteBalanceEnabled() {
            try {
                return this.mCdm.isDisplayWhiteBalanceEnabled();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        boolean setDisplayWhiteBalanceEnabled(boolean z) {
            try {
                return this.mCdm.setDisplayWhiteBalanceEnabled(z);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        boolean isReduceBrightColorsActivated() {
            try {
                return this.mCdm.isReduceBrightColorsActivated();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        boolean setReduceBrightColorsActivated(boolean z) {
            try {
                return this.mCdm.setReduceBrightColorsActivated(z);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        int getReduceBrightColorsStrength() {
            try {
                return this.mCdm.getReduceBrightColorsStrength();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        boolean setReduceBrightColorsStrength(int i) {
            try {
                return this.mCdm.setReduceBrightColorsStrength(i);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        float getReduceBrightColorsOffsetFactor() {
            try {
                return this.mCdm.getReduceBrightColorsOffsetFactor();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        int getColorMode() {
            try {
                return this.mCdm.getColorMode();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void setColorMode(int i) {
            try {
                this.mCdm.setColorMode(i);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        int getTransformCapabilities() {
            try {
                return this.mCdm.getTransformCapabilities();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }
}
