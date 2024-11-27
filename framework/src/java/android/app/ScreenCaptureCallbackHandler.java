package android.app;

/* loaded from: classes.dex */
public class ScreenCaptureCallbackHandler {
    private final android.os.IBinder mActivityToken;
    private final android.util.ArrayMap<android.app.Activity.ScreenCaptureCallback, android.app.ScreenCaptureCallbackHandler.ScreenCaptureRegistration> mScreenCaptureRegistrations = new android.util.ArrayMap<>();
    private final android.app.ScreenCaptureCallbackHandler.ScreenCaptureObserver mObserver = new android.app.ScreenCaptureCallbackHandler.ScreenCaptureObserver(this.mScreenCaptureRegistrations);

    public ScreenCaptureCallbackHandler(android.os.IBinder iBinder) {
        this.mActivityToken = iBinder;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ScreenCaptureRegistration {
        android.app.Activity.ScreenCaptureCallback mCallback;
        java.util.concurrent.Executor mExecutor;

        ScreenCaptureRegistration(java.util.concurrent.Executor executor, android.app.Activity.ScreenCaptureCallback screenCaptureCallback) {
            this.mExecutor = executor;
            this.mCallback = screenCaptureCallback;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ScreenCaptureObserver extends android.app.IScreenCaptureObserver.Stub {
        android.util.ArrayMap<android.app.Activity.ScreenCaptureCallback, android.app.ScreenCaptureCallbackHandler.ScreenCaptureRegistration> mRegistrations;

        ScreenCaptureObserver(android.util.ArrayMap<android.app.Activity.ScreenCaptureCallback, android.app.ScreenCaptureCallbackHandler.ScreenCaptureRegistration> arrayMap) {
            this.mRegistrations = arrayMap;
        }

        @Override // android.app.IScreenCaptureObserver
        public void onScreenCaptured() {
            for (final android.app.ScreenCaptureCallbackHandler.ScreenCaptureRegistration screenCaptureRegistration : this.mRegistrations.values()) {
                screenCaptureRegistration.mExecutor.execute(new java.lang.Runnable() { // from class: android.app.ScreenCaptureCallbackHandler$ScreenCaptureObserver$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.app.ScreenCaptureCallbackHandler.ScreenCaptureRegistration.this.mCallback.onScreenCaptured();
                    }
                });
            }
        }
    }

    public void registerScreenCaptureCallback(java.util.concurrent.Executor executor, android.app.Activity.ScreenCaptureCallback screenCaptureCallback) {
        android.app.ScreenCaptureCallbackHandler.ScreenCaptureRegistration screenCaptureRegistration = new android.app.ScreenCaptureCallbackHandler.ScreenCaptureRegistration(executor, screenCaptureCallback);
        synchronized (this.mScreenCaptureRegistrations) {
            if (this.mScreenCaptureRegistrations.containsKey(screenCaptureCallback)) {
                throw new java.lang.IllegalStateException("Capture observer already registered with the activity");
            }
            this.mScreenCaptureRegistrations.put(screenCaptureCallback, screenCaptureRegistration);
            if (this.mScreenCaptureRegistrations.size() == 1) {
                try {
                    android.app.ActivityTaskManager.getService().registerScreenCaptureObserver(this.mActivityToken, this.mObserver);
                } catch (android.os.RemoteException e) {
                    e.rethrowFromSystemServer();
                }
            }
        }
    }

    public void unregisterScreenCaptureCallback(android.app.Activity.ScreenCaptureCallback screenCaptureCallback) {
        synchronized (this.mScreenCaptureRegistrations) {
            if (!this.mScreenCaptureRegistrations.containsKey(screenCaptureCallback)) {
                throw new java.lang.IllegalStateException("Capture observer not registered with the activity");
            }
            this.mScreenCaptureRegistrations.remove(screenCaptureCallback);
            if (this.mScreenCaptureRegistrations.size() == 0) {
                try {
                    android.app.ActivityTaskManager.getService().unregisterScreenCaptureObserver(this.mActivityToken, this.mObserver);
                } catch (android.os.RemoteException e) {
                    e.rethrowFromSystemServer();
                }
            }
        }
    }
}
