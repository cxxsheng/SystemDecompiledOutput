package android.os;

/* loaded from: classes3.dex */
public class HardwarePropertiesManager {
    public static final int DEVICE_TEMPERATURE_BATTERY = 2;
    public static final int DEVICE_TEMPERATURE_CPU = 0;
    public static final int DEVICE_TEMPERATURE_GPU = 1;
    public static final int DEVICE_TEMPERATURE_SKIN = 3;
    private static final java.lang.String TAG = android.os.HardwarePropertiesManager.class.getSimpleName();
    public static final int TEMPERATURE_CURRENT = 0;
    public static final int TEMPERATURE_SHUTDOWN = 2;
    public static final int TEMPERATURE_THROTTLING = 1;
    public static final int TEMPERATURE_THROTTLING_BELOW_VR_MIN = 3;
    public static final float UNDEFINED_TEMPERATURE = -3.4028235E38f;
    private final android.content.Context mContext;
    private final android.os.IHardwarePropertiesManager mService;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DeviceTemperatureType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface TemperatureSource {
    }

    public HardwarePropertiesManager(android.content.Context context, android.os.IHardwarePropertiesManager iHardwarePropertiesManager) {
        this.mContext = context;
        this.mService = iHardwarePropertiesManager;
    }

    public float[] getDeviceTemperatures(int i, int i2) {
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
                switch (i2) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                        try {
                            return this.mService.getDeviceTemperatures(this.mContext.getOpPackageName(), i, i2);
                        } catch (android.os.RemoteException e) {
                            throw e.rethrowFromSystemServer();
                        }
                    default:
                        android.util.Log.w(TAG, "Unknown device temperature source.");
                        return new float[0];
                }
            default:
                android.util.Log.w(TAG, "Unknown device temperature type.");
                return new float[0];
        }
    }

    public android.os.CpuUsageInfo[] getCpuUsages() {
        try {
            return this.mService.getCpuUsages(this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public float[] getFanSpeeds() {
        try {
            return this.mService.getFanSpeeds(this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
