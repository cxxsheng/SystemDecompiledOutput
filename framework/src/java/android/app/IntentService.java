package android.app;

@java.lang.Deprecated
/* loaded from: classes.dex */
public abstract class IntentService extends android.app.Service {
    private java.lang.String mName;
    private boolean mRedelivery;
    private volatile android.app.IntentService.ServiceHandler mServiceHandler;
    private volatile android.os.Looper mServiceLooper;

    protected abstract void onHandleIntent(android.content.Intent intent);

    private final class ServiceHandler extends android.os.Handler {
        public ServiceHandler(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            android.app.IntentService.this.onHandleIntent((android.content.Intent) message.obj);
            android.app.IntentService.this.stopSelf(message.arg1);
        }
    }

    public IntentService(java.lang.String str) {
        this.mName = str;
    }

    public void setIntentRedelivery(boolean z) {
        this.mRedelivery = z;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        android.os.HandlerThread handlerThread = new android.os.HandlerThread("IntentService[" + this.mName + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        handlerThread.start();
        this.mServiceLooper = handlerThread.getLooper();
        this.mServiceHandler = new android.app.IntentService.ServiceHandler(this.mServiceLooper);
    }

    @Override // android.app.Service
    public void onStart(android.content.Intent intent, int i) {
        android.os.Message obtainMessage = this.mServiceHandler.obtainMessage();
        obtainMessage.arg1 = i;
        obtainMessage.obj = intent;
        this.mServiceHandler.sendMessage(obtainMessage);
    }

    @Override // android.app.Service
    public int onStartCommand(android.content.Intent intent, int i, int i2) {
        onStart(intent, i2);
        return this.mRedelivery ? 3 : 2;
    }

    @Override // android.app.Service
    public void onDestroy() {
        this.mServiceLooper.quit();
    }

    @Override // android.app.Service
    public android.os.IBinder onBind(android.content.Intent intent) {
        return null;
    }
}
