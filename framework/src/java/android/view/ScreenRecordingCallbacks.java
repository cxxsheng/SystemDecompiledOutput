package android.view;

/* loaded from: classes4.dex */
public final class ScreenRecordingCallbacks {
    private static android.view.ScreenRecordingCallbacks sInstance;
    private static final java.lang.Object sLock = new java.lang.Object();
    private android.window.IScreenRecordingCallback mCallbackNotifier;
    private final android.util.ArrayMap<java.util.function.Consumer<java.lang.Integer>, java.util.concurrent.Executor> mCallbacks = new android.util.ArrayMap<>();
    private int mState = 0;

    private ScreenRecordingCallbacks() {
    }

    private static android.view.IWindowManager getWindowManagerService() {
        return (android.view.IWindowManager) java.util.Objects.requireNonNull(android.view.WindowManagerGlobal.getWindowManagerService());
    }

    static android.view.ScreenRecordingCallbacks getInstance() {
        android.view.ScreenRecordingCallbacks screenRecordingCallbacks;
        synchronized (sLock) {
            if (sInstance == null) {
                sInstance = new android.view.ScreenRecordingCallbacks();
            }
            screenRecordingCallbacks = sInstance;
        }
        return screenRecordingCallbacks;
    }

    int addCallback(java.util.concurrent.Executor executor, java.util.function.Consumer<java.lang.Integer> consumer) {
        int i;
        int i2;
        synchronized (sLock) {
            if (this.mCallbackNotifier == null) {
                this.mCallbackNotifier = new android.window.IScreenRecordingCallback.Stub() { // from class: android.view.ScreenRecordingCallbacks.1
                    @Override // android.window.IScreenRecordingCallback
                    public void onScreenRecordingStateChanged(boolean z) {
                        int i3;
                        if (z) {
                            i3 = 1;
                        } else {
                            i3 = 0;
                        }
                        android.view.ScreenRecordingCallbacks.this.notifyCallbacks(i3);
                    }
                };
                try {
                    if (getWindowManagerService().registerScreenRecordingCallback(this.mCallbackNotifier)) {
                        i2 = 1;
                    } else {
                        i2 = 0;
                    }
                    this.mState = i2;
                } catch (android.os.RemoteException e) {
                    e.rethrowFromSystemServer();
                }
            }
            this.mCallbacks.put(consumer, executor);
            i = this.mState;
        }
        return i;
    }

    void removeCallback(java.util.function.Consumer<java.lang.Integer> consumer) {
        synchronized (sLock) {
            this.mCallbacks.remove(consumer);
            if (this.mCallbacks.isEmpty()) {
                try {
                    getWindowManagerService().unregisterScreenRecordingCallback(this.mCallbackNotifier);
                } catch (android.os.RemoteException e) {
                    e.rethrowFromSystemServer();
                }
                this.mCallbackNotifier = null;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyCallbacks(final int i) {
        synchronized (sLock) {
            this.mState = i;
            if (this.mCallbacks.isEmpty()) {
                return;
            }
            java.util.ArrayList arrayList = new java.util.ArrayList();
            for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
                final java.util.function.Consumer<java.lang.Integer> keyAt = this.mCallbacks.keyAt(i2);
                final java.util.concurrent.Executor valueAt = this.mCallbacks.valueAt(i2);
                arrayList.add(new java.lang.Runnable() { // from class: android.view.ScreenRecordingCallbacks$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        valueAt.execute(new java.lang.Runnable() { // from class: android.view.ScreenRecordingCallbacks$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                r1.accept(java.lang.Integer.valueOf(r2));
                            }
                        });
                    }
                });
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            for (int i3 = 0; i3 < arrayList.size(); i3++) {
                try {
                    ((java.lang.Runnable) arrayList.get(i3)).run();
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }
    }
}
