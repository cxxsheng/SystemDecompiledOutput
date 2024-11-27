package com.android.internal.util;

/* loaded from: classes5.dex */
public abstract class AsyncService extends android.app.Service {
    public static final int CMD_ASYNC_SERVICE_DESTROY = 16777216;
    public static final int CMD_ASYNC_SERVICE_ON_START_INTENT = 16777215;
    protected static final boolean DBG = true;
    private static final java.lang.String TAG = "AsyncService";
    com.android.internal.util.AsyncService.AsyncServiceInfo mAsyncServiceInfo;
    android.os.Handler mHandler;
    protected android.os.Messenger mMessenger;

    public static final class AsyncServiceInfo {
        public android.os.Handler mHandler;
        public int mRestartFlags;
    }

    public abstract com.android.internal.util.AsyncService.AsyncServiceInfo createHandler();

    public android.os.Handler getHandler() {
        return this.mHandler;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.mAsyncServiceInfo = createHandler();
        this.mHandler = this.mAsyncServiceInfo.mHandler;
        this.mMessenger = new android.os.Messenger(this.mHandler);
    }

    @Override // android.app.Service
    public int onStartCommand(android.content.Intent intent, int i, int i2) {
        android.util.Log.d(TAG, "onStartCommand");
        android.os.Message obtainMessage = this.mHandler.obtainMessage();
        obtainMessage.what = 16777215;
        obtainMessage.arg1 = i;
        obtainMessage.arg2 = i2;
        obtainMessage.obj = intent;
        this.mHandler.sendMessage(obtainMessage);
        return this.mAsyncServiceInfo.mRestartFlags;
    }

    @Override // android.app.Service
    public void onDestroy() {
        android.util.Log.d(TAG, "onDestroy");
        android.os.Message obtainMessage = this.mHandler.obtainMessage();
        obtainMessage.what = 16777216;
        this.mHandler.sendMessage(obtainMessage);
    }

    @Override // android.app.Service
    public android.os.IBinder onBind(android.content.Intent intent) {
        return this.mMessenger.getBinder();
    }
}
