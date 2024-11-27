package com.android.internal.util;

/* loaded from: classes5.dex */
public class ScreenshotHelper {
    public static final int SCREENSHOT_MSG_PROCESS_COMPLETE = 2;
    public static final int SCREENSHOT_MSG_URI = 1;
    private static final java.lang.String TAG = "ScreenshotHelper";
    private final android.content.Context mContext;
    private final int SCREENSHOT_TIMEOUT_MS = 10000;
    private final java.lang.Object mScreenshotLock = new java.lang.Object();
    private android.os.IBinder mScreenshotService = null;
    private android.content.ServiceConnection mScreenshotConnection = null;
    private final android.content.BroadcastReceiver mBroadcastReceiver = new android.content.BroadcastReceiver() { // from class: com.android.internal.util.ScreenshotHelper.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            synchronized (com.android.internal.util.ScreenshotHelper.this.mScreenshotLock) {
                if (android.content.Intent.ACTION_USER_SWITCHED.equals(intent.getAction())) {
                    com.android.internal.util.ScreenshotHelper.this.resetConnection();
                }
            }
        }
    };

    public ScreenshotHelper(android.content.Context context) {
        this.mContext = context;
        this.mContext.registerReceiver(this.mBroadcastReceiver, new android.content.IntentFilter(android.content.Intent.ACTION_USER_SWITCHED), 2);
    }

    public void takeScreenshot(int i, android.os.Handler handler, java.util.function.Consumer<android.net.Uri> consumer) {
        takeScreenshot(1, i, handler, consumer);
    }

    public void takeScreenshot(int i, int i2, android.os.Handler handler, java.util.function.Consumer<android.net.Uri> consumer) {
        takeScreenshot(new com.android.internal.util.ScreenshotRequest.Builder(i, i2).build(), handler, consumer);
    }

    public void takeScreenshot(com.android.internal.util.ScreenshotRequest screenshotRequest, android.os.Handler handler, java.util.function.Consumer<android.net.Uri> consumer) {
        takeScreenshotInternal(screenshotRequest, handler, consumer, android.app.job.JobInfo.MIN_BACKOFF_MILLIS);
    }

    public void takeScreenshotInternal(com.android.internal.util.ScreenshotRequest screenshotRequest, final android.os.Handler handler, final java.util.function.Consumer<android.net.Uri> consumer, long j) {
        synchronized (this.mScreenshotLock) {
            final java.lang.Runnable runnable = new java.lang.Runnable() { // from class: com.android.internal.util.ScreenshotHelper$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.internal.util.ScreenshotHelper.this.lambda$takeScreenshotInternal$0(consumer);
                }
            };
            final android.os.Message obtain = android.os.Message.obtain(null, 0, screenshotRequest);
            obtain.replyTo = new android.os.Messenger(new android.os.Handler(handler.getLooper()) { // from class: com.android.internal.util.ScreenshotHelper.2
                @Override // android.os.Handler
                public void handleMessage(android.os.Message message) {
                    switch (message.what) {
                        case 1:
                            if (consumer != null) {
                                consumer.accept((android.net.Uri) message.obj);
                            }
                            handler.removeCallbacks(runnable);
                            return;
                        case 2:
                            synchronized (com.android.internal.util.ScreenshotHelper.this.mScreenshotLock) {
                                com.android.internal.util.ScreenshotHelper.this.resetConnection();
                            }
                            return;
                        default:
                            return;
                    }
                }
            });
            if (this.mScreenshotConnection != null && this.mScreenshotService != null) {
                try {
                    new android.os.Messenger(this.mScreenshotService).send(obtain);
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(TAG, "Couldn't take screenshot: " + e);
                    if (consumer != null) {
                        consumer.accept(null);
                    }
                }
                handler.postDelayed(runnable, j);
            }
            if (this.mScreenshotConnection != null) {
                resetConnection();
            }
            android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(this.mContext.getResources().getString(com.android.internal.R.string.config_screenshotServiceComponent));
            android.content.Intent intent = new android.content.Intent();
            intent.setComponent(unflattenFromString);
            android.content.ServiceConnection serviceConnection = new android.content.ServiceConnection() { // from class: com.android.internal.util.ScreenshotHelper.3
                @Override // android.content.ServiceConnection
                public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
                    synchronized (com.android.internal.util.ScreenshotHelper.this.mScreenshotLock) {
                        if (com.android.internal.util.ScreenshotHelper.this.mScreenshotConnection != this) {
                            return;
                        }
                        com.android.internal.util.ScreenshotHelper.this.mScreenshotService = iBinder;
                        try {
                            new android.os.Messenger(com.android.internal.util.ScreenshotHelper.this.mScreenshotService).send(obtain);
                        } catch (android.os.RemoteException e2) {
                            android.util.Log.e(com.android.internal.util.ScreenshotHelper.TAG, "Couldn't take screenshot: " + e2);
                            if (consumer != null) {
                                consumer.accept(null);
                            }
                        }
                    }
                }

                @Override // android.content.ServiceConnection
                public void onServiceDisconnected(android.content.ComponentName componentName) {
                    synchronized (com.android.internal.util.ScreenshotHelper.this.mScreenshotLock) {
                        if (com.android.internal.util.ScreenshotHelper.this.mScreenshotConnection != null) {
                            com.android.internal.util.ScreenshotHelper.this.resetConnection();
                            if (handler.hasCallbacks(runnable)) {
                                android.util.Log.e(com.android.internal.util.ScreenshotHelper.TAG, "Screenshot service disconnected");
                                handler.removeCallbacks(runnable);
                                com.android.internal.util.ScreenshotHelper.this.notifyScreenshotError();
                            }
                        }
                    }
                }
            };
            if (this.mContext.bindServiceAsUser(intent, serviceConnection, android.media.audio.Enums.AUDIO_FORMAT_AAC_MAIN, android.os.UserHandle.CURRENT)) {
                this.mScreenshotConnection = serviceConnection;
                handler.postDelayed(runnable, j);
            } else {
                this.mContext.unbindService(serviceConnection);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$takeScreenshotInternal$0(java.util.function.Consumer consumer) {
        synchronized (this.mScreenshotLock) {
            if (this.mScreenshotConnection != null) {
                android.util.Log.e(TAG, "Timed out before getting screenshot capture response");
                resetConnection();
                notifyScreenshotError();
            }
        }
        if (consumer != null) {
            consumer.accept(null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetConnection() {
        if (this.mScreenshotConnection != null) {
            this.mContext.unbindService(this.mScreenshotConnection);
            this.mScreenshotConnection = null;
            this.mScreenshotService = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyScreenshotError() {
        android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(this.mContext.getResources().getString(com.android.internal.R.string.config_screenshotErrorReceiverComponent));
        android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_USER_PRESENT);
        intent.setComponent(unflattenFromString);
        intent.addFlags(335544320);
        this.mContext.sendBroadcastAsUser(intent, android.os.UserHandle.CURRENT);
    }
}
