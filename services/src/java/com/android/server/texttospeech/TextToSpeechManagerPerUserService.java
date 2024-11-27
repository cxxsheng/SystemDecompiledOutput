package com.android.server.texttospeech;

/* loaded from: classes2.dex */
final class TextToSpeechManagerPerUserService extends com.android.server.infra.AbstractPerUserSystemService<com.android.server.texttospeech.TextToSpeechManagerPerUserService, com.android.server.texttospeech.TextToSpeechManagerService> {
    private static final java.lang.String TAG = com.android.server.texttospeech.TextToSpeechManagerPerUserService.class.getSimpleName();

    interface ThrowingRunnable {
        void runOrThrow() throws android.os.RemoteException;
    }

    TextToSpeechManagerPerUserService(@android.annotation.NonNull com.android.server.texttospeech.TextToSpeechManagerService textToSpeechManagerService, @android.annotation.NonNull java.lang.Object obj, int i) {
        super(textToSpeechManagerService, obj, i);
    }

    void createSessionLocked(java.lang.String str, android.speech.tts.ITextToSpeechSessionCallback iTextToSpeechSessionCallback) {
        com.android.server.texttospeech.TextToSpeechManagerPerUserService.TextToSpeechSessionConnection.start(getContext(), this.mUserId, str, iTextToSpeechSessionCallback);
    }

