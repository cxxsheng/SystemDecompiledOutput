package android.speech;

/* loaded from: classes3.dex */
class SpeechRecognizerImpl extends android.speech.SpeechRecognizer {
    private static final boolean DBG = false;
    private static final int MSG_CANCEL = 3;
    private static final int MSG_CHANGE_LISTENER = 4;
    private static final int MSG_CHECK_RECOGNITION_SUPPORT = 6;
    private static final int MSG_DESTROY = 8;
    private static final int MSG_SET_TEMPORARY_ON_DEVICE_COMPONENT = 5;
    private static final int MSG_START = 1;
    private static final int MSG_STOP = 2;
    private static final int MSG_TRIGGER_MODEL_DOWNLOAD = 7;
    private static final java.lang.String TAG = "SpeechRecognizer";
    private final android.os.IBinder mClientToken;
    private final android.content.Context mContext;
    private final android.os.Handler mHandler;
    private final android.speech.SpeechRecognizerImpl.InternalRecognitionListener mListener;
    private android.speech.IRecognitionServiceManager mManagerService;
    private final boolean mOnDevice;
    private final java.util.Queue<android.os.Message> mPendingTasks;
    private android.speech.IRecognitionService mService;
    private final android.content.ComponentName mServiceComponent;

    SpeechRecognizerImpl(android.content.Context context, android.content.ComponentName componentName) {
        this(context, componentName, false);
    }

    SpeechRecognizerImpl(android.content.Context context, boolean z) {
        this(context, null, z);
    }

    private SpeechRecognizerImpl(android.content.Context context, android.content.ComponentName componentName, boolean z) {
        this.mHandler = new android.os.Handler(android.os.Looper.getMainLooper()) { // from class: android.speech.SpeechRecognizerImpl.1
            @Override // android.os.Handler
            public void handleMessage(android.os.Message message) {
                switch (message.what) {
                    case 1:
                        android.speech.SpeechRecognizerImpl.this.handleStartListening((android.content.Intent) message.obj);
                        break;
                    case 2:
                        android.speech.SpeechRecognizerImpl.this.handleStopMessage();
                        break;
                    case 3:
                        android.speech.SpeechRecognizerImpl.this.handleCancelMessage();
                        break;
                    case 4:
                        android.speech.SpeechRecognizerImpl.this.handleChangeListener((android.speech.RecognitionListener) message.obj);
                        break;
                    case 5:
                        android.speech.SpeechRecognizerImpl.this.handleSetTemporaryComponent((android.content.ComponentName) message.obj);
                        break;
                    case 6:
                        android.speech.SpeechRecognizerImpl.CheckRecognitionSupportArgs checkRecognitionSupportArgs = (android.speech.SpeechRecognizerImpl.CheckRecognitionSupportArgs) message.obj;
                        android.speech.SpeechRecognizerImpl.this.handleCheckRecognitionSupport(checkRecognitionSupportArgs.mIntent, checkRecognitionSupportArgs.mCallbackExecutor, checkRecognitionSupportArgs.mCallback);
                        break;
                    case 7:
                        android.speech.SpeechRecognizerImpl.ModelDownloadListenerArgs modelDownloadListenerArgs = (android.speech.SpeechRecognizerImpl.ModelDownloadListenerArgs) message.obj;
                        android.speech.SpeechRecognizerImpl.this.handleTriggerModelDownload(modelDownloadListenerArgs.mIntent, modelDownloadListenerArgs.mExecutor, modelDownloadListenerArgs.mModelDownloadListener);
                        break;
                    case 8:
                        android.speech.SpeechRecognizerImpl.this.handleDestroy();
                        break;
                }
            }
        };
        this.mPendingTasks = new java.util.concurrent.LinkedBlockingQueue();
        this.mListener = new android.speech.SpeechRecognizerImpl.InternalRecognitionListener();
        this.mClientToken = new android.os.Binder();
        this.mContext = context;
        this.mServiceComponent = componentName;
        this.mOnDevice = z;
    }

