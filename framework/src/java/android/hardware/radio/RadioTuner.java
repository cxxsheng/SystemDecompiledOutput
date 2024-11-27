package android.hardware.radio;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public abstract class RadioTuner {
    public static final int DIRECTION_DOWN = 1;
    public static final int DIRECTION_UP = 0;

    @java.lang.Deprecated
    public static final int ERROR_BACKGROUND_SCAN_FAILED = 6;

    @java.lang.Deprecated
    public static final int ERROR_BACKGROUND_SCAN_UNAVAILABLE = 5;

    @java.lang.Deprecated
    public static final int ERROR_CANCELLED = 2;

    @java.lang.Deprecated
    public static final int ERROR_CONFIG = 4;

    @java.lang.Deprecated
    public static final int ERROR_HARDWARE_FAILURE = 0;

    @java.lang.Deprecated
    public static final int ERROR_SCAN_TIMEOUT = 3;

    @java.lang.Deprecated
    public static final int ERROR_SERVER_DIED = 1;
    public static final int TUNER_RESULT_CANCELED = 6;
    public static final int TUNER_RESULT_INTERNAL_ERROR = 1;
    public static final int TUNER_RESULT_INVALID_ARGUMENTS = 2;
    public static final int TUNER_RESULT_INVALID_STATE = 3;
    public static final int TUNER_RESULT_NOT_SUPPORTED = 4;
    public static final int TUNER_RESULT_OK = 0;
    public static final int TUNER_RESULT_TIMEOUT = 5;
    public static final int TUNER_RESULT_UNKNOWN_ERROR = 7;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface TunerResultType {
    }

    public abstract int cancel();

    @java.lang.Deprecated
    public abstract void cancelAnnouncement();

    public abstract void close();

    @java.lang.Deprecated
    public abstract int getConfiguration(android.hardware.radio.RadioManager.BandConfig[] bandConfigArr);

    public abstract boolean getMute();

    @java.lang.Deprecated
    public abstract int getProgramInformation(android.hardware.radio.RadioManager.ProgramInfo[] programInfoArr);

    @java.lang.Deprecated
    public abstract java.util.List<android.hardware.radio.RadioManager.ProgramInfo> getProgramList(java.util.Map<java.lang.String, java.lang.String> map);

    public abstract boolean hasControl();

    @java.lang.Deprecated
    public abstract boolean isAnalogForced();

    @java.lang.Deprecated
    public abstract boolean isAntennaConnected();

    @java.lang.Deprecated
    public abstract int scan(int i, boolean z);

    @java.lang.Deprecated
    public abstract void setAnalogForced(boolean z);

    @java.lang.Deprecated
    public abstract int setConfiguration(android.hardware.radio.RadioManager.BandConfig bandConfig);

    public abstract int setMute(boolean z);

    @java.lang.Deprecated
    public abstract boolean startBackgroundScan();

    public abstract int step(int i, boolean z);

    @java.lang.Deprecated
    public abstract int tune(int i, int i2);

    public abstract void tune(android.hardware.radio.ProgramSelector programSelector);

    public int seek(int i, boolean z) {
        throw new java.lang.UnsupportedOperationException("Seeking is not supported");
    }

    public android.graphics.Bitmap getMetadataImage(int i) {
        throw new java.lang.UnsupportedOperationException("Getting metadata image must be implemented in child classes");
    }

    public android.hardware.radio.ProgramList getDynamicProgramList(android.hardware.radio.ProgramList.Filter filter) {
        return null;
    }

    public boolean isConfigFlagSupported(int i) {
        return false;
    }

    public boolean isConfigFlagSet(int i) {
        throw new java.lang.UnsupportedOperationException("isConfigFlagSet is not supported");
    }

    public void setConfigFlag(int i, boolean z) {
        throw new java.lang.UnsupportedOperationException("Setting config flag is not supported");
    }

    public java.util.Map<java.lang.String, java.lang.String> setParameters(java.util.Map<java.lang.String, java.lang.String> map) {
        throw new java.lang.UnsupportedOperationException("Setting parameters is not supported");
    }

    public java.util.Map<java.lang.String, java.lang.String> getParameters(java.util.List<java.lang.String> list) {
        throw new java.lang.UnsupportedOperationException("Getting parameters is not supported");
    }

    public static abstract class Callback {
        public void onError(int i) {
        }

        public void onTuneFailed(int i, android.hardware.radio.ProgramSelector programSelector) {
        }

        @java.lang.Deprecated
        public void onConfigurationChanged(android.hardware.radio.RadioManager.BandConfig bandConfig) {
        }

        public void onProgramInfoChanged(android.hardware.radio.RadioManager.ProgramInfo programInfo) {
        }

        @java.lang.Deprecated
        public void onMetadataChanged(android.hardware.radio.RadioMetadata radioMetadata) {
        }

        public void onTrafficAnnouncement(boolean z) {
        }

        public void onEmergencyAnnouncement(boolean z) {
        }

        public void onAntennaState(boolean z) {
        }

        public void onControlChanged(boolean z) {
        }

        public void onBackgroundScanAvailabilityChange(boolean z) {
        }

        public void onBackgroundScanComplete() {
        }

        public void onProgramListChanged() {
        }

        public void onConfigFlagUpdated(int i, boolean z) {
        }

        public void onParametersUpdated(java.util.Map<java.lang.String, java.lang.String> map) {
        }
    }
}
