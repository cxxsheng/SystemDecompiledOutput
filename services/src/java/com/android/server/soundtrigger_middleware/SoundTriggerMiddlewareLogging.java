package com.android.server.soundtrigger_middleware;

/* loaded from: classes2.dex */
public class SoundTriggerMiddlewareLogging implements com.android.server.soundtrigger_middleware.ISoundTriggerMiddlewareInternal, com.android.server.soundtrigger_middleware.Dumpable {
    private static final int SESSION_MAX_EVENT_SIZE = 128;
    private static final java.lang.String TAG = "SoundTriggerMiddlewareLogging";

    @android.annotation.NonNull
    private final java.util.function.Supplier<android.os.BatteryStatsInternal> mBatteryStatsInternalSupplier;

    @android.annotation.NonNull
    private final com.android.server.soundtrigger_middleware.ISoundTriggerMiddlewareInternal mDelegate;
    private final java.util.Deque<com.android.server.utils.EventLogger> mDetachedSessionEventLoggers;

    @android.annotation.NonNull
    private final com.android.internal.util.LatencyTracker mLatencyTracker;

    @android.annotation.NonNull
    private final com.android.server.utils.EventLogger mServiceEventLogger;
    private final java.util.concurrent.atomic.AtomicInteger mSessionCount;
    private final java.util.Set<com.android.server.utils.EventLogger> mSessionEventLoggers;

    /* JADX INFO: Access modifiers changed from: private */
    static class BatteryStatsHolder {
        private static final android.os.BatteryStatsInternal INSTANCE = (android.os.BatteryStatsInternal) com.android.server.LocalServices.getService(android.os.BatteryStatsInternal.class);

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: -$$Nest$sfgetINSTANCE, reason: not valid java name */
        public static /* bridge */ /* synthetic */ android.os.BatteryStatsInternal m7366$$Nest$sfgetINSTANCE() {
            return INSTANCE;
        }

        private BatteryStatsHolder() {
        }
    }

