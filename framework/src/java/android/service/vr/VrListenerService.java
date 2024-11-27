package android.service.vr;

/* loaded from: classes3.dex */
public abstract class VrListenerService extends android.app.Service {
    private static final int MSG_ON_CURRENT_VR_ACTIVITY_CHANGED = 1;
    public static final java.lang.String SERVICE_INTERFACE = "android.service.vr.VrListenerService";
    private final android.service.vr.IVrListener.Stub mBinder = new android.service.vr.IVrListener.Stub() { // from class: android.service.vr.VrListenerService.1
        @Override // android.service.vr.IVrListener
        public void focusedActivityChanged(android.content.ComponentName componentName, boolean z, int i) {
            android.service.vr.VrListenerService.this.mHandler.obtainMessage(1, z ? 1 : 0, i, componentName).sendToTarget();
        }
    };
    private final android.os.Handler mHandler = new android.service.vr.VrListenerService.VrListenerHandler(android.os.Looper.getMainLooper());

    private final class VrListenerHandler extends android.os.Handler {
        public VrListenerHandler(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    android.service.vr.VrListenerService.this.onCurrentVrActivityChanged((android.content.ComponentName) message.obj, message.arg1 == 1, message.arg2);
                    break;
            }
        }
    }

    @Override // android.app.Service
    public android.os.IBinder onBind(android.content.Intent intent) {
        return this.mBinder;
    }

    public void onCurrentVrActivityChanged(android.content.ComponentName componentName) {
    }

    public void onCurrentVrActivityChanged(android.content.ComponentName componentName, boolean z, int i) {
        if (z) {
            componentName = null;
        }
        onCurrentVrActivityChanged(componentName);
    }

    public static final boolean isVrModePackageEnabled(android.content.Context context, android.content.ComponentName componentName) {
        android.app.ActivityManager activityManager = (android.app.ActivityManager) context.getSystemService(android.app.ActivityManager.class);
        if (activityManager == null) {
            return false;
        }
        return activityManager.isVrModePackageEnabled(componentName);
    }
}
