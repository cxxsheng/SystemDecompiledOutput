package android.app;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public class VrManager {
    private java.util.Map<android.app.VrStateCallback, android.app.VrManager.CallbackEntry> mCallbackMap = new android.util.ArrayMap();
    private final android.service.vr.IVrManager mService;

    /* JADX INFO: Access modifiers changed from: private */
    static class CallbackEntry {
        final android.app.VrStateCallback mCallback;
        final java.util.concurrent.Executor mExecutor;
        final android.service.vr.IVrStateCallbacks mStateCallback = new android.app.VrManager.CallbackEntry.AnonymousClass1();
        final android.service.vr.IPersistentVrStateCallbacks mPersistentStateCallback = new android.app.VrManager.CallbackEntry.AnonymousClass2();

        /* renamed from: android.app.VrManager$CallbackEntry$1, reason: invalid class name */
        class AnonymousClass1 extends android.service.vr.IVrStateCallbacks.Stub {
            AnonymousClass1() {
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onVrStateChanged$0(boolean z) {
                android.app.VrManager.CallbackEntry.this.mCallback.onVrStateChanged(z);
            }

            @Override // android.service.vr.IVrStateCallbacks
            public void onVrStateChanged(final boolean z) {
                android.app.VrManager.CallbackEntry.this.mExecutor.execute(new java.lang.Runnable() { // from class: android.app.VrManager$CallbackEntry$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.app.VrManager.CallbackEntry.AnonymousClass1.this.lambda$onVrStateChanged$0(z);
                    }
                });
            }
        }

        /* renamed from: android.app.VrManager$CallbackEntry$2, reason: invalid class name */
        class AnonymousClass2 extends android.service.vr.IPersistentVrStateCallbacks.Stub {
            AnonymousClass2() {
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onPersistentVrStateChanged$0(boolean z) {
                android.app.VrManager.CallbackEntry.this.mCallback.onPersistentVrStateChanged(z);
            }

            @Override // android.service.vr.IPersistentVrStateCallbacks
            public void onPersistentVrStateChanged(final boolean z) {
                android.app.VrManager.CallbackEntry.this.mExecutor.execute(new java.lang.Runnable() { // from class: android.app.VrManager$CallbackEntry$2$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.app.VrManager.CallbackEntry.AnonymousClass2.this.lambda$onPersistentVrStateChanged$0(z);
                    }
                });
            }
        }

        CallbackEntry(android.app.VrStateCallback vrStateCallback, java.util.concurrent.Executor executor) {
            this.mCallback = vrStateCallback;
            this.mExecutor = executor;
        }
    }

    public VrManager(android.service.vr.IVrManager iVrManager) {
        this.mService = iVrManager;
    }

    public void registerVrStateCallback(java.util.concurrent.Executor executor, android.app.VrStateCallback vrStateCallback) {
        if (vrStateCallback == null || this.mCallbackMap.containsKey(vrStateCallback)) {
            return;
        }
        android.app.VrManager.CallbackEntry callbackEntry = new android.app.VrManager.CallbackEntry(vrStateCallback, executor);
        this.mCallbackMap.put(vrStateCallback, callbackEntry);
        try {
            this.mService.registerListener(callbackEntry.mStateCallback);
            this.mService.registerPersistentVrStateListener(callbackEntry.mPersistentStateCallback);
        } catch (android.os.RemoteException e) {
            try {
                unregisterVrStateCallback(vrStateCallback);
            } catch (java.lang.Exception e2) {
                e.rethrowFromSystemServer();
            }
        }
    }

    public void unregisterVrStateCallback(android.app.VrStateCallback vrStateCallback) {
        android.app.VrManager.CallbackEntry remove = this.mCallbackMap.remove(vrStateCallback);
        if (remove != null) {
            try {
                this.mService.unregisterListener(remove.mStateCallback);
            } catch (android.os.RemoteException e) {
            }
            try {
                this.mService.unregisterPersistentVrStateListener(remove.mPersistentStateCallback);
            } catch (android.os.RemoteException e2) {
            }
        }
    }

    public boolean isVrModeEnabled() {
        try {
            return this.mService.getVrModeState();
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public boolean isPersistentVrModeEnabled() {
        try {
            return this.mService.getPersistentVrModeEnabled();
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public void setPersistentVrModeEnabled(boolean z) {
        try {
            this.mService.setPersistentVrModeEnabled(z);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void setVr2dDisplayProperties(android.app.Vr2dDisplayProperties vr2dDisplayProperties) {
        try {
            this.mService.setVr2dDisplayProperties(vr2dDisplayProperties);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void setAndBindVrCompositor(android.content.ComponentName componentName) {
        try {
            this.mService.setAndBindCompositor(componentName == null ? null : componentName.flattenToString());
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void setStandbyEnabled(boolean z) {
        try {
            this.mService.setStandbyEnabled(z);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void setVrInputMethod(android.content.ComponentName componentName) {
    }

    public int getVr2dDisplayId() {
        try {
            return this.mService.getVr2dDisplayId();
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
            return -1;
        }
    }
}
