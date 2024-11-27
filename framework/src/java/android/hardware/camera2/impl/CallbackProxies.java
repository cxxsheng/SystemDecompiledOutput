package android.hardware.camera2.impl;

/* loaded from: classes.dex */
public class CallbackProxies {

    public static class SessionStateCallbackProxy extends android.hardware.camera2.CameraCaptureSession.StateCallback {
        private final android.hardware.camera2.CameraCaptureSession.StateCallback mCallback;
        private final java.util.concurrent.Executor mExecutor;

        public SessionStateCallbackProxy(java.util.concurrent.Executor executor, android.hardware.camera2.CameraCaptureSession.StateCallback stateCallback) {
            this.mExecutor = (java.util.concurrent.Executor) com.android.internal.util.Preconditions.checkNotNull(executor, "executor must not be null");
            this.mCallback = (android.hardware.camera2.CameraCaptureSession.StateCallback) com.android.internal.util.Preconditions.checkNotNull(stateCallback, "callback must not be null");
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onConfigured(final android.hardware.camera2.CameraCaptureSession cameraCaptureSession) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CallbackProxies$SessionStateCallbackProxy$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.hardware.camera2.impl.CallbackProxies.SessionStateCallbackProxy.this.lambda$onConfigured$0(cameraCaptureSession);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onConfigured$0(android.hardware.camera2.CameraCaptureSession cameraCaptureSession) {
            this.mCallback.onConfigured(cameraCaptureSession);
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onConfigureFailed(final android.hardware.camera2.CameraCaptureSession cameraCaptureSession) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CallbackProxies$SessionStateCallbackProxy$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.hardware.camera2.impl.CallbackProxies.SessionStateCallbackProxy.this.lambda$onConfigureFailed$1(cameraCaptureSession);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onConfigureFailed$1(android.hardware.camera2.CameraCaptureSession cameraCaptureSession) {
            this.mCallback.onConfigureFailed(cameraCaptureSession);
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onReady(final android.hardware.camera2.CameraCaptureSession cameraCaptureSession) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CallbackProxies$SessionStateCallbackProxy$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.hardware.camera2.impl.CallbackProxies.SessionStateCallbackProxy.this.lambda$onReady$2(cameraCaptureSession);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReady$2(android.hardware.camera2.CameraCaptureSession cameraCaptureSession) {
            this.mCallback.onReady(cameraCaptureSession);
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onActive(final android.hardware.camera2.CameraCaptureSession cameraCaptureSession) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CallbackProxies$SessionStateCallbackProxy$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.hardware.camera2.impl.CallbackProxies.SessionStateCallbackProxy.this.lambda$onActive$3(cameraCaptureSession);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onActive$3(android.hardware.camera2.CameraCaptureSession cameraCaptureSession) {
            this.mCallback.onActive(cameraCaptureSession);
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onCaptureQueueEmpty(final android.hardware.camera2.CameraCaptureSession cameraCaptureSession) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CallbackProxies$SessionStateCallbackProxy$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.hardware.camera2.impl.CallbackProxies.SessionStateCallbackProxy.this.lambda$onCaptureQueueEmpty$4(cameraCaptureSession);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureQueueEmpty$4(android.hardware.camera2.CameraCaptureSession cameraCaptureSession) {
            this.mCallback.onCaptureQueueEmpty(cameraCaptureSession);
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onClosed(final android.hardware.camera2.CameraCaptureSession cameraCaptureSession) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CallbackProxies$SessionStateCallbackProxy$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.hardware.camera2.impl.CallbackProxies.SessionStateCallbackProxy.this.lambda$onClosed$5(cameraCaptureSession);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onClosed$5(android.hardware.camera2.CameraCaptureSession cameraCaptureSession) {
            this.mCallback.onClosed(cameraCaptureSession);
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onSurfacePrepared(final android.hardware.camera2.CameraCaptureSession cameraCaptureSession, final android.view.Surface surface) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CallbackProxies$SessionStateCallbackProxy$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.hardware.camera2.impl.CallbackProxies.SessionStateCallbackProxy.this.lambda$onSurfacePrepared$6(cameraCaptureSession, surface);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSurfacePrepared$6(android.hardware.camera2.CameraCaptureSession cameraCaptureSession, android.view.Surface surface) {
            this.mCallback.onSurfacePrepared(cameraCaptureSession, surface);
        }
    }

    private CallbackProxies() {
        throw new java.lang.AssertionError();
    }
}
