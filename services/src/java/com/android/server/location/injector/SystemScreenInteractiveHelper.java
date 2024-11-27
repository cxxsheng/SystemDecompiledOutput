package com.android.server.location.injector;

/* loaded from: classes2.dex */
public class SystemScreenInteractiveHelper extends com.android.server.location.injector.ScreenInteractiveHelper {
    private final android.content.Context mContext;
    private volatile boolean mIsInteractive;
    private boolean mReady;

    public SystemScreenInteractiveHelper(android.content.Context context) {
        this.mContext = context;
    }

    public void onSystemReady() {
        if (this.mReady) {
            return;
        }
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        this.mContext.registerReceiverAsUser(new android.content.BroadcastReceiver() { // from class: com.android.server.location.injector.SystemScreenInteractiveHelper.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context, android.content.Intent intent) {
                boolean z;
                if ("android.intent.action.SCREEN_ON".equals(intent.getAction())) {
                    z = true;
                } else if ("android.intent.action.SCREEN_OFF".equals(intent.getAction())) {
                    z = false;
                } else {
                    return;
                }
                com.android.server.location.injector.SystemScreenInteractiveHelper.this.onScreenInteractiveChanged(z);
            }
        }, android.os.UserHandle.ALL, intentFilter, null, com.android.server.FgThread.getHandler());
        this.mReady = true;
    }

    void onScreenInteractiveChanged(boolean z) {
        if (z == this.mIsInteractive) {
            return;
        }
        this.mIsInteractive = z;
        notifyScreenInteractiveChanged(z);
    }

    @Override // com.android.server.location.injector.ScreenInteractiveHelper
    public boolean isInteractive() {
        com.android.internal.util.Preconditions.checkState(this.mReady);
        return this.mIsInteractive;
    }
}
