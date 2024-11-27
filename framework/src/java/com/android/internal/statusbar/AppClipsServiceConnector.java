package com.android.internal.statusbar;

/* loaded from: classes5.dex */
public class AppClipsServiceConnector {
    private static final java.lang.String TAG = com.android.internal.statusbar.AppClipsServiceConnector.class.getSimpleName();
    private final android.content.Context mContext;
    private final android.os.Handler mHandler;

    public AppClipsServiceConnector(android.content.Context context) {
        this.mContext = context;
        android.os.HandlerThread handlerThread = new android.os.HandlerThread(TAG);
        handlerThread.start();
        this.mHandler = handlerThread.getThreadHandler();
    }

    public boolean canLaunchCaptureContentActivityForNote(int i) {
        try {
            java.util.concurrent.CompletableFuture<java.lang.Boolean> completableFuture = new java.util.concurrent.CompletableFuture<>();
            connectToServiceAndProcessRequest(i, completableFuture);
            return completableFuture.get().booleanValue();
        } catch (java.lang.Exception e) {
            android.util.Log.d(TAG, "Exception from service\n" + e);
            return false;
        }
    }

    private void connectToServiceAndProcessRequest(final int i, final java.util.concurrent.CompletableFuture<java.lang.Boolean> completableFuture) {
        android.content.ServiceConnection serviceConnection = new android.content.ServiceConnection() { // from class: com.android.internal.statusbar.AppClipsServiceConnector.1
            @Override // android.content.ServiceConnection
            public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
                try {
                    completableFuture.complete(java.lang.Boolean.valueOf(com.android.internal.statusbar.IAppClipsService.Stub.asInterface(iBinder).canLaunchCaptureContentActivityForNote(i)));
                } catch (java.lang.Exception e) {
                    android.util.Log.d(com.android.internal.statusbar.AppClipsServiceConnector.TAG, "Exception from service\n" + e);
                }
                completableFuture.complete(false);
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(android.content.ComponentName componentName) {
                if (!completableFuture.isDone()) {
                    completableFuture.complete(false);
                }
            }
        };
        android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(this.mContext.getResources().getString(com.android.internal.R.string.config_screenshotAppClipsServiceComponent));
        android.content.Intent intent = new android.content.Intent();
        intent.setComponent(unflattenFromString);
        if (!this.mContext.bindServiceAsUser(intent, serviceConnection, android.media.audio.Enums.AUDIO_FORMAT_AAC_MAIN, this.mHandler, this.mContext.getUser())) {
            completableFuture.complete(false);
        }
    }
}