    @Override // com.android.server.infra.AbstractPerUserSystemService
    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    protected android.content.pm.ServiceInfo newServiceInfoLocked(@android.annotation.NonNull android.content.ComponentName componentName) throws android.content.pm.PackageManager.NameNotFoundException {
        try {
            return android.app.AppGlobals.getPackageManager().getServiceInfo(componentName, 128L, this.mUserId);
        } catch (android.os.RemoteException e) {
            throw new android.content.pm.PackageManager.NameNotFoundException("Could not get service for " + componentName);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class TextToSpeechSessionConnection extends com.android.internal.infra.ServiceConnector.Impl<android.speech.tts.ITextToSpeechService> {
        private android.speech.tts.ITextToSpeechSessionCallback mCallback;
        private final java.lang.String mEngine;
        private final android.os.IBinder.DeathRecipient mUnbindOnDeathHandler;

        static void start(android.content.Context context, int i, java.lang.String str, android.speech.tts.ITextToSpeechSessionCallback iTextToSpeechSessionCallback) {
            new com.android.server.texttospeech.TextToSpeechManagerPerUserService.TextToSpeechSessionConnection(context, i, str, iTextToSpeechSessionCallback).start();
        }

        private TextToSpeechSessionConnection(android.content.Context context, int i, java.lang.String str, android.speech.tts.ITextToSpeechSessionCallback iTextToSpeechSessionCallback) {
            super(context, new android.content.Intent("android.intent.action.TTS_SERVICE").setPackage(str), 524289, i, new java.util.function.Function() { // from class: com.android.server.texttospeech.TextToSpeechManagerPerUserService$TextToSpeechSessionConnection$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    return android.speech.tts.ITextToSpeechService.Stub.asInterface((android.os.IBinder) obj);
                }
            });
            this.mEngine = str;
            this.mCallback = iTextToSpeechSessionCallback;
            this.mUnbindOnDeathHandler = new android.os.IBinder.DeathRecipient() { // from class: com.android.server.texttospeech.TextToSpeechManagerPerUserService$TextToSpeechSessionConnection$$ExternalSyntheticLambda3
                @Override // android.os.IBinder.DeathRecipient
                public final void binderDied() {
                    com.android.server.texttospeech.TextToSpeechManagerPerUserService.TextToSpeechSessionConnection.this.lambda$new$0();
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$0() {
            unbindEngine("client process death is reported");
        }

        private void start() {
            android.util.Slog.d(com.android.server.texttospeech.TextToSpeechManagerPerUserService.TAG, "Trying to start connection to TTS engine: " + this.mEngine);
            connect().thenAccept(new java.util.function.Consumer() { // from class: com.android.server.texttospeech.TextToSpeechManagerPerUserService$TextToSpeechSessionConnection$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.texttospeech.TextToSpeechManagerPerUserService.TextToSpeechSessionConnection.this.lambda$start$2((android.speech.tts.ITextToSpeechService) obj);
                }
            }).exceptionally(new java.util.function.Function() { // from class: com.android.server.texttospeech.TextToSpeechManagerPerUserService$TextToSpeechSessionConnection$$ExternalSyntheticLambda5
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    java.lang.Void lambda$start$4;
                    lambda$start$4 = com.android.server.texttospeech.TextToSpeechManagerPerUserService.TextToSpeechSessionConnection.this.lambda$start$4((java.lang.Throwable) obj);
                    return lambda$start$4;
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$start$2(android.speech.tts.ITextToSpeechService iTextToSpeechService) {
            if (iTextToSpeechService != null) {
                android.util.Slog.d(com.android.server.texttospeech.TextToSpeechManagerPerUserService.TAG, "Connected successfully to TTS engine: " + this.mEngine);
                try {
                    this.mCallback.onConnected(new android.speech.tts.ITextToSpeechSession.Stub() { // from class: com.android.server.texttospeech.TextToSpeechManagerPerUserService.TextToSpeechSessionConnection.1
                        public void disconnect() {
                            com.android.server.texttospeech.TextToSpeechManagerPerUserService.TextToSpeechSessionConnection.this.unbindEngine("client disconnection request");
                        }
                    }, iTextToSpeechService.asBinder());
                    this.mCallback.asBinder().linkToDeath(this.mUnbindOnDeathHandler, 0);
                    return;
                } catch (android.os.RemoteException e) {
                    android.util.Slog.w(com.android.server.texttospeech.TextToSpeechManagerPerUserService.TAG, "Error notifying the client on connection", e);
                    unbindEngine("failed communicating with the client - process is dead");
                    return;
                }
            }
            android.util.Slog.w(com.android.server.texttospeech.TextToSpeechManagerPerUserService.TAG, "Failed to obtain TTS engine binder");
            com.android.server.texttospeech.TextToSpeechManagerPerUserService.runSessionCallbackMethod(new com.android.server.texttospeech.TextToSpeechManagerPerUserService.ThrowingRunnable() { // from class: com.android.server.texttospeech.TextToSpeechManagerPerUserService$TextToSpeechSessionConnection$$ExternalSyntheticLambda0
                @Override // com.android.server.texttospeech.TextToSpeechManagerPerUserService.ThrowingRunnable
                public final void runOrThrow() {
                    com.android.server.texttospeech.TextToSpeechManagerPerUserService.TextToSpeechSessionConnection.this.lambda$start$1();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$start$1() throws android.os.RemoteException {
            this.mCallback.onError("Failed creating TTS session");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ java.lang.Void lambda$start$4(final java.lang.Throwable th) {
            android.util.Slog.w(com.android.server.texttospeech.TextToSpeechManagerPerUserService.TAG, "TTS engine binding error", th);
            com.android.server.texttospeech.TextToSpeechManagerPerUserService.runSessionCallbackMethod(new com.android.server.texttospeech.TextToSpeechManagerPerUserService.ThrowingRunnable() { // from class: com.android.server.texttospeech.TextToSpeechManagerPerUserService$TextToSpeechSessionConnection$$ExternalSyntheticLambda6
                @Override // com.android.server.texttospeech.TextToSpeechManagerPerUserService.ThrowingRunnable
                public final void runOrThrow() {
                    com.android.server.texttospeech.TextToSpeechManagerPerUserService.TextToSpeechSessionConnection.this.lambda$start$3(th);
                }
            });
            return null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$start$3(java.lang.Throwable th) throws android.os.RemoteException {
            this.mCallback.onError("Failed creating TTS session: " + th.getCause());
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public void onServiceConnectionStatusChanged(android.speech.tts.ITextToSpeechService iTextToSpeechService, boolean z) {
            if (!z) {
                android.util.Slog.w(com.android.server.texttospeech.TextToSpeechManagerPerUserService.TAG, "Disconnected from TTS engine");
                final android.speech.tts.ITextToSpeechSessionCallback iTextToSpeechSessionCallback = this.mCallback;
                java.util.Objects.requireNonNull(iTextToSpeechSessionCallback);
                com.android.server.texttospeech.TextToSpeechManagerPerUserService.runSessionCallbackMethod(new com.android.server.texttospeech.TextToSpeechManagerPerUserService.ThrowingRunnable() { // from class: com.android.server.texttospeech.TextToSpeechManagerPerUserService$TextToSpeechSessionConnection$$ExternalSyntheticLambda1
                    @Override // com.android.server.texttospeech.TextToSpeechManagerPerUserService.ThrowingRunnable
                    public final void runOrThrow() {
                        iTextToSpeechSessionCallback.onDisconnected();
                    }
                });
                try {
                    this.mCallback.asBinder().unlinkToDeath(this.mUnbindOnDeathHandler, 0);
                } catch (java.util.NoSuchElementException e) {
                    android.util.Slog.d(com.android.server.texttospeech.TextToSpeechManagerPerUserService.TAG, "The death recipient was not linked.");
                }
                this.mCallback = null;
            }
        }

        protected long getAutoDisconnectTimeoutMs() {
            return 0L;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void unbindEngine(java.lang.String str) {
            android.util.Slog.d(com.android.server.texttospeech.TextToSpeechManagerPerUserService.TAG, "Unbinding TTS engine: " + this.mEngine + ". Reason: " + str);
            unbind();
        }
    }

    static void runSessionCallbackMethod(com.android.server.texttospeech.TextToSpeechManagerPerUserService.ThrowingRunnable throwingRunnable) {
        try {
            throwingRunnable.runOrThrow();
        } catch (android.os.RemoteException e) {
            android.util.Slog.i(TAG, "Failed running callback method: " + e);
        }
    }
}