    static android.speech.SpeechRecognizerImpl lenientlyCreateOnDeviceSpeechRecognizer(android.content.Context context) {
        if (context == null) {
            throw new java.lang.IllegalArgumentException("Context cannot be null");
        }
        checkIsCalledFromMainThread();
        return new android.speech.SpeechRecognizerImpl(context, true);
    }

    @Override // android.speech.SpeechRecognizer
    public void setRecognitionListener(android.speech.RecognitionListener recognitionListener) {
        checkIsCalledFromMainThread();
        if (this.mListener.mInternalListener == null) {
            handleChangeListener(recognitionListener);
        } else {
            putMessage(android.os.Message.obtain(this.mHandler, 4, recognitionListener));
        }
    }

    @Override // android.speech.SpeechRecognizer
    public void startListening(android.content.Intent intent) {
        if (intent == null) {
            throw new java.lang.IllegalArgumentException("intent must not be null");
        }
        checkIsCalledFromMainThread();
        if (this.mService == null) {
            connectToSystemService();
        }
        putMessage(android.os.Message.obtain(this.mHandler, 1, intent));
    }

    @Override // android.speech.SpeechRecognizer
    public void stopListening() {
        checkIsCalledFromMainThread();
        putMessage(android.os.Message.obtain(this.mHandler, 2));
    }

    @Override // android.speech.SpeechRecognizer
    public void cancel() {
        checkIsCalledFromMainThread();
        putMessage(android.os.Message.obtain(this.mHandler, 3));
    }

    @Override // android.speech.SpeechRecognizer
    public void checkRecognitionSupport(android.content.Intent intent, java.util.concurrent.Executor executor, android.speech.RecognitionSupportCallback recognitionSupportCallback) {
        java.util.Objects.requireNonNull(intent, "intent must not be null");
        java.util.Objects.requireNonNull(recognitionSupportCallback, "listener must not be null");
        if (this.mService == null) {
            connectToSystemService();
        }
        putMessage(android.os.Message.obtain(this.mHandler, 6, new android.speech.SpeechRecognizerImpl.CheckRecognitionSupportArgs(intent, executor, recognitionSupportCallback)));
    }

    @Override // android.speech.SpeechRecognizer
    public void triggerModelDownload(android.content.Intent intent) {
        java.util.Objects.requireNonNull(intent, "intent must not be null");
        if (this.mService == null) {
            connectToSystemService();
        }
        putMessage(android.os.Message.obtain(this.mHandler, 7, new android.speech.SpeechRecognizerImpl.ModelDownloadListenerArgs(intent, null, 0 == true ? 1 : 0)));
    }

    @Override // android.speech.SpeechRecognizer
    public void triggerModelDownload(android.content.Intent intent, java.util.concurrent.Executor executor, android.speech.ModelDownloadListener modelDownloadListener) {
        java.util.Objects.requireNonNull(intent, "intent must not be null");
        if (this.mService == null) {
            connectToSystemService();
        }
        putMessage(android.os.Message.obtain(this.mHandler, 7, new android.speech.SpeechRecognizerImpl.ModelDownloadListenerArgs(intent, executor, modelDownloadListener)));
    }

    @Override // android.speech.SpeechRecognizer
    public void setTemporaryOnDeviceRecognizer(android.content.ComponentName componentName) {
        this.mHandler.sendMessage(android.os.Message.obtain(this.mHandler, 5, componentName));
    }

    static void checkIsCalledFromMainThread() {
        if (android.os.Looper.myLooper() != android.os.Looper.getMainLooper()) {
            throw new java.lang.RuntimeException("SpeechRecognizer should be used only from the application's main thread");
        }
    }