    public SoundTriggerMiddlewareLogging(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.soundtrigger_middleware.ISoundTriggerMiddlewareInternal iSoundTriggerMiddlewareInternal) {
        this(com.android.internal.util.LatencyTracker.getInstance(context), new java.util.function.Supplier() { // from class: com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                android.os.BatteryStatsInternal m7366$$Nest$sfgetINSTANCE;
                m7366$$Nest$sfgetINSTANCE = com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.BatteryStatsHolder.m7366$$Nest$sfgetINSTANCE();
                return m7366$$Nest$sfgetINSTANCE;
            }
        }, iSoundTriggerMiddlewareInternal);
    }

    @com.android.internal.annotations.VisibleForTesting
    public SoundTriggerMiddlewareLogging(@android.annotation.NonNull com.android.internal.util.LatencyTracker latencyTracker, @android.annotation.NonNull java.util.function.Supplier<android.os.BatteryStatsInternal> supplier, @android.annotation.NonNull com.android.server.soundtrigger_middleware.ISoundTriggerMiddlewareInternal iSoundTriggerMiddlewareInternal) {
        this.mServiceEventLogger = new com.android.server.utils.EventLogger(256, "Service Events");
        this.mSessionEventLoggers = java.util.concurrent.ConcurrentHashMap.newKeySet(4);
        this.mDetachedSessionEventLoggers = new java.util.concurrent.LinkedBlockingDeque(4);
        this.mSessionCount = new java.util.concurrent.atomic.AtomicInteger(0);
        this.mDelegate = iSoundTriggerMiddlewareInternal;
        this.mLatencyTracker = latencyTracker;
        this.mBatteryStatsInternalSupplier = supplier;
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerMiddlewareInternal
    @android.annotation.NonNull
    public android.media.soundtrigger_middleware.SoundTriggerModuleDescriptor[] listModules() {
        try {
            android.media.soundtrigger_middleware.SoundTriggerModuleDescriptor[] listModules = this.mDelegate.listModules();
            this.mServiceEventLogger.enqueue(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.ServiceEvent.createForReturn(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.ServiceEvent.Type.LIST_MODULE, android.media.permission.IdentityContext.get().packageName, (com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.ModulePropertySummary[]) java.util.Arrays.stream(listModules).map(new java.util.function.Function() { // from class: com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.ModulePropertySummary lambda$listModules$1;
                    lambda$listModules$1 = com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.lambda$listModules$1((android.media.soundtrigger_middleware.SoundTriggerModuleDescriptor) obj);
                    return lambda$listModules$1;
                }
            }).toArray(new java.util.function.IntFunction() { // from class: com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging$$ExternalSyntheticLambda2
                @Override // java.util.function.IntFunction
                public final java.lang.Object apply(int i) {
                    com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.ModulePropertySummary[] lambda$listModules$2;
                    lambda$listModules$2 = com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.lambda$listModules$2(i);
                    return lambda$listModules$2;
                }
            }), new java.lang.Object[0]).printLog(0, TAG));
            return listModules;
        } catch (java.lang.Exception e) {
            this.mServiceEventLogger.enqueue(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.ServiceEvent.createForException(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.ServiceEvent.Type.LIST_MODULE, android.media.permission.IdentityContext.get().packageName, e, new java.lang.Object[0]).printLog(2, TAG));
            throw e;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.ModulePropertySummary lambda$listModules$1(android.media.soundtrigger_middleware.SoundTriggerModuleDescriptor soundTriggerModuleDescriptor) {
        return new com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.ModulePropertySummary(soundTriggerModuleDescriptor.handle, soundTriggerModuleDescriptor.properties.implementor, soundTriggerModuleDescriptor.properties.version);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.ModulePropertySummary[] lambda$listModules$2(int i) {
        return new com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.ModulePropertySummary[i];
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerMiddlewareInternal
    @android.annotation.NonNull
    public android.media.soundtrigger_middleware.ISoundTriggerModule attach(int i, android.media.soundtrigger_middleware.ISoundTriggerCallback iSoundTriggerCallback, boolean z) {
        try {
            android.media.permission.Identity nonNull = android.media.permission.IdentityContext.getNonNull();
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(nonNull.packageName);
            sb.append(this.mSessionCount.getAndIncrement());
            sb.append(z ? "trusted" : "");
            java.lang.String sb2 = sb.toString();
            com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.ModuleLogging moduleLogging = new com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.ModuleLogging();
            com.android.server.utils.EventLogger eventLogger = new com.android.server.utils.EventLogger(128, "Session logger for: " + sb2);
            moduleLogging.attach(this.mDelegate.attach(i, new com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.CallbackLogging(iSoundTriggerCallback, eventLogger, nonNull), z), eventLogger);
            this.mServiceEventLogger.enqueue(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.ServiceEvent.createForReturn(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.ServiceEvent.Type.ATTACH, sb2, moduleLogging, java.lang.Integer.valueOf(i), iSoundTriggerCallback, java.lang.Boolean.valueOf(z)).printLog(0, TAG));
            this.mSessionEventLoggers.add(eventLogger);
            return moduleLogging;
        } catch (java.lang.Exception e) {
            this.mServiceEventLogger.enqueue(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.ServiceEvent.createForException(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.ServiceEvent.Type.ATTACH, android.media.permission.IdentityContext.get().packageName, e, java.lang.Integer.valueOf(i), iSoundTriggerCallback).printLog(2, TAG));
            throw e;
        }
    }

    public java.lang.String toString() {
        return this.mDelegate.toString();
    }

    private class ModuleLogging implements android.media.soundtrigger_middleware.ISoundTriggerModule {
        private android.media.soundtrigger_middleware.ISoundTriggerModule mDelegate;
        private com.android.server.utils.EventLogger mEventLogger;

        private ModuleLogging() {
        }

        void attach(@android.annotation.NonNull android.media.soundtrigger_middleware.ISoundTriggerModule iSoundTriggerModule, com.android.server.utils.EventLogger eventLogger) {
            this.mDelegate = iSoundTriggerModule;
            this.mEventLogger = eventLogger;
        }

        public int loadModel(android.media.soundtrigger.SoundModel soundModel) throws android.os.RemoteException {
            try {
                int loadModel = this.mDelegate.loadModel(soundModel);
                this.mEventLogger.enqueue(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.createForReturn(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.Type.LOAD_MODEL, java.lang.Integer.valueOf(loadModel), soundModel.uuid).printLog(0, com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.TAG));
                return loadModel;
            } catch (java.lang.Exception e) {
                this.mEventLogger.enqueue(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.createForReturn(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.Type.LOAD_MODEL, e, soundModel.uuid).printLog(2, com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.TAG));
                throw e;
            }
        }

        public int loadPhraseModel(android.media.soundtrigger.PhraseSoundModel phraseSoundModel) throws android.os.RemoteException {
            try {
                int loadPhraseModel = this.mDelegate.loadPhraseModel(phraseSoundModel);
                this.mEventLogger.enqueue(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.createForReturn(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.Type.LOAD_PHRASE_MODEL, java.lang.Integer.valueOf(loadPhraseModel), phraseSoundModel.common.uuid).printLog(0, com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.TAG));
                return loadPhraseModel;
            } catch (java.lang.Exception e) {
                this.mEventLogger.enqueue(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.createForException(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.Type.LOAD_PHRASE_MODEL, e, phraseSoundModel.common.uuid).printLog(2, com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.TAG));
                throw e;
            }
        }

        public void unloadModel(int i) throws android.os.RemoteException {
            try {
                this.mDelegate.unloadModel(i);
                this.mEventLogger.enqueue(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.createForVoid(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.Type.UNLOAD_MODEL, java.lang.Integer.valueOf(i)).printLog(0, com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.TAG));
            } catch (java.lang.Exception e) {
                this.mEventLogger.enqueue(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.createForException(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.Type.UNLOAD_MODEL, e, java.lang.Integer.valueOf(i)).printLog(2, com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.TAG));
                throw e;
            }
        }

        public android.os.IBinder startRecognition(int i, android.media.soundtrigger.RecognitionConfig recognitionConfig) throws android.os.RemoteException {
            try {
                android.os.IBinder startRecognition = this.mDelegate.startRecognition(i, recognitionConfig);
                this.mEventLogger.enqueue(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.createForReturn(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.Type.START_RECOGNITION, startRecognition, java.lang.Integer.valueOf(i), recognitionConfig).printLog(0, com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.TAG));
                return startRecognition;
            } catch (java.lang.Exception e) {
                this.mEventLogger.enqueue(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.createForException(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.Type.START_RECOGNITION, e, java.lang.Integer.valueOf(i), recognitionConfig).printLog(2, com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.TAG));
                throw e;
            }
        }

        public void stopRecognition(int i) throws android.os.RemoteException {
            try {
                this.mDelegate.stopRecognition(i);
                this.mEventLogger.enqueue(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.createForVoid(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.Type.STOP_RECOGNITION, java.lang.Integer.valueOf(i)).printLog(0, com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.TAG));
            } catch (java.lang.Exception e) {
                this.mEventLogger.enqueue(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.createForException(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.Type.STOP_RECOGNITION, e, java.lang.Integer.valueOf(i)).printLog(2, com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.TAG));
                throw e;
            }
        }

        public void forceRecognitionEvent(int i) throws android.os.RemoteException {
            try {
                this.mDelegate.forceRecognitionEvent(i);
                this.mEventLogger.enqueue(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.createForVoid(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.Type.FORCE_RECOGNITION, java.lang.Integer.valueOf(i)).printLog(0, com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.TAG));
            } catch (java.lang.Exception e) {
                this.mEventLogger.enqueue(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.createForException(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.Type.FORCE_RECOGNITION, e, java.lang.Integer.valueOf(i)).printLog(2, com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.TAG));
                throw e;
            }
        }

        public void setModelParameter(int i, int i2, int i3) throws android.os.RemoteException {
            try {
                this.mDelegate.setModelParameter(i, i2, i3);
                this.mEventLogger.enqueue(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.createForVoid(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.Type.SET_MODEL_PARAMETER, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i3)).printLog(0, com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.TAG));
            } catch (java.lang.Exception e) {
                this.mEventLogger.enqueue(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.createForException(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.Type.SET_MODEL_PARAMETER, e, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i3)).printLog(2, com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.TAG));
                throw e;
            }
        }

        public int getModelParameter(int i, int i2) throws android.os.RemoteException {
            try {
                int modelParameter = this.mDelegate.getModelParameter(i, i2);
                this.mEventLogger.enqueue(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.createForReturn(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.Type.GET_MODEL_PARAMETER, java.lang.Integer.valueOf(modelParameter), java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2)).printLog(0, com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.TAG));
                return modelParameter;
            } catch (java.lang.Exception e) {
                this.mEventLogger.enqueue(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.createForException(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.Type.GET_MODEL_PARAMETER, e, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2)).printLog(2, com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.TAG));
                throw e;
            }
        }

        public android.media.soundtrigger.ModelParameterRange queryModelParameterSupport(int i, int i2) throws android.os.RemoteException {
            try {
                android.media.soundtrigger.ModelParameterRange queryModelParameterSupport = this.mDelegate.queryModelParameterSupport(i, i2);
                this.mEventLogger.enqueue(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.createForReturn(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.Type.QUERY_MODEL_PARAMETER, queryModelParameterSupport, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2)).printLog(0, com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.TAG));
                return queryModelParameterSupport;
            } catch (java.lang.Exception e) {
                this.mEventLogger.enqueue(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.createForException(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.Type.QUERY_MODEL_PARAMETER, e, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2)).printLog(2, com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.TAG));
                throw e;
            }
        }

        public void detach() throws android.os.RemoteException {
            try {
                if (com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.this.mSessionEventLoggers.remove(this.mEventLogger)) {
                    while (!com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.this.mDetachedSessionEventLoggers.offerFirst(this.mEventLogger)) {
                        com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.this.mDetachedSessionEventLoggers.pollLast();
                    }
                }
                this.mDelegate.detach();
                this.mEventLogger.enqueue(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.createForVoid(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.Type.DETACH, new java.lang.Object[0]).printLog(0, com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.TAG));
            } catch (java.lang.Exception e) {
                this.mEventLogger.enqueue(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.createForException(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.Type.DETACH, e, new java.lang.Object[0]).printLog(2, com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.TAG));
                throw e;
            }
        }

        public android.os.IBinder asBinder() {
            return this.mDelegate.asBinder();
        }

        public java.lang.String toString() {
            return java.util.Objects.toString(this.mDelegate);
        }
    }

    private class CallbackLogging implements android.media.soundtrigger_middleware.ISoundTriggerCallback {
        private final android.media.soundtrigger_middleware.ISoundTriggerCallback mCallbackDelegate;
        private final com.android.server.utils.EventLogger mEventLogger;
        private final android.media.permission.Identity mOriginatorIdentity;

        private CallbackLogging(android.media.soundtrigger_middleware.ISoundTriggerCallback iSoundTriggerCallback, com.android.server.utils.EventLogger eventLogger, android.media.permission.Identity identity) {
            java.util.Objects.requireNonNull(iSoundTriggerCallback);
            this.mCallbackDelegate = iSoundTriggerCallback;
            java.util.Objects.requireNonNull(eventLogger);
            this.mEventLogger = eventLogger;
            this.mOriginatorIdentity = identity;
        }

        public void onRecognition(int i, android.media.soundtrigger_middleware.RecognitionEventSys recognitionEventSys, int i2) throws android.os.RemoteException {
            try {
                ((android.os.BatteryStatsInternal) com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.this.mBatteryStatsInternalSupplier.get()).noteWakingSoundTrigger(android.os.SystemClock.elapsedRealtime(), this.mOriginatorIdentity.uid);
                this.mCallbackDelegate.onRecognition(i, recognitionEventSys, i2);
                this.mEventLogger.enqueue(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.createForVoid(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.Type.RECOGNITION, java.lang.Integer.valueOf(i), recognitionEventSys, java.lang.Integer.valueOf(i2)).printLog(0, com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.TAG));
            } catch (java.lang.Exception e) {
                this.mEventLogger.enqueue(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.createForException(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.Type.RECOGNITION, e, java.lang.Integer.valueOf(i), recognitionEventSys, java.lang.Integer.valueOf(i2)).printLog(2, com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.TAG));
                throw e;
            }
        }

        public void onPhraseRecognition(int i, android.media.soundtrigger_middleware.PhraseRecognitionEventSys phraseRecognitionEventSys, int i2) throws android.os.RemoteException {
            try {
                ((android.os.BatteryStatsInternal) com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.this.mBatteryStatsInternalSupplier.get()).noteWakingSoundTrigger(android.os.SystemClock.elapsedRealtime(), this.mOriginatorIdentity.uid);
                com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.this.startKeyphraseEventLatencyTracking(phraseRecognitionEventSys.phraseRecognitionEvent);
                this.mCallbackDelegate.onPhraseRecognition(i, phraseRecognitionEventSys, i2);
                this.mEventLogger.enqueue(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.createForVoid(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.Type.RECOGNITION, java.lang.Integer.valueOf(i), phraseRecognitionEventSys, java.lang.Integer.valueOf(i2)).printLog(0, com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.TAG));
            } catch (java.lang.Exception e) {
                this.mEventLogger.enqueue(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.createForException(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.Type.RECOGNITION, e, java.lang.Integer.valueOf(i), phraseRecognitionEventSys, java.lang.Integer.valueOf(i2)).printLog(2, com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.TAG));
                throw e;
            }
        }

        public void onModelUnloaded(int i) throws android.os.RemoteException {
            try {
                this.mCallbackDelegate.onModelUnloaded(i);
                this.mEventLogger.enqueue(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.createForVoid(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.Type.MODEL_UNLOADED, java.lang.Integer.valueOf(i)).printLog(0, com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.TAG));
            } catch (java.lang.Exception e) {
                this.mEventLogger.enqueue(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.createForException(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.Type.MODEL_UNLOADED, e, java.lang.Integer.valueOf(i)).printLog(2, com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.TAG));
                throw e;
            }
        }

        public void onResourcesAvailable() throws android.os.RemoteException {
            try {
                this.mCallbackDelegate.onResourcesAvailable();
                this.mEventLogger.enqueue(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.createForVoid(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.Type.RESOURCES_AVAILABLE, new java.lang.Object[0]).printLog(0, com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.TAG));
            } catch (java.lang.Exception e) {
                this.mEventLogger.enqueue(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.createForException(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.Type.RESOURCES_AVAILABLE, e, new java.lang.Object[0]).printLog(2, com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.TAG));
                throw e;
            }
        }

        public void onModuleDied() throws android.os.RemoteException {
            try {
                this.mCallbackDelegate.onModuleDied();
                this.mEventLogger.enqueue(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.createForVoid(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.Type.MODULE_DIED, new java.lang.Object[0]).printLog(2, com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.TAG));
            } catch (java.lang.Exception e) {
                this.mEventLogger.enqueue(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.createForException(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.Type.MODULE_DIED, e, new java.lang.Object[0]).printLog(2, com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.TAG));
                throw e;
            }
        }

        public android.os.IBinder asBinder() {
            return this.mCallbackDelegate.asBinder();
        }

        public java.lang.String toString() {
            return java.util.Objects.toString(this.mCallbackDelegate);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startKeyphraseEventLatencyTracking(android.media.soundtrigger.PhraseRecognitionEvent phraseRecognitionEvent) {
        if (phraseRecognitionEvent.common.status != 0 || com.android.internal.util.ArrayUtils.isEmpty(phraseRecognitionEvent.phraseExtras)) {
            return;
        }
        java.lang.String str = "KeyphraseId=" + phraseRecognitionEvent.phraseExtras[0].id;
        this.mLatencyTracker.onActionCancel(19);
        this.mLatencyTracker.onActionStart(19, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.StringBuilder printArgs(java.lang.StringBuilder sb, @android.annotation.NonNull java.lang.Object[] objArr) {
        for (int i = 0; i < objArr.length; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            com.android.server.soundtrigger_middleware.ObjectPrinter.print(sb, objArr[i]);
        }
        return sb;
    }

    @Override // com.android.server.soundtrigger_middleware.Dumpable
    public void dump(java.io.PrintWriter printWriter) {
        printWriter.println("##Service-Wide logs:");
        this.mServiceEventLogger.dump(printWriter, "  ");
        printWriter.println("\n##Active Session dumps:\n");
        java.util.Iterator<com.android.server.utils.EventLogger> it = this.mSessionEventLoggers.iterator();
        while (it.hasNext()) {
            it.next().dump(printWriter, "  ");
            printWriter.println("");
        }
        printWriter.println("##Detached Session dumps:\n");
        java.util.Iterator<com.android.server.utils.EventLogger> it2 = this.mDetachedSessionEventLoggers.iterator();
        while (it2.hasNext()) {
            it2.next().dump(printWriter, "  ");
            printWriter.println("");
        }
        if (this.mDelegate instanceof com.android.server.soundtrigger_middleware.Dumpable) {
            ((com.android.server.soundtrigger_middleware.Dumpable) this.mDelegate).dump(printWriter);
        }
    }

    public static void printSystemLog(int i, java.lang.String str, java.lang.String str2, java.lang.Exception exc) {
        switch (i) {
            case 0:
                android.util.Slog.i(str, str2, exc);
                break;
            case 1:
                android.util.Slog.e(str, str2, exc);
                break;
            case 2:
                android.util.Slog.w(str, str2, exc);
                break;
            default:
                android.util.Slog.v(str, str2, exc);
                break;
        }
    }

    public static class ServiceEvent extends com.android.server.utils.EventLogger.Event {
        private final java.lang.Exception mException;
        private final java.lang.String mPackageName;
        private final java.lang.Object[] mParams;
        private final java.lang.Object mReturnValue;
        private final com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.ServiceEvent.Type mType;

        public enum Type {
            ATTACH,
            LIST_MODULE
        }

        public static com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.ServiceEvent createForException(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.ServiceEvent.Type type, java.lang.String str, java.lang.Exception exc, java.lang.Object... objArr) {
            return new com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.ServiceEvent(exc, type, str, null, objArr);
        }

        public static com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.ServiceEvent createForReturn(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.ServiceEvent.Type type, java.lang.String str, java.lang.Object obj, java.lang.Object... objArr) {
            return new com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.ServiceEvent(null, type, str, obj, objArr);
        }

        private ServiceEvent(java.lang.Exception exc, com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.ServiceEvent.Type type, java.lang.String str, java.lang.Object obj, java.lang.Object... objArr) {
            this.mException = exc;
            this.mType = type;
            this.mPackageName = str;
            this.mReturnValue = obj;
            this.mParams = objArr;
        }

        @Override // com.android.server.utils.EventLogger.Event
        public com.android.server.utils.EventLogger.Event printLog(int i, java.lang.String str) {
            com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.printSystemLog(i, str, eventToString(), this.mException);
            return this;
        }

        @Override // com.android.server.utils.EventLogger.Event
        public java.lang.String eventToString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder(this.mType.name());
            sb.append(" [client= ");
            com.android.server.soundtrigger_middleware.ObjectPrinter.print(sb, this.mPackageName);
            sb.append("] (");
            com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.printArgs(sb, this.mParams);
            sb.append(") -> ");
            if (this.mException != null) {
                sb.append("ERROR: ");
                com.android.server.soundtrigger_middleware.ObjectPrinter.print(sb, this.mException);
            } else {
                com.android.server.soundtrigger_middleware.ObjectPrinter.print(sb, this.mReturnValue);
            }
            return sb.toString();
        }
    }

    public static class SessionEvent extends com.android.server.utils.EventLogger.Event {
        private final java.lang.Exception mException;
        private final java.lang.Object[] mParams;
        private final java.lang.Object mReturnValue;
        private final com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.Type mType;

        public enum Type {
            LOAD_MODEL,
            LOAD_PHRASE_MODEL,
            START_RECOGNITION,
            STOP_RECOGNITION,
            FORCE_RECOGNITION,
            UNLOAD_MODEL,
            GET_MODEL_PARAMETER,
            SET_MODEL_PARAMETER,
            QUERY_MODEL_PARAMETER,
            DETACH,
            RECOGNITION,
            MODEL_UNLOADED,
            MODULE_DIED,
            RESOURCES_AVAILABLE
        }

        public static com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent createForException(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.Type type, java.lang.Exception exc, java.lang.Object... objArr) {
            return new com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent(exc, type, null, objArr);
        }

        public static com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent createForReturn(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.Type type, java.lang.Object obj, java.lang.Object... objArr) {
            return new com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent(null, type, obj, objArr);
        }

        public static com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent createForVoid(com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.Type type, java.lang.Object... objArr) {
            return new com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent(null, type, null, objArr);
        }

        private SessionEvent(java.lang.Exception exc, com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.SessionEvent.Type type, java.lang.Object obj, java.lang.Object... objArr) {
            this.mException = exc;
            this.mType = type;
            this.mReturnValue = obj;
            this.mParams = objArr;
        }

        @Override // com.android.server.utils.EventLogger.Event
        public com.android.server.utils.EventLogger.Event printLog(int i, java.lang.String str) {
            com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.printSystemLog(i, str, eventToString(), this.mException);
            return this;
        }

        @Override // com.android.server.utils.EventLogger.Event
        public java.lang.String eventToString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder(this.mType.name());
            sb.append(" (");
            com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging.printArgs(sb, this.mParams);
            sb.append(")");
            if (this.mException != null) {
                sb.append(" -> ERROR: ");
                com.android.server.soundtrigger_middleware.ObjectPrinter.print(sb, this.mException);
            } else if (this.mReturnValue != null) {
                sb.append(" -> ");
                com.android.server.soundtrigger_middleware.ObjectPrinter.print(sb, this.mReturnValue);
            }
            return sb.toString();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class ModulePropertySummary {
        private int mId;
        private java.lang.String mImplementor;
        private int mVersion;

        ModulePropertySummary(int i, java.lang.String str, int i2) {
            this.mId = i;
            this.mImplementor = str;
            this.mVersion = i2;
        }

        public java.lang.String toString() {
            return "{Id: " + this.mId + ", Implementor: " + this.mImplementor + ", Version: " + this.mVersion + "}";
        }
    }
}
