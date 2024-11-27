package android.service.media;

/* loaded from: classes3.dex */
public abstract class CameraPrewarmService extends android.app.Service {
    public static final java.lang.String ACTION_PREWARM = "android.service.media.CameraPrewarmService.ACTION_PREWARM";
    public static final int MSG_CAMERA_FIRED = 1;
    private boolean mCameraIntentFired;
    private final android.os.Handler mHandler = new android.os.Handler() { // from class: android.service.media.CameraPrewarmService.1
        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    android.service.media.CameraPrewarmService.this.mCameraIntentFired = true;
                    break;
                default:
                    super.handleMessage(message);
                    break;
            }
        }
    };

    public abstract void onCooldown(boolean z);

    public abstract void onPrewarm();

    @Override // android.app.Service
    public android.os.IBinder onBind(android.content.Intent intent) {
        if (ACTION_PREWARM.equals(intent.getAction())) {
            onPrewarm();
            return new android.os.Messenger(this.mHandler).getBinder();
        }
        return null;
    }

    @Override // android.app.Service
    public boolean onUnbind(android.content.Intent intent) {
        if (ACTION_PREWARM.equals(intent.getAction())) {
            onCooldown(this.mCameraIntentFired);
            return false;
        }
        return false;
    }
}
