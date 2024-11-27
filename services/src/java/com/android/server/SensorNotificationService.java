package com.android.server;

/* loaded from: classes.dex */
public class SensorNotificationService extends com.android.server.SystemService implements android.hardware.SensorEventListener, android.location.LocationListener {
    private static final java.lang.String ATTRIBUTION_TAG = "SensorNotificationService";
    private static final boolean DBG = false;
    private static final long KM_IN_M = 1000;
    private static final long LOCATION_MIN_DISTANCE = 100000;
    private static final long LOCATION_MIN_TIME = 1800000;
    private static final long MILLIS_2010_1_1 = 1262358000000L;
    private static final long MINUTE_IN_MS = 60000;
    private static final java.lang.String PROPERTY_USE_MOCKED_LOCATION = "sensor.notification.use_mocked";
    private static final java.lang.String TAG = "SensorNotificationService";
    private android.content.Context mContext;
    private long mLocalGeomagneticFieldUpdateTime;
    private android.location.LocationManager mLocationManager;
    private android.hardware.Sensor mMetaSensor;
    private android.hardware.SensorManager mSensorManager;

    public SensorNotificationService(android.content.Context context) {
        super(context.createAttributionContext("SensorNotificationService"));
        this.mLocalGeomagneticFieldUpdateTime = -1800000L;
        this.mContext = getContext();
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        com.android.server.LocalServices.addService(com.android.server.SensorNotificationService.class, this);
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        if (i == 600) {
            this.mSensorManager = (android.hardware.SensorManager) this.mContext.getSystemService("sensor");
            this.mMetaSensor = this.mSensorManager.getDefaultSensor(32);
            if (this.mMetaSensor != null) {
                this.mSensorManager.registerListener(this, this.mMetaSensor, 0);
            }
        }
        if (i == 1000) {
            this.mLocationManager = (android.location.LocationManager) this.mContext.getSystemService("location");
            if (this.mLocationManager != null) {
                this.mLocationManager.requestLocationUpdates("passive", 1800000L, 100000.0f, this);
            }
        }
    }

    private void broadcastDynamicSensorChanged() {
        android.content.Intent intent = new android.content.Intent("android.intent.action.DYNAMIC_SENSOR_CHANGED");
        intent.setFlags(1073741824);
        this.mContext.sendBroadcastAsUser(intent, android.os.UserHandle.ALL);
    }

    @Override // android.hardware.SensorEventListener
    public void onSensorChanged(android.hardware.SensorEvent sensorEvent) {
        if (sensorEvent.sensor == this.mMetaSensor) {
            broadcastDynamicSensorChanged();
        }
    }

    @Override // android.location.LocationListener
    public void onLocationChanged(android.location.Location location) {
        if ((location.getLatitude() == 0.0d && location.getLongitude() == 0.0d) || android.os.SystemClock.elapsedRealtime() - this.mLocalGeomagneticFieldUpdateTime < 600000) {
            return;
        }
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        if (useMockedLocation() == location.isMock() || currentTimeMillis < MILLIS_2010_1_1) {
            return;
        }
        try {
            android.hardware.SensorAdditionalInfo createLocalGeomagneticField = android.hardware.SensorAdditionalInfo.createLocalGeomagneticField(new android.hardware.GeomagneticField((float) location.getLatitude(), (float) location.getLongitude(), (float) location.getAltitude(), currentTimeMillis).getFieldStrength() / 1000.0f, (float) ((r0.getDeclination() * 3.141592653589793d) / 180.0d), (float) ((r0.getInclination() * 3.141592653589793d) / 180.0d));
            if (createLocalGeomagneticField != null) {
                this.mSensorManager.setOperationParameter(createLocalGeomagneticField);
                this.mLocalGeomagneticFieldUpdateTime = android.os.SystemClock.elapsedRealtime();
            }
        } catch (java.lang.IllegalArgumentException e) {
            android.util.Slog.e("SensorNotificationService", "Invalid local geomagnetic field, ignore.");
        }
    }

    @Override // android.hardware.SensorEventListener
    public void onAccuracyChanged(android.hardware.Sensor sensor, int i) {
    }

    @Override // android.location.LocationListener
    public void onStatusChanged(java.lang.String str, int i, android.os.Bundle bundle) {
    }

    @Override // android.location.LocationListener
    public void onProviderEnabled(java.lang.String str) {
    }

    @Override // android.location.LocationListener
    public void onProviderDisabled(java.lang.String str) {
    }

    private boolean useMockedLocation() {
        return "false".equals(java.lang.System.getProperty(PROPERTY_USE_MOCKED_LOCATION, "false"));
    }
}
