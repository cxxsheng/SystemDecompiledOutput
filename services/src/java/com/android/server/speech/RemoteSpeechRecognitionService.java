package com.android.server.speech;

/* loaded from: classes2.dex */
final class RemoteSpeechRecognitionService extends com.android.internal.infra.ServiceConnector.Impl<android.speech.IRecognitionService> {
    private static final boolean DEBUG = false;
    private static final int MAX_CONCURRENT_CLIENTS = 100;
    private static final java.lang.String TAG = com.android.server.speech.RemoteSpeechRecognitionService.class.getSimpleName();
    private final int mCallingUid;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.List<android.util.Pair<android.os.IBinder, android.speech.IRecognitionListener>> mClientListeners;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.Map<android.os.IBinder, com.android.server.speech.RemoteSpeechRecognitionService.ClientState> mClients;
    private final android.content.ComponentName mComponentName;
    private boolean mConnected;
    private final java.lang.Object mLock;

    RemoteSpeechRecognitionService(android.content.Context context, android.content.ComponentName componentName, int i, int i2, boolean z) {
        super(context, new android.content.Intent("android.speech.RecognitionService").setComponent(componentName), getBindingFlags(z), i, new java.util.function.Function() { // from class: com.android.server.speech.RemoteSpeechRecognitionService$$ExternalSyntheticLambda8
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return android.speech.IRecognitionService.Stub.asInterface((android.os.IBinder) obj);
            }
        });
        this.mLock = new java.lang.Object();
        this.mConnected = false;
        this.mClients = new java.util.HashMap();
        this.mClientListeners = new java.util.ArrayList();
        this.mCallingUid = i2;
        this.mComponentName = componentName;
    }

    private static int getBindingFlags(boolean z) {
        if (!z) {
            return 1;
        }
        return 67112961;
    }

    android.content.ComponentName getServiceComponentName() {
        return this.mComponentName;
    }

    void startListening(final android.content.Intent intent, final android.speech.IRecognitionListener iRecognitionListener, @android.annotation.NonNull final android.content.AttributionSource attributionSource) {
        if (iRecognitionListener == null) {
            android.util.Slog.w(TAG, "#startListening called with no preceding #setListening - ignoring.");
            return;
        }
        if (!this.mConnected) {
            tryRespondWithError(iRecognitionListener, 11);
            return;
        }
        synchronized (this.mLock) {
            try {
                final com.android.server.speech.RemoteSpeechRecognitionService.ClientState clientState = this.mClients.get(iRecognitionListener.asBinder());
                if (clientState == null) {
                    if (this.mClients.size() >= 100) {
                        tryRespondWithError(iRecognitionListener, 8);
                        android.util.Log.i(TAG, "#startListening received when the recognizer's capacity is full - ignoring this call.");
                        return;
                    } else {
                        clientState = new com.android.server.speech.RemoteSpeechRecognitionService.ClientState();
                        clientState.mDelegatingListener = new com.android.server.speech.RemoteSpeechRecognitionService.DelegatingListener(iRecognitionListener, new java.lang.Runnable() { // from class: com.android.server.speech.RemoteSpeechRecognitionService$$ExternalSyntheticLambda3
                            @Override // java.lang.Runnable
                            public final void run() {
                                com.android.server.speech.RemoteSpeechRecognitionService.this.lambda$startListening$0(clientState);
                            }
                        }, new java.lang.Runnable() { // from class: com.android.server.speech.RemoteSpeechRecognitionService$$ExternalSyntheticLambda4
                            @Override // java.lang.Runnable
                            public final void run() {
                                com.android.server.speech.RemoteSpeechRecognitionService.this.lambda$startListening$1(iRecognitionListener);
                            }
                        });
                        this.mClients.put(iRecognitionListener.asBinder(), clientState);
                    }
                } else {
                    if (clientState.mRecordingInProgress) {
                        android.util.Slog.i(TAG, "#startListening called while listening is in progress for this caller.");
                        tryRespondWithError(iRecognitionListener, 5);
                        return;
                    }
                    clientState.mRecordingInProgress = true;
                }
                final com.android.server.speech.RemoteSpeechRecognitionService.DelegatingListener delegatingListener = clientState.mDelegatingListener;
                run(new com.android.internal.infra.ServiceConnector.VoidJob() { // from class: com.android.server.speech.RemoteSpeechRecognitionService$$ExternalSyntheticLambda5
                    public final void runNoResult(java.lang.Object obj) {
                        ((android.speech.IRecognitionService) obj).startListening(intent, delegatingListener, attributionSource);
                    }
                });
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startListening$0(com.android.server.speech.RemoteSpeechRecognitionService.ClientState clientState) {
        synchronized (this.mLock) {
            clientState.mRecordingInProgress = false;
        }
    }

    void stopListening(android.speech.IRecognitionListener iRecognitionListener) {
        if (!this.mConnected) {
            tryRespondWithError(iRecognitionListener, 11);
            return;
        }
        synchronized (this.mLock) {
            try {
                com.android.server.speech.RemoteSpeechRecognitionService.ClientState clientState = this.mClients.get(iRecognitionListener.asBinder());
                if (clientState == null) {
                    android.util.Slog.w(TAG, "#stopListening called with no preceding #startListening - ignoring.");
                    tryRespondWithError(iRecognitionListener, 5);
                } else if (!clientState.mRecordingInProgress) {
                    tryRespondWithError(iRecognitionListener, 5);
                    android.util.Slog.i(TAG, "#stopListening called while listening isn't in progress - ignoring.");
                } else {
                    clientState.mRecordingInProgress = false;
                    final com.android.server.speech.RemoteSpeechRecognitionService.DelegatingListener delegatingListener = clientState.mDelegatingListener;
                    run(new com.android.internal.infra.ServiceConnector.VoidJob() { // from class: com.android.server.speech.RemoteSpeechRecognitionService$$ExternalSyntheticLambda7
                        public final void runNoResult(java.lang.Object obj) {
                            ((android.speech.IRecognitionService) obj).stopListening(com.android.server.speech.RemoteSpeechRecognitionService.DelegatingListener.this);
                        }
                    });
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void cancel(android.speech.IRecognitionListener iRecognitionListener, final boolean z) {
        if (!this.mConnected) {
            tryRespondWithError(iRecognitionListener, 11);
        }
        synchronized (this.mLock) {
            try {
                com.android.server.speech.RemoteSpeechRecognitionService.ClientState clientState = this.mClients.get(iRecognitionListener.asBinder());
                if (clientState != null) {
                    clientState.mRecordingInProgress = false;
                    final com.android.server.speech.RemoteSpeechRecognitionService.DelegatingListener delegatingListener = clientState.mDelegatingListener;
                    run(new com.android.internal.infra.ServiceConnector.VoidJob() { // from class: com.android.server.speech.RemoteSpeechRecognitionService$$ExternalSyntheticLambda1
                        public final void runNoResult(java.lang.Object obj) {
                            ((android.speech.IRecognitionService) obj).cancel(delegatingListener, z);
                        }
                    });
                }
                if (z) {
                    lambda$startListening$1(iRecognitionListener);
                    if (this.mClients.isEmpty()) {
                        run(new com.android.internal.infra.ServiceConnector.VoidJob() { // from class: com.android.server.speech.RemoteSpeechRecognitionService$$ExternalSyntheticLambda2
                            public final void runNoResult(java.lang.Object obj) {
                                com.android.server.speech.RemoteSpeechRecognitionService.this.lambda$cancel$5((android.speech.IRecognitionService) obj);
                            }
                        });
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$cancel$5(android.speech.IRecognitionService iRecognitionService) throws java.lang.Exception {
        unbind();
    }

    void checkRecognitionSupport(final android.content.Intent intent, final android.content.AttributionSource attributionSource, final android.speech.IRecognitionSupportCallback iRecognitionSupportCallback) {
        if (!this.mConnected) {
            try {
                iRecognitionSupportCallback.onError(11);
                return;
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(TAG, "Failed to report the connection broke to the caller.", e);
                e.printStackTrace();
                return;
            }
        }
        run(new com.android.internal.infra.ServiceConnector.VoidJob() { // from class: com.android.server.speech.RemoteSpeechRecognitionService$$ExternalSyntheticLambda0
            public final void runNoResult(java.lang.Object obj) {
                ((android.speech.IRecognitionService) obj).checkRecognitionSupport(intent, attributionSource, iRecognitionSupportCallback);
            }
        });
    }

    void triggerModelDownload(final android.content.Intent intent, final android.content.AttributionSource attributionSource, final android.speech.IModelDownloadListener iModelDownloadListener) {
        if (!this.mConnected) {
            try {
                iModelDownloadListener.onError(11);
                return;
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(TAG, "#downloadModel failed due to connection.", e);
                e.printStackTrace();
                return;
            }
        }
        run(new com.android.internal.infra.ServiceConnector.VoidJob() { // from class: com.android.server.speech.RemoteSpeechRecognitionService$$ExternalSyntheticLambda6
            public final void runNoResult(java.lang.Object obj) {
                ((android.speech.IRecognitionService) obj).triggerModelDownload(intent, attributionSource, iModelDownloadListener);
            }
        });
    }

    void shutdown(android.os.IBinder iBinder) {
        synchronized (this.mLock) {
            try {
                for (android.util.Pair<android.os.IBinder, android.speech.IRecognitionListener> pair : this.mClientListeners) {
                    if (pair.first == iBinder) {
                        cancel((android.speech.IRecognitionListener) pair.second, true);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onServiceConnectionStatusChanged(android.speech.IRecognitionService iRecognitionService, boolean z) {
        this.mConnected = z;
        synchronized (this.mLock) {
            if (!z) {
                try {
                    if (this.mClients.isEmpty()) {
                        android.util.Slog.i(TAG, "Connection to speech recognition service lost, but no #startListening has been invoked yet.");
                        return;
                    }
                    for (com.android.server.speech.RemoteSpeechRecognitionService.ClientState clientState : (com.android.server.speech.RemoteSpeechRecognitionService.ClientState[]) this.mClients.values().toArray(new com.android.server.speech.RemoteSpeechRecognitionService.ClientState[0])) {
                        tryRespondWithError(clientState.mDelegatingListener.mRemoteListener, 11);
                        lambda$startListening$1(clientState.mDelegatingListener.mRemoteListener);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    protected long getAutoDisconnectTimeoutMs() {
        return 0L;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: removeClient, reason: merged with bridge method [inline-methods] */
    public void lambda$startListening$1(final android.speech.IRecognitionListener iRecognitionListener) {
        synchronized (this.mLock) {
            try {
                com.android.server.speech.RemoteSpeechRecognitionService.ClientState remove = this.mClients.remove(iRecognitionListener.asBinder());
                if (remove != null) {
                    remove.reset();
                }
                this.mClientListeners.removeIf(new java.util.function.Predicate() { // from class: com.android.server.speech.RemoteSpeechRecognitionService$$ExternalSyntheticLambda9
                    @Override // java.util.function.Predicate
                    public final boolean test(java.lang.Object obj) {
                        boolean lambda$removeClient$8;
                        lambda$removeClient$8 = com.android.server.speech.RemoteSpeechRecognitionService.lambda$removeClient$8(iRecognitionListener, (android.util.Pair) obj);
                        return lambda$removeClient$8;
                    }
                });
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$removeClient$8(android.speech.IRecognitionListener iRecognitionListener, android.util.Pair pair) {
        return pair.second == iRecognitionListener;
    }

    private static void tryRespondWithError(android.speech.IRecognitionListener iRecognitionListener, int i) {
        if (iRecognitionListener != null) {
            try {
                iRecognitionListener.onError(i);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(TAG, android.text.TextUtils.formatSimple("Failed to respond with an error %d to the client", new java.lang.Object[]{java.lang.Integer.valueOf(i)}), e);
            }
        }
    }

    boolean hasActiveSessions() {
        boolean z;
        synchronized (this.mLock) {
            z = !this.mClients.isEmpty();
        }
        return z;
    }

    void associateClientWithActiveListener(android.os.IBinder iBinder, android.speech.IRecognitionListener iRecognitionListener) {
        synchronized (this.mLock) {
            try {
                if (this.mClients.containsKey(iRecognitionListener.asBinder())) {
                    this.mClientListeners.add(new android.util.Pair<>(iBinder, iRecognitionListener));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class DelegatingListener extends android.speech.IRecognitionListener.Stub {
        private final java.lang.Runnable mOnSessionFailure;
        private final java.lang.Runnable mOnSessionSuccess;
        private final android.speech.IRecognitionListener mRemoteListener;

        DelegatingListener(android.speech.IRecognitionListener iRecognitionListener, java.lang.Runnable runnable, java.lang.Runnable runnable2) {
            this.mRemoteListener = iRecognitionListener;
            this.mOnSessionSuccess = runnable;
            this.mOnSessionFailure = runnable2;
        }

        public void onReadyForSpeech(android.os.Bundle bundle) throws android.os.RemoteException {
            this.mRemoteListener.onReadyForSpeech(bundle);
        }

        public void onBeginningOfSpeech() throws android.os.RemoteException {
            this.mRemoteListener.onBeginningOfSpeech();
        }

        public void onRmsChanged(float f) throws android.os.RemoteException {
            this.mRemoteListener.onRmsChanged(f);
        }

        public void onBufferReceived(byte[] bArr) throws android.os.RemoteException {
            this.mRemoteListener.onBufferReceived(bArr);
        }

        public void onEndOfSpeech() throws android.os.RemoteException {
            this.mRemoteListener.onEndOfSpeech();
        }

        public void onError(int i) throws android.os.RemoteException {
            this.mOnSessionFailure.run();
            this.mRemoteListener.onError(i);
        }

        public void onResults(android.os.Bundle bundle) throws android.os.RemoteException {
            this.mOnSessionSuccess.run();
            this.mRemoteListener.onResults(bundle);
        }

        public void onPartialResults(android.os.Bundle bundle) throws android.os.RemoteException {
            this.mRemoteListener.onPartialResults(bundle);
        }

        public void onSegmentResults(android.os.Bundle bundle) throws android.os.RemoteException {
            this.mRemoteListener.onSegmentResults(bundle);
        }

        public void onEndOfSegmentedSession() throws android.os.RemoteException {
            this.mOnSessionSuccess.run();
            this.mRemoteListener.onEndOfSegmentedSession();
        }

        public void onLanguageDetection(android.os.Bundle bundle) throws android.os.RemoteException {
            this.mRemoteListener.onLanguageDetection(bundle);
        }

        public void onEvent(int i, android.os.Bundle bundle) throws android.os.RemoteException {
            this.mRemoteListener.onEvent(i, bundle);
        }
    }

    static class ClientState {
        com.android.server.speech.RemoteSpeechRecognitionService.DelegatingListener mDelegatingListener;
        boolean mRecordingInProgress;

        ClientState(com.android.server.speech.RemoteSpeechRecognitionService.DelegatingListener delegatingListener, boolean z) {
            this.mDelegatingListener = delegatingListener;
            this.mRecordingInProgress = z;
        }

        ClientState(com.android.server.speech.RemoteSpeechRecognitionService.DelegatingListener delegatingListener) {
            this(delegatingListener, true);
        }

        ClientState() {
            this(null, true);
        }

        void reset() {
            this.mDelegatingListener = null;
            this.mRecordingInProgress = false;
        }
    }
}
