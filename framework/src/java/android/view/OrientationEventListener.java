package android.view;

/* loaded from: classes4.dex */
public abstract class OrientationEventListener {
    private static final boolean DEBUG = false;
    public static final int ORIENTATION_UNKNOWN = -1;
    private static final java.lang.String TAG = "OrientationEventListener";
    private static final boolean localLOGV = false;
    private boolean mEnabled;
    private android.view.OrientationListener mOldListener;
    private int mOrientation;
    private int mRate;
    private android.hardware.Sensor mSensor;
    private android.hardware.SensorEventListener mSensorEventListener;
    private android.hardware.SensorManager mSensorManager;

    public abstract void onOrientationChanged(int i);

    public OrientationEventListener(android.content.Context context) {
        this(context, 3);
    }

    public OrientationEventListener(android.content.Context context, int i) {
        this.mOrientation = -1;
        this.mEnabled = false;
        this.mSensorManager = (android.hardware.SensorManager) context.getSystemService(android.content.Context.SENSOR_SERVICE);
        this.mRate = i;
        this.mSensor = this.mSensorManager.getDefaultSensor(1);
        if (this.mSensor != null) {
            this.mSensorEventListener = new android.view.OrientationEventListener.SensorEventListenerImpl();
        }
    }

    void registerListener(android.view.OrientationListener orientationListener) {
        this.mOldListener = orientationListener;
    }

    public void enable() {
        if (this.mSensor == null) {
            android.util.Log.w(TAG, "Cannot detect sensors. Not enabled");
        } else if (!this.mEnabled) {
            this.mSensorManager.registerListener(this.mSensorEventListener, this.mSensor, this.mRate);
            this.mEnabled = true;
        }
    }

    public void disable() {
        if (this.mSensor == null) {
            android.util.Log.w(TAG, "Cannot detect sensors. Invalid disable");
        } else if (this.mEnabled) {
            this.mSensorManager.unregisterListener(this.mSensorEventListener);
            this.mEnabled = false;
        }
    }

    class SensorEventListenerImpl implements android.hardware.SensorEventListener {
        private static final int _DATA_X = 0;
        private static final int _DATA_Y = 1;
        private static final int _DATA_Z = 2;

        SensorEventListenerImpl() {
        }

        @Override // android.hardware.SensorEventListener
        public void onSensorChanged(android.hardware.SensorEvent sensorEvent) {
            int i;
            float[] fArr = sensorEvent.values;
            float f = -fArr[0];
            float f2 = -fArr[1];
            float f3 = -fArr[2];
            if (((f * f) + (f2 * f2)) * 4.0f < f3 * f3) {
                i = -1;
            } else {
                i = 90 - java.lang.Math.round(((float) java.lang.Math.atan2(-f2, f)) * 57.29578f);
                while (i >= 360) {
                    i -= 360;
                }
                while (i < 0) {
                    i += 360;
                }
            }
            if (android.view.OrientationEventListener.this.mOldListener != null) {
                android.view.OrientationEventListener.this.mOldListener.onSensorChanged(1, sensorEvent.values);
            }
            if (i != android.view.OrientationEventListener.this.mOrientation) {
                android.view.OrientationEventListener.this.mOrientation = i;
                android.view.OrientationEventListener.this.onOrientationChanged(i);
            }
        }

        @Override // android.hardware.SensorEventListener
        public void onAccuracyChanged(android.hardware.Sensor sensor, int i) {
        }
    }

    public boolean canDetectOrientation() {
        return this.mSensor != null;
    }
}
