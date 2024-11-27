package com.android.server.wm;

/* loaded from: classes3.dex */
final class TaskFpsCallbackController {
    private final android.content.Context mContext;
    private final java.util.HashMap<android.os.IBinder, java.lang.Long> mTaskFpsCallbacks = new java.util.HashMap<>();
    private final java.util.HashMap<android.os.IBinder, android.os.IBinder.DeathRecipient> mDeathRecipients = new java.util.HashMap<>();

    private static native long nativeRegister(android.window.ITaskFpsCallback iTaskFpsCallback, int i);

    private static native void nativeUnregister(long j);

    TaskFpsCallbackController(android.content.Context context) {
        this.mContext = context;
    }

    void registerListener(int i, final android.window.ITaskFpsCallback iTaskFpsCallback) {
        if (iTaskFpsCallback == null) {
            return;
        }
        android.os.IBinder asBinder = iTaskFpsCallback.asBinder();
        if (this.mTaskFpsCallbacks.containsKey(asBinder)) {
            return;
        }
        this.mTaskFpsCallbacks.put(asBinder, java.lang.Long.valueOf(nativeRegister(iTaskFpsCallback, i)));
        android.os.IBinder.DeathRecipient deathRecipient = new android.os.IBinder.DeathRecipient() { // from class: com.android.server.wm.TaskFpsCallbackController$$ExternalSyntheticLambda0
            @Override // android.os.IBinder.DeathRecipient
            public final void binderDied() {
                com.android.server.wm.TaskFpsCallbackController.this.lambda$registerListener$0(iTaskFpsCallback);
            }
        };
        try {
            asBinder.linkToDeath(deathRecipient, 0);
            this.mDeathRecipients.put(asBinder, deathRecipient);
        } catch (android.os.RemoteException e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: unregisterListener, reason: merged with bridge method [inline-methods] */
    public void lambda$registerListener$0(android.window.ITaskFpsCallback iTaskFpsCallback) {
        if (iTaskFpsCallback == null) {
            return;
        }
        android.os.IBinder asBinder = iTaskFpsCallback.asBinder();
        if (!this.mTaskFpsCallbacks.containsKey(asBinder)) {
            return;
        }
        asBinder.unlinkToDeath(this.mDeathRecipients.get(asBinder), 0);
        this.mDeathRecipients.remove(asBinder);
        nativeUnregister(this.mTaskFpsCallbacks.get(asBinder).longValue());
        this.mTaskFpsCallbacks.remove(asBinder);
    }
}
