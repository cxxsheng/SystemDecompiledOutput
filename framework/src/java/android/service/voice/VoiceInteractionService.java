package android.service.voice;

/* loaded from: classes3.dex */
public class VoiceInteractionService extends android.app.Service {
    static final long MULTIPLE_ACTIVE_HOTWORD_DETECTORS = 193232191;
    public static final java.lang.String SERVICE_INTERFACE = "android.service.voice.VoiceInteractionService";
    public static final java.lang.String SERVICE_META_DATA = "android.voice_interaction";
    private android.service.voice.VisualQueryDetector mActiveVisualQueryDetector;
    private android.hardware.soundtrigger.KeyphraseEnrollmentInfo mKeyphraseEnrollmentInfo;
    com.android.internal.app.IVoiceInteractionManagerService mSystemService;
    static final java.lang.String TAG = android.service.voice.VoiceInteractionService.class.getSimpleName();
    private static final boolean SYSPROP_VISUAL_QUERY_SERVICE_ENABLED = android.os.SystemProperties.getBoolean("ro.hotword.visual_query_service_enabled", false);
    android.service.voice.IVoiceInteractionService mInterface = new android.service.voice.VoiceInteractionService.AnonymousClass1();
    private final java.lang.Object mLock = new java.lang.Object();
    private final java.util.Set<android.service.voice.HotwordDetector> mActiveDetectors = new android.util.ArraySet();
    private boolean mTestModuleForAlwaysOnHotwordDetectorEnabled = false;
    private android.os.IBinder.DeathRecipient mDeathRecipient = new android.os.IBinder.DeathRecipient() { // from class: android.service.voice.VoiceInteractionService$$ExternalSyntheticLambda2
        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            android.service.voice.VoiceInteractionService.this.lambda$new$1();
        }
    };

    /* renamed from: android.service.voice.VoiceInteractionService$1, reason: invalid class name */
    class AnonymousClass1 extends android.service.voice.IVoiceInteractionService.Stub {
        AnonymousClass1() {
        }

        @Override // android.service.voice.IVoiceInteractionService
        public void ready() {
            android.os.Handler.getMain().executeOrSendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.Consumer() { // from class: android.service.voice.VoiceInteractionService$1$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((android.service.voice.VoiceInteractionService) obj).onReady();
                }
            }, android.service.voice.VoiceInteractionService.this));
        }

        @Override // android.service.voice.IVoiceInteractionService
        public void shutdown() {
            android.os.Handler.getMain().executeOrSendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.Consumer() { // from class: android.service.voice.VoiceInteractionService$1$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((android.service.voice.VoiceInteractionService) obj).onShutdownInternal();
                }
            }, android.service.voice.VoiceInteractionService.this));
        }

        @Override // android.service.voice.IVoiceInteractionService
        public void soundModelsChanged() {
            android.os.Handler.getMain().executeOrSendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.Consumer() { // from class: android.service.voice.VoiceInteractionService$1$$ExternalSyntheticLambda7
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((android.service.voice.VoiceInteractionService) obj).onSoundModelsChangedInternal();
                }
            }, android.service.voice.VoiceInteractionService.this));
        }

        @Override // android.service.voice.IVoiceInteractionService
        public void launchVoiceAssistFromKeyguard() {
            android.os.Handler.getMain().executeOrSendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.Consumer() { // from class: android.service.voice.VoiceInteractionService$1$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((android.service.voice.VoiceInteractionService) obj).onLaunchVoiceAssistFromKeyguard();
                }
            }, android.service.voice.VoiceInteractionService.this));
        }

        @Override // android.service.voice.IVoiceInteractionService
        public void getActiveServiceSupportedActions(java.util.List<java.lang.String> list, com.android.internal.app.IVoiceActionCheckCallback iVoiceActionCheckCallback) {
            android.os.Handler.getMain().executeOrSendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: android.service.voice.VoiceInteractionService$1$$ExternalSyntheticLambda6
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                    ((android.service.voice.VoiceInteractionService) obj).onHandleVoiceActionCheck((java.util.List) obj2, (com.android.internal.app.IVoiceActionCheckCallback) obj3);
                }
            }, android.service.voice.VoiceInteractionService.this, list, iVoiceActionCheckCallback));
        }

        @Override // android.service.voice.IVoiceInteractionService
        public void prepareToShowSession(android.os.Bundle bundle, int i) {
            android.os.Handler.getMain().executeOrSendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: android.service.voice.VoiceInteractionService$1$$ExternalSyntheticLambda3
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                    ((android.service.voice.VoiceInteractionService) obj).onPrepareToShowSession((android.os.Bundle) obj2, ((java.lang.Integer) obj3).intValue());
                }
            }, android.service.voice.VoiceInteractionService.this, bundle, java.lang.Integer.valueOf(i)));
        }

        @Override // android.service.voice.IVoiceInteractionService
        public void showSessionFailed(android.os.Bundle bundle) {
            android.os.Handler.getMain().executeOrSendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: android.service.voice.VoiceInteractionService$1$$ExternalSyntheticLambda2
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    ((android.service.voice.VoiceInteractionService) obj).onShowSessionFailed((android.os.Bundle) obj2);
                }
            }, android.service.voice.VoiceInteractionService.this, bundle));
        }

        @Override // android.service.voice.IVoiceInteractionService
        public void detectorRemoteExceptionOccurred(android.os.IBinder iBinder, int i) {
            android.util.Log.d(android.service.voice.VoiceInteractionService.TAG, "detectorRemoteExceptionOccurred");
            android.os.Handler.getMain().executeOrSendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: android.service.voice.VoiceInteractionService$1$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                    ((android.service.voice.VoiceInteractionService) obj).onDetectorRemoteException((android.os.IBinder) obj2, ((java.lang.Integer) obj3).intValue());
                }
            }, android.service.voice.VoiceInteractionService.this, iBinder, java.lang.Integer.valueOf(i)));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onDetectorRemoteException(final android.os.IBinder iBinder, final int i) {
        android.util.Log.d(TAG, "onDetectorRemoteException for " + android.service.voice.HotwordDetector.detectorTypeToString(i));
        this.mActiveDetectors.forEach(new java.util.function.Consumer() { // from class: android.service.voice.VoiceInteractionService$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                android.service.voice.VoiceInteractionService.lambda$onDetectorRemoteException$0(i, iBinder, (android.service.voice.HotwordDetector) obj);
            }
        });
    }

    static /* synthetic */ void lambda$onDetectorRemoteException$0(int i, android.os.IBinder iBinder, android.service.voice.HotwordDetector hotwordDetector) {
        if (i == 1 && (hotwordDetector instanceof android.service.voice.AlwaysOnHotwordDetector)) {
            android.service.voice.AlwaysOnHotwordDetector alwaysOnHotwordDetector = (android.service.voice.AlwaysOnHotwordDetector) hotwordDetector;
            if (alwaysOnHotwordDetector.isSameToken(iBinder)) {
                alwaysOnHotwordDetector.onDetectorRemoteException();
                return;
            }
            return;
        }
        if (i == 2 && (hotwordDetector instanceof android.service.voice.SoftwareHotwordDetector)) {
            android.service.voice.SoftwareHotwordDetector softwareHotwordDetector = (android.service.voice.SoftwareHotwordDetector) hotwordDetector;
            if (softwareHotwordDetector.isSameToken(iBinder)) {
                softwareHotwordDetector.onDetectorRemoteException();
            }
        }
    }

    public void onLaunchVoiceAssistFromKeyguard() {
    }

    public void onPrepareToShowSession(android.os.Bundle bundle, int i) {
    }

    public void onShowSessionFailed(android.os.Bundle bundle) {
    }

    public static boolean isActiveService(android.content.Context context, android.content.ComponentName componentName) {
        android.content.ComponentName unflattenFromString;
        java.lang.String string = android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.VOICE_INTERACTION_SERVICE);
        if (string == null || string.isEmpty() || (unflattenFromString = android.content.ComponentName.unflattenFromString(string)) == null) {
            return false;
        }
        return unflattenFromString.equals(componentName);
    }

    public void setDisabledShowContext(int i) {
        try {
            this.mSystemService.setDisabledShowContext(i);
        } catch (android.os.RemoteException e) {
        }
    }

    public int getDisabledShowContext() {
        try {
            return this.mSystemService.getDisabledShowContext();
        } catch (android.os.RemoteException e) {
            return 0;
        }
    }

    public void showSession(android.os.Bundle bundle, int i) {
        if (this.mSystemService == null) {
            throw new java.lang.IllegalStateException("Not available until onReady() is called");
        }
        try {
            this.mSystemService.showSession(bundle, i, getAttributionTag());
        } catch (android.os.RemoteException e) {
        }
    }

    public java.util.Set<java.lang.String> onGetSupportedVoiceActions(java.util.Set<java.lang.String> set) {
        return java.util.Collections.emptySet();
    }

    @Override // android.app.Service
    public android.os.IBinder onBind(android.content.Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            return this.mInterface.asBinder();
        }
        return null;
    }

    public void onReady() {
        this.mSystemService = com.android.internal.app.IVoiceInteractionManagerService.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.VOICE_INTERACTION_MANAGER_SERVICE));
        java.util.Objects.requireNonNull(this.mSystemService);
        try {
            this.mSystemService.asBinder().linkToDeath(this.mDeathRecipient, 0);
        } catch (android.os.RemoteException e) {
            android.util.Log.wtf(TAG, "unable to link to death with system service");
        }
        this.mKeyphraseEnrollmentInfo = new android.hardware.soundtrigger.KeyphraseEnrollmentInfo(getPackageManager());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1() {
        android.util.Log.e(TAG, "system service binder died shutting down");
        android.os.Handler.getMain().executeOrSendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.Consumer() { // from class: android.service.voice.VoiceInteractionService$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((android.service.voice.VoiceInteractionService) obj).onShutdownInternal();
            }
        }, this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onShutdownInternal() {
        onShutdown();
        safelyShutdownAllHotwordDetectors(true);
    }

    public void onShutdown() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSoundModelsChangedInternal() {
        synchronized (this) {
            this.mActiveDetectors.forEach(new java.util.function.Consumer() { // from class: android.service.voice.VoiceInteractionService$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    android.service.voice.VoiceInteractionService.lambda$onSoundModelsChangedInternal$2((android.service.voice.HotwordDetector) obj);
                }
            });
        }
    }

    static /* synthetic */ void lambda$onSoundModelsChangedInternal$2(android.service.voice.HotwordDetector hotwordDetector) {
        if (hotwordDetector instanceof android.service.voice.AlwaysOnHotwordDetector) {
            ((android.service.voice.AlwaysOnHotwordDetector) hotwordDetector).onSoundModelsChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onHandleVoiceActionCheck(java.util.List<java.lang.String> list, com.android.internal.app.IVoiceActionCheckCallback iVoiceActionCheckCallback) {
        if (iVoiceActionCheckCallback != null) {
            try {
                iVoiceActionCheckCallback.onComplete(new java.util.ArrayList(onGetSupportedVoiceActions(new android.util.ArraySet(list))));
            } catch (android.os.RemoteException e) {
            }
        }
    }

    public final java.util.List<android.hardware.soundtrigger.SoundTrigger.ModuleProperties> listModuleProperties() {
        android.media.permission.Identity identity = new android.media.permission.Identity();
        identity.packageName = android.app.ActivityThread.currentOpPackageName();
        try {
            return this.mSystemService.listModuleProperties(identity);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public final android.service.voice.AlwaysOnHotwordDetector createAlwaysOnHotwordDetector(java.lang.String str, java.util.Locale locale, android.service.voice.AlwaysOnHotwordDetector.Callback callback) {
        return createAlwaysOnHotwordDetectorInternal(str, locale, false, null, null, null, null, callback);
    }

    @android.annotation.SystemApi
    public final android.service.voice.AlwaysOnHotwordDetector createAlwaysOnHotwordDetector(java.lang.String str, java.util.Locale locale, java.util.concurrent.Executor executor, android.service.voice.AlwaysOnHotwordDetector.Callback callback) {
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(locale);
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(callback);
        return createAlwaysOnHotwordDetectorInternal(str, locale, false, null, null, null, executor, callback);
    }

    public final android.service.voice.AlwaysOnHotwordDetector createAlwaysOnHotwordDetectorForTest(java.lang.String str, java.util.Locale locale, android.hardware.soundtrigger.SoundTrigger.ModuleProperties moduleProperties, java.util.concurrent.Executor executor, android.service.voice.AlwaysOnHotwordDetector.Callback callback) {
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(locale);
        java.util.Objects.requireNonNull(moduleProperties);
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(callback);
        return createAlwaysOnHotwordDetectorInternal(str, locale, false, null, null, moduleProperties, executor, callback);
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public final android.service.voice.AlwaysOnHotwordDetector createAlwaysOnHotwordDetector(java.lang.String str, java.util.Locale locale, android.os.PersistableBundle persistableBundle, android.os.SharedMemory sharedMemory, android.service.voice.AlwaysOnHotwordDetector.Callback callback) {
        return createAlwaysOnHotwordDetectorInternal(str, locale, true, persistableBundle, sharedMemory, null, null, callback);
    }

    @android.annotation.SystemApi
    public final android.service.voice.AlwaysOnHotwordDetector createAlwaysOnHotwordDetector(java.lang.String str, java.util.Locale locale, android.os.PersistableBundle persistableBundle, android.os.SharedMemory sharedMemory, java.util.concurrent.Executor executor, android.service.voice.AlwaysOnHotwordDetector.Callback callback) {
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(locale);
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(callback);
        return createAlwaysOnHotwordDetectorInternal(str, locale, true, persistableBundle, sharedMemory, null, executor, callback);
    }

    public final android.service.voice.AlwaysOnHotwordDetector createAlwaysOnHotwordDetectorForTest(java.lang.String str, java.util.Locale locale, android.os.PersistableBundle persistableBundle, android.os.SharedMemory sharedMemory, android.hardware.soundtrigger.SoundTrigger.ModuleProperties moduleProperties, java.util.concurrent.Executor executor, android.service.voice.AlwaysOnHotwordDetector.Callback callback) {
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(locale);
        java.util.Objects.requireNonNull(moduleProperties);
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(callback);
        return createAlwaysOnHotwordDetectorInternal(str, locale, true, persistableBundle, sharedMemory, moduleProperties, executor, callback);
    }

    private android.service.voice.AlwaysOnHotwordDetector createAlwaysOnHotwordDetectorInternal(java.lang.String str, java.util.Locale locale, boolean z, android.os.PersistableBundle persistableBundle, android.os.SharedMemory sharedMemory, android.hardware.soundtrigger.SoundTrigger.ModuleProperties moduleProperties, java.util.concurrent.Executor executor, android.service.voice.AlwaysOnHotwordDetector.Callback callback) {
        android.service.voice.AlwaysOnHotwordDetector alwaysOnHotwordDetector;
        android.hardware.soundtrigger.SoundTrigger.ModuleProperties moduleProperties2;
        if (this.mSystemService == null) {
            throw new java.lang.IllegalStateException("Not available until onReady() is called");
        }
        synchronized (this.mLock) {
            if (!android.app.compat.CompatChanges.isChangeEnabled(MULTIPLE_ACTIVE_HOTWORD_DETECTORS)) {
                safelyShutdownAllHotwordDetectors(false);
            } else {
                for (android.service.voice.HotwordDetector hotwordDetector : this.mActiveDetectors) {
                    if (hotwordDetector.isUsingSandboxedDetectionService() != z) {
                        throw new java.lang.IllegalStateException("It disallows to create trusted and non-trusted detectors at the same time.");
                    }
                    if (hotwordDetector instanceof android.service.voice.AlwaysOnHotwordDetector) {
                        throw new java.lang.IllegalStateException("There is already an active AlwaysOnHotwordDetector. It must be destroyed to create a new one.");
                    }
                }
            }
            alwaysOnHotwordDetector = new android.service.voice.AlwaysOnHotwordDetector(str, locale, executor, callback, this.mKeyphraseEnrollmentInfo, this.mSystemService, getApplicationContext().getApplicationInfo().targetSdkVersion, z, getAttributionTag());
            this.mActiveDetectors.add(alwaysOnHotwordDetector);
            try {
                alwaysOnHotwordDetector.registerOnDestroyListener(new android.service.voice.VoiceInteractionService$$ExternalSyntheticLambda0(this));
                if (!this.mTestModuleForAlwaysOnHotwordDetectorEnabled) {
                    moduleProperties2 = moduleProperties;
                } else {
                    moduleProperties2 = getTestModuleProperties();
                }
                alwaysOnHotwordDetector.initialize(persistableBundle, sharedMemory, moduleProperties2);
            } catch (java.lang.Exception e) {
                this.mActiveDetectors.remove(alwaysOnHotwordDetector);
                alwaysOnHotwordDetector.destroy();
                throw e;
            }
        }
        return alwaysOnHotwordDetector;
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public final android.service.voice.HotwordDetector createHotwordDetector(android.os.PersistableBundle persistableBundle, android.os.SharedMemory sharedMemory, android.service.voice.HotwordDetector.Callback callback) {
        return createHotwordDetectorInternal(persistableBundle, sharedMemory, null, callback);
    }

    @android.annotation.SystemApi
    public final android.service.voice.HotwordDetector createHotwordDetector(android.os.PersistableBundle persistableBundle, android.os.SharedMemory sharedMemory, java.util.concurrent.Executor executor, android.service.voice.HotwordDetector.Callback callback) {
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(callback);
        return createHotwordDetectorInternal(persistableBundle, sharedMemory, executor, callback);
    }

    private android.service.voice.HotwordDetector createHotwordDetectorInternal(android.os.PersistableBundle persistableBundle, android.os.SharedMemory sharedMemory, java.util.concurrent.Executor executor, android.service.voice.HotwordDetector.Callback callback) {
        android.service.voice.SoftwareHotwordDetector softwareHotwordDetector;
        if (this.mSystemService == null) {
            throw new java.lang.IllegalStateException("Not available until onReady() is called");
        }
        synchronized (this.mLock) {
            if (!android.app.compat.CompatChanges.isChangeEnabled(MULTIPLE_ACTIVE_HOTWORD_DETECTORS)) {
                safelyShutdownAllHotwordDetectors(false);
            } else {
                for (android.service.voice.HotwordDetector hotwordDetector : this.mActiveDetectors) {
                    if (!hotwordDetector.isUsingSandboxedDetectionService()) {
                        throw new java.lang.IllegalStateException("It disallows to create trusted and non-trusted detectors at the same time.");
                    }
                    if (hotwordDetector instanceof android.service.voice.SoftwareHotwordDetector) {
                        throw new java.lang.IllegalStateException("There is already an active SoftwareHotwordDetector. It must be destroyed to create a new one.");
                    }
                }
            }
            softwareHotwordDetector = new android.service.voice.SoftwareHotwordDetector(this.mSystemService, null, executor, callback, getAttributionTag());
            this.mActiveDetectors.add(softwareHotwordDetector);
            try {
                softwareHotwordDetector.registerOnDestroyListener(new android.service.voice.VoiceInteractionService$$ExternalSyntheticLambda0(this));
                softwareHotwordDetector.initialize(persistableBundle, sharedMemory);
            } catch (java.lang.Exception e) {
                this.mActiveDetectors.remove(softwareHotwordDetector);
                softwareHotwordDetector.destroy();
                throw e;
            }
        }
        return softwareHotwordDetector;
    }

    @android.annotation.SystemApi
    public final android.service.voice.VisualQueryDetector createVisualQueryDetector(android.os.PersistableBundle persistableBundle, android.os.SharedMemory sharedMemory, java.util.concurrent.Executor executor, android.service.voice.VisualQueryDetector.Callback callback) {
        android.service.voice.VisualQueryDetector visualQueryDetector;
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(callback);
        if (!SYSPROP_VISUAL_QUERY_SERVICE_ENABLED) {
            throw new java.lang.IllegalStateException("VisualQueryDetectionService is not enabled on this system. Please set ro.hotword.visual_query_service_enabled to true.");
        }
        if (this.mSystemService == null) {
            throw new java.lang.IllegalStateException("Not available until onReady() is called");
        }
        synchronized (this.mLock) {
            if (this.mActiveVisualQueryDetector != null) {
                throw new java.lang.IllegalStateException("There is already an active VisualQueryDetector. It must be destroyed to create a new one.");
            }
            java.util.Iterator<android.service.voice.HotwordDetector> it = this.mActiveDetectors.iterator();
            while (it.hasNext()) {
                if (!it.next().isUsingSandboxedDetectionService()) {
                    throw new java.lang.IllegalStateException("It disallows to create trusted and non-trusted detectors at the same time.");
                }
            }
            visualQueryDetector = new android.service.voice.VisualQueryDetector(this.mSystemService, executor, callback, this, getAttributionTag());
            android.service.voice.HotwordDetector initializationDelegate = visualQueryDetector.getInitializationDelegate();
            this.mActiveDetectors.add(initializationDelegate);
            try {
                visualQueryDetector.registerOnDestroyListener(new android.service.voice.VoiceInteractionService$$ExternalSyntheticLambda0(this));
                visualQueryDetector.initialize(persistableBundle, sharedMemory);
                this.mActiveVisualQueryDetector = visualQueryDetector;
            } catch (java.lang.Exception e) {
                this.mActiveDetectors.remove(initializationDelegate);
                visualQueryDetector.destroy();
                throw e;
            }
        }
        return visualQueryDetector;
    }

    @android.annotation.SystemApi
    public final android.media.voice.KeyphraseModelManager createKeyphraseModelManager() {
        android.media.voice.KeyphraseModelManager keyphraseModelManager;
        if (this.mSystemService == null) {
            throw new java.lang.IllegalStateException("Not available until onReady() is called");
        }
        synchronized (this.mLock) {
            keyphraseModelManager = new android.media.voice.KeyphraseModelManager(this.mSystemService);
        }
        return keyphraseModelManager;
    }

    protected final android.hardware.soundtrigger.KeyphraseEnrollmentInfo getKeyphraseEnrollmentInfo() {
        return this.mKeyphraseEnrollmentInfo;
    }

    public final void setTestModuleForAlwaysOnHotwordDetectorEnabled(boolean z) {
        synchronized (this.mLock) {
            this.mTestModuleForAlwaysOnHotwordDetectorEnabled = z;
        }
    }

    private final android.hardware.soundtrigger.SoundTrigger.ModuleProperties getTestModuleProperties() {
        android.hardware.soundtrigger.SoundTrigger.ModuleProperties orElse = listModuleProperties().stream().filter(new java.util.function.Predicate() { // from class: android.service.voice.VoiceInteractionService$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean equals;
                equals = ((android.hardware.soundtrigger.SoundTrigger.ModuleProperties) obj).getSupportedModelArch().equals("injection");
                return equals;
            }
        }).findFirst().orElse(null);
        if (orElse == null) {
            throw new java.lang.IllegalStateException("Fake ST HAL should always be available");
        }
        return orElse;
    }

    public final boolean isKeyphraseAndLocaleSupportedForHotword(java.lang.String str, java.util.Locale locale) {
        return (this.mKeyphraseEnrollmentInfo == null || this.mKeyphraseEnrollmentInfo.getKeyphraseMetadata(str, locale) == null) ? false : true;
    }

    private void safelyShutdownAllHotwordDetectors(final boolean z) {
        synchronized (this.mLock) {
            this.mActiveDetectors.forEach(new java.util.function.Consumer() { // from class: android.service.voice.VoiceInteractionService$$ExternalSyntheticLambda6
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    android.service.voice.VoiceInteractionService.this.lambda$safelyShutdownAllHotwordDetectors$4(z, (android.service.voice.HotwordDetector) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$safelyShutdownAllHotwordDetectors$4(boolean z, android.service.voice.HotwordDetector hotwordDetector) {
        try {
            if (this.mActiveVisualQueryDetector == null || hotwordDetector != this.mActiveVisualQueryDetector.getInitializationDelegate() || z) {
                hotwordDetector.destroy();
            }
        } catch (java.lang.Exception e) {
            android.util.Log.i(TAG, "exception destroying HotwordDetector", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onHotwordDetectorDestroyed(android.service.voice.HotwordDetector hotwordDetector) {
        synchronized (this.mLock) {
            if (this.mActiveVisualQueryDetector != null && hotwordDetector == this.mActiveVisualQueryDetector.getInitializationDelegate()) {
                this.mActiveVisualQueryDetector = null;
            }
            this.mActiveDetectors.remove(hotwordDetector);
        }
    }

    public final void setUiHints(android.os.Bundle bundle) {
        if (bundle == null) {
            throw new java.lang.IllegalArgumentException("Hints must be non-null");
        }
        try {
            this.mSystemService.setUiHints(bundle);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.app.Service
    protected void dump(java.io.FileDescriptor fileDescriptor, final java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        printWriter.println("VOICE INTERACTION");
        synchronized (this.mLock) {
            printWriter.println("  Sandboxed Detector(s):");
            if (this.mActiveDetectors.size() == 0) {
                printWriter.println("    No detector.");
            } else {
                this.mActiveDetectors.forEach(new java.util.function.Consumer() { // from class: android.service.voice.VoiceInteractionService$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        android.service.voice.VoiceInteractionService.lambda$dump$5(printWriter, (android.service.voice.HotwordDetector) obj);
                    }
                });
            }
            printWriter.println("Available Model Enrollment Applications:");
            printWriter.println("  " + this.mKeyphraseEnrollmentInfo);
        }
    }

    static /* synthetic */ void lambda$dump$5(java.io.PrintWriter printWriter, android.service.voice.HotwordDetector hotwordDetector) {
        printWriter.print("  Using sandboxed detection service=");
        printWriter.println(hotwordDetector.isUsingSandboxedDetectionService());
        hotwordDetector.dump("    ", printWriter);
        printWriter.println();
    }
}
