package android.service.voice;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public class VisualQueryDetector {
    private static final boolean DEBUG = false;
    private static final int SETTINGS_DISABLE_BIT = 0;
    private static final int SETTINGS_ENABLE_BIT = 1;
    private static final java.lang.String TAG = android.service.voice.VisualQueryDetector.class.getSimpleName();
    private final java.lang.String mAttributionTag;
    private final android.service.voice.VisualQueryDetector.Callback mCallback;
    private final android.content.Context mContext;
    private final java.util.concurrent.Executor mExecutor;
    private final com.android.internal.app.IVoiceInteractionManagerService mManagerService;
    private android.service.voice.VisualQueryDetector.AccessibilityDetectionEnabledListenerWrapper mActiveAccessibilityListenerWrapper = null;
    private final android.service.voice.VisualQueryDetector.VisualQueryDetectorInitializationDelegate mInitializationDelegate = new android.service.voice.VisualQueryDetector.VisualQueryDetectorInitializationDelegate();

    VisualQueryDetector(com.android.internal.app.IVoiceInteractionManagerService iVoiceInteractionManagerService, java.util.concurrent.Executor executor, android.service.voice.VisualQueryDetector.Callback callback, android.content.Context context, java.lang.String str) {
        this.mManagerService = iVoiceInteractionManagerService;
        this.mCallback = callback;
        this.mExecutor = executor;
        this.mContext = context;
        this.mAttributionTag = str;
    }

    void initialize(android.os.PersistableBundle persistableBundle, android.os.SharedMemory sharedMemory) {
        this.mInitializationDelegate.initialize(persistableBundle, sharedMemory);
    }

    public void updateState(android.os.PersistableBundle persistableBundle, android.os.SharedMemory sharedMemory) {
        synchronized (this.mInitializationDelegate.getLock()) {
            this.mInitializationDelegate.updateState(persistableBundle, sharedMemory);
        }
    }

    public boolean startRecognition() {
        synchronized (this.mInitializationDelegate.getLock()) {
            this.mInitializationDelegate.startRecognition();
            try {
                this.mManagerService.startPerceiving(new android.service.voice.VisualQueryDetector.BinderCallback(this.mExecutor, this.mCallback, this.mInitializationDelegate.getLock()));
            } catch (android.os.RemoteException e) {
                e.rethrowFromSystemServer();
            } catch (java.lang.SecurityException e2) {
                android.util.Slog.e(TAG, "startRecognition failed: " + e2);
                return false;
            }
        }
        return true;
    }

    public boolean stopRecognition() {
        synchronized (this.mInitializationDelegate.getLock()) {
            this.mInitializationDelegate.stopRecognition();
            try {
                this.mManagerService.stopPerceiving();
            } catch (android.os.RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }
        return true;
    }

    public void destroy() {
        synchronized (this.mInitializationDelegate.getLock()) {
            this.mInitializationDelegate.destroy();
        }
    }

    @android.annotation.SystemApi
    public boolean isAccessibilityDetectionEnabled() {
        boolean accessibilityDetectionEnabled;
        android.util.Slog.d(TAG, "Fetching accessibility setting");
        synchronized (this.mInitializationDelegate.getLock()) {
            try {
                try {
                    accessibilityDetectionEnabled = this.mManagerService.getAccessibilityDetectionEnabled();
                } catch (android.os.RemoteException e) {
                    e.rethrowFromSystemServer();
                    return false;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return accessibilityDetectionEnabled;
    }

    @android.annotation.SystemApi
    public void setAccessibilityDetectionEnabledListener(java.util.function.Consumer<java.lang.Boolean> consumer) {
        android.util.Slog.d(TAG, "Registering Accessibility settings listener.");
        synchronized (this.mInitializationDelegate.getLock()) {
            try {
            } catch (android.os.RemoteException e) {
                e.rethrowFromSystemServer();
            }
            if (this.mActiveAccessibilityListenerWrapper != null) {
                android.util.Slog.e(TAG, "Fail to register accessibility setting listener: already registered and not unregistered.");
                throw new java.lang.IllegalStateException("Cannot register listener with listeners already set.");
            }
            this.mActiveAccessibilityListenerWrapper = new android.service.voice.VisualQueryDetector.AccessibilityDetectionEnabledListenerWrapper(consumer);
            this.mManagerService.registerAccessibilityDetectionSettingsListener(this.mActiveAccessibilityListenerWrapper);
        }
    }

    @android.annotation.SystemApi
    public void clearAccessibilityDetectionEnabledListener() {
        android.util.Slog.d(TAG, "Unregistering Accessibility settings listener.");
        synchronized (this.mInitializationDelegate.getLock()) {
            try {
            } catch (android.os.RemoteException e) {
                e.rethrowFromSystemServer();
            }
            if (this.mActiveAccessibilityListenerWrapper == null) {
                android.util.Slog.e(TAG, "Not able to remove the listener: listener does not exist.");
                throw new java.lang.IllegalStateException("Cannot clear listener since it is not set.");
            }
            this.mManagerService.unregisterAccessibilityDetectionSettingsListener(this.mActiveAccessibilityListenerWrapper);
            this.mActiveAccessibilityListenerWrapper = null;
        }
    }

    private final class AccessibilityDetectionEnabledListenerWrapper extends com.android.internal.app.IVoiceInteractionAccessibilitySettingsListener.Stub {
        private java.util.function.Consumer<java.lang.Boolean> mListener;

        AccessibilityDetectionEnabledListenerWrapper(java.util.function.Consumer<java.lang.Boolean> consumer) {
            this.mListener = consumer;
        }

        @Override // com.android.internal.app.IVoiceInteractionAccessibilitySettingsListener
        public void onAccessibilityDetectionChanged(boolean z) {
            this.mListener.accept(java.lang.Boolean.valueOf(z));
        }
    }

    public void dump(java.lang.String str, java.io.PrintWriter printWriter) {
        synchronized (this.mInitializationDelegate.getLock()) {
            this.mInitializationDelegate.dump(str, printWriter);
        }
    }

    public android.service.voice.HotwordDetector getInitializationDelegate() {
        return this.mInitializationDelegate;
    }

    void registerOnDestroyListener(java.util.function.Consumer<android.service.voice.AbstractDetector> consumer) {
        synchronized (this.mInitializationDelegate.getLock()) {
            this.mInitializationDelegate.registerOnDestroyListener(consumer);
        }
    }

    public interface Callback {
        void onFailure(android.service.voice.VisualQueryDetectionServiceFailure visualQueryDetectionServiceFailure);

        void onQueryDetected(java.lang.String str);

        void onQueryFinished();

        void onQueryRejected();

        void onUnknownFailure(java.lang.String str);

        void onVisualQueryDetectionServiceInitialized(int i);

        void onVisualQueryDetectionServiceRestarted();

        default void onQueryDetected(android.service.voice.VisualQueryDetectedResult visualQueryDetectedResult) {
            throw new java.lang.UnsupportedOperationException("This emthod must be implemented for use.");
        }
    }

    private class VisualQueryDetectorInitializationDelegate extends android.service.voice.AbstractDetector {
        VisualQueryDetectorInitializationDelegate() {
            super(android.service.voice.VisualQueryDetector.this.mManagerService, android.service.voice.VisualQueryDetector.this.mExecutor, null);
        }

        @Override // android.service.voice.AbstractDetector
        void initialize(android.os.PersistableBundle persistableBundle, android.os.SharedMemory sharedMemory) {
            initAndVerifyDetector(persistableBundle, sharedMemory, new android.service.voice.VisualQueryDetector.InitializationStateListener(android.service.voice.VisualQueryDetector.this.mExecutor, android.service.voice.VisualQueryDetector.this.mCallback, android.service.voice.VisualQueryDetector.this.mContext), 3, android.service.voice.VisualQueryDetector.this.mAttributionTag);
        }

        @Override // android.service.voice.HotwordDetector
        public boolean stopRecognition() {
            throwIfDetectorIsNoLongerActive();
            return true;
        }

        @Override // android.service.voice.HotwordDetector
        public boolean startRecognition() {
            throwIfDetectorIsNoLongerActive();
            return true;
        }

        @Override // android.service.voice.AbstractDetector, android.service.voice.HotwordDetector
        public final boolean startRecognition(android.os.ParcelFileDescriptor parcelFileDescriptor, android.media.AudioFormat audioFormat, android.os.PersistableBundle persistableBundle) {
            return false;
        }

        @Override // android.service.voice.HotwordDetector
        public boolean isUsingSandboxedDetectionService() {
            return true;
        }

        @Override // android.service.voice.HotwordDetector
        public void dump(java.lang.String str, java.io.PrintWriter printWriter) {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public java.lang.Object getLock() {
            return this.mLock;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class BinderCallback extends android.service.voice.IVisualQueryDetectionVoiceInteractionCallback.Stub {
        private final android.service.voice.VisualQueryDetector.Callback mCallback;
        private final java.util.concurrent.Executor mExecutor;
        private final java.lang.Object mLock;

        BinderCallback(java.util.concurrent.Executor executor, android.service.voice.VisualQueryDetector.Callback callback, java.lang.Object obj) {
            this.mExecutor = executor;
            this.mCallback = callback;
            this.mLock = obj;
        }

        @Override // android.service.voice.IVisualQueryDetectionVoiceInteractionCallback
        public void onQueryDetected(final java.lang.String str) {
            android.util.Slog.v(android.service.voice.VisualQueryDetector.TAG, "BinderCallback#onQueryDetected");
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.service.voice.VisualQueryDetector$BinderCallback$$ExternalSyntheticLambda4
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.service.voice.VisualQueryDetector.BinderCallback.this.lambda$onQueryDetected$1(str);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onQueryDetected$1(final java.lang.String str) throws java.lang.Exception {
            synchronized (this.mLock) {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.service.voice.VisualQueryDetector$BinderCallback$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.service.voice.VisualQueryDetector.BinderCallback.this.lambda$onQueryDetected$0(str);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onQueryDetected$0(java.lang.String str) {
            this.mCallback.onQueryDetected(str);
        }

        @Override // android.service.voice.IVisualQueryDetectionVoiceInteractionCallback
        public void onResultDetected(final android.service.voice.VisualQueryDetectedResult visualQueryDetectedResult) {
            android.util.Slog.v(android.service.voice.VisualQueryDetector.TAG, "BinderCallback#onResultDetected");
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.service.voice.VisualQueryDetector$BinderCallback$$ExternalSyntheticLambda3
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.service.voice.VisualQueryDetector.BinderCallback.this.lambda$onResultDetected$2(visualQueryDetectedResult);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onResultDetected$2(android.service.voice.VisualQueryDetectedResult visualQueryDetectedResult) throws java.lang.Exception {
            synchronized (this.mLock) {
                this.mCallback.onQueryDetected(visualQueryDetectedResult);
            }
        }

        @Override // android.service.voice.IVisualQueryDetectionVoiceInteractionCallback
        public void onQueryFinished() {
            android.util.Slog.v(android.service.voice.VisualQueryDetector.TAG, "BinderCallback#onQueryFinished");
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.service.voice.VisualQueryDetector$BinderCallback$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.service.voice.VisualQueryDetector.BinderCallback.this.lambda$onQueryFinished$4();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onQueryFinished$4() throws java.lang.Exception {
            synchronized (this.mLock) {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.service.voice.VisualQueryDetector$BinderCallback$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.service.voice.VisualQueryDetector.BinderCallback.this.lambda$onQueryFinished$3();
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onQueryFinished$3() {
            this.mCallback.onQueryFinished();
        }

        @Override // android.service.voice.IVisualQueryDetectionVoiceInteractionCallback
        public void onQueryRejected() {
            android.util.Slog.v(android.service.voice.VisualQueryDetector.TAG, "BinderCallback#onQueryRejected");
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.service.voice.VisualQueryDetector$BinderCallback$$ExternalSyntheticLambda7
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.service.voice.VisualQueryDetector.BinderCallback.this.lambda$onQueryRejected$6();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onQueryRejected$6() throws java.lang.Exception {
            synchronized (this.mLock) {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.service.voice.VisualQueryDetector$BinderCallback$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.service.voice.VisualQueryDetector.BinderCallback.this.lambda$onQueryRejected$5();
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onQueryRejected$5() {
            this.mCallback.onQueryRejected();
        }

        @Override // android.service.voice.IVisualQueryDetectionVoiceInteractionCallback
        public void onVisualQueryDetectionServiceFailure(final android.service.voice.VisualQueryDetectionServiceFailure visualQueryDetectionServiceFailure) {
            android.util.Slog.v(android.service.voice.VisualQueryDetector.TAG, "BinderCallback#onVisualQueryDetectionServiceFailure: " + visualQueryDetectionServiceFailure);
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.service.voice.VisualQueryDetector$BinderCallback$$ExternalSyntheticLambda1
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.service.voice.VisualQueryDetector.BinderCallback.this.lambda$onVisualQueryDetectionServiceFailure$8(visualQueryDetectionServiceFailure);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onVisualQueryDetectionServiceFailure$8(final android.service.voice.VisualQueryDetectionServiceFailure visualQueryDetectionServiceFailure) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.service.voice.VisualQueryDetector$BinderCallback$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.voice.VisualQueryDetector.BinderCallback.this.lambda$onVisualQueryDetectionServiceFailure$7(visualQueryDetectionServiceFailure);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onVisualQueryDetectionServiceFailure$7(android.service.voice.VisualQueryDetectionServiceFailure visualQueryDetectionServiceFailure) {
            if (visualQueryDetectionServiceFailure != null) {
                this.mCallback.onFailure(visualQueryDetectionServiceFailure);
            } else {
                this.mCallback.onUnknownFailure("Error data is null");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class InitializationStateListener extends com.android.internal.app.IHotwordRecognitionStatusCallback.Stub {
        private final android.service.voice.VisualQueryDetector.Callback mCallback;
        private final android.content.Context mContext;
        private final java.util.concurrent.Executor mExecutor;

        InitializationStateListener(java.util.concurrent.Executor executor, android.service.voice.VisualQueryDetector.Callback callback, android.content.Context context) {
            this.mExecutor = executor;
            this.mCallback = callback;
            this.mContext = context;
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onKeyphraseDetected(android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent, android.service.voice.HotwordDetectedResult hotwordDetectedResult) {
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onKeyphraseDetectedFromExternalSource(android.service.voice.HotwordDetectedResult hotwordDetectedResult) {
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onGenericSoundTriggerDetected(android.hardware.soundtrigger.SoundTrigger.GenericRecognitionEvent genericRecognitionEvent) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onRejected(android.service.voice.HotwordRejectedResult hotwordRejectedResult) throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onRecognitionPaused() throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onRecognitionResumed() throws android.os.RemoteException {
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onStatusReported(final int i) {
            android.util.Slog.v(android.service.voice.VisualQueryDetector.TAG, "onStatusReported");
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.service.voice.VisualQueryDetector$InitializationStateListener$$ExternalSyntheticLambda7
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.service.voice.VisualQueryDetector.InitializationStateListener.this.lambda$onStatusReported$1(i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onStatusReported$1(final int i) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.service.voice.VisualQueryDetector$InitializationStateListener$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.voice.VisualQueryDetector.InitializationStateListener.this.lambda$onStatusReported$0(i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onStatusReported$0(int i) {
            this.mCallback.onVisualQueryDetectionServiceInitialized(i);
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onProcessRestarted() throws android.os.RemoteException {
            android.util.Slog.v(android.service.voice.VisualQueryDetector.TAG, "onProcessRestarted()");
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.service.voice.VisualQueryDetector$InitializationStateListener$$ExternalSyntheticLambda9
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.service.voice.VisualQueryDetector.InitializationStateListener.this.lambda$onProcessRestarted$3();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onProcessRestarted$3() throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.service.voice.VisualQueryDetector$InitializationStateListener$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.voice.VisualQueryDetector.InitializationStateListener.this.lambda$onProcessRestarted$2();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onProcessRestarted$2() {
            this.mCallback.onVisualQueryDetectionServiceRestarted();
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onHotwordDetectionServiceFailure(android.service.voice.HotwordDetectionServiceFailure hotwordDetectionServiceFailure) throws android.os.RemoteException {
            android.util.Slog.w(android.service.voice.VisualQueryDetector.TAG, "onHotwordDetectionServiceFailure: " + hotwordDetectionServiceFailure);
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onVisualQueryDetectionServiceFailure(final android.service.voice.VisualQueryDetectionServiceFailure visualQueryDetectionServiceFailure) throws android.os.RemoteException {
            android.util.Slog.v(android.service.voice.VisualQueryDetector.TAG, "onVisualQueryDetectionServiceFailure: " + visualQueryDetectionServiceFailure);
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.service.voice.VisualQueryDetector$InitializationStateListener$$ExternalSyntheticLambda2
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.service.voice.VisualQueryDetector.InitializationStateListener.this.lambda$onVisualQueryDetectionServiceFailure$5(visualQueryDetectionServiceFailure);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onVisualQueryDetectionServiceFailure$5(final android.service.voice.VisualQueryDetectionServiceFailure visualQueryDetectionServiceFailure) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.service.voice.VisualQueryDetector$InitializationStateListener$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.voice.VisualQueryDetector.InitializationStateListener.this.lambda$onVisualQueryDetectionServiceFailure$4(visualQueryDetectionServiceFailure);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onVisualQueryDetectionServiceFailure$4(android.service.voice.VisualQueryDetectionServiceFailure visualQueryDetectionServiceFailure) {
            if (visualQueryDetectionServiceFailure != null) {
                this.mCallback.onFailure(visualQueryDetectionServiceFailure);
            } else {
                this.mCallback.onUnknownFailure("Error data is null");
            }
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onSoundTriggerFailure(android.service.voice.SoundTriggerFailure soundTriggerFailure) {
            android.util.Slog.wtf(android.service.voice.VisualQueryDetector.TAG, "Unexpected STFailure in VisualQueryDetector" + soundTriggerFailure);
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onUnknownFailure(final java.lang.String str) throws android.os.RemoteException {
            android.util.Slog.v(android.service.voice.VisualQueryDetector.TAG, "onUnknownFailure: " + str);
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.service.voice.VisualQueryDetector$InitializationStateListener$$ExternalSyntheticLambda4
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.service.voice.VisualQueryDetector.InitializationStateListener.this.lambda$onUnknownFailure$7(str);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onUnknownFailure$7(final java.lang.String str) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.service.voice.VisualQueryDetector$InitializationStateListener$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.voice.VisualQueryDetector.InitializationStateListener.this.lambda$onUnknownFailure$6(str);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onUnknownFailure$6(java.lang.String str) {
            android.service.voice.VisualQueryDetector.Callback callback = this.mCallback;
            if (android.text.TextUtils.isEmpty(str)) {
                str = "Error data is null";
            }
            callback.onUnknownFailure(str);
        }

        @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
        public void onOpenFile(final java.lang.String str, final com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
            android.util.Slog.v(android.service.voice.VisualQueryDetector.TAG, "BinderCallback#onOpenFile " + str);
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.service.voice.VisualQueryDetector$InitializationStateListener$$ExternalSyntheticLambda8
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.service.voice.VisualQueryDetector.InitializationStateListener.this.lambda$onOpenFile$9(str, androidFuture);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onOpenFile$9(final java.lang.String str, final com.android.internal.infra.AndroidFuture androidFuture) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.service.voice.VisualQueryDetector$InitializationStateListener$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.voice.VisualQueryDetector.InitializationStateListener.this.lambda$onOpenFile$8(str, androidFuture);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onOpenFile$8(java.lang.String str, com.android.internal.infra.AndroidFuture androidFuture) {
            android.util.Slog.v(android.service.voice.VisualQueryDetector.TAG, "onOpenFile: " + str + "under internal app storage.");
            android.os.ParcelFileDescriptor parcelFileDescriptor = null;
            try {
                try {
                    parcelFileDescriptor = android.os.ParcelFileDescriptor.open(new java.io.File(this.mContext.getFilesDir(), str), 268435456);
                    android.util.Slog.d(android.service.voice.VisualQueryDetector.TAG, "Successfully opened a file with ParcelFileDescriptor.");
                } catch (java.io.FileNotFoundException e) {
                    android.util.Slog.e(android.service.voice.VisualQueryDetector.TAG, "Cannot open file. No ParcelFileDescriptor returned.");
                }
            } finally {
                androidFuture.complete(parcelFileDescriptor);
            }
        }
    }
}
