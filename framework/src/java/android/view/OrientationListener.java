package android.view;

@java.lang.Deprecated
/* loaded from: classes4.dex */
public abstract class OrientationListener implements android.hardware.SensorListener {
    public static final int ORIENTATION_UNKNOWN = -1;
    private android.view.OrientationEventListener mOrientationEventLis;

    public abstract void onOrientationChanged(int i);

    public OrientationListener(android.content.Context context) {
        this.mOrientationEventLis = new android.view.OrientationListener.OrientationEventListenerInternal(context);
    }

    public OrientationListener(android.content.Context context, int i) {
        this.mOrientationEventLis = new android.view.OrientationListener.OrientationEventListenerInternal(context, i);
    }

    class OrientationEventListenerInternal extends android.view.OrientationEventListener {
        OrientationEventListenerInternal(android.content.Context context) {
            super(context);
        }

        OrientationEventListenerInternal(android.content.Context context, int i) {
            super(context, i);
            registerListener(android.view.OrientationListener.this);
        }

        @Override // android.view.OrientationEventListener
        public void onOrientationChanged(int i) {
            android.view.OrientationListener.this.onOrientationChanged(i);
        }
    }

    public void enable() {
        this.mOrientationEventLis.enable();
    }

    public void disable() {
        this.mOrientationEventLis.disable();
    }

    @Override // android.hardware.SensorListener
    public void onAccuracyChanged(int i, int i2) {
    }

    @Override // android.hardware.SensorListener
    public void onSensorChanged(int i, float[] fArr) {
    }
}