    private void putMessage(android.os.Message message) {
        if (this.mService == null) {
            this.mPendingTasks.offer(message);
        } else {
            this.mHandler.sendMessage(message);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleStartListening(android.content.Intent intent) {
        if (!checkOpenConnection()) {
            return;
        }
        try {
            this.mService.startListening(intent, this.mListener, this.mContext.getAttributionSource());
        } catch (java.lang.Exception e) {
            android.util.Log.e(TAG, "startListening() failed", e);
            this.mListener.onError(5);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleStopMessage() {
        if (!checkOpenConnection()) {
            return;
        }
        try {
            this.mService.stopListening(this.mListener);
        } catch (java.lang.Exception e) {
            android.util.Log.e(TAG, "stopListening() failed", e);
            this.mListener.onError(5);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleCancelMessage() {
        if (!checkOpenConnection()) {
            return;
        }
        try {
            this.mService.cancel(this.mListener, false);
        } catch (java.lang.Exception e) {
            android.util.Log.e(TAG, "cancel() failed", e);
            this.mListener.onError(5);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleSetTemporaryComponent(android.content.ComponentName componentName) {
        if (!maybeInitializeManagerService()) {
            return;
        }
        try {
            this.mManagerService.setTemporaryComponent(componentName);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleCheckRecognitionSupport(android.content.Intent intent, java.util.concurrent.Executor executor, final android.speech.RecognitionSupportCallback recognitionSupportCallback) {
        if (!maybeInitializeManagerService() || !checkOpenConnection()) {
            return;
        }
        try {
            this.mService.checkRecognitionSupport(intent, this.mContext.getAttributionSource(), new android.speech.SpeechRecognizerImpl.InternalSupportCallback(executor, recognitionSupportCallback));
        } catch (java.lang.Exception e) {
            android.util.Log.e(TAG, "checkRecognitionSupport() failed", e);
            executor.execute(new java.lang.Runnable() { // from class: android.speech.SpeechRecognizerImpl$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.speech.RecognitionSupportCallback.this.onError(5);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleTriggerModelDownload(android.content.Intent intent, java.util.concurrent.Executor executor, final android.speech.ModelDownloadListener modelDownloadListener) {
        if (!maybeInitializeManagerService() || !checkOpenConnection()) {
            return;
        }
        if (modelDownloadListener == null) {
            try {
                this.mService.triggerModelDownload(intent, this.mContext.getAttributionSource(), null);
                return;
            } catch (java.lang.Exception e) {
                android.util.Log.e(TAG, "triggerModelDownload() without a listener failed", e);
                this.mListener.onError(5);
                return;
            }
        }
        try {
            this.mService.triggerModelDownload(intent, this.mContext.getAttributionSource(), new android.speech.SpeechRecognizerImpl.InternalModelDownloadListener(executor, modelDownloadListener));
        } catch (java.lang.Exception e2) {
            android.util.Log.e(TAG, "triggerModelDownload() with a listener failed", e2);
            executor.execute(new java.lang.Runnable() { // from class: android.speech.SpeechRecognizerImpl$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.speech.ModelDownloadListener.this.onError(5);
                }
            });
        }
    }

    private boolean checkOpenConnection() {
        if (this.mService != null && this.mService.asBinder().isBinderAlive()) {
            return true;
        }
        this.mListener.onError(5);
        android.util.Log.e(TAG, "not connected to the recognition service");
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleChangeListener(android.speech.RecognitionListener recognitionListener) {
        this.mListener.mInternalListener = recognitionListener;
    }

    @Override // android.speech.SpeechRecognizer
    public void destroy() {
        putMessage(this.mHandler.obtainMessage(8));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDestroy() {
        if (this.mService != null) {
            try {
                this.mService.cancel(this.mListener, true);
            } catch (java.lang.Exception e) {
            }
        }
        this.mService = null;
        this.mPendingTasks.clear();
        this.mListener.mInternalListener = null;
    }

    private void connectToSystemService() {
        if (!maybeInitializeManagerService()) {
            return;
        }
        android.content.ComponentName speechRecognizerComponentName = getSpeechRecognizerComponentName();
        if (!this.mOnDevice && speechRecognizerComponentName == null) {
            this.mListener.onError(5);
            return;
        }
        try {
            this.mManagerService.createSession(speechRecognizerComponentName, this.mClientToken, this.mOnDevice, new android.speech.IRecognitionServiceManagerCallback.Stub() { // from class: android.speech.SpeechRecognizerImpl.2
                @Override // android.speech.IRecognitionServiceManagerCallback
                public void onSuccess(android.speech.IRecognitionService iRecognitionService) throws android.os.RemoteException {
                    android.speech.SpeechRecognizerImpl.this.mService = iRecognitionService;
                    while (!android.speech.SpeechRecognizerImpl.this.mPendingTasks.isEmpty()) {
                        android.speech.SpeechRecognizerImpl.this.mHandler.sendMessage((android.os.Message) android.speech.SpeechRecognizerImpl.this.mPendingTasks.poll());
                    }
                }

                @Override // android.speech.IRecognitionServiceManagerCallback
                public void onError(int i) throws android.os.RemoteException {
                    android.util.Log.e(android.speech.SpeechRecognizerImpl.TAG, "Bind to system recognition service failed with error " + i);
                    android.speech.SpeechRecognizerImpl.this.mListener.onError(i);
                }
            });
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    private synchronized boolean maybeInitializeManagerService() {
        if (this.mManagerService != null) {
            return true;
        }
        android.os.IBinder service = android.os.ServiceManager.getService(android.content.Context.SPEECH_RECOGNITION_SERVICE);
        if (service == null && this.mOnDevice) {
            service = (android.os.IBinder) this.mContext.getSystemService(android.content.Context.SPEECH_RECOGNITION_SERVICE);
        }
        this.mManagerService = android.speech.IRecognitionServiceManager.Stub.asInterface(service);
        if (this.mManagerService != null) {
            return true;
        }
        if (this.mListener != null) {
            this.mListener.onError(5);
        }
        return false;
    }

    private android.content.ComponentName getSpeechRecognizerComponentName() {
        if (this.mOnDevice) {
            return null;
        }
        if (this.mServiceComponent != null) {
            return this.mServiceComponent;
        }
        java.lang.String string = android.provider.Settings.Secure.getString(this.mContext.getContentResolver(), android.provider.Settings.Secure.VOICE_RECOGNITION_SERVICE);
        if (android.text.TextUtils.isEmpty(string)) {
            android.util.Log.e(TAG, "no selected voice recognition service");
            this.mListener.onError(5);
            return null;
        }
        return android.content.ComponentName.unflattenFromString(string);
    }

    private static class CheckRecognitionSupportArgs {
        final android.speech.RecognitionSupportCallback mCallback;
        final java.util.concurrent.Executor mCallbackExecutor;
        final android.content.Intent mIntent;

        private CheckRecognitionSupportArgs(android.content.Intent intent, java.util.concurrent.Executor executor, android.speech.RecognitionSupportCallback recognitionSupportCallback) {
            this.mIntent = intent;
            this.mCallbackExecutor = executor;
            this.mCallback = recognitionSupportCallback;
        }
    }

    private static class ModelDownloadListenerArgs {
        final java.util.concurrent.Executor mExecutor;
        final android.content.Intent mIntent;
        final android.speech.ModelDownloadListener mModelDownloadListener;

        private ModelDownloadListenerArgs(android.content.Intent intent, java.util.concurrent.Executor executor, android.speech.ModelDownloadListener modelDownloadListener) {
            this.mIntent = intent;
            this.mExecutor = executor;
            this.mModelDownloadListener = modelDownloadListener;
        }
    }

    private static class InternalRecognitionListener extends android.speech.IRecognitionListener.Stub {
        private static final int MSG_BEGINNING_OF_SPEECH = 1;
        private static final int MSG_BUFFER_RECEIVED = 2;
        private static final int MSG_END_OF_SPEECH = 3;
        private static final int MSG_ERROR = 4;
        private static final int MSG_LANGUAGE_DETECTION = 12;
        private static final int MSG_ON_EVENT = 9;
        private static final int MSG_PARTIAL_RESULTS = 7;
        private static final int MSG_READY_FOR_SPEECH = 5;
        private static final int MSG_RESULTS = 6;
        private static final int MSG_RMS_CHANGED = 8;
        private static final int MSG_SEGMENT_END_SESSION = 11;
        private static final int MSG_SEGMENT_RESULTS = 10;
        private final android.os.Handler mInternalHandler;
        private android.speech.RecognitionListener mInternalListener;

        private InternalRecognitionListener() {
            this.mInternalHandler = new android.os.Handler(android.os.Looper.getMainLooper()) { // from class: android.speech.SpeechRecognizerImpl.InternalRecognitionListener.1
                @Override // android.os.Handler
                public void handleMessage(android.os.Message message) {
                    if (android.speech.SpeechRecognizerImpl.InternalRecognitionListener.this.mInternalListener == null) {
                    }
                    switch (message.what) {
                        case 1:
                            android.speech.SpeechRecognizerImpl.InternalRecognitionListener.this.mInternalListener.onBeginningOfSpeech();
                            break;
                        case 2:
                            android.speech.SpeechRecognizerImpl.InternalRecognitionListener.this.mInternalListener.onBufferReceived((byte[]) message.obj);
                            break;
                        case 3:
                            android.speech.SpeechRecognizerImpl.InternalRecognitionListener.this.mInternalListener.onEndOfSpeech();
                            break;
                        case 4:
                            android.speech.SpeechRecognizerImpl.InternalRecognitionListener.this.mInternalListener.onError(((java.lang.Integer) message.obj).intValue());
                            break;
                        case 5:
                            android.speech.SpeechRecognizerImpl.InternalRecognitionListener.this.mInternalListener.onReadyForSpeech((android.os.Bundle) message.obj);
                            break;
                        case 6:
                            android.speech.SpeechRecognizerImpl.InternalRecognitionListener.this.mInternalListener.onResults((android.os.Bundle) message.obj);
                            break;
                        case 7:
                            android.speech.SpeechRecognizerImpl.InternalRecognitionListener.this.mInternalListener.onPartialResults((android.os.Bundle) message.obj);
                            break;
                        case 8:
                            android.speech.SpeechRecognizerImpl.InternalRecognitionListener.this.mInternalListener.onRmsChanged(((java.lang.Float) message.obj).floatValue());
                            break;
                        case 9:
                            android.speech.SpeechRecognizerImpl.InternalRecognitionListener.this.mInternalListener.onEvent(message.arg1, (android.os.Bundle) message.obj);
                            break;
                        case 10:
                            android.speech.SpeechRecognizerImpl.InternalRecognitionListener.this.mInternalListener.onSegmentResults((android.os.Bundle) message.obj);
                            break;
                        case 11:
                            android.speech.SpeechRecognizerImpl.InternalRecognitionListener.this.mInternalListener.onEndOfSegmentedSession();
                            break;
                        case 12:
                            android.speech.SpeechRecognizerImpl.InternalRecognitionListener.this.mInternalListener.onLanguageDetection((android.os.Bundle) message.obj);
                            break;
                    }
                }
            };
        }

        @Override // android.speech.IRecognitionListener
        public void onBeginningOfSpeech() {
            android.os.Message.obtain(this.mInternalHandler, 1).sendToTarget();
        }

        @Override // android.speech.IRecognitionListener
        public void onBufferReceived(byte[] bArr) {
            android.os.Message.obtain(this.mInternalHandler, 2, bArr).sendToTarget();
        }

        @Override // android.speech.IRecognitionListener
        public void onEndOfSpeech() {
            android.os.Message.obtain(this.mInternalHandler, 3).sendToTarget();
        }

        @Override // android.speech.IRecognitionListener
        public void onError(int i) {
            android.os.Message.obtain(this.mInternalHandler, 4, java.lang.Integer.valueOf(i)).sendToTarget();
        }

        @Override // android.speech.IRecognitionListener
        public void onReadyForSpeech(android.os.Bundle bundle) {
            android.os.Message.obtain(this.mInternalHandler, 5, bundle).sendToTarget();
        }

        @Override // android.speech.IRecognitionListener
        public void onResults(android.os.Bundle bundle) {
            android.os.Message.obtain(this.mInternalHandler, 6, bundle).sendToTarget();
        }

        @Override // android.speech.IRecognitionListener
        public void onPartialResults(android.os.Bundle bundle) {
            android.os.Message.obtain(this.mInternalHandler, 7, bundle).sendToTarget();
        }

        @Override // android.speech.IRecognitionListener
        public void onRmsChanged(float f) {
            android.os.Message.obtain(this.mInternalHandler, 8, java.lang.Float.valueOf(f)).sendToTarget();
        }

        @Override // android.speech.IRecognitionListener
        public void onSegmentResults(android.os.Bundle bundle) {
            android.os.Message.obtain(this.mInternalHandler, 10, bundle).sendToTarget();
        }

        @Override // android.speech.IRecognitionListener
        public void onEndOfSegmentedSession() {
            android.os.Message.obtain(this.mInternalHandler, 11).sendToTarget();
        }

        @Override // android.speech.IRecognitionListener
        public void onLanguageDetection(android.os.Bundle bundle) {
            android.os.Message.obtain(this.mInternalHandler, 12, bundle).sendToTarget();
        }

        @Override // android.speech.IRecognitionListener
        public void onEvent(int i, android.os.Bundle bundle) {
            android.os.Message.obtain(this.mInternalHandler, 9, i, i, bundle).sendToTarget();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class InternalSupportCallback extends android.speech.IRecognitionSupportCallback.Stub {
        private final android.speech.RecognitionSupportCallback mCallback;
        private final java.util.concurrent.Executor mExecutor;

        private InternalSupportCallback(java.util.concurrent.Executor executor, android.speech.RecognitionSupportCallback recognitionSupportCallback) {
            this.mExecutor = executor;
            this.mCallback = recognitionSupportCallback;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSupportResult$0(android.speech.RecognitionSupport recognitionSupport) {
            this.mCallback.onSupportResult(recognitionSupport);
        }

        @Override // android.speech.IRecognitionSupportCallback
        public void onSupportResult(final android.speech.RecognitionSupport recognitionSupport) throws android.os.RemoteException {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.speech.SpeechRecognizerImpl$InternalSupportCallback$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.speech.SpeechRecognizerImpl.InternalSupportCallback.this.lambda$onSupportResult$0(recognitionSupport);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onError$1(int i) {
            this.mCallback.onError(i);
        }

        @Override // android.speech.IRecognitionSupportCallback
        public void onError(final int i) throws android.os.RemoteException {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.speech.SpeechRecognizerImpl$InternalSupportCallback$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.speech.SpeechRecognizerImpl.InternalSupportCallback.this.lambda$onError$1(i);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class InternalModelDownloadListener extends android.speech.IModelDownloadListener.Stub {
        private final java.util.concurrent.Executor mExecutor;
        private final android.speech.ModelDownloadListener mModelDownloadListener;

        private InternalModelDownloadListener(java.util.concurrent.Executor executor, android.speech.ModelDownloadListener modelDownloadListener) {
            this.mExecutor = executor;
            this.mModelDownloadListener = modelDownloadListener;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onProgress$0(int i) {
            this.mModelDownloadListener.onProgress(i);
        }

        @Override // android.speech.IModelDownloadListener
        public void onProgress(final int i) throws android.os.RemoteException {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.speech.SpeechRecognizerImpl$InternalModelDownloadListener$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    android.speech.SpeechRecognizerImpl.InternalModelDownloadListener.this.lambda$onProgress$0(i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$1() {
            this.mModelDownloadListener.onSuccess();
        }

        @Override // android.speech.IModelDownloadListener
        public void onSuccess() throws android.os.RemoteException {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.speech.SpeechRecognizerImpl$InternalModelDownloadListener$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    android.speech.SpeechRecognizerImpl.InternalModelDownloadListener.this.lambda$onSuccess$1();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onScheduled$2() {
            this.mModelDownloadListener.onScheduled();
        }

        @Override // android.speech.IModelDownloadListener
        public void onScheduled() throws android.os.RemoteException {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.speech.SpeechRecognizerImpl$InternalModelDownloadListener$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.speech.SpeechRecognizerImpl.InternalModelDownloadListener.this.lambda$onScheduled$2();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onError$3(int i) {
            this.mModelDownloadListener.onError(i);
        }

        @Override // android.speech.IModelDownloadListener
        public void onError(final int i) throws android.os.RemoteException {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.speech.SpeechRecognizerImpl$InternalModelDownloadListener$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.speech.SpeechRecognizerImpl.InternalModelDownloadListener.this.lambda$onError$3(i);
                }
            });
        }
    }
}
