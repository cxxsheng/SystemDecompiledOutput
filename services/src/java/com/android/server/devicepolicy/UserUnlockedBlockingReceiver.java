package com.android.server.devicepolicy;

/* loaded from: classes.dex */
class UserUnlockedBlockingReceiver extends android.content.BroadcastReceiver {
    private static final int WAIT_FOR_USER_UNLOCKED_TIMEOUT_SECONDS = 120;
    private final java.util.concurrent.Semaphore mSemaphore = new java.util.concurrent.Semaphore(0);
    private final int mUserId;

    UserUnlockedBlockingReceiver(int i) {
        this.mUserId = i;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(android.content.Context context, android.content.Intent intent) {
        if ("android.intent.action.USER_UNLOCKED".equals(intent.getAction()) && intent.getIntExtra("android.intent.extra.user_handle", com.android.server.am.ProcessList.INVALID_ADJ) == this.mUserId) {
            this.mSemaphore.release();
        }
    }

    public boolean waitForUserUnlocked() {
        try {
            return this.mSemaphore.tryAcquire(120L, java.util.concurrent.TimeUnit.SECONDS);
        } catch (java.lang.InterruptedException e) {
            return false;
        }
    }
}
